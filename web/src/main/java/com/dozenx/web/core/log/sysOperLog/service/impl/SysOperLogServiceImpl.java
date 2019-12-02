/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.log.sysOperLog.service.impl;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.core.log.sysOperLog.bean.SysOperLog;
import com.dozenx.web.core.log.sysOperLog.dao.SysOperLogMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.log.sysOperLog.service.SysOperLogService;

@Service("sysOperLogService")
public class SysOperLogServiceImpl extends BaseService implements SysOperLogService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysOperLogService.class);
    @Resource
    private SysOperLogMapper sysOperLogMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysOperLog> listByParams4Page(HashMap params) {
        return sysOperLogMapper.listByParams4Page(params);
    }
    public List<SysOperLog> listByParams(HashMap params) {
        return sysOperLogMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysOperLogMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysOperLog
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysOperLog sysOperLog) {
        // 进行字段验证
      /* ValidateUtil<SysOperLog> vu = new ValidateUtil<SysOperLog>();
        ResultDTO result = vu.valid(sysOperLog);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (sysOperLog.getId()==null ||  this.selectByPrimaryKey(sysOperLog.getId())==null) {
            sysOperLog.setCreateTime(new Timestamp(new Date().getTime()));

            sysOperLogMapper.insert(sysOperLog);
        } else {
            sysOperLogMapper.updateByPrimaryKeySelective(sysOperLog);
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
        sysOperLogMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysOperLog selectByPrimaryKey(Integer id){
       return sysOperLogMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysOperLogMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }



    @Override
    public ResultDTO insertList(List<SysOperLog> list) {
       sysOperLogMapper.insertBatch(list);
        return null;
    }

    /**
     * 操作日志分页查询
     */

    /**
     * 操作日志分页查询
     */
    @Override
    public List<SysOperLog> getListByParam(String keywords, String userName, String date, String createTimeBegin, String createTimeEnd, Integer userId, Page page) {
        logger.debug("系统操作日志：service传入----:: " + "关键字----" + keywords + "用户名----" + userName + "开始时间----" + createTimeBegin
                + "page---" + page);
        HashMap params =new HashMap();
        params.put("keywords",keywords);
        params.put("userName",userName);
        params.put("page",page);
        params.put("date",date);
        params.put("createTimeBegin",createTimeBegin);
        params.put("createTimeEnd",createTimeEnd);
        List<SysOperLog> operLogList = sysOperLogMapper.listByParams4Page(params);
        return operLogList;
    }






}
