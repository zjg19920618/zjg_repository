package com.boomhope.Bill.Util;

import org.bouncycastle.crypto.digests.SHA1Digest;

import UnionTech.JavaEsscAPI.CommWithEsscSvr;
import UnionTech.JavaEsscAPI.UnionAPI;
import UnionTech.JavaEsscAPI.esscFldTagDef;



public class UnionAPIEX extends UnionAPI {

	public UnionAPIEX(String ip, int port, int timeOut, String gunionIDOfEsscAPI) {
		super(ip, port, timeOut, gunionIDOfEsscAPI);
	}

	public String UnionGenerateMac(String fullKeyName, int lenOfMacData, byte[] macData) throws Exception {
		if (fullKeyName==null || macData==null || (fullKeyName.length() == 0) || (macData.length == 0) || (lenOfMacData <= 0)) {
			throw new Exception("in UnionGenerateMac:: parameter error!\n");
		}

		String hashData = "";
		int hashDataLen = 0;
		byte[] hash = new byte[20];

		SHA1Digest digest = new SHA1Digest();

		byte[] byteMacData = macData;
		int byteMacDataLen = byteMacData.length;
		digest.update(byteMacData, 0, byteMacDataLen);
		hashDataLen = digest.doFinal(hash, 0);
		hashData = new String(hash, "ISO-8859-1");

		//add by john
		if(hashDataLen<0){
			//do nothing
		}
		
		String packageBuf = printCStyle(3, 2);
		int offset = 3;
		esscFldTagDef fldTag = new esscFldTagDef();
		String packageBufFld = UnionPutFldIntoStr(fldTag.conEsscFldKeyName, fullKeyName, fullKeyName.length());
		if (packageBufFld.length() == 0) {
			throw new Exception("in UnionGenerateMac:: UnionPutFldIntoStr for conEsscFldKeyName!\n");
		}
		offset += packageBufFld.length();
		packageBuf = packageBuf + packageBufFld;

		packageBufFld = UnionPutFldIntoStr(fldTag.conEsscFldMacData, hashData, hashData.length());
		if (packageBufFld.length() == 0) {
			throw new Exception("in UnionGenerateMac:: UnionPutFldIntoStr for conEsscFldMacData!\n");
		}
		offset += packageBufFld.length();
		packageBuf = packageBuf + packageBufFld;

		CommWithEsscSvr commWithEsscSvr = new CommWithEsscSvr(this.esscIp, this.esscPort, this.timeOut, this.gunionIDOfEsscAPI);
		commWithEsscSvr.returnPackage = "";
		int MacLen = commWithEsscSvr.UnionCommWithEsscSvr("302", packageBuf, offset);
		String mac = "";
		mac = UnionReadSpecFldFromStr(commWithEsscSvr.returnPackage, MacLen, fldTag.conEsscFldMac);
		return mac;
	}

	public int UnionReadKeyBySpecZmk(String fullKeyName, String zmkName, byte[] keyValue, byte[] checkValue) throws Exception {
		if ((fullKeyName.length() == 0) || (zmkName.length() == 0))
			throw new Exception("in UnionReadKeyBySpecZmk:: parameter error!");

		String packageBuf = printCStyle(3, 2);
		int offset = 3;
		esscFldTagDef fldTag = new esscFldTagDef();
		String packageBufFld = UnionPutFldIntoStr(fldTag.conEsscFldKeyName, fullKeyName, fullKeyName.length());
		if (packageBufFld.length() == 0) {
			throw new Exception("in UnionReadKeyBySpecZmk:: UnionPutFldIntoStr for conEsscFldKeyName!\n");
		}
		offset += packageBufFld.length();
		packageBuf = packageBuf + packageBufFld;

		packageBufFld = UnionPutFldIntoStr(fldTag.conEsscFldZMKName, zmkName, zmkName.length());
		if (packageBufFld.length() == 0) {
			throw new Exception("in UnionReadKeyBySpecZmk:: UnionPutFldIntoStr for conEsscFldZMKName!\n");
		}
		offset += packageBufFld.length();
		packageBuf = packageBuf + packageBufFld;

		CommWithEsscSvr commWithEsscSvr = new CommWithEsscSvr(this.esscIp, this.esscPort, this.timeOut, this.gunionIDOfEsscAPI);

		int Len = commWithEsscSvr.UnionCommWithEsscSvr("288", packageBuf, offset);

		if (Len < 0) {
			throw new Exception("in UnionReadKeyBySpecZmk:: UnionCommWithHsmSvr!");
		}
		String[] tmpStr = { "", "" };

		tmpStr[0] = UnionReadSpecFldFromStr(commWithEsscSvr.returnPackage, Len, fldTag.conEsscFldKeyValue);

		tmpStr[1] = UnionReadSpecFldFromStr(commWithEsscSvr.returnPackage, Len, fldTag.conEsscFldKeyCheckValue);

		System.arraycopy(tmpStr[0].getBytes(), 0, keyValue, 0, tmpStr[0].getBytes().length);
		System.arraycopy(tmpStr[1].getBytes(), 0, checkValue, 0, tmpStr[1].getBytes().length);

		return 0;
	}
}
