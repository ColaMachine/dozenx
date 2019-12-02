/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysUserRole.action;

import com.dozenx.swagger.annotation.*;
import com.dozenx.common.util.MapUtils;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
@APIs(description = "用户角色模块")
@Controller
@RequestMapping("/advertsrv/sys/auth/user/role")
public class SysUserRoleController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysUserRoleController.class);
    /** 权限service **/
    @Autowired
    private SysUserRoleService sysUserRoleService;
    


    @RequestMapping(value = "/msave.json")
    @ResponseBody
    public Object msave(HttpServletRequest request) throws Exception {
        String uids= request.getParameter("uids");
        String roleids= request.getParameter("roleids");
        return sysUserRoleService.msave( uids, roleids);
    }



    @API(summary = "用户角色批量更新接口",

            consumes = "application/json",
            description = "SysUserRoleController 用户角色批量更新接口", parameters = {


            @Param(name = "userId", description = "用户id"
                    , dataType = DataType.LONG, in="body",required = true),
            @Param(name = "roleIds", description = "角色id数组"
                    , dataType = DataType.ARRAY, in="body",required = true),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    @RequestMapping(value = "/update",method=RequestMethod.PUT,produces="application/json")
    @ResponseBody
    public Object update(HttpServletRequest request,@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception {
        Long userId = MapUtils.getLong(bodyParam,"userId");
        ValidateUtil.valid(userId,"userId",new Rule[]{new Required(),new Digits(10,0)});

        Object obj = bodyParam.get("roleIds");
        Integer[] roleIds;
        if(obj==null){
            roleIds=new Integer[]{};
        }else{
            List<Number> ary = (ArrayList<Number>)bodyParam.get("roleIds");//bodyoaran 只不过的参数是 arryList<Double>格式的
            roleIds = new Integer[ary.size()];
            for(int i=0;i<ary.size();i++){
                roleIds[i] = ary.get(i).intValue();
            }

        }
        return sysUserRoleService.batchUpdate(new Long[]{ userId}, roleIds);
    }






}
