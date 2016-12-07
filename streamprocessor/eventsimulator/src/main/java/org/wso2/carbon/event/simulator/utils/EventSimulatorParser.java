package org.wso2.carbon.event.simulator.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.carbon.event.querydeployer.core.QueryDeployer;
import org.wso2.carbon.event.simulator.core.constants.RandomDataGeneratorConstants;
import org.wso2.carbon.event.simulator.core.csvFeedSimulation.CSVFileConfig;
import org.wso2.carbon.event.simulator.core.csvFeedSimulation.core.FileDto;
import org.wso2.carbon.event.simulator.core.exception.EventSimulationException;
import org.wso2.carbon.event.simulator.core.randomdatafeedsimulation.bean.CustomBasedAttribute;
import org.wso2.carbon.event.simulator.core.randomdatafeedsimulation.bean.PrimitiveBasedAttribute;
import org.wso2.carbon.event.simulator.core.randomdatafeedsimulation.bean.PropertyBasedAttributeDto;
import org.wso2.carbon.event.simulator.core.randomdatafeedsimulation.bean.RandomDataSimulationConfig;
import org.wso2.carbon.event.simulator.core.randomdatafeedsimulation.bean.RegexBasedAttributeDto;
import org.wso2.carbon.event.simulator.core.randomdatafeedsimulation.bean.StreamAttributeDto;
import org.wso2.carbon.event.simulator.core.randomdatafeedsimulation.utils.RandomDataGenerator;
import org.wso2.carbon.event.simulator.core.singleventsimulator.SingleEventSimulationConfig;
import org.wso2.msf4j.formparam.FileInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathuriga on 30/11/16.
 */
public class EventSimulatorParser {
    private static final Log log = LogFactory.getLog(EventSimulatorParser.class);
    private EventSimulatorParser(){

    }

    public static RandomDataSimulationConfig randomEventSimulatorParser(String eventSimulationConfig) {
        if (QueryDeployer.executionPlanDetails == null) {
            throw new EventSimulationException("Execution Plan is not deployed");
        }

        RandomDataSimulationConfig randomDataSimulationConfig = new RandomDataSimulationConfig();
        try {
            JSONObject jsonObject = new JSONObject(eventSimulationConfig);
            //set properties to RandomDataSimulationConfig

            randomDataSimulationConfig.setStreamName((String) jsonObject.get("streamName"));
            randomDataSimulationConfig.setEvents(jsonObject.getInt("events"));
            randomDataSimulationConfig.setDelay(jsonObject.getInt("delay"));
            List<StreamAttributeDto> attributeSimulation = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("attributeSimulation");
            if (jsonArray.length() != QueryDeployer.executionPlanDetails.getStreamDefinitionInfoDto().getStreamAttributeDtos().size()) {
                //// TODO: 27/11/16 proper error message
                throw new EventSimulationException("Attribute Simulation configuration is missed");
            }
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                if (!jsonArray.getJSONObject(i).isNull("type")) {
                    if (jsonArray.getJSONObject(i).getString("type").compareTo(RandomDataGeneratorConstants.PropertyBasedAttribute) == 0) {
                        if (!jsonArray.getJSONObject(i).isNull("category") && !jsonArray.getJSONObject(i).isNull("property")) {
                            PropertyBasedAttributeDto propertyBasedAttributeDto = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), PropertyBasedAttributeDto.class);
                            attributeSimulation.add(propertyBasedAttributeDto);
                        } else {
                            throw new EventSimulationException("Category and property should not be null value for " + RandomDataGeneratorConstants.PropertyBasedAttribute);
                            //log.error("Category and property should not be null value for " + RandomDataGeneratorConstants.PropertyBasedAttribute);
                        }

                    } else if (jsonArray.getJSONObject(i).getString("type").compareTo(RandomDataGeneratorConstants.RegexBasedAttribute) == 0) {
                        if (!jsonArray.getJSONObject(i).isNull("pattern")) {
                            RegexBasedAttributeDto regexBasedAttributeDto = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), RegexBasedAttributeDto.class);
                            RandomDataGenerator.validateRegularExpression(regexBasedAttributeDto.getPattern());
                            attributeSimulation.add(regexBasedAttributeDto);
                        } else {
                            throw new EventSimulationException("Pattern should not be null value for " + RandomDataGeneratorConstants.RegexBasedAttribute);
                            //log.error("Pattern should not be null value for " + RandomDataGeneratorConstants.RegexBasedAttribute);
                        }

                    } else if (jsonArray.getJSONObject(i).getString("type").compareTo(RandomDataGeneratorConstants.PrimitiveBasedAttribute) == 0) {
                        if (!jsonArray.getJSONObject(i).isNull("min") && !jsonArray.getJSONObject(i).isNull("max") && !jsonArray.getJSONObject(i).isNull("length")) {
                            PrimitiveBasedAttribute primitiveBasedAttribute = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), PrimitiveBasedAttribute.class);
                            attributeSimulation.add(primitiveBasedAttribute);
                        } else {
                            throw new EventSimulationException("min,max and length value should not be null value for " + RandomDataGeneratorConstants.PrimitiveBasedAttribute);
                            // log.error("min,max and length value should not be null value for " + RandomDataGeneratorConstants.PrimitiveBasedAttribute);
                        }
                    } else if (jsonArray.getJSONObject(i).getString("type").compareTo(RandomDataGeneratorConstants.CustomDataBasedAttribute) == 0) {
                        if (!jsonArray.getJSONObject(i).isNull("customDataList")) {
                            CustomBasedAttribute customBasedAttribute = new CustomBasedAttribute();
                            customBasedAttribute.setType(jsonArray.getJSONObject(i).getString("type"));
                            customBasedAttribute.setCustomData(jsonArray.getJSONObject(i).getString("customDataList"));
                            attributeSimulation.add(customBasedAttribute);
                        } else {
                            new EventSimulationException("CustomDataList shold not be null for " + RandomDataGeneratorConstants.CustomDataBasedAttribute);
                            // log.error("CustomDataList shold not be null for " + RandomDataGeneratorConstants.CustomDataBasedAttribute);
                        }
                    }
                } else {
                    throw new EventSimulationException("Attribute Simulation type is required : " + RandomDataGeneratorConstants.PropertyBasedAttribute + "/" +
                            RandomDataGeneratorConstants.RegexBasedAttribute + "/" + RandomDataGeneratorConstants.PrimitiveBasedAttribute +
                            "/" + RandomDataGeneratorConstants.CustomDataBasedAttribute);
//                    log.error("Attribute Simulation type is required : " + RandomDataGeneratorConstants.PropertyBasedAttribute + "/" +
//                            RandomDataGeneratorConstants.RegexBasedAttribute + "/" + RandomDataGeneratorConstants.PrimitiveBasedAttribute +
//                            "/" + RandomDataGeneratorConstants.CustomDataBasedAttribute);

                }
            }
            randomDataSimulationConfig.setAttributeSimulation(attributeSimulation);

        } catch (JSONException e) {
            // TODO: 30/11/16 proper error message
            log.error(e.getMessage());
        }
        return randomDataSimulationConfig;
    }

    public static SingleEventSimulationConfig singleEventSimuatorParser(String singleEventSimulationConfig) throws IOException {
        if(QueryDeployer.executionPlanDetails==null){
            throw new EventSimulationException("Execution Plan is not deployed");
        }
        ObjectMapper mapper = new ObjectMapper();
        SingleEventSimulationConfig singleEventSimulationConfigInstance = mapper.readValue(singleEventSimulationConfig, SingleEventSimulationConfig.class);
        if(singleEventSimulationConfigInstance.getAttributeValues().size()!=QueryDeployer.executionPlanDetails.getStreamDefinitionInfoDto().getStreamAttributeDtos().size()){
            throw new EventSimulationException("No of Attribute value in not equal to attribute size in Input Stream : No of Attributes in stream is "+ QueryDeployer.executionPlanDetails.getStreamDefinitionInfoDto().getStreamAttributeDtos().size());
        }
        return singleEventSimulationConfigInstance;
    }

    public static CSVFileConfig fileEventSimulatorParser(FileInfo fileInfo, InputStream fileInputStream,String fileName,String inputStream, int delay,char delimiter){
        if(QueryDeployer.executionPlanDetails==null){
            throw new EventSimulationException("Execution Plan is not deployed");
        }
        FileDto fileDto=new FileDto(fileInfo,fileInputStream);
        CSVFileConfig csvFileconfig=new CSVFileConfig(fileName,inputStream,fileDto,delimiter,delay);
        return csvFileconfig;
    }


}
