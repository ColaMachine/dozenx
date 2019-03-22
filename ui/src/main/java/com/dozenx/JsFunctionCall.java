package com.dozenx;

import com.dozenx.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:08 2019/3/21
 * @Modified By:
 */
public class JsFunctionCall extends Expression {
   // public JsFunctionDefinition jsFunction;
    public String functionName;
    public List<String> argsName=new ArrayList<>();
    //解析从圆括号开始
    public JsFunctionCall(String functionName,StringBuffer content,CountableNum pos){
        this.functionName= functionName;
       // JsFunctionDefinition jsFunction = ReadJs.functions.get(functionName);
       // if(jsFunction!=null){
            //找到了
       // }else{
            //等待装备
       // }

        String lastWord=null,word=null;
        while (true){
            if(ReadJs.isValidWord(word)){
                lastWord =word;
            }
            word = ReadJs.readOneWord(content,pos);
            if(word.equals(",")){
                argsName.add(lastWord);
            }else if(word.equals(")")){
                argsName.add(lastWord);
                break;
            }
        }
    }

    @Override
    public void run(CodeContext codeContext) {
        List<Object> list =new ArrayList<>();
        for(String arg:argsName){
            if(StringUtil.checkNumeric(arg)){
                list.add(arg);
            }else if(arg.startsWith("\"") || arg.startsWith("\'") ){
                list.add(arg.replaceAll("\"","").replaceAll("\'",""));
            }else{
                list.add(codeContext.variables.get(arg));
            }
        }
        ReadJs.functions.get(functionName).call(list);
    }
}
