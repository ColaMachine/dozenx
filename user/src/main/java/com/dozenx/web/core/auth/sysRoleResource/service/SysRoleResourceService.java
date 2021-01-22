/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysRoleResource.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.dozenx.web.core.auth.sysResource.bean.SimpleSysResource;
import com.dozenx.web.core.auth.sysResource.bean.SysResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.core.auth.sysRoleResource.bean.SysRoleResource;
import com.dozenx.web.core.auth.sysRoleResource.dao.SysRoleResourceMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import java.util.StringTokenizer;
import  com.dozenx.web.core.auth.sysRoleResource.bean.SysRoleResource;
import  com.dozenx.web.core.auth.sysRoleResource.dao.SysRoleResourceMapper;

import com.dozenx.web.core.log.ResultDTO;

public interface SysRoleResourceService  {
    static final Logger logger = LoggerFactory
            .getLogger(SysRoleResourceService.class);

    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysRoleResource> listByParams4Page(HashMap params) ;
    public List<SysRoleResource> listByParams(HashMap params);

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) ;

    /*
     * 说明:
     * @param SysRoleResource
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysRoleResource sysRoleResource) ;
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public void delete(Integer id);
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysRoleResource selectByPrimaryKey(Integer id);
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) ;
     /**
     * 多项关联保存
     * @param roleIds
     * @param resourceIds
     * @return
     */
    public ResultDTO msave(String roleIds, String resourceIds) ;

    public ResultDTO msave(Integer roleIds, Integer resourceIds) ;

    public ResultDTO insertList(List<SysRoleResource> sysRoleResource) ;

    List<SimpleSysResource> tree(Integer id);
    public   Map<String ,Object>  roleupdateRolePermissions(Map<String, Object> bodyParam) throws Exception ;
}
