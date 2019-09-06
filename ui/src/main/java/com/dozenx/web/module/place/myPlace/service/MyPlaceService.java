/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.place.myPlace.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.place.myPlace.bean.MyPlace;
import com.dozenx.web.module.place.myPlace.dao.MyPlaceMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("myPlaceService")
public class MyPlaceService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(MyPlaceService.class);
    @Resource
    private MyPlaceMapper myPlaceMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<MyPlace> listByParams4Page(HashMap params) {
        return myPlaceMapper.listByParams4Page(params);
    }
    public List<MyPlace> listByParams(HashMap params) {
        return myPlaceMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return myPlaceMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param MyPlace
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(MyPlace myPlace) {
        // 进行字段验证
      /* ValidateUtil<MyPlace> vu = new ValidateUtil<MyPlace>();
        ResultDTO result = vu.valid(myPlace);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (myPlace.getId()==null ||  this.selectByPrimaryKey(myPlace.getId())==null) {
            myPlace.setCreateTime(new Timestamp(new Date().getTime()));

            myPlaceMapper.insert(myPlace);
        } else {
            myPlaceMapper.updateByPrimaryKeySelective(myPlace);
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
        myPlaceMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public MyPlace selectByPrimaryKey(Integer id){
       return myPlaceMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            myPlaceMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
