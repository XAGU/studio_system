package com.xagu.studio.studiosystem.service.impl;

import com.xagu.studio.studiosystem.mirai.helper.SendHelper;
import com.xagu.studio.studiosystem.service.IVerifyService;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author xagu
 * Created on 2020/8/14
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Service("verifyService")
public class VerifyServiceImpl implements IVerifyService {
    private ByteArrayOutputStream byteArrayOutputStream;
    private Long lastVerify = 0L;

    @Override
    public synchronized String accountVerify(MultipartFile file, String account) {
        if (file == null || file.isEmpty() || StringUtils.isEmpty(account)) {
            return "参数错误";
        }
        try {
            com.xagu.studio.studiosystem.mirai.helper.SendHelper.sendMessageToRobot(new PlainText(account));
            com.xagu.studio.studiosystem.mirai.helper.SendHelper.sendImageToRobot(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @Override
    public synchronized String accountVerifyByWx(MultipartFile file, String account) {
        if (file.isEmpty() || StringUtils.isEmpty(account)) {
            return "参数错误";
        }
        //30秒限制时间，发送频率不能太快
        if (System.currentTimeMillis() < lastVerify + 15000) {
            return "发送队列繁忙";
        }
        try {
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            InputStream inputStream = file.getInputStream();
            byteArrayOutputStream = SendHelper.cloneInputStream(inputStream);
            com.xagu.studio.studiosystem.lovelycat.helper.SendHelper.sendMessageToRobot(account);
            com.xagu.studio.studiosystem.lovelycat.helper.SendHelper.sendImageToRobot("http://localhost:8080/verify/getQrCode");
            lastVerify = System.currentTimeMillis   ();
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @Override
    public void getQrCode(HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        response.setContentType("image/png");
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            outputStream = response.getOutputStream();
            //读取
            byte[] buffer = new byte[1024];
            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            int length;
            while ((length = byteArrayInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
