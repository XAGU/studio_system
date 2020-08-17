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
@Table(name = " tb_game")
public class Game {

    @Id
    private String id;
    @Column(name = "game_name")
    private String gameName;
    @Column(name = "adid")
    private String adid;
    @Column(name = "sadid")
    private String sadid;
    @Column(name = "package_name")
    private String packageName;
    @Column(name = "login_sign")
    private String loginSign;
    @Column(name = "logined_sign")
    private String loginedSign;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "script_id")
    private String scriptId;
	/**
	 * 0 正常运行 1 需要立即运行，否则封号
	 */
	@Column(name = "type")
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getAdid() {
		return adid;
	}

	public void setAdid(String adid) {
		this.adid = adid;
	}

	public String getSadid() {
		return sadid;
	}

	public void setSadid(String sadid) {
		this.sadid = sadid;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getLoginSign() {
		return loginSign;
	}

	public void setLoginSign(String loginSign) {
		this.loginSign = loginSign;
	}

	public String getLoginedSign() {
		return loginedSign;
	}

	public void setLoginedSign(String loginedSign) {
		this.loginedSign = loginedSign;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getScriptId() {
		return scriptId;
	}

	public void setScriptId(String scriptId) {
		this.scriptId = scriptId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
