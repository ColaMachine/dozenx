package com.dozenx.web.module.checkin.faceInfo.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.checkin.faceInfo.bean.FaceInfo;

public interface FaceInfoMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(FaceInfo record);

   
    int insertSelective(FaceInfo record);

    FaceInfo  selectByPrimaryKey(Long id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param faceInfo
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(FaceInfo faceInfo);

    /**
     * 说明:根据主键修改record完整内容
     * @param faceInfo
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(FaceInfo faceInfo);

    /**
     * 说明:根据map查找bean结果集
     * @param faceInfo
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<FaceInfo> listByParams(Map faceInfo);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param faceInfo
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<FaceInfo> listByParams4Page(Map faceInfo);
    
    /**
     * 说明:根据map查找map结果集
     * @param faceInfo
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(FaceInfo faceInfo);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param faceInfo
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<FaceInfo> selectBeanByMap4Page(HashMap map);
    
    int countByBean(FaceInfo record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
