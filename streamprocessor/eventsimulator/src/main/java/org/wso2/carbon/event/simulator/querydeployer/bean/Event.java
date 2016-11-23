package org.wso2.carbon.event.simulator.querydeployer.bean;

/**
 * Created by mathuriga on 19/11/16.
 */
public class Event {
    String inputStream;
    int noOfAttribute;
    Object[] eventData=new Object[noOfAttribute];

    public Event() {
    }

    public int getNoOfAttribute() {
        return noOfAttribute;
    }

    public void setNoOfAttribute(int noOfAttribute) {
        this.noOfAttribute = noOfAttribute;
    }


    public String getInputStream() {
        return inputStream;
    }

    public void setInputStream(String inputStream) {
        this.inputStream = inputStream;
    }

    public Object[] getEventData() {
        return eventData;
    }

    public void setEventData(Object[] eventData) {
        this.eventData = eventData;
    }
}
