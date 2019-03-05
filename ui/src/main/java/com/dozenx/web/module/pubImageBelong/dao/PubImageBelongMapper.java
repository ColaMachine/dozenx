package com.dozenx.web.module.pubImageBelong.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.pubImageBelong.bean.PubImageBelong;

public interface PubImageBelongMapper {
    int deleteByPrimaryKey(Integer id);
    
    int insert(PubImageBelong record);

   
    int insertSelective(PubImageBelong record);

    PubImageBelong  selectByPrimaryKey(Integer id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param pubImageBelong
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(PubImageBelong pubImageBelong);

    /**
     * 说明:根据主键修改record完整内容
     * @param pubImageBelong
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(PubImageBelong pubImageBelong);

    /**
     * 说明:根据map查找bean结果集
     * @param pubImageBelong
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<PubImageBelong> listByParams(Map pubImageBelong);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param pubImageBelong
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<PubImageBelong> listByParams4Page(Map pubImageBelong);
    
    /**
     * 说明:根据map查找map结果集
     * @param pubImageBelong
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(PubImageBelong pubImageBelong);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param pubImageBelong
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<PubImageBelong> selectBeanByMap4Page(HashMap map);
    
    int countByBean(PubImageBelong record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      


     void insertBatch(List<PubImageBelong> list);
}
