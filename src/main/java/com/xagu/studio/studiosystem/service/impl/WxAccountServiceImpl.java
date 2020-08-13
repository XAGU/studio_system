package com.xagu.studio.studiosystem.service.impl;

import com.xagu.studio.studiosystem.bean.WxAccount;
import com.xagu.studio.studiosystem.dao.WxAccountRepository;
import com.xagu.studio.studiosystem.mirai.helper.SendHelper;
import com.xagu.studio.studiosystem.service.IWxAccountService;
import jdk.nashorn.internal.ir.IfNode;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author xagu
 * Created on 2020/7/28
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Service("wxAccountService")
@Transactional(rollbackFor = RuntimeException.class)
public class WxAccountServiceImpl implements IWxAccountService {

    @Autowired
    WxAccountRepository wxAccountRepository;
    private ByteArrayOutputStream byteArrayOutputStream;


    @Override
    public boolean addWxAccount(List<WxAccount> accounts) {
        wxAccountRepository.saveAll(accounts);
        return true;
    }

    @Override
    public WxAccount getAccount() {
        WxAccount wxAccount = wxAccountRepository.findFirstByUsedFalseOrderByAddDateAsc();
        if (wxAccount != null) {
            wxAccount.setUsed(true);
        }
        return wxAccount;
    }

    @Override
    public List<WxAccount> getNotUsedAccount() {
        return wxAccountRepository.findByUsedFalse();
    }

    @Override
    public List<WxAccount> getUsedAccount() {
        return wxAccountRepository.findByUsedTrue();
    }

    @Override
    public boolean clearAccount() {
        wxAccountRepository.deleteAll();
        return true;
    }

    @Override
    public String accountVerify(MultipartFile file, String account) {
        if (file == null || file.isEmpty() || StringUtils.isEmpty(account)) {
            return "参数错误";
        }
        try {
            com.xagu.studio.studiosystem.mirai.helper.SendHelper.sendMessageToRobot(new PlainText("辅助" + account));
            com.xagu.studio.studiosystem.mirai.helper.SendHelper.sendImageToRobot(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @Override
    public String accountVerifyByWx(MultipartFile file, String account) {
        if (file.isEmpty() || StringUtils.isEmpty(account)) {
            return "参数错误";
        }
        try {
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            InputStream inputStream = file.getInputStream();
            byteArrayOutputStream = SendHelper.cloneInputStream(inputStream);
            com.xagu.studio.studiosystem.lovelycat.helper.SendHelper.sendMessageToRobot("辅助" + account);
            com.xagu.studio.studiosystem.lovelycat.helper.SendHelper.sendImageToRobot("http://localhost:8080/wx/getQrCode");
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
