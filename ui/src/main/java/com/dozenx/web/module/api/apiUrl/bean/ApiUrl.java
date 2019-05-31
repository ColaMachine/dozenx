/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年04月16日
 * 文件说明:
 */
package com.dozenx.web.module.api.apiUrl.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *api分类实体类
 **/
public class ApiUrl {
    /**编号 null**/
    private Long id;
    /**名称 null**/
    private String name;
    /**url null**/
    private String url;
    /**requestMethod null**/
    private String method;
    /**response null**/
    private String response;
    /**父id null**/
    private Long pid;
    /**概要 null**/
    private String summary;
    /**备注 null**/
    private String description;
    /**允许的请求类型 null**/
    private String consumes;
    /**响应MIME null**/
    private String produces;
    /**创建者 null**/
    private String createUser;
    /**是否废弃 null**/
    private Integer deprecated;
    /**json null**/
    private String json;
    /**创建时间 null**/
    private Timestamp createtime;
    /**更新时间 null**/
    private Timestamp updatetime;
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url=url;
    }
    public String getMethod(){
        return method;
    }
    public void setMethod(String method){
        this.method=method;
    }
    public String getResponse(){
        return response;
    }
    public void setResponse(String response){
        this.response=response;
    }
    public Long getPid(){
        return pid;
    }
    public void setPid(Long pid){
        this.pid=pid;
    }
    public String getSummary(){
        return summary;
    }
    public void setSummary(String summary){
        this.summary=summary;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public String getConsumes(){
        return consumes;
    }
    public void setConsumes(String consumes){
        this.consumes=consumes;
    }
    public String getProduces(){
        return produces;
    }
    public void setProduces(String produces){
        this.produces=produces;
    }
    public String getCreateUser(){
        return createUser;
    }
    public void setCreateUser(String createUser){
        this.createUser=createUser;
    }
    public Integer getDeprecated(){
        return deprecated;
    }
    public void setDeprecated(Integer deprecated){
        this.deprecated=deprecated;
    }
    public String getJson(){
        return json;
    }
    public void setJson(String json){
        this.json=json;
    }
    public Timestamp getCreatetime(){
        return createtime;
    }
    public void setCreatetime(Timestamp createtime){
        this.createtime=createtime;
    }
    public Timestamp getUpdatetime(){
        return updatetime;
    }
    public void setUpdatetime(Timestamp updatetime){
        this.updatetime=updatetime;
    }

}
