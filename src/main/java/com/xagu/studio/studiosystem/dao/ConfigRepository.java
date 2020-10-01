package com.xagu.studio.studiosystem.dao;

import com.xagu.studio.studiosystem.bean.Admin;
import com.xagu.studio.studiosystem.bean.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface ConfigRepository extends JpaRepository<Config, String>, JpaSpecificationExecutor<Config> {
    Config getByKey(String key);
}
