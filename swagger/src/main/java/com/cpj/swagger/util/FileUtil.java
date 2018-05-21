/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年3月13日
 * 文件说明:
 */
package com.cpj.swagger.util;




import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<File> listFile(File file ){
        List<File > fileList =new ArrayList<File>();
        File[] fileAry = file.listFiles();
        for(File childFile : fileAry){
            if(childFile.isDirectory()){
                fileList.addAll(listFile(childFile));
            }else{
                fileList.add(childFile);
            }
        }
        return fileList;
    }
    public static String readFile2Str(String path) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            return null;
           // LogUtil.println("not exsits "+path);

        }
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String s;
        StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            templateStr.append(s + "\r\n");
        }
        if(templateStr==null || templateStr.toString().length()==0){
            //LogUtil.println("file is empty: "+path);
            return "";
        }
//        LogUtil.println(templateStr.toString());
        return templateStr.toString();
    }
    public static String readFile2Str(File file) throws IOException {

        if(!file.exists()){

            return null;
           // LogUtil.println("not exsits "+file);

        }
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String s;
        StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            templateStr.append(s + "\r\n");
        }
        if(templateStr==null || templateStr.toString().length()==0){
           // LogUtil.println("file is empty: "+file);
            return "";
        }
//        LogUtil.println(templateStr.toString());
        return templateStr.toString();
    }

    public static void writeFile(String filePath, String content) throws IOException {
        FileWriter fileWritter = null;
        File file =new File(filePath);
        // BufferedWriter bufferWritter=null;
        try {
            //if file doesnt exists, then create it
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            //true = append file
            fileWritter = new FileWriter(file, false);
            fileWritter.write(content);
            //  bufferWritter = new BufferedWriter(fileWritter);
            //bufferWritter.write(content);
            System.out.println("Done");
        } catch (IOException e) {
            System.out.println(file.getAbsolutePath().toString());
            e.printStackTrace();
            throw e;
        } finally {
           /* bufferWritter.flush();
            if(bufferWritter!=null )
                bufferWritter.close();*/

            fileWritter.flush();
            if (fileWritter != null)
                fileWritter.close();

        }
    }
    public static void writeFile(File file ,String content) throws IOException {
        try {

            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            //true = append file
            FileWriter fileWritter = new FileWriter(file, false);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(content);
            bufferWritter.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public static void main(String args[]){
        try {
            FileReader fr = new FileReader("G://V2.2.7.txt");
            BufferedReader br = new BufferedReader(fr);
            String s;
            String name="";
            StringBuffer result =new StringBuffer();
            while ((s = br.readLine()) != null) {
                // System.out.println(s);
                //System.out.println(s.split("\\s+").length);
                String arr[] = s.split("\\s+");
                System.out.println(arr[0]);
                if(name.equals("")){
                    name=arr[arr.length-1];
                }else if(arr[arr.length-1].trim().equals(name.trim())){

                    result.append("'").append(arr[0]).append("',");

                }else{

                    // System.out.println(name+"select distinct  IPAddress from wii_device_ssid where deviceid in(select id from wii_device where DevId in ("+result.toString()+"))");
                    result=new StringBuffer();
                    name=arr[arr.length-1];
                }
                if(arr.length==3){

                }

            }
            // System.out.println(name+"select distinct  IPAddress from wii_device_ssid where id in(select id from wii_device where DevId in ("+result.toString()+")");
            fr.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static List<String > readFile2List(String path) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            return null;
            //LogUtil.println("read file failed path:"+path);
        }
       // LogUtil.println("read file from1 path:"+path);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        //  BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lines =new ArrayList();
        String s ;
        // StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            lines.add(s);
            // templateStr.append(s + "\r\n");
        }
        return lines;
    }

    public static List<String > readFile2List(File file ) throws IOException {

        if(!file.exists()){
            //LogUtil.println("read file failed path:"+file.getPath());
            return null;
        }
        //LogUtil.println("read file from1 path:"+file.getPath());
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        //  BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lines =new ArrayList();
        String s ;
        // StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            lines.add(s);
            // templateStr.append(s + "\r\n");
        }
        return lines;
    }
}
