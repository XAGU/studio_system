package com.xagu.studio.studiosystem.lovelycat.facade.factory.message;

import com.xagu.studio.studiosystem.bean.LovelyCatMsg;
import com.xagu.studio.studiosystem.enums.EnumKeyWord;

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
     */
    public void execute(LovelyCatMsg msg);
}
