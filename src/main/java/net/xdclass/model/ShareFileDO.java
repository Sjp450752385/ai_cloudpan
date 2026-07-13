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
 * 文件分享表
 * </p>
 *
 * @author Timothy
 * @since 2026-07-14
 */
@Getter
@Setter
@ToString
@TableName("share_file")
@Schema(name = "ShareFileDO", description = "文件分享表")
public class ShareFileDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分享id
     */
    @TableField("share_id")
    @Schema(description = "分享id")
    private Long shareId;

    /**
     * 用户文件的ID
     */
    @TableField("account_file_id")
    @Schema(description = "用户文件的ID")
    private Long accountFileId;

    /**
     * 创建者id
     */
    @TableField("account_id")
    @Schema(description = "创建者id")
    private Long accountId;

    /**
     * 分享时间
     */
    @TableField("gmt_create")
    @Schema(description = "分享时间")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @TableField("gmt_modified")
    @Schema(description = "更新时间")
    private Date gmtModified;
}
