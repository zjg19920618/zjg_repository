package com.boomhope.tms.dao;



import java.io.Serializable;
import java.util.List;

import com.boomhope.tms.entity.Page;



@SuppressWarnings("all")
public interface IBaseDao<T extends Serializable> {
	/**
	 * 保存实体
	 * @param entity 实体
	 * @return 返回保存的实体
	 */
	public Serializable save(Object entity);

	/**
	 * 删除实体
	 * @param entity 实体
	 */
	public void delete(Object entity);
	
	/**
	 * 更新实体
	 * @param entity 实体
	 */
	public void update(Object entity);

	/**
	 * 保存或更新实体
	 * @param entity 实体
	 */
	public void saveOrUpdate(Object entity);
	
	/**
     * 判断主键对应的实体是否存在
     * @param id 主键
     * @return 存在 返回true，否则false
     */
	public boolean exists(Serializable id);
	
	/**
     * 判断主键对应的实体是否存在
     * @param clazz 要判断的实体类
     * @param id 主键
     * @return 存在 返回true，否则false
     */
	public <M> boolean exists(Class<M> clazz, Serializable id);
	
	/**
	 * 根据主键id查询获得实体
	 * @param id 主键
	 * @return 返回id对应的实体
	 */
	public T findOne(Serializable id);
	
	/**
	 * 根据主键id查询获得实体
	 * @param clazz 要查询的实体类
	 * @param id 主键
	 * @return 返回id对应的实体
	 */
	public <M> M findOne(Class<M> clazz, Serializable id);
	
	/**
	 * 查询实体所有记录
	 * @return 返回实体集合
	 */
	public List<T> findAll();
	
	/**
	 * 查询实体所有记录，可分页
	 * @param page 分页信息
	 * @return 返回实体集合
	 */
	public List<T> findAll(Page page);
	
	/**
	 * 查询实体所有记录，可排序
	 * @param sort 排序字符串，格式如：id asc, name desc
	 * @return 返回实体集合
	 */
	public List<T> findAll(String sort);

	/**
	 * 查询实体所有记录，可分页、排序
	 * @param page 分页信息
	 * @param sort 排序字符串，格式如：id asc, name desc
	 * @return 返回实体集合
	 */
	public List<T> findAll(Page page, String sort);
	
	/**
	 * 查询实体所有记录
	 * @param clazz 要查询的实体类
	 * @return 返回实体集合
	 */
	public <M> List<M> findAll(Class<M> clazz);
	
	/**
	 * 查询实体所有记录，可分页
	 * @param clazz 要查询的实体类
	 * @param page 分页信息
	 * @return 返回实体集合
	 */
	public <M> List<M> findAll(Class<M> clazz, Page page);
	
	/**
	 * 查询实体所有记录，可排序
	 * @param clazz 要查询的实体类
	 * @param sort 排序字符串，格式如：id asc, name desc
	 * @return 返回实体集合
	 */
	public <M> List<M> findAll(Class<M> clazz, String sort);
	
	/**
	 * 查询实体所有记录，可分页、排序
	 * @param clazz 要查询的实体类
	 * @param page 分页信息
	 * @param sort 排序字符串，格式如：id asc, name desc
	 * @return 返回实体集合
	 */
	public <M> List<M> findAll(Class<M> clazz, Page page, String sort);
	
	/**
	 * 根据属性查找记录
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return
	 */
	public List<T> findByProperty(String propertyName, Object propertyValue);

	/**
	 * 根据属性通过传入的运算符查询记录
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param operator 运算符
	 * @return List
	 */
	public List<T> findByPropertyWithOperator(String propertyName, Object propertyValue, String operator);
	
	/**
	 * 根据属性查找记录
	 * @param clazz 要查询的实体类
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return
	 */
	public <M> List<M> findByProperty(Class<M> clazz, String propertyName, Object propertyValue);

	/**
	 * 根据属性通过传入的运算符查询记录
	 * @param clazz 要查询的实体类
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param operator 运算符
	 * @return List
	 */
	public <M> List<M> findByPropertyWithOperator(Class<M> clazz, String propertyName, Object propertyValue, String operator);
	
	/**
	 * 统计实体总数
	 * @return 返回总记录数
	 */
	public Long count();
	/**
	 * 统计实体总数
	 * @param clazz 要统计的实体类
	 * @return 返回总记录数
	 */
	public <M> Long count(Class<M> clazz);
	
	/**
	 * 执行HQL语句
	 * @param hql hql语句
	 * @return 返回受影响记录数
	 */
	public Integer executeHql(String hql);
}
