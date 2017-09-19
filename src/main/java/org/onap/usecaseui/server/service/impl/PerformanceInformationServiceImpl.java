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


import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.service.PerformanceInformationService;
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
		 try {
	            if (null == performanceInformation) {
	                logger.error("performanceInformation PerformanceInformation performanceInformation is null!");
	            }
	            logger.info("PerformanceInformationServiceImpl savePerformanceInformation: performanceInformation={}", performanceInformation);
	            Session session = sessionFactory.openSession();
	            Transaction tx = session.beginTransaction();     
	            session.save(performanceInformation);
	            tx.commit();
	            session.flush();
	            session.close();
	            return "1";
	        } catch (Exception e) {
	            logger.error("Exception occurred while performing PerformanceInformationServiceImpl performanceInformation. Details:" + e.getMessage());
	            return "0";
	        }
	        
	}

	@Override
	public String updatePerformanceInformation(PerformanceInformation performanceInformation) {
		try {
            if (null == performanceInformation) {
                logger.error("performanceInformation PerformanceInformation performanceInformation is null!");
            }
            logger.info("PerformanceInformationServiceImpl savePerformanceInformation: performanceInformation={}", performanceInformation);
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();     
            session.update(performanceInformation);
            tx.commit();
            session.flush();
            session.close();
            return "1";
        } catch (Exception e) {
            logger.error("Exception occurred while performing PerformanceInformationServiceImpl performanceInformation. Details:" + e.getMessage());
            return "0";
        }
	}


    
    
    
}
