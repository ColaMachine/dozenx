package com.dozenx.web.core.sms.sysSmsRecord.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.core.sms.sysSmsRecord.bean.SysSmsRecord;

public interface SysSmsRecordMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(SysSmsRecord record);

   
    int insertSelective(SysSmsRecord record);

    SysSmsRecord  selectByPrimaryKey(Long id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param sysSmsRecord
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysSmsRecord sysSmsRecord);

    /**
     * 说明:根据主键修改record完整内容
     * @param sysSmsRecord
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysSmsRecord sysSmsRecord);

    /**
     * 说明:根据map查找bean结果集
     * @param sysSmsRecord
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysSmsRecord> listByParams(Map sysSmsRecord);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param sysSmsRecord
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysSmsRecord> listByParams4Page(Map sysSmsRecord);
    
    /**
     * 说明:根据map查找map结果集
     * @param sysSmsRecord
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysSmsRecord sysSmsRecord);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param sysSmsRecord
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysSmsRecord> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysSmsRecord record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      


     void insertBatch(List<SysSmsRecord> list);
}
