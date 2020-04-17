package com.boomhope.Bill.TransService.BillPrint.Interface;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;

/**
 * 个人客户查询( 01020)-前置07519(基本信息)
 * @author Administrator
 *
 */
@Number(id = 1)
public class IdCardCheckInfo {

	//客户号||客户经理编号||客户姓名||简称||英文姓氏||英文名称||证件类别||签发日期||证件号码||失效日期
	//签发地点||出生地点||出生日期||国籍||民族||性别||教育程度||婚姻状况||健康状况||银行服务评级||是否银行员工
	//是否银行股东||建档机构||建档柜员||建档日期||初始密码标志
	
	@FieldSort(NO = 0)
	private String cust_No;//客户号
	@FieldSort(NO = 1)
	private String cust_Manager_No;//客户经理编号
	@FieldSort(NO = 2)
	private String cust_Name;//客户姓名
	@FieldSort(NO = 3)
	private String simple_Name;//简称
	@FieldSort(NO = 4)
	private String E_Sur_Name;//英文姓氏
	@FieldSort(NO = 5)
	private String E_Name;//英文名称
	@FieldSort(NO = 6)
	private String Id_Type;//证件类别
	@FieldSort(NO = 7)
	private String qf_Date;//签发日期
	@FieldSort(NO = 8)
	private String Id_No;//证件号码
	@FieldSort(NO = 9)
	private String out_Date;//失效日期
	@FieldSort(NO = 10)
	private String qf_Address;//签发地址
	@FieldSort(NO = 11)
	private String birth_Place;//出生地点
	@FieldSort(NO = 12)
	private String birthDay;//出生日期
	@FieldSort(NO = 13)
	private String nationality;//国籍
	@FieldSort(NO = 14)
	private String nation;//民族
	@FieldSort(NO = 15)
	private String sex;//性别
	@FieldSort(NO = 16)
	private String educational;//教育程度
	@FieldSort(NO = 17)
	private String civil_State;//婚姻状况
	@FieldSort(NO = 18)
	private String health_State;//健康状况
	@FieldSort(NO = 19)
	private String bank_Service_Level;//银行服务评级
	@FieldSort(NO = 20)
	private String is_Bank_Employee;//是否银行员工
	@FieldSort(NO = 21)
	private String is_Bank_Stockholder;//是否银行股东
	@FieldSort(NO = 22)
	private String file_Organizational;//建档机构
	@FieldSort(NO = 23)
	private String file_Tell_No;//建档柜员
	@FieldSort(NO = 24)
	private String file_Date;//建档日期
	@FieldSort(NO = 25)
	private String init_Pwd_Mark;//初始密码标志
	public String getCust_No() {
		return cust_No;
	}
	public void setCust_No(String cust_No) {
		this.cust_No = cust_No;
	}
	public String getCust_Manager_No() {
		return cust_Manager_No;
	}
	public void setCust_Manager_No(String cust_Manager_No) {
		this.cust_Manager_No = cust_Manager_No;
	}
	public String getCust_Name() {
		return cust_Name;
	}
	public void setCust_Name(String cust_Name) {
		this.cust_Name = cust_Name;
	}
	public String getSimple_Name() {
		return simple_Name;
	}
	public void setSimple_Name(String simple_Name) {
		this.simple_Name = simple_Name;
	}
	public String getE_Sur_Name() {
		return E_Sur_Name;
	}
	public void setE_Sur_Name(String e_Sur_Name) {
		E_Sur_Name = e_Sur_Name;
	}
	public String getE_Name() {
		return E_Name;
	}
	public void setE_Name(String e_Name) {
		E_Name = e_Name;
	}
	public String getId_Type() {
		return Id_Type;
	}
	public void setId_Type(String id_Type) {
		Id_Type = id_Type;
	}
	public String getQf_Date() {
		return qf_Date;
	}
	public void setQf_Date(String qf_Date) {
		this.qf_Date = qf_Date;
	}
	public String getId_No() {
		return Id_No;
	}
	public void setId_No(String id_No) {
		Id_No = id_No;
	}
	public String getOut_Date() {
		return out_Date;
	}
	public void setOut_Date(String out_Date) {
		this.out_Date = out_Date;
	}
	public String getQf_Address() {
		return qf_Address;
	}
	public void setQf_Address(String qf_Address) {
		this.qf_Address = qf_Address;
	}
	public String getBirth_Place() {
		return birth_Place;
	}
	public void setBirth_Place(String birth_Place) {
		this.birth_Place = birth_Place;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEducational() {
		return educational;
	}
	public void setEducational(String educational) {
		this.educational = educational;
	}
	public String getCivil_State() {
		return civil_State;
	}
	public void setCivil_State(String civil_State) {
		this.civil_State = civil_State;
	}
	public String getHealth_State() {
		return health_State;
	}
	public void setHealth_State(String health_State) {
		this.health_State = health_State;
	}
	public String getBank_Service_Level() {
		return bank_Service_Level;
	}
	public void setBank_Service_Level(String bank_Service_Level) {
		this.bank_Service_Level = bank_Service_Level;
	}
	public String getIs_Bank_Employee() {
		return is_Bank_Employee;
	}
	public void setIs_Bank_Employee(String is_Bank_Employee) {
		this.is_Bank_Employee = is_Bank_Employee;
	}
	public String getIs_Bank_Stockholder() {
		return is_Bank_Stockholder;
	}
	public void setIs_Bank_Stockholder(String is_Bank_Stockholder) {
		this.is_Bank_Stockholder = is_Bank_Stockholder;
	}
	public String getFile_Organizational() {
		return file_Organizational;
	}
	public void setFile_Organizational(String file_Organizational) {
		this.file_Organizational = file_Organizational;
	}
	public String getFile_Tell_No() {
		return file_Tell_No;
	}
	public void setFile_Tell_No(String file_Tell_No) {
		this.file_Tell_No = file_Tell_No;
	}
	public String getFile_Date() {
		return file_Date;
	}
	public void setFile_Date(String file_Date) {
		this.file_Date = file_Date;
	}
	public String getInit_Pwd_Mark() {
		return init_Pwd_Mark;
	}
	public void setInit_Pwd_Mark(String init_Pwd_Mark) {
		this.init_Pwd_Mark = init_Pwd_Mark;
	}
	
	
	
	
	
	
	
}
