package com.dozenx;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:25 2019/3/21
 * @Modified By:
 */
public class CountableNum {
    int i=0;
    public void add(){
        i++;
    }
    public void val(int i){
        this.i =i;
    }
    public int val(){
        return i;
    }
}
