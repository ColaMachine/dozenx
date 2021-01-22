/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2019-10-25 17:04:55
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysRoleMenu.action;
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
import com.dozenx.web.core.auth.sysRoleMenu.service.SysRoleMenuService;
import com.dozenx.web.core.auth.sysRoleMenu.bean.SysRoleMenu;
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


@APIs(description = "角色菜单关系")
@Controller
@RequestMapping("/sys/auth/role/menu")
public class SysRoleMenuController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysRoleMenuController.class);
    /** 权限service **/
    @Autowired
    private SysRoleMenuService sysRoleMenuService;




    @API(summary = "角色菜单批量更新接口",

            consumes = "application/json",
            description = "sysUserController 用户添加接口", parameters = {


            @Param(name = "roleId", description = "角色id"
                    , dataType = DataType.LONG, in="body",required = true),
            @Param(name = "menuIds", description = "菜单id数组"
                    , dataType = DataType.ARRAY, in="body",required = true),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    @RequestMapping(value = "/update",method=RequestMethod.POST,produces="application/json")
    @ResponseBody
    public Object update(HttpServletRequest request,@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception {
        Integer roleId = MapUtils.getInteger(bodyParam,"roleId");
        ValidateUtil.valid(roleId,"roleId",new Rule[]{new Required(),new Digits(10,0)});

        Object obj = bodyParam.get("menuIds");
        Integer[] menuIds;
        if(obj==null){
            menuIds=new Integer[]{};
        }else{
            List<Number> ary = (ArrayList<Number>)bodyParam.get("menuIds");//bodyoaran 只不过的参数是 arryList<Double>格式的
            menuIds = new Integer[ary.size()];
            for(int i=0;i<ary.size();i++){
                menuIds[i] = ary.get(i).intValue();
            }
        }
        return sysRoleMenuService.batchUpdate(new Integer[]{ roleId}, menuIds);//批量绑定 角色id 和菜单id
    }



}
