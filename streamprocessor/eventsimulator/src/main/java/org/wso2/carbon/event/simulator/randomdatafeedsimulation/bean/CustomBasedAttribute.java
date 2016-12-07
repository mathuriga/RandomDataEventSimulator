package org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean;

import java.util.Arrays;


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
        System.out.println(customData);


        String dummy=customData.replace("'","\"");
        String x=dummy.replaceFirst("\"", "");
       // String[] dataList = customData.split(Pattern.quote(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
       String[] dataList = x.split("((\",\")(?=(?:[^\"]*\"[^\"]*\")*[^ ]*$))");
        System.out.println(Arrays.toString(dataList));
        this.setCustomDataList(dataList);
    }
}

