package com.dozenx.web.core.auth.sysUserRole.dao;

import com.dozenx.web.core.auth.sysUserRole.bean.SysUserRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysUserRoleMapper {


    /**
     * 新增指定用户的指定角色
     * @param record
     */
    int insert(SysUserRole record);

    /**
     * 清空指定用户对应的角色
     * @param userId
     */
    void deleteByUserId(Long userId);

    /**
     * 删除指定用户的指定角色
     * @param record
     */
    void delete(SysUserRole record);



    int count(SysUserRole record);
    int deleteExtra(HashMap map);
}
