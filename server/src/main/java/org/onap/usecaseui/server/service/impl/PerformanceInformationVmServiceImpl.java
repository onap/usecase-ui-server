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
import org.onap.usecaseui.server.bean.PerformanceInformationVm;
import org.onap.usecaseui.server.service.PerformanceInformationVmService;
import org.onap.usecaseui.server.util.DateUtils;
import org.onap.usecaseui.server.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;


@Service("PerformanceInformationVmService")
@Transactional
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class PerformanceInformationVmServiceImpl implements PerformanceInformationVmService {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceInformationVmServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;


	@Override
	public List<PerformanceInformationVm> getAllPerformanceInformationByeventId(String eventId) {
		try (Session session = sessionFactory.openSession()){
			String string = "from PerformanceInformationVm a where 1=1 and a.eventId=:eventId";
			Query query = session.createQuery(string);
			query.setString("eventId",eventId);
			List<PerformanceInformationVm> list = query.list();
			session.flush();
			return list;
		}catch (Exception e){
			logger.error("exception occurred while performing PerformanceInformationVmServiceImpl queryDateBetween. LIST:" + e.getMessage());

			return null;
		}



	}



	@Override
	public String savePerformanceInformationVm(PerformanceInformationVm performanceInformationVm) {
		 try(Session session = sessionFactory.openSession();) {
	            if (null == performanceInformationVm) {
	                logger.error("performanceInformationVm savePerformanceInformationVm performanceInformationVm is null!");
	            }
	            logger.info("PerformanceInformationVmServiceImpl savePerformanceInformationVm: performanceInformationVm={}", performanceInformationVm);
	            Transaction tx = session.beginTransaction();     
	            session.save(performanceInformationVm);
	            tx.commit();
	            session.flush();
	            return "1";
	        } catch (Exception e) {
	            logger.error("exception occurred while performing PerformanceInformationVmServiceImpl savePerformanceInformationVm. Details:" + e.getMessage());
	            return "0";
	        }
	        
	}


	@Override
	public String updatePerformanceInformationVm(PerformanceInformationVm performanceInformationVm) {
		try(Session session = sessionFactory.openSession();) {
            if (null == performanceInformationVm) {
                logger.error("performanceInformationVm updatePerformanceInformationVm performanceInformationVm is null!");
            }
            logger.info("PerformanceInformationVmServiceImpl updatePerformanceInformationVm: performanceInformationVm={}", performanceInformationVm);
            Transaction tx = session.beginTransaction();     
            session.update(performanceInformationVm);
            tx.commit();
            session.flush();
            return "1";
        } catch (Exception e) {
            logger.error("exception occurred while performing PerformanceInformationVmServiceImpl updatePerformanceInformationVm. Details:" + e.getMessage());
            return "0";
        }
	}


	public int getAllCount(PerformanceInformationVm performanceInformationVm, int currentPage, int pageSize) {
		try(Session session = sessionFactory.openSession();){
			StringBuffer hql = new StringBuffer("select count(*) from PerformanceInformationVm a where 1=1");
			if (null == performanceInformationVm) {
                //logger.error("AlarmsInformationServiceImpl getAllCount performanceInformationVm is null!");
            }else {
            	if(null!=performanceInformationVm.getName()) {
                	String ver=performanceInformationVm.getName();
                	hql.append(" and a.name like '%"+ver+"%'");
                }
            	if(null!=performanceInformationVm.getValue()) {
                	String ver=performanceInformationVm.getValue();
                	hql.append(" and a.value like '%"+ver+"%'");
                }
            	if(null!=performanceInformationVm.getEventId()) {
                	String ver=performanceInformationVm.getEventId();
                	hql.append(" and a.eventId = '"+ver+"'");
                }
            	if(null!=performanceInformationVm.getCreateTime()) {
                	Date ver =performanceInformationVm.getCreateTime();
                	hql.append(" and a.createTime > '%"+ver+"%'");
                }
            	if(null!=performanceInformationVm.getUpdateTime()) {
                	Date ver =performanceInformationVm.getUpdateTime();
                	hql.append(" and a.updateTime like '%"+ver+"%'");
                }
            }
            long q=(long)session.createQuery(hql.toString()).uniqueResult();
            session.flush();
            return (int)q;
        } catch (Exception e) {
            logger.error("exception occurred while performing PerformanceInformationVmServiceImpl getAllCount. Details:" + e.getMessage());
            return 0;
        }
	}

	@SuppressWarnings("unchecked")	
	@Override
	public Page<PerformanceInformationVm> queryPerformanceInformationVm(PerformanceInformationVm performanceInformationVm,
			int currentPage, int pageSize) {
		Page<PerformanceInformationVm> page = new Page<PerformanceInformationVm>();
		int allRow =this.getAllCount(performanceInformationVm,currentPage,pageSize);
		int offset = page.countOffset(currentPage, pageSize);
		
		try(Session session = sessionFactory.openSession()){
			StringBuffer hql =new StringBuffer("from PerformanceInformationVm a where 1=1 ");
            if (null == performanceInformationVm) {

            }else {
            	if(null!=performanceInformationVm.getName()) {
                	String ver=performanceInformationVm.getName();
                	hql.append(" and a.name like '%"+ver+"%'");
                }
            	if(null!=performanceInformationVm.getValue()) {
                	String ver=performanceInformationVm.getValue();
                	hql.append(" and a.value like '%"+ver+"%'");
                }
            	if(null!=performanceInformationVm.getEventId()) {
                	String ver=performanceInformationVm.getEventId();
                	hql.append(" and a.eventId = '"+ver+"'");
                }
            	if(null!=performanceInformationVm.getCreateTime()) {
                	Date ver =performanceInformationVm.getCreateTime();
                	hql.append(" and a.createTime > '%"+ver+"%'");
                }
            	if(null!=performanceInformationVm.getUpdateTime()) {
                	Date ver =performanceInformationVm.getUpdateTime();
                	hql.append(" and a.updateTime like '%"+ver+"%'");
                }
            } 
            logger.info("PerformanceInformationVmServiceImpl queryPerformanceInformationVm: performanceInformationVm={}", performanceInformationVm);
            Query query = session.createQuery(hql.toString());
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);
            List<PerformanceInformationVm> list= query.list();

            page.setPageNo(currentPage);
            page.setPageSize(pageSize);
            page.setTotalRecords(allRow);
            page.setList(list);
            session.flush();
            return page;
        } catch (Exception e) {
            logger.error("exception occurred while performing PerformanceInformationVmServiceImpl queryPerformanceInformationVm. Details:" + e.getMessage());
            return null;
        }
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PerformanceInformationVm> queryId(String[] id) {
		try(Session session = sessionFactory.openSession();) {
			List<PerformanceInformationVm> list;
			Query query = session.createQuery("from PerformanceInformationVm a where a.eventId IN (:alist)");
			list = query.setParameterList("alist", id).list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception occurred while performing PerformanceInformationVmServiceImpl queryId. Details:" + e.getMessage());
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PerformanceInformationVm> queryDateBetween(String eventId,Date startDate, Date endDate) {
		try(Session session = sessionFactory.openSession()) {
			List<PerformanceInformationVm> list ;
			Query query = session.createQuery("from PerformanceInformationVm a where a.eventId = :eventId and a.createTime BETWEEN :startDate and :endDate");
			list = query.setParameter("eventId",eventId).setParameter("startDate", startDate).setParameter("endDate",endDate).list();
			logger.info("PerformanceInformationVmServiceImpl queryDateBetween: list={}", list);
			return list;
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceInformationVmServiceImpl queryDateBetween. Details:" + e.getMessage());
			return null;
		}
	}



	@Override
	public List<PerformanceInformationVm> queryDateBetween(String resourceId, String name, String startTime, String endTime) {
		try(Session session = sessionFactory.openSession()) {
			String hql = "from PerformanceInformationVm a where 1=1 ";
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
			logger.info("PerformanceInformationVmServiceImpl queryDateBetween: list={}", query.list());
			return query.list();
		} catch (Exception e) {
			logger.error("exception occurred while performing PerformanceInformationVmServiceImpl queryDateBetween. Details:" + e.getMessage());
			return null;
		}
	}

    @Override
    public List<Map<String,String>> queryMaxValueByBetweenDate(String sourceId, String name, String startTime, String endTime) {
        try(Session session = sessionFactory.openSession()) {
            List<Map<String,String>> mapList = new ArrayList<>();
            String hql = "select a.createTime,max(a.value) from PerformanceInformationVm a where 1=1 ";
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
            logger.info("PerformanceInformationVmServiceImpl queryMaxValueByBetweenDate: maxValue={}", mapList.size());
            return mapList;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exception occurred while performing PerformanceInformationVmServiceImpl queryMaxValueByBetweenDate. Details:" + e.getMessage());
            return null;
        }
    }
}
