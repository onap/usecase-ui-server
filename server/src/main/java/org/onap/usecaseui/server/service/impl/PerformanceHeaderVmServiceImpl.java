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
import org.onap.usecaseui.server.bean.PerformanceHeaderVm;
import org.onap.usecaseui.server.service.PerformanceHeaderVmService;
import org.onap.usecaseui.server.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;


@Service("PerformanceHeaderVmService")
@Transactional
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class PerformanceHeaderVmServiceImpl implements PerformanceHeaderVmService {
	
    private static final Logger logger = LoggerFactory.getLogger(PerformanceHeaderVmServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;




	@Override
	public PerformanceHeaderVm getPerformanceHeaderDetail(Integer id) {
		try(Session session = sessionFactory.openSession()) {

			String string = "from PerformanceHeaderVm a where 1=1 and a.id=:id";
			Query q = session.createQuery(string);
			q.setInteger("id",id);
			PerformanceHeaderVm performanceHeaderVm =(PerformanceHeaderVm)q.uniqueResult();
			session.flush();
			return performanceHeaderVm;

		}catch (Exception e){
			logger.error("exception occurred while performing PerformanceHeaderServiceImpl getPerformanceHeaderDetail."+e.getMessage());
			return null;
		}
	}

	@Override
	public int getAllByDatetime(String eventId,  String createTime) {
		try (Session session = sessionFactory.openSession();){
			StringBuffer string = new StringBuffer("select count(*) as count from PerformanceHeaderVm a where 1=1");

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
			//List<PerformanceHeader> list =query.list();
			session.flush();
			return a;

		}catch (Exception e){

			logger.error("exception occurred while performing PerformanceHeaderVmServiceImpl getAllCount."+e.getMessage());
			return 0;
		}

	}


	@Override
	public int getAllCountByEventType(){
		try (Session session = sessionFactory.openSession()){
			StringBuffer count = new StringBuffer("select count(*) from PerformanceHeader a where 1=1");
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
			logger.error("exception occurred while performing PerformanceHeaderServiceImpl getAllCount."+e.getMessage());
			return 0;
		}
	}





	@Override
	public List<PerformanceHeaderVm> getAllByEventType(String eventName,String sourceName,String reportingEntityName,  Date createTime, Date endTime){
		try (Session session = sessionFactory.openSession()){
			StringBuffer string = new StringBuffer("from PerformanceHeaderVm a where 1=1");
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

			List<PerformanceHeaderVm> list =query.list();

			return list;

		}catch (Exception e){

			logger.error("exception occurred while performing PerformanceHeaderVmServiceImpl getAllCount."+e.getMessage());
			return null;
		}



	}



	@Override
	public String savePerformanceHeaderVm(PerformanceHeaderVm performanceHederVm) {
		 try(Session session = sessionFactory.openSession();){
	            if (null == performanceHederVm){
	                logger.error("PerformanceHeaderServiceImpl savePerformanceHeaderVm performanceHeder is null!");
	            }
	            logger.info("PerformanceHeaderServiceImpl savePerformanceHeader: performanceHeder={}", performanceHederVm);
	            Transaction tx = session.beginTransaction();     
	            session.save(performanceHederVm);
	            tx.commit();
	            session.flush();
	            return "1";
	        } catch (Exception e) {
	            logger.error("exception occurred while performing PerformanceHeaderServiceImpl savePerformanceHeader. Details:" + e.getMessage());
	            return "0";
	        }
	        
	}


	@Override
	public String updatePerformanceHeaderVm(PerformanceHeaderVm performanceHederVm) {
		try(Session session = sessionFactory.openSession();){
            if (null == performanceHederVm){
                logger.error("PerformanceHeaderServiceImpl updatePerformanceHeaderVm performanceHeder is null!");
            }
            logger.info("PerformanceHeaderServiceImpl updatePerformanceHeader: performanceHeder={}", performanceHederVm);
            Transaction tx = session.beginTransaction();     
            session.update(performanceHederVm);
            tx.commit();
            session.flush();
            return "1";
        } catch (Exception e) {
            logger.error("exception occurred while performing PerformanceHeaderServiceImpl updatePerformanceHeader. Details:" + e.getMessage());
            return "0";
        }
	}


	public int getAllCount(PerformanceHeaderVm performanceHederVm, int currentPage, int pageSize) {
		try(Session session = sessionFactory.openSession();){
			StringBuffer hql = new StringBuffer("select count(*) from PerformanceHeaderVm a where 1=1");
			if (null == performanceHederVm) {
                //logger.error("PerformanceHeaderServiceImpl getAllCount performanceHeder is null!");
            }else {
            	if(null!=performanceHederVm.getVersion()) {
                	String ver=performanceHederVm.getVersion();
                	hql.append(" and a.version like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getEventName()) {
                	String ver=performanceHederVm.getEventName();
                	hql.append(" and a.eventName like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getDomain()) {
                	String ver=performanceHederVm.getDomain();
                	hql.append(" and a.domain like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getEventId()) {
                	String ver=performanceHederVm.getEventId();
                	hql.append(" and a.eventId = '"+ver+"'");
                }
            	if(null!=performanceHederVm.getNfcNamingCode()) {
                	String ver=performanceHederVm.getNfcNamingCode();
                	hql.append(" and a.nfcNamingCode like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getNfNamingCode()) {
                	String ver=performanceHederVm.getNfNamingCode();
                	hql.append(" and a.nfNamingCode like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getSourceId()) {
                	String ver =performanceHederVm.getSourceId();
                	hql.append(" and a.sourceId like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getSourceName()) {
                	String ver =performanceHederVm.getSourceName();
                	hql.append(" and a.sourceName like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getReportingEntityId()) {
                	String ver =performanceHederVm.getReportingEntityId();
                	hql.append(" and a.reportingEntityId like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getReportingEntityName()) {
                	String ver =performanceHederVm.getReportingEntityName();
                	hql.append(" and a.reportingEntityName like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getPriority()) {
                	String ver =performanceHederVm.getPriority();
                	hql.append(" and a.priority like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getStartEpochMicrosec()) {
                	String ver =performanceHederVm.getStartEpochMicrosec();
                	hql.append(" and a.startEpochMicrosec like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getLastEpochMicroSec()) {
                	String ver =performanceHederVm.getLastEpochMicroSec();
                	hql.append(" and a.lastEpochMicroSec like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getSequence()) {
                	String ver =performanceHederVm.getSequence();
                	hql.append(" and a.sequence like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getMeasurementsForVfScalingVersion()) {
                	String ver =performanceHederVm.getMeasurementsForVfScalingVersion();
                	hql.append(" and a.measurementsForVfScalingVersion like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getMeasurementInterval()) {
                	String ver =performanceHederVm.getMeasurementInterval();
                	hql.append(" and a.measurementInterval like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getEventType()) {
                	String ver =performanceHederVm.getEventType();
                	hql.append(" and a.eventType like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getCreateTime() && null!=performanceHederVm.getUpdateTime()) {
                	hql.append(" and a.createTime between :startTime and :endTime ");
                }
            }
            Query query = session.createQuery(hql.toString());
			if (null != performanceHederVm)
                if(null!=performanceHederVm.getCreateTime() && null!=performanceHederVm.getUpdateTime()) {
                    query.setDate("startTime",performanceHederVm.getCreateTime()).setDate("endTime",performanceHederVm.getUpdateTime());
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
	public Page<PerformanceHeaderVm> queryPerformanceHeaderVm(PerformanceHeaderVm performanceHederVm, int currentPage,
			int pageSize) {
		Page<PerformanceHeaderVm> page = new Page<PerformanceHeaderVm>();
		int allRow =this.getAllCount(performanceHederVm,currentPage,pageSize);
		int offset = page.countOffset(currentPage, pageSize);
		
		try(Session session = sessionFactory.openSession();){
			StringBuffer hql =new StringBuffer("from PerformanceHeaderVm a where 1=1");
            if (null == performanceHederVm) {
                //logger.error("PerformanceHeaderServiceImpl queryPerformanceHeaderVm performanceHeder is null!");
            }else {
            	if(null!=performanceHederVm.getVersion()) {
                	String ver=performanceHederVm.getVersion();
                	hql.append(" and a.version like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getEventName()) {
                	String ver=performanceHederVm.getEventName();
                	hql.append(" and a.eventName like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getDomain()) {
                	String ver=performanceHederVm.getDomain();
                	hql.append(" and a.domain like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getEventId()) {
                	String ver=performanceHederVm.getEventId();
                	hql.append(" and a.eventId = '"+ver+"'");
                }
            	if(null!=performanceHederVm.getNfcNamingCode()) {
                	String ver=performanceHederVm.getNfcNamingCode();
                	hql.append(" and a.nfcNamingCode like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getNfNamingCode()) {
                	String ver=performanceHederVm.getNfNamingCode();
                	hql.append(" and a.nfNamingCode like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getSourceId()) {
                	String ver =performanceHederVm.getSourceId();
                	hql.append(" and a.sourceId = '"+ver+"'");
                }
            	if(null!=performanceHederVm.getSourceName()) {
                	String ver =performanceHederVm.getSourceName();
                	hql.append(" and a.sourceName like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getReportingEntityId()) {
                	String ver =performanceHederVm.getReportingEntityId();
                	hql.append(" and a.reportingEntityId like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getReportingEntityName()) {
                	String ver =performanceHederVm.getReportingEntityName();
                	hql.append(" and a.reportingEntityName like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getPriority()) {
                	String ver =performanceHederVm.getPriority();
                	hql.append(" and a.priority like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getStartEpochMicrosec()) {
                	String ver =performanceHederVm.getStartEpochMicrosec();
                	hql.append(" and a.startEpochMicrosec like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getLastEpochMicroSec()) {
                	String ver =performanceHederVm.getLastEpochMicroSec();
                	hql.append(" and a.lastEpochMicroSec like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getSequence()) {
                	String ver =performanceHederVm.getSequence();
                	hql.append(" and a.sequence like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getMeasurementsForVfScalingVersion()) {
                	String ver =performanceHederVm.getMeasurementsForVfScalingVersion();
                	hql.append(" and a.measurementsForVfScalingVersion like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getMeasurementInterval()) {
                	String ver =performanceHederVm.getMeasurementInterval();
                	hql.append(" and a.measurementInterval like '%"+ver+"%'");
                }
            	if(null!=performanceHederVm.getEventType()) {
                	String ver =performanceHederVm.getEventType();
                	hql.append(" and a.eventType like '%"+ver+"%'");
                }
				if(null!=performanceHederVm.getCreateTime() && null!=performanceHederVm.getUpdateTime()) {
					hql.append(" and a.createTime between :startTime and :endTime ");
				}
            }
            logger.info("PerformanceHeaderServiceImpl queryPerformanceHeader: performanceHeder={}", performanceHederVm);
            Query query = session.createQuery(hql.toString());
			if (null != performanceHederVm)
                if(null!=performanceHederVm.getCreateTime() && null!=performanceHederVm.getUpdateTime()) {
                    query.setDate("startTime",performanceHederVm.getCreateTime()).setDate("endTime",performanceHederVm.getUpdateTime());
                }
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);
            List<PerformanceHeaderVm> list= query.list();
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
	public List<PerformanceHeaderVm> queryId(String[] id) {
		try(Session session = sessionFactory.openSession();) {
			if(id.length==0) {
				logger.error("PerformanceHeaderServiceImpl queryId is null!");
			}
			List<PerformanceHeaderVm> list = new ArrayList<PerformanceHeaderVm>();
			Query query = session.createQuery("from PerformanceHeaderVm a where a.eventName IN (:alist)");
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
			Query query = session.createQuery("select a.sourceId from PerformanceHeaderVm a");
			return query.list();
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceHeaderServiceImpl queryAllSourceId. Details:" + e.getMessage());
			return null;
		}
	}
}
