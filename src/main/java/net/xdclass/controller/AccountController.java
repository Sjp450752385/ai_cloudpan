package net.xdclass.controller;

import net.xdclass.controller.req.AccountRegisterReq;
import net.xdclass.service.AccountService;
import net.xdclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/account/v1")
public class AccountController {
     @Autowired
    private AccountService accountService;

    /**
     * 注册接口
     */

    @RequestMapping("register")
    public JsonData register(@RequestBody AccountRegisterReq req){
        accountService.register(req);
        return JsonData.buildSuccess();
    }

    /**
     * 头像上传接口
     */
    @PostMapping("upload_avatar")
    public JsonData uploadAvatar(@RequestParam("file") MultipartFile file){
        String url = accountService.uploadAvatar(file);
        return JsonData.buildSuccess();
    }
}
