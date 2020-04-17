package com.boomhope.Bill.Util;
/**
 * 将字符串与最长字符串居中对齐
 * @author zhang.m
 *
 */
public class ChangeStringFormat {
	/**
	 * 将字符串与最长字符串居中对齐
	 * @param max 最长字符串的长度
	 * @param word 需居中对齐的字符串
	 * @return 返回居中对齐的字符串
	 */
	public String StringCentered(int max,String word) {
		String Word = "";
		/* 为""或者为null的情况 */
		if(word==""||word==null){
			Word="";
			for(int i=0;i<max;i++){
				Word += " ";
			}
		}else{
			Word = word.trim();
			String xx = "";
			String ss = "";
			boolean m = odevity(max);
			boolean w = odevity(Word.length());
			/*
			 *1、max为奇数，word为偶数
			 *2、max为奇数，word为奇数
			 *3、max为偶数，word为奇数
			 *4、max为偶数，word为偶数 
			 *(m==true && w==true) || (m==false && w==false) 同为奇偶
			 * m==true && w==false m为奇数，w为偶数
			 * m==false && w==true m为偶数，w为奇数
			 */
			if((m==true && w==true) || (m==false && w==false)){
				//word前后追加同样个数空格
				for (int i=0;i<max/2-Word.length()/2;i++){
					xx += " ";
				}
				Word = xx + Word + xx;
			}else if(m==true && w==false){
				//word前追加空格
				for(int i=0;i<max/2-Word.length()/2;i++){
					xx += " ";
				}
				String words = xx + Word;
				//word后追加空格
				for(int i=0;i<max/2-Word.length()/2+max%2;i++){
					ss += " ";
				}
				Word = words + ss;
			}else if(m==false && w==true){
				//word前追加空格
				for(int i=0;i<max/2-Word.length()/2;i++){
					xx += " ";
				}
				String words = xx + Word;
				//word后追加空格
				for(int i=0;i<max/2-Word.length()/2-Word.length()%2;i++){
					ss += " ";
				}
				Word = words + ss;
			}else{
				
			}
		}
		return Word;
	}
	/**
	 * 判断一个数值是奇数偶数，true为奇数，false为偶数
	 * @param number
	 * @return
	 */
	public boolean odevity(int number){
		if(number%2 != 0){
			return true;
		}
		return false;
	}
}



