package com.dozenx.core.exception;

//import com.dozenx.web.core.log.MessagePropertiesResolver;

/**
 * Created by dozen.zhang on 2017/11/9.
 */
public class InterfaceException extends MyException{
    public String code ;
    public String msg;
    public InterfaceException(String code, String msg) {
        super(code,msg);

    }
    public InterfaceException(int code, String msg) {
        super(code,msg);

    }
//core里的代码不应该去应用web项目里的东西
//    public InterfaceException(MessagePropertiesResolver messagePropertiesResolver){
//        this.code = messagePropertiesResolver.code;
//        this.msg = messagePropertiesResolver.msg;
//    }
}
