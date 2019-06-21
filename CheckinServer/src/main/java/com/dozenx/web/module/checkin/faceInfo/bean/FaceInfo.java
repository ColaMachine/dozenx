/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年04月09日
 * 文件说明:
 */
package com.dozenx.web.module.checkin.faceInfo.bean;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *人脸信息实体类
 **/
public class FaceInfo {
    /**编号 null**/
    private Long id;
    /**用户Id null**/
    private Long userId;
    /**路径 null**/
    private String url;
    /**人脸特征数组 null**/
    private String face;
    /**宽高位置信息 null**/
    private String remark;

    private String absPath;

    public String getAbsPath() {
        return absPath;
    }

    public void setAbsPath(String absPath) {
        this.absPath = absPath;
    }

    /**用户名 null**/
    private String name;
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    public Long getUserId(){
        return userId;
    }
    public void setUserId(Long userId){
        this.userId=userId;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url=url;
    }
    public String getFace(){
        return face;
    }
    public void setFace(String face){
        this.face=face;
    }
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark){
        this.remark=remark;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public  Long outId;

    public Long getOutId() {
        return outId;
    }

    public void setOutId(Long outId) {
        this.outId = outId;
    }

    Double[] faceAry;

    public Double[] getFaceAry() {
        return faceAry;
    }

    public void setFaceAry(Double[] faceAry) {
        this.faceAry = faceAry;
    }


    public SysUser sysUser;//备用字段  存储所有者信息

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public Long lastCheckinTime =0l; //用于不重复开门校验
}
