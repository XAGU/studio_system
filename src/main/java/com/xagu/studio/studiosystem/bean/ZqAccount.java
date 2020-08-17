package com.xagu.studio.studiosystem.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author xagu
 */
@Entity
@Table ( name =" tb_zq_account" )
public class ZqAccount {

  	@Id
	private String id;
  	@Column(name = "account_id" )
	private String accountId;
  	@Column(name = "phone" )
	private String phone;
  	@Column(name = "password" )
	private String password;
  	@Column(name = "token" )
	private String token;
  	@Column(name = "money" )
	private String money;
  	@Column(name = "wx_id" )
	private String wxId;
  	@Column(name = "reg_time" )
	private Date regTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
}
