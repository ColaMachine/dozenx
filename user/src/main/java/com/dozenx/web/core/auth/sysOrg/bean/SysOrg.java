/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年04月03日
 * 文件说明:
 */
package com.dozenx.web.core.auth.sysOrg.bean;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;

/**
 *系统公司实体类
 **/
public class SysOrg {
    /**编号 null**/
    private Long id;
    /**公司名称 null**/
    private String name;
    /**编号 null**/
    private String code;
    /**状态 null**/
    private Integer status;
    /**备注 null**/
    private String remark;
    /**创建时间 null**/
    private Timestamp createTime;
    /**更新时间 null**/
    private Timestamp updateTime;
    /**创建人 null**/
    private Long userId;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
