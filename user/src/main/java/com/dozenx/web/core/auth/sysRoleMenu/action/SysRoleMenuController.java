/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysRoleMenu.action;

import com.cpj.swagger.annotation.*;
import com.dozenx.util.*;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.auth.sysRoleMenu.service.SysRoleMenuService;
import com.dozenx.web.core.auth.sysUserRole.service.SysUserRoleService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.rules.Digits;
import com.dozenx.web.core.rules.Required;
import com.dozenx.web.core.rules.Rule;
import com.dozenx.web.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
@APIs(description = "用户角色模块")
@Controller
@RequestMapping(Constants.WEBROOT+"/sys/auth/role/menu")
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
        Long roleId = MapUtils.getLong(bodyParam,"roleId");
        ValidateUtil.valid(roleId,"roleId",new Rule[]{new Required(),new Digits(10,0)});

        Object obj = bodyParam.get("menuIds");
        Long[] menuIds;
        if(obj==null){
            menuIds=new Long[]{};
        }else{
            List<Number> ary = (ArrayList<Number>)bodyParam.get("menuIds");//bodyoaran 只不过的参数是 arryList<Double>格式的
            menuIds = new Long[ary.size()];
            for(int i=0;i<ary.size();i++){
                menuIds[i] = ary.get(i).longValue();
            }
        }
        return sysRoleMenuService.batchUpdate(new Long[]{ roleId}, menuIds);//批量绑定 角色id 和菜单id
    }











}
