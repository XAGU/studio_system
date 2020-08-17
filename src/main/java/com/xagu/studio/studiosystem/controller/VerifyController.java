package com.xagu.studio.studiosystem.controller;

import com.xagu.studio.studiosystem.response.ResponseResult;
import com.xagu.studio.studiosystem.service.IVerifyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xagu
 * Created on 2020/8/14
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
@RequestMapping("verify")
public class VerifyController {

    @Resource
    IVerifyService verifyService;

    @PostMapping("verifyByQQ")
    public ResponseResult qrCodeVerify(MultipartFile file, String account) {
        String ret = verifyService.accountVerify(file, account);
        return ResponseResult.decide("success".equals(ret), ret, ret);
    }

    @PostMapping("verifyByWx")
    public ResponseResult qrCodeVerifyByWx(MultipartFile file, String account) {
        String ret = verifyService.accountVerifyByWx(file, account);
        return ResponseResult.decide("success".equals(ret), ret, ret);
    }

    @RequestMapping("getQrCode")
    public void getQrCode(HttpServletResponse response) {
        verifyService.getQrCode(response);
    }
}
