package org.wso2.carbon.event.simulator.core.csvFeedSimulation;

import org.wso2.carbon.event.simulator.core.csvFeedSimulation.core.FileDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathuriga on 30/11/16.
 */
public class CSVFileConfig {
    private String fileName;
    private String inputStream;
    private FileDto fileDto;
    private char delimiter;
    private  int delay;

    public CSVFileConfig(String fileName, String inputStream, FileDto fileDto, char delimiter, int delay) {
        this.fileName = fileName;
        this.inputStream = inputStream;
        this.fileDto = fileDto;
        this.delimiter = delimiter;
        this.delay = delay;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getInputStream() {
        return inputStream;
    }

    public void setInputStream(String inputStream) {
        this.inputStream = inputStream;
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

    public char getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }
}
