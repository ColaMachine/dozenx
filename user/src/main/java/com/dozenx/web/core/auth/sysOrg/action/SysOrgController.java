/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2019-4-3 10:57:26
 * 文件说明:
 */

package com.dozenx.web.core.auth.sysOrg.action;

import com.dozenx.swagger.annotation.*;
import com.dozenx.common.exception.ParamException;
import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.MapUtils;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.auth.sysOrg.bean.SysOrg;
import com.dozenx.web.core.auth.sysOrg.service.SysOrgService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@APIs(description = "系统公司")
@Controller
@RequestMapping("/sys/auth/org")
public class SysOrgController extends BaseController {
    /**
     * 日志
     **/
    private Logger logger = LoggerFactory.getLogger(SysOrgController.class);
    /**
     * 权限service
     **/
    @Autowired
    private SysOrgService sysOrgService;
//
//
//
//    /**
//     * 说明:ajax请求SysOrg信息
//     * @author dozen.zhang
//     * @date 2019-4-3 10:57:26
//     * @return String
//     */
//       @API(summary="系统公司列表接口",
//                 description="系统公司列表接口",
//                 parameters={
//                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
//                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
//                    @Param(name="id" , description="编号",dataType = DataType.LONG,required =false),// false
//                    @Param(name="name" , description="公司名称",dataType = DataType.STRING,required =false),// false
//                    @Param(name="code" , description="编号",dataType = DataType.STRING,required =false),// false
//                    @Param(name="status" , description="状态",dataType = DataType.INTEGER,required =false),// true
//                    @Param(name="remark" , description="备注",dataType = DataType.STRING,required =false),// false
//                    @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required =false),// false
//                    @Param(name="updateTime" , description="更新时间",dataType = DataType.DATE_TIME,required =false),// false
//                    @Param(name="userId" , description="创建人",dataType = DataType.LONG,required =false),// false
//         })
//    @RequestMapping(value = "/list.json" , method = RequestMethod.GET)
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
//        List<SysOrg> sysOrgs = sysOrgService.listByParams4Page(params);
//        return ResultUtil.getResult(sysOrgs, page);
//    }
//
//   /**
//    * 说明:ajax请求SysOrg信息 无分页版本
//    * @return ResultDTO 返回结果
//    * @author dozen.zhang
//    * @date 2019-4-3 10:57:26
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
//        List<SysOrg> sysOrgs = sysOrgService.listByParams(params);
//        return ResultUtil.getDataResult(sysOrgs);
//    }
//
//   /**
//    * 说明:查看单条信息
//    * @param request 发请求
//    * @return String
//    * @author dozen.zhang
//    * @date 2019-4-3 10:57:26
//    */
//     @API( summary="根据id查询单个系统公司信息",
//               description = "根据id查询单个系统公司信息",
//               parameters={
//                       @Param(name="id" , description="id",in=InType.path,dataType= DataType.LONG,required = true),
//               })
//        @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
//        @ResponseBody
//        public ResultDTO getById(@PathVariable Long id,HttpServletRequest request) {
//                     HashMap<String,Object> result =new HashMap</*String,Object*/>();
//            SysOrg bean = sysOrgService.selectByPrimaryKey(Long.valueOf(id));
//            result.put("bean", bean);
//            return this.getResult(result);
//
//        }
//
//
//
//     /**
//        * 说明:查看单条信息
//        * @param request 发请求
//        * @return String
//        * @author dozen.zhang
//        * @date 2019-4-3 10:57:26
//        */
//      @API( summary="根据id查询单个系统公司信息",
//               description = "根据id查询单个系统公司信息",
//               parameters={
//                       @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
//               })
//        @RequestMapping(value = "/view.json",method = RequestMethod.GET)
//        @ResponseBody
//        public ResultDTO getById(HttpServletRequest request) {
//         String id = request.getParameter("id");
//            SysOrg bean = sysOrgService.selectByPrimaryKey(Long.valueOf(id));
//          //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
//           // result.put("bean", bean);
//            return this.getResult(bean);
//        }
//
//
//    /**
//     * 说明:更新SysOrg信息
//     *
//     * @param request
//     * @throws Exception
//     * @return ResultDTO
//     * @author dozen.zhang
//     * @date 2019-4-3 10:57:26
//     */
//      @API( summary="更新id更新单个系统公司信息",
//        description = "更新id更新单个系统公司信息",
//        parameters={
//           @Param(name="id" , description="编号",type="LONG",required = false),
//           @Param(name="name" , description="公司名称",type="STRING",required = false),
//           @Param(name="code" , description="编号",type="STRING",required = false),
//           @Param(name="status" , description="状态",type="INTEGER",required = true),
//           @Param(name="remark" , description="备注",type="STRING",required = false),
//           @Param(name="createTime" , description="创建时间",type="DATE_TIME",required = false),
//           @Param(name="updateTime" , description="更新时间",type="DATE_TIME",required = false),
//           @Param(name="userId" , description="创建人",type="LONG",required = false),
//        })
//    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
//    @RequestMapping(value = "update.json",method = RequestMethod.PUT)///{id}
//    @ResponseBody
//    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Long id,
//        SysOrg sysOrg =new  SysOrg();
//        /*
//        String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            sysOrg.setId(Long.valueOf(id)) ;
//        }
//
//        String name = request.getParameter("name");
//        if(!StringUtil.isBlank(name)){
//            sysOrg.setName(String.valueOf(name)) ;
//        }
//
//        String code = request.getParameter("code");
//        if(!StringUtil.isBlank(code)){
//            sysOrg.setCode(String.valueOf(code)) ;
//        }
//
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            sysOrg.setStatus(Integer.valueOf(status)) ;
//        }
//
//        String remark = request.getParameter("remark");
//        if(!StringUtil.isBlank(remark)){
//            sysOrg.setRemark(String.valueOf(remark)) ;
//        }
//
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            sysOrg.setCreateTime(Timestamp.valueOf(createTime)) ;
//        }
//
//        String updateTime = request.getParameter("updateTime");
//        if(!StringUtil.isBlank(updateTime)){
//            sysOrg.setUpdateTime(Timestamp.valueOf(updateTime)) ;
//        }
//
//        String userId = request.getParameter("userId");
//        if(!StringUtil.isBlank(userId)){
//            sysOrg.setUserId(Long.valueOf(userId)) ;
//        }
//        */
//        String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            sysOrg.setId(Long.valueOf(id));
//        }
//        String name = request.getParameter("name");
//        if(!StringUtil.isBlank(name)){
//            sysOrg.setName(name);
//        }
//        String code = request.getParameter("code");
//        if(!StringUtil.isBlank(code)){
//            sysOrg.setCode(code);
//        }
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            sysOrg.setStatus(Integer.valueOf(status));
//        }
//        String remark = request.getParameter("remark");
//        if(!StringUtil.isBlank(remark)){
//            sysOrg.setRemark(remark);
//        }
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                sysOrg.setCreateTime(Timestamp.valueOf(createTime));
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                sysOrg.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTime = request.getParameter("updateTime");
//        if(!StringUtil.isBlank(updateTime)){
//            if(StringUtil.checkNumeric(updateTime)){
//                sysOrg.setUpdateTime(Timestamp.valueOf(updateTime));
//            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
//                sysOrg.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String userId = request.getParameter("userId");
//        if(!StringUtil.isBlank(userId)){
//            sysOrg.setUserId(Long.valueOf(userId));
//        }
//
//        //valid
//        ValidateUtil vu = new ValidateUtil();
//        String validStr="";
//        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
//        vu.add("name", name, "公司名称",  new Rule[]{new Length(20)});
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
//        return sysOrgService.save(sysOrg);
//
//    }
//
//
//        /**
//         * 说明:添加SysOrg信息
//         * @param request
//         * @throws Exception
//         * @return ResultDTO
//         * @author dozen.zhang
//         * @date 2019-4-3 10:57:26
//         */
//        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
//        @API( summary="添加单个系统公司信息",
//            description = "添加单个系统公司信息",
//            parameters={
//               @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
//               @Param(name="name" , description="公司名称",dataType = DataType.STRING,required = false),
//               @Param(name="code" , description="编号",dataType = DataType.STRING,required = false),
//               @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = true),
//               @Param(name="remark" , description="备注",dataType = DataType.STRING,required = false),
//               @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
//               @Param(name="updateTime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
//               @Param(name="userId" , description="创建人",dataType = DataType.LONG,required = false),
//            })
//        @RequestMapping(value = "add.json",method = RequestMethod.POST)
//        @ResponseBody
//        public ResultDTO add(HttpServletRequest request) throws Exception {
//            SysOrg sysOrg =new  SysOrg();
//            /*
//            String id = request.getParameter("id");
//            if(!StringUtil.isBlank(id)){
//                sysOrg.setId(Long.valueOf(id)) ;
//            }
//
//            String name = request.getParameter("name");
//            if(!StringUtil.isBlank(name)){
//                sysOrg.setName(String.valueOf(name)) ;
//            }
//
//            String code = request.getParameter("code");
//            if(!StringUtil.isBlank(code)){
//                sysOrg.setCode(String.valueOf(code)) ;
//            }
//
//            String status = request.getParameter("status");
//            if(!StringUtil.isBlank(status)){
//                sysOrg.setStatus(Integer.valueOf(status)) ;
//            }
//
//            String remark = request.getParameter("remark");
//            if(!StringUtil.isBlank(remark)){
//                sysOrg.setRemark(String.valueOf(remark)) ;
//            }
//
//            String createTime = request.getParameter("createTime");
//            if(!StringUtil.isBlank(createTime)){
//                sysOrg.setCreateTime(Timestamp.valueOf(createTime)) ;
//            }
//
//            String updateTime = request.getParameter("updateTime");
//            if(!StringUtil.isBlank(updateTime)){
//                sysOrg.setUpdateTime(Timestamp.valueOf(updateTime)) ;
//            }
//
//            String userId = request.getParameter("userId");
//            if(!StringUtil.isBlank(userId)){
//                sysOrg.setUserId(Long.valueOf(userId)) ;
//            }
//            */
//            String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            sysOrg.setId(Long.valueOf(id));
//        }
//        String name = request.getParameter("name");
//        if(!StringUtil.isBlank(name)){
//            sysOrg.setName(name);
//        }
//        String code = request.getParameter("code");
//        if(!StringUtil.isBlank(code)){
//            sysOrg.setCode(code);
//        }
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            sysOrg.setStatus(Integer.valueOf(status));
//        }
//        String remark = request.getParameter("remark");
//        if(!StringUtil.isBlank(remark)){
//            sysOrg.setRemark(remark);
//        }
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                sysOrg.setCreateTime(Timestamp.valueOf(createTime));
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                sysOrg.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTime = request.getParameter("updateTime");
//        if(!StringUtil.isBlank(updateTime)){
//            if(StringUtil.checkNumeric(updateTime)){
//                sysOrg.setUpdateTime(Timestamp.valueOf(updateTime));
//            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
//                sysOrg.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String userId = request.getParameter("userId");
//        if(!StringUtil.isBlank(userId)){
//            sysOrg.setUserId(Long.valueOf(userId));
//        }
//
//            //valid
//            ValidateUtil vu = new ValidateUtil();
//        String validStr="";
//        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
//        vu.add("name", name, "公司名称",  new Rule[]{new Length(20)});
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
//            return sysOrgService.save(sysOrg);
//
//        }
//
//
//          /**
//                 * 说明:添加SysOrg信息
//                 * @param request
//                 * @throws Exception
//                 * @return ResultDTO
//                 * @author dozen.zhang
//                 * @date 2019-4-3 10:57:26
//                 */
//                // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
//                @API( summary="添加单个系统公司信息",
//                    description = "添加单个系统公司信息",
//                    parameters={
//                       @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
//                       @Param(name="name" , description="公司名称",dataType = DataType.STRING,required = false),
//                       @Param(name="code" , description="编号",dataType = DataType.STRING,required = false),
//                       @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = true),
//                       @Param(name="remark" , description="备注",dataType = DataType.STRING,required = false),
//                       @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
//                       @Param(name="updateTime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
//                       @Param(name="userId" , description="创建人",dataType = DataType.LONG,required = false),
//                    })
//                @RequestMapping(value = "save.json",method = RequestMethod.POST)
//                @ResponseBody
//                public ResultDTO save(HttpServletRequest request) throws Exception {
//                    SysOrg sysOrg =new  SysOrg();
//                    /*
//                    String id = request.getParameter("id");
//                    if(!StringUtil.isBlank(id)){
//                        sysOrg.setId(Long.valueOf(id)) ;
//                    }
//
//                    String name = request.getParameter("name");
//                    if(!StringUtil.isBlank(name)){
//                        sysOrg.setName(String.valueOf(name)) ;
//                    }
//
//                    String code = request.getParameter("code");
//                    if(!StringUtil.isBlank(code)){
//                        sysOrg.setCode(String.valueOf(code)) ;
//                    }
//
//                    String status = request.getParameter("status");
//                    if(!StringUtil.isBlank(status)){
//                        sysOrg.setStatus(Integer.valueOf(status)) ;
//                    }
//
//                    String remark = request.getParameter("remark");
//                    if(!StringUtil.isBlank(remark)){
//                        sysOrg.setRemark(String.valueOf(remark)) ;
//                    }
//
//                    String createTime = request.getParameter("createTime");
//                    if(!StringUtil.isBlank(createTime)){
//                        sysOrg.setCreateTime(Timestamp.valueOf(createTime)) ;
//                    }
//
//                    String updateTime = request.getParameter("updateTime");
//                    if(!StringUtil.isBlank(updateTime)){
//                        sysOrg.setUpdateTime(Timestamp.valueOf(updateTime)) ;
//                    }
//
//                    String userId = request.getParameter("userId");
//                    if(!StringUtil.isBlank(userId)){
//                        sysOrg.setUserId(Long.valueOf(userId)) ;
//                    }
//                    */
//                    String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            sysOrg.setId(Long.valueOf(id));
//        }
//        String name = request.getParameter("name");
//        if(!StringUtil.isBlank(name)){
//            sysOrg.setName(name);
//        }
//        String code = request.getParameter("code");
//        if(!StringUtil.isBlank(code)){
//            sysOrg.setCode(code);
//        }
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            sysOrg.setStatus(Integer.valueOf(status));
//        }
//        String remark = request.getParameter("remark");
//        if(!StringUtil.isBlank(remark)){
//            sysOrg.setRemark(remark);
//        }
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                sysOrg.setCreateTime(Timestamp.valueOf(createTime));
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                sysOrg.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTime = request.getParameter("updateTime");
//        if(!StringUtil.isBlank(updateTime)){
//            if(StringUtil.checkNumeric(updateTime)){
//                sysOrg.setUpdateTime(Timestamp.valueOf(updateTime));
//            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
//                sysOrg.setUpdateTime(new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String userId = request.getParameter("userId");
//        if(!StringUtil.isBlank(userId)){
//            sysOrg.setUserId(Long.valueOf(userId));
//        }
//
//                    //valid
//                    ValidateUtil vu = new ValidateUtil();
//        String validStr="";
//        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
//        vu.add("name", name, "公司名称",  new Rule[]{new Length(20)});
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
//                    return sysOrgService.save(sysOrg);
//
//                }
//
//    /**
//     * 说明:删除SysOrg信息
//     * @param request
//     * @throws Exception
//     * @return ResultDTO
//     * @author dozen.zhang
//     * @date 2019-4-3 10:57:26
//     */
//     @API( summary="根据id删除单个系统公司信息",
//        description = "根据id删除单个系统公司信息",
//        parameters={
//         @Param(name="id" , description="编号",dataType= DataType.LONG,required = true),
//        })
//    @RequestMapping(value = "/delete.json",method = RequestMethod.DELETE)//{id}
//    @ResponseBody
//    public ResultDTO delete(HttpServletRequest request) {//@PathVariable Long id,
//        String idStr = request.getParameter("id");
//        if(StringUtil.isBlank(idStr)){
//            return this.getWrongResultFromCfg("err.param.notnull");
//        }
//        Long id = Long.valueOf(idStr);
//        sysOrgService.delete(id);
//        return this.getResult(SUCC);
//    }
//

    /**
     * 说明:删除SysOrg信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2019-4-3 10:57:26
     */
    @API(summary = "根据id删除单个系统公司信息",
            description = "根据id删除单个系统公司信息",
            parameters = {
                    @Param(name = "id", description = "编号", in = InType.path, dataType = DataType.LONG, required = true),
            })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)//{id}
    @ResponseBody
    public ResultDTO delete(@PathVariable Long id, HttpServletRequest request) {
        sysOrgService.delete(id);
        return this.getResult(SUCC);
    }
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
//       return  sysOrgService.multilDelete(idAry);
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
//        List<SysOrg> list =sysOrgService.listByParams(params);
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
//        colTitle.put("name", "公司名称");
//        colTitle.put("code", "编号");
//        colTitle.put("status", "状态");
//        colTitle.put("remark", "备注");
//        colTitle.put("createTime", "创建时间");
//        colTitle.put("updateTime", "更新时间");
//        colTitle.put("userId", "创建人");
//        List<Map> finalList = new ArrayList<Map>();
//        for (int i = 0; i < list.size(); i++) {
//            SysOrg sm = list.get(i);
//            HashMap<String,Object> map = new HashMap<String,Object>();
//            map.put("id",  list.get(i).getId());
//            map.put("name",  list.get(i).getName());
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
//
//     @API(summary = "批量导入信息",
//                description = "批量导入信息",
//                consumes = "multipart/form-data",
//                parameters = {
//                        @Param(name = "file", description = "编号", in = InType.form, dataType = DataType.FILE, required = true),
//                })
//    @RequestMapping(value = "/import", method = RequestMethod.POST)
//     @ResponseBody
//    public ResultDTO importExcel(@RequestParam("file") MultipartFile file){
//          // 将spring 的file 装成 普通file
//                File xlsFile = null;
//                if (file != null) {
//                    try {
//                        String fileName = System.currentTimeMillis() + ".xls";//取一个随机的名称
//                        String s = PathManager.getInstance().getTmpPath().resolve(fileName).toString();//存入tmp文件夹
//                        Files.copy(file.getInputStream(), PathManager.getInstance().getTmpPath().resolve(fileName));//存到本地
//                        xlsFile = PathManager.getInstance().getTmpPath().resolve(fileName).toFile();//读取
//                    } catch (Exception e) {
//                        logger.error("文件上传出错", e);
//                        throw new BizException("E041412312", "文件上传出错");
//                    }
//                }
//                String result = "";
//                try {
//
//                    // 将导入的中文列名匹配至数据库对应字段
//                    int success = 0;
//                    int fail = 0;
//                    StringBuffer errorMsg = new StringBuffer();//如果某行报错了 需要告知哪一行错误
//        //            Map<String, String> colMatch = new HashMap<String, String>();
//        //            colMatch.put("姓名", "name");
//        //            colMatch.put("单位", "org");
//        //            colMatch.put("邮箱", "email");
//
//
//                    List<Map<String, String>> list = ExcelUtil.getExcelData(xlsFile);//excel 转成 list数据
//                    for (int i = 0; i < list.size(); i++) {
//
//                        Map<String, String> map = list.get(i);
//                        String email = MapUtils.getStringValue(map, "邮箱");
//                        // 检验手机号是否符合规范,不符合continue
//                        if (!StringUtil.isEmail(email)) {
//        //                    throw new ValidException("E2000016", MessageUtil.getMessage("E2000016", telphone));// 手机号码不符合一般格式。
//                            logger.info(" import conf ==> the telphone:" + email + " is not email");
//                            fail++;
//                            errorMsg.append("" + email + " 不是邮箱地址;");
//                            continue;
//                        }
//                        HashMap params = new HashMap();
//                        params.put("email", email);
//                      //  int count = contactsService.countByParams(params);//检查邮箱地址是否存在
//
//                        SysOrg bean = getInfoFromMap(params);
//
//                      //  if (count > 0) {
//
//                      //      logger.warn("邮箱已经存在:" + email);
//                       //     errorMsg.append("邮箱已经存在:" + email);
//                         //   continue;
//
//                       // }
//
//                        try {
//                            sysOrgService.save(bean);
//                            success++;//成功数增加
//                        } catch (Exception e) {
//
//                            fail++;//失败数增加
//                            logger.info("packageservice import conf ==> update fail ==>the telphone:" + email + "", e);
//                            errorMsg.append("the telphone:" + email + " update fail;");
//                        }
//
//                    }
//                    if (StringUtil.isNotBlank(errorMsg.toString()) && fail > 0) {
//                        throw new BizException("E2000016", "导入失败, 失败" + fail + "条。" + errorMsg.toString());
//                    }
//                    return this.getResult(3090182,"导入完成，成功导入" + success + "条，失败" + fail + "条。" + errorMsg.toString());
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    logger.error("导入失败", e);
//                    if (e instanceof org.apache.poi.poifs.filesystem.OfficeXmlFileException) {
//                        throw new BizException("E041412313", "导入的excel需为2003版本");
//                    } else {
//                        throw new BizException("E041412313", e.getMessage());
//                    }
//                }
//
//
//    }
//
//
//      /**
//         * 说明: 跳转到SysOrg列表页面
//         *
//         * @return
//         * @return String
//         * @author dozen.zhang
//         * @date 2015年11月15日下午12:30:45
//         */
//        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
//        public String listHtml() {
//            return "/static/html/SysOrgList.html";
//        }
//
//        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
//        public String listMapperHtml() {
//            return "/static/html/SysOrgListMapper.html";
//        }
//
//
//    /**
//     * 说明:跳转编辑页面
//     * @param request 发请求
//     * @return String
//     * @author dozen.zhang
//     * @date 2019-4-3 10:57:26
//     */
//    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
//    public String editHtml( HttpServletRequest request) {
//        // 查找所有的角色
//        return "/static/html/SysOrgEdit.html";
//    }
//    /**
//         * 说明:跳转查看页面
//         * @param request 发请求
//         * @return String
//         * @author dozen.zhang
//         * @date 2019-4-3 10:57:26
//         */
//    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
//    public String viewHtml( HttpServletRequest request) {
//        return "/static/html/SysOrgView.html";
//    }
//


    private SysOrg getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
        SysOrg sysOrg = new SysOrg();

        String id = MapUtils.getString(bodyParam, "id");
        if (!StringUtil.isBlank(id)) {
            sysOrg.setId(Long.valueOf(id));
        }
        String name = MapUtils.getString(bodyParam, "name");
        if (!StringUtil.isBlank(name)) {
            sysOrg.setName(String.valueOf(name));
        }
        String code = MapUtils.getString(bodyParam, "code");
        if (!StringUtil.isBlank(code)) {
            sysOrg.setCode(String.valueOf(code));
        }
        String status = MapUtils.getString(bodyParam, "status");
        if (!StringUtil.isBlank(status)) {
            sysOrg.setStatus(Integer.valueOf(status));
        }
        String remark = MapUtils.getString(bodyParam, "remark");
        if (!StringUtil.isBlank(remark)) {
            sysOrg.setRemark(String.valueOf(remark));
        }
        String createTime = MapUtils.getString(bodyParam, "createTime");
        if (!StringUtil.isBlank(createTime)) {
            if (StringUtil.checkNumeric(createTime)) {
                sysOrg.setCreateTime(Timestamp.valueOf(createTime));
            } else if (StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")) {
                sysOrg.setCreateTime(new Timestamp(DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = MapUtils.getString(bodyParam, "updateTime");
        if (!StringUtil.isBlank(updateTime)) {
            if (StringUtil.checkNumeric(updateTime)) {
                sysOrg.setUpdateTime(Timestamp.valueOf(updateTime));
            } else if (StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")) {
                sysOrg.setUpdateTime(new Timestamp(DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String userId = MapUtils.getString(bodyParam, "userId");
        if (!StringUtil.isBlank(userId)) {
            sysOrg.setUserId(Long.valueOf(userId));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "编号", new Rule[]{new Digits(15, 0)});
        vu.add("name", name, "公司名称", new Rule[]{new Length(20)});
        vu.add("code", code, "编号", new Rule[]{new Length(20)});
        vu.add("status", status, "状态", new Rule[]{new Digits(1, 0), new CheckBox(new String[]{"1", "2", "3", "9"})});
        vu.add("remark", remark, "备注", new Rule[]{new Length(200)});
        vu.add("createTime", createTime, "创建时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updateTime", updateTime, "更新时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("userId", userId, "创建人", new Rule[]{new Digits(7, 0)});
        validStr = vu.validateString();


        if (StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return sysOrg;
    }
//
//

    /**
     * 说明:添加SysOrg信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2019-4-3 10:57:26
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API(summary = "添加单个系统公司信息",
            description = "添加单个系统公司信息",
            parameters = {
                    @Param(name = "body", description = "参数", schema = "{\"name\":\"公司名\",\"code\":\"公司编码\",\"remark\":\"备注\"}", in = InType.body, dataType = DataType.STRING, required = true),
//                    @Param(name = "id", description = "编号", in = InType.body, dataType = DataType.LONG, required = false),
//                    @Param(name = "name", description = "公司名称", in = InType.body, dataType = DataType.STRING, required = false),
//                    @Param(name = "code", description = "编号", in = InType.body, dataType = DataType.STRING, required = false),
//                    @Param(name = "status", description = "状态", in = InType.body, dataType = DataType.INTEGER, required = true),
//                    @Param(name = "remark", description = "备注", in = InType.body, dataType = DataType.STRING, required = false),
//                    @Param(name = "createTime", description = "创建时间", in = InType.body, dataType = DataType.DATE_TIME, required = false),
//                    @Param(name = "updateTime", description = "更新时间", in = InType.body, dataType = DataType.DATE_TIME, required = false),
//                    @Param(name = "userId", description = "创建人", in = InType.body, dataType = DataType.LONG, required = false),
            })
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO saveInBody(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        SysOrg sysOrg = getInfoFromMap(bodyParam);
        sysOrg.setStatus(1);
        return sysOrgService.save(sysOrg);
    }
//

    /**
     * 说明:添加SysOrg信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2019-4-3 10:57:26
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API(summary = "更新单个系统公司信息",
            description = "更新单个系统公司信息",
            parameters = {
                    @Param(name = "body", description = "参数", schema = "{\"id\":1,\"name\":\"公司名\",\"code\":\"公司编码\",\"remark\":\"备注\"}", in = InType.body, dataType = DataType.STRING, required = true),
//                    @Param(name = "id", description = "编号  ", in = InType.body, dataType = DataType.LONG, required = false),
//                    @Param(name = "name", description = "公司名称  ", in = InType.body, dataType = DataType.STRING, required = false),
//                    @Param(name = "code", description = "编号  ", in = InType.body, dataType = DataType.STRING, required = false),
//                    @Param(name = "status", description = "状态  ", in = InType.body, dataType = DataType.INTEGER, required = true),
//                    @Param(name = "remark", description = "备注  ", in = InType.body, dataType = DataType.STRING, required = false),
//                    @Param(name = "createTime", description = "创建时间  ", in = InType.body, dataType = DataType.DATE_TIME, required = false),
//                    @Param(name = "updateTime", description = "更新时间  ", in = InType.body, dataType = DataType.DATE_TIME, required = false),
//                    @Param(name = "userId", description = "创建人  ", in = InType.body, dataType = DataType.LONG, required = false),
            })
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        SysOrg sysOrg = getInfoFromMap(bodyParam);
        sysOrg.setStatus(1);
        return sysOrgService.save(sysOrg);

    }

    @API(summary = "更新单个系统公司信息",
            description = "更新单个系统公司信息",
            parameters = {
                    @Param(name = "id", description = "编号  ", in = InType.path, dataType = DataType.LONG, required = false),
                    @Param(name = "body", description = "参数", schema = "{\"id\":1,\"name\":\"公司名\",\"code\":\"公司编码\",\"remark\":\"备注\"}", in = InType.body, dataType = DataType.STRING, required = true),
//                    @Param(name = "id", description = "编号  ", in = InType.body, dataType = DataType.LONG, required = false),
//                    @Param(name = "name", description = "公司名称  ", in = InType.body, dataType = DataType.STRING, required = false),
//                    @Param(name = "code", description = "编号  ", in = InType.body, dataType = DataType.STRING, required = false),
//                    @Param(name = "status", description = "状态  ", in = InType.body, dataType = DataType.INTEGER, required = true),
//                    @Param(name = "remark", description = "备注  ", in = InType.body, dataType = DataType.STRING, required = false),
//                    @Param(name = "createTime", description = "创建时间  ", in = InType.body, dataType = DataType.DATE_TIME, required = false),
//                    @Param(name = "updateTime", description = "更新时间  ", in = InType.body, dataType = DataType.DATE_TIME, required = false),
//                    @Param(name = "userId", description = "创建人  ", in = InType.body, dataType = DataType.LONG, required = false),
            })
    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO update(HttpServletRequest request, @PathVariable("id") Long id, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        SysOrg sysOrg = getInfoFromMap(bodyParam);
        sysOrg.setStatus(1);
        return sysOrgService.save(sysOrg);

    }

    /**
     * 说明:ajax请求SysOrg信息
     *
     * @return String
     * @author dozen.zhang
     * @date 2019-4-3 10:57:26
     */
    @API(summary = "系统公司列表接口",
            description = "系统公司列表接口",
            parameters = {

                    @Param(name = "params", description = "{\"pageSize\":15123123,\"curPage\":1,\"name\":\"公司名\",code:\"公司编码\",remark:\"备注\"}", in = InType.query, dataType = DataType.STRING, required = true),
//                    @Param(name = "pageSize", description = "分页大小", in = InType.params, dataType = DataType.INTEGER, required = true),
//
//                    @Param(name = "pageSize", description = "分页大小", in = InType.params, dataType = DataType.INTEGER, required = true),
//                    @Param(name = "curPage", description = "当前页", in = InType.params, dataType = DataType.INTEGER, required = true),
//                    @Param(name = "id", description = "编号  ", in = InType.params, dataType = DataType.LONG, required = false),// false
//                    @Param(name = "name", description = "公司名称  ", in = InType.params, dataType = DataType.STRING, required = false),// false
//                    @Param(name = "code", description = "编号  ", in = InType.params, dataType = DataType.STRING, required = false),// false
//                    @Param(name = "status", description = "状态  ", in = InType.params, dataType = DataType.INTEGER, required = false),// true
//                    @Param(name = "remark", description = "备注  ", in = InType.params, dataType = DataType.STRING, required = false),// false
//                    @Param(name = "createTime", description = "创建时间  ", in = InType.params, dataType = DataType.DATE_TIME, required = false),// false
//                    @Param(name = "updateTime", description = "更新时间  ", in = InType.params, dataType = DataType.DATE_TIME, required = false),// false
//                    @Param(name = "userId", description = "创建人  ", in = InType.params, dataType = DataType.LONG, required = false),// false
            })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO list(HttpServletRequest request, @RequestParam(name = "params", required = true) String paramStr) throws Exception {

        HashMap<String, Object> params = JsonUtil.fromJson(paramStr, HashMap.class);
        Page page = RequestUtil.getPage(params);
        if (page == null) {
            return this.getWrongResultFromCfg("err.param.page");
        }

        String id = MapUtils.getString(params, "id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String name = MapUtils.getString(params, "name");
        if (!StringUtil.isBlank(name)) {
            params.put("name", name);
        }
        String nameLike = MapUtils.getString(params, "nameLike");
        if (!StringUtil.isBlank(nameLike)) {
            params.put("nameLike", nameLike);
        }
        String code = MapUtils.getString(params, "code");
        if (!StringUtil.isBlank(code)) {
            params.put("code", code);
        }
        String codeLike = MapUtils.getString(params, "codeLike");
        if (!StringUtil.isBlank(codeLike)) {
            params.put("codeLike", codeLike);
        }
        String status = MapUtils.getString(params, "status");
        if (!StringUtil.isBlank(status)) {
            params.put("status", status);
        }
        String remark = MapUtils.getString(params, "remark");
        if (!StringUtil.isBlank(remark)) {
            params.put("remark", remark);
        }
        String remarkLike = MapUtils.getString(params, "remarkLike");
        if (!StringUtil.isBlank(remarkLike)) {
            params.put("remarkLike", remarkLike);
        }
        String createTime = MapUtils.getString(params, "createTime");
        if (!StringUtil.isBlank(createTime)) {
            if (StringUtil.checkNumeric(createTime)) {
                params.put("createTime", createTime);
            } else if (StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createTime", new Timestamp(DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = MapUtils.getString(params, "createTimeBegin");
        if (!StringUtil.isBlank(createTimeBegin)) {
            if (StringUtil.checkNumeric(createTimeBegin)) {
                params.put("createTimeBegin", createTimeBegin);
            } else if (StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createTimeBegin", new Timestamp(DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = MapUtils.getString(params, "createTimeEnd");
        if (!StringUtil.isBlank(createTimeEnd)) {
            if (StringUtil.checkNumeric(createTimeEnd)) {
                params.put("createTimeEnd", createTimeEnd);
            } else if (StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createTimeEnd", new Timestamp(DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTime = MapUtils.getString(params, "updateTime");
        if (!StringUtil.isBlank(updateTime)) {
            if (StringUtil.checkNumeric(updateTime)) {
                params.put("updateTime", updateTime);
            } else if (StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updateTime", new Timestamp(DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeBegin = MapUtils.getString(params, "updateTimeBegin");
        if (!StringUtil.isBlank(updateTimeBegin)) {
            if (StringUtil.checkNumeric(updateTimeBegin)) {
                params.put("updateTimeBegin", updateTimeBegin);
            } else if (StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updateTimeBegin", new Timestamp(DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updateTimeEnd = MapUtils.getString(params, "updateTimeEnd");
        if (!StringUtil.isBlank(updateTimeEnd)) {
            if (StringUtil.checkNumeric(updateTimeEnd)) {
                params.put("updateTimeEnd", updateTimeEnd);
            } else if (StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updateTimeEnd", new Timestamp(DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String userId = MapUtils.getString(params, "userId");
        if (!StringUtil.isBlank(userId)) {
            params.put("userId", userId);
        }

        params.put("page", page);
        List<SysOrg> sysOrgs = sysOrgService.listByParams4Page(params);
        return ResultUtil.getResult(sysOrgs, page);
    }

//
//
//       /**
//         * 导出
//         * @param request
//         * @return
//         * @author dozen.zhang
//         */
//        @API(summary="系统公司列表导出接口",
//          description="系统公司列表导出接口",
//          parameters={
//          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
//          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
//             @Param(name="id" , description="编号 ",in=InType.params,dataType = DataType.LONG,required =false),// false
//             @Param(name="name" , description="公司名称 ",in=InType.params,dataType = DataType.STRING,required =false),// false
//             @Param(name="code" , description="编号 ",in=InType.params,dataType = DataType.STRING,required =false),// false
//             @Param(name="status" , description="状态 ",in=InType.params,dataType = DataType.INTEGER,required =false),// true
//             @Param(name="remark" , description="备注 ",in=InType.params,dataType = DataType.STRING,required =false),// false
//             @Param(name="createTime" , description="创建时间 ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
//             @Param(name="updateTime" , description="更新时间 ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
//             @Param(name="userId" , description="创建人 ",in=InType.params,dataType = DataType.LONG,required =false),// false
//          })
//        @RequestMapping(value = "/export", method = RequestMethod.GET)
//        @ResponseBody
//        public ResultDTO exportExcelInBody(HttpServletRequest request,@RequestParam(name = "params", required = true) String paramStr ) throws Exception{
//
//             HashMap<String, Object> params = JsonUtil.fromJson(paramStr, HashMap.class);
//              Page page = RequestUtil.getPage(params);
//             if(page ==null){
//                  return this.getWrongResultFromCfg("err.param.page");
//             }
//
//                     String id = MapUtils.getString(params,"id");
//        if(!StringUtil.isBlank(id)){
//            params.put("id",id);
//        }
//        String name = MapUtils.getString(params,"name");
//        if(!StringUtil.isBlank(name)){
//            params.put("name",name);
//        }
//        String nameLike = MapUtils.getString(params,"nameLike");
//        if(!StringUtil.isBlank(nameLike)){
//            params.put("nameLike",nameLike);
//        }
//        String code = MapUtils.getString(params,"code");
//        if(!StringUtil.isBlank(code)){
//            params.put("code",code);
//        }
//        String codeLike = MapUtils.getString(params,"codeLike");
//        if(!StringUtil.isBlank(codeLike)){
//            params.put("codeLike",codeLike);
//        }
//        String status = MapUtils.getString(params,"status");
//        if(!StringUtil.isBlank(status)){
//            params.put("status",status);
//        }
//        String remark = MapUtils.getString(params,"remark");
//        if(!StringUtil.isBlank(remark)){
//            params.put("remark",remark);
//        }
//        String remarkLike = MapUtils.getString(params,"remarkLike");
//        if(!StringUtil.isBlank(remarkLike)){
//            params.put("remarkLike",remarkLike);
//        }
//        String createTime = MapUtils.getString(params,"createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                params.put("createTime",createTime);
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String createTimeBegin = MapUtils.getString(params,"createTimeBegin");
//        if(!StringUtil.isBlank(createTimeBegin)){
//            if(StringUtil.checkNumeric(createTimeBegin)){
//                params.put("createTimeBegin",createTimeBegin);
//            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String createTimeEnd = MapUtils.getString(params,"createTimeEnd");
//        if(!StringUtil.isBlank(createTimeEnd)){
//            if(StringUtil.checkNumeric(createTimeEnd)){
//                params.put("createTimeEnd",createTimeEnd);
//            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTime = MapUtils.getString(params,"updateTime");
//        if(!StringUtil.isBlank(updateTime)){
//            if(StringUtil.checkNumeric(updateTime)){
//                params.put("updateTime",updateTime);
//            }else if(StringUtil.checkDateStr(updateTime, "yyyy-MM-dd HH:mm:ss")){
//                params.put("updateTime",new Timestamp( DateUtil.parseToDate(updateTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTimeBegin = MapUtils.getString(params,"updateTimeBegin");
//        if(!StringUtil.isBlank(updateTimeBegin)){
//            if(StringUtil.checkNumeric(updateTimeBegin)){
//                params.put("updateTimeBegin",updateTimeBegin);
//            }else if(StringUtil.checkDateStr(updateTimeBegin, "yyyy-MM-dd HH:mm:ss")){
//                params.put("updateTimeBegin",new Timestamp( DateUtil.parseToDate(updateTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String updateTimeEnd = MapUtils.getString(params,"updateTimeEnd");
//        if(!StringUtil.isBlank(updateTimeEnd)){
//            if(StringUtil.checkNumeric(updateTimeEnd)){
//                params.put("updateTimeEnd",updateTimeEnd);
//            }else if(StringUtil.checkDateStr(updateTimeEnd, "yyyy-MM-dd HH:mm:ss")){
//                params.put("updateTimeEnd",new Timestamp( DateUtil.parseToDate(updateTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String userId = MapUtils.getString(params,"userId");
//        if(!StringUtil.isBlank(userId)){
//            params.put("userId",userId);
//        }
//
//             params.put("page",page);
//             List<SysOrg> list = sysOrgService.listByParams4Page(params);
//            // 存放临时文件
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            headers.setContentDispositionFormData("attachment", "list.xlsx");
//              String randomName = DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS")+".xlsx";
//
//            String folder = request.getSession().getServletContext()
//                    .getRealPath("/")
//                    + "xlstmp";
//
//
//            File folder_file = new File(folder);
//            if (!folder_file.exists()) {
//                folder_file.mkdir();
//            }
//            String fileName = folder + File.separator
//                      + randomName;
//            // 得到导出Excle时清单的英中文map
//            LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
//            colTitle.put("id", "编号");
//            colTitle.put("name", "公司名称");
//            colTitle.put("code", "编号");
//            colTitle.put("status", "状态");
//            colTitle.put("remark", "备注");
//            colTitle.put("createTime", "创建时间");
//            colTitle.put("updateTime", "更新时间");
//            colTitle.put("userId", "创建人");
//            List<Map> finalList = new ArrayList<Map>();
//            for (int i = 0; i < list.size(); i++) {
//                SysOrg sm = list.get(i);
//                HashMap<String,Object> map = new HashMap<String,Object>();
//                map.put("id",  list.get(i).getId());
//                map.put("name",  list.get(i).getName());
//                map.put("code",  list.get(i).getCode());
//                map.put("status",  list.get(i).getStatus());
//                map.put("remark",  list.get(i).getRemark());
//                map.put("createTime",  list.get(i).getCreateTime());
//                map.put("updateTime",  list.get(i).getUpdateTime());
//                map.put("userId",  list.get(i).getUserId());
//                finalList.add(map);
//            }
//            try {
//                if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
//                    return this.getResult(SUCC,SysConfig.PATH+"/xlstmp/"+randomName,"导出成功");
//                }
//                /*
//                 * return new ResponseEntity<byte[]>(
//                 * FileUtils.readFileToByteArray(new File(fileName)), headers,
//                 * HttpStatus.CREATED);
//                 */
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return this.getResult(0, "数据为空，导出失败");
//
//        }

}
