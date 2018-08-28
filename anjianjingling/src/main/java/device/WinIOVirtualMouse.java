package device;

import util.VKMapping;
import util.WinIOAPI;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;


public class WinIOVirtualMouse {
	
	
	public static void leftDown() throws Exception{
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.CONTROL_PORT,9	,1);//01001
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.CONTROL_PORT,0	,1);//01001
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.CONTROL_PORT,0	,1);//01001
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.CONTROL_PORT,0	,1);//01001
	}
	
	public static void leftUp() throws Exception{
		 WinIOAPI.KBCWait4IBE(); // 'wait for buffer gets empty
         WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
         
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.CONTROL_PORT,9	,1);//01001
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.CONTROL_PORT,0	,1);//01001
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.CONTROL_PORT,0	,1);//01001
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(WinIOAPI.CONTROL_PORT,0	,1);//01001
	}
	
	
	/*
	MouseMove(9, 0, 0, 0   )//�������
	MouseMove(8, 0, 0, 0   )//����ſ�
	MouseMove(10, 0, 0, 0  )//�Ҽ�����
	MouseMove(8, 0, 0, 0   )//�Ҽ��ſ�
	MouseMove(12, 0, 0, 0  )//�м�����
	MouseMove(8, 0, 0, 0   )//�м��ſ�
	MouseMove(8, 0, 5, 0   )//����5����
	MouseMove(40, 0,(255 xor 5),0   )//����5����
	MouseMove(24,(255 xor 5), 0, 0  )//����5����
	MouseMove(8, 5, 0, 0            )//����5����
	*/
	public  static void MouseMove(int Fun,int x,int y,int z) throws Exception//����˵�������ܣ�x,y,z����λ��
	{
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_CMD,0xD3,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_DATA,(Fun| 0x80),1);

		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_CMD,0xD3,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_DATA,x| 0x80,1);

		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_CMD,0xD3,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_DATA,y| 0x80,1);

		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_CMD,0xD3,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_DATA,z| 0x80,1);
	}
	
	
	/*���統����κ�һ������̧��ʱ��USB�˿�����00 00 00 00H���ݡ�
	������м�����ʱ,USB�˿�����04 00 00 00H���ݡ�
	������������ʱ��USB�˿�����01 00 00 00H���ݡ�
	������Ҽ�����ʱ��USB�˿�����02 00 00 00H���ݡ�
	���������ʱ��USB�˿�����00 00 FX 00H���ݡ�
	���������ʱ��USB�˿�����00 00 0X 00H���ݡ�
	���������ʱ��USB�˿�����00 FX 00 00H���ݡ�
	���������ʱ��USB�˿�����00 0X 00 00H���ݡ�*/
	public  static void MouseMove1(int Fun,int x,int y,int z) throws Exception//����˵�������ܣ�x,y,z����λ��
	{
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_CMD,0xD3,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_DATA,1,1);

		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_CMD,0xD3,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_DATA,0,1);

		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_CMD,0xD3,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_DATA,0,1);

		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_CMD,0xD3,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_DATA,0,1);
	}
	public  static void MouseUp1(int Fun,int x,int y,int z) throws Exception//����˵�������ܣ�x,y,z����λ��
	{
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_CMD,0xD3,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_DATA,0,1);

		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_CMD,0xD3,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_DATA,0,1);

		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_CMD,0xD3,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_DATA,0,1);

		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_CMD,0xD3,1);
		WinIOAPI.KBCWait4IBE();
		WinIOAPI.SetPortVal(KBC_KEY_DATA,0,1);
	}
	
	 public static int KBC_KEY_CMD = 0x64;
     public static int KBC_KEY_DATA = 0x60;
	
 static int  move = 0x0001;
 static	 int       leftdown = 0x0002;
 static		 int        leftup = 0x0004;
	  public static void MyMouseDown() throws Exception
	  {
	      int btScancode = 0;
	      btScancode = leftdown;
	      WinIOAPI.KBCWait4IBE(); // 'wait for buffer gets empty
	  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
	  WinIOAPI. KBCWait4IBE();
	  WinIOAPI. SetPortVal(KBC_KEY_DATA, (btScancode | 0x80), 1);// 'write in io
	  }
	  
	  public static void BusHoundLeftDown() throws Exception
	  {
		  WinIOAPI.KBCWait4IBE();
			WinIOAPI.SetPortVal(WinIOAPI.CONTROL_PORT,0xD3,1);
			WinIOAPI.KBCWait4IBE();
			WinIOAPI.SetPortVal(WinIOAPI.DATA_PORT,(1),1);
		  
			WinIOAPI.KBCWait4IBE();
			WinIOAPI.SetPortVal(WinIOAPI.CONTROL_PORT,0xD3,1);
			WinIOAPI.KBCWait4IBE();
			WinIOAPI.SetPortVal(WinIOAPI.DATA_PORT,(1|0x80),1);
		  
		  
	  
	  }
	  
	  
	  public static void BusHoundLeftDown1() throws Exception
	  {
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_DATA, (1), 1);// 'write in io
		  WinIOAPI. KBCWait4IBE();
		  
		  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_DATA, 0, 1);// 'write in io
		  WinIOAPI. KBCWait4IBE();
		  
		  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_DATA, 0, 1);// 'write in io
		  WinIOAPI. KBCWait4IBE();
		  
		  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_DATA,0, 1);// 'write in io
		  WinIOAPI. KBCWait4IBE();
		  
		  
		  
		
		  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_DATA, (1), 1);// 'write in io
		  WinIOAPI. KBCWait4IBE();
		  
		  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_DATA, 0, 1);// 'write in io
		  WinIOAPI. KBCWait4IBE();
		  
		  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_DATA, 0, 1);// 'write in io
		  WinIOAPI. KBCWait4IBE();
		  
		  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_DATA,0, 1);// 'write in io
		  WinIOAPI. KBCWait4IBE();
		  
		  

		  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_DATA, (1), 1);// 'write in io
		  WinIOAPI. KBCWait4IBE();
		  
		  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_DATA, 0, 1);// 'write in io
		  WinIOAPI. KBCWait4IBE();
		  
		  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_DATA, 0, 1);// 'write in io
		  WinIOAPI. KBCWait4IBE();
		  
		  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_DATA,0, 1);// 'write in io
		  WinIOAPI. KBCWait4IBE();
		  
		  

		  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_DATA, (2), 1);// 'write in io
		  WinIOAPI. KBCWait4IBE();
		  
		  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_DATA, 0, 1);// 'write in io
		  WinIOAPI. KBCWait4IBE();
		  
		  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_DATA, 0, 1);// 'write in io
		  WinIOAPI. KBCWait4IBE();
		  
		  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
		  WinIOAPI. KBCWait4IBE();
		  WinIOAPI. SetPortVal(KBC_KEY_DATA,0, 1);// 'write in io
		  WinIOAPI. KBCWait4IBE();
		  
		  
		  
		  
	  
	  }
	  
      /// Simulate mouse up
      public static void MyMouseUp() throws Exception
      {
          int btScancode = 0;
          btScancode = leftup;
          WinIOAPI.KBCWait4IBE(); // 'wait for buffer gets empty
          WinIOAPI.  SetPortVal(KBC_KEY_CMD,0xD3, 1); //'send write command
          WinIOAPI.  KBCWait4IBE();
          WinIOAPI. SetPortVal(KBC_KEY_DATA, (btScancode | 0x80), 1);// 'write in io
      }
      
      public static void LeftMouseDown() throws Exception
	  {
	      int btScancode = 0;
	      btScancode = leftdown;
	      WinIOAPI.KBCWait4IBE(); // 'wait for buffer gets empty
	  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
	  WinIOAPI. KBCWait4IBE();
	  WinIOAPI. SetPortVal(KBC_KEY_DATA, (1), 1);// 'write in io
	  
	  
	  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
	  WinIOAPI. KBCWait4IBE();
	  WinIOAPI. SetPortVal(KBC_KEY_DATA, 0, 1);// 'write in io
	  
	  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
	  WinIOAPI. KBCWait4IBE();
	  WinIOAPI. SetPortVal(KBC_KEY_DATA, 0, 1);// 'write in io
	  
	  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
	  WinIOAPI. KBCWait4IBE();
	  WinIOAPI. SetPortVal(KBC_KEY_DATA,0, 1);// 'write in io
	  }
      /// Simulate mouse up
      public static void LeftMouseUp() throws Exception
      {
          int btScancode = 0;
          btScancode = leftup;
          WinIOAPI.KBCWait4IBE(); // 'wait for buffer gets empty
          WinIOAPI.  SetPortVal(KBC_KEY_CMD,0xD3, 1); //'send write command
          WinIOAPI.  KBCWait4IBE();
          WinIOAPI. SetPortVal(KBC_KEY_DATA, (1), 1);// 'write in io
          
          
    	  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
    	  WinIOAPI. KBCWait4IBE();
    	  WinIOAPI. SetPortVal(KBC_KEY_DATA, 0, 1);// 'write in io
    	  
    	  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
    	  WinIOAPI. KBCWait4IBE();
    	  WinIOAPI. SetPortVal(KBC_KEY_DATA, 0, 1);// 'write in io
    	  
    	  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
    	  WinIOAPI. KBCWait4IBE();
    	  WinIOAPI. SetPortVal(KBC_KEY_DATA,0, 1);// 'write in io
      }
      
      
      public static  void  onMouse() throws Exception{
    	  WinIOAPI.KBCWait4IBE(); 
    	  WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x09,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1); 
    	    //�ɿ�
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x08,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1);
      }
      
      public static  void  onMouse1() throws Exception{
    	  WinIOAPI.KBCWait4IBE(); 
    	  WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x02,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1); 
    	    //�ɿ�
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x08,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1);
      }
      
      public static  void  onMouse2() throws Exception{
    	  WinIOAPI.KBCWait4IBE(); 
    	  WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x02,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	   
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	   
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	  
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1); 
    	    //�ɿ�
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X64, 0xD3,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	    WinIOAPI.  SetPortVal( 0X60, 0x08,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    	
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1); 
    	    WinIOAPI.KBCWait4IBE(); 
    
    	    WinIOAPI.  SetPortVal( 0X60, 0x00,1);
      }
	
      public static void LeftMouseDown1() throws Exception
	  {
	      int btScancode = 0;
	      btScancode = leftdown;
	      WinIOAPI.KBCWait4IBE(); // 'wait for buffer gets empty
	  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
	  WinIOAPI. KBCWait4IBE();
	  WinIOAPI. SetPortVal(KBC_KEY_DATA, (1|0x80), 1);// 'write in io
	  
	  
	  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
	  WinIOAPI. KBCWait4IBE();
	  WinIOAPI. SetPortVal(KBC_KEY_DATA, 0|0x80, 1);// 'write in io
	  
	  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
	  WinIOAPI. KBCWait4IBE();
	  WinIOAPI. SetPortVal(KBC_KEY_DATA, 0|0x80, 1);// 'write in io
	  
	  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
	  WinIOAPI. KBCWait4IBE();
	  WinIOAPI. SetPortVal(KBC_KEY_DATA,0|0x80, 1);// 'write in io
	  }
      /// Simulate mouse up
      public static void LeftMouseUp1() throws Exception
      {
          int btScancode = 0;
          btScancode = leftup;
          WinIOAPI.KBCWait4IBE(); // 'wait for buffer gets empty
          WinIOAPI.  SetPortVal(KBC_KEY_CMD,0xD3, 1); //'send write command
          WinIOAPI.  KBCWait4IBE();
          WinIOAPI. SetPortVal(KBC_KEY_DATA, (1|0x80), 1);// 'write in io
          
          
    	  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
    	  WinIOAPI. KBCWait4IBE();
    	  WinIOAPI. SetPortVal(KBC_KEY_DATA, 0|0x80, 1);// 'write in io
    	  
    	  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
    	  WinIOAPI. KBCWait4IBE();
    	  WinIOAPI. SetPortVal(KBC_KEY_DATA, 0|0x80, 1);// 'write in io
    	  
    	  WinIOAPI. SetPortVal(KBC_KEY_CMD,0xD3, 1);// 'send write command //208
    	  WinIOAPI. KBCWait4IBE();
    	  WinIOAPI. SetPortVal(KBC_KEY_DATA,0|0x80, 1);// 'write in io
      }
   static   Robot robot;
    static{  try  
      {  
    	robot = new Robot();  
      } catch (Exception e)  
      {  
          e.printStackTrace();  
      }  
    }
	public static void click(int x,int y) throws Exception {
		System.out.println(System.currentTimeMillis()+"�ƶ�");
		robot.mouseMove(x, y);
		//Thread.sleep(100);
		//robot.keyPress(KeyEvent.VK_NUMPAD5);
		Point mousepoint  = MouseInfo.getPointerInfo().getLocation();
		if(mousepoint.x== x &&mousepoint.y==y ){
			
		}else{robot.mouseMove(x, y);
			System.out.println(String.format("Ӧ�õ�� %d��%d ʵ�ʵ���� %d,%d",x,y,mousepoint.x,mousepoint.y));
		}
		System.out.println(System.currentTimeMillis()+"���");
		WinIOVirtualKeyBoard.KeyPress(VKMapping.toScanCode("num_5"));//Thread.sleep(3000);
		
	}
	
	public static void longclick(int x,int y) throws Exception {
		System.out.println(System.currentTimeMillis()+"�ƶ�");
		robot.mouseMove(x, y);
		//Thread.sleep(100);
		//robot.keyPress(KeyEvent.VK_NUMPAD5);
		Point mousepoint  = MouseInfo.getPointerInfo().getLocation();
		if(mousepoint.x== x &&mousepoint.y==y ){
			
		}else{robot.mouseMove(x, y);
			System.out.println(String.format("Ӧ�õ�� %d��%d ʵ�ʵ���� %d,%d",x,y,mousepoint.x,mousepoint.y));
		}
		System.out.println(System.currentTimeMillis()+"���");
		WinIOVirtualKeyBoard.KeyDown(VKMapping.toScanCode("num_5"));Thread.sleep(3000);
		WinIOVirtualKeyBoard.KeyUp(VKMapping.toScanCode("num_5"));Thread.sleep(3000);
		
	}
	
	public static void RandomClick(int x,int y,int width ,int height) throws Exception {
		//System.out.println(System.currentTimeMillis()+"�ƶ�1");
		int newX = (int)(Math.random()*width)+x;
		int newY = (int)(Math.random()*height)+y;
		//System.out.println(String.format("����ƶ���x:%d y:%d",newX,newY));
		robot.mouseMove(newX,newY);//Thread.sleep(3000);
		
//		Point mousepoint  = MouseInfo.getPointerInfo().getLocation();
//		if(mousepoint.x== x &&mousepoint.y==y ){
//			
//		}else{robot.mouseMove(x, y);
//			System.out.println(String.format("Ӧ�õ�� %d��%d ʵ�ʵ���� %d,%d",x,y,mousepoint.x,mousepoint.y));
//		}
//		
		//System.out.println(System.currentTimeMillis()+"���1");
		WinIOVirtualKeyBoard.KeyPress(VKMapping.toScanCode("num_5"));//Thread.sleep(3000);
	}
	
	
	public static void RandomMove(int x,int y,int width ,int height) throws Exception {
		//System.out.println(System.currentTimeMillis()+"�ƶ�1");
		int newX = (int)(Math.random()*width)+x;
		int newY = (int)(Math.random()*height)+y;
		//System.out.println(String.format("����ƶ���x:%d y:%d",newX,newY));
		robot.mouseMove(newX,newY);//Thread.sleep(3000);
		
		
	}
	
	public static void MouseDown() throws Exception {
		//System.out.println(System.currentTimeMillis()+"�ƶ�1");
		WinIOVirtualKeyBoard.KeyDown(VKMapping.toScanCode("num_5"));//Thread.sleep(3000);
		
		
	}
	public static void MouseUp() throws Exception {
		//System.out.println(System.currentTimeMillis()+"�ƶ�1");
		WinIOVirtualKeyBoard.KeyUp(VKMapping.toScanCode("num_5"));//Thread.sleep(3000);
		
		
		
		
	}
	
	public static void BeginDrag()throws Exception {
		WinIOVirtualKeyBoard.KeyDown(VKMapping.toScanCode("num_0"));//Thread.sleep(3000);
		WinIOVirtualKeyBoard.KeyUp(VKMapping.toScanCode("num_0"));//Thread.sleep(3000);
	}
	public static void EndDrag()throws Exception {
		WinIOVirtualKeyBoard.KeyDown(VKMapping.toScanCode("num_."));//Thread.sleep(3000);
		WinIOVirtualKeyBoard.KeyUp(VKMapping.toScanCode("num_."));//Thread.sleep(3000);
	}
	
	
	public  static void Drag(int fromx,int fromy,int tox,int toy,int sec) throws Exception {
		WinIOVirtualMouse.robot.mouseMove(fromx,fromy);//Thread.sleep(3000);(fromx,fromy,40,10);
		Thread.sleep(500);
		WinIOVirtualMouse.BeginDrag();
		//Thread.sleep(sec);
		
		int times =10;
		int nowx=0,nowy=0;
		for(int i=0;i<times;i++){//VirtualMouse.MouseDown();
			nowx=fromx+ (tox-fromx)/times*i;
			nowy=fromy+(toy-fromy)/times*i;
			WinIOVirtualMouse.robot.mouseMove(nowx,nowy);
			//System.out.println();
			Thread.sleep(sec/times);
		}
		WinIOVirtualMouse.robot.mouseMove(tox,toy);
		Thread.sleep(500);
		WinIOVirtualMouse.EndDrag();
		Thread.sleep(500);
	}
}
