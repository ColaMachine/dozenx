package dozenx.keyrobot.module.yys.task;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;



	
	 public abstract class BaseThread extends Thread{
		 protected String key;
		protected String windowName;
		protected int time;
		protected boolean isRunning ;
	
		public static long lastFightTime = System.currentTimeMillis();
		public BaseThread(String key, int time,String windowName){
			this.key=key;
			this.time=time;
			this.windowName=windowName;
			this.isRunning=true;
		}
		
		public void stopRun(){
			isRunning =false;
		}
		
		
	}

