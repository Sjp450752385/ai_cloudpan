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
 * 文件分类表
 * </p>
 *
 * @author Timothy
 * @since 2026-07-14
 */
@Getter
@Setter
@ToString
@TableName("file_suffix")
@Schema(name = "FileSuffixDO", description = "文件分类表")
public class FileSuffixDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件扩展名
     */
    @TableField("file_suffix")
    @Schema(description = "文件扩展名")
    private String fileSuffix;

    /**
     * 文件类型ID
     */
    @TableField("file_type_id")
    @Schema(description = "文件类型ID")
    private Integer fileTypeId;
}
