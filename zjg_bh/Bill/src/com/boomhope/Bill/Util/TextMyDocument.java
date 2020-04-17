package com.boomhope.Bill.Util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.boomhope.Bill.Framework.BaseTransPanelNew;


/**
 * 限定textField中字符串长度
 * @author Administrator
 *
 */

public class TextMyDocument extends PlainDocument{
	int maxLength =10;
	public TextMyDocument(int newMaxLength){
		super();
		maxLength=newMaxLength;
	}
	
	public TextMyDocument(){
		this(10);
	}
	
	//重载父类insertString函数
	public void insertString(int offset,String str,AttributeSet a)throws BadLocationException{
		if(getLength()+str.length()>maxLength){
			new BaseTransPanelNew().openMistakeDialog("你输入的内容长度已经超出限制(限制"+maxLength+"个字)");
			return;
		}else{
			super.insertString(offset, str, a);
		}
	}
}
