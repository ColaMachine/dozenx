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
public class IfStateMent {
    //单一表达式

    //函数表达式

    //左右比对
    List<Expression> judgeCodes =new ArrayList<>();
    List<Expression>  ifBody =new ArrayList<>();

    public IfStateMent(StringBuffer content,CountableNum pos){
        String lastWord=null,word=null;

        ReadJs.readWordUntil(content,pos,'(');
        LogUtil.pringln("开始读取判断条件");
        while(true){
            if(ReadJs.isValidWord(word)){
                lastWord =word;
            }
            word =ReadJs. readOneWord(content,pos);
            //可能是一个函数的名称
            //如果后面跟的是一个(号

            if(word.equals("(") ){
                //之前的world是函数名称吗
                JsFunctionCall jsFunctionCall =new JsFunctionCall(lastWord,content,pos);
                judgeCodes.add(jsFunctionCall);
            } if(word.equals("&") ){
                //之前的world是函数名称吗
                LogUtil.pringln("还有一个判断");

            }else  if(word.equals(")")){
                break;
            }else{

            }
        }


        LogUtil.pringln("开始读取判断body");
        ReadJs.readWordUntil(content,pos,'{');
        lastWord=null;word=null;
        while(true){
            if(ReadJs.isValidWord(word)){
                lastWord =word;
            }
            word =ReadJs. readOneWord(content,pos);
            //可能是一个函数的名称
            //如果后面跟的是一个(号

            if(word.equals("(") ){
                //之前的world是函数名称吗
                JsFunctionCall jsFunctionCall =new JsFunctionCall(lastWord,content,pos);
                ifBody.add(jsFunctionCall);
            } if(word.equals("&") ){
                //之前的world是函数名称吗
                LogUtil.pringln("还有一个判断");

            }else  if(word.equals(")")){
                break;
            }else{

            }
        }
        ReadJs.readWordUntil(content,pos,'}');

    }
    public String name;
    public List<String> codes;
    public List<String> argsName=new ArrayList<>();
}
