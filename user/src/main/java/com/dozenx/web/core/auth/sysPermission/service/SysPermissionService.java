/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysPermission.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.core.auth.sysPermission.bean.SysPermission;
import com.dozenx.web.core.auth.sysPermission.dao.SysPermissionMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

public interface SysPermissionService  {
    static final Logger logger = LoggerFactory
            .getLogger(SysPermissionService.class);

    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysPermission> listByParams4Page(Map params) ;
    public List<SysPermission> listByParams(Map params);

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) ;

    /*
     * 说明:
     * @param SysPermission
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysPermission sysPermission) ;
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
    public SysPermission selectByPrimaryKey(Integer id);
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) ;


       public ResultDTO insertList(List<SysPermission> sysPermission) ;
}
