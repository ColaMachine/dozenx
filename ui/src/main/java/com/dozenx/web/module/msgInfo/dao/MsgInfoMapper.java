package com.dozenx.web.module.msgInfo.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.msgInfo.bean.MsgInfo;

public interface MsgInfoMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(MsgInfo record);

   
    int insertSelective(MsgInfo record);

    MsgInfo  selectByPrimaryKey(Long id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param msgInfo
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(MsgInfo msgInfo);

    /**
     * 说明:根据主键修改record完整内容
     * @param msgInfo
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(MsgInfo msgInfo);

    /**
     * 说明:根据map查找bean结果集
     * @param msgInfo
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<MsgInfo> listByParams(Map msgInfo);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param msgInfo
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<MsgInfo> listByParams4Page(Map msgInfo);
    
    /**
     * 说明:根据map查找map结果集
     * @param msgInfo
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(MsgInfo msgInfo);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param msgInfo
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<MsgInfo> selectBeanByMap4Page(HashMap map);
    
    int countByBean(MsgInfo record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      


     void insertBatch(List<MsgInfo> list);

    void updateCommentCountById(Long id );

    void updateZan(Long id);
}
