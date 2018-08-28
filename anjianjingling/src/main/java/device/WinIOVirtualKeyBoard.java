package device;

import util.VKMapping;
import util.WinIOAPI;

public class WinIOVirtualKeyBoard {
	
	public static void main(String[] args) throws Exception {
		Thread.sleep(3000);
		String s="helloworld";
		KeyDown(VKMapping.toScanCode("num_0"));
		KeyUp(VKMapping.toScanCode("num_0"));
		Thread.sleep(3000);
		KeyDown(VKMapping.toScanCode("num_."));
		KeyUp(VKMapping.toScanCode("num_."));
	}
	public static void KeyDown(int key) throws Exception{
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.CONTROL_PORT,0xd2,1);
		
		
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.DATA_PORT,key,1);
	}
	public static void KeyDownEx(int key) throws Exception{
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.CONTROL_PORT,0xd2,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.DATA_PORT,0xe0,1);
		
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.CONTROL_PORT,0xd2,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.DATA_PORT,key,1);
		
	}
	public static void KeyUpEx(int key) throws Exception{
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.CONTROL_PORT,0xd2,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.DATA_PORT,0xe0,1);
		
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.CONTROL_PORT,0xd2,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.DATA_PORT,(key|0x80),1);
		
	}
	public static void KeyUp(int key) throws Exception{
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.CONTROL_PORT,0xd2,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.DATA_PORT,(key|0x80),1);
		
	}
	
	public static void KeyPress(int key) throws Exception{
		KeyDown(key);
		KeyUp(key);
	}

}
