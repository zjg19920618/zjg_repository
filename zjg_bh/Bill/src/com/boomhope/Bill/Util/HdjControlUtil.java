package com.boomhope.Bill.Util;


import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * �ص����ؼ���ʾ
 * @author Administrator
 *
 */
public class HdjControlUtil {

	private static ActiveXComponent activeX;//ActiveX�ؼ�����  
	
    private static Dispatch dispath;//MS����ĵ��ȶ���  
    

	static  
    {  
        //����ActiveX�ؼ�����  
        //����ʹ��CLSID,Ҳ����ʹ��ProgID  
        activeX = new ActiveXComponent("CLSID:E74D9FB8-3B27-4DE6-8C7A-73982F81FD0D");  
        //��õ��ȶ���  
        dispath = activeX.getObject();  
        
    } 
    public static void main(String[] args) throws InterruptedException {
    	Dispatch.call(dispath, "BH_Reset");//����
    	Dispatch.call(dispath, "BH_Open");//��
    	
		//Dispatch.call(dispath, "BH_CleanResource");//ȡ���ȴ�ɨ��
				
    	//Dispatch.call(dispath, "BH_WaitInsert",new Variant("0"),new Variant("0"),new Variant("aaaaaaa"));
    	//System.out.println("2222222222");
    	//Thread.sleep(3000);
    	
    	//�ȴ������룬��������⵽������ʱ�Ż᷵��0 ������������Ϊ�����ӡ���ַ������粻��Ҫ��ӡ ������ַ�
    	if(Dispatch.call(dispath, "BH_WaitInsert",new Variant("0"),new Variant("0"),new Variant("")).getInt() == 0){
    		//���ɨ����ɾͻ�ȡɨ���·��
    		if(Dispatch.call(dispath, "BH_Scan",new Variant("0")).getInt() == 0){
    			//���
    			System.out.println(Dispatch.call(dispath, "ScanInfo"));
    		}
    	}else{
    		System.out.println(111);
    	}
    	Thread.sleep(1000);
    	//��Ʊ���û�
    	Dispatch.call(dispath, "BH_Eject",new Variant("20"));
    	//����
    	Dispatch.call(dispath, "BH_Reset");
    	//�ر�
    	Dispatch.call(dispath, "BH_Close");
    	//������Դ
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