package util;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Anjian extends JFrame{

	
	public Anjian(){
		
		this.setTitle("��������������");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		this.add(new MyPanel());
		this.add(new MyPanel());
		this.add(new MyPanel());
		this.setSize(500, 200);
		this.setVisible(true);
		this.setResizable(false);
	}
	public static void main(String[] args) {
		new Anjian();
	}
	
	
	
	
	class MyPanel extends JPanel{
		JCheckBox checkbox=new JCheckBox("����");
		JFormattedTextField time=new JFormattedTextField(java.text.NumberFormat.getInstance());
		JTextField windowName=new JTextField("���±�",10);
		JComboBox box=new JComboBox(VKMapping.getKeyName().toArray());
		public MyPanel(){
			this.setLayout(new FlowLayout());
			this.add(checkbox);
			this.add(new JLabel("ʱ��(ms)"));
			time.setColumns(10);
			time.setText("0");
			this.add(time);
			this.add(new JLabel("������"));
			this.add(windowName);
			this.add(new JLabel("����"));
			this.add(box);
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
						} catch (AWTException e1) {
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
	
	
	
	
}
