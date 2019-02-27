/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2019-2-27 16:45:06
 * 文件说明: 
 */

package com.dozenx.web.module.pubComment.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;
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
import com.dozenx.web.module.pubComment.service.PubCommentService;
import com.dozenx.web.module.pubComment.bean.PubComment;
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

@APIs(description = "评论")
@Controller
@RequestMapping("/pub/comment")
public class PubCommentController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(PubCommentController.class);
    /** 权限service **/
    @Autowired
    private PubCommentService pubCommentService;
    


    /**
     * 说明:ajax请求PubComment信息
     * @author dozen.zhang
     * @date 2019-2-27 16:45:06
     * @return String
     */
       @API(summary="评论列表接口",
                 description="评论列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="主键",dataType = DataType.LONG,required =false),// false
                    @Param(name="pid" , description="所属对象id",dataType = DataType.LONG,required =false),// true
                    @Param(name="content" , description="正文",dataType = DataType.STRING,required =false),// true
                    @Param(name="type" , description="类型",dataType = DataType.INTEGER,required =false),// true
                    @Param(name="status" , description="状态",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="createUser" , description="创建人",dataType = DataType.LONG,required =false),// false
                    @Param(name="userName" , description="创建人姓名",dataType = DataType.STRING,required =false),// false
                    @Param(name="face" , description="头像",dataType = DataType.STRING,required =false),// false
                    @Param(name="pic" , description="图片",dataType = DataType.STRING,required =false),// false
                    @Param(name="createtime" , description="创建时间",dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="updatetime" , description="更新时间",dataType = DataType.DATE_TIME,required =false),// false
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
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = request.getParameter("contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            params.put("createUser",createUser);
        }
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            params.put("userName",userName);
        }
        String userNameLike = request.getParameter("userNameLike");
        if(!StringUtil.isBlank(userNameLike)){
            params.put("userNameLike",userNameLike);
        }
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            params.put("face",face);
        }
        String faceLike = request.getParameter("faceLike");
        if(!StringUtil.isBlank(faceLike)){
            params.put("faceLike",faceLike);
        }
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            params.put("pic",pic);
        }
        String picLike = request.getParameter("picLike");
        if(!StringUtil.isBlank(picLike)){
            params.put("picLike",picLike);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                params.put("createtime",createtime);
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtime",new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if(!StringUtil.isBlank(createtimeBegin)){
            if(StringUtil.checkNumeric(createtimeBegin)){
                params.put("createtimeBegin",createtimeBegin);
            }else if(StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeBegin",new Timestamp( DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if(!StringUtil.isBlank(createtimeEnd)){
            if(StringUtil.checkNumeric(createtimeEnd)){
                params.put("createtimeEnd",createtimeEnd);
            }else if(StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeEnd",new Timestamp( DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                params.put("updatetime",updatetime);
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetime",new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = request.getParameter("updatetimeBegin");
        if(!StringUtil.isBlank(updatetimeBegin)){
            if(StringUtil.checkNumeric(updatetimeBegin)){
                params.put("updatetimeBegin",updatetimeBegin);
            }else if(StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeBegin",new Timestamp( DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = request.getParameter("updatetimeEnd");
        if(!StringUtil.isBlank(updatetimeEnd)){
            if(StringUtil.checkNumeric(updatetimeEnd)){
                params.put("updatetimeEnd",updatetimeEnd);
            }else if(StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeEnd",new Timestamp( DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page",page);
        List<PubComment> pubComments = pubCommentService.listByParams4Page(params);
        return ResultUtil.getResult(pubComments, page);
    }
    
   /**
    * 说明:ajax请求PubComment信息 无分页版本
    * @return ResultDTO 返回结果
    * @author dozen.zhang
    * @date 2019-2-27 16:45:06
    */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
                HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = request.getParameter("contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            params.put("createUser",createUser);
        }
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            params.put("userName",userName);
        }
        String userNameLike = request.getParameter("userNameLike");
        if(!StringUtil.isBlank(userNameLike)){
            params.put("userNameLike",userNameLike);
        }
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            params.put("face",face);
        }
        String faceLike = request.getParameter("faceLike");
        if(!StringUtil.isBlank(faceLike)){
            params.put("faceLike",faceLike);
        }
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            params.put("pic",pic);
        }
        String picLike = request.getParameter("picLike");
        if(!StringUtil.isBlank(picLike)){
            params.put("picLike",picLike);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                params.put("createtime",createtime);
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtime",new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if(!StringUtil.isBlank(createtimeBegin)){
            if(StringUtil.checkNumeric(createtimeBegin)){
                params.put("createtimeBegin",createtimeBegin);
            }else if(StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeBegin",new Timestamp( DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if(!StringUtil.isBlank(createtimeEnd)){
            if(StringUtil.checkNumeric(createtimeEnd)){
                params.put("createtimeEnd",createtimeEnd);
            }else if(StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeEnd",new Timestamp( DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                params.put("updatetime",updatetime);
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetime",new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = request.getParameter("updatetimeBegin");
        if(!StringUtil.isBlank(updatetimeBegin)){
            if(StringUtil.checkNumeric(updatetimeBegin)){
                params.put("updatetimeBegin",updatetimeBegin);
            }else if(StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeBegin",new Timestamp( DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = request.getParameter("updatetimeEnd");
        if(!StringUtil.isBlank(updatetimeEnd)){
            if(StringUtil.checkNumeric(updatetimeEnd)){
                params.put("updatetimeEnd",updatetimeEnd);
            }else if(StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeEnd",new Timestamp( DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        List<PubComment> pubComments = pubCommentService.listByParams(params);
        return ResultUtil.getDataResult(pubComments);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2019-2-27 16:45:06
    */
     @API( summary="根据id查询单个评论信息",
               description = "根据id查询单个评论信息",
               parameters={
                       @Param(name="id" , description="id",in=InType.path,dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(@PathVariable Long id,HttpServletRequest request) {
                     HashMap<String,Object> result =new HashMap</*String,Object*/>();
            PubComment bean = pubCommentService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
            return this.getResult(result);

        }



     /**
        * 说明:查看单条信息
        * @param request 发请求
        * @return String
        * @author dozen.zhang
        * @date 2019-2-27 16:45:06
        */
      @API( summary="根据id查询单个评论信息",
               description = "根据id查询单个评论信息",
               parameters={
                       @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view.json",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(HttpServletRequest request) {
         String id = request.getParameter("id");
            PubComment bean = pubCommentService.selectByPrimaryKey(Long.valueOf(id));
          //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
           // result.put("bean", bean);
            return this.getResult(bean);
        }


    /**
     * 说明:更新PubComment信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2019-2-27 16:45:06
     */
      @API( summary="更新id更新单个评论信息",
        description = "更新id更新单个评论信息",
        parameters={
           @Param(name="id" , description="主键",type="LONG",required = false),
           @Param(name="pid" , description="所属对象id",type="LONG",required = true),
           @Param(name="content" , description="正文",type="STRING",required = true),
           @Param(name="type" , description="类型",type="INTEGER",required = true),
           @Param(name="status" , description="状态",type="INTEGER",required = false),
           @Param(name="createUser" , description="创建人",type="LONG",required = false),
           @Param(name="userName" , description="创建人姓名",type="STRING",required = false),
           @Param(name="face" , description="头像",type="STRING",required = false),
           @Param(name="pic" , description="图片",type="STRING",required = false),
           @Param(name="createtime" , description="创建时间",type="DATE_TIME",required = false),
           @Param(name="updatetime" , description="更新时间",type="DATE_TIME",required = false),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update.json",method = RequestMethod.PUT)///{id}
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Long id,
        PubComment pubComment =new  PubComment();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            pubComment.setId(Long.valueOf(id)) ;
        }
        
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            pubComment.setPid(Long.valueOf(pid)) ;
        }
        
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            pubComment.setContent(String.valueOf(content)) ;
        }
        
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            pubComment.setType(Integer.valueOf(type)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            pubComment.setStatus(Integer.valueOf(status)) ;
        }
        
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            pubComment.setCreateUser(Long.valueOf(createUser)) ;
        }
        
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            pubComment.setUserName(String.valueOf(userName)) ;
        }
        
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            pubComment.setFace(String.valueOf(face)) ;
        }
        
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            pubComment.setPic(String.valueOf(pic)) ;
        }
        
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            pubComment.setCreatetime(Timestamp.valueOf(createtime)) ;
        }
        
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            pubComment.setUpdatetime(Timestamp.valueOf(updatetime)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            pubComment.setId(Long.valueOf(id));
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            pubComment.setPid(Long.valueOf(pid));
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            pubComment.setContent(content);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            pubComment.setType(Integer.valueOf(type));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            pubComment.setStatus(Integer.valueOf(status));
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            pubComment.setCreateUser(Long.valueOf(createUser));
        }
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            pubComment.setUserName(userName);
        }
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            pubComment.setFace(face);
        }
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            pubComment.setPic(pic);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                pubComment.setCreatetime(Timestamp.valueOf(createtime));
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                pubComment.setCreatetime(new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                pubComment.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                pubComment.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(9,0)});
        vu.add("pid", pid, "所属对象id",  new Rule[]{new Digits(9,0),new NotEmpty()});
        vu.add("content", content, "正文",  new Rule[]{new Length(500),new NotEmpty()});
        vu.add("type", type, "类型",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3"}),new NotEmpty()});
        vu.add("status", status, "状态",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3","4","5"})});
        vu.add("createUser", createUser, "创建人",  new Rule[]{new Digits(11,0)});
        vu.add("userName", userName, "创建人姓名",  new Rule[]{new Length(20)});
        vu.add("face", face, "头像",  new Rule[]{new Length(50)});
        vu.add("pic", pic, "图片",  new Rule[]{new Length(250)});
        vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return pubCommentService.save(pubComment);
       
    }


        /**
         * 说明:添加PubComment信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-2-27 16:45:06
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个评论信息",
            description = "添加单个评论信息",
            parameters={
               @Param(name="id" , description="主键",dataType = DataType.LONG,required = false),
               @Param(name="pid" , description="所属对象id",dataType = DataType.LONG,required = true),
               @Param(name="content" , description="正文",dataType = DataType.STRING,required = true),
               @Param(name="type" , description="类型",dataType = DataType.INTEGER,required = true),
               @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = false),
               @Param(name="createUser" , description="创建人",dataType = DataType.LONG,required = false),
               @Param(name="userName" , description="创建人姓名",dataType = DataType.STRING,required = false),
               @Param(name="face" , description="头像",dataType = DataType.STRING,required = false),
               @Param(name="pic" , description="图片",dataType = DataType.STRING,required = false),
               @Param(name="createtime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
               @Param(name="updatetime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
            })
        @RequestMapping(value = "add.json",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            PubComment pubComment =new  PubComment();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                pubComment.setId(Long.valueOf(id)) ;
            }
            
            String pid = request.getParameter("pid");
            if(!StringUtil.isBlank(pid)){
                pubComment.setPid(Long.valueOf(pid)) ;
            }
            
            String content = request.getParameter("content");
            if(!StringUtil.isBlank(content)){
                pubComment.setContent(String.valueOf(content)) ;
            }
            
            String type = request.getParameter("type");
            if(!StringUtil.isBlank(type)){
                pubComment.setType(Integer.valueOf(type)) ;
            }
            
            String status = request.getParameter("status");
            if(!StringUtil.isBlank(status)){
                pubComment.setStatus(Integer.valueOf(status)) ;
            }
            
            String createUser = request.getParameter("createUser");
            if(!StringUtil.isBlank(createUser)){
                pubComment.setCreateUser(Long.valueOf(createUser)) ;
            }
            
            String userName = request.getParameter("userName");
            if(!StringUtil.isBlank(userName)){
                pubComment.setUserName(String.valueOf(userName)) ;
            }
            
            String face = request.getParameter("face");
            if(!StringUtil.isBlank(face)){
                pubComment.setFace(String.valueOf(face)) ;
            }
            
            String pic = request.getParameter("pic");
            if(!StringUtil.isBlank(pic)){
                pubComment.setPic(String.valueOf(pic)) ;
            }
            
            String createtime = request.getParameter("createtime");
            if(!StringUtil.isBlank(createtime)){
                pubComment.setCreatetime(Timestamp.valueOf(createtime)) ;
            }
            
            String updatetime = request.getParameter("updatetime");
            if(!StringUtil.isBlank(updatetime)){
                pubComment.setUpdatetime(Timestamp.valueOf(updatetime)) ;
            }
            */
            String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            pubComment.setId(Long.valueOf(id));
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            pubComment.setPid(Long.valueOf(pid));
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            pubComment.setContent(content);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            pubComment.setType(Integer.valueOf(type));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            pubComment.setStatus(Integer.valueOf(status));
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            pubComment.setCreateUser(Long.valueOf(createUser));
        }
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            pubComment.setUserName(userName);
        }
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            pubComment.setFace(face);
        }
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            pubComment.setPic(pic);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                pubComment.setCreatetime(Timestamp.valueOf(createtime));
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                pubComment.setCreatetime(new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                pubComment.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                pubComment.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(9,0)});
        vu.add("pid", pid, "所属对象id",  new Rule[]{new Digits(9,0),new NotEmpty()});
        vu.add("content", content, "正文",  new Rule[]{new Length(500),new NotEmpty()});
        vu.add("type", type, "类型",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3"}),new NotEmpty()});
        vu.add("status", status, "状态",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3","4","5"})});
        vu.add("createUser", createUser, "创建人",  new Rule[]{new Digits(11,0)});
        vu.add("userName", userName, "创建人姓名",  new Rule[]{new Length(20)});
        vu.add("face", face, "头像",  new Rule[]{new Length(50)});
        vu.add("pic", pic, "图片",  new Rule[]{new Length(250)});
        vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

            return pubCommentService.save(pubComment);

        }


          /**
                 * 说明:添加PubComment信息
                 * @param request
                 * @throws Exception
                 * @return ResultDTO
                 * @author dozen.zhang
                 * @date 2019-2-27 16:45:06
                 */
                // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                @API( summary="添加单个评论信息",
                    description = "添加单个评论信息",
                    parameters={
                       @Param(name="id" , description="主键",dataType = DataType.LONG,required = false),
                       @Param(name="pid" , description="所属对象id",dataType = DataType.LONG,required = true),
                       @Param(name="content" , description="正文",dataType = DataType.STRING,required = true),
                       @Param(name="type" , description="类型",dataType = DataType.INTEGER,required = true),
                       @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = false),
                       @Param(name="createUser" , description="创建人",dataType = DataType.LONG,required = false),
                       @Param(name="userName" , description="创建人姓名",dataType = DataType.STRING,required = false),
                       @Param(name="face" , description="头像",dataType = DataType.STRING,required = false),
                       @Param(name="pic" , description="图片",dataType = DataType.STRING,required = false),
                       @Param(name="createtime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
                       @Param(name="updatetime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
                    })
                @RequestMapping(value = "save.json",method = RequestMethod.POST)
                @ResponseBody
                public ResultDTO save(HttpServletRequest request) throws Exception {
                    PubComment pubComment =new  PubComment();
                    /*
                    String id = request.getParameter("id");
                    if(!StringUtil.isBlank(id)){
                        pubComment.setId(Long.valueOf(id)) ;
                    }
                    
                    String pid = request.getParameter("pid");
                    if(!StringUtil.isBlank(pid)){
                        pubComment.setPid(Long.valueOf(pid)) ;
                    }
                    
                    String content = request.getParameter("content");
                    if(!StringUtil.isBlank(content)){
                        pubComment.setContent(String.valueOf(content)) ;
                    }
                    
                    String type = request.getParameter("type");
                    if(!StringUtil.isBlank(type)){
                        pubComment.setType(Integer.valueOf(type)) ;
                    }
                    
                    String status = request.getParameter("status");
                    if(!StringUtil.isBlank(status)){
                        pubComment.setStatus(Integer.valueOf(status)) ;
                    }
                    
                    String createUser = request.getParameter("createUser");
                    if(!StringUtil.isBlank(createUser)){
                        pubComment.setCreateUser(Long.valueOf(createUser)) ;
                    }
                    
                    String userName = request.getParameter("userName");
                    if(!StringUtil.isBlank(userName)){
                        pubComment.setUserName(String.valueOf(userName)) ;
                    }
                    
                    String face = request.getParameter("face");
                    if(!StringUtil.isBlank(face)){
                        pubComment.setFace(String.valueOf(face)) ;
                    }
                    
                    String pic = request.getParameter("pic");
                    if(!StringUtil.isBlank(pic)){
                        pubComment.setPic(String.valueOf(pic)) ;
                    }
                    
                    String createtime = request.getParameter("createtime");
                    if(!StringUtil.isBlank(createtime)){
                        pubComment.setCreatetime(Timestamp.valueOf(createtime)) ;
                    }
                    
                    String updatetime = request.getParameter("updatetime");
                    if(!StringUtil.isBlank(updatetime)){
                        pubComment.setUpdatetime(Timestamp.valueOf(updatetime)) ;
                    }
                    */
                    String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            pubComment.setId(Long.valueOf(id));
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            pubComment.setPid(Long.valueOf(pid));
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            pubComment.setContent(content);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            pubComment.setType(Integer.valueOf(type));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            pubComment.setStatus(Integer.valueOf(status));
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            pubComment.setCreateUser(Long.valueOf(createUser));
        }
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            pubComment.setUserName(userName);
        }
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            pubComment.setFace(face);
        }
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            pubComment.setPic(pic);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                pubComment.setCreatetime(Timestamp.valueOf(createtime));
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                pubComment.setCreatetime(new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                pubComment.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                pubComment.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

                    //valid
                    ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(9,0)});
        vu.add("pid", pid, "所属对象id",  new Rule[]{new Digits(9,0),new NotEmpty()});
        vu.add("content", content, "正文",  new Rule[]{new Length(500),new NotEmpty()});
        vu.add("type", type, "类型",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3"}),new NotEmpty()});
        vu.add("status", status, "状态",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3","4","5"})});
        vu.add("createUser", createUser, "创建人",  new Rule[]{new Digits(11,0)});
        vu.add("userName", userName, "创建人姓名",  new Rule[]{new Length(20)});
        vu.add("face", face, "头像",  new Rule[]{new Length(50)});
        vu.add("pic", pic, "图片",  new Rule[]{new Length(250)});
        vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

                    return pubCommentService.save(pubComment);

                }

    /**
     * 说明:删除PubComment信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2019-2-27 16:45:06
     */
     @API( summary="根据id删除单个评论信息",
        description = "根据id删除单个评论信息",
        parameters={
         @Param(name="id" , description="主键",dataType= DataType.LONG,required = true),
        })
    @RequestMapping(value = "/delete.json",method = RequestMethod.DELETE)//{id}
    @ResponseBody
    public ResultDTO delete(HttpServletRequest request) {//@PathVariable Long id,
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        pubCommentService.delete(id);
        return this.getResult(SUCC);
    }

     /**
         * 说明:删除PubComment信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-2-27 16:45:06
         */
         @API( summary="根据id删除单个评论信息",
            description = "根据id删除单个评论信息",
            parameters={
             @Param(name="id" , description="主键",in=InType.path,dataType= DataType.LONG,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable Long id,HttpServletRequest request) {
            pubCommentService.delete(id);
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
                    vu.add("id", id, "主键",  new Rule[]{});

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
       return  pubCommentService.multilDelete(idAry);
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
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = request.getParameter("contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            params.put("createUser",createUser);
        }
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            params.put("userName",userName);
        }
        String userNameLike = request.getParameter("userNameLike");
        if(!StringUtil.isBlank(userNameLike)){
            params.put("userNameLike",userNameLike);
        }
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            params.put("face",face);
        }
        String faceLike = request.getParameter("faceLike");
        if(!StringUtil.isBlank(faceLike)){
            params.put("faceLike",faceLike);
        }
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            params.put("pic",pic);
        }
        String picLike = request.getParameter("picLike");
        if(!StringUtil.isBlank(picLike)){
            params.put("picLike",picLike);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                params.put("createtime",createtime);
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtime",new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if(!StringUtil.isBlank(createtimeBegin)){
            if(StringUtil.checkNumeric(createtimeBegin)){
                params.put("createtimeBegin",createtimeBegin);
            }else if(StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeBegin",new Timestamp( DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if(!StringUtil.isBlank(createtimeEnd)){
            if(StringUtil.checkNumeric(createtimeEnd)){
                params.put("createtimeEnd",createtimeEnd);
            }else if(StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeEnd",new Timestamp( DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                params.put("updatetime",updatetime);
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetime",new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = request.getParameter("updatetimeBegin");
        if(!StringUtil.isBlank(updatetimeBegin)){
            if(StringUtil.checkNumeric(updatetimeBegin)){
                params.put("updatetimeBegin",updatetimeBegin);
            }else if(StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeBegin",new Timestamp( DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = request.getParameter("updatetimeEnd");
        if(!StringUtil.isBlank(updatetimeEnd)){
            if(StringUtil.checkNumeric(updatetimeEnd)){
                params.put("updatetimeEnd",updatetimeEnd);
            }else if(StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeEnd",new Timestamp( DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        // 查询list集合
        List<PubComment> list =pubCommentService.listByParams(params);
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
        colTitle.put("pid", "所属对象id");
        colTitle.put("content", "正文");
        colTitle.put("type", "类型");
        colTitle.put("status", "状态");
        colTitle.put("createUser", "创建人");
        colTitle.put("userName", "创建人姓名");
        colTitle.put("face", "头像");
        colTitle.put("pic", "图片");
        colTitle.put("createtime", "创建时间");
        colTitle.put("updatetime", "更新时间");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            PubComment sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("pid",  list.get(i).getPid());
            map.put("content",  list.get(i).getContent());
            map.put("type",  list.get(i).getType());
            map.put("status",  list.get(i).getStatus());
            map.put("createUser",  list.get(i).getCreateUser());
            map.put("userName",  list.get(i).getUserName());
            map.put("face",  list.get(i).getFace());
            map.put("pic",  list.get(i).getPic());
            map.put("createtime",  list.get(i).getCreatetime());
            map.put("updatetime",  list.get(i).getUpdatetime());
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

                        PubComment bean = getInfoFromMap(params);

                      //  if (count > 0) {

                      //      logger.warn("邮箱已经存在:" + email);
                       //     errorMsg.append("邮箱已经存在:" + email);
                         //   continue;

                       // }

                        try {
                            pubCommentService.save(bean);
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
         * 说明: 跳转到PubComment列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/PubCommentList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/PubCommentListMapper.html";
        }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2019-2-27 16:45:06
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/PubCommentEdit.html";
    }
    /**
         * 说明:跳转查看页面
         * @param request 发请求
         * @return String
         * @author dozen.zhang
         * @date 2019-2-27 16:45:06
         */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/PubCommentView.html";
    }



    private PubComment getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       PubComment pubComment =new  PubComment();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                pubComment.setId(Long.valueOf(id));
        }
        String pid = MapUtils.getString(bodyParam,"pid");
        if(!StringUtil.isBlank(pid)){
                pubComment.setPid(Long.valueOf(pid));
        }
        String content = MapUtils.getString(bodyParam,"content");
        if(!StringUtil.isBlank(content)){
                pubComment.setContent(String.valueOf(content));
        }
        String type = MapUtils.getString(bodyParam,"type");
        if(!StringUtil.isBlank(type)){
                pubComment.setType(Integer.valueOf(type));
        }
        String status = MapUtils.getString(bodyParam,"status");
        if(!StringUtil.isBlank(status)){
                pubComment.setStatus(Integer.valueOf(status));
        }
        String createUser = MapUtils.getString(bodyParam,"createUser");
        if(!StringUtil.isBlank(createUser)){
                pubComment.setCreateUser(Long.valueOf(createUser));
        }
        String userName = MapUtils.getString(bodyParam,"userName");
        if(!StringUtil.isBlank(userName)){
                pubComment.setUserName(String.valueOf(userName));
        }
        String face = MapUtils.getString(bodyParam,"face");
        if(!StringUtil.isBlank(face)){
                pubComment.setFace(String.valueOf(face));
        }
        String pic = MapUtils.getString(bodyParam,"pic");
        if(!StringUtil.isBlank(pic)){
                pubComment.setPic(String.valueOf(pic));
        }
        String createtime = MapUtils.getString(bodyParam,"createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                pubComment.setCreatetime(Timestamp.valueOf(createtime));
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                pubComment.setCreatetime(new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = MapUtils.getString(bodyParam,"updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                pubComment.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                pubComment.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(9,0)});
        vu.add("pid", pid, "所属对象id",  new Rule[]{new Digits(9,0),new NotEmpty()});
        vu.add("content", content, "正文",  new Rule[]{new Length(500),new NotEmpty()});
        vu.add("type", type, "类型",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3"}),new NotEmpty()});
        vu.add("status", status, "状态",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3","4","5"})});
        vu.add("createUser", createUser, "创建人",  new Rule[]{new Digits(11,0)});
        vu.add("userName", userName, "创建人姓名",  new Rule[]{new Length(20)});
        vu.add("face", face, "头像",  new Rule[]{new Length(50)});
        vu.add("pic", pic, "图片",  new Rule[]{new Length(250)});
        vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  pubComment;
    }


      /**
                     * 说明:添加PubComment信息
                     * @param request
                     * @throws Exception
                     * @return ResultDTO
                     * @author dozen.zhang
                     * @date 2019-2-27 16:45:06
                     */
                    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                    @API( summary="添加单个评论信息",
                        description = "添加单个评论信息",
                        parameters={
                           @Param(name="id" , description="主键"  ,in=InType.body,dataType = DataType.LONG,required = false),
                           @Param(name="pid" , description="所属对象id"  ,in=InType.body,dataType = DataType.LONG,required = true),
                           @Param(name="content" , description="正文"  ,in=InType.body,dataType = DataType.STRING,required = true),
                           @Param(name="type" , description="类型"  ,in=InType.body,dataType = DataType.INTEGER,required = true),
                           @Param(name="status" , description="状态"  ,in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="createUser" , description="创建人"  ,in=InType.body,dataType = DataType.LONG,required = false),
                           @Param(name="userName" , description="创建人姓名"  ,in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="face" , description="头像"  ,in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="pic" , description="图片"  ,in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="createtime" , description="创建时间"  ,in=InType.body,dataType = DataType.DATE_TIME,required = false),
                           @Param(name="updatetime" , description="更新时间"  ,in=InType.body,dataType = DataType.DATE_TIME,required = false),
                        })
                    @RequestMapping(value = "add",method = RequestMethod.POST)
                    @ResponseBody
                    public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
                        PubComment pubComment =    getInfoFromMap(bodyParam);


                        return pubCommentService.save(pubComment);

                    }


    /**
    * 说明:添加PubComment信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2019-2-27 16:45:06
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个评论信息",
    description = "更新单个评论信息",
    parameters={
        @Param(name="id" , description="主键  ",in=InType.body,dataType = DataType.LONG,required = false),
        @Param(name="pid" , description="所属对象id  ",in=InType.body,dataType = DataType.LONG,required = true),
        @Param(name="content" , description="正文  ",in=InType.body,dataType = DataType.STRING,required = true),
        @Param(name="type" , description="类型  ",in=InType.body,dataType = DataType.INTEGER,required = true),
        @Param(name="status" , description="状态  ",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="createUser" , description="创建人  ",in=InType.body,dataType = DataType.LONG,required = false),
        @Param(name="userName" , description="创建人姓名  ",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="face" , description="头像  ",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="pic" , description="图片  ",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="createtime" , description="创建时间  ",in=InType.body,dataType = DataType.DATE_TIME,required = false),
        @Param(name="updatetime" , description="更新时间  ",in=InType.body,dataType = DataType.DATE_TIME,required = false),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    PubComment pubComment =    getInfoFromMap(bodyParam);
    return pubCommentService.save(pubComment);

    }
/**
     * 说明:ajax请求PubComment信息
     * @author dozen.zhang
     * @date 2019-2-27 16:45:06
     * @return String
     */
       @API(summary="评论列表接口",
                 description="评论列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="主键  ",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="pid" , description="所属对象id  ",in=InType.params,dataType = DataType.LONG,required =false),// true
                    @Param(name="content" , description="正文  ",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="type" , description="类型  ",in=InType.params,dataType = DataType.INTEGER,required =false),// true
                    @Param(name="status" , description="状态  ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="createUser" , description="创建人  ",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="userName" , description="创建人姓名  ",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="face" , description="头像  ",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="pic" , description="图片  ",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="createtime" , description="创建时间  ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="updatetime" , description="更新时间  ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
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
        String pid = MapUtils.getString(params,"pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String content = MapUtils.getString(params,"content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = MapUtils.getString(params,"contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String type = MapUtils.getString(params,"type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = MapUtils.getString(params,"status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String createUser = MapUtils.getString(params,"createUser");
        if(!StringUtil.isBlank(createUser)){
            params.put("createUser",createUser);
        }
        String userName = MapUtils.getString(params,"userName");
        if(!StringUtil.isBlank(userName)){
            params.put("userName",userName);
        }
        String userNameLike = MapUtils.getString(params,"userNameLike");
        if(!StringUtil.isBlank(userNameLike)){
            params.put("userNameLike",userNameLike);
        }
        String face = MapUtils.getString(params,"face");
        if(!StringUtil.isBlank(face)){
            params.put("face",face);
        }
        String faceLike = MapUtils.getString(params,"faceLike");
        if(!StringUtil.isBlank(faceLike)){
            params.put("faceLike",faceLike);
        }
        String pic = MapUtils.getString(params,"pic");
        if(!StringUtil.isBlank(pic)){
            params.put("pic",pic);
        }
        String picLike = MapUtils.getString(params,"picLike");
        if(!StringUtil.isBlank(picLike)){
            params.put("picLike",picLike);
        }
        String createtime = MapUtils.getString(params,"createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                params.put("createtime",createtime);
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtime",new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = MapUtils.getString(params,"createtimeBegin");
        if(!StringUtil.isBlank(createtimeBegin)){
            if(StringUtil.checkNumeric(createtimeBegin)){
                params.put("createtimeBegin",createtimeBegin);
            }else if(StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeBegin",new Timestamp( DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = MapUtils.getString(params,"createtimeEnd");
        if(!StringUtil.isBlank(createtimeEnd)){
            if(StringUtil.checkNumeric(createtimeEnd)){
                params.put("createtimeEnd",createtimeEnd);
            }else if(StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeEnd",new Timestamp( DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = MapUtils.getString(params,"updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                params.put("updatetime",updatetime);
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetime",new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = MapUtils.getString(params,"updatetimeBegin");
        if(!StringUtil.isBlank(updatetimeBegin)){
            if(StringUtil.checkNumeric(updatetimeBegin)){
                params.put("updatetimeBegin",updatetimeBegin);
            }else if(StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeBegin",new Timestamp( DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = MapUtils.getString(params,"updatetimeEnd");
        if(!StringUtil.isBlank(updatetimeEnd)){
            if(StringUtil.checkNumeric(updatetimeEnd)){
                params.put("updatetimeEnd",updatetimeEnd);
            }else if(StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeEnd",new Timestamp( DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page",page);
        List<PubComment> pubComments = pubCommentService.listByParams4Page(params);
        return ResultUtil.getResult(pubComments, page);
    }



       /**
         * 导出
         * @param request
         * @return
         * @author dozen.zhang
         */
        @API(summary="评论列表导出接口",
          description="评论列表导出接口",
          parameters={
          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
             @Param(name="id" , description="主键 ",in=InType.params,dataType = DataType.LONG,required =false),// false
             @Param(name="pid" , description="所属对象id ",in=InType.params,dataType = DataType.LONG,required =false),// true
             @Param(name="content" , description="正文 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="type" , description="类型 ",in=InType.params,dataType = DataType.INTEGER,required =false),// true
             @Param(name="status" , description="状态 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="createUser" , description="创建人 ",in=InType.params,dataType = DataType.LONG,required =false),// false
             @Param(name="userName" , description="创建人姓名 ",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="face" , description="头像 ",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="pic" , description="图片 ",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="createtime" , description="创建时间 ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
             @Param(name="updatetime" , description="更新时间 ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
          })
        @RequestMapping(value = "/export", method = RequestMethod.GET)
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
        String pid = MapUtils.getString(params,"pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String content = MapUtils.getString(params,"content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = MapUtils.getString(params,"contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String type = MapUtils.getString(params,"type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = MapUtils.getString(params,"status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String createUser = MapUtils.getString(params,"createUser");
        if(!StringUtil.isBlank(createUser)){
            params.put("createUser",createUser);
        }
        String userName = MapUtils.getString(params,"userName");
        if(!StringUtil.isBlank(userName)){
            params.put("userName",userName);
        }
        String userNameLike = MapUtils.getString(params,"userNameLike");
        if(!StringUtil.isBlank(userNameLike)){
            params.put("userNameLike",userNameLike);
        }
        String face = MapUtils.getString(params,"face");
        if(!StringUtil.isBlank(face)){
            params.put("face",face);
        }
        String faceLike = MapUtils.getString(params,"faceLike");
        if(!StringUtil.isBlank(faceLike)){
            params.put("faceLike",faceLike);
        }
        String pic = MapUtils.getString(params,"pic");
        if(!StringUtil.isBlank(pic)){
            params.put("pic",pic);
        }
        String picLike = MapUtils.getString(params,"picLike");
        if(!StringUtil.isBlank(picLike)){
            params.put("picLike",picLike);
        }
        String createtime = MapUtils.getString(params,"createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                params.put("createtime",createtime);
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtime",new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = MapUtils.getString(params,"createtimeBegin");
        if(!StringUtil.isBlank(createtimeBegin)){
            if(StringUtil.checkNumeric(createtimeBegin)){
                params.put("createtimeBegin",createtimeBegin);
            }else if(StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeBegin",new Timestamp( DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = MapUtils.getString(params,"createtimeEnd");
        if(!StringUtil.isBlank(createtimeEnd)){
            if(StringUtil.checkNumeric(createtimeEnd)){
                params.put("createtimeEnd",createtimeEnd);
            }else if(StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeEnd",new Timestamp( DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = MapUtils.getString(params,"updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                params.put("updatetime",updatetime);
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetime",new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = MapUtils.getString(params,"updatetimeBegin");
        if(!StringUtil.isBlank(updatetimeBegin)){
            if(StringUtil.checkNumeric(updatetimeBegin)){
                params.put("updatetimeBegin",updatetimeBegin);
            }else if(StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeBegin",new Timestamp( DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = MapUtils.getString(params,"updatetimeEnd");
        if(!StringUtil.isBlank(updatetimeEnd)){
            if(StringUtil.checkNumeric(updatetimeEnd)){
                params.put("updatetimeEnd",updatetimeEnd);
            }else if(StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeEnd",new Timestamp( DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

             params.put("page",page);
             List<PubComment> list = pubCommentService.listByParams4Page(params);
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
            colTitle.put("id", "主键");
            colTitle.put("pid", "所属对象id");
            colTitle.put("content", "正文");
            colTitle.put("type", "类型");
            colTitle.put("status", "状态");
            colTitle.put("createUser", "创建人");
            colTitle.put("userName", "创建人姓名");
            colTitle.put("face", "头像");
            colTitle.put("pic", "图片");
            colTitle.put("createtime", "创建时间");
            colTitle.put("updatetime", "更新时间");
            List<Map> finalList = new ArrayList<Map>();
            for (int i = 0; i < list.size(); i++) {
                PubComment sm = list.get(i);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id",  list.get(i).getId());
                map.put("pid",  list.get(i).getPid());
                map.put("content",  list.get(i).getContent());
                map.put("type",  list.get(i).getType());
                map.put("status",  list.get(i).getStatus());
                map.put("createUser",  list.get(i).getCreateUser());
                map.put("userName",  list.get(i).getUserName());
                map.put("face",  list.get(i).getFace());
                map.put("pic",  list.get(i).getPic());
                map.put("createtime",  list.get(i).getCreatetime());
                map.put("updatetime",  list.get(i).getUpdatetime());
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
