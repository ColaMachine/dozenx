package com.dozenx;

import com.dozenx.util.CompareTxt;
import com.dozenx.util.FileUtil;
import com.dozenx.util.LogUtil;

import java.io.*;
import java.util.*;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 14:53 2019/3/21
 * @Modified By:
 */
public class ReadJs {
    public static  Map<String,JsFunction> functions =new HashMap();
    public static void main(){
        try {
            ReadJs readjs =
          new ReadJs();
            readjs.readConfigFile("ReadJs.js");
            readjs.run();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void run(){
        for(CommonCode commonCode : codes){
            commonCode.run();
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

        List<JsFunction> blocks = new ArrayList<JsFunction>();
        // String result = conf_read_token();
        CountableNum pos = new CountableNum();
        String lastWord="",word="";
        for (; ; ) {
            if(word.equals(" ")){
                lastWord= word;
            }
             word =readOneWorrd(content,pos);

            if(word.equals("//")){
                jumpToLineEnd(content,pos);//直接读取到行末尾
                continue;

            }else if(word.equals("function")){
                JsFunction jsFunction =new JsFunction(content,pos);
                functions.put(jsFunction.name,jsFunction);

            }else if(word.equals("(")){
                JsFunctionCall jsFunctionCall =new JsFunctionCall(lastWord,content,pos);
                codes.add(jsFunctionCall);
            }

        }

        //将动作按每200ms 分割 成制定动画

    }
    public static List<CommonCode> codes=new ArrayList<>();

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
    public static String  readOneWorrd(StringBuffer content,CountableNum pos){
        String word ="";

        //  // , = + var function ( )
        while(true){
            char ch = content.charAt(pos.val());

            for(int i=0;i<guanjianchar.length;i++){
                if(ch!=guanjianchar[i] ){
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
}
