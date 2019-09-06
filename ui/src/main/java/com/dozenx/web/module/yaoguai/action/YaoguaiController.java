/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2019-5-26 10:44:45
 * 文件说明: 
 */

package com.dozenx.web.module.yaoguai.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;

import com.dozenx.common.util.*;
import com.dozenx.swagger.annotation.*;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import org.slf4j.Logger;
import com.dozenx.common.exception.ParamException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.dozenx.swagger.annotation.API;
import com.dozenx.swagger.annotation.APIs;
import com.dozenx.swagger.annotation.DataType;
import com.dozenx.swagger.annotation.Param;
import com.dozenx.web.module.yaoguai.service.YaoguaiService;
import com.dozenx.web.module.yaoguai.bean.Yaoguai;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.util.RequestUtil;
import org.springframework.web.bind.annotation.*;
import com.dozenx.web.core.log.ResultDTO;
import org.springframework.web.multipart.MultipartFile;
import com.dozenx.common.Path.PathManager;
import com.dozenx.common.exception.BizException;
import java.nio.file.Files;
import com.dozenx.common.config.SysConfig;

@APIs(description = "妖怪商品")
@Controller
@RequestMapping("/yaoguai")
public class YaoguaiController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(YaoguaiController.class);
    /** 权限service **/
    @Autowired
    private YaoguaiService yaoguaiService;
    


    /**
     * 说明:ajax请求Yaoguai信息
     * @author dozen.zhang
     * @date 2019-5-26 10:44:45
     * @return String
     */
       @API(summary="妖怪商品列表接口",
                 description="妖怪商品列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",dataType = DataType.LONG,required =false),// false
                    @Param(name="name" , description="名称",dataType = DataType.STRING,required =false),// true
                    @Param(name="address" , description="地址",dataType = DataType.STRING,required =false),// false
                    @Param(name="img" , description="图片0",dataType = DataType.STRING,required =false),// true
                    @Param(name="remark" , description="备注",dataType = DataType.STRING,required =false),// false
                    @Param(name="status" , description="状态",dataType = DataType.INTEGER,required =false),// true
                    @Param(name="price" , description="价格",dataType = DataType.FLOAT,required =false),// false
                    @Param(name="creator" , description="创建人id",dataType = DataType.LONG,required =false),// false
                    @Param(name="creatorName" , description="创建人姓名",dataType = DataType.STRING,required =false),// false
                    @Param(name="platform" , description="平台名称",dataType = DataType.STRING,required =false),// false
                    @Param(name="up" , description="顶",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="wg" , description="物攻",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="wf" , description="物防",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="fg" , description="法攻",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="ff" , description="法防",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="sm" , description="生命",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="zf" , description="总分",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="updateTime" , description="更新时间",dataType = DataType.DATE_TIME,required =false),// false
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
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = request.getParameter("addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
        }
        String img = request.getParameter("img");
        if(!StringUtil.isBlank(img)){
            params.put("img",img);
        }
        String imgLike = request.getParameter("imgLike");
        if(!StringUtil.isBlank(imgLike)){
            params.put("imgLike",imgLike);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            params.put("price",price);
        }
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            params.put("creator",creator);
        }
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            params.put("creatorName",creatorName);
        }
        String creatorNameLike = request.getParameter("creatorNameLike");
        if(!StringUtil.isBlank(creatorNameLike)){
            params.put("creatorNameLike",creatorNameLike);
        }
        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            params.put("platform",platform);
        }
        String platformLike = request.getParameter("platformLike");
        if(!StringUtil.isBlank(platformLike)){
            params.put("platformLike",platformLike);
        }
        String up = request.getParameter("up");
        if(!StringUtil.isBlank(up)){
            params.put("up",up);
        }
        String wg = request.getParameter("wg");
        if(!StringUtil.isBlank(wg)){
            params.put("wg",wg);
        }
        String wf = request.getParameter("wf");
        if(!StringUtil.isBlank(wf)){
            params.put("wf",wf);
        }
        String fg = request.getParameter("fg");
        if(!StringUtil.isBlank(fg)){
            params.put("fg",fg);
        }
        String ff = request.getParameter("ff");
        if(!StringUtil.isBlank(ff)){
            params.put("ff",ff);
        }
        String sm = request.getParameter("sm");
        if(!StringUtil.isBlank(sm)){
            params.put("sm",sm);
        }
        String zf = request.getParameter("zf");
        if(!StringUtil.isBlank(zf)){
            params.put("zf",zf);
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
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                params.put("updateTime",updateTime);
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTime",new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeBegin = request.getParameter("updateTimeBegin");
        if(!StringUtil.isBlank(updateTimeBegin)){
            if(StringUtil.checkNumeric(updateTimeBegin)){
                params.put("updateTimeBegin",updateTimeBegin);
            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeBegin",new Timestamp( DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeEnd = request.getParameter("updateTimeEnd");
        if(!StringUtil.isBlank(updateTimeEnd)){
            if(StringUtil.checkNumeric(updateTimeEnd)){
                params.put("updateTimeEnd",updateTimeEnd);
            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeEnd",new Timestamp( DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page",page);
        List<Yaoguai> yaoguais = yaoguaiService.listByParams4Page(params);
        return ResultUtil.getResult(yaoguais, page);
    }
    
   /**
    * 说明:ajax请求Yaoguai信息 无分页版本
    * @return ResultDTO 返回结果
    * @author dozen.zhang
    * @date 2019-5-26 10:44:45
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
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = request.getParameter("addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
        }
        String img = request.getParameter("img");
        if(!StringUtil.isBlank(img)){
            params.put("img",img);
        }
        String imgLike = request.getParameter("imgLike");
        if(!StringUtil.isBlank(imgLike)){
            params.put("imgLike",imgLike);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            params.put("price",price);
        }
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            params.put("creator",creator);
        }
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            params.put("creatorName",creatorName);
        }
        String creatorNameLike = request.getParameter("creatorNameLike");
        if(!StringUtil.isBlank(creatorNameLike)){
            params.put("creatorNameLike",creatorNameLike);
        }
        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            params.put("platform",platform);
        }
        String platformLike = request.getParameter("platformLike");
        if(!StringUtil.isBlank(platformLike)){
            params.put("platformLike",platformLike);
        }
        String up = request.getParameter("up");
        if(!StringUtil.isBlank(up)){
            params.put("up",up);
        }
        String wg = request.getParameter("wg");
        if(!StringUtil.isBlank(wg)){
            params.put("wg",wg);
        }
        String wf = request.getParameter("wf");
        if(!StringUtil.isBlank(wf)){
            params.put("wf",wf);
        }
        String fg = request.getParameter("fg");
        if(!StringUtil.isBlank(fg)){
            params.put("fg",fg);
        }
        String ff = request.getParameter("ff");
        if(!StringUtil.isBlank(ff)){
            params.put("ff",ff);
        }
        String sm = request.getParameter("sm");
        if(!StringUtil.isBlank(sm)){
            params.put("sm",sm);
        }
        String zf = request.getParameter("zf");
        if(!StringUtil.isBlank(zf)){
            params.put("zf",zf);
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
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                params.put("updateTime",updateTime);
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTime",new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeBegin = request.getParameter("updateTimeBegin");
        if(!StringUtil.isBlank(updateTimeBegin)){
            if(StringUtil.checkNumeric(updateTimeBegin)){
                params.put("updateTimeBegin",updateTimeBegin);
            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeBegin",new Timestamp( DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeEnd = request.getParameter("updateTimeEnd");
        if(!StringUtil.isBlank(updateTimeEnd)){
            if(StringUtil.checkNumeric(updateTimeEnd)){
                params.put("updateTimeEnd",updateTimeEnd);
            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeEnd",new Timestamp( DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        List<Yaoguai> yaoguais = yaoguaiService.listByParams(params);
        return ResultUtil.getDataResult(yaoguais);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2019-5-26 10:44:45
    */
     @API( summary="根据id查询单个妖怪商品信息",
               description = "根据id查询单个妖怪商品信息",
               parameters={
                       @Param(name="id" , description="id",in=InType.path,dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(@PathVariable Long id,HttpServletRequest request) {
                     HashMap<String,Object> result =new HashMap</*String,Object*/>();
            Yaoguai bean = yaoguaiService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
            return this.getResult(result);

        }



     /**
        * 说明:查看单条信息
        * @param request 发请求
        * @return String
        * @author dozen.zhang
        * @date 2019-5-26 10:44:45
        */
      @API( summary="根据id查询单个妖怪商品信息",
               description = "根据id查询单个妖怪商品信息",
               parameters={
                       @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view.json",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(HttpServletRequest request) {
         String id = request.getParameter("id");
            Yaoguai bean = yaoguaiService.selectByPrimaryKey(Long.valueOf(id));
          //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
           // result.put("bean", bean);
            return this.getResult(bean);
        }


    /**
     * 说明:更新Yaoguai信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2019-5-26 10:44:45
     */
      @API( summary="更新id更新单个妖怪商品信息",
        description = "更新id更新单个妖怪商品信息",
        parameters={
           @Param(name="id" , description="编号",type="LONG",required = false),
           @Param(name="name" , description="名称",type="STRING",required = true),
           @Param(name="address" , description="地址",type="STRING",required = false),
           @Param(name="img" , description="图片0",type="STRING",required = true),
           @Param(name="remark" , description="备注",type="STRING",required = false),
           @Param(name="status" , description="状态",type="INTEGER",required = true),
           @Param(name="price" , description="价格",type="FLOAT",required = false),
           @Param(name="creator" , description="创建人id",type="LONG",required = false),
           @Param(name="creatorName" , description="创建人姓名",type="STRING",required = false),
           @Param(name="platform" , description="平台名称",type="STRING",required = false),
           @Param(name="up" , description="顶",type="INTEGER",required = false),
           @Param(name="wg" , description="物攻",type="INTEGER",required = false),
           @Param(name="wf" , description="物防",type="INTEGER",required = false),
           @Param(name="fg" , description="法攻",type="INTEGER",required = false),
           @Param(name="ff" , description="法防",type="INTEGER",required = false),
           @Param(name="sm" , description="生命",type="INTEGER",required = false),
           @Param(name="zf" , description="总分",type="INTEGER",required = false),
           @Param(name="createTime" , description="创建时间",type="DATE_TIME",required = false),
           @Param(name="updateTime" , description="更新时间",type="DATE_TIME",required = false),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update.json",method = RequestMethod.PUT)///{id}
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Long id,
        Yaoguai yaoguai =new  Yaoguai();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            yaoguai.setId(Long.valueOf(id)) ;
        }
        
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            yaoguai.setName(String.valueOf(name)) ;
        }
        
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            yaoguai.setAddress(String.valueOf(address)) ;
        }
        
        String img = request.getParameter("img");
        if(!StringUtil.isBlank(img)){
            yaoguai.setImg(String.valueOf(img)) ;
        }
        
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            yaoguai.setRemark(String.valueOf(remark)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            yaoguai.setStatus(Integer.valueOf(status)) ;
        }
        
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            yaoguai.setPrice(BigDecimal.valueOf(price)) ;
        }
        
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            yaoguai.setCreator(Long.valueOf(creator)) ;
        }
        
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            yaoguai.setCreatorName(String.valueOf(creatorName)) ;
        }
        
        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            yaoguai.setPlatform(String.valueOf(platform)) ;
        }
        
        String up = request.getParameter("up");
        if(!StringUtil.isBlank(up)){
            yaoguai.setUp(Integer.valueOf(up)) ;
        }
        
        String wg = request.getParameter("wg");
        if(!StringUtil.isBlank(wg)){
            yaoguai.setWg(Integer.valueOf(wg)) ;
        }
        
        String wf = request.getParameter("wf");
        if(!StringUtil.isBlank(wf)){
            yaoguai.setWf(Integer.valueOf(wf)) ;
        }
        
        String fg = request.getParameter("fg");
        if(!StringUtil.isBlank(fg)){
            yaoguai.setFg(Integer.valueOf(fg)) ;
        }
        
        String ff = request.getParameter("ff");
        if(!StringUtil.isBlank(ff)){
            yaoguai.setFf(Integer.valueOf(ff)) ;
        }
        
        String sm = request.getParameter("sm");
        if(!StringUtil.isBlank(sm)){
            yaoguai.setSm(Integer.valueOf(sm)) ;
        }
        
        String zf = request.getParameter("zf");
        if(!StringUtil.isBlank(zf)){
            yaoguai.setZf(Integer.valueOf(zf)) ;
        }
        
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            yaoguai.setCreateTime(Timestamp.valueOf(createTime)) ;
        }
        
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            yaoguai.setUpdateTime(Timestamp.valueOf(updateTime)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            yaoguai.setId(Long.valueOf(id));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            yaoguai.setName(name);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            yaoguai.setAddress(address);
        }
        String img = request.getParameter("img");
        if(!StringUtil.isBlank(img)){
            yaoguai.setImg(img);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            yaoguai.setRemark(remark);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            yaoguai.setStatus(Integer.valueOf(status));
        }
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            yaoguai.setPrice(new BigDecimal(price));
        }
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            yaoguai.setCreator(Long.valueOf(creator));
        }
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            yaoguai.setCreatorName(creatorName);
        }
        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            yaoguai.setPlatform(platform);
        }
        String up = request.getParameter("up");
        if(!StringUtil.isBlank(up)){
            yaoguai.setUp(Integer.valueOf(up));
        }
        String wg = request.getParameter("wg");
        if(!StringUtil.isBlank(wg)){
            yaoguai.setWg(Integer.valueOf(wg));
        }
        String wf = request.getParameter("wf");
        if(!StringUtil.isBlank(wf)){
            yaoguai.setWf(Integer.valueOf(wf));
        }
        String fg = request.getParameter("fg");
        if(!StringUtil.isBlank(fg)){
            yaoguai.setFg(Integer.valueOf(fg));
        }
        String ff = request.getParameter("ff");
        if(!StringUtil.isBlank(ff)){
            yaoguai.setFf(Integer.valueOf(ff));
        }
        String sm = request.getParameter("sm");
        if(!StringUtil.isBlank(sm)){
            yaoguai.setSm(Integer.valueOf(sm));
        }
        String zf = request.getParameter("zf");
        if(!StringUtil.isBlank(zf)){
            yaoguai.setZf(Integer.valueOf(zf));
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                yaoguai.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                yaoguai.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                yaoguai.setUpdateTime(Timestamp.valueOf(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                yaoguai.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(9,0)});
        vu.add("name", name, "名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("address", address, "地址",  new Rule[]{new Length(200)});
        vu.add("img", img, "图片0",  new Rule[]{new Length(100),new NotEmpty()});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2","3","9"}),new NotEmpty()});
        vu.add("price", price, "价格",  new Rule[]{});
        vu.add("creator", creator, "创建人id",  new Rule[]{new Digits(11,0)});
        vu.add("creatorName", creatorName, "创建人姓名",  new Rule[]{new Length(50)});
        vu.add("platform", platform, "平台名称",  new Rule[]{new Length(50)});
        vu.add("up", up, "顶",  new Rule[]{new Digits(11,0)});
        vu.add("wg", wg, "物攻",  new Rule[]{new Digits(2,0)});
        vu.add("wf", wf, "物防",  new Rule[]{new Digits(2,0)});
        vu.add("fg", fg, "法攻",  new Rule[]{new Digits(2,0)});
        vu.add("ff", ff, "法防",  new Rule[]{new Digits(2,0)});
        vu.add("sm", sm, "生命",  new Rule[]{new Digits(2,0)});
        vu.add("zf", zf, "总分",  new Rule[]{new Digits(3,0)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return yaoguaiService.save(yaoguai);
       
    }


        /**
         * 说明:添加Yaoguai信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-5-26 10:44:45
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个妖怪商品信息",
            description = "添加单个妖怪商品信息",
            parameters={
               @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
               @Param(name="name" , description="名称",dataType = DataType.STRING,required = true),
               @Param(name="address" , description="地址",dataType = DataType.STRING,required = false),
               @Param(name="img" , description="图片0",dataType = DataType.STRING,required = true),
               @Param(name="remark" , description="备注",dataType = DataType.STRING,required = false),
               @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = true),
               @Param(name="price" , description="价格",dataType = DataType.FLOAT,required = false),
               @Param(name="creator" , description="创建人id",dataType = DataType.LONG,required = false),
               @Param(name="creatorName" , description="创建人姓名",dataType = DataType.STRING,required = false),
               @Param(name="platform" , description="平台名称",dataType = DataType.STRING,required = false),
               @Param(name="up" , description="顶",dataType = DataType.INTEGER,required = false),
               @Param(name="wg" , description="物攻",dataType = DataType.INTEGER,required = false),
               @Param(name="wf" , description="物防",dataType = DataType.INTEGER,required = false),
               @Param(name="fg" , description="法攻",dataType = DataType.INTEGER,required = false),
               @Param(name="ff" , description="法防",dataType = DataType.INTEGER,required = false),
               @Param(name="sm" , description="生命",dataType = DataType.INTEGER,required = false),
               @Param(name="zf" , description="总分",dataType = DataType.INTEGER,required = false),
               @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
               @Param(name="updateTime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
            })
        @RequestMapping(value = "add.json",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            Yaoguai yaoguai =new  Yaoguai();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                yaoguai.setId(Long.valueOf(id)) ;
            }
            
            String name = request.getParameter("name");
            if(!StringUtil.isBlank(name)){
                yaoguai.setName(String.valueOf(name)) ;
            }
            
            String address = request.getParameter("address");
            if(!StringUtil.isBlank(address)){
                yaoguai.setAddress(String.valueOf(address)) ;
            }
            
            String img = request.getParameter("img");
            if(!StringUtil.isBlank(img)){
                yaoguai.setImg(String.valueOf(img)) ;
            }
            
            String remark = request.getParameter("remark");
            if(!StringUtil.isBlank(remark)){
                yaoguai.setRemark(String.valueOf(remark)) ;
            }
            
            String status = request.getParameter("status");
            if(!StringUtil.isBlank(status)){
                yaoguai.setStatus(Integer.valueOf(status)) ;
            }
            
            String price = request.getParameter("price");
            if(!StringUtil.isBlank(price)){
                yaoguai.setPrice(BigDecimal.valueOf(price)) ;
            }
            
            String creator = request.getParameter("creator");
            if(!StringUtil.isBlank(creator)){
                yaoguai.setCreator(Long.valueOf(creator)) ;
            }
            
            String creatorName = request.getParameter("creatorName");
            if(!StringUtil.isBlank(creatorName)){
                yaoguai.setCreatorName(String.valueOf(creatorName)) ;
            }
            
            String platform = request.getParameter("platform");
            if(!StringUtil.isBlank(platform)){
                yaoguai.setPlatform(String.valueOf(platform)) ;
            }
            
            String up = request.getParameter("up");
            if(!StringUtil.isBlank(up)){
                yaoguai.setUp(Integer.valueOf(up)) ;
            }
            
            String wg = request.getParameter("wg");
            if(!StringUtil.isBlank(wg)){
                yaoguai.setWg(Integer.valueOf(wg)) ;
            }
            
            String wf = request.getParameter("wf");
            if(!StringUtil.isBlank(wf)){
                yaoguai.setWf(Integer.valueOf(wf)) ;
            }
            
            String fg = request.getParameter("fg");
            if(!StringUtil.isBlank(fg)){
                yaoguai.setFg(Integer.valueOf(fg)) ;
            }
            
            String ff = request.getParameter("ff");
            if(!StringUtil.isBlank(ff)){
                yaoguai.setFf(Integer.valueOf(ff)) ;
            }
            
            String sm = request.getParameter("sm");
            if(!StringUtil.isBlank(sm)){
                yaoguai.setSm(Integer.valueOf(sm)) ;
            }
            
            String zf = request.getParameter("zf");
            if(!StringUtil.isBlank(zf)){
                yaoguai.setZf(Integer.valueOf(zf)) ;
            }
            
            String createTime = request.getParameter("createTime");
            if(!StringUtil.isBlank(createTime)){
                yaoguai.setCreateTime(Timestamp.valueOf(createTime)) ;
            }
            
            String updateTime = request.getParameter("updateTime");
            if(!StringUtil.isBlank(updateTime)){
                yaoguai.setUpdateTime(Timestamp.valueOf(updateTime)) ;
            }
            */
            String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            yaoguai.setId(Long.valueOf(id));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            yaoguai.setName(name);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            yaoguai.setAddress(address);
        }
        String img = request.getParameter("img");
        if(!StringUtil.isBlank(img)){
            yaoguai.setImg(img);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            yaoguai.setRemark(remark);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            yaoguai.setStatus(Integer.valueOf(status));
        }
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            yaoguai.setPrice(new BigDecimal(price));
        }
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            yaoguai.setCreator(Long.valueOf(creator));
        }
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            yaoguai.setCreatorName(creatorName);
        }
        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            yaoguai.setPlatform(platform);
        }
        String up = request.getParameter("up");
        if(!StringUtil.isBlank(up)){
            yaoguai.setUp(Integer.valueOf(up));
        }
        String wg = request.getParameter("wg");
        if(!StringUtil.isBlank(wg)){
            yaoguai.setWg(Integer.valueOf(wg));
        }
        String wf = request.getParameter("wf");
        if(!StringUtil.isBlank(wf)){
            yaoguai.setWf(Integer.valueOf(wf));
        }
        String fg = request.getParameter("fg");
        if(!StringUtil.isBlank(fg)){
            yaoguai.setFg(Integer.valueOf(fg));
        }
        String ff = request.getParameter("ff");
        if(!StringUtil.isBlank(ff)){
            yaoguai.setFf(Integer.valueOf(ff));
        }
        String sm = request.getParameter("sm");
        if(!StringUtil.isBlank(sm)){
            yaoguai.setSm(Integer.valueOf(sm));
        }
        String zf = request.getParameter("zf");
        if(!StringUtil.isBlank(zf)){
            yaoguai.setZf(Integer.valueOf(zf));
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                yaoguai.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                yaoguai.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                yaoguai.setUpdateTime(Timestamp.valueOf(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                yaoguai.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(9,0)});
        vu.add("name", name, "名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("address", address, "地址",  new Rule[]{new Length(200)});
        vu.add("img", img, "图片0",  new Rule[]{new Length(100),new NotEmpty()});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2","3","9"}),new NotEmpty()});
        vu.add("price", price, "价格",  new Rule[]{});
        vu.add("creator", creator, "创建人id",  new Rule[]{new Digits(11,0)});
        vu.add("creatorName", creatorName, "创建人姓名",  new Rule[]{new Length(50)});
        vu.add("platform", platform, "平台名称",  new Rule[]{new Length(50)});
        vu.add("up", up, "顶",  new Rule[]{new Digits(11,0)});
        vu.add("wg", wg, "物攻",  new Rule[]{new Digits(2,0)});
        vu.add("wf", wf, "物防",  new Rule[]{new Digits(2,0)});
        vu.add("fg", fg, "法攻",  new Rule[]{new Digits(2,0)});
        vu.add("ff", ff, "法防",  new Rule[]{new Digits(2,0)});
        vu.add("sm", sm, "生命",  new Rule[]{new Digits(2,0)});
        vu.add("zf", zf, "总分",  new Rule[]{new Digits(3,0)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

            return yaoguaiService.save(yaoguai);

        }


          /**
                 * 说明:添加Yaoguai信息
                 * @param request
                 * @throws Exception
                 * @return ResultDTO
                 * @author dozen.zhang
                 * @date 2019-5-26 10:44:45
                 */
                // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                @API( summary="添加单个妖怪商品信息",
                    description = "添加单个妖怪商品信息",
                    parameters={
                       @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
                       @Param(name="name" , description="名称",dataType = DataType.STRING,required = true),
                       @Param(name="address" , description="地址",dataType = DataType.STRING,required = false),
                       @Param(name="img" , description="图片0",dataType = DataType.STRING,required = true),
                       @Param(name="remark" , description="备注",dataType = DataType.STRING,required = false),
                       @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = true),
                       @Param(name="price" , description="价格",dataType = DataType.FLOAT,required = false),
                       @Param(name="creator" , description="创建人id",dataType = DataType.LONG,required = false),
                       @Param(name="creatorName" , description="创建人姓名",dataType = DataType.STRING,required = false),
                       @Param(name="platform" , description="平台名称",dataType = DataType.STRING,required = false),
                       @Param(name="up" , description="顶",dataType = DataType.INTEGER,required = false),
                       @Param(name="wg" , description="物攻",dataType = DataType.INTEGER,required = false),
                       @Param(name="wf" , description="物防",dataType = DataType.INTEGER,required = false),
                       @Param(name="fg" , description="法攻",dataType = DataType.INTEGER,required = false),
                       @Param(name="ff" , description="法防",dataType = DataType.INTEGER,required = false),
                       @Param(name="sm" , description="生命",dataType = DataType.INTEGER,required = false),
                       @Param(name="zf" , description="总分",dataType = DataType.INTEGER,required = false),
                       @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
                       @Param(name="updateTime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
                    })
                @RequestMapping(value = "save.json",method = RequestMethod.POST)
                @ResponseBody
                public ResultDTO save(HttpServletRequest request) throws Exception {
                    Yaoguai yaoguai =new  Yaoguai();
                    /*
                    String id = request.getParameter("id");
                    if(!StringUtil.isBlank(id)){
                        yaoguai.setId(Long.valueOf(id)) ;
                    }
                    
                    String name = request.getParameter("name");
                    if(!StringUtil.isBlank(name)){
                        yaoguai.setName(String.valueOf(name)) ;
                    }
                    
                    String address = request.getParameter("address");
                    if(!StringUtil.isBlank(address)){
                        yaoguai.setAddress(String.valueOf(address)) ;
                    }
                    
                    String img = request.getParameter("img");
                    if(!StringUtil.isBlank(img)){
                        yaoguai.setImg(String.valueOf(img)) ;
                    }
                    
                    String remark = request.getParameter("remark");
                    if(!StringUtil.isBlank(remark)){
                        yaoguai.setRemark(String.valueOf(remark)) ;
                    }
                    
                    String status = request.getParameter("status");
                    if(!StringUtil.isBlank(status)){
                        yaoguai.setStatus(Integer.valueOf(status)) ;
                    }
                    
                    String price = request.getParameter("price");
                    if(!StringUtil.isBlank(price)){
                        yaoguai.setPrice(BigDecimal.valueOf(price)) ;
                    }
                    
                    String creator = request.getParameter("creator");
                    if(!StringUtil.isBlank(creator)){
                        yaoguai.setCreator(Long.valueOf(creator)) ;
                    }
                    
                    String creatorName = request.getParameter("creatorName");
                    if(!StringUtil.isBlank(creatorName)){
                        yaoguai.setCreatorName(String.valueOf(creatorName)) ;
                    }
                    
                    String platform = request.getParameter("platform");
                    if(!StringUtil.isBlank(platform)){
                        yaoguai.setPlatform(String.valueOf(platform)) ;
                    }
                    
                    String up = request.getParameter("up");
                    if(!StringUtil.isBlank(up)){
                        yaoguai.setUp(Integer.valueOf(up)) ;
                    }
                    
                    String wg = request.getParameter("wg");
                    if(!StringUtil.isBlank(wg)){
                        yaoguai.setWg(Integer.valueOf(wg)) ;
                    }
                    
                    String wf = request.getParameter("wf");
                    if(!StringUtil.isBlank(wf)){
                        yaoguai.setWf(Integer.valueOf(wf)) ;
                    }
                    
                    String fg = request.getParameter("fg");
                    if(!StringUtil.isBlank(fg)){
                        yaoguai.setFg(Integer.valueOf(fg)) ;
                    }
                    
                    String ff = request.getParameter("ff");
                    if(!StringUtil.isBlank(ff)){
                        yaoguai.setFf(Integer.valueOf(ff)) ;
                    }
                    
                    String sm = request.getParameter("sm");
                    if(!StringUtil.isBlank(sm)){
                        yaoguai.setSm(Integer.valueOf(sm)) ;
                    }
                    
                    String zf = request.getParameter("zf");
                    if(!StringUtil.isBlank(zf)){
                        yaoguai.setZf(Integer.valueOf(zf)) ;
                    }
                    
                    String createTime = request.getParameter("createTime");
                    if(!StringUtil.isBlank(createTime)){
                        yaoguai.setCreateTime(Timestamp.valueOf(createTime)) ;
                    }
                    
                    String updateTime = request.getParameter("updateTime");
                    if(!StringUtil.isBlank(updateTime)){
                        yaoguai.setUpdateTime(Timestamp.valueOf(updateTime)) ;
                    }
                    */
                    String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            yaoguai.setId(Long.valueOf(id));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            yaoguai.setName(name);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            yaoguai.setAddress(address);
        }
        String img = request.getParameter("img");
        if(!StringUtil.isBlank(img)){
            yaoguai.setImg(img);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            yaoguai.setRemark(remark);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            yaoguai.setStatus(Integer.valueOf(status));
        }
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            yaoguai.setPrice(new BigDecimal(price));
        }
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            yaoguai.setCreator(Long.valueOf(creator));
        }
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            yaoguai.setCreatorName(creatorName);
        }
        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            yaoguai.setPlatform(platform);
        }
        String up = request.getParameter("up");
        if(!StringUtil.isBlank(up)){
            yaoguai.setUp(Integer.valueOf(up));
        }
        String wg = request.getParameter("wg");
        if(!StringUtil.isBlank(wg)){
            yaoguai.setWg(Integer.valueOf(wg));
        }
        String wf = request.getParameter("wf");
        if(!StringUtil.isBlank(wf)){
            yaoguai.setWf(Integer.valueOf(wf));
        }
        String fg = request.getParameter("fg");
        if(!StringUtil.isBlank(fg)){
            yaoguai.setFg(Integer.valueOf(fg));
        }
        String ff = request.getParameter("ff");
        if(!StringUtil.isBlank(ff)){
            yaoguai.setFf(Integer.valueOf(ff));
        }
        String sm = request.getParameter("sm");
        if(!StringUtil.isBlank(sm)){
            yaoguai.setSm(Integer.valueOf(sm));
        }
        String zf = request.getParameter("zf");
        if(!StringUtil.isBlank(zf)){
            yaoguai.setZf(Integer.valueOf(zf));
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                yaoguai.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                yaoguai.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                yaoguai.setUpdateTime(Timestamp.valueOf(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                yaoguai.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

                    //valid
                    ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(9,0)});
        vu.add("name", name, "名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("address", address, "地址",  new Rule[]{new Length(200)});
        vu.add("img", img, "图片0",  new Rule[]{new Length(100),new NotEmpty()});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2","3","9"}),new NotEmpty()});
        vu.add("price", price, "价格",  new Rule[]{});
        vu.add("creator", creator, "创建人id",  new Rule[]{new Digits(11,0)});
        vu.add("creatorName", creatorName, "创建人姓名",  new Rule[]{new Length(50)});
        vu.add("platform", platform, "平台名称",  new Rule[]{new Length(50)});
        vu.add("up", up, "顶",  new Rule[]{new Digits(11,0)});
        vu.add("wg", wg, "物攻",  new Rule[]{new Digits(2,0)});
        vu.add("wf", wf, "物防",  new Rule[]{new Digits(2,0)});
        vu.add("fg", fg, "法攻",  new Rule[]{new Digits(2,0)});
        vu.add("ff", ff, "法防",  new Rule[]{new Digits(2,0)});
        vu.add("sm", sm, "生命",  new Rule[]{new Digits(2,0)});
        vu.add("zf", zf, "总分",  new Rule[]{new Digits(3,0)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

                    return yaoguaiService.save(yaoguai);

                }

    /**
     * 说明:删除Yaoguai信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2019-5-26 10:44:45
     */
     @API( summary="根据id删除单个妖怪商品信息",
        description = "根据id删除单个妖怪商品信息",
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
        yaoguaiService.delete(id);
        return this.getResult(SUCC);
    }

     /**
         * 说明:删除Yaoguai信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-5-26 10:44:45
         */
         @API( summary="根据id删除单个妖怪商品信息",
            description = "根据id删除单个妖怪商品信息",
            parameters={
             @Param(name="id" , description="编号",in=InType.path,dataType= DataType.LONG,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable Long id,HttpServletRequest request) {
            yaoguaiService.delete(id);
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
       return  yaoguaiService.multilDelete(idAry);
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
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = request.getParameter("addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
        }
        String img = request.getParameter("img");
        if(!StringUtil.isBlank(img)){
            params.put("img",img);
        }
        String imgLike = request.getParameter("imgLike");
        if(!StringUtil.isBlank(imgLike)){
            params.put("imgLike",imgLike);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            params.put("price",price);
        }
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            params.put("creator",creator);
        }
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            params.put("creatorName",creatorName);
        }
        String creatorNameLike = request.getParameter("creatorNameLike");
        if(!StringUtil.isBlank(creatorNameLike)){
            params.put("creatorNameLike",creatorNameLike);
        }
        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            params.put("platform",platform);
        }
        String platformLike = request.getParameter("platformLike");
        if(!StringUtil.isBlank(platformLike)){
            params.put("platformLike",platformLike);
        }
        String up = request.getParameter("up");
        if(!StringUtil.isBlank(up)){
            params.put("up",up);
        }
        String wg = request.getParameter("wg");
        if(!StringUtil.isBlank(wg)){
            params.put("wg",wg);
        }
        String wf = request.getParameter("wf");
        if(!StringUtil.isBlank(wf)){
            params.put("wf",wf);
        }
        String fg = request.getParameter("fg");
        if(!StringUtil.isBlank(fg)){
            params.put("fg",fg);
        }
        String ff = request.getParameter("ff");
        if(!StringUtil.isBlank(ff)){
            params.put("ff",ff);
        }
        String sm = request.getParameter("sm");
        if(!StringUtil.isBlank(sm)){
            params.put("sm",sm);
        }
        String zf = request.getParameter("zf");
        if(!StringUtil.isBlank(zf)){
            params.put("zf",zf);
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
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                params.put("updateTime",updateTime);
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTime",new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeBegin = request.getParameter("updateTimeBegin");
        if(!StringUtil.isBlank(updateTimeBegin)){
            if(StringUtil.checkNumeric(updateTimeBegin)){
                params.put("updateTimeBegin",updateTimeBegin);
            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeBegin",new Timestamp( DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeEnd = request.getParameter("updateTimeEnd");
        if(!StringUtil.isBlank(updateTimeEnd)){
            if(StringUtil.checkNumeric(updateTimeEnd)){
                params.put("updateTimeEnd",updateTimeEnd);
            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeEnd",new Timestamp( DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        // 查询list集合
        List<Yaoguai> list =yaoguaiService.listByParams(params);
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
        colTitle.put("name", "名称");
        colTitle.put("address", "地址");
        colTitle.put("img", "图片0");
        colTitle.put("remark", "备注");
        colTitle.put("status", "状态");
        colTitle.put("price", "价格");
        colTitle.put("creator", "创建人id");
        colTitle.put("creatorName", "创建人姓名");
        colTitle.put("platform", "平台名称");
        colTitle.put("up", "顶");
        colTitle.put("wg", "物攻");
        colTitle.put("wf", "物防");
        colTitle.put("fg", "法攻");
        colTitle.put("ff", "法防");
        colTitle.put("sm", "生命");
        colTitle.put("zf", "总分");
        colTitle.put("createTime", "创建时间");
        colTitle.put("updateTime", "更新时间");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            Yaoguai yaoguai = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("name",  list.get(i).getName());
            map.put("address",  list.get(i).getAddress());
            map.put("img",  list.get(i).getImg());
            map.put("remark",  list.get(i).getRemark());
            map.put("status",  list.get(i).getStatus());
            map.put("price",  list.get(i).getPrice());
            map.put("creator",  list.get(i).getCreator());
            map.put("creatorName",  list.get(i).getCreatorName());
            map.put("platform",  list.get(i).getPlatform());
            map.put("up",  list.get(i).getUp());
            map.put("wg",  list.get(i).getWg());
            map.put("wf",  list.get(i).getWf());
            map.put("fg",  list.get(i).getFg());
            map.put("ff",  list.get(i).getFf());
            map.put("sm",  list.get(i).getSm());
            map.put("zf",  list.get(i).getZf());
            map.put("createTime",  list.get(i).getCreateTime());
            map.put("updateTime",  list.get(i).getUpdateTime());
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

                        Yaoguai bean = getInfoFromMap(params);

                      //  if (count > 0) {

                      //      logger.warn("邮箱已经存在:" + email);
                       //     errorMsg.append("邮箱已经存在:" + email);
                         //   continue;

                       // }

                        try {
                            yaoguaiService.save(bean);
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
         * 说明: 跳转到Yaoguai列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/YaoguaiList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/YaoguaiListMapper.html";
        }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2019-5-26 10:44:45
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/YaoguaiEdit.html";
    }
    /**
         * 说明:跳转查看页面
         * @param request 发请求
         * @return String
         * @author dozen.zhang
         * @date 2019-5-26 10:44:45
         */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/YaoguaiView.html";
    }



    private Yaoguai getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       Yaoguai yaoguai =new  Yaoguai();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                yaoguai.setId(Long.valueOf(id));
        }
        String name = MapUtils.getString(bodyParam,"name");
        if(!StringUtil.isBlank(name)){
                yaoguai.setName(String.valueOf(name));
        }
        String address = MapUtils.getString(bodyParam,"address");
        if(!StringUtil.isBlank(address)){
                yaoguai.setAddress(String.valueOf(address));
        }
        String img = MapUtils.getString(bodyParam,"img");
        if(!StringUtil.isBlank(img)){
                yaoguai.setImg(String.valueOf(img));
        }
        String remark = MapUtils.getString(bodyParam,"remark");
        if(!StringUtil.isBlank(remark)){
                yaoguai.setRemark(String.valueOf(remark));
        }
        String status = MapUtils.getString(bodyParam,"status");
        if(!StringUtil.isBlank(status)){
                yaoguai.setStatus(Integer.valueOf(status));
        }
        String price = MapUtils.getString(bodyParam,"price");
        if(!StringUtil.isBlank(price)){
                yaoguai.setPrice(new BigDecimal(price));
        }
        String creator = MapUtils.getString(bodyParam,"creator");
        if(!StringUtil.isBlank(creator)){
                yaoguai.setCreator(Long.valueOf(creator));
        }
        String creatorName = MapUtils.getString(bodyParam,"creatorName");
        if(!StringUtil.isBlank(creatorName)){
                yaoguai.setCreatorName(String.valueOf(creatorName));
        }
        String platform = MapUtils.getString(bodyParam,"platform");
        if(!StringUtil.isBlank(platform)){
                yaoguai.setPlatform(String.valueOf(platform));
        }
        String up = MapUtils.getString(bodyParam,"up");
        if(!StringUtil.isBlank(up)){
                yaoguai.setUp(Integer.valueOf(up));
        }
        String wg = MapUtils.getString(bodyParam,"wg");
        if(!StringUtil.isBlank(wg)){
                yaoguai.setWg(Integer.valueOf(wg));
        }
        String wf = MapUtils.getString(bodyParam,"wf");
        if(!StringUtil.isBlank(wf)){
                yaoguai.setWf(Integer.valueOf(wf));
        }
        String fg = MapUtils.getString(bodyParam,"fg");
        if(!StringUtil.isBlank(fg)){
                yaoguai.setFg(Integer.valueOf(fg));
        }
        String ff = MapUtils.getString(bodyParam,"ff");
        if(!StringUtil.isBlank(ff)){
                yaoguai.setFf(Integer.valueOf(ff));
        }
        String sm = MapUtils.getString(bodyParam,"sm");
        if(!StringUtil.isBlank(sm)){
                yaoguai.setSm(Integer.valueOf(sm));
        }
        String zf = MapUtils.getString(bodyParam,"zf");
        if(!StringUtil.isBlank(zf)){
                yaoguai.setZf(Integer.valueOf(zf));
        }
        String createTime = MapUtils.getString(bodyParam,"createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                yaoguai.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                yaoguai.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = MapUtils.getString(bodyParam,"updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                yaoguai.setUpdateTime(Timestamp.valueOf(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                yaoguai.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(9,0)});
        vu.add("name", name, "名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("address", address, "地址",  new Rule[]{new Length(200)});
        vu.add("img", img, "图片0",  new Rule[]{new Length(100),new NotEmpty()});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2","3","9"}),new NotEmpty()});
        vu.add("price", price, "价格",  new Rule[]{});
        vu.add("creator", creator, "创建人id",  new Rule[]{new Digits(11,0)});
        vu.add("creatorName", creatorName, "创建人姓名",  new Rule[]{new Length(50)});
        vu.add("platform", platform, "平台名称",  new Rule[]{new Length(50)});
        vu.add("up", up, "顶",  new Rule[]{new Digits(11,0)});
        vu.add("wg", wg, "物攻",  new Rule[]{new Digits(2,0)});
        vu.add("wf", wf, "物防",  new Rule[]{new Digits(2,0)});
        vu.add("fg", fg, "法攻",  new Rule[]{new Digits(2,0)});
        vu.add("ff", ff, "法防",  new Rule[]{new Digits(2,0)});
        vu.add("sm", sm, "生命",  new Rule[]{new Digits(2,0)});
        vu.add("zf", zf, "总分",  new Rule[]{new Digits(3,0)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  yaoguai;
    }


      /**
                     * 说明:添加Yaoguai信息
                     * @param request
                     * @throws Exception
                     * @return ResultDTO
                     * @author dozen.zhang
                     * @date 2019-5-26 10:44:45
                     */
                    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                    @API( summary="添加单个妖怪商品信息",
                        description = "添加单个妖怪商品信息",
                        parameters={
                           @Param(name="id" , description="编号"  ,in=InType.body,dataType = DataType.LONG,required = false),
                           @Param(name="name" , description="名称"  ,in=InType.body,dataType = DataType.STRING,required = true),
                           @Param(name="address" , description="地址"  ,in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="img" , description="图片0"  ,in=InType.body,dataType = DataType.STRING,required = true),
                           @Param(name="remark" , description="备注"  ,in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="status" , description="状态"  ,in=InType.body,dataType = DataType.INTEGER,required = true),
                           @Param(name="price" , description="价格"  ,in=InType.body,dataType = DataType.FLOAT,required = false),
                           @Param(name="creator" , description="创建人id"  ,in=InType.body,dataType = DataType.LONG,required = false),
                           @Param(name="creatorName" , description="创建人姓名"  ,in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="platform" , description="平台名称qq|wx"  ,in=InType.body,dataType = DataType.STRING,required = false),
                           @Param(name="up" , description="顶"  ,in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="wg" , description="物攻"  ,in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="wf" , description="物防"  ,in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="fg" , description="法攻"  ,in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="ff" , description="法防"  ,in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="sm" , description="生命"  ,in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="zf" , description="总分"  ,in=InType.body,dataType = DataType.INTEGER,required = false),
                           @Param(name="createTime" , description="创建时间"  ,in=InType.body,dataType = DataType.DATE_TIME,required = false),
                           @Param(name="updateTime" , description="更新时间"  ,in=InType.body,dataType = DataType.DATE_TIME,required = false),
                        })
                    @RequestMapping(value = "add",method = RequestMethod.POST)
                    @ResponseBody
                    public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
                        Yaoguai yaoguai =    getInfoFromMap(bodyParam);


                        return yaoguaiService.save(yaoguai);

                    }


    /**
    * 说明:添加Yaoguai信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2019-5-26 10:44:45
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个妖怪商品信息",
    description = "更新单个妖怪商品信息",
    parameters={
        @Param(name="id" , description="编号  ",in=InType.body,dataType = DataType.LONG,required = false),
        @Param(name="name" , description="名称  ",in=InType.body,dataType = DataType.STRING,required = true),
        @Param(name="address" , description="地址  ",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="img" , description="图片0  ",in=InType.body,dataType = DataType.STRING,required = true),
        @Param(name="remark" , description="备注  ",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="status" , description="状态  ",in=InType.body,dataType = DataType.INTEGER,required = true),
        @Param(name="price" , description="价格  ",in=InType.body,dataType = DataType.FLOAT,required = false),
        @Param(name="creator" , description="创建人id  ",in=InType.body,dataType = DataType.LONG,required = false),
        @Param(name="creatorName" , description="创建人姓名  ",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="platform" , description="平台名称  qq|wx",in=InType.body,dataType = DataType.STRING,required = false),
        @Param(name="up" , description="顶  ",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="wg" , description="物攻  ",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="wf" , description="物防  ",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="fg" , description="法攻  ",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="ff" , description="法防  ",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="sm" , description="生命  ",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="zf" , description="总分  ",in=InType.body,dataType = DataType.INTEGER,required = false),
        @Param(name="createTime" , description="创建时间  ",in=InType.body,dataType = DataType.DATE_TIME,required = false),
        @Param(name="updateTime" , description="更新时间  ",in=InType.body,dataType = DataType.DATE_TIME,required = false),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    Yaoguai yaoguai =    getInfoFromMap(bodyParam);
    return yaoguaiService.save(yaoguai);

    }
/**
     * 说明:ajax请求Yaoguai信息
     * @author dozen.zhang
     * @date 2019-5-26 10:44:45
     * @return String
     */
       @API(summary="妖怪商品列表接口",
                 description="妖怪商品列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号  ",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="name" , description="名称  ",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="address" , description="地址  ",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="img" , description="图片0  ",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="remark" , description="备注  ",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="status" , description="状态  ",in=InType.params,dataType = DataType.INTEGER,required =false),// true
                    @Param(name="price" , description="价格  ",in=InType.params,dataType = DataType.FLOAT,required =false),// false
                    @Param(name="creator" , description="创建人id  ",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="creatorName" , description="创建人姓名  ",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="platform" , description="平台名称  qq|wx",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="up" , description="顶  ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="wg" , description="物攻  ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="wf" , description="物防  ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="fg" , description="法攻  ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="ff" , description="法防  ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="sm" , description="生命  ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="zf" , description="总分  ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="createTime" , description="创建时间  ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="updateTime" , description="更新时间  ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
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
        String name = MapUtils.getString(params,"name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = MapUtils.getString(params,"nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String address = MapUtils.getString(params,"address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = MapUtils.getString(params,"addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
        }
        String img = MapUtils.getString(params,"img");
        if(!StringUtil.isBlank(img)){
            params.put("img",img);
        }
        String imgLike = MapUtils.getString(params,"imgLike");
        if(!StringUtil.isBlank(imgLike)){
            params.put("imgLike",imgLike);
        }
        String remark = MapUtils.getString(params,"remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = MapUtils.getString(params,"remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String status = MapUtils.getString(params,"status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String price = MapUtils.getString(params,"price");
        if(!StringUtil.isBlank(price)){
            params.put("price",price);
        }
        String creator = MapUtils.getString(params,"creator");
        if(!StringUtil.isBlank(creator)){
            params.put("creator",creator);
        }
        String creatorName = MapUtils.getString(params,"creatorName");
        if(!StringUtil.isBlank(creatorName)){
            params.put("creatorName",creatorName);
        }
        String creatorNameLike = MapUtils.getString(params,"creatorNameLike");
        if(!StringUtil.isBlank(creatorNameLike)){
            params.put("creatorNameLike",creatorNameLike);
        }
        String platform = MapUtils.getString(params,"platform");
        if(!StringUtil.isBlank(platform)){
            params.put("platform",platform);
        }
        String platformLike = MapUtils.getString(params,"platformLike");
        if(!StringUtil.isBlank(platformLike)){
            params.put("platformLike",platformLike);
        }
        String up = MapUtils.getString(params,"up");
        if(!StringUtil.isBlank(up)){
            params.put("up",up);
        }
        String wg = MapUtils.getString(params,"wg");
        if(!StringUtil.isBlank(wg)){
            params.put("wg",wg);
        }
        String wf = MapUtils.getString(params,"wf");
        if(!StringUtil.isBlank(wf)){
            params.put("wf",wf);
        }
        String fg = MapUtils.getString(params,"fg");
        if(!StringUtil.isBlank(fg)){
            params.put("fg",fg);
        }
        String ff = MapUtils.getString(params,"ff");
        if(!StringUtil.isBlank(ff)){
            params.put("ff",ff);
        }
        String sm = MapUtils.getString(params,"sm");
        if(!StringUtil.isBlank(sm)){
            params.put("sm",sm);
        }
        String zf = MapUtils.getString(params,"zf");
        if(!StringUtil.isBlank(zf)){
            params.put("zf",zf);
        }
        String createTime = MapUtils.getString(params,"createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = MapUtils.getString(params,"createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = MapUtils.getString(params,"createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = MapUtils.getString(params,"updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                params.put("updateTime",updateTime);
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTime",new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeBegin = MapUtils.getString(params,"updateTimeBegin");
        if(!StringUtil.isBlank(updateTimeBegin)){
            if(StringUtil.checkNumeric(updateTimeBegin)){
                params.put("updateTimeBegin",updateTimeBegin);
            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeBegin",new Timestamp( DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeEnd = MapUtils.getString(params,"updateTimeEnd");
        if(!StringUtil.isBlank(updateTimeEnd)){
            if(StringUtil.checkNumeric(updateTimeEnd)){
                params.put("updateTimeEnd",updateTimeEnd);
            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeEnd",new Timestamp( DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page",page);
        List<Yaoguai> yaoguais = yaoguaiService.listByParams4Page(params);
        return ResultUtil.getResult(yaoguais, page);
    }



       /**
         * 导出
         * @param request
         * @return
         * @author dozen.zhang
         */
        @API(summary="妖怪商品列表导出接口",
          description="妖怪商品列表导出接口",
          parameters={
          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
             @Param(name="id" , description="编号 ",in=InType.params,dataType = DataType.LONG,required =false),// false
             @Param(name="name" , description="名称 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="address" , description="地址 ",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="img" , description="图片0 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="remark" , description="备注 ",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="status" , description="状态 ",in=InType.params,dataType = DataType.INTEGER,required =false),// true
             @Param(name="price" , description="价格 ",in=InType.params,dataType = DataType.FLOAT,required =false),// false
             @Param(name="creator" , description="创建人id ",in=InType.params,dataType = DataType.LONG,required =false),// false
             @Param(name="creatorName" , description="创建人姓名 ",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="platform" , description="平台名称 qq|wx",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="up" , description="顶 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="wg" , description="物攻 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="wf" , description="物防 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="fg" , description="法攻 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="ff" , description="法防 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="sm" , description="生命 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="zf" , description="总分 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="createTime" , description="创建时间 ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
             @Param(name="updateTime" , description="更新时间 ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
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
        String name = MapUtils.getString(params,"name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = MapUtils.getString(params,"nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String address = MapUtils.getString(params,"address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = MapUtils.getString(params,"addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
        }
        String img = MapUtils.getString(params,"img");
        if(!StringUtil.isBlank(img)){
            params.put("img",img);
        }
        String imgLike = MapUtils.getString(params,"imgLike");
        if(!StringUtil.isBlank(imgLike)){
            params.put("imgLike",imgLike);
        }
        String remark = MapUtils.getString(params,"remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = MapUtils.getString(params,"remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String status = MapUtils.getString(params,"status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String price = MapUtils.getString(params,"price");
        if(!StringUtil.isBlank(price)){
            params.put("price",price);
        }
        String creator = MapUtils.getString(params,"creator");
        if(!StringUtil.isBlank(creator)){
            params.put("creator",creator);
        }
        String creatorName = MapUtils.getString(params,"creatorName");
        if(!StringUtil.isBlank(creatorName)){
            params.put("creatorName",creatorName);
        }
        String creatorNameLike = MapUtils.getString(params,"creatorNameLike");
        if(!StringUtil.isBlank(creatorNameLike)){
            params.put("creatorNameLike",creatorNameLike);
        }
        String platform = MapUtils.getString(params,"platform");
        if(!StringUtil.isBlank(platform)){
            params.put("platform",platform);
        }
        String platformLike = MapUtils.getString(params,"platformLike");
        if(!StringUtil.isBlank(platformLike)){
            params.put("platformLike",platformLike);
        }
        String up = MapUtils.getString(params,"up");
        if(!StringUtil.isBlank(up)){
            params.put("up",up);
        }
        String wg = MapUtils.getString(params,"wg");
        if(!StringUtil.isBlank(wg)){
            params.put("wg",wg);
        }
        String wf = MapUtils.getString(params,"wf");
        if(!StringUtil.isBlank(wf)){
            params.put("wf",wf);
        }
        String fg = MapUtils.getString(params,"fg");
        if(!StringUtil.isBlank(fg)){
            params.put("fg",fg);
        }
        String ff = MapUtils.getString(params,"ff");
        if(!StringUtil.isBlank(ff)){
            params.put("ff",ff);
        }
        String sm = MapUtils.getString(params,"sm");
        if(!StringUtil.isBlank(sm)){
            params.put("sm",sm);
        }
        String zf = MapUtils.getString(params,"zf");
        if(!StringUtil.isBlank(zf)){
            params.put("zf",zf);
        }
        String createTime = MapUtils.getString(params,"createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = MapUtils.getString(params,"createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = MapUtils.getString(params,"createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = MapUtils.getString(params,"updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                params.put("updateTime",updateTime);
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTime",new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeBegin = MapUtils.getString(params,"updateTimeBegin");
        if(!StringUtil.isBlank(updateTimeBegin)){
            if(StringUtil.checkNumeric(updateTimeBegin)){
                params.put("updateTimeBegin",updateTimeBegin);
            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeBegin",new Timestamp( DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeEnd = MapUtils.getString(params,"updateTimeEnd");
        if(!StringUtil.isBlank(updateTimeEnd)){
            if(StringUtil.checkNumeric(updateTimeEnd)){
                params.put("updateTimeEnd",updateTimeEnd);
            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updateTimeEnd",new Timestamp( DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

             params.put("page",page);
             List<Yaoguai> list = yaoguaiService.listByParams4Page(params);
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
            colTitle.put("name", "名称");
            colTitle.put("address", "地址");
            colTitle.put("img", "图片0");
            colTitle.put("remark", "备注");
            colTitle.put("status", "状态");
            colTitle.put("price", "价格");
            colTitle.put("creator", "创建人id");
            colTitle.put("creatorName", "创建人姓名");
            colTitle.put("platform", "平台名称");
            colTitle.put("up", "顶");
            colTitle.put("wg", "物攻");
            colTitle.put("wf", "物防");
            colTitle.put("fg", "法攻");
            colTitle.put("ff", "法防");
            colTitle.put("sm", "生命");
            colTitle.put("zf", "总分");
            colTitle.put("createTime", "创建时间");
            colTitle.put("updateTime", "更新时间");
            List<Map> finalList = new ArrayList<Map>();
            for (int i = 0; i < list.size(); i++) {
                Yaoguai yaoguai = list.get(i);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id",  list.get(i).getId());
                map.put("name",  list.get(i).getName());
                map.put("address",  list.get(i).getAddress());
                map.put("img",  list.get(i).getImg());
                map.put("remark",  list.get(i).getRemark());
                map.put("status",  list.get(i).getStatus());
                map.put("price",  list.get(i).getPrice());
                map.put("creator",  list.get(i).getCreator());
                map.put("creatorName",  list.get(i).getCreatorName());
                map.put("platform",  list.get(i).getPlatform());
                map.put("up",  list.get(i).getUp());
                map.put("wg",  list.get(i).getWg());
                map.put("wf",  list.get(i).getWf());
                map.put("fg",  list.get(i).getFg());
                map.put("ff",  list.get(i).getFf());
                map.put("sm",  list.get(i).getSm());
                map.put("zf",  list.get(i).getZf());
                map.put("createTime",  list.get(i).getCreateTime());
                map.put("updateTime",  list.get(i).getUpdateTime());
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
