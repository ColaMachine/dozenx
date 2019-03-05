/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年03月01日
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
    private Byte type;
    /**属于哪个模块 null**/
    private Integer category;
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
    public Byte getType(){
        return type;
    }
    public void setType(Byte type){
        this.type=type;
    }
    public Integer getCategory(){
        return category;
    }
    public void setCategory(Integer category){
        this.category=category;
    }
    public Timestamp getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }

}
