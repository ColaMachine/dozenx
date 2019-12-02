package com.dozenx.web.module.calendar.calendar.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.calendar.calendar.bean.Calendar;

public interface CalendarMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(Calendar record);

   
    int insertSelective(Calendar record);

    Calendar  selectByPrimaryKey(Long id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param calendar
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Calendar calendar);

    /**
     * 说明:根据主键修改record完整内容
     * @param calendar
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Calendar calendar);

    /**
     * 说明:根据map查找bean结果集
     * @param calendar
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Calendar> listByParams(Map calendar);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param calendar
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Calendar> listByParams4Page(Map calendar);
    
    /**
     * 说明:根据map查找map结果集
     * @param calendar
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Calendar calendar);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param calendar
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Calendar> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Calendar record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
