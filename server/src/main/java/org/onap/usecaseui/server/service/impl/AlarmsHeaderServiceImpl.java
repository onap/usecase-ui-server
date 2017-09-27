/*
 * Copyright (C) 2017 CMCC, Inc. and others. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onap.usecaseui.server.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;


@Service("AlarmsHeaderService")
@Transactional
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class AlarmsHeaderServiceImpl implements AlarmsHeaderService {
	
    private static final Logger logger = LoggerFactory.getLogger(AlarmsHeaderServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;
    
    
	public String saveAlarmsHeader(AlarmsHeader alarmsHeader) {
		 try{
	            if (null == alarmsHeader) {
	                logger.error("AlarmsHeaderServiceImpl saveAlarmsHeader alarmsHeader is null!");
	            }
	            logger.info("AlarmsHeaderServiceImpl saveAlarmsHeader: alarmsHeader={}", alarmsHeader);
	            Session session = sessionFactory.openSession();
	            Transaction tx = session.beginTransaction();     
	            session.save(alarmsHeader);
	            tx.commit();
	            session.flush();
	            session.close();
	            return "1";
	        } catch (Exception e) {
	            logger.error("Exception occurred while performing AlarmsHeaderServiceImpl saveAlarmsHeader. Details:" + e.getMessage());
	            return "0";
	        }
	        
	}
	

	@Override
	public String updateAlarmsHeader(AlarmsHeader alarmsHeader) {
		try{
            if (null == alarmsHeader){
                logger.error("AlarmsHeaderServiceImpl saveAlarmsHeader alarmsHeader is null!");
            }
            logger.info("AlarmsHeaderServiceImpl saveAlarmsHeader: alarmsHeader={}", alarmsHeader);
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();     
            session.update(alarmsHeader);
            tx.commit();
            session.flush();
            session.close();
            return "1";
        } catch (Exception e) {
            logger.error("Exception occurred while performing AlarmsHeaderServiceImpl updateAlarmsHeader. Details:" + e.getMessage());
            return "0";
        }
	}

	public int getAllCount() {
		try{
            Session session = sessionFactory.openSession();
            long q=(long)session.createQuery("select count(*) from AlarmsHeader").uniqueResult();
            session.flush();
            session.close();
            return (int)q;
        } catch (Exception e) {
            logger.error("Exception occurred while performing AlarmsHeaderServiceImpl getAllCount. Details:" + e.getMessage());
            return -1;
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<AlarmsHeader> queryAlarmsHeader(AlarmsHeader alarmsHeader,int currentPage,int pageSize) {
		Page<AlarmsHeader> page = new Page<AlarmsHeader>();
		int allRow =this.getAllCount();
		int offset = page.countOffset(currentPage, pageSize);
		
		try{
//			Date date = new Date();
//			SimpleDateFormat date = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
			StringBuffer hql =new StringBuffer("from AlarmsHeader a where 1=1");
            if (null == alarmsHeader) {
                logger.error("AlarmsHeaderServiceImpl queryAlarmsHeader alarmsHeader is null!");
            }else if(null!=alarmsHeader.getVersion()) {
            	String ver=alarmsHeader.getVersion();
            	hql.append(" and a.version like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getEventName()) {
            	String ver=alarmsHeader.getEventName();
            	hql.append(" and a.eventName like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getAlarmCondition()) {
            	String ver=alarmsHeader.getAlarmCondition();
            	hql.append(" and a.alarmCondition like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getDomain()) {
            	String ver=alarmsHeader.getDomain();
            	hql.append(" and a.domain like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getEventId()) {
            	String ver=alarmsHeader.getEventId();
            	hql.append(" and a.eventId like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getNfcNamingCode()) {
            	String ver=alarmsHeader.getNfcNamingCode();
            	hql.append(" and a.nfcNamingCode like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getNfNamingCode()) {
            	String ver=alarmsHeader.getNfNamingCode();
            	hql.append(" and a.nfNamingCode like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getSourceId()) {
            	String ver =alarmsHeader.getSourceId();
            	hql.append(" and a.sourceId like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getSourceName()) {
            	String ver =alarmsHeader.getSourceName();
            	hql.append(" and a.sourceName like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getReportingEntityId()) {
            	String ver =alarmsHeader.getReportingEntityId();
            	hql.append(" and a.reportingEntityId like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getReportingEntityName()) {
            	String ver =alarmsHeader.getReportingEntityName();
            	hql.append(" and a.reportingEntityName like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getPriority()) {
            	String ver =alarmsHeader.getPriority();
            	hql.append(" and a.priority like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getStartEpochMicrosec()) {
            	String ver =alarmsHeader.getStartEpochMicrosec();
            	hql.append(" and a.startEpochMicrosec like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getLastEpochMicroSec()) {
            	String ver =alarmsHeader.getLastEpochMicroSec();
            	hql.append(" and a.lastEpochMicroSec like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getSequence()) {
            	String ver =alarmsHeader.getSequence();
            	hql.append(" and a.sequence like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getFaultFieldsVersion()) {
            	String ver =alarmsHeader.getFaultFieldsVersion();
            	hql.append(" and a.faultFieldsVersion like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getEventServrity()) {
            	String ver =alarmsHeader.getEventServrity();
            	hql.append(" and a.eventServrity like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getEventType()) {
            	String ver =alarmsHeader.getEventType();
            	hql.append(" and a.eventSourceType like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getEventCategory()) {
            	String ver =alarmsHeader.getEventCategory();
            	hql.append(" and a.eventCategory like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getAlarmCondition()) {
            	String ver =alarmsHeader.getAlarmCondition();
            	hql.append(" and a.alarmCondition like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getSpecificProblem()) {
            	String ver =alarmsHeader.getSpecificProblem();
            	hql.append(" and a.specificProblem like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getVfStatus()) {
            	String ver =alarmsHeader.getVfStatus();
            	hql.append(" and a.vfStatus like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getAlarmInterfaceA()) {
            	String ver =alarmsHeader.getAlarmInterfaceA();
            	hql.append(" and a.alarmInterfaceA like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getStatus()) {
            	String ver =alarmsHeader.getStatus();
            	hql.append(" and a.status like '%"+ver+"%'");
            }else if(null!=alarmsHeader.getCreateTime()) {
            	Date ver =alarmsHeader.getCreateTime();
            	hql.append(" and a.createTime > '%"+ver+"%'");
            }else if(null!=alarmsHeader.getUpdateTime()) {
            	Date ver =alarmsHeader.getUpdateTime();
            	hql.append(" and a.updateTime like '%"+ver+"%'");
            }
            logger.info("AlarmsHeaderServiceImpl saveAlarmsHeader: alarmsHeader={}", alarmsHeader);
            Session session = sessionFactory.openSession();
            Query query = session.createQuery(hql.toString());
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);
            List<AlarmsHeader> list= query.list();
            page.setPageNo(currentPage);
            page.setPageSize(pageSize);
            page.setTotalRecords(allRow);
            page.setList(list);
            session.flush();
            session.close();
            return page;
        } catch (Exception e) {
            logger.error("Exception occurred while performing AlarmsHeaderServiceImpl queryAlarmsHeader. Details:" + e.getMessage());
            return null;
        }
	}

	
	@Override
	public List<AlarmsHeader> queryId(String[] id) {
		try {
			if(id.length==0) {
				logger.error("AlarmsHeaderServiceImpl queryId is null!");
			}
			
			AlarmsHeader alarm = new AlarmsHeader();
			List<AlarmsHeader> list = new ArrayList<AlarmsHeader>();
			Session session = sessionFactory.openSession();
			for(String b:id) {
				Query query = session.createQuery("from AlarmsHeader a where a.eventId =?0");
				alarm=(AlarmsHeader) query.setParameter("0", b).uniqueResult();
				list.add(alarm);
			}
			session.flush();
			session.close();
			return list;
		} catch (Exception e) {
			logger.error("Exception occurred while performing AlarmsHeaderServiceImpl queryId. Details:" + e.getMessage());
			return null;
		}
	}

	
    
    
    
}
