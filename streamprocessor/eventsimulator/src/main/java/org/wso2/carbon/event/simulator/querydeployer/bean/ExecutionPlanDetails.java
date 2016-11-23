package org.wso2.carbon.event.simulator.querydeployer.bean;

/**
 * Created by mathuriga on 06/11/16.
 */
public class ExecutionPlanDetails {
    private String inputStreamStreamDefiniton;
    private String query;
    private String inputStream;
    private String outputStream;
    private String[] attributeTypeList;
    public String getInputStreamStreamDefiniton() {
        return inputStreamStreamDefiniton;
    }

    public void setInputStreamStreamDefiniton(String inputStreamStreamDefiniton) {
        this.inputStreamStreamDefiniton = inputStreamStreamDefiniton;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getInputStream() {
        return inputStream;
    }

    public void setInputStream(String inputStream) {
        this.inputStream = inputStream;
    }

    public String getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(String outputStream) {
        this.outputStream = outputStream;
    }

    public String[] getAttributeTypeList() {
        return attributeTypeList;
    }

    public void setAttributeTypeList(String[] attributeTypeList) {
        this.attributeTypeList = attributeTypeList;
    }

}
