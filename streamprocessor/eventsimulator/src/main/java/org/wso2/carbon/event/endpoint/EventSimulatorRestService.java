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

package org.wso2.carbon.event.endpoint;


import com.google.gson.Gson;
import org.apache.axis2.deployment.DeploymentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.event.simulator.bean.FeedSimulationConfig;
import org.wso2.carbon.event.simulator.csvFeedSimulation.CSVFileConfig;
import org.wso2.carbon.event.simulator.csvFeedSimulation.core.CSVFeedEventSimulator;
import org.wso2.carbon.event.simulator.csvFeedSimulation.core.FileDeployer;
import org.wso2.carbon.event.simulator.singleventsimulator.SingleEventSimulationConfig;
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
 * Simulator REST service is a micro-service built on top of WSO2 msf4j.
 * The REST service provides the capability of simulating events.
 */

@Path("/EventSimulation")
public class EventSimulatorRestService {
    private static final Logger log = LoggerFactory.getLogger(EventSimulatorRestService.class);
    /**
     * Event simulator service executor for event simulator REST service.
     */
    EventSimulatorServiceExecutor eventSimulatorServiceExecutor=new  EventSimulatorServiceExecutor();

    /**
     * Initializes the service classes for resources.
     */
    public EventSimulatorRestService() {
    }

    /**
     * Send single event.
     * singleEventSimulationString: {
                     "streamName":"cseEventStream",
                     "attributeValues":attributeValue
                     };
     * http://127.0.0.1:9090/EventSimulation/singleEventSimulation
     *
     * @param singleEventSimulationString jsonString to be converted to SingleEventSimulationConfig object from the request Json body.
     */
    @POST
    @Path("/singleEventSimulation")
    public void singleEventSimulation(String singleEventSimulationString) throws IOException {
        //parse json string to SingleEventSimulationConfig object
        SingleEventSimulationConfig singleEventSimulationConfiguration = EventSimulatorParser.singleEventSimulatorParser(singleEventSimulationString);
        //start single event simulation
        this.eventSimulatorServiceExecutor.simulateSingleEvent(singleEventSimulationConfiguration);
        log.info("Event is send successfully");
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
        EventSimulatorServiceExecutor.simulateFeedSimulation(feedSimulationConfig);
        String jsonString = new Gson().toJson(message);
        return Response.ok(jsonString, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("/feedSimulation")
    public void stopEvent(){

    }




}
