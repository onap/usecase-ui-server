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

import org.onap.usecaseui.server.service.InitializationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;


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
