package com.dozenx.web.core.auth.sysUserDepart.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.core.auth.sysUserDepart.bean.SysUserDepart;

public interface SysUserDepartMapper {
    
    int insert(SysUserDepart record);

   
    int insertSelective(SysUserDepart record);


    /**
     * 说明:根据map查找bean结果集
     * @param sysUserDepart
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysUserDepart> listByParams(Map sysUserDepart);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param sysUserDepart
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysUserDepart> listByParams4Page(Map sysUserDepart);
    
    /**
     * 说明:根据map查找map结果集
     * @param sysUserDepart
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysUserDepart sysUserDepart);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param sysUserDepart
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysUserDepart> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysUserDepart record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
    int deleteExtra(HashMap map);
}
