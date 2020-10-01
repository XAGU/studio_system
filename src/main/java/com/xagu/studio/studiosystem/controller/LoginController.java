package com.xagu.studio.studiosystem.controller;

import com.xagu.studio.studiosystem.response.ResponseResult;
import com.xagu.studio.studiosystem.service.ILoginService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author xagu
 * Created on 2020/9/8
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
public class LoginController {

    @Resource
    ILoginService loginService;

    @PostMapping(value = "/login")
    public ResponseResult login(@RequestParam("username") String username,
                                @RequestParam("password") String password) {
        return loginService.login(username, password);
    }

    @PostMapping(value = "/logout")
    public ResponseResult login(HttpSession httpSession) {
        String loginUser = (String) httpSession.getAttribute("loginUser");
        httpSession.removeAttribute("loginUser");
        return ResponseResult.SUCCESS("退出登录成功！");
    }

    @PostMapping(value = "/reSetPassword")
    public ResponseResult reSetPassword(String oldPassword, String password, String rePassword) {
        return loginService.reSetPassword(oldPassword, password, rePassword);
    }
}
