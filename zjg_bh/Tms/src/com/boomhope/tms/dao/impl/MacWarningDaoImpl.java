package com.boomhope.tms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IMacWarningDao;
import com.boomhope.tms.entity.BaseParameter;
import com.boomhope.tms.entity.MacWarning;
import com.boomhope.tms.util.Dict;

@Repository(value="macWarningDao")
public class MacWarningDaoImpl extends BaseDao<MacWarning> implements IMacWarningDao {

	@Override
	public boolean ifRegiter(String machineNo) {
		String hql = " from MacWarning as a where a.machineNo='"+machineNo+"' and a.proStatus='"+Dict.MAC_WAR_ON+"'";
		List list = this.findList(hql);
		if(list != null && list.size() >0){
			return true;
		}
		return false;
	}

	@Override
	public BaseParameter findMacWarByType() {
		String hql = "from BaseParameter where parameterType = 'war_type'";
		return (BaseParameter) this.findOne(hql);
	}

	@Override
	public List<BaseParameter> findBaseParameterList() {
		String hql = "from BaseParameter where 1=1";
		return (List<BaseParameter>) this.findList(hql);
	}

	@Override
	public void updateBaseParameter(BaseParameter baseParameter) {
		String hql = "update BaseParameter set parameterType='"+baseParameter.getParameterType()+"',parameterName='"+baseParameter.getParameterName()+"',parameterValue = '"+baseParameter.getParameterValue()+"' where id = '"+baseParameter.getId()+"' ";
		this.executeHql(hql);
		
	}





	
}
