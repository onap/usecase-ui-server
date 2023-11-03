/**
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
import java.util.List;

import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.service.PerformanceHeaderService;
import org.onap.usecaseui.server.util.Page;
import org.onap.usecaseui.server.util.UuiCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;


@Service("PerformanceHeaderService")
@Transactional
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class PerformanceHeaderServiceImpl implements PerformanceHeaderService {

	private static final Logger logger = LoggerFactory.getLogger(PerformanceHeaderServiceImpl.class);

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public Session getSession() {
		return entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();}

	@Override
	public String savePerformanceHeader(PerformanceHeader performanceHeder) {
		Session session = getSession();
		try{
			if (null == performanceHeder){
				logger.error("PerformanceHeaderServiceImpl savePerformanceHeader performanceHeder is null!");
				return "0";
			}
			session.save(performanceHeder);
			session.flush();
			return "1";
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceHeaderServiceImpl savePerformanceHeader. Details:" + e.getMessage());
			return "0";
		}
	}

	@Override
	public String updatePerformanceHeader(PerformanceHeader performanceHeder) {
		Session session = getSession();
		try{
			if (null == performanceHeder){
				logger.error("PerformanceHeaderServiceImpl updatePerformanceHeader performanceHeder is null!");
				return "0";
			}
			session.update(performanceHeder);
			session.flush();
			return "1";
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceHeaderServiceImpl updatePerformanceHeader. Details:" + e.getMessage());
			return "0";
		}
	}

	public int getAllCount(PerformanceHeader performanceHeder, int currentPage, int pageSize) {
		Session session = getSession();
		try{
			StringBuffer hql = new StringBuffer("select count(*) from PerformanceHeader a where 1=1");
			if (null == performanceHeder) {
				return 0;
			}else {
				if(UuiCommonUtil.isNotNullOrEmpty(performanceHeder.getSourceName())) {
					String ver =performanceHeder.getSourceName();
					hql.append(" and a.sourceName like '%"+ver+"%'");
				}
				if(UuiCommonUtil.isNotNullOrEmpty(performanceHeder.getStartEpochMicrosec())&& UuiCommonUtil.isNotNullOrEmpty(performanceHeder.getLastEpochMicroSec())) {
					hql.append(" and (CASE WHEN a.startEpochMicrosec=0 THEN a.lastEpochMicroSec ELSE a.startEpochMicrosec END) between :startTime and :endTime ");
				}
			}
			Query query = session.createQuery(hql.toString());
			if(null!=performanceHeder.getStartEpochMicrosec() && null!=performanceHeder.getLastEpochMicroSec()) {
				query.setParameter("startTime",performanceHeder.getStartEpochMicrosec()).setParameter("endTime",performanceHeder.getLastEpochMicroSec());
			}
			long q=(long)query.uniqueResult();
			session.flush();
			return (int)q;
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceHeaderServiceImpl getAllCount. Details:" + e.getMessage());
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<PerformanceHeader> queryPerformanceHeader(PerformanceHeader performanceHeder, int currentPage, int pageSize) {
		Page<PerformanceHeader> page = new Page<PerformanceHeader>();
		int allRow =this.getAllCount(performanceHeder,currentPage,pageSize);
		int offset = page.countOffset(currentPage, pageSize);
		Session session = getSession();
		try{
			StringBuffer hql =new StringBuffer("from PerformanceHeader a where 1=1");
				if(UuiCommonUtil.isNotNullOrEmpty(performanceHeder.getSourceName())) {
					String ver =performanceHeder.getSourceName();
					hql.append(" and a.sourceName like '%"+ver+"%'");
				}
				if(UuiCommonUtil.isNotNullOrEmpty(performanceHeder.getStartEpochMicrosec())&& UuiCommonUtil.isNotNullOrEmpty(performanceHeder.getLastEpochMicroSec())) {
					hql.append(" and (CASE WHEN a.startEpochMicrosec=0 THEN a.lastEpochMicroSec ELSE a.startEpochMicrosec END) between :startTime and :endTime ");
				}
			Query query = session.createQuery(hql.toString());
			if(UuiCommonUtil.isNotNullOrEmpty(performanceHeder.getStartEpochMicrosec())&& UuiCommonUtil.isNotNullOrEmpty(performanceHeder.getLastEpochMicroSec())) {
				query.setParameter("startTime",performanceHeder.getStartEpochMicrosec()).setParameter("endTime",performanceHeder.getLastEpochMicroSec());
			}
			query.setFirstResult(offset);
			query.setMaxResults(pageSize);
			List<PerformanceHeader> list= query.list();
			page.setPageNo(currentPage);
			page.setPageSize(pageSize);
			page.setTotalRecords(allRow);
			page.setList(list);
			session.flush();
			return page;
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceHeaderServiceImpl queryPerformanceHeader. Details:" + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PerformanceHeader> queryId(String[] id) {
		Session session = getSession();
		try {
			List<PerformanceHeader> list = new ArrayList<PerformanceHeader>();
			if(id.length==0) {
				return list;
			}
			Query query = session.createQuery("from PerformanceHeader a where a.eventName IN (:alist)");
			list = query.setParameterList("alist", id).list();
			return list;
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceHeaderServiceImpl queryId. Details:" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> queryAllSourceNames() {
		Session session = getSession();
		try {
			Query query = session.createQuery("select distinct a.sourceName from PerformanceHeader a");
			return query.list();
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceHeaderServiceImpl queryAllSourceId. Details:" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public PerformanceHeader getPerformanceHeaderById(String id) {
		Session session = getSession();
		try {

			String string = "from PerformanceHeader a where 1=1 and a.id=:id";
			Query q = session.createQuery(string);
			q.setParameter("id",id);
			PerformanceHeader performanceHeader =(PerformanceHeader)q.uniqueResult();
			session.flush();
			return performanceHeader;

		}catch (Exception e){
			logger.error("exception occurred while performing PerformanceHeaderServiceImpl getPerformanceHeaderById."+e.getMessage());
			return null;
		}
	}
}
