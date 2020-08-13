package com.xagu.studio.studiosystem.service.impl;

import com.xagu.studio.studiosystem.bean.WxAccount;
import com.xagu.studio.studiosystem.dao.WxAccountRepository;
import com.xagu.studio.studiosystem.mirai.helper.SendHelper;
import com.xagu.studio.studiosystem.service.IWxAccountService;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/28
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Service("syncWxAccountService")
public class SyncWxAccountServiceImpl implements IWxAccountService {

    @Resource
    IWxAccountService wxAccountService;


    @Override
    public synchronized boolean addWxAccount(List<WxAccount> accounts) {
        return wxAccountService.addWxAccount(accounts);
    }

    @Override
    public synchronized WxAccount getAccount() {
        return wxAccountService.getAccount();
    }

    @Override
    public List<WxAccount> getNotUsedAccount() {
        return wxAccountService.getNotUsedAccount();
    }

    @Override
    public List<WxAccount> getUsedAccount() {
        return wxAccountService.getUsedAccount();
    }

    @Override
    public synchronized boolean clearAccount() {
        return wxAccountService.clearAccount();
    }

    @Override
    public synchronized String accountVerify(MultipartFile file, String account) {
        return wxAccountService.accountVerify(file, account);
    }

    @Override
    public synchronized String accountVerifyByWx(MultipartFile file, String account) {
        return wxAccountService.accountVerifyByWx(file, account);
    }

    @Override
    public void getQrCode(HttpServletResponse response) {
        wxAccountService.getQrCode(response);
    }
}
