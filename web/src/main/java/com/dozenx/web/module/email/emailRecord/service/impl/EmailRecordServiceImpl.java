/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.email.emailRecord.service.impl;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.email.emailRecord.bean.EmailRecord;
import com.dozenx.web.module.email.emailRecord.dao.EmailRecordMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.email.emailRecord.service.EmailRecordService;

@Service("emailRecordService")
public class EmailRecordServiceImpl extends BaseService implements EmailRecordService {
    private static final Logger logger = LoggerFactory
            .getLogger(EmailRecordService.class);
    @Resource
    private EmailRecordMapper emailRecordMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<EmailRecord> listByParams4Page(HashMap params) {
        return emailRecordMapper.listByParams4Page(params);
    }
    public List<EmailRecord> listByParams(HashMap params) {
        return emailRecordMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return emailRecordMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param EmailRecord
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(EmailRecord emailRecord) {
        // 进行字段验证
      /* ValidateUtil<EmailRecord> vu = new ValidateUtil<EmailRecord>();
        ResultDTO result = vu.valid(emailRecord);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (emailRecord.getId()==null ||  this.selectByPrimaryKey(emailRecord.getId())==null) {

            emailRecordMapper.insert(emailRecord);
        } else {
            emailRecordMapper.updateByPrimaryKeySelective(emailRecord);
        }
        return ResultUtil.getSuccResult();
    }
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public void delete(Integer  id){
        emailRecordMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public EmailRecord selectByPrimaryKey(Integer id){
       return emailRecordMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            emailRecordMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }



    @Override
    public ResultDTO insertList(List<EmailRecord> list) {
       emailRecordMapper.insertBatch(list);
        return null;
    }
}
