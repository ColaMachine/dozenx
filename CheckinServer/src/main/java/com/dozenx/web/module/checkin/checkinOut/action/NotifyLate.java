package com.dozenx.web.module.checkin.checkinOut.action;

import com.dozenx.util.DateUtil;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.module.checkin.checkinLate.bean.CheckinLate;
import com.dozenx.web.module.checkin.checkinLate.service.CheckinLateService;
import com.dozenx.web.module.checkin.checkinOut.service.CheckinOutService;
import com.dozenx.web.module.checkin.faceInfo.service.VirtualWeixinService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 12:14 2018/11/23
 * @Modified By:
 */
public class NotifyLate implements Job {
    private static final Logger logger = LoggerFactory.getLogger(NotifyLate.class);
    @Autowired
    private CheckinOutService checkinOutService;
    @Autowired
    private CheckinLateService checkinLateService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String date = DateUtil.toDateStr(calendar.getTime(), DateUtil.YYYY_MM_DD);
        String begin = jobExecutionContext.getJobDetail().getJobDataMap().getString("begin");
        String end = jobExecutionContext.getJobDetail().getJobDataMap().getString("end");
        this.process(date, begin, end, "");
    }


    public void process(String date, String begin, String end, String now) {
        logger.info(date + " " + begin + " " + end);


        //查询整个事件段的考勤数据
        // String dateStr = getTodayDate();
        // String dateStr = "2018-10-17";
        //纯粹的考勤记录检查迟到 如果要加上摄像头 考勤签到的话 需要加入 摄像头考勤数据监测
        List<SysUser> lateUser = checkinOutService.listUsersNotCheckIn2(date + " " + begin + ":00", date + " " + begin + ":00");

        //给每个人发通知
        for (SysUser sysUser : lateUser) {
            //插入一条迟到数据库记录

            //去查 摄像头签到 这个人是否有记录
            try {

                CheckinLate checkinLate = new CheckinLate();
                checkinLate.setKqUserId(sysUser.getOutId());//考勤机用户id
                checkinLate.setCheckType(1);//1暂时无用
                checkinLate.setUserName(sysUser.getUsername());
                checkinLate.setUserId(sysUser.getId());//系统用户id
                checkinLate.setCheckTime(new Timestamp(DateUtil.parseToDate(date + " " + begin + ":00", DateUtil.YYYY_MM_DD_HH_MM_SS).getTime()));
                checkinLateService.save(checkinLate);//保存迟到记录

                //发送迟到通知
                //    CheckinMessageService.sendMsg();
                VirtualWeixinService.sendMsg(sysUser.getUsername(), date + " " + begin + ":00" + "未打卡");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //

        }

        //EmailUtil.send("371452875@qq.com", sb.toString());

        //readFileACCESS(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));
        //getUserMap(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));
    }
}
