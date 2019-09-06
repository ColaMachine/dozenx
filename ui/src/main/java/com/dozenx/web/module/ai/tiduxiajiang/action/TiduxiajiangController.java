package com.dozenx.web.module.ai.tiduxiajiang.action;

import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.StringUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 18:13 2018/11/21
 * @Modified By:
 */
@Controller
public class TiduxiajiangController  {
    /**
     * 日志
     **/
    private Logger logger = LoggerFactory.getLogger(TiduxiajiangController.class);


    public static int[][] getGrayPicture(String filename) throws FileNotFoundException, IOException {
        File file = new File(filename);
        BufferedImage originalImage = ImageIO.read(new FileInputStream(file));
        originalImage.getColorModel();
        System.out.println(originalImage.getColorModel());

        int green = 0, red = 0, blue = 0, rgb;
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();
        int[][] heights = new int[imageHeight][imageWidth];
        for (int i = originalImage.getMinX(); i < imageWidth; i++) {
            for (int j = originalImage.getMinY(); j < imageHeight; j++) {
                //ͼƬ�����ص���ʵ�Ǹ�����������������forѭ������ÿ�����ؽ��в���
                Object data = originalImage.getRaster().getDataElements(i, j, null);//��ȡ�õ����أ�����object���ͱ�ʾ

                red = originalImage.getColorModel().getRed(data);
                blue = originalImage.getColorModel().getBlue(data);
                green = originalImage.getColorModel().getGreen(data);
                heights[j][i] = red;
            }

        }
        return heights;

    }

    public static float qiuDaoA0(float a0, float a1, List<Pointf> exampleList) {
        float sum = 0;
        for (Pointf pointf : exampleList) {
            sum += hanshu(pointf.x, a0, a1) - pointf.y;
        }
        return sum / exampleList.size();
    }


    public static float qiuDaoA1(float a0, float a1, List<Pointf> exampleList) {
        float sum = 0;
        for (Pointf pointf : exampleList) {
            sum += (hanshu(pointf.x, a0, a1) - pointf.y) * pointf.x;
        }
        return sum / exampleList.size();
    }

    public static float hanshu(float x, float a0, float a1) {
        return a0 + a1 * x;
    }

    public static float costFunction(float a0, float a1, List<Pointf> exampleList) {
        float sum = 0;
        float m = exampleList.size();
        for (Pointf point : exampleList) {
            sum += (hanshu(point.x, a0, a1) - point.y) * (hanshu(point.x, a0, a1) - point.y);
        }
        return sum / m / 2;
    }

    @Test
    public void test() {
        String pngPath = "C:\\Users\\dozen.zhang\\Desktop\\test.png";
//        File file = new File(pngPath);
        List<Pointf>  exampleList =new ArrayList<Pointf>();
//
//
//        int minDistance =10;
       // try {
        File file = new File(pngPath);


        float minValue = 0.0005f;
        try {
//            int[][] matrix = getGrayPicture(pngPath);
//            for(int x=0;x<matrix[0].length;x++){
//                for(int y=matrix.length-1;y>=0;y--){
//                    if(matrix[y][x]<10){//说明他是黑色
//                        boolean taijin=false;
//                        for(Pointf point:exampleList){
//                            //如果距离太近不算
//
//                            if(Math.abs(point.x *point.x+ point.y*point.y  -  x*x-y*y ) <minDistance*minDistance ){
//                                taijin=true;
//                                break;
//                            }
//
//                        }
//
//                        if(!taijin){
//                            exampleList.add(new Pointf(x,matrix.length-y));
//                        }
//
//                        if(exampleList.size()>30){
//                            System.out.println("distance 不够长 导致样本过多 请调整distance:"+exampleList.size());
//                            System.exit(0);
//                        }
//                    }
//                }
//            }
//            if(exampleList.size()==0){
//                System.out.println("样本为空 结束");
//                System.exit(0);
//            }
//            System.out.println("example size:"+exampleList.size());
//
//            for(Pointf point: exampleList){
//                System.out.println("point "+point);
//            }
            exampleList.clear();
            exampleList.add(new Pointf(1, 1));
            exampleList.add(new Pointf(2, 2));
            exampleList.add(new Pointf(3, 3));
            //
            System.out.println("得到样本之后开始训练");


            float a0 = 0, a1 = 0;
            float step = 0.1f;
            int index = 0;
            while (true) {
                float xielv0 = qiuDaoA0(a0, a1, exampleList);
                float xielv1 = qiuDaoA1(a0, a1, exampleList);

                System.out.println("xielv0:" + xielv0 + " xielv1:" + xielv1);
                a0 = a0 - step * xielv0;
                a1 = a1 - step * xielv1;
                ;
                System.out.println("theta_0:" + a0 + "  theta_1:" + a1);
                float cost = costFunction(a0, a1, exampleList);
                System.out.println("costFUnction:" + cost);
                if (cost < minValue) {
                    System.out.println(" final result theta_0:" + a0 + " theta_1:" + a1);
                    break;
                }


                System.out.println("第"+(index++)+"次");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
//            BufferedImage image = ImageIO.read(file);
            //获取灰度图片


//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}