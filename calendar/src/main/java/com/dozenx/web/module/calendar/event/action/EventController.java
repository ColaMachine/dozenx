/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-10-15 15:47:24
 * 文件说明: 
 */

package com.dozenx.web.module.calendar.event.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;

import com.dozenx.common.Path.PathManager;
import com.dozenx.common.config.SysConfig;
import com.dozenx.common.exception.BizException;
import com.dozenx.common.exception.ParamException;
import com.dozenx.common.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import com.dozenx.swagger.annotation.*;
import com.dozenx.web.module.calendar.event.bean.Event;
import com.dozenx.web.module.calendar.event.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.dozenx.web.module.calendar.instance.service.InstanceService;
import com.dozenx.web.module.calendar.instance.bean.Instance;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.util.RequestUtil;
import org.springframework.web.bind.annotation.*;
import com.dozenx.web.core.log.ResultDTO;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;

@APIs(description = "日历")
@Controller
@RequestMapping("/rili/event")
public class EventController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(EventController.class);
    /** 权限service **/
    @Autowired
    private EventService eventService;
    


    /**
     * 说明:ajax请求Event信息
     * @author dozen.zhang
     * @date 2018-10-15 15:47:24
     * @return String
     */
       @API(summary="日历列表接口",
                 description="日历列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",dataType = DataType.LONG,required =false),// false
                    @Param(name="calendarId" , description="事件所属日历的 _ID",dataType = DataType.LONG,required =false),// false
                    @Param(name="organizer" , description="事件组织者（所有者）的电子邮件",dataType = DataType.STRING,required =false),// true
                    @Param(name="title" , description="事件的名称",dataType = DataType.STRING,required =false),// true
                    @Param(name="eventLocation" , description="事件的发生地点",dataType = DataType.STRING,required =false),// false
                    @Param(name="description" , description="事件的描述",dataType = DataType.STRING,required =false),// false
                    @Param(name="dtstart" , description="事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示",dataType = DataType.LONG,required =false),// false
                    @Param(name="dtend" , description="事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示",dataType = DataType.LONG,required =false),// false
                    @Param(name="eventTimezone" , description="事件的时区",dataType = DataType.STRING,required =false),// false
                    @Param(name="eventEndTimezone" , description="事件结束时间的时区",dataType = DataType.STRING,required =false),// false
                    @Param(name="duration" , description="RFC5545 格式的事件持续时间。例如，值为 “PT1H” 表示事件应持续一小时，值为 “P2W” 表示持续 2 周。",dataType = DataType.STRING,required =false),// false
                    @Param(name="allDay" , description="值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="rrule" , description="事件的重复发生规则格式。例如，”FREQ=WEEKLY;COUNT=10;WKST=SU”。",dataType = DataType.STRING,required =false),// false
                    @Param(name="rdate" , description="事件的重复发生日期。 RDATE 与 RRULE 通常联合用于定义一组聚合重复实例。 如需查看更详细的介绍，请参阅 RFC5545 规范。",dataType = DataType.STRING,required =false),// false
                    @Param(name="availability" , description="将此事件视为忙碌时间还是可调度的空闲时间",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="guestsCanModify" , description="来宾是否可修改事件。",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="guestsCanInviteOthers" , description="来宾是否可邀请其他来宾。",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="guestsCanSeeGuests" , description="来宾是否可查看参加者列表。",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="user" , description="备注",dataType = DataType.LONG,required =false),// false
                    @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="updateTime" , description="更新时间",dataType = DataType.DATE_TIME,required =false),// false
         })
    @RequestMapping(value = "/list.json" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO list(HttpServletRequest request) throws Exception{
        Page page = RequestUtil.getPage(request);
        if(page ==null){
             return this.getWrongResultFromCfg("err.param.page");
        }
        
        HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String calendarId = request.getParameter("calendarId");
        if(!StringUtil.isBlank(calendarId)){
            params.put("calendarId",calendarId);
        }
        String organizer = request.getParameter("organizer");
        if(!StringUtil.isBlank(organizer)){
            params.put("organizer",organizer);
        }
        String organizerLike = request.getParameter("organizerLike");
        if(!StringUtil.isBlank(organizerLike)){
            params.put("organizerLike",organizerLike);
        }
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = request.getParameter("titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
        }
        String eventLocation = request.getParameter("eventLocation");
        if(!StringUtil.isBlank(eventLocation)){
            params.put("eventLocation",eventLocation);
        }
        String eventLocationLike = request.getParameter("eventLocationLike");
        if(!StringUtil.isBlank(eventLocationLike)){
            params.put("eventLocationLike",eventLocationLike);
        }
        String description = request.getParameter("description");
        if(!StringUtil.isBlank(description)){
            params.put("description",description);
        }
        String descriptionLike = request.getParameter("descriptionLike");
        if(!StringUtil.isBlank(descriptionLike)){
            params.put("descriptionLike",descriptionLike);
        }
        String dtstart = request.getParameter("dtstart");
        if(!StringUtil.isBlank(dtstart)){
            params.put("dtstart",dtstart);
        }
        String dtend = request.getParameter("dtend");
        if(!StringUtil.isBlank(dtend)){
            params.put("dtend",dtend);
        }
        String eventTimezone = request.getParameter("eventTimezone");
        if(!StringUtil.isBlank(eventTimezone)){
            params.put("eventTimezone",eventTimezone);
        }
        String eventTimezoneLike = request.getParameter("eventTimezoneLike");
        if(!StringUtil.isBlank(eventTimezoneLike)){
            params.put("eventTimezoneLike",eventTimezoneLike);
        }
        String eventEndTimezone = request.getParameter("eventEndTimezone");
        if(!StringUtil.isBlank(eventEndTimezone)){
            params.put("eventEndTimezone",eventEndTimezone);
        }
        String eventEndTimezoneLike = request.getParameter("eventEndTimezoneLike");
        if(!StringUtil.isBlank(eventEndTimezoneLike)){
            params.put("eventEndTimezoneLike",eventEndTimezoneLike);
        }
        String duration = request.getParameter("duration");
        if(!StringUtil.isBlank(duration)){
            params.put("duration",duration);
        }
        String durationLike = request.getParameter("durationLike");
        if(!StringUtil.isBlank(durationLike)){
            params.put("durationLike",durationLike);
        }
        String allDay = request.getParameter("allDay");
        if(!StringUtil.isBlank(allDay)){
            params.put("allDay",allDay);
        }
        String rrule = request.getParameter("rrule");
        if(!StringUtil.isBlank(rrule)){
            params.put("rrule",rrule);
        }
        String rruleLike = request.getParameter("rruleLike");
        if(!StringUtil.isBlank(rruleLike)){
            params.put("rruleLike",rruleLike);
        }
        String rdate = request.getParameter("rdate");
        if(!StringUtil.isBlank(rdate)){
            params.put("rdate",rdate);
        }
        String rdateLike = request.getParameter("rdateLike");
        if(!StringUtil.isBlank(rdateLike)){
            params.put("rdateLike",rdateLike);
        }
        String availability = request.getParameter("availability");
        if(!StringUtil.isBlank(availability)){
            params.put("availability",availability);
        }
        String guestsCanModify = request.getParameter("guestsCanModify");
        if(!StringUtil.isBlank(guestsCanModify)){
            params.put("guestsCanModify",guestsCanModify);
        }
        String guestsCanInviteOthers = request.getParameter("guestsCanInviteOthers");
        if(!StringUtil.isBlank(guestsCanInviteOthers)){
            params.put("guestsCanInviteOthers",guestsCanInviteOthers);
        }
        String guestsCanSeeGuests = request.getParameter("guestsCanSeeGuests");
        if(!StringUtil.isBlank(guestsCanSeeGuests)){
            params.put("guestsCanSeeGuests",guestsCanSeeGuests);
        }
        String user = request.getParameter("user");
        if(!StringUtil.isBlank(user)){
            params.put("user",user);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = request.getParameter("createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = request.getParameter("createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                params.put("updateTime",updateTime);
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTime",new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeBegin = request.getParameter("updateTimeBegin");
        if(!StringUtil.isBlank(updateTimeBegin)){
            if(StringUtil.checkNumeric(updateTimeBegin)){
                params.put("updateTimeBegin",updateTimeBegin);
            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeBegin",new Timestamp( DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeEnd = request.getParameter("updateTimeEnd");
        if(!StringUtil.isBlank(updateTimeEnd)){
            if(StringUtil.checkNumeric(updateTimeEnd)){
                params.put("updateTimeEnd",updateTimeEnd);
            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeEnd",new Timestamp( DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page",page);
        List<Event> events = eventService.listByParams4Page(params);
        return ResultUtil.getResult(events, page);
    }
    
   /**
    * 说明:ajax请求Event信息 无分页版本
    * @return ResultDTO 返回结果
    * @author dozen.zhang
    * @date 2018-10-15 15:47:24
    */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
                HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String calendarId = request.getParameter("calendarId");
        if(!StringUtil.isBlank(calendarId)){
            params.put("calendarId",calendarId);
        }
        String organizer = request.getParameter("organizer");
        if(!StringUtil.isBlank(organizer)){
            params.put("organizer",organizer);
        }
        String organizerLike = request.getParameter("organizerLike");
        if(!StringUtil.isBlank(organizerLike)){
            params.put("organizerLike",organizerLike);
        }
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = request.getParameter("titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
        }
        String eventLocation = request.getParameter("eventLocation");
        if(!StringUtil.isBlank(eventLocation)){
            params.put("eventLocation",eventLocation);
        }
        String eventLocationLike = request.getParameter("eventLocationLike");
        if(!StringUtil.isBlank(eventLocationLike)){
            params.put("eventLocationLike",eventLocationLike);
        }
        String description = request.getParameter("description");
        if(!StringUtil.isBlank(description)){
            params.put("description",description);
        }
        String descriptionLike = request.getParameter("descriptionLike");
        if(!StringUtil.isBlank(descriptionLike)){
            params.put("descriptionLike",descriptionLike);
        }
        String dtstart = request.getParameter("dtstart");
        if(!StringUtil.isBlank(dtstart)){
            params.put("dtstart",dtstart);
        }
        String dtend = request.getParameter("dtend");
        if(!StringUtil.isBlank(dtend)){
            params.put("dtend",dtend);
        }
        String eventTimezone = request.getParameter("eventTimezone");
        if(!StringUtil.isBlank(eventTimezone)){
            params.put("eventTimezone",eventTimezone);
        }
        String eventTimezoneLike = request.getParameter("eventTimezoneLike");
        if(!StringUtil.isBlank(eventTimezoneLike)){
            params.put("eventTimezoneLike",eventTimezoneLike);
        }
        String eventEndTimezone = request.getParameter("eventEndTimezone");
        if(!StringUtil.isBlank(eventEndTimezone)){
            params.put("eventEndTimezone",eventEndTimezone);
        }
        String eventEndTimezoneLike = request.getParameter("eventEndTimezoneLike");
        if(!StringUtil.isBlank(eventEndTimezoneLike)){
            params.put("eventEndTimezoneLike",eventEndTimezoneLike);
        }
        String duration = request.getParameter("duration");
        if(!StringUtil.isBlank(duration)){
            params.put("duration",duration);
        }
        String durationLike = request.getParameter("durationLike");
        if(!StringUtil.isBlank(durationLike)){
            params.put("durationLike",durationLike);
        }
        String allDay = request.getParameter("allDay");
        if(!StringUtil.isBlank(allDay)){
            params.put("allDay",allDay);
        }
        String rrule = request.getParameter("rrule");
        if(!StringUtil.isBlank(rrule)){
            params.put("rrule",rrule);
        }
        String rruleLike = request.getParameter("rruleLike");
        if(!StringUtil.isBlank(rruleLike)){
            params.put("rruleLike",rruleLike);
        }
        String rdate = request.getParameter("rdate");
        if(!StringUtil.isBlank(rdate)){
            params.put("rdate",rdate);
        }
        String rdateLike = request.getParameter("rdateLike");
        if(!StringUtil.isBlank(rdateLike)){
            params.put("rdateLike",rdateLike);
        }
        String availability = request.getParameter("availability");
        if(!StringUtil.isBlank(availability)){
            params.put("availability",availability);
        }
        String guestsCanModify = request.getParameter("guestsCanModify");
        if(!StringUtil.isBlank(guestsCanModify)){
            params.put("guestsCanModify",guestsCanModify);
        }
        String guestsCanInviteOthers = request.getParameter("guestsCanInviteOthers");
        if(!StringUtil.isBlank(guestsCanInviteOthers)){
            params.put("guestsCanInviteOthers",guestsCanInviteOthers);
        }
        String guestsCanSeeGuests = request.getParameter("guestsCanSeeGuests");
        if(!StringUtil.isBlank(guestsCanSeeGuests)){
            params.put("guestsCanSeeGuests",guestsCanSeeGuests);
        }
        String user = request.getParameter("user");
        if(!StringUtil.isBlank(user)){
            params.put("user",user);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = request.getParameter("createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = request.getParameter("createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                params.put("updateTime",updateTime);
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTime",new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeBegin = request.getParameter("updateTimeBegin");
        if(!StringUtil.isBlank(updateTimeBegin)){
            if(StringUtil.checkNumeric(updateTimeBegin)){
                params.put("updateTimeBegin",updateTimeBegin);
            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeBegin",new Timestamp( DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeEnd = request.getParameter("updateTimeEnd");
        if(!StringUtil.isBlank(updateTimeEnd)){
            if(StringUtil.checkNumeric(updateTimeEnd)){
                params.put("updateTimeEnd",updateTimeEnd);
            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeEnd",new Timestamp( DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        List<Event> events = eventService.listByParams(params);
        return ResultUtil.getDataResult(events);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2018-10-15 15:47:24
    */
     @API( summary="根据id查询单个日历信息",
               description = "根据id查询单个日历信息",
               parameters={
                       @Param(name="id" , description="id",in=InType.path,dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(@PathVariable Long id,HttpServletRequest request) {
                     HashMap<String,Object> result =new HashMap</*String,Object*/>();
        if(id > 0){
            Event bean = eventService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

        }



     /**
        * 说明:查看单条信息
        * @param request 发请求
        * @return String
        * @author dozen.zhang
        * @date 2018-10-15 15:47:24
        */
      @API( summary="根据id查询单个日历信息",
               description = "根据id查询单个日历信息",
               parameters={
                       @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view.json",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(HttpServletRequest request) {
         String id = request.getParameter("id");
            Event bean = eventService.selectByPrimaryKey(Long.valueOf(id));
          //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
           // result.put("bean", bean);
            return this.getResult(bean);
        }


    /**
     * 说明:更新Event信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-10-15 15:47:24
     */
      @API( summary="更新id更新单个日历信息",
        description = "更新id更新单个日历信息",
        parameters={
           @Param(name="id" , description="编号",type="LONG",required = false),
           @Param(name="calendarId" , description="事件所属日历的 _ID",type="LONG",required = false),
           @Param(name="organizer" , description="事件组织者（所有者）的电子邮件",type="STRING",required = true),
           @Param(name="title" , description="事件的名称",type="STRING",required = true),
           @Param(name="eventLocation" , description="事件的发生地点",type="STRING",required = false),
           @Param(name="description" , description="事件的描述",type="STRING",required = false),
           @Param(name="dtstart" , description="事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示",type="LONG",required = false),
           @Param(name="dtend" , description="事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示",type="LONG",required = false),
           @Param(name="eventTimezone" , description="事件的时区",type="STRING",required = false),
           @Param(name="eventEndTimezone" , description="事件结束时间的时区",type="STRING",required = false),
           @Param(name="duration" , description="RFC5545 格式的事件持续时间。例如，值为 “PT1H” 表示事件应持续一小时，值为 “P2W” 表示持续 2 周。",type="STRING",required = false),
           @Param(name="allDay" , description="值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。",type="INTEGER",required = false),
           @Param(name="rrule" , description="事件的重复发生规则格式。例如，”FREQ=WEEKLY;COUNT=10;WKST=SU”。",type="STRING",required = false),
           @Param(name="rdate" , description="事件的重复发生日期。 RDATE 与 RRULE 通常联合用于定义一组聚合重复实例。 如需查看更详细的介绍，请参阅 RFC5545 规范。",type="STRING",required = false),
           @Param(name="availability" , description="将此事件视为忙碌时间还是可调度的空闲时间",type="INTEGER",required = false),
           @Param(name="guestsCanModify" , description="来宾是否可修改事件。",type="INTEGER",required = false),
           @Param(name="guestsCanInviteOthers" , description="来宾是否可邀请其他来宾。",type="INTEGER",required = false),
           @Param(name="guestsCanSeeGuests" , description="来宾是否可查看参加者列表。",type="INTEGER",required = false),
           @Param(name="user" , description="备注",type="LONG",required = false),
           @Param(name="createTime" , description="创建时间",type="DATE_TIME",required = false),
           @Param(name="updateTime" , description="更新时间",type="DATE_TIME",required = false),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update.json",method = RequestMethod.PUT)///{id}
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Long id,
        Event event =new  Event();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            event.setId(Long.valueOf(id)) ;
        }
        
        String calendarId = request.getParameter("calendarId");
        if(!StringUtil.isBlank(calendarId)){
            event.setCalendarId(Long.valueOf(calendarId)) ;
        }
        
        String organizer = request.getParameter("organizer");
        if(!StringUtil.isBlank(organizer)){
            event.setOrganizer(String.valueOf(organizer)) ;
        }
        
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            event.setTitle(String.valueOf(title)) ;
        }
        
        String eventLocation = request.getParameter("eventLocation");
        if(!StringUtil.isBlank(eventLocation)){
            event.setEventLocation(String.valueOf(eventLocation)) ;
        }
        
        String description = request.getParameter("description");
        if(!StringUtil.isBlank(description)){
            event.setDescription(String.valueOf(description)) ;
        }
        
        String dtstart = request.getParameter("dtstart");
        if(!StringUtil.isBlank(dtstart)){
            event.setDtstart(Long.valueOf(dtstart)) ;
        }
        
        String dtend = request.getParameter("dtend");
        if(!StringUtil.isBlank(dtend)){
            event.setDtend(Long.valueOf(dtend)) ;
        }
        
        String eventTimezone = request.getParameter("eventTimezone");
        if(!StringUtil.isBlank(eventTimezone)){
            event.setEventTimezone(String.valueOf(eventTimezone)) ;
        }
        
        String eventEndTimezone = request.getParameter("eventEndTimezone");
        if(!StringUtil.isBlank(eventEndTimezone)){
            event.setEventEndTimezone(String.valueOf(eventEndTimezone)) ;
        }
        
        String duration = request.getParameter("duration");
        if(!StringUtil.isBlank(duration)){
            event.setDuration(String.valueOf(duration)) ;
        }
        
        String allDay = request.getParameter("allDay");
        if(!StringUtil.isBlank(allDay)){
            event.setAllDay(Integer.valueOf(allDay)) ;
        }
        
        String rrule = request.getParameter("rrule");
        if(!StringUtil.isBlank(rrule)){
            event.setRrule(String.valueOf(rrule)) ;
        }
        
        String rdate = request.getParameter("rdate");
        if(!StringUtil.isBlank(rdate)){
            event.setRdate(String.valueOf(rdate)) ;
        }
        
        String availability = request.getParameter("availability");
        if(!StringUtil.isBlank(availability)){
            event.setAvailability(Integer.valueOf(availability)) ;
        }
        
        String guestsCanModify = request.getParameter("guestsCanModify");
        if(!StringUtil.isBlank(guestsCanModify)){
            event.setGuestsCanModify(Integer.valueOf(guestsCanModify)) ;
        }
        
        String guestsCanInviteOthers = request.getParameter("guestsCanInviteOthers");
        if(!StringUtil.isBlank(guestsCanInviteOthers)){
            event.setGuestsCanInviteOthers(Integer.valueOf(guestsCanInviteOthers)) ;
        }
        
        String guestsCanSeeGuests = request.getParameter("guestsCanSeeGuests");
        if(!StringUtil.isBlank(guestsCanSeeGuests)){
            event.setGuestsCanSeeGuests(Integer.valueOf(guestsCanSeeGuests)) ;
        }
        
        String user = request.getParameter("user");
        if(!StringUtil.isBlank(user)){
            event.setUser(Long.valueOf(user)) ;
        }
        
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            event.setCreateTime(Timestamp.valueOf(createTime)) ;
        }
        
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            event.setUpdateTime(Timestamp.valueOf(updateTime)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            event.setId(Long.valueOf(id));
        }
        String calendarId = request.getParameter("calendarId");
        if(!StringUtil.isBlank(calendarId)){
            event.setCalendarId(Long.valueOf(calendarId));
        }
        String organizer = request.getParameter("organizer");
        if(!StringUtil.isBlank(organizer)){
            event.setOrganizer(organizer);
        }
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            event.setTitle(title);
        }
        String eventLocation = request.getParameter("eventLocation");
        if(!StringUtil.isBlank(eventLocation)){
            event.setEventLocation(eventLocation);
        }
        String description = request.getParameter("description");
        if(!StringUtil.isBlank(description)){
            event.setDescription(description);
        }
        String dtstart = request.getParameter("dtstart");
        if(!StringUtil.isBlank(dtstart)){
            event.setDtstart(Long.valueOf(dtstart));
        }
        String dtend = request.getParameter("dtend");
        if(!StringUtil.isBlank(dtend)){
            event.setDtend(Long.valueOf(dtend));
        }
        String eventTimezone = request.getParameter("eventTimezone");
        if(!StringUtil.isBlank(eventTimezone)){
            event.setEventTimezone(eventTimezone);
        }
        String eventEndTimezone = request.getParameter("eventEndTimezone");
        if(!StringUtil.isBlank(eventEndTimezone)){
            event.setEventEndTimezone(eventEndTimezone);
        }
        String duration = request.getParameter("duration");
        if(!StringUtil.isBlank(duration)){
            event.setDuration(duration);
        }
        String allDay = request.getParameter("allDay");
        if(!StringUtil.isBlank(allDay)){
            event.setAllDay(Integer.valueOf(allDay));
        }
        String rrule = request.getParameter("rrule");
        if(!StringUtil.isBlank(rrule)){
            event.setRrule(rrule);
        }
        String rdate = request.getParameter("rdate");
        if(!StringUtil.isBlank(rdate)){
            event.setRdate(rdate);
        }
        String availability = request.getParameter("availability");
        if(!StringUtil.isBlank(availability)){
            event.setAvailability(Integer.valueOf(availability));
        }
        String guestsCanModify = request.getParameter("guestsCanModify");
        if(!StringUtil.isBlank(guestsCanModify)){
            event.setGuestsCanModify(Integer.valueOf(guestsCanModify));
        }
        String guestsCanInviteOthers = request.getParameter("guestsCanInviteOthers");
        if(!StringUtil.isBlank(guestsCanInviteOthers)){
            event.setGuestsCanInviteOthers(Integer.valueOf(guestsCanInviteOthers));
        }
        String guestsCanSeeGuests = request.getParameter("guestsCanSeeGuests");
        if(!StringUtil.isBlank(guestsCanSeeGuests)){
            event.setGuestsCanSeeGuests(Integer.valueOf(guestsCanSeeGuests));
        }
        String user = request.getParameter("user");
        if(!StringUtil.isBlank(user)){
            event.setUser(Long.valueOf(user));
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                event.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                event.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                event.setUpdateTime(Timestamp.valueOf(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                event.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("calendarId", calendarId, "事件所属日历的 _ID",  new Rule[]{new Digits(11,0)});
        vu.add("organizer", organizer, "事件组织者（所有者）的电子邮件",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("title", title, "事件的名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("eventLocation", eventLocation, "事件的发生地点",  new Rule[]{new Length(50)});
        vu.add("description", description, "事件的描述",  new Rule[]{new Length(100)});
        vu.add("dtstart", dtstart, "事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示",  new Rule[]{new Digits(15,0)});
        vu.add("dtend", dtend, "事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示",  new Rule[]{new Digits(15,0)});
        vu.add("eventTimezone", eventTimezone, "事件的时区",  new Rule[]{new Length(20)});
        vu.add("eventEndTimezone", eventEndTimezone, "事件结束时间的时区",  new Rule[]{new Length(20)});
        vu.add("duration", duration, "RFC5545 格式的事件持续时间。例如，值为 “PT1H” 表示事件应持续一小时，值为 “P2W” 表示持续 2 周。",  new Rule[]{new Length(20)});
        vu.add("allDay", allDay, "值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。",  new Rule[]{new Digits(1,0)});
        vu.add("rrule", rrule, "事件的重复发生规则格式。例如，”FREQ=WEEKLY;COUNT=10;WKST=SU”。",  new Rule[]{new Length(20)});
        vu.add("rdate", rdate, "事件的重复发生日期。 RDATE 与 RRULE 通常联合用于定义一组聚合重复实例。 如需查看更详细的介绍，请参阅 RFC5545 规范。",  new Rule[]{new Length(11)});
        vu.add("availability", availability, "将此事件视为忙碌时间还是可调度的空闲时间",  new Rule[]{new Digits(1,0)});
        vu.add("guestsCanModify", guestsCanModify, "来宾是否可修改事件。",  new Rule[]{new Digits(1,0)});
        vu.add("guestsCanInviteOthers", guestsCanInviteOthers, "来宾是否可邀请其他来宾。",  new Rule[]{new Digits(1,0)});
        vu.add("guestsCanSeeGuests", guestsCanSeeGuests, "来宾是否可查看参加者列表。",  new Rule[]{new Digits(1,0)});
        vu.add("user", user, "备注",  new Rule[]{new Digits(11,0)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return eventService.save(event);
       
    }


        /**
         * 说明:添加Event信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2018-10-15 15:47:24
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个日历信息",
            description = "添加单个日历信息",
            parameters={
               @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
               @Param(name="calendarId" , description="事件所属日历的 _ID",dataType = DataType.LONG,required = false),
               @Param(name="organizer" , description="事件组织者（所有者）的电子邮件",dataType = DataType.STRING,required = true),
               @Param(name="title" , description="事件的名称",dataType = DataType.STRING,required = true),
               @Param(name="eventLocation" , description="事件的发生地点",dataType = DataType.STRING,required = false),
               @Param(name="description" , description="事件的描述",dataType = DataType.STRING,required = false),
               @Param(name="dtstart" , description="事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示",dataType = DataType.LONG,required = false),
               @Param(name="dtend" , description="事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示",dataType = DataType.LONG,required = false),
               @Param(name="eventTimezone" , description="事件的时区",dataType = DataType.STRING,required = false),
               @Param(name="eventEndTimezone" , description="事件结束时间的时区",dataType = DataType.STRING,required = false),
               @Param(name="duration" , description="RFC5545 格式的事件持续时间。例如，值为 “PT1H” 表示事件应持续一小时，值为 “P2W” 表示持续 2 周。",dataType = DataType.STRING,required = false),
               @Param(name="allDay" , description="值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。",dataType = DataType.INTEGER,required = false),
               @Param(name="rrule" , description="事件的重复发生规则格式。例如，”FREQ=WEEKLY;COUNT=10;WKST=SU”。",dataType = DataType.STRING,required = false),
               @Param(name="rdate" , description="事件的重复发生日期。 RDATE 与 RRULE 通常联合用于定义一组聚合重复实例。 如需查看更详细的介绍，请参阅 RFC5545 规范。",dataType = DataType.STRING,required = false),
               @Param(name="availability" , description="将此事件视为忙碌时间还是可调度的空闲时间",dataType = DataType.INTEGER,required = false),
               @Param(name="guestsCanModify" , description="来宾是否可修改事件。",dataType = DataType.INTEGER,required = false),
               @Param(name="guestsCanInviteOthers" , description="来宾是否可邀请其他来宾。",dataType = DataType.INTEGER,required = false),
               @Param(name="guestsCanSeeGuests" , description="来宾是否可查看参加者列表。",dataType = DataType.INTEGER,required = false),
               @Param(name="user" , description="备注",dataType = DataType.LONG,required = false),
               @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
               @Param(name="updateTime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
            })
        @RequestMapping(value = "add.json",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            Event event =new  Event();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                event.setId(Long.valueOf(id)) ;
            }
            
            String calendarId = request.getParameter("calendarId");
            if(!StringUtil.isBlank(calendarId)){
                event.setCalendarId(Long.valueOf(calendarId)) ;
            }
            
            String organizer = request.getParameter("organizer");
            if(!StringUtil.isBlank(organizer)){
                event.setOrganizer(String.valueOf(organizer)) ;
            }
            
            String title = request.getParameter("title");
            if(!StringUtil.isBlank(title)){
                event.setTitle(String.valueOf(title)) ;
            }
            
            String eventLocation = request.getParameter("eventLocation");
            if(!StringUtil.isBlank(eventLocation)){
                event.setEventLocation(String.valueOf(eventLocation)) ;
            }
            
            String description = request.getParameter("description");
            if(!StringUtil.isBlank(description)){
                event.setDescription(String.valueOf(description)) ;
            }
            
            String dtstart = request.getParameter("dtstart");
            if(!StringUtil.isBlank(dtstart)){
                event.setDtstart(Long.valueOf(dtstart)) ;
            }
            
            String dtend = request.getParameter("dtend");
            if(!StringUtil.isBlank(dtend)){
                event.setDtend(Long.valueOf(dtend)) ;
            }
            
            String eventTimezone = request.getParameter("eventTimezone");
            if(!StringUtil.isBlank(eventTimezone)){
                event.setEventTimezone(String.valueOf(eventTimezone)) ;
            }
            
            String eventEndTimezone = request.getParameter("eventEndTimezone");
            if(!StringUtil.isBlank(eventEndTimezone)){
                event.setEventEndTimezone(String.valueOf(eventEndTimezone)) ;
            }
            
            String duration = request.getParameter("duration");
            if(!StringUtil.isBlank(duration)){
                event.setDuration(String.valueOf(duration)) ;
            }
            
            String allDay = request.getParameter("allDay");
            if(!StringUtil.isBlank(allDay)){
                event.setAllDay(Integer.valueOf(allDay)) ;
            }
            
            String rrule = request.getParameter("rrule");
            if(!StringUtil.isBlank(rrule)){
                event.setRrule(String.valueOf(rrule)) ;
            }
            
            String rdate = request.getParameter("rdate");
            if(!StringUtil.isBlank(rdate)){
                event.setRdate(String.valueOf(rdate)) ;
            }
            
            String availability = request.getParameter("availability");
            if(!StringUtil.isBlank(availability)){
                event.setAvailability(Integer.valueOf(availability)) ;
            }
            
            String guestsCanModify = request.getParameter("guestsCanModify");
            if(!StringUtil.isBlank(guestsCanModify)){
                event.setGuestsCanModify(Integer.valueOf(guestsCanModify)) ;
            }
            
            String guestsCanInviteOthers = request.getParameter("guestsCanInviteOthers");
            if(!StringUtil.isBlank(guestsCanInviteOthers)){
                event.setGuestsCanInviteOthers(Integer.valueOf(guestsCanInviteOthers)) ;
            }
            
            String guestsCanSeeGuests = request.getParameter("guestsCanSeeGuests");
            if(!StringUtil.isBlank(guestsCanSeeGuests)){
                event.setGuestsCanSeeGuests(Integer.valueOf(guestsCanSeeGuests)) ;
            }
            
            String user = request.getParameter("user");
            if(!StringUtil.isBlank(user)){
                event.setUser(Long.valueOf(user)) ;
            }
            
            String createTime = request.getParameter("createTime");
            if(!StringUtil.isBlank(createTime)){
                event.setCreateTime(Timestamp.valueOf(createTime)) ;
            }
            
            String updateTime = request.getParameter("updateTime");
            if(!StringUtil.isBlank(updateTime)){
                event.setUpdateTime(Timestamp.valueOf(updateTime)) ;
            }
            */
            String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            event.setId(Long.valueOf(id));
        }
        String calendarId = request.getParameter("calendarId");
        if(!StringUtil.isBlank(calendarId)){
            event.setCalendarId(Long.valueOf(calendarId));
        }
        String organizer = request.getParameter("organizer");
        if(!StringUtil.isBlank(organizer)){
            event.setOrganizer(organizer);
        }
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            event.setTitle(title);
        }
        String eventLocation = request.getParameter("eventLocation");
        if(!StringUtil.isBlank(eventLocation)){
            event.setEventLocation(eventLocation);
        }
        String description = request.getParameter("description");
        if(!StringUtil.isBlank(description)){
            event.setDescription(description);
        }
        String dtstart = request.getParameter("dtstart");
        if(!StringUtil.isBlank(dtstart)){
            event.setDtstart(Long.valueOf(dtstart));
        }
        String dtend = request.getParameter("dtend");
        if(!StringUtil.isBlank(dtend)){
            event.setDtend(Long.valueOf(dtend));
        }
        String eventTimezone = request.getParameter("eventTimezone");
        if(!StringUtil.isBlank(eventTimezone)){
            event.setEventTimezone(eventTimezone);
        }
        String eventEndTimezone = request.getParameter("eventEndTimezone");
        if(!StringUtil.isBlank(eventEndTimezone)){
            event.setEventEndTimezone(eventEndTimezone);
        }
        String duration = request.getParameter("duration");
        if(!StringUtil.isBlank(duration)){
            event.setDuration(duration);
        }
        String allDay = request.getParameter("allDay");
        if(!StringUtil.isBlank(allDay)){
            event.setAllDay(Integer.valueOf(allDay));
        }
        String rrule = request.getParameter("rrule");
        if(!StringUtil.isBlank(rrule)){
            event.setRrule(rrule);
        }
        String rdate = request.getParameter("rdate");
        if(!StringUtil.isBlank(rdate)){
            event.setRdate(rdate);
        }
        String availability = request.getParameter("availability");
        if(!StringUtil.isBlank(availability)){
            event.setAvailability(Integer.valueOf(availability));
        }
        String guestsCanModify = request.getParameter("guestsCanModify");
        if(!StringUtil.isBlank(guestsCanModify)){
            event.setGuestsCanModify(Integer.valueOf(guestsCanModify));
        }
        String guestsCanInviteOthers = request.getParameter("guestsCanInviteOthers");
        if(!StringUtil.isBlank(guestsCanInviteOthers)){
            event.setGuestsCanInviteOthers(Integer.valueOf(guestsCanInviteOthers));
        }
        String guestsCanSeeGuests = request.getParameter("guestsCanSeeGuests");
        if(!StringUtil.isBlank(guestsCanSeeGuests)){
            event.setGuestsCanSeeGuests(Integer.valueOf(guestsCanSeeGuests));
        }
        String user = request.getParameter("user");
        if(!StringUtil.isBlank(user)){
            event.setUser(Long.valueOf(user));
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                event.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                event.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                event.setUpdateTime(Timestamp.valueOf(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                event.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("calendarId", calendarId, "事件所属日历的 _ID",  new Rule[]{new Digits(11,0)});
        vu.add("organizer", organizer, "事件组织者（所有者）的电子邮件",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("title", title, "事件的名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("eventLocation", eventLocation, "事件的发生地点",  new Rule[]{new Length(50)});
        vu.add("description", description, "事件的描述",  new Rule[]{new Length(100)});
        vu.add("dtstart", dtstart, "事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示",  new Rule[]{new Digits(15,0)});
        vu.add("dtend", dtend, "事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示",  new Rule[]{new Digits(15,0)});
        vu.add("eventTimezone", eventTimezone, "事件的时区",  new Rule[]{new Length(20)});
        vu.add("eventEndTimezone", eventEndTimezone, "事件结束时间的时区",  new Rule[]{new Length(20)});
        vu.add("duration", duration, "RFC5545 格式的事件持续时间。例如，值为 “PT1H” 表示事件应持续一小时，值为 “P2W” 表示持续 2 周。",  new Rule[]{new Length(20)});
        vu.add("allDay", allDay, "值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。",  new Rule[]{new Digits(1,0)});
        vu.add("rrule", rrule, "事件的重复发生规则格式。例如，”FREQ=WEEKLY;COUNT=10;WKST=SU”。",  new Rule[]{new Length(20)});
        vu.add("rdate", rdate, "事件的重复发生日期。 RDATE 与 RRULE 通常联合用于定义一组聚合重复实例。 如需查看更详细的介绍，请参阅 RFC5545 规范。",  new Rule[]{new Length(11)});
        vu.add("availability", availability, "将此事件视为忙碌时间还是可调度的空闲时间",  new Rule[]{new Digits(1,0)});
        vu.add("guestsCanModify", guestsCanModify, "来宾是否可修改事件。",  new Rule[]{new Digits(1,0)});
        vu.add("guestsCanInviteOthers", guestsCanInviteOthers, "来宾是否可邀请其他来宾。",  new Rule[]{new Digits(1,0)});
        vu.add("guestsCanSeeGuests", guestsCanSeeGuests, "来宾是否可查看参加者列表。",  new Rule[]{new Digits(1,0)});
        vu.add("user", user, "备注",  new Rule[]{new Digits(11,0)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

            return eventService.save(event);

        }


          /**
                 * 说明:添加Event信息
                 * @param request
                 * @throws Exception
                 * @return ResultDTO
                 * @author dozen.zhang
                 * @date 2018-10-15 15:47:24
                 */
                // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                @API( summary="添加单个日历信息",
                    description = "添加单个日历信息",
                    parameters={
                       @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
                       @Param(name="calendarId" , description="事件所属日历的 _ID",dataType = DataType.LONG,required = false),
                       @Param(name="organizer" , description="事件组织者（所有者）的电子邮件",dataType = DataType.STRING,required = true),
                       @Param(name="title" , description="事件的名称",dataType = DataType.STRING,required = true),
                       @Param(name="eventLocation" , description="事件的发生地点",dataType = DataType.STRING,required = false),
                       @Param(name="description" , description="事件的描述",dataType = DataType.STRING,required = false),
                       @Param(name="dtstart" , description="事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示",dataType = DataType.LONG,required = false),
                       @Param(name="dtend" , description="事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示",dataType = DataType.LONG,required = false),
                       @Param(name="eventTimezone" , description="事件的时区",dataType = DataType.STRING,required = false),
                       @Param(name="eventEndTimezone" , description="事件结束时间的时区",dataType = DataType.STRING,required = false),
                       @Param(name="duration" , description="RFC5545 格式的事件持续时间。例如，值为 “PT1H” 表示事件应持续一小时，值为 “P2W” 表示持续 2 周。",dataType = DataType.STRING,required = false),
                       @Param(name="allDay" , description="值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。",dataType = DataType.INTEGER,required = false),
                       @Param(name="rrule" , description="事件的重复发生规则格式。例如，”FREQ=WEEKLY;COUNT=10;WKST=SU”。",dataType = DataType.STRING,required = false),
                       @Param(name="rdate" , description="事件的重复发生日期。 RDATE 与 RRULE 通常联合用于定义一组聚合重复实例。 如需查看更详细的介绍，请参阅 RFC5545 规范。",dataType = DataType.STRING,required = false),
                       @Param(name="availability" , description="将此事件视为忙碌时间还是可调度的空闲时间",dataType = DataType.INTEGER,required = false),
                       @Param(name="guestsCanModify" , description="来宾是否可修改事件。",dataType = DataType.INTEGER,required = false),
                       @Param(name="guestsCanInviteOthers" , description="来宾是否可邀请其他来宾。",dataType = DataType.INTEGER,required = false),
                       @Param(name="guestsCanSeeGuests" , description="来宾是否可查看参加者列表。",dataType = DataType.INTEGER,required = false),
                       @Param(name="user" , description="备注",dataType = DataType.LONG,required = false),
                       @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
                       @Param(name="updateTime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
                    })
                @RequestMapping(value = "save.json",method = RequestMethod.POST)
                @ResponseBody
                public ResultDTO save(HttpServletRequest request) throws Exception {
                    Event event =new  Event();
                    /*
                    String id = request.getParameter("id");
                    if(!StringUtil.isBlank(id)){
                        event.setId(Long.valueOf(id)) ;
                    }
                    
                    String calendarId = request.getParameter("calendarId");
                    if(!StringUtil.isBlank(calendarId)){
                        event.setCalendarId(Long.valueOf(calendarId)) ;
                    }
                    
                    String organizer = request.getParameter("organizer");
                    if(!StringUtil.isBlank(organizer)){
                        event.setOrganizer(String.valueOf(organizer)) ;
                    }
                    
                    String title = request.getParameter("title");
                    if(!StringUtil.isBlank(title)){
                        event.setTitle(String.valueOf(title)) ;
                    }
                    
                    String eventLocation = request.getParameter("eventLocation");
                    if(!StringUtil.isBlank(eventLocation)){
                        event.setEventLocation(String.valueOf(eventLocation)) ;
                    }
                    
                    String description = request.getParameter("description");
                    if(!StringUtil.isBlank(description)){
                        event.setDescription(String.valueOf(description)) ;
                    }
                    
                    String dtstart = request.getParameter("dtstart");
                    if(!StringUtil.isBlank(dtstart)){
                        event.setDtstart(Long.valueOf(dtstart)) ;
                    }
                    
                    String dtend = request.getParameter("dtend");
                    if(!StringUtil.isBlank(dtend)){
                        event.setDtend(Long.valueOf(dtend)) ;
                    }
                    
                    String eventTimezone = request.getParameter("eventTimezone");
                    if(!StringUtil.isBlank(eventTimezone)){
                        event.setEventTimezone(String.valueOf(eventTimezone)) ;
                    }
                    
                    String eventEndTimezone = request.getParameter("eventEndTimezone");
                    if(!StringUtil.isBlank(eventEndTimezone)){
                        event.setEventEndTimezone(String.valueOf(eventEndTimezone)) ;
                    }
                    
                    String duration = request.getParameter("duration");
                    if(!StringUtil.isBlank(duration)){
                        event.setDuration(String.valueOf(duration)) ;
                    }
                    
                    String allDay = request.getParameter("allDay");
                    if(!StringUtil.isBlank(allDay)){
                        event.setAllDay(Integer.valueOf(allDay)) ;
                    }
                    
                    String rrule = request.getParameter("rrule");
                    if(!StringUtil.isBlank(rrule)){
                        event.setRrule(String.valueOf(rrule)) ;
                    }
                    
                    String rdate = request.getParameter("rdate");
                    if(!StringUtil.isBlank(rdate)){
                        event.setRdate(String.valueOf(rdate)) ;
                    }
                    
                    String availability = request.getParameter("availability");
                    if(!StringUtil.isBlank(availability)){
                        event.setAvailability(Integer.valueOf(availability)) ;
                    }
                    
                    String guestsCanModify = request.getParameter("guestsCanModify");
                    if(!StringUtil.isBlank(guestsCanModify)){
                        event.setGuestsCanModify(Integer.valueOf(guestsCanModify)) ;
                    }
                    
                    String guestsCanInviteOthers = request.getParameter("guestsCanInviteOthers");
                    if(!StringUtil.isBlank(guestsCanInviteOthers)){
                        event.setGuestsCanInviteOthers(Integer.valueOf(guestsCanInviteOthers)) ;
                    }
                    
                    String guestsCanSeeGuests = request.getParameter("guestsCanSeeGuests");
                    if(!StringUtil.isBlank(guestsCanSeeGuests)){
                        event.setGuestsCanSeeGuests(Integer.valueOf(guestsCanSeeGuests)) ;
                    }
                    
                    String user = request.getParameter("user");
                    if(!StringUtil.isBlank(user)){
                        event.setUser(Long.valueOf(user)) ;
                    }
                    
                    String createTime = request.getParameter("createTime");
                    if(!StringUtil.isBlank(createTime)){
                        event.setCreateTime(Timestamp.valueOf(createTime)) ;
                    }
                    
                    String updateTime = request.getParameter("updateTime");
                    if(!StringUtil.isBlank(updateTime)){
                        event.setUpdateTime(Timestamp.valueOf(updateTime)) ;
                    }
                    */
                    String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            event.setId(Long.valueOf(id));
        }
        String calendarId = request.getParameter("calendarId");
        if(!StringUtil.isBlank(calendarId)){
            event.setCalendarId(Long.valueOf(calendarId));
        }
        String organizer = request.getParameter("organizer");
        if(!StringUtil.isBlank(organizer)){
            event.setOrganizer(organizer);
        }
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            event.setTitle(title);
        }
        String eventLocation = request.getParameter("eventLocation");
        if(!StringUtil.isBlank(eventLocation)){
            event.setEventLocation(eventLocation);
        }
        String description = request.getParameter("description");
        if(!StringUtil.isBlank(description)){
            event.setDescription(description);
        }
        String dtstart = request.getParameter("dtstart");
        if(!StringUtil.isBlank(dtstart)){
            event.setDtstart(Long.valueOf(dtstart));
        }
        String dtend = request.getParameter("dtend");
        if(!StringUtil.isBlank(dtend)){
            event.setDtend(Long.valueOf(dtend));
        }
        String eventTimezone = request.getParameter("eventTimezone");
        if(!StringUtil.isBlank(eventTimezone)){
            event.setEventTimezone(eventTimezone);
        }
        String eventEndTimezone = request.getParameter("eventEndTimezone");
        if(!StringUtil.isBlank(eventEndTimezone)){
            event.setEventEndTimezone(eventEndTimezone);
        }
        String duration = request.getParameter("duration");
        if(!StringUtil.isBlank(duration)){
            event.setDuration(duration);
        }
        String allDay = request.getParameter("allDay");
        if(!StringUtil.isBlank(allDay)){
            event.setAllDay(Integer.valueOf(allDay));
        }
        String rrule = request.getParameter("rrule");
        if(!StringUtil.isBlank(rrule)){
            event.setRrule(rrule);
        }
        String rdate = request.getParameter("rdate");
        if(!StringUtil.isBlank(rdate)){
            event.setRdate(rdate);
        }
        String availability = request.getParameter("availability");
        if(!StringUtil.isBlank(availability)){
            event.setAvailability(Integer.valueOf(availability));
        }
        String guestsCanModify = request.getParameter("guestsCanModify");
        if(!StringUtil.isBlank(guestsCanModify)){
            event.setGuestsCanModify(Integer.valueOf(guestsCanModify));
        }
        String guestsCanInviteOthers = request.getParameter("guestsCanInviteOthers");
        if(!StringUtil.isBlank(guestsCanInviteOthers)){
            event.setGuestsCanInviteOthers(Integer.valueOf(guestsCanInviteOthers));
        }
        String guestsCanSeeGuests = request.getParameter("guestsCanSeeGuests");
        if(!StringUtil.isBlank(guestsCanSeeGuests)){
            event.setGuestsCanSeeGuests(Integer.valueOf(guestsCanSeeGuests));
        }
        String user = request.getParameter("user");
        if(!StringUtil.isBlank(user)){
            event.setUser(Long.valueOf(user));
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                event.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                event.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                event.setUpdateTime(Timestamp.valueOf(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                event.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

                    //valid
                    ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("calendarId", calendarId, "事件所属日历的 _ID",  new Rule[]{new Digits(11,0)});
        vu.add("organizer", organizer, "事件组织者（所有者）的电子邮件",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("title", title, "事件的名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("eventLocation", eventLocation, "事件的发生地点",  new Rule[]{new Length(50)});
        vu.add("description", description, "事件的描述",  new Rule[]{new Length(100)});
        vu.add("dtstart", dtstart, "事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示",  new Rule[]{new Digits(15,0)});
        vu.add("dtend", dtend, "事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示",  new Rule[]{new Digits(15,0)});
        vu.add("eventTimezone", eventTimezone, "事件的时区",  new Rule[]{new Length(20)});
        vu.add("eventEndTimezone", eventEndTimezone, "事件结束时间的时区",  new Rule[]{new Length(20)});
        vu.add("duration", duration, "RFC5545 格式的事件持续时间。例如，值为 “PT1H” 表示事件应持续一小时，值为 “P2W” 表示持续 2 周。",  new Rule[]{new Length(20)});
        vu.add("allDay", allDay, "值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。",  new Rule[]{new Digits(1,0)});
        vu.add("rrule", rrule, "事件的重复发生规则格式。例如，”FREQ=WEEKLY;COUNT=10;WKST=SU”。",  new Rule[]{new Length(20)});
        vu.add("rdate", rdate, "事件的重复发生日期。 RDATE 与 RRULE 通常联合用于定义一组聚合重复实例。 如需查看更详细的介绍，请参阅 RFC5545 规范。",  new Rule[]{new Length(11)});
        vu.add("availability", availability, "将此事件视为忙碌时间还是可调度的空闲时间",  new Rule[]{new Digits(1,0)});
        vu.add("guestsCanModify", guestsCanModify, "来宾是否可修改事件。",  new Rule[]{new Digits(1,0)});
        vu.add("guestsCanInviteOthers", guestsCanInviteOthers, "来宾是否可邀请其他来宾。",  new Rule[]{new Digits(1,0)});
        vu.add("guestsCanSeeGuests", guestsCanSeeGuests, "来宾是否可查看参加者列表。",  new Rule[]{new Digits(1,0)});
        vu.add("user", user, "备注",  new Rule[]{new Digits(11,0)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

                    return eventService.save(event);

                }

    /**
     * 说明:删除Event信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-10-15 15:47:24
     */
     @API( summary="根据id删除单个日历信息",
        description = "根据id删除单个日历信息",
        parameters={
         @Param(name="id" , description="编号",dataType= DataType.LONG,required = true),
        })
    @RequestMapping(value = "/delete.json",method = RequestMethod.DELETE)//{id}
    @ResponseBody
    public ResultDTO delete(HttpServletRequest request) {//@PathVariable Long id,
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        eventService.delete(id);
        return this.getResult(SUCC);
    }

     /**
         * 说明:删除Event信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2018-10-15 15:47:24
         */
         @API( summary="根据id删除单个日历信息",
            description = "根据id删除单个日历信息",
            parameters={
             @Param(name="id" , description="编号",in=InType.path,dataType= DataType.LONG,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable Long id,HttpServletRequest request) {
            eventService.delete(id);
            return this.getResult(SUCC);
        }
     /**
     * 多行删除
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/mdel.json")
    @ResponseBody
    public ResultDTO multiDelete(HttpServletRequest request) {
        String idStr = request.getParameter("ids");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        String idStrAry[]= idStr.split(",");
        Long idAry[]=new Long[idStrAry.length];
        for(int i=0,length=idAry.length;i<length;i++){
            ValidateUtil vu = new ValidateUtil();
            String validStr="";
            String id = idStrAry[i];
                    vu.add("id", id, "编号",  new Rule[]{});

            try{
                validStr=vu.validateString();
            }catch(Exception e){
                e.printStackTrace();
                validStr="验证程序异常";
                return ResultUtil.getResult(302,validStr);
            }
            
            if(StringUtil.isNotBlank(validStr)) {
                return ResultUtil.getResult(302,validStr);
            }
            idAry[i]=Long.valueOf(idStrAry[i]);
        }
       return  eventService.multilDelete(idAry);
    }

    /**
     * 导出
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/export.json")
    @ResponseBody   
    public ResultDTO exportExcel(HttpServletRequest request){
               HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String calendarId = request.getParameter("calendarId");
        if(!StringUtil.isBlank(calendarId)){
            params.put("calendarId",calendarId);
        }
        String organizer = request.getParameter("organizer");
        if(!StringUtil.isBlank(organizer)){
            params.put("organizer",organizer);
        }
        String organizerLike = request.getParameter("organizerLike");
        if(!StringUtil.isBlank(organizerLike)){
            params.put("organizerLike",organizerLike);
        }
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = request.getParameter("titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
        }
        String eventLocation = request.getParameter("eventLocation");
        if(!StringUtil.isBlank(eventLocation)){
            params.put("eventLocation",eventLocation);
        }
        String eventLocationLike = request.getParameter("eventLocationLike");
        if(!StringUtil.isBlank(eventLocationLike)){
            params.put("eventLocationLike",eventLocationLike);
        }
        String description = request.getParameter("description");
        if(!StringUtil.isBlank(description)){
            params.put("description",description);
        }
        String descriptionLike = request.getParameter("descriptionLike");
        if(!StringUtil.isBlank(descriptionLike)){
            params.put("descriptionLike",descriptionLike);
        }
        String dtstart = request.getParameter("dtstart");
        if(!StringUtil.isBlank(dtstart)){
            params.put("dtstart",dtstart);
        }
        String dtend = request.getParameter("dtend");
        if(!StringUtil.isBlank(dtend)){
            params.put("dtend",dtend);
        }
        String eventTimezone = request.getParameter("eventTimezone");
        if(!StringUtil.isBlank(eventTimezone)){
            params.put("eventTimezone",eventTimezone);
        }
        String eventTimezoneLike = request.getParameter("eventTimezoneLike");
        if(!StringUtil.isBlank(eventTimezoneLike)){
            params.put("eventTimezoneLike",eventTimezoneLike);
        }
        String eventEndTimezone = request.getParameter("eventEndTimezone");
        if(!StringUtil.isBlank(eventEndTimezone)){
            params.put("eventEndTimezone",eventEndTimezone);
        }
        String eventEndTimezoneLike = request.getParameter("eventEndTimezoneLike");
        if(!StringUtil.isBlank(eventEndTimezoneLike)){
            params.put("eventEndTimezoneLike",eventEndTimezoneLike);
        }
        String duration = request.getParameter("duration");
        if(!StringUtil.isBlank(duration)){
            params.put("duration",duration);
        }
        String durationLike = request.getParameter("durationLike");
        if(!StringUtil.isBlank(durationLike)){
            params.put("durationLike",durationLike);
        }
        String allDay = request.getParameter("allDay");
        if(!StringUtil.isBlank(allDay)){
            params.put("allDay",allDay);
        }
        String rrule = request.getParameter("rrule");
        if(!StringUtil.isBlank(rrule)){
            params.put("rrule",rrule);
        }
        String rruleLike = request.getParameter("rruleLike");
        if(!StringUtil.isBlank(rruleLike)){
            params.put("rruleLike",rruleLike);
        }
        String rdate = request.getParameter("rdate");
        if(!StringUtil.isBlank(rdate)){
            params.put("rdate",rdate);
        }
        String rdateLike = request.getParameter("rdateLike");
        if(!StringUtil.isBlank(rdateLike)){
            params.put("rdateLike",rdateLike);
        }
        String availability = request.getParameter("availability");
        if(!StringUtil.isBlank(availability)){
            params.put("availability",availability);
        }
        String guestsCanModify = request.getParameter("guestsCanModify");
        if(!StringUtil.isBlank(guestsCanModify)){
            params.put("guestsCanModify",guestsCanModify);
        }
        String guestsCanInviteOthers = request.getParameter("guestsCanInviteOthers");
        if(!StringUtil.isBlank(guestsCanInviteOthers)){
            params.put("guestsCanInviteOthers",guestsCanInviteOthers);
        }
        String guestsCanSeeGuests = request.getParameter("guestsCanSeeGuests");
        if(!StringUtil.isBlank(guestsCanSeeGuests)){
            params.put("guestsCanSeeGuests",guestsCanSeeGuests);
        }
        String user = request.getParameter("user");
        if(!StringUtil.isBlank(user)){
            params.put("user",user);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = request.getParameter("createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = request.getParameter("createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                params.put("updateTime",updateTime);
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTime",new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeBegin = request.getParameter("updateTimeBegin");
        if(!StringUtil.isBlank(updateTimeBegin)){
            if(StringUtil.checkNumeric(updateTimeBegin)){
                params.put("updateTimeBegin",updateTimeBegin);
            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeBegin",new Timestamp( DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeEnd = request.getParameter("updateTimeEnd");
        if(!StringUtil.isBlank(updateTimeEnd)){
            if(StringUtil.checkNumeric(updateTimeEnd)){
                params.put("updateTimeEnd",updateTimeEnd);
            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeEnd",new Timestamp( DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        // 查询list集合
        List<Event> list =eventService.listByParams(params);
        // 存放临时文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "list.xlsx");
        String folder = request.getSession().getServletContext()
                .getRealPath("/")
                + "xlstmp";
        File folder_file = new File(folder);
        if (!folder_file.exists()) {
            folder_file.mkdir();
        }
        String fileName = folder + File.separator
                + DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS")
                + ".xlsx";
        // 得到导出Excle时清单的英中文map
        LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
        colTitle.put("id", "编号");
        colTitle.put("calendarId", "事件所属日历的 _ID");
        colTitle.put("organizer", "事件组织者（所有者）的电子邮件");
        colTitle.put("title", "事件的名称");
        colTitle.put("eventLocation", "事件的发生地点");
        colTitle.put("description", "事件的描述");
        colTitle.put("dtstart", "事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示");
        colTitle.put("dtend", "事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示");
        colTitle.put("eventTimezone", "事件的时区");
        colTitle.put("eventEndTimezone", "事件结束时间的时区");
        colTitle.put("duration", "RFC5545 格式的事件持续时间。例如，值为 “PT1H” 表示事件应持续一小时，值为 “P2W” 表示持续 2 周。");
        colTitle.put("allDay", "值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。");
        colTitle.put("rrule", "事件的重复发生规则格式。例如，”FREQ=WEEKLY;COUNT=10;WKST=SU”。");
        colTitle.put("rdate", "事件的重复发生日期。 RDATE 与 RRULE 通常联合用于定义一组聚合重复实例。 如需查看更详细的介绍，请参阅 RFC5545 规范。");
        colTitle.put("availability", "将此事件视为忙碌时间还是可调度的空闲时间");
        colTitle.put("guestsCanModify", "来宾是否可修改事件。");
        colTitle.put("guestsCanInviteOthers", "来宾是否可邀请其他来宾。");
        colTitle.put("guestsCanSeeGuests", "来宾是否可查看参加者列表。");
        colTitle.put("user", "备注");
        colTitle.put("createTime", "创建时间");
        colTitle.put("updateTime", "更新时间");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            Event sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("calendarId",  list.get(i).getCalendarId());
            map.put("organizer",  list.get(i).getOrganizer());
            map.put("title",  list.get(i).getTitle());
            map.put("eventLocation",  list.get(i).getEventLocation());
            map.put("description",  list.get(i).getDescription());
            map.put("dtstart",  list.get(i).getDtstart());
            map.put("dtend",  list.get(i).getDtend());
            map.put("eventTimezone",  list.get(i).getEventTimezone());
            map.put("eventEndTimezone",  list.get(i).getEventEndTimezone());
            map.put("duration",  list.get(i).getDuration());
            map.put("allDay",  list.get(i).getAllDay());
            map.put("rrule",  list.get(i).getRrule());
            map.put("rdate",  list.get(i).getRdate());
            map.put("availability",  list.get(i).getAvailability());
            map.put("guestsCanModify",  list.get(i).getGuestsCanModify());
            map.put("guestsCanInviteOthers",  list.get(i).getGuestsCanInviteOthers());
            map.put("guestsCanSeeGuests",  list.get(i).getGuestsCanSeeGuests());
            map.put("user",  list.get(i).getUser());
            map.put("createTime",  list.get(i).getCreateTime());
            map.put("updateTime",  list.get(i).getUpdateTime());
            finalList.add(map);
        }
        try {
            if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                return this.getResult(SUCC,fileName,"导出成功");
            }
            /*
             * return new ResponseEntity<byte[]>(
             * FileUtils.readFileToByteArray(new File(fileName)), headers,
             * HttpStatus.CREATED);
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.getResult(0, "数据为空，导出失败");
    
    }

     @API(summary = "批量导入信息",
                description = "批量导入信息",
                consumes = "multipart/form-data",
                parameters = {
                        @Param(name = "file", description = "编号", in = InType.form, dataType = DataType.FILE, required = true),
                })
    @RequestMapping(value = "/import", method = RequestMethod.POST)
     @ResponseBody
    public ResultDTO importExcel(@RequestParam("file") MultipartFile file){
          // 将spring 的file 装成 普通file
                File xlsFile = null;
                if (file != null) {
                    try {
                        String fileName = System.currentTimeMillis() + ".xls";//取一个随机的名称
                        String s = PathManager.getInstance().getTmpPath().resolve(fileName).toString();//存入tmp文件夹
                        Files.copy(file.getInputStream(), PathManager.getInstance().getTmpPath().resolve(fileName));//存到本地
                        xlsFile = PathManager.getInstance().getTmpPath().resolve(fileName).toFile();//读取
                    } catch (Exception e) {
                        logger.error("文件上传出错", e);
                        throw new BizException("041412312", "文件上传出错");
                    }
                }
                String result = "";
                try {

                    // 将导入的中文列名匹配至数据库对应字段
                    int success = 0;
                    int fail = 0;
                    StringBuffer errorMsg = new StringBuffer();//如果某行报错了 需要告知哪一行错误
        //            Map<String, String> colMatch = new HashMap<String, String>();
        //            colMatch.put("姓名", "name");
        //            colMatch.put("单位", "org");
        //            colMatch.put("邮箱", "email");


                    List<Map<String, String>> list = ExcelUtil.getExcelData(xlsFile);//excel 转成 list数据
                    for (int i = 0; i < list.size(); i++) {

                        Map<String, String> map = list.get(i);
                        String email = MapUtils.getStringValue(map, "邮箱");
                        // 检验手机号是否符合规范,不符合continue
                        if (!StringUtil.isEmail(email)) {
        //                    throw new ValidException("E2000016", MessageUtil.getMessage("E2000016", telphone));// 手机号码不符合一般格式。
                            logger.info(" import conf ==> the telphone:" + email + " is not email");
                            fail++;
                            errorMsg.append("" + email + " 不是邮箱地址;");
                            continue;
                        }
                        HashMap params = new HashMap();
                        params.put("email", email);
                      //  int count = contactsService.countByParams(params);//检查邮箱地址是否存在

                        Event bean = getInfoFromMap(params);

                      //  if (count > 0) {

                      //      logger.warn("邮箱已经存在:" + email);
                       //     errorMsg.append("邮箱已经存在:" + email);
                         //   continue;

                       // }

                        try {
                            eventService.save(bean);
                            success++;//成功数增加
                        } catch (Exception e) {

                            fail++;//失败数增加
                            logger.info("packageservice import conf ==> update fail ==>the telphone:" + email + "", e);
                            errorMsg.append("the telphone:" + email + " update fail;");
                        }

                    }
                    if (StringUtil.isNotBlank(errorMsg.toString()) && fail > 0) {
                        throw new BizException("2000016", "导入失败, 失败" + fail + "条。" + errorMsg.toString());
                    }
                    return this.getResult(3090182,"导入完成，成功导入" + success + "条，失败" + fail + "条。" + errorMsg.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("导入失败", e);
                    if (e instanceof org.apache.poi.poifs.filesystem.OfficeXmlFileException) {
                        throw new BizException("041412313", "导入的excel需为2003版本");
                    } else {
                        throw new BizException("041412313", e.getMessage());
                    }
                }


    }


      /**
         * 说明: 跳转到Event列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/EventList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/EventListMapper.html";
        }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-10-15 15:47:24
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/EventEdit.html";
    }
    /**
         * 说明:跳转查看页面
         * @param request 发请求
         * @return String
         * @author dozen.zhang
         * @date 2018-10-15 15:47:24
         */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/EventView.html";
    }



    private Event getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       Event event =new  Event();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                event.setId(Long.valueOf(id));
        }
        String calendarId = MapUtils.getString(bodyParam,"calendarId");
        if(!StringUtil.isBlank(calendarId)){
                event.setCalendarId(Long.valueOf(calendarId));
        }
        String organizer = MapUtils.getString(bodyParam,"organizer");
        if(!StringUtil.isBlank(organizer)){
                event.setOrganizer(String.valueOf(organizer));
        }
        String title = MapUtils.getString(bodyParam,"title");
        if(!StringUtil.isBlank(title)){
                event.setTitle(String.valueOf(title));
        }
        String eventLocation = MapUtils.getString(bodyParam,"eventLocation");
        if(!StringUtil.isBlank(eventLocation)){
                event.setEventLocation(String.valueOf(eventLocation));
        }
        String description = MapUtils.getString(bodyParam,"description");
        if(!StringUtil.isBlank(description)){
                event.setDescription(String.valueOf(description));
        }
        String dtstart = MapUtils.getString(bodyParam,"dtstart");
        if(!StringUtil.isBlank(dtstart)){
                event.setDtstart(Long.valueOf(dtstart));
        }
        String dtend = MapUtils.getString(bodyParam,"dtend");
        if(!StringUtil.isBlank(dtend)){
                event.setDtend(Long.valueOf(dtend));
        }
        String eventTimezone = MapUtils.getString(bodyParam,"eventTimezone");
        if(!StringUtil.isBlank(eventTimezone)){
                event.setEventTimezone(String.valueOf(eventTimezone));
        }
        String eventEndTimezone = MapUtils.getString(bodyParam,"eventEndTimezone");
        if(!StringUtil.isBlank(eventEndTimezone)){
                event.setEventEndTimezone(String.valueOf(eventEndTimezone));
        }
        String duration = MapUtils.getString(bodyParam,"duration");
        if(!StringUtil.isBlank(duration)){
                event.setDuration(String.valueOf(duration));
        }
        String allDay = MapUtils.getString(bodyParam,"allDay");
        if(!StringUtil.isBlank(allDay)){
                event.setAllDay(Integer.valueOf(allDay));
        }
        String rrule = MapUtils.getString(bodyParam,"rrule");
        if(!StringUtil.isBlank(rrule)){
                event.setRrule(String.valueOf(rrule));
        }
        String rdate = MapUtils.getString(bodyParam,"rdate");
        if(!StringUtil.isBlank(rdate)){
                event.setRdate(String.valueOf(rdate));
        }
        String availability = MapUtils.getString(bodyParam,"availability");
        if(!StringUtil.isBlank(availability)){
                event.setAvailability(Integer.valueOf(availability));
        }
        String guestsCanModify = MapUtils.getString(bodyParam,"guestsCanModify");
        if(!StringUtil.isBlank(guestsCanModify)){
                event.setGuestsCanModify(Integer.valueOf(guestsCanModify));
        }
        String guestsCanInviteOthers = MapUtils.getString(bodyParam,"guestsCanInviteOthers");
        if(!StringUtil.isBlank(guestsCanInviteOthers)){
                event.setGuestsCanInviteOthers(Integer.valueOf(guestsCanInviteOthers));
        }
        String guestsCanSeeGuests = MapUtils.getString(bodyParam,"guestsCanSeeGuests");
        if(!StringUtil.isBlank(guestsCanSeeGuests)){
                event.setGuestsCanSeeGuests(Integer.valueOf(guestsCanSeeGuests));
        }
        String user = MapUtils.getString(bodyParam,"user");
        if(!StringUtil.isBlank(user)){
                event.setUser(Long.valueOf(user));
        }
        String createTime = MapUtils.getString(bodyParam,"createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                event.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                event.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = MapUtils.getString(bodyParam,"updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                event.setUpdateTime(Timestamp.valueOf(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                event.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("calendarId", calendarId, "事件所属日历的 _ID",  new Rule[]{new Digits(11,0)});
        vu.add("organizer", organizer, "事件组织者（所有者）的电子邮件",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("title", title, "事件的名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("eventLocation", eventLocation, "事件的发生地点",  new Rule[]{new Length(50)});
        vu.add("description", description, "事件的描述",  new Rule[]{new Length(100)});
        vu.add("dtstart", dtstart, "事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示",  new Rule[]{new Digits(15,0)});
        vu.add("dtend", dtend, "事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示",  new Rule[]{new Digits(15,0)});
        vu.add("eventTimezone", eventTimezone, "事件的时区",  new Rule[]{new Length(20)});
        vu.add("eventEndTimezone", eventEndTimezone, "事件结束时间的时区",  new Rule[]{new Length(20)});
        vu.add("duration", duration, "RFC5545 格式的事件持续时间。例如，值为 “PT1H” 表示事件应持续一小时，值为 “P2W” 表示持续 2 周。",  new Rule[]{new Length(20)});
        vu.add("allDay", allDay, "值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。",  new Rule[]{new Digits(1,0)});
        vu.add("rrule", rrule, "事件的重复发生规则格式。例如，”FREQ=WEEKLY;COUNT=10;WKST=SU”。",  new Rule[]{new Length(20)});
        vu.add("rdate", rdate, "事件的重复发生日期。 RDATE 与 RRULE 通常联合用于定义一组聚合重复实例。 如需查看更详细的介绍，请参阅 RFC5545 规范。",  new Rule[]{new Length(11)});
        vu.add("availability", availability, "将此事件视为忙碌时间还是可调度的空闲时间",  new Rule[]{new Digits(1,0)});
        vu.add("guestsCanModify", guestsCanModify, "来宾是否可修改事件。",  new Rule[]{new Digits(1,0)});
        vu.add("guestsCanInviteOthers", guestsCanInviteOthers, "来宾是否可邀请其他来宾。",  new Rule[]{new Digits(1,0)});
        vu.add("guestsCanSeeGuests", guestsCanSeeGuests, "来宾是否可查看参加者列表。",  new Rule[]{new Digits(1,0)});
        vu.add("user", user, "备注",  new Rule[]{new Digits(11,0)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  event;
    }


      /**
                     * 说明:添加Event信息
                     * @param request
                     * @throws Exception
                     * @return ResultDTO
                     * @author dozen.zhang
                     * @date 2018-10-15 15:47:24
                     */
                    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                    @API( summary="添加单个日历信息",
                        description = "添加单个日历信息",
                        parameters={
                           @Param(name="id" , description="编号",in=InType.body,dataType = DataType.LONG,required = false),
                           @Param(name="calendarId" , description="事件所属日历的 _ID",in=InType.body,dataType = DataType.LONG,required = false),
                           @Param(name="organizer" , description="事件组织者（所有者）的电子邮件",in=InType.body,dataType = DataType.STRING,required = true),
                           @Param(name="title" , description="事件的名称",in=InType.body,dataType = DataType.STRING,required = true),
                           @Param(name="eventLocation" , description="事件的发生地点",in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="description" , description="事件的描述",in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="dtstart" , description="事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示",in=InType.body,dataType = DataType.LONG,required = false),
                           @Param(name="dtend" , description="事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示",in=InType.body,dataType = DataType.LONG,required = false),
                           @Param(name="eventTimezone" , description="事件的时区",in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="eventEndTimezone" , description="事件结束时间的时区",in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="duration" , description="RFC5545 格式的事件持续时间。例如，值为 “PT1H” 表示事件应持续一小时，值为 “P2W” 表示持续 2 周。",in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="allDay" , description="值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。",in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="rrule" , description="事件的重复发生规则格式。例如，”FREQ=WEEKLY;COUNT=10;WKST=SU”。",in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="rdate" , description="事件的重复发生日期。 RDATE 与 RRULE 通常联合用于定义一组聚合重复实例。 如需查看更详细的介绍，请参阅 RFC5545 规范。",in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="availability" , description="将此事件视为忙碌时间还是可调度的空闲时间",in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="guestsCanModify" , description="来宾是否可修改事件。",in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="guestsCanInviteOthers" , description="来宾是否可邀请其他来宾。",in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="guestsCanSeeGuests" , description="来宾是否可查看参加者列表。",in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="user" , description="备注",in=InType.body,dataType = DataType.LONG,required = false),
                           @Param(name="createTime" , description="创建时间",in=InType.body,dataType = DataType.DATE_TIME,required = false),
                           @Param(name="updateTime" , description="更新时间",in=InType.body,dataType = DataType.DATE_TIME,required = false),
                        })
                    @RequestMapping(value = "add",method = RequestMethod.POST)
                    @ResponseBody
                    public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
                        Event event =    getInfoFromMap(bodyParam);


                        return eventService.save(event);

                    }


    /**
    * 说明:添加Event信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2018-10-15 15:47:24
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个日历信息",
    description = "更新单个日历信息",
    parameters={
        @Param(name="id" , description="编号",in=InType.body,dataType = DataType.LONG,required = false),
        @Param(name="calendarId" , description="事件所属日历的 _ID",in=InType.body,dataType = DataType.LONG,required = false),
        @Param(name="organizer" , description="事件组织者（所有者）的电子邮件",in=InType.body,dataType = DataType.STRING,required = true),
        @Param(name="title" , description="事件的名称",in=InType.body,dataType = DataType.STRING,required = true),
        @Param(name="eventLocation" , description="事件的发生地点",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="description" , description="事件的描述",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="dtstart" , description="事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示",in=InType.body,dataType = DataType.LONG,required = false),
        @Param(name="dtend" , description="事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示",in=InType.body,dataType = DataType.LONG,required = false),
        @Param(name="eventTimezone" , description="事件的时区",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="eventEndTimezone" , description="事件结束时间的时区",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="duration" , description="RFC5545 格式的事件持续时间。例如，值为 “PT1H” 表示事件应持续一小时，值为 “P2W” 表示持续 2 周。",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="allDay" , description="值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="rrule" , description="事件的重复发生规则格式。例如，”FREQ=WEEKLY;COUNT=10;WKST=SU”。",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="rdate" , description="事件的重复发生日期。 RDATE 与 RRULE 通常联合用于定义一组聚合重复实例。 如需查看更详细的介绍，请参阅 RFC5545 规范。",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="availability" , description="将此事件视为忙碌时间还是可调度的空闲时间",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="guestsCanModify" , description="来宾是否可修改事件。",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="guestsCanInviteOthers" , description="来宾是否可邀请其他来宾。",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="guestsCanSeeGuests" , description="来宾是否可查看参加者列表。",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="user" , description="备注",in=InType.body,dataType = DataType.LONG,required = false),
        @Param(name="createTime" , description="创建时间",in=InType.body,dataType = DataType.DATE_TIME,required = false),
        @Param(name="updateTime" , description="更新时间",in=InType.body,dataType = DataType.DATE_TIME,required = false),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    Event event =    getInfoFromMap(bodyParam);
    return eventService.save(event);

    }
/**
     * 说明:ajax请求Event信息
     * @author dozen.zhang
     * @date 2018-10-15 15:47:24
     * @return String
     */
       @API(summary="日历列表接口",
                 description="日历列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="calendarId" , description="事件所属日历的 _ID",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="organizer" , description="事件组织者（所有者）的电子邮件",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="title" , description="事件的名称",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="eventLocation" , description="事件的发生地点",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="description" , description="事件的描述",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="dtstart" , description="事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="dtend" , description="事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="eventTimezone" , description="事件的时区",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="eventEndTimezone" , description="事件结束时间的时区",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="duration" , description="RFC5545 格式的事件持续时间。例如，值为 “PT1H” 表示事件应持续一小时，值为 “P2W” 表示持续 2 周。",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="allDay" , description="值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="rrule" , description="事件的重复发生规则格式。例如，”FREQ=WEEKLY;COUNT=10;WKST=SU”。",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="rdate" , description="事件的重复发生日期。 RDATE 与 RRULE 通常联合用于定义一组聚合重复实例。 如需查看更详细的介绍，请参阅 RFC5545 规范。",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="availability" , description="将此事件视为忙碌时间还是可调度的空闲时间",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="guestsCanModify" , description="来宾是否可修改事件。",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="guestsCanInviteOthers" , description="来宾是否可邀请其他来宾。",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="guestsCanSeeGuests" , description="来宾是否可查看参加者列表。",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="user" , description="备注",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="createTime" , description="创建时间",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="updateTime" , description="更新时间",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
         })
    @RequestMapping(value = "/list" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO list(HttpServletRequest request,@RequestParam(name = "params", required = true) String paramStr ) throws Exception{

        HashMap<String, Object> params = JsonUtil.fromJson(paramStr, HashMap.class);
         Page page = RequestUtil.getPage(params);
        if(page ==null){
             return this.getWrongResultFromCfg("err.param.page");
        }

                String id = MapUtils.getString(params,"id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String calendarId = MapUtils.getString(params,"calendarId");
        if(!StringUtil.isBlank(calendarId)){
            params.put("calendarId",calendarId);
        }
        String organizer = MapUtils.getString(params,"organizer");
        if(!StringUtil.isBlank(organizer)){
            params.put("organizer",organizer);
        }
        String organizerLike = MapUtils.getString(params,"organizerLike");
        if(!StringUtil.isBlank(organizerLike)){
            params.put("organizerLike",organizerLike);
        }
        String title = MapUtils.getString(params,"title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = MapUtils.getString(params,"titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
        }
        String eventLocation = MapUtils.getString(params,"eventLocation");
        if(!StringUtil.isBlank(eventLocation)){
            params.put("eventLocation",eventLocation);
        }
        String eventLocationLike = MapUtils.getString(params,"eventLocationLike");
        if(!StringUtil.isBlank(eventLocationLike)){
            params.put("eventLocationLike",eventLocationLike);
        }
        String description = MapUtils.getString(params,"description");
        if(!StringUtil.isBlank(description)){
            params.put("description",description);
        }
        String descriptionLike = MapUtils.getString(params,"descriptionLike");
        if(!StringUtil.isBlank(descriptionLike)){
            params.put("descriptionLike",descriptionLike);
        }
        String dtstart = MapUtils.getString(params,"dtstart");
        if(!StringUtil.isBlank(dtstart)){
            params.put("dtstart",dtstart);
        }
        String dtend = MapUtils.getString(params,"dtend");
        if(!StringUtil.isBlank(dtend)){
            params.put("dtend",dtend);
        }
        String eventTimezone = MapUtils.getString(params,"eventTimezone");
        if(!StringUtil.isBlank(eventTimezone)){
            params.put("eventTimezone",eventTimezone);
        }
        String eventTimezoneLike = MapUtils.getString(params,"eventTimezoneLike");
        if(!StringUtil.isBlank(eventTimezoneLike)){
            params.put("eventTimezoneLike",eventTimezoneLike);
        }
        String eventEndTimezone = MapUtils.getString(params,"eventEndTimezone");
        if(!StringUtil.isBlank(eventEndTimezone)){
            params.put("eventEndTimezone",eventEndTimezone);
        }
        String eventEndTimezoneLike = MapUtils.getString(params,"eventEndTimezoneLike");
        if(!StringUtil.isBlank(eventEndTimezoneLike)){
            params.put("eventEndTimezoneLike",eventEndTimezoneLike);
        }
        String duration = MapUtils.getString(params,"duration");
        if(!StringUtil.isBlank(duration)){
            params.put("duration",duration);
        }
        String durationLike = MapUtils.getString(params,"durationLike");
        if(!StringUtil.isBlank(durationLike)){
            params.put("durationLike",durationLike);
        }
        String allDay = MapUtils.getString(params,"allDay");
        if(!StringUtil.isBlank(allDay)){
            params.put("allDay",allDay);
        }
        String rrule = MapUtils.getString(params,"rrule");
        if(!StringUtil.isBlank(rrule)){
            params.put("rrule",rrule);
        }
        String rruleLike = MapUtils.getString(params,"rruleLike");
        if(!StringUtil.isBlank(rruleLike)){
            params.put("rruleLike",rruleLike);
        }
        String rdate = MapUtils.getString(params,"rdate");
        if(!StringUtil.isBlank(rdate)){
            params.put("rdate",rdate);
        }
        String rdateLike = MapUtils.getString(params,"rdateLike");
        if(!StringUtil.isBlank(rdateLike)){
            params.put("rdateLike",rdateLike);
        }
        String availability = MapUtils.getString(params,"availability");
        if(!StringUtil.isBlank(availability)){
            params.put("availability",availability);
        }
        String guestsCanModify = MapUtils.getString(params,"guestsCanModify");
        if(!StringUtil.isBlank(guestsCanModify)){
            params.put("guestsCanModify",guestsCanModify);
        }
        String guestsCanInviteOthers = MapUtils.getString(params,"guestsCanInviteOthers");
        if(!StringUtil.isBlank(guestsCanInviteOthers)){
            params.put("guestsCanInviteOthers",guestsCanInviteOthers);
        }
        String guestsCanSeeGuests = MapUtils.getString(params,"guestsCanSeeGuests");
        if(!StringUtil.isBlank(guestsCanSeeGuests)){
            params.put("guestsCanSeeGuests",guestsCanSeeGuests);
        }
        String user = MapUtils.getString(params,"user");
        if(!StringUtil.isBlank(user)){
            params.put("user",user);
        }
        String createTime = MapUtils.getString(params,"createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = MapUtils.getString(params,"createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = MapUtils.getString(params,"createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = MapUtils.getString(params,"updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                params.put("updateTime",updateTime);
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTime",new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeBegin = MapUtils.getString(params,"updateTimeBegin");
        if(!StringUtil.isBlank(updateTimeBegin)){
            if(StringUtil.checkNumeric(updateTimeBegin)){
                params.put("updateTimeBegin",updateTimeBegin);
            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeBegin",new Timestamp( DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeEnd = MapUtils.getString(params,"updateTimeEnd");
        if(!StringUtil.isBlank(updateTimeEnd)){
            if(StringUtil.checkNumeric(updateTimeEnd)){
                params.put("updateTimeEnd",updateTimeEnd);
            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeEnd",new Timestamp( DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page",page);
        List<Event> events = eventService.listByParams4Page(params);
        return ResultUtil.getResult(events, page);
    }



       /**
         * 导出
         * @param request
         * @return
         * @author dozen.zhang
         */
        @API(summary="日历列表导出接口",
          description="日历列表导出接口",
          parameters={
          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
             @Param(name="id" , description="编号",in=InType.params,dataType = DataType.LONG,required =false),// false
             @Param(name="calendarId" , description="事件所属日历的 _ID",in=InType.params,dataType = DataType.LONG,required =false),// false
             @Param(name="organizer" , description="事件组织者（所有者）的电子邮件",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="title" , description="事件的名称",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="eventLocation" , description="事件的发生地点",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="description" , description="事件的描述",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="dtstart" , description="事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示",in=InType.params,dataType = DataType.LONG,required =false),// false
             @Param(name="dtend" , description="事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示",in=InType.params,dataType = DataType.LONG,required =false),// false
             @Param(name="eventTimezone" , description="事件的时区",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="eventEndTimezone" , description="事件结束时间的时区",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="duration" , description="RFC5545 格式的事件持续时间。例如，值为 “PT1H” 表示事件应持续一小时，值为 “P2W” 表示持续 2 周。",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="allDay" , description="值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="rrule" , description="事件的重复发生规则格式。例如，”FREQ=WEEKLY;COUNT=10;WKST=SU”。",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="rdate" , description="事件的重复发生日期。 RDATE 与 RRULE 通常联合用于定义一组聚合重复实例。 如需查看更详细的介绍，请参阅 RFC5545 规范。",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="availability" , description="将此事件视为忙碌时间还是可调度的空闲时间",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="guestsCanModify" , description="来宾是否可修改事件。",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="guestsCanInviteOthers" , description="来宾是否可邀请其他来宾。",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="guestsCanSeeGuests" , description="来宾是否可查看参加者列表。",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="user" , description="备注",in=InType.params,dataType = DataType.LONG,required =false),// false
             @Param(name="createTime" , description="创建时间",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
             @Param(name="updateTime" , description="更新时间",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
          })
        @RequestMapping(value = "/export")
        @ResponseBody
        public ResultDTO exportExcelInBody(HttpServletRequest request,@RequestParam(name = "params", required = true) String paramStr ) throws Exception{

             HashMap<String, Object> params = JsonUtil.fromJson(paramStr, HashMap.class);
              Page page = RequestUtil.getPage(params);
             if(page ==null){
                  return this.getWrongResultFromCfg("err.param.page");
             }

                     String id = MapUtils.getString(params,"id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String calendarId = MapUtils.getString(params,"calendarId");
        if(!StringUtil.isBlank(calendarId)){
            params.put("calendarId",calendarId);
        }
        String organizer = MapUtils.getString(params,"organizer");
        if(!StringUtil.isBlank(organizer)){
            params.put("organizer",organizer);
        }
        String organizerLike = MapUtils.getString(params,"organizerLike");
        if(!StringUtil.isBlank(organizerLike)){
            params.put("organizerLike",organizerLike);
        }
        String title = MapUtils.getString(params,"title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = MapUtils.getString(params,"titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
        }
        String eventLocation = MapUtils.getString(params,"eventLocation");
        if(!StringUtil.isBlank(eventLocation)){
            params.put("eventLocation",eventLocation);
        }
        String eventLocationLike = MapUtils.getString(params,"eventLocationLike");
        if(!StringUtil.isBlank(eventLocationLike)){
            params.put("eventLocationLike",eventLocationLike);
        }
        String description = MapUtils.getString(params,"description");
        if(!StringUtil.isBlank(description)){
            params.put("description",description);
        }
        String descriptionLike = MapUtils.getString(params,"descriptionLike");
        if(!StringUtil.isBlank(descriptionLike)){
            params.put("descriptionLike",descriptionLike);
        }
        String dtstart = MapUtils.getString(params,"dtstart");
        if(!StringUtil.isBlank(dtstart)){
            params.put("dtstart",dtstart);
        }
        String dtend = MapUtils.getString(params,"dtend");
        if(!StringUtil.isBlank(dtend)){
            params.put("dtend",dtend);
        }
        String eventTimezone = MapUtils.getString(params,"eventTimezone");
        if(!StringUtil.isBlank(eventTimezone)){
            params.put("eventTimezone",eventTimezone);
        }
        String eventTimezoneLike = MapUtils.getString(params,"eventTimezoneLike");
        if(!StringUtil.isBlank(eventTimezoneLike)){
            params.put("eventTimezoneLike",eventTimezoneLike);
        }
        String eventEndTimezone = MapUtils.getString(params,"eventEndTimezone");
        if(!StringUtil.isBlank(eventEndTimezone)){
            params.put("eventEndTimezone",eventEndTimezone);
        }
        String eventEndTimezoneLike = MapUtils.getString(params,"eventEndTimezoneLike");
        if(!StringUtil.isBlank(eventEndTimezoneLike)){
            params.put("eventEndTimezoneLike",eventEndTimezoneLike);
        }
        String duration = MapUtils.getString(params,"duration");
        if(!StringUtil.isBlank(duration)){
            params.put("duration",duration);
        }
        String durationLike = MapUtils.getString(params,"durationLike");
        if(!StringUtil.isBlank(durationLike)){
            params.put("durationLike",durationLike);
        }
        String allDay = MapUtils.getString(params,"allDay");
        if(!StringUtil.isBlank(allDay)){
            params.put("allDay",allDay);
        }
        String rrule = MapUtils.getString(params,"rrule");
        if(!StringUtil.isBlank(rrule)){
            params.put("rrule",rrule);
        }
        String rruleLike = MapUtils.getString(params,"rruleLike");
        if(!StringUtil.isBlank(rruleLike)){
            params.put("rruleLike",rruleLike);
        }
        String rdate = MapUtils.getString(params,"rdate");
        if(!StringUtil.isBlank(rdate)){
            params.put("rdate",rdate);
        }
        String rdateLike = MapUtils.getString(params,"rdateLike");
        if(!StringUtil.isBlank(rdateLike)){
            params.put("rdateLike",rdateLike);
        }
        String availability = MapUtils.getString(params,"availability");
        if(!StringUtil.isBlank(availability)){
            params.put("availability",availability);
        }
        String guestsCanModify = MapUtils.getString(params,"guestsCanModify");
        if(!StringUtil.isBlank(guestsCanModify)){
            params.put("guestsCanModify",guestsCanModify);
        }
        String guestsCanInviteOthers = MapUtils.getString(params,"guestsCanInviteOthers");
        if(!StringUtil.isBlank(guestsCanInviteOthers)){
            params.put("guestsCanInviteOthers",guestsCanInviteOthers);
        }
        String guestsCanSeeGuests = MapUtils.getString(params,"guestsCanSeeGuests");
        if(!StringUtil.isBlank(guestsCanSeeGuests)){
            params.put("guestsCanSeeGuests",guestsCanSeeGuests);
        }
        String user = MapUtils.getString(params,"user");
        if(!StringUtil.isBlank(user)){
            params.put("user",user);
        }
        String createTime = MapUtils.getString(params,"createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = MapUtils.getString(params,"createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = MapUtils.getString(params,"createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = MapUtils.getString(params,"updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                params.put("updateTime",updateTime);
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTime",new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeBegin = MapUtils.getString(params,"updateTimeBegin");
        if(!StringUtil.isBlank(updateTimeBegin)){
            if(StringUtil.checkNumeric(updateTimeBegin)){
                params.put("updateTimeBegin",updateTimeBegin);
            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeBegin",new Timestamp( DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeEnd = MapUtils.getString(params,"updateTimeEnd");
        if(!StringUtil.isBlank(updateTimeEnd)){
            if(StringUtil.checkNumeric(updateTimeEnd)){
                params.put("updateTimeEnd",updateTimeEnd);
            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeEnd",new Timestamp( DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

             params.put("page",page);
             List<Event> list = eventService.listByParams4Page(params);
            // 存放临时文件
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "list.xlsx");
              String randomName = DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS")+".xlsx";

            String folder = request.getSession().getServletContext()
                    .getRealPath("/")
                    + "xlstmp";


            File folder_file = new File(folder);
            if (!folder_file.exists()) {
                folder_file.mkdir();
            }
            String fileName = folder + File.separator
                      + randomName;
            // 得到导出Excle时清单的英中文map
            LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
            colTitle.put("id", "编号");
            colTitle.put("calendarId", "事件所属日历的 _ID");
            colTitle.put("organizer", "事件组织者（所有者）的电子邮件");
            colTitle.put("title", "事件的名称");
            colTitle.put("eventLocation", "事件的发生地点");
            colTitle.put("description", "事件的描述");
            colTitle.put("dtstart", "事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示");
            colTitle.put("dtend", "事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示");
            colTitle.put("eventTimezone", "事件的时区");
            colTitle.put("eventEndTimezone", "事件结束时间的时区");
            colTitle.put("duration", "RFC5545 格式的事件持续时间。例如，值为 “PT1H” 表示事件应持续一小时，值为 “P2W” 表示持续 2 周。");
            colTitle.put("allDay", "值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。");
            colTitle.put("rrule", "事件的重复发生规则格式。例如，”FREQ=WEEKLY;COUNT=10;WKST=SU”。");
            colTitle.put("rdate", "事件的重复发生日期。 RDATE 与 RRULE 通常联合用于定义一组聚合重复实例。 如需查看更详细的介绍，请参阅 RFC5545 规范。");
            colTitle.put("availability", "将此事件视为忙碌时间还是可调度的空闲时间");
            colTitle.put("guestsCanModify", "来宾是否可修改事件。");
            colTitle.put("guestsCanInviteOthers", "来宾是否可邀请其他来宾。");
            colTitle.put("guestsCanSeeGuests", "来宾是否可查看参加者列表。");
            colTitle.put("user", "备注");
            colTitle.put("createTime", "创建时间");
            colTitle.put("updateTime", "更新时间");
            List<Map> finalList = new ArrayList<Map>();
            for (int i = 0; i < list.size(); i++) {
                Event sm = list.get(i);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id",  list.get(i).getId());
                map.put("calendarId",  list.get(i).getCalendarId());
                map.put("organizer",  list.get(i).getOrganizer());
                map.put("title",  list.get(i).getTitle());
                map.put("eventLocation",  list.get(i).getEventLocation());
                map.put("description",  list.get(i).getDescription());
                map.put("dtstart",  list.get(i).getDtstart());
                map.put("dtend",  list.get(i).getDtend());
                map.put("eventTimezone",  list.get(i).getEventTimezone());
                map.put("eventEndTimezone",  list.get(i).getEventEndTimezone());
                map.put("duration",  list.get(i).getDuration());
                map.put("allDay",  list.get(i).getAllDay());
                map.put("rrule",  list.get(i).getRrule());
                map.put("rdate",  list.get(i).getRdate());
                map.put("availability",  list.get(i).getAvailability());
                map.put("guestsCanModify",  list.get(i).getGuestsCanModify());
                map.put("guestsCanInviteOthers",  list.get(i).getGuestsCanInviteOthers());
                map.put("guestsCanSeeGuests",  list.get(i).getGuestsCanSeeGuests());
                map.put("user",  list.get(i).getUser());
                map.put("createTime",  list.get(i).getCreateTime());
                map.put("updateTime",  list.get(i).getUpdateTime());
                finalList.add(map);
            }
            try {
                if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                    return this.getResult(SUCC,SysConfig.PATH+"/xlstmp/"+randomName,"导出成功");
                }
                /*
                 * return new ResponseEntity<byte[]>(
                 * FileUtils.readFileToByteArray(new File(fileName)), headers,
                 * HttpStatus.CREATED);
                 */
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.getResult(0, "数据为空，导出失败");

        }

}
