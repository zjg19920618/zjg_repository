package com.boomhope.tms.dao.impl;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IMsgFlowDao;
import com.boomhope.tms.entity.MsgFlow;

@Repository(value = "msgFlowDao")
public class MsgFlowDaoImpl extends BaseDao<MsgFlow> implements IMsgFlowDao{

}
