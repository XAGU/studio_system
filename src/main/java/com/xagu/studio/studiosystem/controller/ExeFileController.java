package com.xagu.studio.studiosystem.controller;

import com.xagu.studio.studiosystem.response.ResponseResult;
import com.xagu.studio.studiosystem.service.IExeFileService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ExeFileController {

    @Resource
    IExeFileService exeFileService;

    @RequestMapping("uploadScript")
    public ResponseResult uploadScript(MultipartFile file) {
        return exeFileService.uploadScript(file);
    }

    @RequestMapping("downloadScript")
    public ResponseResult downloadScript(String exeFileId) {
        return exeFileService.downloadScript(exeFileId);
    }

    @RequestMapping("randomScript")
    public ResponseResult randomScript(String account) {
        return exeFileService.randomScript(account);
    }

    @RequestMapping("listScript")
    public ResponseResult listScript(Integer page, Integer size) {
        return exeFileService.listExe(page, size);
    }

    @RequestMapping("deleteScript")
    public ResponseResult deleteScript(String exeFileId) {
        return exeFileService.deleteExe(exeFileId);
    }
}
