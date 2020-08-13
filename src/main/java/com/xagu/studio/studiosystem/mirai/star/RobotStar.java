package com.xagu.studio.studiosystem.mirai.star;

import com.xagu.studio.studiosystem.mirai.listener.FriendListener;
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

    @Value("${studio.QQ}")
    private Long QQ;
    @Value("${studio.PASSWORD}")
    private String PASSWORD;
    @Autowired
    FriendListener friendListener;

    private static Long sQQ = null;
    private static String sPassword = null;
    private static FriendListener sFriendListener = null;


    @PostConstruct
    public void initVal() {
        sQQ = this.QQ;
        sPassword = this.PASSWORD;
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
