package com.dozenx.web.core.auth.sysDepart.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.core.auth.sysDepart.bean.SysDepart;

public interface SysDepartMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(SysDepart record);

   
    int insertSelective(SysDepart record);

    SysDepart  selectByPrimaryKey(Long id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param sysDepart
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysDepart sysDepart);

    /**
     * 说明:根据主键修改record完整内容
     * @param sysDepart
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysDepart sysDepart);

    /**
     * 说明:根据map查找bean结果集
     * @param sysDepart
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysDepart> listByParams(Map sysDepart);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param sysDepart
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysDepart> listByParams4Page(Map sysDepart);
    
    /**
     * 说明:根据map查找map结果集
     * @param sysDepart
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysDepart sysDepart);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param sysDepart
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysDepart> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysDepart record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
