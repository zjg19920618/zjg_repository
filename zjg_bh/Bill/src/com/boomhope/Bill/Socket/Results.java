package com.boomhope.Bill.Socket;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Results")
public class Results {
	private Scan SCAN;

	public Scan getSCAN() {
		return SCAN;
	}

	public void setSCAN(Scan sCAN) {
		SCAN = sCAN;
	}

	
}
