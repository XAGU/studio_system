package com.xagu.studio.studiosystem.dao;

import com.xagu.studio.studiosystem.bean.Admin;
import com.xagu.studio.studiosystem.bean.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface AdminRepository extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {
    /**
     *
     * @param username
     * @param password
     * @return a
     */
    Admin findByUsernameEqualsAndPasswordEquals(String username, String password);
}
