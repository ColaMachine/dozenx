package com.dozenx.web.module.checkin.faceCheckinOut.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.checkin.faceCheckinOut.bean.FaceCheckinOut;

public interface FaceCheckinOutMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(FaceCheckinOut record);

   
    int insertSelective(FaceCheckinOut record);

    FaceCheckinOut  selectByPrimaryKey(Long id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param faceCheckinOut
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(FaceCheckinOut faceCheckinOut);

    /**
     * 说明:根据主键修改record完整内容
     * @param faceCheckinOut
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(FaceCheckinOut faceCheckinOut);

    /**
     * 说明:根据map查找bean结果集
     * @param faceCheckinOut
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<FaceCheckinOut> listByParams(Map faceCheckinOut);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param faceCheckinOut
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<FaceCheckinOut> listByParams4Page(Map faceCheckinOut);
    
    /**
     * 说明:根据map查找map结果集
     * @param faceCheckinOut
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(FaceCheckinOut faceCheckinOut);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param faceCheckinOut
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<FaceCheckinOut> selectBeanByMap4Page(HashMap map);
    
    int countByBean(FaceCheckinOut record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
