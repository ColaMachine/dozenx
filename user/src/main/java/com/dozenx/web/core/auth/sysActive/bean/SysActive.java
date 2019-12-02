/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年10月23日
 * 文件说明: 
 */
package com.dozenx.web.core.auth.sysActive.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *激活实体类
 **/
public class SysActive {
        /**主键 null**/
    private Integer id;
    /**类别 null**/
    private String actType;
    /**激活码 null**/
    private String actCode;
    /**用户id null**/
    private Long userId;
    /**账号 null**/
    private String account;
    /**激活状态 null**/
    private Integer actStatus;
    /**激活时间 null**/
    private Date actTime;
    /**创建时间 null**/
    private Date addTime;
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }
    public String getActType(){
        return actType;
    }
    public void setActType(String actType){
        this.actType=actType;
    }
    public String getActCode(){
        return actCode;
    }
    public void setActCode(String actCode){
        this.actCode=actCode;
    }
    public Long getUserId(){
        return userId;
    }
    public void setUserId(Long userId){
        this.userId=userId;
    }
    public String getAccount(){
        return account;
    }
    public void setAccount(String account){
        this.account=account;
    }
    public Integer getActStatus(){
        return actStatus;
    }
    public void setActStatus(Integer actStatus){
        this.actStatus=actStatus;
    }
    public Date getActTime(){
        return actTime;
    }
    public void setActTime(Date actTime){
        this.actTime=actTime;
    }
    public Date getAddTime(){
        return addTime;
    }
    public void setAddTime(Date addTime){
        this.addTime=addTime;
    }

}
