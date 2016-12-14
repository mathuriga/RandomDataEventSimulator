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
package org.wso2.carbon.event.simulator.csvFeedSimulation.core;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.event.simulator.bean.FileStore;
import org.wso2.msf4j.formparam.FileInfo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class used to deploy and undeploy the CSV file which is uploaded by user.
 * File deployer class creates singleton object because It holds only uploaded file details as a
 * FileStore
 *
 * @see FileStore
 */

public class FileDeployer {
    private static final Log log = LogFactory.getLog(FileDeployer.class);
    /**
     * FileStore object which holds In memory for uploaded file details
     */
    private FileStore fileStore;

    /**
     * FileDeployer Object which has private static access to create singleton object
     *
     * @link org.wso2.carbon.event.simulator.csvFeedSimulation.core.FileDeployer#getFileDeployer()
     */
    private static FileDeployer fileDeployer;

    /**
     * Method Singleton FileDeployer object
     *
     * @return fileDeployer
     */
    public static FileDeployer getFileDeployer() {
        if (fileDeployer == null) {
            synchronized (FileDeployer.class) {
                if (fileDeployer == null) {
                    fileDeployer = new FileDeployer(FileStore.getFileStore());
                }
            }
        }
        return fileDeployer;
    }

    /**
     * Initialize the FileDeployer with FileStore property
     *
     * @param fileStore FileStore Object
     */
    private FileDeployer(FileStore fileStore) {
        this.fileStore = fileStore;
    }

    /**
     * Method to deploy the uploaded file. It calls processDeploy method with in that
     *
     * @param fileInfo    FileInfo Bean supports by MSF4J
     * @param inputStream InputStream Of file
     * @throws Exception throw exception if anything exception occurred
     * @link org.wso2.carbon.event.simulator.csvFeedSimulation.core.FileDeployer#processDeploy(FileInfo, InputStream)
     * @see FileInfo
     */
    public void deployFile(FileInfo fileInfo, InputStream inputStream) throws Exception {
        try {
            processDeploy(fileInfo, inputStream);
        } catch (Throwable t) {
            log.error("Could not deploy CSV file : " + fileInfo.getFileName(), t);
            throw new Exception("CSV file not deployed :  " + fileInfo.getFileName(), t);
        }
    }

    /**
     * Method to un deploy the uploaded file. It calls processUndeploy method with in that
     *
     * @param fileName File Name of uploaded CSV file
     * @throws Exception throw exception if anything exception occurred
     * @link processUndeploy
     */
    public void undeployFile(String fileName) throws Exception {
        try {
            processUndeploy(fileName);
        } catch (Throwable t) {
            log.error("Could not undeploy CSV file : " + fileName, t);
            throw new Exception("CSV file could not be undeployed :  " + fileName, t);
        }

    }

    /**
     * Method to remove the file from in memory
     *
     * @param fileName File Name
     * @throws IOException it throws IOException if anything occurred while
     *                     delete the file from temp directory and in memory
     */
    private void processUndeploy(String fileName) throws IOException {
        if (fileStore.checkExists(fileName)) {
            fileStore.removeFile(fileName);
        }
        log.info("CSV file Un deployed successfully :" + fileName);
    }

    /**
     * Method to add the file details in hashmap
     * before inserts the key value pair into map it validates the CSV file extension
     *
     * <p>
     * Before adding the file info it Check if file is already exist. if so existing file
     * will be delete by giving warning and new file wile be add to the map
     * </p>
     * @param fileInfo    FileInfo bean
     * @param inputStream Input Stream of file
     * @throws Exception throw exception if anything exception occurred
     */
    private void processDeploy(FileInfo fileInfo, InputStream inputStream) throws Exception {
        String fileName = fileInfo.getFileName();
        // Validate file extension
        if (validateFile(fileName)) {
            //Check if file is already exist. if so existing file will be delete by giving warning
            //and new file wile be add to the map
            if (fileStore.checkExists(fileName)) {
                fileStore.removeFile(fileInfo.getFileName());
                //todo remove warn
                log.warn("File is already exists: " + fileInfo.getFileName());
            }
            try {
                FileDto fileDto = new FileDto(fileInfo);
                Files.copy(inputStream, Paths.get(System.getProperty("java.io.tmpdir"), fileInfo.getFileName()));
                fileStore.addFile(fileDto);
            } catch (IOException e) {
                log.error("Error while Copying the file " + e.getMessage(), e);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        }
        // TODO: 14/12/16 move it to try block
        log.info("CSV file deployed successfully :" + fileInfo.getFileName());
    }

    /**
     * Method to validate CSV file Extension
     *
     * @param fileName File name
     * @return true if CSV file extension is in correct format
     * @throws Exception throw exception if anything exception occurred
     * @link org.wso2.carbon.event.simulator.csvFeedSimulation.core.FileDeployer#validateFileExtension(java.lang.String)
     */
    private boolean validateFile(String fileName) throws Exception {
//        //Check filename for \ charactors. This cannot be handled at the lower stages.
//        if (fileName.matches("(.*[\\\\].*[/].*|.*[/].*[\\\\].*)")) {
//            log.error("CSV file Validation Failure: one or more of the following illegal characters are in " +
//                    "the package.\n ~!@#$;%^*()+={}[]| \\<>");
//            throw new Exception("CSV file Validation Failure: one or more of the following illegal characters " +
//                    "are in the package. ~!@#$;%^*()+={}[]| \\<>");
//        }
        //validate CSV file Extension
        if (!validateFileExtension(fileName)) {
            // TODO: 06/12/16 proper error message
            log.error("CSV file Extension validation failure : " + ".csv" + "is required for file extension");
            throw new Exception("CSV file Extension validation failure "+ fileName + " : " + ".csv" + " is required for file extension");
        }
        return true;
    }

    /**
     * Method to validate CSV file Extension. It uses regular expression to validate .CSV extension
     *
     * @param fileName File Name
     * @return true if CSV file extension is in correct format
     */
    private boolean validateFileExtension(String fileName) {
        Pattern fileExtensionPattern = Pattern.compile("([^\\s]+(\\.(?i)(csv))$)");
        Matcher matcher = fileExtensionPattern.matcher(fileName);
        return matcher.matches();
    }
}
