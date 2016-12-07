package org.wso2.carbon.event.executionplandelpoyer;

import java.util.List;

/**
 * Created by mathuriga on 03/12/16.
 */
public class StreamDefinitionDto {
    private String streamDefinition;
    private List<StreamAttributeDto> streamAttributeDtos;
    private String streamName;

    public String getStreamDefinition() {
        return streamDefinition;
    }

    public void setStreamDefinition(String streamDefinition) {
        this.streamDefinition = streamDefinition;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public List<StreamAttributeDto> getStreamAttributeDtos() {
        return streamAttributeDtos;
    }

    public void setStreamAttributeDtos(List<StreamAttributeDto> streamAttributeDtos) {
        this.streamAttributeDtos = streamAttributeDtos;
    }
}
