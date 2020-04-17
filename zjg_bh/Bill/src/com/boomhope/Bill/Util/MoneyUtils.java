package com.boomhope.Bill.Util;

public class MoneyUtils {
	public static void main(String agrs[]) {
		
	}

	/**
	 * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零 要用到正则表达式
	 * @param n
	 * @return
	 */
	public static String digitUppercase(double n) {
		String fraction[] = { "角", "分" };
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

		String head = n < 0 ? "负" : "";
		n = Math.abs(n);

		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
		}
		if (s.length() < 1) {
			s = "整";
		}
		int integerPart = (int) Math.floor(n);

		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
		}
		return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
	}
	
	/**
	 * 整存整取业务凭据中的"存期"数字转换方法
	 * @param num
	 * @return
	 */
	public static String intUppercase(int num){
		String digit[] = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一" };
		
		String result="";
		if(num>0){
			if(num>=12){
				result=digit[num/12]+"年";
			}
			else{
				result=digit[num]+"个月";
			}
		}
		return result;
	}


	/**
	 * 协议存款的"存期"数字转换方法
	 * @param num
	 * @return
	 */
	public static String intUppercaseXYCK(String data){
		String monthsUpper=data.toUpperCase();
		Integer n=Integer.parseInt(monthsUpper.replaceAll("\\D",""));
		if(monthsUpper.indexOf("D")!=-1){
			return n+"天";
		}else if(monthsUpper.indexOf("M")!=-1){
			return digitUppercase(n)+"个月";
		}else if(monthsUpper.indexOf("Y")!=-1){
			return digitUppercase(n)+"年";
		}
		return data;
	}
	
	/**
	 * 目前只支持100以内
	 * @param n
	 * @return
	 */
	private static String digitUppercase(int n){
		if(n>100){
			return n+"";
		}
		String digit[] = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		String unit[]={"","十","百","千","万"};
		String s="";
		for (int i = 0; n>0; i++)
		{
			int k=n%(10);
			s=digit[k]+unit[i]+s;
			n=n/10;
			if(n==0){
				break;
			}
		}
		return s.replaceAll("一十零", "十").replaceAll("十零", "十");
	}
			
			
}
