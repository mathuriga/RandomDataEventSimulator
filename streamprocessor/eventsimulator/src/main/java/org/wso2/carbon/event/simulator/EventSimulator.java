package org.wso2.carbon.event.simulator.core;



import org.wso2.carbon.event.simulator.core.randomdatafeedsimulation.bean.RandomDataSimulationConfig;

/**
 * Created by mathuriga on 06/11/16.
 */
public interface EventSimulator {


    public void send(String streamName,org.wso2.carbon.event.querydeployer.bean.Event event);

   // public boolean send(RandomDataSimulationConfig randomDataSimulationConfig);

    public void pauseEvents();

    public void stopEvents();

    public void resumeEvents();

    public RandomDataSimulationConfig configureSimulation(String eventSimulationConfig);
}
