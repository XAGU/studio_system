package com.xagu.studio.studiosystem.lovelycat.facade.factory.message;

import com.xagu.studio.studiosystem.bean.LovelyCatMsg;
import com.xagu.studio.studiosystem.bean.WxAccount;
import com.xagu.studio.studiosystem.enums.EnumKeyWord;
import com.xagu.studio.studiosystem.lovelycat.helper.SendHelper;
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
    public void execute(LovelyCatMsg msg) {
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
        SendHelper.sendSing(retMsg.toString(), msg.getFrom_wxid(), msg.getRobot_wxid());
    }
}
