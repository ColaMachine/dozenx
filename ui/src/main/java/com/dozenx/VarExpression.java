package com.dozenx;

import com.dozenx.common.util.StringUtil;

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
    public String variableName;
    public Expression rightValue;//可能是一个 数组 字符串 数字 函数 bool null json{} object
    public List<String> argsName=new ArrayList<>();
    //解析从圆括号开始
    public VarExpression( StringBuffer content, CountableNum pos){

    }

    @Override
    public void run(CodeContext codeContext) {

    }
}
