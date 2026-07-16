package net.xdclass.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author Timothy
 * @since 2026-07-14
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "AccountDO", description = "用户信息")
public class AccountDTO implements Serializable {


    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;


    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String avatarUrl;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 用户角色 COMMON, ADMIN
     */
    @Schema(description = "用户角色 COMMON, ADMIN")
    private String role;

    /**
     * 逻辑删除（1删除 0未删除）
     */
    @Schema(description = "逻辑删除（1删除 0未删除）")
    private Boolean del;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date gmtModified;
}
