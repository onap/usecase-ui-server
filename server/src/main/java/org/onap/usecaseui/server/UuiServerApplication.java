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

import org.onap.usecaseui.server.util.DmaapSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.unit.DataSize;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = "org.onap.usecaseui.server")
@EnableAutoConfiguration(exclude={JpaRepositoriesAutoConfiguration.class})
public class UuiServerApplication {
    public static DmaapSubscriber dmaapSubscriber;

    @Autowired
    public void setDatastore(DmaapSubscriber dmaapSubscriber) {
        UuiServerApplication.dmaapSubscriber = dmaapSubscriber;
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("512MB"));
        factory.setMaxRequestSize(DataSize.parse("512MB"));
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(UuiServerApplication.class, args);
        dmaapSubscriber.run();
    }
}
