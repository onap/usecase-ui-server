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


import java.sql.Timestamp;
import java.util.*;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.service.AlarmsInformationService;
import org.onap.usecaseui.server.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;


@Service("AlarmsInformationService")
@Transactional
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class AlarmsInformationServiceImpl implements AlarmsInformationService {
	private static final Logger logger = LoggerFactory.getLogger(AlarmsInformationServiceImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.openSession();
	}

	@Override
	public String saveAlarmsInformation(AlarmsInformation alarmsInformation) {
		 try(Session session = getSession()){
				if (null == alarmsInformation) {
					logger.error("alarmsInformation saveAlarmsInformation alarmsInformation is null!");
				}
				logger.info("AlarmsInformationServiceImpl saveAlarmsInformation: alarmsInformation={}", alarmsInformation);
				Transaction tx = session.beginTransaction();
				session.save(alarmsInformation);
				tx.commit();
				session.flush();
				return "1";
			} catch (Exception e) {
				logger.error("exception occurred while performing AlarmsInformationServiceImpl saveAlarmsInformation. Details:" + e.getMessage());
				return "0";
			}
			
	}

	@Override
	public String updateAlarmsInformation(AlarmsInformation alarmsInformation) {
		try(Session session = getSession()){
			if (null == alarmsInformation) {
				logger.error("alarmsInformation updateAlarmsInformation alarmsInformation is null!");
			}
			logger.info("AlarmsInformationServiceImpl updateAlarmsInformation: alarmsInformation={}", alarmsInformation);
			Transaction tx = session.beginTransaction();
			session.update(alarmsInformation);
			tx.commit();
			session.flush();
			return "1";
		} catch (Exception e) {
			logger.error("exception occurred while performing AlarmsInformationServiceImpl updateAlarmsInformation. Details:" + e.getMessage());
			return "0";
		}
	}
	

	public int getAllCount(AlarmsInformation alarmsInformation, int currentPage, int pageSize) {
		try(Session session = getSession()){
			StringBuffer hql = new StringBuffer("select count(*) from AlarmsInformation a where 1=1");
			if (null == alarmsInformation) {
				logger.error("AlarmsInformationServiceImpl getAllCount alarmsInformation is null!");
			}else {
				if(null!=alarmsInformation.getName()) {
					String ver=alarmsInformation.getName();
					hql.append(" and a.name like '%"+ver+"%'");
				}
				if(null!=alarmsInformation.getValue()) {
					String ver=alarmsInformation.getValue();
					hql.append(" and a.value like '%"+ver+"%'");
				}
				if(null!=alarmsInformation.getEventId()) {
					String ver=alarmsInformation.getEventId();
					hql.append(" and a.eventId = '"+ver+"'");
				}
				if(null!=alarmsInformation.getCreateTime()) {
					Date ver =alarmsInformation.getCreateTime();
					hql.append(" and a.createTime > '%"+ver+"%'");
				}
				if(null!=alarmsInformation.getUpdateTime()) {
					Date ver =alarmsInformation.getUpdateTime();
					hql.append(" and a.updateTime like '%"+ver+"%'");
				}
			} 
			long q=(long)session.createQuery(hql.toString()).uniqueResult();
			session.flush();
			return (int)q;
		} catch (Exception e) {
			logger.error("exception occurred while performing AlarmsInformationServiceImpl getAllCount. Details:" + e.getMessage());
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<AlarmsInformation> queryAlarmsInformation(AlarmsInformation alarmsInformation, int currentPage,
			int pageSize) {
		Page<AlarmsInformation> page = new Page<AlarmsInformation>();
		int allRow =this.getAllCount(alarmsInformation,currentPage,pageSize);
		int offset = page.countOffset(currentPage, pageSize);
		
		try(Session session = getSession()){
			StringBuffer hql =new StringBuffer("from AlarmsInformation a where 1=1");
			if (null == alarmsInformation) {
				//logger.error("AlarmsInformationServiceImpl queryAlarmsInformation alarmsInformation is null!");
			}else {
				if(null!=alarmsInformation.getName()) {
					String ver=alarmsInformation.getName();
					hql.append(" and a.name like '%"+ver+"%'");
				}
				if(null!=alarmsInformation.getValue()) {
					String ver=alarmsInformation.getValue();
					hql.append(" and a.value like '%"+ver+"%'");
				}
				if(null!=alarmsInformation.getEventId()) {
					String ver=alarmsInformation.getEventId();
					hql.append(" and a.eventId = '"+ver+"'");
				}
				if(null!=alarmsInformation.getCreateTime()) {
					Date ver =alarmsInformation.getCreateTime();
					hql.append(" and a.createTime > '%"+ver+"%'");
				}
				if(null!=alarmsInformation.getUpdateTime()) {
					Date ver =alarmsInformation.getUpdateTime();
					hql.append(" and a.updateTime like '%"+ver+"%'");
				}
			}
			logger.info("AlarmsInformationServiceImpl queryAlarmsInformation: alarmsInformation={}", alarmsInformation);
			Query query = session.createQuery(hql.toString());
			query.setFirstResult(offset);
			query.setMaxResults(pageSize);
			List<AlarmsInformation> list= query.list();
			page.setPageNo(currentPage);
			page.setPageSize(pageSize);
			page.setTotalRecords(allRow);
			page.setList(list);
			session.flush();
			return page;
		} catch (Exception e) {
			logger.error("exception occurred while performing AlarmsInformationServiceImpl queryAlarmsInformation. Details:" + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AlarmsInformation> queryId(String[] id) {
		try {
			if(id.length==0) {
				logger.error("AlarmsInformationServiceImpl queryId is null!");
			}
			List<AlarmsInformation> list = new ArrayList<AlarmsInformation>();
			Session session = getSession();
			Query query = session.createQuery("from AlarmsInformation a where a.eventId IN (:alist)");
			list = query.setParameterList("alist", id).list();
			session.close();
			return list;
		} catch (Exception e) {
			logger.error("exception occurred while performing AlarmsInformationServiceImpl queryId. Details:" + e.getMessage());
			return null;
		}
	}


	@Override
	public List<Map<String,String>> queryDateBetween(String sourceId, String startTime, String endTime) {
		try(Session session = getSession()) {
			List<Map<String,String>> mapList = new ArrayList<>();
			String hql = "select a.createTime,count(*) from AlarmsInformation a where 1=1 ";
			if (sourceId != null && !"".equals(sourceId)){
				hql += " and a.eventId = :sourceId";
			}
			if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)){
				hql += " and a.createTime between :startTime and :endTime ";
			}
			hql += " group by a.createTime";
			Query query = session.createQuery(hql);
			if (sourceId != null && !"".equals(sourceId)){
				query.setString("sourceId",sourceId);
			}
			if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)){
				query.setString("startTime", startTime).setString("endTime", endTime);
			}
			Iterator it= query.list().iterator();
			while(it.hasNext()){
				Object[] res=(Object[]) it.next();
				Map<String,String> map = new HashMap<>();
				map.put("Time",res[0].toString());
				map.put("Count",res[1].toString());
				mapList.add(map);
			}
			logger.info("AlarmsInformationServiceImpl queryDateBetween: list={}", mapList);
			return mapList;
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceInformationServiceImpl queryDateBetween. Details:" + e.getMessage());
			return null;
		}
	}
}
