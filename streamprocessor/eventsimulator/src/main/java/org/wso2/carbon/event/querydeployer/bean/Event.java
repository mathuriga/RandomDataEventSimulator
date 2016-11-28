package org.wso2.carbon.event.simulator.querydeployer.bean;

/**
 * Created by mathuriga on 19/11/16.
 */
public class Event {
    String streamName;
    Object[] eventData;

    public Event() {
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public Object[] getEventData() {
        return eventData;
    }

    public void setEventData(Object[] eventData) {
        this.eventData = eventData;
    }
}
