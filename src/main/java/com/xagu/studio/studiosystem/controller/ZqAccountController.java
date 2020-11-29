package com.xagu.studio.studiosystem.controller;

import com.xagu.studio.studiosystem.bean.WxAccount;
import com.xagu.studio.studiosystem.bean.ZqAccount;
import com.xagu.studio.studiosystem.response.ResponseResult;
import com.xagu.studio.studiosystem.service.IWxAccountService;
import com.xagu.studio.studiosystem.service.IZqAccountService;
import com.xagu.studio.studiosystem.utils.Constants;
import org.springframework.data.domain.Page;
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
@RequestMapping("zq")
public class ZqAccountController {

    @Resource
    IZqAccountService zqAccountService;


    /**
     * 添加ZQ账号
     *
     * @param zqAccount
     * @return
     */
    @PostMapping("addAccount")
    public ResponseResult addAccount(ZqAccount zqAccount, String imei) {
        return ResponseResult.decide(zqAccountService.addZqAccount(zqAccount, imei),
                "添加中青账号成功",
                "添加中青账号失败");
    }


    @PostMapping("deleteAccount")
    public ResponseResult deleteAccount(String ids) {
        return ResponseResult.decide(zqAccountService.deleteAccount(ids.split(",")),
                "删除中青账号成功",
                "删除中青账号失败");

    }


    @GetMapping("getAccountById")
    public ResponseResult getAccountById(String id) {
        ZqAccount accountById = zqAccountService.getAccountById(id);
        return ResponseResult.decide(accountById != null,
                "获取账号成功",
                "获取账号失败")
                .setData(accountById);
    }

    @GetMapping("getAccountByImei")
    public ResponseResult getAccountByImei(String imei) {
        ZqAccount accountById = zqAccountService.getAccountByImei(imei);
        return ResponseResult.decide(accountById != null,
                "获取账号成功",
                "获取账号失败")
                .setData(accountById);
    }

    @GetMapping("listAccount")
    public ResponseResult listAccount(Integer page, Integer size, String phone, String wxId, String platform) {
        Page<ZqAccount> zqAccounts = zqAccountService.listAccount(page, size, phone, wxId, platform);
        return ResponseResult.SUCCESS("获取账号成功").setData(zqAccounts);
    }


    @PostMapping("setGotMoney")
    public ResponseResult setGotMoney(String id, String money) {
        return ResponseResult.decide(zqAccountService.setGotMoney(id, money),
                "设置领取金额信息成功",
                "设置领取金额信息失败");
    }

}
