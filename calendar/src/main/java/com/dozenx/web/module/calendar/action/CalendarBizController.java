package com.dozenx.web.module.calendar.action;

import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.HttpRequestUtil;
import com.dozenx.swagger.annotation.APIs;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.module.calendar.calendar.action.CalendarController;
import com.dozenx.web.module.calendar.calendar.service.CalendarManagerService;
import com.dozenx.web.module.calendar.calendar.service.CalendarService;
import com.dozenx.web.module.calendar.event.bean.Event;
import com.dozenx.web.module.calendar.event.service.EventService;
import com.dozenx.web.module.calendar.instance.bean.Instance;
import com.dozenx.web.module.calendar.instance.service.InstanceService;
import com.dozenx.web.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:37 2018/10/12
 * @Modified By:
 */

@APIs(description = "日历表")
@Controller

@RequestMapping("/calendar/biz")
public class
CalendarBizController extends BaseController {
    /**
     * 日志
     **/
    private Logger logger = LoggerFactory.getLogger(CalendarController.class);
    /**
     * 权限service
     **/
    @Autowired
    private CalendarService calendarService;

    @Autowired
    private CalendarManagerService calendarManagerService;

    @Autowired
    private InstanceService instanceService;

    @Autowired
    private EventService eventService;

    public static void main(String args[]) {
        //添加一个邮件规则


    }

    /**
     * 每分钟监测是否有http请求任务
     */
  //  @Scheduled(cron = "0 */1 * * * ?")
    public void tictac() {

        try {
            if (RedisUtil.incr("risksrv_calendar_tictac" + DateUtil.toDateStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_DASH), 30) > 1) {//同一分钟内只允许一个进入
                logger.info("统一是建只允许一次定时任务");
                return ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            EmailUtil.send("371452875@qq.com","hell","hello");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        logger.info("滴答calendar event 任务");
        //每隔一分钟检测//检查数据的第一个元素是否执行了

        //先把今天的任务全部加载到一个队列里 按照时间顺序排列
        //所以要一个instance 状态是否过了 过了的话 就不用再查询出来了
        //取出当前一分钟的日历项目
        Calendar calendar = Calendar.getInstance();
        long start = calendar.getTimeInMillis() / 60000 * 60 * 1000;//去除秒数
        long end = start + 30000;

        List<Instance> instances = instanceService.listByStartAndEndTime(start, end);
        //判断当前的任务要不要处理 如果要处理的话 起一个线程 让他们去处理掉 一般都是url
        for (Instance instance : instances) {
            //通过instance 找到events
            Event event = eventService.selectByPrimaryKey(instance.getEventId());
            String desc = event.getDescription();
            if (desc.indexOf("curl") > -1) {
                String result = HttpRequestUtil.sendGet(desc.replace("curl", "").trim());
                logger.info(result);
            }
        }
    }

    /*

    每天晚上生成第二天的日程实例
     */
    @Scheduled(cron = "0 55 23 * * ?")
    //生成日程实例

    @RequestMapping("/tick/day")
    public void tictacEveryDay() {
        //找到 所有的可重复事件
        Long[] time = DateUtil.getTodayStartMills();
        List<Event> events = eventService.listRepeatEvent();
        for (Event event : events) {
            calendarManagerService.addTodayAndNextDayInstanceByEvent(event);
        }
    }
}
