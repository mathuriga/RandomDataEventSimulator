package org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathuriga on 17/11/16.
 */

@XmlRootElement(name="StreamDto")
public class StreamDto {
    private String streamName;
    private List<StreamAttributeDto> streamAttributeDto =new ArrayList<>();

    public String getStreamName() {
        return streamName;
    }

    @XmlElement
    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public List<org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.StreamAttributeDto> getStreamAttributeDto() {
        return streamAttributeDto;
    }

    @XmlElement
    public void setStreamAttributeDto(List<org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.StreamAttributeDto> streamAttributeDto) {
        this.streamAttributeDto = streamAttributeDto;
    }
}
