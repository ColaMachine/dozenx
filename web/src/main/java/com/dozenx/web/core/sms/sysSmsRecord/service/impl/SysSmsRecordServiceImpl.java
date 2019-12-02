/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.sms.sysSmsRecord.service.impl;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.core.sms.sysSmsRecord.bean.SysSmsRecord;
import com.dozenx.web.core.sms.sysSmsRecord.dao.SysSmsRecordMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.sms.sysSmsRecord.service.SysSmsRecordService;

@Service("sysSmsRecordService")
public class SysSmsRecordServiceImpl extends BaseService implements SysSmsRecordService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysSmsRecordService.class);
    @Resource
    private SysSmsRecordMapper sysSmsRecordMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysSmsRecord> listByParams4Page(HashMap params) {
        return sysSmsRecordMapper.listByParams4Page(params);
    }
    public List<SysSmsRecord> listByParams(HashMap params) {
        return sysSmsRecordMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysSmsRecordMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysSmsRecord
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysSmsRecord sysSmsRecord) {
        // 进行字段验证
      /* ValidateUtil<SysSmsRecord> vu = new ValidateUtil<SysSmsRecord>();
        ResultDTO result = vu.valid(sysSmsRecord);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (sysSmsRecord.getId()==null ||  this.selectByPrimaryKey(sysSmsRecord.getId())==null) {
            sysSmsRecord.setCreateTime(new Timestamp(new Date().getTime()));

            sysSmsRecordMapper.insert(sysSmsRecord);
        } else {
            sysSmsRecordMapper.updateByPrimaryKeySelective(sysSmsRecord);
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
    public void delete(Long  id){
        sysSmsRecordMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysSmsRecord selectByPrimaryKey(Long id){
       return sysSmsRecordMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysSmsRecordMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }



    @Override
    public ResultDTO insertList(List<SysSmsRecord> list) {
       sysSmsRecordMapper.insertBatch(list);
        return null;
    }
}
