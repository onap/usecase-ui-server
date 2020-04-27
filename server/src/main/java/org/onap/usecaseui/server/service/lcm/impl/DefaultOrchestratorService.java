/**
 * Copyright (C) 2019 Verizon. All Rights Reserved.
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
package org.onap.usecaseui.server.service.lcm.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.onap.usecaseui.server.service.lcm.OrchestratorService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.*;
import org.onap.usecaseui.server.service.lcm.domain.aai.exceptions.AAIException;
import org.onap.usecaseui.server.util.RestfulServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import retrofit2.Response;

@Service("OrchestratorService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class DefaultOrchestratorService implements OrchestratorService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultOrchestratorService.class);

    private AAIService aaiService;

    public DefaultOrchestratorService() {
        this(RestfulServices.create(AAIService.class));
    }

    public DefaultOrchestratorService(AAIService aaiService) {
        this.aaiService = aaiService;
    }

    @Override
    public List<AAIEsrNfvo> listOrchestrator() {
        try {
            Response<AAIOrchestratorRsp> response = this.aaiService.listOrchestrator().execute();
            if (response.isSuccessful()) {
                List<AAIEsrNfvo> nfvoList = response.body().getEsrNfvo();
                for(AAIEsrNfvo nfvo: nfvoList){
                    String nfvoId = nfvo.getNfvoId();
                    Response<AAISingleOrchestratorRsp> response_orch =
                        this.aaiService.getOrchestrator(nfvoId).execute();
                    EsrSystemInfoList esrSystemInfoList = response_orch.body().getEsrSystemInfoList();
                    List<EsrSystemInfo> esrSystemInfo = esrSystemInfoList.getEsrSystemInfo();
                    nfvo.setName(esrSystemInfo.get(0).getSystemName());

                }
                return nfvoList;
            } else {
                logger.info(String.format("Can not get orchestrators[code=%s, message=%s]", response.code(), response.message()));
                return Collections.emptyList();
            }
        } catch (IOException e) {
            logger.error("list orchestrators occur exception");
            throw new AAIException("AAI is not available.", e);
        }
    }

}
