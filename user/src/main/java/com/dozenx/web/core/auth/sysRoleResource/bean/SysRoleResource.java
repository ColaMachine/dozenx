/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2020年08月07日
 * 文件说明: 
 */
package com.dozenx.web.core.auth.sysRoleResource.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *角色资源关系实体类
 **/
public class SysRoleResource {
        /**主键 null**/
    private Integer id;
    /**角色id null**/
    private Integer roleId;
    /**资源id null**/
    private Integer resourceId;
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }
    public Integer getRoleId(){
        return roleId;
    }
    public void setRoleId(Integer roleId){
        this.roleId=roleId;
    }
    public Integer getResourceId(){
        return resourceId;
    }
    public void setResourceId(Integer resourceId){
        this.resourceId=resourceId;
    }

}
