package com.boomhope.Bill.Socket;

import com.boomhope.tms.message.XStreamNameCoder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Test {

	public static void main(String[] args) {
		String str = "<Results><SCAN><FColor>C:/CBSC_TJPF_20160913/ScanImage/FColor20160919121111.jpg</FColor><RColor>C:/CBSC_TJPF_20160913/ScanImage/RColor20160919121111.jpg</RColor><UV>C:/CBSC_TJPF_20160913/ScanImage/FUV20160919121117.jpg</UV><IR>C:/CBSC_TJPF_20160913/ScanImage/FIR20160919121117.jpg</IR><Mult>0</Mult></SCAN></Results>";
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Results", Results.class);
		reqXs.alias("SCAN", Scan.class);
		Results results = (Results)reqXs.fromXML(str);
		System.out.println(results.getSCAN().getFColor());
	}
}
