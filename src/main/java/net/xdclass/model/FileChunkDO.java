package net.xdclass.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 文件分片信息表
 * </p>
 *
 * @author Timothy
 * @since 2026-07-14
 */
@Getter
@Setter
@ToString
@TableName("file_chunk")
@Schema(name = "FileChunkDO", description = "文件分片信息表")
public class FileChunkDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文件唯一标识（md5）
     */
    @TableField("identifier")
    @Schema(description = "文件唯一标识（md5）")
    private String identifier;

    /**
     * 分片上传ID
     */
    @TableField("upload_id")
    @Schema(description = "分片上传ID")
    private String uploadId;

    /**
     * 文件名
     */
    @TableField("file_name")
    @Schema(description = "文件名")
    private String fileName;

    /**
     * 所属桶名
     */
    @TableField("bucket_name")
    @Schema(description = "所属桶名")
    private String bucketName;

    /**
     * 文件的key
     */
    @TableField("object_key")
    @Schema(description = "文件的key")
    private String objectKey;

    /**
     * 总文件大小（byte）
     */
    @TableField("total_size")
    @Schema(description = "总文件大小（byte）")
    private Long totalSize;

    /**
     * 每个分片大小（byte）
     */
    @TableField("chunk_size")
    @Schema(description = "每个分片大小（byte）")
    private Long chunkSize;

    /**
     * 分片数量
     */
    @TableField("chunk_num")
    @Schema(description = "分片数量")
    private Integer chunkNum;

    /**
     * 用户ID
     */
    @TableField("account_id")
    @Schema(description = "用户ID")
    private Long accountId;

    @TableField("gmt_create")
    private Date gmtCreate;

    @TableField("gmt_modified")
    private Date gmtModified;
}
