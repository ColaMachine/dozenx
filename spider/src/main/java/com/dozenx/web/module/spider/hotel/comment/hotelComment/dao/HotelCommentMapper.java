package com.dozenx.web.module.spider.hotel.comment.hotelComment.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.spider.hotel.comment.hotelComment.bean.HotelComment;

public interface HotelCommentMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(HotelComment record);

   
    int insertSelective(HotelComment record);

    
    HotelComment  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param hotelComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(HotelComment hotelComment);

    /**
     * 说明:根据主键修改record完整内容
     * @param hotelComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(HotelComment hotelComment);

    /**
     * 说明:根据map查找bean结果集
     * @param hotelComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<HotelComment> listByParams(Map hotelComment);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param hotelComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<HotelComment> listByParams4Page(Map hotelComment);
    
    /**
     * 说明:根据map查找map结果集
     * @param hotelComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(HotelComment hotelComment);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param hotelComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<HotelComment> selectBeanByMap4Page(HashMap map);
    
    int countByBean(HotelComment record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
