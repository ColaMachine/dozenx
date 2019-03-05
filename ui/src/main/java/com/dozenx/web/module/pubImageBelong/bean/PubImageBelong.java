/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年02月24日
 * 文件说明: 
 */
package com.dozenx.web.module.pubImageBelong.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *用户实体类
 **/
public class PubImageBelong {
        /**编号 null**/
    private Integer id;
    /**url null**/
    private String url;
    /**上传照片人的Id null**/
    private String creator;
    /**上传人的姓名 null**/
    private String creatorName;
    /**上传照片的时间 null**/
    private Date createDate;
    /**照片的状态 0 使用状态 1 移除状态 9 彻底删除状态 null**/
    private Integer status;
    /**顺序id null**/
    private Integer order;
    /**父组件id null**/
    private Integer pid;
    /**图片id null**/
    private Integer pubImageId;
    /**类别 null**/
    private String type;
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url=url;
    }
    public String getCreator(){
        return creator;
    }
    public void setCreator(String creator){
        this.creator=creator;
    }
    public String getCreatorName(){
        return creatorName;
    }
    public void setCreatorName(String creatorName){
        this.creatorName=creatorName;
    }
    public Date getCreateDate(){
        return createDate;
    }
    public void setCreateDate(Date createDate){
        this.createDate=createDate;
    }
    public Integer getStatus(){
        return status;
    }
    public void setStatus(Integer status){
        this.status=status;
    }
    public Integer getOrder(){
        return order;
    }
    public void setOrder(Integer order){
        this.order=order;
    }
    public Integer getPid(){
        return pid;
    }
    public void setPid(Integer pid){
        this.pid=pid;
    }
    public Integer getPubImageId(){
        return pubImageId;
    }
    public void setPubImageId(Integer pubImageId){
        this.pubImageId=pubImageId;
    }
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type=type;
    }


    public boolean find =false;

}
