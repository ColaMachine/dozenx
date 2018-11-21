/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.core.auth.active.bean;
import java.sql.Timestamp;
import java.util.Date;

public class Active {
    
/**主键**/
    private Integer id;
    public Integer getId(){
        return id;
    }    public void setId(Integer id){
        this.id=id;
    }
/**类别**/
    private String type;
    public String getType(){
        return type;
    }    public void setType(String type){
        this.type=type;
    }
/**验证码**/
    private String code;
    public String getCode(){
        return code;
    }    public void setCode(String code){
        this.code=code;
    }
/**用户id**/
    private Long userId;
    public Long getUserId(){
        return userId;
    }    public void setUserId(Long userId){
        this.userId=userId;
    }
/**状态**/
    private Integer status;
    public Integer getStatus(){
        return status;
    }    public void setStatus(Integer status){
        this.status=status;
    }
/**激活时间**/
    private Date activedTime;
    public Date getActivedTime(){
        return activedTime;
    }    public void setActivedTime(Date activedTime){
        this.activedTime=activedTime;
    }
/**账号**/
    private String account;
    public String getAccount(){
        return account;
    }    public void setAccount(String account){
        this.account=account;
    }
/**创建时间**/
    private Date createTime;
    public Date getCreateTime(){
        return createTime;
    }    public void setCreateTime(Date createTime){
        this.createTime=createTime;
    }
}
