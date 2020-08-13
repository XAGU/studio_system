package com.xagu.studio.studiosystem.lovelycat.facade.factory.message;

import com.xagu.studio.studiosystem.bean.LovelyCatMsg;
import com.xagu.studio.studiosystem.enums.EnumKeyWord;
import com.xagu.studio.studiosystem.lovelycat.helper.SendHelper;
import com.xagu.studio.studiosystem.service.IWxAccountService;
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
    IWxAccountService syncWxAccountService;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.CLEAR_ACCOUNT;
    }

    @Override
    public void execute(LovelyCatMsg msg) {
        StringBuilder retMsg = new StringBuilder("");
        if (syncWxAccountService.clearAccount()) {
            retMsg.append("清空成功！");
        } else {
            retMsg.append("清空失败！");
        }
        SendHelper.sendSing(retMsg.toString(), msg.getFrom_wxid(), msg.getRobot_wxid());
    }
}
