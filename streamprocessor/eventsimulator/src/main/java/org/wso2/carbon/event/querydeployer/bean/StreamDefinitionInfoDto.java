package org.wso2.carbon.event.querydeployer.bean;

import java.util.List;

/**
 * Created by mathuriga on 25/11/16.
 */
public class StreamDefinitionInfoDto {
    private String streamName;
    private String streamVersion;
    private String streamDefinition;
//    private String streamDescription;
    private List<StreamAttributeDto> streamAttributeDtos;

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getStreamVersion() {
        return streamVersion;
    }

    public void setStreamVersion(String streamVersion) {
        this.streamVersion = streamVersion;
    }

    public String getStreamDefinition() {
        return streamDefinition;
    }

    public void setStreamDefinition(String streamDefinition) {
        this.streamDefinition = streamDefinition;
    }

//    public String getStreamDescription() {
//        return streamDescription;
//    }
//
//    public void setStreamDescription(String streamDescription) {
//        this.streamDescription = streamDescription;
//    }

    public List<StreamAttributeDto> getStreamAttributeDtos() {
        return streamAttributeDtos;
    }

    public void setStreamAttributeDtos(List<StreamAttributeDto> streamAttributeDtos) {
        this.streamAttributeDtos = streamAttributeDtos;
    }
}
