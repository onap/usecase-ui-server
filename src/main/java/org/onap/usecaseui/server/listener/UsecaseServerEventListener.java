/*
 * 文 件 名:  UsecaseServerEventListener.java
 * 版    权:  Raisecom Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  donghu
 * 修改时间:  2017年8月17日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package org.onap.usecaseui.server.listener;

import org.onap.usecaseui.server.service.InitializationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * @author  donghu
 * @version  [版本号, 2017年8月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UsecaseServerEventListener implements ApplicationListener<ApplicationReadyEvent>
{
    private static final Logger logger = LoggerFactory.getLogger(UsecaseServerEventListener.class);
    
    @Autowired
    InitializationService initializationService;
    
    public void onApplicationEvent(ApplicationReadyEvent arg0)
    {
        initializationService.initialize();
    }
    
}
