
package org.onap.usecaseui.server.service.impl;


import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
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

	@Override
	public String saveAlarmsHeader(AlarmsHeader alarmsHeader) {
		 try{
	            if (null == alarmsHeader) {
	                logger.error("AlarmsHeaderServiceImpl saveAlarmsHeader alarmsHeader is null!");
	            }
	            logger.info("AlarmsHeaderServiceImpl saveAlarmsHeader: alarmsHeader={}", alarmsHeader);
	            Session session = sessionFactory.openSession();
	            Transaction tx = session.beginTransaction();     
	            session.save(alarmsHeader);
	            tx.commit();
	            session.flush();
	            session.close();
	            return "1";
	        } catch (Exception e) {
	            logger.error("Exception occurred while performing AlarmsHeaderServiceImpl saveActiveAlarmInfo. Details:" + e.getMessage());
	            return "0";
	        }
	        
	}

	@Override
	public String updateAlarmsHeader(AlarmsHeader alarmsHeader) {
		try{
            if (null == alarmsHeader){
                logger.error("AlarmsHeaderServiceImpl saveAlarmsHeader alarmsHeader is null!");
            }
            logger.info("AlarmsHeaderServiceImpl saveAlarmsHeader: alarmsHeader={}", alarmsHeader);
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();     
            session.update(alarmsHeader);
            tx.commit();
            session.flush();
            session.close();
            return "1";
        } catch (Exception e) {
            logger.error("Exception occurred while performing AlarmsHeaderServiceImpl saveActiveAlarmInfo. Details:" + e.getMessage());
            return "0";
        }
	}

	@Override
	public List<AlarmsHeader> queryAlarmsHeader(AlarmsHeader alarmsHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	
    
    
    
}
