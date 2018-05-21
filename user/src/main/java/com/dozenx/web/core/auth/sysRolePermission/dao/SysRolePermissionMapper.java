package com.dozenx.web.core.auth.sysRolePermission.dao;

import com.dozenx.web.core.auth.sysRolePermission.bean.SysRolePermission;
import com.dozenx.web.core.auth.sysUserRole.bean.SysUserRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysRolePermissionMapper {
    


    
    int insert(SysRolePermission record);

   
    int insertSelective(SysRolePermission record);


    /**
     * 说明:根据map查找bean结果集
     * @param sysRolePermission
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysRolePermission> listByParams(Map sysRolePermission);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param sysRolePermission
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysRolePermission> listByParams4Page(Map sysRolePermission);

    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

    int count(SysRolePermission record);
    int deleteExtra(HashMap map);
}
