package com.boomhope.Bill.Util;


import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 回单机控件演示
 * @author Administrator
 *
 */
public class HdjControlUtil {

	private static ActiveXComponent activeX;//ActiveX控件对象  
	
    private static Dispatch dispath;//MS级别的调度对象  
    

	static  
    {  
        //创建ActiveX控件对象  
        //可以使用CLSID,也可以使用ProgID  
        activeX = new ActiveXComponent("CLSID:E74D9FB8-3B27-4DE6-8C7A-73982F81FD0D");  
        //获得调度对象  
        dispath = activeX.getObject();  
        
    } 
    public static void main(String[] args) throws InterruptedException {
    	Dispatch.call(dispath, "BH_Reset");//重置
    	Dispatch.call(dispath, "BH_Open");//打开
    	
		//Dispatch.call(dispath, "BH_CleanResource");//取消等待扫描
				
    	//Dispatch.call(dispath, "BH_WaitInsert",new Variant("0"),new Variant("0"),new Variant("aaaaaaa"));
    	//System.out.println("2222222222");
    	//Thread.sleep(3000);
    	
    	//等待表单插入，当机器检测到表单插入时才会返回0 ，第三个参数为背书打印的字符串，如不需要打印 传入空字符
    	if(Dispatch.call(dispath, "BH_WaitInsert",new Variant("0"),new Variant("0"),new Variant("")).getInt() == 0){
    		//如果扫描完成就获取扫描件路径
    		if(Dispatch.call(dispath, "BH_Scan",new Variant("0")).getInt() == 0){
    			//输出
    			System.out.println(Dispatch.call(dispath, "ScanInfo"));
    		}
    	}else{
    		System.out.println(111);
    	}
    	Thread.sleep(1000);
    	//退票给用户
    	Dispatch.call(dispath, "BH_Eject",new Variant("20"));
    	//重置
    	Dispatch.call(dispath, "BH_Reset");
    	//关闭
    	Dispatch.call(dispath, "BH_Close");
    	//清理资源
    	Dispatch.call(dispath, "BH_CleanResource");
	}
    
    public static ActiveXComponent getActiveX() {
		return activeX;
	}
	public static void setActiveX(ActiveXComponent activeX) {
		HdjControlUtil.activeX = activeX;
	}
	public static Dispatch getDispath() {
		return dispath;
	}
	public static void setDispath(Dispatch dispath) {
		HdjControlUtil.dispath = dispath;
	}

}