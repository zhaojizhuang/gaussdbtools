package com.gauss.window;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;






public class Main implements ActionListener {
	
	static Logger log = LogManager.getLogger(Main.class.getName());
	
	JFrame frame = new JFrame("gaussdb-tools");// 框架布局
	Container con = new Container();//
	JLabel choseSQLtxt = new JLabel("选择sql文件");
	JTextField SQLFilePath = new JTextField();// 文件的路径
	JButton button2 = new JButton("...");// 选择
	JFileChooser jfc = new JFileChooser();// 文件选择器
	JButton button3 = new JButton("确定");//

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					log.info("进入main run "+System.getProperty("user.dir"));
					Main window = new Main();
					log.info("创建 main窗口");
					log.warn("adsfasdf");
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// 绑定到选择文件，先择文件事件
		if (e.getSource().equals(button2)) {
			System.out.println("点击选择文件");
			jfc.setFileSelectionMode(0);// 设定只能选择到文件
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;// 撤销则返回
			} else {
				File f = jfc.getSelectedFile();// f为选择到的文件
				SQLFilePath.setText(f.getAbsolutePath());
				System.out.println("chosen file is: " + f);

				try {
					//SFTPTest.putfile("localhost", "root", "666666", 22, f.toString(), "/b.a");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if (e.getSource().equals(button3)) {
			// 弹出对话框可以改变里面的参数具体得靠大家自己去看，时间很短
			JOptionPane.showMessageDialog(null, "弹出对话框的实例，欢迎您-漆艾琳！", "提示", 2);
		}
	}

	public void initFrame() {
		jfc.setCurrentDirectory(new File("d://"));// 文件选择器的初始目录定为d盘

		double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

		double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

		frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置
		frame.setSize(800, 600);// 设定窗口大小

		choseSQLtxt.setBounds(10, 35, 90, 20);
		SQLFilePath.setBounds(110, 35, 120, 20);
		button2.setBounds(245, 36, 50, 20);
		button3.setBounds(311, 36, 60, 20);
		button2.addActionListener(this); // 添加事件处理
		button3.addActionListener(this); // 添加事件处理
		con.add(choseSQLtxt);
		con.add(SQLFilePath);
		con.add(button2);
		con.add(button3);
		frame.setVisible(true);// 窗口可见
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序
		frame.getContentPane().add(con);
	}
}
