package dozenx.keyrobot.module.yys.task;


import device.WinIOVirtualMouse;
import org.xvolks.jnative.util.User32;
import util.YinYangShiHelper;

public class DoujiThread extends BaseThread{
		
		public DoujiThread(String key, int time, String windowName) {
			super(key, time, windowName);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			
			while(isRunning){
				try {
					if(User32.GetWindowText(User32.GetForegroundWindow()).contains(this.windowName)){
						//VirtualKeyBoard.KeyPress(VKMapping.toScanCode(this.key));
						
						YinYangShiHelper.porcessShouDong();
						YinYangShiHelper.porcessZhunbei();
						if(YinYangShiHelper.isDoujiEntry()){//�Ƿ���׼��״̬��
							
							sleep(5000);
							
						
							WinIOVirtualMouse.RandomClick(1085,586,50,50);
						}/*else if(YinYangShiHelper.isYaoqizhunbei()){
							//����뿪
							Thread.sleep(1000);
							VirtualMouse.click(129,91);
							
							Thread.sleep(2000);
							VirtualMouse.click(740,435);
							
						}*//*else if(YinYangShiHelper.isDoujizhunbei()){ //�ڶ���׼����ʱ�� ��׼���뿪 ���Ǽ���ս��
							//����뿪
//							Thread.sleep(1000);
//							VirtualMouse.click(129,91);
//							
//							Thread.sleep(2000);
//							VirtualMouse.click(740,435);
							
							YinYangShiHelper.clickZhunbei();
							
						}*/else if(YinYangShiHelper.isDoujiFailed()){
							//����뿪
							YinYangShiHelper.closeShengli();
							sleep(1000);
							WinIOVirtualMouse.RandomClick(802,456,18,19);
						}else if(YinYangShiHelper.isDoujiSuccess()){
							//����뿪
							YinYangShiHelper.closeShengli();
							
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

