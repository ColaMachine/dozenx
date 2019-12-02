/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.calendar.calendar.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.calendar.calendar.bean.Calendar;
import com.dozenx.web.module.calendar.calendar.dao.CalendarMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("calendarService")
public class CalendarService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(CalendarService.class);
    @Resource
    private CalendarMapper calendarMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Calendar> listByParams4Page(HashMap params) {
        return calendarMapper.listByParams4Page(params);
    }
    public List<Calendar> listByParams(HashMap params) {
        return calendarMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return calendarMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param Calendar
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Calendar calendar) {
        // 进行字段验证
      /* ValidateUtil<Calendar> vu = new ValidateUtil<Calendar>();
        ResultDTO result = vu.valid(calendar);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (calendar.getId()==null ||  this.selectByPrimaryKey(calendar.getId())==null) {
            calendar.setCreateTime(new Timestamp(new Date().getTime()));

            calendarMapper.insert(calendar);
        } else {
            calendarMapper.updateByPrimaryKeySelective(calendar);
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
        calendarMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public Calendar selectByPrimaryKey(Long id){
       return calendarMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            calendarMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
