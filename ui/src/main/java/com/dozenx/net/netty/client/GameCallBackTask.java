package com.dozenx.net.netty.client;


import com.dozenx.net.netty.cmd.ResultCmd;

/**
 * Created by dozen.zhang on 2017/2/21.
 */
public class GameCallBackTask implements Runnable {
    public Object lock;
    public GameCallBackTask(Object lock){
        this.lock=lock;
    }
    private ResultCmd result;

    public ResultCmd getResult() {
        return result;
    }

    public void setResult(ResultCmd result) {
        this.result = result;
    }

    @Override
    public void run() {

    }
}
