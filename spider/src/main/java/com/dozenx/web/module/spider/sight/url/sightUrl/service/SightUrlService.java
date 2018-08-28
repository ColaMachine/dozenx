/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.spider.sight.url.sightUrl.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.spider.sight.url.sightUrl.bean.SightUrl;
import com.dozenx.web.module.spider.sight.url.sightUrl.dao.SightUrlMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.UUIDUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("sightUrlService")
public class SightUrlService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SightUrlService.class);
    @Resource
    private SightUrlMapper sightUrlMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SightUrl> listByParams4Page(HashMap params) {
        return sightUrlMapper.listByParams4Page(params);
    }
    public List<SightUrl> listByParams(HashMap params) {
        return sightUrlMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sightUrlMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SightUrl
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SightUrl sightUrl) {
        // 进行字段验证
      /* ValidateUtil<SightUrl> vu = new ValidateUtil<SightUrl>();
        ResultDTO result = vu.valid(sightUrl);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (sightUrl.getId()==null ||  this.selectByPrimaryKey(sightUrl.getId())==null) {
            sightUrl.setCreateTime(new Timestamp(new Date().getTime()));

            sightUrlMapper.insert(sightUrl);
        } else {
            sightUrl.setUpdatetime(new Timestamp(new Date().getTime()));
            sightUrlMapper.updateByPrimaryKeySelective(sightUrl);
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
        sightUrlMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SightUrl selectByPrimaryKey(Integer id){
       return sightUrlMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sightUrlMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
