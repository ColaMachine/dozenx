package dozenx.keyrobot.module.yys.task;

import device.WinIOVirtualKeyBoard;
import device.WinIOVirtualMouse;
import org.xvolks.jnative.util.User32;
import dozenx.keyrobot.sound.PlayMusic;
import util.VKMapping;
import util.YinYangShiHelper;

public class GuiwangThread extends BaseThread {

    public GuiwangThread(String key, int time, String windowName) {
        super(key, time, windowName);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void run() {
        long nowTime = 0;
        long lastTime = 0;
        long battleBeginTime = 0;
        while (isRunning) {
            try {
                if (User32.GetWindowText(User32.GetForegroundWindow())
                        .contains(this.windowName)) {// ���������ʦ�Ĵ���


                    sleep(5);
                    nowTime = System.currentTimeMillis();
                    ProcessCJGW();
                    YinYangShiHelper.processTeamViewer();
                    YinYangShiHelper.processJinbiXieZhu();
                    if (YinYangShiHelper.isBattleing()) {// �����ս����
                        this.lastFightTime = System.currentTimeMillis();
                        // ���ҵ�
                        // VirtualMouse.RandomClick(965,247,42,54);
                        // System.out.println("1miao =====================");
                        // YinYangShiHelper.processDashe();
                        sleep(1000);
//						if (battleBeginTime == 0) {
//							battleBeginTime = System.currentTimeMillis();
//						} else {
//							int jiange = (int) (nowTime - battleBeginTime) / 1000;
//							System.out.println("���뿪ʼ" + jiange);
//
//							if (jiange == 1 || jiange == 11 || jiange == 22
//									|| jiange == 24) {
//								VirtualMouse.RandomClick(623, 214, 40, 40);
//								VirtualMouse.RandomClick(623, 214, 40, 40);
//							}
//						}

                    } else
                    /*
					 * if (YinYangShiHelper.isFaXianChaojiGuiWang()) { // TODO
					 * ���ŷ��ֹ������� // PlayMusic christmas = new PlayMusic();
					 * christmas.play(); Thread.sleep(1000); //
					 * YinYangShiHelper.clickJieshoushoudongZudui(); //
					 * Thread.sleep(3000); // continue; } else if
					 * (YinYangShiHelper.isTianleiSelect()) {// ������ڵ�ͼ�� //
					 * �͵������25����
					 * 
					 * if (YinYangShiHelper.isLouChengSelectPanel()) {
					 * YinYangShiHelper.clickCloseLouChengSelectPanel();
					 * Thread.sleep(1000); } // ˢ�°�ť if
					 * (YinYangShiHelper.isTianleiShiZuidui()) {
					 * YinYangShiHelper.clickJiaru(); } else {
					 * YinYangShiHelper.clickShuaxing();
					 * 
					 * } } else if (YinYangShiHelper.isCJGWfail()) {
					 * YinYangShiHelper.closeShengli(); } else
					 *//*
						 * if (YinYangShiHelper.isTingYuan()) {
						 * Thread.sleep(1000); if
						 * (YinYangShiHelper.isZidongyaoqing()) {
						 * YinYangShiHelper.clickJieshouZidongZudui(); } else if
						 * (YinYangShiHelper.isZaiciyaoqing()) {
						 * YinYangShiHelper.clickJieshoushoudongZudui(); } else
						 * {
						 * 
						 * if (YinYangShiHelper.isTingYuan()) {
						 * Thread.sleep(1000); if
						 * (YinYangShiHelper.isZidongyaoqing()) {
						 * YinYangShiHelper.clickJieshouZidongZudui(); } else if
						 * (YinYangShiHelper.isZaiciyaoqing()) {
						 * YinYangShiHelper .clickJieshoushoudongZudui(); } }
						 * YinYangShiHelper.clickTingYuanZuDui();
						 * Thread.sleep(1000);
						 * YinYangShiHelper.clickYuhuninzuduipanel();
						 * Thread.sleep(1000);
						 * YinYangShiHelper.clickYuhun6inzuduipanel();
						 * Thread.sleep(1000);
						 * YinYangShiHelper.clickChuangjianduiwu();
						 * Thread.sleep(1000);
						 * YinYangShiHelper.clickChuangjianBtn(); } }else
						 */if (YinYangShiHelper.isKaishiDuiWu()) {// ���½ǵİ�ť�ǻ�ɫ��

                        sleep(500);

						/*
						 * if (YinYangShiHelper.isFaXianChaojiGuiWang()) { //
						 * TODO ���ŷ��ֹ������� //
						 * 
						 * YinYangShiHelper.clickJieshoushoudongZudui();
						 * PlayMusic christmas = new PlayMusic();
						 * christmas.play(); Thread.sleep(3000); // ������볬������ }
						 */
                        // �ȴ�ȫ��׼������

                        // while(!YinYangShiHelper.isAllReady()){
                        // Thread.sleep(500);
                        // }

                        WinIOVirtualMouse.click(1018, 616);// ��ʼ�ƶ�
                        // ���ҵ�
                    } else if (YinYangShiHelper.isYaoqizhunbei()) {
                        YinYangShiHelper.clickZhunbei();
                        // Thread.sleep(1000);
                        // VirtualMouse.RandomClick(705,361, 40, 40);
                        // Thread.sleep(27000);
                        // VirtualMouse.RandomClick(705,361, 40, 40);
                    } else if (YinYangShiHelper.ishun10Shengli()) {

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
					/*
					 * 
					 */
                    else if (YinYangShiHelper.isBattleEnd1()) {

                        WinIOVirtualMouse.RandomClick(728, 415, 130, 30);// ��ʼ�ƶ�
                        // ���ҵ�
                        sleep(1000);
                        WinIOVirtualMouse.RandomClick(728, 415, 130, 30);// ��ʼ�ƶ�
                        sleep(3000);

                    } else if (YinYangShiHelper.isJixuzudui()) {

                        YinYangShiHelper.clickJixuzudui();

                        // Thread.sleep(6000);
                        // VirtualMouse.RandomClick(705,361, 40, 40);
                        //
                        // Thread.sleep(32000);
                        // VirtualMouse.RandomClick(705,361, 40, 40);
                        // lastFightTime = System.currentTimeMillis();
                    }/*
					 * else if (YinYangShiHelper.isYaoqizhunbei()) {// �Ƿ���׼��״̬��
					 * YinYangShiHelper.clickZhunbei();// ���׼��
					 * Thread.sleep(5000); }
					 */ else if (YinYangShiHelper.isBattleEnd()) {

                        WinIOVirtualMouse.click(672, 542);// ��ʼ�ƶ�
                        battleBeginTime = 0;
                        WinIOVirtualMouse.RandomClick(672, 542, 100, 100);// ��ʼ�ƶ�
                        sleep(500);
						/*
						 * VirtualMouse.RandomClick(672, 542, 100, 100);// ��ʼ�ƶ�
						 * Thread.sleep(55000);
						 */
                        // Thread.sleep(1500);
                        // VirtualMouse.RandomClick(672, 542, 100, 100);// ��ʼ�ƶ�
                        // lastFightTime = System.currentTimeMillis();
                    } else {
                        // if (System.currentTimeMillis() - lastFightTime <
                        // 2000) {
                        // VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
                        // Thread.sleep(200);
                        // VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
                        // Thread.sleep(500);
                        // VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
                        // Thread.sleep(200);
                        // VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
                        // Thread.sleep(200);
                        // VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
                        //
                        // Thread.sleep(500);
                        // VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
                        // Thread.sleep(200);
                        // VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
                        // Thread.sleep(200);
                        // VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
                        // Thread.sleep(200);
                        //
                        // Thread.sleep(1500);
                        // VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
                        // Thread.sleep(200);
                        // VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
                        // Thread.sleep(200);
                        // VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
                        // Thread.sleep(200);
                        //
                        // Thread.sleep(1500);
                        // VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
                        // Thread.sleep(200);
                        // VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
                        // Thread.sleep(200);
                        // VirtualMouse.RandomClick(675, 129, 130, 30);// ��ʼ�ƶ�
                        // Thread.sleep(200);
                        // }

                        System.out.println("ʲô��û��⵽");
                    }
                } else {
                    sleep(5000);
                    WinIOVirtualMouse.RandomClick(158, 313, 5, 5);//���̽��
                    sleep(3000);
                    WinIOVirtualKeyBoard.KeyPress(VKMapping.toScanCode("enter"));

                }
                sleep(1000);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                isRunning = false;
            }
        }

    }

    public static void ProcessCJGW() throws InterruptedException, Exception {
        if (YinYangShiHelper.isFaXianChaojiGuiWang()) {// ������ֳ���������������
            // TODO ���ŷ��ֹ�������
            //
            PlayMusic christmas = new PlayMusic();
            christmas.play();
            sleep(1000);
            YinYangShiHelper.clickJieshoushoudongZudui();
            sleep(1000);
            YinYangShiHelper.clickJieshoushoudongZudui();
            sleep(1000);
            // continue;
        } else if (YinYangShiHelper.isTianleiSelect()) {// ������ڵ�ͼ��
            // �͵������25����

            if (YinYangShiHelper.isLouChengSelectPanel()) {
                YinYangShiHelper.clickCloseLouChengSelectPanel();
                sleep(1000);
            }
            // ˢ�°�ť
            if (YinYangShiHelper.isTianleiShiZuidui()) {
                YinYangShiHelper.clickJiaru();
            } else {
                YinYangShiHelper.clickShuaxing();

            }
        } else if (YinYangShiHelper.isTingYuan()) {// �����ͥԺ�Ļ�
            sleep(1000);// ��Ϣ1��
            if (YinYangShiHelper.isZidongyaoqing()) {// �Զ��������
                YinYangShiHelper.clickJieshouZidongZudui();
            } else if (YinYangShiHelper.isZaiciyaoqing()) {
                YinYangShiHelper.clickJieshoushoudongZudui();
            } else {// �����������һ��ͥԺ����

                YinYangShiHelper.clickTingYuanZuDui();
                sleep(1000);
//				YinYangShiHelper.clickYuhuninzuduipanel();
                sleep(1000);
                YinYangShiHelper.clickJuexingtianlei();
                sleep(1000);
                WinIOVirtualMouse.Drag(569, 547, 561, 261, 2000);
                sleep(1000);
                WinIOVirtualMouse.RandomClick(558, 551, 40, 40);

                sleep(1000);
                WinIOVirtualMouse.click(558, 551);

                sleep(3000);
                YinYangShiHelper.clickChuangjianduiwu();
                sleep(3000);

                sleep(1000);
                YinYangShiHelper.clickChuangjianBtn();
                System.out.println("����");
            }
        } else if (YinYangShiHelper.isCJGWfail()
                || YinYangShiHelper.isCJGWsucc()) {// �����������ʧ�ܻ��߳ɹ�
            YinYangShiHelper.closeShengli();
            // �뿪
            sleep(3000);
            // break;
            return;
            // ������Ҫ�ж��Ƿ���Ҫ���Ž��еڶ�����ս��

        } else if (YinYangShiHelper.isGuiWangPanel()) {//
            // ���½ǵİ�ť�ǻ�ɫ�� // continue;
            if (YinYangShiHelper.isNoCJGW() || YinYangShiHelper.isNoCJGW2()) {// ���û�й������뿪
                sleep(1000);
                YinYangShiHelper.closeCJGWpanel();
                sleep(1000);
                YinYangShiHelper.clickExit();
                return;// ����ͥԺ
            }
            sleep(500);
            // ����й����žż���
            YinYangShiHelper.clickChaojiguiwangTiaozhan();

            // ���׼����ʼ
            sleep(1000);
            if (YinYangShiHelper.isYaoqizhunbei()) {
                YinYangShiHelper.clickZhunbei();

                sleep(1000);

                while (true) {// һֱ�ȵ������ɹ�����ʧ���뿪
                    if (YinYangShiHelper.isCJGWfail()
                            || YinYangShiHelper.isCJGWsucc()) {
                        YinYangShiHelper.closeShengli();// �뿪
                        sleep(3000);
                        break;// ������Ҫ�ж��Ƿ���Ҫ���Ž��еڶ�����ս��

                    }
                    System.out.println("��զ������");
                    sleep(3000);
                }// ���û�г��������� ���뿪
                if (YinYangShiHelper.isNoCJGW() || YinYangShiHelper.isNoCJGW2()) {
                    sleep(1000);
                    YinYangShiHelper.closeCJGWpanel();
                    sleep(1000);
                    YinYangShiHelper.clickExit();
                    return;// ����ͥԺ
                } //
                // �ж��Ƿ���Ҫ������ս

            }

        }
    }
}
