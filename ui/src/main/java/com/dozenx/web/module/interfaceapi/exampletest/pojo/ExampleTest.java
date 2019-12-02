package com.dozenx.web.module.interfaceapi.exampletest.pojo;

import com.dozenx.web.module.interfaceapi.interfaceinfo.pojo.InterfaceInfo;

import java.util.Date;

public class ExampleTest {
    private Integer id;
    private InterfaceInfo interfaceInfo;
    private String params;
    private Date createTime;
    private Date updateTime;

    public ExampleTest() {
    }

    public ExampleTest(InterfaceInfo interfaceInfo, String params) {
        this.interfaceInfo = interfaceInfo;
        this.params = params;
    }

    public ExampleTest(Integer id, InterfaceInfo interfaceInfo, String params, Date createTime, Date updateTime) {
        this.id = id;
        this.interfaceInfo = interfaceInfo;
        this.params = params;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ExampleTest{" +
                "id=" + id +
                ", interfaceInfo=" + interfaceInfo +
                ", params='" + params + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InterfaceInfo getInterfaceInfo() {
        return interfaceInfo;
    }

    public void setInterfaceInfo(InterfaceInfo interfaceInfo) {
        this.interfaceInfo = interfaceInfo;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
