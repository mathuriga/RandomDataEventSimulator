package org.wso2.carbon.event.simulator.core.csvFeedSimulation.core;

import org.wso2.msf4j.formparam.FileInfo;

import java.io.InputStream;

/**
 * Created by mathuriga on 30/11/16.
 */
public class FileDto {
    private FileInfo fileInfo;
    private InputStream fileInputStream;

    public FileDto(FileInfo fileInfo, InputStream fileInputStream) {
        this.fileInfo = fileInfo;
        this.fileInputStream = fileInputStream;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }
}
