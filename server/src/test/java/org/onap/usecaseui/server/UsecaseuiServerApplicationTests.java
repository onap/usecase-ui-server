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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsecaseuiServerApplicationTests {

	@Test
	public void contextLoads() {
        String data = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/topologyD_data.json"));
            String tmpStr = "";
            while ((tmpStr=br.readLine()) != null){
                data += tmpStr;
            }
            br.close();
            //System.out.println(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectMapper om = new ObjectMapper();
        try {
            Map<String,Object> map = om.readValue(data, Map.class);
            List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("services");
            list.forEach( i -> {
                i.forEach( (k,v) ->{
                    System.out.println(k+"_"+v);
                } );
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
