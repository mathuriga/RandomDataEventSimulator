package org.wso2.carbon.event.simulator.bean;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mathuriga on 06/12/16.
 */
public class FeedSimulationConfig {
    private boolean orderBytimeStamp=false;
   // private HashMap<String,StreamConfiguration> streamConfigurationMapInfo;
    List<StreamConfiguration> streamConfigurationList;

    public FeedSimulationConfig() {
    }

    public boolean isOrderBytimeStamp() {
        return orderBytimeStamp;
    }

    public void setOrderBytimeStamp(boolean orderBytimeStamp) {
        this.orderBytimeStamp = orderBytimeStamp;
    }

    public List<StreamConfiguration> getStreamConfigurationList() {
        return streamConfigurationList;
    }

    public void setStreamConfigurationList(List<StreamConfiguration> streamConfigurationList) {
        this.streamConfigurationList = streamConfigurationList;
    }
}
