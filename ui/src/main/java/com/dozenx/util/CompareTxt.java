package com.dozenx.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 8:53 2018/11/1
 * @Modified By:
 */
public class CompareTxt {

    public static void main(String args[]){
        try {
            List<String> finalAList =new ArrayList<>();
           List<String> aList =  FileUtil.readFile2List("G://a.txt");

            for(String a:aList){
                String[] ary = a.split(" ");
                for(int i=0;i<ary.length;i++){
                    String trim = ary[i].trim();
                    if(StringUtil.isNotBlank(trim)) {
                        System.out.println(trim);
                        finalAList.add(trim);
                    }
                }
            }


            List<String> finalBList =new ArrayList<>();
            List<String> bList =  FileUtil.readFile2List("G://b.txt");

            for(String b:bList){
                String[] ary = b.split(" ");
                for(int i=0;i<ary.length;i++){
                    String trim = ary[i].trim();
                    if(StringUtil.isNotBlank(trim)) {
                        System.out.println(trim);
                        finalBList.add(trim);
                    }
                }
            }
            //两个都消除没有的
            for(int i=finalAList.size()-1;i>=0;i--){
                String txt = finalAList.get(i);
                if(finalBList.contains(txt)){
                    finalBList.remove(txt);
                    finalAList.remove(txt);
                }

            }
            System.out.println("a多了这些");
            for(String a:finalAList){
                System.out.println(a);
            }
            System.out.println("b多了这些");
            for(String b:finalBList){
                System.out.println(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
