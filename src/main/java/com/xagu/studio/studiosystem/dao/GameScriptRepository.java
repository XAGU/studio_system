package com.xagu.studio.studiosystem.dao;

import com.xagu.studio.studiosystem.bean.GameScript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface GameScriptRepository extends JpaRepository<GameScript, String>, JpaSpecificationExecutor<GameScript> {

    Integer deleteGameScriptById(String fileId);

    boolean existsByFileNameAndAndPath(String fileName, String path);

    GameScript findOneById(String id);


}
