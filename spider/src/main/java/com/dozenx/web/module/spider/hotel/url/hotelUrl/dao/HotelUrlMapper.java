package com.dozenx.web.module.spider.hotel.url.hotelUrl.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.spider.hotel.url.hotelUrl.bean.HotelUrl;

public interface HotelUrlMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(HotelUrl record);

   
    int insertSelective(HotelUrl record);

    
    HotelUrl  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param hotelUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(HotelUrl hotelUrl);

    /**
     * 说明:根据主键修改record完整内容
     * @param hotelUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(HotelUrl hotelUrl);

    /**
     * 说明:根据map查找bean结果集
     * @param hotelUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<HotelUrl> listByParams(Map hotelUrl);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param hotelUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<HotelUrl> listByParams4Page(Map hotelUrl);
    
    /**
     * 说明:根据map查找map结果集
     * @param hotelUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(HotelUrl hotelUrl);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param hotelUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<HotelUrl> selectBeanByMap4Page(HashMap map);
    
    int countByBean(HotelUrl record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
