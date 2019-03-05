/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年11月7日 上午9:31:20
* 创建作者：尤小平
* 文件名称：Location.java
* 版本：  v1.0
* 功能：位置 脸在图片中的位置
* 修改记录：
*/
package com.dozenx.web.module.checkin.checkinOut.bean;

public class Location {
    /**
     * 人脸区域离左边界的距离
     */
    private int left;
    /**
     * 人脸区域离上边界的距离
     */
    private int top;
    /**
     * 人脸区域的宽度
     */
    private int width;
    /**
     * 人脸区域的高度
     */
    private int height;

    public Location() {

    }

    /**
     * @return the left
     */
    public int getLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(int left) {
        this.left = left;
    }

    /**
     * @return the top
     */
    public int getTop() {
        return top;
    }

    /**
     * @param top the top to set
     */
    public void setTop(int top) {
        this.top = top;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }
}
