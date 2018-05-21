package com.dozenx.web.module.calendar.action;

import com.alibaba.fastjson.JSON;
import com.dozenx.util.DateUtil;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.calendar.bean.Activity;
import com.dozenx.web.module.calendar.service.ActivityService;
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
        @Resource(name="activityService")
        private ActivityService activityService;

     /*   public void setActivityService(ActivityService activityService) {
            this.activityService = activityService;
        }*/

        /**
         * @Title: getActivities
         * @Description: 根据起始时间和结束时间获取活动
         * @param:
         * @return HashMap 返回类型
         * @author 371452875@qq.com
         * @return
         * @throws Exception
         * @date 2014年4月17日
         */
        @RequestMapping(value = "/getActivities.json", method = RequestMethod.POST)
        public @ResponseBody
        ResultDTO getActivities(HttpServletRequest request)
                throws Exception {
            long startDate = Integer.valueOf(request.getParameter("STARTDATE"));
            long endDate = Integer.valueOf(request.getParameter("ENDDATE"));

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
            List activities = activityService.getActivities(startDate, endDate,
                    userId);
            return this.getResult(activities);
            // returnMap.put("LIST", activities);
            // returnMap.put(SysConfig.AJAX_RESULT, SysConfig.AJAX_SUCC);
            // return returnMap;
        }


    /**
     * @Title: getActivities
     * @Description: 根据起始时间和结束时间获取活动
     * @param:
     * @return HashMap 返回类型
     * @author 371452875@qq.com
     * @return
     * @throws Exception
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

        List activities = activityService.getActivities(startDate, endDate );
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
        public @ResponseBody ResultDTO saveActivitys(HttpServletRequest request) {

            String jsonstr = request.getParameter("jsonstr");
            List<HashMap> mapListJson=JSON.parseArray(jsonstr,HashMap.class);

            List<Activity> list = new ArrayList<Activity>();

            for (HashMap object : mapListJson) {
                log.debug("starttime" + object.get("STARTTIME") + "  endtime"
                        + object.get("ENDTIME"));
                int startTime = (int) object.get("STARTTIME");
                int endTime = (int) object.get("ENDTIME");
                String title = (String) object.get("TITLE");

                Object value = object.get("ID");
                Long id=0l;
                if(value instanceof Integer){
                    id =Long.valueOf((int) object.get("ID") );
                }else if(value instanceof String){
                    id =Long.valueOf((String)object.get("ID")) ;
                }
                Activity activity = new Activity();
                if (object.get("ISDEL")!=null) {
                    activity.setIsdel((Boolean)object.get("ISDEL"));
                } else {
                    activity.setIsdel(false);
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
        @RequestMapping(value = "/saveActivity.json", method = RequestMethod.POST)
        public @ResponseBody ResultDTO saveActivity(HttpServletRequest request) {
            HashMap returnMap = new HashMap();

            int startTime = Integer.valueOf(request.getParameter("STARTTIME"));
            int endTime = Integer.valueOf(request.getParameter("ENDTIME"));
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
    public @ResponseBody ResultDTO exportActivity(HttpServletRequest request) {
        HashMap returnMap = new HashMap();

        int startTime = Integer.valueOf(request.getParameter("STARTTIME"));
        int endTime = Integer.valueOf(request.getParameter("ENDTIME"));
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


}
