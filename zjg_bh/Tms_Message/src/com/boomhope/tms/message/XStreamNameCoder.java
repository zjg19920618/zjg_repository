package com.boomhope.tms.message;

import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/***
 * 解决xstream的bug,重新封装NameCoder
 * @author shaopeng
 *
 */
public class XStreamNameCoder extends XmlFriendlyNameCoder {
	public XStreamNameCoder() {
		super("_-", "_");
	}
}