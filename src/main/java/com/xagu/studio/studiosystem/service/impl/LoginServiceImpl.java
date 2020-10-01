package com.xagu.studio.studiosystem.service.impl;

import com.xagu.studio.studiosystem.bean.Admin;
import com.xagu.studio.studiosystem.dao.AdminRepository;
import com.xagu.studio.studiosystem.response.ResponseResult;
import com.xagu.studio.studiosystem.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;

/**
 * @author xagu
 * Created on 2020/9/8
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Service("loginService")
public class LoginServiceImpl implements ILoginService {


    @Autowired
    HttpSession httpSession;
    @Autowired
    AdminRepository adminRepository;

    @Override
    public ResponseResult login(String username, String password) {
        if (StringUtils.isEmpty(username)) {
            return ResponseResult.FAILED("用户名不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            return ResponseResult.FAILED("密码不能为空");
        }
        Admin admin = adminRepository.findByUsernameEqualsAndPasswordEquals(username, password);
        if (admin != null) {
            httpSession.setAttribute("loginUser", username);
        }
        return ResponseResult.decide(admin != null,
                "登录成功",
                "账户或密码错误");
    }

    @Override
    public ResponseResult reSetPassword(String oldPassword, String password, String rePassword) {
        if (StringUtils.isEmpty(oldPassword)) {
            return ResponseResult.FAILED("当前密码不能为空！");
        }
        if (StringUtils.isEmpty(password)) {
            return ResponseResult.FAILED("新密码不能为空！");
        }
        if (StringUtils.isEmpty(rePassword)) {
            return ResponseResult.FAILED("确认密码不能为空！");
        }
        if (!password.equals(rePassword)) {
            return ResponseResult.FAILED("两次密码不一致！");
        }
        Admin admin = adminRepository.findByUsernameEqualsAndPasswordEquals("admin", oldPassword);
        if (admin == null) {
            return ResponseResult.FAILED("当前密码错误！");
        }
        if (oldPassword.equals(password)) {
            return ResponseResult.FAILED("新密码不能与当前密码相同！");
        }
        admin.setPassword(password);
        adminRepository.save(admin);
        return ResponseResult.SUCCESS("当前密码已修改为：" + password + "！");
    }
}
