package com.dozenx.web.module.spider.art.comment.artComment.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.spider.art.comment.artComment.bean.ArtComment;

public interface ArtCommentMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(ArtComment record);

   
    int insertSelective(ArtComment record);

    
    ArtComment  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param artComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(ArtComment artComment);

    /**
     * 说明:根据主键修改record完整内容
     * @param artComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(ArtComment artComment);

    /**
     * 说明:根据map查找bean结果集
     * @param artComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<ArtComment> listByParams(Map artComment);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param artComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<ArtComment> listByParams4Page(Map artComment);
    
    /**
     * 说明:根据map查找map结果集
     * @param artComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(ArtComment artComment);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param artComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<ArtComment> selectBeanByMap4Page(HashMap map);
    
    int countByBean(ArtComment record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
