package com.dozenx;

import com.dozenx.common.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:08 2019/3/21
 * @Modified By:
 */
public class JsFunctionDefinition implements  FunctionDefinition{
    public String name;
    public List<Expression> codeBody =new ArrayList<>();
    public List<String> argsName=new ArrayList<>();
    public String returnName;
   public List<Expression> commonCodes = new ArrayList<>();

    public JsFunctionDefinition(StringBuffer content, CountableNum pos){
        String lastWord=null,word=null;
        String space =ReadJs. readOneWord(content,pos);
        if(!" ".equals(space)){
            LogUtil.err("function后面 必须有 空格");
        }
      //  assert space.equals(" ");//function 后面应该跟的是空格
        name = ReadJs.readOneWord(content,pos);
        ReadJs.readWordUntil(content,pos,'(');
        //读取参数名称
        while(true){
            if(ReadJs.isValidWord(word))
                lastWord = word;
           // word =ReadJs. readOneWorrd(content,pos);

            //可能是一个函数的名称
            //如果后面跟的是一个(号


            word = ReadJs.readOneWord(content,pos);
            if(word.equals(",")){
                ReadJs.addArgsName(argsName,lastWord);
               // argsName.add(lastWord);
            }else if(word.equals(")")){
                ReadJs.addArgsName(argsName,lastWord);

                break;
            }

        }

        ReadJs.readWordUntil(content,pos,'{');
        LogUtil.pringln("开始读取函数body");
        while(true){
            if(!" ".equals(word))
                lastWord = word;
            word =ReadJs. readOneWord(content,pos);
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
                LogUtil.pringln("函数体结束");
                break;
            }else{
               //
                LogUtil.pringln("多余的代码"+word);
            }
        }
      //  ReadJs.readWordUntil(content,pos,'}');
    }

    public Object call(List<Object> args) {
        Map<String,Object> localVariableMap =new HashMap<>();
        CodeContext codeContext =new CodeContext();
        codeContext.variables=localVariableMap;
        for(int i=0;i<argsName.size();i++){
            if(i>args.size()-1)
                break;
            localVariableMap.put(argsName.get(i),args.get(i));
        }
        for(Expression expression : codeBody){
            expression.run(codeContext);
        }

        return localVariableMap.get(returnName);//返回指定的值

    }


}
