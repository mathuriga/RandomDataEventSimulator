package org.wso2.carbon.event.simulator.utils;

import org.wso2.carbon.event.simulator.bean.FeedSimulationConfig;
import org.wso2.carbon.event.simulator.bean.StreamConfiguration;
import org.wso2.carbon.event.simulator.constants.EventSimulatorConstants;
import org.wso2.carbon.event.simulator.csvFeedSimulation.CSVFileConfig;
import org.wso2.carbon.event.simulator.csvFeedSimulation.core.CSVFeedEventSimulator;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.RandomDataSimulationConfig;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.core.RandomDataEventSimulator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mathuriga on 07/12/16.
 */
public class EventSimulatorServiceExecutor {
    private EventSimulatorServiceExecutor() {
    }

    private static volatile boolean running = false;

    public static void simulate(FeedSimulationConfig feedSimulationConfig) {
       // if (!running) {
            synchronized (EventSimulatorServiceExecutor.class) {
                if (!running) {
                    running = true;
                    int noOfStream = feedSimulationConfig.getStreamConfigurationList().size();
                    ExecutorService executor = Executors.newFixedThreadPool(noOfStream);//creating a thread pool for feed simulation
                    for (int i = 0; i < noOfStream; i++) {
                        SimulationStarter simulationStarter = new SimulationStarter(feedSimulationConfig.getStreamConfigurationList().get(i));
                        // Thread simulatorThread=new Thread(simulationStarter);
                        executor.execute(simulationStarter);//calling execute method of ExecutorService
                    }
                }if(running){

                }

                //  executor.shutdown();
//                else {
//                    // System.out.println("already running");
//                }
            //}

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


}
