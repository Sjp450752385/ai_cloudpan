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
 * 用户分享表
 * </p>
 *
 * @author Timothy
 * @since 2026-07-14
 */
@Getter
@Setter
@ToString
@TableName("share")
@Schema(name = "ShareDO", description = "用户分享表")
public class ShareDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分享id
     */
    @Schema(description = "分享id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分享名称
     */
    @TableField("share_name")
    @Schema(description = "分享名称")
    private String shareName;

    /**
     * 分享类型（no_code没有提取码 ,need_code有提取码）
     */
    @TableField("share_type")
    @Schema(description = "分享类型（no_code没有提取码 ,need_code有提取码）")
    private String shareType;

    /**
     * 分享类型（0 永久有效；1: 7天有效；2: 30天有效）
     */
    @TableField("share_day_type")
    @Schema(description = "分享类型（0 永久有效；1: 7天有效；2: 30天有效）")
    private Integer shareDayType;

    /**
     * 分享有效天数（永久有效为0）
     */
    @TableField("share_day")
    @Schema(description = "分享有效天数（永久有效为0）")
    private Integer shareDay;

    /**
     * 分享结束时间
     */
    @TableField("share_end_time")
    @Schema(description = "分享结束时间")
    private Date shareEndTime;

    /**
     * 分享链接地址
     */
    @TableField("share_url")
    @Schema(description = "分享链接地址")
    private String shareUrl;

    /**
     * 分享提取码
     */
    @TableField("share_code")
    @Schema(description = "分享提取码")
    private String shareCode;

    /**
     * 分享状态  used正常, expired已失效,  cancled取消
     */
    @TableField("share_status")
    @Schema(description = "分享状态  used正常, expired已失效,  cancled取消")
    private String shareStatus;

    /**
     * 分享创建人
     */
    @TableField("account_id")
    @Schema(description = "分享创建人")
    private Long accountId;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    @Schema(description = "创建时间")
    private Date gmtCreate;

    @TableField("gmt_modified")
    private Date gmtModified;
}
