/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年10月25日
 * 文件说明: 
 */
package com.dozenx.web.core.log.sysOperLog.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *操作日志实体类
 **/
public class SysOperLog {
        /**主键 null**/
    private Integer id;
    /**模块名称 null**/
    private String moduleName;
    /**结果 null**/
    private String operResult;
    /**参数 null**/
    private String params;
    /**操作对象 null**/
    private String compName;
    /**操作详情 null**/
    private String operDetail;
    /**操作人 null**/
    private Long userId;
    /**用户ip null**/
    private String userIp;
    /**操作人 null**/
    private String userName;
    /**创建时间 null**/
    private Timestamp createTime;
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }
    public String getModuleName(){
        return moduleName;
    }
    public void setModuleName(String moduleName){
        this.moduleName=moduleName;
    }
    public String getOperResult(){
        return operResult;
    }
    public void setOperResult(String operResult){
        this.operResult=operResult;
    }
    public String getParams(){
        return params;
    }
    public void setParams(String params){
        this.params=params;
    }
    public String getCompName(){
        return compName;
    }
    public void setCompName(String compName){
        this.compName=compName;
    }
    public String getOperDetail(){
        return operDetail;
    }
    public void setOperDetail(String operDetail){
        this.operDetail=operDetail;
    }
    public Long getUserId(){
        return userId;
    }
    public void setUserId(Long userId){
        this.userId=userId;
    }
    public String getUserIp(){
        return userIp;
    }
    public void setUserIp(String userIp){
        this.userIp=userIp;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }
    public Timestamp getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }

}
