/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-10-15 15:47:24
 * 文件说明: 
 */

package com.dozenx.web.module.calendar.instance.action;
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

@APIs(description = "日程实例")
@Controller
@RequestMapping("/rili/instance")
public class InstanceController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(InstanceController.class);
    /** 权限service **/
    @Autowired
    private InstanceService instanceService;
    


    /**
     * 说明:ajax请求Instance信息
     * @author dozen.zhang
     * @date 2018-10-15 15:47:24
     * @return String
     */
       @API(summary="日程实例列表接口",
                 description="日程实例列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",dataType = DataType.LONG,required =false),// false
                    @Param(name="begin" , description="实例的开始时间，以协调世界时毫秒数表示。",dataType = DataType.LONG,required =false),// false
                    @Param(name="end" , description="实例的结束时间，以协调世界时毫秒数表示。",dataType = DataType.LONG,required =false),// true
                    @Param(name="eventId" , description="该实例对应事件的 _ID。",dataType = DataType.LONG,required =false),// true
                    @Param(name="endDay" , description="与日历时区相应的实例儒略历结束日。",dataType = DataType.DATE,required =false),// false
                    @Param(name="endMinute" , description="事件的名称",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="startDay" , description="与日历时区相应的实例儒略历开始日。",dataType = DataType.DATE,required =false),// true
                    @Param(name="startMinute" , description="从日历时区午夜开始计算的实例开始时间（分钟）。",dataType = DataType.INTEGER,required =false),// true
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
        String begin = request.getParameter("begin");
        if(!StringUtil.isBlank(begin)){
            params.put("begin",begin);
        }
        String end = request.getParameter("end");
        if(!StringUtil.isBlank(end)){
            params.put("end",end);
        }
        String eventId = request.getParameter("eventId");
        if(!StringUtil.isBlank(eventId)){
            params.put("eventId",eventId);
        }
        String endDay = request.getParameter("endDay");
        if(!StringUtil.isBlank(endDay)){
            if(StringUtil.checkNumeric(endDay)){
                params.put("endDay",endDay);
            }else if(StringUtil.checkDateStr(endDay, "yyyy-MM-dd")){
                params.put("endDay",DateUtil.parseToDate(endDay, "yyyy-MM-dd"));
            }
        }
        String endDayBegin = request.getParameter("endDayBegin");
        if(!StringUtil.isBlank(endDayBegin)){
            if(StringUtil.checkNumeric(endDayBegin)){
                params.put("endDayBegin",endDayBegin);
            }else if(StringUtil.checkDateStr(endDayBegin, "yyyy-MM-dd")){
                params.put("endDayBegin",DateUtil.parseToDate(endDayBegin, "yyyy-MM-dd"));
            }
        }
        String endDayEnd = request.getParameter("endDayEnd");
        if(!StringUtil.isBlank(endDayEnd)){
            if(StringUtil.checkNumeric(endDayEnd)){
                params.put("endDayEnd",endDayEnd);
            }else if(StringUtil.checkDateStr(endDayEnd, "yyyy-MM-dd")){
                params.put("endDayEnd",DateUtil.parseToDate(endDayEnd, "yyyy-MM-dd"));
            }
        }
        String endMinute = request.getParameter("endMinute");
        if(!StringUtil.isBlank(endMinute)){
            params.put("endMinute",endMinute);
        }
        String startDay = request.getParameter("startDay");
        if(!StringUtil.isBlank(startDay)){
            if(StringUtil.checkNumeric(startDay)){
                params.put("startDay",startDay);
            }else if(StringUtil.checkDateStr(startDay, "yyyy-MM-dd")){
                params.put("startDay",DateUtil.parseToDate(startDay, "yyyy-MM-dd"));
            }
        }
        String startDayBegin = request.getParameter("startDayBegin");
        if(!StringUtil.isBlank(startDayBegin)){
            if(StringUtil.checkNumeric(startDayBegin)){
                params.put("startDayBegin",startDayBegin);
            }else if(StringUtil.checkDateStr(startDayBegin, "yyyy-MM-dd")){
                params.put("startDayBegin",DateUtil.parseToDate(startDayBegin, "yyyy-MM-dd"));
            }
        }
        String startDayEnd = request.getParameter("startDayEnd");
        if(!StringUtil.isBlank(startDayEnd)){
            if(StringUtil.checkNumeric(startDayEnd)){
                params.put("startDayEnd",startDayEnd);
            }else if(StringUtil.checkDateStr(startDayEnd, "yyyy-MM-dd")){
                params.put("startDayEnd",DateUtil.parseToDate(startDayEnd, "yyyy-MM-dd"));
            }
        }
        String startMinute = request.getParameter("startMinute");
        if(!StringUtil.isBlank(startMinute)){
            params.put("startMinute",startMinute);
        }

        params.put("page",page);
        List<Instance> instances = instanceService.listByParams4Page(params);
        return ResultUtil.getResult(instances, page);
    }
    
   /**
    * 说明:ajax请求Instance信息 无分页版本
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
        String begin = request.getParameter("begin");
        if(!StringUtil.isBlank(begin)){
            params.put("begin",begin);
        }
        String end = request.getParameter("end");
        if(!StringUtil.isBlank(end)){
            params.put("end",end);
        }
        String eventId = request.getParameter("eventId");
        if(!StringUtil.isBlank(eventId)){
            params.put("eventId",eventId);
        }
        String endDay = request.getParameter("endDay");
        if(!StringUtil.isBlank(endDay)){
            if(StringUtil.checkNumeric(endDay)){
                params.put("endDay",endDay);
            }else if(StringUtil.checkDateStr(endDay, "yyyy-MM-dd")){
                params.put("endDay",DateUtil.parseToDate(endDay, "yyyy-MM-dd"));
            }
        }
        String endDayBegin = request.getParameter("endDayBegin");
        if(!StringUtil.isBlank(endDayBegin)){
            if(StringUtil.checkNumeric(endDayBegin)){
                params.put("endDayBegin",endDayBegin);
            }else if(StringUtil.checkDateStr(endDayBegin, "yyyy-MM-dd")){
                params.put("endDayBegin",DateUtil.parseToDate(endDayBegin, "yyyy-MM-dd"));
            }
        }
        String endDayEnd = request.getParameter("endDayEnd");
        if(!StringUtil.isBlank(endDayEnd)){
            if(StringUtil.checkNumeric(endDayEnd)){
                params.put("endDayEnd",endDayEnd);
            }else if(StringUtil.checkDateStr(endDayEnd, "yyyy-MM-dd")){
                params.put("endDayEnd",DateUtil.parseToDate(endDayEnd, "yyyy-MM-dd"));
            }
        }
        String endMinute = request.getParameter("endMinute");
        if(!StringUtil.isBlank(endMinute)){
            params.put("endMinute",endMinute);
        }
        String startDay = request.getParameter("startDay");
        if(!StringUtil.isBlank(startDay)){
            if(StringUtil.checkNumeric(startDay)){
                params.put("startDay",startDay);
            }else if(StringUtil.checkDateStr(startDay, "yyyy-MM-dd")){
                params.put("startDay",DateUtil.parseToDate(startDay, "yyyy-MM-dd"));
            }
        }
        String startDayBegin = request.getParameter("startDayBegin");
        if(!StringUtil.isBlank(startDayBegin)){
            if(StringUtil.checkNumeric(startDayBegin)){
                params.put("startDayBegin",startDayBegin);
            }else if(StringUtil.checkDateStr(startDayBegin, "yyyy-MM-dd")){
                params.put("startDayBegin",DateUtil.parseToDate(startDayBegin, "yyyy-MM-dd"));
            }
        }
        String startDayEnd = request.getParameter("startDayEnd");
        if(!StringUtil.isBlank(startDayEnd)){
            if(StringUtil.checkNumeric(startDayEnd)){
                params.put("startDayEnd",startDayEnd);
            }else if(StringUtil.checkDateStr(startDayEnd, "yyyy-MM-dd")){
                params.put("startDayEnd",DateUtil.parseToDate(startDayEnd, "yyyy-MM-dd"));
            }
        }
        String startMinute = request.getParameter("startMinute");
        if(!StringUtil.isBlank(startMinute)){
            params.put("startMinute",startMinute);
        }

        List<Instance> instances = instanceService.listByParams(params);
        return ResultUtil.getDataResult(instances);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2018-10-15 15:47:24
    */
     @API( summary="根据id查询单个日程实例信息",
               description = "根据id查询单个日程实例信息",
               parameters={
                       @Param(name="id" , description="id",in=InType.path,dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(@PathVariable Long id,HttpServletRequest request) {
                     HashMap<String,Object> result =new HashMap</*String,Object*/>();
        if(id > 0){
            Instance bean = instanceService.selectByPrimaryKey(Long.valueOf(id));
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
      @API( summary="根据id查询单个日程实例信息",
               description = "根据id查询单个日程实例信息",
               parameters={
                       @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view.json",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(HttpServletRequest request) {
         String id = request.getParameter("id");
            Instance bean = instanceService.selectByPrimaryKey(Long.valueOf(id));
          //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
           // result.put("bean", bean);
            return this.getResult(bean);
        }


    /**
     * 说明:更新Instance信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-10-15 15:47:24
     */
      @API( summary="更新id更新单个日程实例信息",
        description = "更新id更新单个日程实例信息",
        parameters={
           @Param(name="id" , description="编号",type="LONG",required = false),
           @Param(name="begin" , description="实例的开始时间，以协调世界时毫秒数表示。",type="LONG",required = false),
           @Param(name="end" , description="实例的结束时间，以协调世界时毫秒数表示。",type="LONG",required = true),
           @Param(name="eventId" , description="该实例对应事件的 _ID。",type="LONG",required = true),
           @Param(name="endDay" , description="与日历时区相应的实例儒略历结束日。",type="DATE",required = false),
           @Param(name="endMinute" , description="事件的名称",type="INTEGER",required = false),
           @Param(name="startDay" , description="与日历时区相应的实例儒略历开始日。",type="DATE",required = true),
           @Param(name="startMinute" , description="从日历时区午夜开始计算的实例开始时间（分钟）。",type="INTEGER",required = true),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update.json",method = RequestMethod.PUT)///{id}
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Long id,
        Instance instance =new  Instance();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            instance.setId(Long.valueOf(id)) ;
        }
        
        String begin = request.getParameter("begin");
        if(!StringUtil.isBlank(begin)){
            instance.setBegin(Long.valueOf(begin)) ;
        }
        
        String end = request.getParameter("end");
        if(!StringUtil.isBlank(end)){
            instance.setEnd(Long.valueOf(end)) ;
        }
        
        String eventId = request.getParameter("eventId");
        if(!StringUtil.isBlank(eventId)){
            instance.setEventId(Long.valueOf(eventId)) ;
        }
        
        String endDay = request.getParameter("endDay");
        if(!StringUtil.isBlank(endDay)){
            instance.setEndDay(Date.valueOf(endDay)) ;
        }
        
        String endMinute = request.getParameter("endMinute");
        if(!StringUtil.isBlank(endMinute)){
            instance.setEndMinute(Integer.valueOf(endMinute)) ;
        }
        
        String startDay = request.getParameter("startDay");
        if(!StringUtil.isBlank(startDay)){
            instance.setStartDay(Date.valueOf(startDay)) ;
        }
        
        String startMinute = request.getParameter("startMinute");
        if(!StringUtil.isBlank(startMinute)){
            instance.setStartMinute(Integer.valueOf(startMinute)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            instance.setId(Long.valueOf(id));
        }
        String begin = request.getParameter("begin");
        if(!StringUtil.isBlank(begin)){
            instance.setBegin(Long.valueOf(begin));
        }
        String end = request.getParameter("end");
        if(!StringUtil.isBlank(end)){
            instance.setEnd(Long.valueOf(end));
        }
        String eventId = request.getParameter("eventId");
        if(!StringUtil.isBlank(eventId)){
            instance.setEventId(Long.valueOf(eventId));
        }
        String endDay = request.getParameter("endDay");
        if(!StringUtil.isBlank(endDay)){
            if(StringUtil.checkNumeric(endDay)){
                instance.setEndDay(new Date(endDay));
            }else if(StringUtil.checkDateStr(endDay, "yyyy-MM-dd")){
                instance.setEndDay(DateUtil.parseToDate(endDay, "yyyy-MM-dd"));
            }
        }
        String endMinute = request.getParameter("endMinute");
        if(!StringUtil.isBlank(endMinute)){
            instance.setEndMinute(Integer.valueOf(endMinute));
        }
        String startDay = request.getParameter("startDay");
        if(!StringUtil.isBlank(startDay)){
            if(StringUtil.checkNumeric(startDay)){
                instance.setStartDay(new Date(startDay));
            }else if(StringUtil.checkDateStr(startDay, "yyyy-MM-dd")){
                instance.setStartDay(DateUtil.parseToDate(startDay, "yyyy-MM-dd"));
            }
        }
        String startMinute = request.getParameter("startMinute");
        if(!StringUtil.isBlank(startMinute)){
            instance.setStartMinute(Integer.valueOf(startMinute));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
        vu.add("begin", begin, "实例的开始时间，以协调世界时毫秒数表示。",  new Rule[]{new Digits(15,0)});
        vu.add("end", end, "实例的结束时间，以协调世界时毫秒数表示。",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("eventId", eventId, "该实例对应事件的 _ID。",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("endDay", endDay, "与日历时区相应的实例儒略历结束日。",  new Rule[]{new DateValue("yyyy-MM-dd")});
        vu.add("endMinute", endMinute, "事件的名称",  new Rule[]{new Digits(5,0)});
        vu.add("startDay", startDay, "与日历时区相应的实例儒略历开始日。",  new Rule[]{new DateValue("yyyy-MM-dd"),new NotEmpty()});
        vu.add("startMinute", startMinute, "从日历时区午夜开始计算的实例开始时间（分钟）。",  new Rule[]{new Digits(5,0),new NotEmpty()});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return instanceService.save(instance);
       
    }


        /**
         * 说明:添加Instance信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2018-10-15 15:47:24
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个日程实例信息",
            description = "添加单个日程实例信息",
            parameters={
               @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
               @Param(name="begin" , description="实例的开始时间，以协调世界时毫秒数表示。",dataType = DataType.LONG,required = false),
               @Param(name="end" , description="实例的结束时间，以协调世界时毫秒数表示。",dataType = DataType.LONG,required = true),
               @Param(name="eventId" , description="该实例对应事件的 _ID。",dataType = DataType.LONG,required = true),
               @Param(name="endDay" , description="与日历时区相应的实例儒略历结束日。",dataType = DataType.DATE,required = false),
               @Param(name="endMinute" , description="事件的名称",dataType = DataType.INTEGER,required = false),
               @Param(name="startDay" , description="与日历时区相应的实例儒略历开始日。",dataType = DataType.DATE,required = true),
               @Param(name="startMinute" , description="从日历时区午夜开始计算的实例开始时间（分钟）。",dataType = DataType.INTEGER,required = true),
            })
        @RequestMapping(value = "add.json",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            Instance instance =new  Instance();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                instance.setId(Long.valueOf(id)) ;
            }
            
            String begin = request.getParameter("begin");
            if(!StringUtil.isBlank(begin)){
                instance.setBegin(Long.valueOf(begin)) ;
            }
            
            String end = request.getParameter("end");
            if(!StringUtil.isBlank(end)){
                instance.setEnd(Long.valueOf(end)) ;
            }
            
            String eventId = request.getParameter("eventId");
            if(!StringUtil.isBlank(eventId)){
                instance.setEventId(Long.valueOf(eventId)) ;
            }
            
            String endDay = request.getParameter("endDay");
            if(!StringUtil.isBlank(endDay)){
                instance.setEndDay(Date.valueOf(endDay)) ;
            }
            
            String endMinute = request.getParameter("endMinute");
            if(!StringUtil.isBlank(endMinute)){
                instance.setEndMinute(Integer.valueOf(endMinute)) ;
            }
            
            String startDay = request.getParameter("startDay");
            if(!StringUtil.isBlank(startDay)){
                instance.setStartDay(Date.valueOf(startDay)) ;
            }
            
            String startMinute = request.getParameter("startMinute");
            if(!StringUtil.isBlank(startMinute)){
                instance.setStartMinute(Integer.valueOf(startMinute)) ;
            }
            */
            String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            instance.setId(Long.valueOf(id));
        }
        String begin = request.getParameter("begin");
        if(!StringUtil.isBlank(begin)){
            instance.setBegin(Long.valueOf(begin));
        }
        String end = request.getParameter("end");
        if(!StringUtil.isBlank(end)){
            instance.setEnd(Long.valueOf(end));
        }
        String eventId = request.getParameter("eventId");
        if(!StringUtil.isBlank(eventId)){
            instance.setEventId(Long.valueOf(eventId));
        }
        String endDay = request.getParameter("endDay");
        if(!StringUtil.isBlank(endDay)){
            if(StringUtil.checkNumeric(endDay)){
                instance.setEndDay(new Date(endDay));
            }else if(StringUtil.checkDateStr(endDay, "yyyy-MM-dd")){
                instance.setEndDay(DateUtil.parseToDate(endDay, "yyyy-MM-dd"));
            }
        }
        String endMinute = request.getParameter("endMinute");
        if(!StringUtil.isBlank(endMinute)){
            instance.setEndMinute(Integer.valueOf(endMinute));
        }
        String startDay = request.getParameter("startDay");
        if(!StringUtil.isBlank(startDay)){
            if(StringUtil.checkNumeric(startDay)){
                instance.setStartDay(new Date(startDay));
            }else if(StringUtil.checkDateStr(startDay, "yyyy-MM-dd")){
                instance.setStartDay(DateUtil.parseToDate(startDay, "yyyy-MM-dd"));
            }
        }
        String startMinute = request.getParameter("startMinute");
        if(!StringUtil.isBlank(startMinute)){
            instance.setStartMinute(Integer.valueOf(startMinute));
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
        vu.add("begin", begin, "实例的开始时间，以协调世界时毫秒数表示。",  new Rule[]{new Digits(15,0)});
        vu.add("end", end, "实例的结束时间，以协调世界时毫秒数表示。",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("eventId", eventId, "该实例对应事件的 _ID。",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("endDay", endDay, "与日历时区相应的实例儒略历结束日。",  new Rule[]{new DateValue("yyyy-MM-dd")});
        vu.add("endMinute", endMinute, "事件的名称",  new Rule[]{new Digits(5,0)});
        vu.add("startDay", startDay, "与日历时区相应的实例儒略历开始日。",  new Rule[]{new DateValue("yyyy-MM-dd"),new NotEmpty()});
        vu.add("startMinute", startMinute, "从日历时区午夜开始计算的实例开始时间（分钟）。",  new Rule[]{new Digits(5,0),new NotEmpty()});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

            return instanceService.save(instance);

        }


          /**
                 * 说明:添加Instance信息
                 * @param request
                 * @throws Exception
                 * @return ResultDTO
                 * @author dozen.zhang
                 * @date 2018-10-15 15:47:24
                 */
                // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                @API( summary="添加单个日程实例信息",
                    description = "添加单个日程实例信息",
                    parameters={
                       @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
                       @Param(name="begin" , description="实例的开始时间，以协调世界时毫秒数表示。",dataType = DataType.LONG,required = false),
                       @Param(name="end" , description="实例的结束时间，以协调世界时毫秒数表示。",dataType = DataType.LONG,required = true),
                       @Param(name="eventId" , description="该实例对应事件的 _ID。",dataType = DataType.LONG,required = true),
                       @Param(name="endDay" , description="与日历时区相应的实例儒略历结束日。",dataType = DataType.DATE,required = false),
                       @Param(name="endMinute" , description="事件的名称",dataType = DataType.INTEGER,required = false),
                       @Param(name="startDay" , description="与日历时区相应的实例儒略历开始日。",dataType = DataType.DATE,required = true),
                       @Param(name="startMinute" , description="从日历时区午夜开始计算的实例开始时间（分钟）。",dataType = DataType.INTEGER,required = true),
                    })
                @RequestMapping(value = "save.json",method = RequestMethod.POST)
                @ResponseBody
                public ResultDTO save(HttpServletRequest request) throws Exception {
                    Instance instance =new  Instance();
                    /*
                    String id = request.getParameter("id");
                    if(!StringUtil.isBlank(id)){
                        instance.setId(Long.valueOf(id)) ;
                    }
                    
                    String begin = request.getParameter("begin");
                    if(!StringUtil.isBlank(begin)){
                        instance.setBegin(Long.valueOf(begin)) ;
                    }
                    
                    String end = request.getParameter("end");
                    if(!StringUtil.isBlank(end)){
                        instance.setEnd(Long.valueOf(end)) ;
                    }
                    
                    String eventId = request.getParameter("eventId");
                    if(!StringUtil.isBlank(eventId)){
                        instance.setEventId(Long.valueOf(eventId)) ;
                    }
                    
                    String endDay = request.getParameter("endDay");
                    if(!StringUtil.isBlank(endDay)){
                        instance.setEndDay(Date.valueOf(endDay)) ;
                    }
                    
                    String endMinute = request.getParameter("endMinute");
                    if(!StringUtil.isBlank(endMinute)){
                        instance.setEndMinute(Integer.valueOf(endMinute)) ;
                    }
                    
                    String startDay = request.getParameter("startDay");
                    if(!StringUtil.isBlank(startDay)){
                        instance.setStartDay(Date.valueOf(startDay)) ;
                    }
                    
                    String startMinute = request.getParameter("startMinute");
                    if(!StringUtil.isBlank(startMinute)){
                        instance.setStartMinute(Integer.valueOf(startMinute)) ;
                    }
                    */
                    String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            instance.setId(Long.valueOf(id));
        }
        String begin = request.getParameter("begin");
        if(!StringUtil.isBlank(begin)){
            instance.setBegin(Long.valueOf(begin));
        }
        String end = request.getParameter("end");
        if(!StringUtil.isBlank(end)){
            instance.setEnd(Long.valueOf(end));
        }
        String eventId = request.getParameter("eventId");
        if(!StringUtil.isBlank(eventId)){
            instance.setEventId(Long.valueOf(eventId));
        }
        String endDay = request.getParameter("endDay");
        if(!StringUtil.isBlank(endDay)){
            if(StringUtil.checkNumeric(endDay)){
                instance.setEndDay(new Date(endDay));
            }else if(StringUtil.checkDateStr(endDay, "yyyy-MM-dd")){
                instance.setEndDay(DateUtil.parseToDate(endDay, "yyyy-MM-dd"));
            }
        }
        String endMinute = request.getParameter("endMinute");
        if(!StringUtil.isBlank(endMinute)){
            instance.setEndMinute(Integer.valueOf(endMinute));
        }
        String startDay = request.getParameter("startDay");
        if(!StringUtil.isBlank(startDay)){
            if(StringUtil.checkNumeric(startDay)){
                instance.setStartDay(new Date(startDay));
            }else if(StringUtil.checkDateStr(startDay, "yyyy-MM-dd")){
                instance.setStartDay(DateUtil.parseToDate(startDay, "yyyy-MM-dd"));
            }
        }
        String startMinute = request.getParameter("startMinute");
        if(!StringUtil.isBlank(startMinute)){
            instance.setStartMinute(Integer.valueOf(startMinute));
        }

                    //valid
                    ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
        vu.add("begin", begin, "实例的开始时间，以协调世界时毫秒数表示。",  new Rule[]{new Digits(15,0)});
        vu.add("end", end, "实例的结束时间，以协调世界时毫秒数表示。",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("eventId", eventId, "该实例对应事件的 _ID。",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("endDay", endDay, "与日历时区相应的实例儒略历结束日。",  new Rule[]{new DateValue("yyyy-MM-dd")});
        vu.add("endMinute", endMinute, "事件的名称",  new Rule[]{new Digits(5,0)});
        vu.add("startDay", startDay, "与日历时区相应的实例儒略历开始日。",  new Rule[]{new DateValue("yyyy-MM-dd"),new NotEmpty()});
        vu.add("startMinute", startMinute, "从日历时区午夜开始计算的实例开始时间（分钟）。",  new Rule[]{new Digits(5,0),new NotEmpty()});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

                    return instanceService.save(instance);

                }

    /**
     * 说明:删除Instance信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-10-15 15:47:24
     */
     @API( summary="根据id删除单个日程实例信息",
        description = "根据id删除单个日程实例信息",
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
        instanceService.delete(id);
        return this.getResult(SUCC);
    }

     /**
         * 说明:删除Instance信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2018-10-15 15:47:24
         */
         @API( summary="根据id删除单个日程实例信息",
            description = "根据id删除单个日程实例信息",
            parameters={
             @Param(name="id" , description="编号",in=InType.path,dataType= DataType.LONG,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable Long id,HttpServletRequest request) {
            instanceService.delete(id);
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
       return  instanceService.multilDelete(idAry);
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
        String begin = request.getParameter("begin");
        if(!StringUtil.isBlank(begin)){
            params.put("begin",begin);
        }
        String end = request.getParameter("end");
        if(!StringUtil.isBlank(end)){
            params.put("end",end);
        }
        String eventId = request.getParameter("eventId");
        if(!StringUtil.isBlank(eventId)){
            params.put("eventId",eventId);
        }
        String endDay = request.getParameter("endDay");
        if(!StringUtil.isBlank(endDay)){
            if(StringUtil.checkNumeric(endDay)){
                params.put("endDay",endDay);
            }else if(StringUtil.checkDateStr(endDay, "yyyy-MM-dd")){
                params.put("endDay",DateUtil.parseToDate(endDay, "yyyy-MM-dd"));
            }
        }
        String endDayBegin = request.getParameter("endDayBegin");
        if(!StringUtil.isBlank(endDayBegin)){
            if(StringUtil.checkNumeric(endDayBegin)){
                params.put("endDayBegin",endDayBegin);
            }else if(StringUtil.checkDateStr(endDayBegin, "yyyy-MM-dd")){
                params.put("endDayBegin",DateUtil.parseToDate(endDayBegin, "yyyy-MM-dd"));
            }
        }
        String endDayEnd = request.getParameter("endDayEnd");
        if(!StringUtil.isBlank(endDayEnd)){
            if(StringUtil.checkNumeric(endDayEnd)){
                params.put("endDayEnd",endDayEnd);
            }else if(StringUtil.checkDateStr(endDayEnd, "yyyy-MM-dd")){
                params.put("endDayEnd",DateUtil.parseToDate(endDayEnd, "yyyy-MM-dd"));
            }
        }
        String endMinute = request.getParameter("endMinute");
        if(!StringUtil.isBlank(endMinute)){
            params.put("endMinute",endMinute);
        }
        String startDay = request.getParameter("startDay");
        if(!StringUtil.isBlank(startDay)){
            if(StringUtil.checkNumeric(startDay)){
                params.put("startDay",startDay);
            }else if(StringUtil.checkDateStr(startDay, "yyyy-MM-dd")){
                params.put("startDay",DateUtil.parseToDate(startDay, "yyyy-MM-dd"));
            }
        }
        String startDayBegin = request.getParameter("startDayBegin");
        if(!StringUtil.isBlank(startDayBegin)){
            if(StringUtil.checkNumeric(startDayBegin)){
                params.put("startDayBegin",startDayBegin);
            }else if(StringUtil.checkDateStr(startDayBegin, "yyyy-MM-dd")){
                params.put("startDayBegin",DateUtil.parseToDate(startDayBegin, "yyyy-MM-dd"));
            }
        }
        String startDayEnd = request.getParameter("startDayEnd");
        if(!StringUtil.isBlank(startDayEnd)){
            if(StringUtil.checkNumeric(startDayEnd)){
                params.put("startDayEnd",startDayEnd);
            }else if(StringUtil.checkDateStr(startDayEnd, "yyyy-MM-dd")){
                params.put("startDayEnd",DateUtil.parseToDate(startDayEnd, "yyyy-MM-dd"));
            }
        }
        String startMinute = request.getParameter("startMinute");
        if(!StringUtil.isBlank(startMinute)){
            params.put("startMinute",startMinute);
        }

        // 查询list集合
        List<Instance> list =instanceService.listByParams(params);
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
        colTitle.put("begin", "实例的开始时间，以协调世界时毫秒数表示。");
        colTitle.put("end", "实例的结束时间，以协调世界时毫秒数表示。");
        colTitle.put("eventId", "该实例对应事件的 _ID。");
        colTitle.put("endDay", "与日历时区相应的实例儒略历结束日。");
        colTitle.put("endMinute", "事件的名称");
        colTitle.put("startDay", "与日历时区相应的实例儒略历开始日。");
        colTitle.put("startMinute", "从日历时区午夜开始计算的实例开始时间（分钟）。");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            Instance sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("begin",  list.get(i).getBegin());
            map.put("end",  list.get(i).getEnd());
            map.put("eventId",  list.get(i).getEventId());
            map.put("endDay",  list.get(i).getEndDay());
            map.put("endMinute",  list.get(i).getEndMinute());
            map.put("startDay",  list.get(i).getStartDay());
            map.put("startMinute",  list.get(i).getStartMinute());
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

                        Instance bean = getInfoFromMap(params);

                      //  if (count > 0) {

                      //      logger.warn("邮箱已经存在:" + email);
                       //     errorMsg.append("邮箱已经存在:" + email);
                         //   continue;

                       // }

                        try {
                            instanceService.save(bean);
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
         * 说明: 跳转到Instance列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/InstanceList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/InstanceListMapper.html";
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
        return "/static/html/InstanceEdit.html";
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
        return "/static/html/InstanceView.html";
    }



    private Instance getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       Instance instance =new  Instance();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                instance.setId(Long.valueOf(id));
        }
        String begin = MapUtils.getString(bodyParam,"begin");
        if(!StringUtil.isBlank(begin)){
                instance.setBegin(Long.valueOf(begin));
        }
        String end = MapUtils.getString(bodyParam,"end");
        if(!StringUtil.isBlank(end)){
                instance.setEnd(Long.valueOf(end));
        }
        String eventId = MapUtils.getString(bodyParam,"eventId");
        if(!StringUtil.isBlank(eventId)){
                instance.setEventId(Long.valueOf(eventId));
        }
        String endDay = MapUtils.getString(bodyParam,"endDay");
        if(!StringUtil.isBlank(endDay)){
            if(StringUtil.checkNumeric(endDay)){
                instance.setEndDay(new Date(endDay));
            }else if(StringUtil.checkDateStr(endDay, "yyyy-MM-dd")){
                instance.setEndDay(DateUtil.parseToDate(endDay, "yyyy-MM-dd"));
            }
        }
        String endMinute = MapUtils.getString(bodyParam,"endMinute");
        if(!StringUtil.isBlank(endMinute)){
                instance.setEndMinute(Integer.valueOf(endMinute));
        }
        String startDay = MapUtils.getString(bodyParam,"startDay");
        if(!StringUtil.isBlank(startDay)){
            if(StringUtil.checkNumeric(startDay)){
                instance.setStartDay(new Date(startDay));
            }else if(StringUtil.checkDateStr(startDay, "yyyy-MM-dd")){
                instance.setStartDay(DateUtil.parseToDate(startDay, "yyyy-MM-dd"));
            }
        }
        String startMinute = MapUtils.getString(bodyParam,"startMinute");
        if(!StringUtil.isBlank(startMinute)){
                instance.setStartMinute(Integer.valueOf(startMinute));
        }

        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
        vu.add("begin", begin, "实例的开始时间，以协调世界时毫秒数表示。",  new Rule[]{new Digits(15,0)});
        vu.add("end", end, "实例的结束时间，以协调世界时毫秒数表示。",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("eventId", eventId, "该实例对应事件的 _ID。",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("endDay", endDay, "与日历时区相应的实例儒略历结束日。",  new Rule[]{new DateValue("yyyy-MM-dd")});
        vu.add("endMinute", endMinute, "事件的名称",  new Rule[]{new Digits(5,0)});
        vu.add("startDay", startDay, "与日历时区相应的实例儒略历开始日。",  new Rule[]{new DateValue("yyyy-MM-dd"),new NotEmpty()});
        vu.add("startMinute", startMinute, "从日历时区午夜开始计算的实例开始时间（分钟）。",  new Rule[]{new Digits(5,0),new NotEmpty()});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  instance;
    }


      /**
                     * 说明:添加Instance信息
                     * @param request
                     * @throws Exception
                     * @return ResultDTO
                     * @author dozen.zhang
                     * @date 2018-10-15 15:47:24
                     */
                    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                    @API( summary="添加单个日程实例信息",
                        description = "添加单个日程实例信息",
                        parameters={
                           @Param(name="id" , description="编号",in=InType.body,dataType = DataType.LONG,required = false),
                           @Param(name="begin" , description="实例的开始时间，以协调世界时毫秒数表示。",in=InType.body,dataType = DataType.LONG,required = false),
                           @Param(name="end" , description="实例的结束时间，以协调世界时毫秒数表示。",in=InType.body,dataType = DataType.LONG,required = true),
                           @Param(name="eventId" , description="该实例对应事件的 _ID。",in=InType.body,dataType = DataType.LONG,required = true),
                           @Param(name="endDay" , description="与日历时区相应的实例儒略历结束日。",in=InType.body,dataType = DataType.DATE,required = false),
                           @Param(name="endMinute" , description="事件的名称",in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="startDay" , description="与日历时区相应的实例儒略历开始日。",in=InType.body,dataType = DataType.DATE,required = true),
                           @Param(name="startMinute" , description="从日历时区午夜开始计算的实例开始时间（分钟）。",in=InType.body,dataType = DataType.INTEGER,required = true),
                        })
                    @RequestMapping(value = "add",method = RequestMethod.POST)
                    @ResponseBody
                    public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
                        Instance instance =    getInfoFromMap(bodyParam);


                        return instanceService.save(instance);

                    }


    /**
    * 说明:添加Instance信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2018-10-15 15:47:24
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个日程实例信息",
    description = "更新单个日程实例信息",
    parameters={
        @Param(name="id" , description="编号",in=InType.body,dataType = DataType.LONG,required = false),
        @Param(name="begin" , description="实例的开始时间，以协调世界时毫秒数表示。",in=InType.body,dataType = DataType.LONG,required = false),
        @Param(name="end" , description="实例的结束时间，以协调世界时毫秒数表示。",in=InType.body,dataType = DataType.LONG,required = true),
        @Param(name="eventId" , description="该实例对应事件的 _ID。",in=InType.body,dataType = DataType.LONG,required = true),
        @Param(name="endDay" , description="与日历时区相应的实例儒略历结束日。",in=InType.body,dataType = DataType.DATE,required = false),
        @Param(name="endMinute" , description="事件的名称",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="startDay" , description="与日历时区相应的实例儒略历开始日。",in=InType.body,dataType = DataType.DATE,required = true),
        @Param(name="startMinute" , description="从日历时区午夜开始计算的实例开始时间（分钟）。",in=InType.body,dataType = DataType.INTEGER,required = true),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    Instance instance =    getInfoFromMap(bodyParam);
    return instanceService.save(instance);

    }
/**
     * 说明:ajax请求Instance信息
     * @author dozen.zhang
     * @date 2018-10-15 15:47:24
     * @return String
     */
       @API(summary="日程实例列表接口",
                 description="日程实例列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页",in= InType.params, dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="begin" , description="实例的开始时间，以协调世界时毫秒数表示。",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="end" , description="实例的结束时间，以协调世界时毫秒数表示。",in=InType.params,dataType = DataType.LONG,required =false),// true
                    @Param(name="eventId" , description="该实例对应事件的 _ID。",in=InType.params,dataType = DataType.LONG,required =false),// true
                    @Param(name="endDay" , description="与日历时区相应的实例儒略历结束日。",in=InType.params,dataType = DataType.DATE,required =false),// false
                    @Param(name="endMinute" , description="事件的名称",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="startDay" , description="与日历时区相应的实例儒略历开始日。",in=InType.params,dataType = DataType.DATE,required =false),// true
                    @Param(name="startMinute" , description="从日历时区午夜开始计算的实例开始时间（分钟）。",in=InType.params,dataType = DataType.INTEGER,required =false),// true
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
        String begin = MapUtils.getString(params,"begin");
        if(!StringUtil.isBlank(begin)){
            params.put("begin",begin);
        }
        String end = MapUtils.getString(params,"end");
        if(!StringUtil.isBlank(end)){
            params.put("end",end);
        }
        String eventId = MapUtils.getString(params,"eventId");
        if(!StringUtil.isBlank(eventId)){
            params.put("eventId",eventId);
        }
        String endDay = MapUtils.getString(params,"endDay");
        if(!StringUtil.isBlank(endDay)){
            if(StringUtil.checkNumeric(endDay)){
                params.put("endDay",endDay);
            }else if(StringUtil.checkDateStr(endDay, "yyyy-MM-dd")){
                params.put("endDay",DateUtil.parseToDate(endDay, "yyyy-MM-dd"));
            }
        }
        String endDayBegin = MapUtils.getString(params,"endDayBegin");
        if(!StringUtil.isBlank(endDayBegin)){
            if(StringUtil.checkNumeric(endDayBegin)){
                params.put("endDayBegin",endDayBegin);
            }else if(StringUtil.checkDateStr(endDayBegin, "yyyy-MM-dd")){
                params.put("endDayBegin",DateUtil.parseToDate(endDayBegin, "yyyy-MM-dd"));
            }
        }
        String endDayEnd = MapUtils.getString(params,"endDayEnd");
        if(!StringUtil.isBlank(endDayEnd)){
            if(StringUtil.checkNumeric(endDayEnd)){
                params.put("endDayEnd",endDayEnd);
            }else if(StringUtil.checkDateStr(endDayEnd, "yyyy-MM-dd")){
                params.put("endDayEnd",DateUtil.parseToDate(endDayEnd, "yyyy-MM-dd"));
            }
        }
        String endMinute = MapUtils.getString(params,"endMinute");
        if(!StringUtil.isBlank(endMinute)){
            params.put("endMinute",endMinute);
        }
        String startDay = MapUtils.getString(params,"startDay");
        if(!StringUtil.isBlank(startDay)){
            if(StringUtil.checkNumeric(startDay)){
                params.put("startDay",startDay);
            }else if(StringUtil.checkDateStr(startDay, "yyyy-MM-dd")){
                params.put("startDay",DateUtil.parseToDate(startDay, "yyyy-MM-dd"));
            }
        }
        String startDayBegin = MapUtils.getString(params,"startDayBegin");
        if(!StringUtil.isBlank(startDayBegin)){
            if(StringUtil.checkNumeric(startDayBegin)){
                params.put("startDayBegin",startDayBegin);
            }else if(StringUtil.checkDateStr(startDayBegin, "yyyy-MM-dd")){
                params.put("startDayBegin",DateUtil.parseToDate(startDayBegin, "yyyy-MM-dd"));
            }
        }
        String startDayEnd = MapUtils.getString(params,"startDayEnd");
        if(!StringUtil.isBlank(startDayEnd)){
            if(StringUtil.checkNumeric(startDayEnd)){
                params.put("startDayEnd",startDayEnd);
            }else if(StringUtil.checkDateStr(startDayEnd, "yyyy-MM-dd")){
                params.put("startDayEnd",DateUtil.parseToDate(startDayEnd, "yyyy-MM-dd"));
            }
        }
        String startMinute = MapUtils.getString(params,"startMinute");
        if(!StringUtil.isBlank(startMinute)){
            params.put("startMinute",startMinute);
        }

        params.put("page",page);
        List<Instance> instances = instanceService.listByParams4Page(params);
        return ResultUtil.getResult(instances, page);
    }



       /**
         * 导出
         * @param request
         * @return
         * @author dozen.zhang
         */
        @API(summary="日程实例列表导出接口",
          description="日程实例列表导出接口",
          parameters={
          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
             @Param(name="id" , description="编号",in=InType.params,dataType = DataType.LONG,required =false),// false
             @Param(name="begin" , description="实例的开始时间，以协调世界时毫秒数表示。",in=InType.params,dataType = DataType.LONG,required =false),// false
             @Param(name="end" , description="实例的结束时间，以协调世界时毫秒数表示。",in=InType.params,dataType = DataType.LONG,required =false),// true
             @Param(name="eventId" , description="该实例对应事件的 _ID。",in=InType.params,dataType = DataType.LONG,required =false),// true
             @Param(name="endDay" , description="与日历时区相应的实例儒略历结束日。",in=InType.params,dataType = DataType.DATE,required =false),// false
             @Param(name="endMinute" , description="事件的名称",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="startDay" , description="与日历时区相应的实例儒略历开始日。",in=InType.params,dataType = DataType.DATE,required =false),// true
             @Param(name="startMinute" , description="从日历时区午夜开始计算的实例开始时间（分钟）。",in=InType.params,dataType = DataType.INTEGER,required =false),// true
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
        String begin = MapUtils.getString(params,"begin");
        if(!StringUtil.isBlank(begin)){
            params.put("begin",begin);
        }
        String end = MapUtils.getString(params,"end");
        if(!StringUtil.isBlank(end)){
            params.put("end",end);
        }
        String eventId = MapUtils.getString(params,"eventId");
        if(!StringUtil.isBlank(eventId)){
            params.put("eventId",eventId);
        }
        String endDay = MapUtils.getString(params,"endDay");
        if(!StringUtil.isBlank(endDay)){
            if(StringUtil.checkNumeric(endDay)){
                params.put("endDay",endDay);
            }else if(StringUtil.checkDateStr(endDay, "yyyy-MM-dd")){
                params.put("endDay",DateUtil.parseToDate(endDay, "yyyy-MM-dd"));
            }
        }
        String endDayBegin = MapUtils.getString(params,"endDayBegin");
        if(!StringUtil.isBlank(endDayBegin)){
            if(StringUtil.checkNumeric(endDayBegin)){
                params.put("endDayBegin",endDayBegin);
            }else if(StringUtil.checkDateStr(endDayBegin, "yyyy-MM-dd")){
                params.put("endDayBegin",DateUtil.parseToDate(endDayBegin, "yyyy-MM-dd"));
            }
        }
        String endDayEnd = MapUtils.getString(params,"endDayEnd");
        if(!StringUtil.isBlank(endDayEnd)){
            if(StringUtil.checkNumeric(endDayEnd)){
                params.put("endDayEnd",endDayEnd);
            }else if(StringUtil.checkDateStr(endDayEnd, "yyyy-MM-dd")){
                params.put("endDayEnd",DateUtil.parseToDate(endDayEnd, "yyyy-MM-dd"));
            }
        }
        String endMinute = MapUtils.getString(params,"endMinute");
        if(!StringUtil.isBlank(endMinute)){
            params.put("endMinute",endMinute);
        }
        String startDay = MapUtils.getString(params,"startDay");
        if(!StringUtil.isBlank(startDay)){
            if(StringUtil.checkNumeric(startDay)){
                params.put("startDay",startDay);
            }else if(StringUtil.checkDateStr(startDay, "yyyy-MM-dd")){
                params.put("startDay",DateUtil.parseToDate(startDay, "yyyy-MM-dd"));
            }
        }
        String startDayBegin = MapUtils.getString(params,"startDayBegin");
        if(!StringUtil.isBlank(startDayBegin)){
            if(StringUtil.checkNumeric(startDayBegin)){
                params.put("startDayBegin",startDayBegin);
            }else if(StringUtil.checkDateStr(startDayBegin, "yyyy-MM-dd")){
                params.put("startDayBegin",DateUtil.parseToDate(startDayBegin, "yyyy-MM-dd"));
            }
        }
        String startDayEnd = MapUtils.getString(params,"startDayEnd");
        if(!StringUtil.isBlank(startDayEnd)){
            if(StringUtil.checkNumeric(startDayEnd)){
                params.put("startDayEnd",startDayEnd);
            }else if(StringUtil.checkDateStr(startDayEnd, "yyyy-MM-dd")){
                params.put("startDayEnd",DateUtil.parseToDate(startDayEnd, "yyyy-MM-dd"));
            }
        }
        String startMinute = MapUtils.getString(params,"startMinute");
        if(!StringUtil.isBlank(startMinute)){
            params.put("startMinute",startMinute);
        }

             params.put("page",page);
             List<Instance> list = instanceService.listByParams4Page(params);
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
            colTitle.put("begin", "实例的开始时间，以协调世界时毫秒数表示。");
            colTitle.put("end", "实例的结束时间，以协调世界时毫秒数表示。");
            colTitle.put("eventId", "该实例对应事件的 _ID。");
            colTitle.put("endDay", "与日历时区相应的实例儒略历结束日。");
            colTitle.put("endMinute", "事件的名称");
            colTitle.put("startDay", "与日历时区相应的实例儒略历开始日。");
            colTitle.put("startMinute", "从日历时区午夜开始计算的实例开始时间（分钟）。");
            List<Map> finalList = new ArrayList<Map>();
            for (int i = 0; i < list.size(); i++) {
                Instance sm = list.get(i);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id",  list.get(i).getId());
                map.put("begin",  list.get(i).getBegin());
                map.put("end",  list.get(i).getEnd());
                map.put("eventId",  list.get(i).getEventId());
                map.put("endDay",  list.get(i).getEndDay());
                map.put("endMinute",  list.get(i).getEndMinute());
                map.put("startDay",  list.get(i).getStartDay());
                map.put("startMinute",  list.get(i).getStartMinute());
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
