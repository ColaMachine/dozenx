package com.dozenx.net.netty.cmd;

import com.dozenx.common.util.ByteUtil;
import com.dozenx.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by luying on 17/2/18.
 */
public class CmdUtil {
    private Logger logger = LoggerFactory.getLogger(CmdUtil.class);
    public static GameCmd getCmd(byte[] bytes){

        int cmdTypeVal= ByteUtil.getInt(bytes);

       Class cls =  CmdType.idToClassMap.get(cmdTypeVal);

        if(cls==null){
            LogUtil.err("can't recognize the cmd: cmdType:"+cmdTypeVal);
            return null;
        }

       /* if (cmdTypeVal == CmdType.StartTaskCmd.ordinal()) {//equip
            return new StartTaskCmd(bytes);
        }else{
            LogUtil.err("can't recognize the cmd");
            return null;
        }*/
        //反射实例化对

        Constructor<GameCmd> cons;
        try {
            cons = cls.getConstructor(byte[].class);//获得构造方法
            GameCmd cmd = cons.newInstance(bytes);
            return cmd;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;



    }
}
