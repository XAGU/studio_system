package com.xagu.studio.studiosystem.mirai.facade.factory.message;

import com.xagu.studio.studiosystem.enums.EnumKeyWord;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Message;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface MessageFacade {
    /**
     * 关键字诶类型
     *
     * @return
     */
    public EnumKeyWord get();

    /**
     * 操作
     *
     * @param sender
     * @param message
     */
    public void execute(Contact sender, Message message);
}
