package com.dozenx.web.module.artical.dao;


import com.dozenx.web.module.artical.bean.Artical;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ArticalMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(Artical record);

   
    int insertSelective(Artical record);

    
    Artical selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param artical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Artical artical);

    /**
     * 说明:根据主键修改record完整内容
     * @param artical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Artical artical);

    /**
     * 说明:根据map查找bean结果集
     * @param artical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Artical> listByParams(Map artical);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param artical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Artical> listByParams4Page(Map artical);


    List<HashMap<String,Object>> listWithUserInfoByParams4Page(Map artical);
    /**
     * 说明:根据map查找map结果集
     * @param artical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Artical artical);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param artical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Artical> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Artical record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);


    void updateCommentCountById(Long id);

    void updateViewCount(@Param("id") Long articleId, @Param("viewCount")int viewCount);
}
