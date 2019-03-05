/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.msgInfo.service.impl;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.msgInfo.bean.MsgInfo;
import com.dozenx.web.module.msgInfo.dao.MsgInfoMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.msgInfo.service.MsgInfoService;

@Service("msgInfoService")
public class MsgInfoServiceImpl extends BaseService implements MsgInfoService {
    private static final Logger logger = LoggerFactory
            .getLogger(MsgInfoService.class);
    @Resource
    private MsgInfoMapper msgInfoMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<MsgInfo> listByParams4Page(HashMap params) {
        return msgInfoMapper.listByParams4Page(params);
    }
    public List<MsgInfo> listByParams(HashMap params) {
        return msgInfoMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return msgInfoMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param MsgInfo
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(MsgInfo msgInfo) {
        // 进行字段验证
      /* ValidateUtil<MsgInfo> vu = new ValidateUtil<MsgInfo>();
        ResultDTO result = vu.valid(msgInfo);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (msgInfo.getId()==null ||  this.selectByPrimaryKey(msgInfo.getId())==null) {

            msgInfoMapper.insert(msgInfo);
        } else {
            msgInfo.setUpdatetime(new Timestamp(new Date().getTime()));
            msgInfoMapper.updateByPrimaryKeySelective(msgInfo);
        }
        return ResultUtil.getDataResult(msgInfo);
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
        msgInfoMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public MsgInfo selectByPrimaryKey(Long id){
       return msgInfoMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            msgInfoMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }



    @Override
    public ResultDTO insertList(List<MsgInfo> list) {
       msgInfoMapper.insertBatch(list);
        return null;
    }

    @Override
    public void updateCommmentCountById(Long id) {
        msgInfoMapper.updateCommentCountById(id);
    }

    @Override
    public void updateZan(Long id ){
        msgInfoMapper.updateZan(id);
    }
}
