package com.dozenx.web.module.interfaceapi.module.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.interfaceapi.module.bean.Module;

public interface ModuleMapper {
    int deleteByPrimaryKey(Integer id);
    
    int insert(Module record);

   
    int insertSelective(Module record);

    Module  selectByPrimaryKey(Integer id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param module
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Module module);

    /**
     * 说明:根据主键修改record完整内容
     * @param module
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Module module);

    /**
     * 说明:根据map查找bean结果集
     * @param module
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Module> listByParams(Map module);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param module
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Module> listByParams4Page(Map module);
    
    /**
     * 说明:根据map查找map结果集
     * @param module
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Module module);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param module
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Module> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Module record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      


     void insertBatch(List<Module> list);
}
