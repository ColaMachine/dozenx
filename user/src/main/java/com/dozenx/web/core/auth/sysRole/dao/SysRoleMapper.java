package com.dozenx.web.core.auth.sysRole.dao;

import com.dozenx.web.core.auth.sysRole.bean.SysRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Author: dozen.zhang
 * @Description:角色dao层
 * @Date: 2018/2/28
 */
public interface SysRoleMapper {
    /**
     * @Author: dozen.zhang
     * @Description:物理删除数据
     * @Date: 2018/2/28
     */
    int realDeleteByPrimaryKey(Long id);
    /**
     * @Author: dozen.zhang
     * @Description:逻辑删除数据
     * @Date: 2018/2/28
     */
    int deleteByPrimaryKey(Long id);
    /**
     * @Author: dozen.zhang
     * @Description:新增角色数据
     * @Date: 2018/2/28
     */
    int insert(SysRole record);

    /**
     * @Author: dozen.zhang
     * @Description:新增角色数据
     * @Date: 2018/2/28
     */
    int insertSelective(SysRole record);

    /**
     * @Author: dozen.zhang
     * @Description:获取角色数据
     * @Date: 2018/2/28
     */
    SysRole  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param sysRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysRole sysRole);

    /**
     * 说明:根据主键修改record完整内容
     * @param sysRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysRole sysRole);

    /**
     * 说明:根据map查找bean结果集
     * @param sysRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysRole> listByParams(Map sysRole);

    /**
     * 说明:根据bean查找bean结果集
     * @param sysRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysRole> listByParams4Page(Map sysRole);

    /**
     * 说明:根据map查找map结果集
     * @param sysRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysRole sysRole);*/


    /**
     * 说明:根据map查找map结果集
     * @param sysRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysRole> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysRole record);*/
    /**
     * @Author: dozen.zhang
     * @Description:角色个数
     * @Date: 2018/2/28
     */
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

    /**
     * 说明:根据bean查找bean结果集
     * @param userId
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysRole> listByUserId(Long userId);
}
