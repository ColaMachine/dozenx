package com.dozenx;

import com.dozenx.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:08 2019/3/21
 * @Modified By:
 */
public class JsFunction implements CommonCode{
    public String name;
    public List<CommonCode> codeBody =new ArrayList<>();
    public List<String> argsName=new ArrayList<>();

   public List<CommonCode> commonCodes = new ArrayList<>();

    public JsFunction (StringBuffer content,CountableNum pos){
        String lastWord="",word="";
        assert ReadJs. readOneWorrd(content,pos).equals(" ");//function 后面应该跟的是空格
        name = ReadJs.readOneWorrd(content,pos);
        //读取参数名称
        while(true){
            if(word!=" ")
                lastWord = word;
            word =ReadJs. readOneWorrd(content,pos);
            //可能是一个函数的名称
            //如果后面跟的是一个(号


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

        ReadJs.readWordUntil(content,pos,'{');
        LogUtil.pringln("开始读取函数body");
        while(true){
            if(word!=" ")
                lastWord = word;
            word =ReadJs. readOneWorrd(content,pos);
            //可能是一个函数的名称
            //如果后面跟的是一个(号

            if(word.equals("(") ){
                //之前的world是函数名称吗
                JsFunctionCall jsFunctionCall =new JsFunctionCall(lastWord,content,pos);
                codeBody.add(jsFunctionCall);
            } if(word.equals(";") ){
                //之前的world是函数名称吗
                LogUtil.pringln("下一行代码");

            }else  if(word.equals("}")){
                break;
            }else{
               //
                LogUtil.pringln("多余的代码"+word);
            }
        }
        ReadJs.readWordUntil(content,pos,'}');
    }
}
