package com.boomhope.Bill.Test;

import com.boomhope.Bill.Util.ChangeStringFormat;

public class TestStringFormat {
	public static void main(String[] args) {
		ChangeStringFormat test = new ChangeStringFormat();
		int max = 11;//本金11个字节、本息合计11个字节、利息8个字节
	    String word = "";//可为""、null及任意数值
//	    System.out.println("word的长度："+word.length());
	    //输出字符长度范例
		for(int i=0;i<max;i++){
			System.out.print("*");
		}
		System.out.println("");
	    String w = test.StringCentered(max,word);
	    System.out.println(w);
	    System.out.println("转换后长度："+w.length());
	}
}
