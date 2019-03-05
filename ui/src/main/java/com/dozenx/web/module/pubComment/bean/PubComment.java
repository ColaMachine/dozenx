/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.pubComment.bean;
import java.sql.Timestamp;

public class PubComment {
    
/**主键**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }
/**所属文章id**/
    private Long pid;
    public Long getPid(){
        return pid;
    }    public void setPid(Long pid){
        this.pid=pid;
    }
/**正文**/
    private String content;
    public String getContent(){
        return content;
    }    public void setContent(String content){
        this.content=content;
    }
/**类型**/
    private Integer type;
    public Integer getType(){
        return type;
    }    public void setType(Integer type){
        this.type=type;
    }
/**状态**/
    private Integer status;
    public Integer getStatus(){
        return status;
    }    public void setStatus(Integer status){
        this.status=status;
    }
/**创建人**/
    private Long createUser;
    public Long getCreateUser(){
        return createUser;
    }    public void setCreateUser(Long createUser){
        this.createUser=createUser;
    }
/**创建人姓名**/
    private String userName;
    public String getUserName(){
        return userName;
    }    public void setUserName(String userName){
        this.userName=userName;
    }
/**头像**/
    private String face;
    public String getFace(){
        return face;
    }    public void setFace(String face){
        this.face=face;
    }
/**创建时间**/
    private Timestamp createtime;
    public Timestamp getCreatetime(){
        return createtime;
    }    public void setCreatetime(Timestamp createtime){
        this.createtime=createtime;
    }
/**更新时间**/
    private Timestamp updatetime;
    public Timestamp getUpdatetime(){
        return updatetime;
    }    public void setUpdatetime(Timestamp updatetime){
        this.updatetime=updatetime;
    }

}
