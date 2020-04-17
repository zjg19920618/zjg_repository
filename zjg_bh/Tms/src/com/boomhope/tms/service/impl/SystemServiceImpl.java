package com.boomhope.tms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.boomhope.tms.dao.IBaseDictDao;
import com.boomhope.tms.dao.IBaseMenuDao;
import com.boomhope.tms.dao.IBaseParameterDao;
import com.boomhope.tms.dao.IBaseRoleDao;
import com.boomhope.tms.dao.IBaseRoleMenuDao;
import com.boomhope.tms.dao.IBaseUnitDao;
import com.boomhope.tms.dao.IBaseUserDao;
import com.boomhope.tms.dao.IBaseUserRoleMapDao;
import com.boomhope.tms.dao.IUnitViewDao;
import com.boomhope.tms.entity.BaseDict;
import com.boomhope.tms.entity.BaseMenu;
import com.boomhope.tms.entity.BaseParameter;
import com.boomhope.tms.entity.BaseRole;
import com.boomhope.tms.entity.BaseRoleMenu;
import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.BaseUserRoleMap;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.UnitView;
import com.boomhope.tms.service.ISystemService;

@SuppressWarnings("unused")
@Service("systemService")
public class SystemServiceImpl implements ISystemService{
	private static final Log log = LogFactory.getLog(ManuServiceImpl.class);

	private IBaseUserDao baseUserDao;
	private IBaseRoleDao baseRoleDao;
	private IBaseUserRoleMapDao baseUserRoleMapDao;
	private IBaseDictDao baseDictDao;
	private IBaseMenuDao baseMenuDao;
	private IBaseRoleMenuDao baseRoleMenuDao;
	private IUnitViewDao unitViewDao;
	private IBaseUnitDao baseUnitDao;
	private IBaseParameterDao baseParameterDao;
	
	
	@Resource(name="baseParameterDao")
	public void setBaseParameterDao(IBaseParameterDao baseParameterDao) {
		this.baseParameterDao = baseParameterDao;
	}

	@Resource(name="baseUnitDao")
	public void setBaseUnitDao(IBaseUnitDao baseUnitDao) {
		this.baseUnitDao = baseUnitDao;
	}

	@Resource(name="unitViewDao")
	public void setUnitViewDao(IUnitViewDao unitViewDao) {
		this.unitViewDao = unitViewDao;
	}

	@Resource(name="baseRoleMenuDao")
	public void setBaseRoleMenuDao(IBaseRoleMenuDao baseRoleMenuDao) {
		this.baseRoleMenuDao = baseRoleMenuDao;
	}

	@Resource(name="baseMenuDao")
	public void setBaseMenuDao(IBaseMenuDao baseMenuDao) {
		this.baseMenuDao = baseMenuDao;
	}

	@Resource(name="baseDictDao")
	public void setBaseDictDao(IBaseDictDao baseDictDao) {
		this.baseDictDao = baseDictDao;
	}

	@Resource(name="baseUserRoleMapDao")
	public void setBaseUserRoleMapDao(IBaseUserRoleMapDao baseUserRoleMapDao) {
		this.baseUserRoleMapDao = baseUserRoleMapDao;
	}

	@Resource(name="baseRoleDao")
	public void setBaseRoleDao(IBaseRoleDao baseRoleDao) {
		this.baseRoleDao = baseRoleDao;
	}

	@Resource(name="baseUserDao")
	public void setBaseUserDao(IBaseUserDao baseUserDao) {
		this.baseUserDao = baseUserDao;
	}

	@Override
	public List<BaseUser> findBeseUserList(Page page, Map<String, String> map) {
		List<BaseUser> list = baseUserDao.findBaseuser(page, map);
		return list;
	}

	@Override
	public void saveBaseUser(BaseUser baseUser) {
		
		baseUserDao.saveOrUpdate(baseUser);
	}

	@Override
	public void delBaseuser(String  username) {
		baseUserDao.delBaseuser(username);
	}

	@Override
	public List<BaseRole> findBaseRole() {
		
		return baseRoleDao.findAll();
	}

	@Override
	public List<BaseRole> getUserRole(String username) {
		return baseRoleDao.getUserRole(username);
	}

	@Override
	public void saveUserRole(String userName, String[] roleCodes) {
		baseUserRoleMapDao.delUserRoleMapByUserName(userName);
		for (int i = 0; i < roleCodes.length; i++) {
			if (!roleCodes[i].equals("") && roleCodes[i]!=null) {
				int roleCode = Integer.parseInt(roleCodes[i]);
				BaseUserRoleMap b = new BaseUserRoleMap();
				BaseRole baseRole = new BaseRole();
				baseRole.setRoleCode(roleCode);
				b.setBaseRole(baseRole);
				BaseUser baseUser = new BaseUser();
				baseUser.setUsername(userName);
				b.setBaseUser(baseUser);
				baseUserRoleMapDao.save(b);
			}
		}
	}

	@Override
	public List<BaseDict> getDict(String groupName) {
		return baseDictDao.getDict(groupName);
	}

	@Override
	public void saveRole1(BaseRole baseRole) {
		baseRoleDao.UpdateBaseRole(baseRole);
	}
	
	@Override
	public void saveRole(BaseRole baseRole) {
		baseRoleDao.save(baseRole);
	}
	@Override
	public List<BaseMenu> getBaseMenuList(int roleCode) {
		List<BaseMenu> menuList =  getMenu(0);
		List<Integer> baseMenuList = baseMenuDao.getBaseMenuList(roleCode);
		if(baseMenuList != null && baseMenuList.size() > 0){
			for (BaseMenu menu : menuList) {
				if(baseMenuList.indexOf(menu.getMenuId()) != -1){
					menu.setSelectd("Y");
				}else{
					menu.setSelectd("N");
				}
			}
			
		}
		
		return menuList;
	}
	
	public List<BaseMenu> getMenu(int parentId){
		List<BaseMenu> all = new ArrayList<BaseMenu>();
		List<BaseMenu> parentList = baseMenuDao.getBaseMenuListByParent(parentId);
		if(parentList ==null || parentList.size() == 0){
			return null;
		}
		for (BaseMenu baseMenu : parentList) {
			//查询是否选中
			
			all.add(baseMenu);
			List<BaseMenu> childList = getMenu(baseMenu.getMenuId());
			if(childList ==null || childList.size() == 0){
				continue;
			}
			for (BaseMenu baseMenu2 : childList) {
				all.add(baseMenu2);
			}
		}
		return all;
	}

	@Override
	public List<BaseMenu> getBaseMenuList() {
		return baseMenuDao.getBaseMenuList();
	}

	@Override
	public void delBaseRole(int roleCode) {
		// 删除角色菜单表
		baseRoleMenuDao.delRoleMenuByRoleCode(roleCode);
		// 删除用户角色表
		baseUserRoleMapDao.delUserRoleMapByRoleCode(roleCode);
		// 删除角色表
		BaseRole b = new BaseRole();
		b.setRoleCode(roleCode);
		baseRoleDao.delete(b);
	}

	@Override
	public List<UnitView> getUnitViewList(Page page, Map<String, String> map) {
		return unitViewDao.getUnitViewList(page, map);
	}

	@Override
	public void saveBaseUnit(BaseUnit baseUnit) {
		baseUnitDao.saveBaseUnit(baseUnit);
	}

	@Override
	public void delBaseUnit(BaseUnit baseUnit) {
		baseUnitDao.delete(baseUnit);
	}

	@Override
	public List<BaseRole> findBaseRoleList(Page page, Map<String, String> map,
			String sort) {
		 List<BaseRole> list = baseRoleDao.findBaseRoleList(page, map, sort);
		 log.debug(list);
		 return list;
	}

	@Override
	public List<UnitView> getUnitViewList(Map<String, String> map) {
		return unitViewDao.getUnitViewList(map);
	}

	@Override
	public void saveRoleMenu(String[] menuIds, Integer roleId) {
		// 删除角色菜单
		baseUserRoleMapDao.delUserRoleMapByRoleCode(roleId);
		// 保存角色菜单
		for (String str : menuIds) {
			if (!str.equals("")&&str!=null) {
			BaseRoleMenu b = new BaseRoleMenu();
			BaseMenu bm = new BaseMenu();
			bm.setMenuId(Integer.parseInt(str));
			b.setBaseMenu(bm);
			
			BaseRole br = new BaseRole();
			br.setRoleCode(roleId);
			b.setBaseRole(br);
			baseUserRoleMapDao.save(b);
			}
		}
	}

	@Override
	public List<BaseUnit> findBaseUnitCN(String unitCode) {
		return baseUserRoleMapDao.findBaseUnitCN(unitCode);
	}

	@Override
	public List<BaseUnit> findBaseUnitCN1(String unitName) {
		return baseUserRoleMapDao.findBaseUnitCN1(unitName);
	}


	@Override
	public List<BaseParameter> findBaseParameterByParentType(String type) {
		return baseParameterDao.findBaseParameterByParentType(type);
	}


	@Override
	public List<BaseRole> findBaseRoleTest(String roleName) {
		
		return baseUserRoleMapDao.findBaseRoleTest(roleName);
	}

	@Override
	public List<BaseUser> findBeanEmail(String username,String Email) {
		
		return baseUserRoleMapDao.findBeanEmail(username,Email);
	}

	@Override
	public List<BaseUser> findBuName(String username) {
		
		return baseUserRoleMapDao.findBuName(username);
	}

	@Override
	public List<BaseUser> findBuName1(String email) {
		
		return baseUserRoleMapDao.findBuName1(email);
	}
	
	@Override
	public BaseUnit getBaseUintByMachineNo(String machineNo) {
		return baseUnitDao.getBaseUintByMachineNo(machineNo);
	}

	@Override
	public void editBaseUnit1(BaseUnit unit) {
		baseUnitDao.editBaseUnit1(unit);
		
	}

	@Override
	public void saveBaseUser1(BaseUser baseUser) {
		baseUserDao.saveBaseUser1(baseUser);
		
	}

	@Override
	public List<BaseUser> findBeanEmail1(String username, String email) {
		return 	baseUserDao.findBeanEmail1(username,email);
		
	}

	@Override
	public List<BaseRole> findBaseRoleTest1(String roleName, int roleCode) {
		
		return 	baseUserDao.findBaseRoleTest1(roleName,roleCode);
	}

	@Override
	public BaseUser findOne(String userName) {
		return baseUserDao.findOne(BaseUser.class, userName);
	}

	@Override
	public List<BaseUnit> findBaseUnitCN2(String unitCode, String unitName) {
		
		return baseUserDao.findBaseUnitCN2(unitCode,unitName);
	}

	@Override
	public BaseUnit findcreatDate(String unitCode) {
		
		return baseUserDao.findcreatDate(unitCode);
	}

	@Override
	public void saveBaseRole(BaseUserRoleMap baseUserRoleMap) {
		baseRoleDao.saveOrUpdate(baseUserRoleMap);
		
	}

	@Override
	public void saveBaseRoleMenu(BaseRoleMenu baseRoleMenu) {
		baseMenuDao.save(baseRoleMenu);
	}

	@Override
	public List<BaseUnit> getBaseUnitCityS(String districtCounty) {
		return baseUnitDao.getBaseUnitCityS(districtCounty);
	}

	@Override
	public List<UnitView> getUnitViewEXCEl(Map<String, String> map) {
		return baseUnitDao.getUnitViewEXCEl(map);
	}

	@Override
	public void delBaseuser1(String username) {
		baseUserDao.delBaseuser1(username);
	}
}
