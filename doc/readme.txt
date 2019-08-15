# 数据库
创建一个新的数据库awifi_ssc库 并执行awifi_ssc.sql 脚本

配置数据库地址
在classes/jdbc.properties里

# redis

给一个独立的redis库

配置redis地址
在classes/config.properties里
cache.redis.ip=192.168.41.75
#cache.redis.ip=192.168.212.45
cache.redis.port=6379
cache.redis.database.index=2

config.cfg
修改auth选项 对应的是redis的密码
## 用到的redis key 罗列如下

# tomcat
新建一个tomcat
/service/tomcat-npbiz-ezhike-8096/
创建目录
/service/tomcat-npbiz-ezhike-8096/awifi-ezhike
上传ezhike.war包至awifi-ezhike目录并解压
修改tomcat-xxxx/conf/service.xml
在host name 下一行增加
<Context path="/risksrv" docBase="/data/service/tomcat-awifi-risk-8090/awifi-risk" debug="0" reloadable="false"></Context>
    140

# nginx 配置
# 配置图片存储
需要一个静态nfs目录 /service/media目录 与 nginx静态文件夹进行挂载
给nginx配置ip hash 或者做session共享 选择一项
upstream server_lib{
    server localhost:8089 weight=10;
    server localhost:8090 weight=5;
    ip_hash;
}


# 垃圾清理
定时扫描tomcat下日志 然后加入cron定时任务
每分时间同步
*/1 * * * * /usr/sbin/ntpdate cn.ntp.org.cn && /sbin/hwclock -w >/dev/null 2>&1
每晚tomcat 日志清理
59 23  * * *  /service/tomcat-awifi-np-biz-3502/bin/clean.sh
1 0 * * * /bin/find /service/tomcat-awifi-np-biz-3502/logs/ -mtime +3 -type f -name "*.log" -exec /bin/rm -rf {} \;


配置kafaka
在config.properties里

kafka_server=192.168.212.67:9092,192.168.212.71:9092,192.168.212.72:9092

配置hbase环境
打开kerberos 认证方法 修改配置文件hbase.properties
kerbrosEnable=false
将hbase 目录下的hdfs.keytab 放到 /root/目录下
将 krb5.conf放入到/etc/目录下

配置/etc/hosts文件
加入hbase hosts



登录界面
Ngx配置（已申请外网端口、域名、udp端口）
Ngx静态文件配置   智威补充  解压到 tomcat-ezhike-biz-8085/webapps/ROOT
http://ip:port/#/login

#定时固化客流数据 每隔1个小时固化一下
55 1-24/1 * * * curl  "http://127.0.0.1:8085/ezhikesrv/customer/solid/customer"
#缓存探针对应信息 每隔10分钟固化一下
1-59/10 23  * * * curl  "http://127.0.0.1:8085/ezhikesrv/probe/cache"
#缓存探针对应信息 每天固化一下
50 23 * * * curl  "http://127.0.0.1:8081/ezhikesrv/stat/35"



2019-08-06 23:21:20

sys_user表要把多余的字段放到其他表

sys_user表的userName字段不明却
nkname 太拗口 应该直接叫nick
昵称没有冲突判断
应该提供单独修改昵称的页面
昵称的修改应该有记录可查 并对最近修改的昵称进行核查
jsp!!!! 很重要啊

单独提供用户根据id 的缓存方案
sys_user表还要有个业务id 不能老存自增id
pbkdf2