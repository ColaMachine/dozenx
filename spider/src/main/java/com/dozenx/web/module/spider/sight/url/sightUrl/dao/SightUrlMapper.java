package com.dozenx.web.module.spider.sight.url.sightUrl.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.spider.sight.url.sightUrl.bean.SightUrl;

public interface SightUrlMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(SightUrl record);

   
    int insertSelective(SightUrl record);

    
    SightUrl  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param sightUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SightUrl sightUrl);

    /**
     * 说明:根据主键修改record完整内容
     * @param sightUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SightUrl sightUrl);

    /**
     * 说明:根据map查找bean结果集
     * @param sightUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SightUrl> listByParams(Map sightUrl);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param sightUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SightUrl> listByParams4Page(Map sightUrl);
    
    /**
     * 说明:根据map查找map结果集
     * @param sightUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SightUrl sightUrl);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param sightUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SightUrl> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SightUrl record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
