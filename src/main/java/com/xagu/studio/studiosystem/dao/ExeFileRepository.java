package com.xagu.studio.studiosystem.dao;

import com.xagu.studio.studiosystem.bean.ExeFile;
import com.xagu.studio.studiosystem.bean.WxAccount;
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
public interface ExeFileRepository extends JpaRepository<ExeFile, String>, JpaSpecificationExecutor<ExeFile> {

    Integer deleteExeFileById(String fileId);

    boolean existsByFileNameAndAndPath(String fileName, String path);

    @Query(nativeQuery = true, value = "SELECT * FROM exe_file WHERE exe_file.id NOT IN(SELECT DISTINCT exe_file.id FROM exe_file INNER JOIN exe_file_account_list on exe_file.id = exe_file_account_list.exe_files_id INNER JOIN wx_account ON exe_file_account_list.account_list_id  = ?) order by RANDOM() limit 1")
    ExeFile randomScriptExe(String account);

    /**
     * 标记exe已运行
     *
     * @param account
     * @return
     */
    @Modifying
    @Query(nativeQuery = true, value = "insert into exe_file_account_list values (?,?)")
    void setExeRuned(String fileId, String account);
}
