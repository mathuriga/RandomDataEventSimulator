package org.wso2.carbon.event.executionplandelpoyer.utils;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.wso2.carbon.event.executionplandelpoyer.ExecutionPlanDto;
import org.wso2.carbon.event.executionplandelpoyer.Queries;
import org.wso2.carbon.event.executionplandelpoyer.StreamDefinitionDto;

import java.util.Map;

/**
 * Created by mathuriga on 03/12/16.
 */
public class ExecutionPlanParser {
    private static final Log log = LogFactory.getLog(ExecutionPlanParser.class);

    private ExecutionPlanParser(){
    }

    public static ExecutionPlanDto executionPlanDtoParser(String executionPlan){
        ExecutionPlanDto executionPlanDto=new ExecutionPlanDto();
        JSONObject jsonObject=new JSONObject(executionPlan);
        Gson gson = new Gson();
        executionPlanDto.setExecutionPlanName((String) jsonObject.get("executionPlanName"));
        JSONArray inputStreamArray=jsonObject.getJSONArray("inputStream");
        System.out.println(inputStreamArray.length());
        //set input stream definition details
        for(int i=0;i<inputStreamArray.length();i++){
            StreamDefinitionDto streamDefinitionDto= gson.fromJson(String.valueOf(inputStreamArray.getJSONObject(i)),StreamDefinitionDto.class);
            executionPlanDto.getInputStreamDtoMap().put(streamDefinitionDto.getStreamName(),streamDefinitionDto);
        }

        //set  output Stream Definition details
        JSONArray outputStreamArray=jsonObject.getJSONArray("OutputStream");

        for(int i=0;i<outputStreamArray.length();i++){
            StreamDefinitionDto streamDefinitionDto=gson.fromJson(String.valueOf(outputStreamArray.getJSONObject(i)),StreamDefinitionDto.class);
            executionPlanDto.getOutputStreamDtoMap().put(streamDefinitionDto.getStreamName(),streamDefinitionDto);
        }

        //set query details
        JSONArray queriesArray=jsonObject.getJSONArray("Queries");

        for(int i=0;i<queriesArray.length();i++){
            Queries queries=gson.fromJson(String.valueOf(queriesArray.getJSONObject(i)),Queries.class);
            executionPlanDto.getQueriesMap().put(queries.getQueryName(),queries);

        }
        return executionPlanDto;
    }
}
