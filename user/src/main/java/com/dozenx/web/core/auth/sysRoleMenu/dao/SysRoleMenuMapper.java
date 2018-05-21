package com.dozenx.web.core.auth.sysRoleMenu.dao;

import com.dozenx.web.core.auth.sysRoleMenu.bean.SysRoleMenu;
import com.dozenx.web.core.auth.sysUserRole.bean.SysUserRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysRoleMenuMapper {


    /**
     * 新增指定用户的指定角色
     * @param record
     */
    int insert(SysRoleMenu record);

    /**
     * 清空指定用户对应的角色
     * @param userId
     */
    void deleteByRoleId(Long userId);

    /**
     * 删除指定用户的指定角色
     * @param record
     */
    void delete(SysRoleMenu record);



    int count(SysRoleMenu record);
    int deleteExtra(HashMap map);
}
