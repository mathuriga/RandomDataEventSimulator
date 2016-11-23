package org.wso2.carbon.event.simulator.querydeployer.core;


import org.wso2.carbon.event.simulator.querydeployer.bean.ExecutionPlanDetails;
import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.output.StreamCallback;

import java.util.Arrays;

/**
 * Created by mathuriga on 06/11/16.
 */
public class QueryDeployer {
    public static ExecutionPlanDetails executionPlanDetails;
    public SiddhiManager siddhiManager;
    public static ExecutionPlanRuntime executionPlanRuntime;

    public QueryDeployer(ExecutionPlanDetails executionPlanDetails) {
        this.executionPlanDetails = executionPlanDetails;
    }

    public void deployQuery() {
        siddhiManager = new SiddhiManager();
        executionPlanRuntime = siddhiManager.createExecutionPlanRuntime(this.executionPlanDetails.getInputStreamStreamDefiniton() + this.executionPlanDetails.getQuery());
        executionPlanRuntime.addCallback(executionPlanDetails.getOutputStream(), new StreamCallback() {
            @Override
            public void receive(Event[] events) {
                System.out.println("Output Event: " + Arrays.deepToString(events));
            }
        });
        System.out.println("Execution Plan is deployed Successfully");
    }


}
