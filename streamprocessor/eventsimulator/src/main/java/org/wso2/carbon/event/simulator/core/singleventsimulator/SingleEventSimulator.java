package org.wso2.carbon.event.simulator.singleventsimulator;

import org.wso2.carbon.event.simulator.EventSimulator;
import org.wso2.carbon.event.simulator.querydeployer.bean.Event;
import org.wso2.carbon.event.simulator.querydeployer.bean.ExecutionPlanDetails;
import org.wso2.carbon.event.simulator.querydeployer.core.QueryDeployer;
import org.wso2.carbon.event.simulator.utils.EventConverter;
import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.stream.input.InputHandler;

import java.util.Arrays;

/**
 * Created by mathuriga on 20/11/16.
 */
public class SingleEventSimulator extends EventSimulator {
    private static SingleEventSimulator singleEventSimulator;
    private SingleEventSimulationConfig singleEventSimulationConfig;

    private SingleEventSimulator(){

    }

    public static SingleEventSimulator getsingleEventSimulator() {
        if (singleEventSimulator == null) {
            synchronized (SingleEventSimulator.class) {
                if (singleEventSimulator == null) {
                    singleEventSimulator = new SingleEventSimulator();
                }
            }
        }
        return singleEventSimulator;
    }

    public boolean Send(ExecutionPlanRuntime executionPlanRuntime, ExecutionPlanDetails executionPlan, Event event) {
        InputHandler inputHandler = executionPlanRuntime.getInputHandler(executionPlan.getInputStream());
        executionPlanRuntime.start();
        try {
            inputHandler.send(event.getEventData());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean convertEvent(ExecutionPlanDetails executionPlan, SingleEventSimulationConfig singleEventSimulationConfig) {
        boolean status=false;

        try{
            this.singleEventSimulationConfig=singleEventSimulationConfig;
            String[] attributeValue;
            attributeValue= (String[]) singleEventSimulationConfig.getAttributeValues().toArray();
            Event event= EventConverter.eventConverter(attributeValue, QueryDeployer.executionPlanDetails);
            System.out.println("Input Event: "+ Arrays.deepToString(event.getEventData()));
            status=this.Send(QueryDeployer.executionPlanRuntime,executionPlan,event);

        }catch(Exception e){

        }

        return status;
    }

}
