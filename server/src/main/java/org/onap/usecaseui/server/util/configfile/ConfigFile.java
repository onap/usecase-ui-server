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

package org.onap.usecaseui.server.util.configfile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.Model;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.ModelConfig;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.ModelInfor;
//import org.onap.usecaseui.server.service.impl.DataMonitorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigFile {

    private static final Logger logger = LoggerFactory.getLogger(ConfigFile.class);

    public static ModelConfig readFile()
    {
        JSONParser parser = new JSONParser();
        String jsonPath="/home/root1/Desktop/modelconfig.json";
        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();

        try {

            Object object = parser.parse(new FileReader(jsonPath));
            //System.out.println(object.toString());
            ModelConfig modelInformation = mapper.readValue(object.toString(), new TypeReference<ModelConfig>() {
            });

            return modelInformation;
        }catch (ParseException | IOException ex)
        {
            logger.error("Exception occured while reading configuration file:"+ex);
            return null;
        }
    }
    public static Map<String, Model> readConfigToMap(ModelConfig modelConfig)
    {

        //ModelConfig modelConfig = readFile();
        //Map<String, Model> modelinfo = modelConfig.getModels().stream()
           //     .collect(Collectors.toMap(ModelInfor::getModelType, ModelInfor::getModel));
        return null;
    }
}
