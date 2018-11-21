/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.place.myPlace.bean;
import java.sql.Timestamp;
import java.util.Date;

public class MyPlace {
    
/**编号**/
    private Integer id;
    public Integer getId(){
        return id;
    }    public void setId(Integer id){
        this.id=id;
    }
/**名称**/
    private String name;
    public String getName(){
        return name;
    }    public void setName(String name){
        this.name=name;
    }
/**编号**/
    private String code;
    public String getCode(){
        return code;
    }    public void setCode(String code){
        this.code=code;
    }
/**省**/
    private Integer province;
    public Integer getProvince(){
        return province;
    }    public void setProvince(Integer province){
        this.province=province;
    }
/**市**/
    private Integer city;
    public Integer getCity(){
        return city;
    }    public void setCity(Integer city){
        this.city=city;
    }
/**区**/
    private Integer county;
    public Integer getCounty(){
        return county;
    }    public void setCounty(Integer county){
        this.county=county;
    }
/**价格**/
    private Float price;
    public Float getPrice(){
        return price;
    }    public void setPrice(Float price){
        this.price=price;
    }
/**详细地址**/
    private String address;
    public String getAddress(){
        return address;
    }    public void setAddress(String address){
        this.address=address;
    }
/**封面**/
    private String cover;
    public String getCover(){
        return cover;
    }    public void setCover(String cover){
        this.cover=cover;
    }
/**图片**/
    private String pic;
    public String getPic(){
        return pic;
    }    public void setPic(String pic){
        this.pic=pic;
    }
/**创建日期**/
    private Date createTime;
    public Date getCreateTime(){
        return createTime;
    }    public void setCreateTime(Date createTime){
        this.createTime=createTime;
    }
/**更新日期**/
    private Date updateTime;
    public Date getUpdateTime(){
        return updateTime;
    }    public void setUpdateTime(Date updateTime){
        this.updateTime=updateTime;
    }
/**联系电话**/
    private String telno;
    public String getTelno(){
        return telno;
    }    public void setTelno(String telno){
        this.telno=telno;
    }
/**创建人**/
    private Long createUser;
    public Long getCreateUser(){
        return createUser;
    }    public void setCreateUser(Long createUser){
        this.createUser=createUser;
    }
/**经度**/
    private Float lng;
    public Float getLng(){
        return lng;
    }    public void setLng(Float lng){
        this.lng=lng;
    }
/**纬度**/
    private Float lat;
    public Float getLat(){
        return lat;
    }    public void setLat(Float lat){
        this.lat=lat;
    }
/**评论**/
    private Integer comments;
    public Integer getComments(){
        return comments;
    }    public void setComments(Integer comments){
        this.comments=comments;
    }
/**评分**/
    private Float score;
    public Float getScore(){
        return score;
    }    public void setScore(Float score){
        this.score=score;
    }
/**标签**/
    private String lable;
    public String getLable(){
        return lable;
    }    public void setLable(String lable){
        this.lable=lable;
    }
/**备注**/
    private String remark;
    public String getRemark(){
        return remark;
    }    public void setRemark(String remark){
        this.remark=remark;
    }
}
