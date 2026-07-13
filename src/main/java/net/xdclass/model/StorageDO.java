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
 * 存储信息表
 * </p>
 *
 * @author Timothy
 * @since 2026-07-14
 */
@Getter
@Setter
@ToString
@TableName("storage")
@Schema(name = "StorageDO", description = "存储信息表")
public class StorageDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 所属用户
     */
    @TableField("account_id")
    @Schema(description = "所属用户")
    private Long accountId;

    /**
     * 占用存储大小
     */
    @TableField("used_size")
    @Schema(description = "占用存储大小")
    private Long usedSize;

    /**
     * 总容量大小，字节存储
     */
    @TableField("total_size")
    @Schema(description = "总容量大小，字节存储")
    private Long totalSize;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    @Schema(description = "创建时间")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @TableField("gmt_modified")
    @Schema(description = "更新时间")
    private Date gmtModified;
}
