package com.dozenx.web.core.auth.validcode.action;

import com.dozenx.swagger.annotation.*;
import com.dozenx.common.config.Config;
import com.dozenx.common.config.ValidCodeConfig;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.auth.validcode.service.ValidCodeService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.util.RequestUtil;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;


@APIs(description = "验证码")

@Controller
@RequestMapping("/code")
public class ValidCodeController extends BaseController{
    /** * Logger 引入 */
    private static Logger logger = LoggerFactory.getLogger(ValidCodeController.class);
    @Autowired
    private ValidCodeService validCodeService;
    public void setValidCodeService(ValidCodeService validCodeService) {
        this.validCodeService = validCodeService;
    }


//    @APIResponse(value = "{\"r\":0,\"data\":base64二维验证码图片}")
//    @RequestMapping(value = "/login/pic/captcha", method = RequestMethod.GET, produces = "application/json")
//    public
//    @ResponseBody
//    ResultDTO imgCode(HttpServletRequest request, HttpServletResponse response) {
//
//        return validCodeService.getImgValidCode("calendar", sessionId);
//    }
    /**
     * 第三方系统前端请求获取短信验证码
     * @param request
     * @return
     * @author dozen.zhang
     */


    @RequestMapping(value = "/sms/get.json")
    @ResponseBody
    public JSONPObject smsGet(HttpServletRequest request ,HttpServletResponse response){


        //request.getSession(true);//强制生成session 以防止 getRequestedSessionId返回为null
        //logger.info("sessionid:" + request.getSession().getId());
        //===设置到网站的根目录下 jsession cookie值 这样访问任何微服务都会带上这个cookie值
//        Cookie hit=new Cookie("JSESSIONID",request.getSession().getId());
//        hit.setHttpOnly(true);//如果设置了"HttpOnly"属性，那么通过程序(JS脚本、Applet等)将无法访问该Cookie
//        hit.setMaxAge(60*60);//设置生存期为1小时
////		hit.setDomain("www.zifansky.cn");//子域，在这个子域下才可以访问该Cookie
//        hit.setPath("/");//在这个路径下面的页面才可以访问该Cookie
//		hit.setSecure(true);//如果设置了Secure，则只有当使用https协议连接时cookie才可以被页面访问
//        response.addCookie(hit);
        UUID uuid = UUID.randomUUID();
        String sessionId = uuid.toString();
        this.setSessionAttribute(request , "uid", sessionId);


        ValidCodeConfig config = Config.getInstance().getValidCode();
        String ip = RequestUtil.getIp(request);
        String timeStamp = request.getParameter("timeStamp");
        String appId =request.getParameter("appid");
        String phone =request.getParameter("phone");
        String systemCode = request.getParameter("systemno");
        //调用生成验证码服务 返回验证码 或者失败原因

        this.setSessionAttribute(request,"phone",phone);
        ResultDTO result =
                validCodeService.getSmsValidCode(systemCode,phone,ip);

        result.setData(null);



       /* try {
            response.setHeader("Content-Type", "application/javascript;charset=UTF-8");
            String jsonp= new String(callbackParam+"("+JSON.toJSONString(result)+")))))");
            response.getOutputStream().write(jsonp.getBytes("UTF-8"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        //将result转成JSON串输出
        // return result;
        String callbackParam = request.getParameter("callback");
        JSONPObject object =new JSONPObject(callbackParam,result);
        return object;
    }



    @API(summary = "用户登录接口",
            consumes = "application/json",
            description = " 用户登录接口", parameters = {


            @Param(name="phone" , description="手机号", in= InType.query,dataType = DataType.STRING,required = true),
            @Param(name="systemno" , description="系统号", in= InType.query,dataType = DataType.STRING,required = true),
//
//            @Param(name = "loginName", description = "用户名",schema = ""
//                    , dataType = DataType.STRING, in = "body", required = true),
//            @Param(name = "pwd", description = "加密后的密码"
//                    , dataType = DataType.STRING, in = "body", required = true),
//            @Param(name = "picCaptcha", description = "验证码"
//                    , dataType = DataType.LONG, in = "body", required = true),

    })
    @RequestMapping(value = "/sms")
    @ResponseBody
    public ResultDTO smsCode(HttpServletRequest request ,HttpServletResponse response){


      //  request.getSession(true);//强制生成session 以防止 getRequestedSessionId返回为null
        logger.info("sessionid:" + this.getSessionId(request));
        //===设置到网站的根目录下 jsession cookie值 这样访问任何微服务都会带上这个cookie值
        Cookie hit=new Cookie("JSESSIONID",this.getSessionId(request));
        hit.setHttpOnly(true);//如果设置了"HttpOnly"属性，那么通过程序(JS脚本、Applet等)将无法访问该Cookie
        hit.setMaxAge(60*60);//设置生存期为1小时
//		hit.setDomain("www.zifansky.cn");//子域，在这个子域下才可以访问该Cookie
        hit.setPath("/");//在这个路径下面的页面才可以访问该Cookie
//		hit.setSecure(true);//如果设置了Secure，则只有当使用https协议连接时cookie才可以被页面访问
        response.addCookie(hit);
        UUID uuid = UUID.randomUUID();
        String sessionId = uuid.toString();
        this.setSessionAttribute(request , "uid", sessionId);


        ValidCodeConfig config = Config.getInstance().getValidCode();
        String ip = RequestUtil.getIp(request);
        String timeStamp = request.getParameter("timeStamp");
        String appId =request.getParameter("appid");
        String phone =request.getParameter("phone");
        String systemCode = request.getParameter("systemno");
        //调用生成验证码服务 返回验证码 或者失败原因
        this.setSessionAttribute(request,"phone",phone);
        ResultDTO result =
                validCodeService.getSmsValidCode(systemCode,phone,ip);
        result.setData(null);
        return result;
    }
    /**
     * 第三方系统前端请求获取短信验证码
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/email/get.json")
    @ResponseBody
    public JSONPObject emailGet(HttpServletRequest request ,HttpServletResponse response){
        ValidCodeConfig config = Config.getInstance().getValidCode();
        String timeStamp = request.getParameter("timeStamp");
        String appId =request.getParameter("appid");
        String phone =request.getParameter("email");
        String systemCode = request.getParameter("systemno");
        //调用生成验证码服务 返回验证码 或者失败原因
        ResultDTO result =
                validCodeService.getEmailValidCode(systemCode,phone);
        result.setData(null);



       /* try {
            response.setHeader("Content-Type", "application/javascript;charset=UTF-8");
            String jsonp= new String(callbackParam+"("+JSON.toJSONString(result)+")))))");
            response.getOutputStream().write(jsonp.getBytes("UTF-8"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        //将result转成JSON串输出
        // return result;
        String callbackParam = request.getParameter("callback");
        JSONPObject object =new JSONPObject(callbackParam,result);
        return object;
    }
    @RequestMapping(value = "/img/get.json")
    @ResponseBody
    public JSONPObject imgGet(HttpServletRequest request,HttpServletResponse response)throws Exception{
        String systemno =request.getParameter("systemno");
        String sessionid = request.getParameter("sessionid");
        String sid =request.getSession().getId();
        System.out.println("sid"+sid);
        //调用生成验证码服务 返回验证码 或者失败原因
        ResultDTO result =
                validCodeService.getImgValidCode(systemno,sid);
        // result.setData(null);

        if(result.isRight()){
            Object data=result.getData();
            HashMap map =(HashMap)data;
            map.put("code", "");
            map.remove("code");
            result.setData(map);
        }
        String callbackParam = request.getParameter("callback");

        JSONPObject object =new JSONPObject(callbackParam,result);
        return object;



    }


    /**
     * 第三方系统前端请求获取短信验证码
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/sms/b/get.json")
    @ResponseBody
    public Object smsBehindGet(HttpServletRequest request){
        String timeStamp = request.getParameter("timeStamp");
        String appId =request.getParameter("appid");
        String phone =request.getParameter("phone");
        String ip =request.getParameter("ip");
        String systemCode = request.getParameter("systemCode");
        //调用生成验证码服务 返回验证码 或者失败原因

        ResultDTO result =
                validCodeService.getSmsValidCode(systemCode,phone,ip);

        return result;



    }

    @RequestMapping(value = "/img/b/get.json")
    @ResponseBody
    public Object imgBehindGet(HttpServletRequest request){
        String token =request.getParameter("token");
        String timestamp = request.getParameter("timestamp");
        String systemno =request.getParameter("systemno");
        String sessionid = request.getParameter("sessionid");

        String sid =this.getSessionId(request);
        //System.out.println("sid"+sid);
        //调用生成验证码服务 返回验证码 或者失败原因
        ResultDTO result =
                validCodeService.getImgValidCode(systemno,sid);
        return result;
    }

    @RequestMapping(value = "/sms/valid.json")
    @ResponseBody
    public ResultDTO smsValidCode(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String systemno = request.getParameter("systemno");
        String code = request.getParameter("code");
        //调用生成验证码服务 返回验证码 或者失败原因
        ResultDTO result = validCodeService.smsValidCode(systemno, phone, code);

        return result;
    }


    @RequestMapping(value = "/jsonp/sms/valid.json")
    @ResponseBody
    public JSONPObject jsonpSmsValidCode(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String systemno = request.getParameter("systemno");
        String code = request.getParameter("code");
        //调用生成验证码服务 返回验证码 或者失败原因
        ResultDTO result = validCodeService.smsValidCode(systemno, phone, code);
        String callbackParam = request.getParameter("callback");
        JSONPObject object =new JSONPObject(callbackParam,result);
        return object;
    }

    @RequestMapping(value = "/img/valid.json")
    @ResponseBody
    public ResultDTO imgValidCode(HttpServletRequest request){
        String systemno = request.getParameter("systemno");
        String sessionid = request.getRequestedSessionId();
        String code = request.getParameter("code");
        //调用生成验证码服务 返回验证码 或者失败原因
        ResultDTO result = validCodeService.imgValidCode(systemno, sessionid, code);

        if(result.isRight()){
            this.setSessionParam(request,"hellosms","1");
        }
        String callbackParam = request.getParameter("callback");
        return result;

    }

    @RequestMapping(value = "/jsonp/img/valid.json")
    @ResponseBody
    public JSONPObject jsonpImgValidCode(HttpServletRequest request){
        String systemno = request.getParameter("systemno");
        String sessionid = request.getParameter("sessionid");
        String code = request.getParameter("code");
        //调用生成验证码服务 返回验证码 或者失败原因
        ResultDTO result = validCodeService.imgValidCode(systemno, sessionid, code);
        String callbackParam = request.getParameter("callback");
        JSONPObject object =new JSONPObject(callbackParam,result);
        return object;
    }

    @RequestMapping(value = "/sms/b/valid.json")
    @ResponseBody
    public ResultDTO smsBehindValidCode(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String systemno = request.getParameter("systemno");
        String code = request.getParameter("code");
        //调用生成验证码服务 返回验证码 或者失败原因
        ResultDTO result = validCodeService.smsValidCode(systemno, phone, code);

        return result;
    }
    @RequestMapping(value = "/img/b/valid.json")
    @ResponseBody
    public ResultDTO imgBehindValidCode(HttpServletRequest request){
        String systemno = request.getParameter("systemno");
        String sessionid = request.getRequestedSessionId();
        String code = request.getParameter("code");
        //调用生成验证码服务 返回验证码 或者失败原因
        ResultDTO result = validCodeService.imgValidCode(systemno, sessionid, code);
        return result;
    }
    @RequestMapping(value = "/img/request.json", method = RequestMethod.GET)
    public @ResponseBody ResultDTO imgRequest(HttpServletRequest request){
        String uuuid =UUID.randomUUID().toString();//StringUtil.getRandomAlphaDigitString(11);// RandomUtil.getRandom(10000000);
        request.getSession().setAttribute("uuuid",request.getRequestedSessionId());
        return validCodeService.getImgValidCode("calendar",request.getRequestedSessionId());
    }
    /**
     * @Author: dozen.zhang
     * @Description:获取二维验证码图片接口
     * @Date: 2018/2/8
     */
    @API(summary = "获取验证码",
            consumes = "application/x-www-form-urlencoded",
            description = " ", parameters = {

    })
    @APIResponse(value = "{\"r\":0,\"data\":base64二维验证码图片}")
    @RequestMapping(value = "/img",method=RequestMethod.GET,produces="application/json")
    public @ResponseBody ResultDTO imgCode(HttpServletRequest request){
        return validCodeService.getImgValidCode("calendar",request.getRequestedSessionId());
    }
    @RequestMapping(value = "/sms/request.json", method = RequestMethod.GET)
    public @ResponseBody ResultDTO smsRequest(HttpServletRequest request){



        String phone =request.getParameter("phone");

        //防止短信被滥用接口
        if(this.getSessionParam(request,"hellosms")==null){
            logger.error("cheater ip:"+   request.getRemoteAddr()+" xforward "+request.getHeader("x-forwarded-for")+"phone:"+phone);
            return this.getResult(30106601);
        }

        if(StringUtil.isBlank(phone)||!StringUtil.isPhone(phone)){
            return ResultUtil.getResult(30106602,"手机号不能为空");
        }
        String ip =RequestUtil.getIp(request);
        return validCodeService.getSmsValidCode("calendar",phone,ip);
    }
    public static void main(String[] args) {
        //\222\200\177\000\000\001
        byte[] byteData=new byte[]{0x36,};

        System.out.println(123);

        System.out.println(new String(byteData));
        //  ApplicationContext ac = new FileSystemXmlApplicationContext("C:\\zzw\\workspace\\awifiui\\src\\main\\resources\\config\\xml\\applicationContext.xml");
        // Object object = ac.getBean("validCodeService");
        //System.out.println(object);//DefaultBeanDefinitionDocumentReader
        //ComponentScanBeanDefinitionParser
    }

}
