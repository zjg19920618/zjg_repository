package com.boomhope.Bill.Util;


/**
 * 全额止付，只收不付、封闭冻结、挂失不允许开户；部分止付、部分冻结允许开户
 * @author Administrator
 *
 */
public class CardStat
{
//	
	private String acctStat;//账号状态
	private String cardStat;//卡状态
	
	private int stat2;		//[2] 0_无 1_质押止付(封闭冻结) 2_其它止付 3_只收不付 4_部分冻结 
	private int stat3;		//[3] 过期标志 0_未过期 1_已过期 
	private int stat5;		//[5] 1_卡临时挂失 2_卡正式挂失 
	private int stat12;	//[12] 1_密码临时挂失 2_密码正式挂失 
	private int stat22;	//[22] 止付标志 0_无 1_部分止付 2_全额止付
	

	/**
	 * 关联账户：Q-销户;G-挂失;D-封闭冻结;S-只收不付----不允许加挂
	 * @return
	 */
	public String existsPutIntAcct(){
		String code="";
		try
		{
			String stat=cardStat.toUpperCase();
			if("Q".equals(stat)){
				code="销户";
			}else if(stat5==1){
				code="卡临时挂失";
			}else if(stat5==2){
				code="卡正式挂失";
			}else if(stat12==1){
				code="密码临时挂失";
			}else if(stat12==2){
				code="密码正式挂失";
			}else if(stat2==1){
				code="封闭冻结";
			}else if(stat2==3){
				code="只收不付";
			}else{
				code="";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return code;
	}
	
	/**
	 * 是否可以开户
	 * 全额止付，只收不付、封闭冻结、已过期
	 * 卡临时挂失、卡正式挂失、密码临时挂失、密码正式挂失 
	 */
	public String existsOpenAccount(){
//		Map<String,Object> map=new HashMap<String, Object>();
//		int ret=0;
		String code="";
		try
		{
			if(stat22==2){
				code="全额止付";
			}else if(stat2==3){
				code="只收不付";
			}else if(stat2==1){
				code="封闭冻结";
			}else if(stat3==1){
				code="已过期";
			}else if(stat5==1){
				code="卡临时挂失";
			}else if(stat5==2){
				code="卡正式挂失";
			}else if(stat12==1){
				code="密码临时挂失";
			}else if(stat12==2){
				code="密码正式挂失";
			}else{
				code="";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return code;
	}
	
	/**
	[1] 1_冲正结清 2_冲正恢复 
	[2] 0_无 1_质押止付(封闭冻结) 2_其它止付 3_只收不付 4_部分冻结 
	[3] 过期标志 0_未过期 1_已过期 
	[4] 1_没收 
	[5] 1_卡临时挂失 2_卡正式挂失 
	[6] 限制取现 0_不限制 1_限制 
	[7] 验证磁道错误次数(此标志应该单独设立，但唐山已经上线为减少风险故暂放在此) 
	[8] 1_挂失取现 2_挂失补发 
	[9] 1_挂失申请书临时挂失  2_挂失申请书正式挂失 
	[10] 0-折单打印户名 1-不打印  
	[11] 0-新帐户(缺省) 1-旧帐户  
	[12] 1_密码临时挂失 2_密码正式挂失 
	[13] 0_禁止 1_电话转账 
	[14] 验证指纹错误次数 
	[15] 是否换卡 Y_更换过 
	[16] 是否开通银信通 Y_是 
	[17]  钱包帐户是否作废 0 正常 *-作废 
	[18]是否计息Y/N 
	[19] 约定转存 0 未开通 1 开通 
	[20] 电话银行 0 未开通 1 开通 
	[21] 回收标志 0-不可读卡 1-可读卡 
	[22] 止付标志 0_无 1_部分止付 2_全额止付
	[23] 卡多储种标志 1-开通

	 * @param ACCT_STAT
	 */
	public CardStat(String acctStat, String cardStat)
	{
		super();
		this.acctStat = acctStat;
		this.cardStat = cardStat;
		this.stat2=getStat(2);
		this.stat3=getStat(3);
		this.stat5=getStat(5);
		this.stat12=getStat(12);
		this.stat22=getStat(22);
	}

	private int getStat(int k){
		try
		{
			if(this.acctStat.length()>k&&k>0){
				char v=this.acctStat.charAt(k-1);
				if(' '==v){
					return 0;
				}
				return Integer.parseInt(v+"");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	public String getAcctStat()
	{
		return acctStat;
	}

	public String getCardStat()
	{
		return cardStat;
	}

	public int getStat2()
	{
		return stat2;
	}

	public int getStat3()
	{
		return stat3;
	}

	public int getStat5()
	{
		return stat5;
	}

	public int getStat12()
	{
		return stat12;
	}

	public int getStat22()
	{
		return stat22;
	}
	
	

}
