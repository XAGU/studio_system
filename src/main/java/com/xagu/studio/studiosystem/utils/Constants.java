package com.xagu.studio.studiosystem.utils;


/**
 * @author xagu
 * Created on 2020/6/23
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface Constants {


    String CONTENT_TYPE = "application/vnd.android.package-archive";
    String CONTENT_TYPE2 = "application/vnd.android.package-archive";

    //0 正常运行 1 需要立即运行，否则封号
    interface GameType{
        String AFTER_RUN = "0";
        String RIGHT_NOW_RUN = "1";
    }

    //0未使用，1已被获取，2已被登录，3密码错误，4验证失败
    interface WxAccountStatus {
        String NOT_USED = "0";
        String USED = "1";
        String LOGGED = "2";
        String PASSWORD_ERROR = "3";
        String AUTH_FAILED = "4";
    }

    interface Page {
        Integer DEFAULT_PAGE = 1;
        Integer MIN_SIZE = 5;
    }

}
