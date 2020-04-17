package com.boomhope.tms.dao.impl;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.hql.internal.ast.QueryTranslatorImpl;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;

import com.boomhope.tms.dao.IBaseDao;
import com.boomhope.tms.entity.Page;

@SuppressWarnings("all")
public abstract class BaseDaoOrcl<T extends Serializable> implements IBaseDao<T> {

	@Autowired
	protected SessionFactory sessionFactory;
	
	/** 泛型实体类 */
	protected Class<T> entityClass;
	/** 泛型实体类对应的映射信息 */
	protected AbstractEntityPersister persister;
	/** 主键字段映射的实体字段名，只能是单一主键 */
	protected String idField;
	/** 实体对应的表名 */
	protected String tableName;
	
	public BaseDaoOrcl() {
		entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/**
	 * 在dao创建完成后执行,设置一些属性
	 */
	@PostConstruct
	public void postConstruct(){
		persister = getPersister(entityClass);
		tableName = persister.getTableName();
		idField = persister.getIdentifierPropertyName();
	}
	
	protected AbstractEntityPersister getPersister(Class<?> c) {
		return (AbstractEntityPersister) sessionFactory.getClassMetadata(c);
	}
	
	/**
	 * 获得session
	 * @return
	 */
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 获得query
	 * @param hql
	 * @return
	 */
	protected Query getQuery(String hql) {
		return getCurrentSession().createQuery(hql);
	}
	
	/**
	 * 获得query
	 * @param hql
	 * @return
	 */
	protected SQLQuery getSQLQuery(String sql) {
		return getCurrentSession().createSQLQuery(sql);
	}
	
	/**
	 * 获得query并设置参数
	 * @param hql
	 * @param param 参数,类型可为：List、Map、Object[]、Entity
	 * @return
	 */
	protected Query getQuery(String hql, Object param) {
		if(param == null){
			return getQuery(hql);
		}
		if(param instanceof List){
			return getQuery(hql, (List)param);
		}
		if(param instanceof Map){
			return getQuery(hql, (Map)param);
		}
		if(param instanceof Object[]){
			return getQuery(hql, (Object[])param);
		}
		Query query = getQuery(hql);
		query.setProperties(param);
		return query;
	}
	
	/**
	 * 获得SQLQuery并设置参数
	 * @param sql sql语句
	 * @param param 参数,类型可为：List、Map、Object[]、Entity
	 * @return
	 */
	protected SQLQuery getSQLQuery(String sql, Object param) {
		if(param == null){
			return getSQLQuery(sql);
		}
		if(param instanceof List){
			return getSQLQuery(sql, (List)param);
		}
		if(param instanceof Map){
			return getSQLQuery(sql, (Map)param);
		}
		if(param instanceof Object[]){
			return getSQLQuery(sql, (Object[])param);
		}
		SQLQuery sqlQuery = getSQLQuery(sql);
		sqlQuery.setProperties(param);
		return sqlQuery;
	}
	
	/**
	 * 获得query并且设置参数
	 * @param hql
	 * @param param map参数
	 * @return
	 */
	protected Query getQuery(String hql, Map<String, Object> param) {
		Query query = getQuery(hql);
		removeUnUserfulKey(param);
		query.setProperties(param);
		return query;
	}
	
	/**
	 * 获得SQLQuery并且设置参数
	 * @param sql
	 * @param param map参数
	 * @return
	 */
	protected SQLQuery getSQLQuery(String sql, Map<String, Object> param) {
		SQLQuery sqlQuery = getSQLQuery(sql);
		removeUnUserfulKey(param);
		sqlQuery.setProperties(param);
		return sqlQuery;
	}

	/**
	 * 获得query并且设置参数
	 * @param hql
	 * @param param list参数
	 * @return
	 */
	protected Query getQuery(String hql, List<Object> param) {
		Query query = getQuery(hql);
		setParameter(query, param);
		return query;
	}
	
	/**
	 * 获得query并且设置参数
	 * @param hql
	 * @param param Object数组
	 * @return
	 */
	protected Query getQuery(String hql, Object[] param) {
		Query query = getQuery(hql);
		setParameter(query, param);
		return query;
	}
	
	/**
	 * 获得SQLQuery并且设置参数
	 * @param sql
	 * @param param list参数
	 * @return
	 */
	protected SQLQuery getSQLQuery(String sql, List<Object> param) {
		SQLQuery sqlQuery = getSQLQuery(sql);
		setParameter(sqlQuery, param);
		return sqlQuery;
	}
	
	/**
	 * 获得SQLQuery并且设置参数
	 * @param sql
	 * @param param Object数组
	 * @return
	 */
	protected SQLQuery getSQLQuery(String sql, Object[] param) {
		SQLQuery sqlQuery = getSQLQuery(sql);
		setParameter(sqlQuery, param);
		return sqlQuery;
	}
	
	/**
	 * 设置query的参数
	 * @param query
	 * @param param list参数
	 */
	protected void setParameter(Query query, List<Object> param) {
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				query.setParameter(i, param.get(i));
			}
		}
	}
	
	/**
	 * 设置query的参数
	 * @param query
	 * @param param Object数组参数
	 */
	protected void setParameter(Query query, Object[] param) {
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}
	}
	
	/**
	 * 保存实体
	 * @param entity 实体
	 * @return 返回保存的实体
	 */
	@Override
	public Serializable save(Object entity) {
		return getCurrentSession().save(entity);
	}

	/**
	 * 删除实体
	 * @param entity 实体
	 */
	@Override
	public void delete(Object entity) {
		getCurrentSession().delete(entity);
	}
	
	/**
	 * 更新实体
	 * @param entity 实体
	 */
	@Override
	public void update(Object entity) {
		getCurrentSession().update(entity);
	}

	/**
	 * 保存或更新实体
	 * @param entity 实体
	 */
	@Override
	public void saveOrUpdate(Object entity) {
		getCurrentSession().saveOrUpdate(entity);
	}
	
	/**
     * 判断主键对应的实体是否存在
     * @param id 主键
     * @return 存在 返回true，否则false
     */
	@Override
	public boolean exists(Serializable id){
		return exists(entityClass, id);
	}
	
	/**
     * 判断主键对应的实体是否存在
     * @param clazz 要判断的实体类
     * @param id 主键
     * @return 存在 返回true，否则false
     */
	@Override
	public <M> boolean exists(Class<M> clazz, Serializable id){
		M m = findOne(clazz, id);
		return m!=null;
	}
	
	/**
	 * 根据主键id查询获得实体
	 * @param id 主键
	 * @return 返回id对应的实体
	 */
	@Override
	public T findOne(Serializable id) {
		return findOne(entityClass, id);
	}
	
	/**
	 * 根据主键id查询获得实体
	 * @param clazz 要查询的实体类
	 * @param id 主键
	 * @return 返回id对应的实体
	 */
	@Override
	public <M> M findOne(Class<M> clazz, Serializable id) {
		return (M) getCurrentSession().get(clazz, id);
	}
	
	/**
	 * 查询获得一条记录
	 * @param hql 查询语句
	 * @return 返回符合条件的第一条记录
	 */
	protected Object findOne(String hql) {
		List<?> list = findList(hql, null, null, null);
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}

	/**
	 * 查询获得一条记录
	 * @param hql 查询语句
	 * @param param 查询参数,类型可为：List、Map、Object[]、Entity
	 * @return 返回符合条件的第一条记录
	 */
	protected Object findOne(String hql, Object param) {
		List<?> list = findList(hql, param, null, null);
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}
	
	/**
	 * 查询实体所有记录
	 * @return 返回实体集合
	 */
	@Override
	public List<T> findAll() {
		return findAll(entityClass, null, null);
	}
	
	/**
	 * 查询实体所有记录，可分页
	 * @param page 分页信息
	 * @return 返回实体集合
	 */
	@Override
	public List<T> findAll(Page page) {
		return findAll(entityClass, page, null);
	}
	
	/**
	 * 查询实体所有记录，可排序
	 * @param sort 排序字符串，格式如：id asc, name desc
	 * @return 返回实体集合
	 */
	@Override
	public List<T> findAll(String sort) {
		return findAll(entityClass, null, sort);
	}

	/**
	 * 查询实体所有记录，可分页、排序
	 * @param page 分页信息
	 * @param sort 排序字符串，格式如：id asc, name desc
	 * @return 返回实体集合
	 */
	@Override
	public List<T> findAll(Page page, String sort) {
		return findAll(entityClass, page, sort);
	}
	
	/**
	 * 查询实体所有记录
	 * @param clazz 要查询的实体类
	 * @return 返回实体集合
	 */
	@Override
	public <M> List<M> findAll(Class<M> clazz) {
		return findAll(clazz, null, null);
	}
	
	/**
	 * 查询实体所有记录，可分页
	 * @param clazz 要查询的实体类
	 * @param page 分页信息
	 * @return 返回实体集合
	 */
	@Override
	public <M> List<M> findAll(Class<M> clazz, Page page) {
		return findAll(clazz, page, null);
	}
	
	/**
	 * 查询实体所有记录，可排序
	 * @param clazz 要查询的实体类
	 * @param sort 排序字符串，格式如：id asc, name desc
	 * @return 返回实体集合
	 */
	@Override
	public <M> List<M> findAll(Class<M> clazz, String sort) {
		return findAll(clazz, null, sort);
	}
	
	/**
	 * 查询实体所有记录，可分页、排序
	 * @param clazz 要查询的实体类
	 * @param page 分页信息
	 * @param sort 排序字符串，格式如：id asc, name desc
	 * @return 返回实体集合
	 */
	@Override
	public <M> List<M> findAll(Class<M> clazz, Page page, String sort) {
		String hql = "from " + clazz.getName() + " where 1=1 ";
		return (List<M>) findList(hql, null, page, sort);
	}
	
	/**
	 * 根据属性查找记录
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return
	 */
	@Override
	public List<T> findByProperty(String propertyName, Object propertyValue) {
		return findByPropertyWithOperator(entityClass, propertyName, propertyValue, "=");
	}

	/**
	 * 根据属性通过传入的运算符查询记录
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param operator 运算符
	 * @return List
	 */
	@Override
	public List<T> findByPropertyWithOperator(String propertyName, Object propertyValue, String operator) {
		return findByPropertyWithOperator(entityClass, propertyName, propertyValue, operator);
	}
	
	/**
	 * 根据属性查找记录
	 * @param clazz 要查询的实体类
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return
	 */
	@Override
	public <M> List<M> findByProperty(Class<M> clazz, String propertyName, Object propertyValue) {
		return findByPropertyWithOperator(clazz, propertyName, propertyValue, "=");
	}

	/**
	 * 根据属性通过传入的运算符查询记录
	 * @param clazz 要查询的实体类
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param operator 运算符
	 * @return List
	 */
	@Override
	public <M> List<M> findByPropertyWithOperator(Class<M> clazz, String propertyName, Object propertyValue, String operator) {
		String hql = "from " + clazz.getName() + " model where model." + propertyName + " " + operator + ":"+propertyName;
		Map map = new HashMap();
		map.put(propertyName, propertyValue);
		return (List<M>) findList(hql, map, null, null);
	}
	
	/**
	 * 查询实体集合
	 * @param hql 查询语句
	 * @return 返回符合条件的实体集合
	 */
	protected List<?> findList(String hql) {
		return findList(hql, null, null, null);
	}
	
	/**
	 * 查询实体集合
	 * @param hql 查询语句
	 * @param param 查询参数,类型可为：List、Map、Object[]、Entity
	 * @return 返回符合条件的实体集合
	 */
	protected List<?> findList(String hql, Object param) {
		return findList(hql, param, null, null);
	}
	
	/**
	 * 查询实体集合, 可分页
	 * @param hql 查询语句
	 * @param param 查询参数,类型可为：List、Map、Object[]、Entity
	 * @param page 分页信息
	 * @return 返回符合条件的实体集合
	 */
	protected List<?> findList(String hql, Object param, Page page, String sort) {
		if(page != null && page.getSortString()!=null && (sort == null || "".equals(sort.trim()))){
			sort = page.getSortString();
		}
		if(sort != null && !"".equals(sort.trim())){
			if(sort.toLowerCase().trim().startsWith("order ")){
				hql += sort;
			}else{
				hql += " order by " + sort;
			}
		}
		Query query = getQuery(hql, param);
		if (page != null) {
			Integer rowCount = Integer.valueOf(countNoCount(hql, param).toString());
			page.setRowCount(rowCount);
			query.setFirstResult(page.getRowStart()).setMaxResults(page.getPageSize());
		}
		return query.list();
	}
	
	/**
	 * 统计实体总数
	 * @return 返回总记录数
	 */
	@Override
	public Long count(){
		return count(entityClass);
	}
	
	/**
	 * 统计实体总数
	 * @param clazz 要统计的实体类
	 * @return 返回总记录数
	 */
	@Override
	public <M> Long count(Class<M> clazz){
		return (Long) getQuery("select count(*) from " + clazz.getName()).uniqueResult();
	}
	
	
	/**
	 * 根据hql和参数统计，hql语句必须以 select count(*) 开始
	 * @param hql hql语句，必须类似 select count(*) from table
	 * @param param 查询参数,类型可为：List、Map、Object[]、Entity
	 * @return
	 */
	protected Long count(String hql, Object param) {
		return (Long) getQuery(hql, param).uniqueResult();
	}

	/**
	 * 根据hql和参数统计，hql语句不是以 select count(*) 开始的
	 * @param hql hql语句，不必类似select count(*) from table这种语句，主要统计类似有 group by这种的
	 * @param param 查询参数,类型可为：List、Map、Object[]、Entity
	 * @return
	 */
	protected BigDecimal countNoCount(String hql, Object param) {
		String sql = "select count(*) from (" + transHqlToSql(hql) + ")  countTable";
		SQLQuery sqlQuery = null;
		if(param == null || param instanceof List || param instanceof Object[]){
			sqlQuery = getSQLQuery(sql, param);
		}else{
			sqlQuery = getSQLQuery(sql);
			Map map = null;
			if(param instanceof Map){
				map = (Map)param;
			}else{
				map = ConvertObjToMap(param);
			}
			if(map != null){
				String key = "";
				int fromIndex = 0;
				int subBeginIndex = 0;
				int i = 0;
				while((subBeginIndex = hql.indexOf(":", fromIndex) + 1)!=0){
					if(hql.indexOf(" ", subBeginIndex)!=-1){
						key = hql.substring(subBeginIndex, hql.indexOf(" ", subBeginIndex));
					}else{
						key = hql.substring(subBeginIndex);
					}
					fromIndex = subBeginIndex + key.length();
					sqlQuery.setParameter(i++, map.get(key));
				}
			}
		}
		return (BigDecimal) sqlQuery.uniqueResult();
	}
	
	/**
	 * 执行HQL语句
	 * @param hql hql语句
	 * @param param 类型可为：List、Map、Object[]、Entity
	 * @return 返回受影响记录数
	 */
	public Integer executeHql(String hql, Object param) {
		return getQuery(hql, param).executeUpdate();
	}
	
	/**
	 * 执行HQL语句
	 * @param hql hql语句
	 * @return 返回受影响记录数
	 */
	public Integer executeHql(String hql) {
		return getQuery(hql).executeUpdate();
	}
	
	/**
	 * 将hql语句转换为sql语句
	 * @param hql 要转换的hql语句
	 * @return sql语句
	 */
	protected String transHqlToSql(String hql) {
		if (hql == null || hql.equals("")) {
			return null;
		}
		QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(hql, hql, Collections.EMPTY_MAP, (SessionFactoryImpl) sessionFactory); // 得到Query转换器实现类
		queryTranslator.compile(Collections.EMPTY_MAP, false);
		String sql = queryTranslator.getSQLString(); // 得到sql
		return sql;
	}
	
	/**
	 * 将实体类转换成map
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	protected Map ConvertObjToMap(Object obj){
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (obj == null)
			return null;
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				try {
					Field f = fields[i];
					f.setAccessible(true);
					reMap.put(f.getName(), f.get(obj));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
		}
		return reMap;
	}

	
	/**
	 * 判断参数是否有用
	 * @param key	参数名称
	 * @param val	参数值
	 * @return boolean	有用返回true,没用false
	 */
	protected static boolean isKeyUseful(Object key, Object val) {
		if(key == null){
			return false;
		}
		if(val instanceof Collection){
			return true;
		}
		return val!=null && !"".equals(val) && !(key.toString().equals("page"))&&(!key.toString().equals("rows")&&(!key.toString().equals("order"))&&(!key.toString().equals("sort")));
	}
	
	/**
	 * 去掉无用的参数
	 * @param map	参数集合
	 * @return	返回去除无用参数后的参数集合
	 */
	protected Map removeUnUserfulKey(Map map) {
		if(map == null){
			return null;
		}
		Map paraMap = new HashMap();
		Iterator it=map.keySet().iterator();
		while(it.hasNext()){
			Object key = it.next();
//			map.remove(key);
			Object val = map.get(key);
			if (isKeyUseful(key, val)) {
				paraMap.put(key, val);
			}
		}
		return paraMap;
	}
	
	/**
	 * 去掉无用的参数
	 * @param map	参数集合
	 * @return	返回去除无用参数后的参数集合
	 */
	protected boolean isEmpty(Object value) {
		return value==null || "".equals(value.toString().trim());
	}
}
