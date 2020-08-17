package com.xagu.studio.studiosystem.lovelycat.facade.factory.message;

import com.xagu.studio.studiosystem.bean.LovelyCatMsg;
import com.xagu.studio.studiosystem.bean.WxAccount;
import com.xagu.studio.studiosystem.enums.EnumKeyWord;
import com.xagu.studio.studiosystem.lovelycat.helper.SendHelper;
import com.xagu.studio.studiosystem.service.IWxAccountService;
import com.xagu.studio.studiosystem.utils.MessageUtil;
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
    IWxAccountService wxAccountService;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.ADD_WX;
    }

    @Override
    public void execute(LovelyCatMsg msg) {
        StringBuilder retMsg = new StringBuilder("");
        String separator = MessageUtil.separator(msg.getMsg());
        separator.trim();
        ArrayList<String> split = MessageUtil.split(separator);
        if (split.size() != 2) {
            retMsg.append("添加微信格式错误！");
        } else {
            try {
                List<WxAccount> wxAccounts = wxAccountService.batchAddAccount(split.get(1));
                if (wxAccounts.size() == 0) {
                    retMsg.delete(0, retMsg.length()).append("账号数据为空，或格式错误！");
                } else {
                    retMsg.delete(0, retMsg.length()).append("成功添加").append(wxAccounts.size()).append("条记录！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                retMsg.delete(0, retMsg.length()).append("添加失败，请检查格式或者是否有重复！");
            }
        }
        SendHelper.sendSing(retMsg.toString(), msg.getFrom_wxid(), msg.getRobot_wxid());
    }
}
