/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.spider.hotel.url.hotelUrl.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.dozenx.web.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.spider.hotel.url.hotelUrl.bean.HotelUrl;
import com.dozenx.web.module.spider.hotel.url.hotelUrl.dao.HotelUrlMapper;
import com.dozenx.util.UUIDUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("hotelUrlService")
public class HotelUrlService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(HotelUrlService.class);
    @Resource
    private HotelUrlMapper hotelUrlMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<HotelUrl> listByParams4Page(HashMap params) {
        return hotelUrlMapper.listByParams4Page(params);
    }
    public List<HotelUrl> listByParams(HashMap params) {
        return hotelUrlMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return hotelUrlMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param HotelUrl
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(HotelUrl hotelUrl) {
        // 进行字段验证
      /* ValidateUtil<HotelUrl> vu = new ValidateUtil<HotelUrl>();
        ResultDTO result = vu.valid(hotelUrl);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (hotelUrl.getId()==null ||  this.selectByPrimaryKey(hotelUrl.getId())==null) {
            hotelUrl.setCreateTime(new Timestamp(new Date().getTime()));

            hotelUrlMapper.insert(hotelUrl);
        } else {
            hotelUrl.setUpdatetime(new Timestamp(new Date().getTime()));
            hotelUrlMapper.updateByPrimaryKeySelective(hotelUrl);
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
        hotelUrlMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public HotelUrl selectByPrimaryKey(Integer id){
       return hotelUrlMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            hotelUrlMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}