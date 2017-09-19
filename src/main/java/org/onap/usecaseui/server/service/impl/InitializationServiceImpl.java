/*
 * 文 件 名:  InitializationServiceImpl.java
 * 版    权:  Raisecom Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  donghu
 * 修改时间:  2017年8月17日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package org.onap.usecaseui.server.service.impl;

import javax.transaction.Transactional;

import org.onap.usecaseui.server.service.InitializationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

/**
 * <系统初始化>
 * <功能详细描述>
 * @author  donghu
 * @version  [版本号, 2017年8月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("InitializationService")
@Transactional
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class InitializationServiceImpl implements InitializationService
{
    private static final Logger logger = LoggerFactory.getLogger(InitializationServiceImpl.class);
    /**{@inheritDoc}
     */
    public void initialize()
    {
        logger.info("InitializationServiceImpl initialize init...");
        
    }
    
}
