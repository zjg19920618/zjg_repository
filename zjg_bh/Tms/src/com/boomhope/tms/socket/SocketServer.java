package com.boomhope.tms.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.boomhope.tms.transaction.TransRouting;

/***
 * socket 服务端
 * @author shaopeng
 *
 */
public class SocketServer {
	Logger logger = Logger.getLogger(SocketServer.class);
	private ServerSocket serverSocket;
	int maxConnections ;		// 最大连接数
	int listenerPort ;	// 监听端口号
	public SocketServer(int maxConnections, int listenerPort){
		
		this.maxConnections = maxConnections;
		this.listenerPort = listenerPort;
	}
	
	/***
	 * 创建连接
	 */
	public void connect(){
		/* 创建线程 */
		for (int i = 0; i < maxConnections; i++) {
			ConnectionPoolHandler connectionPoolHandler = new ConnectionPoolHandler();
			new Thread(connectionPoolHandler, "socket监听线程:" + i).start();
		}
		
		/* 创建连接 */
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(listenerPort, maxConnections);
			while (true) {
				Socket socket = serverSocket.accept();
				ConnectionPoolHandler.processRequest(socket);
			}
		} catch (IOException e) {
			logger.error("创建链接失败："+e);
		}
	}

	public void closeSocket(){
		try {
			serverSocket.close();
		} catch (IOException e) {
			logger.error("关闭链接失败："+e);
		}
	}
	

	public static void main(String[] args) {
		
		int maxConnections = 5;
		int listenerPort = 9999;
		
		/* 创建线程 */
		for (int i = 0; i < maxConnections; i++) {
			ConnectionPoolHandler connectionPoolHandler = new ConnectionPoolHandler();
			new Thread(connectionPoolHandler, "socket监听线程:" + i).start();
		}
		
		/* 创建连接 */
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(listenerPort, maxConnections);
			while (true) {
				Socket socket = serverSocket.accept();
				ConnectionPoolHandler.processRequest(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}


/***
 * ���ӳ�
 * @author shaopeng
 *
 */
class ConnectionPoolHandler implements Runnable{
	Logger logger = Logger.getLogger(ConnectionPoolHandler.class);
	private Socket socket;
	@SuppressWarnings("rawtypes")
	private static List pool = new LinkedList();
	/**
	* ��������
	*/
	public void handleConnection() {
		try {

			/* 接收请求报文 */
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			String msg = "";
			String value = null;
			while ((value = in.readLine()) != null) {
				msg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			/* 调用交易路由模块 */
			logger.info("******************request msg:\n"+msg);
			TransRouting transHandle = new TransRouting();
			String retMsg = transHandle.Routing(msg);
			logger.info("******************response msg:\n"+retMsg);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));  
            bw.write(retMsg + "\n");  
            bw.flush();

		} catch (Exception e) {
			logger.error("报文处理异常："+e);
		} finally {
			try {
				socket.shutdownInput();
			} catch (IOException e) {
				logger.error("socket 输入流关闭异常：" + e);
			}
			try {
				socket.shutdownOutput();
			} catch (IOException e) {
				logger.error("socket 输出流关闭异常：" + e);
			}
			try {
				socket.close();
			} catch (IOException e) {
				logger.error("socket 关闭异常：" + e);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void processRequest(Socket socket) {
		synchronized (pool) {
			pool.add(pool.size(), socket);
			pool.notifyAll();
		}
	}
	
	/*
	* (non-Javadoc)
	* 
	* @see java.lang.Runnable#run()
	*/
	public void run() {
		while (true) {
			synchronized (pool) {
				while (pool.isEmpty()) {
					try {
						pool.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				socket = (Socket) pool.remove(0);
			}
			handleConnection();
		}
	}
	 
}
