/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.checkin.checkinLate.bean;
import java.sql.Timestamp;
import java.util.Date;

public class CheckinLate {
    
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
/**考勤类型**/
    private Integer checkType;
    public Integer getCheckType(){
        return checkType;
    }    public void setCheckType(Integer checkType){
        this.checkType=checkType;
    }
/**检查时间**/
    private Timestamp checkTime;
    public Timestamp getCheckTime(){
        return checkTime;
    }    public void setCheckTime(Timestamp checkTime){
        this.checkTime=checkTime;
    }
/**考勤机用户id**/
    private Long kqUserId;
    public Long getKqUserId(){
        return kqUserId;
    }    public void setKqUserId(Long kqUserId){
        this.kqUserId=kqUserId;
    }
}
