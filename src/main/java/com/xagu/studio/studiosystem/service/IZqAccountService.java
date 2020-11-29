package com.xagu.studio.studiosystem.service;

import com.xagu.studio.studiosystem.bean.ZqAccount;
import org.springframework.data.domain.Page;

/**
 * @author xagu
 * Created on 2020/8/15
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface IZqAccountService {
    boolean addZqAccount(ZqAccount zqAccount, String imei);

    boolean deleteAccount(String[] split);

    ZqAccount getAccountById(String id);

    Page<ZqAccount> listAccount(Integer page, Integer size, String phone, String wxId,String platform);


    boolean setGotMoney(String id, String money);

    ZqAccount getAccountByImei(String imei);
}
