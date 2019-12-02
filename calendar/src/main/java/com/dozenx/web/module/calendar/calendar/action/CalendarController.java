/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-10-15 15:47:24
 * 文件说明: 
 */

package com.dozenx.web.module.calendar.calendar.action;

import com.dozenx.common.Path.PathManager;
import com.dozenx.common.config.SysConfig;
import com.dozenx.common.exception.BizException;
import com.dozenx.common.exception.ParamException;
import com.dozenx.common.util.*;
import com.dozenx.swagger.annotation.*;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.module.calendar.calendar.bean.Calendar;
import com.dozenx.web.module.calendar.calendar.service.CalendarService;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.*;

@APIs(description = "日历表")
@Controller
@RequestMapping("/rili/calendar")
public class CalendarController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(CalendarController.class);
    /** 权限service **/
    @Autowired
    private CalendarService calendarService;
    


    /**
     * 说明:ajax请求Calendar信息
     * @author dozen.zhang
     * @date 2018-10-15 15:47:24
     * @return String
     */
       @API(summary="日历表列表接口",
                 description="日历表列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",dataType = DataType.LONG,required =false),// false
                    @Param(name="name" , description="日历的名称",dataType = DataType.STRING,required =false),// true
                    @Param(name="calendarDisplayName" , description="该日历显示给用户时使用的名称",dataType = DataType.STRING,required =false),// true
                    @Param(name="visible" , description="表示是否选择显示该日历的布尔值",dataType = DataType.INTEGER,required =false),// true
                    @Param(name="syncEvents" , description="表示是否应同步日历并将其事件存储在设备上",dataType = DataType.INTEGER,required =false),// true
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
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String calendarDisplayName = request.getParameter("calendarDisplayName");
        if(!StringUtil.isBlank(calendarDisplayName)){
            params.put("calendarDisplayName",calendarDisplayName);
        }
        String calendarDisplayNameLike = request.getParameter("calendarDisplayNameLike");
        if(!StringUtil.isBlank(calendarDisplayNameLike)){
            params.put("calendarDisplayNameLike",calendarDisplayNameLike);
        }
        String visible = request.getParameter("visible");
        if(!StringUtil.isBlank(visible)){
            params.put("visible",visible);
        }
        String syncEvents = request.getParameter("syncEvents");
        if(!StringUtil.isBlank(syncEvents)){
            params.put("syncEvents",syncEvents);
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
        List<Calendar> calendars = calendarService.listByParams4Page(params);
        return ResultUtil.getResult(calendars, page);
    }
    
   /**
    * 说明:ajax请求Calendar信息 无分页版本
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
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String calendarDisplayName = request.getParameter("calendarDisplayName");
        if(!StringUtil.isBlank(calendarDisplayName)){
            params.put("calendarDisplayName",calendarDisplayName);
        }
        String calendarDisplayNameLike = request.getParameter("calendarDisplayNameLike");
        if(!StringUtil.isBlank(calendarDisplayNameLike)){
            params.put("calendarDisplayNameLike",calendarDisplayNameLike);
        }
        String visible = request.getParameter("visible");
        if(!StringUtil.isBlank(visible)){
            params.put("visible",visible);
        }
        String syncEvents = request.getParameter("syncEvents");
        if(!StringUtil.isBlank(syncEvents)){
            params.put("syncEvents",syncEvents);
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

        List<Calendar> calendars = calendarService.listByParams(params);
        return ResultUtil.getDataResult(calendars);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2018-10-15 15:47:24
    */
     @API( summary="根据id查询单个日历表信息",
               description = "根据id查询单个日历表信息",
               parameters={
                       @Param(name="id" , description="id",in=InType.path,dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(@PathVariable Long id,HttpServletRequest request) {
                     HashMap<String,Object> result =new HashMap</*String,Object*/>();
        if(id > 0){
            Calendar bean = calendarService.selectByPrimaryKey(Long.valueOf(id));
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
      @API( summary="根据id查询单个日历表信息",
               description = "根据id查询单个日历表信息",
               parameters={
                       @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view.json",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(HttpServletRequest request) {
            String id = request.getParameter("id");
            Calendar bean = calendarService.selectByPrimaryKey(Long.valueOf(id));
          //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
           // result.put("bean", bean);
            return this.getResult(bean);
        }

    /**
     * 说明:更新Calendar信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-10-15 15:47:24
     */
      @API( summary="更新id更新单个日历表信息",
        description = "更新id更新单个日历表信息",
        parameters={
           @Param(name="id" , description="编号",type="LONG",required = false),
           @Param(name="name" , description="日历的名称",type="STRING",required = true),
           @Param(name="calendarDisplayName" , description="该日历显示给用户时使用的名称",type="STRING",required = true),
           @Param(name="visible" , description="表示是否选择显示该日历的布尔值",type="INTEGER",required = true),
           @Param(name="syncEvents" , description="表示是否应同步日历并将其事件存储在设备上",type="INTEGER",required = true),
           @Param(name="user" , description="备注",type="LONG",required = false),
           @Param(name="createTime" , description="创建时间",type="DATE_TIME",required = false),
           @Param(name="updateTime" , description="更新时间",type="DATE_TIME",required = false),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update.json",method = RequestMethod.PUT)///{id}
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Long id,
        Calendar calendar =new  Calendar();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            calendar.setId(Long.valueOf(id)) ;
        }
        
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            calendar.setName(String.valueOf(name)) ;
        }
        
        String calendarDisplayName = request.getParameter("calendarDisplayName");
        if(!StringUtil.isBlank(calendarDisplayName)){
            calendar.setCalendarDisplayName(String.valueOf(calendarDisplayName)) ;
        }
        
        String visible = request.getParameter("visible");
        if(!StringUtil.isBlank(visible)){
            calendar.setVisible(Integer.valueOf(visible)) ;
        }
        
        String syncEvents = request.getParameter("syncEvents");
        if(!StringUtil.isBlank(syncEvents)){
            calendar.setSyncEvents(Integer.valueOf(syncEvents)) ;
        }
        
        String user = request.getParameter("user");
        if(!StringUtil.isBlank(user)){
            calendar.setUser(Long.valueOf(user)) ;
        }
        
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            calendar.setCreateTime(Timestamp.valueOf(createTime)) ;
        }
        
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            calendar.setUpdateTime(Timestamp.valueOf(updateTime)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            calendar.setId(Long.valueOf(id));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            calendar.setName(name);
        }
        String calendarDisplayName = request.getParameter("calendarDisplayName");
        if(!StringUtil.isBlank(calendarDisplayName)){
            calendar.setCalendarDisplayName(calendarDisplayName);
        }
        String visible = request.getParameter("visible");
        if(!StringUtil.isBlank(visible)){
            calendar.setVisible(Integer.valueOf(visible));
        }
        String syncEvents = request.getParameter("syncEvents");
        if(!StringUtil.isBlank(syncEvents)){
            calendar.setSyncEvents(Integer.valueOf(syncEvents));
        }
        String user = request.getParameter("user");
        if(!StringUtil.isBlank(user)){
            calendar.setUser(Long.valueOf(user));
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                calendar.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                calendar.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                calendar.setUpdateTime(Timestamp.valueOf(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                calendar.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("name", name, "日历的名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("calendarDisplayName", calendarDisplayName, "该日历显示给用户时使用的名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("visible", visible, "表示是否选择显示该日历的布尔值",  new Rule[]{new Digits(1,0),new NotEmpty()});
        vu.add("syncEvents", syncEvents, "表示是否应同步日历并将其事件存储在设备上",  new Rule[]{new Digits(1,0),new NotEmpty()});
        vu.add("user", user, "备注",  new Rule[]{new Digits(11,0)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return calendarService.save(calendar);
       
    }


        /**
         * 说明:添加Calendar信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2018-10-15 15:47:24
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个日历表信息",
            description = "添加单个日历表信息",
            parameters={
               @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
               @Param(name="name" , description="日历的名称",dataType = DataType.STRING,required = true),
               @Param(name="calendarDisplayName" , description="该日历显示给用户时使用的名称",dataType = DataType.STRING,required = true),
               @Param(name="visible" , description="表示是否选择显示该日历的布尔值",dataType = DataType.INTEGER,required = true),
               @Param(name="syncEvents" , description="表示是否应同步日历并将其事件存储在设备上",dataType = DataType.INTEGER,required = true),
               @Param(name="user" , description="备注",dataType = DataType.LONG,required = false),
               @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
               @Param(name="updateTime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
            })
        @RequestMapping(value = "add.json",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            Calendar calendar =new  Calendar();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                calendar.setId(Long.valueOf(id)) ;
            }
            
            String name = request.getParameter("name");
            if(!StringUtil.isBlank(name)){
                calendar.setName(String.valueOf(name)) ;
            }
            
            String calendarDisplayName = request.getParameter("calendarDisplayName");
            if(!StringUtil.isBlank(calendarDisplayName)){
                calendar.setCalendarDisplayName(String.valueOf(calendarDisplayName)) ;
            }
            
            String visible = request.getParameter("visible");
            if(!StringUtil.isBlank(visible)){
                calendar.setVisible(Integer.valueOf(visible)) ;
            }
            
            String syncEvents = request.getParameter("syncEvents");
            if(!StringUtil.isBlank(syncEvents)){
                calendar.setSyncEvents(Integer.valueOf(syncEvents)) ;
            }
            
            String user = request.getParameter("user");
            if(!StringUtil.isBlank(user)){
                calendar.setUser(Long.valueOf(user)) ;
            }
            
            String createTime = request.getParameter("createTime");
            if(!StringUtil.isBlank(createTime)){
                calendar.setCreateTime(Timestamp.valueOf(createTime)) ;
            }
            
            String updateTime = request.getParameter("updateTime");
            if(!StringUtil.isBlank(updateTime)){
                calendar.setUpdateTime(Timestamp.valueOf(updateTime)) ;
            }
            */
            String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            calendar.setId(Long.valueOf(id));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            calendar.setName(name);
        }
        String calendarDisplayName = request.getParameter("calendarDisplayName");
        if(!StringUtil.isBlank(calendarDisplayName)){
            calendar.setCalendarDisplayName(calendarDisplayName);
        }
        String visible = request.getParameter("visible");
        if(!StringUtil.isBlank(visible)){
            calendar.setVisible(Integer.valueOf(visible));
        }
        String syncEvents = request.getParameter("syncEvents");
        if(!StringUtil.isBlank(syncEvents)){
            calendar.setSyncEvents(Integer.valueOf(syncEvents));
        }
        String user = request.getParameter("user");
        if(!StringUtil.isBlank(user)){
            calendar.setUser(Long.valueOf(user));
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                calendar.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                calendar.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                calendar.setUpdateTime(Timestamp.valueOf(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                calendar.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("name", name, "日历的名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("calendarDisplayName", calendarDisplayName, "该日历显示给用户时使用的名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("visible", visible, "表示是否选择显示该日历的布尔值",  new Rule[]{new Digits(1,0),new NotEmpty()});
        vu.add("syncEvents", syncEvents, "表示是否应同步日历并将其事件存储在设备上",  new Rule[]{new Digits(1,0),new NotEmpty()});
        vu.add("user", user, "备注",  new Rule[]{new Digits(11,0)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

            return calendarService.save(calendar);

        }


          /**
                 * 说明:添加Calendar信息
                 * @param request
                 * @throws Exception
                 * @return ResultDTO
                 * @author dozen.zhang
                 * @date 2018-10-15 15:47:24
                 */
                // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                @API( summary="添加单个日历表信息",
                    description = "添加单个日历表信息",
                    parameters={
                       @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
                       @Param(name="name" , description="日历的名称",dataType = DataType.STRING,required = true),
                       @Param(name="calendarDisplayName" , description="该日历显示给用户时使用的名称",dataType = DataType.STRING,required = true),
                       @Param(name="visible" , description="表示是否选择显示该日历的布尔值",dataType = DataType.INTEGER,required = true),
                       @Param(name="syncEvents" , description="表示是否应同步日历并将其事件存储在设备上",dataType = DataType.INTEGER,required = true),
                       @Param(name="user" , description="备注",dataType = DataType.LONG,required = false),
                       @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
                       @Param(name="updateTime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
                    })
                @RequestMapping(value = "save.json",method = RequestMethod.POST)
                @ResponseBody
                public ResultDTO save(HttpServletRequest request) throws Exception {
                    Calendar calendar =new  Calendar();
                    /*
                    String id = request.getParameter("id");
                    if(!StringUtil.isBlank(id)){
                        calendar.setId(Long.valueOf(id)) ;
                    }
                    
                    String name = request.getParameter("name");
                    if(!StringUtil.isBlank(name)){
                        calendar.setName(String.valueOf(name)) ;
                    }
                    
                    String calendarDisplayName = request.getParameter("calendarDisplayName");
                    if(!StringUtil.isBlank(calendarDisplayName)){
                        calendar.setCalendarDisplayName(String.valueOf(calendarDisplayName)) ;
                    }
                    
                    String visible = request.getParameter("visible");
                    if(!StringUtil.isBlank(visible)){
                        calendar.setVisible(Integer.valueOf(visible)) ;
                    }
                    
                    String syncEvents = request.getParameter("syncEvents");
                    if(!StringUtil.isBlank(syncEvents)){
                        calendar.setSyncEvents(Integer.valueOf(syncEvents)) ;
                    }
                    
                    String user = request.getParameter("user");
                    if(!StringUtil.isBlank(user)){
                        calendar.setUser(Long.valueOf(user)) ;
                    }
                    
                    String createTime = request.getParameter("createTime");
                    if(!StringUtil.isBlank(createTime)){
                        calendar.setCreateTime(Timestamp.valueOf(createTime)) ;
                    }
                    
                    String updateTime = request.getParameter("updateTime");
                    if(!StringUtil.isBlank(updateTime)){
                        calendar.setUpdateTime(Timestamp.valueOf(updateTime)) ;
                    }
                    */
                    String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            calendar.setId(Long.valueOf(id));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            calendar.setName(name);
        }
        String calendarDisplayName = request.getParameter("calendarDisplayName");
        if(!StringUtil.isBlank(calendarDisplayName)){
            calendar.setCalendarDisplayName(calendarDisplayName);
        }
        String visible = request.getParameter("visible");
        if(!StringUtil.isBlank(visible)){
            calendar.setVisible(Integer.valueOf(visible));
        }
        String syncEvents = request.getParameter("syncEvents");
        if(!StringUtil.isBlank(syncEvents)){
            calendar.setSyncEvents(Integer.valueOf(syncEvents));
        }
        String user = request.getParameter("user");
        if(!StringUtil.isBlank(user)){
            calendar.setUser(Long.valueOf(user));
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                calendar.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                calendar.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                calendar.setUpdateTime(Timestamp.valueOf(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                calendar.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

                    //valid
                    ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("name", name, "日历的名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("calendarDisplayName", calendarDisplayName, "该日历显示给用户时使用的名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("visible", visible, "表示是否选择显示该日历的布尔值",  new Rule[]{new Digits(1,0),new NotEmpty()});
        vu.add("syncEvents", syncEvents, "表示是否应同步日历并将其事件存储在设备上",  new Rule[]{new Digits(1,0),new NotEmpty()});
        vu.add("user", user, "备注",  new Rule[]{new Digits(11,0)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

                    return calendarService.save(calendar);

                }

    /**
     * 说明:删除Calendar信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-10-15 15:47:24
     */
     @API( summary="根据id删除单个日历表信息",
        description = "根据id删除单个日历表信息",
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
        calendarService.delete(id);
        return this.getResult(SUCC);
    }

     /**
         * 说明:删除Calendar信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2018-10-15 15:47:24
         */
         @API( summary="根据id删除单个日历表信息",
            description = "根据id删除单个日历表信息",
            parameters={
             @Param(name="id" , description="编号",in=InType.path,dataType= DataType.LONG,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable Long id,HttpServletRequest request) {
            calendarService.delete(id);
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
       return  calendarService.multilDelete(idAry);
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
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String calendarDisplayName = request.getParameter("calendarDisplayName");
        if(!StringUtil.isBlank(calendarDisplayName)){
            params.put("calendarDisplayName",calendarDisplayName);
        }
        String calendarDisplayNameLike = request.getParameter("calendarDisplayNameLike");
        if(!StringUtil.isBlank(calendarDisplayNameLike)){
            params.put("calendarDisplayNameLike",calendarDisplayNameLike);
        }
        String visible = request.getParameter("visible");
        if(!StringUtil.isBlank(visible)){
            params.put("visible",visible);
        }
        String syncEvents = request.getParameter("syncEvents");
        if(!StringUtil.isBlank(syncEvents)){
            params.put("syncEvents",syncEvents);
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
        List<Calendar> list =calendarService.listByParams(params);
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
        colTitle.put("name", "日历的名称");
        colTitle.put("calendarDisplayName", "该日历显示给用户时使用的名称");
        colTitle.put("visible", "表示是否选择显示该日历的布尔值");
        colTitle.put("syncEvents", "表示是否应同步日历并将其事件存储在设备上");
        colTitle.put("user", "备注");
        colTitle.put("createTime", "创建时间");
        colTitle.put("updateTime", "更新时间");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            Calendar sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("name",  list.get(i).getName());
            map.put("calendarDisplayName",  list.get(i).getCalendarDisplayName());
            map.put("visible",  list.get(i).getVisible());
            map.put("syncEvents",  list.get(i).getSyncEvents());
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
                        throw new BizException(041412312, "文件上传出错");
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

                        Calendar bean = getInfoFromMap(params);

                      //  if (count > 0) {

                      //      logger.warn("邮箱已经存在:" + email);
                       //     errorMsg.append("邮箱已经存在:" + email);
                         //   continue;

                       // }

                        try {
                            calendarService.save(bean);
                            success++;//成功数增加
                        } catch (Exception e) {

                            fail++;//失败数增加
                            logger.info("packageservice import conf ==> update fail ==>the telphone:" + email + "", e);
                            errorMsg.append("the telphone:" + email + " update fail;");
                        }

                    }
                    if (StringUtil.isNotBlank(errorMsg.toString()) && fail > 0) {
                        throw new BizException(2000016, "导入失败, 失败" + fail + "条。" + errorMsg.toString());
                    }
                    return this.getResult(3090182,"导入完成，成功导入" + success + "条，失败" + fail + "条。" + errorMsg.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("导入失败", e);
                    if (e instanceof org.apache.poi.poifs.filesystem.OfficeXmlFileException) {
                        throw new BizException(041412313, "导入的excel需为2003版本");
                    } else {
                        throw new BizException(041412313, e.getMessage());
                    }
                }


    }


      /**
         * 说明: 跳转到Calendar列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/CalendarList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/CalendarListMapper.html";
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
        return "/static/html/CalendarEdit.html";
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
        return "/static/html/CalendarView.html";
    }



    private Calendar getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       Calendar calendar =new  Calendar();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                calendar.setId(Long.valueOf(id));
        }
        String name = MapUtils.getString(bodyParam,"name");
        if(!StringUtil.isBlank(name)){
                calendar.setName(String.valueOf(name));
        }
        String calendarDisplayName = MapUtils.getString(bodyParam,"calendarDisplayName");
        if(!StringUtil.isBlank(calendarDisplayName)){
                calendar.setCalendarDisplayName(String.valueOf(calendarDisplayName));
        }
        String visible = MapUtils.getString(bodyParam,"visible");
        if(!StringUtil.isBlank(visible)){
                calendar.setVisible(Integer.valueOf(visible));
        }
        String syncEvents = MapUtils.getString(bodyParam,"syncEvents");
        if(!StringUtil.isBlank(syncEvents)){
                calendar.setSyncEvents(Integer.valueOf(syncEvents));
        }
        String user = MapUtils.getString(bodyParam,"user");
        if(!StringUtil.isBlank(user)){
                calendar.setUser(Long.valueOf(user));
        }
        String createTime = MapUtils.getString(bodyParam,"createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                calendar.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                calendar.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = MapUtils.getString(bodyParam,"updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                calendar.setUpdateTime(Timestamp.valueOf(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                calendar.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("name", name, "日历的名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("calendarDisplayName", calendarDisplayName, "该日历显示给用户时使用的名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("visible", visible, "表示是否选择显示该日历的布尔值",  new Rule[]{new Digits(1,0),new NotEmpty()});
        vu.add("syncEvents", syncEvents, "表示是否应同步日历并将其事件存储在设备上",  new Rule[]{new Digits(1,0),new NotEmpty()});
        vu.add("user", user, "备注",  new Rule[]{new Digits(11,0)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  calendar;
    }


      /**
                     * 说明:添加Calendar信息
                     * @param request
                     * @throws Exception
                     * @return ResultDTO
                     * @author dozen.zhang
                     * @date 2018-10-15 15:47:24
                     */
                    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                    @API( summary="添加单个日历表信息",
                        description = "添加单个日历表信息",
                        parameters={
                           @Param(name="id" , description="编号",in=InType.body,dataType = DataType.LONG,required = false),
                           @Param(name="name" , description="日历的名称",in=InType.body,dataType = DataType.STRING,required = true),
                           @Param(name="calendarDisplayName" , description="该日历显示给用户时使用的名称",in=InType.body,dataType = DataType.STRING,required = true),
                           @Param(name="visible" , description="表示是否选择显示该日历的布尔值",in=InType.body,dataType = DataType.INTEGER,required = true),
                           @Param(name="syncEvents" , description="表示是否应同步日历并将其事件存储在设备上",in=InType.body,dataType = DataType.INTEGER,required = true),
                           @Param(name="user" , description="备注",in=InType.body,dataType = DataType.LONG,required = false),
                           @Param(name="createTime" , description="创建时间",in=InType.body,dataType = DataType.DATE_TIME,required = false),
                           @Param(name="updateTime" , description="更新时间",in=InType.body,dataType = DataType.DATE_TIME,required = false),
                        })
                    @RequestMapping(value = "add",method = RequestMethod.POST)
                    @ResponseBody
                    public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
                        Calendar calendar =    getInfoFromMap(bodyParam);


                        return calendarService.save(calendar);

                    }


    /**
    * 说明:添加Calendar信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2018-10-15 15:47:24
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个日历表信息",
    description = "更新单个日历表信息",
    parameters={
        @Param(name="id" , description="编号",in=InType.body,dataType = DataType.LONG,required = false),
        @Param(name="name" , description="日历的名称",in=InType.body,dataType = DataType.STRING,required = true),
        @Param(name="calendarDisplayName" , description="该日历显示给用户时使用的名称",in=InType.body,dataType = DataType.STRING,required = true),
        @Param(name="visible" , description="表示是否选择显示该日历的布尔值",in=InType.body,dataType = DataType.INTEGER,required = true),
        @Param(name="syncEvents" , description="表示是否应同步日历并将其事件存储在设备上",in=InType.body,dataType = DataType.INTEGER,required = true),
        @Param(name="user" , description="备注",in=InType.body,dataType = DataType.LONG,required = false),
        @Param(name="createTime" , description="创建时间",in=InType.body,dataType = DataType.DATE_TIME,required = false),
        @Param(name="updateTime" , description="更新时间",in=InType.body,dataType = DataType.DATE_TIME,required = false),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    Calendar calendar =    getInfoFromMap(bodyParam);
    return calendarService.save(calendar);

    }
/**
     * 说明:ajax请求Calendar信息
     * @author dozen.zhang
     * @date 2018-10-15 15:47:24
     * @return String
     */
       @API(summary="日历表列表接口",
                 description="日历表列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="name" , description="日历的名称",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="calendarDisplayName" , description="该日历显示给用户时使用的名称",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="visible" , description="表示是否选择显示该日历的布尔值",in=InType.params,dataType = DataType.INTEGER,required =false),// true
                    @Param(name="syncEvents" , description="表示是否应同步日历并将其事件存储在设备上",in=InType.params,dataType = DataType.INTEGER,required =false),// true
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
        String name = MapUtils.getString(params,"name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = MapUtils.getString(params,"nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String calendarDisplayName = MapUtils.getString(params,"calendarDisplayName");
        if(!StringUtil.isBlank(calendarDisplayName)){
            params.put("calendarDisplayName",calendarDisplayName);
        }
        String calendarDisplayNameLike = MapUtils.getString(params,"calendarDisplayNameLike");
        if(!StringUtil.isBlank(calendarDisplayNameLike)){
            params.put("calendarDisplayNameLike",calendarDisplayNameLike);
        }
        String visible = MapUtils.getString(params,"visible");
        if(!StringUtil.isBlank(visible)){
            params.put("visible",visible);
        }
        String syncEvents = MapUtils.getString(params,"syncEvents");
        if(!StringUtil.isBlank(syncEvents)){
            params.put("syncEvents",syncEvents);
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
        List<Calendar> calendars = calendarService.listByParams4Page(params);
        return ResultUtil.getResult(calendars, page);
    }



       /**
         * 导出
         * @param request
         * @return
         * @author dozen.zhang
         */
        @API(summary="日历表列表导出接口",
          description="日历表列表导出接口",
          parameters={
          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
             @Param(name="id" , description="编号",in=InType.params,dataType = DataType.LONG,required =false),// false
             @Param(name="name" , description="日历的名称",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="calendarDisplayName" , description="该日历显示给用户时使用的名称",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="visible" , description="表示是否选择显示该日历的布尔值",in=InType.params,dataType = DataType.INTEGER,required =false),// true
             @Param(name="syncEvents" , description="表示是否应同步日历并将其事件存储在设备上",in=InType.params,dataType = DataType.INTEGER,required =false),// true
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
        String name = MapUtils.getString(params,"name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = MapUtils.getString(params,"nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String calendarDisplayName = MapUtils.getString(params,"calendarDisplayName");
        if(!StringUtil.isBlank(calendarDisplayName)){
            params.put("calendarDisplayName",calendarDisplayName);
        }
        String calendarDisplayNameLike = MapUtils.getString(params,"calendarDisplayNameLike");
        if(!StringUtil.isBlank(calendarDisplayNameLike)){
            params.put("calendarDisplayNameLike",calendarDisplayNameLike);
        }
        String visible = MapUtils.getString(params,"visible");
        if(!StringUtil.isBlank(visible)){
            params.put("visible",visible);
        }
        String syncEvents = MapUtils.getString(params,"syncEvents");
        if(!StringUtil.isBlank(syncEvents)){
            params.put("syncEvents",syncEvents);
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
             List<Calendar> list = calendarService.listByParams4Page(params);
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
            colTitle.put("name", "日历的名称");
            colTitle.put("calendarDisplayName", "该日历显示给用户时使用的名称");
            colTitle.put("visible", "表示是否选择显示该日历的布尔值");
            colTitle.put("syncEvents", "表示是否应同步日历并将其事件存储在设备上");
            colTitle.put("user", "备注");
            colTitle.put("createTime", "创建时间");
            colTitle.put("updateTime", "更新时间");
            List<Map> finalList = new ArrayList<Map>();
            for (int i = 0; i < list.size(); i++) {
                Calendar sm = list.get(i);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id",  list.get(i).getId());
                map.put("name",  list.get(i).getName());
                map.put("calendarDisplayName",  list.get(i).getCalendarDisplayName());
                map.put("visible",  list.get(i).getVisible());
                map.put("syncEvents",  list.get(i).getSyncEvents());
                map.put("user",  list.get(i).getUser());
                map.put("createTime",  list.get(i).getCreateTime());
                map.put("updateTime",  list.get(i).getUpdateTime());
                finalList.add(map);
            }
            try {
                if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                    return this.getResult(SUCC, SysConfig.PATH+"/xlstmp/"+randomName,"导出成功");
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
