package com.xagu.studio.studiosystem.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/28
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "account")})
@Entity
public class WxAccount {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "id", columnDefinition = "varchar(32)")
    private String id;
    /**
     * 微信号
     */
    @Column(name = "account", columnDefinition = "varchar(32) unique")
    private String account;
    /**
     * 密码
     */
    @Column(name = "password", columnDefinition = "varchar(32)")
    private String password;
    /**
     * 添加时间
     */
    @Column(name = "add_date")
    private Date addDate;
    /**
     * 是否使用
     */
    @Column(name = "used")
    private boolean used = false;

    @ManyToMany(mappedBy = "accountList")
    private List<ExeFile> exeFiles;

    public List<ExeFile> getExeFiles() {
        return exeFiles;
    }

    public void setExeFiles(List<ExeFile> exeFiles) {
        this.exeFiles = exeFiles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
