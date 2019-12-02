package com.dozenx.web.module.calendar.calendar.service;

import com.dozenx.common.exception.BizException;
import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.module.calendar.event.bean.Event;
import com.dozenx.web.module.calendar.event.service.EventService;
import com.dozenx.web.module.calendar.instance.bean.Instance;
import com.dozenx.web.module.calendar.instance.service.InstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 16:40 2018/10/12
 * @Modified By:
 */
@Service("calendarManangerService")
public class CalendarManagerService {
    private static Logger logger = LoggerFactory.getLogger(CalendarManagerService.class);
    public static CalendarManagerService calendarMananger = null;
    public static final Object lock = new Object();
    @Autowired
    private InstanceService instanceService;
    @Autowired
    private EventService eventService;

    public static CalendarManagerService getInstance() {
        if (calendarMananger == null) {
            synchronized (lock) {
                if (calendarMananger == null) {
                    calendarMananger = new CalendarManagerService();
                }
            }
        }
        return calendarMananger;
    }


    public void addTodayAndNextDayInstanceByEvent(Event event) {//一般在零点0分执行
        //校验
        if (StringUtil.isNotBlank(event.getRrule())) {//如果rule 不为空说明是重复事件
            String durationStr = event.getDuration();//获取持续时间
            Long duration = null;
            if (StringUtil.isNotBlank(durationStr)) {
                if (durationStr.startsWith("pt")) {
                    durationStr = durationStr.substring(2);//去掉前缀pt
                    if (durationStr.endsWith("M")) {//持续时间 分钟
                        duration = Long.valueOf(durationStr.replace("M", "")) * 60 * 1000;
                    } else if (durationStr.endsWith("D")) {//持续时间 天
                        duration = Long.valueOf(durationStr.replace("D", "")) * 24 * 60 * 60 * 1000;
                    } else if (durationStr.endsWith("H")) {//持续时间 小时
                        duration = Long.valueOf(durationStr.replace("H", "")) * 60 * 60 * 1000;
                    }
                } else {//如果传过来的是一个数字
                    duration = Long.valueOf(durationStr);
                }
            }

            event.getDtstart();//获得开始的日期
            Calendar calendar = Calendar.getInstance();//今天
            calendar.setTimeInMillis(event.getDtstart());//
            String freq = "";
            String rruleAry[] = event.getRrule().split(";");//重复的规则 解析
            String untilStr = "";
            String counntStr = "";
            for (int i = 0; i < rruleAry.length; i++) {//重复的规则 解析
                String[] keyValueAry = rruleAry[i].split("=");//名值对
                if (keyValueAry[0].equals("FREQ")) {
                    freq = keyValueAry[1];

                    //校验
                    if (StringUtil.isBlank(freq) || (!freq.equals("MONTHLY") && !freq.equals("WEEKLY") && !freq.equals("DAILY"))) {//不为空 且 必须为WEEKLY 或者daily
                        throw new BizException(30705109, "freq 必须是MONTHLY WEEKLY DAILY");
                    }

                } else if (keyValueAry[0].equals("UNTIL")) {//直到哪天
                    untilStr = keyValueAry[1];
                } else if (keyValueAry[0].equals("COUNT")) {// 重复的个数
                    counntStr = keyValueAry[1];
                }
            }

            Long until = null;//如果没有until的话 说明是一直持续的
            if (StringUtil.isNotBlank(counntStr)) {//count 代表的是重复的次数
                Long count = Long.valueOf(counntStr);

                if (freq.equals("WEEKLY")) {//每周
                    until = event.getDtstart() + count * 7 * 24 * 60 * 60 * 1000;
                    //theEndTime+=theBeginTime+duration;

                } else if (freq.equals("DAILY")) {//每周
                    until = event.getDtstart() + count * 24 * 60 * 60 * 1000;
                    // theEndTime+= theBeginTime+duration;

                } else if (freq.equals("MONTHLY")) {//每月同一天
                    // until = event.getDtstart() + count * 24 * 60 * 60 * 1000;
                    Calendar tmpCalendar = Calendar.getInstance();
                    tmpCalendar.setTimeInMillis(event.getDtstart());
                    tmpCalendar.add(Calendar.MONTH, 1);//切到下个月
                    until = tmpCalendar.getTimeInMillis();
                    // theEndTime+= theBeginTime+duration;

                }
                untilStr = "" + until;//转成字符串后面用


            }


            Long[] startEndAry = DateUtil.getTodayStartMills();//获得今天的凌晨的时间和午夜的时间
            Long todayStart = startEndAry[0];
            Long todayEnd = startEndAry[1];
            Long nextDayEnd = startEndAry[1] + 24 * 60 * 60 * 1000;

            if (StringUtil.isBlank(untilStr)) {//说明是永久的
                until = todayStart + 7 * 24 * 60 * 60 * 1000;
                untilStr = until + "";
            }


            //如果截止时间不为空的话 循环一直取到截止时间 生成若干个实例
            Long thePointerTime = event.getDtstart();//获得事件开始时间
            if (StringUtil.isNotBlank(untilStr)) {
                until = Long.valueOf(untilStr);
                event.setUntil(until);
                eventService.save(event);//把until 更新到事件当中
                // Long theEndTime =theBeginTime+duration;// event.getDtend();//获得事件开始时间

                if (thePointerTime > until)
                    logger.info("逻辑出错 时间超出截止时间");
                event.setUntil(until);
                while (true) {//如果是1年的话就要插入全年的实例
                    //只计算当天的实例
                    //加入instance实例

                    if (thePointerTime >= todayStart) {//如果小于当前事件 就不要插入instance 了
                        //实际上小于当前事件不需要插入instance了
                        //生成指定实例

                        if (thePointerTime > nextDayEnd) {//超出了本次核算范围
                            break;
                        }
                        Instance instance = new Instance();
                        instance.setBegin(thePointerTime);//设置开始时间
                        instance.setEnd(thePointerTime + duration);//设置当天结束时间
                        calendar.setTimeInMillis(thePointerTime);//将calendar 变成开始时间 方便计算时分秒
                        instance.setStartMinute(calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE));//开始的分钟

                        instance.setStartDay(calendar.getTime());

                        calendar.setTimeInMillis(thePointerTime + duration);//设置结束时间
                        instance.setEndMinute(calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE));//计算时分秒
                        instance.setEndDay(calendar.getTime());//
                        instance.setEventId(event.getId());//

                        //查询下有没有重复掉
                        HashMap param = new HashMap();
                        param.put("eventId", event.getId());
                        param.put("begin", thePointerTime);
                        List<Instance> list = instanceService.listByParams(param);
                        if (list == null || list.size() == 0) {
                            instanceService.save(instance);//新增加一条数据
                        }

                    } else {
                        //  continue;//如果轮询到的日期小于当前事件则跳过
                    }
                    //freq dstart freq dend freq until freq count freq until

                    if (freq.equals("WEEKLY")) {//每周
                        thePointerTime += 24 * 7 * 60 * 60 * 1000;
                        //theEndTime+=theBeginTime+duration;

                    } else if (freq.equals("DAILY")) {//每周
                        thePointerTime += 24 * 60 * 60 * 1000;
                        // theEndTime+= theBeginTime+duration;

                    } else if (freq.equals("MONTHLY")) {//每周
                        // thePointerTime += 24 * 60 * 60 * 1000;

                        Calendar tmpCalendar = Calendar.getInstance();
                        tmpCalendar.setTimeInMillis(thePointerTime);
                        tmpCalendar.add(Calendar.MONTH, 1);//切到下个月
                        thePointerTime = tmpCalendar.getTimeInMillis();

                        // theEndTime+= theBeginTime+duration;

                    } else {
                        throw new BizException(30705109, "freq 必须是WEEKLY DAILY");
                    }

                    if (thePointerTime > until)
                        break;

                }
            } else {//只保存一条即可 非重复事件
                Instance instance = new Instance();
                //开始时间
                instance.setBegin(event.getDtstart());
                instance.setEnd(event.getDtend());
                calendar.setTimeInMillis(event.getDtstart());
                instance.setStartMinute(calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE));//开始的分钟
                instance.setStartDay(calendar.getTime());

                //结束时间
                calendar.setTimeInMillis(event.getDtend());
                instance.setEndMinute(calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE));
                instance.setEndDay(calendar.getTime());
                instance.setEventId(event.getId());
                instanceService.save(instance);

            }
        }
    }

    /**
     * 保存日历
     * step1 saveBean
     * step2 saveCalendarEvent
     * step3 judgeAddOrUpdateByEventId
     * add
     * insertEvent createInstance
     * update bean's eventId
     * update
     * deleteInTanceByEventId
     * createInstance
     * update Event
     *
     * @param event
     */
    public void save(Event event) {
        if (event.getId() != null && eventService.selectByPrimaryKey(event.getId()) != null) {
            updateEventAndInstance(event);
        } else {
            addEventAndInstance(event);//添加新的日历
        }
    }

    /**
     * 添加日历事件  如果是重复更新事件请用update 方法
     * 此方法重复添加会添加多条日历事件
     *
     * @param event
     */
    private void addEventAndInstance(Event event) {
        if (StringUtil.isBlank(event.getTitle())) {
            throw new BizException(30801042, "出错 event title   不能为空");
        }
        //判断是否为重复时间
        boolean repeatFlag = false;
        if (StringUtil.isNotBlank(event.getRrule())) {
            repeatFlag = true;
        } else {
            repeatFlag = false;
        }
        if (event.getDtstart() == null || event.getDtstart() == 0
        ) {
            throw new BizException(30801044, "出错 dtstart   不能为空");
        }
        if (repeatFlag) {
//            if (event.getDtend() != null && event.getDtend() > 0) {
//                throw new BizException(30801055, "重复事件 dtend 不能有值");
//            }
            if (StringUtil.isBlank(event.getDuration())) {
                throw new BizException(30801056, "重复事件 duration 不能为空");
            }

        } else {
            if (event.getDtend() == null || event.getDtend() == 0) {
                throw new BizException(30801057, "非重复事件 dtend 不能为空");
            }
            if (event.getDtstart() > event.getDtend()) {
                throw new BizException(30801049, "出错 dtstart 不能晚于 dtend");
            }
        }
        //如果这个event 是没有rrule 的 说明他不用重复 那么 就 直接保存一个instatnce 就可以额了
        //否则还要保存多个instatnce
        eventService.save(event);//保存到数据库
        //清理之前的instance
//        instanceService.deleteByEventId(event.getId());
        addTodayAndNextDayInstanceByEvent(event);


    }


    /**
     * 更新 需要先删除原有的instance
     *
     * @param event
     */
    private void updateEventAndInstance(Event event) {

        //如果这个event 是没有rrule 的 说明他不用重复 那么 就 直接保存一个instatnce 就可以额了
        //否则还要保存多个instatnce
        if (StringUtil.isBlank(event.getTitle())) {
            throw new BizException(30801042, "出错 event title   不能为空");
        }

        //判断是否为重复时间
        boolean repeatFlag = false;
        if (StringUtil.isNotBlank(event.getRrule())) {
            repeatFlag = true;
        } else {
            repeatFlag = false;
        }

        if (event.getDtstart() == null || event.getDtstart() == 0
        ) {
            throw new BizException(30801044, "出错 dtstart   不能为空");
        }

        if (repeatFlag) {
            if (event.getDtend() != null && event.getDtend() > 0) {
                throw new BizException(30801055, "重复事件 dtend 不能有值");
            }
            if (StringUtil.isBlank(event.getDuration())) {
                throw new BizException(30801055, "重复事件 duration 不能为空");
            }

        } else {
            if (event.getDtend() == null || event.getDtend() == 0) {
                throw new BizException(30801055, "非重复事件 dtend 不能为空");
            }
            if (event.getDtstart() > event.getDtend()) {
                throw new BizException(30801049, "出错 dtstart 不能晚于 dtend");
            }
        }
        //校验
        eventService.save(event);//保存到数据库
        HashMap params = new HashMap<>();
        params.put("eventId", event.getId());
        List<Instance> list = instanceService.listByParams(params);
        for (Instance instance : list) {
            instanceService.delete(instance.getId());//删除原有的instance  删除未发生的事件
        }
        this.addTodayAndNextDayInstanceByEvent(event);

    }

    public void addToday(Event event) {
        Calendar today = Calendar.getInstance();
        //如果这个event 是没有rrule 的 说明他不用重复 那么 就 直接保存一个instatnce 就可以额了
        //否则还要保存多个instatnce
        if (StringUtil.isBlank(event.getTitle())) {
            throw new BizException(30801042, "出错 event title   不能为空");
        }

        //判断是否为重复时间
        boolean repeatFlag = false;
        if (StringUtil.isNotBlank(event.getRrule())) {
            repeatFlag = true;
        } else {
            repeatFlag = false;
        }

        if (event.getDtstart() == null || event.getDtstart() == 0
        ) {
            throw new BizException(30801044, "出错 dtstart   不能为空");
        }

        if (repeatFlag) {
            if (event.getDtend() != null && event.getDtend() > 0) {
                throw new BizException(30801055, "重复事件 dtend 不能有值");
            }
            if (StringUtil.isBlank(event.getDuration())) {
                throw new BizException(30801055, "重复事件 duration 不能为空");
            }

        } else {
            if (event.getDtend() == null || event.getDtend() == 0) {
                throw new BizException(30801055, "非重复事件 dtend 不能为空");
            }
            if (event.getDtstart() > event.getDtend()) {
                throw new BizException(30801049, "出错 dtstart 不能晚于 dtend");
            }
        }


        //校验


        eventService.save(event);//保存到数据库

        if (repeatFlag) {
            String durationStr = event.getDuration();
            Long duration = null;
            durationStr = durationStr.substring(2);
            if (durationStr.endsWith("M")) {
                duration = Long.valueOf(durationStr.replace("M", "")) * 60 * 1000;
            } else if (durationStr.endsWith("D")) {

            }
            event.getDtstart();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(event.getDtstart());
            String freq = "";
            String rruleAry[] = event.getRrule().split(";");
            String untilStr = "";
            String counntStr = "";
            for (int i = 0; i < rruleAry.length; i++) {
                String[] keyValueAry = rruleAry[i].split("=");//名值对
                if (keyValueAry[0].equals("FREQ")) {
                    freq = keyValueAry[1];
                } else if (keyValueAry[0].equals("UNTIL")) {
                    untilStr = keyValueAry[1];
                } else if (keyValueAry[0].equals("COUNT")) {
                    counntStr = keyValueAry[1];
                }
            }

            Long until = null;//如果没有until的话 说明是一直持续的
            //如果截止时间不为空的话 循环一直取到截止时间 生成若干个实例
            if (StringUtil.isNotBlank(untilStr)) {
                until = Long.valueOf(untilStr);

                Long theBeginTime = event.getDtstart();//获得事件开始时间
                // Long theEndTime =theBeginTime+duration;// event.getDtend();//获得事件开始时间

                if (theBeginTime > until)
                    logger.info("逻辑出错 时间超出截止时间");

                while (true) {


                    //加入instance实例
                    Instance instance = new Instance();
                    instance.setBegin(theBeginTime);
                    instance.setEnd(theBeginTime + duration);
                    calendar.setTimeInMillis(theBeginTime);
                    instance.setStartMinute(calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE));//开始的分钟

                    instance.setStartDay(calendar.getTime());

                    calendar.setTimeInMillis(theBeginTime + duration);
                    instance.setEndMinute(calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE));
                    instance.setEndDay(calendar.getTime());
                    instance.setEventId(event.getId());
                    instanceService.save(instance);


                    if (freq.equals("WEEKLY")) {//每周
                        theBeginTime += 24 * 7 * 60 * 60 * 1000;
                        //theEndTime+=theBeginTime+duration;

                    } else if (freq.equals("DAILY")) {//每周
                        theBeginTime += 24 * 60 * 60 * 1000;
                        // theEndTime+= theBeginTime+duration;

                    } else {
                        throw new BizException(30705109, "freq 必须是WEEKLY DAILY");
                    }

                    if (theBeginTime > until)
                        break;

                }
            } else {//只保存一条即可


            }


        }
    }


    /**
     * 删除事件
     *
     * @param event
     */
    private void delete(Event event) {
        HashMap params = new HashMap<>();
        params.put("eventId", event.getId());//根据eventId 删除子实例的数据
        List<Instance> list = instanceService.listByParams(params);
        for (Instance instance : list) {
            instanceService.delete(instance.getId());//删除原有的instance
        }
        //this.addTodayAndNextDayInstanceByEvent(event);
        eventService.delete(event.getId());//保存到数据库
    }

    /**
     * 删除事件
     *
     * @param //event
     */
    public void delete(Long eventId) {
        HashMap params = new HashMap<>();
        params.put("eventId", eventId);//根据eventId 删除子实例的数据
        List<Instance> list = instanceService.listByParams(params);
        for (Instance instance : list) {
            instanceService.delete(instance.getId());//删除原有的instance
        }
        //this.addTodayAndNextDayInstanceByEvent(event);
        eventService.delete(eventId);//保存到数据库
    }

}
