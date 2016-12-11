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
package org.wso2.carbon.event.simulator.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.event.simulator.bean.FeedSimulationConfig;
import org.wso2.carbon.event.simulator.bean.StreamConfiguration;
import org.wso2.carbon.event.simulator.constants.EventSimulatorConstants;
import org.wso2.carbon.event.simulator.csvFeedSimulation.CSVFileConfig;
import org.wso2.carbon.event.simulator.csvFeedSimulation.core.CSVFeedEventSimulator;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.RandomDataSimulationConfig;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.core.RandomDataEventSimulator;
import org.wso2.carbon.event.simulator.singleventsimulator.SingleEventSimulationConfig;
import org.wso2.carbon.event.simulator.singleventsimulator.SingleEventSimulator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * EventSimulatorServiceExecutor starts the simulation execution for single Event and
 * Feed Simulation
 */

public class EventSimulatorServiceExecutor {
    private static final Log log = LogFactory.getLog(EventSimulatorServiceExecutor.class);

    /**
     * Executor Service for Thread Pool.
     * Thread pool is needed for Feed simulation
     */
    private static volatile ExecutorService executor;

    /**
     * running used to indicate simulation process
     * In the case of single event simulator If running is true it won't allowed another single
     * Event simulation process until previous one finishes it's task
     */
    private static volatile boolean running = false;

    /**
     * Initializes the Event Simulator Service Executor classes for simulation process.
     */
    public EventSimulatorServiceExecutor() {

    }

    /**
     * Initialize the SingleEventSimulator
     *
     * @param singleEventSimulationConfig SingleEventSimulationConfiguration
     */
    public void simulateSingleEvent(SingleEventSimulationConfig singleEventSimulationConfig) {
        if (!running) {
            synchronized (this) {
                if (!running) {
                    SingleEventSimulator singleEventSimulator = new SingleEventSimulator();
                    singleEventSimulator.send(singleEventSimulationConfig);
                }
            }
        }
        log.error("Event is send success Fully");
    }

    public static void simulateFeedSimulation(FeedSimulationConfig feedSimulationConfig) throws InterruptedException {
        // if (!running) {
        final Object lock = new Object();

        synchronized (EventSimulatorServiceExecutor.class) {
            if (!running) {

                running = true;
                int noOfStream = feedSimulationConfig.getStreamConfigurationList().size();
                executor = Executors.newFixedThreadPool(noOfStream);//creating a thread pool for feed simulation
                for (int i = 0; i < noOfStream; i++) {
                    SimulationStarter simulationStarter = new SimulationStarter(feedSimulationConfig.getStreamConfigurationList().get(i));
                    // Thread simulatorThread=new Thread(simulationStarter);
                    executor.execute(simulationStarter);//calling execute method of ExecutorService
                }
            } else if (running) {
                executor.shutdownNow();
                log.info("SimulationService is stopped");
                // final boolean isFinished=executor.awaitTermination(1, TimeUnit.MILLISECONDS);
                //log.debug("Simulation task is stop successfully " + isFinished);
            }
        }
    }


    public static class SimulationStarter implements Runnable {
        StreamConfiguration streamConfiguration;

        public SimulationStarter(StreamConfiguration streamConfiguration) {
            this.streamConfiguration = streamConfiguration;
        }

        @Override
        public void run() {
            if (streamConfiguration.getSimulationType().compareTo(EventSimulatorConstants.RANDOM_DATA_SIMULATION) == 0) {
                RandomDataEventSimulator randomDataEventSimulator = new RandomDataEventSimulator();
                randomDataEventSimulator.send((RandomDataSimulationConfig) streamConfiguration);
            } else if (streamConfiguration.getSimulationType().compareTo(EventSimulatorConstants.FILE_FEED_SIMULATION) == 0) {
                CSVFeedEventSimulator csvFeedEventSimulator = new CSVFeedEventSimulator();
                csvFeedEventSimulator.send((CSVFileConfig) streamConfiguration);
            }
        }

    }

    public void pause() {

    }

}



