package com.dozenx.web.core.aop;

import com.dozenx.common.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author: wangsaichao
 * @date: 2017/10/25
 * @description: 配置切面,配置日志 和请求参数打印
 */
@Aspect
//@Order(-99) // 控制多个Aspect的执行顺序，越小越先执行,为了要在Spring的事务之后执行,所以给他设置99
@Configuration
public class LogAop {

    private static final Logger logger = LoggerFactory.getLogger(LogAop.class);

    /**
     * 定义切点Pointcut
     * 第一个*号：表示返回类型， *号表示所有的类型
     * 第二个*号：表示类名，*号表示所有的类
     * 第三个*号：表示方法名，*号表示所有的方法
     * 后面括弧里面表示方法的参数，两个句点表示任何参数
     */
    @Pointcut("execution(* com.awifi.*.controller.*.*(..)) || execution(* com.dozenx.*.action.*.*(..)) || execution(* com.awifi.*.action.*.*(..)) ")
    public void executionService() {

    }


    /**
     * 方法调用之前调用
     * @param joinPoint
     */
    @Before(value = "executionService()")
    public void doBefore(JoinPoint joinPoint){
        try {
            //http请求传入日志id
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes)  RequestContextHolder.getRequestAttributes();
            if(requestAttributes!=null){//像一些方法 比如kafka listrener 是没有request的
                HttpServletRequest request = requestAttributes.getRequest();
                if (request != null) {
                    String traceId = request.getHeader("SMARTHOME_TRACE_ID");
                    //添加日志打印
                    if (!StringUtil.isBlank(traceId)) {
                        MDC.put("SMARTHOME_TRACE_ID", traceId);
                    }
                }
            }

            //日志id不存在则添加日志打印
            if (MDC.get("SMARTHOME_TRACE_ID") == null) {
                String traceId = String.valueOf(UUID.randomUUID());
                MDC.put("SMARTHOME_TRACE_ID", traceId);
            }
            if (joinPoint != null) {
                logger.debug("=====>@Before：请求参数为：{}", Arrays.toString(joinPoint.getArgs()));
            }
        }catch (Exception e){
            logger.warn("警告！日志id添加 出错");
        }
    }

    /**
     * 方法之后调用
     * @param joinPoint
     * @param returnValue 方法返回值
     */
    @AfterReturning(pointcut = "executionService()",returning="returnValue")
    public void  doAfterReturning(JoinPoint joinPoint,Object returnValue){

        logger.debug("=====>@AfterReturning：响应参数为：{}",returnValue);
        // 处理完请求，返回内容
        MDC.clear();
    }

    /**
     * 统计方法执行耗时Around环绕通知
     * @param joinPoint
     * @return
     */
    @Around("executionService()")
    public Object timeAround(ProceedingJoinPoint joinPoint) throws Throwable {

        //获取开始执行的时间
        long startTime = System.currentTimeMillis();

        // 定义返回对象、得到方法需要的参数
        Object obj = null;
        //Object[] args = joinPoint.getArgs();

        try {
            obj = joinPoint.proceed();
        } catch (Throwable e) {
            logger.debug("=====>统计某方法执行耗时环绕通知出错", e);
            throw e;
        }

        // 获取执行结束的时间
        long endTime = System.currentTimeMillis();
        //MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        // 打印耗时的信息
        logger.debug("=====>处理本次请求共耗时：{} ms",endTime-startTime);
        return obj;
    }

}