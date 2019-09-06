/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2017-11-20 10:48:51
 * 文件说明: 
 */

package com.dozenx.web.module.map.mapData.action;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import com.dozenx.common.util.*;
import com.dozenx.swagger.annotation.*;
import com.dozenx.common.Path.PathManager;

import javax.servlet.http.HttpServletRequest;

import com.dozenx.web.core.Constants;
import com.dozenx.web.core.log.ErrorMessage;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.dozenx.web.module.map.mapData.service.MapDataService;
import com.dozenx.web.module.map.mapData.bean.MapData;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.util.RequestUtil;
import org.springframework.web.bind.annotation.*;
import com.dozenx.web.core.log.ResultDTO;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@APIs(description = "用户")
@Controller
@RequestMapping(Constants.WEBROOT+"/mapdata")
public class MapDataController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(MapDataController.class);
    /** 权限service **/
    @Autowired
    private MapDataService mapDataService;
    


    /**
     * 说明:ajax请求MapData信息
     * @author dozen.zhang
     * @date 2017-11-20 10:48:51
     * @return String
     */
       @API(summary="用户列表接口",
                 description="用户列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
                    @Param(name="name" , description="名称",dataType = DataType.STRING,required = false),
                    @Param(name="content" , description="内容",dataType = DataType.STRING,required = false),
                    @Param(name="lng" , description="经度",dataType = DataType.FLOAT,required = false),
                    @Param(name="lat" , description="维度",dataType = DataType.FLOAT,required = false),
                    @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = false),
                    @Param(name="address" , description="地址",dataType = DataType.STRING,required = false),
                    @Param(name="updatetime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
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
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = request.getParameter("contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String lng = request.getParameter("lng");
        if(!StringUtil.isBlank(lng)){
            params.put("lng",lng);
        }
        String lat = request.getParameter("lat");
        if(!StringUtil.isBlank(lat)){
            params.put("lat",lat);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = request.getParameter("addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
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
        List<MapData> mapDatas = mapDataService.listByParams4Page(params);
        return ResultUtil.getResult(mapDatas, page);
    }
    
   /**
    * 说明:ajax请求MapData信息 无分页版本
    * @return ResultDTO 返回结果
    * @author dozen.zhang
    * @date 2017-11-20 10:48:51
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
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = request.getParameter("contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String lng = request.getParameter("lng");
        if(!StringUtil.isBlank(lng)){
            params.put("lng",lng);
        }
        String lat = request.getParameter("lat");
        if(!StringUtil.isBlank(lat)){
            params.put("lat",lat);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = request.getParameter("addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
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

        List<MapData> mapDatas = mapDataService.listByParams(params);
        return ResultUtil.getDataResult(mapDatas);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2017-11-20 10:48:51
    */
  @API( summary="根据id查询单个用户信息",
           description = "根据id查询单个用户信息",
           parameters={
                   @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
           })
    @RequestMapping(value = "/view.json",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(HttpServletRequest request) {
            HashMap<String,Object> result =new HashMap</*String,Object*/>();
      String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            MapData bean = mapDataService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        MapData bean = mapDataService.selectByPrimaryKey(Long.valueOf(id));
        HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }

    
    /**
     * 说明:更新MapData信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2017-11-20 10:48:51
     */
      @API( summary="根据id更新单个用户信息",
        description = "根据id更新单个用户信息",
        parameters={
           @Param(name="id" , description="编号",type="LONG",required = false),
           @Param(name="name" , description="名称",type="STRING",required = false),
           @Param(name="content" , description="内容",type="STRING",required = false),
           @Param(name="lng" , description="经度",type="FLOAT",required = false),
           @Param(name="lat" , description="维度",type="FLOAT",required = false),
           @Param(name="status" , description="状态",type="INTEGER",required = true),
           @Param(name="address" , description="地址",type="STRING",required = false),
           @Param(name="updatetime" , description="更新时间",type="TIMESTAMP",required = false),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "save.json",method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {
        MapData mapData =new  MapData();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            mapData.setId(Long.valueOf(id)) ;
        }
        
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            mapData.setName(String.valueOf(name)) ;
        }
        
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            mapData.setContent(String.valueOf(content)) ;
        }
        
        String lng = request.getParameter("lng");
        if(!StringUtil.isBlank(lng)){
            mapData.setLng(BigDecimal.valueOf(lng)) ;
        }
        
        String lat = request.getParameter("lat");
        if(!StringUtil.isBlank(lat)){
            mapData.setLat(BigDecimal.valueOf(lat)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            mapData.setStatus(Integer.valueOf(status)) ;
        }
        
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            mapData.setAddress(String.valueOf(address)) ;
        }
        
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            mapData.setUpdatetime(Timestamp.valueOf(updatetime)) ;
        */

       String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            mapData.setId(Long.valueOf(id));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            mapData.setName(name);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            mapData.setContent(content);
        }
        String lng = request.getParameter("lng");
        if(!StringUtil.isBlank(lng)){
            mapData.setLng(new BigDecimal(lng));
        }
        String lat = request.getParameter("lat");
        if(!StringUtil.isBlank(lat)){
            mapData.setLat(new BigDecimal(lat));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            mapData.setStatus(Integer.valueOf(status));
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            mapData.setAddress(address);
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                mapData.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                mapData.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id+"", "编号",  new Rule[]{new Digits(15,0)});
        vu.add("name", name, "名称",  new Rule[]{new Length(20)});
        vu.add("content", content, "内容",  new Rule[]{new Length(50)});
        vu.add("lng", lng, "经度",  new Rule[]{});
        vu.add("lat", lat, "维度",  new Rule[]{});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","-1"}),new NotEmpty()});
        vu.add("address", address, "地址",  new Rule[]{new Length(50)});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return mapDataService.save(mapData);
       
    }


        /**
         * 说明:添加MapData信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2017-11-20 10:48:51
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个用户信息",
            description = "添加单个用户信息",
            parameters={
               @Param(name="name" , description="名称",in= InType.form,dataType = DataType.STRING,required = false),
               @Param(name="content" , description="内容",in= InType.form,dataType = DataType.STRING,required = false),
               @Param(name="lng" , description="经度",in= InType.form,dataType = DataType.FLOAT,required = false),
               @Param(name="lat" , description="维度",in= InType.form, dataType = DataType.FLOAT,required = false),
               @Param(name="address" , description="地址",in= InType.form,dataType = DataType.STRING,required = false),
            })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {

            MapData mapData =new  MapData();

        /*    String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            mapData.setId(Long.valueOf(id));
        }*/
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            mapData.setName(name);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            mapData.setContent(content);
        }
        String lng = request.getParameter("lng");
        if(!StringUtil.isBlank(lng)){
            mapData.setLng(new BigDecimal(lng));
        }
        String lat = request.getParameter("lat");
        if(!StringUtil.isBlank(lat)){
            mapData.setLat(new BigDecimal(lat));
        }
        /*String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            mapData.setStatus(Integer.valueOf(status));
        }*/
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            mapData.setAddress(address);
        }
            String img =request.getParameter("img");
            mapData.setImg(img);
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                mapData.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                mapData.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
     //   vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
        vu.add("name", name, "名称",  new Rule[]{new Length(20)});
        vu.add("content", content, "内容",  new Rule[]{new Length(550)});
        vu.add("lng", lng, "经度",  new Rule[]{});
        vu.add("lat", lat, "维度",  new Rule[]{});
    //    vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","-1"}),new NotEmpty()});
        vu.add("address", address, "地址",  new Rule[]{new Length(50)});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

            return mapDataService.save(mapData);

        }
    /**
     * 说明:删除MapData信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2017-11-20 10:48:51
     */
     @API( summary="根据id删除单个用户信息",
        description = "根据id删除单个用户信息",
        parameters={
         @Param(name="id" , description="编号",dataType= DataType.LONG,required = true),
        })
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResultDTO delete(@PathVariable Long id,HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
     //  Long id = Long.valueOf(idStr);
        mapDataService.delete(id);
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
       return  mapDataService.multilDelete(idAry);
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
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = request.getParameter("contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String lng = request.getParameter("lng");
        if(!StringUtil.isBlank(lng)){
            params.put("lng",lng);
        }
        String lat = request.getParameter("lat");
        if(!StringUtil.isBlank(lat)){
            params.put("lat",lat);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = request.getParameter("addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
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
        List<MapData> list =mapDataService.listByParams(params);
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
        colTitle.put("content", "内容");
        colTitle.put("lng", "经度");
        colTitle.put("lat", "维度");
        colTitle.put("status", "状态");
        colTitle.put("address", "地址");
        colTitle.put("updatetime", "更新时间");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            MapData sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("name",  list.get(i).getName());
            map.put("content",  list.get(i).getContent());
            map.put("lng",  list.get(i).getLng());
            map.put("lat",  list.get(i).getLat());
            map.put("status",  list.get(i).getStatus());
            map.put("address",  list.get(i).getAddress());
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
    private static List<String > headers = new ArrayList<String>(Arrays.asList("name", "address", "lat","lng"));
    @RequestMapping(value = "/import.json")
    public ResultDTO importExcel(HttpServletRequest request) throws Exception {

        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multiRequest.getFile("file");// 导入的excel文件，不允许为空
        if (file == null || file.isEmpty()) {
            return ResultUtil.getResult(40007407,ErrorMessage.getErrorMsg("err.file.nonexist"));
            // 导入的excel文件不允许为空!
        }

        List<String> errors = new ArrayList<String>();

        List<Map<String,String >> dataList=null;
        try {
            dataList=ExcelUtil.getExcelDataFromStream(file.getInputStream(), headers);
        }catch (Exception e){
            logger.error("40007408 err.file.excel.parse.msg ",e);
            return this.getResult(40007408,  ErrorMessage.getErrorMsg("err.file.excel.parse"));
        }
        List<MapData> finalList = this.checkData(dataList, errors); // 验证通过记录数
        int errSize = errors.size();
        if (errSize == 0) {
           return  mapDataService.addBatch(finalList);
        } else {
            String relativePath = "temp/"+StringUtil.getRandomDigitString(5);
            ResultDTO resultDTO =new ResultDTO( );
            resultDTO.setR(40007409);
            if (errSize < 10) {
                String msg = StringUtil.join( ",",errors.toArray());
                resultDTO.setMsg( msg);
            } else {
                ExcelUtil.getExcelFileFromList(errors, PathManager.getInstance().getTmpPath().resolve(relativePath).toString());
                resultDTO.setMsg( "<a href=\""+relativePath+"\">错误原因过多:请下载excel查看</a>");
                resultDTO.setData(relativePath);
            }
            return resultDTO;
        }


    }
    public void geoProcess(){

    }

    public List<MapData> checkData(List<Map<String,String>> dataList,List<String > errors) throws Exception {
        //开始检查数据
        int rows = dataList.size();//获取总数据个数
        HashSet<String> mapDataNameSet = new HashSet<>();// 热点名称集合 用来去重

        List<MapData> finalList = new ArrayList<>();

        for (int i = 1; i <= rows; i++) {
            //MapData mapData = new MapData();
           Map<String,String> mapDataMap = dataList.get(i);

            String name = MapUtils.getString(mapDataMap,"name");
            String address = MapUtils.getString(mapDataMap,"address");
            String lat = MapUtils.getString(mapDataMap,"lat");
            String lng = MapUtils.getString(mapDataMap,"lng");
            String content = MapUtils.getString(mapDataMap,"content");


            ValidateUtil vu = new ValidateUtil();
            String validStr="";

            vu.add("name", name, "名称",  new Rule[]{new Length(20)});
            vu.add("content", content, "内容",  new Rule[]{new Length(50)});
            vu.add("lng", lng, "经度",  new Rule[]{});
            vu.add("lat", lat, "维度",  new Rule[]{});
           vu.add("address", address, "地址",  new Rule[]{new Length(50)});

            validStr = vu.validateString();
            if(StringUtil.isNotBlank(validStr)) {
               errors.add("第" + i + "行 数据错误 " +validStr);
                continue;
            }
            if (!mapDataNameSet.add(name)) {
                errors.add("第" + i + "行的‘热点名称’在Excel表格中存在重复值! ");
                continue;
            }
            MapData mapData = new MapData();
            mapData.setName( name);
            mapData.setContent(content);
            mapData.setLat(new BigDecimal(lat));
            mapData.setLng(new BigDecimal(lng));
            finalList.add(mapData);

//            if (errors.size() == 0) {
//                hotAreaList.add(hotArea);
//            }
        }
        return finalList;
    }

      /**
         * 说明: 跳转到MapData列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/MapDataList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/MapDataListMapper.html";
        }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2017-11-20 10:48:51
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/MapDataEdit.html";
    }
    /**
         * 说明:跳转查看页面
         * @param request 发请求
         * @return String
         * @author dozen.zhang
         * @date 2017-11-20 10:48:51
         */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/MapDataView.html";
    }


    /**
     * 1.获取附近的热点
     * @Author :zhangzw
     * @Date: 2017/11/14 14:35
     * @params :  * @param accessToken
     *
     */

    @API( summary="查询附近地图信息",
            description = "查询附近地图信息",
            parameters={

                    @Param(name="lng" , description="经度",dataType = DataType.DOUBLE,required = false),
                    @Param(name="lat" , description="维度",dataType = DataType.DOUBLE,required = false),
                    @Param(name="dist" , description="范围",dataType = DataType.INTEGER,required = true),
                    @Param(name="pageSize" , description="分页大小",dataType = DataType.INTEGER,required = true),
                    @Param(name="curPage" , description="当前页",dataType = DataType.INTEGER,required = true),
            })
    @RequestMapping(value = "nearby", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO nearBy(HttpServletRequest request ) throws Exception{
        //Map<String,Object> paramsMap = JsonUtils.fromJson(params, Map.class);//请求参数 json格式

        //valid
        ValidateUtil<Object> vu = new ValidateUtil();
        String validStr="";


        // 校验经度
        String xposStr = request.getParameter("lng");
        vu.add("lng",xposStr , "经度[lng]",new Rule[]{new Required(),new Digits(3,15),new FloatRange(-180f,180f)});

        // 校验纬度
        String yposStr = request.getParameter("lat");
        vu.add("lat", yposStr, "纬度[lat]",new Rule[]{new Required(),new Digits(3,15),new FloatRange(-90f,90f)});

        //校验距离
        String distStr= request.getParameter("dist");
        vu.add("dist", distStr, "距离[dist]",new Rule[]{new Required(),new Digits(5,8),new FloatRange(1f,10000f)});

        //获取分页
        String pageSizeStr= request.getParameter("pageSize");
        vu.add("pageSize",pageSizeStr,"分页大小[pageSize]",new Rule[]{new Required(),new Digits(2,0),new IntegerRange(0,10000)});

        String  pageNoStr = request.getParameter("curPage");
        vu.add("curPage",pageNoStr,"分页大小[pageNo]",new Rule[]{new Required(),new Digits(2,0),new IntegerRange(0,10)});



        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }


        BigDecimal xpos =new  BigDecimal(xposStr) ;//

        BigDecimal ypos= new BigDecimal(yposStr);
        Double dist= CastUtil.toDouble(distStr);


        Integer pageNo = CastUtil.toInteger( pageNoStr);
        Integer pageSize = CastUtil.toInteger(pageSizeStr);//每页记录数



        Map<String,Object> paramMap=new HashMap();
        paramMap.put("lng",new BigDecimal(xpos.toString()));
        paramMap.put("lat",new BigDecimal(ypos.toString()));
        paramMap.put("dist",new BigDecimal(dist.toString()));
        Page page = new Page(pageNo,pageSize);
        paramMap.put("page",page);

        List<MapData> merchantHotAreaList=mapDataService.queryNearby(paramMap);
//        Integer totalPage=0;
//        Integer totalRecord=0;
//        if(merchantHotAreaList!=null && merchantHotAreaList.size()!=0){
//            totalPage= merchantHotAreaList.size()/pageSize;
//            totalPage+=merchantHotAreaList.size()%pageSize == 0 ? 0 : 1;
//            totalRecord=merchantHotAreaList.size();
//        }
//        List<MerchantHotarea> pageList=new ArrayList<>();
//        if(pageNo<totalPage){
//            pageList=merchantHotAreaList.subList((pageNo-1)*pageSize,pageNo*pageSize);
//        }else {
//            if(pageNo==totalPage){
//                pageList=merchantHotAreaList.subList((pageNo-1)*pageSize,merchantHotAreaList.size());
//
//
//            }else {
//                if(pageNo>totalPage) {
//                    pageList=null;
//                }else {
//                    pageList=merchantHotAreaList.subList(0,merchantHotAreaList.size());
//                    page.setPageSize(1);
//                }
//            }
//
//        }
//
//        page.setRecords(pageList);
//        page.setPageNo(pageNo);
//        page.setTotalRecord(totalRecord);

        return  ResultUtil.getResult(0,merchantHotAreaList,"",page);
    }
    @API( summary="添加单个地图信息",
            description = "添加单个地图信息",
            parameters={

                    @Param(name="lng" , description="经度",dataType = DataType.DOUBLE,required = false),
                    @Param(name="lat" , description="维度",dataType = DataType.DOUBLE,required = false),
                    @Param(name="dist" , description="范围",dataType = DataType.INTEGER,required = true),
                    @Param(name="pageSize" , description="分页大小",dataType = DataType.INTEGER,required = true),
                    @Param(name="curPage" , description="当前页",dataType = DataType.INTEGER,required = true),
            })


    @RequestMapping(value = "address2latlng", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getLatLngByAddress(HttpServletRequest request){
        String address = request.getParameter("address");


        return  null;
    }
}
