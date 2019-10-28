package com.dozenx.web.module.calendar.action;

import com.alibaba.fastjson.JSON;
import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.HttpRequestUtil;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.annotation.RequiresLogin;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.calendar.bean.Activity;
import com.dozenx.web.module.calendar.service.ActivityService;
import com.dozenx.web.util.BeanUtil;
import com.dozenx.web.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dozen.zhang on 2017/3/29.
 */

@Controller
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
        List<HashMap> activities = activityService.getActivities(startDate, endDate,
                userId);

        try {
            String service = ConfigUtil.getConfig("calendar.subscribe.service");
            if (StringUtil.isNotBlank(service)) {
                ActivityService activityService = (ActivityService) BeanUtil.getBean(service);

                List<HashMap> results = activityService.getActivities(startDate, endDate, userId);
                activities.addAll(results);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            String url = ConfigUtil.getConfig("calendar.subscribe.url");
            if(StringUtil.isNotBlank(showType)){
                url+="&showType="+showType;//0 是普通 1是摄像头 2是迟到
            }
            logger.info("calendar.subscribe.url:" + url);
            if (StringUtil.isNotBlank(url)) {
                url = url.replace("${userId}", "" + getUserId(request)).replace("${begin}", DateUtil.toString(new Date(startDate * 60000), "yyyy-MM-dd%20HH:mm:ss")).replace("${end}", DateUtil.toString(new Date(endDate * 60000), "yyyy-MM-dd%20HH:mm:ss"));
                String result = HttpRequestUtil.sendGet(url);
                ResultDTO resultDTO = JsonUtil.toJavaBean(result, ResultDTO.class);
                if (resultDTO.isRight()) {
                    List<HashMap> list = JsonUtil.toList(resultDTO.getData().toString(), HashMap.class);
                    activities.addAll(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        HashMap returnMap = new HashMap();

        List activities = activityService.getActivities(startDate, endDate);
        return this.getResult(activities);
        // returnMap.put("LIST", activities);
        // returnMap.put(SysConfig.AJAX_RESULT, SysConfig.AJAX_SUCC);
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
    @RequestMapping(value = "/saveActivitys.json", method = RequestMethod.POST)
    @RequiresLogin
    public @ResponseBody
    ResultDTO saveActivitys(HttpServletRequest request) {

        String jsonstr = request.getParameter("jsonstr");
        List<HashMap> mapListJson = JSON.parseArray(jsonstr, HashMap.class);

        List<Activity> list = new ArrayList<Activity>();

        for (HashMap object : mapListJson) {
            log.debug("starttime" + object.get("startTime") + "  endtime"
                    + object.get("endTime"));
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

    /**
     * 说明:保存事项
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月28日下午5:12:26
     */
    @RequiresLogin
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


}
