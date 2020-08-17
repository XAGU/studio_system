package com.xagu.studio.studiosystem.controller;

import com.xagu.studio.studiosystem.bean.WxAccount;
import com.xagu.studio.studiosystem.response.ResponseResult;
import com.xagu.studio.studiosystem.service.IWxAccountService;
import com.xagu.studio.studiosystem.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
    IWxAccountService wxAccountService;


    /**
     * 添加微信账号
     *
     * @param wxAccount
     * @return
     */
    @PostMapping("addAccount")
    public ResponseResult addAccount(WxAccount wxAccount) {
        return ResponseResult.decide(wxAccountService.addWxAccount(wxAccount),
                "添加账号成功",
                "添加账号失败");
    }

    @PostMapping("batchAddAccount")
    public ResponseResult batchAddAccount(String wxAccount) {
        try {
            List<WxAccount> wxAccounts = wxAccountService.batchAddAccount(wxAccount);
            if (wxAccounts.size() == 0) {
                return ResponseResult.FAILED("账号数据为空，或格式错误！");
            } else {
                return ResponseResult.SUCCESS("成功添加" + wxAccounts.size() + "条记录！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAILED("添加失败，请检查格式或者是否有重复！");
        }
    }

    @PostMapping("deleteAccount")
    public ResponseResult deleteAccount(String ids) {
        return ResponseResult.decide(wxAccountService.deleteAccount(ids.split(",")),
                "删除账号成功",
                "删除账号失败");

    }

    @PostMapping("updateAccount")
    public ResponseResult updateAccount(WxAccount wxAccount) {
        return ResponseResult.decide(wxAccountService.updateAccount(wxAccount),
                "更新账号成功",
                "更新账号失败");
    }

    @GetMapping("getAccountById")
    public ResponseResult getAccountById(String id) {
        WxAccount accountById = wxAccountService.getAccountById(id);
        return ResponseResult.decide(accountById != null,
                "获取账号成功",
                "获取账号失败")
                .setData(accountById);
    }

    @GetMapping("listAccount")
    public ResponseResult listAccount(Integer page, Integer size, String account, String imei, String status) {
        Page<WxAccount> wxAccounts = wxAccountService.listAccount(page, size, account, imei, status);
        return ResponseResult.SUCCESS("获取账号成功").setData(wxAccounts);
    }

    @GetMapping("getAccount")
    public ResponseResult getAccount(String imei) {
        if (StringUtils.isEmpty(imei)) {
            return ResponseResult.FAILED("imei设备号不能为空！");
        }
        WxAccount account = wxAccountService.getAccount(imei);
        return ResponseResult.decide(account != null,
                "获取账号成功",
                "账号不足")
                .setData(account);
    }

    @PostMapping("setAccountLogged")
    public ResponseResult setAccountLogged(String id) {
        WxAccount account = new WxAccount();
        account.setId(id);
        account.setStatus(Constants.WxAccountStatus.LOGGED);
        return ResponseResult.decide(wxAccountService.updateAccount(account),
                "同步账户信息成功",
                "同步账户信息失败");
    }

}
