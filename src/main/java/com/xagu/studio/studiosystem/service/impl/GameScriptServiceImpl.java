package com.xagu.studio.studiosystem.service.impl;

import com.xagu.studio.studiosystem.bean.GameScript;
import com.xagu.studio.studiosystem.dao.GameScriptRepository;
import com.xagu.studio.studiosystem.response.ResponseResult;
import com.xagu.studio.studiosystem.service.IGameScriptService;
import com.xagu.studio.studiosystem.utils.Constants;
import com.xagu.studio.studiosystem.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Optional;

/**
 * @author xagu
 * Created on 2020/8/3
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Transactional(rollbackFor = RuntimeException.class)
@Service("gameScriptService")
public class GameScriptServiceImpl extends BaseService implements IGameScriptService {
    @Autowired
    GameScriptRepository gameScriptRepository;
    @Value("${studio.SAVEPATH}")
    private String filePath;
    @Autowired
    private HttpServletResponse response;

    @Autowired
    SnowFlake snowFlake;


    @Override
    public ResponseResult uploadScript(MultipartFile file) {
        //判断是否有文件
        if (file.isEmpty()) {
            return ResponseResult.FAILED("上传文件不能为空！");
        }
        //判断文件类型，我们只支持图片上传png，jpg，gif
        String contentType = file.getContentType();
        if (!Constants.CONTENT_TYPE.equals(contentType) && !Constants.CONTENT_TYPE2.equals(contentType)) {
            return ResponseResult.FAILED(contentType + " 文件类型不支持！");
        }
        //获取相关数据，比如文件类型、文件名称
        String originalFilename = file.getOriginalFilename();
        //创建图片的保存目录
        String targetPath = filePath + File.separator + originalFilename;
        if (gameScriptRepository.existsByFileNameAndAndPath(originalFilename, targetPath)) {
            return ResponseResult.FAILED("失败，文件已上传或文件名重复");
        }
        File targetFile = new File(targetPath);
        if (!targetFile.getParentFile().exists()) {
            targetFile.mkdirs();
        }
        try {
            //保存文件
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.FAILED("文件上传失败！");
        }
        //记录文件
        GameScript gameScript = new GameScript();
        gameScript.setId(snowFlake.nextId() + "");
        gameScript.setPath(targetPath);
        gameScript.setFileName(originalFilename);
        gameScript.setUpdateTime(new Date());
        gameScriptRepository.save(gameScript);
        //返回结果 访问路径 名称 alt
        return ResponseResult.SUCCESS("上传脚本成功！").setData(gameScript);
    }

    @Override
    public ResponseResult downloadScript(String exeFileId) {
        Optional<GameScript> exeFile = gameScriptRepository.findById(exeFileId);
        if (!exeFile.isPresent()) {
            return ResponseResult.FAILED("请求脚本文件不存在");
        }
        File file = new File(exeFile.get().getPath());
        if (!file.exists()) {
            return ResponseResult.FAILED("请求脚本文件不存在！");
        }
        ServletOutputStream outputStream = null;
        FileInputStream fileInputStream = null;
        response.setContentType(Constants.CONTENT_TYPE);
        System.out.println(exeFile.get().getFileName());
        try {
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(exeFile.get().getFileName(), "UTF-8"));
            outputStream = response.getOutputStream();
            //读取
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }

    @Override
    public ResponseResult listGameScript(Integer page, Integer size) {
        page = this.checkPage(page);
        size = this.checkSize(size);
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<GameScript> exeFiles = gameScriptRepository.findAll(pageable);
        return ResponseResult.SUCCESS("获取列表成功！").setData(exeFiles);
    }

    @Override
    public ResponseResult deleteGameScript(String gameScriptId) {
        boolean isSuccess = false;
        Optional<GameScript> exeFile = gameScriptRepository.findById(gameScriptId);
        if (exeFile.isPresent()) {
            if (gameScriptRepository.deleteGameScriptById(gameScriptId) > 0) {
                isSuccess = true;
                File file = new File(exeFile.get().getPath());
                if (file.exists()) {
                    //存在则删除
                    file.delete();
                }
            }
        }
        return ResponseResult.decide(isSuccess,
                "删除脚本成功！",
                "删除脚本失败！");
    }

}
