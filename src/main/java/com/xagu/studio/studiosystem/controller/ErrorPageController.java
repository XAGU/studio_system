package com.xagu.studio.studiosystem.controller;

import com.xagu.studio.studiosystem.response.ResponseResult;
import com.xagu.studio.studiosystem.response.ResponseState;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xagu
 * Created on 2020/6/26
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
public class ErrorPageController {

    @RequestMapping("403")
    public ResponseResult error403() {
        return ResponseResult.state(ResponseState.ERROR_403);
    }

    @RequestMapping("404")
    public ResponseResult error404() {
        return ResponseResult.state(ResponseState.ERROR_404);
    }

    @RequestMapping("504")
    public ResponseResult error504() {
        return ResponseResult.state(ResponseState.ERROR_504);
    }

    @RequestMapping("505")
    public ResponseResult error505() {
        return ResponseResult.state(ResponseState.ERROR_505);
    }

    @RequestMapping("500")
    public ResponseResult error500() {
        return ResponseResult.state(ResponseState.ERROR_500);
    }


}
