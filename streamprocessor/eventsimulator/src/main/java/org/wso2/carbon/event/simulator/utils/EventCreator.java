package org.wso2.carbon.event.simulator.utils;


import org.wso2.carbon.event.simulator.constants.EventSimulatorConstants;
import org.wso2.carbon.event.simulator.querydeployer.bean.Event;
import org.wso2.carbon.event.simulator.querydeployer.bean.ExecutionPlanDetails;


/**
 * Created by mathuriga on 13/11/16.
 */
public class EventCreator {
    private EventCreator( ) {

    }

    public static Event eventCreator(String[] dataList, ExecutionPlanDetails executionPlan) {
        Event event=new Event();
        event.setNoOfAttribute(executionPlan.getAttributeTypeList().length);
        event.setInputStream(executionPlan.getInputStream());
        Object[] eventData=new Object[executionPlan.getAttributeTypeList().length];
        for(int j=0;j<dataList.length;j++){
            String type=executionPlan.getAttributeTypeList()[j];
            switch (type){
                case EventSimulatorConstants.Attributetype_Integer:
                    eventData[j]=Integer.parseInt(String.valueOf(dataList[j])); break;
                case EventSimulatorConstants.Attributetype_Long:
                    eventData[j]=Long.parseLong(String.valueOf(dataList[j])); break;
                case EventSimulatorConstants.Attributetype_Float:
                    eventData[j]=Float.parseFloat(String.valueOf(dataList[j])); break;
                case EventSimulatorConstants.Attributetype_Double:
                    eventData[j]=Double.parseDouble(String.valueOf(dataList[j])); break;
                case EventSimulatorConstants.Attributetype_String:
                    eventData[j]=dataList[j]; break;
                case EventSimulatorConstants.Attributetype_Boolean:
                    eventData[j]=Boolean.parseBoolean(String.valueOf(dataList[j])); break;
            }

        }
        event.setEventData(eventData);
        return event;
    }
}
