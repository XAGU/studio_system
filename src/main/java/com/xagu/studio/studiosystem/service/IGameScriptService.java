package com.xagu.studio.studiosystem.service;

import com.xagu.studio.studiosystem.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xagu
 * Created on 2020/8/3
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface IGameScriptService {
    /**
     * 上传脚本exe
     * @param file
     */
    ResponseResult uploadScript(MultipartFile file);

    /**
     * 下载exe脚本
     */
    ResponseResult downloadScript(String exeFileId);

    ResponseResult listGameScript(Integer page, Integer size);

    ResponseResult deleteGameScript(String gameScriptId);

}
