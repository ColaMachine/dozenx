/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年12月26日
 * 文件说明: 
 */
package com.dozenx.web.core.codegen;

import com.dozenx.util.StringUtil;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

public class ZTable {
    private String pkg;
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    private String name;
private String tableName;
private ZMapper mapper;
public ZMapper getMapper() {
    return mapper;
}
public void setMapper(ZMapper mapper) {
    this.mapper = mapper;
}
public String getTableName() {
    return tableName;
}
public void setTableName(String tableName) {
    this.tableName = tableName;
}

private String remark;
public String getRemark() {
    return remark;
}
public void setRemark(String remark) {
    this.remark = remark;
}

private ZColum pk;
public ZColum getPk() {
    return pk;
}
public void init(){
    for(int i=cols.size()-1;i>=0;i--){
        ZColum col =cols.get(i);
        if(col==null){
            System.out.print("not null");
            cols.remove(i);
            continue;
        }
        if(col.isPk()){
            pk=col;
                    //break;
        }
        if(col.isList()){
            col.setList(true);
        }else{
            col.setList(false);
        }
        if(col.isEdit()){
            col.setEdit(true);
        }else{
            col.setEdit(false);
        }

        //如果colName是空的话 就用name 去替代

        if(StringUtil.isBlank(col.getColName())){
            col.setColName(col.getName());
        }
    }
}
public void setPk(ZColum pk) {
    this.pk = pk;
}

private  List<ZColum> cols ;



public List<ZColum> getCols() {
    return cols;
}

public void setCols(List<ZColum> cols) {
    this.cols = cols;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public static class Handler implements JsonSerializer<ZTable>, JsonDeserializer<ZTable> {

  
    /**
     * @param json 参数
     * @param typeOfT 参数
     * @param context 上下文
     * @return RedisConfig
     * @throws JsonParseException 抛出异常
     */
    public ZTable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        ZTable talbe =new ZTable();
        JsonObject jsonObject = json.getAsJsonObject();
        talbe = context.deserialize(json, ZTable.class);
        if(jsonObject.get("list")==null){

        }

//        List<ZColum> zcolums= jsonObject.get("column").;
        return talbe;
    }

 
    /**
     * @param src 参数
     * @param typeOfT 参数
     * @param context 上下文
     * @return JsonElement 抛出异常
     */
    public JsonElement serialize(ZTable src, Type typeOfT, JsonSerializationContext context) {
        // JsonObject result =new JsonObject();
        // result = context.serialize(src);
        return context.serialize(src);
    }


 

}
}
