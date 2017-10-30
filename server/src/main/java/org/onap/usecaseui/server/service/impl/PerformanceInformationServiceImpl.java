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

import javax.persistence.Id;
import javax.transaction.Transactional;

import org.hibernate.*;
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.service.PerformanceInformationService;
import org.onap.usecaseui.server.util.Page;
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
    private SessionFactory sessionFactory;


	@Override
	public String savePerformanceInformation(PerformanceInformation performanceInformation) {
		 try {
	            if (null == performanceInformation) {
	                logger.error("performanceInformation savePerformanceInformation performanceInformation is null!");
	            }
	            logger.info("PerformanceInformationServiceImpl savePerformanceInformation: performanceInformation={}", performanceInformation);
	            Session session = sessionFactory.openSession();
	            Transaction tx = session.beginTransaction();     
	            session.save(performanceInformation);
	            tx.commit();
	            session.flush();
	            session.close();
	            return "1";
	        } catch (Exception e) {
	            logger.error("exception occurred while performing PerformanceInformationServiceImpl savePerformanceInformation. Details:" + e.getMessage());
	            return "0";
	        }
	        
	}


	@Override
	public String updatePerformanceInformation(PerformanceInformation performanceInformation) {
		try {
            if (null == performanceInformation) {
                logger.error("performanceInformation updatePerformanceInformation performanceInformation is null!");
            }
            logger.info("PerformanceInformationServiceImpl updatePerformanceInformation: performanceInformation={}", performanceInformation);
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();     
            session.update(performanceInformation);
            tx.commit();
            session.flush();
            session.close();
            return "1";
        } catch (Exception e) {
            logger.error("exception occurred while performing PerformanceInformationServiceImpl updatePerformanceInformation. Details:" + e.getMessage());
            return "0";
        }
	}


	public int getAllCount(PerformanceInformation performanceInformation, int currentPage, int pageSize) {
		try{
			StringBuffer hql = new StringBuffer("select count(*) from PerformanceInformation a where 1=1");
			if (null == performanceInformation) {
                //logger.error("AlarmsInformationServiceImpl getAllCount performanceInformation is null!");
            }else {
            	if(null!=performanceInformation.getName()) {
                	String ver=performanceInformation.getName();
                	hql.append(" and a.name like '%"+ver+"%'");
                }
            	if(null!=performanceInformation.getValue()) {
                	String ver=performanceInformation.getValue();
                	hql.append(" and a.value like '%"+ver+"%'");
                }
            	if(null!=performanceInformation.getEventId()) {
                	String ver=performanceInformation.getEventId();
                	hql.append(" and a.eventId = '"+ver+"'");
                }
            	if(null!=performanceInformation.getCreateTime()) {
                	Date ver =performanceInformation.getCreateTime();
                	hql.append(" and a.createTime > '%"+ver+"%'");
                }
            	if(null!=performanceInformation.getUpdateTime()) {
                	Date ver =performanceInformation.getUpdateTime();
                	hql.append(" and a.updateTime like '%"+ver+"%'");
                }
            }
            Session session = sessionFactory.openSession();
            long q=(long)session.createQuery(hql.toString()).uniqueResult();
            session.flush();
            session.close();
            return (int)q;
        } catch (Exception e) {
            logger.error("exception occurred while performing PerformanceInformationServiceImpl getAllCount. Details:" + e.getMessage());
            return 0;
        }
	}

	@SuppressWarnings("unchecked")	
	@Override
	public Page<PerformanceInformation> queryPerformanceInformation(PerformanceInformation performanceInformation,
			int currentPage, int pageSize) {
		Page<PerformanceInformation> page = new Page<PerformanceInformation>();
		int allRow =this.getAllCount(performanceInformation,currentPage,pageSize);
		int offset = page.countOffset(currentPage, pageSize);
		
		try{
			StringBuffer hql =new StringBuffer("from PerformanceInformation a where 1=1 ");
            if (null == performanceInformation) {
                //logger.error("AlarmsInformationServiceImpl queryPerformanceInformation performanceInformation is null!");
            }else {
            	if(null!=performanceInformation.getName()) {
                	String ver=performanceInformation.getName();
                	hql.append(" and a.name like '%"+ver+"%'");
                }
            	if(null!=performanceInformation.getValue()) {
                	String ver=performanceInformation.getValue();
                	hql.append(" and a.value like '%"+ver+"%'");
                }
            	if(null!=performanceInformation.getEventId()) {
                	String ver=performanceInformation.getEventId();
                	hql.append(" and a.eventId = '"+ver+"'");
                }
            	if(null!=performanceInformation.getCreateTime()) {
                	Date ver =performanceInformation.getCreateTime();
                	hql.append(" and a.createTime > '%"+ver+"%'");
                }
            	if(null!=performanceInformation.getUpdateTime()) {
                	Date ver =performanceInformation.getUpdateTime();
                	hql.append(" and a.updateTime like '%"+ver+"%'");
                }
            } 
            logger.info("PerformanceInformationServiceImpl queryPerformanceInformation: performanceInformation={}", performanceInformation);
            Session session = sessionFactory.openSession();
            Query query = session.createQuery(hql.toString());
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);
            List<PerformanceInformation> list= query.list();

            page.setPageNo(currentPage);
            page.setPageSize(pageSize);
            page.setTotalRecords(allRow);
            page.setList(list);
            session.flush();
            session.close();
            return page;
        } catch (Exception e) {
            logger.error("exception occurred while performing PerformanceInformationServiceImpl queryPerformanceInformation. Details:" + e.getMessage());
            return null;
        }
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PerformanceInformation> queryId(String[] id) {
		try {
			if(id.length==0) {
				logger.error("PerformanceInformationServiceImpl queryId is null!");
			}
			List<PerformanceInformation> list = new ArrayList<>();
			Session session = sessionFactory.openSession();
			Query query = session.createQuery("from PerformanceInformation a where a.eventId IN (:alist)");
			list = query.setParameterList("alist", id).list();
            session.close();
			return list;
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceInformationServiceImpl queryId. Details:" + e.getMessage());
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PerformanceInformation> queryDateBetween(String eventId,Date startDate, Date endDate) {
		try {
			List<PerformanceInformation> list = new ArrayList<>();
			Session session = sessionFactory.openSession();
			Query query = session.createQuery("from PerformanceInformation a where a.eventId = :eventId and a.createTime BETWEEN :startDate and :endDate");
			list = query.setParameter("eventId",eventId).setParameter("startDate", startDate).setParameter("endDate",endDate).list();
			session.close();
			return list;
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceInformationServiceImpl queryId. Details:" + e.getMessage());
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public int queryDataBetweenSum(String eventId, String name, Date startDate, Date endDate){

		try(Session session = sessionFactory.openSession();) {
			int sum = 0;
			Query query = session.createQuery("select sum(a.value) from PerformanceInformation a where a.eventId = :eventId and a.name = :name and a.createTime BETWEEN :startDate and :endDate");
			sum = Integer.parseInt(query.setParameter("eventId",eventId).setParameter("name",name).setParameter("startDate", startDate).setParameter("endDate",endDate).uniqueResult().toString());
			return sum;
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceInformationServiceImpl queryId. Details:" + e.getMessage());
			return 0;
		}
	}

}
