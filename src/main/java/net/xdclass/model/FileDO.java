package net.xdclass.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户文件表
 * </p>
 *
 * @author Timothy
 * @since 2026-07-14
 */
@Getter
@Setter
@ToString
@TableName("file")
@Schema(name = "FileDO", description = "用户文件表")
public class FileDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     */
    @Schema(description = "文件id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id，是哪个用户初次上传的
     */
    @TableField("account_id")
    @Schema(description = "用户id，是哪个用户初次上传的")
    private Long accountId;

    /**
     * 文件名称，秒传需要用到，冗余存储
     */
    @TableField("file_name")
    @Schema(description = "文件名称，秒传需要用到，冗余存储")
    private String fileName;

    /**
     * 文件的后缀拓展名，冗余存储
     */
    @TableField("file_suffix")
    @Schema(description = "文件的后缀拓展名，冗余存储")
    private String fileSuffix;

    /**
     * 文件大小，字节，冗余存储
     */
    @TableField("file_size")
    @Schema(description = "文件大小，字节，冗余存储")
    private Long fileSize;

    /**
     * 文件的key, 格式 日期/md5.拓展名，比如 2024-11-13/921674fd-cdaf-459a-be7b-109469e7050d.png
     */
    @TableField("object_key")
    @Schema(description = "文件的key, 格式 日期/md5.拓展名，比如 2024-11-13/921674fd-cdaf-459a-be7b-109469e7050d.png")
    private String objectKey;

    /**
     * 唯一标识，文件MD5
     */
    @TableField("identifier")
    @Schema(description = "唯一标识，文件MD5")
    private String identifier;

    /**
     * 逻辑删除（0未删除，1已删除）
     */
    @TableLogic
    @TableField("del")
    @Schema(description = "逻辑删除（0未删除，1已删除）")
    private Boolean del;

    /**
     * 更新时间
     */
    @TableField("gmt_modified")
    @Schema(description = "更新时间")
    private Date gmtModified;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    @Schema(description = "创建时间")
    private Date gmtCreate;
}
