package com.xagu.studio.studiosystem.mirai.facade.factory.message;

import com.xagu.studio.studiosystem.enums.EnumKeyWord;
import com.xagu.studio.studiosystem.mirai.helper.SendHelper;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Component
public class HelpMessage implements MessageFacade {
    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.HELP;
    }

    @Override
    public void execute(Contact sender, Message message) {
        PlainText plainText = new PlainText("指令列表：\n" +
                "①添加微信\n" +
                "②获取微信\n" +
                "③已使用微信\n" +
                "④未使用微信\n" +
                "⑤清空微信\n");
        SendHelper.sendSing(sender, plainText);
    }
}
