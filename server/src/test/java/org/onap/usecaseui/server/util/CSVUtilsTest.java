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
package org.onap.usecaseui.server.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVUtilsTest {

    @Test
    public void CSVTest(){
        String[] headers = new String[]{"name","age","birthday"};
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"Tom","15","2001-1-12"});
        data.add(new String[]{"Jerry","18","2005-1-12"});
        data.add(new String[]{"John","20","2011-1-12"});
        String csvPath = "csvFiles/data.csv";
        CSVUtils.writeCsv(headers,data,csvPath);
    }

    @Test
    public void CSVRead(){
        String[] headers = new String[]{"name","age","birthday"};
        String csvPath = "csvFiles/data.csv";
        try {
            CSVUtils.readCSV(csvPath,headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
