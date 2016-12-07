package org.wso2.carbon.event.simulator.csvFeedSimulation;

import org.wso2.carbon.event.simulator.bean.StreamConfiguration;
import org.wso2.carbon.event.simulator.csvFeedSimulation.core.FileDto;

/**
 * Created by mathuriga on 30/11/16.
 */
public class CSVFileConfig extends StreamConfiguration{
    private String fileName;
    private String streamName;
    private FileDto fileDto;
    private String delimiter;
    private  int delay;

    public CSVFileConfig() {
        super();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public FileDto getFileDto() {
        return fileDto;
    }

    public void setFileDto(FileDto fileDto) {
        this.fileDto = fileDto;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
}
