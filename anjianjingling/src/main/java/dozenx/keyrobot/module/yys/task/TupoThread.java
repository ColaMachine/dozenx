package dozenx.keyrobot.module.yys.task;


import device.WinIOVirtualMouse;
import util.User32;
import util.YinYangShiHelper;

public class TupoThread extends BaseThread{
		
		public TupoThread(String key, int time, String windowName) {
			super(key, time, windowName);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			int width=305;
			int height=122;
			int nums[][]={
//					{442,172,105,98,90,},
//					{751,179,106,100,91,},
//					{1055,182,106,100,92,},
//					{447,295,106,99,90,},
//					{752,298,106,99,91,},
//					{1045,295,106,99,90,},
//					{439,416,212,199,182,},
//					{744,416,106,99,91,},
//					{1058,419,106,100,91,},
					
					//{508,168,201,182,160},
					{506,170,207,192,173,},
					
					{545,169,201,182,160},
					{850,171,202,184,163},
					{241,288,201,182,160},
					{814,279,201,182,160},
					{849,288,201,183,161},
					//{236,409,201,182,160},
					{501,412,213,200,184,},
					{543,409,201,182,160},
					{849,408,201,183,161}
			};
			
			while(isRunning){
				try {
					if(User32.GetWindowText(User32.GetForegroundWindow()).contains(this.windowName)){
						//VirtualKeyBoard.KeyPress(VKMapping.toScanCode(this.key));
						
						YinYangShiHelper.processJinbiXieZhu();
						if(YinYangShiHelper.isYaoqizhunbei()){//�Ƿ���׼��״̬��
							YinYangShiHelper.clickZhunbei();//���׼��
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
							
							//�Ƿ���ͻ�ƽ���
						
						int count=0;
						ok:
						for(int x=0;x<3;x++){
							for(int y=0;y<3;y++){
								//int xpos = 238+x*width;
							//	int ypos = 160+y*height;
//								int[] nums =new int[]{//203,186,165, ��δ��� 103,95,85 �Ǵ��
//										xpos,ypos,207,191,171
//										
//								};
								//System.out.println(xpos+":"+ypos);
								
								if(YinYangShiHelper.isTupoSelect()){
									//VirtualMouse.RandomClick(165,240, 30, 39);//����δѡ��״̬
									WinIOVirtualMouse.click(215,371);
								//	215,371,33,26,21,
									sleep(500);
								}
								if(YinYangShiHelper.isTupoUnSelect()){
									boolean result = YinYangShiHelper.PixelEqual(nums[y*3+x]);
									if(result){//697,472,217,134,48,  2*305=610
										WinIOVirtualMouse.RandomClick(373+x*width, 227+y*height,40,40);
										
										//388,351,203,181,156, ���׼����ť
										sleep(2000);
										//������
										System.out.println("������"+(388+x*width)+" "+(351+height*y));
										WinIOVirtualMouse.RandomClick(388+x*width,351+height*y,40,40);
										sleep(3000);
										System.out.println("x"+x+"y"+y+"��û�д��");
										
										break ok;
										
									}else{System.out.println("x"+x+"y"+y+"�Ѿ������");
										count ++;
									}
								}
							
							}
						}
						if(count==9){
							System.out.println("ȫ�������� ��ˢ��");
						}
						}
						//966,243,194,194,220,
						
						
					}else{
//						Thread.sleep(5000);
//						VirtualMouse.RandomClick(158,313,5,5);//���̽��
					}
					sleep(time*100);
					
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					isRunning =false;
				}
			}
			
		}
		
		
	}

