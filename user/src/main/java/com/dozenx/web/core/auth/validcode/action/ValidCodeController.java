package com.dozenx.web.core.auth.validcode.action;

import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIResponse;
import com.cpj.swagger.annotation.APIs;
import com.dozenx.core.config.Config;
import com.dozenx.core.config.ValidCodeConfig;
import com.dozenx.util.RandomUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.StringUtil;
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

    /**
     * 第三方系统前端请求获取短信验证码
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/sms/get.json")
    @ResponseBody
    public JSONPObject smsGet(HttpServletRequest request ,HttpServletResponse response){
        ValidCodeConfig config = Config.getInstance().getValidCode();
        String ip = RequestUtil.getIp(request);
        String timeStamp = request.getParameter("timeStamp");
        String appId =request.getParameter("appid");
        String phone =request.getParameter("phone");
        String systemCode = request.getParameter("systemno");
        //调用生成验证码服务 返回验证码 或者失败原因
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

        String sid =request.getSession().getId();
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
            request.getSession().setAttribute("hellosms","1");
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
        if(request.getSession().getAttribute("hellosms")==null){
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
