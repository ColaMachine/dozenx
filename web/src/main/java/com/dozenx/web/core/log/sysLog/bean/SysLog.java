/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年10月25日
 * 文件说明: 
 */
package com.dozenx.web.core.log.sysLog.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *系统日志实体类
 **/
public class SysLog {
        /**编号 null**/
    private Integer id;
    /**代码路径 null**/
    private String logPath;
    /**日志类型 null**/
    private Byte logType;
    /**日志编号 null**/
    private Integer logCode;
    /**操作参数 null**/
    private String param;
    /**用户 null**/
    private String userName;
    /**消息 null**/
    private String logMsg;
    /**创建时间 null**/
    private Timestamp createTime;
    /**开始时间 null**/
    private Timestamp startTime;
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }
    public String getLogPath(){
        return logPath;
    }
    public void setLogPath(String logPath){
        this.logPath=logPath;
    }
    public Byte getLogType(){
        return logType;
    }
    public void setLogType(Byte logType){
        this.logType=logType;
    }
    public Integer getLogCode(){
        return logCode;
    }
    public void setLogCode(Integer logCode){
        this.logCode=logCode;
    }
    public String getParam(){
        return param;
    }
    public void setParam(String param){
        this.param=param;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }
    public String getLogMsg(){
        return logMsg;
    }
    public void setLogMsg(String logMsg){
        this.logMsg=logMsg;
    }
    public Timestamp getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }
    public Timestamp getStartTime(){
        return startTime;
    }
    public void setStartTime(Timestamp startTime){
        this.startTime=startTime;
    }

}
