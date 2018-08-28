package dozenx.keyrobot;


import dozenx.keyrobot.conf.Configuration;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Iterator;

public class MyThread implements Runnable{
	private Robot robot;
	private int x;
	private int y;
	int index;
	public void run(){
		performance();
	}
	private void performance(){
		try
		{robot=new Robot();
			do{
				for (int i=0;i<500;i++){
				Iterator<String> it= Configuration.getDos().iterator();
				while(it.hasNext()){
					String s=it.next();
					//System.out.println(s);
					if(s.indexOf("keypress")!=-1){
						robot.keyPress(Integer.valueOf(s.substring(8).trim()));
					}else if(s.indexOf("delay")!=-1){
						Thread.sleep(Integer.valueOf(s.substring(6).trim()));
					}else if(s.indexOf("mousemove")!=-1){
						String[] dim=s.substring(9).split(",");
						robot.mouseMove(Integer.valueOf(dim[0].trim()), Integer.valueOf(dim[1].trim()));
					}/*else if(s.indexOf("mousepress")!=-1){
						robot.mousePress(InputEvent.BUTTON1_MASK);
					}*/else if(s.indexOf("mousepress")!=-1){
						robot.mousePress(InputEvent.BUTTON1_MASK);
						Thread.sleep(10);
						robot.mouseRelease(InputEvent.BUTTON1_MASK);
						Thread.sleep(12);
					}else if(s.indexOf("tab")!=-1){
						robot.keyPress(KeyEvent.VK_TAB); robot.keyRelease(KeyEvent.VK_TAB); 
					}
					else if(s.indexOf("key1")!=-1){
						robot.keyPress(KeyEvent.VK_1); robot.keyRelease(KeyEvent.VK_1); 
					}else if(s.indexOf("key2")!=-1){
						robot.keyPress(KeyEvent.VK_2); robot.keyRelease(KeyEvent.VK_2); 
					}else if(s.indexOf("key3")!=-1){
						robot.keyPress(KeyEvent.VK_3); robot.keyRelease(KeyEvent.VK_3); 
					}
					else if(s.indexOf("keyleft_press")!=-1){
						robot.keyPress(KeyEvent.VK_LEFT); 
					}
					else if(s.indexOf("keyleft_release")!=-1){
						robot.keyRelease(KeyEvent.VK_LEFT); 
					}else if(s.indexOf("space")!=-1){
						
						robot.keyPress(KeyEvent.VK_SPACE); 
						Thread.sleep(12);
						
						robot.keyRelease(KeyEvent.VK_SPACE);
					}
					
				}
				
				robot.mousePress(InputEvent.BUTTON1_MASK);
				Thread.sleep(10);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				Thread.sleep(12);
				}
			}
			while (!Configuration.isTerminated());
			}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
