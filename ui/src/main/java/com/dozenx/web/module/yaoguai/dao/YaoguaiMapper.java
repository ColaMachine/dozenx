package com.dozenx.web.module.yaoguai.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.yaoguai.bean.Yaoguai;

public interface YaoguaiMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(Yaoguai record);

   
    int insertSelective(Yaoguai record);

    Yaoguai  selectByPrimaryKey(Long id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param yaoguai
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Yaoguai yaoguai);

    /**
     * 说明:根据主键修改record完整内容
     * @param yaoguai
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Yaoguai yaoguai);

    /**
     * 说明:根据map查找bean结果集
     * @param yaoguai
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Yaoguai> listByParams(Map yaoguai);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param yaoguai
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Yaoguai> listByParams4Page(Map yaoguai);
    
    /**
     * 说明:根据map查找map结果集
     * @param yaoguai
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Yaoguai yaoguai);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param yaoguai
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Yaoguai> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Yaoguai record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      


     void insertBatch(List<Yaoguai> list);
}
