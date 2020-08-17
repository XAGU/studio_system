package com.xagu.studio.studiosystem.service;

import com.xagu.studio.studiosystem.bean.Log;
import org.springframework.data.domain.Page;

/**
 * @author xagu
 * Created on 2020/8/15
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface ILogService {
    boolean addLog(Log log);

    Page<Log> listLog(Integer page, Integer size, String wxId);

    boolean clearLog(String wxId);
}
