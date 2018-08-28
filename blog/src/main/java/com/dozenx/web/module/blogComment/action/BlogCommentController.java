/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-7-22 9:32:20
 * 文件说明: 
 */

package com.dozenx.web.module.blogComment.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;

import com.dozenx.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.dozenx.web.core.auth.session.SessionUser;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
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
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import com.dozenx.web.module.blogComment.service.BlogCommentService;
import com.dozenx.web.module.blogComment.bean.BlogComment;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.util.StringUtil;
import com.dozenx.web.util.RequestUtil;
import org.springframework.web.bind.annotation.*;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.util.DateUtil;
@APIs(description = "评论")
@Controller
@RequestMapping("/blog/comment")
public class BlogCommentController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(BlogCommentController.class);
    /** 权限service **/
    @Autowired
    private BlogCommentService blogCommentService;
    


    /**
     * 说明:ajax请求BlogComment信息
     * @author dozen.zhang
     * @date 2018-7-22 9:32:20
     * @return String
     */
       @API(summary="评论列表接口",
                 description="评论列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="主键",dataType = DataType.LONG,required =false),// false
                    @Param(name="pid" , description="所属文章id",dataType = DataType.LONG,required =false),// true
                    @Param(name="content" , description="正文",dataType = DataType.STRING,required =false),// true
                    @Param(name="type" , description="类型",dataType = DataType.INTEGER,required =false),// true
                    @Param(name="status" , description="状态",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="createUser" , description="创建人",dataType = DataType.LONG,required =false),// false
                    @Param(name="userName" , description="创建人姓名",dataType = DataType.STRING,required =false),// false
                    @Param(name="face" , description="头像",dataType = DataType.STRING,required =false),// false
                    @Param(name="createtime" , description="创建时间",dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="updatetime" , description="更新时间",dataType = DataType.DATE_TIME,required =false),// false
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
        List<BlogComment> blogComments = blogCommentService.listByParams4Page(params);
        return ResultUtil.getResult(blogComments, page);
    }
    
   /**
    * 说明:ajax请求BlogComment信息 无分页版本
    * @return ResultDTO 返回结果
    * @author dozen.zhang
    * @date 2018-7-22 9:32:20
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

        List<BlogComment> blogComments = blogCommentService.listByParams(params);
        return ResultUtil.getDataResult(blogComments);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2018-7-22 9:32:20
    */
  @API( summary="根据id查询单个评论信息",
           description = "根据id查询单个评论信息",
           parameters={
                   @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
           })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(@PathVariable Long id,HttpServletRequest request) {
            HashMap<String,Object> result =new HashMap</*String,Object*/>();
        if(id > 0){
            BlogComment bean = blogCommentService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        BlogComment bean = blogCommentService.selectByPrimaryKey(Long.valueOf(id));
        HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }

    
    /**
     * 说明:更新BlogComment信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-7-22 9:32:20
     */
      @API( summary="根据id更新单个评论信息",
        description = "根据id更新单个评论信息",
        parameters={
           @Param(name="id" , description="主键",type="LONG",required = false),
           @Param(name="pid" , description="所属文章id",type="LONG",required = true),
           @Param(name="content" , description="正文",type="STRING",required = true),
           @Param(name="type" , description="类型",type="INTEGER",required = true),
           @Param(name="status" , description="状态",type="INTEGER",required = false),
           @Param(name="createUser" , description="创建人",type="LONG",required = false),
           @Param(name="userName" , description="创建人姓名",type="STRING",required = false),
           @Param(name="face" , description="头像",type="STRING",required = false),
           @Param(name="createtime" , description="创建时间",type="TIMESTAMP",required = false),
           @Param(name="updatetime" , description="更新时间",type="TIMESTAMP",required = false),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO update(@PathVariable Long id,HttpServletRequest request) throws Exception {
        BlogComment blogComment =new  BlogComment();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            blogComment.setId(Long.valueOf(id)) ;
        }
        
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            blogComment.setPid(Long.valueOf(pid)) ;
        }
        
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            blogComment.setContent(String.valueOf(content)) ;
        }
        
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            blogComment.setType(Integer.valueOf(type)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            blogComment.setStatus(Integer.valueOf(status)) ;
        }
        
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            blogComment.setCreateUser(Long.valueOf(createUser)) ;
        }
        
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            blogComment.setUserName(String.valueOf(userName)) ;
        }
        
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            blogComment.setFace(String.valueOf(face)) ;
        }
        
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            blogComment.setCreatetime(Timestamp.valueOf(createtime)) ;
        }
        
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            blogComment.setUpdatetime(Timestamp.valueOf(updatetime)) ;
        }
        */
       // String id = request.getParameter("id");
        if(!StringUtil.isBlank(id+"")){
            blogComment.setId(Long.valueOf(id));
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            blogComment.setPid(Long.valueOf(pid));
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            blogComment.setContent(content);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            blogComment.setType(Integer.valueOf(type));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            blogComment.setStatus(Integer.valueOf(status));
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            blogComment.setCreateUser(Long.valueOf(createUser));
        }
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            blogComment.setUserName(userName);
        }
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            blogComment.setFace(face);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                blogComment.setCreatetime(Timestamp.valueOf(createtime));
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                blogComment.setCreatetime(new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                blogComment.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                blogComment.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(9,0)});
        vu.add("pid", pid, "所属文章id",  new Rule[]{new Digits(9,0),new NotEmpty()});
        vu.add("content", content, "正文",  new Rule[]{new Length(500),new NotEmpty()});
        vu.add("type", type, "类型",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3"}),new NotEmpty()});
        vu.add("status", status, "状态",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3","4","5"})});
        vu.add("createUser", createUser, "创建人",  new Rule[]{new Digits(11,0)});
        vu.add("userName", userName, "创建人姓名",  new Rule[]{new Length(20)});
        vu.add("face", face, "头像",  new Rule[]{new Length(50)});
        vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return blogCommentService.save(blogComment);
       
    }


        /**
         * 说明:添加BlogComment信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2018-7-22 9:32:20
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个评论信息",
            description = "添加单个评论信息",
            parameters={
               @Param(name="id" , description="主键",dataType = DataType.LONG,required = false),
               @Param(name="pid" , description="所属文章id",dataType = DataType.LONG,required = true),
               @Param(name="content" , description="正文",dataType = DataType.STRING,required = true),
               @Param(name="type" , description="类型",dataType = DataType.INTEGER,required = true),
               @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = false),
               @Param(name="createUser" , description="创建人",dataType = DataType.LONG,required = false),
               @Param(name="userName" , description="创建人姓名",dataType = DataType.STRING,required = false),
               @Param(name="face" , description="头像",dataType = DataType.STRING,required = false),
               @Param(name="createtime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
               @Param(name="updatetime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
            })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            BlogComment blogComment =new  BlogComment();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                blogComment.setId(Long.valueOf(id)) ;
            }
            
            String pid = request.getParameter("pid");
            if(!StringUtil.isBlank(pid)){
                blogComment.setPid(Long.valueOf(pid)) ;
            }
            
            String content = request.getParameter("content");
            if(!StringUtil.isBlank(content)){
                blogComment.setContent(String.valueOf(content)) ;
            }
            
            String type = request.getParameter("type");
            if(!StringUtil.isBlank(type)){
                blogComment.setType(Integer.valueOf(type)) ;
            }
            
            String status = request.getParameter("status");
            if(!StringUtil.isBlank(status)){
                blogComment.setStatus(Integer.valueOf(status)) ;
            }
            
            String createUser = request.getParameter("createUser");
            if(!StringUtil.isBlank(createUser)){
                blogComment.setCreateUser(Long.valueOf(createUser)) ;
            }
            
            String userName = request.getParameter("userName");
            if(!StringUtil.isBlank(userName)){
                blogComment.setUserName(String.valueOf(userName)) ;
            }
            
            String face = request.getParameter("face");
            if(!StringUtil.isBlank(face)){
                blogComment.setFace(String.valueOf(face)) ;
            }
            
            String createtime = request.getParameter("createtime");
            if(!StringUtil.isBlank(createtime)){
                blogComment.setCreatetime(Timestamp.valueOf(createtime)) ;
            }
            
            String updatetime = request.getParameter("updatetime");
            if(!StringUtil.isBlank(updatetime)){
                blogComment.setUpdatetime(Timestamp.valueOf(updatetime)) ;
            }
            */
            String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            blogComment.setId(Long.valueOf(id));
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            blogComment.setPid(Long.valueOf(pid));
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            blogComment.setContent(content);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            blogComment.setType(Integer.valueOf(type));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            blogComment.setStatus(Integer.valueOf(status));
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            blogComment.setCreateUser(Long.valueOf(createUser));
        }
        String userName = request.getParameter("userName");
        if(!StringUtil.isBlank(userName)){
            blogComment.setUserName(userName);
        }
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            blogComment.setFace(face);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                blogComment.setCreatetime(Timestamp.valueOf(createtime));
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                blogComment.setCreatetime(new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                blogComment.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                blogComment.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(9,0)});
        vu.add("pid", pid, "所属文章id",  new Rule[]{new Digits(9,0),new NotEmpty()});
        vu.add("content", content, "正文",  new Rule[]{new Length(500),new NotEmpty()});
        vu.add("type", type, "类型",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3"}),new NotEmpty()});
        vu.add("status", status, "状态",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3","4","5"})});
        vu.add("createUser", createUser, "创建人",  new Rule[]{new Digits(11,0)});
        vu.add("userName", userName, "创建人姓名",  new Rule[]{new Length(20)});
        vu.add("face", face, "头像",  new Rule[]{new Length(50)});
        vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }
            SessionUser sessionUser = this.getUser(request);
       if(sessionUser!=null){
           blogComment.setCreateUser(sessionUser.getUserId());
           blogComment.setUserName(sessionUser.getUserName());
           blogComment.setFace(sessionUser.getFace());
       }else{
           blogComment.setCreateUser(-1l);
           blogComment.setUserName("游客");
           blogComment.setFace("/static/img/timg.jpeg");
       }
            return blogCommentService.save(blogComment);

        }
    /**
     * 说明:删除BlogComment信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-7-22 9:32:20
     */
     @API( summary="根据id删除单个评论信息",
        description = "根据id删除单个评论信息",
        parameters={
         @Param(name="id" , description="主键",dataType= DataType.LONG,required = true),
        })
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResultDTO delete(@PathVariable Long id,HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
       // Long id = Long.valueOf(idStr);
        blogCommentService.delete(id);
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
       return  blogCommentService.multilDelete(idAry);
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
        List<BlogComment> list =blogCommentService.listByParams(params);
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
        colTitle.put("pid", "所属文章id");
        colTitle.put("content", "正文");
        colTitle.put("type", "类型");
        colTitle.put("status", "状态");
        colTitle.put("createUser", "创建人");
        colTitle.put("userName", "创建人姓名");
        colTitle.put("face", "头像");
        colTitle.put("createtime", "创建时间");
        colTitle.put("updatetime", "更新时间");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            BlogComment sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("pid",  list.get(i).getPid());
            map.put("content",  list.get(i).getContent());
            map.put("type",  list.get(i).getType());
            map.put("status",  list.get(i).getStatus());
            map.put("createUser",  list.get(i).getCreateUser());
            map.put("userName",  list.get(i).getUserName());
            map.put("face",  list.get(i).getFace());
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
    @RequestMapping(value = "/import.json")
    public void importExcel(){
        
    }


      /**
         * 说明: 跳转到BlogComment列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/BlogCommentList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/BlogCommentListMapper.html";
        }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-7-22 9:32:20
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/BlogCommentEdit.html";
    }
    /**
         * 说明:跳转查看页面
         * @param request 发请求
         * @return String
         * @author dozen.zhang
         * @date 2018-7-22 9:32:20
         */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/BlogCommentView.html";
    }
}
