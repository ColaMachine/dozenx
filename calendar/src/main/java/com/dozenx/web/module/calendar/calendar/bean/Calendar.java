/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.calendar.calendar.bean;
import java.sql.Timestamp;
import java.util.Date;

public class Calendar {
    
/**编号**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }
/**日历的名称**/
    private String name;
    public String getName(){
        return name;
    }    public void setName(String name){
        this.name=name;
    }
/**该日历显示给用户时使用的名称**/
    private String calendarDisplayName;
    public String getCalendarDisplayName(){
        return calendarDisplayName;
    }    public void setCalendarDisplayName(String calendarDisplayName){
        this.calendarDisplayName=calendarDisplayName;
    }
/**表示是否选择显示该日历的布尔值**/
    private Integer visible;
    public Integer getVisible(){
        return visible;
    }    public void setVisible(Integer visible){
        this.visible=visible;
    }
/**表示是否应同步日历并将其事件存储在设备上**/
    private Integer syncEvents;
    public Integer getSyncEvents(){
        return syncEvents;
    }    public void setSyncEvents(Integer syncEvents){
        this.syncEvents=syncEvents;
    }
/**备注**/
    private Long user;
    public Long getUser(){
        return user;
    }    public void setUser(Long user){
        this.user=user;
    }
/**创建时间**/
    private Timestamp createTime;
    public Timestamp getCreateTime(){
        return createTime;
    }    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }
/**更新时间**/
    private Timestamp updateTime;
    public Timestamp getUpdateTime(){
        return updateTime;
    }    public void setUpdateTime(Timestamp updateTime){
        this.updateTime=updateTime;
    }
}
