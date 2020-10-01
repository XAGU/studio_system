package com.xagu.studio.studiosystem.service;

import com.xagu.studio.studiosystem.bean.Game;
import com.xagu.studio.studiosystem.response.ResponseResult;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author xagu
 * Created on 2020/8/15
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface ILoginService {


    ResponseResult login(String username, String password);

    ResponseResult reSetPassword(String oldPassword, String password, String rePassword);

}
