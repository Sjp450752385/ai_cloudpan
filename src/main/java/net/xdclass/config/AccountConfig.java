package net.xdclass.config;

public class AccountConfig {

    //密码加盐
    public static final String ACCOUNT_SALT = "xdclass.net168";

    //默认存储空间大小 100M
    public static final Long DEFAULT_STORAGE_SIZE = 1024 * 1024 * 100L;

    //根文件夹名称
    public static final String ROOT_FOLDER_NAME = "全部文件夹";

    //根文件夹的父ID
    public static final Long ROOT_PARENT_ID = 0L;
}
