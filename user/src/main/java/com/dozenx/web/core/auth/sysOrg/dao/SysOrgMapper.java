package com.dozenx.web.core.auth.sysOrg.dao;

import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;

import com.dozenx.web.core.auth.sysOrg.bean.SysOrg;

public interface SysOrgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysOrg record);


    int insertSelective(SysOrg record);

    SysOrg selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     *
     * @param sysOrg
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysOrg sysOrg);

    /**
     * 说明:根据主键修改record完整内容
     *
     * @param sysOrg
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysOrg sysOrg);

    /**
     * 说明:根据map查找bean结果集
     *
     * @param sysOrg
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysOrg> listByParams(Map sysOrg);

    /**
     * 说明:根据bean查找bean结果集
     *
     * @param sysOrg
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysOrg> listByParams4Page(Map sysOrg);

    /**
     * 说明:根据map查找map结果集
     * @param sysOrg
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /* List<Map> selectMapByBean4Page(SysOrg sysOrg);*/


    /**
     * 说明:根据map查找map结果集
     *
     * @param sysOrg
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysOrg> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysOrg record);*/

    int countByParams(HashMap map);

    int countByOrParams(HashMap map);


    void insertBatch(List<SysOrg> list);

     public String getOrgNameByCamera(Long camera);
}
