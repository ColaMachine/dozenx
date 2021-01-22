package com.dozenx.web.core.auth.sysResource.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;

import com.dozenx.web.core.auth.sysResource.bean.SimpleSysResource;
import com.dozenx.web.core.auth.sysResource.bean.SysResource;

public interface SysResourceMapper {
    int deleteByPrimaryKey(Integer id);
    
    int insert(SysResource record);

   
    int insertSelective(SysResource record);

    SysResource  selectByPrimaryKey(Integer id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param sysResource
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysResource sysResource);

    /**
     * 说明:根据主键修改record完整内容
     * @param sysResource
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysResource sysResource);

    /**
     * 说明:根据map查找bean结果集
     * @param sysResource
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysResource> listByParams(Map sysResource);


    List<SimpleSysResource> listSimpleByParams(Map sysResource);
    /**
     * 说明:根据bean查找bean结果集
     * @param sysResource
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysResource> listByParams4Page(Map sysResource);
    
    /**
     * 说明:根据map查找map结果集
     * @param sysResource
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysResource sysResource);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param sysResource
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysResource> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysResource record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      


     void insertBatch(List<SysResource> list);
}
