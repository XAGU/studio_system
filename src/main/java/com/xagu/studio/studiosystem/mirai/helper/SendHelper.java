package com.xagu.studio.studiosystem.mirai.helper;

import com.xagu.studio.studiosystem.mirai.star.RobotStar;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
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

    @Value("${studio.ROBOT}")
    private Long ROBOT;

    private static Long sROBOT = null;
    private static Contact sRobot = null;

    @PostConstruct
    public void initVal() {
        sROBOT = this.ROBOT;

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
        sRobot.sendMessage(message);
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