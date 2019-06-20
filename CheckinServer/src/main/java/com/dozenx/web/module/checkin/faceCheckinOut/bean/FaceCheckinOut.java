/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年05月05日
 * 文件说明:
 */
package com.dozenx.web.module.checkin.faceCheckinOut.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *人脸检入实体类
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
    /**耗时 null**/
    private Integer cost;
    /**平均分数 null**/
    private Float avgScore;
    /**识别图片地址 null**/
    private String regUrl;
    /**原图url null**/
    private String oriUrl;
    /**识别图片地址 null**/
    private String regPath;
    /**原图路径 null**/
    private String oriPath;

    private Long cameraId;

    public Long getCameraId() {
        return cameraId;
    }

    public void setCameraId(Long cameraId) {
        this.cameraId = cameraId;
    }

    /**备注 null**/
    private String remark;
    /**耗时 null**/
    private Integer time;
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
    public Integer getCost(){
        return cost;
    }
    public void setCost(Integer cost){
        this.cost=cost;
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
    public String getRegPath(){
        return regPath;
    }
    public void setRegPath(String regPath){
        this.regPath=regPath;
    }
    public String getOriPath(){
        return oriPath;
    }
    public void setOriPath(String oriPath){
        this.oriPath=oriPath;
    }
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark){
        this.remark=remark;
    }
    public Integer getTime(){
        return time;
    }
    public void setTime(Integer time){
        this.time=time;
    }

}
