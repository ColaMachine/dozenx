/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年10月25日
 * 文件说明: 
 */
package com.dozenx.web.core.auth.sysPermission.bean;
import java.io.Serializable;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *权限配置实体类
 **/
public class SysPermission  implements Serializable {
        /**编号 null**/
    private Integer id;
    /**父主键 null**/
    private Integer pid;
    /**权限名称 null**/
    private String permissionName;
    /**权限代码 null**/
    private String permissionCode;
    /**权限url null**/
    private String permissionUrl;
    /**排序id null**/
    private Integer orderNo;
    /**状态 null**/
    private Integer status;
    /**备注 null**/
    private String remark;
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
    public String getPermissionName(){
        return permissionName;
    }
    public void setPermissionName(String permissionName){
        this.permissionName=permissionName;
    }
    public String getPermissionCode(){
        return permissionCode;
    }
    public void setPermissionCode(String permissionCode){
        this.permissionCode=permissionCode;
    }
    public String getPermissionUrl(){
        return permissionUrl;
    }
    public void setPermissionUrl(String permissionUrl){
        this.permissionUrl=permissionUrl;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
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

    public List<SysPermission> childs ;

    public List<SysPermission> getChilds() {
        return childs;
    }

    public void setChilds(List<SysPermission> childs) {
        this.childs = childs;
    }

    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
