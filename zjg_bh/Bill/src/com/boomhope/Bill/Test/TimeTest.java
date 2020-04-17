package com.boomhope.Bill.Test;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



public class TimeTest {
	 private static Logger logger = Logger.getLogger(TimeTest.class);  
	 public static void main(String[] args) {  
		
	        //timer1();  
	       // timer2();  
	        timer3();  
	        //timer4();  
	        int i = 1;
	        while(true){
			i++;
			System.out.println(i);
			if(i>500000){
				
				break;
			}
			timer.cancel();
		}
	       
	    } 

	public static void timer1() {  
        Timer timer = new Timer();  
        timer.schedule(new TimerTask() {  
            public void run() {  
                System.out.println("-------设定要指定任务--------");  
            }  
        }, 2000);// 设定指定的时间time,此处为2000毫秒  
    } 
	
	 // 第二种方法：设定指定任务task在指定延迟delay后进行固定延迟peroid的执行  
    // schedule(TimerTask task, long delay, long period)  
    public static void timer2() {  
        Timer timer = new Timer();  
        timer.schedule(new TimerTask() {  
            public void run() {  
                System.out.println("-------设定要指定任务--------");  
            }  
        }, 1000, 1000);  
    }  
    static Timer timer = null;
    // 第三种方法：设定指定任务task在指定延迟delay后进行固定频率peroid的执行。  
    // scheduleAtFixedRate(TimerTask task, long delay, long period)  
    public static void timer3() {  
        timer = new Timer();  
        timer.scheduleAtFixedRate(new TimerTask() {  
            public void run() {  
                System.out.println("-------设定要指定任务--------");  
                logger.info("日志");
            }  
        }, 1000, 2000);  
    } 
}
