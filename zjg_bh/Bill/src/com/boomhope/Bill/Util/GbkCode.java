package com.boomhope.Bill.Util;

import java.io.UnsupportedEncodingException;

public class GbkCode {
    private static String hexString = "0123456789ABCDEF";
 
    private static int hex2Dec(char ch) {
        if (ch == '0')
            return 0;
        if (ch == '1')
            return 1;
        if (ch == '2')
            return 2;
        if (ch == '3')
            return 3;
        if (ch == '4')
            return 4;
        if (ch == '5')
            return 5;
        if (ch == '6')
            return 6;
        if (ch == '7')
            return 7;
        if (ch == '8')
            return 8;
        if (ch == '9')
            return 9;
        if (ch == 'a')
            return 10;
        if (ch == 'A')
            return 10;
        if (ch == 'B')
            return 11;
        if (ch == 'b')
            return 11;
        if (ch == 'C')
            return 12;
        if (ch == 'c')
            return 12;
        if (ch == 'D')
            return 13;
        if (ch == 'd')
            return 13;
        if (ch == 'E')
            return 14;
        if (ch == 'e')
            return 14;
        if (ch == 'F')
            return 15;
        if (ch == 'f')
            return 15;
        else
            return -1;
 
    }
 
    public static String decode(String hexStr) throws UnsupportedEncodingException {
        if (null == hexStr || "".equals(hexStr) || (hexStr.length()) % 2 != 0) {
            return null;
        }
 
        int byteLength = hexStr.length() / 2;
        byte[] bytes = new byte[byteLength];
 
        int temp = 0;
        for (int i = 0; i < byteLength; i++) {
            temp = hex2Dec(hexStr.charAt(2 * i)) * 16 + hex2Dec(hexStr.charAt(2 * i + 1));
            bytes[i] = (byte) (temp < 128 ? temp : temp - 256);
        }
        return new String(bytes,"GBK");
    }
 
    /*
     * ���ַ�����16��������,�����������ַ�������ģ�
     */
    public static String encode(String str) throws UnsupportedEncodingException {
        // ���Ĭ�ϱ����ȡ�ֽ�����
        byte[] bytes = str.getBytes("GBK");
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // ���ֽ�������ÿ���ֽڲ���2λ16��������
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }
     
    public static void main(String[] args) throws UnsupportedEncodingException{
        String han=".";
        String result=encode(han);
        System.out.println("ת����"+result);
        System.out.println("C4FAB1BEB4CED6D8D6C3C3DCC2EBC7EBC7F3B5C4B3F5CABCC3DCC2EBCAC73A".equals(result));
        System.out.println("��ת���"+ decode(result));
    }
}
