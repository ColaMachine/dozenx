/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年02月17日
 * 文件说明: 
 */
package com.dozenx.web.module.zan.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *赞实体类
 **/
public class Zan {
        /**编号 null**/
    private Long id;
    /**商户id null**/
    private Long pid;
    /**用户id null**/
    private Long userId;
    /**类型 null**/
    private Integer type;
    /**创建时间 null**/
    private Timestamp createTime;
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
    public Long getUserId(){
        return userId;
    }
    public void setUserId(Long userId){
        this.userId=userId;
    }
    public Integer getType(){
        return type;
    }
    public void setType(Integer type){
        this.type=type;
    }
    public Timestamp getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }

}
