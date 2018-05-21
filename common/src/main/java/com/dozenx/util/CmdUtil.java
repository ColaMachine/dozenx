/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年3月13日
 * 文件说明:
 */
package com.dozenx.util;

import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdUtil {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CmdUtil.class);
    public static void execCommand(String commandStr,String filePath){
        try {

            File dir = new File(filePath);
            // String command="netstat -an";
            String command = "cmd /c "+commandStr;
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(command, null, dir);
            BufferedReader br = new BufferedReader(new InputStreamReader(p
                    .getInputStream()));
            StringBuffer sb = new StringBuffer();
            String inline;
            while (null != (inline = br.readLine())) {
                sb.append(inline).append("\n");
            }
            System.out.println(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void execCommand(String[] arstringCommand) {
        for (int i = 0; i < arstringCommand.length; i++) {
            System.out.print(arstringCommand[i] + " ");
        }
        try {
            Runtime.getRuntime().exec(arstringCommand);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void execCommand(String arstringCommand) {
        try {
            Runtime.getRuntime().exec(arstringCommand);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void cmd(){
        //打开记算器
        String[] arstringCommand = new String[] {
                "cmd ",
                "/k",
                "cd",
                "c:/zzw/calendar/gulp;",
                "gulp",
                "build",
        };
        execCommand(arstringCommand);
        //打开记事本
        String cmd = "cmd /k start notepad";
        execCommand(cmd);
    }

    public static void main(String[] args){

    }
}
