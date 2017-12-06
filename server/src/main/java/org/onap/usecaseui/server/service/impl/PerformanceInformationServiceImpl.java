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
import java.util.*;

import javax.persistence.Id;
import javax.transaction.Transactional;

import org.hibernate.*;
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.service.PerformanceInformationService;
import org.onap.usecaseui.server.util.DateUtils;
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
		 try(Session session = sessionFactory.openSession();) {
	            if (null == performanceInformation) {
	                logger.error("performanceInformation savePerformanceInformation performanceInformation is null!");
	            }
	            logger.info("PerformanceInformationServiceImpl savePerformanceInformation: performanceInformation={}", performanceInformation);
	            Transaction tx = session.beginTransaction();     
	            session.save(performanceInformation);
	            tx.commit();
	            session.flush();
	            return "1";
	        } catch (Exception e) {
	            logger.error("exception occurred while performing PerformanceInformationServiceImpl savePerformanceInformation. Details:" + e.getMessage());
	            return "0";
	        }
	        
	}


	@Override
	public String updatePerformanceInformation(PerformanceInformation performanceInformation) {
		try(Session session = sessionFactory.openSession();) {
            if (null == performanceInformation) {
                logger.error("performanceInformation updatePerformanceInformation performanceInformation is null!");
            }
            logger.info("PerformanceInformationServiceImpl updatePerformanceInformation: performanceInformation={}", performanceInformation);
            Transaction tx = session.beginTransaction();     
            session.update(performanceInformation);
            tx.commit();
            session.flush();
            return "1";
        } catch (Exception e) {
            logger.error("exception occurred while performing PerformanceInformationServiceImpl updatePerformanceInformation. Details:" + e.getMessage());
            return "0";
        }
	}


	public int getAllCount(PerformanceInformation performanceInformation, int currentPage, int pageSize) {
		try(Session session = sessionFactory.openSession();){
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
            long q=(long)session.createQuery(hql.toString()).uniqueResult();
            session.flush();
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
		
		try(Session session = sessionFactory.openSession()){
			StringBuffer hql =new StringBuffer("from PerformanceInformation a where 1=1 ");
            if (null == performanceInformation) {

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
            Query query = session.createQuery(hql.toString());
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);
            List<PerformanceInformation> list= query.list();

            page.setPageNo(currentPage);
            page.setPageSize(pageSize);
            page.setTotalRecords(allRow);
            page.setList(list);
            session.flush();
            return page;
        } catch (Exception e) {
            logger.error("exception occurred while performing PerformanceInformationServiceImpl queryPerformanceInformation. Details:" + e.getMessage());
            return null;
        }
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PerformanceInformation> queryId(String[] id) {
		try(Session session = sessionFactory.openSession();) {
			List<PerformanceInformation> list;
			Query query = session.createQuery("from PerformanceInformation a where a.eventId IN (:alist)");
			list = query.setParameterList("alist", id).list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception occurred while performing PerformanceInformationServiceImpl queryId. Details:" + e.getMessage());
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PerformanceInformation> queryDateBetween(String eventId,Date startDate, Date endDate) {
		try(Session session = sessionFactory.openSession()) {
			List<PerformanceInformation> list ;
			Query query = session.createQuery("from PerformanceInformation a where a.eventId = :eventId and a.createTime BETWEEN :startDate and :endDate");
			list = query.setParameter("eventId",eventId).setParameter("startDate", startDate).setParameter("endDate",endDate).list();
			logger.info("PerformanceInformationServiceImpl queryDateBetween: list={}", list);
			return list;
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceInformationServiceImpl queryDateBetween. Details:" + e.getMessage());
			return null;
		}
	}



	@Override
	public List<PerformanceInformation> queryDateBetween(String resourceId, String name, String startTime, String endTime) {
		try(Session session = sessionFactory.openSession()) {
			String hql = "from PerformanceInformation a where 1=1 ";
			if (resourceId != null && !"".equals(resourceId)){
				hql += " and a.eventId = :resourceId";
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
    public List<Map<String,String>> queryMaxValueByBetweenDate(String sourceId, String name, String startTime, String endTime) {
        try(Session session = sessionFactory.openSession()) {
            List<Map<String,String>> mapList = new ArrayList<>();
            String hql = "select a.createTime,max(a.value) from PerformanceInformation a where 1=1 ";
            if (sourceId != null && !"".equals(sourceId)){
                hql += " and a.eventId = :resourceId";
            }
            if (name != null && !"".equals(name)){
                hql += " and a.name = :name ";
            }
            if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)){
                hql += " and a.createTime between :startTime and :endTime ";
            }
            hql += " group by a.createTime";
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
            Iterator it= query.list().iterator();
            while(it.hasNext()){
                Object[] res=(Object[]) it.next();
                Map<String,String> map = new HashMap<>();
                map.put("Time",res[0].toString());
                map.put("Max",res[1].toString());
                mapList.add(map);
            }
            logger.info("PerformanceInformationServiceImpl queryMaxValueByBetweenDate: maxValue={}", mapList.size());
            return mapList;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exception occurred while performing PerformanceInformationServiceImpl queryMaxValueByBetweenDate. Details:" + e.getMessage());
            return null;
        }
    }
}
