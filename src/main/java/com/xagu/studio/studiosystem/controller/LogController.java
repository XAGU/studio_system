package com.xagu.studio.studiosystem.controller;

import com.xagu.studio.studiosystem.bean.Log;
import com.xagu.studio.studiosystem.response.ResponseResult;
import com.xagu.studio.studiosystem.service.ILogService;
import com.xagu.studio.studiosystem.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xagu
 * Created on 2020/8/15
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
@RequestMapping("log")
public class LogController {

    @Resource
    ILogService logService;

    /**
     * 添加Log
     *
     * @param log
     * @return
     */
    @PostMapping("addLog")
    public ResponseResult addLog(Log log) {
        return ResponseResult.decide(logService.addLog(log),
                "记录日志成功",
                "记录日志失败");
    }


    @GetMapping("listLog")
    public ResponseResult listAccount(Integer page, Integer size,String wxId) {
        Page<Log> wxAccounts = logService.listLog(page, size,wxId);
        return ResponseResult.SUCCESS("获取日志成功").setData(wxAccounts);
    }

    @PostMapping("clearLog")
    public ResponseResult clearLog(String wxId) {
        return ResponseResult.decide(logService.clearLog(wxId),
                "清空日志成功",
                "清空日志失败");
    }


}
