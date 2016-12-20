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
import org.apache.log4j.Logger;
import org.wso2.carbon.event.executionplandelpoyer.ExecutionPlanDeployer;
import org.wso2.carbon.event.executionplandelpoyer.ExecutionPlanDto;
import org.wso2.carbon.event.simulator.bean.FeedSimulationConfig;
import org.wso2.carbon.event.simulator.bean.StreamConfiguration;
import org.wso2.carbon.event.simulator.constants.EventSimulatorConstants;
import org.wso2.carbon.event.simulator.csvFeedSimulation.CSVFileConfig;
import org.wso2.carbon.event.simulator.csvFeedSimulation.core.CSVFeedEventSimulator;
import org.wso2.carbon.event.simulator.exception.EventSimulationException;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.RandomDataSimulationConfig;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.core.RandomDataEventSimulator;
import org.wso2.carbon.event.simulator.singleventsimulator.SingleEventDto;
import org.wso2.carbon.event.simulator.singleventsimulator.SingleEventSimulator;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;


/**
 * EventSimulatorServiceExecutor starts the simulation execution for single Event and
 * Feed Simulation
 */

public class EventSimulatorServiceExecutor{
    private static final Logger log = Logger.getLogger(EventSimulatorServiceExecutor.class);

    /**
     * running used to indicate simulation process
     * In the case of single event simulator If running is true it won't allowed another single
     * Event simulation process until previous one finishes it's task
     */
    private volatile boolean running = false;

    /**
     * RandomDataEventSimulator
     */
    private RandomDataEventSimulator randomDataEventSimulator;

    /**
     * CSVFeedEventSimulator
     */
    private CSVFeedEventSimulator csvFeedEventSimulator;

    /**
     * Initialize the SingleEventSimulator
     * call send function to start the single event simulation
     *
     * @param singleEventDto SingleEventSimulationConfiguration
     */
    public void simulateSingleEvent(SingleEventDto singleEventDto) {
        if (!running) {
            synchronized (this) {
                if (!running) {
                    SingleEventSimulator singleEventSimulator = new SingleEventSimulator();
                    singleEventSimulator.send(singleEventDto);
                }
            }
        }
        log.info("Event is send success Fully");
    }

    /**
     * Creates the thread pool for feed Simulation
     *
     * @param feedSimulationConfig FeedSimulationConfig
     * @throws InterruptedException InterruptedException exception
     */
    public void simulateFeedSimulation(FeedSimulationConfig feedSimulationConfig) {
        try {
            if (!running) {
                synchronized (this) {
                    if (!running) {
                        running = true;
                        int noOfStream = feedSimulationConfig.getStreamConfigurationList().size();

                        //creating a thread pool for feed simulation
                        for (int i = 0; i < noOfStream; i++) {
                            SimulationStarter simulationStarter = new SimulationStarter(feedSimulationConfig.getStreamConfigurationList().get(i));
                            //calling execute method of ExecutorService
//                        executor.execute(simulationStarter);
                            new Thread(simulationStarter).start();
                        }
                    }
                }
            }
        }catch (Exception e){
            throw new EventSimulationException(e.getMessage());
        }
    }


    private class SimulationStarter implements Runnable {
        StreamConfiguration streamConfiguration;

        SimulationStarter(StreamConfiguration streamConfiguration) {
            this.streamConfiguration = streamConfiguration;
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() throws EventSimulationException {
            try {
                if (streamConfiguration.getSimulationType().compareTo(EventSimulatorConstants.RANDOM_DATA_SIMULATION) == 0) {
                    randomDataEventSimulator = new RandomDataEventSimulator();
                    randomDataEventSimulator.send((RandomDataSimulationConfig) streamConfiguration);
                } else if (streamConfiguration.getSimulationType().compareTo(EventSimulatorConstants.FILE_FEED_SIMULATION) == 0) {
                    csvFeedEventSimulator = new CSVFeedEventSimulator();
                    csvFeedEventSimulator.send((CSVFileConfig) streamConfiguration);
                }
                // TODO: 14/12/16 For Database simulation
            } catch (RuntimeException e) {
                throw new EventSimulationException("Error while simulation :" + e.getMessage());
            }

        }
    }

    /**
     * Stop the simulation process
     */
    public void stop() {
        if (running) {
            synchronized (this) {
                if (running) {
                    if (randomDataEventSimulator != null) {
                        RandomDataEventSimulator.isStopped = true;
                        randomDataEventSimulator = null;
                    }
                    if (csvFeedEventSimulator != null) {
                        CSVFeedEventSimulator.isStopped = true;
                        csvFeedEventSimulator = null;
                    }
                    this.running = false;
                    ExecutionPlanDeployer.getInstance().getExecutionPlanRuntime().shutdown();
                    log.info("Event Simulation process is stop");
                }
            }
        }
        return;
    }

    /**
     * pause the simulation process
     */
    public void pause() {
        if (running) {
            if (randomDataEventSimulator != null) {
                RandomDataEventSimulator.isPaused = true;
            }
            if (csvFeedEventSimulator != null) {
                CSVFeedEventSimulator.isPaused = true;
            }
            log.info("Event Simulation process is paused");
        }
    }

    /**
     * resume the simulation process
     */
    // TODO: 14/12/16 have to fix the bug 
    public void resume() {
        if (running) {
            if (randomDataEventSimulator != null) {
                RandomDataEventSimulator.isPaused = false;
                randomDataEventSimulator.resume();
            }
            if (csvFeedEventSimulator != null) {
                CSVFeedEventSimulator.isPaused = false;
                csvFeedEventSimulator.resumeEvents();
            }
            System.out.println("Event Simulation process is Resumed");
        }
    }

}



