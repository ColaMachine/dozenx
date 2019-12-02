package com.dozenx.web.module.calendar.event.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.calendar.event.bean.Event;

public interface EventMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(Event record);

   
    int insertSelective(Event record);

    Event  selectByPrimaryKey(Long id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param event
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Event event);

    /**
     * 说明:根据主键修改record完整内容
     * @param event
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Event event);

    /**
     * 说明:根据map查找bean结果集
     * @param event
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Event> listByParams(Map event);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param event
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Event> listByParams4Page(Map event);
    
    /**
     * 说明:根据map查找map结果集
     * @param event
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Event event);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param event
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Event> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Event record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

    List<Event> listRepeatEventByTime(Long start, Long end);

    List<Event> listRepeatEvent();
}
