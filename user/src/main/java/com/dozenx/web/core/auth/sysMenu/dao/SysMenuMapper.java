package com.dozenx.web.core.auth.sysMenu.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.core.auth.sysMenu.bean.SysMenu;

public interface SysMenuMapper {
    int deleteByPrimaryKey(Integer id);
    
    int insert(SysMenu record);

   
    int insertSelective(SysMenu record);

    SysMenu  selectByPrimaryKey(Integer id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param sysMenu
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysMenu sysMenu);

    /**
     * 说明:根据主键修改record完整内容
     * @param sysMenu
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysMenu sysMenu);

    /**
     * 说明:根据map查找bean结果集
     * @param sysMenu
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysMenu> listByParams(Map sysMenu);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param sysMenu
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysMenu> listByParams4Page(Map sysMenu);
    
    /**
     * 说明:根据map查找map结果集
     * @param sysMenu
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysMenu sysMenu);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param sysMenu
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysMenu> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysMenu record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      


     void insertBatch(List<SysMenu> list);
    /**
     * @Author: dozen.zhang
     * @Description:根据用户id 查菜单
     * @Date: 2018/2/8
     */
    List<SysMenu> selectMenuByUserId(Long userId);
    /**
     * @Author: dozen.zhang
     * @Description:根据角色id 查菜单
     * @Date: 2018/2/8
     */
    List<SysMenu> selectMenuByRoleId(Long roleId);

    /**
     * @Author: dozen.zhang
     * @Description:根据角色id 查找所有菜单
     * @Date: 2018/2/8
     */
    List<SysMenu> selectAllMenu();
}
