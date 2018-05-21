package com.dozenx.web.module.map.mapData.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.map.mapData.bean.MapData;

public interface MapDataMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(MapData record);

   
    int insertSelective(MapData record);

    
    MapData  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param mapData
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(MapData mapData);

    /**
     * 说明:根据主键修改record完整内容
     * @param mapData
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(MapData mapData);

    /**
     * 说明:根据map查找bean结果集
     * @param mapData
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<MapData> listByParams(Map mapData);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param mapData
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<MapData> listByParams4Page(Map mapData);
    
    /**
     * 说明:根据map查找map结果集
     * @param mapData
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(MapData mapData);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param mapData
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<MapData> selectBeanByMap4Page(HashMap map);
    
    int countByBean(MapData record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

    public List<MapData> queryNearby(Map map);
      
}
