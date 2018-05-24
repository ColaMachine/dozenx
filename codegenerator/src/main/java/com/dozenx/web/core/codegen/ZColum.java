/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年12月26日
 * 文件说明: 
 */
package com.dozenx.web.core.codegen;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class ZColum {
private String name;
    private String colName;
    private boolean nn;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    private String references;
    private String file;


    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    private boolean list;
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getReferences() {
        return references;

    }

    public void setReferences(String references) {
        this.references = references;
    }

    private boolean pk;

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    private boolean edit;

    private boolean uq;

    public boolean isUq() {
        return uq;
    }

    public void setUq(boolean uq) {
        this.uq = uq;
    }

    private boolean ai;
public boolean isAi() {
    return ai;
}
public void setAi(boolean ai) {
    this.ai = ai;
}
private String def;
public boolean isNn() {
    return nn;
}
public void setNn(boolean nn) {
    this.nn = nn;
}
public boolean isPk() {
    return pk;
}
public void setPk(boolean pk) {
    this.pk = pk;
}
public String getDef() {
    return def;
}
public void setDef(String def) {
    this.def = def;
}
private String type;
private String remark;
private Map showValue;
public Map getShowValue() {
    return showValue;
}
public void setShowValue(Map showValue) {
    this.showValue = showValue;
}
private String valid;
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public String getType() {
    return type;
}
public void setType(String type) {
    this.type = type;
}
public String getRemark() {
    return remark;
}
public void setRemark(String remark) {
    this.remark = remark;
}
public String getValid() {
    return valid;
}
public void setValid(String valid) {
    this.valid = valid;
}
/*
public String getJavaType(){
    return "long";
}*/
    /*
    name:"id",
    type:varchar(40),
    cn_name:"id",
    valid:"Length(min=1,max=40 ,message='图片名称不能超过40个')",*/


    public static class Handler implements JsonSerializer<ZColum>, JsonDeserializer<ZColum> {


        /**
         * @param json 参数
         * @param typeOfT 参数
         * @param context 上下文
         * @return RedisConfig
         * @throws JsonParseException 抛出异常
         */
        public ZColum deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            Gson gson =new Gson();
            System.out.println("column name:"+json.getAsJsonObject().get("name"));
            ZColum col = gson.fromJson(json,ZColum.class);
            JsonObject jsonObject = json.getAsJsonObject();
           /* col = context.deserialize(json, ZColum.class);
            col.setList(jsonObject.get("list")==null?true: Boolean.valueOf(jsonObject.get("list").getAsBoolean()));
            col.setAi(jsonObject.get("ai")==null?true: Boolean.valueOf(jsonObject.get("ai").getAsBoolean()));
            col.setDef(jsonObject.get("ai").getAsString());
           col.setEdit(jsonObject.get("edit")==null?true: Boolean.valueOf(jsonObject.get("edit").getAsBoolean()));
            col.setFile(jsonObject.get("file").getAsString());
            col.setName(jsonObject.get("name").getAsString());
            col.setNn(jsonObject.get("nn")==null?true: Boolean.valueOf(jsonObject.get("nn").getAsBoolean()));
            col.setPk(jsonObject.get("pk")==null?true: Boolean.valueOf(jsonObject.get("pk").getAsBoolean()));
            col.setReferences(jsonObject.get("name").getAsString());
            col.setRemark(jsonObject.get("name").getAsString());
            col.setShowValue(jsonObject.get("showValue")..getAsString());
            col.setType(jsonObject.get("name").getAsString());
            col.setUq(jsonObject.get("uq")==null?true: Boolean.valueOf(jsonObject.get("uq").getAsBoolean()));
            col.setValid(jsonObject.get("valid").getAsString());*/
            if(jsonObject.get("list")==null){
                col.setList(true);
            }
            if(jsonObject.get("edit")==null){
                col.setEdit(true);
            }

            //System.out.println(json);
//        List<ZColum> zcolums= jsonObject.get("column").;
            return col;
        }


        /**
         * @param src 参数
         * @param typeOfT 参数
         * @param context 上下文
         * @return JsonElement 抛出异常
         */
        public JsonElement serialize(ZColum src, Type typeOfT, JsonSerializationContext context) {
            // JsonObject result =new JsonObject();
            // result = context.serialize(src);
            return context.serialize(src);
        }




    }
}
