package net.xdclass.controller.req;

import lombok.Data;

@Data
public class AccountRegisterReq {
    /**
     * 用户名
     */
    private String username;

    private String password;

    private String phone;

    private String avatarUrl;

}
