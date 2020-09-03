package com.dozenx.web.core.auth.dao;

import com.dozenx.web.core.auth.sysPermission.bean.SysPermission;
import com.dozenx.web.core.auth.sysResource.bean.SysResource;
import com.dozenx.web.core.auth.sysRole.bean.SysRole;

import java.util.List;


public interface SysAuthMapper {
    
    /**
     * 说明:根据用户id获取所有权限资源
     * @param id
     * @return
     * @return List<SysResource>
     * @author dozen.zhang
     * @date 2016年3月18日下午9:01:44
     */

    public List<SysPermission> selectPermissionByUserId(Long userId);

    public List<String> selectPermissionStrByUserId(Long userId);

    public List<SysPermission> selectPermissionByRoleId(Long roleId);
    /**
     * 根据用户id获得角色信息
     * @param userid
     * @return
     */
    public List<SysRole> selectRolesByUserId(Long userId);

    public List<SysResource> selectSysResourceByUserId(Long roleId);
}
