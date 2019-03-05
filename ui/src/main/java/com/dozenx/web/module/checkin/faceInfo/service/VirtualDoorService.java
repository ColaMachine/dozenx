package com.dozenx.web.module.checkin.faceInfo.service;

import com.dozenx.util.CmdUtil;
import com.dozenx.web.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:35 2018/11/19
 * @Modified By:
 */
public class VirtualDoorService {
    private static Logger logger = LoggerFactory.getLogger(VirtualDoorService.class);
    static Long lastOpenTime = System.currentTimeMillis();
    static int minInterval = 7 * 1000;

    public static void open(int port) {
        if (System.currentTimeMillis() - lastOpenTime < minInterval) {
            return;
        }


        logger.info("正在开门");
        try {
            DatagramSocket datagramSocket = new DatagramSocket();

            //准备数据，把数据封装到数据包中。
            String data = "opendoor";
            //创建了一个数据包
            DatagramPacket packet = new DatagramPacket(data.getBytes(), 0, data.getBytes().length, new InetSocketAddress("127.0.0.1", port));
            //调用udp的服务发送数据包
            datagramSocket.send(packet);
            //关闭资源 ---实际上就是释放占用的端口号
            datagramSocket.close();
            System.out.println("open door");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String cmd = "./getpsdata";
//        try {
//            CmdUtil.execCommand(cmd, ConfigUtil.getConfig("door"));//"/data/service/tomcat-kq-8087/door_control"
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String args[]) {
        //建立udp的服务
        try {
            DatagramSocket datagramSocket = new DatagramSocket();

            //准备数据，把数据封装到数据包中。
            String data = "opendoor";
            //创建了一个数据包
            DatagramPacket packet = new DatagramPacket(data.getBytes(), 0, data.getBytes().length, new InetSocketAddress("127.0.0.1", 1324));
            //调用udp的服务发送数据包
            datagramSocket.send(packet);
            //关闭资源 ---实际上就是释放占用的端口号
            datagramSocket.close();
            System.out.println("open door");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}