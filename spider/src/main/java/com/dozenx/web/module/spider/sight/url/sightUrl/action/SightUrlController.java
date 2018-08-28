/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-8-9 15:05:37
 * 文件说明: 
 */

package com.dozenx.web.module.spider.sight.url.sightUrl.action;
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
import com.dozenx.web.module.spider.sight.url.sightUrl.service.SightUrlService;
import com.dozenx.web.module.spider.sight.url.sightUrl.bean.SightUrl;
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
@APIs(description = "用户")
@Controller
@RequestMapping("/spider/sight/url/")
public class SightUrlController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SightUrlController.class);
    /** 权限service **/
    @Autowired
    private SightUrlService sightUrlService;
    


    /**
     * 说明:ajax请求SightUrl信息
     * @author dozen.zhang
     * @date 2018-8-9 15:05:37
     * @return String
     */
       @API(summary="用户列表接口",
                 description="用户列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="name" , description="景区名称",dataType = DataType.STRING,required =false),// false
                    @Param(name="url" , description="url",dataType = DataType.STRING,required =false),// false
                    @Param(name="platform" , description="平台",dataType = DataType.STRING,required =false),// false
                    @Param(name="type" , description="类型",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="status" , description="状态",dataType = DataType.INTEGER,required =false),// true
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
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }
        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            params.put("platform",platform);
        }
        String platformLike = request.getParameter("platformLike");
        if(!StringUtil.isBlank(platformLike)){
            params.put("platformLike",platformLike);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
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
        List<SightUrl> sightUrls = sightUrlService.listByParams4Page(params);
        return ResultUtil.getResult(sightUrls, page);
    }
    
   /**
    * 说明:ajax请求SightUrl信息 无分页版本
    * @return ResultDTO 返回结果
    * @author dozen.zhang
    * @date 2018-8-9 15:05:37
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
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }
        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            params.put("platform",platform);
        }
        String platformLike = request.getParameter("platformLike");
        if(!StringUtil.isBlank(platformLike)){
            params.put("platformLike",platformLike);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
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

        List<SightUrl> sightUrls = sightUrlService.listByParams(params);
        return ResultUtil.getDataResult(sightUrls);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2018-8-9 15:05:37
    */
  @API( summary="根据id查询单个用户信息",
           description = "根据id查询单个用户信息",
           parameters={
                   @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
           })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(@PathVariable Integer id,HttpServletRequest request) {
            HashMap<String,Object> result =new HashMap</*String,Object*/>();
        if(id > 0){
            SightUrl bean = sightUrlService.selectByPrimaryKey(Integer.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        SightUrl bean = sightUrlService.selectByPrimaryKey(Integer.valueOf(id));
        HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }

    
    /**
     * 说明:更新SightUrl信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-8-9 15:05:37
     */
      @API( summary="根据id更新单个用户信息",
        description = "根据id更新单个用户信息",
        parameters={
           @Param(name="id" , description="编号",type="INTEGER",required = false),
           @Param(name="name" , description="景区名称",type="STRING",required = false),
           @Param(name="url" , description="url",type="STRING",required = false),
           @Param(name="platform" , description="平台",type="STRING",required = false),
           @Param(name="type" , description="类型",type="INTEGER",required = false),
           @Param(name="status" , description="状态",type="INTEGER",required = true),
           @Param(name="createTime" , description="创建时间",type="DATE_TIME",required = false),
           @Param(name="updatetime" , description="更新时间",type="DATE_TIME",required = false),
           @Param(name="outId" , description="外部id",type="INTEGER",required = false),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO update(@PathVariable Integer id,HttpServletRequest request) throws Exception {
        SightUrl sightUrl =new  SightUrl();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sightUrl.setId(Integer.valueOf(id)) ;
        }
        
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            sightUrl.setName(String.valueOf(name)) ;
        }
        
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            sightUrl.setUrl(String.valueOf(url)) ;
        }
        
        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            sightUrl.setPlatform(String.valueOf(platform)) ;
        }
        
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            sightUrl.setType(Integer.valueOf(type)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            sightUrl.setStatus(Integer.valueOf(status)) ;
        }
        
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            sightUrl.setCreateTime(Timestamp.valueOf(createTime)) ;
        }
        
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            sightUrl.setUpdatetime(Timestamp.valueOf(updatetime)) ;
        }
        
        String outId = request.getParameter("outId");
        if(!StringUtil.isBlank(outId)){
            sightUrl.setOutId(Integer.valueOf(outId)) ;
        }
        */
//        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id+"")){
            sightUrl.setId(Integer.valueOf(id));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            sightUrl.setName(name);
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            sightUrl.setUrl(url);
        }
        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            sightUrl.setPlatform(platform);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            sightUrl.setType(Integer.valueOf(type));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            sightUrl.setStatus(Integer.valueOf(status));
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                sightUrl.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                sightUrl.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                sightUrl.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                sightUrl.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String outId = request.getParameter("outId");
        if(!StringUtil.isBlank(outId)){
            sightUrl.setOutId(Integer.valueOf(outId));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("name", name, "景区名称",  new Rule[]{new Length(100)});
        vu.add("url", url, "url",  new Rule[]{new Length(500)});
        vu.add("platform", platform, "平台",  new Rule[]{new Length(50)});
        vu.add("type", type, "类型",  new Rule[]{new Digits(4,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","9"}),new NotEmpty()});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("outId", outId, "外部id",  new Rule[]{new Digits(15,0)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return sightUrlService.save(sightUrl);
       
    }


        /**
         * 说明:添加SightUrl信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2018-8-9 15:05:37
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个用户信息",
            description = "添加单个用户信息",
            parameters={
               @Param(name="id" , description="编号",dataType = DataType.INTEGER,required = false),
               @Param(name="name" , description="景区名称",dataType = DataType.STRING,required = false),
               @Param(name="url" , description="url",dataType = DataType.STRING,required = false),
               @Param(name="platform" , description="平台",dataType = DataType.STRING,required = false),
               @Param(name="type" , description="类型",dataType = DataType.INTEGER,required = false),
               @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = true),
               @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
               @Param(name="updatetime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
               @Param(name="outId" , description="外部id",dataType = DataType.INTEGER,required = false),
            })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            SightUrl sightUrl =new  SightUrl();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                sightUrl.setId(Integer.valueOf(id)) ;
            }
            
            String name = request.getParameter("name");
            if(!StringUtil.isBlank(name)){
                sightUrl.setName(String.valueOf(name)) ;
            }
            
            String url = request.getParameter("url");
            if(!StringUtil.isBlank(url)){
                sightUrl.setUrl(String.valueOf(url)) ;
            }
            
            String platform = request.getParameter("platform");
            if(!StringUtil.isBlank(platform)){
                sightUrl.setPlatform(String.valueOf(platform)) ;
            }
            
            String type = request.getParameter("type");
            if(!StringUtil.isBlank(type)){
                sightUrl.setType(Integer.valueOf(type)) ;
            }
            
            String status = request.getParameter("status");
            if(!StringUtil.isBlank(status)){
                sightUrl.setStatus(Integer.valueOf(status)) ;
            }
            
            String createTime = request.getParameter("createTime");
            if(!StringUtil.isBlank(createTime)){
                sightUrl.setCreateTime(Timestamp.valueOf(createTime)) ;
            }
            
            String updatetime = request.getParameter("updatetime");
            if(!StringUtil.isBlank(updatetime)){
                sightUrl.setUpdatetime(Timestamp.valueOf(updatetime)) ;
            }
            
            String outId = request.getParameter("outId");
            if(!StringUtil.isBlank(outId)){
                sightUrl.setOutId(Integer.valueOf(outId)) ;
            }
            */
            String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sightUrl.setId(Integer.valueOf(id));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            sightUrl.setName(name);
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            sightUrl.setUrl(url);
        }
        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            sightUrl.setPlatform(platform);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            sightUrl.setType(Integer.valueOf(type));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            sightUrl.setStatus(Integer.valueOf(status));
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                sightUrl.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                sightUrl.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                sightUrl.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                sightUrl.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String outId = request.getParameter("outId");
        if(!StringUtil.isBlank(outId)){
            sightUrl.setOutId(Integer.valueOf(outId));
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("name", name, "景区名称",  new Rule[]{new Length(100)});
        vu.add("url", url, "url",  new Rule[]{new Length(500)});
        vu.add("platform", platform, "平台",  new Rule[]{new Length(50)});
        vu.add("type", type, "类型",  new Rule[]{new Digits(4,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","9"}),new NotEmpty()});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("outId", outId, "外部id",  new Rule[]{new Digits(15,0)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

            return sightUrlService.save(sightUrl);

        }
    /**
     * 说明:删除SightUrl信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-8-9 15:05:37
     */
     @API( summary="根据id删除单个用户信息",
        description = "根据id删除单个用户信息",
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
//        Integer id = Integer.valueOf(idStr);
        sightUrlService.delete(id);
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
       return  sightUrlService.multilDelete(idAry);
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
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }
        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            params.put("platform",platform);
        }
        String platformLike = request.getParameter("platformLike");
        if(!StringUtil.isBlank(platformLike)){
            params.put("platformLike",platformLike);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
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
        List<SightUrl> list =sightUrlService.listByParams(params);
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
        colTitle.put("name", "景区名称");
        colTitle.put("url", "url");
        colTitle.put("platform", "平台");
        colTitle.put("type", "类型");
        colTitle.put("status", "状态");
        colTitle.put("createTime", "创建时间");
        colTitle.put("updatetime", "更新时间");
        colTitle.put("outId", "外部id");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            SightUrl sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("name",  list.get(i).getName());
            map.put("url",  list.get(i).getUrl());
            map.put("platform",  list.get(i).getPlatform());
            map.put("type",  list.get(i).getType());
            map.put("status",  list.get(i).getStatus());
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
         * 说明: 跳转到SightUrl列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/SightUrlList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/SightUrlListMapper.html";
        }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-8-9 15:05:37
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/SightUrlEdit.html";
    }
    /**
         * 说明:跳转查看页面
         * @param request 发请求
         * @return String
         * @author dozen.zhang
         * @date 2018-8-9 15:05:37
         */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/SightUrlView.html";
    }
}
