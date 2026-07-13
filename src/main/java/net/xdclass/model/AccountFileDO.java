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
@TableName("account_file")
@Schema(name = "AccountFileDO", description = "用户文件表")
public class AccountFileDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("account_id")
    @Schema(description = "用户ID")
    private Long accountId;

    /**
     * 状态 0不是文件夹，1是文件夹
     */
    @TableField("is_dir")
    @Schema(description = "状态 0不是文件夹，1是文件夹")
    private Integer isDir;

    /**
     * 上层文件夹ID,顶层文件夹为0
     */
    @TableField("parent_id")
    @Schema(description = "上层文件夹ID,顶层文件夹为0")
    private Long parentId;

    /**
     * 文件ID，真正存储的文件
     */
    @TableField("file_id")
    @Schema(description = "文件ID，真正存储的文件")
    private Long fileId;

    /**
     * 文件名称
     */
    @TableField("file_name")
    @Schema(description = "文件名称")
    private String fileName;

    /**
     * 文件类型：普通文件common 、压缩文件compress 、  excel  、 word  、 pdf  、 txt  、 图片img  、音频audio  、视频video 、ppt 、源码文件code  、 csv
     */
    @TableField("file_type")
    @Schema(description = "文件类型：普通文件common 、压缩文件compress 、  excel  、 word  、 pdf  、 txt  、 图片img  、音频audio  、视频video 、ppt 、源码文件code  、 csv")
    private String fileType;

    /**
     * 文件的后缀拓展名
     */
    @TableField("file_suffix")
    @Schema(description = "文件的后缀拓展名")
    private String fileSuffix;

    /**
     * 文件大小，字节
     */
    @TableField("file_size")
    @Schema(description = "文件大小，字节")
    private Long fileSize;

    /**
     * 逻辑删除（0未删除，1已删除）
     */
    @TableLogic
    @TableField("del")
    @Schema(description = "逻辑删除（0未删除，1已删除）")
    private Boolean del;

    /**
     * 删除日期
     */
    @TableField("del_time")
    @Schema(description = "删除日期")
    private Date delTime;

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
