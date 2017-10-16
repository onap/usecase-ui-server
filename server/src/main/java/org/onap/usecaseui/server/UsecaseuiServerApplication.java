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
package org.onap.usecaseui.server;

import org.onap.msb.sdk.discovery.common.RouteException;
import org.onap.msb.sdk.discovery.entity.MicroServiceInfo;
import org.onap.msb.sdk.discovery.entity.Node;
import org.onap.msb.sdk.httpclient.msb.MSBServiceClient;
import org.onap.usecaseui.server.util.RestfulServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@ComponentScan(basePackages = "org.onap.usecaseui.server")
public class UsecaseuiServerApplication {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    
	public static void main(String[] args) {
		SpringApplication.run(UsecaseuiServerApplication.class, args);
        String msbUrl = RestfulServices.getMsbAddress();
        if (msbUrl.contains(":")) {
            String[] ipAndPort = msbUrl.split(":");
            MSBServiceClient msbClient = new MSBServiceClient(ipAndPort[0], Integer.parseInt(ipAndPort[1]));

            MicroServiceInfo msinfo = new MicroServiceInfo();
            msinfo.setServiceName("usecase-ui-server");
            msinfo.setVersion("v1");
            msinfo.setUrl("/api/usecaseui/server/v1");
            msinfo.setProtocol("REST");
            msinfo.setVisualRange("0|1");

            try {
                Set<Node> nodes = new HashSet<>();
                Node node1 = new Node();
                node1.setIp(InetAddress.getLocalHost().getHostAddress());
                node1.setPort("8082");
                nodes.add(node1);
                msinfo.setNodes(nodes);
                msbClient.registerMicroServiceInfo(msinfo, false);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (RouteException e) {
                e.printStackTrace();
            }
        }
	}
	
}
