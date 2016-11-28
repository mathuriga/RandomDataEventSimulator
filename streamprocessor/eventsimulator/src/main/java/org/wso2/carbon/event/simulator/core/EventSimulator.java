package org.wso2.carbon.event.simulator;



import org.wso2.carbon.event.simulator.querydeployer.bean.ExecutionPlanDetails;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.RandomDataSimulationConfig;
import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.event.Event;

import java.util.List;

/**
 * Created by mathuriga on 06/11/16.
 */
public interface EventSimulator {


    public void send(org.wso2.carbon.event.simulator.querydeployer.bean.Event event);

    public boolean send(RandomDataSimulationConfig randomDataSimulationConfig);

    public void pauseEvents();

    public void stopEvents();

    public void resumeEvents();

    public RandomDataSimulationConfig configureSimulation(String eventSimulationConfig);
}
