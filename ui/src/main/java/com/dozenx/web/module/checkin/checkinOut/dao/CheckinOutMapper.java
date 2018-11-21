package com.dozenx.web.module.checkin.checkinOut.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;

import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.module.checkin.checkinOut.bean.CheckinOut;

public interface CheckinOutMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(CheckinOut record);

   
    int insertSelective(CheckinOut record);

    CheckinOut  selectByPrimaryKey(Long id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param checkinOut
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(CheckinOut checkinOut);

    /**
     * 说明:根据主键修改record完整内容
     * @param checkinOut
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(CheckinOut checkinOut);

    /**
     * 说明:根据map查找bean结果集
     * @param checkinOut
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<CheckinOut> listByParams(Map checkinOut);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param checkinOut
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<CheckinOut> listByParams4Page(Map checkinOut);
    
    /**
     * 说明:根据map查找map结果集
     * @param checkinOut
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(CheckinOut checkinOut);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param checkinOut
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<CheckinOut> selectBeanByMap4Page(HashMap map);
    
    int countByBean(CheckinOut record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);


    List<SysUser> listUsersNotCheckIn(String begin, String end);
    List<SysUser> listUsersNotCheckIn2(String begin, String end);
      
}
