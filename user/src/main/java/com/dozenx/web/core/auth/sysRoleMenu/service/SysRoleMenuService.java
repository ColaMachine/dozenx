/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysRoleMenu.service;

import com.dozenx.web.core.log.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface SysRoleMenuService  {
    static final Logger logger = LoggerFactory
            .getLogger(SysRoleMenuService.class);


    /**
     * 多项关联保存
     * @param menuIds
     * @param roleIds
     * @return
     */
    public ResultDTO msave(String menuIds,String roleIds) ;

    public  ResultDTO batchUpdate(Integer[] roleIdAryReal,Integer[] menuIdAryReal);

}
