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
import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Component
public class NotUsedMessage implements MessageFacade {

    @Resource
    IWxAccountService syncWxAccountService;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.NOT_USED_ACCOUNT;
    }

    @Override
    public void execute(Contact sender, Message message) {
        StringBuilder retMsg = new StringBuilder("未使用的微信：\n");
        List<WxAccount> notUsedAccount = syncWxAccountService.getNotUsedAccount();
        for (int i = 0; i < notUsedAccount.size(); i++) {
            WxAccount wxAccount = notUsedAccount.get(i);
            retMsg.append(wxAccount.getAccount())
                    .append("----")
                    .append(wxAccount.getPassword())
                    .append("\n");
            if (i % 10 == 0 && i != 0) {
                PlainText plainText = new PlainText(retMsg);
                SendHelper.sendSing(sender, plainText);
                retMsg.delete(0, retMsg.length());
            }
        }
        //10个微信一组，最后剩下的
        if (retMsg.length() > 0) {
            PlainText plainText = new PlainText(retMsg);
            SendHelper.sendSing(sender, plainText);
        }
    }
}
