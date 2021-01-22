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
import java.util.ArrayList;
import java.util.List;

/**
 *资源配置实体类
 **/
public class SimpleSysResource implements Serializable {
        /**编号 null**/
    private Integer id;
    private Integer pid;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    private String name;

    /**资源分类 null**/
    private String type;
    /**资源对应URL null**/
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public List<SimpleSysResource> getChilds() {
        return childs;
    }

    public void setChilds(List<SimpleSysResource> childs) {
        this.childs = childs;
    }

    public List<SimpleSysResource> childs =new ArrayList<>();

    public String getMenuUrl(){
        return this.url;
    }
}
