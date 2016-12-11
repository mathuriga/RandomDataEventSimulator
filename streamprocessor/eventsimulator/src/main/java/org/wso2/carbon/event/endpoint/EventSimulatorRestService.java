package org.wso2.carbon.event.endpoint;


import com.google.gson.Gson;
import org.apache.axis2.deployment.DeploymentException;
import org.wso2.carbon.event.simulator.bean.FeedSimulationConfig;
import org.wso2.carbon.event.simulator.csvFeedSimulation.CSVFileConfig;
import org.wso2.carbon.event.simulator.csvFeedSimulation.core.CSVFeedEventSimulator;
import org.wso2.carbon.event.simulator.csvFeedSimulation.core.FileDeployer;
import org.wso2.carbon.event.simulator.singleventsimulator.SingleEventSimulationConfig;
import org.wso2.carbon.event.simulator.singleventsimulator.SingleEventSimulator;
import org.wso2.carbon.event.simulator.utils.EventSimulatorParser;
import org.wso2.carbon.event.simulator.utils.EventSimulatorServiceExecutor;
import org.wso2.msf4j.formparam.FileInfo;
import org.wso2.msf4j.formparam.FormDataParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by mathuriga on 12/11/16.
 */

@Path("/EventSimulation")
public class EventSimulatorEndpoint {


    @POST
    @Path("/singleEventSimulation")
    public Response singleEventSimulation(String singleEventSimulationConfig) throws IOException {
        //JSON from String to Object
        String message = null;
        SingleEventSimulator singleEventSimulator=SingleEventSimulator.getSingleEventSimulator();
        SingleEventSimulationConfig singleEventSimulationConfigInstance= (SingleEventSimulationConfig) singleEventSimulator.configureSingleEventSimulation(singleEventSimulationConfig);


        if(singleEventSimulator.send(singleEventSimulationConfigInstance)){
            message= "Event is send successfully";
        }
        System.out.println(message);
        String jsonString = new Gson().toJson(message);
        return Response.ok(jsonString, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("/fileFeedSimulation")
    public Response fileFeedSimulation(String fileConfig) throws IOException {
        String message = null;
        System.out.println("lol");
        CSVFeedEventSimulator csvFeedEventSimulator=new CSVFeedEventSimulator();
        CSVFileConfig csvFileConfig= EventSimulatorParser.fileEventSimulatorParser(fileConfig);

        if(csvFeedEventSimulator.send(csvFileConfig)){
            message= "Event is send successfully";
        }

        String jsonString = new Gson().toJson(message);
        return Response.ok(jsonString, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*").build();

    }


    @POST
    @Path("/fileDeploy")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upLoadFileService(@FormDataParam("file") FileInfo fileInfo,
                                       @FormDataParam("file") InputStream fileInputStream) throws DeploymentException {

        String message = null;

        FileDeployer fileDeployer=FileDeployer.getFileDeployer();
        try {
            fileDeployer.deployFile(fileInfo,fileInputStream);
        } catch (DeploymentException e) {
            throw new DeploymentException(e);
        }

        String jsonString = new Gson().toJson(message);
        return Response.ok(jsonString, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("/fileUndeploy")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upLoadFileService(@FormDataParam("fileName") String fileName) throws DeploymentException {

        String message = null;
        FileDeployer fileDeployer=FileDeployer.getFileDeployer();
        try {
            fileDeployer.undeployFile(fileName);
        } catch (DeploymentException e) {
            throw new DeploymentException(e);
        }

        String jsonString = new Gson().toJson(message);
        return Response.ok(jsonString, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("/feedSimulation")
    public Response feedSimulation(String feedSimulationConfigDetails) throws DeploymentException, InterruptedException {

        String message = null;

        FeedSimulationConfig feedSimulationConfig=EventSimulatorParser.feedSimulationConfigParser(feedSimulationConfigDetails);
        EventSimulatorServiceExecutor.simulate(feedSimulationConfig);
        String jsonString = new Gson().toJson(message);
        return Response.ok(jsonString, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("/feedSimulation")
    public void stopEvent(){

    }




}
