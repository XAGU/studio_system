package com.xagu.studio.studiosystem.service;

import com.xagu.studio.studiosystem.bean.WxAccount;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/28
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface IWxAccountService {
    /**
     * 添加微信号
     * @return
     */
    boolean addWxAccount(List<WxAccount> wxAccounts);

    /**
     * 获取微信号
     * @return
     */
    WxAccount getAccount();

    List<WxAccount> getNotUsedAccount();

    List<WxAccount> getUsedAccount();

    boolean clearAccount();

    String accountVerify(MultipartFile file,String account);

    String accountVerifyByWx(MultipartFile file,String account);

    void getQrCode(HttpServletResponse response);
}
