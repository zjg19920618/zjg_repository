package com.boomhope.Bill.Socket;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SCAN")
public class Scan {
	
	private String FColor;
	private String RColor;
	private String UV;
	private String IR;
	private String Mult;
	public String getFColor() {
		return FColor;
	}
	public void setFColor(String fColor) {
		FColor = fColor;
	}
	public String getRColor() {
		return RColor;
	}
	public void setRColor(String rColor) {
		RColor = rColor;
	}
	public String getUV() {
		return UV;
	}
	public void setUV(String uV) {
		UV = uV;
	}
	public String getIR() {
		return IR;
	}
	public void setIR(String iR) {
		IR = iR;
	}
	public String getMult() {
		return Mult;
	}
	public void setMult(String mult) {
		Mult = mult;
	}
	
}
