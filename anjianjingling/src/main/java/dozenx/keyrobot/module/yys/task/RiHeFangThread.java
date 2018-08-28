package dozenx.keyrobot.module.yys.task;


import device.WinIOVirtualMouse;
import util.User32;
import util.YinYangShiHelper;

public class RiHeFangThread extends BaseThread{
		public RiHeFangThread(String key, int time, String windowName) {
			super(key, time, windowName);
			
		}

	
		
		@Override
		public void run() {
			while(isRunning){
				try {
					if(User32.GetWindowText(User32.GetForegroundWindow()).contains(this.windowName)){
						//VirtualKeyBoard.KeyPress(VKMapping.toScanCode(this.key));
						
						YinYangShiHelper.judgeAndProccessShengli();
						if(YinYangShiHelper.findRiHeFang()){//������ڵ�ͼ�� �͵������25����
							sleep(1000);
							continue;
						}else if(YinYangShiHelper.findRiHeFang2()){
							sleep(1000);
							continue;
							
						}else 
						if(YinYangShiHelper.isYaoqifengyingSelect()){//������ڵ�ͼ�� �͵������25����
							if(YinYangShiHelper.findRiHeFang()){//������ڵ�ͼ�� �͵������25����
								sleep(1000);
								continue;
							}else if(YinYangShiHelper.findRiHeFang2()){
								sleep(1000);
								continue;
								
							}
							YinYangShiHelper.clickShuaxing();
							sleep(1000);
							System.out.println("���ˢ��");
							if(YinYangShiHelper.findYaoqify()){//������ڵ�ͼ�� �͵������25����
								sleep(1000);
								continue;
								
							}
							/*if(YinYangShiHelper.findRiHeFang()){//������ڵ�ͼ�� �͵������25����
								Thread.sleep(1000);
								continue;
								
							}else if(YinYangShiHelper.findRiHeFang2()){Thread.sleep(1000);
								continue;
								
							}*/
						}else if(YinYangShiHelper.isYaoqizhunbei()){//������ڵ�ͼ�� �͵������25����
							YinYangShiHelper.clickZhunbei();
						}else if(YinYangShiHelper.isBattleing()){//������ڵ�ͼ�� �͵������25����
							sleep(5000);
						}else if(YinYangShiHelper.isShengli()){//������ڵ�ͼ�� �͵������25����
							YinYangShiHelper.closeShengli();
							
						}else if(YinYangShiHelper.isYaoBattleEnd()){//������ڵ�ͼ�� �͵������25����
							YinYangShiHelper.closeShengli();
							
						}else if(YinYangShiHelper.isBattleEnd1()){
								
								WinIOVirtualMouse.RandomClick(728,415, 130, 30);//��ʼ�ƶ�
								//���ҵ�
								sleep(1000);
								WinIOVirtualMouse.RandomClick(728,415, 130, 30);//��ʼ�ƶ�
								
							
							
						}else if(YinYangShiHelper.isBattleEnd1()){
							
							WinIOVirtualMouse.RandomClick(728,415, 130, 30);//��ʼ�ƶ�
							//���ҵ�
							sleep(1000);
							WinIOVirtualMouse.RandomClick(728,415, 130, 30);//��ʼ�ƶ�
							
						}else if(YinYangShiHelper.isTingYuan()){//������ڵ�ͼ�� �͵������25����
							YinYangShiHelper.clickTingYuanZuDui();
						}else if(YinYangShiHelper.isYaoqifengyingUnSelect()){//������ڵ�ͼ�� �͵������25����
							YinYangShiHelper.clickYaoqifengying();
						}/*else
						if(YinYangShiHelper.findRiHeFang()){//������ڵ�ͼ�� �͵������25����
								
							
						}else if(YinYangShiHelper.findRiHeFang2()){
							
							
						}*/
						else {
							sleep(3000);
							System.out.println("ʲô��û�ҵ�");
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

