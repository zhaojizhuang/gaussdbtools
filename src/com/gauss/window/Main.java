package com.gauss.window;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gauss.shell.ExeCommand;
import com.gauss.shell.SFTPTest;
import com.sun.org.apache.bcel.internal.generic.NEW;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;






public class Main implements ActionListener {
	
	static Logger log = LogManager.getLogger(Main.class.getName());
	static String SQL_file_path=null;
	static Vector<String> out=new Vector<>();
	JFrame frame = new JFrame("GaussDb 可视化小工具");// 框架布局
	Container con = new Container();//
	JLabel choseSQLtxt = new JLabel("选择sql文件");
	JTextField SQLFilePath = new JTextField();// 文件的路径
	JButton chosefile_button = new JButton("...");// 选择
	JFileChooser jfc = new JFileChooser();// 文件选择器
	JButton commit_button = new JButton("确定");//
	private final JScrollPane scrollPane = new JScrollPane();
	static JLabel logLabel = new javax.swing.JLabel();  
	public static  JTextArea logTextArea=new JTextArea();  
	private final JLabel lblNewLabel = new JLabel("提示！通过··· 选择文件 或者将文件拖入 选择框");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					log.info("进入main run ");
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
		if (e.getSource().equals(chosefile_button)) {
			System.out.println("点击选择文件");
			jfc.setFileSelectionMode(0);// 设定只能选择到文件
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;// 撤销则返回
			} else {
				File f = jfc.getSelectedFile();// f为选择到的文件
				SQLFilePath.setText(f.getAbsolutePath());
				log.info("chosen file is: " + f);
				SQL_file_path = f.toString();
			
			}
		}
		if (e.getSource().equals(commit_button)) {
			// 弹出对话框可以改变里面的参数具体得靠大家自己去看，时间很短
			try {
				SFTPTest.uploadfile(SQL_file_path);
				log.info("zh执行");
				ExeCommand.Exec("ls "+SQL_file_path+"cd /", out);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				log.error(e1);
				JOptionPane.showMessageDialog(null, "执行SQL失败", "提示", 2);
			}
			JOptionPane.showMessageDialog(null, "执行SQL成功", "提示", 2);
		}
	}

	public void initFrame() {
		jfc.setCurrentDirectory(new File("d://"));// 文件选择器的初始目录定为d盘

		double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

		double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

		frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置
		frame.setSize(800, 600);// 设定窗口大小

		choseSQLtxt.setBounds(32, 35, 90, 20);
		SQLFilePath.setBounds(125, 35, 470, 20);
		
		SQLFilePath.setTransferHandler(new TransferHandler()  
        {  
            private static final long serialVersionUID = 1L;  
            @Override  
            public boolean importData(JComponent comp, Transferable t) {  
                try {  
                    Object o = t.getTransferData(DataFlavor.javaFileListFlavor);  
  
                    String filepath = o.toString();  
                    if (filepath.startsWith("[")) {  
                        filepath = filepath.substring(1);  
                    }  
                    if (filepath.endsWith("]")) {  
                        filepath = filepath.substring(0, filepath.length() - 1);  
                    }  
                    log.info(filepath);  
                    SQLFilePath.setText(filepath);  
                    return true;  
                }  
                catch (Exception e) {  
                    e.printStackTrace();  
                }  
                return false;  
            }  
            @Override  
            public boolean canImport(JComponent comp, DataFlavor[] flavors) {  
                for (int i = 0; i < flavors.length; i++) {  
                    if (DataFlavor.javaFileListFlavor.equals(flavors[i])) {  
                        return true;  
                    }  
                }  
                return false;  
            }  
        });  
		
		
		
		
		
		
		chosefile_button.setBounds(606, 36, 50, 20);
		commit_button.setBounds(678, 36, 60, 20);
		chosefile_button.addActionListener(this); // 添加事件处理
		commit_button.addActionListener(this); // 添加事件处理
		con.add(choseSQLtxt);
		con.add(SQLFilePath);
		con.add(chosefile_button);
		con.add(commit_button);
		frame.setVisible(true);// 窗口可见
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序
		frame.getContentPane().add(con);
		scrollPane.setBounds(28, 328, 740, 208);
		
		con.add(scrollPane);
		logLabel.setText(" adsfadfadsfadsf");  
		  
        logTextArea.setColumns(20);  
        logTextArea.setRows(5);  
        scrollPane.setViewportView(logTextArea);  
        
        JLabel label = new JLabel("输出：");
        label.setBounds(28, 300, 61, 16);
        con.add(label);
        
        JTextArea textArea = new JTextArea();
        textArea.setBounds(32, 110, 733, 144);
        con.add(textArea);
        
        JButton button = new JButton("执行");
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        button.setBackground(Color.MAGENTA);
        button.setForeground(Color.BLACK);
        button.setBounds(574, 266, 82, 29);
        con.add(button);
        
        JButton button_1 = new JButton("提交修改");
        button_1.setBounds(669, 266, 96, 29);
        con.add(button_1);
        lblNewLabel.setForeground(Color.GRAY);
        lblNewLabel.setBounds(33, 6, 291, 16);
        
        con.add(lblNewLabel);
      
	}
	public static void SetText(String text){
		  logTextArea.setText(text);
	}
	public static void appendText(String text){
		logTextArea.append(text+"\r\n");
	}
}
