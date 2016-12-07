package org.wso2.carbon.event.simulator.csvFeedSimulation.core;

import org.apache.axis2.deployment.DeploymentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.msf4j.formparam.FileInfo;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mathuriga on 05/12/16.
 */
public class FileDeployer {
    private static final Log log = LogFactory.getLog(FileDeployer.class);
    private static FileDeployer fileDeployer;
    public static FileDeployer getFileDeployer() {
        if (fileDeployer == null) {
            synchronized (FileDeployer.class) {
                if (fileDeployer == null) {
                    fileDeployer = new FileDeployer();
                }
            }
        }
        return fileDeployer;
    }

    public void deployFile(FileInfo fileInfo, InputStream inputStream) throws DeploymentException {
        try {
            System.out.println(fileInfo.getFileName());
           processDeploy(fileInfo,inputStream);
        } catch (Throwable t) {
            log.error("Could not deploy CSV file : " + fileInfo.getFileName(), t);
            throw new DeploymentException("CSV file not deployed and in inactive state :  " + fileInfo.getFileName(), t);
        }
    }

    public void undeployFile(String fileName) throws DeploymentException {
        try {
            processUndeploy(fileName);
        } catch (Throwable t) {
            log.error("Could not undeploy CSV file : " + fileName, t);
            throw new DeploymentException("CSV file could not be undeployed :  " + fileName, t);
        }

    }

    public void processUndeploy(String fileName){
        CSVFeedEventSimulator csvFeedEventSimulator=CSVFeedEventSimulator.getCSVFeedEventSimulator();
        csvFeedEventSimulator.getCsvFileInfoMap().remove(fileName);
        log.info("CSV file " + fileName + " Undeployed successfully.");
    }

    public void processDeploy(FileInfo fileInfo,InputStream inputStream) throws Exception {
        if(validateFile(fileInfo)){
            CSVFeedEventSimulator csvFeedEventSimulator=CSVFeedEventSimulator.getCSVFeedEventSimulator();
            if (Files.exists(Paths.get(System.getProperty("java.io.tmpdir"), fileInfo.getFileName()))) {
                Files.deleteIfExists(Paths.get(System.getProperty("java.io.tmpdir"), fileInfo.getFileName()));
                if(csvFeedEventSimulator.getCsvFileInfoMap().containsKey(fileInfo.getFileName())){
                    log.warn("File is already exists: " + fileInfo.getFileName());
                    csvFeedEventSimulator.getCsvFileInfoMap().remove(fileInfo.getFileName());
                }
            }
            FileDto fileDto=new FileDto(fileInfo,inputStream);
            Files.copy(inputStream, Paths.get(System.getProperty("java.io.tmpdir"), fileInfo.getFileName()));
            csvFeedEventSimulator.getCsvFileInfoMap().put(fileInfo.getFileName(),fileDto);
        }
        log.info("CSV file " + fileInfo.getFileName() + " deployed successfully.");
    }

    public boolean validateFile(FileInfo fileInfo) throws Exception {
        String fileName=fileInfo.getFileName();
        //Check filename for \ charactors. This cannot be handled at the lower stages.
        if (fileName.matches("(.*[\\\\].*[/].*|.*[/].*[\\\\].*)")) {

            log.error("CSV file Validation Failure: one or more of the following illegal characters are in " +
                    "the package.\n ~!@#$;%^*()+={}[]| \\<>");
            throw new Exception("CSV file Validation Failure: one or more of the following illegal characters " +
                    "are in the package. ~!@#$;%^*()+={}[]| \\<>");
        }
        if(!validateFileExtension(fileName)){
            // TODO: 06/12/16 proper error message
            log.error("CSV file Extension validation failure : "+ ".csv" +"is required for file extension");
            throw new Exception("CSV file Extension validation failure : "+ ".csv" +"is required for file extension");
        }
        return true;
    }

    public boolean validateFileExtension(String fileName) {
        Pattern fileExtensionPattern = Pattern.compile("([^\\s]+(\\.(?i)(csv))$)");
        Matcher matcher = fileExtensionPattern.matcher(fileName);
        if(matcher.matches()){
            return true;
        }
        return false;
    }
}
