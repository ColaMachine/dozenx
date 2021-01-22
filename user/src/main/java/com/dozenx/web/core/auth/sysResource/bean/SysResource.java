/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2020年08月07日
 * 文件说明: 
 */
package com.dozenx.web.core.auth.sysResource.bean;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *资源配置实体类
 **/
public class SysResource implements Serializable {
        /**编号 null**/
    private Integer id;
    /**父主键 null**/
    private Integer pid;
    /*private String SysResource_name;
    public String getSysResource_name(){
        return SysResource_name;
    }
    public void setSysResource_name(String SysResource_name){
        this.SysResource_name=SysResource_name;
    }    *//**资源名称 null**/
    @JsonProperty("permission_name")
    private String name;
    /**资源代码 null**/
    private String code;
    /**资源分类 null**/
    private String type;
    /**资源对应URL null**/
    private String url;
    /**排序id null**/
    private Integer order;
    /**状态 null**/
    private Integer status;
    /**备注 null**/
    private String remark;
    /**创建时间 null**/
    private Timestamp createTime;
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }
    public Integer getPid(){
        return pid;
    }
    public void setPid(Integer pid){
        this.pid=pid;
    }
//    @JsonProperty("permission_name")
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getCode(){
        return code;
    }
    public void setCode(String code){
        this.code=code;
    }
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type=type;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url=url;
    }
    public Integer getOrder(){
        return order;
    }
    public void setOrder(Integer order){
        this.order=order;
    }
    public Integer getStatus(){
        return status;
    }
    public void setStatus(Integer status){
        this.status=status;
    }
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark){
        this.remark=remark;
    }
    public Timestamp getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }

    public String getPermissionName(){
        return name;
    }
    public String getMenuName(){
        return name;
    }
    public boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<SysResource> getChilds() {
        return childs;
    }

    public void setChilds(List<SysResource> childs) {
        this.childs = childs;
    }

    public List<SysResource> childs =new ArrayList<>();

    public String getMenuUrl(){
        return this.url;
    }
}
