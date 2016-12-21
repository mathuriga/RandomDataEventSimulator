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
package org.wso2.carbon.event.simulator.csvFeedSimulation.core;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.wso2.carbon.event.executionplandelpoyer.ExecutionPlanDeployer;
import org.wso2.carbon.event.executionplandelpoyer.ExecutionPlanDto;
import org.wso2.carbon.event.executionplandelpoyer.Event;
import org.wso2.carbon.event.simulator.EventSimulator;
import org.wso2.carbon.event.simulator.csvFeedSimulation.CSVFileSimulationDto;
import org.wso2.carbon.event.simulator.exception.EventSimulationException;
import org.wso2.carbon.event.simulator.utils.EventConverter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * This simulator simulates the execution plan by sending events. These events are generated by
 * inputs from CSV file.
 * <p>
 * This simulator class implements EventSimulator Interface
 */
public class CSVFeedEventSimulator implements EventSimulator {
    private static final Logger log = Logger.getLogger(CSVFeedEventSimulator.class);

    /**
     * Percentage of send events
     */
    private double percentage = 0;

    /**
     * Flag used to pause the simulation.
     */
    public volatile static boolean isPaused = false;

    /**
     * Flag used to stop the simulation.
     */
    public volatile static boolean isStopped = false;


    /**
     * Initialize CSVFeedEventSimulator to start the simulation
     */
    public CSVFeedEventSimulator() {
    }


    /**
     * send created event to siddhi input handler
     *
     * @param streamName Stream Name
     * @param event      Created Event
     */
    @Override
    public void send(String streamName, Event event) {
        try {
            /*
            get the input handler for particular input stream Name and send the event to that input handler
             */
            ExecutionPlanDeployer.getInstance().getInputHandlerMap().get(streamName).send(event.getEventData());
        } catch (InterruptedException e) {
            log.error("Error occurred during send event :" + e.getMessage());
        }
    }

    /**
     * start simulation for given configuration
     *
     * @param csvFileConfig csvFileConfig
     * @return true if all event send successfully
     */
    public boolean send(CSVFileSimulationDto csvFileConfig) {
        synchronized (this) {
            sendEvent(ExecutionPlanDeployer.getInstance().getExecutionPlanDto(), csvFileConfig);
        }
        return true;
    }

    @Override
    public void resume() {
        synchronized (this) {
            this.notifyAll();
        }


    }

    /**
     * This method must be called within a synchronized block to avoid multiple file simulators from running simultaneously.
     * Read the values from uploaded CSV file and convert those values into event and send those events to
     * input handler
     * <p>
     * <p>
     * To read the CSV file It uses CSV parser Library.
     * {@link <a href="https://commons.apache.org/proper/commons-csv/apidocs/org/apache/commons/csv/CSVParser.html">CSVParser</a>}
     * </p>
     * <p>
     * <p>
     * CSV file can be separated by one of these fallowing character , , ; , \t by default
     * It has capability to have user defined delimiter
     * Any field may be quoted (with double quotes)
     * Fields with embedded commas or delimiter characters must be double quoted.
     * </p>
     * <p>
     * Initialize CSVParser
     *
     * @param executionPlanDto ExecutionPlanDto
     * @param csvFileConfig    CSVFileSimulationDto
     */
    private void sendEvent(ExecutionPlanDto executionPlanDto, CSVFileSimulationDto csvFileConfig) {

        /*
          return no of events read from CSV file during ever iteration
         */
        long noOfEvents = 0;
        int delay = csvFileConfig.getDelay();
        /*
        Reader for reading character streams from file
         */
        Reader in = null;
        /*
        CSVParser to read CSV Values
         */
        CSVParser csvParser = null;
        if (delay <= 0) {
            log.warn("Events will be sent continuously since the delay between events are set to "
                    + delay + "milliseconds");
            delay = 0;
        }

        try {
                /*
                Initialize Reader
                 */
            in = new FileReader(String.valueOf(Paths.get(System.getProperty("java.io.tmpdir"), csvFileConfig.getFileDto().getFileInfo().getFileName())));

                /*
                Initialize CSVParser with appropriate CSVFormat according to delimiter
                 */

            switch (csvFileConfig.getDelimiter()) {
                case ",":
                    csvParser = CSVParser.parse(in, CSVFormat.DEFAULT);
                    break;
                case ";":
                    csvParser = CSVParser.parse(in, CSVFormat.EXCEL);
                    break;
                case "\\t":
                    csvParser = CSVParser.parse(in, CSVFormat.TDF);
                    break;
                default:
                    csvParser = CSVParser.parse(in, CSVFormat.newFormat(csvFileConfig.getDelimiter().charAt(0)));
            }

            int attributeSize = executionPlanDto.getInputStreamDtoMap().get(csvFileConfig.getStreamName()).getStreamAttributeDtos().size();

                /*
                Iterate through the CSV file line by line
                 */

            for (CSVRecord record : csvParser) {
                try {
                    synchronized (this) {
                        if (isStopped) {
                            isStopped = false;
                            break;
                        }
                        if (isPaused) {
                            this.wait();
                        }
                    }

                    if (record.size() != attributeSize) {
                        log.warn("No of attribute is not equal to attribute size: " + attributeSize + " is needed" + "in Row no:" + noOfEvents + 1);
                    }
                    String[] attributes = new String[attributeSize];
                    noOfEvents = csvParser.getCurrentLineNumber();

                    for (int i = 0; i < record.size(); i++) {
                        attributes[i] = record.get(i);
                    }

                    //convert Attribute values into event
                    Event event = EventConverter.eventConverter(csvFileConfig.getStreamName(), attributes, executionPlanDto);
                    // TODO: 13/12/16 delete sout
                    System.out.println("Input Event " + Arrays.deepToString(event.getEventData()));
//

                    //send the event to input handler
                    send(csvFileConfig.getStreamName(), event);

                    //delay between two events
                    if (delay > 0) {
                        Thread.sleep(delay);
                    }
                } catch (EventSimulationException e) {
                    log.error("Event dropped due to Error occurred during generating an event" + e.getMessage());
                } catch (InterruptedException e) {
                    log.error("Error occurred during send event" + e.getMessage());
                }
            }

        } catch (IllegalArgumentException e) {
            // TODO: 02/12/16 proper error message
            throw new EventSimulationException("File Parameters are null" + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new EventSimulationException("File not found :" + csvFileConfig.getFileDto().getFileInfo().getFileName());
        } catch (IOException e) {
            throw new EventSimulationException("Error occurred while reading the file");
        } finally {
            try {
                if (in != null && csvParser != null)
                    in.close();
                csvParser.close();
            } catch (IOException e) {
                throw new EventSimulationException("Error occurred during closing the file");
            }
        }
    }


}

