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

import java.util.UUID;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.onap.usecaseui.server.bean.ActiveAlarmInfo;
import org.onap.usecaseui.server.service.AlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;


@Service("AlarmService")
@Transactional
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class AlarmServiceImpl implements AlarmService
{
    private static final Logger logger = LoggerFactory.getLogger(AlarmServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;
    
    public String hello()
    {
        return "Hello";
    }


    @Transactional
    public String saveActiveAlarmInfo(ActiveAlarmInfo acAlarmInfo)
    {
        try
        {
            if (null == acAlarmInfo)
            {
                logger.error("AlarmServiceImpl saveActiveAlarmInfo acAlarmInfo is null!");
            }
            logger.info("AlarmServiceImpl saveActiveAlarmInfo: acAlarmInfo={}", acAlarmInfo);
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();     
            acAlarmInfo.setId(UUID.randomUUID().toString());
            session.save(acAlarmInfo);
            tx.commit();
            session.flush();
            session.close();
        }
        catch (Exception e)
        {
            logger.error("Exception occurred while performing AlarmServiceImpl saveActiveAlarmInfo. Details:" + e.getMessage());
        }
        
        return acAlarmInfo.getId();
    }
    
}
