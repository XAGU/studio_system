package com.xagu.studio.studiosystem.mirai.facade.factory.message;

import com.xagu.studio.studiosystem.enums.EnumKeyWord;
import com.xagu.studio.studiosystem.mirai.helper.SendHelper;
import com.xagu.studio.studiosystem.service.IWxAccountService;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Component
public class ClearAccountMessage implements MessageFacade {

    @Resource
    IWxAccountService wxAccountService;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.CLEAR_ACCOUNT;
    }

    @Override
    public void execute(Contact sender, Message message) {
        StringBuilder retMsg = new StringBuilder("");
        if (wxAccountService.clearAccount()) {
            retMsg.append("清空成功！");
        } else {
            retMsg.append("清空失败！");
        }
        PlainText plainText = new PlainText(retMsg);
        SendHelper.sendSing(sender, plainText);
    }
}
