/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-11-21 16:42:27
 * 文件说明: 
 */

package com.dozenx.web.module.checkin.checkinLate.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.cpj.swagger.annotation.*;
import java.util.LinkedHashMap;
import com.dozenx.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import org.slf4j.Logger;
import com.dozenx.core.exception.ParamException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.dozenx.web.module.checkin.checkinLate.service.CheckinLateService;
import com.dozenx.web.module.checkin.checkinLate.bean.CheckinLate;
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

@APIs(description = "考勤迟到表")
@Controller
@RequestMapping("/checkin/late")
public class CheckinLateController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(CheckinLateController.class);
    /** 权限service **/
    @Autowired
    private CheckinLateService checkinLateService;
    


    /**
     * 说明:ajax请求CheckinLate信息
     * @author dozen.zhang
     * @date 2018-11-21 16:42:27
     * @return String
     */
       @API(summary="考勤迟到表列表接口",
                 description="考勤迟到表列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",dataType = DataType.LONG,required =false),// false
                    @Param(name="userId" , description="用户Id",dataType = DataType.LONG,required =false),// true
                    @Param(name="userName" , description="用户名",dataType = DataType.STRING,required =false),// true
                    @Param(name="checkType" , description="考勤类型",dataType = DataType.INTEGER,required =false),// true
                    @Param(name="checkTime" , description="检查时间",dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="kqUserId" , description="考勤机用户id",dataType = DataType.LONG,required =false),// true
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
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            params.put("userId",userId);
        }
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            params.put("userName",userName);
        }
        String userNameLike = request.getParameter("userNameLike");
        if(!StringUtil.isBlank(userNameLike)){
            params.put("userNameLike",userNameLike);
        }
        String checkType = request.getParameter("checkType");
        if(!StringUtil.isBlank(checkType)){
            params.put("checkType",checkType);
        }
        String checkTime = request.getParameter("checkTime");
        if(!StringUtil.isBlank(checkTime)){
            if(StringUtil.checkNumeric(checkTime)){
                params.put("checkTime",checkTime);
            }else if(StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("checkTime",new Timestamp( DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String checkTimeBegin = request.getParameter("checkTimeBegin");
        if(!StringUtil.isBlank(checkTimeBegin)){
            if(StringUtil.checkNumeric(checkTimeBegin)){
                params.put("checkTimeBegin",checkTimeBegin);
            }else if(StringUtil.checkDateStr(checkTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("checkTimeBegin",new Timestamp( DateUtil.parseToDate(checkTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String checkTimeEnd = request.getParameter("checkTimeEnd");
        if(!StringUtil.isBlank(checkTimeEnd)){
            if(StringUtil.checkNumeric(checkTimeEnd)){
                params.put("checkTimeEnd",checkTimeEnd);
            }else if(StringUtil.checkDateStr(checkTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("checkTimeEnd",new Timestamp( DateUtil.parseToDate(checkTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String kqUserId = request.getParameter("kqUserId");
        if(!StringUtil.isBlank(kqUserId)){
            params.put("kqUserId",kqUserId);
        }

        params.put("page",page);
        List<CheckinLate> checkinLates = checkinLateService.listByParams4Page(params);
        return ResultUtil.getResult(checkinLates, page);
    }
    
   /**
    * 说明:ajax请求CheckinLate信息 无分页版本
    * @return ResultDTO 返回结果
    * @author dozen.zhang
    * @date 2018-11-21 16:42:27
    */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
                HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            params.put("userId",userId);
        }
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            params.put("userName",userName);
        }
        String userNameLike = request.getParameter("userNameLike");
        if(!StringUtil.isBlank(userNameLike)){
            params.put("userNameLike",userNameLike);
        }
        String checkType = request.getParameter("checkType");
        if(!StringUtil.isBlank(checkType)){
            params.put("checkType",checkType);
        }
        String checkTime = request.getParameter("checkTime");
        if(!StringUtil.isBlank(checkTime)){
            if(StringUtil.checkNumeric(checkTime)){
                params.put("checkTime",checkTime);
            }else if(StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("checkTime",new Timestamp( DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String checkTimeBegin = request.getParameter("checkTimeBegin");
        if(!StringUtil.isBlank(checkTimeBegin)){
            if(StringUtil.checkNumeric(checkTimeBegin)){
                params.put("checkTimeBegin",checkTimeBegin);
            }else if(StringUtil.checkDateStr(checkTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("checkTimeBegin",new Timestamp( DateUtil.parseToDate(checkTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String checkTimeEnd = request.getParameter("checkTimeEnd");
        if(!StringUtil.isBlank(checkTimeEnd)){
            if(StringUtil.checkNumeric(checkTimeEnd)){
                params.put("checkTimeEnd",checkTimeEnd);
            }else if(StringUtil.checkDateStr(checkTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("checkTimeEnd",new Timestamp( DateUtil.parseToDate(checkTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String kqUserId = request.getParameter("kqUserId");
        if(!StringUtil.isBlank(kqUserId)){
            params.put("kqUserId",kqUserId);
        }

        List<CheckinLate> checkinLates = checkinLateService.listByParams(params);
        return ResultUtil.getDataResult(checkinLates);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2018-11-21 16:42:27
    */
     @API( summary="根据id查询单个考勤迟到表信息",
               description = "根据id查询单个考勤迟到表信息",
               parameters={
                       @Param(name="id" , description="id",in=InType.path,dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(@PathVariable Long id,HttpServletRequest request) {
                     HashMap<String,Object> result =new HashMap</*String,Object*/>();
        if(id > 0){
            CheckinLate bean = checkinLateService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

        }



     /**
        * 说明:查看单条信息
        * @param request 发请求
        * @return String
        * @author dozen.zhang
        * @date 2018-11-21 16:42:27
        */
      @API( summary="根据id查询单个考勤迟到表信息",
               description = "根据id查询单个考勤迟到表信息",
               parameters={
                       @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view.json",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(HttpServletRequest request) {
         String id = request.getParameter("id");
            CheckinLate bean = checkinLateService.selectByPrimaryKey(Long.valueOf(id));
          //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
           // result.put("bean", bean);
            return this.getResult(bean);
        }


    /**
     * 说明:更新CheckinLate信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-11-21 16:42:27
     */
      @API( summary="更新id更新单个考勤迟到表信息",
        description = "更新id更新单个考勤迟到表信息",
        parameters={
           @Param(name="id" , description="编号",type="LONG",required = false),
           @Param(name="userId" , description="用户Id",type="LONG",required = true),
           @Param(name="userName" , description="用户名",type="STRING",required = true),
           @Param(name="checkType" , description="考勤类型",type="INTEGER",required = true),
           @Param(name="checkTime" , description="检查时间",type="DATE_TIME",required = false),
           @Param(name="kqUserId" , description="考勤机用户id",type="LONG",required = true),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update.json",method = RequestMethod.PUT)///{id}
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Long id,
        CheckinLate checkinLate =new  CheckinLate();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            checkinLate.setId(Long.valueOf(id)) ;
        }
        
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            checkinLate.setUserId(Long.valueOf(userId)) ;
        }
        
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            checkinLate.setUserName(String.valueOf(userName)) ;
        }
        
        String checkType = request.getParameter("checkType");
        if(!StringUtil.isBlank(checkType)){
            checkinLate.setCheckType(Integer.valueOf(checkType)) ;
        }
        
        String checkTime = request.getParameter("checkTime");
        if(!StringUtil.isBlank(checkTime)){
            checkinLate.setCheckTime(Timestamp.valueOf(checkTime)) ;
        }
        
        String kqUserId = request.getParameter("kqUserId");
        if(!StringUtil.isBlank(kqUserId)){
            checkinLate.setKqUserId(Long.valueOf(kqUserId)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            checkinLate.setId(Long.valueOf(id));
        }
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            checkinLate.setUserId(Long.valueOf(userId));
        }
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            checkinLate.setUserName(userName);
        }
        String checkType = request.getParameter("checkType");
        if(!StringUtil.isBlank(checkType)){
            checkinLate.setCheckType(Integer.valueOf(checkType));
        }
        String checkTime = request.getParameter("checkTime");
        if(!StringUtil.isBlank(checkTime)){
            if(StringUtil.checkNumeric(checkTime)){
                checkinLate.setCheckTime(Timestamp.valueOf(checkTime));
            }else if(StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")){
                checkinLate.setCheckTime(new Timestamp( DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String kqUserId = request.getParameter("kqUserId");
        if(!StringUtil.isBlank(kqUserId)){
            checkinLate.setKqUserId(Long.valueOf(kqUserId));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
        vu.add("userId", userId, "用户Id",  new Rule[]{new Digits(13,0),new NotEmpty()});
        vu.add("userName", userName, "用户名",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("checkType", checkType, "考勤类型",  new Rule[]{new Digits(3,0),new NotEmpty()});
        vu.add("checkTime", checkTime, "检查时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("kqUserId", kqUserId, "考勤机用户id",  new Rule[]{new Digits(13,0),new NotEmpty()});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return checkinLateService.save(checkinLate);
       
    }


        /**
         * 说明:添加CheckinLate信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2018-11-21 16:42:27
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个考勤迟到表信息",
            description = "添加单个考勤迟到表信息",
            parameters={
               @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
               @Param(name="userId" , description="用户Id",dataType = DataType.LONG,required = true),
               @Param(name="userName" , description="用户名",dataType = DataType.STRING,required = true),
               @Param(name="checkType" , description="考勤类型",dataType = DataType.INTEGER,required = true),
               @Param(name="checkTime" , description="检查时间",dataType = DataType.DATE_TIME,required = false),
               @Param(name="kqUserId" , description="考勤机用户id",dataType = DataType.LONG,required = true),
            })
        @RequestMapping(value = "add.json",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            CheckinLate checkinLate =new  CheckinLate();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                checkinLate.setId(Long.valueOf(id)) ;
            }
            
            String userId = request.getParameter("userId");
            if(!StringUtil.isBlank(userId)){
                checkinLate.setUserId(Long.valueOf(userId)) ;
            }
            
            String userName = request.getParameter("userName");
            if(!StringUtil.isBlank(userName)){
                checkinLate.setUserName(String.valueOf(userName)) ;
            }
            
            String checkType = request.getParameter("checkType");
            if(!StringUtil.isBlank(checkType)){
                checkinLate.setCheckType(Integer.valueOf(checkType)) ;
            }
            
            String checkTime = request.getParameter("checkTime");
            if(!StringUtil.isBlank(checkTime)){
                checkinLate.setCheckTime(Timestamp.valueOf(checkTime)) ;
            }
            
            String kqUserId = request.getParameter("kqUserId");
            if(!StringUtil.isBlank(kqUserId)){
                checkinLate.setKqUserId(Long.valueOf(kqUserId)) ;
            }
            */
            String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            checkinLate.setId(Long.valueOf(id));
        }
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            checkinLate.setUserId(Long.valueOf(userId));
        }
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            checkinLate.setUserName(userName);
        }
        String checkType = request.getParameter("checkType");
        if(!StringUtil.isBlank(checkType)){
            checkinLate.setCheckType(Integer.valueOf(checkType));
        }
        String checkTime = request.getParameter("checkTime");
        if(!StringUtil.isBlank(checkTime)){
            if(StringUtil.checkNumeric(checkTime)){
                checkinLate.setCheckTime(Timestamp.valueOf(checkTime));
            }else if(StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")){
                checkinLate.setCheckTime(new Timestamp( DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String kqUserId = request.getParameter("kqUserId");
        if(!StringUtil.isBlank(kqUserId)){
            checkinLate.setKqUserId(Long.valueOf(kqUserId));
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
        vu.add("userId", userId, "用户Id",  new Rule[]{new Digits(13,0),new NotEmpty()});
        vu.add("userName", userName, "用户名",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("checkType", checkType, "考勤类型",  new Rule[]{new Digits(3,0),new NotEmpty()});
        vu.add("checkTime", checkTime, "检查时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("kqUserId", kqUserId, "考勤机用户id",  new Rule[]{new Digits(13,0),new NotEmpty()});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

            return checkinLateService.save(checkinLate);

        }


          /**
                 * 说明:添加CheckinLate信息
                 * @param request
                 * @throws Exception
                 * @return ResultDTO
                 * @author dozen.zhang
                 * @date 2018-11-21 16:42:27
                 */
                // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                @API( summary="添加单个考勤迟到表信息",
                    description = "添加单个考勤迟到表信息",
                    parameters={
                       @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
                       @Param(name="userId" , description="用户Id",dataType = DataType.LONG,required = true),
                       @Param(name="userName" , description="用户名",dataType = DataType.STRING,required = true),
                       @Param(name="checkType" , description="考勤类型",dataType = DataType.INTEGER,required = true),
                       @Param(name="checkTime" , description="检查时间",dataType = DataType.DATE_TIME,required = false),
                       @Param(name="kqUserId" , description="考勤机用户id",dataType = DataType.LONG,required = true),
                    })
                @RequestMapping(value = "save.json",method = RequestMethod.POST)
                @ResponseBody
                public ResultDTO save(HttpServletRequest request) throws Exception {
                    CheckinLate checkinLate =new  CheckinLate();
                    /*
                    String id = request.getParameter("id");
                    if(!StringUtil.isBlank(id)){
                        checkinLate.setId(Long.valueOf(id)) ;
                    }
                    
                    String userId = request.getParameter("userId");
                    if(!StringUtil.isBlank(userId)){
                        checkinLate.setUserId(Long.valueOf(userId)) ;
                    }
                    
                    String userName = request.getParameter("userName");
                    if(!StringUtil.isBlank(userName)){
                        checkinLate.setUserName(String.valueOf(userName)) ;
                    }
                    
                    String checkType = request.getParameter("checkType");
                    if(!StringUtil.isBlank(checkType)){
                        checkinLate.setCheckType(Integer.valueOf(checkType)) ;
                    }
                    
                    String checkTime = request.getParameter("checkTime");
                    if(!StringUtil.isBlank(checkTime)){
                        checkinLate.setCheckTime(Timestamp.valueOf(checkTime)) ;
                    }
                    
                    String kqUserId = request.getParameter("kqUserId");
                    if(!StringUtil.isBlank(kqUserId)){
                        checkinLate.setKqUserId(Long.valueOf(kqUserId)) ;
                    }
                    */
                    String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            checkinLate.setId(Long.valueOf(id));
        }
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            checkinLate.setUserId(Long.valueOf(userId));
        }
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            checkinLate.setUserName(userName);
        }
        String checkType = request.getParameter("checkType");
        if(!StringUtil.isBlank(checkType)){
            checkinLate.setCheckType(Integer.valueOf(checkType));
        }
        String checkTime = request.getParameter("checkTime");
        if(!StringUtil.isBlank(checkTime)){
            if(StringUtil.checkNumeric(checkTime)){
                checkinLate.setCheckTime(Timestamp.valueOf(checkTime));
            }else if(StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")){
                checkinLate.setCheckTime(new Timestamp( DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String kqUserId = request.getParameter("kqUserId");
        if(!StringUtil.isBlank(kqUserId)){
            checkinLate.setKqUserId(Long.valueOf(kqUserId));
        }

                    //valid
                    ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
        vu.add("userId", userId, "用户Id",  new Rule[]{new Digits(13,0),new NotEmpty()});
        vu.add("userName", userName, "用户名",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("checkType", checkType, "考勤类型",  new Rule[]{new Digits(3,0),new NotEmpty()});
        vu.add("checkTime", checkTime, "检查时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("kqUserId", kqUserId, "考勤机用户id",  new Rule[]{new Digits(13,0),new NotEmpty()});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

                    return checkinLateService.save(checkinLate);

                }

    /**
     * 说明:删除CheckinLate信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-11-21 16:42:27
     */
     @API( summary="根据id删除单个考勤迟到表信息",
        description = "根据id删除单个考勤迟到表信息",
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
        checkinLateService.delete(id);
        return this.getResult(SUCC);
    }

     /**
         * 说明:删除CheckinLate信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2018-11-21 16:42:27
         */
         @API( summary="根据id删除单个考勤迟到表信息",
            description = "根据id删除单个考勤迟到表信息",
            parameters={
             @Param(name="id" , description="编号",in=InType.path,dataType= DataType.LONG,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable Long id,HttpServletRequest request) {
            checkinLateService.delete(id);
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
       return  checkinLateService.multilDelete(idAry);
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
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            params.put("userId",userId);
        }
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            params.put("userName",userName);
        }
        String userNameLike = request.getParameter("userNameLike");
        if(!StringUtil.isBlank(userNameLike)){
            params.put("userNameLike",userNameLike);
        }
        String checkType = request.getParameter("checkType");
        if(!StringUtil.isBlank(checkType)){
            params.put("checkType",checkType);
        }
        String checkTime = request.getParameter("checkTime");
        if(!StringUtil.isBlank(checkTime)){
            if(StringUtil.checkNumeric(checkTime)){
                params.put("checkTime",checkTime);
            }else if(StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("checkTime",new Timestamp( DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String checkTimeBegin = request.getParameter("checkTimeBegin");
        if(!StringUtil.isBlank(checkTimeBegin)){
            if(StringUtil.checkNumeric(checkTimeBegin)){
                params.put("checkTimeBegin",checkTimeBegin);
            }else if(StringUtil.checkDateStr(checkTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("checkTimeBegin",new Timestamp( DateUtil.parseToDate(checkTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String checkTimeEnd = request.getParameter("checkTimeEnd");
        if(!StringUtil.isBlank(checkTimeEnd)){
            if(StringUtil.checkNumeric(checkTimeEnd)){
                params.put("checkTimeEnd",checkTimeEnd);
            }else if(StringUtil.checkDateStr(checkTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("checkTimeEnd",new Timestamp( DateUtil.parseToDate(checkTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String kqUserId = request.getParameter("kqUserId");
        if(!StringUtil.isBlank(kqUserId)){
            params.put("kqUserId",kqUserId);
        }

        // 查询list集合
        List<CheckinLate> list =checkinLateService.listByParams(params);
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
        colTitle.put("userId", "用户Id");
        colTitle.put("userName", "用户名");
        colTitle.put("checkType", "考勤类型");
        colTitle.put("checkTime", "检查时间");
        colTitle.put("kqUserId", "考勤机用户id");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            CheckinLate sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("userId",  list.get(i).getUserId());
            map.put("userName",  list.get(i).getUserName());
            map.put("checkType",  list.get(i).getCheckType());
            map.put("checkTime",  list.get(i).getCheckTime());
            map.put("kqUserId",  list.get(i).getKqUserId());
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

                        CheckinLate bean = getInfoFromMap(params);

                      //  if (count > 0) {

                      //      logger.warn("邮箱已经存在:" + email);
                       //     errorMsg.append("邮箱已经存在:" + email);
                         //   continue;

                       // }

                        try {
                            checkinLateService.save(bean);
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
         * 说明: 跳转到CheckinLate列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/CheckinLateList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/CheckinLateListMapper.html";
        }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-11-21 16:42:27
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/CheckinLateEdit.html";
    }
    /**
         * 说明:跳转查看页面
         * @param request 发请求
         * @return String
         * @author dozen.zhang
         * @date 2018-11-21 16:42:27
         */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/CheckinLateView.html";
    }



    private CheckinLate getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       CheckinLate checkinLate =new  CheckinLate();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                checkinLate.setId(Long.valueOf(id));
        }
        String userId = MapUtils.getString(bodyParam,"userId");
        if(!StringUtil.isBlank(userId)){
                checkinLate.setUserId(Long.valueOf(userId));
        }
        String userName = MapUtils.getString(bodyParam,"userName");
        if(!StringUtil.isBlank(userName)){
                checkinLate.setUserName(String.valueOf(userName));
        }
        String checkType = MapUtils.getString(bodyParam,"checkType");
        if(!StringUtil.isBlank(checkType)){
                checkinLate.setCheckType(Integer.valueOf(checkType));
        }
        String checkTime = MapUtils.getString(bodyParam,"checkTime");
        if(!StringUtil.isBlank(checkTime)){
            if(StringUtil.checkNumeric(checkTime)){
                checkinLate.setCheckTime(Timestamp.valueOf(checkTime));
            }else if(StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")){
                checkinLate.setCheckTime(new Timestamp( DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String kqUserId = MapUtils.getString(bodyParam,"kqUserId");
        if(!StringUtil.isBlank(kqUserId)){
                checkinLate.setKqUserId(Long.valueOf(kqUserId));
        }

        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
        vu.add("userId", userId, "用户Id",  new Rule[]{new Digits(13,0),new NotEmpty()});
        vu.add("userName", userName, "用户名",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("checkType", checkType, "考勤类型",  new Rule[]{new Digits(3,0),new NotEmpty()});
        vu.add("checkTime", checkTime, "检查时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("kqUserId", kqUserId, "考勤机用户id",  new Rule[]{new Digits(13,0),new NotEmpty()});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  checkinLate;
    }


      /**
                     * 说明:添加CheckinLate信息
                     * @param request
                     * @throws Exception
                     * @return ResultDTO
                     * @author dozen.zhang
                     * @date 2018-11-21 16:42:27
                     */
                    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                    @API( summary="添加单个考勤迟到表信息",
                        description = "添加单个考勤迟到表信息",
                        parameters={
                           @Param(name="id" , description="编号",in=InType.body,dataType = DataType.LONG,required = false),
                           @Param(name="userId" , description="用户Id",in=InType.body,dataType = DataType.LONG,required = true),
                           @Param(name="userName" , description="用户名",in=InType.body,dataType = DataType.STRING,required = true),
                           @Param(name="checkType" , description="考勤类型",in=InType.body,dataType = DataType.INTEGER,required = true),
                           @Param(name="checkTime" , description="检查时间",in=InType.body,dataType = DataType.DATE_TIME,required = false),
                           @Param(name="kqUserId" , description="考勤机用户id",in=InType.body,dataType = DataType.LONG,required = true),
                        })
                    @RequestMapping(value = "add",method = RequestMethod.POST)
                    @ResponseBody
                    public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
                        CheckinLate checkinLate =    getInfoFromMap(bodyParam);


                        return checkinLateService.save(checkinLate);

                    }


    /**
    * 说明:添加CheckinLate信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2018-11-21 16:42:27
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个考勤迟到表信息",
    description = "更新单个考勤迟到表信息",
    parameters={
        @Param(name="id" , description="编号",in=InType.body,dataType = DataType.LONG,required = false),
        @Param(name="userId" , description="用户Id",in=InType.body,dataType = DataType.LONG,required = true),
        @Param(name="userName" , description="用户名",in=InType.body,dataType = DataType.STRING,required = true),
        @Param(name="checkType" , description="考勤类型",in=InType.body,dataType = DataType.INTEGER,required = true),
        @Param(name="checkTime" , description="检查时间",in=InType.body,dataType = DataType.DATE_TIME,required = false),
        @Param(name="kqUserId" , description="考勤机用户id",in=InType.body,dataType = DataType.LONG,required = true),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    CheckinLate checkinLate =    getInfoFromMap(bodyParam);
    return checkinLateService.save(checkinLate);

    }
/**
     * 说明:ajax请求CheckinLate信息
     * @author dozen.zhang
     * @date 2018-11-21 16:42:27
     * @return String
     */
       @API(summary="考勤迟到表列表接口",
                 description="考勤迟到表列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="userId" , description="用户Id",in=InType.params,dataType = DataType.LONG,required =false),// true
                    @Param(name="userName" , description="用户名",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="checkType" , description="考勤类型",in=InType.params,dataType = DataType.INTEGER,required =false),// true
                    @Param(name="checkTime" , description="检查时间",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="kqUserId" , description="考勤机用户id",in=InType.params,dataType = DataType.LONG,required =false),// true
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
        String userId = MapUtils.getString(params,"userId");
        if(!StringUtil.isBlank(userId)){
            params.put("userId",userId);
        }
        String userName = MapUtils.getString(params,"userName");
        if(!StringUtil.isBlank(userName)){
            params.put("userName",userName);
        }
        String userNameLike = MapUtils.getString(params,"userNameLike");
        if(!StringUtil.isBlank(userNameLike)){
            params.put("userNameLike",userNameLike);
        }
        String checkType = MapUtils.getString(params,"checkType");
        if(!StringUtil.isBlank(checkType)){
            params.put("checkType",checkType);
        }
        String checkTime = MapUtils.getString(params,"checkTime");
        if(!StringUtil.isBlank(checkTime)){
            if(StringUtil.checkNumeric(checkTime)){
                params.put("checkTime",checkTime);
            }else if(StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("checkTime",new Timestamp( DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String checkTimeBegin = MapUtils.getString(params,"checkTimeBegin");
        if(!StringUtil.isBlank(checkTimeBegin)){
            if(StringUtil.checkNumeric(checkTimeBegin)){
                params.put("checkTimeBegin",checkTimeBegin);
            }else if(StringUtil.checkDateStr(checkTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("checkTimeBegin",new Timestamp( DateUtil.parseToDate(checkTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String checkTimeEnd = MapUtils.getString(params,"checkTimeEnd");
        if(!StringUtil.isBlank(checkTimeEnd)){
            if(StringUtil.checkNumeric(checkTimeEnd)){
                params.put("checkTimeEnd",checkTimeEnd);
            }else if(StringUtil.checkDateStr(checkTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("checkTimeEnd",new Timestamp( DateUtil.parseToDate(checkTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String kqUserId = MapUtils.getString(params,"kqUserId");
        if(!StringUtil.isBlank(kqUserId)){
            params.put("kqUserId",kqUserId);
        }

        params.put("page",page);
        List<CheckinLate> checkinLates = checkinLateService.listByParams4Page(params);
        return ResultUtil.getResult(checkinLates, page);
    }



       /**
         * 导出
         * @param request
         * @return
         * @author dozen.zhang
         */
        @API(summary="考勤迟到表列表导出接口",
          description="考勤迟到表列表导出接口",
          parameters={
          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
             @Param(name="id" , description="编号",in=InType.params,dataType = DataType.LONG,required =false),// false
             @Param(name="userId" , description="用户Id",in=InType.params,dataType = DataType.LONG,required =false),// true
             @Param(name="userName" , description="用户名",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="checkType" , description="考勤类型",in=InType.params,dataType = DataType.INTEGER,required =false),// true
             @Param(name="checkTime" , description="检查时间",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
             @Param(name="kqUserId" , description="考勤机用户id",in=InType.params,dataType = DataType.LONG,required =false),// true
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
        String userId = MapUtils.getString(params,"userId");
        if(!StringUtil.isBlank(userId)){
            params.put("userId",userId);
        }
        String userName = MapUtils.getString(params,"userName");
        if(!StringUtil.isBlank(userName)){
            params.put("userName",userName);
        }
        String userNameLike = MapUtils.getString(params,"userNameLike");
        if(!StringUtil.isBlank(userNameLike)){
            params.put("userNameLike",userNameLike);
        }
        String checkType = MapUtils.getString(params,"checkType");
        if(!StringUtil.isBlank(checkType)){
            params.put("checkType",checkType);
        }
        String checkTime = MapUtils.getString(params,"checkTime");
        if(!StringUtil.isBlank(checkTime)){
            if(StringUtil.checkNumeric(checkTime)){
                params.put("checkTime",checkTime);
            }else if(StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("checkTime",new Timestamp( DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String checkTimeBegin = MapUtils.getString(params,"checkTimeBegin");
        if(!StringUtil.isBlank(checkTimeBegin)){
            if(StringUtil.checkNumeric(checkTimeBegin)){
                params.put("checkTimeBegin",checkTimeBegin);
            }else if(StringUtil.checkDateStr(checkTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("checkTimeBegin",new Timestamp( DateUtil.parseToDate(checkTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String checkTimeEnd = MapUtils.getString(params,"checkTimeEnd");
        if(!StringUtil.isBlank(checkTimeEnd)){
            if(StringUtil.checkNumeric(checkTimeEnd)){
                params.put("checkTimeEnd",checkTimeEnd);
            }else if(StringUtil.checkDateStr(checkTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("checkTimeEnd",new Timestamp( DateUtil.parseToDate(checkTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String kqUserId = MapUtils.getString(params,"kqUserId");
        if(!StringUtil.isBlank(kqUserId)){
            params.put("kqUserId",kqUserId);
        }

             params.put("page",page);
             List<CheckinLate> list = checkinLateService.listByParams4Page(params);
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
            colTitle.put("userId", "用户Id");
            colTitle.put("userName", "用户名");
            colTitle.put("checkType", "考勤类型");
            colTitle.put("checkTime", "检查时间");
            colTitle.put("kqUserId", "考勤机用户id");
            List<Map> finalList = new ArrayList<Map>();
            for (int i = 0; i < list.size(); i++) {
                CheckinLate sm = list.get(i);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id",  list.get(i).getId());
                map.put("userId",  list.get(i).getUserId());
                map.put("userName",  list.get(i).getUserName());
                map.put("checkType",  list.get(i).getCheckType());
                map.put("checkTime",  list.get(i).getCheckTime());
                map.put("kqUserId",  list.get(i).getKqUserId());
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
