package net.xdclass.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.xdclass.controller.req.FolderCreateReq;
import net.xdclass.mapper.AccountMapper;
import net.xdclass.mapper.FileMapper;
import net.xdclass.service.AccountFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountFileServiceImpl implements AccountFileService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private FileMapper fileMapper;

    @Override
    public void createFolder(FolderCreateReq createRootFolderReq) {

    }
}
