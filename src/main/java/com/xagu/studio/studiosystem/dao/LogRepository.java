package com.xagu.studio.studiosystem.dao;

import com.xagu.studio.studiosystem.bean.Log;
import com.xagu.studio.studiosystem.bean.WxAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface LogRepository extends JpaRepository<Log, String>, JpaSpecificationExecutor<Log> {
    Integer deleteByWxId(String wxId);

}
