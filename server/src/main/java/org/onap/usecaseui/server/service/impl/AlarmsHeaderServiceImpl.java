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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.bean.SortMaster;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.util.Page;
import org.onap.usecaseui.server.util.UuiCommonUtil;
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
				return "0";
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
				return "0";
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
				return -1;
			}else {
				if(UuiCommonUtil.isNotNullOrEmpty(alarmsHeader.getSourceName())) {
					String ver =alarmsHeader.getSourceName();
					count.append(" and a.sourceName like '%"+ver+"%'");
				}
				if(UuiCommonUtil.isNotNullOrEmpty(alarmsHeader.getPriority())) {
					String ver =alarmsHeader.getPriority();
					count.append(" and a.priority like '%"+ver+"%'");
				}
				if(UuiCommonUtil.isNotNullOrEmpty(alarmsHeader.getStatus())) {
					String ver =alarmsHeader.getStatus();
					count.append(" and a.status = '"+ver+"'");
				}
				if(UuiCommonUtil.isNotNullOrEmpty(alarmsHeader.getStartEpochMicrosec())&&UuiCommonUtil.isNotNullOrEmpty(alarmsHeader.getLastEpochMicroSec())) {
					count.append(" and (CASE WHEN a.startEpochMicrosec=0 THEN a.lastEpochMicroSec ELSE a.startEpochMicrosec END) between :startTime and :endTime ");
				}
			}
			Query query = session.createQuery(count.toString());
			if(UuiCommonUtil.isNotNullOrEmpty(alarmsHeader.getStartEpochMicrosec())&&UuiCommonUtil.isNotNullOrEmpty(alarmsHeader.getLastEpochMicroSec())) {
				query.setString("startTime",alarmsHeader.getStartEpochMicrosec());
				query.setString("endTime",alarmsHeader.getLastEpochMicroSec());
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
				logger.error("AlarmsHeaderServiceImpl queryAlarmsHeader alarmsHeader is null!");
				return null;
			}else {
				if(UuiCommonUtil.isNotNullOrEmpty(alarmsHeader.getSourceName())) {
					String ver =alarmsHeader.getSourceName();
					hql.append(" and a.sourceName like '%"+ver+"%'");
				}
				if(UuiCommonUtil.isNotNullOrEmpty(alarmsHeader.getVfStatus())) {
					String ver =alarmsHeader.getVfStatus();
					hql.append(" and a.vfStatus = '"+ver+"'");
				}
				if(UuiCommonUtil.isNotNullOrEmpty(alarmsHeader.getStatus())) {
					String ver =alarmsHeader.getStatus();
					hql.append(" and a.status = '"+ver+"'");
				}
				if(UuiCommonUtil.isNotNullOrEmpty(alarmsHeader.getStartEpochMicrosec())&&UuiCommonUtil.isNotNullOrEmpty(alarmsHeader.getLastEpochMicroSec())) {
					hql.append(" and (CASE WHEN a.startEpochMicrosec=0 THEN a.lastEpochMicroSec ELSE a.startEpochMicrosec END) between :startTime and :endTime ");
				}
			}
			logger.info("AlarmsHeaderServiceImpl queryAlarmsHeader: alarmsHeader={}", alarmsHeader);
			Query query = session.createQuery(hql.toString());
			if(UuiCommonUtil.isNotNullOrEmpty(alarmsHeader.getStartEpochMicrosec())&&UuiCommonUtil.isNotNullOrEmpty(alarmsHeader.getLastEpochMicroSec())) {
				query.setString("startTime",alarmsHeader.getStartEpochMicrosec());
				query.setString("endTime",alarmsHeader.getLastEpochMicroSec());
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
			List<AlarmsHeader> list = new ArrayList<AlarmsHeader>();
			if(id.length==0) {
				logger.error("AlarmsHeaderServiceImpl queryId is null!");
				return list;
			}
			Query query = session.createQuery("from AlarmsHeader a where a.eventName IN (:alist)");
			list = query.setParameterList("alist", id).list();
			return list;
		} catch (Exception e) {
			logger.error("exception occurred while performing AlarmsHeaderServiceImpl queryId. Details:" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public String updateAlarmsHeader2018(String status, Timestamp date, String startEpochMicrosecCleared, String lastEpochMicroSecCleared, String eventName, String reportingEntityName, String specificProblem) {

		try(Session session = getSession()){
			//try(Session session = sessionFactory.getCurrentSession();){
			session.beginTransaction();

			//Query q=session.createQuery("update AlarmsHeader set status='"+status+"', updateTime='"+date+"' , startEpochMicrosecCleared='"+startEpochMicrosecCleared+"'  ,lastEpochMicroSecCleared='"+lastEpochMicroSecCleared+"'    where eventName='"+eventName+"' and reportingEntityName='"+reportingEntityName+"' and specificProblem ='"+specificProblem+"'");
            Query q=session.createQuery("update AlarmsHeader set status=:status, startEpochMicrosecCleared=:startEpochMicrosecCleared  ,lastEpochMicroSecCleared=:lastEpochMicroSecCleared    where eventName=:eventName and reportingEntityName=:reportingEntityName and specificProblem =:specificProblem");

            q.setString("status",status);

            q.setString("startEpochMicrosecCleared",startEpochMicrosecCleared);
            q.setString("lastEpochMicroSecCleared",lastEpochMicroSecCleared);
            q.setString("eventName",eventName);
            q.setString("reportingEntityName",reportingEntityName);
            q.setString("specificProblem",specificProblem);


            q.executeUpdate();
			session.getTransaction().commit();
			session.flush();
			return "1";
		} catch (Exception e) {
			logger.error("exception occurred while performing AlarmsInformationServiceImpl updateAlarmsInformation. Details:" + e.getMessage());
			return "0";
		}
	}

    @Override
    public String queryStatusCount(String status) {
        try(Session session = getSession()){
            String hql = "select count(status) from AlarmsHeader a";
            if (!status.equals("0"))
                hql+=" where a.status = :status";
            Query query = session.createQuery(hql);
            if (!status.equals("0"))
                query.setString("status",status);
            return query.uniqueResult().toString();
        } catch (Exception e) {
            logger.error("exception occurred while performing AlarmsHeaderServiceImpl queryStatusCount. Details:" + e.getMessage());
            return null;
        }
    }
    
	@Override
	public AlarmsHeader getAlarmsHeaderById(String id) {
		try(Session session = getSession()) {

			String string = "from AlarmsHeader a where 1=1 and a.id=:id";
			Query q = session.createQuery(string);
			q.setString("id",id);
			AlarmsHeader alarmsHeader =(AlarmsHeader)q.uniqueResult();
 			return alarmsHeader;

		}catch (Exception e){
			logger.error("exception occurred while performing AlarmsHeaderServiceImpl getAlarmsHeaderDetail."+e.getMessage());
			return null;
		}
	}

	@Override
	public List<SortMaster> listSortMasters(String sortType) {
		
		try(Session session = getSession()){
			StringBuffer hql =new StringBuffer("from SortMaster a where 1=1 and a.sortType=:sortType");
			Query query = session.createQuery(hql.toString());
			query.setString("sortType",sortType);
			List<SortMaster> list= query.list();
			return list;
		} catch (Exception e) {
			logger.error("exception occurred while performing AlarmsInformationServiceImpl listSortMasters. Details:" + e.getMessage());
			return Collections.emptyList();
		}
	}
}
