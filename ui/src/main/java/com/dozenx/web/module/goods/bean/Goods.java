/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.goods.bean;
import java.sql.Timestamp;
import java.util.Date;

public class Goods {
    
/**编号**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }
/**商户id**/
    private Long shopId;
    public Long getShopId(){
        return shopId;
    }    public void setShopId(Long shopId){
        this.shopId=shopId;
    }
/**名称**/
    private String name;
    public String getName(){
        return name;
    }    public void setName(String name){
        this.name=name;
    }
/**副标题**/
    private String subName;
    public String getSubName(){
        return subName;
    }    public void setSubName(String subName){
        this.subName=subName;
    }
/**副标题**/
    private String detail;
    public String getDetail(){
        return detail;
    }    public void setDetail(String detail){
        this.detail=detail;
    }
/**地址**/
    private String address;
    public String getAddress(){
        return address;
    }    public void setAddress(String address){
        this.address=address;
    }
/**手机号码**/
    private String telno;
    public String getTelno(){
        return telno;
    }    public void setTelno(String telno){
        this.telno=telno;
    }
/**图片**/
    private String img;
    public String getImg(){
        return img;
    }    public void setImg(String img){
        this.img=img;
    }
/**图片**/
    private String img1;
    public String getImg1(){
        return img1;
    }    public void setImg1(String img1){
        this.img1=img1;
    }
/**图片**/
    private String img2;
    public String getImg2(){
        return img2;
    }    public void setImg2(String img2){
        this.img2=img2;
    }
/**图片**/
    private String img3;
    public String getImg3(){
        return img3;
    }    public void setImg3(String img3){
        this.img3=img3;
    }
/**备注**/
    private String remark;
    public String getRemark(){
        return remark;
    }    public void setRemark(String remark){
        this.remark=remark;
    }
/**状态**/
    private Integer status;
    public Integer getStatus(){
        return status;
    }    public void setStatus(Integer status){
        this.status=status;
    }
/**价格**/
    private Integer price;
    public Integer getPrice(){
        return price;
    }    public void setPrice(Integer price){
        this.price=price;
    }
/**创建人id**/
    private Long creator;
    public Long getCreator(){
        return creator;
    }    public void setCreator(Long creator){
        this.creator=creator;
    }
/**创建时间**/
    private Timestamp createTime;
    public Timestamp getCreateTime(){
        return createTime;
    }    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }
/**更新时间**/
    private Timestamp updateTime;
    public Timestamp getUpdateTime(){
        return updateTime;
    }    public void setUpdateTime(Timestamp updateTime){
        this.updateTime=updateTime;
    }
}
