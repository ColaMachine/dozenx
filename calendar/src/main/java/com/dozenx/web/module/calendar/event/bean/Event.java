/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.calendar.event.bean;
import java.sql.Timestamp;
import java.util.Date;

public class Event {
    
/**编号**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }
/**事件所属日历的 _ID**/
    private Long calendarId;
    public Long getCalendarId(){
        return calendarId;
    }    public void setCalendarId(Long calendarId){
        this.calendarId=calendarId;
    }
/**事件组织者（所有者）的电子邮件**/
    private String organizer;
    public String getOrganizer(){
        return organizer;
    }    public void setOrganizer(String organizer){
        this.organizer=organizer;
    }
/**事件的名称**/
    private String title;
    public String getTitle(){
        return title;
    }    public void setTitle(String title){
        this.title=title;
    }
/**事件的发生地点**/
    private String eventLocation;
    public String getEventLocation(){
        return eventLocation;
    }    public void setEventLocation(String eventLocation){
        this.eventLocation=eventLocation;
    }
/**事件的描述**/
    private String description;
    public String getDescription(){
        return description;
    }    public void setDescription(String description){
        this.description=description;
    }
/**事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示**/
    private Long dtstart;
    public Long getDtstart(){
        return dtstart;
    }    public void setDtstart(Long dtstart){
        this.dtstart=dtstart;
    }
/**事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示**/
    private Long dtend;
    public Long getDtend(){
        return dtend;
    }    public void setDtend(Long dtend){
        this.dtend=dtend;
    }
/**事件的时区**/
    private String eventTimezone;
    public String getEventTimezone(){
        return eventTimezone;
    }    public void setEventTimezone(String eventTimezone){
        this.eventTimezone=eventTimezone;
    }
/**事件结束时间的时区**/
    private String eventEndTimezone;
    public String getEventEndTimezone(){
        return eventEndTimezone;
    }    public void setEventEndTimezone(String eventEndTimezone){
        this.eventEndTimezone=eventEndTimezone;
    }
/**RFC5545 格式的事件持续时间。例如，值为 “PT1H” 表示事件应持续一小时，值为 “P2W” 表示持续 2 周。**/
    private String duration;
    public String getDuration(){
        return duration;
    }    public void setDuration(String duration){
        this.duration=duration;
    }
/**值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。**/
    private Integer allDay;
    public Integer getAllDay(){
        return allDay;
    }    public void setAllDay(Integer allDay){
        this.allDay=allDay;
    }
/**事件的重复发生规则格式。例如，”FREQ=WEEKLY;COUNT=10;WKST=SU”。**/
    private String rrule;
    public String getRrule(){
        return rrule;
    }    public void setRrule(String rrule){
        this.rrule=rrule;
    }
/**事件的重复发生日期。 RDATE 与 RRULE 通常联合用于定义一组聚合重复实例。 如需查看更详细的介绍，请参阅 RFC5545 规范。**/
    private String rdate;
    public String getRdate(){
        return rdate;
    }    public void setRdate(String rdate){
        this.rdate=rdate;
    }
/**将此事件视为忙碌时间还是可调度的空闲时间**/
    private Integer availability;
    public Integer getAvailability(){
        return availability;
    }    public void setAvailability(Integer availability){
        this.availability=availability;
    }
/**来宾是否可修改事件。**/
    private Integer guestsCanModify;
    public Integer getGuestsCanModify(){
        return guestsCanModify;
    }    public void setGuestsCanModify(Integer guestsCanModify){
        this.guestsCanModify=guestsCanModify;
    }
/**来宾是否可邀请其他来宾。**/
    private Integer guestsCanInviteOthers;
    public Integer getGuestsCanInviteOthers(){
        return guestsCanInviteOthers;
    }    public void setGuestsCanInviteOthers(Integer guestsCanInviteOthers){
        this.guestsCanInviteOthers=guestsCanInviteOthers;
    }
/**来宾是否可查看参加者列表。**/
    private Integer guestsCanSeeGuests;
    public Integer getGuestsCanSeeGuests(){
        return guestsCanSeeGuests;
    }    public void setGuestsCanSeeGuests(Integer guestsCanSeeGuests){
        this.guestsCanSeeGuests=guestsCanSeeGuests;
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

    public Long until;

    public Long getUntil() {
        return until;
    }

    public void setUntil(Long until) {
        this.until = until;
    }
}
