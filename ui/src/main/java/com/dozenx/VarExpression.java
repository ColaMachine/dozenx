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
public class VarExpression extends Expression {
   // public JsFunctionDefinition jsFunction;
    public Expression variableName;
    public Expression rightValue;//可能是一个 数组 字符串 数字 函数 bool null json{} object
    public List<String> argsName=new ArrayList<>();
    //解析从圆括号开始
    public VarExpression( StringBuffer content, CountableNum pos){
        //ReadJs.read
        ReadJs.jumpSpace(content,pos);
        String lastWord=null,word=null;
        while (true){
            if(ReadJs.isValidWord(word)){
                lastWord =word;
            }
            word = ReadJs.readOneWord(content,pos);
            if(ReadJs.isValidWord(word)){
                variableName = word;
            }
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
