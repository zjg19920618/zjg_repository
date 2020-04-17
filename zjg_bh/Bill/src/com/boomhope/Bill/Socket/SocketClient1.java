package com.boomhope.Bill.Socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Util.Property;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.tms.Tms0002PeriBean;
import com.boomhope.tms.message.in.tms.Tms0002ReqBean;
import com.boomhope.tms.message.in.tms.Tms0002ReqBodyBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/***
 * socket客户端
 * @author shaopeng
 *
 */
public class SocketClient1 {
	
	Socket socket;
	
	public SocketClient1() throws UnknownHostException, IOException{
		socket = new Socket(Property.BP_IP, Property.BP_PORT);
	}
	
	/***
	 * 发送报文
	 * @param reqMsg 请求报文
	 * @return 响应报文
	 * @throws IOException 
	 */
	private String sendMsg(String reqMsg) throws IOException{
        
		//构建IO  
        InputStream is = socket.getInputStream();  
        OutputStream os = socket.getOutputStream();  

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
        
        //向服务器端发送一条消息  
        bw.write(reqMsg + "\n");  
        bw.flush();  

        //读取服务器返回的消息  
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
		
		String retMsg = "";
		String value = null;
		while ((value = br.readLine()) != null) {
			retMsg += value + "\n";
			if ("</Root>".equals(value)){
				break;
			}
		}
		os.close();
		is.close();
		return retMsg;
	}
	
	/***
	 * 外设状态上送(TMS_0002)报文处理
	 * @param map 上送参数
	 * @throws IOException 
	 */
	public void doTms0002() throws IOException{
		
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));

		xstream.autodetectAnnotations(true);
		
		/* TMS_0002报文测试 */
		List<Tms0002PeriBean> periList = new ArrayList<Tms0002PeriBean>();
		
		/* 外设信息 */
		Tms0002PeriBean periBean = new Tms0002PeriBean();
		periBean.setPeriId("peri001");
		periBean.setPeriStatus("1");
		periList.add(periBean);
		
		Tms0002PeriBean periBean2 = new Tms0002PeriBean();
		periBean2.setPeriId("peri002");
		periBean2.setPeriStatus("2");
		periList.add(periBean2);
		
		/* 请求报文体 */
		Tms0002ReqBodyBean bodyBean = new Tms0002ReqBodyBean();
		bodyBean.setMachineStatus("1");
		bodyBean.setPeriList(periList);
		
		/* 请求报文头 */
		InReqHeadBean headBean = new InReqHeadBean();
		headBean.setTradeCode("TMS_0002");
		headBean.setMachineNo(GlobalParameter.machineNo);
		headBean.setBranchNo(GlobalParameter.branchNo);
		headBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		headBean.setProductCode("TMS");
		
		/* 请求报文 */
		Tms0002ReqBean reqBean = new Tms0002ReqBean();
		reqBean.setHeadBean(headBean);
		reqBean.setBodyBean(bodyBean);

		xstream.alias("ROOT", Tms0002ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", Tms0002ReqBodyBean.class);
		
		String reqMsg = xstream.toXML(reqBean);
		
		String resMsg = sendMsg(reqMsg);
		
		System.out.println("TMS_0002 ResMsg: " + resMsg);
		
	}

}
