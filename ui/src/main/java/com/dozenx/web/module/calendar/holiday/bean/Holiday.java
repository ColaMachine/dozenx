/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.calendar.holiday.bean;
import java.sql.Timestamp;
import java.util.Date;

public class Holiday {
    
/**编号**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }
/**日期**/
    private Date date;
    public Date getDate(){
        return date;
    }    public void setDate(Date date){
        this.date=date;
    }
/**是否是节假日**/
    private Integer type;
    public Integer getType(){
        return type;
    }    public void setType(Integer type){
        this.type=type;
    }
/**备注**/
    private String remark;
    public String getRemark(){
        return remark;
    }    public void setRemark(String remark){
        this.remark=remark;
    }
/**节日名称**/
    private String name;
    public String getName(){
        return name;
    }    public void setName(String name){
        this.name=name;
    }
/**农历**/
    private String nl;
    public String getNl(){
        return nl;
    }    public void setNl(String nl){
        this.nl=nl;
    }
/**阴历**/
    private String yl;
    public String getYl(){
        return yl;
    }    public void setYl(String yl){
        this.yl=yl;
    }
}
