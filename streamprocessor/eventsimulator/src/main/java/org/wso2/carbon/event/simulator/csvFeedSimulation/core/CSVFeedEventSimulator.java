package org.wso2.carbon.event.simulator.csvFeedSimulation.core;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.event.executionplandelpoyer.ExecutionPlanDeployer;
import org.wso2.carbon.event.executionplandelpoyer.ExecutionPlanDto;
import org.wso2.carbon.event.querydeployer.bean.Event;
import org.wso2.carbon.event.simulator.EventSimulator;
import org.wso2.carbon.event.simulator.csvFeedSimulation.CSVFileConfig;
import org.wso2.carbon.event.simulator.exception.EventSimulationException;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.RandomDataSimulationConfig;
import org.wso2.carbon.event.simulator.utils.EventConverter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by mathuriga on 30/11/16.
 */
public class CSVFeedEventSimulator implements EventSimulator {
    private static final Log log = LogFactory.getLog(CSVFeedEventSimulator.class);

    public CSVFeedEventSimulator() {
    }

    @Override
    public void send(String streamName,Event event) {
        try {
            ExecutionPlanDeployer.getExecutionPlanDeployer().getInputHandlerMap().get(streamName).send(event.getEventData());
        } catch (InterruptedException e) {
            log.error("Error occurred during send event :" + e.getMessage());
        }
    }

    public boolean send(CSVFileConfig csvFileConfig) {
        ExecutionPlanDeployer.getExecutionPlanDeployer().getExecutionPlanRuntime().start();
//        EventCreatorFile eventCreatorFile = new EventCreatorFile(ExecutionPlanDeployer.getExecutionPlanDeployer().getExecutionPlanDto(), csvFileConfig);
//        Thread eventCreatorFileThread = new Thread(eventCreatorFile);
//        eventCreatorFileThread.start();
        sendEvent(ExecutionPlanDeployer.getExecutionPlanDeployer().getExecutionPlanDto(), csvFileConfig);
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

    public synchronized void sendEvent(ExecutionPlanDto executionPlanDto, CSVFileConfig csvFileConfig) {
        double percentage = 0;
        final Object lock = new Object();
        boolean isPaused = false;
        boolean isStopped = false;
        long noOfEvents = 0;
        int delay = csvFileConfig.getDelay();
        Reader in=null;
        CSVParser csvParser = null;
            if (delay <= 0) {
                log.warn("Events will be sent continuously since the delay between events are set to "
                        + delay + "milliseconds");
                delay = 0;
            }

            try {
                in = new FileReader(String.valueOf(Paths.get(System.getProperty("java.io.tmpdir"), csvFileConfig.getFileDto().getFileInfo().getFileName())));

                csvParser=CSVParser.parse(in,CSVFormat.DEFAULT);

                // TODO: 02/12/16 handle if csv dATA IS NULL
                if(csvParser==null){
                    // TODO: 02/12/16 proper error message
                    log.error("Data is not found in file");
                    return;
                }

                int attributeSize = executionPlanDto.getInputStreamDtoMap().get(csvFileConfig.getStreamName()).getStreamAttributeDtos().size();
                for (CSVRecord record : csvParser) {
                    try {
                        if (!isPaused) {
                            if (record.size() != attributeSize) {
                                log.warn("No of attribute is not equal to attribute size: " + attributeSize + " is needed" + "in Row no:" + noOfEvents + 1);
                            }
                            String[] attributes = new String[attributeSize];
                            noOfEvents=csvParser.getCurrentLineNumber();

                            for (int i = 0; i < record.size(); i++) {
                                attributes[i] = record.get(i);
                            }

                            Event event = EventConverter.eventConverter(csvFileConfig.getStreamName(),attributes, executionPlanDto);
                            System.out.println("Input Event " + Arrays.deepToString(event.getEventData()));
                            System.out.println("------------------------------------------------------");

                            //send event
                           send(csvFileConfig.getStreamName(),event);
                            if (delay > 0) {
                                Thread.sleep(delay);
                            }
//                            percentage = ((noOfEvents) * 100) /;
//                            System.out.println("Percentage: " + percentage);
                        } else if (isStopped) {
                            break;
                        } else {
                            synchronized (lock) {
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    continue;
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.error("Error occurred : Failed to send an event " + e.getMessage());
                    }
                }
            } catch (IllegalArgumentException e) {
                // TODO: 02/12/16 proper error message
                throw new EventSimulationException("File Parameters are null" + e.getMessage());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new EventSimulationException("File not found :" + csvFileConfig.getFileDto().getFileInfo().getFileName());
            } catch (IOException e) {
                throw new EventSimulationException("Error occurred while reading the file");
            } finally {
                try {
                    if(in!=null&&csvParser!=null)
                    in.close();
                    csvParser.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new EventSimulationException("Error occurred during closing the file");
                }
            }
        }
//    }
}

