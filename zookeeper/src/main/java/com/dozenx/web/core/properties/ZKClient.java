package com.dozenx.web.core.properties;


import org.apache.zookeeper.ZooKeeper;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dozen.zhang on 2017/1/10.
 */
public class ZKClient  {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(ZKClient.class);
    public static int ZK_SESSION_TIMEOUT = 55000;

    public static String ZK_REGISTRY_PATH = "/registry";
    public static  String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/data";

    ZooKeeper zk = null;
    //插销锁
    private CountDownLatch latch = new CountDownLatch(1);
    //zookeeper对象

   // Watcher watcher =null;
    private ZooKeeper connectServer(String registryAddress,String path,Properties properties) {
       // ZooKeeper zk = null;
        PropertiesWatcher watcher =  new PropertiesWatcher(latch,path,properties);

        try {
            zk = new ZooKeeper(registryAddress, ZK_SESSION_TIMEOUT,watcher);
            watcher.setZk( zk);
            logger.info("latch.await()!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            latch.await();
        } catch (IOException | InterruptedException e) {
            logger.error("", e);
        }
        return zk;
    }

}
