/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.event.simulator.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.carbon.event.executionplandelpoyer.ExecutionPlanDeployer;
import org.wso2.carbon.event.executionplandelpoyer.ExecutionPlanDto;
import org.wso2.carbon.event.executionplandelpoyer.StreamDefinitionDto;
import org.wso2.carbon.event.simulator.bean.FeedSimulationConfig;
import org.wso2.carbon.event.simulator.bean.FileStore;
import org.wso2.carbon.event.simulator.bean.StreamConfiguration;
import org.wso2.carbon.event.simulator.constants.EventSimulatorConstants;
import org.wso2.carbon.event.simulator.constants.RandomDataGeneratorConstants;
import org.wso2.carbon.event.simulator.csvFeedSimulation.CSVFileConfig;
import org.wso2.carbon.event.simulator.csvFeedSimulation.core.FileDto;
import org.wso2.carbon.event.simulator.exception.EventSimulationException;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.CustomBasedAttribute;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.PrimitiveBasedAttribute;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.PropertyBasedAttributeDto;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.RandomDataSimulationConfig;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.RegexBasedAttributeDto;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.StreamAttributeDto;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.utils.RandomDataGenerator;
import org.wso2.carbon.event.simulator.singleventsimulator.SingleEventSimulationConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * EventSimulatorParser is an util class used to
 * convert Json string into relevant event simulation configuration object
 */
public class EventSimulatorParser {
    private static final Log log = LogFactory.getLog(EventSimulatorParser.class);

    /*
    Initialize EventSimulatorParser
     */
    private EventSimulatorParser(){
    }

    /**
     *
     * Convert the RandomEventSimulationConfig string into RandomDataSimulationConfig Object
     * RandomRandomDataSimulationConfig can have one or more attribute simulation configuration.
     * these can be one of below types
     *    1.PRIMITIVEBASED : String/Integer/Float/Double/Boolean
     *    2.PROPERTYBASED  : this type indicates the type which generates meaning full data.
     *                       eg: If full name it generate meaning full name
     *    3.REGEXBASED     : this type indicates the type which generates data using given regex
     *    4.CUSTOMDATA     : this type indicates the type which generates data in given data list
     *
     * Initialize RandomDataSimulationConfig
     *
     * @param RandomEventSimulationConfig RandomEventSimulationConfiguration String
     * @return RandomDataSimulationConfig Object
     */
    private static RandomDataSimulationConfig randomDataSimulatorParser(String RandomEventSimulationConfig) {
        RandomDataSimulationConfig randomDataSimulationConfig = new RandomDataSimulationConfig();
        try {
            JSONObject jsonObject = new JSONObject(RandomEventSimulationConfig);
            ExecutionPlanDto executionPlanDto=ExecutionPlanDeployer.getExecutionPlanDeployer().getExecutionPlanDto();
            StreamDefinitionDto streamDefinitionDto=executionPlanDto.getInputStreamDtoMap().get(randomDataSimulationConfig.getStreamName());

            //set properties to RandomDataSimulationConfig
            randomDataSimulationConfig.setStreamName((String) jsonObject.get(EventSimulatorConstants.STREAM_NAME));
            randomDataSimulationConfig.setEvents(jsonObject.getInt(EventSimulatorConstants.EVENTS));
            randomDataSimulationConfig.setDelay(jsonObject.getInt(EventSimulatorConstants.DELAY));
            List<StreamAttributeDto> attributeSimulation = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray(EventSimulatorConstants.ATTRIBUTE_CONFIGURATION);
            if (jsonArray.length() != streamDefinitionDto.getStreamAttributeDtos().size()) {
                // TODO: 27/11/16 proper error message
                throw new EventSimulationException("Configuration for attribute is missing in "+ streamDefinitionDto.getStreamName() +
                        " : " + " No of attribute in stream " + streamDefinitionDto.getStreamAttributeDtos().size());
            }

            //convert each attribute simulation configuration as relevant objects

            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                if (!jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.RANDOMDATAGENERATORTYPE)) {
                    if (jsonArray.getJSONObject(i).getString(EventSimulatorConstants.RANDOMDATAGENERATORTYPE).compareTo(RandomDataGeneratorConstants.PROPERTY_BASED_ATTRIBUTE) == 0) {
                        if (!jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.PROPERTYBASEDATTRIBUTE_CATEGORY) && !jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.PROPERTYBASEDATTRIBUTE_PROPERTY)) {
                            PropertyBasedAttributeDto propertyBasedAttributeDto = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), PropertyBasedAttributeDto.class);
                            attributeSimulation.add(propertyBasedAttributeDto);
                        } else {
                            throw new EventSimulationException("Category and property should not be null value for " + RandomDataGeneratorConstants.PROPERTY_BASED_ATTRIBUTE);
                            //log.error("Category and property should not be null value for " + RandomDataGeneratorConstants.PROPERTY_BASED_ATTRIBUTE);
                        }

                    } else if (jsonArray.getJSONObject(i).getString(EventSimulatorConstants.RANDOMDATAGENERATORTYPE).compareTo(RandomDataGeneratorConstants.REGEX_BASED_ATTRIBUTE) == 0) {
                        if (!jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.REGEXBASEDATTRIBUTE_PATTERN)) {
                            RegexBasedAttributeDto regexBasedAttributeDto = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), RegexBasedAttributeDto.class);
                            RandomDataGenerator.validateRegularExpression(regexBasedAttributeDto.getPattern());
                            attributeSimulation.add(regexBasedAttributeDto);
                        } else {
                            throw new EventSimulationException("Pattern should not be null value for " + RandomDataGeneratorConstants.REGEX_BASED_ATTRIBUTE);
                            //log.error("Pattern should not be null value for " + RandomDataGeneratorConstants.REGEX_BASED_ATTRIBUTE);
                        }

                    } else if (jsonArray.getJSONObject(i).getString(EventSimulatorConstants.RANDOMDATAGENERATORTYPE).compareTo(RandomDataGeneratorConstants.PRIMITIVE_BASED_ATTRIBUTE) == 0) {
                        if (!jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.PRIMITIVEBASEDATTRIBUTE_MIN) && !jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.PRIMITIVEBASEDATTRIBUTE_MAX) && !jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.PRIMITIVEBASEDATTRIBUTE_LENGTH_DECIMAL)) {
                            PrimitiveBasedAttribute primitiveBasedAttribute = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), PrimitiveBasedAttribute.class);
                            attributeSimulation.add(primitiveBasedAttribute);
                        } else {
                            throw new EventSimulationException("min,max and length value should not be null value for " + RandomDataGeneratorConstants.PRIMITIVE_BASED_ATTRIBUTE);
                            // log.error("min,max and length value should not be null value for " + RandomDataGeneratorConstants.PRIMITIVE_BASED_ATTRIBUTE);
                        }
                    } else if (jsonArray.getJSONObject(i).getString(EventSimulatorConstants.RANDOMDATAGENERATORTYPE).compareTo(RandomDataGeneratorConstants.CUSTOM_DATA_BASED_ATTRIBUTE) == 0) {
                        if (!jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.CUSTOMDATABASEDATTRIBUTE_LIST)) {
                            CustomBasedAttribute customBasedAttribute = new CustomBasedAttribute();
                            customBasedAttribute.setType(jsonArray.getJSONObject(i).getString(EventSimulatorConstants.RANDOMDATAGENERATORTYPE));
                            customBasedAttribute.setCustomData(jsonArray.getJSONObject(i).getString(EventSimulatorConstants.CUSTOMDATABASEDATTRIBUTE_LIST));
                            attributeSimulation.add(customBasedAttribute);
                        } else {
                            throw new EventSimulationException("CustomDataList shold not be null for " + RandomDataGeneratorConstants.CUSTOM_DATA_BASED_ATTRIBUTE);
                            // log.error("CustomDataList should not be null for " + RandomDataGeneratorConstants.CUSTOM_DATA_BASED_ATTRIBUTE);
                        }
                    }
                } else {
                    throw new EventSimulationException("Attribute Simulation type is required : " + RandomDataGeneratorConstants.PROPERTY_BASED_ATTRIBUTE + "/" +
                            RandomDataGeneratorConstants.REGEX_BASED_ATTRIBUTE + "/" + RandomDataGeneratorConstants.PRIMITIVE_BASED_ATTRIBUTE +
                            "/" + RandomDataGeneratorConstants.CUSTOM_DATA_BASED_ATTRIBUTE);
//                    log.error("Attribute Simulation type is required : " + RandomDataGeneratorConstants.PROPERTY_BASED_ATTRIBUTE + "/" +
//                            RandomDataGeneratorConstants.REGEX_BASED_ATTRIBUTE + "/" + RandomDataGeneratorConstants.PRIMITIVE_BASED_ATTRIBUTE +
//                            "/" + RandomDataGeneratorConstants.CUSTOM_DATA_BASED_ATTRIBUTE);

                }
            }
            randomDataSimulationConfig.setAttributeSimulation(attributeSimulation);
        } catch (JSONException e) {
            log.error(e.getMessage());
        }
        return randomDataSimulationConfig;
    }


    /**
     * Convert the singleEventSimulationConfigurationString string into SingleEventSimulationConfig Object
     * Initialize SingleEventSimulationConfig
     * @param singleEventSimulationConfigurationString singleEventSimulationConfigurationString String
     * @return SingleEventSimulationConfig Object
     * @throws IOException throw exception if any exception occurred during mapping
     */
    public static SingleEventSimulationConfig singleEventSimulatorParser(String singleEventSimulationConfigurationString) throws IOException {
        //check whether the execution plan is deployed
        if (ExecutionPlanDeployer.getExecutionPlanDeployer() == null) {
            throw new EventSimulationException("Execution Plan is not deployed");
        }
        SingleEventSimulationConfig singleEventSimulationConfig;
        ObjectMapper mapper = new ObjectMapper();
            //Convert the singleEventSimulationConfigurationString string into SingleEventSimulationConfig Object
            singleEventSimulationConfig = mapper.readValue(singleEventSimulationConfigurationString, SingleEventSimulationConfig.class);

            ExecutionPlanDto executionPlanDto = ExecutionPlanDeployer.getExecutionPlanDeployer().getExecutionPlanDto();
            StreamDefinitionDto streamDefinitionDto = executionPlanDto.getInputStreamDtoMap().get(singleEventSimulationConfig.getStreamName());
            if (singleEventSimulationConfig.getAttributeValues().size() != streamDefinitionDto.getStreamAttributeDtos().size()) {
                log.error("No of Attribute values is not equal to attribute size in " +
                        singleEventSimulationConfig.getStreamName() + " : Required attribute size " + streamDefinitionDto.getStreamAttributeDtos().size());
                throw new EventSimulationException("No of Attribute value in not equal to attribute size in Input Stream : No of Attributes in " +
                        singleEventSimulationConfig.getStreamName() + " is " + streamDefinitionDto.getStreamAttributeDtos().size());
            }
        return singleEventSimulationConfig;
    }

    /**
     * Convert the csvFileDetails string into CSVFileConfig Object
     *
     * Initialize CSVFileConfig
     * Initialize FileStore
     *
     * @param csvFileDetails csvFileDetails String
     * @return CSVFileConfig Object
     */
    public static CSVFileConfig fileEventSimulatorParser(String csvFileDetails) {
        CSVFileConfig csvFileConfig = new CSVFileConfig();
        FileStore fileStore=FileStore.getFileStore();
        try {
            JSONObject jsonObject = new JSONObject(csvFileDetails);
            csvFileConfig.setStreamName((String) jsonObject.get(EventSimulatorConstants.STREAM_NAME));
            csvFileConfig.setFileName((String) jsonObject.get(EventSimulatorConstants.FILE_NAME));
            //get the fileDto from FileStore if file exist and set this value.
            FileDto fileDto;
            if(fileStore.checkExists(csvFileConfig.getFileName())){
                 fileDto= fileStore.getFileInfoMap().get(csvFileConfig.getFileName());
            }else {
                log.error("File is not Exist");
                throw new EventSimulationException("File is not Exist");
            }
            csvFileConfig.setFileDto(fileDto);
            csvFileConfig.setDelimiter((String) jsonObject.get(EventSimulatorConstants.DELIMITER));
            csvFileConfig.setDelay(jsonObject.getInt(EventSimulatorConstants.DELAY));
        } catch (JSONException e) {
            log.error(e.getMessage());
        }
        return csvFileConfig;
    }


    /**
     * Convert the feedSimulationDetails string into FeedSimulationConfig Object
     * Three types of feed simulation are applicable for an input stream
     * These types are
     *       1. CSV file feed simulation : Simulate using CSV File
     *       2. Database Simulation : Simulate using Database source
     *       3. Random data simulation : Simulate using Generated random Data
     *
     * Initialize FeedSimulationConfig
     * @param feedSimulationDetails feedSimulationDetails
     * @return FeedSimulationConfig Object
     */
    public static FeedSimulationConfig feedSimulationConfigParser(String feedSimulationDetails){
        //check whether the execution plan is deployed
        if (ExecutionPlanDeployer.getExecutionPlanDeployer() == null) {
            throw new EventSimulationException("Execution Plan is not deployed");
        }

        FeedSimulationConfig feedSimulationConfig=new FeedSimulationConfig();
        try{
            JSONObject jsonObject = new JSONObject(feedSimulationDetails);
            if(jsonObject.getBoolean(EventSimulatorConstants.ORDER_BY_TIMESTAMP)) {
                feedSimulationConfig.setOrderByTimeStamp(jsonObject.getBoolean(EventSimulatorConstants.ORDER_BY_TIMESTAMP));
            }
            List<StreamConfiguration> streamConfigurationList=new ArrayList<>();

            //check the simulation type for a given stream and convert the string to relevant configuration object
            //            1. CSV file feed simulation : Simulate using CSV File
            //            2. Database Simulation : Simulate using Database source
            //            3. Random data simulation : Simulate using Generated random Data

            JSONArray jsonArray = jsonObject.getJSONArray(EventSimulatorConstants.STREAM_CONFIGURATION);
            for(int i=0;i<jsonArray.length();i++){
                if (!jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.SIMULATION_TYPE)){
                    String feedSimulationType=jsonArray.getJSONObject(i).getString(EventSimulatorConstants.SIMULATION_TYPE);
                    switch (feedSimulationType){
                        case EventSimulatorConstants.RANDOM_DATA_SIMULATION:
                            RandomDataSimulationConfig randomDataSimulationConfig=randomDataSimulatorParser(String.valueOf(jsonArray.getJSONObject(i)));
                            randomDataSimulationConfig.setSimulationType(EventSimulatorConstants.RANDOM_DATA_SIMULATION);
                            streamConfigurationList.add(randomDataSimulationConfig);
                            break;
                        case EventSimulatorConstants.FILE_FEED_SIMULATION:
                            CSVFileConfig csvFileConfig=EventSimulatorParser.fileEventSimulatorParser(String.valueOf(jsonArray.getJSONObject(i)));
                            csvFileConfig.setSimulationType(EventSimulatorConstants.FILE_FEED_SIMULATION);
                            //streamConfigurationListMap.put(csvFileConfig.getStreamName(),csvFileConfig);
                            streamConfigurationList.add(csvFileConfig);
                            break;
                        default:
                            log.error(feedSimulationType + "is not available");
                            throw new EventSimulationException(feedSimulationType + "is not available");
                    }
                }else{
                    log.error(EventSimulatorConstants.SIMULATION_TYPE + "is null");
                    throw new EventSimulationException(EventSimulatorConstants.SIMULATION_TYPE + "is null");
                }
            }
           feedSimulationConfig.setStreamConfigurationList(streamConfigurationList);
        }catch (JSONException e){
            log.error(e.getMessage());
        }

       return feedSimulationConfig;
    }

}
