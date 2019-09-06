/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-10-9 10:07:24
 * 文件说明: 
 */

package com.dozenx.web.core.auth.active.action;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import com.dozenx.common.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.dozenx.swagger.annotation.API;
import com.dozenx.swagger.annotation.APIs;
import com.dozenx.swagger.annotation.DataType;
import com.dozenx.swagger.annotation.Param;
import com.dozenx.web.core.auth.active.service.ActiveService;
import com.dozenx.web.core.auth.active.bean.Active;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.util.RequestUtil;
import org.springframework.web.bind.annotation.*;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.common.util.DateUtil;
@APIs(description = "激活")
@Controller
@RequestMapping("/sys/auth/active")
public class ActiveController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(ActiveController.class);
    /** 权限service **/
    @Autowired
    private ActiveService activeService;
    


    /**
     * 说明:ajax请求Active信息
     * @author dozen.zhang
     * @date 2018-10-9 10:07:24
     * @return String
     */
       @API(summary="激活列表接口",
                 description="激活列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="主键",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="type" , description="类别",dataType = DataType.STRING,required =false),// true
                    @Param(name="code" , description="验证码",dataType = DataType.STRING,required =false),// true
                    @Param(name="userId" , description="用户id",dataType = DataType.LONG,required =false),// false
                    @Param(name="status" , description="状态",dataType = DataType.INTEGER,required =false),// true
                    @Param(name="activedTime" , description="激活时间",dataType = DataType.DATE,required =false),// false
                    @Param(name="account" , description="账号",dataType = DataType.STRING,required =false),// false
                    @Param(name="createTime" , description="创建时间",dataType = DataType.DATE,required =false),// false
         })
    @RequestMapping(value = "/list" , method = RequestMethod.GET)
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
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String typeLike = request.getParameter("typeLike");
        if(!StringUtil.isBlank(typeLike)){
            params.put("typeLike",typeLike);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            params.put("code",code);
        }
        String codeLike = request.getParameter("codeLike");
        if(!StringUtil.isBlank(codeLike)){
            params.put("codeLike",codeLike);
        }
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            params.put("userId",userId);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String activedTime = request.getParameter("activedTime");
        if(!StringUtil.isBlank(activedTime)){
            if(StringUtil.checkNumeric(activedTime)){
                params.put("activedTime",activedTime);
            }else if(StringUtil.checkDateStr(activedTime, "yyyy-MM-dd")){
                params.put("activedTime",DateUtil.parseToDate(activedTime, "yyyy-MM-dd"));
            }
        }
        String activedTimeBegin = request.getParameter("activedTimeBegin");
        if(!StringUtil.isBlank(activedTimeBegin)){
            if(StringUtil.checkNumeric(activedTimeBegin)){
                params.put("activedTimeBegin",activedTimeBegin);
            }else if(StringUtil.checkDateStr(activedTimeBegin, "yyyy-MM-dd")){
                params.put("activedTimeBegin",DateUtil.parseToDate(activedTimeBegin, "yyyy-MM-dd"));
            }
        }
        String activedTimeEnd = request.getParameter("activedTimeEnd");
        if(!StringUtil.isBlank(activedTimeEnd)){
            if(StringUtil.checkNumeric(activedTimeEnd)){
                params.put("activedTimeEnd",activedTimeEnd);
            }else if(StringUtil.checkDateStr(activedTimeEnd, "yyyy-MM-dd")){
                params.put("activedTimeEnd",DateUtil.parseToDate(activedTimeEnd, "yyyy-MM-dd"));
            }
        }
        String account = request.getParameter("account");
        if(!StringUtil.isBlank(account)){
            params.put("account",account);
        }
        String accountLike = request.getParameter("accountLike");
        if(!StringUtil.isBlank(accountLike)){
            params.put("accountLike",accountLike);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd")){
                params.put("createTime",DateUtil.parseToDate(createTime, "yyyy-MM-dd"));
            }
        }
        String createTimeBegin = request.getParameter("createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd")){
                params.put("createTimeBegin",DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd"));
            }
        }
        String createTimeEnd = request.getParameter("createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd")){
                params.put("createTimeEnd",DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd"));
            }
        }

        params.put("page",page);
        List<Active> actives = activeService.listByParams4Page(params);
        return ResultUtil.getResult(actives, page);
    }
    
   /**
    * 说明:ajax请求Active信息 无分页版本
    * @return ResultDTO 返回结果
    * @author dozen.zhang
    * @date 2018-10-9 10:07:24
    */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
                HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String typeLike = request.getParameter("typeLike");
        if(!StringUtil.isBlank(typeLike)){
            params.put("typeLike",typeLike);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            params.put("code",code);
        }
        String codeLike = request.getParameter("codeLike");
        if(!StringUtil.isBlank(codeLike)){
            params.put("codeLike",codeLike);
        }
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            params.put("userId",userId);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String activedTime = request.getParameter("activedTime");
        if(!StringUtil.isBlank(activedTime)){
            if(StringUtil.checkNumeric(activedTime)){
                params.put("activedTime",activedTime);
            }else if(StringUtil.checkDateStr(activedTime, "yyyy-MM-dd")){
                params.put("activedTime",DateUtil.parseToDate(activedTime, "yyyy-MM-dd"));
            }
        }
        String activedTimeBegin = request.getParameter("activedTimeBegin");
        if(!StringUtil.isBlank(activedTimeBegin)){
            if(StringUtil.checkNumeric(activedTimeBegin)){
                params.put("activedTimeBegin",activedTimeBegin);
            }else if(StringUtil.checkDateStr(activedTimeBegin, "yyyy-MM-dd")){
                params.put("activedTimeBegin",DateUtil.parseToDate(activedTimeBegin, "yyyy-MM-dd"));
            }
        }
        String activedTimeEnd = request.getParameter("activedTimeEnd");
        if(!StringUtil.isBlank(activedTimeEnd)){
            if(StringUtil.checkNumeric(activedTimeEnd)){
                params.put("activedTimeEnd",activedTimeEnd);
            }else if(StringUtil.checkDateStr(activedTimeEnd, "yyyy-MM-dd")){
                params.put("activedTimeEnd",DateUtil.parseToDate(activedTimeEnd, "yyyy-MM-dd"));
            }
        }
        String account = request.getParameter("account");
        if(!StringUtil.isBlank(account)){
            params.put("account",account);
        }
        String accountLike = request.getParameter("accountLike");
        if(!StringUtil.isBlank(accountLike)){
            params.put("accountLike",accountLike);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd")){
                params.put("createTime",DateUtil.parseToDate(createTime, "yyyy-MM-dd"));
            }
        }
        String createTimeBegin = request.getParameter("createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd")){
                params.put("createTimeBegin",DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd"));
            }
        }
        String createTimeEnd = request.getParameter("createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd")){
                params.put("createTimeEnd",DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd"));
            }
        }

        List<Active> actives = activeService.listByParams(params);
        return ResultUtil.getDataResult(actives);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2018-10-9 10:07:24
    */
  @API( summary="根据id查询单个激活信息",
           description = "根据id查询单个激活信息",
           parameters={
                   @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
           })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(@PathVariable Integer id,HttpServletRequest request) {
            HashMap<String,Object> result =new HashMap</*String,Object*/>();
        if(id > 0){
            Active bean = activeService.selectByPrimaryKey(Integer.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        Active bean = activeService.selectByPrimaryKey(Integer.valueOf(id));
        HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }

     /**
        * 说明:查看单条信息
        * @param request 发请求
        * @return String
        * @author dozen.zhang
        * @date 2018-10-9 10:07:24
        */
      @API( summary="根据id查询单个激活信息",
               description = "根据id查询单个激活信息",
               parameters={
                       @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(HttpServletRequest request) {
                HashMap<String,Object> result =new HashMap</*String,Object*/>();
//        if(id > 0){
//            Active bean = activeService.selectByPrimaryKey(Integer.valueOf(id));
//            result.put("bean", bean);
//        }
//        return this.getResult(result);



         String id = request.getParameter("id");
            Active bean = activeService.selectByPrimaryKey(Integer.valueOf(id));
          //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
          //  result.put("bean", bean);
            return this.getResult(bean);
        }


    /**
     * 说明:更新Active信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-10-9 10:07:24
     */
      @API( summary="根据id更新单个激活信息",
        description = "根据id更新单个激活信息",
        parameters={
           @Param(name="id" , description="主键",type="INTEGER",required = false),
           @Param(name="type" , description="类别",type="STRING",required = true),
           @Param(name="code" , description="验证码",type="STRING",required = true),
           @Param(name="userId" , description="用户id",type="LONG",required = false),
           @Param(name="status" , description="状态",type="INTEGER",required = true),
           @Param(name="activedTime" , description="激活时间",type="DATE",required = false),
           @Param(name="account" , description="账号",type="STRING",required = false),
           @Param(name="createTime" , description="创建时间",type="DATE",required = false),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update",method = RequestMethod.PUT)///{id}
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Integer id,
        Active active =new  Active();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            active.setId(Integer.valueOf(id)) ;
        }
        
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            active.setType(String.valueOf(type)) ;
        }
        
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            active.setCode(String.valueOf(code)) ;
        }
        
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            active.setUserId(Long.valueOf(userId)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            active.setStatus(Integer.valueOf(status)) ;
        }
        
        String activedTime = request.getParameter("activedTime");
        if(!StringUtil.isBlank(activedTime)){
            active.setActivedTime(Date.valueOf(activedTime)) ;
        }
        
        String account = request.getParameter("account");
        if(!StringUtil.isBlank(account)){
            active.setAccount(String.valueOf(account)) ;
        }
        
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            active.setCreateTime(Date.valueOf(createTime)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            active.setId(Integer.valueOf(id));
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            active.setType(type);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            active.setCode(code);
        }
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            active.setUserId(Long.valueOf(userId));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            active.setStatus(Integer.valueOf(status));
        }
        String activedTime = request.getParameter("activedTime");
        if(!StringUtil.isBlank(activedTime)){
            if(StringUtil.checkNumeric(activedTime)){
                active.setActivedTime(new Date(activedTime));
            }else if(StringUtil.checkDateStr(activedTime, "yyyy-MM-dd")){
                active.setActivedTime(DateUtil.parseToDate(activedTime, "yyyy-MM-dd"));
            }
        }
        String account = request.getParameter("account");
        if(!StringUtil.isBlank(account)){
            active.setAccount(account);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                active.setCreateTime(new Date(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd")){
                active.setCreateTime(DateUtil.parseToDate(createTime, "yyyy-MM-dd"));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(11,0)});
        vu.add("type", type, "类别",  new Rule[]{new Length(40),new NotEmpty()});
        vu.add("code", code, "验证码",  new Rule[]{new Length(40),new NotEmpty()});
        vu.add("userId", userId, "用户id",  new Rule[]{new Digits(11,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new NotEmpty()});
        vu.add("activedTime", activedTime, "激活时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("account", account, "账号",  new Rule[]{new Length(30)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return activeService.save(active);
       
    }


        /**
         * 说明:添加Active信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2018-10-9 10:07:24
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个激活信息",
            description = "添加单个激活信息",
            parameters={
               @Param(name="id" , description="主键",dataType = DataType.INTEGER,required = false),
               @Param(name="type" , description="类别",dataType = DataType.STRING,required = true),
               @Param(name="code" , description="验证码",dataType = DataType.STRING,required = true),
               @Param(name="userId" , description="用户id",dataType = DataType.LONG,required = false),
               @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = true),
               @Param(name="activedTime" , description="激活时间",dataType = DataType.DATE,required = false),
               @Param(name="account" , description="账号",dataType = DataType.STRING,required = false),
               @Param(name="createTime" , description="创建时间",dataType = DataType.DATE,required = false),
            })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            Active active =new  Active();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                active.setId(Integer.valueOf(id)) ;
            }
            
            String type = request.getParameter("type");
            if(!StringUtil.isBlank(type)){
                active.setType(String.valueOf(type)) ;
            }
            
            String code = request.getParameter("code");
            if(!StringUtil.isBlank(code)){
                active.setCode(String.valueOf(code)) ;
            }
            
            String userId = request.getParameter("userId");
            if(!StringUtil.isBlank(userId)){
                active.setUserId(Long.valueOf(userId)) ;
            }
            
            String status = request.getParameter("status");
            if(!StringUtil.isBlank(status)){
                active.setStatus(Integer.valueOf(status)) ;
            }
            
            String activedTime = request.getParameter("activedTime");
            if(!StringUtil.isBlank(activedTime)){
                active.setActivedTime(Date.valueOf(activedTime)) ;
            }
            
            String account = request.getParameter("account");
            if(!StringUtil.isBlank(account)){
                active.setAccount(String.valueOf(account)) ;
            }
            
            String createTime = request.getParameter("createTime");
            if(!StringUtil.isBlank(createTime)){
                active.setCreateTime(Date.valueOf(createTime)) ;
            }
            */
            String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            active.setId(Integer.valueOf(id));
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            active.setType(type);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            active.setCode(code);
        }
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            active.setUserId(Long.valueOf(userId));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            active.setStatus(Integer.valueOf(status));
        }
        String activedTime = request.getParameter("activedTime");
        if(!StringUtil.isBlank(activedTime)){
            if(StringUtil.checkNumeric(activedTime)){
                active.setActivedTime(new Date(activedTime));
            }else if(StringUtil.checkDateStr(activedTime, "yyyy-MM-dd")){
                active.setActivedTime(DateUtil.parseToDate(activedTime, "yyyy-MM-dd"));
            }
        }
        String account = request.getParameter("account");
        if(!StringUtil.isBlank(account)){
            active.setAccount(account);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                active.setCreateTime(new Date(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd")){
                active.setCreateTime(DateUtil.parseToDate(createTime, "yyyy-MM-dd"));
            }
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(11,0)});
        vu.add("type", type, "类别",  new Rule[]{new Length(40),new NotEmpty()});
        vu.add("code", code, "验证码",  new Rule[]{new Length(40),new NotEmpty()});
        vu.add("userId", userId, "用户id",  new Rule[]{new Digits(11,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new NotEmpty()});
        vu.add("activedTime", activedTime, "激活时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("account", account, "账号",  new Rule[]{new Length(30)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

            return activeService.save(active);

        }


          /**
                 * 说明:添加Active信息
                 * @param request
                 * @throws Exception
                 * @return ResultDTO
                 * @author dozen.zhang
                 * @date 2018-10-9 10:07:24
                 */
                // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                @API( summary="添加单个激活信息",
                    description = "添加单个激活信息",
                    parameters={
                       @Param(name="id" , description="主键",dataType = DataType.INTEGER,required = false),
                       @Param(name="type" , description="类别",dataType = DataType.STRING,required = true),
                       @Param(name="code" , description="验证码",dataType = DataType.STRING,required = true),
                       @Param(name="userId" , description="用户id",dataType = DataType.LONG,required = false),
                       @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = true),
                       @Param(name="activedTime" , description="激活时间",dataType = DataType.DATE,required = false),
                       @Param(name="account" , description="账号",dataType = DataType.STRING,required = false),
                       @Param(name="createTime" , description="创建时间",dataType = DataType.DATE,required = false),
                    })
                @RequestMapping(value = "save",method = RequestMethod.POST)
                @ResponseBody
                public ResultDTO save(HttpServletRequest request) throws Exception {
                    Active active =new  Active();
                    /*
                    String id = request.getParameter("id");
                    if(!StringUtil.isBlank(id)){
                        active.setId(Integer.valueOf(id)) ;
                    }
                    
                    String type = request.getParameter("type");
                    if(!StringUtil.isBlank(type)){
                        active.setType(String.valueOf(type)) ;
                    }
                    
                    String code = request.getParameter("code");
                    if(!StringUtil.isBlank(code)){
                        active.setCode(String.valueOf(code)) ;
                    }
                    
                    String userId = request.getParameter("userId");
                    if(!StringUtil.isBlank(userId)){
                        active.setUserId(Long.valueOf(userId)) ;
                    }
                    
                    String status = request.getParameter("status");
                    if(!StringUtil.isBlank(status)){
                        active.setStatus(Integer.valueOf(status)) ;
                    }
                    
                    String activedTime = request.getParameter("activedTime");
                    if(!StringUtil.isBlank(activedTime)){
                        active.setActivedTime(Date.valueOf(activedTime)) ;
                    }
                    
                    String account = request.getParameter("account");
                    if(!StringUtil.isBlank(account)){
                        active.setAccount(String.valueOf(account)) ;
                    }
                    
                    String createTime = request.getParameter("createTime");
                    if(!StringUtil.isBlank(createTime)){
                        active.setCreateTime(Date.valueOf(createTime)) ;
                    }
                    */
                    String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            active.setId(Integer.valueOf(id));
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            active.setType(type);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            active.setCode(code);
        }
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            active.setUserId(Long.valueOf(userId));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            active.setStatus(Integer.valueOf(status));
        }
        String activedTime = request.getParameter("activedTime");
        if(!StringUtil.isBlank(activedTime)){
            if(StringUtil.checkNumeric(activedTime)){
                active.setActivedTime(new Date(activedTime));
            }else if(StringUtil.checkDateStr(activedTime, "yyyy-MM-dd")){
                active.setActivedTime(DateUtil.parseToDate(activedTime, "yyyy-MM-dd"));
            }
        }
        String account = request.getParameter("account");
        if(!StringUtil.isBlank(account)){
            active.setAccount(account);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                active.setCreateTime(new Date(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd")){
                active.setCreateTime(DateUtil.parseToDate(createTime, "yyyy-MM-dd"));
            }
        }

                    //valid
                    ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(11,0)});
        vu.add("type", type, "类别",  new Rule[]{new Length(40),new NotEmpty()});
        vu.add("code", code, "验证码",  new Rule[]{new Length(40),new NotEmpty()});
        vu.add("userId", userId, "用户id",  new Rule[]{new Digits(11,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new NotEmpty()});
        vu.add("activedTime", activedTime, "激活时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("account", account, "账号",  new Rule[]{new Length(30)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

                    return activeService.save(active);

                }

    /**
     * 说明:删除Active信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-10-9 10:07:24
     */
     @API( summary="根据id删除单个激活信息",
        description = "根据id删除单个激活信息",
        parameters={
         @Param(name="id" , description="主键",dataType= DataType.INTEGER,required = true),
        })
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)//{id}
    @ResponseBody
    public ResultDTO delete(HttpServletRequest request) {//@PathVariable Integer id,
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Integer id = Integer.valueOf(idStr);
        activeService.delete(id);
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
        Integer idAry[]=new Integer[idStrAry.length];
        for(int i=0,length=idAry.length;i<length;i++){
            ValidateUtil vu = new ValidateUtil();
            String validStr="";
            String id = idStrAry[i];
                    vu.add("id", id, "主键",  new Rule[]{new Digits(11,0)});

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
            idAry[i]=Integer.valueOf(idStrAry[i]);
        }
       return  activeService.multilDelete(idAry);
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
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String typeLike = request.getParameter("typeLike");
        if(!StringUtil.isBlank(typeLike)){
            params.put("typeLike",typeLike);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            params.put("code",code);
        }
        String codeLike = request.getParameter("codeLike");
        if(!StringUtil.isBlank(codeLike)){
            params.put("codeLike",codeLike);
        }
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            params.put("userId",userId);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String activedTime = request.getParameter("activedTime");
        if(!StringUtil.isBlank(activedTime)){
            if(StringUtil.checkNumeric(activedTime)){
                params.put("activedTime",activedTime);
            }else if(StringUtil.checkDateStr(activedTime, "yyyy-MM-dd")){
                params.put("activedTime",DateUtil.parseToDate(activedTime, "yyyy-MM-dd"));
            }
        }
        String activedTimeBegin = request.getParameter("activedTimeBegin");
        if(!StringUtil.isBlank(activedTimeBegin)){
            if(StringUtil.checkNumeric(activedTimeBegin)){
                params.put("activedTimeBegin",activedTimeBegin);
            }else if(StringUtil.checkDateStr(activedTimeBegin, "yyyy-MM-dd")){
                params.put("activedTimeBegin",DateUtil.parseToDate(activedTimeBegin, "yyyy-MM-dd"));
            }
        }
        String activedTimeEnd = request.getParameter("activedTimeEnd");
        if(!StringUtil.isBlank(activedTimeEnd)){
            if(StringUtil.checkNumeric(activedTimeEnd)){
                params.put("activedTimeEnd",activedTimeEnd);
            }else if(StringUtil.checkDateStr(activedTimeEnd, "yyyy-MM-dd")){
                params.put("activedTimeEnd",DateUtil.parseToDate(activedTimeEnd, "yyyy-MM-dd"));
            }
        }
        String account = request.getParameter("account");
        if(!StringUtil.isBlank(account)){
            params.put("account",account);
        }
        String accountLike = request.getParameter("accountLike");
        if(!StringUtil.isBlank(accountLike)){
            params.put("accountLike",accountLike);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd")){
                params.put("createTime",DateUtil.parseToDate(createTime, "yyyy-MM-dd"));
            }
        }
        String createTimeBegin = request.getParameter("createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd")){
                params.put("createTimeBegin",DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd"));
            }
        }
        String createTimeEnd = request.getParameter("createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd")){
                params.put("createTimeEnd",DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd"));
            }
        }

        // 查询list集合
        List<Active> list =activeService.listByParams(params);
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
        colTitle.put("id", "主键");
        colTitle.put("type", "类别");
        colTitle.put("code", "验证码");
        colTitle.put("userId", "用户id");
        colTitle.put("status", "状态");
        colTitle.put("activedTime", "激活时间");
        colTitle.put("account", "账号");
        colTitle.put("createTime", "创建时间");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            Active sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("type",  list.get(i).getType());
            map.put("code",  list.get(i).getCode());
            map.put("userId",  list.get(i).getUserId());
            map.put("status",  list.get(i).getStatus());
            map.put("activedTime",  list.get(i).getActivedTime());
            map.put("account",  list.get(i).getAccount());
            map.put("createTime",  list.get(i).getCreateTime());
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
    @RequestMapping(value = "/import.json")
    public void importExcel(){
        
    }


      /**
         * 说明: 跳转到Active列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/ActiveList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/ActiveListMapper.html";
        }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-10-9 10:07:24
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/ActiveEdit.html";
    }
    /**
         * 说明:跳转查看页面
         * @param request 发请求
         * @return String
         * @author dozen.zhang
         * @date 2018-10-9 10:07:24
         */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/ActiveView.html";
    }
}
