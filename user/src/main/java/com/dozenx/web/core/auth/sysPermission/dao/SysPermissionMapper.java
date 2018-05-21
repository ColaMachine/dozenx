package com.dozenx.web.core.auth.sysPermission.dao;

import com.dozenx.web.core.auth.sysPermission.bean.SysPermission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysPermissionMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(SysPermission record);

   
    int insertSelective(SysPermission record);

    
    SysPermission  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param sysPermission
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysPermission sysPermission);

    /**
     * 说明:根据主键修改record完整内容
     * @param sysPermission
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysPermission sysPermission);

    /**
     * 说明:根据map查找bean结果集
     * @param sysPermission
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysPermission> listByParams(Map sysPermission);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param sysPermission
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysPermission> listByParams4Page(Map sysPermission);
    
    /**
     * 说明:根据map查找map结果集
     * @param sysPermission
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysPermission sysPermission);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param sysPermission
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysPermission> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysPermission record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
