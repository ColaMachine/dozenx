package com.dozenx.web.core.auth.active.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.core.auth.active.bean.Active;

public interface ActiveMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(Active record);

   
    int insertSelective(Active record);

    
    Active  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param active
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Active active);

    /**
     * 说明:根据主键修改record完整内容
     * @param active
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Active active);

    /**
     * 说明:根据map查找bean结果集
     * @param active
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Active> listByParams(Map active);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param active
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Active> listByParams4Page(Map active);
    
    /**
     * 说明:根据map查找map结果集
     * @param active
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Active active);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param active
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Active> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Active record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

    Active selectUnActiveByAccount(String account);
}
