package util;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class ColorPicker {
	
	
	public static Color getMouseColor() throws AWTException{
		  Color pixel = new Color(0,0,0);
		  Robot robot;
		  Point mousepoint;
		  int R,G,B;
		  MouseInfo mouseinfo;
		  robot = new Robot();
		  mousepoint = MouseInfo.getPointerInfo().getLocation();
		  pixel = robot.getPixelColor(mousepoint.x,mousepoint.y);
		  R = pixel.getRed();
		  G = pixel.getGreen();
		  B = pixel.getBlue(); 
		  System.out.println("R"+R+"G"+G+"B"+B);
		  return pixel;
	}
	
	public static Color getColor(int x,int y) throws AWTException{
		
		 
		  Color   pixel = robot.getPixelColor(x,y);
		
		  return pixel;
	}
	
	public static void main(String args[]){
//		try {
//		//	ColorPicker.getColor();
//		} catch (AWTException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	public static Robot robot ;
	static{
		
		try {
			robot=  new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean screenColorike(Point point, Color color) throws AWTException{
		
		Color pixel  = robot.getPixelColor(point.x,point.y);
		//System.out.println(pixel);
		
		//System.out.println("����color:"+color);
		//System.out.println("ʵ��color:"+pixel);
		if(Math.abs(color.getRed() - pixel.getRed())<10  && Math.abs(color.getGreen() -pixel.getGreen() )<10&& Math.abs(color.getBlue()- pixel.getBlue())<10){
		//if(color.getRed() == pixel.getRed()  && color.getGreen() == pixel.getGreen() && color.getBlue() == pixel.getBlue()){
			return true;
			
		}
		return false;
		
	}
	 public static void captureScreen(String fileName, String folder,int startX,int startY,int endX,int endY) throws Exception {
		 
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
	        Rectangle screenRectangle = new Rectangle(screenSize);
			 screenRectangle.x= startX;
			 screenRectangle.y= startY;
			 screenRectangle.width = endX-startX;
			 screenRectangle.height =endY - startY;
	        Robot robot = new Robot();
	        BufferedImage image = robot.createScreenCapture(screenRectangle);
	        // ��ͼ�����·�� 
	        File screenFile = new File(fileName);    
	        // ���·��������,�򴴽�  
	        if (!screenFile.getParentFile().exists()) {  
	            screenFile.getParentFile().mkdirs();  
	        } 
	        //�ж��ļ��Ƿ���ڣ������ھʹ����ļ�
	        if(!screenFile.exists()&& !screenFile .isDirectory()) {
	            screenFile.mkdir();
	        }
	        
	        File f = new File(screenFile, folder);        
	        ImageIO.write(image, "png", f);
	        //�Զ���
	        /*if (Desktop.isDesktopSupported()
	                 && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
	                    Desktop.getDesktop().open(f);*/
    }
	 
	 public static BufferedImage captureScreen(int startX,int startY,int endX,int endY) throws Exception {
		 Rectangle screenRectangle = new Rectangle();
		 screenRectangle.x= startX;
		 screenRectangle.y=  startY;
		 screenRectangle.width = endX-startX;
		 screenRectangle.height =endY -  startY;
		 
		 int red ;
         int green ;
         int blue;
         Object data;
		BufferedImage image = robot.createScreenCapture(screenRectangle);
		return image;
 }
	public static Point searchColorAtY(int beginX,int endX,int beginY,int endY ,Color color) throws AWTException {
		 Rectangle screenRectangle = new Rectangle();
		 screenRectangle.x= beginX;
		 screenRectangle.y=  beginY;
		 screenRectangle.width = endX-beginX;
		 screenRectangle.height =endY -  beginY;
		 
		 int red ;
         int green ;
         int blue;
         Object data;
		BufferedImage image = robot.createScreenCapture(screenRectangle);
		for(int x=beginX;x<endX;x++){
			for(int y=beginY;y<endY;y++){
				//System.out.println(System.currentTimeMillis()+"         x"+x+"y"+y);
				
				    data =image.getRaster().getDataElements(x-beginX, y-beginY, null);//��ȡ���ص�
		             //ColorModel��һ��������ͼƬĳ���rgbֵ�ֱ�ȡ�����࣬����ȡ��alphaֵ
		              red = image.getColorModel().getRed(data);
		              green = image.getColorModel().getGreen(data);
		              blue = image.getColorModel().getBlue(data);
				
				
				if(Math.abs(red-color.getRed())<10 && Math.abs(blue-color.getBlue())<10&& Math.abs(green-color.getGreen())<10 ){
					return new Point(x,y);
				}
			}
		}
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Point searchColorAtY1(int beginX,int endX,int beginY,int endY ,Color color) throws AWTException {
		 Rectangle screenRectangle = new Rectangle();
		 screenRectangle.x= beginX;
		 screenRectangle.y=  beginY;
		 screenRectangle.width = endX-beginX;
		 screenRectangle.height =endY -  beginY;
		 
		 int red ;
        int green ;
        int blue;
        Object data;
		BufferedImage image = robot.createScreenCapture(screenRectangle);
		
		
		  /* File screenFile = new File("e://test//test2");    
	        // ���·��������,�򴴽�  
	        if (!screenFile.getParentFile().exists()) {  
	            screenFile.getParentFile().mkdirs();  
	        } 
	        //�ж��ļ��Ƿ���ڣ������ھʹ����ļ�
	        if(!screenFile.exists()&& !screenFile .isDirectory()) {
	            screenFile.mkdir();
	        }
	        
	        File f = new File(screenFile, "test.png");        
	        try {
				ImageIO.write(image, "png", f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		*/
		for(int x=beginX;x<endX;x++){
			for(int y=beginY;y<endY;y++){
				//System.out.println(System.currentTimeMillis()+"         x"+x+"y"+y);
				
				    data =image.getRaster().getDataElements(x-beginX, y-beginY, null);//��ȡ���ص�
		             //ColorModel��һ��������ͼƬĳ���rgbֵ�ֱ�ȡ�����࣬����ȡ��alphaֵ
		              red = image.getColorModel().getRed(data);
		              green = image.getColorModel().getGreen(data);
		              blue = image.getColorModel().getBlue(data);
				
				
				if(Math.abs(red-color.getRed())<8 && Math.abs(blue-color.getBlue())<8&& Math.abs(green-color.getGreen())<8 ){
					return new Point(x,y);
				}
			}
		}
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Point searchColorAtYBak(int beginX,int endX,int beginY,int endY ,Color color) throws AWTException {
		
		for(int x=beginX;x<endX;x++){
			for(int y=beginY;y<endY;y++){
				//System.out.println(System.currentTimeMillis()+"         x"+x+"y"+y);
				
				 
				Color xyColor = getColor(x,y);
				
				
				if(Math.abs(xyColor.getRed()-color.getRed())<10 && Math.abs(xyColor.getBlue()-color.getBlue())<10 ){
					return new Point(x,y);
				}
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

}
