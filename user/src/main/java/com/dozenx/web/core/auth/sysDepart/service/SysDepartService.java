/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysDepart.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.core.auth.sysDepart.bean.SysDepart;
import com.dozenx.web.core.auth.sysDepart.dao.SysDepartMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("sysDepartService")
public class SysDepartService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysDepartService.class);
    @Resource
    private SysDepartMapper sysDepartMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysDepart> listByParams4Page(HashMap params) {
        return sysDepartMapper.listByParams4Page(params);
    }
    public List<SysDepart> listByParams(HashMap params) {
        return sysDepartMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysDepartMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysDepart
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysDepart sysDepart) {
        // 进行字段验证
      /* ValidateUtil<SysDepart> vu = new ValidateUtil<SysDepart>();
        ResultDTO result = vu.valid(sysDepart);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (sysDepart.getId()==null ||  this.selectByPrimaryKey(sysDepart.getId())==null) {
            sysDepart.setCreateTime(new Timestamp(new Date().getTime()));

            sysDepartMapper.insert(sysDepart);
        } else {
            sysDepartMapper.updateByPrimaryKeySelective(sysDepart);
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
        sysDepartMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysDepart selectByPrimaryKey(Long id){
       return sysDepartMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysDepartMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
