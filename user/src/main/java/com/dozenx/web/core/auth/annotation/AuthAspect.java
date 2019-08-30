package com.dozenx.web.core.auth.annotation;

import com.dozenx.core.config.SysConfig;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.annotation.RequiresPermission;
import com.dozenx.web.core.auth.session.SessionUser;
import com.dozenx.web.core.auth.sysPermission.bean.SysPermission;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.SpringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:12 2018/3/26
 * @Modified By:
 */

@Component
@Aspect
public class AuthAspect {


    @Around("execution(* *.*(..)) && @annotation(com.dozenx.web.core.annotation.RequiresLogin)")
    public Object validLogin(ProceedingJoinPoint pjp ) throws Throwable {
        Object result=null;

        Object[] objectAry =  pjp.getArgs();

        HttpServletRequest request =null;
        for ( int i=0;i<objectAry.length;i++){
            if(objectAry[i] instanceof HttpServletRequest){
                request = (HttpServletRequest)objectAry[i];

            }
        }
        if(request==null){
            return pjp.proceed();
            //  throw new Exception( "request 不能为空");
        }else{
            SessionUser session = (SessionUser) new BaseController().getUser(request);
            if(session==null){
                return ResultUtil.getResult(504,"未登录");

            }
        }
        return pjp.proceed();

    }

    /**
     *
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("execution(* *.*(..)) && @annotation(com.dozenx.web.core.annotation.RequiresPermission)")
    public Object validPermission(ProceedingJoinPoint pjp ) throws Throwable {
        Object result=null;
        Method method=getMethod(pjp);
        Object[] objectAry =  pjp.getArgs();
        HttpServletRequest request =null;
        for ( int i=0;i<objectAry.length;i++){
            if(objectAry[i] instanceof HttpServletRequest){
                request = (HttpServletRequest)objectAry[i];

            }
        }
        if(request==null){
            return pjp.proceed();
            //  throw new Exception( "request 不能为空");
        }else{
            RequiresPermission permission = method.getAnnotation(RequiresPermission.class);
            String requestMethod = request.getMethod();
            if(permission!=null){
                String name = permission.name();
//                List<SysPermission > permissions = (List<SysPermission >)request.getSession().getAttribute(Constants.SESSION_PERMISSIONS);
//                if(permissions==null ){
//                    //如果permissions为null 既有可能是session过期了
//                    Object session = request.getSession().getAttribute(Constants.SESSION_USER);
//                    if(session==null){
//                        return ResultUtil.getResult(504,"未登录");
//
//                    }
//
//                    return ResultUtil.getResult(505,"没有权限");
//                }
//                boolean hasPermission =false;


                List<String > permissions = (List<String >)new BaseController().getSessionAttribute(request,Constants.SESSION_PERMISSIONS,List.class);
                if(permissions==null ){
                    //如果permissions为null 既有可能是session过期了
                    Object session = new BaseController().getUser(request);
                    if(session==null){
                        return ResultUtil.getResult(504,"未登录");

                    }

                    return ResultUtil.getResult(505,"没有权限");
                }
                boolean hasPermission =false;


                String realUrl =request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo())+":"+requestMethod;
                System.out.println("代校验url"+realUrl);
                System.out.println("系统根目录"+ SysConfig.PATH);
                //如果带校验的url 有 webROOTPATH 的话 需要去掉之后匹配才能成功

                Method pjpMethod=getMethod(pjp);
                String apiUrl = SpringUtil.getRequestMappingUrl(pjpMethod);
                System.out.println("代校验api url"+apiUrl);
                //如果含有{pathVariable}的话就要 模糊匹配了
//                if(permissions != null )
//                    for(SysPermission permission1: permissions){
//                        System.out.println("匹配的url"+permission1.getUrl());
//                        if(StringUtil.isNotBlank( permission1.getUrl()) && (apiUrl.equals(permission1.getUrl())||permission1.getUrl().endsWith(apiUrl))) {
//                            hasPermission =true;
//                            break;
//                        }
//                    }
//                if(!hasPermission){
//                    return ResultUtil.getResult(505,"没有权限");
//                }

                if(permissions!=null  )
                    for(String url: permissions){
                        System.out.println("匹配的url"+url);
                        if(StringUtil.isNotBlank( url) && (apiUrl.equals(url)||url.endsWith(apiUrl))) {
                            System.out.println("匹配了");
                            hasPermission =true;
                            break;
                        }
                    }
                if(!hasPermission){
                    return ResultUtil.getResult(505,"没有权限");
                }

            }
        }
        return pjp.proceed();

    }


    /**
     *  获取被拦截方法对象
     *
     *  MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象
     *    而缓存的注解在实现类的方法上
     *  所以应该使用反射获取当前对象的方法对象
     */
    public Method getMethod(ProceedingJoinPoint pjp){
        //获取参数的类型
        Object [] args=pjp.getArgs();
        Class [] argTypes=new Class[pjp.getArgs().length];
        for(int i=0;i<args.length;i++){
            if(args[i]!=null) {
                String name = args[i].getClass().getName();
                //System.out.println(args[i].getClass().getName());
                //System.out.println(args[i].getClass().toString());

                argTypes[i] = args[i].getClass();
                if (args[i].getClass().getName().equals("org.apache.catalina.connector.RequestFacade")) {
                    argTypes[i] = HttpServletRequest.class;
                }
            }
        }
        Method method=null;
        try {

            Class[] parameterTypes = ((MethodSignature)pjp.getSignature()).getMethod().getParameterTypes();
            method=pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(),parameterTypes);
            //   method=pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(),argTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return method;

    }

}
