package com.xagu.studio.studiosystem.bean;

/**
 * @author xagu
 * Created on 2020/8/2
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public class LovelyCatMsg {
    private Integer type;//事件类型（事件列表可参考 - 事件列表demo）
    private Integer msg_type;//消息类型（仅在私聊和群消息事件中，代表消息的表现形式，如文字消息、语音、等等）
    private String from_wxid;//1级来源id（比如发消息的人的id）
    private String final_from_wxid;//1级来源昵称（比如发消息的人昵称）
    private String from_name;//2级来源id（群消息事件下，1级来源为群id，2级来源为发消息的成员id，私聊事件下都一样）
    private String final_from_name;//2级来源昵称
    private String robot_wxid;// 当前登录的账号（机器人）标识id
    private String file_url;//如果是文件消息（图片、语音、视频、动态表情），这里则是可直接访问的网络地址，非文件消息时为空
    private String msg;// 消息内容
    private String time;// 时间
    private String rid;// 附加参数（暂未用到，请忽略）

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(Integer msg_type) {
        this.msg_type = msg_type;
    }

    public String getFrom_wxid() {
        return from_wxid;
    }

    public void setFrom_wxid(String from_wxid) {
        this.from_wxid = from_wxid;
    }

    public String getFinal_from_wxid() {
        return final_from_wxid;
    }

    public void setFinal_from_wxid(String final_from_wxid) {
        this.final_from_wxid = final_from_wxid;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getFinal_from_name() {
        return final_from_name;
    }

    public void setFinal_from_name(String final_from_name) {
        this.final_from_name = final_from_name;
    }

    public String getRobot_wxid() {
        return robot_wxid;
    }

    public void setRobot_wxid(String robot_wxid) {
        this.robot_wxid = robot_wxid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}
