package com.xagu.studio.studiosystem.controller;

import com.xagu.studio.studiosystem.response.ResponseResult;
import com.xagu.studio.studiosystem.service.IGameScriptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author xagu
 * Created on 2020/8/3
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
@RequestMapping("script")
public class GameScriptController {

    @Resource
    IGameScriptService exeFileService;

    @PostMapping("uploadScript")
    public ResponseResult uploadScript(MultipartFile file) {
        return exeFileService.uploadScript(file);
    }

    @GetMapping("downloadScript")
    public ResponseResult downloadScript(String exeFileId) {
        return exeFileService.downloadScript(exeFileId);
    }


    @GetMapping("listScript")
    public ResponseResult listScript(Integer page, Integer size) {
        return exeFileService.listGameScript(page, size);
    }

    @PostMapping("deleteScript")
    public ResponseResult deleteScript(String exeFileId) {
        return exeFileService.deleteGameScript(exeFileId);
    }
}
