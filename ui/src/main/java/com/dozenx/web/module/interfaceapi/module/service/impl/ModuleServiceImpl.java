/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.interfaceapi.module.service.impl;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.interfaceapi.module.bean.Module;
import com.dozenx.web.module.interfaceapi.module.dao.ModuleMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.interfaceapi.module.service.ModuleService;

@Service("moduleService")
public class ModuleServiceImpl extends BaseService implements ModuleService {
    private static final Logger logger = LoggerFactory
            .getLogger(ModuleService.class);
    @Resource
    private ModuleMapper moduleMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Module> listByParams4Page(HashMap params) {
        return moduleMapper.listByParams4Page(params);
    }
    public List<Module> listByParams(HashMap params) {
        return moduleMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return moduleMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param Module
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Module module) {
        // 进行字段验证
      /* ValidateUtil<Module> vu = new ValidateUtil<Module>();
        ResultDTO result = vu.valid(module);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (module.getId()==null ||  this.selectByPrimaryKey(module.getId())==null) {
            module.setCreateTime(new Timestamp(new Date().getTime()));

            moduleMapper.insert(module);
        } else {
            moduleMapper.updateByPrimaryKeySelective(module);
        }
        return ResultUtil.getDataResult(module);
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
        moduleMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public Module selectByPrimaryKey(Integer id){
       return moduleMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            moduleMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }



    @Override
    public ResultDTO insertList(List<Module> list) {
       moduleMapper.insertBatch(list);
        return null;
    }
}
