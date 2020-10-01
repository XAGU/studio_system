package com.xagu.studio.studiosystem.controller;

import com.xagu.studio.studiosystem.bean.Log;
import com.xagu.studio.studiosystem.response.ResponseResult;
import com.xagu.studio.studiosystem.service.IConsoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author xagu
 * Created on 2020/8/15
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
@RequestMapping("console")
public class ConsoleController {

    @Resource
    IConsoleService consoleService;

    @GetMapping("getBaseInfo")
    public ResponseResult getBaseInfo() {
        Map<String, Object> info = consoleService.getBaseInfo();
        return ResponseResult.SUCCESS("获取信息成功").setData(info);
    }


}
