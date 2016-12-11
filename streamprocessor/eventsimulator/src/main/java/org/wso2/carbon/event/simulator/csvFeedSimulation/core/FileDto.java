package org.wso2.carbon.event.simulator.csvFeedSimulation.core;

import org.wso2.msf4j.formparam.FileInfo;

import java.io.InputStream;

/**
 * Created by mathuriga on 30/11/16.
 */
public class FileDto {
    private FileInfo fileInfo;


    public FileDto(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }


}
