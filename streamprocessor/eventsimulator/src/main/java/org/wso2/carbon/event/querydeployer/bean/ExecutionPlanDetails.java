package org.wso2.carbon.event.simulator.querydeployer.bean;

/**
 * Created by mathuriga on 06/11/16.
 */
public class ExecutionPlanDetails {
    private StreamDefinitionInfoDto streamDefinitionInfoDto;
    private String query;
    private String inputStream;
    private String outputStream;

    public StreamDefinitionInfoDto getStreamDefinitionInfoDto() {
        return streamDefinitionInfoDto;
    }

    public void setStreamDefinitionInfoDto(StreamDefinitionInfoDto streamDefinitionInfoDto) {
        this.streamDefinitionInfoDto = streamDefinitionInfoDto;
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
}
