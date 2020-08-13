package com.xagu.studio.studiosystem.mirai.facade.factory.message;

import com.xagu.studio.studiosystem.bean.WxAccount;
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
public class GetAccountMessage implements MessageFacade {

    @Resource
    IWxAccountService syncWxAccountService;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GET_ACCOUNT;
    }

    @Override
    public void execute(Contact sender, Message message) {
        StringBuilder retMsg = new StringBuilder("微信：\n");
        WxAccount account = syncWxAccountService.getAccount();
        if (account != null) {
            retMsg.append(account.getAccount())
                    .append("----")
                    .append(account.getPassword());
        } else {
            retMsg.delete(0, retMsg.length())
                    .append("库存微信号不足！");
        }
        PlainText plainText = new PlainText(retMsg);
        SendHelper.sendSing(sender, plainText);
    }
}
