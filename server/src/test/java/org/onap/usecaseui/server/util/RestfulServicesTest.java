/**
 * Copyright 2016-2017 ZTE Corporation.
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
package org.onap.usecaseui.server.util;

import org.junit.Assert;
import org.junit.Test;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;

public class RestfulServicesTest {
    @Test
    public void testCreateServiceImpl() throws Exception {
        Object aaiService = createService(AAIService.class);

        Assert.assertTrue(aaiService instanceof AAIService);
    }

    private <T> Object createService(Class<T> clazz) {
        return RestfulServices.create(clazz);
    }
}