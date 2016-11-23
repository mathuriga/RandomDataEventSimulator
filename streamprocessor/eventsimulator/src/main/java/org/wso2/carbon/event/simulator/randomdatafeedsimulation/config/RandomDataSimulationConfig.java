package org.wso2.carbon.event.simulator.randomdatafeedsimulation.config;

import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.StreamDto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by mathuriga on 17/11/16.
 */

@XmlRootElement(name="RandomDataSimulationConfig")
public class RandomDataSimulationConfig {
    private StreamDto streamDto;
    private String option;
    private double noOfEvents;
    private int delay;

//    public enum SimulationType {
//        RANDOM,
//        ADVANCED_SIMULATION
//    }

    public RandomDataSimulationConfig(){

    }

    @XmlElement
    public org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.StreamDto getStreamDto() {
        return streamDto;
    }

    @XmlElement
    public void setStreamDto(org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.StreamDto streamDto) {
        this.streamDto = streamDto;
    }

    public String getOption() {
        return option;
    }

    @XmlElement
    public void setOption(String option) {
        this.option = option;
    }

    public double getNoOfEvents() {
        return noOfEvents;
    }

    @XmlElement
    public void setNoOfEvents(double noOfEvents) {
        this.noOfEvents = noOfEvents;
    }

    public int getDelay() {
        return delay;
    }

    @XmlElement
    public void setDelay(int delay) {
        this.delay = delay;
    }
}
