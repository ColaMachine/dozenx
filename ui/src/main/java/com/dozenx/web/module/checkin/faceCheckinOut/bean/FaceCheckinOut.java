/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年03月29日
 * 文件说明:
 */
package com.dozenx.web.module.checkin.faceCheckinOut.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *考勤实体类
 **/
public class FaceCheckinOut {
    /**编号 null**/
    private Long id;
    /**用户Id null**/
    private Long userId;
    /**用户姓名 null**/
    private String userName;
    /**摄像机编号 null**/
    private String camera;
    /**考勤类型 null**/
    private Integer checkType;
    /**创建时间 null**/
    private Timestamp checkTime;
    /**人脸匹配度 null**/
    private Float score;
    /**平均分数 null**/
    private Float avgScore;
    /**识别图片地址 null**/
    private String regUrl;
    /**原图url null**/
    private String oriUrl;
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
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }
    public String getCamera(){
        return camera;
    }
    public void setCamera(String camera){
        this.camera=camera;
    }
    public Integer getCheckType(){
        return checkType;
    }
    public void setCheckType(Integer checkType){
        this.checkType=checkType;
    }
    public Timestamp getCheckTime(){
        return checkTime;
    }
    public void setCheckTime(Timestamp checkTime){
        this.checkTime=checkTime;
    }
    public Float getScore(){
        return score;
    }
    public void setScore(Float score){
        this.score=score;
    }
    public Float getAvgScore(){
        return avgScore;
    }
    public void setAvgScore(Float avgScore){
        this.avgScore=avgScore;
    }
    public String getRegUrl(){
        return regUrl;
    }
    public void setRegUrl(String regUrl){
        this.regUrl=regUrl;
    }
    public String getOriUrl(){
        return oriUrl;
    }
    public void setOriUrl(String oriUrl){
        this.oriUrl=oriUrl;
    }

}
