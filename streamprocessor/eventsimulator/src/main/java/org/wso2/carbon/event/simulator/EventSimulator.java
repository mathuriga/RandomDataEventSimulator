package org.wso2.carbon.event.simulator;



import org.wso2.carbon.event.simulator.querydeployer.bean.ExecutionPlanDetails;
import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.event.Event;

import java.util.List;

/**
 * Created by mathuriga on 06/11/16.
 */
public abstract class EventSimulator {
    protected double percentage;
    protected List<String> outputList;
    protected int noOfEventsArrived;
    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public List<String> getOutputList() {
        return outputList;
    }

    public void setOutputList(List<String> outputList) {
        this.outputList = outputList;
    }

    public boolean Send(ExecutionPlanRuntime executionPlanRuntime, ExecutionPlanDetails executionPlan, Event event) {
        return false;
    }

    public boolean Send(ExecutionPlanRuntime executionPlanRuntime, ExecutionPlanDetails executionPlan, org.wso2.carbon.event.simulator.querydeployer.bean.Event event, int delay) {
        return false;
    }
}
