package com.dozenx.web.module.interfaceapi.interfacelog.pojo;
import java.util.Date;

public class InterfaceLog {
    private Integer id;
    private Integer testId;
    private String result;
    private Date createTime;

    public InterfaceLog() {
    }

    public InterfaceLog(Integer testId, String result) {
        this.testId = testId;
        this.result = result;
    }

    public InterfaceLog(Integer id, Integer testId, String result, Date createTime) {
        this.id = id;
        this.testId = testId;
        this.result = result;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "InterfaceLog{" +
                "id=" + id +
                ", testId=" + testId +
                ", result='" + result + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
