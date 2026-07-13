package net.xdclass.component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.enums.BizCodeEnum;
import net.xdclass.exception.BizException;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 基于 Amazon S3 SDK 实现的 MinIO 文件存储引擎。
 *
 * <p>MinIO 兼容 S3 协议，因此可以直接通过 {@link AmazonS3Client} 完成存储桶、
 * 文件上传、删除和下载等操作。所有 SDK 调用统一交由 {@link #execute(String, StorageOperation)}
 * 处理日志和异常转换。</p>
 */
@Component
@Slf4j
public class MinIOFileStoreEngine implements StoreEngine {

    /** S3 客户端，由 Spring 容器注入。 */
    @Resource
    private AmazonS3Client amazonS3Client;

    /**
     * 检查指定存储桶是否存在。
     */
    @Override
    public boolean bucketExists(String bucketName) {
        return execute("检查存储桶", () -> amazonS3Client.doesBucketExistV2(bucketName));
    }

    /**
     * 删除空存储桶。
     *
     * <p>存储桶不存在或桶内仍有对象时返回 {@code false}；调用 MinIO 失败时抛出业务异常。</p>
     */
    @Override
    public boolean removeBucket(String bucketName) {
        return execute("删除存储桶", () -> {
            if (!amazonS3Client.doesBucketExistV2(bucketName)) {
                return false;
            }

            // MinIO 不允许直接删除非空存储桶，删除前先检查桶内对象。
            ListObjectsV2Result result = amazonS3Client.listObjectsV2(bucketName);
            if (!result.getObjectSummaries().isEmpty()) {
                return false;
            }

            amazonS3Client.deleteBucket(bucketName);
            return !amazonS3Client.doesBucketExistV2(bucketName);
        });
    }

    /**
     * 创建存储桶；如果存储桶已存在则直接返回，保证重复调用安全。
     */
    @Override
    public void createBucket(String bucketName) {
        execute("创建存储桶", () -> {
            if (amazonS3Client.doesBucketExistV2(bucketName)) {
                log.info("Bucket {} already exists.", bucketName);
                return null;
            }

            amazonS3Client.createBucket(bucketName);
            log.info("Bucket {} created.", bucketName);
            return null;
        });
    }

    /**
     * 查询当前账号有权限访问的全部存储桶。
     */
    @Override
    public List<Bucket> getAllBucket() {
        return execute("查询存储桶列表", amazonS3Client::listBuckets);
    }

    /**
     * 查询指定存储桶内的对象；存储桶不存在时返回空集合。
     */
    @Override
    public List<S3ObjectSummary> listObjects(String bucketName) {
        return execute("查询对象列表", () -> {
            if (!amazonS3Client.doesBucketExistV2(bucketName)) {
                return List.of();
            }
            return amazonS3Client.listObjectsV2(bucketName).getObjectSummaries();
        });
    }

    /**
     * 检查指定对象是否存在。
     */
    @Override
    public boolean doesObjectExist(String bucketName, String objectKey) {
        return execute("检查对象", () -> amazonS3Client.doesObjectExist(bucketName, objectKey));
    }

    /**
     * 将本地文件上传至指定存储桶。
     */
    @Override
    public boolean upload(String bucketName, String objectKey, String localFileName) {
        return execute("上传本地文件", () -> {
            amazonS3Client.putObject(bucketName, objectKey, new File(localFileName));
            return true;
        });
    }

    /**
     * 上传 Web 请求中的文件，并将文件大小及媒体类型写入对象元数据。
     */
    @Override
    public boolean upload(String bucketName, String objectKey, MultipartFile file) {
        return execute("上传文件", () -> {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            // 上传结束后及时关闭 MultipartFile 输入流，避免资源泄漏。
            try (InputStream inputStream = file.getInputStream()) {
                amazonS3Client.putObject(bucketName, objectKey, inputStream, objectMetadata);
            }
            return true;
        });
    }

    /**
     * 删除指定对象。
     */
    @Override
    public boolean delete(String bucketName, String objectKey) {
        return execute("删除对象", () -> {
            amazonS3Client.deleteObject(bucketName, objectKey);
            return true;
        });
    }

    /**
     * 生成带有效期的预签名下载地址，无需公开存储桶即可临时访问对象。
     */
    @Override
    public String getDownloadUrl(String bucketName, String remoteFileName, long timeout, TimeUnit unit) {
        return execute("生成下载地址", () -> {
            Date expiration = new Date(System.currentTimeMillis() + unit.toMillis(timeout));
            return amazonS3Client.generatePresignedUrl(bucketName, remoteFileName, expiration).toString();
        });
    }

    /**
     * 将 MinIO 对象以附件形式写入 HTTP 响应。
     *
     * <p>文件名使用 UTF-8 构造 Content-Disposition 响应头，以兼容中文及空格等字符。</p>
     */
    @Override
    public void download2Response(String bucketName, String objectKey, HttpServletResponse response) {
        execute("下载对象", () -> {
            // 对象键可能包含目录，只取最后一段作为浏览器下载时显示的文件名。
            String fileName = objectKey.substring(objectKey.lastIndexOf('/') + 1);
            ContentDisposition contentDisposition = ContentDisposition.attachment()
                    .filename(fileName, StandardCharsets.UTF_8)
                    .build();

            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            // 关闭 S3Object 及其输入流，但不关闭由 Servlet 容器管理的响应输出流。
            try (S3Object s3Object = amazonS3Client.getObject(bucketName, objectKey);
                 InputStream inputStream = s3Object.getObjectContent()) {
                inputStream.transferTo(response.getOutputStream());
            }
            return null;
        });
    }

    /**
     * 统一执行 MinIO 操作，并将底层异常转换为项目可识别的业务异常。
     *
     * @param operation        操作名称，用于错误日志定位
     * @param storageOperation 实际执行的存储操作
     * @param <T>              操作返回值类型
     * @return 存储操作的执行结果
     * @throws BizException MinIO SDK 或 I/O 操作执行失败时抛出
     */
    private <T> T execute(String operation, StorageOperation<T> storageOperation) {
        try {
            return storageOperation.execute();
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("MinIO operation failed: operation={}", operation, e);
            throw new BizException(BizCodeEnum.FILE_STORAGE_ERROR, e);
        }
    }

    /**
     * 支持抛出受检异常的存储操作，用于统一包装 SDK 与 I/O 调用。
     */
    @FunctionalInterface
    private interface StorageOperation<T> {
        T execute() throws Exception;
    }
}
