/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2019-10-25 15:58:05
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysMenu.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.dozenx.common.util.MapUtils;
import com.dozenx.common.util.JsonUtil;
//import com.dozenx.common.util.ExcelUtil;
import java.math.BigDecimal;
import com.dozenx.swagger.annotation.*;
import java.util.LinkedHashMap;
import com.dozenx.common.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import com.dozenx.web.core.Constants;
import com.dozenx.web.core.annotation.RequiresLogin;
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
import com.dozenx.web.core.auth.sysMenu.service.SysMenuService;
import com.dozenx.web.core.auth.sysMenu.bean.SysMenu;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.util.RequestUtil;
import org.springframework.web.bind.annotation.*;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.common.util.DateUtil;
import org.springframework.web.multipart.MultipartFile;
import com.dozenx.common.Path.PathManager;
import com.dozenx.common.exception.BizException;
import java.nio.file.Files;
import com.dozenx.common.config.SysConfig;

@APIs(description = "资源配置")
@Controller
@RequestMapping("/sys/auth/menu")
public class SysMenuController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysMenuController.class);
    /** 权限service **/
    @Autowired
    private SysMenuService sysMenuService;
    



  /**
         * 说明:添加SysMenu信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-10-25 15:58:05
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
        @API( summary="添加单个资源配置信息",
            description = "添加单个资源配置信息",
            parameters={

                @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"主键,类型 INTEGER, required = false \" "
                + "  \"pid\":\"父菜单,类型 INTEGER, required = false \" "
                + "  \"menuName\":\"菜单名称,类型 STRING, required = true \" "
                + "  \"menuCode\":\"菜单代码,类型 STRING, required = true \" "
                + "  \"menuPermission\":\"权限,类型 STRING, required = false \" "
                + "  \"menuUrl\":\"资源对应URL,类型 STRING, required = false \" "
                + "  \"orderNo\":\"排序id,类型 BYTE, required = false \" "
                + "  \"status\":\"状态,类型 BYTE, required = true \" "
                + "  \"remark\":\"备注,类型 STRING, required = false \" "
                + "  \"icon\":\"图标,类型 STRING, required = false \" "
                + "  }", in = InType.body, dataType = DataType.STRING, required = true),
                  })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
            SysMenu sysMenu =    getInfoFromMap(bodyParam);


            return sysMenuService.save(sysMenu);

        }

 /**
         * 说明:删除SysMenu信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-10-25 15:58:05
         */
         @API( summary="根据id删除单个资源配置信息",
            description = "根据id删除单个资源配置信息",
            parameters={
             @Param(name="id" , description="主键",in=InType.path,dataType= DataType.INTEGER,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable Integer id,HttpServletRequest request) {
            sysMenuService.delete(id);
            return this.getResult(SUCC);
        }


    /**
    * 说明:添加SysMenu信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2019-10-25 15:58:05
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个资源配置信息",
    description = "更新单个资源配置信息",
    parameters={
              @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"主键,类型 INTEGER, required = false \" "
                       + "  \"pid\":\"父菜单,类型 INTEGER, required = false \" "
                       + "  \"menuName\":\"菜单名称,类型 STRING, required = true \" "
                       + "  \"menuCode\":\"菜单代码,类型 STRING, required = true \" "
                       + "  \"menuPermission\":\"权限,类型 STRING, required = false \" "
                       + "  \"menuUrl\":\"资源对应URL,类型 STRING, required = false \" "
                       + "  \"orderNo\":\"排序id,类型 BYTE, required = false \" "
                       + "  \"status\":\"状态,类型 BYTE, required = true \" "
                       + "  \"remark\":\"备注,类型 STRING, required = false \" "
                       + "  \"icon\":\"图标,类型 STRING, required = false \" "
                       + " } ", in = InType.body, dataType = DataType.STRING, required = true),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    SysMenu sysMenu =    getInfoFromMap(bodyParam);
    return sysMenuService.save(sysMenu);

    }
/**
     * 说明:ajax请求SysMenu信息
     * @author dozen.zhang
     * @date 2019-10-25 15:58:05
     * @return String
     */
       @API(summary="资源配置列表接口",
                 description="资源配置列表接口",
                 parameters={

                        @Param(name = "params",  description = "{ \"pageSize\":15,\"curPage\":1,"
                          +"\"id\":\"主键,类型 INTEGER, required = false \" "
                                 + " \"pid\":\"父菜单,类型 INTEGER, required = false \" "
                                 + " \"menuName\":\"菜单名称,类型 STRING, required = true \" "
                                 + " \"menuCode\":\"菜单代码,类型 STRING, required = true \" "
                                 + " \"menuPermission\":\"权限,类型 STRING, required = false \" "
                                 + " \"menuUrl\":\"资源对应URL,类型 STRING, required = false \" "
                                 + " \"orderNo\":\"排序id,类型 BYTE, required = false \" "
                                 + " \"status\":\"状态,类型 BYTE, required = true \" "
                                 + " \"remark\":\"备注,类型 STRING, required = false \" "
                                 + " \"icon\":\"图标,类型 STRING, required = false \" "
                                 + "  }",
                        in = InType.query, dataType = DataType.STRING, required = true),

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
        String pid = MapUtils.getString(params,"pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String menuName = MapUtils.getString(params,"menuName");
        if(!StringUtil.isBlank(menuName)){
            params.put("menuName",menuName);
        }
        String menuNameLike = MapUtils.getString(params,"menuNameLike");
        if(!StringUtil.isBlank(menuNameLike)){
            params.put("menuNameLike",menuNameLike);
        }
        String menuCode = MapUtils.getString(params,"menuCode");
        if(!StringUtil.isBlank(menuCode)){
            params.put("menuCode",menuCode);
        }
        String menuCodeLike = MapUtils.getString(params,"menuCodeLike");
        if(!StringUtil.isBlank(menuCodeLike)){
            params.put("menuCodeLike",menuCodeLike);
        }
        String menuPermission = MapUtils.getString(params,"menuPermission");
        if(!StringUtil.isBlank(menuPermission)){
            params.put("menuPermission",menuPermission);
        }
        String menuPermissionLike = MapUtils.getString(params,"menuPermissionLike");
        if(!StringUtil.isBlank(menuPermissionLike)){
            params.put("menuPermissionLike",menuPermissionLike);
        }
        String menuUrl = MapUtils.getString(params,"menuUrl");
        if(!StringUtil.isBlank(menuUrl)){
            params.put("menuUrl",menuUrl);
        }
        String menuUrlLike = MapUtils.getString(params,"menuUrlLike");
        if(!StringUtil.isBlank(menuUrlLike)){
            params.put("menuUrlLike",menuUrlLike);
        }
        String orderNo = MapUtils.getString(params,"orderNo");
        if(!StringUtil.isBlank(orderNo)){
            params.put("orderNo",orderNo);
        }
        String status = MapUtils.getString(params,"status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String remark = MapUtils.getString(params,"remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = MapUtils.getString(params,"remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String icon = MapUtils.getString(params,"icon");
        if(!StringUtil.isBlank(icon)){
            params.put("icon",icon);
        }
        String iconLike = MapUtils.getString(params,"iconLike");
        if(!StringUtil.isBlank(iconLike)){
            params.put("iconLike",iconLike);
        }

        params.put("page",page);
        List<SysMenu> sysMenus = sysMenuService.listByParams4Page(params);
        return ResultUtil.getResult(sysMenus, page);
    }


    private SysMenu getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       SysMenu sysMenu =new  SysMenu();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                sysMenu.setId(Integer.valueOf(id));
        }
        String pid = MapUtils.getString(bodyParam,"pid");
        if(!StringUtil.isBlank(pid)){
                sysMenu.setPid(Integer.valueOf(pid));
        }
        String menuName = MapUtils.getString(bodyParam,"menuName");
        if(!StringUtil.isBlank(menuName)){
                sysMenu.setMenuName(String.valueOf(menuName));
        }
        String menuCode = MapUtils.getString(bodyParam,"menuCode");
        if(!StringUtil.isBlank(menuCode)){
                sysMenu.setMenuCode(String.valueOf(menuCode));
        }
        String menuPermission = MapUtils.getString(bodyParam,"menuPermission");
        if(!StringUtil.isBlank(menuPermission)){
                sysMenu.setMenuPermission(String.valueOf(menuPermission));
        }
        String menuUrl = MapUtils.getString(bodyParam,"menuUrl");
        if(!StringUtil.isBlank(menuUrl)){
                sysMenu.setMenuUrl(String.valueOf(menuUrl));
        }
        String orderNo = MapUtils.getString(bodyParam,"orderNo");
        if(!StringUtil.isBlank(orderNo)){
                sysMenu.setOrderNo(Byte.valueOf(orderNo));
        }
        String status = MapUtils.getString(bodyParam,"status");
        if(!StringUtil.isBlank(status)){
                sysMenu.setStatus(Byte.valueOf(status));
        }
        String remark = MapUtils.getString(bodyParam,"remark");
        if(!StringUtil.isBlank(remark)){
                sysMenu.setRemark(String.valueOf(remark));
        }
        String icon = MapUtils.getString(bodyParam,"icon");
        if(!StringUtil.isBlank(icon)){
                sysMenu.setIcon(String.valueOf(icon));
        }

        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(11,0)});
        vu.add("pid", pid, "父菜单",  new Rule[]{new Digits(11,0)});
        vu.add("menuName", menuName, "菜单名称",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("menuCode", menuCode, "菜单代码",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("menuPermission", menuPermission, "权限",  new Rule[]{new Length(255)});
        vu.add("menuUrl", menuUrl, "资源对应URL",  new Rule[]{new Length(255)});
        vu.add("orderNo", orderNo, "排序id",  new Rule[]{new Digits(4,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(4,0),new CheckBox(new String[]{"1","2"}),new NotEmpty()});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(20)});
        vu.add("icon", icon, "图标",  new Rule[]{new Length(20)});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  sysMenu;
    }




//
//       /**
//         * 导出
//         * @param request
//         * @return
//         * @author dozen.zhang
//         */
//        @API(summary="资源配置列表导出接口",
//          description="资源配置列表导出接口",
//          parameters={
//          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
//          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
//             @Param(name="id" , description="主键 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
//             @Param(name="pid" , description="父菜单 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
//             @Param(name="menuName" , description="菜单名称 ",in=InType.params,dataType = DataType.STRING,required =false),// true
//             @Param(name="menuCode" , description="菜单代码 ",in=InType.params,dataType = DataType.STRING,required =false),// true
//             @Param(name="menuPermission" , description="权限 ",in=InType.params,dataType = DataType.STRING,required =false),// false
//             @Param(name="menuUrl" , description="资源对应URL ",in=InType.params,dataType = DataType.STRING,required =false),// false
//             @Param(name="orderNo" , description="排序id ",in=InType.params,dataType = DataType.BYTE,required =false),// false
//             @Param(name="status" , description="状态 ",in=InType.params,dataType = DataType.BYTE,required =false),// true
//             @Param(name="remark" , description="备注 ",in=InType.params,dataType = DataType.STRING,required =false),// false
//             @Param(name="icon" , description="图标 ",in=InType.params,dataType = DataType.STRING,required =false),// false
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
//        String pid = MapUtils.getString(params,"pid");
//        if(!StringUtil.isBlank(pid)){
//            params.put("pid",pid);
//        }
//        String menuName = MapUtils.getString(params,"menuName");
//        if(!StringUtil.isBlank(menuName)){
//            params.put("menuName",menuName);
//        }
//        String menuNameLike = MapUtils.getString(params,"menuNameLike");
//        if(!StringUtil.isBlank(menuNameLike)){
//            params.put("menuNameLike",menuNameLike);
//        }
//        String menuCode = MapUtils.getString(params,"menuCode");
//        if(!StringUtil.isBlank(menuCode)){
//            params.put("menuCode",menuCode);
//        }
//        String menuCodeLike = MapUtils.getString(params,"menuCodeLike");
//        if(!StringUtil.isBlank(menuCodeLike)){
//            params.put("menuCodeLike",menuCodeLike);
//        }
//        String menuPermission = MapUtils.getString(params,"menuPermission");
//        if(!StringUtil.isBlank(menuPermission)){
//            params.put("menuPermission",menuPermission);
//        }
//        String menuPermissionLike = MapUtils.getString(params,"menuPermissionLike");
//        if(!StringUtil.isBlank(menuPermissionLike)){
//            params.put("menuPermissionLike",menuPermissionLike);
//        }
//        String menuUrl = MapUtils.getString(params,"menuUrl");
//        if(!StringUtil.isBlank(menuUrl)){
//            params.put("menuUrl",menuUrl);
//        }
//        String menuUrlLike = MapUtils.getString(params,"menuUrlLike");
//        if(!StringUtil.isBlank(menuUrlLike)){
//            params.put("menuUrlLike",menuUrlLike);
//        }
//        String orderNo = MapUtils.getString(params,"orderNo");
//        if(!StringUtil.isBlank(orderNo)){
//            params.put("orderNo",orderNo);
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
//        String icon = MapUtils.getString(params,"icon");
//        if(!StringUtil.isBlank(icon)){
//            params.put("icon",icon);
//        }
//        String iconLike = MapUtils.getString(params,"iconLike");
//        if(!StringUtil.isBlank(iconLike)){
//            params.put("iconLike",iconLike);
//        }
//
//             params.put("page",page);
//             List<SysMenu> list = sysMenuService.listByParams4Page(params);
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
//            colTitle.put("id", "主键");
//            colTitle.put("pid", "父菜单");
//            colTitle.put("menuName", "菜单名称");
//            colTitle.put("menuCode", "菜单代码");
//            colTitle.put("menuPermission", "权限");
//            colTitle.put("menuUrl", "资源对应URL");
//            colTitle.put("orderNo", "排序id");
//            colTitle.put("status", "状态");
//            colTitle.put("remark", "备注");
//            colTitle.put("icon", "图标");
//            List<Map> finalList = new ArrayList<Map>();
//            for (int i = 0; i < list.size(); i++) {
//                SysMenu sm = list.get(i);
//                HashMap<String,Object> map = new HashMap<String,Object>();
//                map.put("id",  list.get(i).getId());
//                map.put("pid",  list.get(i).getPid());
//                map.put("menuName",  list.get(i).getMenuName());
//                map.put("menuCode",  list.get(i).getMenuCode());
//                map.put("menuPermission",  list.get(i).getMenuPermission());
//                map.put("menuUrl",  list.get(i).getMenuUrl());
//                map.put("orderNo",  list.get(i).getOrderNo());
//                map.put("status",  list.get(i).getStatus());
//                map.put("remark",  list.get(i).getRemark());
//                map.put("icon",  list.get(i).getIcon());
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
    /**
     * @Author: dozen.zhang
     * @Description:当前用户树状菜单
     * @Date: 2018/2/8
     */
    @API(summary = "当前用户树状菜单",
            consumes = "application/x-www-form-urlencoded",
            description = " ", parameters = { })
    @APIResponse(value = "{\"r\":0,\"data\":[{\"id\":123,\"username\":\"123\",\"password\":\"123\",\"nkname\":\"123\",\"type\":null,\"status\":1,\"email\":null,\"telno\":\"13969696969\",\"idcard\":\"23\",\"sex\":0,\"birth\":1517414400000,\"integral\":123,\"address\":\"123\",\"wechat\":\"123\",\"qq\":123,\"face\":\"static/img/timg.jpeg\",\"remark\":\"123\",\"outId\":null,\"createtime\":1517901790000,\"updatetime\":1517901790000}],\"msg\":null,\"page\":{\"curPage\":1,\"totalPage\":1,\"pageSize\":10,\"totalCount\":1,\"beginIndex\":0,\"hasPrePage\":false,\"hasNextPage\":false},\"other\":null,\"right\":true}")
    @RequiresLogin
    @RequestMapping(value = "/tree/my",method=RequestMethod.GET,produces="application/json")
    @ResponseBody
    public ResultDTO listMenu(HttpServletRequest request){
        //String id=request.getParameter("id");
//        Long userId = this.getUserId(request);
//        //根据用户id查找菜单
//
//        List<SysMenu> sysMenuTree = sysMenuService.selectMenuByUserId(userId);
//
//        List<SysMenu> finalList = new ArrayList<SysMenu>();//最终返回前台的list
//
//        //组装成树状结构
//        for(int i=sysMenuTree.size()-1;i>=0;i--){//倒序 方便找到后删除
//            SysMenu sysMenu = sysMenuTree.get(i);
//
//            if(sysMenu.getPid()==0){
//                finalList.add(sysMenu);
//                sysMenu.childs=new ArrayList<>();
//                for(int j=sysMenuTree.size()-1;j>=0;j--){//倒序 方便找到后删除
//                    SysMenu childMenu = sysMenuTree.get(j);//遍历所有的项目查找所有子项
//                    if(childMenu.getPid() == sysMenu.getId()){
//                        sysMenu.childs.add(childMenu);//塞入到childs中 并从集合中删除
//                        // sysMenuTree.remove(j);
//                    }
//                }
//                // sysMenuTree.remove(i);
//            }
//        }
        return  ResultUtil.getDataResult(new BaseController().getSessionAttribute(request, Constants.SESSION_MENUS,List.class));//返回集合
    }

}
