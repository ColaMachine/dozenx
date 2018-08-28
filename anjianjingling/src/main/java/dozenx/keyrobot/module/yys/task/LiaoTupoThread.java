package dozenx.keyrobot.module.yys.task;


import device.WinIOVirtualMouse;
import org.xvolks.jnative.util.User32;
import util.YinYangShiHelper;

public class LiaoTupoThread extends BaseThread{
		
		
		public LiaoTupoThread(String key, int time, String windowName) {
			super(key, time, windowName);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			int width=300;
			int height=122;
			
			
			int startX=825;
			int startY=181;
			int nums[][]={
					
					{819,177,237,208,133,},
					{1110,185,210,196,178,},
					{807,311,212,199,182,},
					{1112,312,211,197,180,},
					{794,426,211,197,180,},
					{1094,430,212,199,182,},
					{801,553,213,199,183,},
					{1100,549,212,198,181,		}	};
			
int clicks[][]={
					
					{	707,369,250,194,103,},
					{1007,365,150,58,46,},
					{703,489,217,130,48,},
					{1021,498,203,181,156,},
					{705,610,176,88,43,},
					{1007,606,150,58,46,},
					{705,449,179,90,44,},
					{1007,449,210,142,82,},
			};
			while(isRunning){
				try {
					if(User32.GetWindowText(User32.GetForegroundWindow()).contains(this.windowName)){
						//VirtualKeyBoard.KeyPress(VKMapping.toScanCode(this.key));
						if(YinYangShiHelper.isYaoqizhunbei()){
							YinYangShiHelper.clickZhunbei();
							sleep(5000);
						}else 
						if(YinYangShiHelper.isBattleing()){
							sleep(5000);
						}else if(YinYangShiHelper.isBattleEnd()){
							YinYangShiHelper.closeShengli();
						}else if(YinYangShiHelper.isBattleEnd1()){
							YinYangShiHelper.closeShengli();
						}else if(YinYangShiHelper.isBattleFailed()){
							YinYangShiHelper.closeShengli();
						}
						else{
							Ok:
							for(int x=1;x>=0;x--){
								for(int y=3;y>=0;y--){
									
									System.out.println("x:"+(startX+width*x)  +"y:"+(startY+height*y));
									boolean result = YinYangShiHelper.PixelEqual(new int[]{startX+width*x,startY+height*y,205,188,168});
									
									System.out.println("���û"+result);
									//boolean result = YinYangShiHelper.PixelEqual(nums[y*2+x]);
									if(result){//697,472,217,134,48,  2*305=610
										
										if(YinYangShiHelper.isLiaotupoSelectEnemy()){
											//�ص�ѡ��ҳ��
											System.out.println("�ص�ѡ��ҳ��");
											WinIOVirtualMouse.click(500,270);//ѡ�ж��� ��������
											sleep(3000);
										}else{
											System.out.println("�ж�Ϊδѡ��ҳ��");
										}
										if(!YinYangShiHelper.isLiaotupoSelectEnemy()){
	System.out.println("x"+x+"y"+y+"��û�д��");
											
											//VirtualMouse.RandomClick(nums[y*2+x][0], nums[y*2+x][1],40,40);
											System.out.println(String.format("ѡ����˵��%d %d ",nums[y*2+x][0], nums[y*2+x][1]));//�������
											WinIOVirtualMouse.click(nums[y*2+x][0], nums[y*2+x][1]);//ѡ�ж��� ��������
											//388,351,203,181,156, ���׼����ť	
											sleep(4000);
											//������
											System.out.println("����������"+(clicks[y*2+x][0])+" "+(clicks[y*2+x][1]));//�������
											WinIOVirtualMouse.click(clicks[y*2+x][0],clicks[y*2+x][1]);
											sleep(6000);
											break Ok;
										}else{
											System.out.println("�ж�Ϊѡ��ҳ��");
										}
										
										
										
										
									}else{
										System.out.println("x"+x+"y"+y+"�����");
									}
								
								}
							}
							
							
						//	VirtualMouse.click(688,211);
							//388,351,203,181,156, ���׼����ť
							//Thread.sleep(2000);
							//������
							
						//	712,369
							//System.out.println("������"+(388+x*width)+" "+(351+height*y));
						//	VirtualMouse.RandomClick(712,369,40,40);
							//Thread.sleep(3000);
						
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

