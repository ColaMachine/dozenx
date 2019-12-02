/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年11月21日
 * 文件说明: 
 */
package com.dozenx.web.core.sms.sysSmsRecord.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *短信验证码发送历史实体类
 **/
public class SysSmsRecord {
        /**id null**/
    private Long id;
    /**手机号码 null**/
    private String phone;
    /**业务类型 null**/
    private String biz;
    /**发送时间 null**/
    private Timestamp createTime;
    /**内容 null**/
    private String content;
    /**发送状态 null**/
    private Byte status;
    /**失败原因 null**/
    private String reason;
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone=phone;
    }
    public String getBiz(){
        return biz;
    }
    public void setBiz(String biz){
        this.biz=biz;
    }
    public Timestamp getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content=content;
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

}
