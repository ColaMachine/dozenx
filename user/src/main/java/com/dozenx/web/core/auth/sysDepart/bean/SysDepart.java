/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.core.auth.sysDepart.bean;
import java.sql.Timestamp;
import java.util.Date;

public class SysDepart {
    
/**编号**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }
/**部门名称**/
    private String name;
    public String getName(){
        return name;
    }    public void setName(String name){
        this.name=name;
    }
/**父级部门id**/
    private Long pid;
    public Long getPid(){
        return pid;
    }    public void setPid(Long pid){
        this.pid=pid;
    }
/**编号**/
    private String code;
    public String getCode(){
        return code;
    }    public void setCode(String code){
        this.code=code;
    }
/**状态**/
    private Integer status;
    public Integer getStatus(){
        return status;
    }    public void setStatus(Integer status){
        this.status=status;
    }
/**备注**/
    private String remark;
    public String getRemark(){
        return remark;
    }    public void setRemark(String remark){
        this.remark=remark;
    }
/**创建时间**/
    private Timestamp createTime;
    public Timestamp getCreateTime(){
        return createTime;
    }    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }
/**更新时间**/
    private Timestamp updateTime;
    public Timestamp getUpdateTime(){
        return updateTime;
    }    public void setUpdateTime(Timestamp updateTime){
        this.updateTime=updateTime;
    }
/**创建人**/
    private Long userId;
    public Long getUserId(){
        return userId;
    }    public void setUserId(Long userId){
        this.userId=userId;
    }
}
