/*
 * Copyright (C) 2020 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.conf.intent;

import org.onap.usecaseui.server.service.intent.IntentInstanceService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.IOException;

@Configuration
@EnableScheduling
public class IntentScheduleTask {


    @Resource(name = "IntentInstanceService")
    private IntentInstanceService intentInstanceService;

    @Scheduled(cron = "0/20 * * * * ?")
    public void getIntentInstanceCompleteness() {
        intentInstanceService.getIntentInstanceProgress();
    }
    @Scheduled(cron = "0/20 * * * * ?")
    public void getIntentInstanceBandwidth() throws IOException {
        intentInstanceService.getIntentInstanceBandwidth();
    }
}
