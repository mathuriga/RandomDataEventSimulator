package org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathuriga on 17/11/16.
 */


public class RandomDataSimulationConfig {
    private int delay;
    private List<StreamAttributeDto> attributeSimulation =new ArrayList<>();
    private String streamName;
    private double events;

    public RandomDataSimulationConfig(){

    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public List<StreamAttributeDto> getAttributeSimulation() {
        return attributeSimulation;
    }

    public void setAttributeSimulation(List<StreamAttributeDto> attributeSimulation) {
        this.attributeSimulation = attributeSimulation;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public double getEvents() {
        return events;
    }

    public void setEvents(double events) {
        this.events = events;
    }
}
