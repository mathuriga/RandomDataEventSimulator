package org.wso2.carbon.event.simulator.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.carbon.event.executionplandelpoyer.ExecutionPlanDeployer;
import org.wso2.carbon.event.querydeployer.core.QueryDeployer;
import org.wso2.carbon.event.simulator.EventSimulator;
import org.wso2.carbon.event.simulator.bean.FeedSimulationConfig;
import org.wso2.carbon.event.simulator.bean.StreamConfiguration;
import org.wso2.carbon.event.simulator.constants.EventSimulatorConstants;
import org.wso2.carbon.event.simulator.constants.RandomDataGeneratorConstants;
import org.wso2.carbon.event.simulator.csvFeedSimulation.CSVFileConfig;
import org.wso2.carbon.event.simulator.csvFeedSimulation.core.CSVFeedEventSimulator;
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
import java.util.HashMap;
import java.util.List;

/**
 * Created by mathuriga on 30/11/16.
 */
public class EventSimulatorParser {
    private static final Log log = LogFactory.getLog(EventSimulatorParser.class);
    private EventSimulatorParser(){

    }

    public static RandomDataSimulationConfig randomDataSimulatorParser(String eventSimulationConfig) {
//        if (ExecutionPlanDeployer.getExecutionPlanDeployer() == null) {
//            throw new EventSimulationException("Execution Plan is not deployed");
//        }

        RandomDataSimulationConfig randomDataSimulationConfig = new RandomDataSimulationConfig();
        try {
            JSONObject jsonObject = new JSONObject(eventSimulationConfig);
            //set properties to RandomDataSimulationConfig

            randomDataSimulationConfig.setStreamName((String) jsonObject.get(EventSimulatorConstants.STREAM_NAME));
            randomDataSimulationConfig.setEvents(jsonObject.getInt(EventSimulatorConstants.EVENTS));
            randomDataSimulationConfig.setDelay(jsonObject.getInt(EventSimulatorConstants.DELAY));
            List<StreamAttributeDto> attributeSimulation = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray(EventSimulatorConstants.ATTRIBUTE_CONFIGURATION);
//            if (jsonArray.length() != ExecutionPlanDeployer.getExecutionPlanDeployer().getExecutionPlanDto().getInputStreamDtoMap().get(randomDataSimulationConfig.getStreamName()).getStreamAttributeDtos().size()) {
//                //// TODO: 27/11/16 proper error message
//                throw new EventSimulationException("Attribute Simulation configuration is missed");
//            }
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                if (!jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.RANDOMDATAGENERATORTYPE)) {
                    if (jsonArray.getJSONObject(i).getString(EventSimulatorConstants.RANDOMDATAGENERATORTYPE).compareTo(RandomDataGeneratorConstants.PropertyBasedAttribute) == 0) {
                        if (!jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.PROPERTYBASEDATTRIBUTE_CATEGORY) && !jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.PROPERTYBASEDATTRIBUTE_PROPERTY)) {
                            PropertyBasedAttributeDto propertyBasedAttributeDto = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), PropertyBasedAttributeDto.class);
                            attributeSimulation.add(propertyBasedAttributeDto);
                        } else {
                            throw new EventSimulationException("Category and property should not be null value for " + RandomDataGeneratorConstants.PropertyBasedAttribute);
                            //log.error("Category and property should not be null value for " + RandomDataGeneratorConstants.PropertyBasedAttribute);
                        }

                    } else if (jsonArray.getJSONObject(i).getString(EventSimulatorConstants.RANDOMDATAGENERATORTYPE).compareTo(RandomDataGeneratorConstants.RegexBasedAttribute) == 0) {
                        if (!jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.REGEXBASEDATTRIBUTE_PATTERN)) {
                            RegexBasedAttributeDto regexBasedAttributeDto = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), RegexBasedAttributeDto.class);
                            RandomDataGenerator.validateRegularExpression(regexBasedAttributeDto.getPattern());
                            attributeSimulation.add(regexBasedAttributeDto);
                        } else {
                            throw new EventSimulationException("Pattern should not be null value for " + RandomDataGeneratorConstants.RegexBasedAttribute);
                            //log.error("Pattern should not be null value for " + RandomDataGeneratorConstants.RegexBasedAttribute);
                        }

                    } else if (jsonArray.getJSONObject(i).getString(EventSimulatorConstants.RANDOMDATAGENERATORTYPE).compareTo(RandomDataGeneratorConstants.PrimitiveBasedAttribute) == 0) {
                        if (!jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.PRIMITIVEBASEDATTRIBUTE_MIN) && !jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.PRIMITIVEBASEDATTRIBUTE_MAX) && !jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.PRIMITIVEBASEDATTRIBUTE_LENGTH_DECIMAL)) {
                            PrimitiveBasedAttribute primitiveBasedAttribute = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), PrimitiveBasedAttribute.class);
                            attributeSimulation.add(primitiveBasedAttribute);
                        } else {
                            throw new EventSimulationException("min,max and length value should not be null value for " + RandomDataGeneratorConstants.PrimitiveBasedAttribute);
                            // log.error("min,max and length value should not be null value for " + RandomDataGeneratorConstants.PrimitiveBasedAttribute);
                        }
                    } else if (jsonArray.getJSONObject(i).getString(EventSimulatorConstants.RANDOMDATAGENERATORTYPE).compareTo(RandomDataGeneratorConstants.CustomDataBasedAttribute) == 0) {
                        if (!jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.CUSTOMDATABASEDATTRIBUTE_LIST)) {
                            CustomBasedAttribute customBasedAttribute = new CustomBasedAttribute();
                            customBasedAttribute.setType(jsonArray.getJSONObject(i).getString(EventSimulatorConstants.RANDOMDATAGENERATORTYPE));
                            customBasedAttribute.setCustomData(jsonArray.getJSONObject(i).getString(EventSimulatorConstants.CUSTOMDATABASEDATTRIBUTE_LIST));
                            attributeSimulation.add(customBasedAttribute);
                        } else {
                            new EventSimulationException("CustomDataList shold not be null for " + RandomDataGeneratorConstants.CustomDataBasedAttribute);
                            // log.error("CustomDataList should not be null for " + RandomDataGeneratorConstants.CustomDataBasedAttribute);
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
            log.error(e.getMessage());
        }
        return randomDataSimulationConfig;
    }


    public static SingleEventSimulationConfig singleEventSimuatorParser(String singleEventSimulationConfig) throws IOException {
        if(ExecutionPlanDeployer.getExecutionPlanDeployer()==null){
            throw new EventSimulationException("Execution Plan is not deployed");
        }
        ObjectMapper mapper = new ObjectMapper();
        SingleEventSimulationConfig singleEventSimulationConfigInstance = mapper.readValue(singleEventSimulationConfig, SingleEventSimulationConfig.class);
        if(singleEventSimulationConfigInstance.getAttributeValues().size()!=QueryDeployer.executionPlanDetails.getStreamDefinitionInfoDto().getStreamAttributeDtos().size()){
            throw new EventSimulationException("No of Attribute value in not equal to attribute size in Input Stream : No of Attributes in stream is "+ QueryDeployer.executionPlanDetails.getStreamDefinitionInfoDto().getStreamAttributeDtos().size());
        }
        return singleEventSimulationConfigInstance;
    }

//    public static CSVFileConfig fileEventSimulatorParser(FileInfo fileInfo, InputStream fileInputStream,String fileName,String inputStream, int delay,char delimiter){
//        if(QueryDeployer.executionPlanDetails==null){
//            throw new EventSimulationException("Execution Plan is not deployed");
//        }
//        FileDto fileDto=new FileDto(fileInfo,fileInputStream);
//        CSVFileConfig csvFileconfig=new CSVFileConfig(fileName,inputStream,fileDto,delimiter,delay);
//        return csvFileconfig;
//    }

    public static CSVFileConfig fileEventSimulatorParser(String csvFileDetails) {
//        if (ExecutionPlanDeployer.getExecutionPlanDeployer() == null) {
//            throw new EventSimulationException("Execution Plan is not deployed");
//        }
        CSVFileConfig csvFileConfig = null;
        try {
            CSVFeedEventSimulator csvFeedEventSimulator = CSVFeedEventSimulator.getCSVFeedEventSimulator();
            csvFileConfig = new CSVFileConfig();
            JSONObject jsonObject = new JSONObject(csvFileDetails);
            csvFileConfig.setStreamName((String) jsonObject.get(EventSimulatorConstants.STREAM_NAME));
            csvFileConfig.setFileName((String) jsonObject.get(EventSimulatorConstants.FILE_NAME));
            FileDto fileDto=null;
            if(csvFeedEventSimulator.getCsvFileInfoMap().containsKey(csvFileConfig.getFileName())){
                 fileDto= csvFeedEventSimulator.getCsvFileInfoMap().get(csvFileConfig.getFileName());
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


    public static FeedSimulationConfig feedSimulationConfigParser(String feedSimulationDetails){
//        if (ExecutionPlanDeployer.getExecutionPlanDeployer() == null) {
//            throw new EventSimulationException("Execution Plan is not deployed");
//        }
        System.out.println();
        FeedSimulationConfig feedSimulationConfig=null;
        try{
            feedSimulationConfig=new FeedSimulationConfig();
            JSONObject jsonObject = new JSONObject(feedSimulationDetails);
            if(jsonObject.getBoolean(EventSimulatorConstants.ORDER_BY_TIMESTAMP)) {
                feedSimulationConfig.setOrderBytimeStamp(jsonObject.getBoolean(EventSimulatorConstants.ORDER_BY_TIMESTAMP));
            }
            //HashMap<String,StreamConfiguration> streamConfigurationListMap=new HashMap<>();
            List<StreamConfiguration> streamConfigurationList=new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray(EventSimulatorConstants.STREAM_CONFIGURATION);
            Gson gson = new Gson();
            for(int i=0;i<jsonArray.length();i++){
                if (!jsonArray.getJSONObject(i).isNull(EventSimulatorConstants.SIMULATION_TYPE)){
                    String feedSimulationType=jsonArray.getJSONObject(i).getString(EventSimulatorConstants.SIMULATION_TYPE);
                    switch (feedSimulationType){
                        case EventSimulatorConstants.RANDOM_DATA_SIMULATION:
                            RandomDataSimulationConfig randomDataSimulationConfig=EventSimulatorParser.randomDataSimulatorParser(String.valueOf(jsonArray.getJSONObject(i)));
                            randomDataSimulationConfig.setSimulationType(EventSimulatorConstants.RANDOM_DATA_SIMULATION);
                            //streamConfigurationListMap.put(randomDataSimulationConfig.getStreamName(),randomDataSimulationConfig);
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
//            System.out.println(feedSimulationConfig.isOrderBytimeStamp());
//            System.out.println(feedSimulationConfig.getStreamConfigurationMapInfo().get("inputStream1").getSimulationType());
//            System.out.println(((RandomDataSimulationConfig) feedSimulationConfig.getStreamConfigurationMapInfo().get("inputStream1")).getDelay());
//            System.out.println((((PropertyBasedAttributeDto) (((RandomDataSimulationConfig) feedSimulationConfig.getStreamConfigurationMapInfo().get("inputStream1")).getAttributeSimulation()).get(0)).getCategory()));
//            System.out.println(feedSimulationConfig.getStreamConfigurationMapInfo().get("inputStream2").getSimulationType());
//            System.out.println(((CSVFileConfig) feedSimulationConfig.getStreamConfigurationMapInfo().get("inputStream2")).getDelimiter());
        }catch (JSONException e){
            log.error(e.getMessage());
        }

       return feedSimulationConfig;
    }

}
