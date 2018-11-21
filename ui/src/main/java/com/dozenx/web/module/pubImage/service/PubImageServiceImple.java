/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.pubImage.service;

import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.pubImage.bean.PubImage;
import com.dozenx.web.module.pubImage.dao.PubImageMapper;
import com.dozenx.web.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("pubImageService")
@Transactional
public class PubImageServiceImple   implements  PubImageService{
    private static final Logger logger = LoggerFactory
            .getLogger(PubImageServiceImple.class);
    @Resource
    private PubImageMapper pubImageMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    @Override
    public List<PubImage> listByParams4Page(HashMap params) {
        return pubImageMapper.listByParams4Page(params);
    }
    @Override
    public List<PubImage> listByParams(HashMap params) {
        return pubImageMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
     @Override
    public int countByParams(HashMap params) {
           return pubImageMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param PubImage
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    @Override
    public ResultDTO save(PubImage pubImage) {
        // 进行字段验证
      /* ValidateUtil<PubImage> vu = new ValidateUtil<PubImage>();
        ResultDTO result = vu.valid(pubImage);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (pubImage.getId()==null ||  this.selectByPrimaryKey(pubImage.getId())==null) {

            pubImageMapper.insert(pubImage);
        } else {
            pubImageMapper.updateByPrimaryKeySelective(pubImage);
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
    @Override
    public void delete(Integer  id){
        pubImageMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    @Override
    public PubImage selectByPrimaryKey(Integer id){
       return pubImageMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    @Override
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            pubImageMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
