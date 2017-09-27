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

	@Override
	public String saveAlarmsInformation(AlarmsInformation alarmsInformation) {
		 try{
	            if (null == alarmsInformation) {
	                logger.error("alarmsInformation AlarmsInformation alarmsInformation is null!");
	            }
	            logger.info("AlarmsInformationServiceImpl saveAlarmsInformation: alarmsInformation={}", alarmsInformation);
	            Session session = sessionFactory.openSession();
	            Transaction tx = session.beginTransaction();     
	            session.save(alarmsInformation);
	            tx.commit();
	            session.flush();
	            session.close();
	            return "1";
	        } catch (Exception e) {
	            logger.error("Exception occurred while performing AlarmsInformationServiceImpl saveAlarmsInformation. Details:" + e.getMessage());
	            return "0";
	        }
	        
	}

	@Override
	public String updateAlarmsInformation(AlarmsInformation alarmsInformation) {
		try{
            if (null == alarmsInformation) {
                logger.error("alarmsInformation AlarmsInformation alarmsInformation is null!");
            }
            logger.info("AlarmsInformationServiceImpl updateAlarmsInformation: alarmsInformation={}", alarmsInformation);
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();     
            session.update(alarmsInformation);
            tx.commit();
            session.flush();
            session.close();
            return "1";
        } catch (Exception e) {
            logger.error("Exception occurred while performing AlarmsInformationServiceImpl updateAlarmsInformation. Details:" + e.getMessage());
            return "0";
        }
	}
	

	public int getAllCount() {
		try{
            Session session = sessionFactory.openSession();
            long q=(long)session.createQuery("select count(*) from AlarmsInformation").uniqueResult();
            session.flush();
            session.close();
            return (int)q;
        } catch (Exception e) {
            logger.error("Exception occurred while performing AlarmsInformationServiceImpl getAllCount. Details:" + e.getMessage());
            return 0;
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<AlarmsInformation> queryAlarmsInformation(AlarmsInformation alarmsInformation, int currentPage,
			int pageSize) {
		Page<AlarmsInformation> page = new Page<AlarmsInformation>();
		int allRow =this.getAllCount();
		int offset = page.countOffset(currentPage, pageSize);
		
		try{
			StringBuffer hql =new StringBuffer("from AlarmsInformation a where 1=1");
            if (null == alarmsInformation) {
                logger.error("AlarmsInformationServiceImpl queryAlarmsInformation alarmsInformation is null!");
            }else if(null!=alarmsInformation.getName()) {
            	String ver=alarmsInformation.getName();
            	hql.append(" and a.name like '%"+ver+"%'");
            }else if(null!=alarmsInformation.getValue()) {
            	String ver=alarmsInformation.getValue();
            	hql.append(" and a.value like '%"+ver+"%'");
            }else if(null!=alarmsInformation.getEventId()) {
            	String ver=alarmsInformation.getEventId();
            	hql.append(" and a.eventId = '"+ver+"'");
            }else if(null!=alarmsInformation.getCreateTime()) {
            	Date ver =alarmsInformation.getCreateTime();
            	hql.append(" and a.createTime > '%"+ver+"%'");
            }else if(null!=alarmsInformation.getUpdateTime()) {
            	Date ver =alarmsInformation.getUpdateTime();
            	hql.append(" and a.updateTime like '%"+ver+"%'");
            }
            logger.info("AlarmsInformationServiceImpl queryAlarmsInformation: alarmsInformation={}", alarmsInformation);
            Session session = sessionFactory.openSession();
            Query query = session.createQuery(hql.toString());
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);
            List<AlarmsInformation> list= query.list();
            page.setPageNo(currentPage);
            page.setPageSize(pageSize);
            page.setTotalRecords(allRow);
            page.setList(list);
            session.flush();
            session.close();
            return page;
        } catch (Exception e) {
            logger.error("Exception occurred while performing AlarmsInformationServiceImpl queryAlarmsInformation. Details:" + e.getMessage());
            return null;
        }
	}

	@Override
	public List<AlarmsInformation> queryId(String[] id) {
		try {
			if(id.length==0) {
				logger.error("AlarmsInformationServiceImpl queryId is null!");
			}
			List<AlarmsInformation> list = new ArrayList<AlarmsInformation>();
			Session session = sessionFactory.openSession();
			Query query = session.createQuery("from AlarmsInformation a where a.eventId IN (:alist)");
			list = query.setParameterList("alist", id).list();
			session.close();
			return list;
		} catch (Exception e) {
			logger.error("Exception occurred while performing AlarmsInformationServiceImpl queryId. Details:" + e.getMessage());
			return null;
		}
	}

    
    
    
}
