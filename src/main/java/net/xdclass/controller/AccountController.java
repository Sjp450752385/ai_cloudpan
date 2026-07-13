package net.xdclass.controller;

import net.xdclass.controller.req.AccountRegisterReq;
import net.xdclass.service.AccountService;
import net.xdclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
