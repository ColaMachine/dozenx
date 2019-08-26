package com.dozenx.web.module.email.emailRecord.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.email.emailRecord.bean.EmailRecord;

public interface EmailRecordMapper {
    int deleteByPrimaryKey(Integer id);
    
    int insert(EmailRecord record);

   
    int insertSelective(EmailRecord record);

    EmailRecord  selectByPrimaryKey(Integer id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param emailRecord
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(EmailRecord emailRecord);

    /**
     * 说明:根据主键修改record完整内容
     * @param emailRecord
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(EmailRecord emailRecord);

    /**
     * 说明:根据map查找bean结果集
     * @param emailRecord
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<EmailRecord> listByParams(Map emailRecord);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param emailRecord
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<EmailRecord> listByParams4Page(Map emailRecord);
    
    /**
     * 说明:根据map查找map结果集
     * @param emailRecord
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(EmailRecord emailRecord);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param emailRecord
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<EmailRecord> selectBeanByMap4Page(HashMap map);
    
    int countByBean(EmailRecord record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      


     void insertBatch(List<EmailRecord> list);
}
