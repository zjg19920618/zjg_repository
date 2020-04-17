package com.boomhope.tms.dao.impl;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.ITransFlowDao;
import com.boomhope.tms.entity.TransFlow;

/***
 * 交易流水Dao实现类
 * @author shaopeng
 *
 */
@Repository(value = "transFlowDao")
public class TransFlowDaoImpl  extends BaseDao<TransFlow> implements ITransFlowDao{

}
