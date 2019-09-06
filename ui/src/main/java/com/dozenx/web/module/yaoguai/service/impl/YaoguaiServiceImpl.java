/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.yaoguai.service.impl;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.yaoguai.bean.Yaoguai;
import com.dozenx.web.module.yaoguai.dao.YaoguaiMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.yaoguai.service.YaoguaiService;

@Service("yaoguaiService")
public class YaoguaiServiceImpl extends BaseService implements YaoguaiService {
    private static final Logger logger = LoggerFactory
            .getLogger(YaoguaiService.class);
    @Resource
    private YaoguaiMapper yaoguaiMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Yaoguai> listByParams4Page(HashMap params) {
        return yaoguaiMapper.listByParams4Page(params);
    }
    public List<Yaoguai> listByParams(HashMap params) {
        return yaoguaiMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return yaoguaiMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param Yaoguai
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Yaoguai yaoguai) {
        // 进行字段验证
      /* ValidateUtil<Yaoguai> vu = new ValidateUtil<Yaoguai>();
        ResultDTO result = vu.valid(yaoguai);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (yaoguai.getId()==null ||  this.selectByPrimaryKey(yaoguai.getId())==null) {
            yaoguai.setCreateTime(new Timestamp(new Date().getTime()));

            yaoguaiMapper.insert(yaoguai);
        } else {
            yaoguaiMapper.updateByPrimaryKeySelective(yaoguai);
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
        yaoguaiMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public Yaoguai selectByPrimaryKey(Long id){
       return yaoguaiMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            yaoguaiMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }



    @Override
    public ResultDTO insertList(List<Yaoguai> list) {
       yaoguaiMapper.insertBatch(list);
        return null;
    }
}
