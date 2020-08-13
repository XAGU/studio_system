package com.xagu.studio.studiosystem.mirai.facade.factory.message;

import com.xagu.studio.studiosystem.bean.WxAccount;
import com.xagu.studio.studiosystem.enums.EnumKeyWord;
import com.xagu.studio.studiosystem.mirai.helper.SendHelper;
import com.xagu.studio.studiosystem.utils.MessageUtil;
import com.xagu.studio.studiosystem.service.IWxAccountService;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Component
public class AddWxMessage implements MessageFacade {

    @Resource
    IWxAccountService syncWxAccountService;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.ADD_WX;
    }

    @Override
    public void execute(Contact sender, Message message) {
        StringBuilder retMsg = new StringBuilder("");
        String separator = MessageUtil.separator(message.contentToString());
        separator.trim();
        ArrayList<String> split = MessageUtil.split(separator);
        if (split.size() != 2) {
            retMsg.append("添加微信格式错误！");
        } else {


            String[] accounts = split.get(1).split("\n");
            List<WxAccount> wxAccounts = new ArrayList<>();
            for (String account : accounts) {
                String[] accountAndPass = account.split("----");
                if (accountAndPass.length != 2) {
                    continue;
                }
                WxAccount wxAccount = new WxAccount();
                wxAccount.setAccount(accountAndPass[0]);
                wxAccount.setPassword(accountAndPass[1]);
                wxAccount.setAddDate(new Date());
                wxAccounts.add(wxAccount);
            }
            if (wxAccounts.size() > 0) {
                try {
                    if (syncWxAccountService.addWxAccount(wxAccounts)) {
                        retMsg.delete(0, retMsg.length()).append("成功添加" + wxAccounts.size() + "条记录！");
                    }
                } catch (JpaSystemException e) {
                    retMsg.delete(0, retMsg.length()).append("添加失败，请检查格式或者是否有重复！");
                }
            } else {
                retMsg.delete(0, retMsg.length()).append("账号数据为空，或格式错误！");
            }
        }
        PlainText plainText = new PlainText(retMsg);
        SendHelper.sendSing(sender, plainText);
    }
}
