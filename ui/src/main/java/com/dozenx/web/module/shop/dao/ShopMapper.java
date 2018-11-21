package com.dozenx.web.module.shop.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.shop.bean.Shop;

public interface ShopMapper {
    int deleteByPrimaryKey(Integer id);
    
    int insert(Shop record);

   
    int insertSelective(Shop record);

    Shop  selectByPrimaryKey(Integer id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param shop
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Shop shop);

    /**
     * 说明:根据主键修改record完整内容
     * @param shop
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Shop shop);

    /**
     * 说明:根据map查找bean结果集
     * @param shop
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Shop> listByParams(Map shop);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param shop
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Shop> listByParams4Page(Map shop);
    
    /**
     * 说明:根据map查找map结果集
     * @param shop
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Shop shop);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param shop
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Shop> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Shop record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
