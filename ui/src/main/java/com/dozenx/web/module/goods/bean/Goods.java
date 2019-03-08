/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年03月06日
 * 文件说明:
 */
package com.dozenx.web.module.goods.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *商品实体类
 **/
public class Goods {
    /**编号 null**/
    private Long id;
    /**商户id null**/
    private Long shopId;
    /**名称 null**/
    private String name;
    /**副标题 null**/
    private String subName;
    /**详细内容 null**/
    private String detail;
    /**地址 null**/
    private String address;
    /**手机号码 null**/
    private String telno;
    /**图片0 null**/
    private String img;
    /**图片1 null**/
    private String img1;
    /**图片2 null**/
    private String img2;
    /**图片3 null**/
    private String img3;
    /**备注 null**/
    private String remark;
    /**状态 null**/
    private Integer status;
    /**价格 null**/
    private BigDecimal price;
    /**标签 逗号分割的标签**/
    private String tags;
    /**价格描述 null**/
    private String priceDesc;
    /**创建人id null**/
    private Long creator;
    /**创建人姓名 null**/
    private String creatorName;
    /**平台名称 淘宝|京东**/
    private String platform;
    /**评论数 评论数**/
    private Integer comments;
    /**分数 分数**/
    private Float score;
    /**外链 跳转url**/
    private String link;
    /**顶 null**/
    private Integer up;
    /**踩 null**/
    private Integer down;
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
    public Long getShopId(){
        return shopId;
    }
    public void setShopId(Long shopId){
        this.shopId=shopId;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getSubName(){
        return subName;
    }
    public void setSubName(String subName){
        this.subName=subName;
    }
    public String getDetail(){
        return detail;
    }
    public void setDetail(String detail){
        this.detail=detail;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public String getTelno(){
        return telno;
    }
    public void setTelno(String telno){
        this.telno=telno;
    }
    public String getImg(){
        return img;
    }
    public void setImg(String img){
        this.img=img;
    }
    public String getImg1(){
        return img1;
    }
    public void setImg1(String img1){
        this.img1=img1;
    }
    public String getImg2(){
        return img2;
    }
    public void setImg2(String img2){
        this.img2=img2;
    }
    public String getImg3(){
        return img3;
    }
    public void setImg3(String img3){
        this.img3=img3;
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
    public String getTags(){
        return tags;
    }
    public void setTags(String tags){
        this.tags=tags;
    }
    public String getPriceDesc(){
        return priceDesc;
    }
    public void setPriceDesc(String priceDesc){
        this.priceDesc=priceDesc;
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
    public Integer getComments(){
        return comments;
    }
    public void setComments(Integer comments){
        this.comments=comments;
    }
    public Float getScore(){
        return score;
    }
    public void setScore(Float score){
        this.score=score;
    }
    public String getLink(){
        return link;
    }
    public void setLink(String link){
        this.link=link;
    }
    public Integer getUp(){
        return up;
    }
    public void setUp(Integer up){
        this.up=up;
    }
    public Integer getDown(){
        return down;
    }
    public void setDown(Integer down){
        this.down=down;
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
