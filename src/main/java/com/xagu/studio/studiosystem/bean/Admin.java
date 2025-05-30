package com.xagu.studio.studiosystem.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author xagu
 */
@Entity
@Table ( name ="tb_admin" )
public class Admin {

	@Id
  	@Column(name = "username" )
	private String username;
  	@Column(name = "password" )
	private String password;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
