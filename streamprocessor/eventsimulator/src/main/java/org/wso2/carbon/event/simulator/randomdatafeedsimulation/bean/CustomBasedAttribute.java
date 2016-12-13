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
package org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean;

import java.util.Arrays;


/**
 * CustomBasedAttribute represents the Random data generator based on custom data list
 * Constant value to represent to this type is ""CUSTOMDATA"
 * It has data list which is given by user
 *
 * <p>
 * Eg: If user want to generate value for an attribute WSO2 products
 * then user cangive a list of products as
 * "list": "CEP,ESB,DAS" with in json body
 * </p>
 *
 * <p>
 * Eg for json string for configuration
 * {
 * "type": "CUSTOMDATA",
 * "list": "CEP,ESB,DAS"
 * }
 * </p>
 */
public class CustomBasedAttribute extends StreamAttributeDto {

    /**
     * List of custom data value given by user
     */
    private String[] customDataList;


//    public CustomBasedAttribute(String[] customDataList) {
//        this.customDataList = customDataList;
//    }

    public CustomBasedAttribute() {

    }

    public String[] getCustomDataList() {
        return customDataList;
    }

    private void setCustomDataList(String[] customDataList) {
        this.customDataList = customDataList;
    }

    /**
     * Method to split the data list into seperated values and assign it to customDataList
     *
     * @param customData String that has data list values
     *                   Initial string format is ""CEP,Siddhi",ESB,DAS"
     */
    public void setCustomData(String customData) {
        String clonedData = customData.replace("'", "\"");
        String trimmedData = clonedData.substring(1, clonedData.length() - 1);
        String[] dataList = trimmedData.split("((\",\")(?=(?:[^\"]*\"[^\"]*\")*[^ ]*$))");
        System.out.println(Arrays.toString(dataList));
        this.setCustomDataList(dataList);
    }
}

