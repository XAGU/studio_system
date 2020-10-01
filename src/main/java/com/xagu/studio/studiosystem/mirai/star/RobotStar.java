package com.xagu.studio.studiosystem.mirai.star;

import com.xagu.studio.studiosystem.bean.Config;
import com.xagu.studio.studiosystem.dao.ConfigRepository;
import com.xagu.studio.studiosystem.mirai.listener.FriendListener;
import com.xagu.studio.studiosystem.utils.Constants;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.SystemDeviceInfoKt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Component
public class RobotStar {
    public static Bot bot = null;

    @Autowired
    FriendListener friendListener;
    @Autowired
    ConfigRepository configRepository;

    public static Long sQQ = null;
    public static String sPassword = null;
    private static FriendListener sFriendListener = null;


    @PostConstruct
    public void initVal() {
        Config configQQ = configRepository.getByKey(Constants.Config.QQ_NUM);
        if (configQQ == null) {
            configQQ = new Config();
            configQQ.setKey(Constants.Config.QQ_NUM);
            configQQ.setValue("11111");
        }
        sQQ = Long.parseLong(configQQ.getValue());
        configRepository.save(configQQ);
        Config configPass = configRepository.getByKey(Constants.Config.QQ_PASS);
        if (configPass == null) {
            configPass = new Config();
            configPass.setKey(Constants.Config.QQ_PASS);
            configPass.setValue("11111");
        }
        sPassword = configPass.getValue();
        configRepository.save(configPass);
        sFriendListener = this.friendListener;
    }


    public static void star() {

        // 机器人
        bot = BotFactoryJvm.newBot(sQQ, sPassword);
        // 登陆
        bot.login();


        /**
         * 事件监听器注册
         */
        Events.registerEvents(bot, sFriendListener);
        /**
         * 挂载该机器人的协程
         */
        bot.join();
    }
}
