/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年11月7日 上午9:28:33
* 创建作者：尤小平
* 文件名称：Landmark.java
* 版本：  v1.0
* 功能：5个关键点位置，左眼中心、右眼中心、鼻尖、左嘴角，右嘴角。
* 修改记录：
*/
package com.dozenx.web.module.checkin.checkinOut.bean;

public class Landmark {
    /**
     * x坐标
     */
    private Double x;
    /**
     * y坐标
     */
    private Double y;

    public Landmark() {

    }

    /**
     * @return the x
     */
    public Double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(Double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public Double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(Double y) {
        this.y = y;
    }
}
