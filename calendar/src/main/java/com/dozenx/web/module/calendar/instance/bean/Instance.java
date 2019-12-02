/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.calendar.instance.bean;
import java.sql.Timestamp;
import java.util.Date;

public class Instance {
    
/**编号**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }
/**实例的开始时间，以协调世界时毫秒数表示。**/
    private Long begin;
    public Long getBegin(){
        return begin;
    }    public void setBegin(Long begin){
        this.begin=begin;
    }
/**实例的结束时间，以协调世界时毫秒数表示。**/
    private Long end;
    public Long getEnd(){
        return end;
    }    public void setEnd(Long end){
        this.end=end;
    }
/**该实例对应事件的 _ID。**/
    private Long eventId;
    public Long getEventId(){
        return eventId;
    }    public void setEventId(Long eventId){
        this.eventId=eventId;
    }
/**与日历时区相应的实例儒略历结束日。**/
    private Date endDay;
    public Date getEndDay(){
        return endDay;
    }    public void setEndDay(Date endDay){
        this.endDay=endDay;
    }
/**事件的名称**/
    private Integer endMinute;
    public Integer getEndMinute(){
        return endMinute;
    }    public void setEndMinute(Integer endMinute){
        this.endMinute=endMinute;
    }
/**与日历时区相应的实例儒略历开始日。**/
    private Date startDay;
    public Date getStartDay(){
        return startDay;
    }    public void setStartDay(Date startDay){
        this.startDay=startDay;
    }
/**从日历时区午夜开始计算的实例开始时间（分钟）。**/
    private Integer startMinute;
    public Integer getStartMinute(){
        return startMinute;
    }    public void setStartMinute(Integer startMinute){
        this.startMinute=startMinute;
    }
}
