package com.dozenx.net.netty.cmd;

/**
 * Created by luying on 17/2/7.
 */
public interface GameCmd {
    public byte[] toBytes();

    public void delete();

    public CmdType getCmdType();

    public int val();
}
