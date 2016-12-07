package org.wso2.carbon.event.executionplandelpoyer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mathuriga on 03/12/16.
 */
public class ExecutionPlanDto {
    private String executionPlanName;
    private Map<String,StreamDefinitionDto> inputStreamDtoMap=new HashMap<>();
    private Map<String,StreamDefinitionDto> outputStreamDtoMap=new HashMap<>();
    private Map<String,Queries> queriesMap=new HashMap<>();

    public ExecutionPlanDto() {
    }

    public String getExecutionPlanName() {
        return executionPlanName;
    }

    public void setExecutionPlanName(String executionPlanName) {
        this.executionPlanName = executionPlanName;
    }

    public Map<String, StreamDefinitionDto> getInputStreamDtoMap() {
        return inputStreamDtoMap;
    }

    public void setInputStreamDtoMap(Map<String, StreamDefinitionDto> inputStreamDtoMap) {
        this.inputStreamDtoMap = inputStreamDtoMap;
    }

    public Map<String, StreamDefinitionDto> getOutputStreamDtoMap() {
        return outputStreamDtoMap;
    }

    public void setOutputStreamDtoMap(Map<String, StreamDefinitionDto> outputStreamDtoMap) {
        this.outputStreamDtoMap = outputStreamDtoMap;
    }

    public Map<String, Queries> getQueriesMap() {
        return queriesMap;
    }

    public void setQueriesMap(Map<String, Queries> queriesMap) {
        this.queriesMap = queriesMap;
    }
}

