/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-12-9 21:16:24
 * 文件说明: 
 */

package com.dozenx.web.module.calendar.holiday.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.cpj.swagger.annotation.*;
import com.dozenx.util.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.dozenx.web.util.ConfigUtil;
import org.slf4j.Logger;
import com.dozenx.core.exception.ParamException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import com.dozenx.web.module.calendar.holiday.service.HolidayService;
import com.dozenx.web.module.calendar.holiday.bean.Holiday;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.util.StringUtil;
import com.dozenx.web.util.RequestUtil;
import org.springframework.web.bind.annotation.*;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.util.DateUtil;
import org.springframework.web.multipart.MultipartFile;
import com.dozenx.core.Path.PathManager;
import com.dozenx.core.exception.BizException;
import java.nio.file.Files;
import com.dozenx.core.config.SysConfig;

@APIs(description = "节假日")
@Controller
@RequestMapping("/calendar/holiday")
public class HolidayController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(HolidayController.class);
    /** 权限service **/
    @Autowired
    private HolidayService holidayService;
    


    /**
     * 说明:ajax请求Holiday信息
     * @author dozen.zhang
     * @date 2018-12-9 21:16:24
     * @return String
     */
       @API(summary="节假日列表接口",
                 description="节假日列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",dataType = DataType.LONG,required =false),// false
                    @Param(name="date" , description="日期",dataType = DataType.DATE,required =false),// false
                    @Param(name="type" , description="是否是节假日",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="remark" , description="备注",dataType = DataType.STRING,required =false),// false
                    @Param(name="name" , description="节日名称",dataType = DataType.STRING,required =false),// false
                    @Param(name="nl" , description="农历",dataType = DataType.STRING,required =false),// false
                    @Param(name="yl" , description="阴历",dataType = DataType.STRING,required =false),// false
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
        String date = request.getParameter("date");
        if(!StringUtil.isBlank(date)){
            if(StringUtil.checkNumeric(date)){
                params.put("date",date);
            }else if(StringUtil.checkDateStr(date, "yyyy-MM-dd")){
                params.put("date",DateUtil.parseToDate(date, "yyyy-MM-dd"));
            }
        }
        String dateBegin = request.getParameter("dateBegin");
        if(!StringUtil.isBlank(dateBegin)){
            if(StringUtil.checkNumeric(dateBegin)){
                params.put("dateBegin",dateBegin);
            }else if(StringUtil.checkDateStr(dateBegin, "yyyy-MM-dd")){
                params.put("dateBegin",DateUtil.parseToDate(dateBegin, "yyyy-MM-dd"));
            }
        }
        String dateEnd = request.getParameter("dateEnd");
        if(!StringUtil.isBlank(dateEnd)){
            if(StringUtil.checkNumeric(dateEnd)){
                params.put("dateEnd",dateEnd);
            }else if(StringUtil.checkDateStr(dateEnd, "yyyy-MM-dd")){
                params.put("dateEnd",DateUtil.parseToDate(dateEnd, "yyyy-MM-dd"));
            }
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String nl = request.getParameter("nl");
        if(!StringUtil.isBlank(nl)){
            params.put("nl",nl);
        }
        String nlLike = request.getParameter("nlLike");
        if(!StringUtil.isBlank(nlLike)){
            params.put("nlLike",nlLike);
        }
        String yl = request.getParameter("yl");
        if(!StringUtil.isBlank(yl)){
            params.put("yl",yl);
        }
        String ylLike = request.getParameter("ylLike");
        if(!StringUtil.isBlank(ylLike)){
            params.put("ylLike",ylLike);
        }

        params.put("page",page);
        List<Holiday> holidays = holidayService.listByParams4Page(params);
        return ResultUtil.getResult(holidays, page);
    }
    
   /**
    * 说明:ajax请求Holiday信息 无分页版本
    * @return ResultDTO 返回结果
    * @author dozen.zhang
    * @date 2018-12-9 21:16:24
    */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
                HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String date = request.getParameter("date");
        if(!StringUtil.isBlank(date)){
            if(StringUtil.checkNumeric(date)){
                params.put("date",date);
            }else if(StringUtil.checkDateStr(date, "yyyy-MM-dd")){
                params.put("date",DateUtil.parseToDate(date, "yyyy-MM-dd"));
            }
        }
        String dateBegin = request.getParameter("dateBegin");
        if(!StringUtil.isBlank(dateBegin)){
            if(StringUtil.checkNumeric(dateBegin)){
                params.put("dateBegin",dateBegin);
            }else if(StringUtil.checkDateStr(dateBegin, "yyyy-MM-dd")){
                params.put("dateBegin",DateUtil.parseToDate(dateBegin, "yyyy-MM-dd"));
            }
        }
        String dateEnd = request.getParameter("dateEnd");
        if(!StringUtil.isBlank(dateEnd)){
            if(StringUtil.checkNumeric(dateEnd)){
                params.put("dateEnd",dateEnd);
            }else if(StringUtil.checkDateStr(dateEnd, "yyyy-MM-dd")){
                params.put("dateEnd",DateUtil.parseToDate(dateEnd, "yyyy-MM-dd"));
            }
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String nl = request.getParameter("nl");
        if(!StringUtil.isBlank(nl)){
            params.put("nl",nl);
        }
        String nlLike = request.getParameter("nlLike");
        if(!StringUtil.isBlank(nlLike)){
            params.put("nlLike",nlLike);
        }
        String yl = request.getParameter("yl");
        if(!StringUtil.isBlank(yl)){
            params.put("yl",yl);
        }
        String ylLike = request.getParameter("ylLike");
        if(!StringUtil.isBlank(ylLike)){
            params.put("ylLike",ylLike);
        }

        List<Holiday> holidays = holidayService.listByParams(params);
        return ResultUtil.getDataResult(holidays);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2018-12-9 21:16:24
    */
     @API( summary="根据id查询单个节假日信息",
               description = "根据id查询单个节假日信息",
               parameters={
                       @Param(name="id" , description="id",in=InType.path,dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(@PathVariable Long id,HttpServletRequest request) {
                     HashMap<String,Object> result =new HashMap</*String,Object*/>();
        if(id > 0){
            Holiday bean = holidayService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

        }



     /**
        * 说明:查看单条信息
        * @param request 发请求
        * @return String
        * @author dozen.zhang
        * @date 2018-12-9 21:16:24
        */
      @API( summary="根据id查询单个节假日信息",
               description = "根据id查询单个节假日信息",
               parameters={
                       @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view.json",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(HttpServletRequest request) {
         String id = request.getParameter("id");
            Holiday bean = holidayService.selectByPrimaryKey(Long.valueOf(id));
          //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
           // result.put("bean", bean);
            return this.getResult(bean);
        }


    /**
     * 说明:更新Holiday信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-12-9 21:16:24
     */
      @API( summary="更新id更新单个节假日信息",
        description = "更新id更新单个节假日信息",
        parameters={
           @Param(name="id" , description="编号",type="LONG",required = false),
           @Param(name="date" , description="日期",type="DATE",required = false),
           @Param(name="type" , description="是否是节假日",type="INTEGER",required = false),
           @Param(name="remark" , description="备注",type="STRING",required = false),
           @Param(name="name" , description="节日名称",type="STRING",required = false),
           @Param(name="nl" , description="农历",type="STRING",required = false),
           @Param(name="yl" , description="阴历",type="STRING",required = false),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update.json",method = RequestMethod.PUT)///{id}
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Long id,
        Holiday holiday =new  Holiday();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            holiday.setId(Long.valueOf(id)) ;
        }
        
        String date = request.getParameter("date");
        if(!StringUtil.isBlank(date)){
            holiday.setDate(Date.valueOf(date)) ;
        }
        
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            holiday.setType(Integer.valueOf(type)) ;
        }
        
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            holiday.setRemark(String.valueOf(remark)) ;
        }
        
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            holiday.setName(String.valueOf(name)) ;
        }
        
        String nl = request.getParameter("nl");
        if(!StringUtil.isBlank(nl)){
            holiday.setNl(String.valueOf(nl)) ;
        }
        
        String yl = request.getParameter("yl");
        if(!StringUtil.isBlank(yl)){
            holiday.setYl(String.valueOf(yl)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            holiday.setId(Long.valueOf(id));
        }
        String date = request.getParameter("date");
        if(!StringUtil.isBlank(date)){
            if(StringUtil.checkNumeric(date)){
                holiday.setDate(new Date(date));
            }else if(StringUtil.checkDateStr(date, "yyyy-MM-dd")){
                holiday.setDate(DateUtil.parseToDate(date, "yyyy-MM-dd"));
            }
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            holiday.setType(Integer.valueOf(type));
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            holiday.setRemark(remark);
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            holiday.setName(name);
        }
        String nl = request.getParameter("nl");
        if(!StringUtil.isBlank(nl)){
            holiday.setNl(nl);
        }
        String yl = request.getParameter("yl");
        if(!StringUtil.isBlank(yl)){
            holiday.setYl(yl);
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("date", date, "日期",  new Rule[]{new DateValue("yyyy-MM-dd")});
        vu.add("type", type, "是否是节假日",  new Rule[]{new Digits(1,0)});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(50)});
        vu.add("name", name, "节日名称",  new Rule[]{new Length(50)});
        vu.add("nl", nl, "农历",  new Rule[]{new Length(50)});
        vu.add("yl", yl, "阴历",  new Rule[]{new Length(50)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return holidayService.save(holiday);
       
    }


        /**
         * 说明:添加Holiday信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2018-12-9 21:16:24
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个节假日信息",
            description = "添加单个节假日信息",
            parameters={
               @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
               @Param(name="date" , description="日期",dataType = DataType.DATE,required = false),
               @Param(name="type" , description="是否是节假日",dataType = DataType.INTEGER,required = false),
               @Param(name="remark" , description="备注",dataType = DataType.STRING,required = false),
               @Param(name="name" , description="节日名称",dataType = DataType.STRING,required = false),
               @Param(name="nl" , description="农历",dataType = DataType.STRING,required = false),
               @Param(name="yl" , description="阴历",dataType = DataType.STRING,required = false),
            })
        @RequestMapping(value = "add.json",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            Holiday holiday =new  Holiday();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                holiday.setId(Long.valueOf(id)) ;
            }
            
            String date = request.getParameter("date");
            if(!StringUtil.isBlank(date)){
                holiday.setDate(Date.valueOf(date)) ;
            }
            
            String type = request.getParameter("type");
            if(!StringUtil.isBlank(type)){
                holiday.setType(Integer.valueOf(type)) ;
            }
            
            String remark = request.getParameter("remark");
            if(!StringUtil.isBlank(remark)){
                holiday.setRemark(String.valueOf(remark)) ;
            }
            
            String name = request.getParameter("name");
            if(!StringUtil.isBlank(name)){
                holiday.setName(String.valueOf(name)) ;
            }
            
            String nl = request.getParameter("nl");
            if(!StringUtil.isBlank(nl)){
                holiday.setNl(String.valueOf(nl)) ;
            }
            
            String yl = request.getParameter("yl");
            if(!StringUtil.isBlank(yl)){
                holiday.setYl(String.valueOf(yl)) ;
            }
            */
            String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            holiday.setId(Long.valueOf(id));
        }
        String date = request.getParameter("date");
        if(!StringUtil.isBlank(date)){
            if(StringUtil.checkNumeric(date)){
                holiday.setDate(new Date(date));
            }else if(StringUtil.checkDateStr(date, "yyyy-MM-dd")){
                holiday.setDate(DateUtil.parseToDate(date, "yyyy-MM-dd"));
            }
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            holiday.setType(Integer.valueOf(type));
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            holiday.setRemark(remark);
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            holiday.setName(name);
        }
        String nl = request.getParameter("nl");
        if(!StringUtil.isBlank(nl)){
            holiday.setNl(nl);
        }
        String yl = request.getParameter("yl");
        if(!StringUtil.isBlank(yl)){
            holiday.setYl(yl);
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("date", date, "日期",  new Rule[]{new DateValue("yyyy-MM-dd")});
        vu.add("type", type, "是否是节假日",  new Rule[]{new Digits(1,0)});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(50)});
        vu.add("name", name, "节日名称",  new Rule[]{new Length(50)});
        vu.add("nl", nl, "农历",  new Rule[]{new Length(50)});
        vu.add("yl", yl, "阴历",  new Rule[]{new Length(50)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

            return holidayService.save(holiday);

        }


          /**
                 * 说明:添加Holiday信息
                 * @param request
                 * @throws Exception
                 * @return ResultDTO
                 * @author dozen.zhang
                 * @date 2018-12-9 21:16:24
                 */
                // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                @API( summary="添加单个节假日信息",
                    description = "添加单个节假日信息",
                    parameters={
                       @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
                       @Param(name="date" , description="日期",dataType = DataType.DATE,required = false),
                       @Param(name="type" , description="是否是节假日",dataType = DataType.INTEGER,required = false),
                       @Param(name="remark" , description="备注",dataType = DataType.STRING,required = false),
                       @Param(name="name" , description="节日名称",dataType = DataType.STRING,required = false),
                       @Param(name="nl" , description="农历",dataType = DataType.STRING,required = false),
                       @Param(name="yl" , description="阴历",dataType = DataType.STRING,required = false),
                    })
                @RequestMapping(value = "save.json",method = RequestMethod.POST)
                @ResponseBody
                public ResultDTO save(HttpServletRequest request) throws Exception {
                    Holiday holiday =new  Holiday();
                    /*
                    String id = request.getParameter("id");
                    if(!StringUtil.isBlank(id)){
                        holiday.setId(Long.valueOf(id)) ;
                    }
                    
                    String date = request.getParameter("date");
                    if(!StringUtil.isBlank(date)){
                        holiday.setDate(Date.valueOf(date)) ;
                    }
                    
                    String type = request.getParameter("type");
                    if(!StringUtil.isBlank(type)){
                        holiday.setType(Integer.valueOf(type)) ;
                    }
                    
                    String remark = request.getParameter("remark");
                    if(!StringUtil.isBlank(remark)){
                        holiday.setRemark(String.valueOf(remark)) ;
                    }
                    
                    String name = request.getParameter("name");
                    if(!StringUtil.isBlank(name)){
                        holiday.setName(String.valueOf(name)) ;
                    }
                    
                    String nl = request.getParameter("nl");
                    if(!StringUtil.isBlank(nl)){
                        holiday.setNl(String.valueOf(nl)) ;
                    }
                    
                    String yl = request.getParameter("yl");
                    if(!StringUtil.isBlank(yl)){
                        holiday.setYl(String.valueOf(yl)) ;
                    }
                    */
                    String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            holiday.setId(Long.valueOf(id));
        }
        String date = request.getParameter("date");
        if(!StringUtil.isBlank(date)){
            if(StringUtil.checkNumeric(date)){
                holiday.setDate(new Date(date));
            }else if(StringUtil.checkDateStr(date, "yyyy-MM-dd")){
                holiday.setDate(DateUtil.parseToDate(date, "yyyy-MM-dd"));
            }
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            holiday.setType(Integer.valueOf(type));
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            holiday.setRemark(remark);
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            holiday.setName(name);
        }
        String nl = request.getParameter("nl");
        if(!StringUtil.isBlank(nl)){
            holiday.setNl(nl);
        }
        String yl = request.getParameter("yl");
        if(!StringUtil.isBlank(yl)){
            holiday.setYl(yl);
        }

                    //valid
                    ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("date", date, "日期",  new Rule[]{new DateValue("yyyy-MM-dd")});
        vu.add("type", type, "是否是节假日",  new Rule[]{new Digits(1,0)});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(50)});
        vu.add("name", name, "节日名称",  new Rule[]{new Length(50)});
        vu.add("nl", nl, "农历",  new Rule[]{new Length(50)});
        vu.add("yl", yl, "阴历",  new Rule[]{new Length(50)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

                    return holidayService.save(holiday);

                }

    /**
     * 说明:删除Holiday信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-12-9 21:16:24
     */
     @API( summary="根据id删除单个节假日信息",
        description = "根据id删除单个节假日信息",
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
        holidayService.delete(id);
        return this.getResult(SUCC);
    }

     /**
         * 说明:删除Holiday信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2018-12-9 21:16:24
         */
         @API( summary="根据id删除单个节假日信息",
            description = "根据id删除单个节假日信息",
            parameters={
             @Param(name="id" , description="编号",in=InType.path,dataType= DataType.LONG,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable Long id,HttpServletRequest request) {
            holidayService.delete(id);
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
       return  holidayService.multilDelete(idAry);
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
        String date = request.getParameter("date");
        if(!StringUtil.isBlank(date)){
            if(StringUtil.checkNumeric(date)){
                params.put("date",date);
            }else if(StringUtil.checkDateStr(date, "yyyy-MM-dd")){
                params.put("date",DateUtil.parseToDate(date, "yyyy-MM-dd"));
            }
        }
        String dateBegin = request.getParameter("dateBegin");
        if(!StringUtil.isBlank(dateBegin)){
            if(StringUtil.checkNumeric(dateBegin)){
                params.put("dateBegin",dateBegin);
            }else if(StringUtil.checkDateStr(dateBegin, "yyyy-MM-dd")){
                params.put("dateBegin",DateUtil.parseToDate(dateBegin, "yyyy-MM-dd"));
            }
        }
        String dateEnd = request.getParameter("dateEnd");
        if(!StringUtil.isBlank(dateEnd)){
            if(StringUtil.checkNumeric(dateEnd)){
                params.put("dateEnd",dateEnd);
            }else if(StringUtil.checkDateStr(dateEnd, "yyyy-MM-dd")){
                params.put("dateEnd",DateUtil.parseToDate(dateEnd, "yyyy-MM-dd"));
            }
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String nl = request.getParameter("nl");
        if(!StringUtil.isBlank(nl)){
            params.put("nl",nl);
        }
        String nlLike = request.getParameter("nlLike");
        if(!StringUtil.isBlank(nlLike)){
            params.put("nlLike",nlLike);
        }
        String yl = request.getParameter("yl");
        if(!StringUtil.isBlank(yl)){
            params.put("yl",yl);
        }
        String ylLike = request.getParameter("ylLike");
        if(!StringUtil.isBlank(ylLike)){
            params.put("ylLike",ylLike);
        }

        // 查询list集合
        List<Holiday> list =holidayService.listByParams(params);
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
        colTitle.put("date", "日期");
        colTitle.put("type", "是否是节假日");
        colTitle.put("remark", "备注");
        colTitle.put("name", "节日名称");
        colTitle.put("nl", "农历");
        colTitle.put("yl", "阴历");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            Holiday sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("date",  list.get(i).getDate());
            map.put("type",  list.get(i).getType());
            map.put("remark",  list.get(i).getRemark());
            map.put("name",  list.get(i).getName());
            map.put("nl",  list.get(i).getNl());
            map.put("yl",  list.get(i).getYl());
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
                        throw new BizException("E041412312", "文件上传出错");
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

                        Holiday bean = getInfoFromMap(params);

                      //  if (count > 0) {

                      //      logger.warn("邮箱已经存在:" + email);
                       //     errorMsg.append("邮箱已经存在:" + email);
                         //   continue;

                       // }

                        try {
                            holidayService.save(bean);
                            success++;//成功数增加
                        } catch (Exception e) {

                            fail++;//失败数增加
                            logger.info("packageservice import conf ==> update fail ==>the telphone:" + email + "", e);
                            errorMsg.append("the telphone:" + email + " update fail;");
                        }

                    }
                    if (StringUtil.isNotBlank(errorMsg.toString()) && fail > 0) {
                        throw new BizException("E2000016", "导入失败, 失败" + fail + "条。" + errorMsg.toString());
                    }
                    return this.getResult(3090182,"导入完成，成功导入" + success + "条，失败" + fail + "条。" + errorMsg.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("导入失败", e);
                    if (e instanceof org.apache.poi.poifs.filesystem.OfficeXmlFileException) {
                        throw new BizException("E041412313", "导入的excel需为2003版本");
                    } else {
                        throw new BizException("E041412313", e.getMessage());
                    }
                }


    }


      /**
         * 说明: 跳转到Holiday列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/HolidayList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/HolidayListMapper.html";
        }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-12-9 21:16:24
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/HolidayEdit.html";
    }
    /**
         * 说明:跳转查看页面
         * @param request 发请求
         * @return String
         * @author dozen.zhang
         * @date 2018-12-9 21:16:24
         */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/HolidayView.html";
    }



    private Holiday getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       Holiday holiday =new  Holiday();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                holiday.setId(Long.valueOf(id));
        }
        String date = MapUtils.getString(bodyParam,"date");
        if(!StringUtil.isBlank(date)){
            if(StringUtil.checkNumeric(date)){
                holiday.setDate(new Date(date));
            }else if(StringUtil.checkDateStr(date, "yyyy-MM-dd")){
                holiday.setDate(DateUtil.parseToDate(date, "yyyy-MM-dd"));
            }
        }
        String type = MapUtils.getString(bodyParam,"type");
        if(!StringUtil.isBlank(type)){
                holiday.setType(Integer.valueOf(type));
        }
        String remark = MapUtils.getString(bodyParam,"remark");
        if(!StringUtil.isBlank(remark)){
                holiday.setRemark(String.valueOf(remark));
        }
        String name = MapUtils.getString(bodyParam,"name");
        if(!StringUtil.isBlank(name)){
                holiday.setName(String.valueOf(name));
        }
        String nl = MapUtils.getString(bodyParam,"nl");
        if(!StringUtil.isBlank(nl)){
                holiday.setNl(String.valueOf(nl));
        }
        String yl = MapUtils.getString(bodyParam,"yl");
        if(!StringUtil.isBlank(yl)){
                holiday.setYl(String.valueOf(yl));
        }

        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("date", date, "日期",  new Rule[]{new DateValue("yyyy-MM-dd")});
        vu.add("type", type, "是否是节假日",  new Rule[]{new Digits(1,0)});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(50)});
        vu.add("name", name, "节日名称",  new Rule[]{new Length(50)});
        vu.add("nl", nl, "农历",  new Rule[]{new Length(50)});
        vu.add("yl", yl, "阴历",  new Rule[]{new Length(50)});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  holiday;
    }


      /**
                     * 说明:添加Holiday信息
                     * @param request
                     * @throws Exception
                     * @return ResultDTO
                     * @author dozen.zhang
                     * @date 2018-12-9 21:16:24
                     */
                    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                    @API( summary="添加单个节假日信息",
                        description = "添加单个节假日信息",
                        parameters={
                           @Param(name="id" , description="编号",in=InType.body,dataType = DataType.LONG,required = false),
                           @Param(name="date" , description="日期",in=InType.body,dataType = DataType.DATE,required = false),
                           @Param(name="type" , description="是否是节假日",in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="remark" , description="备注",in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="name" , description="节日名称",in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="nl" , description="农历",in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="yl" , description="阴历",in=InType.body,dataType = DataType.STRING,required = false),
                        })
                    @RequestMapping(value = "add",method = RequestMethod.POST)
                    @ResponseBody
                    public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
                        Holiday holiday =    getInfoFromMap(bodyParam);


                        return holidayService.save(holiday);

                    }


    /**
    * 说明:添加Holiday信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2018-12-9 21:16:24
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个节假日信息",
    description = "更新单个节假日信息",
    parameters={
        @Param(name="id" , description="编号",in=InType.body,dataType = DataType.LONG,required = false),
        @Param(name="date" , description="日期",in=InType.body,dataType = DataType.DATE,required = false),
        @Param(name="type" , description="是否是节假日",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="remark" , description="备注",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="name" , description="节日名称",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="nl" , description="农历",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="yl" , description="阴历",in=InType.body,dataType = DataType.STRING,required = false),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    Holiday holiday =    getInfoFromMap(bodyParam);
    return holidayService.save(holiday);

    }
/**
     * 说明:ajax请求Holiday信息
     * @author dozen.zhang
     * @date 2018-12-9 21:16:24
     * @return String
     */
       @API(summary="节假日列表接口",
                 description="节假日列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="date" , description="日期",in=InType.params,dataType = DataType.DATE,required =false),// false
                    @Param(name="type" , description="是否是节假日",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="remark" , description="备注",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="name" , description="节日名称",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="nl" , description="农历",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="yl" , description="阴历",in=InType.params,dataType = DataType.STRING,required =false),// false
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
        String date = MapUtils.getString(params,"date");
        if(!StringUtil.isBlank(date)){
            if(StringUtil.checkNumeric(date)){
                params.put("date",date);
            }else if(StringUtil.checkDateStr(date, "yyyy-MM-dd")){
                params.put("date",DateUtil.parseToDate(date, "yyyy-MM-dd"));
            }
        }
        String dateBegin = MapUtils.getString(params,"dateBegin");
        if(!StringUtil.isBlank(dateBegin)){
            if(StringUtil.checkNumeric(dateBegin)){
                params.put("dateBegin",dateBegin);
            }else if(StringUtil.checkDateStr(dateBegin, "yyyy-MM-dd")){
                params.put("dateBegin",DateUtil.parseToDate(dateBegin, "yyyy-MM-dd"));
            }
        }
        String dateEnd = MapUtils.getString(params,"dateEnd");
        if(!StringUtil.isBlank(dateEnd)){
            if(StringUtil.checkNumeric(dateEnd)){
                params.put("dateEnd",dateEnd);
            }else if(StringUtil.checkDateStr(dateEnd, "yyyy-MM-dd")){
                params.put("dateEnd",DateUtil.parseToDate(dateEnd, "yyyy-MM-dd"));
            }
        }
        String type = MapUtils.getString(params,"type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String remark = MapUtils.getString(params,"remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = MapUtils.getString(params,"remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String name = MapUtils.getString(params,"name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = MapUtils.getString(params,"nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String nl = MapUtils.getString(params,"nl");
        if(!StringUtil.isBlank(nl)){
            params.put("nl",nl);
        }
        String nlLike = MapUtils.getString(params,"nlLike");
        if(!StringUtil.isBlank(nlLike)){
            params.put("nlLike",nlLike);
        }
        String yl = MapUtils.getString(params,"yl");
        if(!StringUtil.isBlank(yl)){
            params.put("yl",yl);
        }
        String ylLike = MapUtils.getString(params,"ylLike");
        if(!StringUtil.isBlank(ylLike)){
            params.put("ylLike",ylLike);
        }

        params.put("page",page);
        List<Holiday> holidays = holidayService.listByParams4Page(params);
        return ResultUtil.getResult(holidays, page);
    }



       /**
         * 导出
         * @param request
         * @return
         * @author dozen.zhang
         */
        @API(summary="节假日列表导出接口",
          description="节假日列表导出接口",
          parameters={
          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
             @Param(name="id" , description="编号",in=InType.params,dataType = DataType.LONG,required =false),// false
             @Param(name="date" , description="日期",in=InType.params,dataType = DataType.DATE,required =false),// false
             @Param(name="type" , description="是否是节假日",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="remark" , description="备注",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="name" , description="节日名称",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="nl" , description="农历",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="yl" , description="阴历",in=InType.params,dataType = DataType.STRING,required =false),// false
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
        String date = MapUtils.getString(params,"date");
        if(!StringUtil.isBlank(date)){
            if(StringUtil.checkNumeric(date)){
                params.put("date",date);
            }else if(StringUtil.checkDateStr(date, "yyyy-MM-dd")){
                params.put("date",DateUtil.parseToDate(date, "yyyy-MM-dd"));
            }
        }
        String dateBegin = MapUtils.getString(params,"dateBegin");
        if(!StringUtil.isBlank(dateBegin)){
            if(StringUtil.checkNumeric(dateBegin)){
                params.put("dateBegin",dateBegin);
            }else if(StringUtil.checkDateStr(dateBegin, "yyyy-MM-dd")){
                params.put("dateBegin",DateUtil.parseToDate(dateBegin, "yyyy-MM-dd"));
            }
        }
        String dateEnd = MapUtils.getString(params,"dateEnd");
        if(!StringUtil.isBlank(dateEnd)){
            if(StringUtil.checkNumeric(dateEnd)){
                params.put("dateEnd",dateEnd);
            }else if(StringUtil.checkDateStr(dateEnd, "yyyy-MM-dd")){
                params.put("dateEnd",DateUtil.parseToDate(dateEnd, "yyyy-MM-dd"));
            }
        }
        String type = MapUtils.getString(params,"type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String remark = MapUtils.getString(params,"remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = MapUtils.getString(params,"remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String name = MapUtils.getString(params,"name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = MapUtils.getString(params,"nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String nl = MapUtils.getString(params,"nl");
        if(!StringUtil.isBlank(nl)){
            params.put("nl",nl);
        }
        String nlLike = MapUtils.getString(params,"nlLike");
        if(!StringUtil.isBlank(nlLike)){
            params.put("nlLike",nlLike);
        }
        String yl = MapUtils.getString(params,"yl");
        if(!StringUtil.isBlank(yl)){
            params.put("yl",yl);
        }
        String ylLike = MapUtils.getString(params,"ylLike");
        if(!StringUtil.isBlank(ylLike)){
            params.put("ylLike",ylLike);
        }

             params.put("page",page);
             List<Holiday> list = holidayService.listByParams4Page(params);
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
            colTitle.put("date", "日期");
            colTitle.put("type", "是否是节假日");
            colTitle.put("remark", "备注");
            colTitle.put("name", "节日名称");
            colTitle.put("nl", "农历");
            colTitle.put("yl", "阴历");
            List<Map> finalList = new ArrayList<Map>();
            for (int i = 0; i < list.size(); i++) {
                Holiday sm = list.get(i);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id",  list.get(i).getId());
                map.put("date",  list.get(i).getDate());
                map.put("type",  list.get(i).getType());
                map.put("remark",  list.get(i).getRemark());
                map.put("name",  list.get(i).getName());
                map.put("nl",  list.get(i).getNl());
                map.put("yl",  list.get(i).getYl());
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

    @PostConstruct
    @Scheduled(cron="0 0 0 * * *")
    public void holiday(){
        //尽量将全年的节假日安排放到一张holiday的表中
        //如果实在查询不到的话 就每天早上调用接口 但是这个接口会有问题,到时候就麻烦了
        Calendar calendar = Calendar.getInstance();
        for(int i=0;i<365;i++) {
            String holidayUrl = ConfigUtil.getConfig("holiday.url");
            String theDayStr = DateUtil.toDateStr(calendar.getTime(),DateUtil.YYYY_MM_DD);
            holidayUrl = holidayUrl.replace("{date}", theDayStr);
            String result = HttpRequestUtil.sendGet(holidayUrl);

            Map resultMap = JsonUtil.toMap(result);
            Holiday holiday = new Holiday();
            holiday.setDate(calendar.getTime());


            HashMap<String, Object> map = new HashMap<>();
            map.put("date", theDayStr);
            List<Holiday> days = holidayService.listByParams(map);
            if (days != null && days.size() > 0) {
                holiday = days.get(i);

            }

            if (MapUtils.getString(resultMap, "code").equals("0")) {

                JSONObject holidayMap = (JSONObject) resultMap.get("holiday");
                if (holidayMap != null) {
                    holiday.setType(1);
                    holiday.setName(MapUtils.getString(holidayMap, "name"));

                    //CalendarState . TodayIsWorkDay =false;
                } else {
                    holiday.setType(0);
                }


                holidayService.save(holiday);
            }

            calendar.add(Calendar.DAY_OF_YEAR,1);
        }
    }

}
