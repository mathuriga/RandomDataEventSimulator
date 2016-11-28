package org.wso2.carbon.event.simulator.utils;


import org.wso2.carbon.event.simulator.Exception.EventSimulationException;
import org.wso2.carbon.event.simulator.constants.EventSimulatorConstants;
import org.wso2.carbon.event.simulator.querydeployer.bean.Event;
import org.wso2.carbon.event.simulator.querydeployer.bean.ExecutionPlanDetails;
import org.wso2.carbon.event.simulator.querydeployer.bean.StreamAttributeDto;

import java.util.List;


/**
 * Created by mathuriga on 13/11/16.
 */
public class EventConverter {
    private EventConverter( ) {

    }

    public static Event eventConverter(String[] dataList, ExecutionPlanDetails executionPlan) {
        Event event=new Event();
        event.setStreamName(executionPlan.getStreamDefinitionInfoDto().getStreamName());
        Object[] eventData=new Object[executionPlan.getStreamDefinitionInfoDto().getStreamAttributeDtos().size()];
        List<StreamAttributeDto> streamAttributeDto=executionPlan.getStreamDefinitionInfoDto().getStreamAttributeDtos();
        for(int j=0;j<dataList.length;j++){
            String type=executionPlan.getStreamDefinitionInfoDto().getStreamAttributeDtos().get(j).getAttributeType();
            switch (type){
                case EventSimulatorConstants.Attributetype_Integer:
                    try {
                        eventData[j]=Integer.parseInt(String.valueOf(dataList[j]));
                    }catch (NumberFormatException e){
                        throw new EventSimulationException("Incorrect value types for the attribute - " + streamAttributeDto.get(j).getAttributeName() +
                         ", expected" + streamAttributeDto.get(j).getAttributeType() + " : " + e.getMessage());
                    }
                    break;
                case EventSimulatorConstants.Attributetype_Long:
                    try{
                        eventData[j]=Long.parseLong(String.valueOf(dataList[j]));
                    }catch (NumberFormatException e){
                        throw new EventSimulationException("Incorrect value types for the attribute - " + streamAttributeDto.get(j).getAttributeName() +
                                ", expected" + streamAttributeDto.get(j).getAttributeType() + " : " + e.getMessage());
                    }
                    break;
                case EventSimulatorConstants.Attributetype_Float:
                    try {
                        eventData[j]=Float.parseFloat(String.valueOf(dataList[j]));
                    }catch (NumberFormatException e){
                        throw new EventSimulationException("Incorrect value types for the attribute - " + streamAttributeDto.get(j).getAttributeName() +
                                ", expected" + streamAttributeDto.get(j).getAttributeType() + " : " + e.getMessage());
                    }
                    break;
                case EventSimulatorConstants.Attributetype_Double:
                    try{
                        eventData[j]=Double.parseDouble(String.valueOf(dataList[j]));
                    }catch (NumberFormatException e){
                        throw new EventSimulationException("Incorrect value types for the attribute - " + streamAttributeDto.get(j).getAttributeName() +
                                ", expected" + streamAttributeDto.get(j).getAttributeType() + " : " + e.getMessage());
                    }
                    break;
                case EventSimulatorConstants.Attributetype_String:
                    eventData[j]=dataList[j]; break;
                case EventSimulatorConstants.Attributetype_Boolean:
                    if (String.valueOf(dataList[j]).equalsIgnoreCase("true") || String.valueOf(dataList[j]).equalsIgnoreCase("false")) {
                        eventData[j] = Boolean.parseBoolean(String.valueOf(dataList[j]));
                    } else{
                        throw new EventSimulationException("Incorrect value types for the attribute - " + streamAttributeDto.get(j).getAttributeName() +
                                ", expected" + streamAttributeDto.get(j).getAttributeType() + " : " + new IllegalArgumentException().getMessage());
                    }
                    break;
            }

        }
        event.setEventData(eventData);
        return event;
    }
}
