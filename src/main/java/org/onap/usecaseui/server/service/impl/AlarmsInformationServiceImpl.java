
package org.onap.usecaseui.server.service.impl;


import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.service.AlarmsInformationService;
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
	            logger.info("AlarmsHeaderServiceImpl saveAlarmsInformation: alarmsInformation={}", alarmsInformation);
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
            logger.info("AlarmsHeaderServiceImpl saveAlarmsInformation: alarmsInformation={}", alarmsInformation);
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();     
            session.update(alarmsInformation);
            tx.commit();
            session.flush();
            session.close();
            return "1";
        } catch (Exception e) {
            logger.error("Exception occurred while performing AlarmsInformationServiceImpl saveAlarmsInformation. Details:" + e.getMessage());
            return "0";
        }
	}

    
    
    
}
