package com.xagu.studio.studiosystem.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xagu
 * Created on 2020/8/14
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface IVerifyService {
    String accountVerify(MultipartFile file, String account);

    String accountVerifyByWx(MultipartFile file,String account);

    void getQrCode(HttpServletResponse response);
}
