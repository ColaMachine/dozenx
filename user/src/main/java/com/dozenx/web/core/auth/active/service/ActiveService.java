/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.active.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.dozenx.core.exception.BizException;
import com.dozenx.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.core.auth.active.bean.Active;
import com.dozenx.web.core.auth.active.dao.ActiveMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("activeService")
public class ActiveService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(ActiveService.class);
    @Resource
    private ActiveMapper activeMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Active> listByParams4Page(HashMap params) {
        return activeMapper.listByParams4Page(params);
    }
    public List<Active> listByParams(HashMap params) {
        return activeMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return activeMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param Active
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Active active) {
        // 进行字段验证
      /* ValidateUtil<Active> vu = new ValidateUtil<Active>();
        ResultDTO result = vu.valid(active);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (active.getId()==null ||  this.selectByPrimaryKey(active.getId())==null) {
            active.setCreateTime(new Timestamp(new Date().getTime()));

            activeMapper.insert(active);
        } else {
            activeMapper.updateByPrimaryKeySelective(active);
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
        activeMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public Active selectByPrimaryKey(Integer id){
       return activeMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            activeMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }

    /**
     * 根据账号 验证码 和 类型 查找未激活的记录
     * @param account
     * @param type
     * @param code
     * @author zhangzw 2018年10月9日11:22:32
     * @return
     */

    public Active selectByParam(String account,String type,String code){
        HashMap<String,Object> params =new HashMap<>( );
        params.put("status",0);
        params.put("account",account);
        params.put("code",code);
        List<Active> list =  this.listByParams(params);
        if(list ==null || list.size()==0){
            return null;
        }
        return list.get(0);
    }


    //对外暴露的接口 将记录修改为激活状态
    public ResultDTO updateActive(String account ,String type,String code ) {
        Active active =selectByParam(account,type,code);
        if(active == null)
            throw new BizException(30205153,"激活失败");
        active.setStatus(1);

        active.setActivedTime(DateUtil.getNowTimeStamp());
        this.activeMapper.updateByPrimaryKey(active);
        return ResultUtil.getSuccResult();
    }
    //对外暴露的接口 新增一条 注册验证码
    public ResultDTO save(String account ,String type,String code ) {
        Active active =new Active() ;
        active.setAccount(account);
        active.setCode(code);
        active.setType(type);
        active.setStatus(0);
        active.setCreateTime(DateUtil.getNowTimeStamp());
        this.activeMapper.insert(active);
        return ResultUtil.getSuccResult();
    }
}
