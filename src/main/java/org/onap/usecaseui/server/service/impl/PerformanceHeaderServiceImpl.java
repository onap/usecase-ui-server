
package org.onap.usecaseui.server.service.impl;


import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.service.PerformanceHeaderService;
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
		 try{
	            if (null == performanceHeder){
	                logger.error("PerformanceHeaderServiceImpl savePerformanceHeader performanceHeder is null!");
	            }
	            logger.info("PerformanceHeaderServiceImpl savePerformanceHeader: performanceHeder={}", performanceHeder);
	            Session session = sessionFactory.openSession();
	            Transaction tx = session.beginTransaction();     
	            session.save(performanceHeder);
	            tx.commit();
	            session.flush();
	            session.close();
	            return "1";
	        } catch (Exception e) {
	            logger.error("Exception occurred while performing PerformanceHeaderServiceImpl savePerformanceHeader. Details:" + e.getMessage());
	            return "0";
	        }
	        
	}

	@Override
	public String updatePerformanceHeader(PerformanceHeader performanceHeder) {
		try{
            if (null == performanceHeder){
                logger.error("PerformanceHeaderServiceImpl savePerformanceHeader performanceHeder is null!");
            }
            logger.info("PerformanceHeaderServiceImpl savePerformanceHeader: performanceHeder={}", performanceHeder);
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();     
            session.update(performanceHeder);
            tx.commit();
            session.flush();
            session.close();
            return "1";
        } catch (Exception e) {
            logger.error("Exception occurred while performing PerformanceHeaderServiceImpl savePerformanceHeader. Details:" + e.getMessage());
            return "0";
        }
	}


    
    
}
