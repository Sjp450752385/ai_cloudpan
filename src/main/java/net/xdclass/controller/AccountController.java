package net.xdclass.controller;

import net.xdclass.controller.req.AccountLoginReq;
import net.xdclass.controller.req.AccountRegisterReq;
import net.xdclass.dto.AccountDTO;
import net.xdclass.enums.BizCodeEnum;
import net.xdclass.service.AccountService;
import net.xdclass.utils.JsonData;
import net.xdclass.utils.JwtUtil;
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
        return JsonData.buildSuccess(url);
    }

    /**
     * 登录模块
     */
    @PostMapping("login")
    public JsonData login(@RequestBody AccountLoginReq req){
        AccountDTO accountDTO = accountService.login(req);

        //生成token jwt 一般前端存在localStorage或者cookie中，后续每次请求都带上token，后端解析token获取用户信息
        String token = JwtUtil.geneLoginJWT(accountDTO);


        return JsonData.buildSuccess(token);
    }
}
