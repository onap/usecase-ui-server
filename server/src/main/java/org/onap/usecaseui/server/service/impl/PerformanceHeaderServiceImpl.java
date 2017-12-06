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
import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.service.PerformanceHeaderService;
import org.onap.usecaseui.server.util.Page;
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
    private SessionFactory sessionFactory;


	@Override
	public String savePerformanceHeader(PerformanceHeader performanceHeder) {
		 try(Session session = sessionFactory.openSession();){
	            if (null == performanceHeder){
	                logger.error("PerformanceHeaderServiceImpl savePerformanceHeader performanceHeder is null!");
	            }
	            logger.info("PerformanceHeaderServiceImpl savePerformanceHeader: performanceHeder={}", performanceHeder);
	            Transaction tx = session.beginTransaction();     
	            session.save(performanceHeder);
	            tx.commit();
	            session.flush();
	            return "1";
	        } catch (Exception e) {
	            logger.error("exception occurred while performing PerformanceHeaderServiceImpl savePerformanceHeader. Details:" + e.getMessage());
	            return "0";
	        }
	        
	}


	@Override
	public String updatePerformanceHeader(PerformanceHeader performanceHeder) {
		try(Session session = sessionFactory.openSession();){
            if (null == performanceHeder){
                logger.error("PerformanceHeaderServiceImpl updatePerformanceHeader performanceHeder is null!");
            }
            logger.info("PerformanceHeaderServiceImpl updatePerformanceHeader: performanceHeder={}", performanceHeder);
            Transaction tx = session.beginTransaction();     
            session.update(performanceHeder);
            tx.commit();
            session.flush();
            return "1";
        } catch (Exception e) {
            logger.error("exception occurred while performing PerformanceHeaderServiceImpl updatePerformanceHeader. Details:" + e.getMessage());
            return "0";
        }
	}


	public int getAllCount(PerformanceHeader performanceHeder, int currentPage, int pageSize) {
		try(Session session = sessionFactory.openSession();){
			StringBuffer hql = new StringBuffer("select count(*) from PerformanceHeader a where 1=1");
			if (null == performanceHeder) {
                //logger.error("PerformanceHeaderServiceImpl getAllCount performanceHeder is null!");
            }else {
            	if(null!=performanceHeder.getVersion()) {
                	String ver=performanceHeder.getVersion();
                	hql.append(" and a.version like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getEventName()) {
                	String ver=performanceHeder.getEventName();
                	hql.append(" and a.eventName like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getDomain()) {
                	String ver=performanceHeder.getDomain();
                	hql.append(" and a.domain like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getEventId()) {
                	String ver=performanceHeder.getEventId();
                	hql.append(" and a.eventId = '"+ver+"'");
                }
            	if(null!=performanceHeder.getNfcNamingCode()) {
                	String ver=performanceHeder.getNfcNamingCode();
                	hql.append(" and a.nfcNamingCode like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getNfNamingCode()) {
                	String ver=performanceHeder.getNfNamingCode();
                	hql.append(" and a.nfNamingCode like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getSourceId()) {
                	String ver =performanceHeder.getSourceId();
                	hql.append(" and a.sourceId like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getSourceName()) {
                	String ver =performanceHeder.getSourceName();
                	hql.append(" and a.sourceName like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getReportingEntityId()) {
                	String ver =performanceHeder.getReportingEntityId();
                	hql.append(" and a.reportingEntityId like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getReportingEntityName()) {
                	String ver =performanceHeder.getReportingEntityName();
                	hql.append(" and a.reportingEntityName like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getPriority()) {
                	String ver =performanceHeder.getPriority();
                	hql.append(" and a.priority like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getStartEpochMicrosec()) {
                	String ver =performanceHeder.getStartEpochMicrosec();
                	hql.append(" and a.startEpochMicrosec like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getLastEpochMicroSec()) {
                	String ver =performanceHeder.getLastEpochMicroSec();
                	hql.append(" and a.lastEpochMicroSec like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getSequence()) {
                	String ver =performanceHeder.getSequence();
                	hql.append(" and a.sequence like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getMeasurementsForVfScalingVersion()) {
                	String ver =performanceHeder.getMeasurementsForVfScalingVersion();
                	hql.append(" and a.measurementsForVfScalingVersion like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getMeasurementInterval()) {
                	String ver =performanceHeder.getMeasurementInterval();
                	hql.append(" and a.measurementInterval like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getEventType()) {
                	String ver =performanceHeder.getEventType();
                	hql.append(" and a.eventType like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getCreateTime() && null!=performanceHeder.getUpdateTime()) {
                	hql.append(" and a.createTime between :startTime and :endTime ");
                }
            }
            Query query = session.createQuery(hql.toString());
			if (null != performanceHeder)
                if(null!=performanceHeder.getCreateTime() && null!=performanceHeder.getUpdateTime()) {
                    query.setDate("startTime",performanceHeder.getCreateTime()).setDate("endTime",performanceHeder.getUpdateTime());
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
	public Page<PerformanceHeader> queryPerformanceHeader(PerformanceHeader performanceHeder, int currentPage,
			int pageSize) {
		Page<PerformanceHeader> page = new Page<PerformanceHeader>();
		int allRow =this.getAllCount(performanceHeder,currentPage,pageSize);
		int offset = page.countOffset(currentPage, pageSize);
		
		try(Session session = sessionFactory.openSession();){
			StringBuffer hql =new StringBuffer("from PerformanceHeader a where 1=1");
            if (null == performanceHeder) {
                //logger.error("PerformanceHeaderServiceImpl queryPerformanceHeader performanceHeder is null!");
            }else {
            	if(null!=performanceHeder.getVersion()) {
                	String ver=performanceHeder.getVersion();
                	hql.append(" and a.version like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getEventName()) {
                	String ver=performanceHeder.getEventName();
                	hql.append(" and a.eventName like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getDomain()) {
                	String ver=performanceHeder.getDomain();
                	hql.append(" and a.domain like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getEventId()) {
                	String ver=performanceHeder.getEventId();
                	hql.append(" and a.eventId = '"+ver+"'");
                }
            	if(null!=performanceHeder.getNfcNamingCode()) {
                	String ver=performanceHeder.getNfcNamingCode();
                	hql.append(" and a.nfcNamingCode like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getNfNamingCode()) {
                	String ver=performanceHeder.getNfNamingCode();
                	hql.append(" and a.nfNamingCode like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getSourceId()) {
                	String ver =performanceHeder.getSourceId();
                	hql.append(" and a.sourceId = '"+ver+"'");
                }
            	if(null!=performanceHeder.getSourceName()) {
                	String ver =performanceHeder.getSourceName();
                	hql.append(" and a.sourceName like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getReportingEntityId()) {
                	String ver =performanceHeder.getReportingEntityId();
                	hql.append(" and a.reportingEntityId like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getReportingEntityName()) {
                	String ver =performanceHeder.getReportingEntityName();
                	hql.append(" and a.reportingEntityName like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getPriority()) {
                	String ver =performanceHeder.getPriority();
                	hql.append(" and a.priority like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getStartEpochMicrosec()) {
                	String ver =performanceHeder.getStartEpochMicrosec();
                	hql.append(" and a.startEpochMicrosec like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getLastEpochMicroSec()) {
                	String ver =performanceHeder.getLastEpochMicroSec();
                	hql.append(" and a.lastEpochMicroSec like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getSequence()) {
                	String ver =performanceHeder.getSequence();
                	hql.append(" and a.sequence like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getMeasurementsForVfScalingVersion()) {
                	String ver =performanceHeder.getMeasurementsForVfScalingVersion();
                	hql.append(" and a.measurementsForVfScalingVersion like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getMeasurementInterval()) {
                	String ver =performanceHeder.getMeasurementInterval();
                	hql.append(" and a.measurementInterval like '%"+ver+"%'");
                }
            	if(null!=performanceHeder.getEventType()) {
                	String ver =performanceHeder.getEventType();
                	hql.append(" and a.eventType like '%"+ver+"%'");
                }
				if(null!=performanceHeder.getCreateTime() && null!=performanceHeder.getUpdateTime()) {
					hql.append(" and a.createTime between :startTime and :endTime ");
				}
            }
            logger.info("PerformanceHeaderServiceImpl queryPerformanceHeader: performanceHeder={}", performanceHeder);
            Query query = session.createQuery(hql.toString());
			if (null != performanceHeder)
                if(null!=performanceHeder.getCreateTime() && null!=performanceHeder.getUpdateTime()) {
                    query.setDate("startTime",performanceHeder.getCreateTime()).setDate("endTime",performanceHeder.getUpdateTime());
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
		try(Session session = sessionFactory.openSession();) {
			if(id.length==0) {
				logger.error("PerformanceHeaderServiceImpl queryId is null!");
			}
			List<PerformanceHeader> list = new ArrayList<PerformanceHeader>();
			Query query = session.createQuery("from PerformanceHeader a where a.eventName IN (:alist)");
			list = query.setParameterList("alist", id).list();
			return list;
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceHeaderServiceImpl queryId. Details:" + e.getMessage());
			return null;
		}
	}


	@Override
	public List<String> queryAllSourceId() {
		try(Session session = sessionFactory.openSession();) {
			Query query = session.createQuery("select a.sourceId from PerformanceHeader a");
			return query.list();
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceHeaderServiceImpl queryAllSourceId. Details:" + e.getMessage());
			return null;
		}
	}
}
