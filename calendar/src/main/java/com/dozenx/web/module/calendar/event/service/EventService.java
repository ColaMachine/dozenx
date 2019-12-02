/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.calendar.event.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.calendar.event.bean.Event;
import com.dozenx.web.module.calendar.event.dao.EventMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("eventService")
public class EventService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(EventService.class);
    @Resource
    private EventMapper eventMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Event> listByParams4Page(HashMap params) {
        return eventMapper.listByParams4Page(params);
    }
    public List<Event> listByParams(HashMap params) {
        return eventMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return eventMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param Event
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Event event) {
        // 进行字段验证
      /* ValidateUtil<Event> vu = new ValidateUtil<Event>();
        ResultDTO result = vu.valid(event);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (event.getId()==null ||  this.selectByPrimaryKey(event.getId())==null) {
            event.setCreateTime(new Timestamp(new Date().getTime()));

            eventMapper.insert(event);
        } else {
            eventMapper.updateByPrimaryKeySelective(event);
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
        eventMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public Event selectByPrimaryKey(Long id){
       return eventMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            eventMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }

    public List<Event> listRepeatEventByTime(Long start,long end) {//选定涵盖这一天的重复事件  where dtstart < 这一天结尾的时间 并且 until 大于这一天开始的时间
       return eventMapper.listRepeatEventByTime(start,end);
    }

    public List<Event> listRepeatEvent() {//选定涵盖这一天的重复事件  where dtstart < 这一天结尾的时间 并且 until 大于这一天开始的时间
        return eventMapper.listRepeatEvent();
    }
}
