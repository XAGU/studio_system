package com.xagu.studio.studiosystem.controller;

import com.xagu.studio.studiosystem.bean.Config;
import com.xagu.studio.studiosystem.dao.ConfigRepository;
import com.xagu.studio.studiosystem.mirai.star.RobotStar;
import com.xagu.studio.studiosystem.response.ResponseResult;
import com.xagu.studio.studiosystem.service.IConsoleService;
import com.xagu.studio.studiosystem.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author xagu
 * Created on 2020/8/15
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
@RequestMapping("config")
public class ConfigController {

    @Autowired
    ConfigRepository configRepository;

    @PostMapping("updateConfig")
    public ResponseResult updateConfig(String robotWx, String robotQq, String qqNum, String qqPass, String keyword) {
        if (!StringUtils.isEmpty(robotWx)) {
            Config robotWxConfig = configRepository.getByKey(Constants.Config.ROBOT_WX);
            if (robotWxConfig == null) {
                robotWxConfig = new Config();
                robotWxConfig.setKey(Constants.Config.ROBOT_WX);
            }
            robotWxConfig.setValue(robotWx);
            configRepository.save(robotWxConfig);
            //
            com.xagu.studio.studiosystem.lovelycat.helper.SendHelper.sROBOT = robotWx;
        }
        if (!StringUtils.isEmpty(qqNum)) {
            Config qqNumConfig = configRepository.getByKey(Constants.Config.QQ_NUM);
            if (qqNumConfig == null) {
                qqNumConfig = new Config();
                qqNumConfig.setKey(Constants.Config.QQ_NUM);
            }
            qqNumConfig.setValue(qqNum);
            configRepository.save(qqNumConfig);
            RobotStar.sQQ = Long.parseLong(qqNum);
        }
        if (!StringUtils.isEmpty(qqPass)) {
            Config qqPassConfig = configRepository.getByKey(Constants.Config.QQ_PASS);
            if (qqPassConfig == null) {
                qqPassConfig = new Config();
                qqPassConfig.setKey(Constants.Config.QQ_PASS);
            }
            qqPassConfig.setValue(qqPass);
            configRepository.save(qqPassConfig);
            RobotStar.sPassword = qqPass;
        }
        if (!StringUtils.isEmpty(robotQq)) {
            Config robotQqConfig = configRepository.getByKey(Constants.Config.ROBOT_QQ);
            if (robotQqConfig == null) {
                robotQqConfig = new Config();
                robotQqConfig.setKey(Constants.Config.ROBOT_QQ);
            }
            robotQqConfig.setValue(robotQq);
            configRepository.save(robotQqConfig);
            com.xagu.studio.studiosystem.mirai.helper.SendHelper.sROBOT = Long.parseLong(robotQq);
            com.xagu.studio.studiosystem.mirai.helper.SendHelper.sRobot = null;
        }
        if (!StringUtils.isEmpty(keyword)) {
            Config keywordConfig = configRepository.getByKey(Constants.Config.KEYWORD);
            if (keywordConfig == null) {
                keywordConfig = new Config();
                keywordConfig.setKey(Constants.Config.KEYWORD);
            }
            keywordConfig.setValue(keyword);
            configRepository.save(keywordConfig);
            com.xagu.studio.studiosystem.mirai.helper.SendHelper.keyword = keyword;
            com.xagu.studio.studiosystem.lovelycat.helper.SendHelper.keyword = keyword;
        }
        return ResponseResult.SUCCESS("更新配置成功");
    }


    @GetMapping("getConfig")
    public ResponseResult getConfig() {
        List<Config> configs = configRepository.findAll();
        return ResponseResult.SUCCESS("更新配置成功").setData(configs);
    }


}
