/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-10-9 18:07:29
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysDepart.action;
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
import com.dozenx.web.core.auth.sysDepart.service.SysDepartService;
import com.dozenx.web.core.auth.sysDepart.bean.SysDepart;
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
@APIs(description = "系统部门")
@Controller
@RequestMapping("/sys/auth/depart")
public class SysDepartController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysDepartController.class);
    /** 权限service **/
    @Autowired
    private SysDepartService sysDepartService;
    

//
//    /**
//     * 说明:ajax请求SysDepart信息
//     * @author dozen.zhang
//     * @date 2018-10-9 18:07:29
//     * @return String
//     */
//       @API(summary="系统部门列表接口",
//                 description="系统部门列表接口",
//                 parameters={
//                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
//                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
//                    @Param(name="id" , description="编号",dataType = DataType.LONG,required =false),// false
//                    @Param(name="name" , description="部门名称",dataType = DataType.STRING,required =false),// false
//                    @Param(name="pid" , description="父级部门id",dataType = DataType.LONG,required =false),// false
//                    @Param(name="code" , description="编号",dataType = DataType.STRING,required =false),// false
//                    @Param(name="status" , description="状态",dataType = DataType.INTEGER,required =false),// true
//                    @Param(name="remark" , description="备注",dataType = DataType.STRING,required =false),// false
//                    @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required =false),// false
//                    @Param(name="updateTime" , description="更新时间",dataType = DataType.DATE_TIME,required =false),// false
//                    @Param(name="userId" , description="创建人",dataType = DataType.LONG,required =false),// false
//         })
//    @RequestMapping(value = "/list" , method = RequestMethod.GET)
//    @ResponseBody
//    public ResultDTO list(HttpServletRequest request) throws Exception{
//        Page page = RequestUtil.getPage(request);
//        if(page ==null){
//             return this.getWrongResultFromCfg("err.param.page");
//        }
//
//        HashMap<String,Object> params= new HashMap<String,Object>();
//        String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            params.put("id",id);
//        }
//        String name = request.getParameter("name");
//        if(!StringUtil.isBlank(name)){
//            params.put("name",name);
//        }
//        String nameLike = request.getParameter("nameLike");
//        if(!StringUtil.isBlank(nameLike)){
//            params.put("nameLike",nameLike);
//        }
//        String pid = request.getParameter("pid");
//        if(!StringUtil.isBlank(pid)){
//            params.put("pid",pid);
//        }
//        String code = request.getParameter("code");
//        if(!StringUtil.isBlank(code)){
//            params.put("code",code);
//        }
//        String codeLike = request.getParameter("codeLike");
//        if(!StringUtil.isBlank(codeLike)){
//            params.put("codeLike",codeLike);
//        }
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            params.put("status",status);
//        }
//        String remark = request.getParameter("remark");
//        if(!StringUtil.isBlank(remark)){
//            params.put("remark",remark);
//        }
//        String remarkLike = request.getParameter("remarkLike");
//        if(!StringUtil.isBlank(remarkLike)){
//            params.put("remarkLike",remarkLike);
//        }
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                params.put("createTime",createTime);
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String createTimeBegin = request.getParameter("createTimeBegin");
//        if(!StringUtil.isBlank(createTimeBegin)){
//            if(StringUtil.checkNumeric(createTimeBegin)){
//                params.put("createTimeBegin",createTimeBegin);
//            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String createTimeEnd = request.getParameter("createTimeEnd");
//        if(!StringUtil.isBlank(createTimeEnd)){
//            if(StringUtil.checkNumeric(createTimeEnd)){
//                params.put("createTimeEnd",createTimeEnd);
//            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTime = request.getParameter("updateTime");
//        if(!StringUtil.isBlank(updateTime)){
//            if(StringUtil.checkNumeric(updateTime)){
//                params.put("updateTime",updateTime);
//            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
//                params.put("updateTime",new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTimeBegin = request.getParameter("updateTimeBegin");
//        if(!StringUtil.isBlank(updateTimeBegin)){
//            if(StringUtil.checkNumeric(updateTimeBegin)){
//                params.put("updateTimeBegin",updateTimeBegin);
//            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd HH:mm:ss")){
//                params.put("updateTimeBegin",new Timestamp( DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTimeEnd = request.getParameter("updateTimeEnd");
//        if(!StringUtil.isBlank(updateTimeEnd)){
//            if(StringUtil.checkNumeric(updateTimeEnd)){
//                params.put("updateTimeEnd",updateTimeEnd);
//            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd HH:mm:ss")){
//                params.put("updateTimeEnd",new Timestamp( DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String userId = request.getParameter("userId");
//        if(!StringUtil.isBlank(userId)){
//            params.put("userId",userId);
//        }
//
//        params.put("page",page);
//        List<SysDepart> sysDeparts = sysDepartService.listByParams4Page(params);
//        return ResultUtil.getResult(sysDeparts, page);
//    }
//
//   /**
//    * 说明:ajax请求SysDepart信息 无分页版本
//    * @return ResultDTO 返回结果
//    * @author dozen.zhang
//    * @date 2018-10-9 18:07:29
//    */
//    @RequestMapping(value = "/listAll.json")
//    @ResponseBody
//    public ResultDTO listAll(HttpServletRequest request) {
//                HashMap<String,Object> params= new HashMap<String,Object>();
//        String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            params.put("id",id);
//        }
//        String name = request.getParameter("name");
//        if(!StringUtil.isBlank(name)){
//            params.put("name",name);
//        }
//        String nameLike = request.getParameter("nameLike");
//        if(!StringUtil.isBlank(nameLike)){
//            params.put("nameLike",nameLike);
//        }
//        String pid = request.getParameter("pid");
//        if(!StringUtil.isBlank(pid)){
//            params.put("pid",pid);
//        }
//        String code = request.getParameter("code");
//        if(!StringUtil.isBlank(code)){
//            params.put("code",code);
//        }
//        String codeLike = request.getParameter("codeLike");
//        if(!StringUtil.isBlank(codeLike)){
//            params.put("codeLike",codeLike);
//        }
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            params.put("status",status);
//        }
//        String remark = request.getParameter("remark");
//        if(!StringUtil.isBlank(remark)){
//            params.put("remark",remark);
//        }
//        String remarkLike = request.getParameter("remarkLike");
//        if(!StringUtil.isBlank(remarkLike)){
//            params.put("remarkLike",remarkLike);
//        }
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                params.put("createTime",createTime);
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String createTimeBegin = request.getParameter("createTimeBegin");
//        if(!StringUtil.isBlank(createTimeBegin)){
//            if(StringUtil.checkNumeric(createTimeBegin)){
//                params.put("createTimeBegin",createTimeBegin);
//            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String createTimeEnd = request.getParameter("createTimeEnd");
//        if(!StringUtil.isBlank(createTimeEnd)){
//            if(StringUtil.checkNumeric(createTimeEnd)){
//                params.put("createTimeEnd",createTimeEnd);
//            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTime = request.getParameter("updateTime");
//        if(!StringUtil.isBlank(updateTime)){
//            if(StringUtil.checkNumeric(updateTime)){
//                params.put("updateTime",updateTime);
//            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
//                params.put("updateTime",new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTimeBegin = request.getParameter("updateTimeBegin");
//        if(!StringUtil.isBlank(updateTimeBegin)){
//            if(StringUtil.checkNumeric(updateTimeBegin)){
//                params.put("updateTimeBegin",updateTimeBegin);
//            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd HH:mm:ss")){
//                params.put("updateTimeBegin",new Timestamp( DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTimeEnd = request.getParameter("updateTimeEnd");
//        if(!StringUtil.isBlank(updateTimeEnd)){
//            if(StringUtil.checkNumeric(updateTimeEnd)){
//                params.put("updateTimeEnd",updateTimeEnd);
//            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd HH:mm:ss")){
//                params.put("updateTimeEnd",new Timestamp( DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String userId = request.getParameter("userId");
//        if(!StringUtil.isBlank(userId)){
//            params.put("userId",userId);
//        }
//
//        List<SysDepart> sysDeparts = sysDepartService.listByParams(params);
//        return ResultUtil.getDataResult(sysDeparts);
//    }
//
//   /**
//    * 说明:查看单条信息
//    * @param request 发请求
//    * @return String
//    * @author dozen.zhang
//    * @date 2018-10-9 18:07:29
//    */
//  @API( summary="根据id查询单个系统部门信息",
//           description = "根据id查询单个系统部门信息",
//           parameters={
//                   @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
//           })
//    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
//    @ResponseBody
//    public ResultDTO getById(@PathVariable Long id,HttpServletRequest request) {
//            HashMap<String,Object> result =new HashMap</*String,Object*/>();
//        if(id > 0){
//            SysDepart bean = sysDepartService.selectByPrimaryKey(Long.valueOf(id));
//            result.put("bean", bean);
//        }
//        return this.getResult(result);
//
//      /*  String id = request.getParameter("id");
//        SysDepart bean = sysDepartService.selectByPrimaryKey(Long.valueOf(id));
//        HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
//        result.put("bean", bean);
//        return this.getResult(bean);*/
//    }
//
//     /**
//        * 说明:查看单条信息
//        * @param request 发请求
//        * @return String
//        * @author dozen.zhang
//        * @date 2018-10-9 18:07:29
//        */
//      @API( summary="根据id查询单个系统部门信息",
//               description = "根据id查询单个系统部门信息",
//               parameters={
//                       @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
//               })
//        @RequestMapping(value = "/view",method = RequestMethod.GET)
//        @ResponseBody
//        public ResultDTO getById(HttpServletRequest request) {
//                HashMap<String,Object> result =new HashMap</*String,Object*/>();
////        if(id > 0){
////            SysDepart bean = sysDepartService.selectByPrimaryKey(Long.valueOf(id));
////            result.put("bean", bean);
////        }
////        return this.getResult(result);
//
//
//
//         String id = request.getParameter("id");
//            SysDepart bean = sysDepartService.selectByPrimaryKey(Long.valueOf(id));
////            HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
////            result.put("bean", bean);
//            return this.getResult(bean);
//        }
//
//
//    /**
//     * 说明:更新SysDepart信息
//     *
//     * @param request
//     * @throws Exception
//     * @return ResultDTO
//     * @author dozen.zhang
//     * @date 2018-10-9 18:07:29
//     */
//      @API( summary="根据id更新单个系统部门信息",
//        description = "根据id更新单个系统部门信息",
//        parameters={
//           @Param(name="id" , description="编号",type="LONG",required = false),
//           @Param(name="name" , description="部门名称",type="STRING",required = false),
//           @Param(name="pid" , description="父级部门id",type="LONG",required = false),
//           @Param(name="code" , description="编号",type="STRING",required = false),
//           @Param(name="status" , description="状态",type="INTEGER",required = true),
//           @Param(name="remark" , description="备注",type="STRING",required = false),
//           @Param(name="createTime" , description="创建时间",type="DATE_TIME",required = false),
//           @Param(name="updateTime" , description="更新时间",type="DATE_TIME",required = false),
//           @Param(name="userId" , description="创建人",type="LONG",required = false),
//        })
//    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
//    @RequestMapping(value = "update",method = RequestMethod.PUT)///{id}
//    @ResponseBody
//    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Long id,
//        SysDepart sysDepart =new  SysDepart();
//        /*
//        String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            sysDepart.setId(Long.valueOf(id)) ;
//        }
//
//        String name = request.getParameter("name");
//        if(!StringUtil.isBlank(name)){
//            sysDepart.setName(String.valueOf(name)) ;
//        }
//
//        String pid = request.getParameter("pid");
//        if(!StringUtil.isBlank(pid)){
//            sysDepart.setPid(Long.valueOf(pid)) ;
//        }
//
//        String code = request.getParameter("code");
//        if(!StringUtil.isBlank(code)){
//            sysDepart.setCode(String.valueOf(code)) ;
//        }
//
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            sysDepart.setStatus(Integer.valueOf(status)) ;
//        }
//
//        String remark = request.getParameter("remark");
//        if(!StringUtil.isBlank(remark)){
//            sysDepart.setRemark(String.valueOf(remark)) ;
//        }
//
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            sysDepart.setCreateTime(Timestamp.valueOf(createTime)) ;
//        }
//
//        String updateTime = request.getParameter("updateTime");
//        if(!StringUtil.isBlank(updateTime)){
//            sysDepart.setUpdateTime(Timestamp.valueOf(updateTime)) ;
//        }
//
//        String userId = request.getParameter("userId");
//        if(!StringUtil.isBlank(userId)){
//            sysDepart.setUserId(Long.valueOf(userId)) ;
//        }
//        */
//        String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            sysDepart.setId(Long.valueOf(id));
//        }
//        String name = request.getParameter("name");
//        if(!StringUtil.isBlank(name)){
//            sysDepart.setName(name);
//        }
//        String pid = request.getParameter("pid");
//        if(!StringUtil.isBlank(pid)){
//            sysDepart.setPid(Long.valueOf(pid));
//        }
//        String code = request.getParameter("code");
//        if(!StringUtil.isBlank(code)){
//            sysDepart.setCode(code);
//        }
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            sysDepart.setStatus(Integer.valueOf(status));
//        }
//        String remark = request.getParameter("remark");
//        if(!StringUtil.isBlank(remark)){
//            sysDepart.setRemark(remark);
//        }
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                sysDepart.setCreateTime(Timestamp.valueOf(createTime));
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                sysDepart.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTime = request.getParameter("updateTime");
//        if(!StringUtil.isBlank(updateTime)){
//            if(StringUtil.checkNumeric(updateTime)){
//                sysDepart.setUpdateTime(Timestamp.valueOf(updateTime));
//            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
//                sysDepart.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String userId = request.getParameter("userId");
//        if(!StringUtil.isBlank(userId)){
//            sysDepart.setUserId(Long.valueOf(userId));
//        }
//
//        //valid
//        ValidateUtil vu = new ValidateUtil();
//        String validStr="";
//        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
//        vu.add("name", name, "部门名称",  new Rule[]{new Length(20)});
//        vu.add("pid", pid, "父级部门id",  new Rule[]{new Digits(15,0)});
//        vu.add("code", code, "编号",  new Rule[]{new Length(20)});
//        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2","3","9"}),new NotEmpty()});
//        vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
//        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
//        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
//        vu.add("userId", userId, "创建人",  new Rule[]{new Digits(7,0)});
//        validStr = vu.validateString();
//        if(StringUtil.isNotBlank(validStr)) {
//            return ResultUtil.getResult(302,validStr);
//        }
//
//        return sysDepartService.save(sysDepart);
//
//    }
//
//
//        /**
//         * 说明:添加SysDepart信息
//         * @param request
//         * @throws Exception
//         * @return ResultDTO
//         * @author dozen.zhang
//         * @date 2018-10-9 18:07:29
//         */
//        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
//        @API( summary="添加单个系统部门信息",
//            description = "添加单个系统部门信息",
//            parameters={
//               @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
//               @Param(name="name" , description="部门名称",dataType = DataType.STRING,required = false),
//               @Param(name="pid" , description="父级部门id",dataType = DataType.LONG,required = false),
//               @Param(name="code" , description="编号",dataType = DataType.STRING,required = false),
//               @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = true),
//               @Param(name="remark" , description="备注",dataType = DataType.STRING,required = false),
//               @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
//               @Param(name="updateTime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
//               @Param(name="userId" , description="创建人",dataType = DataType.LONG,required = false),
//            })
//        @RequestMapping(value = "add",method = RequestMethod.POST)
//        @ResponseBody
//        public ResultDTO add(HttpServletRequest request) throws Exception {
//            SysDepart sysDepart =new  SysDepart();
//            /*
//            String id = request.getParameter("id");
//            if(!StringUtil.isBlank(id)){
//                sysDepart.setId(Long.valueOf(id)) ;
//            }
//
//            String name = request.getParameter("name");
//            if(!StringUtil.isBlank(name)){
//                sysDepart.setName(String.valueOf(name)) ;
//            }
//
//            String pid = request.getParameter("pid");
//            if(!StringUtil.isBlank(pid)){
//                sysDepart.setPid(Long.valueOf(pid)) ;
//            }
//
//            String code = request.getParameter("code");
//            if(!StringUtil.isBlank(code)){
//                sysDepart.setCode(String.valueOf(code)) ;
//            }
//
//            String status = request.getParameter("status");
//            if(!StringUtil.isBlank(status)){
//                sysDepart.setStatus(Integer.valueOf(status)) ;
//            }
//
//            String remark = request.getParameter("remark");
//            if(!StringUtil.isBlank(remark)){
//                sysDepart.setRemark(String.valueOf(remark)) ;
//            }
//
//            String createTime = request.getParameter("createTime");
//            if(!StringUtil.isBlank(createTime)){
//                sysDepart.setCreateTime(Timestamp.valueOf(createTime)) ;
//            }
//
//            String updateTime = request.getParameter("updateTime");
//            if(!StringUtil.isBlank(updateTime)){
//                sysDepart.setUpdateTime(Timestamp.valueOf(updateTime)) ;
//            }
//
//            String userId = request.getParameter("userId");
//            if(!StringUtil.isBlank(userId)){
//                sysDepart.setUserId(Long.valueOf(userId)) ;
//            }
//            */
//            String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            sysDepart.setId(Long.valueOf(id));
//        }
//        String name = request.getParameter("name");
//        if(!StringUtil.isBlank(name)){
//            sysDepart.setName(name);
//        }
//        String pid = request.getParameter("pid");
//        if(!StringUtil.isBlank(pid)){
//            sysDepart.setPid(Long.valueOf(pid));
//        }
//        String code = request.getParameter("code");
//        if(!StringUtil.isBlank(code)){
//            sysDepart.setCode(code);
//        }
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            sysDepart.setStatus(Integer.valueOf(status));
//        }
//        String remark = request.getParameter("remark");
//        if(!StringUtil.isBlank(remark)){
//            sysDepart.setRemark(remark);
//        }
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                sysDepart.setCreateTime(Timestamp.valueOf(createTime));
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                sysDepart.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTime = request.getParameter("updateTime");
//        if(!StringUtil.isBlank(updateTime)){
//            if(StringUtil.checkNumeric(updateTime)){
//                sysDepart.setUpdateTime(Timestamp.valueOf(updateTime));
//            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
//                sysDepart.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String userId = request.getParameter("userId");
//        if(!StringUtil.isBlank(userId)){
//            sysDepart.setUserId(Long.valueOf(userId));
//        }
//
//            //valid
//            ValidateUtil vu = new ValidateUtil();
//        String validStr="";
//        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
//        vu.add("name", name, "部门名称",  new Rule[]{new Length(20)});
//        vu.add("pid", pid, "父级部门id",  new Rule[]{new Digits(15,0)});
//        vu.add("code", code, "编号",  new Rule[]{new Length(20)});
//        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2","3","9"}),new NotEmpty()});
//        vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
//        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
//        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
//        vu.add("userId", userId, "创建人",  new Rule[]{new Digits(7,0)});
//        validStr = vu.validateString();
//        if(StringUtil.isNotBlank(validStr)) {
//            return ResultUtil.getResult(302,validStr);
//        }
//
//            return sysDepartService.save(sysDepart);
//
//        }
//
//
//          /**
//                 * 说明:添加SysDepart信息
//                 * @param request
//                 * @throws Exception
//                 * @return ResultDTO
//                 * @author dozen.zhang
//                 * @date 2018-10-9 18:07:29
//                 */
//                // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
//                @API( summary="添加单个系统部门信息",
//                    description = "添加单个系统部门信息",
//                    parameters={
//                       @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
//                       @Param(name="name" , description="部门名称",dataType = DataType.STRING,required = false),
//                       @Param(name="pid" , description="父级部门id",dataType = DataType.LONG,required = false),
//                       @Param(name="code" , description="编号",dataType = DataType.STRING,required = false),
//                       @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = true),
//                       @Param(name="remark" , description="备注",dataType = DataType.STRING,required = false),
//                       @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
//                       @Param(name="updateTime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
//                       @Param(name="userId" , description="创建人",dataType = DataType.LONG,required = false),
//                    })
//                @RequestMapping(value = "save",method = RequestMethod.POST)
//                @ResponseBody
//                public ResultDTO save(HttpServletRequest request) throws Exception {
//                    SysDepart sysDepart =new  SysDepart();
//                    /*
//                    String id = request.getParameter("id");
//                    if(!StringUtil.isBlank(id)){
//                        sysDepart.setId(Long.valueOf(id)) ;
//                    }
//
//                    String name = request.getParameter("name");
//                    if(!StringUtil.isBlank(name)){
//                        sysDepart.setName(String.valueOf(name)) ;
//                    }
//
//                    String pid = request.getParameter("pid");
//                    if(!StringUtil.isBlank(pid)){
//                        sysDepart.setPid(Long.valueOf(pid)) ;
//                    }
//
//                    String code = request.getParameter("code");
//                    if(!StringUtil.isBlank(code)){
//                        sysDepart.setCode(String.valueOf(code)) ;
//                    }
//
//                    String status = request.getParameter("status");
//                    if(!StringUtil.isBlank(status)){
//                        sysDepart.setStatus(Integer.valueOf(status)) ;
//                    }
//
//                    String remark = request.getParameter("remark");
//                    if(!StringUtil.isBlank(remark)){
//                        sysDepart.setRemark(String.valueOf(remark)) ;
//                    }
//
//                    String createTime = request.getParameter("createTime");
//                    if(!StringUtil.isBlank(createTime)){
//                        sysDepart.setCreateTime(Timestamp.valueOf(createTime)) ;
//                    }
//
//                    String updateTime = request.getParameter("updateTime");
//                    if(!StringUtil.isBlank(updateTime)){
//                        sysDepart.setUpdateTime(Timestamp.valueOf(updateTime)) ;
//                    }
//
//                    String userId = request.getParameter("userId");
//                    if(!StringUtil.isBlank(userId)){
//                        sysDepart.setUserId(Long.valueOf(userId)) ;
//                    }
//                    */
//                    String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            sysDepart.setId(Long.valueOf(id));
//        }
//        String name = request.getParameter("name");
//        if(!StringUtil.isBlank(name)){
//            sysDepart.setName(name);
//        }
//        String pid = request.getParameter("pid");
//        if(!StringUtil.isBlank(pid)){
//            sysDepart.setPid(Long.valueOf(pid));
//        }
//        String code = request.getParameter("code");
//        if(!StringUtil.isBlank(code)){
//            sysDepart.setCode(code);
//        }
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            sysDepart.setStatus(Integer.valueOf(status));
//        }
//        String remark = request.getParameter("remark");
//        if(!StringUtil.isBlank(remark)){
//            sysDepart.setRemark(remark);
//        }
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                sysDepart.setCreateTime(Timestamp.valueOf(createTime));
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                sysDepart.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTime = request.getParameter("updateTime");
//        if(!StringUtil.isBlank(updateTime)){
//            if(StringUtil.checkNumeric(updateTime)){
//                sysDepart.setUpdateTime(Timestamp.valueOf(updateTime));
//            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
//                sysDepart.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String userId = request.getParameter("userId");
//        if(!StringUtil.isBlank(userId)){
//            sysDepart.setUserId(Long.valueOf(userId));
//        }
//
//                    //valid
//                    ValidateUtil vu = new ValidateUtil();
//        String validStr="";
//        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
//        vu.add("name", name, "部门名称",  new Rule[]{new Length(20)});
//        vu.add("pid", pid, "父级部门id",  new Rule[]{new Digits(15,0)});
//        vu.add("code", code, "编号",  new Rule[]{new Length(20)});
//        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2","3","9"}),new NotEmpty()});
//        vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
//        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
//        vu.add("updateTime", updateTime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
//        vu.add("userId", userId, "创建人",  new Rule[]{new Digits(7,0)});
//        validStr = vu.validateString();
//        if(StringUtil.isNotBlank(validStr)) {
//            return ResultUtil.getResult(302,validStr);
//        }
//
//                    return sysDepartService.save(sysDepart);
//
//                }
//
//    /**
//     * 说明:删除SysDepart信息
//     * @param request
//     * @throws Exception
//     * @return ResultDTO
//     * @author dozen.zhang
//     * @date 2018-10-9 18:07:29
//     */
//     @API( summary="根据id删除单个系统部门信息",
//        description = "根据id删除单个系统部门信息",
//        parameters={
//         @Param(name="id" , description="编号",dataType= DataType.LONG,required = true),
//        })
//    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)//{id}
//    @ResponseBody
//    public ResultDTO delete(HttpServletRequest request) {//@PathVariable Long id,
//        String idStr = request.getParameter("id");
//        if(StringUtil.isBlank(idStr)){
//            return this.getWrongResultFromCfg("err.param.notnull");
//        }
//        Long id = Long.valueOf(idStr);
//        sysDepartService.delete(id);
//        return this.getResult(SUCC);
//    }
//     /**
//     * 多行删除
//     * @param request
//     * @return
//     * @author dozen.zhang
//     */
//    @RequestMapping(value = "/mdel.json")
//    @ResponseBody
//    public ResultDTO multiDelete(HttpServletRequest request) {
//        String idStr = request.getParameter("ids");
//        if(StringUtil.isBlank(idStr)){
//            return this.getWrongResultFromCfg("err.param.notnull");
//        }
//        String idStrAry[]= idStr.split(",");
//        Long idAry[]=new Long[idStrAry.length];
//        for(int i=0,length=idAry.length;i<length;i++){
//            ValidateUtil vu = new ValidateUtil();
//            String validStr="";
//            String id = idStrAry[i];
//                    vu.add("id", id, "编号",  new Rule[]{});
//
//            try{
//                validStr=vu.validateString();
//            }catch(Exception e){
//                e.printStackTrace();
//                validStr="验证程序异常";
//                return ResultUtil.getResult(302,validStr);
//            }
//
//            if(StringUtil.isNotBlank(validStr)) {
//                return ResultUtil.getResult(302,validStr);
//            }
//            idAry[i]=Long.valueOf(idStrAry[i]);
//        }
//       return  sysDepartService.multilDelete(idAry);
//    }
//
//    /**
//     * 导出
//     * @param request
//     * @return
//     * @author dozen.zhang
//     */
//    @RequestMapping(value = "/export.json")
//    @ResponseBody
//    public ResultDTO exportExcel(HttpServletRequest request){
//               HashMap<String,Object> params= new HashMap<String,Object>();
//        String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            params.put("id",id);
//        }
//        String name = request.getParameter("name");
//        if(!StringUtil.isBlank(name)){
//            params.put("name",name);
//        }
//        String nameLike = request.getParameter("nameLike");
//        if(!StringUtil.isBlank(nameLike)){
//            params.put("nameLike",nameLike);
//        }
//        String pid = request.getParameter("pid");
//        if(!StringUtil.isBlank(pid)){
//            params.put("pid",pid);
//        }
//        String code = request.getParameter("code");
//        if(!StringUtil.isBlank(code)){
//            params.put("code",code);
//        }
//        String codeLike = request.getParameter("codeLike");
//        if(!StringUtil.isBlank(codeLike)){
//            params.put("codeLike",codeLike);
//        }
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            params.put("status",status);
//        }
//        String remark = request.getParameter("remark");
//        if(!StringUtil.isBlank(remark)){
//            params.put("remark",remark);
//        }
//        String remarkLike = request.getParameter("remarkLike");
//        if(!StringUtil.isBlank(remarkLike)){
//            params.put("remarkLike",remarkLike);
//        }
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                params.put("createTime",createTime);
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String createTimeBegin = request.getParameter("createTimeBegin");
//        if(!StringUtil.isBlank(createTimeBegin)){
//            if(StringUtil.checkNumeric(createTimeBegin)){
//                params.put("createTimeBegin",createTimeBegin);
//            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String createTimeEnd = request.getParameter("createTimeEnd");
//        if(!StringUtil.isBlank(createTimeEnd)){
//            if(StringUtil.checkNumeric(createTimeEnd)){
//                params.put("createTimeEnd",createTimeEnd);
//            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTime = request.getParameter("updateTime");
//        if(!StringUtil.isBlank(updateTime)){
//            if(StringUtil.checkNumeric(updateTime)){
//                params.put("updateTime",updateTime);
//            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
//                params.put("updateTime",new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTimeBegin = request.getParameter("updateTimeBegin");
//        if(!StringUtil.isBlank(updateTimeBegin)){
//            if(StringUtil.checkNumeric(updateTimeBegin)){
//                params.put("updateTimeBegin",updateTimeBegin);
//            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd HH:mm:ss")){
//                params.put("updateTimeBegin",new Timestamp( DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTimeEnd = request.getParameter("updateTimeEnd");
//        if(!StringUtil.isBlank(updateTimeEnd)){
//            if(StringUtil.checkNumeric(updateTimeEnd)){
//                params.put("updateTimeEnd",updateTimeEnd);
//            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd HH:mm:ss")){
//                params.put("updateTimeEnd",new Timestamp( DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String userId = request.getParameter("userId");
//        if(!StringUtil.isBlank(userId)){
//            params.put("userId",userId);
//        }
//
//        // 查询list集合
//        List<SysDepart> list =sysDepartService.listByParams(params);
//        // 存放临时文件
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDispositionFormData("attachment", "list.xlsx");
//        String folder = request.getSession().getServletContext()
//                .getRealPath("/")
//                + "xlstmp";
//        File folder_file = new File(folder);
//        if (!folder_file.exists()) {
//            folder_file.mkdir();
//        }
//        String fileName = folder + File.separator
//                + DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS")
//                + ".xlsx";
//        // 得到导出Excle时清单的英中文map
//        LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
//        colTitle.put("id", "编号");
//        colTitle.put("name", "部门名称");
//        colTitle.put("pid", "父级部门id");
//        colTitle.put("code", "编号");
//        colTitle.put("status", "状态");
//        colTitle.put("remark", "备注");
//        colTitle.put("createTime", "创建时间");
//        colTitle.put("updateTime", "更新时间");
//        colTitle.put("userId", "创建人");
//        List<Map> finalList = new ArrayList<Map>();
//        for (int i = 0; i < list.size(); i++) {
//            SysDepart sm = list.get(i);
//            HashMap<String,Object> map = new HashMap<String,Object>();
//            map.put("id",  list.get(i).getId());
//            map.put("name",  list.get(i).getName());
//            map.put("pid",  list.get(i).getPid());
//            map.put("code",  list.get(i).getCode());
//            map.put("status",  list.get(i).getStatus());
//            map.put("remark",  list.get(i).getRemark());
//            map.put("createTime",  list.get(i).getCreateTime());
//            map.put("updateTime",  list.get(i).getUpdateTime());
//            map.put("userId",  list.get(i).getUserId());
//            finalList.add(map);
//        }
//        try {
//            if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
//                return this.getResult(SUCC,fileName,"导出成功");
//            }
//            /*
//             * return new ResponseEntity<byte[]>(
//             * FileUtils.readFileToByteArray(new File(fileName)), headers,
//             * HttpStatus.CREATED);
//             */
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return this.getResult(0, "数据为空，导出失败");
//
//    }
//    @RequestMapping(value = "/import.json")
//    public void importExcel(){
//
//    }
//
//
//      /**
//         * 说明: 跳转到SysDepart列表页面
//         *
//         * @return
//         * @return String
//         * @author dozen.zhang
//         * @date 2015年11月15日下午12:30:45
//         */
//        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
//        public String listHtml() {
//            return "/static/html/SysDepartList.html";
//        }
//
//        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
//        public String listMapperHtml() {
//            return "/static/html/SysDepartListMapper.html";
//        }
//
//
//    /**
//     * 说明:跳转编辑页面
//     * @param request 发请求
//     * @return String
//     * @author dozen.zhang
//     * @date 2018-10-9 18:07:29
//     */
//    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
//    public String editHtml( HttpServletRequest request) {
//        // 查找所有的角色
//        return "/static/html/SysDepartEdit.html";
//    }
//    /**
//         * 说明:跳转查看页面
//         * @param request 发请求
//         * @return String
//         * @author dozen.zhang
//         * @date 2018-10-9 18:07:29
//         */
//    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
//    public String viewHtml( HttpServletRequest request) {
//        return "/static/html/SysDepartView.html";
//    }
}
