package com.xagu.studio.studiosystem.mirai.facade.factory;

import com.xagu.studio.studiosystem.enums.EnumKeyWord;
import com.xagu.studio.studiosystem.mirai.facade.factory.message.MessageFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Component
public class MessageFactory {

    @Autowired
    private List<MessageFacade> messageFacades;

    public MessageFacade get(EnumKeyWord keyWord) {

        for (MessageFacade messageFacade : messageFacades) {
            if (messageFacade.get().equals(keyWord)) {
                return messageFacade;
            }
        }
        return null;
    }


}
