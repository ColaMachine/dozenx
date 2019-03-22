package com.dozenx;

import java.io.*;
import java.util.*;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 14:53 2019/3/21
 * @Modified By:
 */
public class ReadJs {
    public static  Map<String,FunctionDefinition> functions =new HashMap();

    public static Map<String,Object> variables = new HashMap<>();

    public static void main(String args[]){
        FunctionDefinition alert = new AlertFunction();
        functions.put("alert",alert);
        try {
            ReadJs readjs = new ReadJs();

            readjs.readConfigFile("ReadJs.js");
            readjs.run();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void run(){
        CodeContext codeContext =new CodeContext();
        for(Expression commonCode : codes){//排除了function定义后剩下的运行代码 分批次运行 每个代码的运行都会影响到当前上线文里的变量的定义
            commonCode.run(codeContext);
        }
    }

    public void readConfigFile(String path) throws Exception {
        StringBuffer content = new StringBuffer();
        File file = new File(path);
        try {

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            try {
                line = br.readLine();

                while (line != null) {
                    if (!line.trim().startsWith("//")) {
                        content.append(line+"\n");
                    }

                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<JsFunctionDefinition> blocks = new ArrayList<JsFunctionDefinition>();
        // String result = conf_read_token();
        CountableNum pos = new CountableNum();
        String lastWord="",word="";
        for (; ; ) {
            if(!" ".equals(word)){
                lastWord= word;
            }
             word =readOneWord(content,pos);
            if(word==null)
                break;
            if(word.equals("//")){
                jumpToLineEnd(content,pos);//直接读取到行末尾
                continue;

            }else if(word.equals("function")){
                JsFunctionDefinition jsFunction =new JsFunctionDefinition(content,pos);
                functions.put(jsFunction.name,jsFunction);

            }else if(word.equals("(")){
                JsFunctionCall jsFunctionCall =new JsFunctionCall(lastWord,content,pos);
                codes.add(jsFunctionCall);
            }

        }

        //将动作按每200ms 分割 成制定动画

    }
    public static List<Expression> codes=new ArrayList<>();

    public static String readStartUntil(char start,char end,StringBuffer content,CountableNum pos){
        String returnVal="";
        boolean begin=false;
        while(true){
            char ch = content.charAt(pos.val());
            pos.add();
            if(ch==start){
                begin=true;
            }
            if(ch==end){
               break;
            }
            if(begin){
                returnVal+=ch;
            }


        }
        return  returnVal;
    }




    public static String guanjianzi[] = new String[]{"//",",","=","function","var","(",")"," ","+","\n",";","{","}","[","]","if"};
    public static char[] guanjianchar = new char[]{',','=','(',')',' ','+','\n',';','{','}','[',']'};
    public static void readWordUntil(StringBuffer content,CountableNum pos,char ch){
        while(true){
           char car = content.charAt(pos.val());
            pos.add();
            if(car==ch){
                return;
            }
        }
    }
    public static String  readOneWord(StringBuffer content,CountableNum pos){
        String word ="";
        //2019-03-22 10:52:43需不需要判断如果是双引号的就把双引号的包括的内容都当做一个word返回给他

        //  // , = + var function ( )
        while(true){
            if(pos.val()>content.length()-1){
                return null;
            }
            char ch = content.charAt(pos.val());

            for(int i=0;i<guanjianchar.length;i++){
                if(ch==guanjianchar[i] ){
                   if(word.length()==0){
                       pos.add();
                       return ch+"";
                   }else{
                       return word;
                   }
                }
            }
            pos.add();

            word+=ch;
            for(int i=0;i<guanjianzi.length;i++){
                if(word.equals(guanjianzi[i] )){
                    return word;
                }
            }


        }

    }
    public static void jumpToLineEnd(StringBuffer content,CountableNum pos){
        int index = content.indexOf("\n",pos.val());
         pos.val(index);
    }

    public static boolean  isValidWord(String s){
        if(s==null){
            return false;
        }

        if(" ".equals(s)){
            return false;
        }
        if(s.length()==0){
            return false;
        }

        for(int i=0;i<guanjianzi.length;i++){
            if(s.equals(guanjianzi[i] )){
                return false;
            }
        }

        return true;
    }


public static void addArgsName(List<String> args ,String s) {
    if (isValidWord(s)) {
        args.add(s);
    }
}

    public static void jumpSpace(StringBuffer content,CountableNum pos){
        while (true){
            char ch = content.charAt(pos.val());
            if(ch==' '){
                pos.add();
            }else{
                break;
            }
        }
    }
}