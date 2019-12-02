/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年10月25日
 * 文件说明: 
 */
package com.dozenx.web.core.auth.sysRoleMenu.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *角色菜单关系实体类
 **/
public class SysRoleMenu {
        /**主键 null**/
    private Integer id;
    /**角色id null**/
    private Integer roleId;
    /**菜单id null**/
    private Integer menuId;
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
    public Integer getMenuId(){
        return menuId;
    }
    public void setMenuId(Integer menuId){
        this.menuId=menuId;
    }

}
