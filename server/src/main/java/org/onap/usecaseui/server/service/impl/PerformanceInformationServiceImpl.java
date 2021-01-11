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


import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import com.google.common.base.Throwables;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.service.PerformanceInformationService;
import org.onap.usecaseui.server.util.UuiCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;


@Service("PerformanceInformationService")
@Transactional
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class PerformanceInformationServiceImpl implements PerformanceInformationService {

	private static final Logger logger = LoggerFactory.getLogger(PerformanceInformationServiceImpl.class);

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public Session getSession() {
		return entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();}

	@Override
	public String savePerformanceInformation(PerformanceInformation performanceInformation) {
		Session session = getSession();
		try {
			if (null == performanceInformation) {
			}
			session.save(performanceInformation);
			session.flush();
			return "1";
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceInformationServiceImpl savePerformanceInformation. Details:" + e.getMessage());
			return "0";
		}
	}

	@Override
	public String updatePerformanceInformation(PerformanceInformation performanceInformation) {
		Session session = getSession();
		try {
			if (null == performanceInformation) {
			}
			logger.info("PerformanceInformationServiceImpl updatePerformanceInformation: performanceInformation={}", performanceInformation);
			session.update(performanceInformation);
			session.flush();
			return "1";
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceInformationServiceImpl updatePerformanceInformation. Details:" + e.getMessage());
			return "0";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PerformanceInformation> queryId(String[] id) {
		Session session = getSession();
		try {
			if(id.length==0) {
			}
			List<PerformanceInformation> list = new ArrayList<>();
			Query query = session.createQuery("from PerformanceInformation a where a.sourceId IN (:alist)");
			list = query.setParameterList("alist", id).list();
			return list;
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceInformationServiceImpl queryId. Details:" + Throwables.getStackTraceAsString(e));
			logger.error("exception occurred while performing PerformanceInformationServiceImpl queryId. Details:" + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PerformanceInformation> queryDateBetween(String sourceId, Date startDate, Date endDate) {
		Session session = getSession();
		try {
			List<PerformanceInformation> list = new ArrayList<>();
			Query query = session.createQuery("from PerformanceInformation a where a.sourceId = :sourceId and a.createTime BETWEEN :startDate and :endDate");
			list = query.setParameter("sourceId",sourceId).setParameter("startDate", startDate).setParameter("endDate",endDate).list();
			return list;
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceInformationServiceImpl queryDateBetween. Details:" + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int queryDataBetweenSum(String sourceId, String name, Date startDate, Date endDate){
		Session session = getSession();
		try {
			int sum = 0;
			Query query = session.createQuery("select sum(a.value) from PerformanceInformation a where a.sourceId = :sourceId and a.name = :name and a.createTime BETWEEN :startDate and :endDate");
			sum = Integer.parseInt(query.setParameter("sourceId",sourceId).setParameter("name",name).setParameter("startDate", startDate).setParameter("endDate",endDate).uniqueResult().toString());
			return sum;
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceInformationServiceImpl queryDataBetweenSum. Details:" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<PerformanceInformation> queryDateBetween(String resourceId, String name, String startTime, String endTime) {
		Session session = getSession();
		try {
			String hql = "from PerformanceInformation a where 1=1 ";
			if (resourceId != null && !"".equals(resourceId)){
				hql += " and a.sourceId = :resourceId";
			}
			if (name != null && !"".equals(name)){
				hql += " and a.name = :name ";
			}
			if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)){
				hql += " and a.createTime between :startTime and :endTime ";
			}
			Query query = session.createQuery(hql);
			if (resourceId != null && !"".equals(resourceId)){
				query.setString("resourceId",resourceId);
			}
			if (name != null && !"".equals(name)){
				query.setString("name",name);
			}
			if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)){
				query.setString("startTime", startTime).setString("endTime", endTime);
			}
			logger.info("PerformanceInformationServiceImpl queryDateBetween: list={}", query.list());
			return query.list();
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceInformationServiceImpl queryDateBetween. Details:" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<PerformanceInformation> getAllPerformanceInformationByHeaderId(String headerId) {
		Session session = getSession();
		try {
			String string = "from PerformanceInformation a where 1=1 and a.headerId=:headerId";
			Query query = session.createQuery(string);
			query.setString("headerId",headerId);
			List<PerformanceInformation> list = query.list();
			session.flush();
			return list;
		}catch (Exception e){
			logger.error("exception occurred while performing PerformanceInformationServiceImpl queryDateBetween. LIST:" + e.getMessage());

			return null;
		}
	}
	
    @Override
    public String queryMaxValueByBetweenDate(String sourceId, String name, String startTime, String endTime) {
			Session session = getSession();
		     try {
            String hql = "select max(a.value) from PerformanceInformation a where 1=1 ";
            if (sourceId != null && !"".equals(sourceId)){
                hql += " and a.sourceId = :resourceId";
            }
            if (name != null && !"".equals(name)){
                hql += " and a.name = :name ";
            }
            if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)){
            	hql += " and (CASE WHEN a.startEpochMicrosec=0 THEN a.lastEpochMicroSec ELSE a.startEpochMicrosec END) between :startTime and :endTime ";
            }
            Query query = session.createQuery(hql);
            if (sourceId != null && !"".equals(sourceId)){
                query.setString("resourceId",sourceId);
            }
            if (name != null && !"".equals(name)){
                query.setString("name",name);
            }
            if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)){
                query.setString("startTime", startTime).setString("endTime", endTime);
            }
            String num=(String) query.uniqueResult();
            return UuiCommonUtil.isNotNullOrEmpty(num)?num:0+"";
        } catch (Exception e) {
			logger.error("exception occurred while performing PerformanceInformationServiceImpl queryMaxValueByBetweenDate. Details:" + Throwables.getStackTraceAsString(e));
            logger.error("exception occurred while performing PerformanceInformationServiceImpl queryMaxValueByBetweenDate. Details:" + e.getMessage());
            return 0+"";
        }
    }
}
