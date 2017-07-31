package com.gauss.shell;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.PropertiesUtil;

import com.gauss.fileutils.PropertiesUtils;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.sun.org.apache.bcel.internal.generic.NEW;

import oracle.jrockit.jfr.ActiveRecordingEvent;
import sun.util.logging.resources.logging;

public class SFTPTest {

	private static Logger log= LogManager.getLogger(SFTPTest.class);
	static hostinfo host = new hostinfo(); 
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
    public static void uploadfile(String sQL_file_path){
    	ReadParams();
    	log.info("开始上传文件"+sQL_file_path);
    	putfile(host, sQL_file_path);
    }
    public static void ReadParams(){
    	log.info("read params");
    	host.setHost(PropertiesUtils.getKeyValue("host"));
    	host.setName(PropertiesUtils.getKeyValue("name"));
    	host.setPassword(PropertiesUtils.getKeyValue("password"));
    	host.setPort(PropertiesUtils.getKeyValue("port"));
    	host.setSql_dst_path(PropertiesUtils.getKeyValue("sql_dst_path"));
    	log.info("hostinfo:"+host+",");
    }
    
    
    public static void  putfile(hostinfo host,String srcfile){
    	 //1、创建JSch类，好比是FlashFXP工具
    	   JSch jsch = new JSch();
    	        
    	   //2、创建本次的文件传输会话对象，并连接到SFTP服务器。它好比是通过FlashFXP工具连接到SFTP服务器
    	   Session session ;
    	   ChannelSftp channelSftp = null;
    	   try{
    		   log.info("jsch getsession ,"+host);
    		   session = jsch.getSession(host.getName(), host.getHost(), Integer.parseInt(host.getPort()));
	    	   session.setPassword(host.getPassword());
	    	   Properties config = new Properties();
	    	   config.put("StrictHostKeyChecking", "no");
	    	   session.setConfig(config);
	    	   session.connect();
	    	   log.info("session get connected");
    	   //3、在该session会话中开启一个SFTP通道，之后就可以在该通道中进行文件传输了
    	       channelSftp = (ChannelSftp)session.openChannel("sftp");
    	       channelSftp.connect();
    	       log.info("channelsftp connected");
    	   	   //4、进行文件传输操作：put()、get()....
        	   channelSftp.put(srcfile,host.getSql_dst_path());
        	   log.info("进行文件传输操作：put.....");
        	   //5、操作完毕后，关闭通道并退出本次会话
        	   if(channelSftp!=null && channelSftp.isConnected()){
        	        channelSftp.disconnect();
        	   }
        	        
        	   if(session!=null && session.isConnected()){
        	        session.disconnect();
        	   }
    	   }catch(Exception e){
    	       e.printStackTrace();
    	       log.error(e);
    	       channelSftp.disconnect();
    	   }
    	        
 

    	 
    }
   
}