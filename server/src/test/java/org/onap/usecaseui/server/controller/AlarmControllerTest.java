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
package org.onap.usecaseui.server.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.UsecaseuiServerApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UsecaseuiServerApplication.class)
@WebAppConfiguration
public class AlarmControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test1() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/alarm/1/100")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void test2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/alarm/1/100/null/null/null/null/null/null")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        mvc.perform(MockMvcRequestBuilders.get("/alarm/1/100/502fe15c-aa07-ed26-3f87-4d5c1784bc5b/management-server-backup/High/null/null/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        mvc.perform(MockMvcRequestBuilders.get("/alarm/1/100/502fe15c-aa07-ed26-3f87-4d5c1784bc5b/management-server-backup/High/123/456/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void test3() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/alarm/statusCount")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void test4() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/alarm/sourceId")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void test5() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/alarm/diagram")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("sourceId","").param("startTime","2017-10-1 16:34")
                .param("endTime","2017-12-5 0:0").param("showMode","auto")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        mvc.perform(MockMvcRequestBuilders.post("/alarm/diagram")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("sourceId","").param("startTime","2017-10-1 16:34")
                .param("endTime","2017-12-5 0:0").param("showMode","year")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        mvc.perform(MockMvcRequestBuilders.post("/alarm/diagram")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("sourceId","").param("startTime","2017-10-1 16:34")
                .param("endTime","2017-12-5 0:0").param("showMode","month")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        mvc.perform(MockMvcRequestBuilders.post("/alarm/diagram")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("sourceId","").param("startTime","2017-10-1 16:34")
                .param("endTime","2017-12-5 0:0").param("showMode","day")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        mvc.perform(MockMvcRequestBuilders.post("/alarm/diagram")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("sourceId","").param("startTime","2017-10-1 16:34")
                .param("endTime","2017-12-5 0:0").param("showMode","hour")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        mvc.perform(MockMvcRequestBuilders.post("/alarm/diagram")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("sourceId","").param("startTime","2017-10-1 16:34")
                .param("endTime","2017-12-5 0:0").param("showMode","minute")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}
