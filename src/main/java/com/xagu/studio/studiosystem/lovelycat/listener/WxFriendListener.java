package com.xagu.studio.studiosystem.lovelycat.listener;

import com.xagu.studio.studiosystem.bean.LovelyCatMsg;
import com.xagu.studio.studiosystem.enums.EnumKeyWord;
import com.xagu.studio.studiosystem.lovelycat.facade.factory.MessageFactory;
import com.xagu.studio.studiosystem.lovelycat.facade.factory.message.MessageFacade;
import com.xagu.studio.studiosystem.utils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Service
public class WxFriendListener {


    @Autowired
    private MessageFactory messageFactory;


    /**
     * Java方法级别注解,标注一个方法为事件监听器
     *
     * @param msg
     */
    public void onMessage(LovelyCatMsg msg) {
        String text = msg.getMsg();
        if (!StringUtils.isEmpty(text)) {
            // 关键词检索
            EnumKeyWord ruleEnum = EnumKeyWord.friendFind(MessageUtil.getKeybyWord(text, 1));
            if (ruleEnum == null) {
                return;
            }
            // 会话处理器
            MessageFacade messageFacade = messageFactory.get(ruleEnum);
            if (messageFacade != null) {
                messageFacade.execute(msg);
            }
        }

    }
}
