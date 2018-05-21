package com.dozenx.web.core.properties;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dozen.zhang on 2017/7/6.
 */
public class PropertiesWatcher implements Watcher {

    CountDownLatch latch =null;
    ZooKeeper zk = null ;
    String rootPath = null;
    private static Stat stat = new Stat();
    Properties properties = null;
    public void setZk(ZooKeeper zk ){
        this.zk=zk;
    }
    public PropertiesWatcher(CountDownLatch latch,  String rootPath,Properties properties){
        this.latch = latch;

        this.rootPath = rootPath;
        this.properties = properties;
    }
    public PropertiesWatcher(CountDownLatch latch, ZooKeeper zk , String rootPath,Properties properties){
        this.latch = latch;
        this.zk = zk;
        this.rootPath = rootPath;
        this.properties = properties;
    }
    Logger logger = LoggerFactory.getLogger(ZKClient.class);
    @Override
    public void process(WatchedEvent event) {
        try {
            logger.info("changed ");
            if (event.getState() == Event.KeeperState.SyncConnected) {
                logger.debug("zookeeper connected");
                latch.countDown();
            }
            //如果是发生了改变 请通知我
            if (event.getType() == Event.EventType.NodeChildrenChanged) {
                //说明发生了数据改变了
                logger.debug("zookeeper child changed");

                //同步整个节点下的数据
                                  /*  List<String> zkStrList =  zk.getChildren((String)properties.get("zookeeper.path"));
                                    properties.putAll();*/

            }
            synConfigFromZookeeper(rootPath);
            addWatch(rootPath);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void addWatch(String path) throws KeeperException, InterruptedException {
        zk.exists(path, new PropertiesWatcher(latch,zk,rootPath,properties));
    }
    public void synConfigFromZookeeper(String path) throws KeeperException, InterruptedException {

        List<String> configs = zk.getChildren(path,true);
        for(String configKey:configs){
            //获取所有配置数据
            logger.info("zookeeper config:"+configKey);
            String configValue = new String(zk.getData(path+"/"+configKey,true,stat));
            logger.info("同步读取节点内容： "+configKey+":"+configValue);

            properties.put(configKey , configValue);
                         /*   System.out.println("同步读取节点内容：" + new String(zk.getData(path,true,stat)));
                            System.out.println("同步读取Stat：czxid=" + stat.getCzxid()
                                    + ";mzxid=" + stat.getMzxid() + ";version="  + stat.getVersion());*/
        }


    }
}
