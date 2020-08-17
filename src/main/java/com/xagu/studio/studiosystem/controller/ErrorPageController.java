package com.xagu.studio.studiosystem.controller;

import com.xagu.studio.studiosystem.response.ResponseResult;
import com.xagu.studio.studiosystem.response.ResponseState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author xagu
 * Created on 2020/6/26
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Controller
public class ErrorPageController {

    @ResponseBody
    @RequestMapping("403")
    public ResponseResult error403() {
        return ResponseResult.state(ResponseState.ERROR_403);
    }

    @GetMapping("403")
    public String getError403() {
        return "redirect:/view/error/403.html";
    }

    @ResponseBody
    @RequestMapping("404")
    public ResponseResult error404() {
        return ResponseResult.state(ResponseState.ERROR_404);
    }

    @GetMapping("404")
    public String getError404() {
        return "redirect:/view/error/404.html";
    }

    @ResponseBody
    @RequestMapping("504")
    public ResponseResult error504() {
        return ResponseResult.state(ResponseState.ERROR_504);
    }

    @ResponseBody
    @RequestMapping("505")
    public ResponseResult error505() {
        return ResponseResult.state(ResponseState.ERROR_505);
    }

    @ResponseBody
    @RequestMapping("500")
    public ResponseResult error500() {
        return ResponseResult.state(ResponseState.ERROR_500);
    }

    @GetMapping("500")
    public String getError500() {
        return "redirect:/view/error/500.html";
    }


}
