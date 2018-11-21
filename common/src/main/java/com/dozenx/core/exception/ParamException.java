package com.dozenx.core.exception;

/**
 * Created by dozen.zhang on 2017/2/24.
 */
public class ParamException extends  MyException {
    public ParamException(String code, String msg) {
        super(code,msg);

    }
    public ParamException(int code, String msg) {
        super(code,msg);

    }


}
