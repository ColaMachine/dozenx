package com.dozenx.web.core.auth.bean;

import java.sql.Timestamp;

/* @Author: dozen.zhang
 * @Description:激活类
 * @Date:17:09 2018/1/2
 */
public class Active {
private String activeid;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	private long userid;
private boolean actived;
public boolean isActived() {
	return actived;
}
public void setActived(boolean actived) {
	this.actived = actived;
}
private Timestamp activedtime;
public String getActiveid() {
	return activeid;
}
public void setActiveid(String activeid) {
	this.activeid = activeid;
}

public Timestamp getActivedtime() {
	return activedtime;
}
public void setActivedtime(Timestamp activedtime) {
	this.activedtime = activedtime;
}
}
