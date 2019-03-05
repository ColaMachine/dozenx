/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.checkin.faceCheckinOut.bean;
import java.sql.Timestamp;
import java.util.Date;

public class FaceCheckinOut {
    
/**编号**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }
/**用户Id**/
    private Long userId;
    public Long getUserId(){
        return userId;
    }    public void setUserId(Long userId){
        this.userId=userId;
    }
/**用户姓名**/
    private String userName;
    public String getUserName(){
        return userName;
    }    public void setUserName(String userName){
        this.userName=userName;
    }
/**摄像机编号**/
    private String camera;
    public String getCamera(){
        return camera;
    }    public void setCamera(String camera){
        this.camera=camera;
    }
/**考勤类型**/
    private Integer checkType;
    public Integer getCheckType(){
        return checkType;
    }    public void setCheckType(Integer checkType){
        this.checkType=checkType;
    }
/**创建时间**/
    private Timestamp checkTime;
    public Timestamp getCheckTime(){
        return checkTime;
    }    public void setCheckTime(Timestamp checkTime){
        this.checkTime=checkTime;
    }
/**人脸匹配度**/
    private Float score;
    public Float getScore(){
        return score;
    }    public void setScore(Float score){
        this.score=score;
    }
}
