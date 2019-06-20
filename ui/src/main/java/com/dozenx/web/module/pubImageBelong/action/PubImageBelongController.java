/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2019-2-24 13:04:28
 * 文件说明: 
 */

package com.dozenx.web.module.pubImageBelong.action;
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
import com.dozenx.web.module.pubImageBelong.service.PubImageBelongService;
import com.dozenx.web.module.pubImageBelong.bean.PubImageBelong;
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

@APIs(description = "用户")
@Controller
@RequestMapping("/pubimage/belong/")
public class PubImageBelongController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(PubImageBelongController.class);
    /** 权限service **/
    @Autowired
    private PubImageBelongService pubImageBelongService;
    


    /**
     * 说明:ajax请求PubImageBelong信息
     * @author dozen.zhang
     * @date 2019-2-24 13:04:28
     * @return String
     */
       @API(summary="用户列表接口",
                 description="用户列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="url" , description="url",dataType = DataType.STRING,required =false),// false
                    @Param(name="creator" , description="上传照片人的Id",dataType = DataType.STRING,required =false),// false
                    @Param(name="creatorName" , description="上传人的姓名",dataType = DataType.STRING,required =false),// false
                    @Param(name="createDate" , description="上传照片的时间",dataType = DataType.DATE,required =false),// false
                    @Param(name="status" , description="照片的状态 0 使用状态 1 移除状态 9 彻底删除状态",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="order" , description="顺序id",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="pid" , description="父组件id",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="pubImageId" , description="图片id",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="type" , description="类别",dataType = DataType.STRING,required =false),// false
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
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            params.put("creator",creator);
        }
        String creatorLike = request.getParameter("creatorLike");
        if(!StringUtil.isBlank(creatorLike)){
            params.put("creatorLike",creatorLike);
        }
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            params.put("creatorName",creatorName);
        }
        String creatorNameLike = request.getParameter("creatorNameLike");
        if(!StringUtil.isBlank(creatorNameLike)){
            params.put("creatorNameLike",creatorNameLike);
        }
        String createDate = request.getParameter("createDate");
        if(!StringUtil.isBlank(createDate)){
            if(StringUtil.checkNumeric(createDate)){
                params.put("createDate",createDate);
            }else if(StringUtil.checkDateStr(createDate, "yyyy-MM-dd")){
                params.put("createDate",DateUtil.parseToDate(createDate, "yyyy-MM-dd"));
            }
        }
        String createDateBegin = request.getParameter("createDateBegin");
        if(!StringUtil.isBlank(createDateBegin)){
            if(StringUtil.checkNumeric(createDateBegin)){
                params.put("createDateBegin",createDateBegin);
            }else if(StringUtil.checkDateStr(createDateBegin, "yyyy-MM-dd")){
                params.put("createDateBegin",DateUtil.parseToDate(createDateBegin, "yyyy-MM-dd"));
            }
        }
        String createDateEnd = request.getParameter("createDateEnd");
        if(!StringUtil.isBlank(createDateEnd)){
            if(StringUtil.checkNumeric(createDateEnd)){
                params.put("createDateEnd",createDateEnd);
            }else if(StringUtil.checkDateStr(createDateEnd, "yyyy-MM-dd")){
                params.put("createDateEnd",DateUtil.parseToDate(createDateEnd, "yyyy-MM-dd"));
            }
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String order = request.getParameter("order");
        if(!StringUtil.isBlank(order)){
            params.put("order",order);
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String pubImageId = request.getParameter("pubImageId");
        if(!StringUtil.isBlank(pubImageId)){
            params.put("pubImageId",pubImageId);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String typeLike = request.getParameter("typeLike");
        if(!StringUtil.isBlank(typeLike)){
            params.put("typeLike",typeLike);
        }

        params.put("page",page);
        List<PubImageBelong> pubImageBelongs = pubImageBelongService.listByParams4Page(params);
        return ResultUtil.getResult(pubImageBelongs, page);
    }
    
   /**
    * 说明:ajax请求PubImageBelong信息 无分页版本
    * @return ResultDTO 返回结果
    * @author dozen.zhang
    * @date 2019-2-24 13:04:28
    */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
                HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            params.put("creator",creator);
        }
        String creatorLike = request.getParameter("creatorLike");
        if(!StringUtil.isBlank(creatorLike)){
            params.put("creatorLike",creatorLike);
        }
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            params.put("creatorName",creatorName);
        }
        String creatorNameLike = request.getParameter("creatorNameLike");
        if(!StringUtil.isBlank(creatorNameLike)){
            params.put("creatorNameLike",creatorNameLike);
        }
        String createDate = request.getParameter("createDate");
        if(!StringUtil.isBlank(createDate)){
            if(StringUtil.checkNumeric(createDate)){
                params.put("createDate",createDate);
            }else if(StringUtil.checkDateStr(createDate, "yyyy-MM-dd")){
                params.put("createDate",DateUtil.parseToDate(createDate, "yyyy-MM-dd"));
            }
        }
        String createDateBegin = request.getParameter("createDateBegin");
        if(!StringUtil.isBlank(createDateBegin)){
            if(StringUtil.checkNumeric(createDateBegin)){
                params.put("createDateBegin",createDateBegin);
            }else if(StringUtil.checkDateStr(createDateBegin, "yyyy-MM-dd")){
                params.put("createDateBegin",DateUtil.parseToDate(createDateBegin, "yyyy-MM-dd"));
            }
        }
        String createDateEnd = request.getParameter("createDateEnd");
        if(!StringUtil.isBlank(createDateEnd)){
            if(StringUtil.checkNumeric(createDateEnd)){
                params.put("createDateEnd",createDateEnd);
            }else if(StringUtil.checkDateStr(createDateEnd, "yyyy-MM-dd")){
                params.put("createDateEnd",DateUtil.parseToDate(createDateEnd, "yyyy-MM-dd"));
            }
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String order = request.getParameter("order");
        if(!StringUtil.isBlank(order)){
            params.put("order",order);
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String pubImageId = request.getParameter("pubImageId");
        if(!StringUtil.isBlank(pubImageId)){
            params.put("pubImageId",pubImageId);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String typeLike = request.getParameter("typeLike");
        if(!StringUtil.isBlank(typeLike)){
            params.put("typeLike",typeLike);
        }

        List<PubImageBelong> pubImageBelongs = pubImageBelongService.listByParams(params);
        return ResultUtil.getDataResult(pubImageBelongs);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2019-2-24 13:04:28
    */
     @API( summary="根据id查询单个用户信息",
               description = "根据id查询单个用户信息",
               parameters={
                       @Param(name="id" , description="id",in=InType.path,dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(@PathVariable Integer id,HttpServletRequest request) {
                     HashMap<String,Object> result =new HashMap</*String,Object*/>();
            PubImageBelong bean = pubImageBelongService.selectByPrimaryKey(Integer.valueOf(id));
            result.put("bean", bean);
            return this.getResult(result);

        }



     /**
        * 说明:查看单条信息
        * @param request 发请求
        * @return String
        * @author dozen.zhang
        * @date 2019-2-24 13:04:28
        */
      @API( summary="根据id查询单个用户信息",
               description = "根据id查询单个用户信息",
               parameters={
                       @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view.json",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(HttpServletRequest request) {
         String id = request.getParameter("id");
            PubImageBelong bean = pubImageBelongService.selectByPrimaryKey(Integer.valueOf(id));
          //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
           // result.put("bean", bean);
            return this.getResult(bean);
        }


    /**
     * 说明:更新PubImageBelong信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2019-2-24 13:04:28
     */
      @API( summary="更新id更新单个用户信息",
        description = "更新id更新单个用户信息",
        parameters={
           @Param(name="id" , description="编号",type="INTEGER",required = false),
           @Param(name="url" , description="url",type="STRING",required = false),
           @Param(name="creator" , description="上传照片人的Id",type="STRING",required = false),
           @Param(name="creatorName" , description="上传人的姓名",type="STRING",required = false),
           @Param(name="createDate" , description="上传照片的时间",type="DATE",required = false),
           @Param(name="status" , description="照片的状态 0 使用状态 1 移除状态 9 彻底删除状态",type="INTEGER",required = false),
           @Param(name="order" , description="顺序id",type="INTEGER",required = false),
           @Param(name="pid" , description="父组件id",type="INTEGER",required = false),
           @Param(name="pubImageId" , description="图片id",type="INTEGER",required = false),
           @Param(name="type" , description="类别",type="STRING",required = false),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update.json",method = RequestMethod.PUT)///{id}
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Integer id,
        PubImageBelong pubImageBelong =new  PubImageBelong();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            pubImageBelong.setId(Integer.valueOf(id)) ;
        }
        
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            pubImageBelong.setUrl(String.valueOf(url)) ;
        }
        
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            pubImageBelong.setCreator(String.valueOf(creator)) ;
        }
        
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            pubImageBelong.setCreatorName(String.valueOf(creatorName)) ;
        }
        
        String createDate = request.getParameter("createDate");
        if(!StringUtil.isBlank(createDate)){
            pubImageBelong.setCreateDate(Date.valueOf(createDate)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            pubImageBelong.setStatus(Integer.valueOf(status)) ;
        }
        
        String order = request.getParameter("order");
        if(!StringUtil.isBlank(order)){
            pubImageBelong.setOrder(Integer.valueOf(order)) ;
        }
        
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            pubImageBelong.setPid(Integer.valueOf(pid)) ;
        }
        
        String pubImageId = request.getParameter("pubImageId");
        if(!StringUtil.isBlank(pubImageId)){
            pubImageBelong.setPubImageId(Integer.valueOf(pubImageId)) ;
        }
        
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            pubImageBelong.setType(String.valueOf(type)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            pubImageBelong.setId(Integer.valueOf(id));
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            pubImageBelong.setUrl(url);
        }
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            pubImageBelong.setCreator(creator);
        }
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            pubImageBelong.setCreatorName(creatorName);
        }
        String createDate = request.getParameter("createDate");
        if(!StringUtil.isBlank(createDate)){
            if(StringUtil.checkNumeric(createDate)){
                pubImageBelong.setCreateDate(new Date(createDate));
            }else if(StringUtil.checkDateStr(createDate, "yyyy-MM-dd")){
                pubImageBelong.setCreateDate(DateUtil.parseToDate(createDate, "yyyy-MM-dd"));
            }
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            pubImageBelong.setStatus(Integer.valueOf(status));
        }
        String order = request.getParameter("order");
        if(!StringUtil.isBlank(order)){
            pubImageBelong.setOrder(Integer.valueOf(order));
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            pubImageBelong.setPid(Integer.valueOf(pid));
        }
        String pubImageId = request.getParameter("pubImageId");
        if(!StringUtil.isBlank(pubImageId)){
            pubImageBelong.setPubImageId(Integer.valueOf(pubImageId));
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            pubImageBelong.setType(type);
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("url", url, "url",  new Rule[]{new Length(100)});
        vu.add("creator", creator, "上传照片人的Id",  new Rule[]{new Length(50)});
        vu.add("creatorName", creatorName, "上传人的姓名",  new Rule[]{new Length(50)});
        vu.add("createDate", createDate, "上传照片的时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("status", status, "照片的状态 0 使用状态 1 移除状态 9 彻底删除状态",  new Rule[]{new Digits(1,0)});
        vu.add("order", order, "顺序id",  new Rule[]{new Digits(3,0)});
        vu.add("pid", pid, "父组件id",  new Rule[]{new Digits(11,0)});
        vu.add("pubImageId", pubImageId, "图片id",  new Rule[]{new Digits(11,0)});
        vu.add("type", type, "类别",  new Rule[]{new Length(11)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return pubImageBelongService.save(pubImageBelong);
       
    }


        /**
         * 说明:添加PubImageBelong信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-2-24 13:04:28
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个用户信息",
            description = "添加单个用户信息",
            parameters={
               @Param(name="id" , description="编号",dataType = DataType.INTEGER,required = false),
               @Param(name="url" , description="url",dataType = DataType.STRING,required = false),
               @Param(name="creator" , description="上传照片人的Id",dataType = DataType.STRING,required = false),
               @Param(name="creatorName" , description="上传人的姓名",dataType = DataType.STRING,required = false),
               @Param(name="createDate" , description="上传照片的时间",dataType = DataType.DATE,required = false),
               @Param(name="status" , description="照片的状态 0 使用状态 1 移除状态 9 彻底删除状态",dataType = DataType.INTEGER,required = false),
               @Param(name="order" , description="顺序id",dataType = DataType.INTEGER,required = false),
               @Param(name="pid" , description="父组件id",dataType = DataType.INTEGER,required = false),
               @Param(name="pubImageId" , description="图片id",dataType = DataType.INTEGER,required = false),
               @Param(name="type" , description="类别",dataType = DataType.STRING,required = false),
            })
        @RequestMapping(value = "add.json",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            PubImageBelong pubImageBelong =new  PubImageBelong();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                pubImageBelong.setId(Integer.valueOf(id)) ;
            }
            
            String url = request.getParameter("url");
            if(!StringUtil.isBlank(url)){
                pubImageBelong.setUrl(String.valueOf(url)) ;
            }
            
            String creator = request.getParameter("creator");
            if(!StringUtil.isBlank(creator)){
                pubImageBelong.setCreator(String.valueOf(creator)) ;
            }
            
            String creatorName = request.getParameter("creatorName");
            if(!StringUtil.isBlank(creatorName)){
                pubImageBelong.setCreatorName(String.valueOf(creatorName)) ;
            }
            
            String createDate = request.getParameter("createDate");
            if(!StringUtil.isBlank(createDate)){
                pubImageBelong.setCreateDate(Date.valueOf(createDate)) ;
            }
            
            String status = request.getParameter("status");
            if(!StringUtil.isBlank(status)){
                pubImageBelong.setStatus(Integer.valueOf(status)) ;
            }
            
            String order = request.getParameter("order");
            if(!StringUtil.isBlank(order)){
                pubImageBelong.setOrder(Integer.valueOf(order)) ;
            }
            
            String pid = request.getParameter("pid");
            if(!StringUtil.isBlank(pid)){
                pubImageBelong.setPid(Integer.valueOf(pid)) ;
            }
            
            String pubImageId = request.getParameter("pubImageId");
            if(!StringUtil.isBlank(pubImageId)){
                pubImageBelong.setPubImageId(Integer.valueOf(pubImageId)) ;
            }
            
            String type = request.getParameter("type");
            if(!StringUtil.isBlank(type)){
                pubImageBelong.setType(String.valueOf(type)) ;
            }
            */
            String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            pubImageBelong.setId(Integer.valueOf(id));
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            pubImageBelong.setUrl(url);
        }
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            pubImageBelong.setCreator(creator);
        }
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            pubImageBelong.setCreatorName(creatorName);
        }
        String createDate = request.getParameter("createDate");
        if(!StringUtil.isBlank(createDate)){
            if(StringUtil.checkNumeric(createDate)){
                pubImageBelong.setCreateDate(new Date(createDate));
            }else if(StringUtil.checkDateStr(createDate, "yyyy-MM-dd")){
                pubImageBelong.setCreateDate(DateUtil.parseToDate(createDate, "yyyy-MM-dd"));
            }
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            pubImageBelong.setStatus(Integer.valueOf(status));
        }
        String order = request.getParameter("order");
        if(!StringUtil.isBlank(order)){
            pubImageBelong.setOrder(Integer.valueOf(order));
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            pubImageBelong.setPid(Integer.valueOf(pid));
        }
        String pubImageId = request.getParameter("pubImageId");
        if(!StringUtil.isBlank(pubImageId)){
            pubImageBelong.setPubImageId(Integer.valueOf(pubImageId));
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            pubImageBelong.setType(type);
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("url", url, "url",  new Rule[]{new Length(100)});
        vu.add("creator", creator, "上传照片人的Id",  new Rule[]{new Length(50)});
        vu.add("creatorName", creatorName, "上传人的姓名",  new Rule[]{new Length(50)});
        vu.add("createDate", createDate, "上传照片的时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("status", status, "照片的状态 0 使用状态 1 移除状态 9 彻底删除状态",  new Rule[]{new Digits(1,0)});
        vu.add("order", order, "顺序id",  new Rule[]{new Digits(3,0)});
        vu.add("pid", pid, "父组件id",  new Rule[]{new Digits(11,0)});
        vu.add("pubImageId", pubImageId, "图片id",  new Rule[]{new Digits(11,0)});
        vu.add("type", type, "类别",  new Rule[]{new Length(11)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

            return pubImageBelongService.save(pubImageBelong);

        }


          /**
                 * 说明:添加PubImageBelong信息
                 * @param request
                 * @throws Exception
                 * @return ResultDTO
                 * @author dozen.zhang
                 * @date 2019-2-24 13:04:28
                 */
                // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                @API( summary="添加单个用户信息",
                    description = "添加单个用户信息",
                    parameters={
                       @Param(name="id" , description="编号",dataType = DataType.INTEGER,required = false),
                       @Param(name="url" , description="url",dataType = DataType.STRING,required = false),
                       @Param(name="creator" , description="上传照片人的Id",dataType = DataType.STRING,required = false),
                       @Param(name="creatorName" , description="上传人的姓名",dataType = DataType.STRING,required = false),
                       @Param(name="createDate" , description="上传照片的时间",dataType = DataType.DATE,required = false),
                       @Param(name="status" , description="照片的状态 0 使用状态 1 移除状态 9 彻底删除状态",dataType = DataType.INTEGER,required = false),
                       @Param(name="order" , description="顺序id",dataType = DataType.INTEGER,required = false),
                       @Param(name="pid" , description="父组件id",dataType = DataType.INTEGER,required = false),
                       @Param(name="pubImageId" , description="图片id",dataType = DataType.INTEGER,required = false),
                       @Param(name="type" , description="类别",dataType = DataType.STRING,required = false),
                    })
                @RequestMapping(value = "save.json",method = RequestMethod.POST)
                @ResponseBody
                public ResultDTO save(HttpServletRequest request) throws Exception {
                    PubImageBelong pubImageBelong =new  PubImageBelong();
                    /*
                    String id = request.getParameter("id");
                    if(!StringUtil.isBlank(id)){
                        pubImageBelong.setId(Integer.valueOf(id)) ;
                    }
                    
                    String url = request.getParameter("url");
                    if(!StringUtil.isBlank(url)){
                        pubImageBelong.setUrl(String.valueOf(url)) ;
                    }
                    
                    String creator = request.getParameter("creator");
                    if(!StringUtil.isBlank(creator)){
                        pubImageBelong.setCreator(String.valueOf(creator)) ;
                    }
                    
                    String creatorName = request.getParameter("creatorName");
                    if(!StringUtil.isBlank(creatorName)){
                        pubImageBelong.setCreatorName(String.valueOf(creatorName)) ;
                    }
                    
                    String createDate = request.getParameter("createDate");
                    if(!StringUtil.isBlank(createDate)){
                        pubImageBelong.setCreateDate(Date.valueOf(createDate)) ;
                    }
                    
                    String status = request.getParameter("status");
                    if(!StringUtil.isBlank(status)){
                        pubImageBelong.setStatus(Integer.valueOf(status)) ;
                    }
                    
                    String order = request.getParameter("order");
                    if(!StringUtil.isBlank(order)){
                        pubImageBelong.setOrder(Integer.valueOf(order)) ;
                    }
                    
                    String pid = request.getParameter("pid");
                    if(!StringUtil.isBlank(pid)){
                        pubImageBelong.setPid(Integer.valueOf(pid)) ;
                    }
                    
                    String pubImageId = request.getParameter("pubImageId");
                    if(!StringUtil.isBlank(pubImageId)){
                        pubImageBelong.setPubImageId(Integer.valueOf(pubImageId)) ;
                    }
                    
                    String type = request.getParameter("type");
                    if(!StringUtil.isBlank(type)){
                        pubImageBelong.setType(String.valueOf(type)) ;
                    }
                    */
                    String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            pubImageBelong.setId(Integer.valueOf(id));
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            pubImageBelong.setUrl(url);
        }
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            pubImageBelong.setCreator(creator);
        }
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            pubImageBelong.setCreatorName(creatorName);
        }
        String createDate = request.getParameter("createDate");
        if(!StringUtil.isBlank(createDate)){
            if(StringUtil.checkNumeric(createDate)){
                pubImageBelong.setCreateDate(new Date(createDate));
            }else if(StringUtil.checkDateStr(createDate, "yyyy-MM-dd")){
                pubImageBelong.setCreateDate(DateUtil.parseToDate(createDate, "yyyy-MM-dd"));
            }
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            pubImageBelong.setStatus(Integer.valueOf(status));
        }
        String order = request.getParameter("order");
        if(!StringUtil.isBlank(order)){
            pubImageBelong.setOrder(Integer.valueOf(order));
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            pubImageBelong.setPid(Integer.valueOf(pid));
        }
        String pubImageId = request.getParameter("pubImageId");
        if(!StringUtil.isBlank(pubImageId)){
            pubImageBelong.setPubImageId(Integer.valueOf(pubImageId));
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            pubImageBelong.setType(type);
        }

                    //valid
                    ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("url", url, "url",  new Rule[]{new Length(100)});
        vu.add("creator", creator, "上传照片人的Id",  new Rule[]{new Length(50)});
        vu.add("creatorName", creatorName, "上传人的姓名",  new Rule[]{new Length(50)});
        vu.add("createDate", createDate, "上传照片的时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("status", status, "照片的状态 0 使用状态 1 移除状态 9 彻底删除状态",  new Rule[]{new Digits(1,0)});
        vu.add("order", order, "顺序id",  new Rule[]{new Digits(3,0)});
        vu.add("pid", pid, "父组件id",  new Rule[]{new Digits(11,0)});
        vu.add("pubImageId", pubImageId, "图片id",  new Rule[]{new Digits(11,0)});
        vu.add("type", type, "类别",  new Rule[]{new Length(11)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

                    return pubImageBelongService.save(pubImageBelong);

                }

    /**
     * 说明:删除PubImageBelong信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2019-2-24 13:04:28
     */
     @API( summary="根据id删除单个用户信息",
        description = "根据id删除单个用户信息",
        parameters={
         @Param(name="id" , description="编号",dataType= DataType.INTEGER,required = true),
        })
    @RequestMapping(value = "/delete.json",method = RequestMethod.DELETE)//{id}
    @ResponseBody
    public ResultDTO delete(HttpServletRequest request) {//@PathVariable Integer id,
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Integer id = Integer.valueOf(idStr);
        pubImageBelongService.delete(id);
        return this.getResult(SUCC);
    }

     /**
         * 说明:删除PubImageBelong信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-2-24 13:04:28
         */
         @API( summary="根据id删除单个用户信息",
            description = "根据id删除单个用户信息",
            parameters={
             @Param(name="id" , description="编号",in=InType.path,dataType= DataType.INTEGER,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable Integer id,HttpServletRequest request) {
            pubImageBelongService.delete(id);
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
       return  pubImageBelongService.multilDelete(idAry);
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
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            params.put("creator",creator);
        }
        String creatorLike = request.getParameter("creatorLike");
        if(!StringUtil.isBlank(creatorLike)){
            params.put("creatorLike",creatorLike);
        }
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            params.put("creatorName",creatorName);
        }
        String creatorNameLike = request.getParameter("creatorNameLike");
        if(!StringUtil.isBlank(creatorNameLike)){
            params.put("creatorNameLike",creatorNameLike);
        }
        String createDate = request.getParameter("createDate");
        if(!StringUtil.isBlank(createDate)){
            if(StringUtil.checkNumeric(createDate)){
                params.put("createDate",createDate);
            }else if(StringUtil.checkDateStr(createDate, "yyyy-MM-dd")){
                params.put("createDate",DateUtil.parseToDate(createDate, "yyyy-MM-dd"));
            }
        }
        String createDateBegin = request.getParameter("createDateBegin");
        if(!StringUtil.isBlank(createDateBegin)){
            if(StringUtil.checkNumeric(createDateBegin)){
                params.put("createDateBegin",createDateBegin);
            }else if(StringUtil.checkDateStr(createDateBegin, "yyyy-MM-dd")){
                params.put("createDateBegin",DateUtil.parseToDate(createDateBegin, "yyyy-MM-dd"));
            }
        }
        String createDateEnd = request.getParameter("createDateEnd");
        if(!StringUtil.isBlank(createDateEnd)){
            if(StringUtil.checkNumeric(createDateEnd)){
                params.put("createDateEnd",createDateEnd);
            }else if(StringUtil.checkDateStr(createDateEnd, "yyyy-MM-dd")){
                params.put("createDateEnd",DateUtil.parseToDate(createDateEnd, "yyyy-MM-dd"));
            }
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String order = request.getParameter("order");
        if(!StringUtil.isBlank(order)){
            params.put("order",order);
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String pubImageId = request.getParameter("pubImageId");
        if(!StringUtil.isBlank(pubImageId)){
            params.put("pubImageId",pubImageId);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String typeLike = request.getParameter("typeLike");
        if(!StringUtil.isBlank(typeLike)){
            params.put("typeLike",typeLike);
        }

        // 查询list集合
        List<PubImageBelong> list =pubImageBelongService.listByParams(params);
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
        colTitle.put("url", "url");
        colTitle.put("creator", "上传照片人的Id");
        colTitle.put("creatorName", "上传人的姓名");
        colTitle.put("createDate", "上传照片的时间");
        colTitle.put("status", "照片的状态 0 使用状态 1 移除状态 9 彻底删除状态");
        colTitle.put("order", "顺序id");
        colTitle.put("pid", "父组件id");
        colTitle.put("pubImageId", "图片id");
        colTitle.put("type", "类别");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            PubImageBelong sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("url",  list.get(i).getUrl());
            map.put("creator",  list.get(i).getCreator());
            map.put("creatorName",  list.get(i).getCreatorName());
            map.put("createDate",  list.get(i).getCreateDate());
            map.put("status",  list.get(i).getStatus());
            map.put("order",  list.get(i).getOrder());
            map.put("pid",  list.get(i).getPid());
            map.put("pubImageId",  list.get(i).getPubImageId());
            map.put("type",  list.get(i).getType());
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
                        @Param(name = "file", description = "编号", in = InType.formData, dataType = DataType.FILE, required = true),
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

                        PubImageBelong bean = getInfoFromMap(params);

                      //  if (count > 0) {

                      //      logger.warn("邮箱已经存在:" + email);
                       //     errorMsg.append("邮箱已经存在:" + email);
                         //   continue;

                       // }

                        try {
                            pubImageBelongService.save(bean);
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
         * 说明: 跳转到PubImageBelong列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/PubImageBelongList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/PubImageBelongListMapper.html";
        }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2019-2-24 13:04:28
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/PubImageBelongEdit.html";
    }
    /**
         * 说明:跳转查看页面
         * @param request 发请求
         * @return String
         * @author dozen.zhang
         * @date 2019-2-24 13:04:28
         */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/PubImageBelongView.html";
    }



    private PubImageBelong getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       PubImageBelong pubImageBelong =new  PubImageBelong();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                pubImageBelong.setId(Integer.valueOf(id));
        }
        String url = MapUtils.getString(bodyParam,"url");
        if(!StringUtil.isBlank(url)){
                pubImageBelong.setUrl(String.valueOf(url));
        }
        String creator = MapUtils.getString(bodyParam,"creator");
        if(!StringUtil.isBlank(creator)){
                pubImageBelong.setCreator(String.valueOf(creator));
        }
        String creatorName = MapUtils.getString(bodyParam,"creatorName");
        if(!StringUtil.isBlank(creatorName)){
                pubImageBelong.setCreatorName(String.valueOf(creatorName));
        }
        String createDate = MapUtils.getString(bodyParam,"createDate");
        if(!StringUtil.isBlank(createDate)){
            if(StringUtil.checkNumeric(createDate)){
                pubImageBelong.setCreateDate(new Date(createDate));
            }else if(StringUtil.checkDateStr(createDate, "yyyy-MM-dd")){
                pubImageBelong.setCreateDate(DateUtil.parseToDate(createDate, "yyyy-MM-dd"));
            }
        }
        String status = MapUtils.getString(bodyParam,"status");
        if(!StringUtil.isBlank(status)){
                pubImageBelong.setStatus(Integer.valueOf(status));
        }
        String order = MapUtils.getString(bodyParam,"order");
        if(!StringUtil.isBlank(order)){
                pubImageBelong.setOrder(Integer.valueOf(order));
        }
        String pid = MapUtils.getString(bodyParam,"pid");
        if(!StringUtil.isBlank(pid)){
                pubImageBelong.setPid(Integer.valueOf(pid));
        }
        String pubImageId = MapUtils.getString(bodyParam,"pubImageId");
        if(!StringUtil.isBlank(pubImageId)){
                pubImageBelong.setPubImageId(Integer.valueOf(pubImageId));
        }
        String type = MapUtils.getString(bodyParam,"type");
        if(!StringUtil.isBlank(type)){
                pubImageBelong.setType(String.valueOf(type));
        }

        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("url", url, "url",  new Rule[]{new Length(100)});
        vu.add("creator", creator, "上传照片人的Id",  new Rule[]{new Length(50)});
        vu.add("creatorName", creatorName, "上传人的姓名",  new Rule[]{new Length(50)});
        vu.add("createDate", createDate, "上传照片的时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("status", status, "照片的状态 0 使用状态 1 移除状态 9 彻底删除状态",  new Rule[]{new Digits(1,0)});
        vu.add("order", order, "顺序id",  new Rule[]{new Digits(3,0)});
        vu.add("pid", pid, "父组件id",  new Rule[]{new Digits(11,0)});
        vu.add("pubImageId", pubImageId, "图片id",  new Rule[]{new Digits(11,0)});
        vu.add("type", type, "类别",  new Rule[]{new Length(11)});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  pubImageBelong;
    }


      /**
                     * 说明:添加PubImageBelong信息
                     * @param request
                     * @throws Exception
                     * @return ResultDTO
                     * @author dozen.zhang
                     * @date 2019-2-24 13:04:28
                     */
                    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                    @API( summary="添加单个用户信息",
                        description = "添加单个用户信息",
                        parameters={
                           @Param(name="id" , description="编号"  ,in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="url" , description="url"  ,in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="creator" , description="上传照片人的Id"  ,in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="creatorName" , description="上传人的姓名"  ,in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="createDate" , description="上传照片的时间"  ,in=InType.body,dataType = DataType.DATE,required = false),
                           @Param(name="status" , description="照片的状态 0 使用状态 1 移除状态 9 彻底删除状态"  ,in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="order" , description="顺序id"  ,in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="pid" , description="父组件id"  ,in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="pubImageId" , description="图片id"  ,in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="type" , description="类别"  ,in=InType.body,dataType = DataType.STRING,required = false),
                        })
                    @RequestMapping(value = "add",method = RequestMethod.POST)
                    @ResponseBody
                    public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
                        PubImageBelong pubImageBelong =    getInfoFromMap(bodyParam);


                        return pubImageBelongService.save(pubImageBelong);

                    }


    /**
    * 说明:添加PubImageBelong信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2019-2-24 13:04:28
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个用户信息",
    description = "更新单个用户信息",
    parameters={
        @Param(name="id" , description="编号  ",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="url" , description="url  ",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="creator" , description="上传照片人的Id  ",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="creatorName" , description="上传人的姓名  ",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="createDate" , description="上传照片的时间  ",in=InType.body,dataType = DataType.DATE,required = false),
        @Param(name="status" , description="照片的状态 0 使用状态 1 移除状态 9 彻底删除状态  ",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="order" , description="顺序id  ",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="pid" , description="父组件id  ",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="pubImageId" , description="图片id  ",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="type" , description="类别  ",in=InType.body,dataType = DataType.STRING,required = false),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    PubImageBelong pubImageBelong =    getInfoFromMap(bodyParam);
    return pubImageBelongService.save(pubImageBelong);

    }
/**
     * 说明:ajax请求PubImageBelong信息
     * @author dozen.zhang
     * @date 2019-2-24 13:04:28
     * @return String
     */
       @API(summary="用户列表接口",
                 description="用户列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号  ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="url" , description="url  ",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="creator" , description="上传照片人的Id  ",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="creatorName" , description="上传人的姓名  ",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="createDate" , description="上传照片的时间  ",in=InType.params,dataType = DataType.DATE,required =false),// false
                    @Param(name="status" , description="照片的状态 0 使用状态 1 移除状态 9 彻底删除状态  ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="order" , description="顺序id  ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="pid" , description="父组件id  ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="pubImageId" , description="图片id  ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="type" , description="类别  ",in=InType.params,dataType = DataType.STRING,required =false),// false
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
        String url = MapUtils.getString(params,"url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = MapUtils.getString(params,"urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }
        String creator = MapUtils.getString(params,"creator");
        if(!StringUtil.isBlank(creator)){
            params.put("creator",creator);
        }
        String creatorLike = MapUtils.getString(params,"creatorLike");
        if(!StringUtil.isBlank(creatorLike)){
            params.put("creatorLike",creatorLike);
        }
        String creatorName = MapUtils.getString(params,"creatorName");
        if(!StringUtil.isBlank(creatorName)){
            params.put("creatorName",creatorName);
        }
        String creatorNameLike = MapUtils.getString(params,"creatorNameLike");
        if(!StringUtil.isBlank(creatorNameLike)){
            params.put("creatorNameLike",creatorNameLike);
        }
        String createDate = MapUtils.getString(params,"createDate");
        if(!StringUtil.isBlank(createDate)){
            if(StringUtil.checkNumeric(createDate)){
                params.put("createDate",createDate);
            }else if(StringUtil.checkDateStr(createDate, "yyyy-MM-dd")){
                params.put("createDate",DateUtil.parseToDate(createDate, "yyyy-MM-dd"));
            }
        }
        String createDateBegin = MapUtils.getString(params,"createDateBegin");
        if(!StringUtil.isBlank(createDateBegin)){
            if(StringUtil.checkNumeric(createDateBegin)){
                params.put("createDateBegin",createDateBegin);
            }else if(StringUtil.checkDateStr(createDateBegin, "yyyy-MM-dd")){
                params.put("createDateBegin",DateUtil.parseToDate(createDateBegin, "yyyy-MM-dd"));
            }
        }
        String createDateEnd = MapUtils.getString(params,"createDateEnd");
        if(!StringUtil.isBlank(createDateEnd)){
            if(StringUtil.checkNumeric(createDateEnd)){
                params.put("createDateEnd",createDateEnd);
            }else if(StringUtil.checkDateStr(createDateEnd, "yyyy-MM-dd")){
                params.put("createDateEnd",DateUtil.parseToDate(createDateEnd, "yyyy-MM-dd"));
            }
        }
        String status = MapUtils.getString(params,"status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String order = MapUtils.getString(params,"order");
        if(!StringUtil.isBlank(order)){
            params.put("order",order);
        }
        String pid = MapUtils.getString(params,"pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String pubImageId = MapUtils.getString(params,"pubImageId");
        if(!StringUtil.isBlank(pubImageId)){
            params.put("pubImageId",pubImageId);
        }
        String type = MapUtils.getString(params,"type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String typeLike = MapUtils.getString(params,"typeLike");
        if(!StringUtil.isBlank(typeLike)){
            params.put("typeLike",typeLike);
        }

        params.put("page",page);
        List<PubImageBelong> pubImageBelongs = pubImageBelongService.listByParams4Page(params);
        return ResultUtil.getResult(pubImageBelongs, page);
    }



       /**
         * 导出
         * @param request
         * @return
         * @author dozen.zhang
         */
        @API(summary="用户列表导出接口",
          description="用户列表导出接口",
          parameters={
          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
             @Param(name="id" , description="编号 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="url" , description="url ",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="creator" , description="上传照片人的Id ",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="creatorName" , description="上传人的姓名 ",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="createDate" , description="上传照片的时间 ",in=InType.params,dataType = DataType.DATE,required =false),// false
             @Param(name="status" , description="照片的状态 0 使用状态 1 移除状态 9 彻底删除状态 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="order" , description="顺序id ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="pid" , description="父组件id ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="pubImageId" , description="图片id ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="type" , description="类别 ",in=InType.params,dataType = DataType.STRING,required =false),// false
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
        String url = MapUtils.getString(params,"url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = MapUtils.getString(params,"urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }
        String creator = MapUtils.getString(params,"creator");
        if(!StringUtil.isBlank(creator)){
            params.put("creator",creator);
        }
        String creatorLike = MapUtils.getString(params,"creatorLike");
        if(!StringUtil.isBlank(creatorLike)){
            params.put("creatorLike",creatorLike);
        }
        String creatorName = MapUtils.getString(params,"creatorName");
        if(!StringUtil.isBlank(creatorName)){
            params.put("creatorName",creatorName);
        }
        String creatorNameLike = MapUtils.getString(params,"creatorNameLike");
        if(!StringUtil.isBlank(creatorNameLike)){
            params.put("creatorNameLike",creatorNameLike);
        }
        String createDate = MapUtils.getString(params,"createDate");
        if(!StringUtil.isBlank(createDate)){
            if(StringUtil.checkNumeric(createDate)){
                params.put("createDate",createDate);
            }else if(StringUtil.checkDateStr(createDate, "yyyy-MM-dd")){
                params.put("createDate",DateUtil.parseToDate(createDate, "yyyy-MM-dd"));
            }
        }
        String createDateBegin = MapUtils.getString(params,"createDateBegin");
        if(!StringUtil.isBlank(createDateBegin)){
            if(StringUtil.checkNumeric(createDateBegin)){
                params.put("createDateBegin",createDateBegin);
            }else if(StringUtil.checkDateStr(createDateBegin, "yyyy-MM-dd")){
                params.put("createDateBegin",DateUtil.parseToDate(createDateBegin, "yyyy-MM-dd"));
            }
        }
        String createDateEnd = MapUtils.getString(params,"createDateEnd");
        if(!StringUtil.isBlank(createDateEnd)){
            if(StringUtil.checkNumeric(createDateEnd)){
                params.put("createDateEnd",createDateEnd);
            }else if(StringUtil.checkDateStr(createDateEnd, "yyyy-MM-dd")){
                params.put("createDateEnd",DateUtil.parseToDate(createDateEnd, "yyyy-MM-dd"));
            }
        }
        String status = MapUtils.getString(params,"status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String order = MapUtils.getString(params,"order");
        if(!StringUtil.isBlank(order)){
            params.put("order",order);
        }
        String pid = MapUtils.getString(params,"pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String pubImageId = MapUtils.getString(params,"pubImageId");
        if(!StringUtil.isBlank(pubImageId)){
            params.put("pubImageId",pubImageId);
        }
        String type = MapUtils.getString(params,"type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String typeLike = MapUtils.getString(params,"typeLike");
        if(!StringUtil.isBlank(typeLike)){
            params.put("typeLike",typeLike);
        }

             params.put("page",page);
             List<PubImageBelong> list = pubImageBelongService.listByParams4Page(params);
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
            colTitle.put("url", "url");
            colTitle.put("creator", "上传照片人的Id");
            colTitle.put("creatorName", "上传人的姓名");
            colTitle.put("createDate", "上传照片的时间");
            colTitle.put("status", "照片的状态 0 使用状态 1 移除状态 9 彻底删除状态");
            colTitle.put("order", "顺序id");
            colTitle.put("pid", "父组件id");
            colTitle.put("pubImageId", "图片id");
            colTitle.put("type", "类别");
            List<Map> finalList = new ArrayList<Map>();
            for (int i = 0; i < list.size(); i++) {
                PubImageBelong sm = list.get(i);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id",  list.get(i).getId());
                map.put("url",  list.get(i).getUrl());
                map.put("creator",  list.get(i).getCreator());
                map.put("creatorName",  list.get(i).getCreatorName());
                map.put("createDate",  list.get(i).getCreateDate());
                map.put("status",  list.get(i).getStatus());
                map.put("order",  list.get(i).getOrder());
                map.put("pid",  list.get(i).getPid());
                map.put("pubImageId",  list.get(i).getPubImageId());
                map.put("type",  list.get(i).getType());
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
