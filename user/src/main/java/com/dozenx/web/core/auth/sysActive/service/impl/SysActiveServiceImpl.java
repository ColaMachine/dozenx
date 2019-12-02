/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysActive.service.impl;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.dozenx.common.exception.BizException;
import com.dozenx.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.core.auth.sysActive.bean.SysActive;
import com.dozenx.web.core.auth.sysActive.dao.SysActiveMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.auth.sysActive.service.SysActiveService;

@Service("sysActiveService")
public class SysActiveServiceImpl extends BaseService implements SysActiveService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysActiveService.class);
    @Resource
    private SysActiveMapper sysActiveMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysActive> listByParams4Page(HashMap params) {
        return sysActiveMapper.listByParams4Page(params);
    }
    public List<SysActive> listByParams(HashMap params) {
        return sysActiveMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysActiveMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysActive
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysActive sysActive) {
        // 进行字段验证
      /* ValidateUtil<SysActive> vu = new ValidateUtil<SysActive>();
        ResultDTO result = vu.valid(sysActive);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段

       //判断是更新还是插入
        if (sysActive.getId()==null ||  this.selectByPrimaryKey(sysActive.getId())==null) {

            sysActiveMapper.insert(sysActive);
        } else {
            sysActiveMapper.updateByPrimaryKeySelective(sysActive);
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
        sysActiveMapper.deleteByPrimaryKey(id);
    }
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysActive selectByPrimaryKey(Integer id){
       return sysActiveMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysActiveMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }



    @Override
    public ResultDTO insertList(List<SysActive> list) {
       sysActiveMapper.insertBatch(list);
        return null;
    }


    /**
     * 根据账号 验证码 和 类型 查找未激活的记录
     * @param account
     * @param type
     * @param code
     * @author zhangzw 2018年10月9日11:22:32
     * @return
     */

    public SysActive selectByParam(String account,String type,String code){
        HashMap<String,Object> params =new HashMap<>( );
        params.put("status",0);
        params.put("account",account);
        params.put("code",code);
        List<SysActive> list =  this.listByParams(params);
        if(list ==null || list.size()==0){
            return null;
        }
        return list.get(0);
    }


    //对外暴露的接口 将记录修改为激活状态
    public ResultDTO updateActive(String account ,String type,String code ) {
        SysActive active =selectByParam(account,type,code);
        if(active == null)
            throw new BizException(30205153,"激活失败");
        active.setActStatus(1);

        active.setActTime(DateUtil.getNowTimeStamp());
        this.sysActiveMapper.updateByPrimaryKey(active);
        return ResultUtil.getSuccResult();
    }
    //对外暴露的接口 新增一条 注册验证码
    public ResultDTO save(String account ,String type,String code ) {
        SysActive active =new SysActive() ;
       // active.setAccount(account);
        active.setActCode(code);
        active.setActType(type);
        active.setActStatus(0);
        active.setAddTime(DateUtil.getNowTimeStamp());
        this.sysActiveMapper.insert(active);
        return ResultUtil.getSuccResult();
    }



}
