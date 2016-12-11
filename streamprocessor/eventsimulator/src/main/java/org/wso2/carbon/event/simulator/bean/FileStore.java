package org.wso2.carbon.event.simulator.bean;

import org.wso2.carbon.event.simulator.csvFeedSimulation.core.FileDto;
import org.wso2.msf4j.formparam.FileInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Created by mathuriga on 08/12/16.
 */
public class FileStore {
    private HashMap<String,FileDto> fileInfoMap=new HashMap<String,FileDto>();
    private static FileStore fileStore;

        public static FileStore getFileStore() {
        if (fileStore == null) {
            synchronized (FileStore.class) {
                if (fileStore == null) {
                    fileStore = new FileStore();
                }
            }
        }
        return fileStore;
    }

    public HashMap<String, FileDto> getFileInfoMap() {
        return fileInfoMap;
    }

    public void setFileInfoMap(HashMap<String, FileDto> fileInfoMap) {
        this.fileInfoMap = fileInfoMap;
    }

    public void addFile(FileDto fileDto){
        fileInfoMap.put(fileDto.getFileInfo().getFileName(),fileDto);
    }

    public void removeFile(String fileName) throws IOException {
        Files.deleteIfExists(Paths.get(System.getProperty("java.io.tmpdir"), fileName));
        fileInfoMap.remove(fileName);
    }

    public Boolean checkExists(String fileName){
        if(Files.exists(Paths.get(System.getProperty("java.io.tmpdir"), fileName))){
            return true;
        }
    return false;
    }
}
