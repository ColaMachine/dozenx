package com.dozenx.web.module.pubComment.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.dozenx.web.module.pubComment.bean.PubComment;

public interface PubCommentMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(PubComment record);

   
    int insertSelective(PubComment record);

    
    PubComment selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param pubComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(PubComment pubComment);

    /**
     * 说明:根据主键修改record完整内容
     * @param pubComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(PubComment pubComment);

    /**
     * 说明:根据map查找bean结果集
     * @param pubComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<PubComment> listByParams(Map pubComment);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param pubComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<PubComment> listByParams4Page(Map pubComment);
    
    /**
     * 说明:根据map查找map结果集
     * @param pubComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(PubComment pubComment);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param pubComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<PubComment> selectBeanByMap4Page(HashMap map);
    
    int countByBean(PubComment record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
