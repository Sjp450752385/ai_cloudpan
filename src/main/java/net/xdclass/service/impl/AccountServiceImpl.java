package net.xdclass.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.config.AccountConfig;
import net.xdclass.controller.req.AccountRegisterReq;
import net.xdclass.enums.AccountRoleEnum;
import net.xdclass.enums.BizCodeEnum;
import net.xdclass.exception.BizException;
import net.xdclass.mapper.AccountMapper;
import net.xdclass.model.AccountDO;
import net.xdclass.service.AccountService;
import net.xdclass.utils.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 1.查询手机号是否重复
     * 2.加密密码
     * 3.插入数据库
     * 4.其他相关初始化操作
     * @param req
     */
    @Override
    public void register(AccountRegisterReq req) {
        List<AccountDO> accountDOList = accountMapper.selectList(new QueryWrapper<AccountDO>().eq("phone",req.getPhone()));
        if(!accountDOList.isEmpty()){
            throw new BizException(BizCodeEnum.ACCOUNT_REPEAT);
        }

        AccountDO accountDO = SpringBeanUtil.copyProperties(req,AccountDO.class);

        //加密
        String digestAsHex = DigestUtils.md5DigestAsHex((AccountConfig.ACCOUNT_SALT + req.getPassword()).getBytes());
        accountDO.setPassword(digestAsHex);
        accountDO.setRole(AccountRoleEnum.COMMON.name());
        accountMapper.insert(accountDO);

        //其他


    }
}
