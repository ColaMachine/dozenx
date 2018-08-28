package dozenx.keyrobot.module.yys.task;

import device.WinIOVirtualMouse;
import org.xvolks.jnative.util.User32;
import util.ColorPicker;
import util.YinYangShiHelper;

import java.awt.Color;
import java.awt.Point;

public class FubenThread extends BaseThread {
	public FubenThread(String key, int time, String windowName) {
		super(key, time, windowName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {

		while (isRunning) {
			try {
				if (User32.GetWindowText(User32.GetForegroundWindow())
						.contains(this.windowName)) {

					System.out.println("�µ��ֻ�");
					YinYangShiHelper.processCommon();
					
					// VirtualKeyBoard.KeyPress(VKMapping.toScanCode(this.key));
					//GuiwangThread.ProcessCJGW();
					//lastTime= System.currentTimeMillis();
//					if (YinYangShiHelper.isGouXie()) {// ������ڵ�ͼ�� �͵������25����
//						System.out.println("isGouXie���ֻ�");
//					} else

						YinYangShiHelper.processKaishiDuiWu();
					YinYangShiHelper.porcessZhunbei();
					if (YinYangShiHelper.isMap()) {// ������ڵ�ͼ�� �͵������25����

//						YinYangShiHelper.processFenNuShiJu();
						YinYangShiHelper.processNianShou();

						if (YinYangShiHelper.isDaDituBaoXiang()) {
							sleep(3000);
							YinYangShiHelper.baoxiangLingquSure();
						}
//						if (YinYangShiHelper.isDaDituYaoqi()) {
//							Thread.sleep(3000);
//							YinYangShiHelper.zuduitiaozhan();
//							Thread.sleep(3000);
//							YinYangShiHelper.chuangjianduiwu();
//						}
//						Thread.sleep(5000);
						if (YinYangShiHelper.entry25FuBen())

							lastFightTime = System.currentTimeMillis();
					} else if (YinYangShiHelper.isFubenEntry()) {// ������ڵ�ͼ��
																	// �͵������25����
						WinIOVirtualMouse.click(930, 555);// ���̽��

						sleep(2000);
					}/*
					 * else if(YinYangShiHelper.isFuBenEntry()){
					 * VirtualMouse.click(930,555);//���̽�� lastFightTime =
					 * System.currentTimeMillis(); }
					 */else if (YinYangShiHelper.isShengli()) {
						// VirtualMouse.RandomClick(619,576,50,50);//
						YinYangShiHelper.closeShengli();

					} else if (YinYangShiHelper.isFuBenJieweiHuodeJiangli()) {
						// VirtualMouse.RandomClick(619,576,50,50);//
						YinYangShiHelper.closeShengli();

					} else if (YinYangShiHelper.isFuBen()) {
						// ���ҵ�
						Long startTime = System.currentTimeMillis();

						Point point = ColorPicker.searchColorAtY(316, 1241,
								241, 246, new Color(46, 24, 11));
						// Point point=
						// ColorPicker.searchColorAtY(316,1241,300,310,new
						// Color(46,24,11));
						// if(point==null)
						// point=
						// ColorPicker.searchColorAtY(116,1241,241,248,new
						// Color(67,139,171));
						// point= ColorPicker.searchColorAtY(961,196,463,464,new
						// Color(46,24,11));
						// if(point==null)
						// point=
						// ColorPicker.searchColorAtY(116,1241,186,187,new
						// Color(67,139,171));
						// if(point==null)
						// point=
						// ColorPicker.searchColorAtY(116,1241,401,402,new
						// Color(46,24,11));
						// if(point==null)
						// point=
						// ColorPicker.searchColorAtY(116,1241,188,189,new
						// Color(237,216,202));
						// if(point==null)
						// point=
						// ColorPicker.searchColorAtY(116,1241,326,327,new
						// Color(46,24,11));
						//
						// if(point==null)
						// point=
						// ColorPicker.searchColorAtY(116,1241,202,203,new
						// Color(46,24,11));
						//
						// if(point==null)
						// point=
						// ColorPicker.searchColorAtY(116,1241,187,188,new
						// Color(46,24,11));
						// if(point==null)
						// point=
						// ColorPicker.searchColorAtY(116,1241,332,333,new
						// Color(46,24,11));
						// if(point==null)
						// point=
						// ColorPicker.searchColorAtY(116,1241,276,277,new
						// Color(46,24,11));
						// if(point==null)
						// point=
						// ColorPicker.searchColorAtY(116,1241,349,350,new
						// Color(46,24,11));

						Long endTime = System.currentTimeMillis();
						System.out.println("��Χ���ķ�ʱ��:" + (endTime - startTime));
						if (point == null) {
							Long intervalTime = System.currentTimeMillis()
									- lastFightTime;
							if (intervalTime > 10000) {
								// �뿪����

								System.out.println("����10����");
								for (int i = 0; i < 3; i++) {
									if (YinYangShiHelper.isMap()) {
										break;
									}
									if (!YinYangShiHelper.openBaoxiang()) {
										break;
									}
									sleep(1000);
								}
								// 255,242,202,
								// 112,320,255,255,239,
								// 1244,525,56,48,59,

								// �뿪����ǰ �������

								// 255,242,202,�뿪����
								System.out.println("�ȴ�5��");
								sleep(5000);
								// �ȴ��뿪���� �ص����ͼ
								// if(YinYangShiHelper.isFuBen()){
								// System.out.println("�����뿪����");
								// VirtualMouse.click(157,130);//��ʼ�ƶ�
								// Thread.sleep(1000);
								// VirtualMouse.RandomClick(728,415, 130,
								// 30);//��ʼ�ƶ�
								// Thread.sleep(3000);
								// }
								// Thread.sleep(3000);
								// �����ͼ���б������ȡ

							} else {
								System.out.println("kaishi�ƶ�");
								// VirtualMouse.click(979,429);//��ʼ�ƶ�
								WinIOVirtualMouse.RandomClick(979, 586, 20, 5);// ��ʼ�ƶ�

								System.out
										.println("�����ϴ���սʱ��"
												+ (System.currentTimeMillis() - lastFightTime));
							}
						} else {
							System.out.println("��ʼ����");
							WinIOVirtualMouse.click(point.x, point.y);// ��ʼ����
							sleep(10000);
						}
					} else if (YinYangShiHelper.isBattleEnd1()) {
						System.out.println("ս��jiesu1");
						WinIOVirtualMouse.RandomClick(728, 415, 130, 30);// ��ʼ�ƶ�
						// ���ҵ�
						sleep(1000);
						WinIOVirtualMouse.RandomClick(728, 415, 130, 30);// ��ʼ�ƶ�

					} else if (YinYangShiHelper.isBattleing()) {
						System.out.println("����ս����");
						// ���ҵ�
						
						sleep(1000);
					} else if (YinYangShiHelper.isBattleEnd()) {
						System.out.println("ս������");
						WinIOVirtualMouse.click(672, 542);// ��ʼ�ƶ�
					
						WinIOVirtualMouse.RandomClick(672, 542, 100, 100);// ��ʼ�ƶ�
						lastFightTime = System.currentTimeMillis();
					} else {
						System.out.println("ʲô��û��⵽");
					}

					// 966,243,194,194,220,

				}
				sleep(time * 100);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				isRunning = false;
			}
		}

	}

}
