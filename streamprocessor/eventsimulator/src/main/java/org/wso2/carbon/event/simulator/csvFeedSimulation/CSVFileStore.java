package org.wso2.carbon.event.simulator.core.csvFeedSimulation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mathuriga on 30/11/16.
 */

// TODO: 30/11/16 is this class required in actual case?
public class CSVFileStore {
    static HashMap<String,ArrayList<String>> fileList=new java.util.HashMap<String,ArrayList<String>>();
    static HashMap<String,CSVFileConfig> fileListtoName=new java.util.HashMap<String,CSVFileConfig>();
    public static HashMap<String, ArrayList<String>> getFileList() {
        return fileList;
    }
}
