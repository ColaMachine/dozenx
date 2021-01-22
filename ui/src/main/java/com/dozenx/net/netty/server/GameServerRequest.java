package com.dozenx.net.netty.server;


import com.dozenx.net.netty.cmd.GameCmd;
import com.dozenx.net.netty.server.bean.Worker;

/**
 * Created by luying on 17/2/18.
 */
public class GameServerRequest {
    private GameCmd cmd ;

    public GameCmd getCmd() {
        return cmd;
    }

    public void setCmd(GameCmd cmd) {
        this.cmd = cmd;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    private Worker worker;
    public GameServerRequest(GameCmd cmd , Worker worker){
        this.cmd =cmd ;
        this. worker = worker;
    }


}
