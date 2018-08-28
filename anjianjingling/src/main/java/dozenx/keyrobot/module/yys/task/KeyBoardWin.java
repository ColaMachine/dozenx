package dozenx.keyrobot.module.yys.task;

import device.WinIOVirtualKeyBoard;
import org.xvolks.jnative.util.User32;
import util.ColorPicker;
import util.VKMapping;
import util.YinYangShiHelper;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;





public class KeyBoardWin extends JFrame{
	private final Image screenImage = new BufferedImage(50,50,2);
	private final Canvas canvas = new Canvas();
	private final Graphics2D screenGraphic = (Graphics2D) canvas.getGraphics();
	private final ImageIcon icon =new ImageIcon("C:\\Users\\colamachine\\Desktop\\QQͼƬ20180219184012.png");
	public KeyBoardWin(){
		
		this.setTitle("��������������");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		//JLabel label =new JLabel();
		
		
		
		//label.setIcon(icon);
		//this.add(label);
		this.add(new MyPanel(1,"����"));
		this.add(new MyPanel(2,"����"));
		this.add(new MyPanel(3,"�պͷ�"));
		this.add(new MyPanel(4,"ͻ��"));
		this.add(new MyPanel(5,"�ͻ��"));
		this.add(new MyPanel(6,"��ʮ"));
		this.add(new MyPanel(7,"�������"));
		this.add(new MyPanel(8,"douji"));
		this.add(new MyPanel(9,"����"));
		this.add(new MyPanel(10,"����"));
		this.add(canvas);canvas.setBackground(Color.black);canvas.setSize(150,150);
		this.setSize(1524, 468);
		this.setVisible(true);
		this.setResizable(true);
		
		System.out.println(System.getProperty("java.library.path"));
	}
	public static void main(String[] args) {
		new KeyBoardWin();
	}
	
	
	class HotKeyThread extends Thread{
		private String key;
		private String windowName;
		private int time;
		private boolean isRunning ;
		public HotKeyThread(String key, int time,String windowName){
			this.key=key;
			this.time=time;
			this.windowName=windowName;
			this.isRunning=true;
		}
		@Override
		public void run() {
			while(isRunning){
				try {
					if(User32.GetWindowText(User32.GetForegroundWindow()).contains(this.windowName)){
						WinIOVirtualKeyBoard.KeyPress(VKMapping.toScanCode(this.key));
						
						//
						//VirtualMouse.MouseMove(8, 5, 0, 0            );///����5����
//					VirtualMouse.MouseMove1(9, 0, 0, 0   );//�������
//						Thread.sleep(200);
//						VirtualMouse.MouseUp1(8, 0, 0, 0   );//����ſ�
//						Thread.sleep(200);
//						VirtualMouse.onMouse1();
//						
				//	VirtualMouse.MouseMove(2, 0, 0, 0   );//�������
//						Thread.sleep(200);
//						VirtualMouse.MouseMove(8, 0, 0, 0   );//����ſ�
//						Thread.sleep(200);
//						Thread.sleep(200);
//						VirtualMouse.MyMouseDown();
//						Thread.sleep(200);
//						VirtualMouse.MyMouseUp();
//						Thread.sleep(200);
						//VirtualMouse.LeftMouseDown1();
						
						//VirtualMouse.BusHoundLeftDown();
						//Thread.sleep(200);
						//VirtualMouse.LeftMouseUp();
					//	VirtualMouse.leftDown();
						
					}
					sleep(time*100);
					
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					isRunning =false;
				}
			}
			
		}
		
		public void stopRun(){
			isRunning =false;
		}
	}
	
	class MyPanel extends JPanel{
		JCheckBox checkbox=new JCheckBox("����");
		JFormattedTextField time=new JFormattedTextField(java.text.NumberFormat.getInstance());
		JTextField windowName=new JTextField("����ʦ",10);
		JComboBox box=new JComboBox(VKMapping.getKeyName().toArray());
		public MyPanel(final int index,String name ){
			this.setLayout(new FlowLayout());
			this.add(checkbox);
			this.add(new JLabel("ʱ��(ms)"));
			time.setColumns(10);
			time.setText("11");
			this.add(time);
			this.add(new JLabel("������"+name));
			this.add(windowName);
			this.add(new JLabel("����"));
			this.add(box);
			
			
					checkbox.addActionListener(new ActionListener() {
						BaseThread th ;
					public void actionPerformed(ActionEvent e) {

						
						if(checkbox.isSelected()){
							//th=new YinYangShiThread(box.getSelectedItem().toString(),Integer.parseInt(time.getText()),windowName.getText());
							
							if(index == 1){
							th=new FubenThread(box.getSelectedItem().toString(),Integer.parseInt(time.getText()),windowName.getText());
							}else if(index == 2){
								th=new JuexingThread(box.getSelectedItem().toString(),Integer.parseInt(time.getText()),windowName.getText());
							}else if(index == 3){
								th=new RiHeFangThread(box.getSelectedItem().toString(),Integer.parseInt(time.getText()),windowName.getText());
							}else if(index == 4){
								th=new TupoThread(box.getSelectedItem().toString(),Integer.parseInt(time.getText()),windowName.getText());
							}else if(index ==5){
								th=new LiaoTupoThread(box.getSelectedItem().toString(),Integer.parseInt(time.getText()),windowName.getText());
							}else if(index ==6){
								th=new hunshiThread(box.getSelectedItem().toString(),Integer.parseInt(time.getText()),windowName.getText());
							}else if(index ==7){
								th=new yuhunshiThread(box.getSelectedItem().toString(),Integer.parseInt(time.getText()),windowName.getText());
							}else if(index ==8){
								th=new DoujiThread(box.getSelectedItem().toString(),Integer.parseInt(time.getText()),windowName.getText());
							}else if(index ==9){
								th=new HeipingThread(box.getSelectedItem().toString(),Integer.parseInt(time.getText()),windowName.getText());
							}else if(index ==10){
								th=new GuiwangThread(box.getSelectedItem().toString(),Integer.parseInt(time.getText()),windowName.getText());
							}
//							th.setPriority(Thread.MAX_PRIORITY);
							th.start();
						}else{
							if(th!=null){
								th.stopRun();
								th=null;
							}
							
						}
					}
					});
				
				
			
			
			
			
			
			
			
			
			time.addKeyListener(	new KeyAdapter()//���̼�����ť
	        {
	            public void keyPressed(KeyEvent e)
	            {
	                if(e.getKeyCode()==KeyEvent.VK_F1){
	                	Point mousepoint
	                	  = MouseInfo.getPointerInfo().getLocation();
	           	
	                	//System.out.println(String.format("mouse position: x:%d y:%d",mousepoint.x,mousepoint.y));
	                	 Color pixel = new Color(0,0,0);
	                	 int R,G,B;
	                	 Robot robot;
	                	 try {
							robot = new Robot();
							 pixel = robot.getPixelColor(mousepoint.x,mousepoint.y);
			           		  R = pixel.getRed();
			           		  G = pixel.getGreen();
			           		  B = pixel.getBlue(); 
			                	//System.out.println(String.format("r:%d g:%d b:%d",R,G,B));
			                	
			                	System.out.println(String.format("%d,%d,%d,%d,%d,",mousepoint.x,mousepoint.y,R,G,B));
			                	
			                	//��ͼ
			                	
			                	drawView(ColorPicker.captureScreen( mousepoint.x, mousepoint.y, mousepoint.x+10, mousepoint.y+10));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	                	
	                }
	                
	                if(e.getKeyCode()==KeyEvent.VK_F2){
	                	
	                	 try {
	                		 System.out.println(YinYangShiHelper.isMap());
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	                	
	                }
	               
	            }
	            
	        });
		}
	}
	
	
	public void drawView(BufferedImage image){
		//screenGraphic.drawImage(image,0,0,null);
		canvas.getGraphics().drawImage(image, 0, 0, 150, 150, 0, 0, 10, 10, null);
		//icon.setImage(image);
//		try {
//			icon.setImage(ImageIO.read(new File("E:\\test\\test2\\test.png")));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
