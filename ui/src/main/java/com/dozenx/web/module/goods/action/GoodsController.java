/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-10-24 23:32:59
 * 文件说明: 
 */

package com.dozenx.web.module.goods.action;
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

import com.dozenx.web.core.annotation.RequiresLogin;
import com.dozenx.web.module.pubImageBelong.bean.PubImageBelong;
import com.dozenx.web.module.pubImageBelong.service.PubImageBelongService;
import com.dozenx.web.module.zan.bean.Zan;
import com.dozenx.web.module.zan.service.ZanService;
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
import com.dozenx.web.module.goods.service.GoodsService;
import com.dozenx.web.module.goods.bean.Goods;
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

@APIs(description = "商品")
@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(GoodsController.class);
    /** 权限service **/
    @Autowired
    private GoodsService goodsService;



    /**
     * 说明:ajax请求Goods信息
     * @author dozen.zhang
     * @date 2018-12-2 16:05:28
     * @return String
     */
    @API(summary="商品列表接口",
            description="商品列表接口",
            parameters={
                    @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                    @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",dataType = DataType.LONG,required =false),// false
                    @Param(name="shopId" , description="商户id",dataType = DataType.LONG,required =false),// false
                    @Param(name="name" , description="名称",dataType = DataType.STRING,required =false),// true
                    @Param(name="subName" , description="副标题",dataType = DataType.STRING,required =false),// true
                    @Param(name="detail" , description="副标题",dataType = DataType.STRING,required =false),// true
                    @Param(name="address" , description="地址",dataType = DataType.STRING,required =false),// false
                    @Param(name="telno" , description="手机号码",dataType = DataType.STRING,required =false),// false
                    @Param(name="img" , description="图片",dataType = DataType.STRING,required =false),// true
                    @Param(name="img1" , description="图片",dataType = DataType.STRING,required =false),// false
                    @Param(name="img2" , description="图片",dataType = DataType.STRING,required =false),// false
                    @Param(name="img3" , description="图片",dataType = DataType.STRING,required =false),// false
                    @Param(name="remark" , description="备注",dataType = DataType.STRING,required =false),// false
                    @Param(name="status" , description="状态",dataType = DataType.INTEGER,required =false),// true
                    @Param(name="price" , description="价格",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="priceDesc" , description="价格描述",dataType = DataType.STRING,required =false),// false
                    @Param(name="creator" , description="创建人id",dataType = DataType.LONG,required =false),// false
                    @Param(name="creatorName" , description="创建人姓名",dataType = DataType.STRING,required =false),// false
                    @Param(name="platform" , description="平台名称",dataType = DataType.STRING,required =false),// false
                    @Param(name="comments" , description="评论数",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="score" , description="分数",dataType = DataType.FLOAT,required =false),// false
                    @Param(name="link" , description="外链",dataType = DataType.STRING,required =false),// false
                    @Param(name="up" , description="顶",dataType = DataType.INTEGER,required =false),// false
                    @Param(name="down" , description="踩",dataType = DataType.INTEGER,required =false),// false
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
        String shopId = request.getParameter("shopId");
        if(!StringUtil.isBlank(shopId)){
            params.put("shopId",shopId);
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String subName = request.getParameter("subName");
        if(!StringUtil.isBlank(subName)){
            params.put("subName",subName);
        }
        String subNameLike = request.getParameter("subNameLike");
        if(!StringUtil.isBlank(subNameLike)){
            params.put("subNameLike",subNameLike);
        }
        String detail = request.getParameter("detail");
        if(!StringUtil.isBlank(detail)){
            params.put("detail",detail);
        }
        String detailLike = request.getParameter("detailLike");
        if(!StringUtil.isBlank(detailLike)){
            params.put("detailLike",detailLike);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = request.getParameter("addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            params.put("telno",telno);
        }
        String telnoLike = request.getParameter("telnoLike");
        if(!StringUtil.isBlank(telnoLike)){
            params.put("telnoLike",telnoLike);
        }
        String img = request.getParameter("img");
        if(!StringUtil.isBlank(img)){
            params.put("img",img);
        }
        String imgLike = request.getParameter("imgLike");
        if(!StringUtil.isBlank(imgLike)){
            params.put("imgLike",imgLike);
        }
        String img1 = request.getParameter("img1");
        if(!StringUtil.isBlank(img1)){
            params.put("img1",img1);
        }
        String img1Like = request.getParameter("img1Like");
        if(!StringUtil.isBlank(img1Like)){
            params.put("img1Like",img1Like);
        }
        String img2 = request.getParameter("img2");
        if(!StringUtil.isBlank(img2)){
            params.put("img2",img2);
        }
        String img2Like = request.getParameter("img2Like");
        if(!StringUtil.isBlank(img2Like)){
            params.put("img2Like",img2Like);
        }
        String img3 = request.getParameter("img3");
        if(!StringUtil.isBlank(img3)){
            params.put("img3",img3);
        }
        String img3Like = request.getParameter("img3Like");
        if(!StringUtil.isBlank(img3Like)){
            params.put("img3Like",img3Like);
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
        String priceDesc = request.getParameter("priceDesc");
        if(!StringUtil.isBlank(priceDesc)){
            params.put("priceDesc",priceDesc);
        }
        String priceDescLike = request.getParameter("priceDescLike");
        if(!StringUtil.isBlank(priceDescLike)){
            params.put("priceDescLike",priceDescLike);
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
        String comments = request.getParameter("comments");
        if(!StringUtil.isBlank(comments)){
            params.put("comments",comments);
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            params.put("score",score);
        }
        String link = request.getParameter("link");
        if(!StringUtil.isBlank(link)){
            params.put("link",link);
        }
        String linkLike = request.getParameter("linkLike");
        if(!StringUtil.isBlank(linkLike)){
            params.put("linkLike",linkLike);
        }
        String up = request.getParameter("up");
        if(!StringUtil.isBlank(up)){
            params.put("up",up);
        }
        String down = request.getParameter("down");
        if(!StringUtil.isBlank(down)){
            params.put("down",down);
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
        List<Goods> goodss = goodsService.listByParams4Page(params);
        return ResultUtil.getResult(goodss, page);
    }

    /**
     * 说明:ajax请求Goods信息 无分页版本
     * @return ResultDTO 返回结果
     * @author dozen.zhang
     * @date 2018-12-2 16:05:28
     */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
        HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String shopId = request.getParameter("shopId");
        if(!StringUtil.isBlank(shopId)){
            params.put("shopId",shopId);
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String subName = request.getParameter("subName");
        if(!StringUtil.isBlank(subName)){
            params.put("subName",subName);
        }
        String subNameLike = request.getParameter("subNameLike");
        if(!StringUtil.isBlank(subNameLike)){
            params.put("subNameLike",subNameLike);
        }
        String detail = request.getParameter("detail");
        if(!StringUtil.isBlank(detail)){
            params.put("detail",detail);
        }
        String detailLike = request.getParameter("detailLike");
        if(!StringUtil.isBlank(detailLike)){
            params.put("detailLike",detailLike);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = request.getParameter("addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            params.put("telno",telno);
        }
        String telnoLike = request.getParameter("telnoLike");
        if(!StringUtil.isBlank(telnoLike)){
            params.put("telnoLike",telnoLike);
        }
        String img = request.getParameter("img");
        if(!StringUtil.isBlank(img)){
            params.put("img",img);
        }
        String imgLike = request.getParameter("imgLike");
        if(!StringUtil.isBlank(imgLike)){
            params.put("imgLike",imgLike);
        }
        String img1 = request.getParameter("img1");
        if(!StringUtil.isBlank(img1)){
            params.put("img1",img1);
        }
        String img1Like = request.getParameter("img1Like");
        if(!StringUtil.isBlank(img1Like)){
            params.put("img1Like",img1Like);
        }
        String img2 = request.getParameter("img2");
        if(!StringUtil.isBlank(img2)){
            params.put("img2",img2);
        }
        String img2Like = request.getParameter("img2Like");
        if(!StringUtil.isBlank(img2Like)){
            params.put("img2Like",img2Like);
        }
        String img3 = request.getParameter("img3");
        if(!StringUtil.isBlank(img3)){
            params.put("img3",img3);
        }
        String img3Like = request.getParameter("img3Like");
        if(!StringUtil.isBlank(img3Like)){
            params.put("img3Like",img3Like);
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
        String priceDesc = request.getParameter("priceDesc");
        if(!StringUtil.isBlank(priceDesc)){
            params.put("priceDesc",priceDesc);
        }
        String priceDescLike = request.getParameter("priceDescLike");
        if(!StringUtil.isBlank(priceDescLike)){
            params.put("priceDescLike",priceDescLike);
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
        String comments = request.getParameter("comments");
        if(!StringUtil.isBlank(comments)){
            params.put("comments",comments);
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            params.put("score",score);
        }
        String link = request.getParameter("link");
        if(!StringUtil.isBlank(link)){
            params.put("link",link);
        }
        String linkLike = request.getParameter("linkLike");
        if(!StringUtil.isBlank(linkLike)){
            params.put("linkLike",linkLike);
        }
        String up = request.getParameter("up");
        if(!StringUtil.isBlank(up)){
            params.put("up",up);
        }
        String down = request.getParameter("down");
        if(!StringUtil.isBlank(down)){
            params.put("down",down);
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

        List<Goods> goodss = goodsService.listByParams(params);
        return ResultUtil.getDataResult(goodss);
    }

    /**
     * 说明:查看单条信息
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-12-2 16:05:28
     */
    @API( summary="根据id查询单个商品信息",
            description = "根据id查询单个商品信息",
            parameters={
                    @Param(name="id" , description="id",in=InType.path,dataType= DataType.LONG,required = true),
            })
    @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(@PathVariable Long id,HttpServletRequest request) {
        HashMap<String,Object> result =new HashMap</*String,Object*/>();
        if(id > 0){
            Goods bean = goodsService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

    }



    /**
     * 说明:查看单条信息
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-12-2 16:05:28
     */
    @API( summary="根据id查询单个商品信息",
            description = "根据id查询单个商品信息",
            parameters={
                    @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
            })
    @RequestMapping(value = "/view.json",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(HttpServletRequest request) {
        String id = request.getParameter("id");
        Goods bean = goodsService.selectByPrimaryKey(Long.valueOf(id));
        //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
        // result.put("bean", bean);
        return this.getResult(bean);
    }


    /**
     * 说明:更新Goods信息
     *
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-12-2 16:05:28
     */
    @API( summary="更新id更新单个商品信息",
            description = "更新id更新单个商品信息",
            parameters={
                    @Param(name="id" , description="编号",type="LONG",required = false),
                    @Param(name="shopId" , description="商户id",type="LONG",required = false),
                    @Param(name="name" , description="名称",type="STRING",required = true),
                    @Param(name="subName" , description="副标题",type="STRING",required = true),
                    @Param(name="detail" , description="副标题",type="STRING",required = true),
                    @Param(name="address" , description="地址",type="STRING",required = false),
                    @Param(name="telno" , description="手机号码",type="STRING",required = false),
                    @Param(name="img" , description="图片",type="STRING",required = true),
                    @Param(name="img1" , description="图片",type="STRING",required = false),
                    @Param(name="img2" , description="图片",type="STRING",required = false),
                    @Param(name="img3" , description="图片",type="STRING",required = false),
                    @Param(name="remark" , description="备注",type="STRING",required = false),
                    @Param(name="status" , description="状态",type="INTEGER",required = true),
                    @Param(name="price" , description="价格",type="INTEGER",required = false),
                    @Param(name="priceDesc" , description="价格描述",type="STRING",required = false),
                    @Param(name="creator" , description="创建人id",type="LONG",required = false),
                    @Param(name="creatorName" , description="创建人姓名",type="STRING",required = false),
                    @Param(name="platform" , description="平台名称",type="STRING",required = false),
                    @Param(name="comments" , description="评论数",type="INTEGER",required = false),
                    @Param(name="score" , description="分数",type="FLOAT",required = false),
                    @Param(name="link" , description="外链",type="STRING",required = false),
                    @Param(name="up" , description="顶",type="INTEGER",required = false),
                    @Param(name="down" , description="踩",type="INTEGER",required = false),
                    @Param(name="createTime" , description="创建时间",type="DATE_TIME",required = false),
                    @Param(name="updateTime" , description="更新时间",type="DATE_TIME",required = false),
            })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update.json",method = RequestMethod.PUT)///{id}
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Long id,
        Goods goods =new  Goods();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            goods.setId(Long.valueOf(id)) ;
        }

        String shopId = request.getParameter("shopId");
        if(!StringUtil.isBlank(shopId)){
            goods.setShopId(Long.valueOf(shopId)) ;
        }

        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            goods.setName(String.valueOf(name)) ;
        }

        String subName = request.getParameter("subName");
        if(!StringUtil.isBlank(subName)){
            goods.setSubName(String.valueOf(subName)) ;
        }

        String detail = request.getParameter("detail");
        if(!StringUtil.isBlank(detail)){
            goods.setDetail(String.valueOf(detail)) ;
        }

        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            goods.setAddress(String.valueOf(address)) ;
        }

        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            goods.setTelno(String.valueOf(telno)) ;
        }

        String img = request.getParameter("img");
        if(!StringUtil.isBlank(img)){
            goods.setImg(String.valueOf(img)) ;
        }

        String img1 = request.getParameter("img1");
        if(!StringUtil.isBlank(img1)){
            goods.setImg1(String.valueOf(img1)) ;
        }

        String img2 = request.getParameter("img2");
        if(!StringUtil.isBlank(img2)){
            goods.setImg2(String.valueOf(img2)) ;
        }

        String img3 = request.getParameter("img3");
        if(!StringUtil.isBlank(img3)){
            goods.setImg3(String.valueOf(img3)) ;
        }

        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            goods.setRemark(String.valueOf(remark)) ;
        }

        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            goods.setStatus(Integer.valueOf(status)) ;
        }

        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            goods.setPrice(Integer.valueOf(price)) ;
        }

        String priceDesc = request.getParameter("priceDesc");
        if(!StringUtil.isBlank(priceDesc)){
            goods.setPriceDesc(String.valueOf(priceDesc)) ;
        }

        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            goods.setCreator(Long.valueOf(creator)) ;
        }

        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            goods.setCreatorName(String.valueOf(creatorName)) ;
        }

        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            goods.setPlatform(String.valueOf(platform)) ;
        }

        String comments = request.getParameter("comments");
        if(!StringUtil.isBlank(comments)){
            goods.setComments(Integer.valueOf(comments)) ;
        }

        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            goods.setScore(Float.valueOf(score)) ;
        }

        String link = request.getParameter("link");
        if(!StringUtil.isBlank(link)){
            goods.setLink(String.valueOf(link)) ;
        }

        String up = request.getParameter("up");
        if(!StringUtil.isBlank(up)){
            goods.setUp(Integer.valueOf(up)) ;
        }

        String down = request.getParameter("down");
        if(!StringUtil.isBlank(down)){
            goods.setDown(Integer.valueOf(down)) ;
        }

        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            goods.setCreateTime(Timestamp.valueOf(createTime)) ;
        }

        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            goods.setUpdateTime(Timestamp.valueOf(updateTime)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            goods.setId(Long.valueOf(id));
        }
        String shopId = request.getParameter("shopId");
        if(!StringUtil.isBlank(shopId)){
            goods.setShopId(Long.valueOf(shopId));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            goods.setName(name);
        }
        String subName = request.getParameter("subName");
        if(!StringUtil.isBlank(subName)){
            goods.setSubName(subName);
        }
        String detail = request.getParameter("detail");
        if(!StringUtil.isBlank(detail)){
            goods.setDetail(detail);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            goods.setAddress(address);
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            goods.setTelno(telno);
        }
        String img = request.getParameter("img");
        if(!StringUtil.isBlank(img)){
            goods.setImg(img);
        }
        String img1 = request.getParameter("img1");
        if(!StringUtil.isBlank(img1)){
            goods.setImg1(img1);
        }
        String img2 = request.getParameter("img2");
        if(!StringUtil.isBlank(img2)){
            goods.setImg2(img2);
        }
        String img3 = request.getParameter("img3");
        if(!StringUtil.isBlank(img3)){
            goods.setImg3(img3);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            goods.setRemark(remark);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            goods.setStatus(Integer.valueOf(status));
        }
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            goods.setPrice(Integer.valueOf(price));
        }
        String priceDesc = request.getParameter("priceDesc");
        if(!StringUtil.isBlank(priceDesc)){
            goods.setPriceDesc(priceDesc);
        }
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            goods.setCreator(Long.valueOf(creator));
        }
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            goods.setCreatorName(creatorName);
        }
        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            goods.setPlatform(platform);
        }
        String comments = request.getParameter("comments");
        if(!StringUtil.isBlank(comments)){
            goods.setComments(Integer.valueOf(comments));
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            goods.setScore(Float.valueOf(score));
        }
        String link = request.getParameter("link");
        if(!StringUtil.isBlank(link)){
            goods.setLink(link);
        }
        String up = request.getParameter("up");
        if(!StringUtil.isBlank(up)){
            goods.setUp(Integer.valueOf(up));
        }
        String down = request.getParameter("down");
        if(!StringUtil.isBlank(down)){
            goods.setDown(Integer.valueOf(down));
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                goods.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                goods.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                goods.setUpdateTime(Timestamp.valueOf(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                goods.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(9,0)});
        vu.add("shopId", shopId, "商户id",  new Rule[]{new Digits(9,0)});
        vu.add("name", name, "名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("subName", subName, "副标题",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("detail", detail, "副标题",  new Rule[]{new Length(500),new NotEmpty()});
        vu.add("address", address, "地址",  new Rule[]{new Length(200)});
        vu.add("telno", telno, "手机号码",  new Rule[]{new Length(11),new PhoneRule()});
        vu.add("img", img, "图片",  new Rule[]{new Length(100),new NotEmpty()});
        vu.add("img1", img1, "图片",  new Rule[]{new Length(100)});
        vu.add("img2", img2, "图片",  new Rule[]{new Length(100)});
        vu.add("img3", img3, "图片",  new Rule[]{new Length(100)});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2","3","9"}),new NotEmpty()});
        vu.add("price", price, "价格",  new Rule[]{new Digits(11,0)});
        vu.add("priceDesc", priceDesc, "价格描述",  new Rule[]{new Length(50)});
        vu.add("creator", creator, "创建人id",  new Rule[]{new Digits(11,0)});
        vu.add("creatorName", creatorName, "创建人姓名",  new Rule[]{new Length(50)});
        vu.add("platform", platform, "平台名称",  new Rule[]{new Length(50)});
        vu.add("comments", comments, "评论数",  new Rule[]{new Digits(11,0)});
        vu.add("score", score, "分数",  new Rule[]{new Digits(6,3)});
        vu.add("link", link, "外链",  new Rule[]{new Length(100)});
        vu.add("up", up, "顶",  new Rule[]{new Digits(11,0)});
        vu.add("down", down, "踩",  new Rule[]{new Digits(11,0)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return goodsService.save(goods);

    }


    /**
     * 说明:添加Goods信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-12-2 16:05:28
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @API( summary="添加单个商品信息",
            description = "添加单个商品信息",
            parameters={
                    @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
                    @Param(name="shopId" , description="商户id",dataType = DataType.LONG,required = false),
                    @Param(name="name" , description="名称",dataType = DataType.STRING,required = true),
                    @Param(name="subName" , description="副标题",dataType = DataType.STRING,required = true),
                    @Param(name="detail" , description="副标题",dataType = DataType.STRING,required = true),
                    @Param(name="address" , description="地址",dataType = DataType.STRING,required = false),
                    @Param(name="telno" , description="手机号码",dataType = DataType.STRING,required = false),
                    @Param(name="img" , description="图片",dataType = DataType.STRING,required = true),
                    @Param(name="img1" , description="图片",dataType = DataType.STRING,required = false),
                    @Param(name="img2" , description="图片",dataType = DataType.STRING,required = false),
                    @Param(name="img3" , description="图片",dataType = DataType.STRING,required = false),
                    @Param(name="remark" , description="备注",dataType = DataType.STRING,required = false),
                    @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = true),
                    @Param(name="price" , description="价格",dataType = DataType.INTEGER,required = false),
                    @Param(name="priceDesc" , description="价格描述",dataType = DataType.STRING,required = false),
                    @Param(name="creator" , description="创建人id",dataType = DataType.LONG,required = false),
                    @Param(name="creatorName" , description="创建人姓名",dataType = DataType.STRING,required = false),
                    @Param(name="platform" , description="平台名称",dataType = DataType.STRING,required = false),
                    @Param(name="comments" , description="评论数",dataType = DataType.INTEGER,required = false),
                    @Param(name="score" , description="分数",dataType = DataType.FLOAT,required = false),
                    @Param(name="link" , description="外链",dataType = DataType.STRING,required = false),
                    @Param(name="up" , description="顶",dataType = DataType.INTEGER,required = false),
                    @Param(name="down" , description="踩",dataType = DataType.INTEGER,required = false),
                    @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
                    @Param(name="updateTime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
            })
    @RequestMapping(value = "add.json",method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO add(HttpServletRequest request) throws Exception {
        Goods goods =new  Goods();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                goods.setId(Long.valueOf(id)) ;
            }

            String shopId = request.getParameter("shopId");
            if(!StringUtil.isBlank(shopId)){
                goods.setShopId(Long.valueOf(shopId)) ;
            }

            String name = request.getParameter("name");
            if(!StringUtil.isBlank(name)){
                goods.setName(String.valueOf(name)) ;
            }

            String subName = request.getParameter("subName");
            if(!StringUtil.isBlank(subName)){
                goods.setSubName(String.valueOf(subName)) ;
            }

            String detail = request.getParameter("detail");
            if(!StringUtil.isBlank(detail)){
                goods.setDetail(String.valueOf(detail)) ;
            }

            String address = request.getParameter("address");
            if(!StringUtil.isBlank(address)){
                goods.setAddress(String.valueOf(address)) ;
            }

            String telno = request.getParameter("telno");
            if(!StringUtil.isBlank(telno)){
                goods.setTelno(String.valueOf(telno)) ;
            }

            String img = request.getParameter("img");
            if(!StringUtil.isBlank(img)){
                goods.setImg(String.valueOf(img)) ;
            }

            String img1 = request.getParameter("img1");
            if(!StringUtil.isBlank(img1)){
                goods.setImg1(String.valueOf(img1)) ;
            }

            String img2 = request.getParameter("img2");
            if(!StringUtil.isBlank(img2)){
                goods.setImg2(String.valueOf(img2)) ;
            }

            String img3 = request.getParameter("img3");
            if(!StringUtil.isBlank(img3)){
                goods.setImg3(String.valueOf(img3)) ;
            }

            String remark = request.getParameter("remark");
            if(!StringUtil.isBlank(remark)){
                goods.setRemark(String.valueOf(remark)) ;
            }

            String status = request.getParameter("status");
            if(!StringUtil.isBlank(status)){
                goods.setStatus(Integer.valueOf(status)) ;
            }

            String price = request.getParameter("price");
            if(!StringUtil.isBlank(price)){
                goods.setPrice(Integer.valueOf(price)) ;
            }

            String priceDesc = request.getParameter("priceDesc");
            if(!StringUtil.isBlank(priceDesc)){
                goods.setPriceDesc(String.valueOf(priceDesc)) ;
            }

            String creator = request.getParameter("creator");
            if(!StringUtil.isBlank(creator)){
                goods.setCreator(Long.valueOf(creator)) ;
            }

            String creatorName = request.getParameter("creatorName");
            if(!StringUtil.isBlank(creatorName)){
                goods.setCreatorName(String.valueOf(creatorName)) ;
            }

            String platform = request.getParameter("platform");
            if(!StringUtil.isBlank(platform)){
                goods.setPlatform(String.valueOf(platform)) ;
            }

            String comments = request.getParameter("comments");
            if(!StringUtil.isBlank(comments)){
                goods.setComments(Integer.valueOf(comments)) ;
            }

            String score = request.getParameter("score");
            if(!StringUtil.isBlank(score)){
                goods.setScore(Float.valueOf(score)) ;
            }

            String link = request.getParameter("link");
            if(!StringUtil.isBlank(link)){
                goods.setLink(String.valueOf(link)) ;
            }

            String up = request.getParameter("up");
            if(!StringUtil.isBlank(up)){
                goods.setUp(Integer.valueOf(up)) ;
            }

            String down = request.getParameter("down");
            if(!StringUtil.isBlank(down)){
                goods.setDown(Integer.valueOf(down)) ;
            }

            String createTime = request.getParameter("createTime");
            if(!StringUtil.isBlank(createTime)){
                goods.setCreateTime(Timestamp.valueOf(createTime)) ;
            }

            String updateTime = request.getParameter("updateTime");
            if(!StringUtil.isBlank(updateTime)){
                goods.setUpdateTime(Timestamp.valueOf(updateTime)) ;
            }
            */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            goods.setId(Long.valueOf(id));
        }
        String shopId = request.getParameter("shopId");
        if(!StringUtil.isBlank(shopId)){
            goods.setShopId(Long.valueOf(shopId));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            goods.setName(name);
        }
        String subName = request.getParameter("subName");
        if(!StringUtil.isBlank(subName)){
            goods.setSubName(subName);
        }
        String detail = request.getParameter("detail");
        if(!StringUtil.isBlank(detail)){
            goods.setDetail(detail);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            goods.setAddress(address);
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            goods.setTelno(telno);
        }
        String img = request.getParameter("img");
        if(!StringUtil.isBlank(img)){
            goods.setImg(img);
        }
        String img1 = request.getParameter("img1");
        if(!StringUtil.isBlank(img1)){
            goods.setImg1(img1);
        }
        String img2 = request.getParameter("img2");
        if(!StringUtil.isBlank(img2)){
            goods.setImg2(img2);
        }
        String img3 = request.getParameter("img3");
        if(!StringUtil.isBlank(img3)){
            goods.setImg3(img3);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            goods.setRemark(remark);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            goods.setStatus(Integer.valueOf(status));
        }
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            goods.setPrice(Integer.valueOf(price));
        }
        String priceDesc = request.getParameter("priceDesc");
        if(!StringUtil.isBlank(priceDesc)){
            goods.setPriceDesc(priceDesc);
        }
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            goods.setCreator(Long.valueOf(creator));
        }
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            goods.setCreatorName(creatorName);
        }
        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            goods.setPlatform(platform);
        }
        String comments = request.getParameter("comments");
        if(!StringUtil.isBlank(comments)){
            goods.setComments(Integer.valueOf(comments));
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            goods.setScore(Float.valueOf(score));
        }
        String link = request.getParameter("link");
        if(!StringUtil.isBlank(link)){
            goods.setLink(link);
        }
        String up = request.getParameter("up");
        if(!StringUtil.isBlank(up)){
            goods.setUp(Integer.valueOf(up));
        }
        String down = request.getParameter("down");
        if(!StringUtil.isBlank(down)){
            goods.setDown(Integer.valueOf(down));
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                goods.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                goods.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                goods.setUpdateTime(Timestamp.valueOf(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                goods.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(9,0)});
        vu.add("shopId", shopId, "商户id",  new Rule[]{new Digits(9,0)});
        vu.add("name", name, "名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("subName", subName, "副标题",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("detail", detail, "副标题",  new Rule[]{new Length(500),new NotEmpty()});
        vu.add("address", address, "地址",  new Rule[]{new Length(200)});
        vu.add("telno", telno, "手机号码",  new Rule[]{new Length(11),new PhoneRule()});
        vu.add("img", img, "图片",  new Rule[]{new Length(100),new NotEmpty()});
        vu.add("img1", img1, "图片",  new Rule[]{new Length(100)});
        vu.add("img2", img2, "图片",  new Rule[]{new Length(100)});
        vu.add("img3", img3, "图片",  new Rule[]{new Length(100)});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2","3","9"}),new NotEmpty()});
        vu.add("price", price, "价格",  new Rule[]{new Digits(11,0)});
        vu.add("priceDesc", priceDesc, "价格描述",  new Rule[]{new Length(50)});
        vu.add("creator", creator, "创建人id",  new Rule[]{new Digits(11,0)});
        vu.add("creatorName", creatorName, "创建人姓名",  new Rule[]{new Length(50)});
        vu.add("platform", platform, "平台名称",  new Rule[]{new Length(50)});
        vu.add("comments", comments, "评论数",  new Rule[]{new Digits(11,0)});
        vu.add("score", score, "分数",  new Rule[]{new Digits(6,3)});
        vu.add("link", link, "外链",  new Rule[]{new Length(100)});
        vu.add("up", up, "顶",  new Rule[]{new Digits(11,0)});
        vu.add("down", down, "踩",  new Rule[]{new Digits(11,0)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return goodsService.save(goods);

    }


    /**
     * 说明:添加Goods信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-12-2 16:05:28
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="添加单个商品信息",
            description = "添加单个商品信息",
            parameters={
                    @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
                    @Param(name="shopId" , description="商户id",dataType = DataType.LONG,required = false),
                    @Param(name="name" , description="名称",dataType = DataType.STRING,required = true),
                    @Param(name="subName" , description="副标题",dataType = DataType.STRING,required = true),
                    @Param(name="detail" , description="副标题",dataType = DataType.STRING,required = true),
                    @Param(name="address" , description="地址",dataType = DataType.STRING,required = false),
                    @Param(name="telno" , description="手机号码",dataType = DataType.STRING,required = false),
                    @Param(name="img" , description="图片",dataType = DataType.STRING,required = true),
                    @Param(name="img1" , description="图片",dataType = DataType.STRING,required = false),
                    @Param(name="img2" , description="图片",dataType = DataType.STRING,required = false),
                    @Param(name="img3" , description="图片",dataType = DataType.STRING,required = false),
                    @Param(name="remark" , description="备注",dataType = DataType.STRING,required = false),
                    @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = true),
                    @Param(name="price" , description="价格",dataType = DataType.INTEGER,required = false),
                    @Param(name="priceDesc" , description="价格描述",dataType = DataType.STRING,required = false),
                    @Param(name="creator" , description="创建人id",dataType = DataType.LONG,required = false),
                    @Param(name="creatorName" , description="创建人姓名",dataType = DataType.STRING,required = false),
                    @Param(name="platform" , description="平台名称",dataType = DataType.STRING,required = false),
                    @Param(name="comments" , description="评论数",dataType = DataType.INTEGER,required = false),
                    @Param(name="score" , description="分数",dataType = DataType.FLOAT,required = false),
                    @Param(name="link" , description="外链",dataType = DataType.STRING,required = false),
                    @Param(name="up" , description="顶",dataType = DataType.INTEGER,required = false),
                    @Param(name="down" , description="踩",dataType = DataType.INTEGER,required = false),
                    @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
                    @Param(name="updateTime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
            })
    @RequestMapping(value = "save.json",method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO save(HttpServletRequest request) throws Exception {
        Goods goods =new  Goods();
                    /*
                    String id = request.getParameter("id");
                    if(!StringUtil.isBlank(id)){
                        goods.setId(Long.valueOf(id)) ;
                    }

                    String shopId = request.getParameter("shopId");
                    if(!StringUtil.isBlank(shopId)){
                        goods.setShopId(Long.valueOf(shopId)) ;
                    }

                    String name = request.getParameter("name");
                    if(!StringUtil.isBlank(name)){
                        goods.setName(String.valueOf(name)) ;
                    }

                    String subName = request.getParameter("subName");
                    if(!StringUtil.isBlank(subName)){
                        goods.setSubName(String.valueOf(subName)) ;
                    }

                    String detail = request.getParameter("detail");
                    if(!StringUtil.isBlank(detail)){
                        goods.setDetail(String.valueOf(detail)) ;
                    }

                    String address = request.getParameter("address");
                    if(!StringUtil.isBlank(address)){
                        goods.setAddress(String.valueOf(address)) ;
                    }

                    String telno = request.getParameter("telno");
                    if(!StringUtil.isBlank(telno)){
                        goods.setTelno(String.valueOf(telno)) ;
                    }

                    String img = request.getParameter("img");
                    if(!StringUtil.isBlank(img)){
                        goods.setImg(String.valueOf(img)) ;
                    }

                    String img1 = request.getParameter("img1");
                    if(!StringUtil.isBlank(img1)){
                        goods.setImg1(String.valueOf(img1)) ;
                    }

                    String img2 = request.getParameter("img2");
                    if(!StringUtil.isBlank(img2)){
                        goods.setImg2(String.valueOf(img2)) ;
                    }

                    String img3 = request.getParameter("img3");
                    if(!StringUtil.isBlank(img3)){
                        goods.setImg3(String.valueOf(img3)) ;
                    }

                    String remark = request.getParameter("remark");
                    if(!StringUtil.isBlank(remark)){
                        goods.setRemark(String.valueOf(remark)) ;
                    }

                    String status = request.getParameter("status");
                    if(!StringUtil.isBlank(status)){
                        goods.setStatus(Integer.valueOf(status)) ;
                    }

                    String price = request.getParameter("price");
                    if(!StringUtil.isBlank(price)){
                        goods.setPrice(Integer.valueOf(price)) ;
                    }

                    String priceDesc = request.getParameter("priceDesc");
                    if(!StringUtil.isBlank(priceDesc)){
                        goods.setPriceDesc(String.valueOf(priceDesc)) ;
                    }

                    String creator = request.getParameter("creator");
                    if(!StringUtil.isBlank(creator)){
                        goods.setCreator(Long.valueOf(creator)) ;
                    }

                    String creatorName = request.getParameter("creatorName");
                    if(!StringUtil.isBlank(creatorName)){
                        goods.setCreatorName(String.valueOf(creatorName)) ;
                    }

                    String platform = request.getParameter("platform");
                    if(!StringUtil.isBlank(platform)){
                        goods.setPlatform(String.valueOf(platform)) ;
                    }

                    String comments = request.getParameter("comments");
                    if(!StringUtil.isBlank(comments)){
                        goods.setComments(Integer.valueOf(comments)) ;
                    }

                    String score = request.getParameter("score");
                    if(!StringUtil.isBlank(score)){
                        goods.setScore(Float.valueOf(score)) ;
                    }

                    String link = request.getParameter("link");
                    if(!StringUtil.isBlank(link)){
                        goods.setLink(String.valueOf(link)) ;
                    }

                    String up = request.getParameter("up");
                    if(!StringUtil.isBlank(up)){
                        goods.setUp(Integer.valueOf(up)) ;
                    }

                    String down = request.getParameter("down");
                    if(!StringUtil.isBlank(down)){
                        goods.setDown(Integer.valueOf(down)) ;
                    }

                    String createTime = request.getParameter("createTime");
                    if(!StringUtil.isBlank(createTime)){
                        goods.setCreateTime(Timestamp.valueOf(createTime)) ;
                    }

                    String updateTime = request.getParameter("updateTime");
                    if(!StringUtil.isBlank(updateTime)){
                        goods.setUpdateTime(Timestamp.valueOf(updateTime)) ;
                    }
                    */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            goods.setId(Long.valueOf(id));
        }
        String shopId = request.getParameter("shopId");
        if(!StringUtil.isBlank(shopId)){
            goods.setShopId(Long.valueOf(shopId));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            goods.setName(name);
        }
        String subName = request.getParameter("subName");
        if(!StringUtil.isBlank(subName)){
            goods.setSubName(subName);
        }
        String detail = request.getParameter("detail");
        if(!StringUtil.isBlank(detail)){
            goods.setDetail(detail);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            goods.setAddress(address);
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            goods.setTelno(telno);
        }
        String img = request.getParameter("img");
        if(!StringUtil.isBlank(img)){
            goods.setImg(img);
        }
        String img1 = request.getParameter("img1");
        if(!StringUtil.isBlank(img1)){
            goods.setImg1(img1);
        }
        String img2 = request.getParameter("img2");
        if(!StringUtil.isBlank(img2)){
            goods.setImg2(img2);
        }
        String img3 = request.getParameter("img3");
        if(!StringUtil.isBlank(img3)){
            goods.setImg3(img3);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            goods.setRemark(remark);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            goods.setStatus(Integer.valueOf(status));
        }
        String price = request.getParameter("price");
        if(!StringUtil.isBlank(price)){
            goods.setPrice(Integer.valueOf(price));
        }
        String priceDesc = request.getParameter("priceDesc");
        if(!StringUtil.isBlank(priceDesc)){
            goods.setPriceDesc(priceDesc);
        }
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            goods.setCreator(Long.valueOf(creator));
        }
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            goods.setCreatorName(creatorName);
        }
        String platform = request.getParameter("platform");
        if(!StringUtil.isBlank(platform)){
            goods.setPlatform(platform);
        }
        String comments = request.getParameter("comments");
        if(!StringUtil.isBlank(comments)){
            goods.setComments(Integer.valueOf(comments));
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            goods.setScore(Float.valueOf(score));
        }
        String link = request.getParameter("link");
        if(!StringUtil.isBlank(link)){
            goods.setLink(link);
        }
        String up = request.getParameter("up");
        if(!StringUtil.isBlank(up)){
            goods.setUp(Integer.valueOf(up));
        }
        String down = request.getParameter("down");
        if(!StringUtil.isBlank(down)){
            goods.setDown(Integer.valueOf(down));
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                goods.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                goods.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = request.getParameter("updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                goods.setUpdateTime(Timestamp.valueOf(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                goods.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(9,0)});
        vu.add("shopId", shopId, "商户id",  new Rule[]{new Digits(9,0)});
        vu.add("name", name, "名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("subName", subName, "副标题",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("detail", detail, "副标题",  new Rule[]{new Length(500),new NotEmpty()});
        vu.add("address", address, "地址",  new Rule[]{new Length(200)});
        vu.add("telno", telno, "手机号码",  new Rule[]{new Length(11),new PhoneRule()});
        vu.add("img", img, "图片",  new Rule[]{new Length(100),new NotEmpty()});
        vu.add("img1", img1, "图片",  new Rule[]{new Length(100)});
        vu.add("img2", img2, "图片",  new Rule[]{new Length(100)});
        vu.add("img3", img3, "图片",  new Rule[]{new Length(100)});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2","3","9"}),new NotEmpty()});
        vu.add("price", price, "价格",  new Rule[]{new Digits(11,0)});
        vu.add("priceDesc", priceDesc, "价格描述",  new Rule[]{new Length(50)});
        vu.add("creator", creator, "创建人id",  new Rule[]{new Digits(11,0)});
        vu.add("creatorName", creatorName, "创建人姓名",  new Rule[]{new Length(50)});
        vu.add("platform", platform, "平台名称",  new Rule[]{new Length(50)});
        vu.add("comments", comments, "评论数",  new Rule[]{new Digits(11,0)});
        vu.add("score", score, "分数",  new Rule[]{new Digits(6,3)});
        vu.add("link", link, "外链",  new Rule[]{new Length(100)});
        vu.add("up", up, "顶",  new Rule[]{new Digits(11,0)});
        vu.add("down", down, "踩",  new Rule[]{new Digits(11,0)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }
        String[] imgAry = request.getParameter("imageUrls").split(",");//new String[]{img,img1,img2,img3};
        goodsService.save(goods);


        return pubImageBelongService.save(goods.getId(),imgAry);

    }

    @Resource
    private PubImageBelongService pubImageBelongService;

    /**
     * 说明:删除Goods信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-12-2 16:05:28
     */
    @API( summary="根据id删除单个商品信息",
            description = "根据id删除单个商品信息",
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
        goodsService.delete(id);
        return this.getResult(SUCC);
    }

    /**
     * 说明:删除Goods信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-12-2 16:05:28
     */
    @API( summary="根据id删除单个商品信息",
            description = "根据id删除单个商品信息",
            parameters={
                    @Param(name="id" , description="编号",in=InType.path,dataType= DataType.LONG,required = true),
            })
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
    @ResponseBody
    public ResultDTO delete(@PathVariable Long id,HttpServletRequest request) {
        goodsService.delete(id);
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
        return  goodsService.multilDelete(idAry);
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
        String shopId = request.getParameter("shopId");
        if(!StringUtil.isBlank(shopId)){
            params.put("shopId",shopId);
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String subName = request.getParameter("subName");
        if(!StringUtil.isBlank(subName)){
            params.put("subName",subName);
        }
        String subNameLike = request.getParameter("subNameLike");
        if(!StringUtil.isBlank(subNameLike)){
            params.put("subNameLike",subNameLike);
        }
        String detail = request.getParameter("detail");
        if(!StringUtil.isBlank(detail)){
            params.put("detail",detail);
        }
        String detailLike = request.getParameter("detailLike");
        if(!StringUtil.isBlank(detailLike)){
            params.put("detailLike",detailLike);
        }
        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = request.getParameter("addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            params.put("telno",telno);
        }
        String telnoLike = request.getParameter("telnoLike");
        if(!StringUtil.isBlank(telnoLike)){
            params.put("telnoLike",telnoLike);
        }
        String img = request.getParameter("img");
        if(!StringUtil.isBlank(img)){
            params.put("img",img);
        }
        String imgLike = request.getParameter("imgLike");
        if(!StringUtil.isBlank(imgLike)){
            params.put("imgLike",imgLike);
        }
        String img1 = request.getParameter("img1");
        if(!StringUtil.isBlank(img1)){
            params.put("img1",img1);
        }
        String img1Like = request.getParameter("img1Like");
        if(!StringUtil.isBlank(img1Like)){
            params.put("img1Like",img1Like);
        }
        String img2 = request.getParameter("img2");
        if(!StringUtil.isBlank(img2)){
            params.put("img2",img2);
        }
        String img2Like = request.getParameter("img2Like");
        if(!StringUtil.isBlank(img2Like)){
            params.put("img2Like",img2Like);
        }
        String img3 = request.getParameter("img3");
        if(!StringUtil.isBlank(img3)){
            params.put("img3",img3);
        }
        String img3Like = request.getParameter("img3Like");
        if(!StringUtil.isBlank(img3Like)){
            params.put("img3Like",img3Like);
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
        String priceDesc = request.getParameter("priceDesc");
        if(!StringUtil.isBlank(priceDesc)){
            params.put("priceDesc",priceDesc);
        }
        String priceDescLike = request.getParameter("priceDescLike");
        if(!StringUtil.isBlank(priceDescLike)){
            params.put("priceDescLike",priceDescLike);
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
        String comments = request.getParameter("comments");
        if(!StringUtil.isBlank(comments)){
            params.put("comments",comments);
        }
        String score = request.getParameter("score");
        if(!StringUtil.isBlank(score)){
            params.put("score",score);
        }
        String link = request.getParameter("link");
        if(!StringUtil.isBlank(link)){
            params.put("link",link);
        }
        String linkLike = request.getParameter("linkLike");
        if(!StringUtil.isBlank(linkLike)){
            params.put("linkLike",linkLike);
        }
        String up = request.getParameter("up");
        if(!StringUtil.isBlank(up)){
            params.put("up",up);
        }
        String down = request.getParameter("down");
        if(!StringUtil.isBlank(down)){
            params.put("down",down);
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
        List<Goods> list =goodsService.listByParams(params);
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
        colTitle.put("shopId", "商户id");
        colTitle.put("name", "名称");
        colTitle.put("subName", "副标题");
        colTitle.put("detail", "副标题");
        colTitle.put("address", "地址");
        colTitle.put("telno", "手机号码");
        colTitle.put("img", "图片");
        colTitle.put("img1", "图片");
        colTitle.put("img2", "图片");
        colTitle.put("img3", "图片");
        colTitle.put("remark", "备注");
        colTitle.put("status", "状态");
        colTitle.put("price", "价格");
        colTitle.put("priceDesc", "价格描述");
        colTitle.put("creator", "创建人id");
        colTitle.put("creatorName", "创建人姓名");
        colTitle.put("platform", "平台名称");
        colTitle.put("comments", "评论数");
        colTitle.put("score", "分数");
        colTitle.put("link", "外链");
        colTitle.put("up", "顶");
        colTitle.put("down", "踩");
        colTitle.put("createTime", "创建时间");
        colTitle.put("updateTime", "更新时间");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            Goods sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("shopId",  list.get(i).getShopId());
            map.put("name",  list.get(i).getName());
            map.put("subName",  list.get(i).getSubName());
            map.put("detail",  list.get(i).getDetail());
            map.put("address",  list.get(i).getAddress());
            map.put("telno",  list.get(i).getTelno());
            map.put("img",  list.get(i).getImg());
            map.put("img1",  list.get(i).getImg1());
            map.put("img2",  list.get(i).getImg2());
            map.put("img3",  list.get(i).getImg3());
            map.put("remark",  list.get(i).getRemark());
            map.put("status",  list.get(i).getStatus());
            map.put("price",  list.get(i).getPrice());
            map.put("priceDesc",  list.get(i).getPriceDesc());
            map.put("creator",  list.get(i).getCreator());
            map.put("creatorName",  list.get(i).getCreatorName());
            map.put("platform",  list.get(i).getPlatform());
            map.put("comments",  list.get(i).getComments());
            map.put("score",  list.get(i).getScore());
            map.put("link",  list.get(i).getLink());
            map.put("up",  list.get(i).getUp());
            map.put("down",  list.get(i).getDown());
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

                Goods bean = getInfoFromMap(params);

                //  if (count > 0) {

                //      logger.warn("邮箱已经存在:" + email);
                //     errorMsg.append("邮箱已经存在:" + email);
                //   continue;

                // }

                try {
                    goodsService.save(bean);
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
     * 说明: 跳转到Goods列表页面
     *
     * @return
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String listHtml() {
        return "/static/html/GoodsList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapperHtml() {
        return "/static/html/GoodsListMapper.html";
    }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-12-2 16:05:28
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/GoodsEdit.html";
    }
    /**
     * 说明:跳转查看页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-12-2 16:05:28
     */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/GoodsView.html";
    }



    private Goods getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
        Goods goods =new  Goods();

        String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
            goods.setId(Long.valueOf(id));
        }
        String shopId = MapUtils.getString(bodyParam,"shopId");
        if(!StringUtil.isBlank(shopId)){
            goods.setShopId(Long.valueOf(shopId));
        }
        String name = MapUtils.getString(bodyParam,"name");
        if(!StringUtil.isBlank(name)){
            goods.setName(String.valueOf(name));
        }
        String subName = MapUtils.getString(bodyParam,"subName");
        if(!StringUtil.isBlank(subName)){
            goods.setSubName(String.valueOf(subName));
        }
        String detail = MapUtils.getString(bodyParam,"detail");
        if(!StringUtil.isBlank(detail)){
            goods.setDetail(String.valueOf(detail));
        }
        String address = MapUtils.getString(bodyParam,"address");
        if(!StringUtil.isBlank(address)){
            goods.setAddress(String.valueOf(address));
        }
        String telno = MapUtils.getString(bodyParam,"telno");
        if(!StringUtil.isBlank(telno)){
            goods.setTelno(String.valueOf(telno));
        }
        String img = MapUtils.getString(bodyParam,"img");
        if(!StringUtil.isBlank(img)){
            goods.setImg(String.valueOf(img));
        }
        String img1 = MapUtils.getString(bodyParam,"img1");
        if(!StringUtil.isBlank(img1)){
            goods.setImg1(String.valueOf(img1));
        }
        String img2 = MapUtils.getString(bodyParam,"img2");
        if(!StringUtil.isBlank(img2)){
            goods.setImg2(String.valueOf(img2));
        }
        String img3 = MapUtils.getString(bodyParam,"img3");
        if(!StringUtil.isBlank(img3)){
            goods.setImg3(String.valueOf(img3));
        }
        String remark = MapUtils.getString(bodyParam,"remark");
        if(!StringUtil.isBlank(remark)){
            goods.setRemark(String.valueOf(remark));
        }
        String status = MapUtils.getString(bodyParam,"status");
        if(!StringUtil.isBlank(status)){
            goods.setStatus(Integer.valueOf(status));
        }
        String price = MapUtils.getString(bodyParam,"price");
        if(!StringUtil.isBlank(price)){
            goods.setPrice(Integer.valueOf(price));
        }
        String priceDesc = MapUtils.getString(bodyParam,"priceDesc");
        if(!StringUtil.isBlank(priceDesc)){
            goods.setPriceDesc(String.valueOf(priceDesc));
        }
        String creator = MapUtils.getString(bodyParam,"creator");
        if(!StringUtil.isBlank(creator)){
            goods.setCreator(Long.valueOf(creator));
        }
        String creatorName = MapUtils.getString(bodyParam,"creatorName");
        if(!StringUtil.isBlank(creatorName)){
            goods.setCreatorName(String.valueOf(creatorName));
        }
        String platform = MapUtils.getString(bodyParam,"platform");
        if(!StringUtil.isBlank(platform)){
            goods.setPlatform(String.valueOf(platform));
        }
        String comments = MapUtils.getString(bodyParam,"comments");
        if(!StringUtil.isBlank(comments)){
            goods.setComments(Integer.valueOf(comments));
        }
        String score = MapUtils.getString(bodyParam,"score");
        if(!StringUtil.isBlank(score)){
            goods.setScore(Float.valueOf(score));
        }
        String link = MapUtils.getString(bodyParam,"link");
        if(!StringUtil.isBlank(link)){
            goods.setLink(String.valueOf(link));
        }
        String up = MapUtils.getString(bodyParam,"up");
        if(!StringUtil.isBlank(up)){
            goods.setUp(Integer.valueOf(up));
        }
        String down = MapUtils.getString(bodyParam,"down");
        if(!StringUtil.isBlank(down)){
            goods.setDown(Integer.valueOf(down));
        }
        String createTime = MapUtils.getString(bodyParam,"createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                goods.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                goods.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = MapUtils.getString(bodyParam,"updateTime");
        if(!StringUtil.isBlank(updateTime)){
            if(StringUtil.checkNumeric(updateTime)){
                goods.setUpdateTime(Timestamp.valueOf(updateTime));
            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
                goods.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(9,0)});
        vu.add("shopId", shopId, "商户id",  new Rule[]{new Digits(9,0)});
        vu.add("name", name, "名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("subName", subName, "副标题",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("detail", detail, "副标题",  new Rule[]{new Length(500),new NotEmpty()});
        vu.add("address", address, "地址",  new Rule[]{new Length(200)});
        vu.add("telno", telno, "手机号码",  new Rule[]{new Length(11),new PhoneRule()});
        vu.add("img", img, "图片",  new Rule[]{new Length(100),new NotEmpty()});
        vu.add("img1", img1, "图片",  new Rule[]{new Length(100)});
        vu.add("img2", img2, "图片",  new Rule[]{new Length(100)});
        vu.add("img3", img3, "图片",  new Rule[]{new Length(100)});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2","3","9"}),new NotEmpty()});
        vu.add("price", price, "价格",  new Rule[]{new Digits(11,0)});
        vu.add("priceDesc", priceDesc, "价格描述",  new Rule[]{new Length(50)});
        vu.add("creator", creator, "创建人id",  new Rule[]{new Digits(11,0)});
        vu.add("creatorName", creatorName, "创建人姓名",  new Rule[]{new Length(50)});
        vu.add("platform", platform, "平台名称",  new Rule[]{new Length(50)});
        vu.add("comments", comments, "评论数",  new Rule[]{new Digits(11,0)});
        vu.add("score", score, "分数",  new Rule[]{new Digits(6,3)});
        vu.add("link", link, "外链",  new Rule[]{new Length(100)});
        vu.add("up", up, "顶",  new Rule[]{new Digits(11,0)});
        vu.add("down", down, "踩",  new Rule[]{new Digits(11,0)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  goods;
    }


    /**
     * 说明:添加Goods信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-12-2 16:05:28
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="添加单个商品信息",
            description = "添加单个商品信息",
            parameters={
                    @Param(name="id" , description="编号",in=InType.body,dataType = DataType.LONG,required = false),
                    @Param(name="shopId" , description="商户id",in=InType.body,dataType = DataType.LONG,required = false),
                    @Param(name="name" , description="名称",in=InType.body,dataType = DataType.STRING,required = true),
                    @Param(name="subName" , description="副标题",in=InType.body,dataType = DataType.STRING,required = true),
                    @Param(name="detail" , description="副标题",in=InType.body,dataType = DataType.STRING,required = true),
                    @Param(name="address" , description="地址",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="telno" , description="手机号码",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="img" , description="图片",in=InType.body,dataType = DataType.STRING,required = true),
                    @Param(name="img1" , description="图片",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="img2" , description="图片",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="img3" , description="图片",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="remark" , description="备注",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="status" , description="状态",in=InType.body,dataType = DataType.INTEGER,required = true),
                    @Param(name="price" , description="价格",in=InType.body,dataType = DataType.INTEGER,required = false),
                    @Param(name="priceDesc" , description="价格描述",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="creator" , description="创建人id",in=InType.body,dataType = DataType.LONG,required = false),
                    @Param(name="creatorName" , description="创建人姓名",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="platform" , description="平台名称",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="comments" , description="评论数",in=InType.body,dataType = DataType.INTEGER,required = false),
                    @Param(name="score" , description="分数",in=InType.body,dataType = DataType.FLOAT,required = false),
                    @Param(name="link" , description="外链",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="up" , description="顶",in=InType.body,dataType = DataType.INTEGER,required = false),
                    @Param(name="down" , description="踩",in=InType.body,dataType = DataType.INTEGER,required = false),
                    @Param(name="createTime" , description="创建时间",in=InType.body,dataType = DataType.DATE_TIME,required = false),
                    @Param(name="updateTime" , description="更新时间",in=InType.body,dataType = DataType.DATE_TIME,required = false),
            })
    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        Goods goods =    getInfoFromMap(bodyParam);


        return goodsService.save(goods);

    }


    /**
     * 说明:添加Goods信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-12-2 16:05:28
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个商品信息",
            description = "更新单个商品信息",
            parameters={
                    @Param(name="id" , description="编号",in=InType.body,dataType = DataType.LONG,required = false),
                    @Param(name="shopId" , description="商户id",in=InType.body,dataType = DataType.LONG,required = false),
                    @Param(name="name" , description="名称",in=InType.body,dataType = DataType.STRING,required = true),
                    @Param(name="subName" , description="副标题",in=InType.body,dataType = DataType.STRING,required = true),
                    @Param(name="detail" , description="副标题",in=InType.body,dataType = DataType.STRING,required = true),
                    @Param(name="address" , description="地址",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="telno" , description="手机号码",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="img" , description="图片",in=InType.body,dataType = DataType.STRING,required = true),
                    @Param(name="img1" , description="图片",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="img2" , description="图片",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="img3" , description="图片",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="remark" , description="备注",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="status" , description="状态",in=InType.body,dataType = DataType.INTEGER,required = true),
                    @Param(name="price" , description="价格",in=InType.body,dataType = DataType.INTEGER,required = false),
                    @Param(name="priceDesc" , description="价格描述",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="creator" , description="创建人id",in=InType.body,dataType = DataType.LONG,required = false),
                    @Param(name="creatorName" , description="创建人姓名",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="platform" , description="平台名称",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="comments" , description="评论数",in=InType.body,dataType = DataType.INTEGER,required = false),
                    @Param(name="score" , description="分数",in=InType.body,dataType = DataType.FLOAT,required = false),
                    @Param(name="link" , description="外链",in=InType.body,dataType = DataType.STRING,required = false),
                    @Param(name="up" , description="顶",in=InType.body,dataType = DataType.INTEGER,required = false),
                    @Param(name="down" , description="踩",in=InType.body,dataType = DataType.INTEGER,required = false),
                    @Param(name="createTime" , description="创建时间",in=InType.body,dataType = DataType.DATE_TIME,required = false),
                    @Param(name="updateTime" , description="更新时间",in=InType.body,dataType = DataType.DATE_TIME,required = false),
            })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        Goods goods =    getInfoFromMap(bodyParam);
        return goodsService.save(goods);

    }
    /**
     * 说明:ajax请求Goods信息
     * @author dozen.zhang
     * @date 2018-12-2 16:05:28
     * @return String
     */
    @API(summary="商品列表接口",
            description="商品列表接口",
            parameters={
                    @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
                    @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="shopId" , description="商户id",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="name" , description="名称",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="subName" , description="副标题",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="detail" , description="副标题",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="address" , description="地址",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="telno" , description="手机号码",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="img" , description="图片",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="img1" , description="图片",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="img2" , description="图片",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="img3" , description="图片",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="remark" , description="备注",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="status" , description="状态",in=InType.params,dataType = DataType.INTEGER,required =false),// true
                    @Param(name="price" , description="价格",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="priceDesc" , description="价格描述",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="creator" , description="创建人id",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="creatorName" , description="创建人姓名",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="platform" , description="平台名称",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="comments" , description="评论数",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="score" , description="分数",in=InType.params,dataType = DataType.FLOAT,required =false),// false
                    @Param(name="link" , description="外链",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="up" , description="顶",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="down" , description="踩",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="createTime" , description="创建时间",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="updateTime" , description="更新时间",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
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
        String shopId = MapUtils.getString(params,"shopId");
        if(!StringUtil.isBlank(shopId)){
            params.put("shopId",shopId);
        }
        String name = MapUtils.getString(params,"name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = MapUtils.getString(params,"nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String subName = MapUtils.getString(params,"subName");
        if(!StringUtil.isBlank(subName)){
            params.put("subName",subName);
        }
        String subNameLike = MapUtils.getString(params,"subNameLike");
        if(!StringUtil.isBlank(subNameLike)){
            params.put("subNameLike",subNameLike);
        }
        String detail = MapUtils.getString(params,"detail");
        if(!StringUtil.isBlank(detail)){
            params.put("detail",detail);
        }
        String detailLike = MapUtils.getString(params,"detailLike");
        if(!StringUtil.isBlank(detailLike)){
            params.put("detailLike",detailLike);
        }
        String address = MapUtils.getString(params,"address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = MapUtils.getString(params,"addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
        }
        String telno = MapUtils.getString(params,"telno");
        if(!StringUtil.isBlank(telno)){
            params.put("telno",telno);
        }
        String telnoLike = MapUtils.getString(params,"telnoLike");
        if(!StringUtil.isBlank(telnoLike)){
            params.put("telnoLike",telnoLike);
        }
        String img = MapUtils.getString(params,"img");
        if(!StringUtil.isBlank(img)){
            params.put("img",img);
        }
        String imgLike = MapUtils.getString(params,"imgLike");
        if(!StringUtil.isBlank(imgLike)){
            params.put("imgLike",imgLike);
        }
        String img1 = MapUtils.getString(params,"img1");
        if(!StringUtil.isBlank(img1)){
            params.put("img1",img1);
        }
        String img1Like = MapUtils.getString(params,"img1Like");
        if(!StringUtil.isBlank(img1Like)){
            params.put("img1Like",img1Like);
        }
        String img2 = MapUtils.getString(params,"img2");
        if(!StringUtil.isBlank(img2)){
            params.put("img2",img2);
        }
        String img2Like = MapUtils.getString(params,"img2Like");
        if(!StringUtil.isBlank(img2Like)){
            params.put("img2Like",img2Like);
        }
        String img3 = MapUtils.getString(params,"img3");
        if(!StringUtil.isBlank(img3)){
            params.put("img3",img3);
        }
        String img3Like = MapUtils.getString(params,"img3Like");
        if(!StringUtil.isBlank(img3Like)){
            params.put("img3Like",img3Like);
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
        String priceDesc = MapUtils.getString(params,"priceDesc");
        if(!StringUtil.isBlank(priceDesc)){
            params.put("priceDesc",priceDesc);
        }
        String priceDescLike = MapUtils.getString(params,"priceDescLike");
        if(!StringUtil.isBlank(priceDescLike)){
            params.put("priceDescLike",priceDescLike);
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
        String comments = MapUtils.getString(params,"comments");
        if(!StringUtil.isBlank(comments)){
            params.put("comments",comments);
        }
        String score = MapUtils.getString(params,"score");
        if(!StringUtil.isBlank(score)){
            params.put("score",score);
        }
        String link = MapUtils.getString(params,"link");
        if(!StringUtil.isBlank(link)){
            params.put("link",link);
        }
        String linkLike = MapUtils.getString(params,"linkLike");
        if(!StringUtil.isBlank(linkLike)){
            params.put("linkLike",linkLike);
        }
        String up = MapUtils.getString(params,"up");
        if(!StringUtil.isBlank(up)){
            params.put("up",up);
        }
        String down = MapUtils.getString(params,"down");
        if(!StringUtil.isBlank(down)){
            params.put("down",down);
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
        List<Goods> goodss = goodsService.listByParams4Page(params);
        return ResultUtil.getResult(goodss, page);
    }



    /**
     * 导出
     * @param request
     * @return
     * @author dozen.zhang
     */
    @API(summary="商品列表导出接口",
            description="商品列表导出接口",
            parameters={
                    @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
                    @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="shopId" , description="商户id",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="name" , description="名称",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="subName" , description="副标题",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="detail" , description="副标题",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="address" , description="地址",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="telno" , description="手机号码",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="img" , description="图片",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="img1" , description="图片",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="img2" , description="图片",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="img3" , description="图片",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="remark" , description="备注",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="status" , description="状态",in=InType.params,dataType = DataType.INTEGER,required =false),// true
                    @Param(name="price" , description="价格",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="priceDesc" , description="价格描述",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="creator" , description="创建人id",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="creatorName" , description="创建人姓名",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="platform" , description="平台名称",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="comments" , description="评论数",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="score" , description="分数",in=InType.params,dataType = DataType.FLOAT,required =false),// false
                    @Param(name="link" , description="外链",in=InType.params,dataType = DataType.STRING,required =false),// false
                    @Param(name="up" , description="顶",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="down" , description="踩",in=InType.params,dataType = DataType.INTEGER,required =false),// false
                    @Param(name="createTime" , description="创建时间",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
                    @Param(name="updateTime" , description="更新时间",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
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
        String shopId = MapUtils.getString(params,"shopId");
        if(!StringUtil.isBlank(shopId)){
            params.put("shopId",shopId);
        }
        String name = MapUtils.getString(params,"name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = MapUtils.getString(params,"nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String subName = MapUtils.getString(params,"subName");
        if(!StringUtil.isBlank(subName)){
            params.put("subName",subName);
        }
        String subNameLike = MapUtils.getString(params,"subNameLike");
        if(!StringUtil.isBlank(subNameLike)){
            params.put("subNameLike",subNameLike);
        }
        String detail = MapUtils.getString(params,"detail");
        if(!StringUtil.isBlank(detail)){
            params.put("detail",detail);
        }
        String detailLike = MapUtils.getString(params,"detailLike");
        if(!StringUtil.isBlank(detailLike)){
            params.put("detailLike",detailLike);
        }
        String address = MapUtils.getString(params,"address");
        if(!StringUtil.isBlank(address)){
            params.put("address",address);
        }
        String addressLike = MapUtils.getString(params,"addressLike");
        if(!StringUtil.isBlank(addressLike)){
            params.put("addressLike",addressLike);
        }
        String telno = MapUtils.getString(params,"telno");
        if(!StringUtil.isBlank(telno)){
            params.put("telno",telno);
        }
        String telnoLike = MapUtils.getString(params,"telnoLike");
        if(!StringUtil.isBlank(telnoLike)){
            params.put("telnoLike",telnoLike);
        }
        String img = MapUtils.getString(params,"img");
        if(!StringUtil.isBlank(img)){
            params.put("img",img);
        }
        String imgLike = MapUtils.getString(params,"imgLike");
        if(!StringUtil.isBlank(imgLike)){
            params.put("imgLike",imgLike);
        }
        String img1 = MapUtils.getString(params,"img1");
        if(!StringUtil.isBlank(img1)){
            params.put("img1",img1);
        }
        String img1Like = MapUtils.getString(params,"img1Like");
        if(!StringUtil.isBlank(img1Like)){
            params.put("img1Like",img1Like);
        }
        String img2 = MapUtils.getString(params,"img2");
        if(!StringUtil.isBlank(img2)){
            params.put("img2",img2);
        }
        String img2Like = MapUtils.getString(params,"img2Like");
        if(!StringUtil.isBlank(img2Like)){
            params.put("img2Like",img2Like);
        }
        String img3 = MapUtils.getString(params,"img3");
        if(!StringUtil.isBlank(img3)){
            params.put("img3",img3);
        }
        String img3Like = MapUtils.getString(params,"img3Like");
        if(!StringUtil.isBlank(img3Like)){
            params.put("img3Like",img3Like);
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
        String priceDesc = MapUtils.getString(params,"priceDesc");
        if(!StringUtil.isBlank(priceDesc)){
            params.put("priceDesc",priceDesc);
        }
        String priceDescLike = MapUtils.getString(params,"priceDescLike");
        if(!StringUtil.isBlank(priceDescLike)){
            params.put("priceDescLike",priceDescLike);
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
        String comments = MapUtils.getString(params,"comments");
        if(!StringUtil.isBlank(comments)){
            params.put("comments",comments);
        }
        String score = MapUtils.getString(params,"score");
        if(!StringUtil.isBlank(score)){
            params.put("score",score);
        }
        String link = MapUtils.getString(params,"link");
        if(!StringUtil.isBlank(link)){
            params.put("link",link);
        }
        String linkLike = MapUtils.getString(params,"linkLike");
        if(!StringUtil.isBlank(linkLike)){
            params.put("linkLike",linkLike);
        }
        String up = MapUtils.getString(params,"up");
        if(!StringUtil.isBlank(up)){
            params.put("up",up);
        }
        String down = MapUtils.getString(params,"down");
        if(!StringUtil.isBlank(down)){
            params.put("down",down);
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
        List<Goods> list = goodsService.listByParams4Page(params);
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
        colTitle.put("shopId", "商户id");
        colTitle.put("name", "名称");
        colTitle.put("subName", "副标题");
        colTitle.put("detail", "副标题");
        colTitle.put("address", "地址");
        colTitle.put("telno", "手机号码");
        colTitle.put("img", "图片");
        colTitle.put("img1", "图片");
        colTitle.put("img2", "图片");
        colTitle.put("img3", "图片");
        colTitle.put("remark", "备注");
        colTitle.put("status", "状态");
        colTitle.put("price", "价格");
        colTitle.put("priceDesc", "价格描述");
        colTitle.put("creator", "创建人id");
        colTitle.put("creatorName", "创建人姓名");
        colTitle.put("platform", "平台名称");
        colTitle.put("comments", "评论数");
        colTitle.put("score", "分数");
        colTitle.put("link", "外链");
        colTitle.put("up", "顶");
        colTitle.put("down", "踩");
        colTitle.put("createTime", "创建时间");
        colTitle.put("updateTime", "更新时间");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            Goods sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("shopId",  list.get(i).getShopId());
            map.put("name",  list.get(i).getName());
            map.put("subName",  list.get(i).getSubName());
            map.put("detail",  list.get(i).getDetail());
            map.put("address",  list.get(i).getAddress());
            map.put("telno",  list.get(i).getTelno());
            map.put("img",  list.get(i).getImg());
            map.put("img1",  list.get(i).getImg1());
            map.put("img2",  list.get(i).getImg2());
            map.put("img3",  list.get(i).getImg3());
            map.put("remark",  list.get(i).getRemark());
            map.put("status",  list.get(i).getStatus());
            map.put("price",  list.get(i).getPrice());
            map.put("priceDesc",  list.get(i).getPriceDesc());
            map.put("creator",  list.get(i).getCreator());
            map.put("creatorName",  list.get(i).getCreatorName());
            map.put("platform",  list.get(i).getPlatform());
            map.put("comments",  list.get(i).getComments());
            map.put("score",  list.get(i).getScore());
            map.put("link",  list.get(i).getLink());
            map.put("up",  list.get(i).getUp());
            map.put("down",  list.get(i).getDown());
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


    /**
     * 说明:ajax请求Goods信息
     * @author dozen.zhang
     * @date 2018-12-2 16:05:28
     * @return String
     */
    @API(summary="支持",
            description="支持",
            parameters={

            })
    @RequestMapping(value = "/zan" , method = RequestMethod.POST)
    @ResponseBody
    @RequiresLogin
    public ResultDTO zan(HttpServletRequest request) throws Exception{
        Long  pid = Long.valueOf(request.getParameter("pid"));

        HashMap  map  =new HashMap();
        map.put("pid",pid);
        Long userId = this.getUserId(request);
        if(userId==null){
            return this.getResult(504, "未登录");
        }
        zanService.up(userId,pid,1);

        goodsService.updateZan(pid);

        return ResultUtil.getDataResult(goodsService.selectByPrimaryKey(pid));
    }


    @API(summary="反对",
            description="反对",
            parameters={
            })
    @RequestMapping(value = "/down" , method = RequestMethod.POST)
    @ResponseBody
    @RequiresLogin
    public ResultDTO down(HttpServletRequest request) throws Exception{
        Long  pid = Long.valueOf(request.getParameter("pid"));

        HashMap  map  =new HashMap();
        map.put("pid",pid);
        Long userId = this.getUserId(request);
        if(userId==null){
            return this.getResult(504, "未登录");
        }
        zanService.down(userId,pid,1);

        goodsService.updateZan(pid);

        return ResultUtil.getDataResult(goodsService.selectByPrimaryKey(pid));
    }

    @Resource
    ZanService zanService ;

}
