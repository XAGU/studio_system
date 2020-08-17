package com.xagu.studio.studiosystem.dao;

import com.xagu.studio.studiosystem.bean.WxAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface WxAccountRepository extends JpaRepository<WxAccount, String>, JpaSpecificationExecutor<WxAccount> {
    WxAccount findOneById(String id);

    WxAccount findOneByImei(String imei);

    Integer deleteWxAccountsByIdIn(String[] ids);

    WxAccount findFirstByStatusEqualsOrderByUpdateTimeAsc(String status);

    List<WxAccount> findByStatusEquals(String status);

    List<WxAccount> findByStatusNot(String status);

}
