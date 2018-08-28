package com.dozenx.web.module.blogComment.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.blogComment.bean.BlogComment;

public interface BlogCommentMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(BlogComment record);

   
    int insertSelective(BlogComment  record);

    
    BlogComment  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param blogComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(BlogComment blogComment);

    /**
     * 说明:根据主键修改record完整内容
     * @param blogComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(BlogComment blogComment);

    /**
     * 说明:根据map查找bean结果集
     * @param blogComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<BlogComment> listByParams(Map blogComment);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param blogComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<BlogComment> listByParams4Page(Map blogComment);
    
    /**
     * 说明:根据map查找map结果集
     * @param blogComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(BlogComment blogComment);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param blogComment
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<BlogComment> selectBeanByMap4Page(HashMap map);
    
    int countByBean(BlogComment record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
