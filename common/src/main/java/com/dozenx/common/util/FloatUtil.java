package com.dozenx.common.util;

//import org.springframework.util.DigestUtils;

/**
 * 字符串工具类
 *
 * @author 张智威
 *         2017年4月10日 上午10:07:58
 */
public class FloatUtil {
    public boolean floatEqual(Float a, Float b) {
        if (a == null)
            return b == null;
        if (b == null)
            return false;
        return Math.abs(a - b) >= 0.0000001;
    }
}
