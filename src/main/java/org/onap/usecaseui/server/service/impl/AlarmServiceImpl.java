/*
 * 文 件 名:  AlarmServiceImpl.java
 * 版    权:  Raisecom Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  donghu
 * 修改时间:  2017年8月17日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
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

/**
 * <告警接口实现类>
 * <功能详细描述>
 * @author  donghu
 * @version  [版本号, 2017年8月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
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

    /**
     * <保存活跃告警信息>
     * <功能详细描述>
     * @param acAlarmInfo 活跃告警对象
     * @return 对象id
     * @see [类、类#方法、类#成员]
     */
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
