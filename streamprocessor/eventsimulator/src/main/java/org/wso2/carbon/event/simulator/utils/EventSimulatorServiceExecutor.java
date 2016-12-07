package org.wso2.carbon.event.simulator.utils;

import org.wso2.carbon.event.simulator.bean.FeedSimulationConfig;
import org.wso2.carbon.event.simulator.constants.EventSimulatorConstants;
import org.wso2.carbon.event.simulator.csvFeedSimulation.CSVFileConfig;
import org.wso2.carbon.event.simulator.csvFeedSimulation.core.CSVFeedEventSimulator;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.RandomDataSimulationConfig;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.core.RandomDataEventSimulator;

/**
 * Created by mathuriga on 07/12/16.
 */
public class EventSimulatorServiceExecutor {
    private EventSimulatorServiceExecutor(){
    }

    public static void simulate(FeedSimulationConfig feedSimulationConfig){
        int noOfStream=feedSimulationConfig.getStreamConfigurationList().size();
        if(!feedSimulationConfig.isOrderBytimeStamp()){
            for(int i=0;i<noOfStream;i++){
                if(feedSimulationConfig.getStreamConfigurationList().get(i).getSimulationType()== EventSimulatorConstants.RANDOM_DATA_SIMULATION){
                    RandomDataEventSimulator randomDataEventSimulator = RandomDataEventSimulator.getRandomDataEventSimulator();
                    randomDataEventSimulator.send((RandomDataSimulationConfig) feedSimulationConfig.getStreamConfigurationList().get(i));
                }else if(feedSimulationConfig.getStreamConfigurationList().get(i).getSimulationType()== EventSimulatorConstants.FILE_FEED_SIMULATION){
                    CSVFeedEventSimulator csvFeedEventSimulator=CSVFeedEventSimulator.getCSVFeedEventSimulator();
                    csvFeedEventSimulator.send((CSVFileConfig) feedSimulationConfig.getStreamConfigurationList().get(i));
                }
            }
        }else {
            // TODO: 07/12/16 Orderbytimestampcase
        }
        
    }
}
