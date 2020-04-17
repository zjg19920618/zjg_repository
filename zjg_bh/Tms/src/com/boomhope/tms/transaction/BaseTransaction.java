package com.boomhope.tms.transaction;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class BaseTransaction {
	
	private static final Logger logger = Logger.getLogger(BaseTransaction.class);
	
	private static ApplicationContext context;
	static {
		context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml"});
    }
	public static Object getBean(String beanName) throws Exception {
		if (context == null) {
			throw new Exception(
					"ApplicationContext 没有初始化！您无法获得业务处理对象，系统无法正常运行");
		}
		try {
			return context.getBean(beanName);
		} catch (BeansException exp) {
			if (exp instanceof NoSuchBeanDefinitionException) {
				logger.debug("bean[" + beanName + "]尚未装载到容器中！");
			} else {
				logger.error("读取[" + beanName + "]失败！", exp);
			}
			throw new Exception("读取[" + beanName + "]失败！", exp);
		}
	}
	
	/***
	 * 生成错误响应报文
	 * @param resCode
	 * @param resMsg
	 * @return
	 */
	protected String makeFailMsg(String resCode, String resMsg){
		XStream resXs = new XStream(new XppDriver(new XStreamNameCoder()));
		resXs.autodetectAnnotations(true);

		InResBean resBean = new InResBean();
		resBean.getHeadBean().setResCode(resCode);
		resBean.getHeadBean().setResMsg(resMsg);
		
		return resXs.toXML(resBean);
	}

}
