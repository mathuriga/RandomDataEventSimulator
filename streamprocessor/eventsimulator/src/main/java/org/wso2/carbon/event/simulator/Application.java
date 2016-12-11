package org.wso2.carbon.event.simulator;


import org.wso2.carbon.event.endpoint.EventSimulatorRestService;
import org.wso2.carbon.event.endpoint.ExecutionPlanEndpoint;
import org.wso2.msf4j.MicroservicesRunner;

/**
 * Created by mathuriga on 12/11/16.
 */
public class Application {
    public static void main(String[] args) {
        new MicroservicesRunner().deploy(
                new EventSimulatorRestService(),
                new ExecutionPlanEndpoint()).start();
    }
}
