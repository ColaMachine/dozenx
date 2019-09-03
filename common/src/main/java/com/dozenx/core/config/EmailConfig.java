
package com.dozenx.core.config;

/**
 * @author dozen.zhang
 *
 */
public class EmailConfig {
    //##设置传输协议
    private String protocol;
    //##设置发信邮箱的smtp地址
    private String host;
    //认证
    private String auth;
    //调试
    private String debug;
    //端口
    private int port;
    //用户
    private String username;
    //密码
    private String pwd;
    //是否启用ssl
    private boolean ssl;

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
