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


import java.text.SimpleDateFormat;
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
import org.onap.usecaseui.server.util.DateUtils;
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

	private Session getSession() {
		return sessionFactory.openSession();
	}

	public String saveAlarmsHeader(AlarmsHeader alarmsHeader) {
		 try(Session session = getSession()){
			if (null == alarmsHeader) {
				logger.error("AlarmsHeaderServiceImpl saveAlarmsHeader alarmsHeader is null!");
			}
			logger.info("AlarmsHeaderServiceImpl saveAlarmsHeader: alarmsHeader={}", alarmsHeader);
			Transaction tx = session.beginTransaction();     
			session.save(alarmsHeader);
			tx.commit();
			session.flush();
			return "1";
		} catch (Exception e) {
			logger.error("exception occurred while performing AlarmsHeaderServiceImpl saveAlarmsHeader. Details:" + e.getMessage());
			return "0";
		}
	}

	@Override
	public String updateAlarmsHeader(AlarmsHeader alarmsHeader) {
		try(Session session = getSession()){
			if (null == alarmsHeader){
				logger.error("AlarmsHeaderServiceImpl updateAlarmsHeader alarmsHeader is null!");
			}
			logger.info("AlarmsHeaderServiceImpl updateAlarmsHeader: alarmsHeader={}", alarmsHeader);
			Transaction tx = session.beginTransaction();     
			session.update(alarmsHeader);
			tx.commit();
			session.flush();
			return "1";
		} catch (Exception e) {
			logger.error("exception occurred while performing AlarmsHeaderServiceImpl updateAlarmsHeader. Details:" + e.getMessage());
			return "0";
		}
	}

	public int getAllCount(AlarmsHeader alarmsHeader,int currentPage,int pageSize) {
		try(Session session = getSession()){
			StringBuffer count=new StringBuffer("select count(*) from AlarmsHeader a where 1=1");
			if (null == alarmsHeader) {
				logger.error("AlarmsHeaderServiceImpl getAllCount alarmsHeader is null!");
			}else {
				if(null!=alarmsHeader.getVersion()) {
					String ver=alarmsHeader.getVersion();
					count.append(" and a.version like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getEventName()) {
					String ver=alarmsHeader.getEventName();
					count.append(" and a.eventName like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getAlarmCondition()) {
					String ver=alarmsHeader.getAlarmCondition();
					count.append(" and a.alarmCondition like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getDomain()) {
					String ver=alarmsHeader.getDomain();
					count.append(" and a.domain like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getEventId()) {
					String ver=alarmsHeader.getEventId();
					count.append(" and a.eventId = '"+ver+"'");
				}
				if(null!=alarmsHeader.getNfcNamingCode()) {
					String ver=alarmsHeader.getNfcNamingCode();
					count.append(" and a.nfcNamingCode like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getNfNamingCode()) {
					String ver=alarmsHeader.getNfNamingCode();
					count.append(" and a.nfNamingCode like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getSourceId()) {
					String ver =alarmsHeader.getSourceId();
					count.append(" and a.sourceId like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getSourceName()) {
					String ver =alarmsHeader.getSourceName();
					count.append(" and a.sourceName like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getReportingEntityId()) {
					String ver =alarmsHeader.getReportingEntityId();
					count.append(" and a.reportingEntityId like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getReportingEntityName()) {
					String ver =alarmsHeader.getReportingEntityName();
					count.append(" and a.reportingEntityName like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getPriority()) {
					String ver =alarmsHeader.getPriority();
					count.append(" and a.priority like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getStartEpochMicrosec()) {
					String ver =alarmsHeader.getStartEpochMicrosec();
					count.append(" and a.startEpochMicrosec like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getLastEpochMicroSec()) {
					String ver =alarmsHeader.getLastEpochMicroSec();
					count.append(" and a.lastEpochMicroSec like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getSequence()) {
					String ver =alarmsHeader.getSequence();
					count.append(" and a.sequence like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getFaultFieldsVersion()) {
					String ver =alarmsHeader.getFaultFieldsVersion();
					count.append(" and a.faultFieldsVersion like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getEventServrity()) {
					String ver =alarmsHeader.getEventServrity();
					count.append(" and a.eventServrity like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getEventType()) {
					String ver =alarmsHeader.getEventType();
					count.append(" and a.eventSourceType like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getEventCategory()) {
					String ver =alarmsHeader.getEventCategory();
					count.append(" and a.eventCategory like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getAlarmCondition()) {
					String ver =alarmsHeader.getAlarmCondition();
					count.append(" and a.alarmCondition like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getSpecificProblem()) {
					String ver =alarmsHeader.getSpecificProblem();
					count.append(" and a.specificProblem like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getVfStatus()) {
					String ver =alarmsHeader.getVfStatus();
					count.append(" and a.vfStatus = '"+ver+"'");
				}
				if(null!=alarmsHeader.getAlarmInterfaceA()) {
					String ver =alarmsHeader.getAlarmInterfaceA();
					count.append(" and a.alarmInterfaceA like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getStatus()) {
					String ver =alarmsHeader.getStatus();
					count.append(" and a.status = '"+ver+"'");
				}
				if(null!=alarmsHeader.getCreateTime() || alarmsHeader.getUpdateTime()!= null) {
					count.append(" and a.createTime between :startTime and :endTime");
				}
			}
			Query query = session.createQuery(count.toString());
			if(null!=alarmsHeader.getCreateTime() || alarmsHeader.getUpdateTime()!= null) {
				query.setDate("startTime",alarmsHeader.getCreateTime());
				query.setDate("endTime",alarmsHeader.getUpdateTime());
			}
			long q=(long)query.uniqueResult();
			session.flush();
			return (int)q;
		} catch (Exception e) {
			logger.error("exception occurred while performing AlarmsHeaderServiceImpl getAllCount. Details:" + e.getMessage());
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<AlarmsHeader> queryAlarmsHeader(AlarmsHeader alarmsHeader,int currentPage,int pageSize) {
		Page<AlarmsHeader> page = new Page<AlarmsHeader>();
		int allRow =this.getAllCount(alarmsHeader,currentPage,pageSize);
		int offset = page.countOffset(currentPage, pageSize);

		try(Session session = getSession()){
			StringBuffer hql =new StringBuffer("from AlarmsHeader a where 1=1");
			if (null == alarmsHeader) {
				//logger.error("AlarmsHeaderServiceImpl queryAlarmsHeader alarmsHeader is null!");
			}else {
				if(null!=alarmsHeader.getVersion()) {
					String ver=alarmsHeader.getVersion();
					hql.append(" and a.version like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getEventName()) {
					String ver=alarmsHeader.getEventName();
					hql.append(" and a.eventName = '"+ver+"'");
				}
				if(null!=alarmsHeader.getAlarmCondition()) {
					String ver=alarmsHeader.getAlarmCondition();
					hql.append(" and a.alarmCondition like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getDomain()) {
					String ver=alarmsHeader.getDomain();
					hql.append(" and a.domain like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getEventId()) {
					String ver=alarmsHeader.getEventId();
					hql.append(" and a.eventId = '"+ver+"'");
				}
				if(null!=alarmsHeader.getNfcNamingCode()) {
					String ver=alarmsHeader.getNfcNamingCode();
					hql.append(" and a.nfcNamingCode like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getNfNamingCode()) {
					String ver=alarmsHeader.getNfNamingCode();
					hql.append(" and a.nfNamingCode like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getSourceId()) {
					String ver =alarmsHeader.getSourceId();
					hql.append(" and a.sourceId like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getSourceName()) {
					String ver =alarmsHeader.getSourceName();
					hql.append(" and a.sourceName like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getReportingEntityId()) {
					String ver =alarmsHeader.getReportingEntityId();
					hql.append(" and a.reportingEntityId like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getReportingEntityName()) {
					String ver =alarmsHeader.getReportingEntityName();
					hql.append(" and a.reportingEntityName like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getPriority()) {
					String ver =alarmsHeader.getPriority();
					hql.append(" and a.priority like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getStartEpochMicrosec()) {
					String ver =alarmsHeader.getStartEpochMicrosec();
					hql.append(" and a.startEpochMicrosec like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getLastEpochMicroSec()) {
					String ver =alarmsHeader.getLastEpochMicroSec();
					hql.append(" and a.lastEpochMicroSec like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getSequence()) {
					String ver =alarmsHeader.getSequence();
					hql.append(" and a.sequence like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getFaultFieldsVersion()) {
					String ver =alarmsHeader.getFaultFieldsVersion();
					hql.append(" and a.faultFieldsVersion like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getEventServrity()) {
					String ver =alarmsHeader.getEventServrity();
					hql.append(" and a.eventServrity like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getEventType()) {
					String ver =alarmsHeader.getEventType();
					hql.append(" and a.eventSourceType like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getEventCategory()) {
					String ver =alarmsHeader.getEventCategory();
					hql.append(" and a.eventCategory like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getAlarmCondition()) {
					String ver =alarmsHeader.getAlarmCondition();
					hql.append(" and a.alarmCondition like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getSpecificProblem()) {
					String ver =alarmsHeader.getSpecificProblem();
					hql.append(" and a.specificProblem like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getVfStatus()) {
					String ver =alarmsHeader.getVfStatus();
					hql.append(" and a.vfStatus = '"+ver+"'");
				}
				if(null!=alarmsHeader.getAlarmInterfaceA()) {
					String ver =alarmsHeader.getAlarmInterfaceA();
					hql.append(" and a.alarmInterfaceA like '%"+ver+"%'");
				}
				if(null!=alarmsHeader.getStatus()) {
					String ver =alarmsHeader.getStatus();
					hql.append(" and a.status = '"+ver+"'");
				}
				if(null!=alarmsHeader.getCreateTime() || alarmsHeader.getUpdateTime()!= null) {
					hql.append(" and a.createTime between :startTime and :endTime");
				}
			}
			logger.info("AlarmsHeaderServiceImpl queryAlarmsHeader: alarmsHeader={}", alarmsHeader);
			Query query = session.createQuery(hql.toString());
			if(null!=alarmsHeader.getCreateTime() || alarmsHeader.getUpdateTime()!= null) {
				query.setDate("startTime",alarmsHeader.getCreateTime());
				query.setDate("endTime",alarmsHeader.getUpdateTime());
			}
			query.setFirstResult(offset);
			query.setMaxResults(pageSize);
			List<AlarmsHeader> list= query.list();
			page.setPageNo(currentPage);
			page.setPageSize(pageSize);
			page.setTotalRecords(allRow);
			page.setList(list);
			session.flush();
			return page;
		} catch (Exception e) {
			logger.error("exception occurred while performing AlarmsHeaderServiceImpl queryAlarmsHeader. Details:" + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AlarmsHeader> queryId(String[] id) {
		try(Session session = getSession()){
			if(id.length==0) {
				logger.error("AlarmsHeaderServiceImpl queryId is null!");
			}
			List<AlarmsHeader> list = new ArrayList<AlarmsHeader>();
			Query query = session.createQuery("from AlarmsHeader a where a.eventName IN (:alist)");
			list = query.setParameterList("alist", id).list();
			return list;
		} catch (Exception e) {
			logger.error("exception occurred while performing AlarmsHeaderServiceImpl queryId. Details:" + e.getMessage());
			return null;
		}
	}
}
