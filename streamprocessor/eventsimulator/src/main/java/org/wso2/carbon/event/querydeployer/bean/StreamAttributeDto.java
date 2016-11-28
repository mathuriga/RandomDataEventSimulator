package org.wso2.carbon.event.simulator.querydeployer.bean;

/**
 * Created by mathuriga on 25/11/16.
 */
public class StreamAttributeDto {
    String attributeName;
    String attributeType;

    public StreamAttributeDto(){

    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }
}
