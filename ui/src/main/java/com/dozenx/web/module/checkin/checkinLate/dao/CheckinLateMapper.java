package com.dozenx.web.module.checkin.checkinLate.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.checkin.checkinLate.bean.CheckinLate;

public interface CheckinLateMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(CheckinLate record);

   
    int insertSelective(CheckinLate record);

    CheckinLate  selectByPrimaryKey(Long id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param checkinLate
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(CheckinLate checkinLate);

    /**
     * 说明:根据主键修改record完整内容
     * @param checkinLate
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(CheckinLate checkinLate);

    /**
     * 说明:根据map查找bean结果集
     * @param checkinLate
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<CheckinLate> listByParams(Map checkinLate);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param checkinLate
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<CheckinLate> listByParams4Page(Map checkinLate);
    
    /**
     * 说明:根据map查找map结果集
     * @param checkinLate
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(CheckinLate checkinLate);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param checkinLate
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<CheckinLate> selectBeanByMap4Page(HashMap map);
    
    int countByBean(CheckinLate record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
