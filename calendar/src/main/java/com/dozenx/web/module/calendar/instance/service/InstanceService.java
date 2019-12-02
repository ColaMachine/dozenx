/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.calendar.instance.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.calendar.instance.bean.Instance;
import com.dozenx.web.module.calendar.instance.dao.InstanceMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("instanceService")
public class InstanceService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(InstanceService.class);
    @Resource
    private InstanceMapper instanceMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Instance> listByParams4Page(HashMap params) {
        return instanceMapper.listByParams4Page(params);
    }
    public List<Instance> listByParams(HashMap params) {
        return instanceMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return instanceMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param Instance
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Instance instance) {
        // 进行字段验证
      /* ValidateUtil<Instance> vu = new ValidateUtil<Instance>();
        ResultDTO result = vu.valid(instance);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (instance.getId()==null ||  this.selectByPrimaryKey(instance.getId())==null) {

            instanceMapper.insert(instance);
        } else {
            instanceMapper.updateByPrimaryKeySelective(instance);
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
        instanceMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public Instance selectByPrimaryKey(Long id){
       return instanceMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            instanceMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }

    /**
     * diy
     * @param start
     * @param end
     * @return
     */
    public List<Instance> listByStartAndEndTime(Long start,Long end){
      return   instanceMapper.listByStartAndEndTime(start,end);

    }

}
