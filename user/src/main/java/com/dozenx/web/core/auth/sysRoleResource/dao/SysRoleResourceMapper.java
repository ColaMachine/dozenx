package com.dozenx.web.core.auth.sysRoleResource.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.core.auth.sysRoleResource.bean.SysRoleResource;

public interface SysRoleResourceMapper {
    
    int insert(SysRoleResource record);

   
    int insertSelective(SysRoleResource record);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param sysRoleResource
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysRoleResource sysRoleResource);

    /**
     * 说明:根据主键修改record完整内容
     * @param sysRoleResource
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysRoleResource sysRoleResource);

    /**
     * 说明:根据map查找bean结果集
     * @param sysRoleResource
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysRoleResource> listByParams(Map sysRoleResource);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param sysRoleResource
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysRoleResource> listByParams4Page(Map sysRoleResource);
    
    /**
     * 说明:根据map查找map结果集
     * @param sysRoleResource
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysRoleResource sysRoleResource);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param sysRoleResource
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysRoleResource> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysRoleResource record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
    int deleteExtra(HashMap map);


     void insertBatch(List<SysRoleResource> list);

    void deleteByPrimaryKey(Integer integer);

    SysRoleResource selectByPrimaryKey(Integer id);

    int count(SysRoleResource sysUserRole);
}
