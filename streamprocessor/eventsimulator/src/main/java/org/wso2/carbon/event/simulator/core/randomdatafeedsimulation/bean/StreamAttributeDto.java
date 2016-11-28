package org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean;


/**
 * Created by mathuriga on 17/11/16.
 */


public abstract class StreamAttributeDto {
    private String type;


    public StreamAttributeDto() {
    }

    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }



}
