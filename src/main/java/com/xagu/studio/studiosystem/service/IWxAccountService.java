package com.xagu.studio.studiosystem.service;

import com.xagu.studio.studiosystem.bean.WxAccount;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/28
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface IWxAccountService {
    /**
     * 批量添加微信号
     *
     * @param wxAccounts
     * @return
     */
    List<WxAccount> batchAddAccount(String wxAccounts);

    /**
     * 添加微信
     *
     * @param account
     * @return
     */
    public boolean addWxAccount(WxAccount account);

    /**
     * 获取微信号
     *
     * @return
     */
    WxAccount getAccount(String imei);

    List<WxAccount> getNotUsedAccount();

    List<WxAccount> getUsedAccount();

    boolean clearAccount();


    /**
     * 删除微信账户
     *
     * @param ids
     * @return
     */
    boolean deleteAccount(String[] ids);

    boolean updateAccount(WxAccount wxAccount);

    WxAccount getAccountById(String id);

    Page<WxAccount> listAccount(Integer page, Integer size, String account, String imei, String status, String id);
}
