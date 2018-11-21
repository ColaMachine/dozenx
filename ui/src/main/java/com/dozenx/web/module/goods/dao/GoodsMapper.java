package com.dozenx.web.module.goods.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.goods.bean.Goods;

public interface GoodsMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(Goods record);

   
    int insertSelective(Goods record);

    Goods  selectByPrimaryKey(Long id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param goods
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Goods goods);

    /**
     * 说明:根据主键修改record完整内容
     * @param goods
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Goods goods);

    /**
     * 说明:根据map查找bean结果集
     * @param goods
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Goods> listByParams(Map goods);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param goods
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Goods> listByParams4Page(Map goods);
    
    /**
     * 说明:根据map查找map结果集
     * @param goods
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Goods goods);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param goods
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Goods> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Goods record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
