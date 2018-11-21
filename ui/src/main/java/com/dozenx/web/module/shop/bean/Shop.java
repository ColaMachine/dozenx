/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.shop.bean;
import java.sql.Timestamp;
import java.util.Date;

public class Shop {
    
/**编号**/
    private Integer id;
    public Integer getId(){
        return id;
    }    public void setId(Integer id){
        this.id=id;
    }
/**名称**/
    private String name;
    public String getName(){
        return name;
    }    public void setName(String name){
        this.name=name;
    }
/**地址**/
    private String address;
    public String getAddress(){
        return address;
    }    public void setAddress(String address){
        this.address=address;
    }
/**手机号码**/
    private String telno;
    public String getTelno(){
        return telno;
    }    public void setTelno(String telno){
        this.telno=telno;
    }
/**备注**/
    private String remark;
    public String getRemark(){
        return remark;
    }    public void setRemark(String remark){
        this.remark=remark;
    }
/**创建人id**/
    private Long creator;
    public Long getCreator(){
        return creator;
    }    public void setCreator(Long creator){
        this.creator=creator;
    }
/**创建时间**/
    private Timestamp createTime;
    public Timestamp getCreateTime(){
        return createTime;
    }    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }
/**状态**/
    private Integer status;
    public Integer getStatus(){
        return status;
    }    public void setStatus(Integer status){
        this.status=status;
    }
/**更新时间**/
    private Timestamp updateTime;
    public Timestamp getUpdateTime(){
        return updateTime;
    }    public void setUpdateTime(Timestamp updateTime){
        this.updateTime=updateTime;
    }
}
