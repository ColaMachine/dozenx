/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.spider.sight.url.sightUrl.bean;
import java.sql.Timestamp;
import java.util.Date;

public class SightUrl {
    
/**编号**/
    private Integer id;
    public Integer getId(){
        return id;
    }    public void setId(Integer id){
        this.id=id;
    }
/**景区名称**/
    private String name;
    public String getName(){
        return name;
    }    public void setName(String name){
        this.name=name;
    }
/**url**/
    private String url;
    public String getUrl(){
        return url;
    }    public void setUrl(String url){
        this.url=url;
    }
/**平台**/
    private String platform;
    public String getPlatform(){
        return platform;
    }    public void setPlatform(String platform){
        this.platform=platform;
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
/**创建时间**/
    private Timestamp createTime;
    public Timestamp getCreateTime(){
        return createTime;
    }    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }
/**更新时间**/
    private Timestamp updatetime;
    public Timestamp getUpdatetime(){
        return updatetime;
    }    public void setUpdatetime(Timestamp updatetime){
        this.updatetime=updatetime;
    }
/**外部id**/
    private Integer outId;
    public Integer getOutId(){
        return outId;
    }    public void setOutId(Integer outId){
        this.outId=outId;
    }
}
