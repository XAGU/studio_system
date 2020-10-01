package com.xagu.studio.studiosystem.dao;

import com.xagu.studio.studiosystem.bean.WxAccount;
import com.xagu.studio.studiosystem.bean.ZqAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface ZqAccountRepository extends JpaRepository<ZqAccount, String>, JpaSpecificationExecutor<ZqAccount> {
    ZqAccount findOneById(String id);

    Integer deleteZqAccountsByIdIn(String[] ids);

    ZqAccount findOneByWxId(String wxId);

    @Query(nativeQuery = true, value = "select count(*) from tb_zq_account where date_format(reg_time,'%Y-%m-%d')= date_format(?,'%Y-%m-%d')")
    Long countToday(Date date);

    @Query(nativeQuery = true, value = "select DATE_FORMAT(reg_time,'%Y-%m-%d') as days,count(*) count from tb_zq_account WHERE DATE_SUB(DATE_FORMAT(?,'%Y-%m-%d'), INTERVAL ? DAY) <= date(reg_time) group by DATE_FORMAT(reg_time,'%Y-%m-%d') ORDER BY days")
    List<Map<String, String>> countday(Date date,int past);

}
