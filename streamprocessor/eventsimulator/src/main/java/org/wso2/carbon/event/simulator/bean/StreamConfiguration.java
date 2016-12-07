package org.wso2.carbon.event.simulator.bean;

/**
 * Created by mathuriga on 06/12/16.
 */
public abstract class StreamConfiguration {
    private String simulationType;

    public String getSimulationType() {
        return simulationType;
    }

    public void setSimulationType(String simulationType) {
        this.simulationType = simulationType;
    }
}
