package com.dozenx.web.module.calendar.holiday.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.calendar.holiday.bean.Holiday;

public interface HolidayMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(Holiday record);

   
    int insertSelective(Holiday  record);

    Holiday  selectByPrimaryKey(Long id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param holiday
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Holiday holiday);

    /**
     * 说明:根据主键修改record完整内容
     * @param holiday
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Holiday holiday);

    /**
     * 说明:根据map查找bean结果集
     * @param holiday
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Holiday> listByParams(Map holiday);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param holiday
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Holiday> listByParams4Page(Map holiday);
    
    /**
     * 说明:根据map查找map结果集
     * @param holiday
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Holiday holiday);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param holiday
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Holiday> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Holiday record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
