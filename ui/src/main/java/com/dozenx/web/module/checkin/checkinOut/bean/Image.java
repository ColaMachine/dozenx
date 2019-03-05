/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年11月7日 上午9:57:47
* 创建作者：尤小平
* 文件名称：Image.java
* 版本：  v1.0
* 功能：人脸图
* 修改记录：
*/
package com.dozenx.web.module.checkin.checkinOut.bean;

public class Image {
    private String key;
    private int width;
    private int height;

    public Image() {
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
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
