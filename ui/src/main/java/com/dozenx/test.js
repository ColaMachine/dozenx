


    var tingyuan_color=[ 223, 627, 89, 93, 142,
                                        330, 635, 219, 215, 212,
                                        458, 635, 193, 183, 175,
                                        578, 637, 216, 93, 20,
                                        715, 627, 157, 127, 118];
   function isTingYuan() {
        var result = ColorUtil.PixelEqual(tingyuan_color);
        if (result) {
            logger.info("庭院");
        }
        return result;
    }


    /**
     * 如果是自动邀请组队的话
     *
     * @return
     * @throws Exception
     */
      var zidongyaoqing_color=[ 223, 627, 89, 93, 142,
                                             330, 635, 219, 215, 212,
                                             458, 635, 193, 183, 175,
                                             578, 637, 216, 93, 20,
                                             715, 627, 157, 127, 118];
    function isZidongyaoqing()  {

         var result = ColorUtil.PixelEqual(zidongyaoqing_color);
                if (result) {
                    logger.info("自动邀请");
                }
                return result;
    }

    /**
     * 如果是手动请求组队的话
     *
     * @return
     * @throws Exception
     */
 var zaiciyaoqing_color=[   150, 284, 223, 110, 91,
                                           209, 292, 144, 120, 97,
                                           241, 291, 90, 179, 99,
                                           293, 295, 231, 217, 199];
    function isZaiciyaoqing()  {
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
       var result = ColorUtil.PixelEqual(zaiciyaoqing_color);
              if (result) {
                  logger.info("再次邀请");
              }
              return result;
    }

     var zudui_color=[   245, 243, 156, 122, 93,
                                               275, 257, 133, 123, 111,
                                               242, 307, 120, 80, 47,
                                               274, 386, 174, 160, 144];


   function isZuDui() {
        int[] nums = new int[]{

        };
        var result = ColorUtil.PixelEqual(tingyuan_color);
               if (result) {
                   logger.info("正在组队");
               }
               return result;
    }

    public static boolean isYaoqifengyingSelect() throws Exception {
        int[] nums = new int[]{
                256, 586, 254, 207, 125,
		/*245,243,156,122,93,
		275,257,133,123,111,
		242,307,120,80,47,
		274,386,174,160,144,*/
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("妖气封印选中");
        }
        return result;
    }

    /**
     * 天雷有没有选中
     *
     * @return
     * @throws Exception
     */
    public static boolean isTianleiSelect() throws Exception {
        int[] nums = new int[]{
                255, 513, 255, 202, 123,
                258, 533, 255, 204, 124,
                295, 535, 253, 216, 129,
                349, 531, 173, 140, 89,
                370, 537, 255, 203, 123,
                401, 509, 255, 204, 124,
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("天雷选中");
        }
        return result;
    }

    /**
     * 左侧有弹框提示
     *
     * @return
     * @throws Exception
     */
    public static boolean isFaXianChaojiGuiWang() throws Exception {


        int[] nums = new int[]{
//				271,302,179,76,39,
//				236,303,42,38,34,
//				202,287,228,218,207,
//				460,291,27,82,105,
//				487,287,191,96,46,
//				385,287,206,142,118,
//				204,289,227,217,205,
//				189,295,227,217,205,
                369, 291, 208, 141, 117,
                411, 292, 212, 112, 87,
                447, 294, 219, 184, 162,
                477, 294, 131, 93, 78,
                135, 305, 208, 143, 67,
                117, 301, 178, 163, 147,
                165, 299, 132, 110, 92,
                470, 310, 93, 55, 52,
                446, 321, 63, 49, 62,
                485, 291, 94, 79, 92,
//				247,294,222,211,198,
//				293,314,217,178,159,
//				347,311,169,118,100,
//				358,298,173,118,100,
//				372,307,210,145,122,
//				145,313,208,140,63,
//				264,292,219,206,192,
//				416,298,211,117,92,
//				454,295,231,151,74,
//				474,292,32,50,89,
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("发现超级鬼王");
        }
        return result;
    }

    /**
     * 超级鬼王胜利
     *
     * @return
     * @throws Exception
     */
    public static boolean isCJGWsucc() throws Exception {
        int[] nums = new int[]{
                534, 161, 118, 23, 15,
                521, 190, 152, 28, 18,
                577, 190, 158, 28, 18,
                645, 186, 168, 142, 93,
                691, 168, 124, 90, 45,
                779, 161, 212, 187, 137,
                840, 149, 243, 235, 203,
                660, 237, 229, 59, 43,
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("鬼王胜利");
        }
        return result;
    }

    /**
     * 超级鬼王挑战点击
     *
     * @throws Exception
     */
    public static void clickChaojiguiwangTiaozhan() throws Exception {
        VirtualMouse.RandomClick(1124, 551, 40, 40);
        Thread.sleep(1000);
    }

    /**
     * 接受手动组队
     *
     * @throws Exception
     */
    public static void clickJieshoushoudongZudui() throws Exception {
        VirtualMouse.RandomClick(240, 308, 5, 5);
        Thread.sleep(1000);
    }

    /**
     * 接受自动组队
     *
     * @throws Exception
     */
    public static void clickJieshouZidongZudui() throws Exception {
        VirtualMouse.RandomClick(325, 292, 5, 5);
        Thread.sleep(1000);
    }

    /**
     * 鬼王的查询界面
     *
     * @return
     * @throws Exception
     */
    public static boolean isGuiWangPanel() throws Exception {


        int[] nums = new int[]{
                361, 161, 46, 4, 7,
                367, 183, 31, 8, 14,
                426, 151, 89, 13, 16,
                431, 189, 209, 187, 125,
                495, 163, 101, 52, 56,
                504, 172, 211, 201, 202,
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("鬼王界面");
        }
        return result;
    }

    /**
     * 如果没有超级鬼王可以挑战了
     *
     * @return
     * @throws Exception
     */

    public static boolean isNoCJGW() throws Exception {


        int[] nums = new int[]{
                271, 231, 189, 170, 153,
                393, 231, 189, 170, 153,
                482, 232, 189, 170, 153,
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("超级鬼王打完了");
        }
        return result;
    }

    public static boolean isNoCJGW2() throws Exception {


        int[] nums = new int[]{
                1115, 599, 11, 7, 4,
                1056, 603, 12, 8, 5,
                1041, 630, 11, 6, 4,
                1090, 631, 9, 6, 5,
                1150, 631, 109, 71, 44,
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("超级鬼王打完");
        }
        return result;
    }

    public static boolean isTianleiShiZuidui() throws Exception {
        int[] nums = new int[]{
//				620,240,209,204,188,
//				664,265,223,205,179,
                1072, 254, 121, 68, 34,
                1077, 249, 108, 63, 35,
                1072, 254, 121, 68, 34,
                1077, 249, 108, 63, 35,
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("天雷101组队");
        }
        return result;
    }

    /**
     * 庭院组队 选中左侧项目
     *
     * @return
     * @throws Exception
     */

    public static boolean isYaoqifengyingUnSelect() throws Exception {
        int[] nums = new int[]{

                266, 580, 232, 214, 193,

	/*	245,243,156,122,93,
		275,257,133,123,111,
		242,307,120,80,47,
		274,386,174,160,144,*/
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("妖气封印未选中");
        }
        return result;
    }

    public static boolean isFubenEntry() throws Exception {

        int[] nums = new int[]{
                379, 533, 53, 26, 13,
                426, 536, 193, 116, 47,
                501, 534, 241, 226, 210,
                554, 532, 201, 196, 199,
                702, 543, 202, 149, 81,
                971, 545, 113, 88, 55,
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("正在地图");
        }
        return result;

    }

    public static boolean isMap() throws Exception {
        int[] nums = new int[]{
                179, 652, 96, 36, 102,
                279, 647, 140, 69, 47,
                394, 663, 140, 115, 93,
                460, 662, 115, 103, 134,
                565, 666, 144, 50, 137,
                649, 666, 22, 13, 13,

        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("正在地图");
        }
        return result;
    }

    public static boolean processGouXie() throws Exception {
        int[] nums = new int[]{
                726, 510, 115, 53, 175,
                724, 496, 231, 70, 55,
                724, 494, 227, 44, 13,
                712, 503, 223, 186, 163,
                724, 523, 137, 69, 209,
                691, 518, 94, 30, 150,
                690, 486, 119, 24, 199,

        };


        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            VirtualMouse.RandomClick(850, 422, 24, 24);
            System.out.println("发现勾协");
        }
        return result;
    }


    public static boolean isFuBenEntry() throws Exception {
//		int[] nums =new int[]{
//				781,368,238,236,230,
//				839,360,79,53,51,
//				863,350,197,188,178,
//				955,446,94,89,98,
//				703,473,95,91,102,
//				795,542,61,36,45,
//				853,545,59,35,45,
//				977,555,243,178,94,
//
//
//
//		};
        int[] nums = new int[]{
                907, 560, 243, 178, 94,
                924, 552, 243, 178, 94,
                962, 552, 60, 50, 38,
                983, 556, 243, 178, 94,
                379, 364, 62, 12, 11,
                358, 370, 105, 66, 57,
                359, 489, 64, 13, 13,
                1085, 437, 90, 58, 37,
                1079, 327, 164, 93, 30,
                1084, 312, 201, 139, 82,
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("正在副本入口");
        }
        return result;


    }

    public static boolean isJuexingEntry() throws Exception {


        int[] nums = new int[]{

                665, 453, 151, 123, 83,
                713, 453, 42, 23, 12,
                518, 464, 200, 176, 148,
                712, 508, 243, 178, 94,
                913, 505, 243, 178, 94,
                345, 638, 169, 121, 76,
                513, 629, 73, 62, 60,
                704, 621, 71, 58, 57,
                916, 626, 55, 28, 113,/*
				759,327,214,200,185,
				845,329,250,109,81,
				800,259,236,131,112,
				696,512,241,177,93,
				721,512,243,178,94,
				908,512,247,187,99,*/
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("正在副本入口");
        }
        return result;

    }

    public static Color baoxiangColor = new Color(152, 55, 27);

    /**
     * 战斗开始准备界面
     *
     * @return
     * @throws Exception
     */
    public static boolean isYaoqizhunbei() throws Exception {
        int[] nums = new int[]{532, 678, 54, 34, 50,
//				377,683,41,29,39,
//				511,677,52,40,54,
//				/*685,677,49,37,47,
//				927,681,44,31,43,
//				1111,682,195,142,89,*/
//				1175,673,218,175,116,
                484, 688, 54, 40, 49,
                555, 687, 55, 42, 61,
                764, 693, 50, 38, 52,
                902, 684, 41, 30, 42,
                300, 683, 42, 31, 49,
                1107, 685, 191, 134, 81,
        };


        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("正在妖气准备中");
        }
        return result;

		/**/
    }

    public static boolean ProcessTuPoZhunbei() throws Exception {
        int[] nums = new int[]{
//				1104,672,224,188,129,

                1104, 656, 206, 156, 99,
                1106, 689, 191, 134, 81,
                1181, 688, 53, 34, 44,
        };


        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("正在突破准备中");

            clickZhunbei();
        }
        return result;

		/**/
    }

    public static boolean porcessZhunbei() throws Exception {
        if (isYaoqizhunbei()) {
            System.out.println("正在妖气准备中 点击开始");
            clickZhunbei();
            return true;
        }
        return false;
    }


    public static boolean isDoujizhunbei() throws Exception {
        int[] nums = new int[]{
                149, 96, 142, 130, 91,
                209, 96, 214, 196, 161,
                271, 94, 206, 189, 153,
                170, 555, 39, 32, 25,
                176, 652, 37, 30, 34,
        };


        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("正在斗鸡准备中");
        }
        return result;

		/**/
    }


    public static boolean isJuexingZhunbei() throws Exception {
        int[] nums = new int[]{532, 678, 54, 34, 50,
                424, 684, 47, 30, 43,
                355, 690, 33, 19, 24,
                1126, 664, 199, 147, 93,
                1177, 678, 209, 161, 103,
                1100, 690, 242, 219, 173,
                1085, 691, 233, 204, 152,

        };


        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("正在觉醒准备中");
        }
        return result;


    }

    public static boolean openBaoxiang() throws Exception {
        Point point1 = ColorPicker.searchColorAtY1(354, 849, 325, 515, baoxiangColor);

        if (point1 != null) {
            System.out.println("找到了宝物的位置了：x" + point1.x + "y" + point1.y);
            VirtualMouse.click(point1.x, point1.y);//打开宝箱
            Thread.sleep(500);
//		VirtualMouse.click(point1.x,point1.y);//打开宝箱
//		Thread.sleep(3000);

            //开始出现领取后的物品画面

            //关闭它
            VirtualMouse.RandomClick(654, 561, 50, 50);//开始移动

            Thread.sleep(1000);
//		VirtualMouse.RandomClick(604,558,50,50);//开始移动
//
//		Thread.sleep(1000);
//		VirtualMouse.RandomClick(604,558,50,50);//开始移动
//
//		Thread.sleep(1000);
//		VirtualMouse.RandomClick(604,558,50,50);//开始移动

            return true;

        }
        return true;
    }


    public static boolean PixelEqual(int[] nums) throws Exception {

        for (int i = 0; i < nums.length / 5; i += 5) {
            if (!ColorPicker.screenColorike(new Point(nums[i], nums[i + 1]), new Color(nums[i + 2], nums[i + 3], nums[i + 4]))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isShengli() throws Exception {
        int[] nums = new int[]{
                526, 288, 66, 49, 34,
                540, 289, 180, 151, 86,
                550, 292, 54, 43, 25,
                565, 296, 145, 116, 66,
                586, 301, 42, 31, 21,
                611, 297, 76, 56, 38,
                621, 297, 64, 46, 25,
                642, 296, 74, 55, 37,
                654, 299, 149, 119, 67,
                679, 302, 71, 52, 36,
                699, 289, 23, 18, 12,
                713, 288, 36, 27, 18,
                724, 295, 52, 39, 27,
                773, 299, 42, 31, 21,
        };


        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("胜利界面");
        }
        return result;

    }

    public static boolean ishun10Shengli() throws Exception {
        int[] nums = new int[]{
                581, 174, 184, 149, 128,
                564, 182, 144, 26, 17,
                575, 219, 176, 85, 67,
                747, 193, 156, 127, 89,
                883, 182, 197, 171, 118,

        };


        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("胜利界面");
        }
        return result;

    }


    public static boolean isDaDituBaoXiang() throws Exception {
        int[] nums = new int[]{

//		552,413,128,21,16,
//		563,411,77,2,5,
//		536,403,157,16,18,
//		538,423,204,118,133,

                152, 248, 174, 43, 25,
                174, 243, 239, 114, 84,
                185, 249, 177, 51, 25,
                187, 275, 163, 107, 30,
                154, 275, 29, 19, 18,
        };


        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("正在副本获取奖励中");
            VirtualMouse.click(152, 248);
        }
        return result;


    }

    public static boolean isDaDituYaoqi() throws Exception {
        int[] nums = new int[]{
                169, 257, 123, 16, 2,
                154, 246, 73, 29, 23,
                141, 244, 32, 20, 19,
                138, 279, 53, 45, 40,
                201, 275, 26, 16, 16,
                189, 254, 89, 1, 0,
        };


        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("大地图妖气");
            VirtualMouse.click(152, 248);
        }
        return result;


    }

    public static boolean baoxiangLingquSure() throws Exception {
        int[] nums = new int[]{
                701, 534, 42, 23, 18,
                681, 544, 177, 50, 29,
                660, 566, 85, 35, 14,
                642, 579, 83, 7, 8,
                613, 476, 227, 143, 18,
                607, 524, 183, 74, 32,
                605, 546, 147, 182, 224,
                618, 548, 78, 9, 9,
        };
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("正在地圖領取寶箱確認");


            VirtualMouse.RandomClick(605, 546, 41, 31);
        }
        return result;


    }

    public static boolean zuduitiaozhan() throws Exception {


        VirtualMouse.RandomClick(1045, 523, 41, 31);

        return true;


    }

    public static boolean chuangjianduiwu() throws Exception {


        VirtualMouse.RandomClick(707, 520, 41, 31);

        return true;


    }

    public static boolean isFuBenJieweiHuodeJiangli() throws Exception {
        int[] nums = new int[]{

                424, 320, 134, 126, 122,
                418, 318, 131, 122, 119,
                418, 353, 179, 166, 164,
                436, 407, 139, 103, 62,
                655, 287, 30, 22, 15,
                615, 279, 163, 124, 66,
                793, 285, 43, 27, 13,

        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("正在副本结尾获得奖励");
            VirtualMouse.click(552, 413);
        }
        return result;


    }


    public static boolean isNianShou() throws Exception {
        int[] nums = new int[]{
                155, 241, 192, 140, 68,
                187, 250, 51, 27, 12,
                186, 278, 69, 61, 94,
                162, 273, 55, 39, 53,
                157, 257, 127, 45, 51,
                141, 257, 34, 22, 21,

        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("发现年兽");
            //VirtualMouse.click(552,413);
        }
        return result;
    }

    public static boolean isFenNuShiJu() throws Exception {
        int[] nums = new int[]{
                151, 249, 60, 16, 67,
                188, 252, 135, 46, 37,
                191, 268, 103, 44, 28,
                162, 273, 123, 35, 23,
                153, 259, 131, 21, 28,
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("愤怒石距");
            //VirtualMouse.click(552,413);
        }
        return result;
    }


    public static void processNianShou() throws Exception {
        if (isNianShou()) {
            processDadituFaxian();
        }
    }

    public static void processFenNuShiJu() throws Exception {
        if (isFenNuShiJu()) {
            processDadituFaxian();
        }
    }

    public static void processDadituFaxian() throws Exception {
        Thread.sleep(3000);
        VirtualMouse.RandomClick(151, 249, 16, 16);

        Thread.sleep(3000);
        System.out.println("进入大地图的发现");
        VirtualMouse.RandomClick(998, 541, 16, 16);

        Thread.sleep(3000);
        System.out.println("创建队伍");
        VirtualMouse.RandomClick(617, 521, 16, 16);
    }


    public static boolean isFuBen() throws Exception {
        int[] nums = new int[]{



/*				149,572,161,61,96,
				149,583,141,48,82,
				147,593,63,55,68,
				124,625,86,79,110,
				126,649,73,62,78,
				138,683,64,63,90,
				150,701,60,59,89,
				178,685,53,45,88,
				146,686,65,65,91*/
                131, 649, 77, 69, 90,
                133, 676, 64, 62, 94,
                154, 694, 63, 64, 88,
//				150,588,106,38,61,
//				120,623,69,65,106,
//				136,652,101,94,123,
//				145,696,55,53,87,
                174, 674, 44, 44, 47

        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("正在副本中");
            //VirtualMouse.click(552,413);
        }
        return result;


    }


    public static boolean isBattle() throws Exception {


        int[] nums = new int[]{
                162, 661, 129, 104, 92,
                176, 660, 96, 80, 62,
                144, 593, 52, 38, 25


        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("正在战斗中");
        }
        return result;


    }


    public static boolean isBattleing() throws Exception {
        System.out.println("检测是否在在战斗中");
        int[] nums = new int[]{
                160, 655, 124, 116, 103,
                167, 632, 222, 210, 194,


        };
        int[] nums2 =
                new int[]{
                        159, 652, 248, 243, 224,
                        824, 685, 46, 30, 28,
                        1230, 582, 97, 91, 115,
                        1226, 603, 93, 87, 109,};
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums) || PixelEqual(nums2);
        if (result) {
            System.out.println("正在战斗中");
        }
        return result;


    }

    public static boolean ProcessBattleing() throws Exception {
        if (isBattleing()) {
            return true;
        }
        return false;
    }

    public static boolean isBattleEnd() throws Exception {
        int[] nums = new int[]{
				/*584,450,124,44,4,
				589,480,118,58,36,
				607,492,116,94,69,
				709,484,36,25,21,*/
                696, 471, 201, 180, 151,
                725, 451, 47, 34, 29,
                688, 543, 185, 119, 55,
                706, 538, 165, 38, 25,


        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("战斗结束");
        }
        return result;


    }

    public static boolean hasJinyanjiacheng() throws Exception {
        int[] nums = new int[]{
                257, 652, 127, 164, 211,
                255, 659, 117, 157, 211,
        };

        boolean result = PixelEqual(nums);
        if (result) {
            VirtualMouse.RandomClick(600, 600, 40, 40);
            Thread.sleep(50);
            VirtualMouse.RandomClick(600, 600, 40, 40);
            Thread.sleep(50);
            VirtualMouse.RandomClick(600, 600, 40, 40);
            Thread.sleep(50);
            VirtualMouse.RandomClick(600, 600, 40, 40);
            Thread.sleep(50);
            System.out.println("战斗结束1");
        }


        return result;
    }

    public static boolean isBattleEnd1() throws Exception {
        int[] nums = new int[]{
				/*557,237,142,26,17,
				490,272,225,213,190,
				530,229,168,87,72,*/
                568, 242, 154, 28, 18,
                579, 225, 192, 172, 149,


        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("战斗结束1");
        }
        return result;


    }

    /**
     * //右下角的按钮是黄色的
     *
     * @return
     * @throws Exception
     */
    public static boolean isKaishiDuiWu() throws Exception {
        int[] nums = new int[]{
                1018, 616, 243, 178, 94,
                1014, 612, 243, 178, 94,
                1053, 610, 243, 178, 94,
                1060, 611, 243, 178, 94,

        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("开始队伍");
        }
        return result;


    }

    public static boolean processKaishiDuiWu() throws InterruptedException, Exception {
        if (YinYangShiHelper.isKaishiDuiWu()) {// 右下角的按钮是黄色的

            Thread.sleep(500);

            if (YinYangShiHelper.isFaXianChaojiGuiWang()) {
                // TODO 播放发现鬼往呻吟
                //

                YinYangShiHelper.clickJieshoushoudongZudui();
                PlayMusic christmas = new PlayMusic();
                christmas.play();
                Thread.sleep(3000);
                // 点击加入超级鬼王
            }
            // 等待全部准备好了

            // while(!YinYangShiHelper.isAllReady()){
            // Thread.sleep(500);
            // }

            VirtualMouse.click(1018, 616);// 开始移动
            // 查找点
            Thread.sleep(1000);
            return true;

        }
        return false;
    }

    public static boolean isAllReady() throws Exception {
        int[] nums = new int[]{
                925, 349, 162, 155, 147,
                942, 357, 162, 155, 147,
                949, 388, 162, 155, 147,

        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("开始队伍");
        }
        return !result;
    }

    public static boolean isKaishiYuhun() throws Exception {
        int[] nums = new int[]{
                923, 503, 243, 178, 94,
                962, 517, 243, 178, 94,
                989, 526, 248, 188, 99,

        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("开始队伍");
        }
        return result;


    }

    public static boolean isDoujiSuccess() throws Exception {
        int[] nums = new int[]{
                532, 152, 121, 24, 16,
                524, 186, 159, 28, 18,
                560, 185, 158, 28, 18,
                645, 174, 163, 138, 90,
                780, 160, 204, 181, 132,
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("斗鸡陈宫");
        }
        return result;

    }

    public static boolean isDoujiFailed() throws Exception {
        int[] nums = new int[]{
                518, 143, 78, 70, 86,
                501, 180, 29, 8, 11,
                551, 173, 91, 82, 101,
                648, 236, 162, 147, 102,
                758, 166, 170, 161, 146,
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("斗鸡失败");
        }
        return result;

    }

    public static boolean isBattleFailed() throws Exception {
        int[] nums = new int[]{
                512, 239, 99, 90, 111,
                524, 229, 86, 79, 97,
                528, 206, 74, 66, 81,
                556, 233, 91, 82, 101,
                688, 184, 216, 206, 173,
                755, 214, 185, 178, 163,
                857, 241, 171, 163, 147,
                697, 497, 192, 52, 52,
                477, 521, 77, 75, 75,
                859, 509, 225, 211, 203,

        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("战斗失败");
        }
        return result;


    }


    public static boolean isTupoSelect() throws Exception {
        int[] nums = new int[]{
                152, 250, 41, 31, 50,
                157, 453, 8, 7, 23,
                155, 579, 16, 17, 28,

        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("突破选中");
        }
        return result;


    }

    public static boolean isTupoUnSelect() throws Exception {
        int[] nums = new int[]{
                165, 240, 93, 69, 119,
                154, 384, 26, 27, 62,
                175, 534, 36, 35, 69,


        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("突破未选中");
        }
        return result;


    }

    public static boolean isYaoBattleEnd() throws Exception {
        int[] nums = new int[]{

                609, 462, 193, 106, 23,
                635, 533, 158, 178, 221,
                638, 559, 106, 11, 13,
                719, 562, 12, 1, 1,
                674, 592, 46, 2, 6,
				/*651,594,25,1,10,
				755,525,212,137,22,
				707,473,202,181,152,*/

        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("战斗结束1");
        }
        return result;


    }


    public static boolean findRiHeFang() throws Exception {

        //
        int[] nums = new int[]{
                595, 237, 41, 38, 34,

        };

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("日和坊第一行发现");
            VirtualMouse.RandomClick(1004, 245, 40, 40);
        }
        return result;
    }

    public static boolean findYaoqify() throws Exception {

        //

        VirtualMouse.RandomClick(1004, 245, 40, 40);

        return true;
    }

    public static boolean findRiHeFang2() throws Exception {

        //595,237,41,38,34,
        int[] nums = new int[]{
                595, 327, 40, 37, 33,

        };

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("日和坊第2行发现");
            VirtualMouse.RandomClick(1004, 339, 40, 40);
        }
        return result;
    }

    /**
     * 在庭院中点击组队按钮
     *
     * @throws Exception
     */
    public static void clickTingYuanZuDui() throws Exception {
        VirtualMouse.RandomClick(315, 615, 40, 40);
    }

    /**
     * 在庭院中点击组队按钮
     *
     * @throws Exception
     */
    public static void clickJiaru() throws Exception {
        VirtualMouse.RandomClick(1018, 244, 40, 20);
    }

    /**
     * 在组队界面点击妖气封印
     *
     * @throws Exception
     */
    public static void clickYaoqifengying() throws Exception {
        VirtualMouse.RandomClick(261, 572, 40, 40);
    }

    /**
     * 选中觉醒天雷
     *
     * @throws Exception
     */
    public static void clickJuexingtianlei() throws Exception {
        VirtualMouse.RandomClick(326, 452, 40, 10);
    }


    /**
     * 选中庭院中的御魂选项
     *
     * @throws Exception
     */
    public static void clickYuhuninzuduipanel() throws Exception {
        VirtualMouse.RandomClick(294, 579, 40, 10);
        System.out.println("选中御魂项目");
    }

    /**
     * 选中御魂6
     *
     * @throws Exception
     */
    public static void clickYuhun6inzuduipanel() throws Exception {
        VirtualMouse.RandomClick(498, 577, 10, 1);
        System.out.println("选中御魂6");
    }

    public static void clickChuangjianduiwu() throws Exception {
        VirtualMouse.RandomClick(976, 614, 40, 10);
        System.out.println("创建队伍");
    }

    public static void clickChuangjianBtn() throws Exception {
        VirtualMouse.RandomClick(854, 580, 40, 10);
        System.out.println("创建");
    }


    /**
     * 选中觉醒天雷
     *
     * @throws Exception
     */
    public static void clickCloseLouChengSelectPanel() throws Exception {

		/*
		 * 574,228,255,208,123,
536,228,255,208,123,
512,228,101,77,39,
480,224,255,208,123,
		 */
        VirtualMouse.RandomClick(677, 337, 40, 10);
    }

    /**
     * 天雷有没有选中
     *
     * @return
     * @throws Exception
     */
    public static boolean isLouChengSelectPanel() throws Exception {
        int[] nums = new int[]{
                574, 228, 255, 208, 123,
                536, 228, 255, 208, 123,
                512, 228, 101, 77, 39,
                480, 224, 255, 208, 123,
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("楼层选择");
        }
        return result;
    }

    /**
     * 在组队界面点击刷星按钮
     *
     * @throws Exception
     */
    public static void clickShuaxing() throws Exception {

        //点击刷新
        VirtualMouse.RandomClick(483, 618, 40, 40);
    }

    public static void closeHuodeJiangli() throws Exception {
        // TODO Auto-generated method stub
        VirtualMouse.RandomClick(635, 530, 51, 51);//点击探索
        System.out.println("关闭获得奖里");
    }

    public static void judgeAndProccessShengli() throws Exception {

        if (YinYangShiHelper.isShengli() || YinYangShiHelper.ishun10Shengli()) {
            //VirtualMouse.RandomClick(619,576,50,50);//
            YinYangShiHelper.closeShengli();

        }
    }

    public static void closeShengli() throws Exception {
        // TODO Auto-generated method stub
        VirtualMouse.RandomClick(680, 657, 50, 10);//点击探索
        System.out.println("关闭胜利");
    }

    /**
     * 关闭超级鬼王界面
     *
     * @throws Exception
     */
    public static void closeCJGWpanel() throws Exception {
        // TODO Auto-generated method stub
        VirtualMouse.RandomClick(1146, 131, 5, 5);//点击探索
        System.out.println("关闭超级鬼王界面");
    }

    /**
     * 从地图返回庭院界面
     *
     * @throws Exception
     */
    public static void clickExit() throws Exception {
        // TODO Auto-generated method stub
        VirtualMouse.RandomClick(158, 113, 5, 5);//点击探索
        System.out.println("地图返回庭院");
    }

    public static void clickZhunbei() throws Exception {
        VirtualMouse.RandomClick(1105, 524, 50, 50);//点击探索
    }

    /**
     * 进入25层副本
     *
     * @throws Exception
     */
    public static boolean entry25FuBen() throws Exception {

        VirtualMouse.click(1076, 499);//点击25层
        Thread.sleep(2000);
        if (YinYangShiHelper.isFuBenEntry()) {
            VirtualMouse.click(930, 555);//点击探索

            Thread.sleep(2000);
        }
        if (YinYangShiHelper.isFuBen()) {
            return true;
        }
        return false;

    }

    public static boolean entry26FuBen() throws Exception {

        VirtualMouse.RandomClick(1083, 605, 40, 4);//点击25层
        Thread.sleep(2000);
        if (YinYangShiHelper.isFuBenEntry()) {
            VirtualMouse.click(930, 555);//点击探索

            Thread.sleep(2000);
        }
        if (YinYangShiHelper.isFuBen()) {
            return true;
        }
        return false;

    }

    public static boolean isLiaotupoSelectEnemy() throws Exception {
        int nums[] = {
//		353,181,21,7,7,
//		383,186,59,27,7,
//		371,221,72,42,9,
//		368,255,11,3,1,
//		431,255,85,79,71,

                378, 331, 48, 2, 9,
                354, 430, 49, 3, 9,
                395, 570, 84, 77, 70,
        };

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("寮突破选中了敌人");
            VirtualMouse.RandomClick(1004, 339, 40, 40);
        }
        return result;
    }


    public static boolean isHunShiHuihe() throws Exception {
        int nums[] = {
                627, 336, 218, 196, 171,
                692, 342, 217, 188, 148,
                729, 352, 224, 196, 164,
        };

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("魂十回合");
        }
        return result;
    }

    public static boolean isHunShi1Huihe() throws Exception {
        int nums[] = {
                622, 392, 150, 132, 105,
                718, 417, 233, 202, 152,
                734, 412, 228, 192, 138,
        };

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("斗鸡入口");
            VirtualMouse.RandomClick(1073, 552, 40, 40);
        }
        return result;
    }


    public static boolean isHunShi3Huihe() throws Exception {
        int nums[] = {
                632, 369, 214, 184, 144,
                710, 409, 233, 207, 163,
                711, 409, 233, 207, 163,
        };

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("斗鸡入口");
            VirtualMouse.RandomClick(1073, 552, 40, 40);
        }
        return result;
    }

    public static boolean isHunShiErHuihe() throws Exception {
        int nums[] = {
                653, 342, 220, 200, 176,
                687, 396, 53, 48, 41,
                693, 434, 232, 196, 136,
                622, 443, 176, 168, 145,
//				643,353,215,187,150,
//				714,392,231,195,135,
//				704,406,116,103,70,
        };

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("斗鸡入口");
            VirtualMouse.RandomClick(1073, 552, 40, 40);
        }
        return result;
    }

    public static boolean isDoujiEntry() throws Exception {
        int nums[] = {
//				322,564,199,189,180,
//				927,581,176,167,159,
//				1058,596,224,196,153,
//				548,621,85,66,60,
                1043, 584, 214, 185, 148,
                1048, 617, 228, 196, 141,
                1068, 617, 178, 159, 127,
                1088, 597, 230, 197, 149,
        };

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("斗鸡入口");
            VirtualMouse.RandomClick(1073, 552, 40, 40);
        }
        return result;
    }

    public static boolean isShouDong() throws Exception {
        int nums[] = {
			/*	163,655,109,93,72,
				153,652,240,235,216,
				146,654,52,43,51,
				150,667,81,69,78,
				181,661,89,81,69,*/
                146, 651, 74, 64, 54,
                148, 662, 75, 68, 87,
                175, 654, 53, 44, 34,
        };

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("手动状态");
        }
        return result;
    }

    public static boolean porcessShouDong() throws Exception {

        if (isShouDong()) {
            VirtualMouse.RandomClick(163, 655, 20, 20);
            Thread.sleep(3000);
            return true;
        }
        return false;
    }

    public static boolean isLiaotupoUnSelectEnemy() throws Exception {
        int nums[] = {
                347, 188, 143, 41, 22,
                375, 188, 245, 204, 122,
//				373,234,117,45,19,
//				346,328,119,8,19,
//				399,433,140,43,53,
//				374,497,176,97,36,
        };

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("寮突破未选中敌人");
            //VirtualMouse.RandomClick(1004,339, 40, 40);
        }
        return result;
    }


    public static boolean isJixuzudui() throws Exception {


        int nums[] = {
                593, 306, 203, 181, 156,
                697, 305, 203, 181, 156,
                608, 380, 114, 95, 77,
                603, 444, 223, 104, 81,
                818, 451, 243, 178, 94
        };

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("isJixuzudui");
            //VirtualMouse.RandomClick(1004,339, 40, 40);
        }
        return result;
    }

    public static void clickJixuzudui() throws Exception {


        VirtualMouse.click(606, 381);
        Thread.sleep(1000);
        VirtualMouse.RandomClick(712, 438, 10, 11);
        Thread.sleep(1000);
    }

    public static boolean isShiju() throws Exception {
        int nums[] = {
                358, 563, 116, 25, 19,
                459, 563, 125, 79, 115,
                557, 582, 83, 47, 26,
                656, 582, 225, 182, 108,
                744, 566, 237, 184, 39,
        };

        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("寮突破未选中敌人");
            //VirtualMouse.RandomClick(1004,339, 40, 40);
        }
        return result;

    }


    /**
     * 天雷有没有选中
     *
     * @return
     * @throws Exception
     */
    public static boolean isCJGWfail() throws Exception {
        int[] nums = new int[]{
                523, 175, 78, 70, 87,
                502, 191, 81, 77, 95,
                505, 228, 105, 93, 116,
                551, 218, 91, 82, 101,
                756, 193, 181, 173, 158,
                855, 196, 219, 215, 203,
                921, 196, 175, 167, 153,
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("天雷选中");
        }
        return result;
    }

    public static boolean isDashe() throws Exception {
        int[] nums = new int[]{

                662, 114, 8, 42, 43,
                680, 134, 90, 126, 130,
                673, 129, 44, 19, 41
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("大蛇了");
        }
        return result;
    }


    public static void processDashe() throws InterruptedException, Exception {
        // TODO Auto-generated method stub
		/*if(isHunShiHuihe()){
			System.out.println("1回合 =====================");
			Thread.sleep(2000);
			VirtualMouse.RandomClick(623,214, 40, 40);
			VirtualMouse.RandomClick(623,214, 40, 40);
			Thread.sleep(5000);
		}*//*else if(isHunShiErHuihe()){
			System.out.println("2回合 =====================");
			Thread.sleep(2000);
			VirtualMouse.RandomClick(623,214, 40, 40);
		}else if(isHunShi3Huihe()){
			System.out.println("3回合 =====================");
			Thread.sleep(2000);
			VirtualMouse.RandomClick(623,214, 40, 40);
		}else */

        if (isDashe()) {


            //Thread.sleep(1000);
            System.out.println("大蛇回合 =====================");
            //Thread.sleep(2000);
            System.out.println("开始选中 =====================");
            VirtualMouse.RandomClick(619, 163, 4, 4);
            for (int i = 0; i < 10; i++)
                VirtualMouse.RandomClick(619, 163, 4, 4);
            System.out.println("28miao =====================");
            Thread.sleep(100);
            //Thread.sleep(500);

        }

    }


    public static boolean isJinbiXieZhu() throws Exception {
        int[] nums = new int[]{

                623, 503, 66, 67, 118,
                644, 501, 34, 31, 48,
                713, 506, 238, 224, 191,
                733, 500, 224, 190, 124,
                873, 437, 85, 177, 95,
        };
        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(nums);
        if (result) {
            System.out.println("大蛇了");
        }
        return result;
    }

    public static void processJinbiXieZhu() throws InterruptedException, Exception {
        if (isJinbiXieZhu()) {
            VirtualMouse.click(873, 437);
        }
    }

    public static void processCommon() throws InterruptedException, Exception {

        YinYangShiHelper.processJinbiXieZhu();

        YinYangShiHelper.processGouXie();
        YinYangShiHelper.processTeamViewer();
    }

    public static int[] teamviewerNums = new int[]{

            463, 321, 0, 163, 245,
            501, 329, 0, 173, 245,
            494, 366, 0, 148, 245,
    };

    public static void processTeamViewer() throws InterruptedException, Exception {


        //List<Integer> list =ArrayList.tnew ArrayList(nums);
        boolean result = PixelEqual(teamviewerNums);
        if (result) {
            System.out.println("teamviewer");
        }

        if (result) {

            VirtualMouse.click(885, 424);


        }

    }

    public static int[] JinsuSuccessNums = new int[]{

            521, 131, 200, 182, 161,
            497, 198, 223, 211, 188,
            545, 194, 202, 50, 32,
            578, 157, 153, 28, 18,
    };

    public static boolean processJinsuSuccess() throws InterruptedException, Exception {


        boolean result = PixelEqual(JinsuSuccessNums);
        if (result) {
            System.out.println("JinsuSuccessNums");
        }

        if (result) {

            closeShengli();


        }
        return result;

    }

    public static boolean processJinsuFail() throws InterruptedException, Exception {


        boolean result = PixelEqual(JinsuSuccessNums);
        if (result) {
            System.out.println("JinsuSuccessNums");
        }

        if (result) {

            closeShengli();


        }
        return result;

    }

//	cc198,226,242,


    public static int[] JinsuStartNums = new int[]{

            192, 138, 166, 212, 212,
            213, 177, 48, 145, 168,
            334, 154, 45, 101, 130,
            324, 192, 19, 6, 2,
    };

    public static boolean processJinsuStart() throws InterruptedException, Exception {


        boolean result = PixelEqual(JinsuStartNums);
        if (result) {
            VirtualMouse.RandomClick(1121, 569, 22, 26);
            System.out.println("JinsuStart");
        }

        return result;

    }

    public static boolean porcessJinSuZhunbei() throws Exception {


        int[] nums = new int[]{

                432, 674, 63, 49, 62,
                525, 681, 54, 41, 55,
                658, 693, 63, 48, 64,
                808, 679, 145, 140, 136,
                1132, 552, 255, 248, 228,
                1154, 671, 208, 159, 102,
                1081, 684, 231, 200, 147,
        };
        boolean result = PixelEqual(nums);
        if (result) {
            VirtualMouse.RandomClick(1129, 529, 22, 26);
            System.out.println("zhunbei");
        }

        return result;
    }


//cc198,226,242,
}


