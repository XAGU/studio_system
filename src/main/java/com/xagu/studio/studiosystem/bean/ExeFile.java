package com.xagu.studio.studiosystem.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author xagu
 * Created on 2020/8/3
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"file_name", "path"})})
@Entity
public class ExeFile {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "id", columnDefinition = "varchar(32)")
    private String id;
    /**
     * 微信号
     */
    @Column(name = "file_name", columnDefinition = "varchar(32) unique")
    private String fileName;
    /**
     * 文件路径
     */
    @Column(name = "path", columnDefinition = "varchar(256) unique")
    private String path;
    /**
     * 添加时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    @ManyToMany
    private List<WxAccount> accountList;

    public List<WxAccount> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<WxAccount> accountList) {
        this.accountList = accountList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
