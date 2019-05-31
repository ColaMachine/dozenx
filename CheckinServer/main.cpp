#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include "HCNetSDK.h"

#define CLOSE_DOOR 0
#define OPEN_DOOR 1
#define ALWAYS_OPEN 2
#define ALWAYS_CLOSE 3

#include<stdio.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<arpa/inet.h>

#include <string>
#include <string.h>
#include <map>
#include <vector>
#include <fstream>
#include <algorithm>
#define _SHORT_PORT    (7098)

int main()
{
 NET_DVR_Init();
 NET_DVR_SetConnectTime(2000, 1);
 NET_DVR_SetReconnect(10000, true);

 LONG lUserID;
 NET_DVR_USER_LOGIN_INFO struLoginInfo = {0};
 struLoginInfo.bUseAsynLogin = 0;

 strcpy(struLoginInfo.sDeviceAddress, "192.168.90.203");
 //设备 IP 地址
 struLoginInfo.wPort = 8000;
 //设备服务端口
 strcpy(struLoginInfo.sUserName, "admin");
 //登录用户名
 strcpy(struLoginInfo.sPassword, "admin12345");
 //登录密码

 //设备信息, 输出参数
 NET_DVR_DEVICEINFO_V40 struDeviceInfoV40 = {0};

 lUserID = NET_DVR_Login_V40(&struLoginInfo, &struDeviceInfoV40);
 if (lUserID < 0)
 {
  printf("Login failed, error code: %d\n", NET_DVR_GetLastError());
  NET_DVR_Cleanup();
  return 1;
 }
printf("login succ\n");
 //开门，以门 1 为例
 BOOL bRet;  LONG lGatewayIndex = 1;//门禁序号，从 1 开始，-1 表示对所有门进行操作
 DWORD dwStaic = 1;//命令值：0-关闭，1-打开，2-常开，3-常关
printf("open\n");


        int fd, len;
     struct sockaddr_in ar = { AF_INET };
     socklen_t al = sizeof (struct sockaddr_in);
     char msg[BUFSIZ];

     if ((fd = socket(PF_INET, SOCK_DGRAM, IPPROTO_UDP)) == -1) {
         perror("main socket dgram");
         exit(EXIT_FAILURE);
     }

     printf("udp server start [%d][0.0.0.0][%hd] ...\n", fd, _SHORT_PORT);

     // 彜~M佊¡余¨纾Q孾Z作°佝~@
     ar.sin_port = htons(_SHORT_PORT);
     if (bind(fd, (struct sockaddr *)&ar, al) == -1) {
         perror("main bind INADDR_ANY");
         exit(EXIT_FAILURE);
     }
      while ((len = recvfrom(fd, msg, sizeof msg - 1, 0, (struct sockaddr *)&ar, &al)) > 0) {
             msg[len] = '\0';
             printf("[%s:%hd] -> %s\n", inet_ntoa(ar.sin_addr), ntohs(ar.sin_port), msg);
     //Start_Control();
            if(strcmp(msg,"opendoor")==0 ){
                bRet = NET_DVR_ControlGateway(lUserID,lGatewayIndex,dwStaic);
            }
             // 佛~^彘¾继续住~Q轀~A纾Y客彈·端
          //   sendto(fd, msg, len, 0, (struct sockaddr *)&ar, al);
         }
     close(fd);






 if (!bRet)
 {
  printf("NET_DVR_ControlGateway failed, error:%d\n",NET_DVR_GetLastError());
  NET_DVR_Logout(lUserID);
  NET_DVR_Cleanup();
  return 1;
 }
 printf("sleep\n");
 //---------------------------------------  //退出
 sleep(5);

 //注销用户
 NET_DVR_Logout(lUserID);  //释放 SDK 资源
 NET_DVR_Cleanup();
 return 1;
}
