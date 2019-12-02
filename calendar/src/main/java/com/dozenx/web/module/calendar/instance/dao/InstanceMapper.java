package com.dozenx.web.module.calendar.instance.dao;

import com.dozenx.web.module.calendar.instance.bean.Instance;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface InstanceMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(Instance record);

   
    int insertSelective(Instance record);

    Instance  selectByPrimaryKey(Long id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param instance
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Instance instance);

    /**
     * 说明:根据主键修改record完整内容
     * @param instance
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Instance instance);

    /**
     * 说明:根据map查找bean结果集
     * @param instance
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Instance> listByParams(Map instance);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param instance
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Instance> listByParams4Page(Map instance);
    
    /**
     * 说明:根据map查找map结果集
     * @param instance
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Instance instance);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param instance
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Instance> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Instance record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      List<Instance> listByStartAndEndTime(Long start, Long end);
}
