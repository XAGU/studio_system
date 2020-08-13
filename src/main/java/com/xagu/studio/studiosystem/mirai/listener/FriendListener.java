package com.xagu.studio.studiosystem.mirai.listener;

import com.sun.istack.internal.NotNull;
import com.xagu.studio.studiosystem.enums.EnumKeyWord;
import com.xagu.studio.studiosystem.mirai.facade.factory.MessageFactory;
import com.xagu.studio.studiosystem.mirai.facade.factory.message.MessageFacade;
import com.xagu.studio.studiosystem.utils.MessageUtil;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Service
public class FriendListener extends SimpleListenerHost {


    @Autowired
    private MessageFactory messageFactory;


    /**
     * Java方法级别注解,标注一个方法为事件监听器
     *
     * @param event
     */
    @EventHandler
    public void onMessage(FriendMessageEvent event) {

        /**
         * 消息链
         */
        MessageChain messageChain = event.getMessage();
        PlainText plainText = messageChain.first(PlainText.Key);
        if (plainText != null) {
            // 关键词检索
            EnumKeyWord ruleEnum = EnumKeyWord.friendFind(MessageUtil.getKeybyWord(plainText.contentToString(), 1));
            if (ruleEnum == null) {
                return;
            }
            // 会话处理器
            MessageFacade messageFacade = messageFactory.get(ruleEnum);
            if (messageFacade != null) {
                messageFacade.execute(event.getSender(), plainText);
            }
        }

    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        /**
         * 异常处理方式
         * 先直接打印堆栈吧～
         */
        throw new RuntimeException("在事件处理中发生异常", exception);
        //RobotStar.bot.getFriend(1154685452L).sendMessage("私聊消息处理错误!" + exception.getMessage());
    }
}
