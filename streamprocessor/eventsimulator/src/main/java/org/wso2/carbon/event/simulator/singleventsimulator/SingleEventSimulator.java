/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.event.simulator.singleventsimulator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.event.executionplandelpoyer.ExecutionPlanDeployer;
import org.wso2.carbon.event.simulator.EventSimulator;
import org.wso2.carbon.event.querydeployer.bean.Event;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.RandomDataSimulationConfig;
import org.wso2.carbon.event.simulator.utils.EventConverter;

import java.util.Arrays;

/**
 * SingleEventSimulator simulates the deployed execution plan using single event.
 * It implements EventSimulator parentclass
 */
public class SingleEventSimulator implements EventSimulator {
    private static final Log log = LogFactory.getLog(SingleEventSimulator.class);

    /**
     * Initialize single event simulator for single event simulation process
     */
    public SingleEventSimulator() {
    }

    /**
     * send the created event to siddhi InputHandler of particular input stream
     *
     * @param streamName Stream Name
     * @param event      Event Object
     */

    @Override
    public void send(String streamName, Event event) {
        try {
            ExecutionPlanDeployer.getExecutionPlanDeployer().getInputHandlerMap().get(streamName).send(event.getEventData());
        } catch (InterruptedException e) {
            log.error("Error occurred during send event :" + e.getMessage());
        }
    }

    /**
     * Create event as stated in single event simulation configuration
     * send created event to inputHandler for further process in siddhi
     * <p>
     * Initialize new event
     *
     * @param singleEventSimulationConfig single Event Configuration
     * @return true if event send successfully ; false if fails
     */
    public boolean send(SingleEventSimulationConfig singleEventSimulationConfig) {
        //attributeValue used to store values of attributes of an input stream
        String[] attributeValue = new String[singleEventSimulationConfig.getAttributeValues().size()];
        attributeValue = singleEventSimulationConfig.getAttributeValues().toArray(attributeValue);
        String streamName = singleEventSimulationConfig.getStreamName();

        Event event = new Event();

        //Convert attribute value as an Event
        try {
            event = EventConverter.eventConverter(streamName, attributeValue, ExecutionPlanDeployer.getExecutionPlanDeployer().getExecutionPlanDto());
            // TODO: 11/12/16 delete print statement
            System.out.println("Input Event " + Arrays.deepToString(event.getEventData()));
        } catch (Exception e) {
            log.error("Error occurred : Failed to create an event" + e.getMessage());
        }

        //send created event to inputHandler for further process in siddhi
        try {
            send(streamName, event);
        } catch (Exception e) {
            log.error("Error occurred : Failed to send an event" + e.getMessage());
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
