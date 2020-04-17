package com.boomhope.tms.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.boomhope.tms.dao.ITCnapsBkinfoDao;
import com.boomhope.tms.entity.TCnapsBkinfo;
import com.boomhope.tms.model.Email;
import com.boomhope.tms.service.IMailService;
import com.boomhope.tms.service.ITCnapsBkinfoService;


@Service("TCnapsBkinfoServiceImpl")
public class TCnapsBkinfoServiceImpl implements ITCnapsBkinfoService {
	    private Log log = LogFactory.getLog(getClass());
	    private ITCnapsBkinfoDao tCnapsBkinfoDao;
	    
	    @Resource(name="TCnapsBkinfoDaoImpl")
		public void settCnapsBkinfoDao(ITCnapsBkinfoDao tCnapsBkinfoDao) {
			this.tCnapsBkinfoDao = tCnapsBkinfoDao;
		}

		@Override
		public void updateBankInfoList(List<TCnapsBkinfo> list)
				throws Exception {
			List<String> delList=new ArrayList<>();
			for (TCnapsBkinfo tCnapsBkinfo : list) {
				if("CC00".equals(tCnapsBkinfo.getAlttype())){
					tCnapsBkinfoDao.addBankInfo(tCnapsBkinfo);
				}else if("CC01".equals(tCnapsBkinfo.getAlttype())){
					tCnapsBkinfoDao.updateBankInfo(tCnapsBkinfo);
				}else if("CC02".equals(tCnapsBkinfo.getAlttype())){
					delList.add(tCnapsBkinfo.getBankNo());
				}
			}
			tCnapsBkinfoDao.deleteBankInfo(delList);
		}

		@Override
		public List<TCnapsBkinfo> getBankInfoList(Map params) throws Exception {
			return tCnapsBkinfoDao.findBankInfoList(params);
		}  
	  
	    
}
