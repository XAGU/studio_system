package com.xagu.studio.studiosystem.bean;

import com.xagu.studio.studiosystem.utils.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author xagu
 */
@Entity
@Table(name = "tb_wx_account")
public class WxAccount {

    @Id
    private String id;
    @Column(name = "account")
    private String account;
    @Column(name = "password")
    private String password;
    @Column(name = "`key`")
    private String key;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "status")
    private String status = Constants.WxAccountStatus.NOT_USED;
    @Column(name = "imei")
    private String imei;


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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Override
    public String toString() {
        return "WxAccount{" +
                "id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", key='" + key + '\'' +
                ", updateTime=" + updateTime +
                ", status='" + status + '\'' +
                ", imei='" + imei + '\'' +
                '}';
    }
}
