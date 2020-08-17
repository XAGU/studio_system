package com.xagu.studio.studiosystem.controller;

import com.xagu.studio.studiosystem.bean.LovelyCatMsg;
import com.xagu.studio.studiosystem.lovelycat.listener.WxFriendListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xagu
 * Created on 2020/8/14
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
@RequestMapping("lovelyCat")
public class LovelyCatController {

    @Autowired
    WxFriendListener wxFriendListener;

    @PostMapping("callback")
    public void callBack(LovelyCatMsg msg) {
        wxFriendListener.onMessage(msg);
    }

}
