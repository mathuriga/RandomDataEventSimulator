package org.wso2.carbon.event.simulator.core.singleventsimulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.event.executionplandelpoyer.ExecutionPlanDeployer;
import org.wso2.carbon.event.simulator.core.EventSimulator;
import org.wso2.carbon.event.querydeployer.bean.Event;
import org.wso2.carbon.event.querydeployer.core.QueryDeployer;
import org.wso2.carbon.event.simulator.core.exception.EventSimulationException;
import org.wso2.carbon.event.simulator.core.randomdatafeedsimulation.bean.RandomDataSimulationConfig;
import org.wso2.carbon.event.simulator.core.utils.EventConverter;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by mathuriga on 20/11/16.
 */
public class SingleEventSimulator implements EventSimulator {
    private static final Log log = LogFactory.getLog(SingleEventSimulator.class);
    private static SingleEventSimulator singleEventSimulator;
    private SingleEventSimulationConfig singleEventSimulationConfig;

    private SingleEventSimulator(){

    }

    public static SingleEventSimulator getSingleEventSimulator() {
        if (singleEventSimulator == null) {
            synchronized (SingleEventSimulator.class) {
                if (singleEventSimulator == null) {
                    singleEventSimulator = new SingleEventSimulator();
                }
            }
        }
        return singleEventSimulator;
    }

    public Object configureSingleEventSimulation(String singleEventSimulationConfig) throws IOException {
        if(ExecutionPlanDeployer.getExecutionPlanDeployer()==null){
            throw new EventSimulationException("Execution Plan is not deployed");
        }
        ObjectMapper mapper = new ObjectMapper();
        SingleEventSimulationConfig singleEventSimulationConfigInstance = mapper.readValue(singleEventSimulationConfig, SingleEventSimulationConfig.class);
        return singleEventSimulationConfigInstance;
    }


    @Override
    public void send(String streamName,Event event) {
        try {
            ExecutionPlanDeployer.getExecutionPlanDeployer().getInputHandlerMap().get(streamName).send(event.getEventData());
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("Error occurred during send event :" + e.getMessage());
        }
    }

    public boolean send(SingleEventSimulationConfig singleEventSimulationConfig){
        System.out.println(singleEventSimulationConfig.getAttributeValues().get(0));
        String[] attributeValue= new String[singleEventSimulationConfig.getAttributeValues().size()];
        attributeValue=singleEventSimulationConfig.getAttributeValues().toArray(attributeValue);
        String streamName=singleEventSimulationConfig.getStreamName();
        Event event =new Event();

        System.out.println(ExecutionPlanDeployer.getExecutionPlanDeployer().getExecutionPlanDto().getExecutionPlanName());
        try{
            event = EventConverter.eventConverter(streamName,attributeValue,ExecutionPlanDeployer.getExecutionPlanDeployer().getExecutionPlanDto());
            System.out.println(Arrays.deepToString(event.getEventData()));
            //event = EventConverter.eventConverter(attributeValue, QueryDeployer.executionPlanDetails);
            System.out.println("Input Event " + Arrays.deepToString(event.getEventData()));
            System.out.println("------------------------------------------------------");
        }catch (Exception e) {
            log.error("Error occurred : Failed to create an event" +e.getMessage());
        }
            //send event
        try{
            send(streamName,event);
        }catch (Exception e){
            log.error("Error occurred : Failed to send an event" +e.getMessage());
        }
        return true;
    }




    @Override
    public void pauseEvents() {

    }

    @Override
    public void stopEvents() {

    }

    @Override
    public void resumeEvents() {

    }

    @Override
    public RandomDataSimulationConfig configureSimulation(String eventSimulationConfig) {
        return null;
    }
}
