package dozenx.keyrobot.module.yys.task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



	
	 public class HeipingThread extends BaseThread{
		public HeipingThread(String key, int time, String windowName) {
			super(key, time, windowName);
			// TODO Auto-generated constructor stub
		}

	
		
		@Override
		public void run() {
			while(true){
				try {
					
					sleep(5000);
					
					
					
					
					String cmd =" %systemroot%\\system32\\scrnsave.scr /s ";
					//cmd ="cmd /k start e:\\shutdownscreen.bat";
					
					
					
					ProcessBuilder builder = new ProcessBuilder();
					List<String> list = new ArrayList<>();
					list.add("cmd.exe");
					list.add("/c");
					list.add("%systemroot%\\system32\\scrnsave.scr");
					list.add("/s");
					Process process = builder.command(list).start();
					InputStream inputStream = process.getInputStream();
					
					BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"gb2312"));
					String line = null;
					while((line = br.readLine()) != null) {
						System.out.println(line);
					}
					
					System.out.println(cmd);
					//%systemroot%\system32\scrnsave.scr /s
//					 Runtime rt = Runtime.getRuntime();  
//					    Process p = null;  
//					   
//					    int exitVal;  
//					    p = rt.exec(cmd);  
//					    // ���̵ĳ���ֵ�����ݹ�����0 ��ʾ������ֹ��   
//					    boolean flag =false;
//					    exitVal = p.waitFor();  
//					    if(exitVal == 0){  
//					        flag = true;  
//					    }else{  
//					        flag = false;  
//					    }  
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					isRunning =false;
				}
			
			       
			
		}
		
		}
		
	}

