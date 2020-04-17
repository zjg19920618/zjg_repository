package com.boomhope.Bill.Test;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintJobAttributeSet;

public class PrintTest {

	public static void getPrintName(){
		HashPrintJobAttributeSet ha=new HashPrintJobAttributeSet();
		DocFlavor fl = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintService[] pr=PrintServiceLookup.lookupPrintServices(fl, ha);
		for(int i=0;i<pr.length;i++){
			System.out.println(pr[i].getName());
		}
	}
	
	public static void main(String[] args) {
		getPrintName();
	}
	
}
