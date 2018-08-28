package com.dozenx.web.module.spider.sight.comment.sightComment.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.spider.sight.comment.sightComment.bean.SightComment;

public interface SightCommentMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(SightComment record);

   
    int insertSelective(SightComment record);

    
    SightComment  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param sightComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SightComment sightComment);

    /**
     * 说明:根据主键修改record完整内容
     * @param sightComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SightComment sightComment);

    /**
     * 说明:根据map查找bean结果集
     * @param sightComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SightComment> listByParams(Map sightComment);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param sightComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SightComment> listByParams4Page(Map sightComment);
    
    /**
     * 说明:根据map查找map结果集
     * @param sightComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SightComment sightComment);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param sightComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SightComment> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SightComment record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
