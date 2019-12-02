package com.dozenx.web.module.calendar.action;

import com.alibaba.fastjson.JSON;
import com.dozenx.common.exception.BizException;
import com.dozenx.common.util.*;
import com.dozenx.web.core.annotation.RequiresLogin;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.calendar.bean.Activity;
import com.dozenx.web.module.calendar.calendar.service.CalendarManagerService;
import com.dozenx.web.module.calendar.event.bean.Event;
import com.dozenx.web.module.calendar.event.service.EventService;
import com.dozenx.web.module.calendar.service.ActivityService;
import com.dozenx.web.util.BeanUtil;
import com.dozenx.web.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dozen.zhang on 2017/3/29.
 */

@Controller("ActivityController")
@RequestMapping("/activity")
public class CalendarController extends BaseController {

    private static final Logger log = LoggerFactory
            .getLogger(CalendarController.class);

    @InitBinder
    // 此处的参数也可以是ServletRequestDataBinder类型
    public void initBinder(ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    /*
     * @RequestMapping(value = "/listAjax") public @ResponseBody HashMap
     * listAjax(HttpServletRequest request) throws IOException { HashMap params=
     * MapUtil.request2Map(request); Page page=new Page(request); HashMap
     * returnMap = new HashMap(); ListPage<HashMap> listRowCount =
     * adService.getAdsetPageList(params, page); page = listRowCount.getPage();
     * returnMap.put("total", page.getTotalPage()); returnMap.put("records",
     * page.getTotalCount()); returnMap.put("rows", listRowCount.getList());
     * return returnMap;
     *
     * }
     */
    @Resource(name = "activityService")
    private ActivityService activityService;


    @Resource(name = "eventService")
    private EventService eventService;

     /*   public void setActivityService(ActivityService activityService) {
            this.activityService = activityService;
        }*/

    /**
     * @return
     * @throws Exception
     * @Title: getActivities
     * @Description: 根据起始时间和结束时间获取活动
     * @param:
     * @author 371452875@qq.com
     * @date 2014年4月17日
     */
    @RequestMapping(value = "/getActivities.json")//, method = RequestMethod.POST
    @RequiresLogin
    public @ResponseBody
    ResultDTO getActivities(HttpServletRequest request)
            throws Exception {
        long startDate = Integer.valueOf(request.getParameter("STARTDATE"));
        long endDate = Integer.valueOf(request.getParameter("ENDDATE"));
        String showType = request.getParameter("showType");
        log.debug("startDate:" + 23930040);
        DateUtil.printTimestampby60(23930040);
        log.debug("endDate:" + endDate);
        DateUtil.printTimestampby60(23930940);
        HashMap returnMap = new HashMap();
        //            SysUser user = (SysUser) request.getSession().getAttribute("user");
        //            if (user == null) {
        //                throw new Exception("err.logic.session.notexsist");
        //            }
        //            Long userid = user.getId();

        Long userId = this.getUserId(request);
        List<Activity> activities = activityService.getActivities(startDate, endDate,
                userId);

        try {
            String service = ConfigUtil.getConfig("calendar.subscribe.service");
            if (StringUtil.isNotBlank(service)) {
                ActivityService activityService = (ActivityService) BeanUtil.getBean(service);

                List<Activity> results = activityService.getActivities(startDate, endDate, userId);
                activities.addAll(results);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            String url = ConfigUtil.getConfig("calendar.subscribe.url");
            if (StringUtil.isNotBlank(showType)) {
                url += "&showType=" + showType;//0 是普通 1是摄像头 2是迟到
            }
            logger.info("calendar.subscribe.url:" + url);
            if (StringUtil.isNotBlank(url)) {
                url = url.replace("${userId}", "" + getUserId(request)).replace("${begin}", DateUtil.toString(new Date(startDate * 60000), "yyyy-MM-dd%20HH:mm:ss")).replace("${end}", DateUtil.toString(new Date(endDate * 60000), "yyyy-MM-dd%20HH:mm:ss"));
                String result = HttpRequestUtil.sendGet(url);
                ResultDTO resultDTO = JsonUtil.toJavaBean(result, ResultDTO.class);
                if (resultDTO.isRight()) {
                    List<Activity> list = JsonUtil.toList(resultDTO.getData().toString(), Activity.class);
                    activities.addAll(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Event> events = eventService.listByParams(new HashMap());
        for (Event event : events) {
            Activity activity = event2Activity(event);
//
            activities.add(activity);
        }


        /*    {
                "r": 0,
                    "data": [{
                        "isdel": false,
                        "title": "写学习博客",
                        "id": 2018111245,
                        "startTime": 25700040,
                        "endTime": 25700100,
                        "userId": 207,
                        "type": 0,
                        "privacy": 0
            }, {
                "isdel": false,
                        "title": "复合后退回",
                        "id": 2018111344,
                        "startTime": 25701450,
                        "endTime": 25701510,
                        "userId": 207,
                        "type": 0,
                        "privacy": 0
            }, {
                "isdel": false,
                        "title": "制证报表和错误报表分开",
                        "id": 20181112102,
                        "startTime": 25700250,
                        "endTime": 25700310,
                        "userId": 207,
                        "type": 0,
                        "privacy": 0
            }],
                "right": true
            }*/

        return this.getResult(activities);
        // returnMap.put("LIST", activities);
        // returnMap.put(SysConfig.AJAX_RESULT, SysConfig.AJAX_SUCC);
        // return returnMap;
    }

    /**
     * @return
     * @throws Exception
     * @Title: getActivities
     * @Description: 根据起始时间和结束时间获取活动
     * @param:
     * @author 371452875@qq.com
     * @date 2014年4月17日
     */
    @RequestMapping(value = "/getAllActivities.json", method = RequestMethod.POST)
    public @ResponseBody
    ResultDTO getAllActivities(HttpServletRequest request)
            throws Exception {
        long startDate = Integer.valueOf(request.getParameter("STARTDATE"));
        long endDate = Integer.valueOf(request.getParameter("ENDDATE"));

        log.debug("startDate:" + startDate);
        DateUtil.printTimestampby60(startDate);
        log.debug("endDate:" + endDate);
        DateUtil.printTimestampby60(endDate);
//        HashMap returnMap = new HashMap();

        List<Activity> activities = activityService.getActivities(startDate, endDate);

        List<Event> events = eventService.listByParams(new HashMap());
        for (Event event : events) {
            Activity activity = new Activity();
//            activity.setCount(event.get);
            String rule = event.getRrule();
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
            activity.setFreq(freq);
            if (StringUtil.isNotBlank(counntStr))
                activity.setCount(Integer.valueOf(counntStr));
            if (StringUtil.isNotBlank(untilStr))
                activity.setUntil(Long.valueOf(untilStr));
            activity.setId(event.getId());
            activities.add(activity);
        }

        return this.getResult(activities);
        // returnMap.put("LIST", activities);
        // returnMap.put(SysConfig.AJAX_RESULT, SysConfig.AJAX_SUCC);
        // return returnMap;
    }

    @Autowired
    CalendarManagerService calendarManagerService;

    /**
     * 说明:保存事项 保存多个事件 由前端 同步保存 loopCheckAndSave
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月28日下午5:12:26
     */
    @RequestMapping(value = "/saveActivitys.json", method = RequestMethod.POST)
    @RequiresLogin
    public @ResponseBody
    ResultDTO saveActivitys(HttpServletRequest request) {

        String jsonstr = request.getParameter("jsonstr");
        List<HashMap> mapListJson = JSON.parseArray(jsonstr, HashMap.class);

        List<Activity> list = new ArrayList<Activity>();


        for (HashMap object : mapListJson) {    //遍历多个
            log.debug("starttime" + object.get("startTime") + "  endtime"
                    + object.get("endTime"));

            String actType = MapUtils.getString(object, "actType");
            String location = MapUtils.getString(object, "location");

            int startTime = (int) object.get("startTime");
            int endTime = (int) object.get("endTime");
            String title = (String) object.get("title");

            Object value = object.get("id");
            Long id = 0l;
            if (value instanceof Integer) {
                id = Long.valueOf((int) object.get("id"));
            } else if (value instanceof Long) {
                id = (Long) object.get("id");
            } else if (value instanceof String) {
                id = Long.valueOf((String) object.get("id"));
            }


            String freq = MapUtils.getString(object, "freq");
            if (StringUtil.isNotBlank(freq)) {
                Event event = map2Activity(object);

                calendarManagerService.save(event);
                //这个event 会有很多的instance
                //这个instance 要马上产生 如果是无限的 那么就怎么办呢
//                bean.setEventId(event.getId());
            } else {

                Activity activity = new Activity();
                if (object.get("isDel") != null) {
                    activity.setIsdel((Boolean) object.get("isDel"));
                } else {
                    activity.setIsdel(false);
                }

                if (object.get("type") != null) {
                    activity.setType(Byte.valueOf(object.get("type") + ""));
                }
                activity.setStartTime(startTime);
                activity.setEndTime(endTime);
                activity.setTitle(title);
                activity.setId(id);
                activity.setUserId(this.getUserId(request));
                list.add(activity);
            }
        }
        if (list.size() > 0)
            activityService.saveActivitys(list);
        /*
         * if(StringUtil.isEmpty(request.getParameter("ID"))){
         * activity.setActivityId(UUID.randomUUID().toString());
         * activityService.saveActivity(activity); }else{
         * activity.setActivityId(request.getParameter("ID"));
         * activityService.updateActivity(activity); }
         */
        return this.getResult();
        // HashMap returnMap =new HashMap();
        // returnMap.put("RESULT", true);
        // return returnMap;
    }

    public Activity event2Activity(Event event){
        Activity activity = new Activity();
//            activity.setCount(event.get);
        String rule = event.getRrule();
        String freq = "";
        String rruleAry[] = event.getRrule().split(";");
        String untilStr = "";
        String counntStr = "";
        String intervalStr = "";
        for (int i = 0; i < rruleAry.length; i++) {
            String[] keyValueAry = rruleAry[i].split("=");//名值对
            if (keyValueAry[0].equals("FREQ")) {
                freq = keyValueAry[1];
            } else if (keyValueAry[0].equals("UNTIL")) {
                untilStr = keyValueAry[1];
            } else if (keyValueAry[0].equals("COUNT")) {
                counntStr = keyValueAry[1];
            } else if (keyValueAry[0].equals("INTERVAL")) {
                intervalStr = keyValueAry[1];
            }
        }
        activity.setFreq(freq);

        activity.setTitle(event.getTitle());
        activity.setStartTime((int)(event.getDtstart()/1000/60));
        activity.setEndTime((int)(event.getDtend()/1000/60));
        if (StringUtil.isNotBlank(counntStr))
            activity.setCount(Integer.valueOf(counntStr));
        if (StringUtil.isNotBlank(untilStr))
            activity.setUntil(Long.valueOf(untilStr));
        if (StringUtil.isNotBlank(intervalStr))
        activity.setInterval(Integer.valueOf(intervalStr));
        if(event.getUntil()!=null){
            activity.setUntil(event.getUntil());
        }

        activity.setId(event.getId());
        return  activity;
    }

    public Event map2Activity(Map<String, Object> map){

        String actType = MapUtils.getString(map, "actType");        //类型


        int startTime = (int) map.get("startTime"); //开始时间 分钟单位
        int endTime = (int) map.get("endTime"); //结束时间


        Object value = map.get("id");   //id
        Long id = 0l;
        if (value instanceof Integer) {
            id = Long.valueOf((int) map.get("id"));
        } else if (value instanceof Long) {
            id = (Long) map.get("id");
        } else if (value instanceof String) {
            id = Long.valueOf((String) map.get("id"));
        }


        String freq = MapUtils.getString(map, "freq");  //频率
        if (StringUtil.isNotBlank(freq)) {
            Event event = new Event();

            event.setId(id);


            String count = MapUtils.getString(map, "count");        //个数

            String byday = MapUtils.getString(map, "byday");        //星期几

            String location = MapUtils.getString(map, "location");      //位置

            event.setDescription(MapUtils.getString(map, "description")); //说明

            String title = (String) map.get("title");   //标题
            event.setTitle(title);

            String until = MapUtils.getString(map, "until");
            if (StringUtil.isNotBlank(until)) {
                event.setUntil(Long.valueOf(until));
            }


            event.setEventLocation(location);
            event.setDescription(MapUtils.getString(map, "description"));
            event.setDtend(endTime * 60l * 1000);
            //
            //   Date date = bean.getDstart();
//                Calendar calendar = Calendar.getInstance();//开始时间默认是当前事件
//                // calendar.setTime(date);
//                calendar.set(Calendar.HOUR_OF_DAY, bean.getHour());
//                calendar.set(Calendar.MINUTE, bean.getMinute());
//                calendar.set(Calendar.SECOND,0);
            event.setDtstart(startTime * 60l * 1000);
            // 反馈信息收集表、制证情况统计表、报错类型统计表
            event.setDuration(event.getDtend() - event.getDtstart() + "");

            //或者不保存event
//                if (StringUtil.isNotBlank(bean.getFreq())) {

            String rrule = "FREQ=" + freq.toUpperCase() + ";";
            if (StringUtil.isNotBlank(byday)) {
                rrule += "byday=" + byday+ ";";
                if(!freq.toLowerCase().equals("weekly")){
                    throw new BizException("30305503","星期几只能在周期是星期的时候设置");
                }
            }


            if (StringUtil.isNotBlank(until)) {
                rrule += "until=" + until+ ";";
            }

            String interval = MapUtils.getString(map, "interval");      //频率间隔
            if (StringUtil.isNotBlank(interval)) {
                rrule += "interval=" + interval+ ";";
            }

//                    if (bean.getDend() != null) {
//                        calendar.setTime(bean.getDend());
//                        calendar.set(Calendar.HOUR_OF_DAY, bean.getHour());
//                        calendar.set(Calendar.MINUTE, bean.getMinute());
//                        Long until = calendar.getTimeInMillis();
//                        rrule += "UNTIL=" + until;
//                    }
            event.setRrule(rrule.toUpperCase());

            //String newUrl = URLUtil.connact(ConfigUtil.getConfig("serverUrl"), "coepScan/manualScan?params={'id':" + bean.getId() + "}");

            //触发的时候要把该单位的报表发送到该单位底下的所有联系人的邮箱当中
            //event.setDuration("PT1M");
            return event;
        }
        return  null;
    }
    /**
     * 说明:保存事项
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月28日下午5:12:26
     */
    @RequiresLogin
    @Deprecated
    @RequestMapping(value = "/saveActivity.json", method = RequestMethod.POST)
    public @ResponseBody
    ResultDTO saveActivity(HttpServletRequest request) {
        HashMap returnMap = new HashMap();

        int startTime = Integer.valueOf(request.getParameter("startTime"));
        int endTime = Integer.valueOf(request.getParameter("endTime"));
        Activity activity = new Activity();

        activity.setStartTime(startTime);
        activity.setEndTime(endTime);
        activity.setTitle(request.getParameter("TITLE"));
        activity.setId(Long.valueOf(request.getParameter("ID")));
        activityService.saveActivity(activity);
        /*
         * if(StringUtil.isEmpty(request.getParameter("ID"))){
         * activity.setActivityId(UUID.randomUUID().toString());
         * activityService.saveActivity(activity); }else{
         * activity.setActivityId(request.getParameter("ID"));
         * activityService.updateActivity(activity); }
         */
        return getResult();
        // returnMap.put("RESULT", true);
        // return returnMap;
    }


    /**
     * 说明:保存事项
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月28日下午5:12:26
     */
    @RequestMapping(value = "/exportActivity.json", method = RequestMethod.POST)
    public @ResponseBody
    ResultDTO exportActivity(HttpServletRequest request) {
        HashMap returnMap = new HashMap();

        int startTime = Integer.valueOf(request.getParameter("startTime"));
        int endTime = Integer.valueOf(request.getParameter("endTime"));
        Activity activity = new Activity();

        activity.setStartTime(startTime);
        activity.setEndTime(endTime);
        activity.setTitle(request.getParameter("title"));
        activity.setId(Long.valueOf(request.getParameter("id")));
        activityService.saveActivity(activity);
        /*
         * if(StringUtil.isEmpty(request.getParameter("ID"))){
         * activity.setActivityId(UUID.randomUUID().toString());
         * activityService.saveActivity(activity); }else{
         * activity.setActivityId(request.getParameter("ID"));
         * activityService.updateActivity(activity); }
         */
        return getResult();
        // returnMap.put("RESULT", true);
        // return returnMap;
    }
//    @PostConstruct
//    @Scheduled(cron="0 0 0 * * *")
//    public void holiday(){
//        //尽量将全年的节假日安排放到一张holiday的表中
//        //如果实在查询不到的话 就每天早上调用接口 但是这个接口会有问题,到时候就麻烦了
//
//
//        String holidayUrl = ConfigUtil.getConfig("holiday.url");
//       String today =  DateUtil.getTodayDate();
//        holidayUrl = holidayUrl.replace("{date}",today);
//        String result = HttpRequestUtil.sendGet(holidayUrl);
//
//        Map resultMap = JsonUtil.toMap(result);
//        if(MapUtils.getString(resultMap,"code").equals("0")){
//
//            Object holiday = resultMap.get("holiday");
//            if(holiday!=null){
//                CalendarState . TodayIsWorkDay =false;
//            }
//        }
//    }



    public static void main(String args[]){

        Long value =26224470l*60*1000*1000;

        System.out.println(value);
    }
}
