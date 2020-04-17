    package com.boomhope.Bill.TransService.AccTransfer.TransferUtil;  
      
    import java.util.ArrayList;  
    import java.util.HashMap;  
    import java.util.List;  
    import java.util.Map;  
      
      
    /** 
     * ������԰�������55�������list��Map����ʽ 
     *  
     * @author sandy 
     * @version $Revision: 1.1 $ �������� 2012-5-7 
     */  
    public final class SAXUnionFiled55Utils  
    {  
      
        /** 
         * ����55�� 
         *  
         * ���򽫸��ݲ�ͬ�Ľ������������ͬ�����������������Ľ��������ͷ�����֮�䴫����Щ������IC�����׵��������ݣ����������ǽ����κ��޸ĺʹ��� 
         * Ϊ��Ӧ��������Ҫ���ϱ仯����� 
         * ���������TLV��tag-length-value���ı�ʾ��ʽ����ÿ��������tag��ǩ(T)������ȡֵ�ĳ���(L)������ȡֵ(V)���ɡ� 
         * tag��ǩ������Ϊbit 
         * ����16���Ʊ�ʾ��ռ1��2���ֽڳ��ȡ����磬"9F33"Ϊһ��ռ�������ֽڵ�tag��ǩ����"95"Ϊһ��ռ��һ���ֽڵ�tag��ǩ 
         * ����tag��ǩ�ĵ�һ���ֽ� 
         * ��ע���ֽ�������Ϊ��������������һ���ֽڼ�Ϊ����ߵ��ֽڡ�bit�������ͬ�����ĺ����bitΪ"11111"����˵����tagռ�����ֽ� 
         * ������"9F33"������ռһ���ֽڣ�����"95"�� ���򳤶ȣ���L����������ҲΪbit��ռ1��3���ֽڳ��ȡ��������������£� a) 
         * ��L�ֶ�������ֽڵ�����bitλ����bit8��Ϊ0����ʾ��L�ֶ�ռһ���ֽڣ����ĺ���7��bitλ����bit7��bit1����ʾ����ȡֵ�ĳ��ȣ� 
         * ���ö���������ʾ����ȡֵ���ȵ�ʮ������ 
         * �����磬ĳ����ȡֵռ3���ֽڣ���ô������ȡֵ���ȱ�ʾΪ"00000011"�����ԣ�������ȡֵ�ĳ�����1��127 
         * �ֽ�֮�䣬��ô��L�ֶα����ռһ���ֽڡ� b) 
         * ��L�ֶ�������ֽڵ�����bitλ����bit8��Ϊ1����ʾ��L�ֶβ�ֹռһ���ֽڣ���ô������ռ�����ֽ��ɸ������ֽڵĺ���7��bitλ 
         * ����bit7��bit1����ʮ����ȡֵ��ʾ�����磬�������ֽ�Ϊ10000010����ʾL�ֶγ����ֽ��⣬���滹�������ֽڡ�������ֽ� 
         * ��ʮ����ȡֵ��ʾ����ȡֵ�ĳ��ȡ����磬��L�ֶ�Ϊ"1000 0001 1111 1111"����ʾ������ȡֵռ255���ֽڡ� 
         * ���ԣ�������ȡֵ�ĳ�����128��255�ֽ�֮�䣬��ô��L�ֶα�����ռ�����ֽ� 
         *  
         * @return tlv list 
         */  
        public static List<TLV> saxUnionField55_2List(String hexfiled55)  
        {  
      
            if (null == hexfiled55)  
            {  
                throw new IllegalArgumentException("55���ֵ����Ϊ��!");  
            }  
      
            return builderTLV(hexfiled55);  
        }  
      
        private static List<TLV> builderTLV(String hexString)  
        {  
            List<TLV> tlvs = new ArrayList<TLV>();  
      
            int position = 0;  
            while (position != hexString.length())  
            {  
                String _hexTag = getUnionTag(hexString, position);  
                position += _hexTag.length();  
                  
                LPositon l_position = getUnionLAndPosition(hexString, position);  
                int _vl = l_position.get_vL();  
                  
                position = l_position.get_position();  
                  
                String _value = hexString.substring(position, position + _vl * 2);  
                  
                position = position + _value.length();  
                  
                tlvs.add(new TLV(_hexTag, _vl, _value));  
            }  
            return tlvs;  
        }  
      
        /** 
         * ����55�� 
         *  
         * ���򽫸��ݲ�ͬ�Ľ������������ͬ�����������������Ľ��������ͷ�����֮�䴫����Щ������IC�����׵��������ݣ����������ǽ����κ��޸ĺʹ��� 
         * Ϊ��Ӧ��������Ҫ���ϱ仯����� 
         * ���������TLV��tag-length-value���ı�ʾ��ʽ����ÿ��������tag��ǩ(T)������ȡֵ�ĳ���(L)������ȡֵ(V)���ɡ� 
         * tag��ǩ������Ϊbit 
         * ����16���Ʊ�ʾ��ռ1��2���ֽڳ��ȡ����磬"9F33"Ϊһ��ռ�������ֽڵ�tag��ǩ����"95"Ϊһ��ռ��һ���ֽڵ�tag��ǩ 
         * ����tag��ǩ�ĵ�һ���ֽ� 
         * ��ע���ֽ�������Ϊ��������������һ���ֽڼ�Ϊ����ߵ��ֽڡ�bit�������ͬ�����ĺ����bitΪ"11111"����˵����tagռ�����ֽ� 
         * ������"9F33"������ռһ���ֽڣ�����"95"�� ���򳤶ȣ���L����������ҲΪbit��ռ1��3���ֽڳ��ȡ��������������£� a) 
         * ��L�ֶ�������ֽڵ�����bitλ����bit8��Ϊ0����ʾ��L�ֶ�ռһ���ֽڣ����ĺ���7��bitλ����bit7��bit1����ʾ����ȡֵ�ĳ��ȣ� 
         * ���ö���������ʾ����ȡֵ���ȵ�ʮ������ 
         * �����磬ĳ����ȡֵռ3���ֽڣ���ô������ȡֵ���ȱ�ʾΪ"00000011"�����ԣ�������ȡֵ�ĳ�����1��127 
         * �ֽ�֮�䣬��ô��L�ֶα����ռһ���ֽڡ� b) 
         * ��L�ֶ�������ֽڵ�����bitλ����bit8��Ϊ1����ʾ��L�ֶβ�ֹռһ���ֽڣ���ô������ռ�����ֽ��ɸ������ֽڵĺ���7��bitλ 
         * ����bit7��bit1����ʮ����ȡֵ��ʾ�����磬�������ֽ�Ϊ10000010����ʾL�ֶγ����ֽ��⣬���滹�������ֽڡ�������ֽ� 
         * ��ʮ����ȡֵ��ʾ����ȡֵ�ĳ��ȡ����磬��L�ֶ�Ϊ"1000 0001 1111 1111"����ʾ������ȡֵռ255���ֽڡ� 
         * ���ԣ�������ȡֵ�ĳ�����128��255�ֽ�֮�䣬��ô��L�ֶα�����ռ�����ֽ� 
         *  
         * @return tlv map 
         */  
        public static Map<String, TLV> saxUnionField55_2Map(String hexfiled55)  
        {  
      
            if (null == hexfiled55)  
            {  
                throw new IllegalArgumentException("55���ֵ����Ϊ��!");  
            }  
      
            return builderKeyAndTLV(hexfiled55);  
        }  
      
        public static Map<String, TLV> builderKeyAndTLV(String hexString)  
        {  
      
            Map<String, TLV> tlvs = new HashMap<String, TLV>();  
      
            int position = 0;  
            while (position != hexString.length())  
            {  
                String _hexTag = getUnionTag(hexString, position);  
                position += _hexTag.length();  
                LPositon l_position = getUnionLAndPosition(hexString, position);  
                int _vl = l_position.get_vL();  
                position = l_position.get_position();  
                String _value = hexString.substring(position, position + _vl * 2);  
                position = position + _value.length();  
                tlvs.put(_hexTag, new TLV(_hexTag, _vl, _value));  
            }  
            return tlvs;  
        }  
      
        /** 
         * ��������Value�ĳ��� 
         *  
         * @param hexString 
         * @param position 
         * @return 
         */  
        private static LPositon getUnionLAndPosition(String hexString, int position)  
        {  
      
            String firstByteString = hexString.substring(position, position + 2);  
            int i = Integer.parseInt(firstByteString, 16);  
            String hexLength = "";  
      
            if (((i >>> 7) & 1) == 0)  
            {  
                hexLength = hexString.substring(position, position + 2);  
                position = position + 2;  
      
            } else  
            {  
                // ��������bitλΪ1��ʱ��ȡ�ú�7bit��ֵ��  
                int _L_Len = i & 127;  
                position = position + 2;  
                hexLength = hexString.substring(position, position + _L_Len * 2);  
                // position��ʾ��һ���ֽڣ�����ı�ʾ�ж��ٸ��ֽ�����ʾ�����Valueֵ  
                position = position + _L_Len * 2;  
      
            }  
            return new LPositon(Integer.parseInt(hexLength, 16), position);  
      
        }  
      
        private static String getUnionTag(String hexString, int position)  
        {  
            String firstByte = hexString.substring(position, position + 2);  
            int i = Integer.parseInt(firstByte, 16);  
            if ((i & 0x1f) == 0x1f)  
            {  
                return hexString.substring(position, position + 4);  
      
            } else  
            {  
                return hexString.substring(position, position + 2);  
            }  
      
        }  
      
        static class LPositon  
        {  
            private int _vL;  
            private int _position;  
      
            public LPositon(int _vL, int position)  
            {  
                this._vL = _vL;  
                this._position = position;  
            }  
      
            public int get_vL()  
            {  
                return _vL;  
            }  
      
            public void set_vL(int _vL)  
            {  
                this._vL = _vL;  
            }  
      
            public int get_position()  
            {  
                return _position;  
            }  
      
            public void set_position(int _position)  
            {  
                this._position = _position;  
            }  
      
        }  
      
        public static void main(String[] args)  
        {  
            List<TLV> list = SAXUnionFiled55Utils  
                    .saxUnionField55_2List("9F2608BC661B269CE724D89F2701809F101307010103A0A000010A01000000000067706DBA9F3704064B14799F360200B9950580200408409A031705049C01179F02060000000000045F2A02015682027C009F1A0201569F03060000000000009F33032040C89F34030203029F3501219F1E08626F686F6E6720208408A0000003330101019F090200309F410200239F631030333133313234300000000000000000");
            //9F26083518D038C8E4F7569F2701809F101307010103A0A000010A010000030000B688563F9F37047A57B02C9F36020421950580200408409A031705039C01179F02060000000000045F2A02015682027C009F1A0201569F03060000000000009F33032040C89F34030203029F3501219F1E08626F686F6E6720208408A0000003330101019F090200309F410200239F631030333133313234300000000000000000
            //9F2608BC661B269CE724D89F2701809F101307010103A0A000010A01000000000067706DBA9F3704064B14799F360200B9950580200408409A031705049C01179F02060000000000045F2A02015682027C009F1A0201569F03060000000000009F33032040C89F34030203029F3501219F1E08626F686F6E6720208408A0000003330101019F090200309F410200239F631030333133313234300000000000000000
            Map<String,String> map = new HashMap<String, String>();
            for (TLV tlv : list)  
            {  
            	map.put(tlv.getTag(), tlv.getValue());
                System.out.println(tlv);  
            }  
            System.out.println(map.get("9F02"));
        }  
      
    }  