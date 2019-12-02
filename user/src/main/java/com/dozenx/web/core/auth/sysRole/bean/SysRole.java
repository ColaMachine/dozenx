/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年10月25日
 * 文件说明: 
 */
package com.dozenx.web.core.auth.sysRole.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *角色实体类
 **/
public class SysRole {
        /**编号 null**/
    private Integer id;
    /**角色名 null**/
    private String roleName;
    /**角色代码 null**/
    private String roleCode;
    /**排序 null**/
    private Integer orderNo;
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
    public String getRoleName(){
        return roleName;
    }
    public void setRoleName(String roleName){
        this.roleName=roleName;
    }
    public String getRoleCode(){
        return roleCode;
    }
    public void setRoleCode(String roleCode){
        this.roleCode=roleCode;
    }
    public Integer getOrderNo(){
        return orderNo;
    }
    public void setOrderNo(Integer orderNo){
        this.orderNo=orderNo;
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

}
