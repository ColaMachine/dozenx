/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-8-5 13:52:14
 * 文件说明: 
 */

package com.dozenx.web.module.spider.hotel.comment.hotelComment.action;

import com.dozenx.swagger.annotation.API;
import com.dozenx.swagger.annotation.APIs;
import com.dozenx.swagger.annotation.DataType;
import com.dozenx.swagger.annotation.Param;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.module.spider.hotel.comment.hotelComment.bean.HotelComment;
import com.dozenx.web.module.spider.hotel.comment.hotelComment.service.HotelCommentService;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;
@APIs(description = "酒店评论")
@Controller
@RequestMapping("/spider/hotel/comment/")
public class HotelCommentController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(HotelCommentController.class);
    /** 权限service **/
    @Autowired
    private HotelCommentService hotelCommentService;
    


    /**
     * 说明:ajax请求HotelComment信息
     * @author dozen.zhang
     * @date 2018-8-5 13:52:14
     * @return String
     */
       @API(summary="酒店评论列表接口",
                 description="酒店评论列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="cotent" , description="评论内容",dataType = DataType.STRING,required =false),// false
                    @Param(name="score" , description="分数",dataType = DataType.FLOAT,required =false),// false
                    @Param(name="type" , description="类型",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="status" , description="状态",dataType = DataType.INTEGER,required =false),// true
                    @Param(name="data_time" , description="原始时间",dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="author" , description="作者",dataType = DataType.STRING,required =false),// false
                    @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="updatetime" , description="更新时间",dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="outId" , description="外部id",dataType = DataType.INTEGER,required =false),// false
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
        String cotent = request.getParameter("cotent");
        if(!StringUtil.isBlank(cotent)){
            params.put("cotent",cotent);
        }
        String cotentLike = request.getParameter("cotentLike");
        if(!StringUtil.isBlank(cotentLike)){
            params.put("cotentLike",cotentLike);
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            params.put("score",score);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String data_time = request.getParameter("data_time");
        if(!StringUtil.isBlank(data_time)){
            if(StringUtil.checkNumeric(data_time)){
                params.put("data_time",data_time);
            }else if(StringUtil.checkDateStr(data_time, "yyyy-MM-dd HH:mm:ss")){
                params.put("data_time",new Timestamp( DateUtil.parseToDate(data_time, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String data_timeBegin = request.getParameter("data_timeBegin");
        if(!StringUtil.isBlank(data_timeBegin)){
            if(StringUtil.checkNumeric(data_timeBegin)){
                params.put("data_timeBegin",data_timeBegin);
            }else if(StringUtil.checkDateStr(data_timeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("data_timeBegin",new Timestamp( DateUtil.parseToDate(data_timeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String data_timeEnd = request.getParameter("data_timeEnd");
        if(!StringUtil.isBlank(data_timeEnd)){
            if(StringUtil.checkNumeric(data_timeEnd)){
                params.put("data_timeEnd",data_timeEnd);
            }else if(StringUtil.checkDateStr(data_timeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("data_timeEnd",new Timestamp( DateUtil.parseToDate(data_timeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String author = request.getParameter("author");
        if(!StringUtil.isBlank(author)){
            params.put("author",author);
        }
        String authorLike = request.getParameter("authorLike");
        if(!StringUtil.isBlank(authorLike)){
            params.put("authorLike",authorLike);
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
        String outId = request.getParameter("outId");
        if(!StringUtil.isBlank(outId)){
            params.put("outId",outId);
        }

        params.put("page",page);
        List<HotelComment> hotelComments = hotelCommentService.listByParams4Page(params);
        return ResultUtil.getResult(hotelComments, page);
    }
    
   /**
    * 说明:ajax请求HotelComment信息 无分页版本
    * @return ResultDTO 返回结果
    * @author dozen.zhang
    * @date 2018-8-5 13:52:14
    */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
                HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String cotent = request.getParameter("cotent");
        if(!StringUtil.isBlank(cotent)){
            params.put("cotent",cotent);
        }
        String cotentLike = request.getParameter("cotentLike");
        if(!StringUtil.isBlank(cotentLike)){
            params.put("cotentLike",cotentLike);
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            params.put("score",score);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String data_time = request.getParameter("data_time");
        if(!StringUtil.isBlank(data_time)){
            if(StringUtil.checkNumeric(data_time)){
                params.put("data_time",data_time);
            }else if(StringUtil.checkDateStr(data_time, "yyyy-MM-dd HH:mm:ss")){
                params.put("data_time",new Timestamp( DateUtil.parseToDate(data_time, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String data_timeBegin = request.getParameter("data_timeBegin");
        if(!StringUtil.isBlank(data_timeBegin)){
            if(StringUtil.checkNumeric(data_timeBegin)){
                params.put("data_timeBegin",data_timeBegin);
            }else if(StringUtil.checkDateStr(data_timeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("data_timeBegin",new Timestamp( DateUtil.parseToDate(data_timeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String data_timeEnd = request.getParameter("data_timeEnd");
        if(!StringUtil.isBlank(data_timeEnd)){
            if(StringUtil.checkNumeric(data_timeEnd)){
                params.put("data_timeEnd",data_timeEnd);
            }else if(StringUtil.checkDateStr(data_timeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("data_timeEnd",new Timestamp( DateUtil.parseToDate(data_timeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String author = request.getParameter("author");
        if(!StringUtil.isBlank(author)){
            params.put("author",author);
        }
        String authorLike = request.getParameter("authorLike");
        if(!StringUtil.isBlank(authorLike)){
            params.put("authorLike",authorLike);
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
        String outId = request.getParameter("outId");
        if(!StringUtil.isBlank(outId)){
            params.put("outId",outId);
        }

        List<HotelComment> hotelComments = hotelCommentService.listByParams(params);
        return ResultUtil.getDataResult(hotelComments);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2018-8-5 13:52:14
    */
  @API( summary="根据id查询单个酒店评论信息",
           description = "根据id查询单个酒店评论信息",
           parameters={
                   @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
           })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(@PathVariable Integer id,HttpServletRequest request) {
            HashMap<String,Object> result =new HashMap</*String,Object*/>();
        if(id > 0){
            HotelComment bean = hotelCommentService.selectByPrimaryKey(Integer.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        HotelComment bean = hotelCommentService.selectByPrimaryKey(Integer.valueOf(id));
        HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }

    
    /**
     * 说明:更新HotelComment信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-8-5 13:52:14
     */
      @API( summary="根据id更新单个酒店评论信息",
        description = "根据id更新单个酒店评论信息",
        parameters={
           @Param(name="id" , description="编号",type="INTEGER",required = false),
           @Param(name="cotent" , description="评论内容",type="STRING",required = false),
           @Param(name="score" , description="分数",type="FLOAT",required = false),
           @Param(name="type" , description="类型",type="INTEGER",required = false),
           @Param(name="status" , description="状态",type="INTEGER",required = true),
           @Param(name="data_time" , description="原始时间",type="TIMESTAMP",required = false),
           @Param(name="author" , description="作者",type="STRING",required = false),
           @Param(name="createTime" , description="创建时间",type="TIMESTAMP",required = false),
           @Param(name="updatetime" , description="更新时间",type="TIMESTAMP",required = false),
           @Param(name="outId" , description="外部id",type="INTEGER",required = false),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO update(@PathVariable Integer id,HttpServletRequest request) throws Exception {
        HotelComment hotelComment =new  HotelComment();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            hotelComment.setId(Integer.valueOf(id)) ;
        }
        
        String cotent = request.getParameter("cotent");
        if(!StringUtil.isBlank(cotent)){
            hotelComment.setCotent(String.valueOf(cotent)) ;
        }
        
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            hotelComment.setScore(Float.valueOf(score)) ;
        }
        
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            hotelComment.setType(Integer.valueOf(type)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            hotelComment.setStatus(Integer.valueOf(status)) ;
        }
        
        String data_time = request.getParameter("data_time");
        if(!StringUtil.isBlank(data_time)){
            hotelComment.setData_time(Timestamp.valueOf(data_time)) ;
        }
        
        String author = request.getParameter("author");
        if(!StringUtil.isBlank(author)){
            hotelComment.setAuthor(String.valueOf(author)) ;
        }
        
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            hotelComment.setCreateTime(Timestamp.valueOf(createTime)) ;
        }
        
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            hotelComment.setUpdatetime(Timestamp.valueOf(updatetime)) ;
        }
        
        String outId = request.getParameter("outId");
        if(!StringUtil.isBlank(outId)){
            hotelComment.setOutId(Integer.valueOf(outId)) ;
        }
        */
        //String id = request.getParameter("id");
        if(!StringUtil.isBlank(id+"")){
            hotelComment.setId(Integer.valueOf(id));
        }
        String cotent = request.getParameter("cotent");
        if(!StringUtil.isBlank(cotent)){
            hotelComment.setCotent(cotent);
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            hotelComment.setScore(Float.valueOf(score));
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            hotelComment.setType(Integer.valueOf(type));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            hotelComment.setStatus(Integer.valueOf(status));
        }
        String data_time = request.getParameter("data_time");
        if(!StringUtil.isBlank(data_time)){
            if(StringUtil.checkNumeric(data_time)){
                hotelComment.setData_time(Timestamp.valueOf(data_time));
            }else if(StringUtil.checkDateStr(data_time, "yyyy-MM-dd HH:mm:ss")){
                hotelComment.setData_time(new Timestamp( DateUtil.parseToDate(data_time, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String author = request.getParameter("author");
        if(!StringUtil.isBlank(author)){
            hotelComment.setAuthor(author);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                hotelComment.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                hotelComment.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                hotelComment.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                hotelComment.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String outId = request.getParameter("outId");
        if(!StringUtil.isBlank(outId)){
            hotelComment.setOutId(Integer.valueOf(outId));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("cotent", cotent, "评论内容",  new Rule[]{new Length(50)});
        vu.add("score", score, "分数",  new Rule[]{new Digits(1,1)});
        vu.add("type", type, "类型",  new Rule[]{new Digits(4,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","9"}),new NotEmpty()});
        vu.add("data_time", data_time, "原始时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("author", author, "作者",  new Rule[]{new Length(30)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("outId", outId, "外部id",  new Rule[]{new Digits(15,0)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return hotelCommentService.save(hotelComment);
       
    }


        /**
         * 说明:添加HotelComment信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2018-8-5 13:52:14
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个酒店评论信息",
            description = "添加单个酒店评论信息",
            parameters={
               @Param(name="id" , description="编号",dataType = DataType.INTEGER,required = false),
               @Param(name="cotent" , description="评论内容",dataType = DataType.STRING,required = false),
               @Param(name="score" , description="分数",dataType = DataType.FLOAT,required = false),
               @Param(name="type" , description="类型",dataType = DataType.INTEGER,required = false),
               @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = true),
               @Param(name="data_time" , description="原始时间",dataType = DataType.DATE_TIME,required = false),
               @Param(name="author" , description="作者",dataType = DataType.STRING,required = false),
               @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
               @Param(name="updatetime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
               @Param(name="outId" , description="外部id",dataType = DataType.INTEGER,required = false),
            })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            HotelComment hotelComment =new  HotelComment();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                hotelComment.setId(Integer.valueOf(id)) ;
            }
            
            String cotent = request.getParameter("cotent");
            if(!StringUtil.isBlank(cotent)){
                hotelComment.setCotent(String.valueOf(cotent)) ;
            }
            
            String score = request.getParameter("score");
            if(!StringUtil.isBlank(score)){
                hotelComment.setScore(Float.valueOf(score)) ;
            }
            
            String type = request.getParameter("type");
            if(!StringUtil.isBlank(type)){
                hotelComment.setType(Integer.valueOf(type)) ;
            }
            
            String status = request.getParameter("status");
            if(!StringUtil.isBlank(status)){
                hotelComment.setStatus(Integer.valueOf(status)) ;
            }
            
            String data_time = request.getParameter("data_time");
            if(!StringUtil.isBlank(data_time)){
                hotelComment.setData_time(Timestamp.valueOf(data_time)) ;
            }
            
            String author = request.getParameter("author");
            if(!StringUtil.isBlank(author)){
                hotelComment.setAuthor(String.valueOf(author)) ;
            }
            
            String createTime = request.getParameter("createTime");
            if(!StringUtil.isBlank(createTime)){
                hotelComment.setCreateTime(Timestamp.valueOf(createTime)) ;
            }
            
            String updatetime = request.getParameter("updatetime");
            if(!StringUtil.isBlank(updatetime)){
                hotelComment.setUpdatetime(Timestamp.valueOf(updatetime)) ;
            }
            
            String outId = request.getParameter("outId");
            if(!StringUtil.isBlank(outId)){
                hotelComment.setOutId(Integer.valueOf(outId)) ;
            }
            */
            String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            hotelComment.setId(Integer.valueOf(id));
        }
        String cotent = request.getParameter("cotent");
        if(!StringUtil.isBlank(cotent)){
            hotelComment.setCotent(cotent);
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            hotelComment.setScore(Float.valueOf(score));
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            hotelComment.setType(Integer.valueOf(type));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            hotelComment.setStatus(Integer.valueOf(status));
        }
        String data_time = request.getParameter("data_time");
        if(!StringUtil.isBlank(data_time)){
            if(StringUtil.checkNumeric(data_time)){
                hotelComment.setData_time(Timestamp.valueOf(data_time));
            }else if(StringUtil.checkDateStr(data_time, "yyyy-MM-dd HH:mm:ss")){
                hotelComment.setData_time(new Timestamp( DateUtil.parseToDate(data_time, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String author = request.getParameter("author");
        if(!StringUtil.isBlank(author)){
            hotelComment.setAuthor(author);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                hotelComment.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                hotelComment.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                hotelComment.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                hotelComment.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String outId = request.getParameter("outId");
        if(!StringUtil.isBlank(outId)){
            hotelComment.setOutId(Integer.valueOf(outId));
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("cotent", cotent, "评论内容",  new Rule[]{new Length(50)});
        vu.add("score", score, "分数",  new Rule[]{new Digits(1,1)});
        vu.add("type", type, "类型",  new Rule[]{new Digits(4,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","9"}),new NotEmpty()});
        vu.add("data_time", data_time, "原始时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("author", author, "作者",  new Rule[]{new Length(30)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("outId", outId, "外部id",  new Rule[]{new Digits(15,0)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

            return hotelCommentService.save(hotelComment);

        }
    /**
     * 说明:删除HotelComment信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-8-5 13:52:14
     */
     @API( summary="根据id删除单个酒店评论信息",
        description = "根据id删除单个酒店评论信息",
        parameters={
         @Param(name="id" , description="编号",dataType= DataType.INTEGER,required = true),
        })
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResultDTO delete(@PathVariable Integer id,HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        //Integer id = Integer.valueOf(idStr);
        hotelCommentService.delete(id);
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
                    vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});

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
       return  hotelCommentService.multilDelete(idAry);
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
        String cotent = request.getParameter("cotent");
        if(!StringUtil.isBlank(cotent)){
            params.put("cotent",cotent);
        }
        String cotentLike = request.getParameter("cotentLike");
        if(!StringUtil.isBlank(cotentLike)){
            params.put("cotentLike",cotentLike);
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            params.put("score",score);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String data_time = request.getParameter("data_time");
        if(!StringUtil.isBlank(data_time)){
            if(StringUtil.checkNumeric(data_time)){
                params.put("data_time",data_time);
            }else if(StringUtil.checkDateStr(data_time, "yyyy-MM-dd HH:mm:ss")){
                params.put("data_time",new Timestamp( DateUtil.parseToDate(data_time, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String data_timeBegin = request.getParameter("data_timeBegin");
        if(!StringUtil.isBlank(data_timeBegin)){
            if(StringUtil.checkNumeric(data_timeBegin)){
                params.put("data_timeBegin",data_timeBegin);
            }else if(StringUtil.checkDateStr(data_timeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("data_timeBegin",new Timestamp( DateUtil.parseToDate(data_timeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String data_timeEnd = request.getParameter("data_timeEnd");
        if(!StringUtil.isBlank(data_timeEnd)){
            if(StringUtil.checkNumeric(data_timeEnd)){
                params.put("data_timeEnd",data_timeEnd);
            }else if(StringUtil.checkDateStr(data_timeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("data_timeEnd",new Timestamp( DateUtil.parseToDate(data_timeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String author = request.getParameter("author");
        if(!StringUtil.isBlank(author)){
            params.put("author",author);
        }
        String authorLike = request.getParameter("authorLike");
        if(!StringUtil.isBlank(authorLike)){
            params.put("authorLike",authorLike);
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
        String outId = request.getParameter("outId");
        if(!StringUtil.isBlank(outId)){
            params.put("outId",outId);
        }

        // 查询list集合
        List<HotelComment> list =hotelCommentService.listByParams(params);
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
        colTitle.put("cotent", "评论内容");
        colTitle.put("score", "分数");
        colTitle.put("type", "类型");
        colTitle.put("status", "状态");
        colTitle.put("data_time", "原始时间");
        colTitle.put("author", "作者");
        colTitle.put("createTime", "创建时间");
        colTitle.put("updatetime", "更新时间");
        colTitle.put("outId", "外部id");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            HotelComment sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("cotent",  list.get(i).getCotent());
            map.put("score",  list.get(i).getScore());
            map.put("type",  list.get(i).getType());
            map.put("status",  list.get(i).getStatus());
            map.put("data_time",  list.get(i).getData_time());
            map.put("author",  list.get(i).getAuthor());
            map.put("createTime",  list.get(i).getCreateTime());
            map.put("updatetime",  list.get(i).getUpdatetime());
            map.put("outId",  list.get(i).getOutId());
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
         * 说明: 跳转到HotelComment列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/HotelCommentList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/HotelCommentListMapper.html";
        }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-8-5 13:52:14
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/HotelCommentEdit.html";
    }
    /**
         * 说明:跳转查看页面
         * @param request 发请求
         * @return String
         * @author dozen.zhang
         * @date 2018-8-5 13:52:14
         */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/HotelCommentView.html";
    }
}
