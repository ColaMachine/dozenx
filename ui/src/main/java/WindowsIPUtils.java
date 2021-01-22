public class WindowsIPUtils {

    public static void main(String args[]){

        try {
            Runtime
                    .getRuntime()
//                    .exec("Netsh interface ip set address \"本地连接\" static 172.16.26.117 255.255.255.254 172.16.26.1");
 .exec("Netsh interface ip set address \"本地连接\" static 172.200.96.117  255.255.248.0 172.200.96.1");

//           Netsh interface ip set address "本地连接"  source=static addr=172.200.96.117 mask=255.255.248.0 gateway=172.200.96.1 gwmetric=1
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

//
//
//:: 设置IP地址
//        set /p choice=请选择设置类型(1:外网IP / 2:内网IP / 3:自动获取IP):
//        echo.
//        if "%choice%"=="1" goto ip1
//        if "%choice%"=="2" goto ip2
//        if "%choice%"=="3" goto ip3
//        goto main
//
//        :ip1
//        echo 外网IP自动设置开始...
//        echo.
//        echo 正在设置外网IP及子网掩码
//        cmd /c netsh interface ip set address name="以太网" source=static addr=ip地址 mask=子网掩码 gateway=默认网关 gwmetric=1
//        echo 正在设置外网DNS服务器
//        cmd /c netsh interface ip add dnsservers name="以太网" address=DNS index=1
//        cmd /c netsh interface ip add dnsservers name="以太网" address=DNS index=2
//        echo 外网IP设置完成
//        pause
//        exit
//        if errorlevel 2 goto main
//        if errorlevel 1 goto end
//
//        :ip2
//        echo 内网IP自动设置开始...
//        echo.
//        echo 正在设置内网IP及子网掩码
//        cmd /c netsh interface ip set address name="以太网" source=static addr=ip地址 mask=子网掩码 gateway=默认网关 gwmetric=1
//        echo 正在设置内网DNS服务器
//        cmd /c netsh interface ip add dnsservers name="以太网" address=DNS index=1
//        cmd /c netsh interface ip add dnsservers name="以太网" address=DNS index=2
//        echo 内网IP设置完成
//        pause
//        exit
//        if errorlevel 2 goto main
//        if errorlevel 1 goto end
//
//        :ip3
//        echo IP自动设置开始....
//        echo.
//        echo 自动获取IP地址....
//        netsh interface ip set address name = "以太网" source = dhcp
//        echo 自动获取DNS服务器....
//        netsh interface ip set dns name = "以太网" source = dhcp
//@rem 设置自动获取IP
//        echo 设置完成
//        pause
//        exit
//        if errorlevel 2 goto main
//        if errorlevel 1 goto end
//
//        :end