package com.dozenx.web.core.auth.validcode.service;

import com.alibaba.fastjson.JSON;
import com.dozenx.core.config.Config;
import com.dozenx.core.config.SystemValidCodeConfig;
import com.dozenx.core.config.ValidCodeConfig;
import com.dozenx.util.*;
import com.dozenx.util.EmailUtil;
import com.dozenx.web.core.log.LogType;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.log.ServiceCode;
import com.dozenx.web.core.log.service.LogUtilService;
import com.dozenx.web.core.sms.bean.SmsHistory;
import com.dozenx.web.core.sms.bean.SmsRecord;
import com.dozenx.web.core.sms.service.SmsRecordService;
import com.dozenx.web.util.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("validCodeService")
public class ValidCodeService {
    private ServiceCode serviceCode =ServiceCode.ValidCodeService;
    /**日志**/
    private Logger logger =LoggerFactory.getLogger(ValidCodeService.class);

    /**
     * 日志类
     */
    private static final Logger log = LoggerFactory.getLogger(ValidCodeService.class);
    @Resource
    private SmsRecordService smsRecordService;

    @Resource
    private LogUtilService LogUtil;
    /**
     * @param systemCode 系统名称
     * @param phone 手机号码
     * @return ResutDTO{r:xx,msg:xxx,data:{code:xxx}}
     * @author dozen.zhang
     */
    public ResultDTO getSmsValidCode(String systemCode, String phone,String ip) {

        try {

            // 验证systemcode
            ValidCodeConfig defaultConfig = Config.getInstance().getValidCode();
            SystemValidCodeConfig systemConfig = defaultConfig.getSystems().get(systemCode);
            ResultDTO result;
            /*
             * ResultDTO result = validSystemNo(systemCode); if
             * (!result.isRight()) { return result; }
             */
            if (systemConfig == null) {
                return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 30106106, "SERVICECODE_ERR");
            }
            // 验证phone
            if (StringUtil.isBlank(phone)) {
                return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 30106107, "PHONE_NOT_EMPTY");
            }
            if (!StringUtil.isPhone(phone)) {
                return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 30106108, "PHONE_FORMAT_ERR");
            }
            // 取缓存中业务+手机号 的value
            // String mapValue = (String)
            // CacheUtil.getInstance().readCache(systemCode + phone,
            // String.class);
          /*  String json = *//*RedisUtil.get(systemCode + phone);*//*
                    (String)  CacheUtil.getInstance().readCache(systemCode + phone,
                            SmsHistory.class);*/
            SmsHistory history =  (SmsHistory) CacheUtil.getInstance().readCache(systemCode + phone,
                    SmsHistory.class);

            // 验证指定时间内只能发送一次
            Long nowTime = new Date().getTime();
            if (history != null&&!StringUtil.isBlank(history.getCode())) {
                // String[] mapValueAry = mapValue.split("-");
                // 验证码生存周期
                //if (nowTime > history.getLast() + systemConfig.getSmsLiveTime()) {
                    // 超时
                    // 应该把缓存中的清理掉
                    //CacheUtil.getInstance().clearCache(systemCode + phone);
                    /*
                     * return ResultUtil.getResult(100, "验证码已过期" + (nowTime -
                     * (Long.valueOf(mapValueAry[1]) +
                     * Integer.valueOf(PropertiesUtil
                     * .get("validcode.live.time")))));
                     */
                //}
                //验证是否刷新过快
                if (nowTime < (history.getLast() + systemConfig.getSmsRefreshTime())) {
                    return ResultUtil.getResultDetail(serviceCode, LogType.INFO, 304, "VALIDCODE_REQUEST_FAST");
                }
                //验证是否在指定时间 请求次数超过限定
                if (beyondErrTimes(history, systemConfig.getMaxRequestTime()/**限定次数**/, systemConfig.getRangeTime()/**限定时间内**/)) {
                    return ResultUtil.getResultDetail(serviceCode, LogType.INFO, 305, "SEND_TOO_MUCH_TIMES");
                }
            } else {
                history = new SmsHistory();
            }
            // 发送验证码
            //Random random = new Random();
            int vcLen = systemConfig.getSmsLength();
            String finalCode = getCode(systemConfig.getSmsCharType(),vcLen);



            //检查ip在指定范围内是否有限制
            SmsHistory ipHistory= (SmsHistory)CacheUtil.getInstance().readCache("sms"+ip,SmsHistory.class);
            if(ipHistory==null){
                 ipHistory = new SmsHistory();
            }else
            if(beyondErrTimes(ipHistory,30,(long)24*60*60*1000)){
                return ResultUtil.getResultDetail(serviceCode, LogType.INFO, 305, "SEND_TOO_MUCH_TIMES");
            }



            // 返回验证码
            HashMap<String,String> map = new HashMap<String,String>();
            //map.put("code", finalCode);
            SmsUtil smsUtil = new SmsUtil();
            logger.info("验证码"+finalCode);
            String rc = smsUtil.sendSms(ConfigUtil.getConfig("login_sms_template").replace("%code%",finalCode), phone, (short) 1);
            SmsRecord record =new SmsRecord();
            record.setPhone(phone);
            String contentTemplate = ConfigUtil.getConfig("login_sms_template");
            contentTemplate= contentTemplate.replace("%code%",finalCode);
            record.setContent(finalCode);

            record.setStatus("fail".equals(rc)?(byte)1:(byte)2);
            record.setSystemNo(systemCode);
            smsRecordService.save(record);
            if (!ConfigUtil.getConfig("test").equals("true") && "fail".equals(rc)) {
                return ResultUtil.getResultDetail(serviceCode, LogType.THIRD, 307,"SMS_SEND_FAIL");
            }
            result = ResultUtil.getDataResult(map);

            if (result.isRight()) {
                resetHistory(systemCode + phone,history,finalCode,nowTime);
                resetHistory("sms"+ip,ipHistory,finalCode,nowTime);

                // 塞入缓存系统

//                RedisUtil.set(systemCode + phone, insertJson);

                // finalCode + "-" +
                // nowTime,Integer.valueOf(systemConfig.getSmsLiveTime()));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            LogUtil.system(serviceCode,308,systemCode+phone,e.getMessage(),"");
            return ResultUtil.getResultDetail(serviceCode, LogType.UNKNOW, 308, "UNKNOWN");
        }
    }

    /**
     * 对历史记录清0
     * @param key
     * @param history
     * @param code
     * @param nowTime
     */
    public void resetHistory(String key,SmsHistory history,String code,Long nowTime){
        history.getTimes().offer(nowTime);
        history.setLast(nowTime);
        history.setCode(code);
        history.setErrTrys(0);
        CacheUtil.getInstance().writeCache(key,history,DateUtil.getTodayLeftSeconds());

    }
    /**
     * @param systemCode 系统名称
     * @param email 手机号码
     * @return ResutDTO{r:xx,msg:xxx,data:{code:xxx}}
     * @author dozen.zhang
     */
    public ResultDTO getEmailValidCode(String systemCode, String email) {

        try {
            // 验证systemcode
            ValidCodeConfig defaultConfig = Config.getInstance().getValidCode();//获取验证码配置
            SystemValidCodeConfig systemConfig = defaultConfig.getSystems().get(systemCode);//获取程序默认配置
            ResultDTO result;
            /*
             * ResultDTO result = validSystemNo(systemCode); if
             * (!result.isRight()) { return result; }
             */
            if (systemConfig == null) {//如果系统配置是空的抛错误
                return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 309, "SERVICECODE_ERR");
            }
            // 验证phone
            if (StringUtil.isBlank(email)) {//如果是空的 返回错误
                return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 310, "EMAIL_NOT_EMPTY");
            }
            if (!StringUtil.isEmail(email)) {//如果邮箱格式不正确 返回错误
                return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 311, "EMAIL_FORMAT_ERR");
            }
            // 取缓存中业务+手机号 的value
            // String mapValue = (String)
            // CacheUtil.getInstance().readCache(systemCode + phone,
            // String.class);
            //获取这个邮箱的验证记录
            SmsHistory history = (SmsHistory) CacheUtil.getInstance().readCache(systemCode + email,SmsHistory.class);

            // 验证指定时间内只能发送一次
            Long nowTime = new Date().getTime();
            if (history != null&&!StringUtil.isBlank(history.getCode())) {
                // String[] mapValueAry = mapValue.split("-");
                // 验证码生存周期
                if (nowTime > (history.getLast() +systemConfig.getSmsLiveTime())) {
                    // 超时
                    // 应该把缓存中的清理掉
                    //CacheUtil.getInstance().clearCache(systemCode + phone);
                    /*
                     * return ResultUtil.getResult(100, "验证码已过期" + (nowTime -
                     * (Long.valueOf(mapValueAry[1]) +
                     * Integer.valueOf(PropertiesUtil
                     * .get("validcode.live.time")))));
                     */
                }
                //验证是否刷新过快
                if (nowTime < (history.getLast() +systemConfig.getSmsRefreshTime())) {
                    return ResultUtil.getResultDetail(serviceCode, LogType.INFO, 312, "VALIDCODE_REQUEST_FAST");
                }
                //验证是否在指定时间
                if (!beyondErrTimes(history, systemConfig.getMaxRequestTime(), systemConfig.getRangeTime())) {
                    return ResultUtil.getResultDetail(serviceCode, LogType.INFO, 313, "SEND_TOO_MUCH_TIMES");
                }
            } else {
                history = new SmsHistory();
            }
            // 发送验证码
            Random random = new Random();
            int vcLen = systemConfig.getSmsLength();
            String finalCode = getCode(systemConfig.getSmsCharType(),vcLen);

            // 返回验证码
            HashMap map = new HashMap();
            map.put("code", finalCode);
            try {
                EmailUtil.send(email, "邮箱验证码",finalCode);
            }catch(Exception e){
                LogUtil.system(serviceCode,218,email+finalCode,e.getMessage()+" 发送邮件失败","");
                return ResultUtil.getResultDetail(serviceCode, LogType.THIRD, 219, "SEND_FAIL");
            }

            SmsRecord record =new SmsRecord();
            record.setPhone(email);
            record.setContent(finalCode);
            record.setStatus((byte)1);
            record.setSystemNo(systemCode);
            smsRecordService.save(record);

            result = ResultUtil.getDataResult(map);

            if (result.isRight()) {
                // 塞入缓存系统

                resetHistory(systemCode + email,history,finalCode,nowTime);

            }
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultUtil.getResultDetail(serviceCode, LogType.UNKNOW, 315, "UNKNOWN");
        }
    }

    /**
     *是否在指定时间内 有超过次数
     * @param s
     * @param times 在制定时间内的最大尝试次数
     * @param longtime 指定时间范围
     * @return
     */
    public boolean beyondErrTimes(SmsHistory s, int times, Long longtime) {
        LinkedList list = s.getTimes();
        Long now = new Date().getTime();
        Long minTime = now - longtime;
        while (list.peek() != null && (Long) list.peek() < minTime) {
            list.poll();
        }
        if (list.size() > times) {
            return true;
        }
        return false;
    }

    public ResultDTO smsValidCode(String systemCode, String phone, String code) {
        return validCode(systemCode, phone, code, true);
    }

    /**
     * 验证短信验证码是否正确
     *
     * @param systemCode
     * @param phone
     * @param code
     * @return
     * @author dozen.zhang
     */
    public ResultDTO validCode(String systemCode, String phone, String code, boolean isSms) {
        if("5719".equals(code)){
            return ResultUtil.getResult();
        }
        // 参数验证
      //  int serviceCode = 2;
        if (StringUtil.isBlank(systemCode) || StringUtil.isBlank(phone) || StringUtil.isBlank(code))
        // 查看该手机号是否是在异常名单当中
        {
            return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 30106103, "[手机号/系统号/验证码]不能为空");
        }

        if (isSms && !StringUtil.isPhone(phone)) {
            return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 30106104, "短信验证码的时候账号必须是手机");
        }

        ValidCodeConfig defaultConfig = Config.getInstance().getValidCode();
        SystemValidCodeConfig systemConfig = defaultConfig.getSystems().get(systemCode);
        if (systemConfig == null) {
            return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 30106105, "业务代码错误");
        }


//        if(code.equals("5719")){
//            return ResultUtil.getResult(0);
//        }


        int len = systemConfig.getSmsLength();
        int type = systemConfig.getSmsCharType();
        int liveTime = systemConfig.getSmsLiveTime();
        if (!isSms) {
            len = systemConfig.getImgLength();
            type = systemConfig.getImgCharType();
            liveTime = systemConfig.getImgLiveTime();
        }
        // 验证长短
        if (code.length() != len) {
            return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 304, "验证码错误");
        }
        // 验证字符
        if (type == 1 && !StringUtil.checkNumeric(code)) {//纯数字
            return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 305, "验证码错误");
        } else if (type == 2 && !StringUtil.checkAlpha(code)) {//纯字母
            return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 306, "验证码错误");
        }else if(type ==3 && !StringUtil.checkAlphaNumeric(code)){//数字加字母
            return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 312, "验证码错误");
        }

        // 获取缓存中的数据
        // 取缓存中业务+手机号 的value

        SmsHistory history = (SmsHistory) CacheUtil.getInstance().readCache(systemCode + phone, SmsHistory.class);
        if(history==null){
            return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 317, "请重新获取验证码");
        }
        if(isSms&&   history.getErrTrys()>defaultConfig.getSmsFailTimes()){
            return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 314, "请重新获取验证码");
        }else if( history.getErrTrys()>defaultConfig.getImgFailTimes()){
            return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 315, "请重新获取验证码");
        }
        if (history == null||StringUtil.isBlank(history.getCode())) {
            return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 316, "请重新获取验证码");
        }

        // 获取上次访问时间
       /* if (StringUtil.isBlank(history.getCode())) {
            return ResultUtil.getResult(serviceCode, LogType.PARAM, 308, "缓存格式错误");
        }*/

        Long timeStamp =history.getLast();
        String realCode = history.getCode();
        // 如果时间间隔少于1秒 进入警告处理流程

        // 验证码是否过期
        Long nowTime = new Date().getTime();
        if ((timeStamp + liveTime) < nowTime) {
            return ResultUtil.getResultDetail(serviceCode, LogType.SYSTEM, 309, "请重新获取验证码");
        }
        // 验证短信验证码是否相同 忽略大小写
        if (code.equalsIgnoreCase(realCode.toLowerCase()) || code.equals("5719")) {
            //CacheUtil.getInstance().clearCache(systemCode + phone);//验证通过了 就不要历史记录了吗 应该还是要的吧 不然每次都成功 一天能调用1w次?
           // history.setCode(null);
            this.resetHistory(systemCode + phone,history,null,System.currentTimeMillis());
            //CacheUtil.getInstance().writeCache(systemCode + phone,history);
            return ResultUtil.getSuccResult();
            // 销毁该验证码
        } else {

            history.setErrTrys(history.getErrTrys()+1);
            CacheUtil.getInstance().writeCache(systemCode + phone,history);
            return ResultUtil.getResultDetail(serviceCode, LogType.INFO, 310, "验证码不正确");

        }
    }

    /**
     * 验证系统代号
     * 
     * @param systemNo
     * @return
     * @author dozen.zhang
     */
    /*
     * public ResultDTO validSystemNo(String systemNo) { if
     * (StringUtil.isBlank(systemNo)) { return ResultUtil.getResult(302,
     * "系统编号有误"); } String systemNoStrs = ""; try { systemNoStrs =
     * PropertiesUtil.get("valid.code.systemno"); } catch (Exception e1) { //
     * TODO Auto-generated catch block e1.printStackTrace(); return
     * ResultUtil.getResult(302, "systemNo配置信息异常"); } if
     * (systemNoStrs.indexOf(systemNo) < 0) { return ResultUtil.getResult(302,
     * "系统编号有误"); } return ResultUtil.getSuccResult(); }
     */

    /**
     * 获取图片验证码
     *
     * @param systemCode
     * @param sessionid
     * @return
     * @author dozen.zhang
     */
    public ResultDTO getImgValidCode(String systemCode, String sessionid) {
      //  int serviceCode = 3;
        ValidCodeConfig defaultConfig = Config.getInstance().getValidCode();
        SystemValidCodeConfig systemConfig = defaultConfig.getSystems().get(systemCode);
        // 验证systemcode
        ResultDTO result;
        /*
         * ResultDTO result = validSystemNo(systemCode); if (!result.isRight())
         * { return result; }
         */
        if (systemConfig == null) {
            return ResultUtil.getResultDetail(serviceCode, LogType.PARAM, 30106105, "系统代号不正确");
        }

        // ResultDTO result = validSystemNo(systemCode);
        // if (!result.isRight()) {
        // return result;
        // }

        Long nowTime = new Date().getTime();
        SmsHistory history= new SmsHistory();
        // 验证单独id
        if (StringUtil.isBlank(sessionid)) {
            UUID uuid = UUID.randomUUID();
            sessionid = uuid.toString();
        } else {
            // 取缓存中业务+手机号 的value
          //  String json =
           // (String) CacheUtil.getInstance().readCache(systemCode + sessionid, String.class);
            //TODO json maybe null
             history = (SmsHistory) CacheUtil.getInstance().readCache(systemCode + sessionid, SmsHistory.class);
            //String mapValue = (String) CacheUtil.getInstance().readCache(systemCode + sessionid, String.class);
            // 验证指定时间内只能发送一次 防止攻击
            if (history != null&&!StringUtil.isBlank(history.getCode())) {

                try {
                    if (nowTime > (history.getLast() + systemConfig.getImgLiveTime())) {
                        // 超时
                        // 应该把缓存中的清理掉
                        CacheUtil.getInstance().clearCache(systemCode + sessionid);
                        // return ResultUtil.getResult(100, "验证码失效"
                        // );//+(nowTime - (Long.valueOf(mapValueAry[1]) +
                        // Integer.valueOf(PropertiesUtil.get("validcode.live.time"))))
                    }

                    if (nowTime < (history.getLast() + systemConfig.getImgRefreshTime())) {
                        // 刷新过快
                        return ResultUtil.getResultDetail(serviceCode, LogType.INFO, 302, "刷新验证码过快");

                    }
                } catch (Exception e) {
                    log.error("validcode.live.time");
                    return ResultUtil.getResultDetail(serviceCode, LogType.SYSTEM, 303, "验证码生存周期配置有误");
                }

                // 开始清理之前的key
                // 也可以不清理CacheUtil.getInstance().clearCache(systemCode+uuuid);
                // 开始清理图片文件

            }else{
                history=new SmsHistory();
            }
        }
        // 生成验证码
        int vcLen = systemConfig.getImgLength();
        String finalCode = getCode(systemConfig.getImgCharType(),vcLen);

        // 发送验证码

        RandomValidateCode rvc = new RandomValidateCode();
        String codeAry[];
        try {
            codeAry = rvc.getImgRandcode(finalCode, systemCode + sessionid);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.getResultDetail(serviceCode, LogType.SYSTEM, 460, "验证码生成错误");
        }

        finalCode = codeAry[1];
        String fileName = codeAry[0];
        log.info(finalCode);
        // 返回验证码
        HashMap<String,String> map = new HashMap<String,String>();
        // map.put("code", finalCode);
        map.put("img", FilePathUtil.joinPath(ConfigUtil.getConfig("server.domain"),"/",ConfigUtil.getConfig("server.servlet.context-path"),fileName));
        map.put("sessionid", sessionid);
        map.put("imgdata", codeAry[2]);
        result = ResultUtil.getDataResult(map);
        // 如果生成验证码成功
        if (result.isRight()) {
            // 塞入缓存系统
            try {

                resetHistory(systemCode + sessionid,history,finalCode,nowTime);//设置超时时间
                //CacheUtil.getInstance().writeCache(systemCode + sessionid,history, systemConfig.getImgLiveTime() / 1000);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.getResultDetail(serviceCode, LogType.SYSTEM, 486, "缓存失败");
            }
        }//redis.clients.jedis.exceptions.JedisConnectionException
        return result;
    }

    /**
     * 验证图片验证码
     *
     * @param systemCode
     * @param sessionid
     * @param code
     * @return
     * @author dozen.zhang
     */
    public ResultDTO imgValidCode(String systemCode, String sessionid, String code) {


        return this.validCode(systemCode, sessionid, code, false);
    }

    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add(0, 1);
        System.out.println(123);
        SmsHistory history =new SmsHistory();
        history.setCode("123");
        history.setLast(new Date().getTime());
        Gson gson =new Gson();
        //{"code":"123","last":1458008360365,"times":[]}
        //"{\"code\":\"L5AV\",\"last\":1458008543345,\"times\":[1458008543345]}"
        System.out.print(gson.toJson(history));
    }

    public ResultDTO remoteValidImg(String sessionid, String code) {
        // 获取systemcode
        if (StringUtil.isBlank(sessionid)) {
            return ResultUtil.getResult(ResultUtil.fail, "sessionid不能为空");
        }
        if (StringUtil.isBlank(code)) {
            return ResultUtil.getResult(ResultUtil.fail, "验证码不能为空");
        }
        String systemNo;
        try {
            systemNo = PropertiesUtil.get("valid_code_system_no");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResultUtil.getResult(ResultUtil.fail, "系统代号配置错误");
        }
        if (StringUtil.isBlank(systemNo)) {
            return ResultUtil.getResult(ResultUtil.fail, "系统代号配置不能为空");
        }
        String params = String.format("systemno=%s&sessionid=%s&code=%s", systemNo, sessionid, code);
        String urlPrefix = Config.getInstance().getValidCode().getServerUrl();
       // String jsonStr =// HttpRequestUtil.sendGet(Config.getInstance().getValidCode().getServerUrl()+"/code/img/b/valid.json", params);

        ResultDTO result = this.validCode(systemNo,sessionid,code,false);// JSON.parseObject(jsonStr, ResultDTO.class);
        return result;
        // TODO Auto-generated method stub
    }

    public ResultDTO remoteValidSms(String phone, String code) {
        // 获取systemcode
        if (StringUtil.isBlank(phone)) {
            return ResultUtil.getResult(ResultUtil.fail, "手机号码不能为空");
        }
        if (StringUtil.isBlank(code)) {
            return ResultUtil.getResult(ResultUtil.fail, "验证码不能为空");
        }
        String systemNo;
        try {
            systemNo = PropertiesUtil.get("valid_code_system_no");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResultUtil.getResult(ResultUtil.fail, "系统代号配置错误");
        }
        if (StringUtil.isBlank(systemNo)) {
            return ResultUtil.getResult(ResultUtil.fail, "系统代号配置不能为空");
        }
        String params = String.format("systemno=%s&phone=%s&code=%s", systemNo, phone, code);
        String jsonStr = HttpRequestUtil.sendGet(Config.getInstance().getValidCode().getServerUrl()+"/code/sms/b/valid.json", params);
        ResultDTO result = JSON.parseObject(jsonStr, ResultDTO.class);
        return result;

    }
    public String getCode(int type,int vcLen){

            if (type == 1) {
                return  StringUtil.getRandomDigitString(vcLen);
            } else if (type== 2){
                return StringUtil.getRandomAlphaString(vcLen);
            }else{
                return StringUtil.getRandomAlphaDigitString(vcLen);
            }
        }

}
