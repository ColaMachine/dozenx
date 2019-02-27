package com.dozenx.web.module.zan.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.zan.bean.Zan;

public interface ZanMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(Zan record);

   
    int insertSelective(Zan record);

    Zan  selectByPrimaryKey(Long id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param zan
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Zan zan);

    /**
     * 说明:根据主键修改record完整内容
     * @param zan
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Zan zan);

    /**
     * 说明:根据map查找bean结果集
     * @param zan
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Zan> listByParams(Map zan);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param zan
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Zan> listByParams4Page(Map zan);
    
    /**
     * 说明:根据map查找map结果集
     * @param zan
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Zan zan);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param zan
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Zan> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Zan record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      


     void insertBatch(List<Zan> list);
}
