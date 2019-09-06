/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysRole.action;
import com.dozenx.common.util.*;
import com.dozenx.swagger.annotation.*;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.annotation.RequiresPermission;
import com.dozenx.web.core.auth.sysRole.bean.SysRole;
import com.dozenx.web.core.auth.sysRole.service.SysRoleService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ErrorMessage;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;
@APIs(description = "角色模块")
@Controller
@RequestMapping(Constants.WEBROOT+"/sys/auth/role")
public class SysRoleController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysRoleController.class);
    /** 权限service **/
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 说明: 跳转到角色列表页面
     *
     * @return
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String list() {
        return "/static/html/SysRoleList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/SysRoleListMapper.html";
    }

    /**
     * 说明:ajax请求角色信息
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午12:31:55
     */
    @RequestMapping(value = "/list.json")
    @ResponseBody
    public Object list(HttpServletRequest request) {
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
        String orderNo = request.getParameter("orderNo");
        if(!StringUtil.isBlank(orderNo)){
            params.put("orderNo",orderNo);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                params.put("createtime",createtime);
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtime",new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if(!StringUtil.isBlank(createtimeBegin)){
            if(StringUtil.checkNumeric(createtimeBegin)){
                params.put("createtimeBegin",createtimeBegin);
            }else if(StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeBegin",new Timestamp( DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if(!StringUtil.isBlank(createtimeEnd)){
            if(StringUtil.checkNumeric(createtimeEnd)){
                params.put("createtimeEnd",createtimeEnd);
            }else if(StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeEnd",new Timestamp( DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page",page);
        List<SysRole> sysRoles = sysRoleService.listByParams4Page(params);
        return ResultUtil.getResult(sysRoles, page);
    }

    /**
     * 说明:ajax请求角色信息 无分页版本
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午12:31:55
     */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public Object listAll(HttpServletRequest request) {
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
        String orderNo = request.getParameter("orderNo");
        if(!StringUtil.isBlank(orderNo)){
            params.put("orderNo",orderNo);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                params.put("createtime",createtime);
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtime",new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if(!StringUtil.isBlank(createtimeBegin)){
            if(StringUtil.checkNumeric(createtimeBegin)){
                params.put("createtimeBegin",createtimeBegin);
            }else if(StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeBegin",new Timestamp( DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if(!StringUtil.isBlank(createtimeEnd)){
            if(StringUtil.checkNumeric(createtimeEnd)){
                params.put("createtimeEnd",createtimeEnd);
            }else if(StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeEnd",new Timestamp( DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        List<SysRole> sysRoles = sysRoleService.listByParams(params);
        return ResultUtil.getDataResult(sysRoles);
    }

    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/SysRoleEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/SysRoleView.html";
    }

    @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
        String id = request.getParameter("id");
        HashMap<String,Object> result =new HashMap<String,Object>();
        if(!StringUtil.isBlank(id)){
            SysRole bean = sysRoleService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        SysRole bean = sysRoleService.selectByPrimaryKey(Long.valueOf(id));
        HashMap<String,Object> result =new HashMap<String,Object>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }


    /**
     * 说明:保存角色信息
     *
     * @param request
     * @return
     * @throws Exception
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:00
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public Object save(HttpServletRequest request) throws Exception {
        SysRole sysRole =new  SysRole();

        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysRole.setId(Long.valueOf(id));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            sysRole.setName(name);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            sysRole.setCode(code);
        }
        String orderNo = request.getParameter("orderNo");
        if(!StringUtil.isBlank(orderNo)){
            sysRole.setOrderNo(Integer.valueOf(orderNo));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            sysRole.setStatus(Integer.valueOf(status));
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            sysRole.setRemark(remark);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                sysRole.setCreatetime(Timestamp.valueOf(createtime));
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                sysRole.setCreatetime(new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(10,0)});
        vu.add("name", name, "角色名",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("code", code, "角色代码",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("orderNo", orderNo, "排序",  new Rule[]{new Digits(11,0),new NotEmpty()});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2"}),new NotEmpty()});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(255)});
        vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return sysRoleService.save(sysRole);

    }

    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        sysRoleService.delete(id);
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
    public Object multiDelete(HttpServletRequest request) {
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
        return  sysRoleService.multilDelete(idAry);
    }

    /**
     * 导出
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/export.json")
    @ResponseBody
    public Object exportExcel(HttpServletRequest request){
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
        String orderNo = request.getParameter("orderNo");
        if(!StringUtil.isBlank(orderNo)){
            params.put("orderNo",orderNo);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                params.put("createtime",createtime);
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtime",new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if(!StringUtil.isBlank(createtimeBegin)){
            if(StringUtil.checkNumeric(createtimeBegin)){
                params.put("createtimeBegin",createtimeBegin);
            }else if(StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeBegin",new Timestamp( DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if(!StringUtil.isBlank(createtimeEnd)){
            if(StringUtil.checkNumeric(createtimeEnd)){
                params.put("createtimeEnd",createtimeEnd);
            }else if(StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeEnd",new Timestamp( DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        // 查询list集合
        List<SysRole> list =sysRoleService.listByParams(params);
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
        colTitle.put("name", "角色名");
        colTitle.put("code", "角色代码");
        colTitle.put("orderNo", "排序");
        colTitle.put("status", "状态");
        colTitle.put("remark", "备注");
        colTitle.put("createtime", "创建时间");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SysRole sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("name",  list.get(i).getName());
            map.put("code",  list.get(i).getCode());
            map.put("orderNo",  list.get(i).getOrderNo());
            map.put("status",  list.get(i).getStatus());
            map.put("remark",  list.get(i).getRemark());
            map.put("createtime",  list.get(i).getCreateTime());
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
     * 说明:ajax请求角色信息
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午12:31:55
     */
    @API(summary = "角色列表接口",
            consumes = "application/x-www-form-urlencoded",
            description = "sysUserController 角色列表分页查询接口", parameters = {

            @Param(name = "params", description = "{\"curPage\":1,\"pageSize\":20,\"code\":\"\",\"name\":\"\"}", dataType = DataType.STRING, in="query",required = true),
    })
    @APIResponse(value = "{\"r\":0,\"data\":[{\"id\":333,\"name\":\"ccc\",\"code\":\"ccc\",\"orderNo\":1,\"remark\":\"ccc\"},{\"id\":123123,\"name\":\"管理员1\",\"code\":\"role_admin\",\"orderNo\":1,\"remark\":\"管理员1\"}],\"page\":{\"curPage\":1,\"totalPage\":1,\"pageSize\":20,\"totalCount\":14,\"beginIndex\":0,\"hasPrePage\":false,\"hasNextPage\":false},\"right\":true}")
    @RequestMapping(value = "/list",method=RequestMethod.GET,produces="application/json")
    @RequiresPermission
    @ResponseBody
    public Object listRestful(HttpServletRequest request,@RequestParam(name="params",required=true) String paramStr) {
        Map<String,Object> params = JsonUtil.fromJson(paramStr,Map.class);
        Page page =  RequestUtil.getPage(params);
        params.put("page",page);
//        String name = MapUtils.getString(params,"name");
//        if(StringUtils.isNotEmpty(name)){//支持模糊查询
//            params.remove("name");
//            params.put("nameLike",name);
//        }
//        String code = MapUtils.getString(params,"code");
//        if(StringUtils.isNotEmpty(code)){//支持模糊查询
//            params.remove("code");
//            params.put("codeLike",code);
//        }
        List<SysRole> sysRoles = sysRoleService.listByParams4Page(params);
        return ResultUtil.getResult(sysRoles, page);
    }

    /**
     * 说明:ajax请求角色信息
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午12:31:55
     */
    @API(summary = "角色列表接口",
            consumes = "application/x-www-form-urlencoded",
            description = "sysUserController 角色列表分页查询接口", parameters = {

            @Param(name = "params", description = "{\"curPage\":1,\"pageSize\":20,\"code\":\"\",\"name\":\"\"}", dataType = DataType.STRING, in="query",required = true),
    })
    @APIResponse(value = "{\"r\":0,\"data\":[{\"id\":333,\"name\":\"ccc\",\"code\":\"ccc\",\"orderNo\":1,\"remark\":\"ccc\"},{\"id\":123123,\"name\":\"管理员1\",\"code\":\"role_admin\",\"orderNo\":1,\"remark\":\"管理员1\"}],\"page\":{\"curPage\":1,\"totalPage\":1,\"pageSize\":20,\"totalCount\":14,\"beginIndex\":0,\"hasPrePage\":false,\"hasNextPage\":false},\"right\":true}")
    @RequestMapping(value = "/drop/list",method=RequestMethod.GET,produces="application/json")
    @ResponseBody
    public Object droplistRestful(HttpServletRequest request) {
        List<SysRole> sysRoles = sysRoleService.listByParams(new HashMap<String,Object>());
        return ResultUtil.getDataResult(sysRoles);
    }


    /**
     * 说明:添加角色信息
     *
     * @param request
     * @return
     * @throws Exception
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:00
     */

    @API(summary = "角色添加接口",
            consumes = "application/json",
            description = "sysRoleController 角色添加接口", parameters = {
            @Param(name = "name", description = "角色名"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "code", description = "角色代号"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "orderNo", description = "排序号"
                    , dataType = DataType.LONG, in="body",required = false),
            @Param(name = "remark", description = "角色备注"
                    , dataType = DataType.STRING, in="body",required = true),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/add",method=RequestMethod.POST,produces="application/json")
    @RequiresPermission
    @ResponseBody
    public Object add(HttpServletRequest request,@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception {
        SysRole sysRole =new  SysRole();

        String name = MapUtils.getString(bodyParam,"name");
        if(!StringUtil.isBlank(name)){
            sysRole.setName(name);
        }
        String code = MapUtils.getString(bodyParam,"code");

        if(!StringUtil.isBlank(code)){
            sysRole.setCode(code);
        }
        Integer orderNo = MapUtils.getInteger(bodyParam,"orderNo");
        if(orderNo!=null){
            sysRole.setOrderNo(orderNo);
        }
        sysRole.setStatus(0);
        sysRole.setCreatetime(DateUtil.getNowTimeStamp());
        String remark = MapUtils.getString(bodyParam,"remark");
        if(!StringUtil.isBlank(remark)){
            sysRole.setRemark(remark);
        }
        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";

        vu.add("name", name, "角色名",  new Rule[]{new Length(20),new NotEmpty(),new Regex(RegexConstants.ZHONGWEN_ALPHA_NUMBER_PATTERN)});
        vu.add("code", code, "角色代码",  new Rule[]{new Length(20),new NotEmpty(),new Regex(RegexConstants.ALPHA_NUMBER_PATTERN)});
        vu.add("orderNo", orderNo, "排序",  new Rule[]{new Digits(3,0)});//考虑到角色没必要强制排序 将从修改中移除此项目 2018年2月24日09:05:59 ,new NotEmpty()

        vu.add("remark", remark, "备注",  new Rule[]{new Length(255),new CharRegex()});
        //vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return sysRoleService.save(sysRole);

    }

    @API(summary = "角色更新接口",
            consumes = "application/json",
            description = "sysRoleController 角色添加接口", parameters = {
            @Param(name = "id", description = "角色id"
                    , dataType = DataType.LONG, in="body",required = true),
            @Param(name = "name", description = "角色名"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "orderNo", description = "排序号"
                    , dataType = DataType.LONG, in="body",required = false),
            @Param(name = "code", description = "角色代号"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "remark", description = "角色备注"
                    , dataType = DataType.STRING, in="body",required = true),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/update/{id}",method=RequestMethod.PUT,produces="application/json")
    @RequiresPermission
    @ResponseBody

    public Object update(HttpServletRequest request,@PathVariable Long id,@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception {
        SysRole sysRole =new  SysRole();
         id   = MapUtils.getLong(bodyParam,"id");
        if(id==null || id ==0){
            return this.getResult(10202001,ErrorMessage.getErrorMsg("err.param.null","id"));
        }

        sysRole.setId(id);
        String name = MapUtils.getString(bodyParam,"name");
        if(!StringUtil.isBlank(name)){
            sysRole.setName(name);
        }
        String code = MapUtils.getString(bodyParam,"code");

        if(!StringUtil.isBlank(code)){
            sysRole.setCode(code);
        }
        Integer orderNo = MapUtils.getInteger(bodyParam,"orderNo");
        if(orderNo!=null){
            sysRole.setOrderNo(orderNo);
        }
        sysRole.setStatus(0);
        sysRole.setCreatetime(DateUtil.getNowTimeStamp());
        String remark = MapUtils.getString(bodyParam,"remark");
        if(!StringUtil.isBlank(remark)){
            sysRole.setRemark(remark);
        }
        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("name", name, "角色名",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("code", code, "角色代码",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("orderNo", orderNo, "排序",  new Rule[]{new Digits(3,0)});//考虑到角色没必要强制排序 将从修改中移除此项目 2018年2月24日09:05:59 ,new NotEmpty()
        vu.add("remark", remark, "备注",  new Rule[]{new Length(255)});
        //vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return sysRoleService.save(sysRole);

    }

    @API(summary = "角色详情接口",
            consumes = "application/json",
            description = "角色详情接口", parameters = {
            @Param(name = "id", description = "/view/{id}", dataType = DataType.STRING, in="path",required = true),
    })
    @APIResponse(value = "{ \"r\": 0, \"data\": { \"id\": 123, \"username\": \"123\", \"password\": \"123\", \"nkname\": \"123\", \"status\": 1, \"telno\": \"13969696969\", \"idcard\": \"23\", \"sex\": 0, \"birth\": \"Feb 1, 2018 12:00:00 AM\", \"integral\": 123, \"address\": \"123\", \"wechat\": \"123\", \"qq\": 123, \"face\": \"static/img/timg.jpeg\", \"remark\": \"123\", \"createTime\": \"Feb 6, 2018 3:23:10 PM\", \"updateTime\": \"Feb 6, 2018 3:23:10 PM\" } }")
    @RequestMapping(value = "/view/{id}" ,method=RequestMethod.GET,produces="application/json")
    @RequiresPermission
    @ResponseBody
    public Object view(@PathVariable ("id") Long id , HttpServletRequest request) {

        if(id==null || id ==0 ){

            return this.getResult(10202002, ErrorMessage.getErrorMsg("err.param.null","角色id"));
        }
        SysRole bean = sysRoleService.selectByPrimaryKey(id);
        return this.getResult(bean);


    }

    @API(summary = "角色删除接口",

            consumes = "application/x-www-form-urlencoded",
            description = "sysUserController 角色删除接口", parameters = {

            @Param(name = "id", description = "用户id", dataType = DataType.LONG, in="PATH",required = true),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    @RequiresPermission
    @RequestMapping(value = "/del/{id}" ,method=RequestMethod.DELETE,produces="application/json" )
    @ResponseBody
    public Object deleteRestFul(@PathVariable("id") Long id, HttpServletRequest request ) {

        if(id==null ){
            return this.getResult(10202003, ErrorMessage.getErrorMsg("err.param.null","角色id"));
        }

        sysRoleService.delete(id);//将状态为改成9
        return this.getResult(SUCC);
    }
}
