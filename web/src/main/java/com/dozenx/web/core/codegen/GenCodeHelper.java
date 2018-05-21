/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年3月12日
 * 文件说明: 
 */
package com.dozenx.web.core.codegen;

import com.dozenx.util.DateUtil;
import com.dozenx.util.ReflactorUtil;
import com.dozenx.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class GenCodeHelper {

    public static <T> T getValidBean(HttpServletRequest request, String name,Class<T> clzz) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        //利用反射创建一个类
        T object = clzz.newInstance();
        //取出ztable
        ZTable table = Generator.allTable.get(name);
        HashMap map  = new HashMap();
        //通过名称的方式反射得到值 并塞入 map 或者 bean
        for(ZColum col :table.getCols()){
            String colName =col.getName();
            String type = col.getType().toLowerCase();
            String value = request.getParameter(colName);
            if(!StringUtil.isBlank(value)){
                if(type.startsWith("date")||type.startsWith("timestamp")||type.startsWith("datetime")){
                    if(StringUtil.checkNumeric(value)){
                        map.put(colName,new Timestamp(Long.valueOf(value)));
                        ReflactorUtil.setValue(object,new Timestamp(Long.valueOf(value)),colName);
                    }
                    else{
                        if(type.startsWith("timestamp")){
                            map.put(colName,new Timestamp(DateUtil.parseToDate( value,"yyyy-MM-dd" ).getTime()));
                            ReflactorUtil.setValue(object,new Timestamp(DateUtil.parseToDate( value,"yyyy-MM-dd" ).getTime()),colName);
                        }
                        if(type.startsWith("date")){
                            map.put(colName,DateUtil.parseToDate( value,"yyyy-MM-dd" ));
                            ReflactorUtil.setValue(object,DateUtil.parseToDate( value,"yyyy-MM-dd" ),colName);
                        }
                    }
                }else{
                    map.put(colName,value);
                    ReflactorUtil.setValue(object,value,colName);
                }
            }
        }
        return object;
    }
    /**
     * 代码字符串  new Timestamp(DateUtil.parseToDate(startTime,"yyyy-MM-dd").getTime())
     * @param type
     * @param name
     * @return
     */
    public static String changeStrVar2BeanType(String type,String name){
    StringBuffer sb = new StringBuffer();
    if (type.startsWith("timestamp")) {
        sb.append("new Timestamp( DateUtil.parseToDate(" +name+ ", \"" + StringUtil.getYMDStr(type) + "\").getTime())");
    }else
    if (type.startsWith("date")||type.startsWith("datetime")) {
        sb.append("DateUtil.parseToDate(" + name + ", \"" + StringUtil.getYMDStr(type) + "\")");
    }else
    if (type.startsWith("int")||type.startsWith("Long")||type.startsWith("tinyint")) {
        sb.append(changeMySqlType2JavaType(type)+".valueOf("+name+")");
    }else{
        sb.append(name);
    }
    return sb.toString();
}

    public static ZColum getColFromCols(List<ZColum> cols , String colName){
        for(ZColum col:cols ){
            if(col.getName().equals(colName)){
                return col;
            }
        }
        return null;
    }
public static Integer getIntFromKuoHao(String str){
    int index = str.indexOf("(");
    if(index==-1){
        return null;
    }
    else{
        int end =str.indexOf(")");
        if(end>index){
            String val = str.substring(index+1,end);
            return Integer.valueOf(val);
        }else{
            return null;
        }
        
    }
}

    /**
     * mysql 的字段类型转换成java的类型
     * @param type
     * @return
     */
    public static String changeMySqlType2JavaType(String type) {
    String typeName = null;
    type = type.toLowerCase();
    if (type.startsWith("varchar")) {
        typeName = "String";
    } else if (type.startsWith("int")) {
        typeName = "Integer";
    } else if (type.startsWith("bigint")) {
        typeName = "Long";
    } else if (type.startsWith("float")) {
        typeName = "Float";
    } else if (type.startsWith("double")) {
        typeName = "Double";
    } else if (type.startsWith("date")) {
        typeName = "Date";
    } else if (type.startsWith("timestamp")) {
        typeName = "Timestamp";
    } else if (type.startsWith("text")) {
        typeName = "String";
    }
    else if (type.startsWith("char")) {
        typeName = "String";
    } else if (type.startsWith("tinyint")) {
        typeName = "Byte";
    } else if (type.startsWith("decimal")) {
        typeName = "BigDecimal";
    }
    return typeName;
}
    public static String changeMySqlType2ApiType(String type) {
        String typeName = null;
        type = type.toLowerCase();
        if (type.startsWith("varchar")) {
            typeName = "STRING";
        } else if (type.startsWith("int")) {
            typeName = "INTEGER";
        } else if (type.startsWith("bigint")) {
            typeName = "LONG";
        } else if (type.startsWith("float")) {
            typeName = "FLOAT";
        } else if (type.startsWith("double")) {
            typeName = "DOUBLE";
        } else if (type.startsWith("date")) {
            typeName = "DATE";
        } else if (type.startsWith("timestamp")) {
            typeName = "TIMESTAMP";
        } else if (type.startsWith("text")) {
            typeName = "STRING";
        }
        else if (type.startsWith("char")) {
            typeName = "STRING";
        } else if (type.startsWith("tinyint")) {
            typeName = "BYTE";
        }
        else if (type.startsWith("decimal")) {
            typeName = "FLOAT";
        }
        return typeName;
    }

}
