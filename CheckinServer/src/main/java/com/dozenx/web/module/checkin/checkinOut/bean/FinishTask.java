/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年11月7日 上午9:29:42
* 创建作者：尤小平
* 文件名称：FinishTask.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.dozenx.web.module.checkin.checkinOut.bean;


import java.math.BigDecimal;
import java.util.List;

public class FinishTask {
    /**
     * 5个关键点位置，左眼中心、右眼中心、鼻尖、左嘴角，右嘴角
     */
    private List<Landmark> landmark;
    /**
     * 人脸在图片中的位置
     */
    private Location location;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 人脸置信度，范围0-1

     */
    private Double faceProbability;
    /**
     * 特征值
     */
    private Double[] embedding;
    /**
     * 年龄
     */
    private String age;
    /**
     * 性别 M/F 男或女
     */
    private String gender;

    /**
     * 人脸图
     */
    private Image image;

    public FinishTask() {

    }

    /**
     * @return the landmark
     */
    public List<Landmark> getLandmark() {
        return landmark;
    }

    /**
     * @param landmark the landmark to set
     */
    public void setLandmark(List<Landmark> landmark) {
        this.landmark = landmark;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the faceProbability
     */
    public Double getFaceProbability() {
        return faceProbability;
    }

    /**
     * @param faceProbability the faceProbability to set
     */
    public void setFaceProbability(Double faceProbability) {
        this.faceProbability = faceProbability;
    }


    /**
     * @return the age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(String age) {
        this.age = age;
    }



    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(Image image) {
        this.image = image;
    }

    public Double[] getEmbedding() {
        return embedding;
    }

    public void setEmbedding(Double[] embedding) {
        this.embedding = embedding;
    }
}
