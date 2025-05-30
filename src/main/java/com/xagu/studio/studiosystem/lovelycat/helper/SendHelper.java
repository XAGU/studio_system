package com.xagu.studio.studiosystem.lovelycat.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xagu.studio.studiosystem.bean.Config;
import com.xagu.studio.studiosystem.dao.ConfigRepository;
import com.xagu.studio.studiosystem.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Component
public class SendHelper {


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ConfigRepository configRepository;

    public static String sROBOT = null;
    private static RestTemplate sRestTemplate = null;
    private static ObjectMapper sObjectMapper = null;
    private static String URL = "http://127.0.0.1:8073/send";
    private static String sMyWxId = null;
    public static String keyword = null;

    @PostConstruct
    public void initVal() {
        Config config = configRepository.getByKey(Constants.Config.ROBOT_WX);
        if (config == null) {
            config = new Config();
            config.setKey(Constants.Config.ROBOT_WX);
            config.setValue("zx592715276");
        }
        sROBOT = config.getValue();
        configRepository.save(config);

        Config keywordConfig = configRepository.getByKey(Constants.Config.KEYWORD);
        if (keywordConfig == null) {
            keywordConfig = new Config();
            keywordConfig.setKey(Constants.Config.KEYWORD);
            keywordConfig.setValue("辅助");
        }
        keyword = keywordConfig.getValue();
        configRepository.save(keywordConfig);
        sRestTemplate = this.restTemplate;
        sObjectMapper = this.objectMapper;
    }

    /**
     * 发送文本消息
     */
    public static boolean sendSing(String msg, String sender, String myWxId) {
        return sendMsg(msg, sender, myWxId);
    }


    public static boolean sendImageToRobot(String path) throws IOException {
        if (StringUtils.isEmpty(sMyWxId)) {
            sMyWxId = getMyWxId();
        }
        return send(path, 103, sROBOT, sMyWxId);
    }

    private static boolean send(String msg, int code, String toWxId, String myWxId) {
        Map<String, Object> data = new HashMap<>();
        data.put("type", code);
        try {
            data.put("msg", URLEncoder.encode(msg, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        data.put("to_wxid", toWxId);
        data.put("robot_wxid", myWxId);
        ResponseEntity<String> responseEntity = sRestTemplate.postForEntity(URL, data, String.class);
        return isSuccess(responseEntity.getBody());
    }

    public static boolean sendMessageToRobot(String msg) {
        if (StringUtils.isEmpty(sMyWxId)) {
            sMyWxId = getMyWxId();
        }
        return sendMsg(keyword + msg, sROBOT, sMyWxId);
    }

    private static boolean sendMsg(String msg, String robot, String myWxId) {
        return send(msg, 100, robot, myWxId);
    }

    private static boolean isSuccess(String body) {
        try {
            JsonNode jsonNode = sObjectMapper.readTree(body);
            int code = jsonNode.get("code").asInt();
            return code == 0;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }


    private static String getMyWxId() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", 203);
        ResponseEntity<String> responseEntity = sRestTemplate.postForEntity(URL, data, String.class);
        try {
            String body = URLDecoder.decode(responseEntity.getBody(), "UTF-8");
            body = body.replaceAll("\"\\[", "\\[");
            body = body.replaceAll("]\"", "]");
            JsonNode jsonNode = sObjectMapper.readTree(body);
            int code = jsonNode.get("code").asInt();
            if (code == 0) {
                return jsonNode.findValue("wxid").asText();
            } else {
                return null;
            }
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}