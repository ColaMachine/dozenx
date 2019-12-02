/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年10月25日
 * 文件说明: 
 */
package com.dozenx.web.core.auth.sysMenu.bean;
import java.io.Serializable;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *资源配置实体类
 **/
public class SysMenu implements Serializable {
        /**主键 null**/
    private Integer id;
    /**父菜单 null**/
    private Integer pid;
    private String SysMenu_name;
    public String getSysMenu_name(){
        return SysMenu_name;
    }
    public void setSysMenu_name(String SysMenu_name){
        this.SysMenu_name=SysMenu_name;
    }    /**菜单名称 null**/
    private String menuName;
    /**菜单代码 null**/
    private String menuCode;
    /**权限 null**/
    private String menuPermission;
    /**资源对应URL null**/
    private String menuUrl;
    /**排序id null**/
    private Byte orderNo;
    /**状态 null**/
    private Byte status;
    /**备注 null**/
    private String remark;
    /**图标 null**/
    private String icon;
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
    public String getMenuName(){
        return menuName;
    }
    public void setMenuName(String menuName){
        this.menuName=menuName;
    }
    public String getMenuCode(){
        return menuCode;
    }
    public void setMenuCode(String menuCode){
        this.menuCode=menuCode;
    }
    public String getMenuPermission(){
        return menuPermission;
    }
    public void setMenuPermission(String menuPermission){
        this.menuPermission=menuPermission;
    }
    public String getMenuUrl(){
        return menuUrl;
    }
    public void setMenuUrl(String menuUrl){
        this.menuUrl=menuUrl;
    }
    public Byte getOrderNo(){
        return orderNo;
    }
    public void setOrderNo(Byte orderNo){
        this.orderNo=orderNo;
    }
    public Byte getStatus(){
        return status;
    }
    public void setStatus(Byte status){
        this.status=status;
    }
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark){
        this.remark=remark;
    }
    public String getIcon(){
        return icon;
    }
    public void setIcon(String icon){
        this.icon=icon;
    }
    public List<SysMenu> childs;
}
