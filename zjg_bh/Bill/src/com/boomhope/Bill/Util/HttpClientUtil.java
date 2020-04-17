package com.boomhope.Bill.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * http请求类
 * @author zy
 *
 */
public class HttpClientUtil {
	
	static Logger logger  = Logger.getLogger(HttpClientUtil.class);
	
	public static void main(String[] args) throws Exception {
		JSONObject json=new JSONObject();
		json.put("command","MT_REQUEST");
		json.put("spid","QGXTDX");
		json.put("sppassword","111111");
		json.put("dc","15");
		json.put("sm",GbkCode.encode("您的验证码是：121387"));
		json.put("da","15010900015");
		HttpClientUtil util=new HttpClientUtil();
		System.out.println(util.httpPost("http://198.1.8.17:8082/sms/mt", json));
	}
	//发送短信验证码
	public boolean sendMessageCode(JSONObject json)throws Exception{
		json.put("command",Property.command);//操作请求
		json.put("spid",Property.spid);//用户号
		json.put("sppassword",Property.sppassword);//密码
		json.put("dc","15");//编码格式GBK
//		json.put("sm",GbkCode.encode("您的验证码是：121387"));//短信内容
//		json.put("da","15010900015");//手机号
		String msg=httpPost(Property.sUrl, json);
		logger.info(msg);
		if(msg.contains("mterrcode=000")){
			return true;
		}
		return false;
	}
	
	/**
	 * post请求
	 * @param requestUrl
	 * @param paramMap
	 */
	public String  post(String requestUrl,JSONObject paramMap){
		HttpClient client = null;
		try {
			// 创建客户端实例
			client = new DefaultHttpClient();
			// 设置请求参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("params", paramMap.toString()));
			// 绑定请求
			HttpPost post = new HttpPost(requestUrl);
			// 设置请求参数
			post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

			// 进行请求
			HttpResponse httpResponse = client.execute(post);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				 return EntityUtils.toString(httpResponse.getEntity());
				 
			} else {
				logger.error("http异常返回值："+httpResponse.getStatusLine().getStatusCode());
				return "-1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.getConnectionManager().shutdown();
			}
		}
		return null;
	}
	
	/**
	 * 通用httpPost调用
	 * 
	 * @param requestUrl
	 * @param paramMap 是JSONObject类型（K:V类型，下面循环遍历，依次作为参数传输出去）
	 * @return
	 */
	public String  httpPost(String requestUrl,JSONObject paramMap){
		HttpClient client = null;
		try {
			// 创建客户端实例
			//client = new DefaultHttpClient();
			/*RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(180 * 1000).setConnectTimeout(5000)
					.setConnectionRequestTimeout(5000).setStaleConnectionCheckEnabled(true).build();
			CloseableHttpClient httpClient = HttpClients.custom()
					.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true))
					.setDefaultRequestConfig(defaultRequestConfig).build();*/
			client = HttpClients.createDefault();
			// 设置请求参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Iterator keys = paramMap.keys();
			while(keys.hasNext()){
				String keyName = (String) keys.next();
				nvps.add(new BasicNameValuePair(keyName, paramMap.getString(keyName)));
			}
			// 绑定请求
			HttpPost post = new HttpPost(requestUrl);
			// 设置请求参数
			post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			// 进行请求
			HttpResponse httpResponse = client.execute(post);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(httpResponse.getEntity());
			} else {
				logger.error("http异常返回值："+httpResponse.getStatusLine().getStatusCode());
				return "-1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("http请求失败："+e);
		} finally {
			if (client != null) {
				client.getConnectionManager().shutdown();
			}
		}
		return null;
	}
	 

}
