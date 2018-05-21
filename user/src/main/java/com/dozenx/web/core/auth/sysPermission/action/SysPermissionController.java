/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysPermission.action;

import com.cpj.swagger.annotation.*;
import com.dozenx.core.exception.ValidException;
import com.dozenx.util.*;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.annotation.RequiresLogin;
import com.dozenx.web.core.auth.service.AuthService;
import com.dozenx.web.core.auth.sysMenu.bean.SysMenu;
import com.dozenx.web.core.auth.sysPermission.bean.SysPermission;
import com.dozenx.web.core.auth.sysPermission.service.SysPermissionService;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ErrorMessage;
import com.dozenx.web.core.log.ResultDTO;
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
import java.util.*;
@APIs(description = "权限模块")
@Controller
@RequestMapping(Constants.WEBROOT+"/sys/auth/permission")
public class SysPermissionController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysPermissionController.class);
    /** 权限service **/
    @Autowired
    private SysPermissionService sysPermissionService;


    @Autowired
    private AuthService authService;
    
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
        return "/static/html/SysPermissionList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/SysPermissionListMapper.html";
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
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
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

        params.put("page",page);
        List<SysPermission> sysPermissions = sysPermissionService.listByParams4Page(params);
        return ResultUtil.getResult(sysPermissions, page);
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
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
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

        List<SysPermission> sysPermissions = sysPermissionService.listByParams(params);
        return ResultUtil.getDataResult(sysPermissions);
    }
    
    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/SysPermissionEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/SysPermissionView.html";
    }
   
    @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
            String id = request.getParameter("id");
        HashMap<String,Object> result =new HashMap<String,Object>();
        if(!StringUtil.isBlank(id)){
            SysPermission bean = sysPermissionService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        SysPermission bean = sysPermissionService.selectByPrimaryKey(Long.valueOf(id));
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
        SysPermission sysPermission =new  SysPermission();

        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysPermission.setId(Long.valueOf(id));
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            sysPermission.setPid(Long.valueOf(pid));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            sysPermission.setName(name);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            sysPermission.setCode(code);
        }
        String orderNo = request.getParameter("orderNo");
        if(!StringUtil.isBlank(orderNo)){
            sysPermission.setOrderNo(Integer.valueOf(orderNo));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            sysPermission.setStatus(Integer.valueOf(status));
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            sysPermission.setRemark(remark);
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(10,0)});
        vu.add("pid", pid, "父主键",  new Rule[]{new Digits(10,0)});
        vu.add("name", name, "权限名称",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("code", code, "权限代码",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("orderNo", orderNo, "排序id",  new Rule[]{new Digits(11,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2"}),new NotEmpty()});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(20)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return sysPermissionService.save(sysPermission);
       
    }

    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        sysPermissionService.delete(id);
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
       return  sysPermissionService.multilDelete(idAry);
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
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
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

        // 查询list集合
        List<SysPermission> list =sysPermissionService.listByParams(params);
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
        colTitle.put("pid", "父主键");
        colTitle.put("name", "权限名称");
        colTitle.put("code", "权限代码");
        colTitle.put("orderNo", "排序id");
        colTitle.put("status", "状态");
        colTitle.put("remark", "备注");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SysPermission sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("pid",  list.get(i).getPid());
            map.put("name",  list.get(i).getName());
            map.put("code",  list.get(i).getCode());
            map.put("orderNo",  list.get(i).getOrderNo());
            map.put("status",  list.get(i).getStatus());
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
     * 说明:权限列表信息
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午12:31:55
     */

    @API(summary = "权限列表接口",
            consumes = "application/x-www-form-urlencoded",
            description = "sysPermissionController 用户列表分页查询接口", parameters = {

            @Param(name = "params", description = "{name:\'接口名称\', url:\"123\", curPage:1,pageSize:30 }" , dataType = DataType.STRING, in="query",required = true),
    })
    @APIResponse(value = "{\"r\":0,\"data\":[{\"id\":123,\"name\":\"123\",\"url\":\"123\"}],\"page\":{\"curPage\":1,\"totalPage\":1,\"pageSize\":10,\"totalCount\":1,\"beginIndex\":0,\"hasPrePage\":false,\"hasNextPage\":false}}")

    @RequestMapping(value = "/list",method=RequestMethod.GET,produces="application/json")
    @ResponseBody
    public Object list( HttpServletRequest request,@RequestParam(name="params",required=true) String paramStr) {
        Map<String,Object> params = JsonUtil.fromJson(paramStr,Map.class);
        Page page =  RequestUtil.getPage(params);
        params.put("page",page);
        List<SysPermission> sysPermissions = sysPermissionService.listByParams4Page(params);
        return ResultUtil.getResult(sysPermissions, page);
    }



    @API(summary = "权限列表树状接口",
            consumes = "application/x-www-form-urlencoded",
            description = "sysPermissionController 用户列表分页查询接口", parameters = {

    })
    @APIResponse(value = "{\"r\":0,\"data\":[{\"id\":123,\"name\":\"123\",\"url\":\"123\"}],\"page\":{\"curPage\":1,\"totalPage\":1,\"pageSize\":10,\"totalCount\":1,\"beginIndex\":0,\"hasPrePage\":false,\"hasNextPage\":false}}")

    @RequestMapping(value = "/tree",method=RequestMethod.GET,produces="application/json")
    @ResponseBody
    public Object tree( HttpServletRequest request,@RequestParam(name="params",required=true) String paramStr) {
        Map<String,Object> params = JsonUtil.fromJson(paramStr,Map.class);

        List<SysPermission> sysPermissions = sysPermissionService.listByParams(params);


        List<SysPermission> finalList = new ArrayList<SysPermission>();//最终返回前台的list

        //组装成树状结构
        for(int i=0,length=sysPermissions.size();i<length;i++){//倒序 方便找到后删除
            SysPermission sysPermission = sysPermissions.get(i);

            if(sysPermission.getPid()==0){
                finalList.add(sysPermission);
                sysPermission.childs=new ArrayList<>();
                for(int j=0;j<length;j++){//倒序 方便找到后删除
                    SysPermission child = sysPermissions.get(j);//遍历所有的项目查找所有子项
                    if(child.getPid() == sysPermission.getId()){
                        sysPermission.childs.add(child);//塞入到childs中 并从集合中删除
                        // sysMenuTree.remove(j);
                    }
                }
                // sysMenuTree.remove(i);
            }
        }

        return this.getResult(sysPermissions);
    }




    public SysPermission getParamFromMap(Map<String,Object> bodyParam) throws Exception {
        SysPermission sysPermission =new SysPermission();
        Long id = MapUtils.getLong(bodyParam,"id");
        sysPermission.setId(id);

        Long pid = MapUtils.getLong(bodyParam,"pid");

        if(pid!=null){
            sysPermission.setPid(pid);
        }

        String name = MapUtils.getString(bodyParam,"name");

        if(!StringUtil.isBlank(name)){
            sysPermission.setName(name);
        }

        String code = MapUtils.getString(bodyParam,"code");

        if(!StringUtil.isBlank(code)){
            sysPermission.setCode(code);
        }

        Integer orderNo = MapUtils.getInteger(bodyParam,"orderNo");
        if(orderNo!=null){
            sysPermission.setOrderNo(orderNo);
        }
        Integer status = MapUtils.getInteger(bodyParam,"status");

        if(status!=null){
            sysPermission.setStatus(status);
        }
        String remark = MapUtils.getString(bodyParam,"remark");
        if(!StringUtil.isBlank(remark)){
            sysPermission.setRemark(remark);
        }
        String url = MapUtils.getString(bodyParam,"url");
        if(!StringUtil.isBlank(url)){
            sysPermission.setUrl(url);
        }

        ValidateUtil vu = new ValidateUtil();
        String validStr="";

        vu.add("pid", pid+"", "父主键",  new Rule[]{new Digits(10,0)});
        vu.add("name", name, "权限名称",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("code", code, "权限代码",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("orderNo", orderNo, "排序id",  new Rule[]{new Digits(11,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2","9"})});

        vu.add("url", url, "url",  new Rule[]{new Length(0,250)});

        vu.add("remark", remark, "备注",  new Rule[]{new Length(20)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            throw new ValidException("302",validStr);
        }
        return sysPermission;
    }

    @API(summary = "权限添加接口",

            consumes = "application/json",
            description = "sysUserController 用户添加接口", parameters = {



            @Param(name = "pid", description = "父节点id"
                    , dataType = DataType.LONG, in="body",required = true),
            @Param(name = "name", description = "名称"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "code", description = "编号"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "url", description = "url"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "orderNo", description = "排序标号"
                    , dataType = DataType.LONG, in="body",required = true),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    @RequestMapping(value = "/add",method=RequestMethod.POST,produces="application/json")
    @ResponseBody
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    public Object add(HttpServletRequest request,@RequestBody(required=true) Map<String,Object> bodyParam ) throws Exception {
        SysPermission sysPermission =getParamFromMap(bodyParam);
        return sysPermissionService.save(sysPermission);

    }


    @API(summary = "权限修改接口",
            consumes = "application/json",
            description = "sysUserController 用户添加接口", parameters = {
            @Param(name = "id", description = "id"
                    , dataType = DataType.LONG, in="body",required = true),
            @Param(name = "pid", description = "父节点id"
                    , dataType = DataType.LONG, in="body",required = true),
            @Param(name = "name", description = "名称"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "code", description = "编号"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "url", description = "url"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "orderNo", description = "排序标号"
                    , dataType = DataType.LONG, in="body",required = true),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    @RequestMapping(value = "/update",method=RequestMethod.PUT,produces="application/json")
    @ResponseBody
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    public Object update( HttpServletRequest request,@RequestBody(required=true) Map<String,Object> bodyParam ) throws Exception {
        SysPermission sysPermission =getParamFromMap(bodyParam);
        return sysPermissionService.save(sysPermission);
    }

    @API(summary = "权限删除接口",

            consumes = "application/x-www-form-urlencoded",
            description = " ", parameters = {

            @Param(name = "id", description = "id", dataType = DataType.LONG, in="PATH",required = true),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")

    @RequestMapping(value = "/del/{id}" ,method=RequestMethod.DELETE,produces="application/json")
    @ResponseBody
    public Object deleteRestFul(@PathVariable("id") Long id, HttpServletRequest request ) {

        if(id==null ){
            return this.getResult(10202003, ErrorMessage.getErrorMsg("err.param.null","用户id"));
        }

        sysPermissionService.delete(id);//将状态为改成9
        return this.getResult(SUCC);
    }



    @API(summary = "查看详情接口",

            consumes = "application/x-www-form-urlencoded",
            description = "查看详情接口", parameters = {

            @Param(name = "id", description = "/view/{id}", dataType = DataType.STRING, in="path",required = true),
    })
    @APIResponse(value = "{ \"r\": 0, \"data\": { \"id\": 编号, \"username\": \"123\", \"password\": \"123\", \"nkname\": \"123\", \"status\": 1, \"telno\": \"13969696969\", \"idcard\": \"23\", \"sex\": 0, \"birth\": \"Feb 1, 2018 12:00:00 AM\", \"integral\": 123, \"address\": \"123\", \"wechat\": \"123\", \"qq\": 123, \"face\": \"static/img/timg.jpeg\", \"remark\": \"123\", \"createTime\": \"Feb 6, 2018 3:23:10 PM\", \"updateTime\": \"Feb 6, 2018 3:23:10 PM\" } }")

    @RequestMapping(value = "/view/{id}" ,method=RequestMethod.GET,produces="application/json")
    @ResponseBody
    public Object viewRestFul(@PathVariable ("id") Long id , HttpServletRequest request) {

        HashMap<String,Object> result =new HashMap<String,Object>();
        if(id>0){
            SysPermission bean = sysPermissionService.selectByPrimaryKey(Long.valueOf(id));
            return this.getResult(bean);

        }
        return this.getResult(10102300, ErrorMessage.getErrorMsg("err.param.null","id"));

    }



    /**
     * @Author: dozen.zhang
     * @Description:当前用户树状菜单
     * @Date: 2018/2/8
     */
    @API(summary = "当前用户权限集合",
            consumes = "application/x-www-form-urlencoded",
            description = " ", parameters = { })
    @APIResponse(value = "{\"r\":0,\"data\":[{\"id\":123,\"username\":\"123\",\"password\":\"123\",\"nkname\":\"123\",\"type\":null,\"status\":1,\"email\":null,\"telno\":\"13969696969\",\"idcard\":\"23\",\"sex\":0,\"birth\":1517414400000,\"integral\":123,\"address\":\"123\",\"wechat\":\"123\",\"qq\":123,\"face\":\"static/img/timg.jpeg\",\"remark\":\"123\",\"outId\":null,\"createtime\":1517901790000,\"updatetime\":1517901790000}],\"msg\":null,\"page\":{\"curPage\":1,\"totalPage\":1,\"pageSize\":10,\"totalCount\":1,\"beginIndex\":0,\"hasPrePage\":false,\"hasNextPage\":false},\"other\":null,\"right\":true}")
    @RequiresLogin
    @RequestMapping(value = "/my",method=RequestMethod.GET,produces="application/json")
    @ResponseBody
    public ResultDTO listPermissions(HttpServletRequest request){
      return this.getResult( request.getSession().getAttribute(Constants.SESSION_PERMISSIONS));//塞入到用户session中
    }
}
