/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.checkin.checkinOut.service;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.checkin.checkinOut.bean.CheckinOut;
import com.dozenx.web.module.checkin.checkinOut.dao.CheckinOutMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("checkinOutService")
public class CheckinOutService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(CheckinOutService.class);
    @Resource
    private CheckinOutMapper checkinOutMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<CheckinOut> listByParams4Page(HashMap params) {
        return checkinOutMapper.listByParams4Page(params);
    }
    public List<CheckinOut> listByParams(HashMap params) {
        return checkinOutMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return checkinOutMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param CheckinOut
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(CheckinOut checkinOut) {
        // 进行字段验证
      /* ValidateUtil<CheckinOut> vu = new ValidateUtil<CheckinOut>();
        ResultDTO result = vu.valid(checkinOut);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (checkinOut.getId()==null ||  this.selectByPrimaryKey(checkinOut.getId())==null) {

            checkinOutMapper.insert(checkinOut);
        } else {
            checkinOutMapper.updateByPrimaryKeySelective(checkinOut);
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
        checkinOutMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public CheckinOut selectByPrimaryKey(Long id){
       return checkinOutMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            checkinOutMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }

    public List<SysUser> listUsersNotCheckIn(String begin,String end){
        return checkinOutMapper.listUsersNotCheckIn(begin,end);
    }
    public List<SysUser> listUsersNotCheckInMachineAndCamera(String begin,String end){
        return checkinOutMapper.listUsersNotCheckInMachineAndCamera(begin,end);
    }
}
