package dozenx.keyrobot.module.yys.task;

import device.WinIOVirtualMouse;
import org.xvolks.jnative.util.User32;
import util.YinYangShiHelper;

public class hunshiThread extends BaseThread {

	public hunshiThread(String key, int time, String windowName) {
		super(key, time, windowName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		long nowTime = 0;
		long lastTime = 0;
		long battleBeginTime=0;
		while (isRunning) {
			try {
				if (User32.GetWindowText(User32.GetForegroundWindow())
						.contains(this.windowName)) {
					nowTime=System.currentTimeMillis();
					YinYangShiHelper.processCommon();
					 if (YinYangShiHelper.isBattleing()) {
						this.lastFightTime = System.currentTimeMillis();
						// ���ҵ�
						// VirtualMouse.RandomClick(965,247,42,54);
//						System.out.println("1miao =====================");
						YinYangShiHelper.processDashe();
						//Thread.sleep(1000);
						if(battleBeginTime==0){
							battleBeginTime=System.currentTimeMillis();
						}else{
							int jiange = (int)( nowTime-battleBeginTime)/1000;
							System.out.println("���뿪ʼ"+jiange);
							
							if(jiange==1|| jiange==11 || jiange>22&&jiange<29  ){
								WinIOVirtualMouse.RandomClick(611,123, 40, 40);
								WinIOVirtualMouse.RandomClick(611,123, 40, 40);
							}
						}
					
					} else
					/*if (YinYangShiHelper.isFaXianChaojiGuiWang()) {
						// TODO ���ŷ��ֹ�������
						//
						PlayMusic christmas = new PlayMusic();
						christmas.play();
						Thread.sleep(1000);
						// YinYangShiHelper.clickJieshoushoudongZudui();
						// Thread.sleep(3000);
						// continue;
					} else if (YinYangShiHelper.isTianleiSelect()) {// ������ڵ�ͼ��
																	// �͵������25����

						if (YinYangShiHelper.isLouChengSelectPanel()) {
							YinYangShiHelper.clickCloseLouChengSelectPanel();
							Thread.sleep(1000);
						}
						// ˢ�°�ť
						if (YinYangShiHelper.isTianleiShiZuidui()) {
							YinYangShiHelper.clickJiaru();
						} else {
							YinYangShiHelper.clickShuaxing();

						}
					} else if (YinYangShiHelper.isCJGWfail()) {
						YinYangShiHelper.closeShengli();
					} else*/  /*if (YinYangShiHelper.isTingYuan()) {
						Thread.sleep(1000);
						if (YinYangShiHelper.isZidongyaoqing()) {
							YinYangShiHelper.clickJieshouZidongZudui();
						} else if (YinYangShiHelper.isZaiciyaoqing()) {
							YinYangShiHelper.clickJieshoushoudongZudui();
						} else {

							if (YinYangShiHelper.isTingYuan()) {
								Thread.sleep(1000);
								if (YinYangShiHelper.isZidongyaoqing()) {
									YinYangShiHelper.clickJieshouZidongZudui();
								} else if (YinYangShiHelper.isZaiciyaoqing()) {
									YinYangShiHelper
											.clickJieshoushoudongZudui();
								}
							}
							YinYangShiHelper.clickTingYuanZuDui();
							Thread.sleep(1000);
							YinYangShiHelper.clickYuhuninzuduipanel();
							Thread.sleep(1000);
							YinYangShiHelper.clickYuhun6inzuduipanel();
							Thread.sleep(1000);
							YinYangShiHelper.clickChuangjianduiwu();
							Thread.sleep(1000);
							YinYangShiHelper.clickChuangjianBtn();
						}
					}else*/ if (YinYangShiHelper.isKaishiDuiWu()) {// ���½ǵİ�ť�ǻ�ɫ��

						sleep(500);

					/*	if (YinYangShiHelper.isFaXianChaojiGuiWang()) {
							// TODO ���ŷ��ֹ�������
							//

							YinYangShiHelper.clickJieshoushoudongZudui();
							PlayMusic christmas = new PlayMusic();
							christmas.play();
							Thread.sleep(3000);
							// ������볬������
						}*/
						// �ȴ�ȫ��׼������

						// while(!YinYangShiHelper.isAllReady()){
						// Thread.sleep(500);
						// }

						WinIOVirtualMouse.click(1018, 616);// ��ʼ�ƶ�
						// ���ҵ�
					}  else if (YinYangShiHelper.isYaoqizhunbei()) {
						YinYangShiHelper.clickZhunbei();
//						Thread.sleep(1000);
//						VirtualMouse.RandomClick(705,361, 40, 40);
//						Thread.sleep(27000);
//						VirtualMouse.RandomClick(705,361, 40, 40);
					}
					else
					if (YinYangShiHelper.ishun10Shengli()) {

						WinIOVirtualMouse.RandomClick(675, 184, 130, 30);// ��ʼ�ƶ�
						// ���ҵ�
						sleep(200);
						WinIOVirtualMouse.RandomClick(675, 184, 130, 30);// ��ʼ�ƶ�
						sleep(500);
						WinIOVirtualMouse.RandomClick(675, 184, 130, 30);// ��ʼ�ƶ�
						sleep(200);
						WinIOVirtualMouse.RandomClick(675, 184, 130, 30);// ��ʼ�ƶ�
						sleep(500);
						WinIOVirtualMouse.RandomClick(675, 184, 130, 30);// ��ʼ�ƶ�
						sleep(200);
						WinIOVirtualMouse.RandomClick(675, 184, 130, 30);// ��ʼ�ƶ�

					}
					/* else if (YinYangShiHelper.isCJGWfail()
							|| YinYangShiHelper.isCJGWsucc()) {
						YinYangShiHelper.closeShengli();// �뿪
						Thread.sleep(3000);
						// break;//������Ҫ�ж��Ƿ���Ҫ���Ž��еڶ�����ս��

					} else if (YinYangShiHelper.isGuiWangPanel()) {// ���½ǵİ�ť�ǻ�ɫ��
						// continue;
						if (YinYangShiHelper.isNoCJGW()
								|| YinYangShiHelper.isNoCJGW2()) {
							Thread.sleep(1000);
							YinYangShiHelper.closeCJGWpanel();
							Thread.sleep(1000);
							YinYangShiHelper.clickExit();
							continue;// ����ͥԺ
						}
						Thread.sleep(500);
						YinYangShiHelper.clickChaojiguiwangTiaozhan();

						// ���׼����ʼ
						Thread.sleep(1000);
						if (YinYangShiHelper.isYaoqizhunbei()) {
							YinYangShiHelper.clickZhunbei();

							Thread.sleep(1000);

							while (true) {
								if (YinYangShiHelper.isCJGWfail()
										|| YinYangShiHelper.isCJGWsucc()) {
									YinYangShiHelper.closeShengli();// �뿪
									Thread.sleep(3000);
									break;// ������Ҫ�ж��Ƿ���Ҫ���Ž��еڶ�����ս��

								}
								System.out.println("��զ������");
								Thread.sleep(3000);
							}
							if (YinYangShiHelper.isNoCJGW()
									|| YinYangShiHelper.isNoCJGW2()) {
								Thread.sleep(1000);
								YinYangShiHelper.closeCJGWpanel();
								Thread.sleep(1000);
								YinYangShiHelper.clickExit();
								continue;// ����ͥԺ
							}
							// �ж��Ƿ���Ҫ������ս

						}

					}*/ else if (YinYangShiHelper.isBattleEnd1()) {

						WinIOVirtualMouse.RandomClick(728, 415, 130, 30);// ��ʼ�ƶ�
						// ���ҵ�
						sleep(1000);
						WinIOVirtualMouse.RandomClick(728, 415, 130, 30);// ��ʼ�ƶ�
						sleep(30000);

					} else if (YinYangShiHelper.isJixuzudui()) {

						YinYangShiHelper.clickJixuzudui();
						
//						Thread.sleep(6000);
//						VirtualMouse.RandomClick(705,361, 40, 40);
//						
//						Thread.sleep(32000);
//						VirtualMouse.RandomClick(705,361, 40, 40);
						// lastFightTime = System.currentTimeMillis();
					}/* else if (YinYangShiHelper.isYaoqizhunbei()) {// �Ƿ���׼��״̬��
						YinYangShiHelper.clickZhunbei();// ���׼��
						Thread.sleep(5000);
					} */else if (YinYangShiHelper.isBattleEnd()) {
						
						WinIOVirtualMouse.click(672, 542);// ��ʼ�ƶ�
						battleBeginTime=0;
						WinIOVirtualMouse.RandomClick(672, 542, 100, 100);// ��ʼ�ƶ�
						sleep(500);
						/*VirtualMouse.RandomClick(672, 542, 100, 100);// ��ʼ�ƶ�
						Thread.sleep(55000);*/
//						Thread.sleep(1500);
//						VirtualMouse.RandomClick(672, 542, 100, 100);// ��ʼ�ƶ�
						// lastFightTime = System.currentTimeMillis();
					} else {
//						if (System.currentTimeMillis() - lastFightTime < 2000) {
//							VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
//							Thread.sleep(200);
//							VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
//							Thread.sleep(500);
//							VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
//							Thread.sleep(200);
//							VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
//							Thread.sleep(200);
//							VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
//
//							Thread.sleep(500);
//							VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
//							Thread.sleep(200);
//							VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
//							Thread.sleep(200);
//							VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
//							Thread.sleep(200);
//
//							Thread.sleep(1500);
//							VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
//							Thread.sleep(200);
//							VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
//							Thread.sleep(200);
//							VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
//							Thread.sleep(200);
//
//							Thread.sleep(1500);
//							VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
//							Thread.sleep(200);
//							VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
//							Thread.sleep(200);
//							VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
//							Thread.sleep(200);
//						}

						System.out.println("ʲô��û��⵽");
					}
				}
				sleep(500);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				isRunning = false;
			}
		}

	}

}
