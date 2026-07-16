package net.xdclass.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.component.StoreEngine;
import net.xdclass.config.AccountConfig;
import net.xdclass.config.MinioConfig;
import net.xdclass.controller.req.AccountLoginReq;
import net.xdclass.controller.req.AccountRegisterReq;
import net.xdclass.controller.req.FolderCreateReq;
import net.xdclass.dto.AccountDTO;
import net.xdclass.enums.AccountRoleEnum;
import net.xdclass.enums.BizCodeEnum;
import net.xdclass.exception.BizException;
import net.xdclass.mapper.AccountMapper;
import net.xdclass.mapper.StorageMapper;
import net.xdclass.model.AccountDO;
import net.xdclass.model.StorageDO;
import net.xdclass.service.AccountFileService;
import net.xdclass.service.AccountService;
import net.xdclass.utils.CommonUtil;
import net.xdclass.utils.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private StoreEngine fileStoreEngine;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private AccountFileService accountFileService;

    @Autowired
    private StorageMapper storageMapper;

    /**
     * 1.查询手机号是否重复
     * 2.加密密码
     * 3.插入数据库
     * 4.其他相关初始化操作
     * @param req
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
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

        //创建默认的存储空间
        StorageDO storageDO = new StorageDO();
        storageDO.setAccountId(accountDO.getId());
        storageDO.setUsedSize(0L);
        storageDO.setTotalSize(AccountConfig.DEFAULT_STORAGE_SIZE);
        storageMapper.insert(storageDO);

        //初始化根目录
        FolderCreateReq createRootFolderReq = FolderCreateReq.builder()
                .accountId(accountDO.getId())
                .parentId(AccountConfig.ROOT_PARENT_ID)
                .folderName(AccountConfig.ROOT_FOLDER_NAME)
                .build();

        accountFileService.createFolder(createRootFolderReq);


    }

    @Override
    public String uploadAvatar(MultipartFile file) {
        String filename = CommonUtil.getFilePath(file.getOriginalFilename());

        fileStoreEngine.upload(minioConfig.getAvatarBucketName(), filename, file);
        return minioConfig.getEndpoint() + "/" + minioConfig.getAvatarBucketName() + "/" + filename;
    }

    @Override
    public AccountDTO login(AccountLoginReq req) {
        //处理密码
        String encryptPassword = DigestUtils.md5DigestAsHex(( AccountConfig.ACCOUNT_SALT+ req.getPassword()).getBytes());
        QueryWrapper<AccountDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", req.getPhone()).eq("password", encryptPassword);
        AccountDO accountDO = accountMapper.selectOne(queryWrapper);
        if(accountDO == null){
            throw new BizException(BizCodeEnum.ACCOUNT_PWD_ERROR);
        }
        return SpringBeanUtil.copyProperties(accountDO, AccountDTO.class);
    }
}
