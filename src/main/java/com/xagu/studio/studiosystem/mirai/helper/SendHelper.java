package com.xagu.studio.studiosystem.mirai.helper;

import com.xagu.studio.studiosystem.bean.Config;
import com.xagu.studio.studiosystem.dao.ConfigRepository;
import com.xagu.studio.studiosystem.mirai.star.RobotStar;
import com.xagu.studio.studiosystem.utils.Constants;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Component
public class SendHelper {

    @Autowired
    ConfigRepository configRepository;

    public static Long sROBOT = null;
    public static Contact sRobot = null;
    public static String keyword = null;

    @PostConstruct
    public void initVal() {
        Config config = configRepository.getByKey(Constants.Config.ROBOT_QQ);

        if (config == null) {
            config = new Config();
            config.setKey(Constants.Config.ROBOT_QQ);
            config.setValue("2422494482");
        }
        sROBOT = Long.parseLong(config.getValue());
        configRepository.save(config);

        Config keywordConfig = configRepository.getByKey(Constants.Config.KEYWORD);
        if (keywordConfig == null) {
            keywordConfig = new Config();
            keywordConfig.setKey(Constants.Config.KEYWORD);
            keywordConfig.setValue("辅助");
        }
        keyword = keywordConfig.getValue();
        configRepository.save(keywordConfig);
    }

    /**
     * 发送消息
     *
     * @param contact  即可以与{@link Bot} 互动的对象. 包含[用户]{@link User}和[群]{@link Group}.
     * @param messages 可发送的或从服务器接收的消息.
     */
    public static void sendSing(Contact contact, Message messages) {
        contact.sendMessage(messages);
    }


    public static void sendImageToRobot(InputStream image) throws IOException {
        if (sRobot == null) {
            sRobot = RobotStar.bot.getFriend(sROBOT);
        }
        ByteArrayOutputStream baos = cloneInputStream(image);
        sRobot.getBot().getGroup(960956963L).uploadImage(new ByteArrayInputStream(baos.toByteArray()));
        Image upload = sRobot.uploadImage(new ByteArrayInputStream(baos.toByteArray()));
        baos.close();
        sRobot.sendMessage(upload);
    }

    public static void sendMessageToRobot(Message message) {
        if (sRobot == null) {
            sRobot = RobotStar.bot.getFriend(sROBOT);
        }
        sRobot.sendMessage(keyword + message);
    }

    public static ByteArrayOutputStream cloneInputStream(InputStream input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}