package com.boomhope.tms.socket;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class SocketContextListener implements ServletContextListener {
	
	Thread thread;

	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		
		
		
		SocketHandle socketHandle = new SocketHandle();
		thread = new Thread(socketHandle);
		thread.start();
	}

}

class SocketHandle implements Runnable{
	Logger logger = Logger.getLogger(SocketContextListener.class);
	
	public Properties aa(){
		InputStream in = null;
	    Properties prop = new Properties();   
	    BufferedReader br= null;
	    try{
	    	in = new FileInputStream(getClass().getClassLoader().getResource("/config.properties").getPath());
	        //in = new BufferedInputStream (new FileInputStream("config\\config.properties"));
	    	br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
	    	prop.load(br); 
	       
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }finally{
	    	try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    return prop;
	}
	
	@Override
	public void run() {
		Properties pop = aa();
		int port = Integer.parseInt(pop.get("socket_port")+"");
		int number = Integer.parseInt(pop.get("socket_number")+"");
		SocketServer socketServer;
		logger.info("create bp socket........start");
		socketServer = new SocketServer(number, port);
		socketServer.connect();
		logger.info("create bp socket........end");

	}
	
}
