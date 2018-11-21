/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.core.auth.sysUserDepart.bean;
import java.sql.Timestamp;
import java.util.Date;

public class SysUserDepart {
    
/**主键**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }
/**用户id**/
    private Long userId;
    public Long getUserId(){
        return userId;
    }    public void setUserId(Long userId){
        this.userId=userId;
    }
/**角色id**/
    private Long departId;
    public Long getDepartId(){
        return departId;
    }    public void setDepartId(Long departId){
        this.departId=departId;
    }
}
