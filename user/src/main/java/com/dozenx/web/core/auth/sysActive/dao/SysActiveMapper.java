package com.dozenx.web.core.auth.sysActive.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.core.auth.sysActive.bean.SysActive;

public interface SysActiveMapper {
    int deleteByPrimaryKey(Integer id);
    
    int insert(SysActive record);

   
    int insertSelective(SysActive record);

    SysActive  selectByPrimaryKey(Integer id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param sysActive
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysActive sysActive);

    /**
     * 说明:根据主键修改record完整内容
     * @param sysActive
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysActive sysActive);

    /**
     * 说明:根据map查找bean结果集
     * @param sysActive
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysActive> listByParams(Map sysActive);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param sysActive
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysActive> listByParams4Page(Map sysActive);
    
    /**
     * 说明:根据map查找map结果集
     * @param sysActive
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysActive sysActive);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param sysActive
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysActive> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysActive record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      


     void insertBatch(List<SysActive> list);
}
