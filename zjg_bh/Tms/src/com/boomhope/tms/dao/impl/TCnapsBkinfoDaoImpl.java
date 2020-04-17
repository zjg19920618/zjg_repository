package com.boomhope.tms.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IBaseParameterDao;
import com.boomhope.tms.dao.ITCnapsBkinfoDao;
import com.boomhope.tms.entity.BaseParameter;
import com.boomhope.tms.entity.MacWarningView;
import com.boomhope.tms.entity.TCnapsBkinfo;

@Repository(value="TCnapsBkinfoDaoImpl")
public class TCnapsBkinfoDaoImpl extends BaseDao<TCnapsBkinfo> implements ITCnapsBkinfoDao {

	@Override
	public List<TCnapsBkinfo> findBankInfoList(Map map) {
		String hql = " from TCnapsBkinfo as a where ";
		
		if( map.get("provinceCode")==null||"".equals(map.get("provinceCode"))){
			return new ArrayList<TCnapsBkinfo>();
		}else{
			hql+=" nodeCode='"+map.get("provinceCode")+"'";
		}
		
		if(map.get("cityCode") != null && !"".equals(map.get("cityCode"))){
			hql +=" and centerCode = '"+map.get("cityCode")+"' ";
		}
		
		if(map.get("bankNo") != null && !"".equals(map.get("bankNo"))){
			hql +=" and bankNo like '%"+map.get("bankNo")+"%' ";
		}
		
		StringBuffer filtrStr=new StringBuffer("%");
		/*如果加上城市的条件有的搜不出来*/
//		if(map.get("cityName") != null && !"".equals(map.get("cityName"))){
//			filtrStr.append((String)map.get("cityName"));
//			filtrStr.append("%");
//		}
//		if(!"".equals(filtrStr)){
//			hql +=" and lname like '"+filtrStr+"' ";
//		}
//		filtrStr=new StringBuffer("%");
		if(map.get("bankName") != null && !"".equals(map.get("bankName"))){
			String bankName=(String)map.get("bankName");
			char[] cs=bankName.toCharArray();
			for (int i = 0; i < cs.length; i++) {
				filtrStr.append(cs[i]);
				filtrStr.append("%");
			}
		}
		if(!"".equals(filtrStr)){
			hql +=" and lname like '"+filtrStr+"' ";
		}
		hql +=" order by bankNo asc";
		List list=findList(hql);
		return list;
	}

	@Override
	public void addBankInfo(TCnapsBkinfo bkInfo) {
		save(bkInfo);
	}

	@Override
	public void updateBankInfo(TCnapsBkinfo bkInfo) {
//		update(bkInfo);
		String hql="update TCnapsBkinfo set bankType='"+bkInfo.getBankType()+"' ,clrBankNo='"+bkInfo.getClrBankNo()+"' ,nodeCode='"+bkInfo.getNodeCode()
				+"' ,suprList='"+bkInfo.getSuprList()+"' ,pbcCode='"+bkInfo.getPbcCode()+"' ,centerCode='"+bkInfo.getCenterCode()+"' ,lname='"+bkInfo.getLname()
				+"' ,sname='"+bkInfo.getSname()+"' ,addr='"+bkInfo.getAddr()+"' ,postCode='"+bkInfo.getPostCode()+"' ,tel='"+bkInfo.getTel()
				+"' ,email='"+bkInfo.getEmail()+"' ,flag='"+bkInfo.getFlag()+"' ,instType='"+bkInfo.getInstType()+"' ,legalPer='"+bkInfo.getLegalPer()
				+"' ,bearBankNo='"+bkInfo.getBearBankNo()+"' ,signFlag='"+bkInfo.getSignFlag()+"' ,issueno='"+bkInfo.getIssueno()
				+"' ,effdate='"+bkInfo.getEffdate()+"' ,invdate='"+bkInfo.getInvdate()+"' ,alttype='"+bkInfo.getAlttype()
				+"'  where bankNo='"+bkInfo.getBankNo()+"'";
		executeHql(hql);
	}

	@Override
	public void deleteBankInfo(List<String> bankNoList) {
		StringBuffer noList=new StringBuffer("'0'");
		for (String string : bankNoList) {
			noList.append(",'");
			noList.append(string);
			noList.append("'");
		}
		String hql="delete from TCnapsBkinfo where bankNo in ("+noList+")";
		
		executeHql(hql);
	}

	
	
}
