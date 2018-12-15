创建一个新的数据库awifi_ssc库 并执行awifi_ssc.sql 脚本

新建一个tomcat
/service/tomcat-npbiz-ezhike-8096/
创建目录
/service/tomcat-npbiz-ezhike-8096/awifi-ezhike
上传ezhike.war包至awifi-ezhike目录并解压

配置数据库地址
在classes/jdbc.properties里

配置redis地址
在classes/config.properties里
--新加redis  单机
redis加密？    智威确认
cache.redis.ip=192.168.41.75
#cache.redis.ip=192.168.212.45
cache.redis.port=6379
cache.redis.database.index=2
cache.redis.pwd.index=123456
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

----------------以下内容不是运维部署手册，不需要执行---------------------
固化数据使用说明
redis
127.0.0.1:6379[2]> hgetall probeData
 1) "50da00303121"
 2) "{\"floorId\":1,\"x\":45,\"y\":90,\"buildId\":1,\"id\":2,\"shopId\":1,\"mac\":\"50da00303121\"}"
 3) "7935003B5D6093B2CB9D2AFE0E683999"
 4) "{\"floorId\":583,\"x\":5,\"y\":5,\"buildId\":35,\"id\":19,\"shopId\":5,\"mac\":\"7935003B5D6093B2CB9D2AFE0E683999\"}"
 5) "mac"
 6) "{\"floorId\":1,\"x\":45,\"y\":90,\"buildId\":1,\"id\":4,\"shopId\":1,\"mac\":\"mac\"}"
 7) "50da00303120"
 8) "{\"floorId\":1,\"x\":45,\"y\":90,\"buildId\":1,\"id\":1,\"shopId\":1,\"mac\":\"50da00303120\"}"
 9) "12345678901111"
10) "{\"floorId\":1,\"x\":33,\"y\":44,\"buildId\":1,\"id\":16,\"shopId\":1,\"mac\":\"12345678901111\"}"
11) "1234567890"
12) "{\"floorId\":1,\"x\":33,\"y\":44,\"buildId\":1,\"id\":23,\"shopId\":1,\"mac\":\"1234567890\"}"
13) "192F032179CF0F3671E461C3D7F131DA"
14) "{\"floorId\":583,\"x\":5,\"y\":17,\"buildId\":35,\"id\":20,\"shopId\":5,\"mac\":\"192F032179CF0F3671E461C3D7F131DA\"}"
15) "14E85B5E7B7F0DD7BA9CB97B6902B171"
16) "{\"floorId\":583,\"x\":15,\"y\":5,\"buildId\":35,\"id\":21,\"shopId\":5,\"mac\":\"14E85B5E7B7F0DD7BA9CB97B6902B171\"}"


我们这里的三台探针的devid

14E85B5E7B7F0DD7BA9CB97B6902B171

7935003B5D6093B2CB9D2AFE0E683999

192F032179CF0F3671E461C3D7F131DA

江苏国光探针
D71269A27E7F9EADCEEE63B37B2F7569
C5FFC9D7AB48A0FDED1C2F3B8C5C98EA
2F44CAE1DFD9DDB6729747BFEFE5621D

流程1
step1  udpserver 监听 7776端口 由外网的端口映射到内网的端口 传入的数据在udpserver中由processAry 进行处理 麦优的数据
  华三也有处理 他们的头部magic字段不同

  处理完了往kafkatopic 中写
  配置在config.properties中

kafka_topic_name=passenger-flow-proble-new

kafka_server=192.168.212.67:9092,192.168.212.71:9092,192.168.212.72:9092

step2
spark消费kafka数据后送入hbase

192.168.41.61 大数据hbase
192.168.212.90:8085 web
kafka 192.168.212.71:9092,192.168.212.72:9092 kafka
