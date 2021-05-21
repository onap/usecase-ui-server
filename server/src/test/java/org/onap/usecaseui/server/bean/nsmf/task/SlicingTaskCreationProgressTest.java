/*
 * Copyright (C) 2019 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.bean.nsmf.task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SlicingTaskCreationProgressTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetSlicingTaskCreationProgress() throws Exception {

        SlicingTaskCreationProgress slicingTaskCreationProgress = new SlicingTaskCreationProgress();
        slicingTaskCreationProgress.setCnProgress("66");
        slicingTaskCreationProgress.setTnProgress("66");
        slicingTaskCreationProgress.setAnProgress("66");
        slicingTaskCreationProgress.setCnStatus("error");
        slicingTaskCreationProgress.setTnStatus("error");
        slicingTaskCreationProgress.setAnStatus("error");
        slicingTaskCreationProgress.setAnStatusDescription("error");
        slicingTaskCreationProgress.setTnStatusDescription("error");
        slicingTaskCreationProgress.setCnStatusDescription("error");

        slicingTaskCreationProgress.getAnProgress();
        slicingTaskCreationProgress.getCnProgress();
        slicingTaskCreationProgress.getTnProgress();
        slicingTaskCreationProgress.getAnStatus();
        slicingTaskCreationProgress.getCnStatus();
        slicingTaskCreationProgress.getTnStatus();
        slicingTaskCreationProgress.getAnStatusDescription();
        slicingTaskCreationProgress.getTnStatusDescription();
        slicingTaskCreationProgress.getCnStatusDescription();
    }
}
