package com.xagu.studio.studiosystem.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public enum EnumKeyWord {


    /**
     * 私聊
     */
    ADD_WX(2, 1, "添加微信"),

    /**
     * 帮助
     */
    HELP(2, 2, "帮助"),

    /**
     * 帮助
     */
    NOT_USED_ACCOUNT(2, 3, "未使用微信"),

    /**
     * 帮助
     */
   GET_ACCOUNT(2, 4, "获取微信"),

    /**
     * 帮助
     */
    CLEAR_ACCOUNT(2, 5, "清空微信"),

    /**
     * 帮助
     */
    USED_ACCOUNT(2, 6, "已使用微信");

    /**
     * 类型
     * 1\群聊 2\私聊
     */
    public Integer type;
    /**
     * 关键码
     */
    public Integer code;
    /**
     * 关键词
     */
    public String keyWord;

    EnumKeyWord(Integer type, Integer code, String keyWord) {
        this.type = type;
        this.code = code;
        this.keyWord = keyWord;
    }

    /**
     * 群聊关键词匹配
     *
     * @param keyWord
     * @return
     */
    public static EnumKeyWord groupFind(String keyWord) {
        List<EnumKeyWord> collect = Arrays.stream(EnumKeyWord.values()).filter(e -> e.type.equals(1)).collect(Collectors.toList());
        for (EnumKeyWord value : collect) {
            if (value.keyWord.equals(keyWord)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 私聊关键词匹配
     *
     * @param keyWord
     * @return
     */
    public static EnumKeyWord friendFind(String keyWord) {
        List<EnumKeyWord> collect = Arrays.stream(EnumKeyWord.values()).filter(e -> e.type.equals(2)).collect(Collectors.toList());
        for (EnumKeyWord value : collect) {
            if (value.keyWord.equals(keyWord)) {
                return value;
            }
        }
        return null;
    }
}
