/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年08月15日
 * 文件说明: 
 */
package com.dozenx.web.module.email.emailRecord.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *邮件发送历史实体类
 **/
public class EmailRecord {
        /**id null**/
    private Integer id;
    /**手机号码 null**/
    private String to;
    /**系统名称 null**/
    private String platform;
    /**业务 null**/
    private String biz;
    /**发送时间 null**/
    private Timestamp addTime;
    /**内容 null**/
    private String content;
    /**标题 null**/
    private String title;
    /**发送状态 null**/
    private Byte status;
    /**失败原因 null**/
    private String reason;
    /**用户 null**/
    private Long user;
    /**ip地址 null**/
    private String ip;
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }
    public String getTo(){
        return to;
    }
    public void setTo(String to){
        this.to=to;
    }
    public String getPlatform(){
        return platform;
    }
    public void setPlatform(String platform){
        this.platform=platform;
    }
    public String getBiz(){
        return biz;
    }
    public void setBiz(String biz){
        this.biz=biz;
    }
    public Timestamp getAddTime(){
        return addTime;
    }
    public void setAddTime(Timestamp addTime){
        this.addTime=addTime;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content=content;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public Byte getStatus(){
        return status;
    }
    public void setStatus(Byte status){
        this.status=status;
    }
    public String getReason(){
        return reason;
    }
    public void setReason(String reason){
        this.reason=reason;
    }
    public Long getUser(){
        return user;
    }
    public void setUser(Long user){
        this.user=user;
    }
    public String getIp(){
        return ip;
    }
    public void setIp(String ip){
        this.ip=ip;
    }

}
