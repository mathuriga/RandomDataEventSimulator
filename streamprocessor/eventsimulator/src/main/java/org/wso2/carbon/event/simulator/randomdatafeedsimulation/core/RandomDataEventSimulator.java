package org.wso2.carbon.event.simulator.randomdatafeedsimulation.core;


import org.wso2.carbon.event.simulator.EventSimulator;
import org.wso2.carbon.event.simulator.constants.EventSimulatorConstants;
import org.wso2.carbon.event.simulator.querydeployer.bean.Event;
import org.wso2.carbon.event.simulator.querydeployer.bean.ExecutionPlanDetails;
import org.wso2.carbon.event.simulator.querydeployer.core.QueryDeployer;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.StreamAttributeDto;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.config.RandomDataSimulationConfig;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.utils.AttributeGenerator;
import org.wso2.carbon.event.simulator.utils.EventCreator;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.utils.RandomDataGenerator;
import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mathuriga on 19/11/16.
 */
public class RandomDataEventSimulator extends EventSimulator {
    private static RandomDataEventSimulator randomDataEventSimulator;
    private List<StreamAttributeDto> streamAttributeDto =new ArrayList<>();

    private RandomDataEventSimulator() {
        super.outputList = new ArrayList<String>();
        super.percentage = 0;
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

    public boolean Send(ExecutionPlanRuntime executionPlanRuntime, ExecutionPlanDetails executionPlan,Event event, int delay) {
//        executionPlanRuntime.addCallback(executionPlan.getOutputStream(), new StreamCallback() {
//
//            @Override
//            public void receive(org.wso2.siddhi.core.event.Event[] events) {
//                System.out.println("Output Event: " + Arrays.deepToString(events));
//                //EventPrinter.print(events);
//            }
//
//        });
        InputHandler inputHandler = executionPlanRuntime.getInputHandler(executionPlan.getInputStream());
        executionPlanRuntime.start();
        try {
            inputHandler.send(event.getEventData());
            Thread.sleep(delay);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean generateRandomEvent(ExecutionPlanDetails executionPlan, RandomDataSimulationConfig randomDataSimulationConfig) {
        boolean status=false;
        List<Long[]> time=new ArrayList<>();
        //if simulation type is RANDOM
        try {
            double noOfEvents = randomDataSimulationConfig.getNoOfEvents();
            this.streamAttributeDto=randomDataSimulationConfig.getStreamDto().getStreamAttributeDto();
        if(randomDataSimulationConfig.getOption().compareTo(EventSimulatorConstants.RandomEventSimulation_RANDOM)==0){

            if (noOfEvents != 0) {
                //generate attribute values
                for (int i = 0; i < noOfEvents; i++) {
                    String[] attributeValue = new String[randomDataSimulationConfig.getStreamDto().getStreamAttributeDto().size()];
                    for (int j = 0; j < randomDataSimulationConfig.getStreamDto().getStreamAttributeDto().size(); j++) {
                        long x=  System.currentTimeMillis();
                        attributeValue[j] = String.valueOf(RandomDataGenerator.generateRandomData(streamAttributeDto.get(j).getAttributeDataType(),streamAttributeDto.get(j).getMin(),streamAttributeDto.get(j).getMax(),streamAttributeDto.get(j).getLength()));
                        long y=System.currentTimeMillis();

                        System.out.println("Time difference to generate Random data"+randomDataSimulationConfig.getStreamDto().getStreamAttributeDto().get(j).getOption()+" : "+(y-x));
                    }

                    //convert event
                    Event event = EventCreator.eventCreator(attributeValue, QueryDeployer.executionPlanDetails);
                    System.out.println("Input Event " + Arrays.deepToString(event.getEventData()));
                    System.out.println("------------------------------------------------------");
                    this.Send(QueryDeployer.executionPlanRuntime, executionPlan, event, randomDataSimulationConfig.getDelay());
                    percentage = ((i + 1) * 100) / noOfEvents;
                    System.out.println("Percentage: " + percentage);
                    status=true;
                }

            }

        }else if(randomDataSimulationConfig.getOption().compareTo(EventSimulatorConstants.RandomEventSimulation_ADVANCED)==0) {
            if (noOfEvents != 0) {
                long startTime=System.currentTimeMillis();
                    //generate attribute values
                    for (int i = 0; i < noOfEvents; i++) {
                        String[] attributeValue = new String[randomDataSimulationConfig.getStreamDto().getStreamAttributeDto().size()];
                        Long[] timelist=new Long[randomDataSimulationConfig.getStreamDto().getStreamAttributeDto().size()];
                        for (int j = 0; j < randomDataSimulationConfig.getStreamDto().getStreamAttributeDto().size(); j++) {
                            long x = System.currentTimeMillis();
                            attributeValue[j] = AttributeGenerator.generateAttributeValue(randomDataSimulationConfig.getStreamDto().getStreamAttributeDto().get(j));
                            long y = System.currentTimeMillis();
                            if((i%100==0)||i==randomDataSimulationConfig.getStreamDto().getStreamAttributeDto().size()-1) {
                                System.out.println("Time difference to generate Random data" + randomDataSimulationConfig.getStreamDto().getStreamAttributeDto().get(j).getOption() + " : " + (y - x));
                                timelist[j]=y-x;
                            }
                        }
                        if((i%100==0)||i==randomDataSimulationConfig.getStreamDto().getStreamAttributeDto().size()-1) {
                            time.add(timelist);
                        }

                        //convert event
                        Event event = EventCreator.eventCreator(attributeValue, QueryDeployer.executionPlanDetails);
                        System.out.println("Input Event " + Arrays.deepToString(event.getEventData()));
                        System.out.println("------------------------------------------------------");
                        this.Send(QueryDeployer.executionPlanRuntime, executionPlan, event, randomDataSimulationConfig.getDelay());
                        percentage = ((i + 1) * 100) / noOfEvents;
                        System.out.println("Percentage: " + percentage);
                        status = true;
                    }
                    System.out.println(Arrays.deepToString(time.toArray()));
                long endtime=System.currentTimeMillis();
                System.out.println("Total Time= "+(endtime-startTime));

                } else if (randomDataSimulationConfig.getNoOfEvents() == 0) {
                    String[] attributeValue = new String[randomDataSimulationConfig.getStreamDto().getStreamAttributeDto().size()];
                    for (int j = 0; j < randomDataSimulationConfig.getStreamDto().getStreamAttributeDto().size(); j++) {
                        attributeValue[j] = AttributeGenerator.generateAttributeValue(randomDataSimulationConfig.getStreamDto().getStreamAttributeDto().get(j));
                    }

                    //convert event
                    Event event = EventCreator.eventCreator(attributeValue, QueryDeployer.executionPlanDetails);
                    this.Send(QueryDeployer.executionPlanRuntime, executionPlan, event, randomDataSimulationConfig.getDelay());
                    status = true;
//                percentage = ((i + 1) * 100) / noOfEvents;

                }
            }
        } catch (Exception e) {
            System.out.println("Error occured during simulation: " + e);
        }


        return status;
    }


}