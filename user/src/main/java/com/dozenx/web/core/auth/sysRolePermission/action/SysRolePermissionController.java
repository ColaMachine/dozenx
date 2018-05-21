/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysRolePermission.action;

import com.cpj.swagger.annotation.*;
import com.dozenx.util.*;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.auth.sysPermission.bean.SysPermission;
import com.dozenx.web.core.auth.sysPermission.service.SysPermissionService;
import com.dozenx.web.core.auth.sysRolePermission.bean.SysRolePermission;
import com.dozenx.web.core.auth.sysRolePermission.service.SysRolePermissionService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.Digits;
import com.dozenx.web.core.rules.NotEmpty;
import com.dozenx.web.core.rules.Required;
import com.dozenx.web.core.rules.Rule;
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
@APIs(description = "角色权限模块")
@Controller
@RequestMapping(Constants.WEBROOT+"/sys/auth/role/permission")
public class SysRolePermissionController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysRolePermissionController.class);
    /** 权限service **/
    @Autowired
    private SysRolePermissionService sysRolePermissionService;
    @Autowired
    private SysPermissionService sysPermissionService;

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
        return "/static/html/SysRolePermissionList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/SysRolePermissionListMapper.html";
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
        String rid = request.getParameter("rid");
        if(!StringUtil.isBlank(rid)){
            params.put("rid",rid);
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }

        params.put("page",page);
        List<SysRolePermission> sysRolePermissions = sysRolePermissionService.listByParams4Page(params);
        return ResultUtil.getResult(sysRolePermissions, page);
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
        String rid = request.getParameter("rid");
        if(!StringUtil.isBlank(rid)){
            params.put("rid",rid);
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }

        List<SysRolePermission> sysRolePermissions = sysRolePermissionService.listByParams(params);
        return ResultUtil.getDataResult(sysRolePermissions);
    }
    
    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/SysRolePermissionEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/SysRolePermissionView.html";
    }
   


    

    @RequestMapping(value = "/msave.json")
    @ResponseBody
    public Object msave(HttpServletRequest request) throws Exception {
        String rids= request.getParameter("rids");
        String pids= request.getParameter("pids");
        return sysRolePermissionService.msave( rids, pids);
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
        String rid = request.getParameter("rid");
        if(!StringUtil.isBlank(rid)){
            params.put("rid",rid);
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }

        // 查询list集合
        List<SysRolePermission> list =sysRolePermissionService.listByParams(params);
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
        colTitle.put("id", "主键");
        colTitle.put("rid", "角色id");
        colTitle.put("pid", "权限id");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SysRolePermission sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();

            map.put("rid",  list.get(i).getRoleId());
            map.put("pid",  list.get(i).getPermissionId());
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

    @API(summary = "角色权限批量更新接口",

            consumes = "application/json",
            description = "sysUserController 用户添加接口", parameters = {


            @Param(name = "roleId", description = "角色id"
                    , dataType = DataType.LONG, in="body",required = true),
            @Param(name = "permissionIds", description = "权限id数组"
                    , dataType = DataType.ARRAY, in="body",required = true),
    })

    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    @RequestMapping(value = "/update",method=RequestMethod.PUT,produces="application/json")
    @ResponseBody
    public Object update(HttpServletRequest request,@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception {
        Long roleId = MapUtils.getLong(bodyParam,"roleId");
        ValidateUtil.valid(roleId,"userId",new Rule[]{new Required(),new Digits(10,0)});

        Object obj = bodyParam.get("permissionIds");
        Long[] permissionIds;
        if(obj==null){
            permissionIds=new Long[]{};
        }else{
            List<Number> ary = (ArrayList<Number>)bodyParam.get("permissionIds");//bodyoaran 只不过的参数是 arryList<Double>格式的
            permissionIds = new Long[ary.size()];
            for(int i=0;i<ary.size();i++){
                permissionIds[i] = ary.get(i).longValue();
            }

        }
        return sysRolePermissionService.batchUpdate(new Long[]{ roleId}, permissionIds);
    }

    @API(summary = "角色权限树接口",

            consumes = "application/json",
            description = "sysUserController 用户添加接口", parameters = {


            @Param(name = "id", description = "角色id"
                    , dataType = DataType.LONG, in="path",required = true),

    })

    @APIResponse(value = "{\"r\":0,\"data\":[{\"id\":123,\"name\":\"123\",\"url\":\"123\"}],\"page\":{\"curPage\":1,\"totalPage\":1,\"pageSize\":10,\"totalCount\":1,\"beginIndex\":0,\"hasPrePage\":false,\"hasNextPage\":false}}")

    @RequestMapping(value = "/tree/{id}",method=RequestMethod.GET,produces="application/json")
    @ResponseBody
    public Object tree( HttpServletRequest request,@PathVariable("id") Long id) {
        HashMap<String,Object> params =new HashMap<String,Object>();
        params.put("status",1);//删除的不要展示
        List<SysPermission> sysPermissions = sysPermissionService.listByParams(params);


        params.put("roleId",id);
        params.put("status",1);//删除的不要展示
        List<SysRolePermission> hasPermissions= this.sysRolePermissionService.listByParams(params);


        List<SysPermission> finalList = new ArrayList<SysPermission>();//最终返回前台的list

        for(int i=0,length=sysPermissions.size();i<length;i++){
            SysPermission sysPermission = sysPermissions.get(i);
            for(int j=0;j<hasPermissions.size();j++){
                SysRolePermission sysRolePermission  = hasPermissions.get(j);
                if(sysPermission.getId() == sysRolePermission.getPermissionId()){
                    sysPermission.setChecked(true);
                    break;
                }
            }

        }
         params =new HashMap<String,Object>();
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

        return this.getResult(finalList);
    }


}
