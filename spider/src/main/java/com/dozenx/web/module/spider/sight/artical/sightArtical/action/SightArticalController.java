/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-8-10 16:26:00
 * 文件说明: 
 */

package com.dozenx.web.module.spider.sight.artical.sightArtical.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;
import com.dozenx.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
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
import com.dozenx.web.module.spider.sight.artical.sightArtical.service.SightArticalService;
import com.dozenx.web.module.spider.sight.artical.sightArtical.bean.SightArtical;
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
@APIs(description = "景点文章")
@Controller
@RequestMapping("/spider/sight/artical/")
public class SightArticalController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SightArticalController.class);
    /** 权限service **/
    @Autowired
    private SightArticalService sightArticalService;
    


    /**
     * 说明:ajax请求SightArtical信息
     * @author dozen.zhang
     * @date 2018-8-10 16:26:00
     * @return String
     */
       @API(summary="景点文章列表接口",
                 description="景点文章列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="content" , description="文章内容",dataType = DataType.STRING,required =false),// false
                    @Param(name="title" , description="文章标题",dataType = DataType.STRING,required =false),// false
                    @Param(name="score" , description="分数",dataType = DataType.FLOAT,required =false),// false
                    @Param(name="type" , description="类型",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="status" , description="状态",dataType = DataType.INTEGER,required =false),// true
                    @Param(name="dataTime" , description="原始时间",dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="url" , description="文章连接",dataType = DataType.STRING,required =false),// false
                    @Param(name="author" , description="作者",dataType = DataType.STRING,required =false),// false
                    @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="updatetime" , description="更新时间",dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="outId" , description="外部id",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="pid" , description="酒店id",dataType = DataType.INTEGER,required =false),// false
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
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = request.getParameter("titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
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
        String dataTime = request.getParameter("dataTime");
        if(!StringUtil.isBlank(dataTime)){
            if(StringUtil.checkNumeric(dataTime)){
                params.put("dataTime",dataTime);
            }else if(StringUtil.checkDateStr(dataTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("dataTime",new Timestamp( DateUtil.parseToDate(dataTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String dataTimeBegin = request.getParameter("dataTimeBegin");
        if(!StringUtil.isBlank(dataTimeBegin)){
            if(StringUtil.checkNumeric(dataTimeBegin)){
                params.put("dataTimeBegin",dataTimeBegin);
            }else if(StringUtil.checkDateStr(dataTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("dataTimeBegin",new Timestamp( DateUtil.parseToDate(dataTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String dataTimeEnd = request.getParameter("dataTimeEnd");
        if(!StringUtil.isBlank(dataTimeEnd)){
            if(StringUtil.checkNumeric(dataTimeEnd)){
                params.put("dataTimeEnd",dataTimeEnd);
            }else if(StringUtil.checkDateStr(dataTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("dataTimeEnd",new Timestamp( DateUtil.parseToDate(dataTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
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
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }

        params.put("page",page);
        List<SightArtical> sightArticals = sightArticalService.listByParams4Page(params);
        return ResultUtil.getResult(sightArticals, page);
    }
    
   /**
    * 说明:ajax请求SightArtical信息 无分页版本
    * @return ResultDTO 返回结果
    * @author dozen.zhang
    * @date 2018-8-10 16:26:00
    */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
                HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = request.getParameter("titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
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
        String dataTime = request.getParameter("dataTime");
        if(!StringUtil.isBlank(dataTime)){
            if(StringUtil.checkNumeric(dataTime)){
                params.put("dataTime",dataTime);
            }else if(StringUtil.checkDateStr(dataTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("dataTime",new Timestamp( DateUtil.parseToDate(dataTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String dataTimeBegin = request.getParameter("dataTimeBegin");
        if(!StringUtil.isBlank(dataTimeBegin)){
            if(StringUtil.checkNumeric(dataTimeBegin)){
                params.put("dataTimeBegin",dataTimeBegin);
            }else if(StringUtil.checkDateStr(dataTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("dataTimeBegin",new Timestamp( DateUtil.parseToDate(dataTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String dataTimeEnd = request.getParameter("dataTimeEnd");
        if(!StringUtil.isBlank(dataTimeEnd)){
            if(StringUtil.checkNumeric(dataTimeEnd)){
                params.put("dataTimeEnd",dataTimeEnd);
            }else if(StringUtil.checkDateStr(dataTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("dataTimeEnd",new Timestamp( DateUtil.parseToDate(dataTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
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
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }

        List<SightArtical> sightArticals = sightArticalService.listByParams(params);
        return ResultUtil.getDataResult(sightArticals);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2018-8-10 16:26:00
    */
  @API( summary="根据id查询单个景点文章信息",
           description = "根据id查询单个景点文章信息",
           parameters={
                   @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
           })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(@PathVariable Integer id,HttpServletRequest request) {
            HashMap<String,Object> result =new HashMap</*String,Object*/>();
        if(id > 0){
            SightArtical bean = sightArticalService.selectByPrimaryKey(Integer.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        SightArtical bean = sightArticalService.selectByPrimaryKey(Integer.valueOf(id));
        HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }

    
    /**
     * 说明:更新SightArtical信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-8-10 16:26:00
     */
      @API( summary="根据id更新单个景点文章信息",
        description = "根据id更新单个景点文章信息",
        parameters={
           @Param(name="id" , description="编号",type="INTEGER",required = false),
           @Param(name="content" , description="文章内容",type="STRING",required = false),
           @Param(name="title" , description="文章标题",type="STRING",required = false),
           @Param(name="score" , description="分数",type="FLOAT",required = false),
           @Param(name="type" , description="类型",type="INTEGER",required = false),
           @Param(name="status" , description="状态",type="INTEGER",required = true),
           @Param(name="dataTime" , description="原始时间",type="DATE_TIME",required = false),
           @Param(name="url" , description="文章连接",type="STRING",required = false),
           @Param(name="author" , description="作者",type="STRING",required = false),
           @Param(name="createTime" , description="创建时间",type="DATE_TIME",required = false),
           @Param(name="updatetime" , description="更新时间",type="DATE_TIME",required = false),
           @Param(name="outId" , description="外部id",type="INTEGER",required = false),
           @Param(name="pid" , description="酒店id",type="INTEGER",required = false),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO update(@PathVariable Integer id,HttpServletRequest request) throws Exception {
        SightArtical sightArtical =new  SightArtical();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sightArtical.setId(Integer.valueOf(id)) ;
        }
        
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            sightArtical.setContent(String.valueOf(content)) ;
        }
        
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            sightArtical.setTitle(String.valueOf(title)) ;
        }
        
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            sightArtical.setScore(Float.valueOf(score)) ;
        }
        
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            sightArtical.setType(Integer.valueOf(type)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            sightArtical.setStatus(Integer.valueOf(status)) ;
        }
        
        String dataTime = request.getParameter("dataTime");
        if(!StringUtil.isBlank(dataTime)){
            sightArtical.setDataTime(Timestamp.valueOf(dataTime)) ;
        }
        
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            sightArtical.setUrl(String.valueOf(url)) ;
        }
        
        String author = request.getParameter("author");
        if(!StringUtil.isBlank(author)){
            sightArtical.setAuthor(String.valueOf(author)) ;
        }
        
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            sightArtical.setCreateTime(Timestamp.valueOf(createTime)) ;
        }
        
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            sightArtical.setUpdatetime(Timestamp.valueOf(updatetime)) ;
        }
        
        String outId = request.getParameter("outId");
        if(!StringUtil.isBlank(outId)){
            sightArtical.setOutId(Integer.valueOf(outId)) ;
        }
        
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            sightArtical.setPid(Integer.valueOf(pid)) ;
        }
        */
        if(!StringUtil.isBlank(id+"")){
            sightArtical.setId(Integer.valueOf(id));
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            sightArtical.setContent(content);
        }
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            sightArtical.setTitle(title);
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            sightArtical.setScore(Float.valueOf(score));
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            sightArtical.setType(Integer.valueOf(type));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            sightArtical.setStatus(Integer.valueOf(status));
        }
        String dataTime = request.getParameter("dataTime");
        if(!StringUtil.isBlank(dataTime)){
            if(StringUtil.checkNumeric(dataTime)){
                sightArtical.setDataTime(Timestamp.valueOf(dataTime));
            }else if(StringUtil.checkDateStr(dataTime, "yyyy-MM-dd HH:mm:ss")){
                sightArtical.setDataTime(new Timestamp( DateUtil.parseToDate(dataTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            sightArtical.setUrl(url);
        }
        String author = request.getParameter("author");
        if(!StringUtil.isBlank(author)){
            sightArtical.setAuthor(author);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                sightArtical.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                sightArtical.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                sightArtical.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                sightArtical.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String outId = request.getParameter("outId");
        if(!StringUtil.isBlank(outId)){
            sightArtical.setOutId(Integer.valueOf(outId));
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            sightArtical.setPid(Integer.valueOf(pid));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("content", content, "文章内容",  new Rule[]{});
        vu.add("title", title, "文章标题",  new Rule[]{new Length(100)});
        vu.add("score", score, "分数",  new Rule[]{new Digits(1,1)});
        vu.add("type", type, "类型",  new Rule[]{new Digits(4,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","9"}),new NotEmpty()});
        vu.add("dataTime", dataTime, "原始时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("url", url, "文章连接",  new Rule[]{new Length(100)});
        vu.add("author", author, "作者",  new Rule[]{new Length(30)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("outId", outId, "外部id",  new Rule[]{new Digits(15,0)});
        vu.add("pid", pid, "酒店id",  new Rule[]{new Digits(11,0)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return sightArticalService.save(sightArtical);
       
    }


        /**
         * 说明:添加SightArtical信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2018-8-10 16:26:00
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个景点文章信息",
            description = "添加单个景点文章信息",
            parameters={
               @Param(name="id" , description="编号",dataType = DataType.INTEGER,required = false),
               @Param(name="content" , description="文章内容",dataType = DataType.STRING,required = false),
               @Param(name="title" , description="文章标题",dataType = DataType.STRING,required = false),
               @Param(name="score" , description="分数",dataType = DataType.FLOAT,required = false),
               @Param(name="type" , description="类型",dataType = DataType.INTEGER,required = false),
               @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = true),
               @Param(name="dataTime" , description="原始时间",dataType = DataType.DATE_TIME,required = false),
               @Param(name="url" , description="文章连接",dataType = DataType.STRING,required = false),
               @Param(name="author" , description="作者",dataType = DataType.STRING,required = false),
               @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
               @Param(name="updatetime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
               @Param(name="outId" , description="外部id",dataType = DataType.INTEGER,required = false),
               @Param(name="pid" , description="酒店id",dataType = DataType.INTEGER,required = false),
            })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            SightArtical sightArtical =new  SightArtical();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                sightArtical.setId(Integer.valueOf(id)) ;
            }
            
            String content = request.getParameter("content");
            if(!StringUtil.isBlank(content)){
                sightArtical.setContent(String.valueOf(content)) ;
            }
            
            String title = request.getParameter("title");
            if(!StringUtil.isBlank(title)){
                sightArtical.setTitle(String.valueOf(title)) ;
            }
            
            String score = request.getParameter("score");
            if(!StringUtil.isBlank(score)){
                sightArtical.setScore(Float.valueOf(score)) ;
            }
            
            String type = request.getParameter("type");
            if(!StringUtil.isBlank(type)){
                sightArtical.setType(Integer.valueOf(type)) ;
            }
            
            String status = request.getParameter("status");
            if(!StringUtil.isBlank(status)){
                sightArtical.setStatus(Integer.valueOf(status)) ;
            }
            
            String dataTime = request.getParameter("dataTime");
            if(!StringUtil.isBlank(dataTime)){
                sightArtical.setDataTime(Timestamp.valueOf(dataTime)) ;
            }
            
            String url = request.getParameter("url");
            if(!StringUtil.isBlank(url)){
                sightArtical.setUrl(String.valueOf(url)) ;
            }
            
            String author = request.getParameter("author");
            if(!StringUtil.isBlank(author)){
                sightArtical.setAuthor(String.valueOf(author)) ;
            }
            
            String createTime = request.getParameter("createTime");
            if(!StringUtil.isBlank(createTime)){
                sightArtical.setCreateTime(Timestamp.valueOf(createTime)) ;
            }
            
            String updatetime = request.getParameter("updatetime");
            if(!StringUtil.isBlank(updatetime)){
                sightArtical.setUpdatetime(Timestamp.valueOf(updatetime)) ;
            }
            
            String outId = request.getParameter("outId");
            if(!StringUtil.isBlank(outId)){
                sightArtical.setOutId(Integer.valueOf(outId)) ;
            }
            
            String pid = request.getParameter("pid");
            if(!StringUtil.isBlank(pid)){
                sightArtical.setPid(Integer.valueOf(pid)) ;
            }
            */
            String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sightArtical.setId(Integer.valueOf(id));
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            sightArtical.setContent(content);
        }
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            sightArtical.setTitle(title);
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            sightArtical.setScore(Float.valueOf(score));
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            sightArtical.setType(Integer.valueOf(type));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            sightArtical.setStatus(Integer.valueOf(status));
        }
        String dataTime = request.getParameter("dataTime");
        if(!StringUtil.isBlank(dataTime)){
            if(StringUtil.checkNumeric(dataTime)){
                sightArtical.setDataTime(Timestamp.valueOf(dataTime));
            }else if(StringUtil.checkDateStr(dataTime, "yyyy-MM-dd HH:mm:ss")){
                sightArtical.setDataTime(new Timestamp( DateUtil.parseToDate(dataTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            sightArtical.setUrl(url);
        }
        String author = request.getParameter("author");
        if(!StringUtil.isBlank(author)){
            sightArtical.setAuthor(author);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                sightArtical.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                sightArtical.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                sightArtical.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                sightArtical.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String outId = request.getParameter("outId");
        if(!StringUtil.isBlank(outId)){
            sightArtical.setOutId(Integer.valueOf(outId));
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            sightArtical.setPid(Integer.valueOf(pid));
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("content", content, "文章内容",  new Rule[]{});
        vu.add("title", title, "文章标题",  new Rule[]{new Length(100)});
        vu.add("score", score, "分数",  new Rule[]{new Digits(1,1)});
        vu.add("type", type, "类型",  new Rule[]{new Digits(4,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","9"}),new NotEmpty()});
        vu.add("dataTime", dataTime, "原始时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("url", url, "文章连接",  new Rule[]{new Length(100)});
        vu.add("author", author, "作者",  new Rule[]{new Length(30)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("outId", outId, "外部id",  new Rule[]{new Digits(15,0)});
        vu.add("pid", pid, "酒店id",  new Rule[]{new Digits(11,0)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

            return sightArticalService.save(sightArtical);

        }
    /**
     * 说明:删除SightArtical信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-8-10 16:26:00
     */
     @API( summary="根据id删除单个景点文章信息",
        description = "根据id删除单个景点文章信息",
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
        sightArticalService.delete(id);
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
       return  sightArticalService.multilDelete(idAry);
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
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = request.getParameter("titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
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
        String dataTime = request.getParameter("dataTime");
        if(!StringUtil.isBlank(dataTime)){
            if(StringUtil.checkNumeric(dataTime)){
                params.put("dataTime",dataTime);
            }else if(StringUtil.checkDateStr(dataTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("dataTime",new Timestamp( DateUtil.parseToDate(dataTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String dataTimeBegin = request.getParameter("dataTimeBegin");
        if(!StringUtil.isBlank(dataTimeBegin)){
            if(StringUtil.checkNumeric(dataTimeBegin)){
                params.put("dataTimeBegin",dataTimeBegin);
            }else if(StringUtil.checkDateStr(dataTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("dataTimeBegin",new Timestamp( DateUtil.parseToDate(dataTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String dataTimeEnd = request.getParameter("dataTimeEnd");
        if(!StringUtil.isBlank(dataTimeEnd)){
            if(StringUtil.checkNumeric(dataTimeEnd)){
                params.put("dataTimeEnd",dataTimeEnd);
            }else if(StringUtil.checkDateStr(dataTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("dataTimeEnd",new Timestamp( DateUtil.parseToDate(dataTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
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
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }

        // 查询list集合
        List<SightArtical> list =sightArticalService.listByParams(params);
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
        colTitle.put("content", "文章内容");
        colTitle.put("title", "文章标题");
        colTitle.put("score", "分数");
        colTitle.put("type", "类型");
        colTitle.put("status", "状态");
        colTitle.put("dataTime", "原始时间");
        colTitle.put("url", "文章连接");
        colTitle.put("author", "作者");
        colTitle.put("createTime", "创建时间");
        colTitle.put("updatetime", "更新时间");
        colTitle.put("outId", "外部id");
        colTitle.put("pid", "酒店id");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            SightArtical sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("content",  list.get(i).getContent());
            map.put("title",  list.get(i).getTitle());
            map.put("score",  list.get(i).getScore());
            map.put("type",  list.get(i).getType());
            map.put("status",  list.get(i).getStatus());
            map.put("dataTime",  list.get(i).getDataTime());
            map.put("url",  list.get(i).getUrl());
            map.put("author",  list.get(i).getAuthor());
            map.put("createTime",  list.get(i).getCreateTime());
            map.put("updatetime",  list.get(i).getUpdatetime());
            map.put("outId",  list.get(i).getOutId());
            map.put("pid",  list.get(i).getPid());
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
         * 说明: 跳转到SightArtical列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/SightArticalList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/SightArticalListMapper.html";
        }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-8-10 16:26:00
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/SightArticalEdit.html";
    }
    /**
         * 说明:跳转查看页面
         * @param request 发请求
         * @return String
         * @author dozen.zhang
         * @date 2018-8-10 16:26:00
         */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/SightArticalView.html";
    }
}
