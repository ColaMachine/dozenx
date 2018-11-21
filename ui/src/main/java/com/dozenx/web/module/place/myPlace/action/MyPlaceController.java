/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-8-30 16:38:15
 * 文件说明: 
 */

package com.dozenx.web.module.place.myPlace.action;
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
import com.dozenx.web.module.place.myPlace.service.MyPlaceService;
import com.dozenx.web.module.place.myPlace.bean.MyPlace;
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
@APIs(description = "场地")
@Controller
@RequestMapping("/placesrv/place")
public class MyPlaceController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(MyPlaceController.class);
    /** 权限service **/
    @Autowired
    private MyPlaceService myPlaceService;
    


    /**
     * 说明:ajax请求MyPlace信息
     * @author dozen.zhang
     * @date 2018-8-30 16:38:15
     * @return String
     */
       @API(summary="场地列表接口",
                 description="场地列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="name" , description="名称",dataType = DataType.STRING,required =false),// false
                    @Param(name="code" , description="编号",dataType = DataType.STRING,required =false),// false
                    @Param(name="province" , description="省",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="city" , description="市",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="county" , description="区",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="price" , description="价格",dataType = DataType.FLOAT,required =false),// false
                    @Param(name="address" , description="详细地址",dataType = DataType.STRING,required =false),// false
                    @Param(name="cover" , description="封面",dataType = DataType.STRING,required =false),// false
                    @Param(name="pic" , description="图片",dataType = DataType.STRING,required =false),// false
                    @Param(name="createTime" , description="创建日期",dataType = DataType.DATE,required =false),// false
                    @Param(name="updateTime" , description="更新日期",dataType = DataType.DATE,required =false),// false
                    @Param(name="telno" , description="联系电话",dataType = DataType.STRING,required =false),// false
                    @Param(name="createUser" , description="创建人",dataType = DataType.LONG,required =false),// false
                    @Param(name="lng" , description="经度",dataType = DataType.FLOAT,required =false),// false
                    @Param(name="lat" , description="纬度",dataType = DataType.FLOAT,required =false),// false
                    @Param(name="comments" , description="评论",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="score" , description="评分",dataType = DataType.FLOAT,required =false),// false
                    @Param(name="lable" , description="标签",dataType = DataType.STRING,required =false),// false
                    @Param(name="remark" , description="备注",dataType = DataType.STRING,required =false),// false
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
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            params.put("code",code);
        }
        String codeLike = request.getParameter("codeLike");
        if(!StringUtil.isBlank(codeLike)){
            params.put("codeLike",codeLike);
        }
        String province = request.getParameter("province");
        if(!StringUtil.isBlank(province)){
            params.put("province",province);
        }
        String city = request.getParameter("city");
        if(!StringUtil.isBlank(city)){
            params.put("city",city);
        }
        String county = request.getParameter("county");
        if(!StringUtil.isBlank(county)){
            params.put("county",county);
        }
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            params.put("price",price);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = request.getParameter("addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
        }
        String cover = request.getParameter("cover");
        if(!StringUtil.isBlank(cover)){
            params.put("cover",cover);
        }
        String coverLike = request.getParameter("coverLike");
        if(!StringUtil.isBlank(coverLike)){
            params.put("coverLike",coverLike);
        }
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            params.put("pic",pic);
        }
        String picLike = request.getParameter("picLike");
        if(!StringUtil.isBlank(picLike)){
            params.put("picLike",picLike);
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
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                params.put("updateTime",updateTime);
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd")){
                params.put("updateTime",DateUtil.parseToDate(updateTime, "yyyy-MM-dd"));
            }
        }
        String updateTimeBegin = request.getParameter("updateTimeBegin");
        if(!StringUtil.isBlank(updateTimeBegin)){
            if(StringUtil.checkNumeric(updateTimeBegin)){
                params.put("updateTimeBegin",updateTimeBegin);
            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd")){
                params.put("updateTimeBegin",DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd"));
            }
        }
        String updateTimeEnd = request.getParameter("updateTimeEnd");
        if(!StringUtil.isBlank(updateTimeEnd)){
            if(StringUtil.checkNumeric(updateTimeEnd)){
                params.put("updateTimeEnd",updateTimeEnd);
            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd")){
                params.put("updateTimeEnd",DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd"));
            }
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            params.put("telno",telno);
        }
        String telnoLike = request.getParameter("telnoLike");
        if(!StringUtil.isBlank(telnoLike)){
            params.put("telnoLike",telnoLike);
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            params.put("createUser",createUser);
        }
        String lng = request.getParameter("lng");
        if(!StringUtil.isBlank(lng)){
            params.put("lng",lng);
        }
        String lat = request.getParameter("lat");
        if(!StringUtil.isBlank(lat)){
            params.put("lat",lat);
        }
        String comments = request.getParameter("comments");
        if(!StringUtil.isBlank(comments)){
            params.put("comments",comments);
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            params.put("score",score);
        }
        String lable = request.getParameter("lable");
        if(!StringUtil.isBlank(lable)){
            params.put("lable",lable);
        }
        String lableLike = request.getParameter("lableLike");
        if(!StringUtil.isBlank(lableLike)){
            params.put("lableLike",lableLike);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }

        params.put("page",page);
        List<MyPlace> myPlaces = myPlaceService.listByParams4Page(params);
        return ResultUtil.getResult(myPlaces, page);
    }
    
   /**
    * 说明:ajax请求MyPlace信息 无分页版本
    * @return ResultDTO 返回结果
    * @author dozen.zhang
    * @date 2018-8-30 16:38:15
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
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            params.put("code",code);
        }
        String codeLike = request.getParameter("codeLike");
        if(!StringUtil.isBlank(codeLike)){
            params.put("codeLike",codeLike);
        }
        String province = request.getParameter("province");
        if(!StringUtil.isBlank(province)){
            params.put("province",province);
        }
        String city = request.getParameter("city");
        if(!StringUtil.isBlank(city)){
            params.put("city",city);
        }
        String county = request.getParameter("county");
        if(!StringUtil.isBlank(county)){
            params.put("county",county);
        }
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            params.put("price",price);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = request.getParameter("addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
        }
        String cover = request.getParameter("cover");
        if(!StringUtil.isBlank(cover)){
            params.put("cover",cover);
        }
        String coverLike = request.getParameter("coverLike");
        if(!StringUtil.isBlank(coverLike)){
            params.put("coverLike",coverLike);
        }
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            params.put("pic",pic);
        }
        String picLike = request.getParameter("picLike");
        if(!StringUtil.isBlank(picLike)){
            params.put("picLike",picLike);
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
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                params.put("updateTime",updateTime);
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd")){
                params.put("updateTime",DateUtil.parseToDate(updateTime, "yyyy-MM-dd"));
            }
        }
        String updateTimeBegin = request.getParameter("updateTimeBegin");
        if(!StringUtil.isBlank(updateTimeBegin)){
            if(StringUtil.checkNumeric(updateTimeBegin)){
                params.put("updateTimeBegin",updateTimeBegin);
            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd")){
                params.put("updateTimeBegin",DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd"));
            }
        }
        String updateTimeEnd = request.getParameter("updateTimeEnd");
        if(!StringUtil.isBlank(updateTimeEnd)){
            if(StringUtil.checkNumeric(updateTimeEnd)){
                params.put("updateTimeEnd",updateTimeEnd);
            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd")){
                params.put("updateTimeEnd",DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd"));
            }
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            params.put("telno",telno);
        }
        String telnoLike = request.getParameter("telnoLike");
        if(!StringUtil.isBlank(telnoLike)){
            params.put("telnoLike",telnoLike);
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            params.put("createUser",createUser);
        }
        String lng = request.getParameter("lng");
        if(!StringUtil.isBlank(lng)){
            params.put("lng",lng);
        }
        String lat = request.getParameter("lat");
        if(!StringUtil.isBlank(lat)){
            params.put("lat",lat);
        }
        String comments = request.getParameter("comments");
        if(!StringUtil.isBlank(comments)){
            params.put("comments",comments);
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            params.put("score",score);
        }
        String lable = request.getParameter("lable");
        if(!StringUtil.isBlank(lable)){
            params.put("lable",lable);
        }
        String lableLike = request.getParameter("lableLike");
        if(!StringUtil.isBlank(lableLike)){
            params.put("lableLike",lableLike);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }

        List<MyPlace> myPlaces = myPlaceService.listByParams(params);
        return ResultUtil.getDataResult(myPlaces);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2018-8-30 16:38:15
    */
  @API( summary="根据id查询单个场地信息",
           description = "根据id查询单个场地信息",
           parameters={
                   @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
           })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(@PathVariable Integer id,HttpServletRequest request) {
            HashMap<String,Object> result =new HashMap</*String,Object*/>();
        if(id > 0){
            MyPlace bean = myPlaceService.selectByPrimaryKey(Integer.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        MyPlace bean = myPlaceService.selectByPrimaryKey(Integer.valueOf(id));
        HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }

     /**
        * 说明:查看单条信息
        * @param request 发请求
        * @return String
        * @author dozen.zhang
        * @date 2018-8-30 16:38:15
        */
      @API( summary="根据id查询单个场地信息",
               description = "根据id查询单个场地信息",
               parameters={
                       @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(HttpServletRequest request) {
//                HashMap<String,Object> result =new HashMap</*String,Object*/>();
//        if(id > 0){
//            MyPlace bean = myPlaceService.selectByPrimaryKey(Integer.valueOf(id));
//            result.put("bean", bean);
//        }
//        return this.getResult(result);



         String id = request.getParameter("id");
            MyPlace bean = myPlaceService.selectByPrimaryKey(Integer.valueOf(id));
            HashMap<String,Object> result =new HashMap<String,Object>();
            result.put("bean", bean);
            return this.getResult(bean);
        }


    /**
     * 说明:更新MyPlace信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-8-30 16:38:15
     */
      @API( summary="根据id更新单个场地信息",
        description = "根据id更新单个场地信息",
        parameters={
           @Param(name="id" , description="编号",type="INTEGER",required = false),
           @Param(name="name" , description="名称",type="STRING",required = false),
           @Param(name="code" , description="编号",type="STRING",required = false),
           @Param(name="province" , description="省",type="INTEGER",required = false),
           @Param(name="city" , description="市",type="INTEGER",required = false),
           @Param(name="county" , description="区",type="INTEGER",required = false),
           @Param(name="price" , description="价格",type="FLOAT",required = false),
           @Param(name="address" , description="详细地址",type="STRING",required = false),
           @Param(name="cover" , description="封面",type="STRING",required = false),
           @Param(name="pic" , description="图片",type="STRING",required = false),
           @Param(name="createTime" , description="创建日期",type="DATE",required = false),
           @Param(name="updateTime" , description="更新日期",type="DATE",required = false),
           @Param(name="telno" , description="联系电话",type="STRING",required = false),
           @Param(name="createUser" , description="创建人",type="LONG",required = false),
           @Param(name="lng" , description="经度",type="FLOAT",required = false),
           @Param(name="lat" , description="纬度",type="FLOAT",required = false),
           @Param(name="comments" , description="评论",type="INTEGER",required = false),
           @Param(name="score" , description="评分",type="FLOAT",required = false),
           @Param(name="lable" , description="标签",type="STRING",required = false),
           @Param(name="remark" , description="备注",type="STRING",required = false),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update",method = RequestMethod.PUT)///{id}
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Integer id,
        MyPlace myPlace =new  MyPlace();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            myPlace.setId(Integer.valueOf(id)) ;
        }
        
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            myPlace.setName(String.valueOf(name)) ;
        }
        
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            myPlace.setCode(String.valueOf(code)) ;
        }
        
        String province = request.getParameter("province");
        if(!StringUtil.isBlank(province)){
            myPlace.setProvince(Integer.valueOf(province)) ;
        }
        
        String city = request.getParameter("city");
        if(!StringUtil.isBlank(city)){
            myPlace.setCity(Integer.valueOf(city)) ;
        }
        
        String county = request.getParameter("county");
        if(!StringUtil.isBlank(county)){
            myPlace.setCounty(Integer.valueOf(county)) ;
        }
        
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            myPlace.setPrice(Float.valueOf(price)) ;
        }
        
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            myPlace.setAddress(String.valueOf(address)) ;
        }
        
        String cover = request.getParameter("cover");
        if(!StringUtil.isBlank(cover)){
            myPlace.setCover(String.valueOf(cover)) ;
        }
        
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            myPlace.setPic(String.valueOf(pic)) ;
        }
        
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            myPlace.setCreateTime(Date.valueOf(createTime)) ;
        }
        
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            myPlace.setUpdateTime(Date.valueOf(updateTime)) ;
        }
        
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            myPlace.setTelno(String.valueOf(telno)) ;
        }
        
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            myPlace.setCreateUser(Long.valueOf(createUser)) ;
        }
        
        String lng = request.getParameter("lng");
        if(!StringUtil.isBlank(lng)){
            myPlace.setLng(Float.valueOf(lng)) ;
        }
        
        String lat = request.getParameter("lat");
        if(!StringUtil.isBlank(lat)){
            myPlace.setLat(Float.valueOf(lat)) ;
        }
        
        String comments = request.getParameter("comments");
        if(!StringUtil.isBlank(comments)){
            myPlace.setComments(Integer.valueOf(comments)) ;
        }
        
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            myPlace.setScore(Float.valueOf(score)) ;
        }
        
        String lable = request.getParameter("lable");
        if(!StringUtil.isBlank(lable)){
            myPlace.setLable(String.valueOf(lable)) ;
        }
        
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            myPlace.setRemark(String.valueOf(remark)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            myPlace.setId(Integer.valueOf(id));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            myPlace.setName(name);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            myPlace.setCode(code);
        }
        String province = request.getParameter("province");
        if(!StringUtil.isBlank(province)){
            myPlace.setProvince(Integer.valueOf(province));
        }
        String city = request.getParameter("city");
        if(!StringUtil.isBlank(city)){
            myPlace.setCity(Integer.valueOf(city));
        }
        String county = request.getParameter("county");
        if(!StringUtil.isBlank(county)){
            myPlace.setCounty(Integer.valueOf(county));
        }
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            myPlace.setPrice(Float.valueOf(price));
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            myPlace.setAddress(address);
        }
        String cover = request.getParameter("cover");
        if(!StringUtil.isBlank(cover)){
            myPlace.setCover(cover);
        }
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            myPlace.setPic(pic);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                myPlace.setCreateTime(new Date(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd")){
                myPlace.setCreateTime(DateUtil.parseToDate(createTime, "yyyy-MM-dd"));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                myPlace.setUpdateTime(new Date(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd")){
                myPlace.setUpdateTime(DateUtil.parseToDate(updateTime, "yyyy-MM-dd"));
            }
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            myPlace.setTelno(telno);
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            myPlace.setCreateUser(Long.valueOf(createUser));
        }
        String lng = request.getParameter("lng");
        if(!StringUtil.isBlank(lng)){
            myPlace.setLng(Float.valueOf(lng));
        }
        String lat = request.getParameter("lat");
        if(!StringUtil.isBlank(lat)){
            myPlace.setLat(Float.valueOf(lat));
        }
        String comments = request.getParameter("comments");
        if(!StringUtil.isBlank(comments)){
            myPlace.setComments(Integer.valueOf(comments));
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            myPlace.setScore(Float.valueOf(score));
        }
        String lable = request.getParameter("lable");
        if(!StringUtil.isBlank(lable)){
            myPlace.setLable(lable);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            myPlace.setRemark(remark);
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("name", name, "名称",  new Rule[]{new Length(500)});
        vu.add("code", code, "编号",  new Rule[]{new Length(20)});
        vu.add("province", province, "省",  new Rule[]{new Digits(11,0)});
        vu.add("city", city, "市",  new Rule[]{new Digits(11,0)});
        vu.add("county", county, "区",  new Rule[]{new Digits(11,0)});
        vu.add("price", price, "价格",  new Rule[]{new Digits(11,2)});
        vu.add("address", address, "详细地址",  new Rule[]{new Length(500)});
        vu.add("cover", cover, "封面",  new Rule[]{new Length(100)});
        vu.add("pic", pic, "图片",  new Rule[]{new Length(500)});
        vu.add("createTime", createTime, "创建日期",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新日期",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("telno", telno, "联系电话",  new Rule[]{new Length(11)});
        vu.add("createUser", createUser, "创建人",  new Rule[]{new Digits(11,0)});
        vu.add("lng", lng, "经度",  new Rule[]{new Digits(5,5)});
        vu.add("lat", lat, "纬度",  new Rule[]{new Digits(5,5)});
        vu.add("comments", comments, "评论",  new Rule[]{new Digits(7,0)});
        vu.add("score", score, "评分",  new Rule[]{new Digits(5,2)});
        vu.add("lable", lable, "标签",  new Rule[]{new Length(500)});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(500)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return myPlaceService.save(myPlace);
       
    }


        /**
         * 说明:添加MyPlace信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2018-8-30 16:38:15
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个场地信息",
            description = "添加单个场地信息",
            parameters={
               @Param(name="id" , description="编号",dataType = DataType.INTEGER,required = false),
               @Param(name="name" , description="名称",dataType = DataType.STRING,required = false),
               @Param(name="code" , description="编号",dataType = DataType.STRING,required = false),
               @Param(name="province" , description="省",dataType = DataType.INTEGER,required = false),
               @Param(name="city" , description="市",dataType = DataType.INTEGER,required = false),
               @Param(name="county" , description="区",dataType = DataType.INTEGER,required = false),
               @Param(name="price" , description="价格",dataType = DataType.FLOAT,required = false),
               @Param(name="address" , description="详细地址",dataType = DataType.STRING,required = false),
               @Param(name="cover" , description="封面",dataType = DataType.STRING,required = false),
               @Param(name="pic" , description="图片",dataType = DataType.STRING,required = false),
               @Param(name="createTime" , description="创建日期",dataType = DataType.DATE,required = false),
               @Param(name="updateTime" , description="更新日期",dataType = DataType.DATE,required = false),
               @Param(name="telno" , description="联系电话",dataType = DataType.STRING,required = false),
               @Param(name="createUser" , description="创建人",dataType = DataType.LONG,required = false),
               @Param(name="lng" , description="经度",dataType = DataType.FLOAT,required = false),
               @Param(name="lat" , description="纬度",dataType = DataType.FLOAT,required = false),
               @Param(name="comments" , description="评论",dataType = DataType.INTEGER,required = false),
               @Param(name="score" , description="评分",dataType = DataType.FLOAT,required = false),
               @Param(name="lable" , description="标签",dataType = DataType.STRING,required = false),
               @Param(name="remark" , description="备注",dataType = DataType.STRING,required = false),
            })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            MyPlace myPlace =new  MyPlace();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                myPlace.setId(Integer.valueOf(id)) ;
            }
            
            String name = request.getParameter("name");
            if(!StringUtil.isBlank(name)){
                myPlace.setName(String.valueOf(name)) ;
            }
            
            String code = request.getParameter("code");
            if(!StringUtil.isBlank(code)){
                myPlace.setCode(String.valueOf(code)) ;
            }
            
            String province = request.getParameter("province");
            if(!StringUtil.isBlank(province)){
                myPlace.setProvince(Integer.valueOf(province)) ;
            }
            
            String city = request.getParameter("city");
            if(!StringUtil.isBlank(city)){
                myPlace.setCity(Integer.valueOf(city)) ;
            }
            
            String county = request.getParameter("county");
            if(!StringUtil.isBlank(county)){
                myPlace.setCounty(Integer.valueOf(county)) ;
            }
            
            String price = request.getParameter("price");
            if(!StringUtil.isBlank(price)){
                myPlace.setPrice(Float.valueOf(price)) ;
            }
            
            String address = request.getParameter("address");
            if(!StringUtil.isBlank(address)){
                myPlace.setAddress(String.valueOf(address)) ;
            }
            
            String cover = request.getParameter("cover");
            if(!StringUtil.isBlank(cover)){
                myPlace.setCover(String.valueOf(cover)) ;
            }
            
            String pic = request.getParameter("pic");
            if(!StringUtil.isBlank(pic)){
                myPlace.setPic(String.valueOf(pic)) ;
            }
            
            String createTime = request.getParameter("createTime");
            if(!StringUtil.isBlank(createTime)){
                myPlace.setCreateTime(Date.valueOf(createTime)) ;
            }
            
            String updateTime = request.getParameter("updateTime");
            if(!StringUtil.isBlank(updateTime)){
                myPlace.setUpdateTime(Date.valueOf(updateTime)) ;
            }
            
            String telno = request.getParameter("telno");
            if(!StringUtil.isBlank(telno)){
                myPlace.setTelno(String.valueOf(telno)) ;
            }
            
            String createUser = request.getParameter("createUser");
            if(!StringUtil.isBlank(createUser)){
                myPlace.setCreateUser(Long.valueOf(createUser)) ;
            }
            
            String lng = request.getParameter("lng");
            if(!StringUtil.isBlank(lng)){
                myPlace.setLng(Float.valueOf(lng)) ;
            }
            
            String lat = request.getParameter("lat");
            if(!StringUtil.isBlank(lat)){
                myPlace.setLat(Float.valueOf(lat)) ;
            }
            
            String comments = request.getParameter("comments");
            if(!StringUtil.isBlank(comments)){
                myPlace.setComments(Integer.valueOf(comments)) ;
            }
            
            String score = request.getParameter("score");
            if(!StringUtil.isBlank(score)){
                myPlace.setScore(Float.valueOf(score)) ;
            }
            
            String lable = request.getParameter("lable");
            if(!StringUtil.isBlank(lable)){
                myPlace.setLable(String.valueOf(lable)) ;
            }
            
            String remark = request.getParameter("remark");
            if(!StringUtil.isBlank(remark)){
                myPlace.setRemark(String.valueOf(remark)) ;
            }
            */
            String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            myPlace.setId(Integer.valueOf(id));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            myPlace.setName(name);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            myPlace.setCode(code);
        }
        String province = request.getParameter("province");
        if(!StringUtil.isBlank(province)){
            myPlace.setProvince(Integer.valueOf(province));
        }
        String city = request.getParameter("city");
        if(!StringUtil.isBlank(city)){
            myPlace.setCity(Integer.valueOf(city));
        }
        String county = request.getParameter("county");
        if(!StringUtil.isBlank(county)){
            myPlace.setCounty(Integer.valueOf(county));
        }
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            myPlace.setPrice(Float.valueOf(price));
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            myPlace.setAddress(address);
        }
        String cover = request.getParameter("cover");
        if(!StringUtil.isBlank(cover)){
            myPlace.setCover(cover);
        }
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            myPlace.setPic(pic);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                myPlace.setCreateTime(new Date(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd")){
                myPlace.setCreateTime(DateUtil.parseToDate(createTime, "yyyy-MM-dd"));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                myPlace.setUpdateTime(new Date(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd")){
                myPlace.setUpdateTime(DateUtil.parseToDate(updateTime, "yyyy-MM-dd"));
            }
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            myPlace.setTelno(telno);
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            myPlace.setCreateUser(Long.valueOf(createUser));
        }
        String lng = request.getParameter("lng");
        if(!StringUtil.isBlank(lng)){
            myPlace.setLng(Float.valueOf(lng));
        }
        String lat = request.getParameter("lat");
        if(!StringUtil.isBlank(lat)){
            myPlace.setLat(Float.valueOf(lat));
        }
        String comments = request.getParameter("comments");
        if(!StringUtil.isBlank(comments)){
            myPlace.setComments(Integer.valueOf(comments));
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            myPlace.setScore(Float.valueOf(score));
        }
        String lable = request.getParameter("lable");
        if(!StringUtil.isBlank(lable)){
            myPlace.setLable(lable);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            myPlace.setRemark(remark);
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("name", name, "名称",  new Rule[]{new Length(500)});
        vu.add("code", code, "编号",  new Rule[]{new Length(20)});
        vu.add("province", province, "省",  new Rule[]{new Digits(11,0)});
        vu.add("city", city, "市",  new Rule[]{new Digits(11,0)});
        vu.add("county", county, "区",  new Rule[]{new Digits(11,0)});
        vu.add("price", price, "价格",  new Rule[]{new Digits(11,2)});
        vu.add("address", address, "详细地址",  new Rule[]{new Length(500)});
        vu.add("cover", cover, "封面",  new Rule[]{new Length(100)});
        vu.add("pic", pic, "图片",  new Rule[]{new Length(500)});
        vu.add("createTime", createTime, "创建日期",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新日期",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("telno", telno, "联系电话",  new Rule[]{new Length(11)});
        vu.add("createUser", createUser, "创建人",  new Rule[]{new Digits(11,0)});
        vu.add("lng", lng, "经度",  new Rule[]{new Digits(5,5)});
        vu.add("lat", lat, "纬度",  new Rule[]{new Digits(5,5)});
        vu.add("comments", comments, "评论",  new Rule[]{new Digits(7,0)});
        vu.add("score", score, "评分",  new Rule[]{new Digits(5,2)});
        vu.add("lable", lable, "标签",  new Rule[]{new Length(500)});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(500)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

            return myPlaceService.save(myPlace);

        }


          /**
                 * 说明:添加MyPlace信息
                 * @param request
                 * @throws Exception
                 * @return ResultDTO
                 * @author dozen.zhang
                 * @date 2018-8-30 16:38:15
                 */
                // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                @API( summary="添加单个场地信息",
                    description = "添加单个场地信息",
                    parameters={
                       @Param(name="id" , description="编号",dataType = DataType.INTEGER,required = false),
                       @Param(name="name" , description="名称",dataType = DataType.STRING,required = false),
                       @Param(name="code" , description="编号",dataType = DataType.STRING,required = false),
                       @Param(name="province" , description="省",dataType = DataType.INTEGER,required = false),
                       @Param(name="city" , description="市",dataType = DataType.INTEGER,required = false),
                       @Param(name="county" , description="区",dataType = DataType.INTEGER,required = false),
                       @Param(name="price" , description="价格",dataType = DataType.FLOAT,required = false),
                       @Param(name="address" , description="详细地址",dataType = DataType.STRING,required = false),
                       @Param(name="cover" , description="封面",dataType = DataType.STRING,required = false),
                       @Param(name="pic" , description="图片",dataType = DataType.STRING,required = false),
                       @Param(name="createTime" , description="创建日期",dataType = DataType.DATE,required = false),
                       @Param(name="updateTime" , description="更新日期",dataType = DataType.DATE,required = false),
                       @Param(name="telno" , description="联系电话",dataType = DataType.STRING,required = false),
                       @Param(name="createUser" , description="创建人",dataType = DataType.LONG,required = false),
                       @Param(name="lng" , description="经度",dataType = DataType.FLOAT,required = false),
                       @Param(name="lat" , description="纬度",dataType = DataType.FLOAT,required = false),
                       @Param(name="comments" , description="评论",dataType = DataType.INTEGER,required = false),
                       @Param(name="score" , description="评分",dataType = DataType.FLOAT,required = false),
                       @Param(name="lable" , description="标签",dataType = DataType.STRING,required = false),
                       @Param(name="remark" , description="备注",dataType = DataType.STRING,required = false),
                    })
                @RequestMapping(value = "save",method = RequestMethod.POST)
                @ResponseBody
                public ResultDTO save(HttpServletRequest request) throws Exception {
                    MyPlace myPlace =new  MyPlace();
                    /*
                    String id = request.getParameter("id");
                    if(!StringUtil.isBlank(id)){
                        myPlace.setId(Integer.valueOf(id)) ;
                    }
                    
                    String name = request.getParameter("name");
                    if(!StringUtil.isBlank(name)){
                        myPlace.setName(String.valueOf(name)) ;
                    }
                    
                    String code = request.getParameter("code");
                    if(!StringUtil.isBlank(code)){
                        myPlace.setCode(String.valueOf(code)) ;
                    }
                    
                    String province = request.getParameter("province");
                    if(!StringUtil.isBlank(province)){
                        myPlace.setProvince(Integer.valueOf(province)) ;
                    }
                    
                    String city = request.getParameter("city");
                    if(!StringUtil.isBlank(city)){
                        myPlace.setCity(Integer.valueOf(city)) ;
                    }
                    
                    String county = request.getParameter("county");
                    if(!StringUtil.isBlank(county)){
                        myPlace.setCounty(Integer.valueOf(county)) ;
                    }
                    
                    String price = request.getParameter("price");
                    if(!StringUtil.isBlank(price)){
                        myPlace.setPrice(Float.valueOf(price)) ;
                    }
                    
                    String address = request.getParameter("address");
                    if(!StringUtil.isBlank(address)){
                        myPlace.setAddress(String.valueOf(address)) ;
                    }
                    
                    String cover = request.getParameter("cover");
                    if(!StringUtil.isBlank(cover)){
                        myPlace.setCover(String.valueOf(cover)) ;
                    }
                    
                    String pic = request.getParameter("pic");
                    if(!StringUtil.isBlank(pic)){
                        myPlace.setPic(String.valueOf(pic)) ;
                    }
                    
                    String createTime = request.getParameter("createTime");
                    if(!StringUtil.isBlank(createTime)){
                        myPlace.setCreateTime(Date.valueOf(createTime)) ;
                    }
                    
                    String updateTime = request.getParameter("updateTime");
                    if(!StringUtil.isBlank(updateTime)){
                        myPlace.setUpdateTime(Date.valueOf(updateTime)) ;
                    }
                    
                    String telno = request.getParameter("telno");
                    if(!StringUtil.isBlank(telno)){
                        myPlace.setTelno(String.valueOf(telno)) ;
                    }
                    
                    String createUser = request.getParameter("createUser");
                    if(!StringUtil.isBlank(createUser)){
                        myPlace.setCreateUser(Long.valueOf(createUser)) ;
                    }
                    
                    String lng = request.getParameter("lng");
                    if(!StringUtil.isBlank(lng)){
                        myPlace.setLng(Float.valueOf(lng)) ;
                    }
                    
                    String lat = request.getParameter("lat");
                    if(!StringUtil.isBlank(lat)){
                        myPlace.setLat(Float.valueOf(lat)) ;
                    }
                    
                    String comments = request.getParameter("comments");
                    if(!StringUtil.isBlank(comments)){
                        myPlace.setComments(Integer.valueOf(comments)) ;
                    }
                    
                    String score = request.getParameter("score");
                    if(!StringUtil.isBlank(score)){
                        myPlace.setScore(Float.valueOf(score)) ;
                    }
                    
                    String lable = request.getParameter("lable");
                    if(!StringUtil.isBlank(lable)){
                        myPlace.setLable(String.valueOf(lable)) ;
                    }
                    
                    String remark = request.getParameter("remark");
                    if(!StringUtil.isBlank(remark)){
                        myPlace.setRemark(String.valueOf(remark)) ;
                    }
                    */
                    String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            myPlace.setId(Integer.valueOf(id));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            myPlace.setName(name);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            myPlace.setCode(code);
        }
        String province = request.getParameter("province");
        if(!StringUtil.isBlank(province)){
            myPlace.setProvince(Integer.valueOf(province));
        }
        String city = request.getParameter("city");
        if(!StringUtil.isBlank(city)){
            myPlace.setCity(Integer.valueOf(city));
        }
        String county = request.getParameter("county");
        if(!StringUtil.isBlank(county)){
            myPlace.setCounty(Integer.valueOf(county));
        }
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            myPlace.setPrice(Float.valueOf(price));
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            myPlace.setAddress(address);
        }
        String cover = request.getParameter("cover");
        if(!StringUtil.isBlank(cover)){
            myPlace.setCover(cover);
        }
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            myPlace.setPic(pic);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                myPlace.setCreateTime(new Date(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd")){
                myPlace.setCreateTime(DateUtil.parseToDate(createTime, "yyyy-MM-dd"));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                myPlace.setUpdateTime(new Date(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd")){
                myPlace.setUpdateTime(DateUtil.parseToDate(updateTime, "yyyy-MM-dd"));
            }
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            myPlace.setTelno(telno);
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            myPlace.setCreateUser(Long.valueOf(createUser));
        }
        String lng = request.getParameter("lng");
        if(!StringUtil.isBlank(lng)){
            myPlace.setLng(Float.valueOf(lng));
        }
        String lat = request.getParameter("lat");
        if(!StringUtil.isBlank(lat)){
            myPlace.setLat(Float.valueOf(lat));
        }
        String comments = request.getParameter("comments");
        if(!StringUtil.isBlank(comments)){
            myPlace.setComments(Integer.valueOf(comments));
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            myPlace.setScore(Float.valueOf(score));
        }
        String lable = request.getParameter("lable");
        if(!StringUtil.isBlank(lable)){
            myPlace.setLable(lable);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            myPlace.setRemark(remark);
        }

                    //valid
                    ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("name", name, "名称",  new Rule[]{new Length(500)});
        vu.add("code", code, "编号",  new Rule[]{new Length(20)});
        vu.add("province", province, "省",  new Rule[]{new Digits(11,0)});
        vu.add("city", city, "市",  new Rule[]{new Digits(11,0)});
        vu.add("county", county, "区",  new Rule[]{new Digits(11,0)});
        vu.add("price", price, "价格",  new Rule[]{new Digits(11,2)});
        vu.add("address", address, "详细地址",  new Rule[]{new Length(500)});
        vu.add("cover", cover, "封面",  new Rule[]{new Length(100)});
        vu.add("pic", pic, "图片",  new Rule[]{new Length(500)});
        vu.add("createTime", createTime, "创建日期",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新日期",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("telno", telno, "联系电话",  new Rule[]{new Length(11)});
        vu.add("createUser", createUser, "创建人",  new Rule[]{new Digits(11,0)});
        vu.add("lng", lng, "经度",  new Rule[]{new Digits(5,5)});
        vu.add("lat", lat, "纬度",  new Rule[]{new Digits(5,5)});
        vu.add("comments", comments, "评论",  new Rule[]{new Digits(7,0)});
        vu.add("score", score, "评分",  new Rule[]{new Digits(5,2)});
        vu.add("lable", lable, "标签",  new Rule[]{new Length(500)});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(500)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

                    return myPlaceService.save(myPlace);

                }

    /**
     * 说明:删除MyPlace信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-8-30 16:38:15
     */
     @API( summary="根据id删除单个场地信息",
        description = "根据id删除单个场地信息",
        parameters={
         @Param(name="id" , description="编号",dataType= DataType.INTEGER,required = true),
        })
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)//{id}
    @ResponseBody
    public ResultDTO delete(HttpServletRequest request) {//@PathVariable Integer id,
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Integer id = Integer.valueOf(idStr);
        myPlaceService.delete(id);
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
       return  myPlaceService.multilDelete(idAry);
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
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            params.put("code",code);
        }
        String codeLike = request.getParameter("codeLike");
        if(!StringUtil.isBlank(codeLike)){
            params.put("codeLike",codeLike);
        }
        String province = request.getParameter("province");
        if(!StringUtil.isBlank(province)){
            params.put("province",province);
        }
        String city = request.getParameter("city");
        if(!StringUtil.isBlank(city)){
            params.put("city",city);
        }
        String county = request.getParameter("county");
        if(!StringUtil.isBlank(county)){
            params.put("county",county);
        }
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            params.put("price",price);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = request.getParameter("addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
        }
        String cover = request.getParameter("cover");
        if(!StringUtil.isBlank(cover)){
            params.put("cover",cover);
        }
        String coverLike = request.getParameter("coverLike");
        if(!StringUtil.isBlank(coverLike)){
            params.put("coverLike",coverLike);
        }
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            params.put("pic",pic);
        }
        String picLike = request.getParameter("picLike");
        if(!StringUtil.isBlank(picLike)){
            params.put("picLike",picLike);
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
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                params.put("updateTime",updateTime);
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd")){
                params.put("updateTime",DateUtil.parseToDate(updateTime, "yyyy-MM-dd"));
            }
        }
        String updateTimeBegin = request.getParameter("updateTimeBegin");
        if(!StringUtil.isBlank(updateTimeBegin)){
            if(StringUtil.checkNumeric(updateTimeBegin)){
                params.put("updateTimeBegin",updateTimeBegin);
            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd")){
                params.put("updateTimeBegin",DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd"));
            }
        }
        String updateTimeEnd = request.getParameter("updateTimeEnd");
        if(!StringUtil.isBlank(updateTimeEnd)){
            if(StringUtil.checkNumeric(updateTimeEnd)){
                params.put("updateTimeEnd",updateTimeEnd);
            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd")){
                params.put("updateTimeEnd",DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd"));
            }
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            params.put("telno",telno);
        }
        String telnoLike = request.getParameter("telnoLike");
        if(!StringUtil.isBlank(telnoLike)){
            params.put("telnoLike",telnoLike);
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            params.put("createUser",createUser);
        }
        String lng = request.getParameter("lng");
        if(!StringUtil.isBlank(lng)){
            params.put("lng",lng);
        }
        String lat = request.getParameter("lat");
        if(!StringUtil.isBlank(lat)){
            params.put("lat",lat);
        }
        String comments = request.getParameter("comments");
        if(!StringUtil.isBlank(comments)){
            params.put("comments",comments);
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            params.put("score",score);
        }
        String lable = request.getParameter("lable");
        if(!StringUtil.isBlank(lable)){
            params.put("lable",lable);
        }
        String lableLike = request.getParameter("lableLike");
        if(!StringUtil.isBlank(lableLike)){
            params.put("lableLike",lableLike);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }

        // 查询list集合
        List<MyPlace> list =myPlaceService.listByParams(params);
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
        colTitle.put("code", "编号");
        colTitle.put("province", "省");
        colTitle.put("city", "市");
        colTitle.put("county", "区");
        colTitle.put("price", "价格");
        colTitle.put("address", "详细地址");
        colTitle.put("cover", "封面");
        colTitle.put("pic", "图片");
        colTitle.put("createTime", "创建日期");
        colTitle.put("updateTime", "更新日期");
        colTitle.put("telno", "联系电话");
        colTitle.put("createUser", "创建人");
        colTitle.put("lng", "经度");
        colTitle.put("lat", "纬度");
        colTitle.put("comments", "评论");
        colTitle.put("score", "评分");
        colTitle.put("lable", "标签");
        colTitle.put("remark", "备注");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            MyPlace sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("name",  list.get(i).getName());
            map.put("code",  list.get(i).getCode());
            map.put("province",  list.get(i).getProvince());
            map.put("city",  list.get(i).getCity());
            map.put("county",  list.get(i).getCounty());
            map.put("price",  list.get(i).getPrice());
            map.put("address",  list.get(i).getAddress());
            map.put("cover",  list.get(i).getCover());
            map.put("pic",  list.get(i).getPic());
            map.put("createTime",  list.get(i).getCreateTime());
            map.put("updateTime",  list.get(i).getUpdateTime());
            map.put("telno",  list.get(i).getTelno());
            map.put("createUser",  list.get(i).getCreateUser());
            map.put("lng",  list.get(i).getLng());
            map.put("lat",  list.get(i).getLat());
            map.put("comments",  list.get(i).getComments());
            map.put("score",  list.get(i).getScore());
            map.put("lable",  list.get(i).getLable());
            map.put("remark",  list.get(i).getRemark());
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
         * 说明: 跳转到MyPlace列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/place/MyPlaceList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/place/MyPlaceListMapper.html";
        }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-8-30 16:38:15
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/place/MyPlaceEdit.html";
    }
    /**
         * 说明:跳转查看页面
         * @param request 发请求
         * @return String
         * @author dozen.zhang
         * @date 2018-8-30 16:38:15
         */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/place/MyPlaceView.html";
    }
}
