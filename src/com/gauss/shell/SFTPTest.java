package com.gauss.shell;

import java.util.Properties;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import oracle.jrockit.jfr.ActiveRecordingEvent;

public class SFTPTest {

	public static String host;
	public static String name;
	public static String password;
	public static String port;
	public static String sql_dst_path;

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args)  {
    	try {
			//transfer("localhost", "root", "666666", "22", "/Users/zhaojizhuang/Class Diagram0.asta", "/usr");
		//putfile("localhost", "root", "666666", 22);
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void uploadfile(){
    	System.out.println("开始上传文件");
    }
    
    
    public static void  putfile(String host,String username,String passwd,int port,String srcfile,String dstfile){
    	 //1、创建JSch类，好比是FlashFXP工具
    	   JSch jsch = new JSch();
    	        
    	   //2、创建本次的文件传输会话对象，并连接到SFTP服务器。它好比是通过FlashFXP工具连接到SFTP服务器
    	   Session session ;
    	   ChannelSftp channelSftp = null;
    	   try{
    		   session = jsch.getSession(username, host, port);
	    	   session.setPassword(passwd);
	    	   Properties config = new Properties();
	    	   config.put("StrictHostKeyChecking", "no");
	    	   session.setConfig(config);
	    	   session.connect();
    	
    	   //3、在该session会话中开启一个SFTP通道，之后就可以在该通道中进行文件传输了
    	       channelSftp = (ChannelSftp)session.openChannel("sftp");
    	       channelSftp.connect();
    	   	   //4、进行文件传输操作：put()、get()....
        	   channelSftp.put(srcfile,dstfile);
        	        
        	   //5、操作完毕后，关闭通道并退出本次会话
        	   if(channelSftp!=null && channelSftp.isConnected()){
        	        channelSftp.disconnect();
        	   }
        	        
        	   if(session!=null && session.isConnected()){
        	        session.disconnect();
        	   }
    	   }catch(Exception e){
    	       e.printStackTrace();
    	       channelSftp.disconnect();
    	   }
    	        
 

    	 
    }
   
}