package com.dozenx;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:08 2019/3/21
 * @Modified By:
 */
public class JsFunctionCall implements CommonCode{
   // public JsFunction jsFunction;
    public String functionName;
    public List<String> argsName=new ArrayList<>();

    public JsFunctionCall(String functionName,StringBuffer content,CountableNum pos){
        this.functionName= functionName;
       // JsFunction jsFunction = ReadJs.functions.get(functionName);
       // if(jsFunction!=null){
            //找到了
       // }else{
            //等待装备
       // }

        String lastWord="",word="";
        while (true){
            if(!word.equals(" ")){
                lastWord =word;
            }
            word = ReadJs.readOneWorrd(content,pos);
            if(word.equals(",")){
                argsName.add(lastWord);
            }else if(word.equals(")")){
                argsName.add(lastWord);
                break;
            }
        }
    }
}
