package dozenx.keyrobot.win;


import dozenx.keyrobot.MyThread;
import dozenx.keyrobot.MyWindowListener;
import dozenx.keyrobot.conf.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyJFrame extends JFrame {
	JButton start_butt = new JButton("start");//��ʼ��ť
	JPanel p = new JPanel();
	JTextField label = new JTextField();

	public MyJFrame(String sTitle, int iWidth, int iHeight) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();//��ȡ��Ļ�ߴ�
		setTitle(sTitle);//���ô������
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//���õ��رմ���ʱ�˳�����
		addWindowListener(new MyWindowListener());
		setSize(iWidth, iHeight);//���ô����С
		label.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F12) {
					final Point mousepoint = MouseInfo.getPointerInfo()
							.getLocation();
					label.setText(mousepoint.x + ":" + mousepoint.y + "\n");
					
				}
			}
		});
		this.setAlwaysOnTop(true);
		start_butt.addActionListener(new ActionListener()//���ÿ�ʼ����
				{
					public Thread t;
					public void actionPerformed(ActionEvent e) {
						System.out.print(start_butt.getText());
						if (start_butt.getText().equals("start")) {
							if (Configuration.isTerminated()) {
								Configuration.setTerminated(false);// ���ò�������Ƿ����û���ֹͣ
								start_butt.setText("end");
								t=new Thread(new MyThread());
								t.start();
							}
						} else if (start_butt.getText().equals("end")) {
	                         //start_butt.setText("start");
							//Configuration.setTerminated(true);
						}
					}
				});
		p.setLayout(new GridLayout(2, 1));
		p.add(label);
		p.add(start_butt);
		// setUndecorated(true);//ȥ���߿�1
		this.setResizable(false);//���ò��ɵ����С
		this.add(p);
		setVisible(true);//��ʾ����
	}

	public static void main(String[] args) {
		Configuration.init();
		MyJFrame mF = new MyJFrame("zzw�İ�����", 100, 100);//��ʼ������
	}
}
