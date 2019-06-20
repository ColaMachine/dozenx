/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年05月26日
 * 文件说明: 
 */
package com.dozenx.web.module.yaoguai.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *妖怪商品实体类
 **/
public class Yaoguai {
        /**编号 null**/
    private Long id;
    /**名称 null**/
    private String name;
    /**地址 null**/
    private String address;
    /**图片0 null**/
    private String img;
    /**备注 null**/
    private String remark;
    /**状态 null**/
    private Integer status;
    /**价格 null**/
    private BigDecimal price;
    /**创建人id null**/
    private Long creator;
    /**创建人姓名 null**/
    private String creatorName;
    /**平台名称 qq|wx**/
    private String platform;
    /**顶 null**/
    private Integer up;
    /**物攻 null**/
    private Integer wg;
    /**物防 null**/
    private Integer wf;
    /**法攻 null**/
    private Integer fg;
    /**法防 null**/
    private Integer ff;
    /**生命 null**/
    private Integer sm;
    /**总分 null**/
    private Integer zf;
    /**创建时间 null**/
    private Timestamp createTime;
    /**更新时间 null**/
    private Timestamp updateTime;
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public String getImg(){
        return img;
    }
    public void setImg(String img){
        this.img=img;
    }
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark){
        this.remark=remark;
    }
    public Integer getStatus(){
        return status;
    }
    public void setStatus(Integer status){
        this.status=status;
    }
    public BigDecimal getPrice(){
        return price;
    }
    public void setPrice(BigDecimal price){
        this.price=price;
    }
    public Long getCreator(){
        return creator;
    }
    public void setCreator(Long creator){
        this.creator=creator;
    }
    public String getCreatorName(){
        return creatorName;
    }
    public void setCreatorName(String creatorName){
        this.creatorName=creatorName;
    }
    public String getPlatform(){
        return platform;
    }
    public void setPlatform(String platform){
        this.platform=platform;
    }
    public Integer getUp(){
        return up;
    }
    public void setUp(Integer up){
        this.up=up;
    }
    public Integer getWg(){
        return wg;
    }
    public void setWg(Integer wg){
        this.wg=wg;
    }
    public Integer getWf(){
        return wf;
    }
    public void setWf(Integer wf){
        this.wf=wf;
    }
    public Integer getFg(){
        return fg;
    }
    public void setFg(Integer fg){
        this.fg=fg;
    }
    public Integer getFf(){
        return ff;
    }
    public void setFf(Integer ff){
        this.ff=ff;
    }
    public Integer getSm(){
        return sm;
    }
    public void setSm(Integer sm){
        this.sm=sm;
    }
    public Integer getZf(){
        return zf;
    }
    public void setZf(Integer zf){
        this.zf=zf;
    }
    public Timestamp getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }
    public Timestamp getUpdateTime(){
        return updateTime;
    }
    public void setUpdateTime(Timestamp updateTime){
        this.updateTime=updateTime;
    }

}
