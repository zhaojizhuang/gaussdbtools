package com.gauss.shell;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gauss.fileutils.PropertiesUtils;
import com.gauss.window.Main;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.sun.org.apache.regexp.internal.recompile;

public class ExeCommand {
	private static Logger log= LogManager.getLogger(SFTPTest.class);
	static hostinfo host = new hostinfo(); 
	 public static int Exec(String command,Vector<String> out){
		 
	       try{
	    	   log.info("执行command，command："+command);
	    	   return Exec(PropertiesUtils.ReadParams(),command,out);
	       }catch (Exception e) {
			// TODO: handle exception
	    	   log.error(e);
	    	   return -1;
		}
	 }
	 public static int Exec(hostinfo host,String command,Vector<String> out) throws Exception, JSchException{
		 int returnCode=0;
	        JSch jsch = new JSch();
	        Session session = jsch.getSession(host.getName(), host.getHost(), Integer.parseInt(host.getPort()));
	    	   session.setPassword(host.getPassword());
	    	   Properties config = new Properties();
	    	   config.put("StrictHostKeyChecking", "no");
	    	   session.setConfig(config);
				session.connect();

	    	   log.info("session get connected");
	    	   Channel channel = session.openChannel("exec");
	    	   ((ChannelExec) channel).setCommand(command);
	    	   channel.setInputStream(null);
	            BufferedReader input = new BufferedReader(new InputStreamReader(channel
	                    .getInputStream()));
	 
	 
	        channel.connect();
	        String line;
	        //log.info("befor while"+input.readLine());
	        while ((line = input.readLine()) != null) {
            	log.info(line);
            	out.add(line);
            	Main.appendText(line);
            }
            if (channel.isClosed()) {
                returnCode = channel.getExitStatus();
            }
            input.close();
	        channel.disconnect();
	        session.disconnect();
			return returnCode;
	 }
	 public static void main(String[] args) {
		 Vector<String> out = new Vector<>();
		Exec("ls",out);
		System.out.println(out);
	}
}
