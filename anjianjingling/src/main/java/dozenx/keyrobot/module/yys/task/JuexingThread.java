package dozenx.keyrobot.module.yys.task;


import device.WinIOVirtualMouse;
import util.User32;
import util.YinYangShiHelper;

public class JuexingThread extends BaseThread{
	
		public JuexingThread(String key, int time, String windowName) {
			super(key, time, windowName);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			while(isRunning){
				try {
					if(User32.GetWindowText(User32.GetForegroundWindow()).contains(this.windowName)){
						//VirtualKeyBoard.KeyPress(VKMapping.toScanCode(this.key));
						if(YinYangShiHelper.isJuexingEntry()){//������ڵ�ͼ�� �͵������25����
								WinIOVirtualMouse.RandomClick(902,494,40,40);
						sleep(6000);
						WinIOVirtualMouse.RandomClick(1097,534,40,40);//���׼��
						sleep(20000);//1105,524
						}else if(YinYangShiHelper.isShengli()){//ʤ��û���ж�
							WinIOVirtualMouse.RandomClick(619,576,50,50);//���̽��
							
						
						}else if(YinYangShiHelper.isJuexingZhunbei()){//׼��
							//VirtualMouse.RandomClick(1105,524,50,50);//���̽��
							
							YinYangShiHelper.clickZhunbei();
						}else if(YinYangShiHelper.isBattleEnd1()){
							
							WinIOVirtualMouse.RandomClick(728,415, 130, 30);//��ʼ�ƶ�
							//���ҵ�
							sleep(1000);
							WinIOVirtualMouse.RandomClick(728,415, 130, 30);//��ʼ�ƶ�
							
						}else if(YinYangShiHelper.isBattleing()){
							
							//���ҵ�
							sleep(5000);
						}else if(YinYangShiHelper.isBattleEnd()){
							
							WinIOVirtualMouse.click(672,542);//��ʼ�ƶ�
							
							WinIOVirtualMouse.RandomClick(672,542,100,100);//��ʼ�ƶ�
							lastFightTime = System.currentTimeMillis();
						}else{
							System.out.println("ʲô��û��⵽");
						}
						
						//966,243,194,194,220,
						
						
					}
					sleep(time*100);
					
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					isRunning =false;
				}
			}
			
		}
		
	
	}

