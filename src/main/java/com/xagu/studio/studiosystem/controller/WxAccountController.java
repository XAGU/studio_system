package com.xagu.studio.studiosystem.controller;

import com.xagu.studio.studiosystem.bean.LovelyCatMsg;
import com.xagu.studio.studiosystem.bean.WxAccount;
import com.xagu.studio.studiosystem.lovelycat.listener.WxFriendListener;
import com.xagu.studio.studiosystem.mirai.helper.SendHelper;
import com.xagu.studio.studiosystem.mirai.listener.FriendListener;
import com.xagu.studio.studiosystem.mirai.star.RobotStar;
import com.xagu.studio.studiosystem.response.ResponseResult;
import com.xagu.studio.studiosystem.service.IWxAccountService;
import io.ktor.client.statement.HttpResponse;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author xagu
 * Created on 2020/7/28
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
@RequestMapping("wx")
public class WxAccountController {

    @Resource
    IWxAccountService syncWxAccountService;

    @Autowired
    WxFriendListener wxFriendListener;


    @RequestMapping("getAccount")
    public ResponseResult getAccount() {
        WxAccount account = syncWxAccountService.getAccount();
        return ResponseResult.decide(account != null, "获取账号成功", "账号不足")
                .setData(account);
    }

    @RequestMapping("callback")
    public void callBack(LovelyCatMsg msg) {
        wxFriendListener.onMessage(msg);
    }

    @RequestMapping("verify")
    public ResponseResult qrCodeVerify(MultipartFile file, String account) {
        String ret = syncWxAccountService.accountVerify(file, account);
        return ResponseResult.decide("success".equals(ret), ret, ret);
}

    @RequestMapping("verifyByWx")
    public ResponseResult qrCodeVerifyByWx(MultipartFile file, String account) {
        String ret = syncWxAccountService.accountVerifyByWx(file, account);
        return ResponseResult.decide("success".equals(ret), ret, ret);
    }

    @RequestMapping("getQrCode")
    public void getQrCode(HttpServletResponse response) {
        syncWxAccountService.getQrCode(response);
    }

}
