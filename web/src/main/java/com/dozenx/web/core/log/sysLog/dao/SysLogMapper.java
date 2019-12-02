package com.dozenx.web.core.log.sysLog.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.core.log.sysLog.bean.SysLog;

public interface SysLogMapper {
    int deleteByPrimaryKey(Integer id);
    
    int insert(SysLog record);

   
    int insertSelective(SysLog record);

    SysLog  selectByPrimaryKey(Integer id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param sysLog
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysLog sysLog);

    /**
     * 说明:根据主键修改record完整内容
     * @param sysLog
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysLog sysLog);

    /**
     * 说明:根据map查找bean结果集
     * @param sysLog
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysLog> listByParams(Map sysLog);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param sysLog
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysLog> listByParams4Page(Map sysLog);
    
    /**
     * 说明:根据map查找map结果集
     * @param sysLog
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysLog sysLog);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param sysLog
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysLog> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysLog record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      


     void insertBatch(List<SysLog> list);
}
