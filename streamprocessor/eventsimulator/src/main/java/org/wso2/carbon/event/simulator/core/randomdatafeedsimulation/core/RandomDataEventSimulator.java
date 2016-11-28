package org.wso2.carbon.event.simulator.randomdatafeedsimulation.core;


import com.google.gson.Gson;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.wso2.carbon.event.simulator.EventSimulator;
import org.wso2.carbon.event.simulator.constants.RandomDataGeneratorConstants;
import org.wso2.carbon.event.simulator.querydeployer.bean.Event;
import org.wso2.carbon.event.simulator.querydeployer.bean.ExecutionPlanDetails;
import org.wso2.carbon.event.simulator.querydeployer.bean.StreamDefinitionInfoDto;
import org.wso2.carbon.event.simulator.querydeployer.core.QueryDeployer;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.CustomBasedAttribute;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.PrimitiveBasedAttribute;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.PropertyBasedAttributeDto;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.RandomDataSimulationConfig;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.RegexBasedAttributeDto;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.StreamAttributeDto;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.utils.AttributeGenerator;
import org.wso2.carbon.event.simulator.utils.EventConverter;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

/**
 * Created by mathuriga on 19/11/16.
 */
public class RandomDataEventSimulator implements EventSimulator {
    private static final Log log = LogFactory.getLog(RandomDataEventSimulator.class);
    private static RandomDataEventSimulator randomDataEventSimulator;
    private List<StreamAttributeDto> streamAttributeDto = new ArrayList<>();

    private RandomDataEventSimulator() {
    }

    public static RandomDataEventSimulator getRandomDataEventSimulator() {
        if (randomDataEventSimulator == null) {
            synchronized (RandomDataEventSimulator.class) {
                if (randomDataEventSimulator == null) {
                    randomDataEventSimulator = new RandomDataEventSimulator();
                }
            }
        }
        return randomDataEventSimulator;
    }

    public RandomDataSimulationConfig configureSimulation(String eventSimulationConfig) {
        JSONObject jsonObject = new JSONObject(eventSimulationConfig);
        //set properties to RandomDataSimulationConfig
        RandomDataSimulationConfig randomDataSimulationConfig = new RandomDataSimulationConfig();
        randomDataSimulationConfig.setStreamName((String) jsonObject.get("streamName"));
        randomDataSimulationConfig.setEvents(jsonObject.getInt("events"));
        randomDataSimulationConfig.setDelay(jsonObject.getInt("delay"));
        List<StreamAttributeDto> attributeSimulation = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("attributeSimulation");
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.getJSONObject(i).getString("type").compareTo(RandomDataGeneratorConstants.PropertyBasedAttribute) == 0) {
                PropertyBasedAttributeDto propertyBasedAttributeDto = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), PropertyBasedAttributeDto.class);
                attributeSimulation.add(propertyBasedAttributeDto);
            } else if (jsonArray.getJSONObject(i).getString("type").compareTo(RandomDataGeneratorConstants.RegexBasedAttribute) == 0) {
                RegexBasedAttributeDto regexBasedAttributeDto = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), RegexBasedAttributeDto.class);
                attributeSimulation.add(regexBasedAttributeDto);
            } else if (jsonArray.getJSONObject(i).getString("type").compareTo(RandomDataGeneratorConstants.PrimitiveBasedAttribute) == 0) {
                PrimitiveBasedAttribute primitiveBasedAttribute = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), PrimitiveBasedAttribute.class);
                attributeSimulation.add(primitiveBasedAttribute);
            } else if (jsonArray.getJSONObject(i).getString("type").compareTo(RandomDataGeneratorConstants.CustomDataBasedAttribute) == 0) {
                CustomBasedAttribute customBasedAttribute = new CustomBasedAttribute();
                customBasedAttribute.setCustomData(jsonArray.getJSONObject(i).getString("customDataList"));
                attributeSimulation.add(customBasedAttribute);
            }
        }
        randomDataSimulationConfig.setAttributeSimulation(attributeSimulation);

        return randomDataSimulationConfig;
    }


    public boolean send(RandomDataSimulationConfig randomDataSimulationConfig) {
        //InputHandler inputHandler = executionPlanRuntime.getInputHandler(executionPlan.getInputStream());
        QueryDeployer.executionPlanRuntime.start();

        EventCreator eventCreator = new EventCreator(QueryDeployer.executionPlanDetails, randomDataSimulationConfig);
        Thread eventCreatorThread = new Thread(eventCreator);
        eventCreatorThread.start();

        return true;
    }

    @Override
    public void send(Event event) {
        try {
            QueryDeployer.inputHandler.send(event.getEventData());
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("Error occurred during send event :" + e.getMessage());
        }
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


    //runnable class for event creator
    class EventCreator implements Runnable {
        ExecutionPlanDetails executionPlanDetails;
        RandomDataSimulationConfig randomDataSimulationConfig;
        double percentage=0;

        List<StreamAttributeDto> streamAttributeDto = new ArrayList<>();
        private final Object lock = new Object();
        private volatile boolean isPaused = false;
        private volatile boolean isStopped = false;

        public EventCreator(ExecutionPlanDetails executionPlanDetails, RandomDataSimulationConfig randomDataSimulationConfig) {
            this.executionPlanDetails = executionPlanDetails;
            this.randomDataSimulationConfig = randomDataSimulationConfig;

        }

        @Override
        public void run() {
            try {
                int delay = randomDataSimulationConfig.getDelay();
                if (delay <= 0) {
                    log.warn("Events will be sent continuously since the delay between events are set to "
                            + delay + "milliseconds");
                    delay = 0;
                }


                if (randomDataSimulationConfig.getEvents() < 0) {
                    log.error("No of events to be generated can't be in negative values");
                }
                double noOfEvents = randomDataSimulationConfig.getEvents();
                this.streamAttributeDto = randomDataSimulationConfig.getAttributeSimulation();
                StreamDefinitionInfoDto streamDefinitionInfoDto = executionPlanDetails.getStreamDefinitionInfoDto();

                try {
                    //generate dummi attriutes to warmup random data generation process
                    String[] dummiAttribute = new String[randomDataSimulationConfig.getAttributeSimulation().size()];
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < randomDataSimulationConfig.getAttributeSimulation().size(); j++) {
                            dummiAttribute[j] = AttributeGenerator.generateAttributeValue(randomDataSimulationConfig.getAttributeSimulation().get(j), streamDefinitionInfoDto.getStreamAttributeDtos().get(j).getAttributeType());
                        }
                    }
                    dummiAttribute = null;
                } catch (Exception e) {
                    log.error("Error occurred during generating dummi attributes");
                }

                //Generate Random Data

                for (int i = 0; i < noOfEvents; i++) {
                    int noOfAttributes = randomDataSimulationConfig.getAttributeSimulation().size();
                    if (!isPaused) {
                        try {
                            String[] attributeValue = new String[noOfAttributes];
                            for (int j = 0; j < noOfAttributes; j++) {
                                attributeValue[j] = AttributeGenerator.generateAttributeValue(randomDataSimulationConfig.getAttributeSimulation().get(j), streamDefinitionInfoDto.getStreamAttributeDtos().get(j).getAttributeType());
                            }
                            //convert event
                            Event event = EventConverter.eventConverter(attributeValue, QueryDeployer.executionPlanDetails);
                            System.out.println("Input Event " + Arrays.deepToString(event.getEventData()));
                            System.out.println("------------------------------------------------------");

                            //send event
                            //Send(QueryDeployer.executionPlanRuntime, executionPlanDetails, event, randomDataSimulationConfig.getDelay());
                            send(event);
                            if (delay > 0) {
                                Thread.sleep(delay);
                            }
                            percentage = ((i + 1) * 100) / noOfEvents;
                            System.out.println("Percentage: " + percentage);

                        } catch (Exception e) {
                            log.error("Error occurred : Failed to create an event");

                        }
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

                }
            } catch (Exception e) {
                log.error("Error occurred");
            }

        }


        public void pause() {
            isPaused = true;
        }

        public void resume() {
            isPaused = false;
            synchronized (lock) {
                lock.notifyAll();
            }
        }

        public void stop() {
            isPaused = true;
            isStopped = true;
            synchronized (lock) {
                lock.notifyAll();
            }
        }

    }
}