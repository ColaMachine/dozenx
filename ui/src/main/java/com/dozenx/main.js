import java.awt.Color;
import java.awt.Point;

public class FubenThread extends BaseThread {
	public FubenThread(String key, int time, String windowName) {
		super(key, time, windowName);
		// TODO Auto-generated constructor stub
	}
var isRunning =true;
var windowName ="";
var lastFightTime =0;
function run(){
    while(isRunning){
        if(YYSUtil.GetWindowText().indexof(windowName)==-1){
            YYSUtil.Sleep(5000);
            YYSUtil.info("未检测到窗口");
            continue;
        }
        if (YYSUtil.isMap()) {
            	if (entry26FuBen())

                    lastFightTime = new Date().getTime();
                }
        }

    }
}
	@Override
	public void run() {
		boolean theBoss =false;
		
		while (isRunning) {
			try {
				if (User32.GetWindowText(User32.GetForegroundWindow())
						.contains(this.windowName)) {
					
					System.out.println("新的轮回");
					// YinYangShiHelper.processCommon();

					// VirtualKeyBoard.KeyPress(VKMapping.toScanCode(this.key));
					// GuiwangThread.ProcessCJGW();
					// lastTime= System.currentTimeMillis();
					// if (YinYangShiHelper.isGouXie()) {// 如果是在地图上 就点击进入25副本
					// System.out.println("isGouXie的轮回");
					// } else
				//	YinYangShiHelper.hasJinyanjiacheng();
					
					YinYangShiHelper.processCommon();
					YinYangShiHelper.processKaishiDuiWu();
					YinYangShiHelper.porcessZhunbei();
					if(YinYangShiHelper.hasJinyanjiacheng()){
						
					}else
					if (YinYangShiHelper.isMap()) {// 如果是在地图上 就点击进入25副本

						// YinYangShiHelper.processFenNuShiJu();
						YinYangShiHelper.processNianShou();

						if (YinYangShiHelper.isDaDituBaoXiang()) {
							Thread.sleep(3000);
							YinYangShiHelper.baoxiangLingquSure();
						}
						// if (YinYangShiHelper.isDaDituYaoqi()) {
						// Thread.sleep(3000);
						// YinYangShiHelper.zuduitiaozhan();
						// Thread.sleep(3000);
						// YinYangShiHelper.chuangjianduiwu();
						// }
						// Thread.sleep(5000);
						if (YinYangShiHelper.entry26FuBen())

							lastFightTime = System.currentTimeMillis();
					} else if (YinYangShiHelper.isFubenEntry()) {// 如果是在地图上
																	// 就点击进入25副本
						VirtualMouse.click(930, 555);// 点击探索

						Thread.sleep(2000);
					}/*
					 * else if(YinYangShiHelper.isFuBenEntry()){
					 * VirtualMouse.click(930,555);//点击探索 lastFightTime =
					 * System.currentTimeMillis(); }
					 */else if (YinYangShiHelper.isShengli()) {
						// VirtualMouse.RandomClick(619,576,50,50);//
						YinYangShiHelper.closeShengli();

					}/* else if (YinYangShiHelper.isFuBenJieweiHuodeJiangli()) {
						// VirtualMouse.RandomClick(619,576,50,50);//
						YinYangShiHelper.closeShengli();

					}*/ else if (YinYangShiHelper.isFuBen()) {
						
						if(!theBoss){
							
						
						// 查找点
						Long startTime = System.currentTimeMillis();
//						854,324,48,25,11,
						Point point = ColorPicker.searchColorAtY(316, 1241,
								324, 326, new Color(46, 24, 11));
						if(point==null){//表示查找boss 在不在
							
							 point=
							 ColorPicker.searchColorAtY(116,1241,206,208,new
							 Color(67,139,171));
							 if(point!=null){//表示查找boss 在不在
								 theBoss=true;
							 }
						}
						
						
						Long endTime = System.currentTimeMillis();
						System.out.println("范围检测耗费时间:" + (endTime - startTime));
						if (point == null) {
							Long intervalTime = System.currentTimeMillis()
									- lastFightTime;
//							if (intervalTime > 10000) {
//								// 离开副本
//
//								System.out.println("大于10秒了");
////								for (int i = 0; i < 3; i++) {
////									if (YinYangShiHelper.isMap()) {
////										break;
////									}
////									if (!YinYangShiHelper.openBaoxiang()) {
////										break;
////									}
////									Thread.sleep(1000);
////								}
//								// 255,242,202,
//								// 112,320,255,255,239,
//								// 1244,525,56,48,59,
//
//								// 离开副本前 点击宝物
//
//								// 255,242,202,离开副本
//								System.out.println("等待5秒");
//								Thread.sleep(5000);
//								// 等待离开副本 回到大地图
//								// if(YinYangShiHelper.isFuBen()){
//								// System.out.println("打算离开副本");
//								// VirtualMouse.click(157,130);//开始移动
//								// Thread.sleep(1000);
//								// VirtualMouse.RandomClick(728,415, 130,
//								// 30);//开始移动
//								// Thread.sleep(3000);
//								// }
//								// Thread.sleep(3000);
//								// 如果地图上有宝箱就领取
//
//							} else {
								System.out.println("kaishi移动");
								// VirtualMouse.click(979,429);//开始移动
								//VirtualMouse.RandomClick(979, 586, 20, 5);// 开始移动
								VirtualMouse.slider(600,500,200,500, 1000,5);
								System.out
										.println("距离上次作战时间"
												+ (System.currentTimeMillis() - lastFightTime));
//							}
						} else {
							System.out.println("开始攻击");
							VirtualMouse.click(point.x, point.y);// 开始攻击
							Thread.sleep(10000);
						}
						}else{
							
							System.out.println("正在等待副本奖励");
							Thread.sleep(2000);
							for (int i = 0; i < 3; i++) {
								if (YinYangShiHelper.isMap()) {
									break;
								}
								if (!YinYangShiHelper.openBaoxiang()) {
									break;
								}
								Thread.sleep(1000);
							}
							System.out.println("等待5秒");
							Thread.sleep(5000);
							theBoss=false;
							
						}
//						Point point = ColorPicker.searchColorAtY(316, 1241,
//								241, 246, new Color(46, 24, 11));
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

						
					} else if (YinYangShiHelper.isBattleEnd1()) {
						System.out.println("战斗jiesu13456");
						VirtualMouse.RandomClick(728, 415, 130, 30);// 开始移动
						// 查找点
						Thread.sleep(1000);
						VirtualMouse.RandomClick(728, 415, 130, 30);// 开始移动
						Thread.sleep(1000);
						VirtualMouse.RandomClick(728, 415, 130, 30);// 开始移动
						Thread.sleep(1000);
						VirtualMouse.RandomClick(728, 415, 130, 30);// 开始移动

					} else if (YinYangShiHelper.isBattleing()) {
						System.out.println("正在战斗中");
						// 查找点

						Thread.sleep(1000);
					} else if (YinYangShiHelper.isBattleEnd()) {
						System.out.println("战斗结束");
						VirtualMouse.click(672, 542);// 开始移动

						VirtualMouse.RandomClick(672, 542, 100, 100);// 开始移动
						lastFightTime = System.currentTimeMillis();
					} else {
						System.out.println("什么都没检测到");
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