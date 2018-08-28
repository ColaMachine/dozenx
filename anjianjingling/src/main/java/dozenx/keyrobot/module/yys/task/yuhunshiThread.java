package dozenx.keyrobot.module.yys.task;


import device.WinIOVirtualMouse;
import org.xvolks.jnative.util.User32;
import util.YinYangShiHelper;

public class yuhunshiThread extends BaseThread{
		
		public yuhunshiThread(String key, int time, String windowName) {
			super(key, time, windowName);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			while(isRunning){
				try {
					if(User32.GetWindowText(User32.GetForegroundWindow()).contains(this.windowName)){
						if(YinYangShiHelper.ishun10Shengli()){
							
							WinIOVirtualMouse.RandomClick(675,184, 130, 30);//��ʼ�ƶ�
							//���ҵ�
							sleep(200);
							WinIOVirtualMouse.RandomClick(675,184, 130, 30);//��ʼ�ƶ�
							sleep(500);
							WinIOVirtualMouse.RandomClick(675,184, 130, 30);//��ʼ�ƶ�
							sleep(200);
							WinIOVirtualMouse.RandomClick(675,184, 130, 30);//��ʼ�ƶ�
							sleep(500);
							WinIOVirtualMouse.RandomClick(675,184, 130, 30);//��ʼ�ƶ�
							sleep(200);
							WinIOVirtualMouse.RandomClick(675,184, 130, 30);//��ʼ�ƶ�
							
						}else if(YinYangShiHelper.isKaishiYuhun()){
								//Thread.sleep(2000);
								//�ȴ�ȫ��׼������
							//965,370,162,155,147,
								/*while(ColorPicker.screenColorike(new Point(965,370), new Color(162,155,147))){
									Thread.sleep(300);
								}*/
								System.out.println("��ʼ������");
							WinIOVirtualMouse.click(923,503);//��ʼ�ƶ�
							//���ҵ�
							sleep(1000);
							
							
						}else if(YinYangShiHelper.isJuexingZhunbei()){
							sleep(3000);
							WinIOVirtualMouse.RandomClick(1181,603,50,50);//���̽��
						
						
					}else 
							if(YinYangShiHelper.isBattleEnd1()){
							
							WinIOVirtualMouse.RandomClick(728,415, 130, 30);//��ʼ�ƶ�
							//���ҵ�
							sleep(1000);
							WinIOVirtualMouse.RandomClick(728,415, 130, 30);//��ʼ�ƶ�
							
						}else if(YinYangShiHelper.isBattleing()){
							this.lastFightTime=System.currentTimeMillis();
							//���ҵ�
							sleep(1000);
						}else if(YinYangShiHelper.isBattleEnd()){
							
							WinIOVirtualMouse.click(672,542);//��ʼ�ƶ�
							
							WinIOVirtualMouse.RandomClick(672,542,100,100);//��ʼ�ƶ�
							sleep(500);
							WinIOVirtualMouse.RandomClick(672,542,100,100);//��ʼ�ƶ�
							
							sleep(1500);
							WinIOVirtualMouse.RandomClick(672,542,100,100);//��ʼ�ƶ�
							//lastFightTime = System.currentTimeMillis();
						}
						
					}
					sleep(time*100);
					
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					isRunning =false;
				}
			}
			
		}
		
		
	}

