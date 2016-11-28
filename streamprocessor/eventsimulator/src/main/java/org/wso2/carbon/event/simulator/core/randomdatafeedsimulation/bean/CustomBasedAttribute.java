package org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean;

/**
 * Created by mathuriga on 27/11/16.
 */
public class CustomBasedAttribute extends StreamAttributeDto{
    String[] customDataList;


    public CustomBasedAttribute(String[] customDataList) {
        this.customDataList = customDataList;
    }

    public CustomBasedAttribute() {

    }

    public String[] getCustomDataList() {
        return customDataList;
    }

    public void setCustomDataList(String[] customDataList) {
        this.customDataList = customDataList;
    }

    public void setCustomData(String customData){
        String[] dataList=customData.split(",");
        this.setCustomDataList(dataList);
    }
}
