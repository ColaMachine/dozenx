package com.dozenx.web.module.api.apiUrl.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.api.apiUrl.bean.ApiUrl;

public interface ApiUrlMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(ApiUrl record);

   
    int insertSelective(ApiUrl record);

    ApiUrl  selectByPrimaryKey(Long id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param apiUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(ApiUrl apiUrl);

    /**
     * 说明:根据主键修改record完整内容
     * @param apiUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(ApiUrl apiUrl);

    /**
     * 说明:根据map查找bean结果集
     * @param apiUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<ApiUrl> listByParams(Map apiUrl);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param apiUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<ApiUrl> listByParams4Page(Map apiUrl);
    
    /**
     * 说明:根据map查找map结果集
     * @param apiUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(ApiUrl apiUrl);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param apiUrl
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<ApiUrl> selectBeanByMap4Page(HashMap map);
    
    int countByBean(ApiUrl record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      


     void insertBatch(List<ApiUrl> list);
}
