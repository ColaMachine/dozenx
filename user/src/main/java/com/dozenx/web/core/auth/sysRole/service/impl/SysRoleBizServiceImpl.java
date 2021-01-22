/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysRole.service.impl;

import com.dozenx.common.exception.BizException;
import com.dozenx.common.util.DateUtil;
import com.dozenx.web.core.auth.sysRole.bean.SysRole;
import com.dozenx.web.core.auth.sysRole.dao.SysRoleMapper;
import com.dozenx.web.core.auth.sysRole.service.SysRoleBizService;
import com.dozenx.web.core.auth.sysRole.service.SysRoleService;
import com.dozenx.web.core.auth.sysUser.service.SysUserService;
import com.dozenx.web.core.auth.sysUserRole.service.SysUserRoleService;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service("sysRoleBizService")
public class SysRoleBizServiceImpl extends BaseService implements SysRoleBizService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysRoleService.class);
    @Resource
    SysUserRoleService sysUserRoleService ;
    @Resource
    SysRoleService sysRoleService ;
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public void delete(Integer  id){
        if(sysUserRoleService.getUserCountByRoleId(id)>0){
            throw new BizException("30305046","该角色下还有用户不能删除");
        }
        sysRoleService.delete(id);
    }   

}
