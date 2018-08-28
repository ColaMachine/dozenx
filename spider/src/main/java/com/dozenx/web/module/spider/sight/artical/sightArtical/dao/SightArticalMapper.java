package com.dozenx.web.module.spider.sight.artical.sightArtical.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.spider.sight.artical.sightArtical.bean.SightArtical;

public interface SightArticalMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(SightArtical record);

   
    int insertSelective(SightArtical record);

    
    SightArtical  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param sightArtical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SightArtical sightArtical);

    /**
     * 说明:根据主键修改record完整内容
     * @param sightArtical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SightArtical sightArtical);

    /**
     * 说明:根据map查找bean结果集
     * @param sightArtical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SightArtical> listByParams(Map sightArtical);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param sightArtical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SightArtical> listByParams4Page(Map sightArtical);
    
    /**
     * 说明:根据map查找map结果集
     * @param sightArtical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SightArtical sightArtical);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param sightArtical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SightArtical> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SightArtical record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
