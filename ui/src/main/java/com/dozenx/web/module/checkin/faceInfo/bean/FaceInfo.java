/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明:
 */
package com.dozenx.web.module.checkin.faceInfo.bean;

import com.dozenx.web.core.auth.sysUser.bean.SysUser;


public class FaceInfo {

    /**编号**/
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**用户Id**/
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**人脸特征数组**/
    private String face;

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    /**宽高位置信息**/
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**用户名**/
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Double[] faceAry;

    public Double[] getFaceAry() {
        return faceAry;
    }

    public void setFaceAry(Double[] faceAry) {
        this.faceAry = faceAry;
    }


    public SysUser sysUser;

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public Long lastCheckinTime =0l;
}
