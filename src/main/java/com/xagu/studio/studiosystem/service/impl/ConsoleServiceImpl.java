package com.xagu.studio.studiosystem.service.impl;

import com.xagu.studio.studiosystem.dao.WxAccountRepository;
import com.xagu.studio.studiosystem.dao.ZqAccountRepository;
import com.xagu.studio.studiosystem.service.IConsoleService;
import com.xagu.studio.studiosystem.service.IWxAccountService;
import com.xagu.studio.studiosystem.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * @author xagu
 * Created on 2020/9/9
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Service("consoleService")
public class ConsoleServiceImpl implements IConsoleService {

    @Autowired
    WxAccountRepository wxAccountRepository;
    @Autowired
    ZqAccountRepository zqAccountRepository;


    /**
     * 获取两个日期之间的日期，包括开始结束日期
     *
     * @param beginData 开始日期
     * @param endData   结束日期
     * @return 日期集合
     */
    public static List<String> getBetweenDates(Date endData, int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past + 1);
        Date beginData = calendar.getTime();
        List<String> result = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (beginData.equals(endData)) {
            result.add(simpleDateFormat.format(beginData));
        } else {
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(beginData);
            tempStart.add(Calendar.DAY_OF_YEAR, 1);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(endData);
            result.add(simpleDateFormat.format(beginData));
            while (tempStart.before(tempEnd)) {
                result.add(simpleDateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
            result.add(simpleDateFormat.format(endData));
        }
        return result;
    }


    @Override
    public Map<String, Object> getBaseInfo() {
        Map<String, Object> ret = new HashMap<>(4);
        //微信总量
        long wxCount = wxAccountRepository.count();
        ret.put("wxCount", wxCount);
        //可用微信
        Long wxCountNotUsed = wxAccountRepository.countByStatusEquals(Constants.WxAccountStatus.NOT_USED);
        ret.put("wxCountNotUsed", wxCountNotUsed);
        //今日中青
        Long zqCountToday = zqAccountRepository.countToday(new Date());
        ret.put("zqCountToday", zqCountToday);
        //中青总量
        long zqCount = zqAccountRepository.count();
        ret.put("zqCount", zqCount);
        //折线图
        List<Map<String, String>> dayCount = zqAccountRepository.countday(new Date(), 15);
        Map<String, Integer> countMap = new HashMap<>(15);
        for (Map<String, String> map : dayCount) {
            Object object = map.get("count");
            countMap.put(map.get("days"), Integer.parseInt(String.valueOf(object)));
        }
        List<String> betweenDates = getBetweenDates(new Date(), 15);
        List<Integer> count = new ArrayList<>();
        for (String betweenDate : betweenDates) {
            Integer val = countMap.get(betweenDate);
            if (val == null) {
                val = 0;
            }
            count.add(val);
        }
        ret.put("dayCount", count);
        ret.put("days", betweenDates);
        return ret;
    }
}
