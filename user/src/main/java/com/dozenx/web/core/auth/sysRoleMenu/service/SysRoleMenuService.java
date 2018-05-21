/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysRoleMenu.service;

import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.auth.sysMenu.bean.SysMenu;
import com.dozenx.web.core.auth.sysMenu.dao.SysMenuMapper;
import com.dozenx.web.core.auth.sysRole.bean.SysRole;
import com.dozenx.web.core.auth.sysRole.dao.SysRoleMapper;
import com.dozenx.web.core.auth.sysRoleMenu.bean.SysRoleMenu;
import com.dozenx.web.core.auth.sysRoleMenu.dao.SysRoleMenuMapper;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.sysUser.dao.SysUserMapper;
import com.dozenx.web.core.auth.sysUserRole.bean.SysUserRole;
import com.dozenx.web.core.auth.sysUserRole.dao.SysUserRoleMapper;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service("sysRoleMenuService")
public class SysRoleMenuService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysRoleMenuService.class);
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysMenuMapper sysMenuMapper;


     /**
         * 多项关联保存
         * @param menuIds
         * @param roleIds
         * @return
         */
        public ResultDTO msave(String menuIds,String roleIds) {
            if(StringUtil.isBlank(roleIds)){
                return ResultUtil.getResult(101,"参数错误");
            }

            String[] menuIdAry= roleIds.split(",");
            String[] roleIdAry=roleIds.split(",");
            Long[] menuIdAryReal =new  Long[menuIdAry.length];
            Long[] roleIdAryReal =new  Long[roleIdAry.length];
            for(int i=0;i<roleIdAry.length;i++){
                if(!StringUtil.checkNumeric(roleIdAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                roleIdAryReal[i]=Long.valueOf(roleIdAry[i]);
            }
            if(StringUtil.isBlank(menuIds)){
                menuIdAryReal=null;
                menuIdAry=null;
            }
            if(menuIdAry!=null)
            for(int i=0;i<menuIdAry.length;i++){
                if(!StringUtil.checkNumeric(menuIdAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                menuIdAryReal[i]=Long.valueOf(roleIdAry[i]);
            }
            batchUpdate(roleIdAryReal,menuIdAryReal);
            //delete from SysUserRole where uid in (1,2,3,4,5) and rid not in(1,2,3)
            return ResultUtil.getSuccResult();
        }

    public  ResultDTO batchUpdate(Long[] roleIdAryReal,Long[] menuIdAryReal){
        //验证父亲id 正确性 是否存在
        if(roleIdAryReal!=null)
            for(int i=0;i< roleIdAryReal.length;i++){
                //
                SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleIdAryReal[i]);
                if(sysRole==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
                //查询的数据不存在
            }
        if(menuIdAryReal!=null)
            for(int i=0;i<menuIdAryReal.length;i++){
                SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(menuIdAryReal[i]);
                //查询的数据不存在
                if(sysMenu==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
            }

        //验证子id 正确性 是否存在
        SysRoleMenu sysRoleMenu =new SysRoleMenu();
        if(roleIdAryReal!=null)
            for(int i=0;i<roleIdAryReal.length;i++){
                for(int j=0;j<menuIdAryReal.length;j++){

                    Long roleId =roleIdAryReal[i];
                    Long menuId =menuIdAryReal[j];
                    //查找是否已经有关联数据了
                    sysRoleMenu.setRoleId(roleId);
                    sysRoleMenu.setMenuId(menuId);

                    int count = sysRoleMenuMapper.count(sysRoleMenu);
                    if(count>0)continue;//如果有记录了就不保存
                    sysRoleMenu.setRoleId(roleId);
                    sysRoleMenu.setMenuId(menuId);
                    sysRoleMenuMapper.insert(sysRoleMenu);
                }
            }
        //删除多余的数据
        HashMap params =new HashMap(2);

        params.put("roleIds",roleIdAryReal);
       if(menuIdAryReal.length>0){
           params.put("menuIds",menuIdAryReal);
       }

        sysRoleMenuMapper.deleteExtra(params);//删除额外的数据
        return ResultUtil.getSuccResult();
    }

}
