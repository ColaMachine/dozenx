package com.dozenx.web.module.place.myPlace.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.place.myPlace.bean.MyPlace;

public interface MyPlaceMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(MyPlace record);

   
    int insertSelective(MyPlace record);

    
    MyPlace  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param myPlace
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(MyPlace myPlace);

    /**
     * 说明:根据主键修改record完整内容
     * @param myPlace
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(MyPlace myPlace);

    /**
     * 说明:根据map查找bean结果集
     * @param myPlace
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<MyPlace> listByParams(Map myPlace);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param myPlace
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<MyPlace> listByParams4Page(Map myPlace);
    
    /**
     * 说明:根据map查找map结果集
     * @param myPlace
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(MyPlace myPlace);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param myPlace
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<MyPlace> selectBeanByMap4Page(HashMap map);
    
    int countByBean(MyPlace record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
