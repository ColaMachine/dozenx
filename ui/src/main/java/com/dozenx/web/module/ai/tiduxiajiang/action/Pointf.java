package com.dozenx.web.module.ai.tiduxiajiang.action;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:26 2018/11/29
 * @Modified By:
 */
public class Pointf {

    public float x;
    public float y;
    public Pointf(float x,float y){
        this.x=x;
        this.y=y;
    }

    public String toString(){
        return "x:"+x+" y:"+y;
    }
}
