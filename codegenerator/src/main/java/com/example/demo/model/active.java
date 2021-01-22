package com.example.demo.model;

import java.util.Date;

public class active {
    private Long id;

    private String activeCode;

    private Long userId;

    private Byte activedStatus;

    private Date activedTime;

    private Date addTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode == null ? null : activeCode.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getActivedStatus() {
        return activedStatus;
    }

    public void setActivedStatus(Byte activedStatus) {
        this.activedStatus = activedStatus;
    }

    public Date getActivedTime() {
        return activedTime;
    }

    public void setActivedTime(Date activedTime) {
        this.activedTime = activedTime;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}