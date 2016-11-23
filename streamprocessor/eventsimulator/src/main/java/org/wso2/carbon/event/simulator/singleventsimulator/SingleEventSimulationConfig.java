package org.wso2.carbon.event.simulator.singleventsimulator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathuriga on 20/11/16.
 */

@XmlRootElement(name = "SingleEventSimulationConfig")
public class SingleEventSimulationConfig {
    private String streamName;
    List<String> attributeValues = new ArrayList<String>();

    public SingleEventSimulationConfig() {
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public List<String> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(List<String> attributeValues) {
        this.attributeValues = attributeValues;
    }
}
