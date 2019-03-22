package com.dozenx;

import com.dozenx.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:08 2019/3/21
 * @Modified By:
 */
public class AlertFunction implements FunctionDefinition{
    public String name="alert";




    @Override
    public Object call(List<Object> args) {
        System.out.println("alert"+args.get(0));
        return null;
    }
}
