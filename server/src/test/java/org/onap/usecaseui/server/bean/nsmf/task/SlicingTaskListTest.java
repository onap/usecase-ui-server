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

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SlicingTaskListTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetSlicingTaskList() throws Exception {

        SlicingTaskList slicingTaskList = new SlicingTaskList();
        List<SlicingTaskInfo> slicingTaskInfoList = new ArrayList<>();

        SlicingTaskInfo slicingTaskInfo = new SlicingTaskInfo();
        slicingTaskInfo.setTaskId("03af656e-7602-47dd-97ee-0c7ca5c39cab");
        slicingTaskInfo.setName("SliceServiceTask");
        slicingTaskInfo.setStatus("Planning");
        slicingTaskInfo.setServiceType("embb");
        slicingTaskInfo.setServiceSnssai("01-010101");
        slicingTaskInfo.setCreateTime("2019-12-19T10:13:26.000+0000");
        slicingTaskInfoList.add(slicingTaskInfo);

        slicingTaskList.setSlicingTaskInfoList(slicingTaskInfoList);
        slicingTaskList.setRecordNumber(slicingTaskInfoList.size());

        slicingTaskList.getRecordNumber();
        slicingTaskList.getSlicingTaskInfoList();
    }
}
