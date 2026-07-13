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

/**
 * <p>
 * 文件类型表
 * </p>
 *
 * @author Timothy
 * @since 2026-07-14
 */
@Getter
@Setter
@ToString
@TableName("file_type")
@Schema(name = "FileTypeDO", description = "文件类型表")
public class FileTypeDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件类型名
     */
    @TableField("file_type_name")
    @Schema(description = "文件类型名")
    private String fileTypeName;
}
