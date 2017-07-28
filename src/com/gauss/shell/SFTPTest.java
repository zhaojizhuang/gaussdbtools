package com.gauss.shell;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.gauss.shell.Sftp.MyProgressMonitor;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPTest {

    public SFTPChannel getSFTPChannel() {
        return new SFTPChannel();
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args)  {
    	try {
			//transfer("localhost", "root", "666666", "22", "/Users/zhaojizhuang/Class Diagram0.asta", "/usr");
		putfile("localhost", "root", "666666", 22);
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static void  putfile(String host,String username,String passwd,int port){
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
        	   channelSftp.put("/Users/zhaojizhuang/Class Diagram0.asta","/a");
        	        
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
    public static void transfer(String host,String user,String password,String port,String srcfile,String dstfile)throws Exception{
        SFTPTest test = new SFTPTest();

        Map<String, String> sftpDetails = new HashMap<String, String>();
        // 设置主机ip，端口，用户名，密码
        sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, host);
        sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, user);
        sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, password);
        sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, port);
        
        String src = srcfile; // 本地文件名
        String dst = dstfile+"/a"; // 目标文件名
              
        SFTPChannel channel = test.getSFTPChannel();
        ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
        
        /**
         * 代码段1
        OutputStream out = chSftp.put(dst, new MyProgressMonitor(), ChannelSftp.OVERWRITE); // 使用OVERWRITE模式
        byte[] buff = new byte[1024 * 256]; // 设定每次传输的数据块大小为256KB
        int read;
        if (out != null) {
            System.out.println("Start to read input stream");
            InputStream is = new FileInputStream(src);
            do {
                read = is.read(buff, 0, buff.length);
                if (read > 0) {
                    out.write(buff, 0, read);
                }
                out.flush();
            } while (read >= 0);
            System.out.println("input stream read done.");
        }
        **/
        
        //chSftp.put(src, dst, new MyProgressMonitor(), ChannelSftp.OVERWRITE); // 代码段2
        
         chSftp.put(new FileInputStream(src), dst, new MyProgressMonitor(), ChannelSftp.OVERWRITE); // 代码段3
        
        chSftp.quit();
        channel.closeChannel();
    }
}