package com.xagu.studio.studiosystem.dao;

import com.xagu.studio.studiosystem.bean.WxAccount;
import com.xagu.studio.studiosystem.bean.ZqAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface ZqAccountRepository extends JpaRepository<ZqAccount, String>, JpaSpecificationExecutor<ZqAccount> {
    ZqAccount findOneById(String id);

    Integer deleteZqAccountsByIdIn(String[] ids);

}
