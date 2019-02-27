/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年02月27日
 * 文件说明: 
 */
package com.dozenx.web.module.pubComment.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *评论实体类
 **/
public class PubComment {
        /**主键 null**/
    private Long id;
    /**所属对象id null**/
    private Long pid;
    /**正文 null**/
    private String content;
    /**类型 null**/
    private Integer type;
    /**状态 null**/
    private Integer status;
    /**创建人 null**/
    private Long createUser;
    /**创建人姓名 null**/
    private String userName;
    /**头像 null**/
    private String face;
    /**图片 null**/
    private String pic;
    /**创建时间 null**/
    private Timestamp createtime;
    /**更新时间 null**/
    private Timestamp updatetime;
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    public Long getPid(){
        return pid;
    }
    public void setPid(Long pid){
        this.pid=pid;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content=content;
    }
    public Integer getType(){
        return type;
    }
    public void setType(Integer type){
        this.type=type;
    }
    public Integer getStatus(){
        return status;
    }
    public void setStatus(Integer status){
        this.status=status;
    }
    public Long getCreateUser(){
        return createUser;
    }
    public void setCreateUser(Long createUser){
        this.createUser=createUser;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }
    public String getFace(){
        return face;
    }
    public void setFace(String face){
        this.face=face;
    }
    public String getPic(){
        return pic;
    }
    public void setPic(String pic){
        this.pic=pic;
    }
    public Timestamp getCreatetime(){
        return createtime;
    }
    public void setCreatetime(Timestamp createtime){
        this.createtime=createtime;
    }
    public Timestamp getUpdatetime(){
        return updatetime;
    }
    public void setUpdatetime(Timestamp updatetime){
        this.updatetime=updatetime;
    }

}
