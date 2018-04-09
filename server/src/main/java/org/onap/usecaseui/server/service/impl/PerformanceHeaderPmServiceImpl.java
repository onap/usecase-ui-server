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
import org.onap.usecaseui.server.bean.PerformanceHeaderPm;
import org.onap.usecaseui.server.service.PerformanceHeaderPmService;
import org.onap.usecaseui.server.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;


@Service("PerformanceHeaderPmService")
@Transactional
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class PerformanceHeaderPmServiceImpl implements PerformanceHeaderPmService {
	
    private static final Logger logger = LoggerFactory.getLogger(PerformanceHeaderPmServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;



	@Override
	public PerformanceHeaderPm getPerformanceHeaderDetail(Integer id) {
		try(Session session = sessionFactory.openSession()) {

			String string = "from PerformanceHeaderPm a where 1=1 and a.id=:id";
			Query q = session.createQuery(string);
			q.setInteger("id",id);
			PerformanceHeaderPm performanceHeaderPm =(PerformanceHeaderPm)q.uniqueResult();
			session.flush();
			return performanceHeaderPm;

		}catch (Exception e){
			logger.error("exception occurred while performing PerformanceHeaderPmServiceImpl getPerformanceHeaderPmDetail."+e.getMessage());
			return null;
		}
	}

	@Override
	public int getAllByDatetime(String eventId,  String createTime) {
		try (Session session = sessionFactory.openSession();){
			StringBuffer string = new StringBuffer("select count(*) as count from PerformanceHeaderPm a where 1=1");

			/*if(!"0".equals(status) &&  status!=null){
				string.append(" and a.status=:status");
			}*/
			if(!"0".equals(eventId) &&  eventId!=null){
				string.append(" and a.eventId=:eventId");
			}



			/*if( null!=createTime && endTime!= null) {
				string.append(" and a.createTime between :startTime and :endTime");
			}*/
			if( null!=createTime) {
				string.append(" and to_days(a.createTime) = to_days('"+createTime+"')");
			}

			/*string.append("     group by DATE_FORMAT(a.createTime,'%y-%m-%d')");*/
			Query query = session.createQuery(string.toString());
			/*if(!"0".equals(status) &&  status!=null) {
				query.setString("status", status);
			}*/
			if(!"0".equals(eventId) &&  eventId!=null) {
				query.setString("eventId", eventId);
			}

			//query.setDate("createTime",createTime);

			/*if( null!=createTime && endTime!= null) {
				query.setDate("startTime",createTime);
				query.setDate("endTime",endTime);

			}*/
			long l = (long)query.uniqueResult();
			int a = (int) l;
			//List<PerformanceHeaderPm> list =query.list();
			session.flush();
			return a;

		}catch (Exception e){

			logger.error("exception occurred while performing PerformanceHeaderPmServiceImpl getAllCount."+e.getMessage());
			return 0;
		}

	}

	@Override
	public int getAllCountByEventType(){
		try (Session session = sessionFactory.openSession()){
			StringBuffer count = new StringBuffer("select count(*) from PerformanceHeaderPm a where 1=1");
			/*if(!"0".equals(status)){
				count.append(" and a.status=:status");
			}*/
			Query  query =session.createQuery(count.toString());
			/*query.setString("status",status);*/
			//int q = (int)query.uniqueResult();
			long q=(long)query.uniqueResult();
			session.flush();
			return (int)q;
		}catch (Exception e){
			logger.error("exception occurred while performing PerformanceHeaderPmServiceImpl getAllCount."+e.getMessage());
			return 0;
		}
	}





	@Override
	public List<PerformanceHeaderPm> getAllByEventType(String eventName, String sourceName, String reportingEntityName, Date createTime, Date endTime){
		try (Session session = sessionFactory.openSession()){
			StringBuffer string = new StringBuffer("from PerformanceHeaderPm a where 1=1");
			/*if(!"0".equals(status)){
				string.append(" and a.status=:status");
			}*/
			if(!"0".equals(eventName) &&  eventName!=null){
				string.append(" and a.eventName=:eventName");
			}
			if(!"0".equals(sourceName) &&  sourceName!=null){
				string.append(" and a.sourceName=:sourceName");
			}

			if(!"0".equals(reportingEntityName) &&  reportingEntityName!=null){
				string.append(" and a.reportingEntityName=:reportingEntityName");
			}
			if( null!=createTime && endTime!= null) {
				string.append(" and a.createTime between :startTime and :endTime");
			}
			Query query = session.createQuery(string.toString());
			/*if(!"0".equals(status)) {
				query.setString("status", status);
			}*/
			if(!"0".equals(eventName) &&  eventName!=null) {
				query.setString("eventName", eventName);
			}
			if(!"0".equals(sourceName) &&  sourceName!=null) {
				query.setString("sourceName", sourceName);
			}

			if(!"0".equals(reportingEntityName) &&  reportingEntityName!=null) {
				query.setString("reportingEntityName", reportingEntityName);
			}
			if( null!=createTime && endTime!= null) {
				query.setDate("startTime",createTime);
				query.setDate("endTime",endTime);

			}

			List<PerformanceHeaderPm> list =query.list();

			return list;

		}catch (Exception e){

			logger.error("exception occurred while performing PerformanceHeaderPmServiceImpl getAllCount."+e.getMessage());
			return null;
		}



	}

















	@Override
	public String savePerformanceHeaderPm(PerformanceHeaderPm performanceHederPm) {
		 try(Session session = sessionFactory.openSession();){
	            if (null == performanceHederPm){
	                logger.error("PerformanceHeaderServiceImpl savePerformanceHeaderPm performanceHeder is null!");
	            }
	            logger.info("PerformanceHeaderServiceImpl savePerformanceHeader: performanceHeder={}", performanceHederPm);
	            Transaction tx = session.beginTransaction();     
	            session.save(performanceHederPm);
	            tx.commit();
	            session.flush();
	            return "1";
	        } catch (Exception e) {
	            logger.error("exception occurred while performing PerformanceHeaderServiceImpl savePerformanceHeader. Details:" + e.getMessage());
	            return "0";
	        }
	        
	}


	@Override
	public String updatePerformanceHeaderPm(PerformanceHeaderPm performanceHederPm) {
		try(Session session = sessionFactory.openSession();){
            if (null == performanceHederPm){
                logger.error("PerformanceHeaderServiceImpl updatePerformanceHeaderPm performanceHeder is null!");
            }
            logger.info("PerformanceHeaderServiceImpl updatePerformanceHeader: performanceHeder={}", performanceHederPm);
            Transaction tx = session.beginTransaction();     
            session.update(performanceHederPm);
            tx.commit();
            session.flush();
            return "1";
        } catch (Exception e) {
            logger.error("exception occurred while performing PerformanceHeaderServiceImpl updatePerformanceHeader. Details:" + e.getMessage());
            return "0";
        }
	}


	public int getAllCount(PerformanceHeaderPm performanceHederPm, int currentPage, int pageSize) {
		try(Session session = sessionFactory.openSession();){
			StringBuffer hql = new StringBuffer("select count(*) from PerformanceHeaderPm a where 1=1");
			if (null == performanceHederPm) {
                //logger.error("PerformanceHeaderServiceImpl getAllCount performanceHeder is null!");
            }else {
            	if(null!=performanceHederPm.getVersion()) {
                	String ver=performanceHederPm.getVersion();
                	hql.append(" and a.version like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getEventName()) {
                	String ver=performanceHederPm.getEventName();
                	hql.append(" and a.eventName like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getDomain()) {
                	String ver=performanceHederPm.getDomain();
                	hql.append(" and a.domain like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getEventId()) {
                	String ver=performanceHederPm.getEventId();
                	hql.append(" and a.eventId = '"+ver+"'");
                }
            	if(null!=performanceHederPm.getNfcNamingCode()) {
                	String ver=performanceHederPm.getNfcNamingCode();
                	hql.append(" and a.nfcNamingCode like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getNfNamingCode()) {
                	String ver=performanceHederPm.getNfNamingCode();
                	hql.append(" and a.nfNamingCode like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getSourceId()) {
                	String ver =performanceHederPm.getSourceId();
                	hql.append(" and a.sourceId like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getSourceName()) {
                	String ver =performanceHederPm.getSourceName();
                	hql.append(" and a.sourceName like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getReportingEntityId()) {
                	String ver =performanceHederPm.getReportingEntityId();
                	hql.append(" and a.reportingEntityId like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getReportingEntityName()) {
                	String ver =performanceHederPm.getReportingEntityName();
                	hql.append(" and a.reportingEntityName like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getPriority()) {
                	String ver =performanceHederPm.getPriority();
                	hql.append(" and a.priority like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getStartEpochMicrosec()) {
                	String ver =performanceHederPm.getStartEpochMicrosec();
                	hql.append(" and a.startEpochMicrosec like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getLastEpochMicroSec()) {
                	String ver =performanceHederPm.getLastEpochMicroSec();
                	hql.append(" and a.lastEpochMicroSec like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getSequence()) {
                	String ver =performanceHederPm.getSequence();
                	hql.append(" and a.sequence like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getMeasurementsForVfScalingVersion()) {
                	String ver =performanceHederPm.getMeasurementsForVfScalingVersion();
                	hql.append(" and a.measurementsForVfScalingVersion like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getMeasurementInterval()) {
                	String ver =performanceHederPm.getMeasurementInterval();
                	hql.append(" and a.measurementInterval like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getEventType()) {
                	String ver =performanceHederPm.getEventType();
                	hql.append(" and a.eventType like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getCreateTime() && null!=performanceHederPm.getUpdateTime()) {
                	hql.append(" and a.createTime between :startTime and :endTime ");
                }
            }
            Query query = session.createQuery(hql.toString());
			if (null != performanceHederPm)
                if(null!=performanceHederPm.getCreateTime() && null!=performanceHederPm.getUpdateTime()) {
                    query.setDate("startTime",performanceHederPm.getCreateTime()).setDate("endTime",performanceHederPm.getUpdateTime());
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
	public Page<PerformanceHeaderPm> queryPerformanceHeaderPm(PerformanceHeaderPm performanceHederPm, int currentPage,
			int pageSize) {
		Page<PerformanceHeaderPm> page = new Page<PerformanceHeaderPm>();
		int allRow =this.getAllCount(performanceHederPm,currentPage,pageSize);
		int offset = page.countOffset(currentPage, pageSize);
		
		try(Session session = sessionFactory.openSession();){
			StringBuffer hql =new StringBuffer("from PerformanceHeaderPm a where 1=1");
            if (null == performanceHederPm) {
                //logger.error("PerformanceHeaderServiceImpl queryPerformanceHeaderPm performanceHeder is null!");
            }else {
            	if(null!=performanceHederPm.getVersion()) {
                	String ver=performanceHederPm.getVersion();
                	hql.append(" and a.version like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getEventName()) {
                	String ver=performanceHederPm.getEventName();
                	hql.append(" and a.eventName like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getDomain()) {
                	String ver=performanceHederPm.getDomain();
                	hql.append(" and a.domain like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getEventId()) {
                	String ver=performanceHederPm.getEventId();
                	hql.append(" and a.eventId = '"+ver+"'");
                }
            	if(null!=performanceHederPm.getNfcNamingCode()) {
                	String ver=performanceHederPm.getNfcNamingCode();
                	hql.append(" and a.nfcNamingCode like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getNfNamingCode()) {
                	String ver=performanceHederPm.getNfNamingCode();
                	hql.append(" and a.nfNamingCode like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getSourceId()) {
                	String ver =performanceHederPm.getSourceId();
                	hql.append(" and a.sourceId = '"+ver+"'");
                }
            	if(null!=performanceHederPm.getSourceName()) {
                	String ver =performanceHederPm.getSourceName();
                	hql.append(" and a.sourceName like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getReportingEntityId()) {
                	String ver =performanceHederPm.getReportingEntityId();
                	hql.append(" and a.reportingEntityId like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getReportingEntityName()) {
                	String ver =performanceHederPm.getReportingEntityName();
                	hql.append(" and a.reportingEntityName like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getPriority()) {
                	String ver =performanceHederPm.getPriority();
                	hql.append(" and a.priority like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getStartEpochMicrosec()) {
                	String ver =performanceHederPm.getStartEpochMicrosec();
                	hql.append(" and a.startEpochMicrosec like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getLastEpochMicroSec()) {
                	String ver =performanceHederPm.getLastEpochMicroSec();
                	hql.append(" and a.lastEpochMicroSec like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getSequence()) {
                	String ver =performanceHederPm.getSequence();
                	hql.append(" and a.sequence like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getMeasurementsForVfScalingVersion()) {
                	String ver =performanceHederPm.getMeasurementsForVfScalingVersion();
                	hql.append(" and a.measurementsForVfScalingVersion like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getMeasurementInterval()) {
                	String ver =performanceHederPm.getMeasurementInterval();
                	hql.append(" and a.measurementInterval like '%"+ver+"%'");
                }
            	if(null!=performanceHederPm.getEventType()) {
                	String ver =performanceHederPm.getEventType();
                	hql.append(" and a.eventType like '%"+ver+"%'");
                }
				if(null!=performanceHederPm.getCreateTime() && null!=performanceHederPm.getUpdateTime()) {
					hql.append(" and a.createTime between :startTime and :endTime ");
				}
            }
            logger.info("PerformanceHeaderServiceImpl queryPerformanceHeader: performanceHeder={}", performanceHederPm);
            Query query = session.createQuery(hql.toString());
			if (null != performanceHederPm)
                if(null!=performanceHederPm.getCreateTime() && null!=performanceHederPm.getUpdateTime()) {
                    query.setDate("startTime",performanceHederPm.getCreateTime()).setDate("endTime",performanceHederPm.getUpdateTime());
                }
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);
            List<PerformanceHeaderPm> list= query.list();
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
	public List<PerformanceHeaderPm> queryId(String[] id) {
		try(Session session = sessionFactory.openSession();) {
			if(id.length==0) {
				logger.error("PerformanceHeaderServiceImpl queryId is null!");
			}
			List<PerformanceHeaderPm> list = new ArrayList<PerformanceHeaderPm>();
			Query query = session.createQuery("from PerformanceHeaderPm a where a.eventName IN (:alist)");
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
			Query query = session.createQuery("select a.sourceId from PerformanceHeaderPm a");
			return query.list();
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceHeaderServiceImpl queryAllSourceId. Details:" + e.getMessage());
			return null;
		}
	}
}
