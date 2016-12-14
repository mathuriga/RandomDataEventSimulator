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
// TODO: 14/12/16 remove this exception
import org.apache.axis2.deployment.DeploymentException;
//todo use apache commons log
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.event.simulator.bean.FeedSimulationConfig;
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
    private EventSimulatorServiceExecutor eventSimulatorServiceExecutor;

    /**
     * Initializes the service classes for resources.
     */
    // TODO: 14/12/16 change it as util 
    public EventSimulatorRestService() {
        eventSimulatorServiceExecutor = new EventSimulatorServiceExecutor();
    }

    /**
     * Send single event.
     * todo give as an eg
     * singleEventSimulationString: {
     * "streamName":"cseEventStream",
     * "attributeValues":attributeValue
     * };
     * http://127.0.0.1:9090/EventSimulation/singleEventSimulation
     *
     * @param singleEventSimulationString jsonString to be converted to SingleEventSimulationConfig object from the request Json body.
     */
    @POST
    @Path("/singleEventSimulation")
    //todo change the param name to simulationstring
    public void singleEventSimulation(String singleEventSimulationString) throws IOException {
        //parse json string to SingleEventSimulationConfig object
        SingleEventSimulationConfig singleEventSimulationConfiguration = EventSimulatorParser.singleEventSimulatorParser(singleEventSimulationString);
        //start single event simulation
        // TODO: 14/12/16 remove this 
        this.eventSimulatorServiceExecutor.simulateSingleEvent(singleEventSimulationConfiguration);
        // TODO: 14/12/16 remove info and include debug log 
        log.info("Event is send successfully");
    }

    /**
     * Deploy CSV file
     * <p>
     * This function use FormDataParam annotation. WSO@2 MSF4J supports this annotation and multipart/form-data content type.
     * <p>
     * </p>
     * The FormDataParam annotation supports complex types and collections (such as List, Set and SortedSet),
     * with the multipart/form-data content type and supports files along with form field submissions.
     * It supports directly to get the file objects in a file upload by using the @FormDataParam  annotation.
     * This annotation can be used with all FormParam supported data types plus file and bean types as well as InputStreams.
     * </p>
     *
     * @param fileInfo        FileInfo bean to hold the filename and the content type attributes of the particular InputStream
     * @param fileInputStream InputStream of the file
     * @return Response of completion of process
     * <p>
     * http://127.0.0.1:9090/EventSimulation/fileDeploy
     */
    @POST
    @Path("/fileDeploy")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    // TODO: 14/12/16  
    public Response upLoadFileService(@FormDataParam("file") FileInfo fileInfo,
                                      @FormDataParam("file") InputStream fileInputStream) throws Exception {

        /*
        Get singleton instance of FileDeployer
         */
        // TODO: 14/12/16 fileupload 
        FileDeployer fileDeployer = FileDeployer.getFileDeployer();
        try {
            fileDeployer.deployFile(fileInfo, fileInputStream);
        } catch (DeploymentException e) {
            throw new DeploymentException(e);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return Response.ok().entity("File uploaded").build();
    }

    /**
     * Delete the file
     * <p>
     * This function use FormDataParam annotation. WSO@2 MSF4J supports this annotation and multipart/form-data content type.
     * <p>
     *
     * @param fileName File Name
     * @return Response of completion of process
     * @throws Exception throw exception if anything exception occurred
     *                   <p>
     *                   http://127.0.0.1:9090/EventSimulation/fileUndeploy
     */
    @POST
    @Path("/fileUndeploy")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response deleteFileService(@FormDataParam("fileName") String fileName) throws Exception {

        /*
         * Get singleton instance of FileDeployer
         */
        FileDeployer fileDeployer = FileDeployer.getFileDeployer();
        try {
            fileDeployer.undeployFile(fileName);
        } catch (DeploymentException e) {
            throw new DeploymentException(e);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
            //e.printStackTrace();
        }
        return Response.ok().entity("File Un deployed").build();
    }


    /**
     * This method produces service for feed simulation
     * <p>
     * For an execution plan It may have one or more input streams.
     * this method provides the capability to simulate each input streams in different cases.
     * such as simulate using CSV File, simulate using Random Data and simulate using
     * database resource.
     * </p>
     * <p>
     * FeedSimulation Configuration Json String Sample
     * feedSimulationConfiguration= {
     * "orderByTimeStamp" : "false",
     * "streamConfiguration" :[
     * {
     * "simulationType" : "RandomDataSimulation",
     * "streamName": "cseEventStream",
     * "events": "5",
     * "delay": "1000",
     * "attributeConfiguration":[
     * {
     * "type": "PROPERTYBASED",
     * "category": "Contact",
     * "property": "Full Name",
     * },
     * {
     * "type": "CUSTOMDATA",
     * "list": "WSO2"
     * },
     * {
     * "type": "REGEXBASED",
     * "pattern": "[+]?[0-9]*\\.?[0-9]+"
     * },
     * {
     * "type": "PRIMITIVEBASED",
     * "min": "2",
     * "max": "200",
     * "length": "2",
     * }
     * ]
     * },
     * {
     * "simulationType" : "FileFeedSimulation",
     * "streamName" : "cseEventStream2",
     * "fileName"   : "cseteststream2.csv",
     * "delimiter"  : ",",
     * "delay"		 : "1000"
     * }
     * ]
     * };
     *
     * @param feedSimulationConfigDetails jsonString to be converted to FeedSimulationConfig object from the request Json body.
     * @return Response of completion of process
     * @throws InterruptedException Interrupted Exception
     *
     * http://127.0.0.1:9090/EventSimulation/feedSimulation
     */
    @POST
    @Path("/feedSimulation")
    public Response feedSimulation(String feedSimulationConfigDetails) throws InterruptedException {
        //parse json string to FeedSimulationConfig object
        FeedSimulationConfig feedSimulationConfig = EventSimulatorParser.feedSimulationConfigParser(feedSimulationConfigDetails);
        //start feed simulation
        // TODO: 14/12/16 change the name 
        this.eventSimulatorServiceExecutor.simulateFeedSimulation(feedSimulationConfig);
        String jsonString = new Gson().toJson("success");
        return javax.ws.rs.core.Response.ok(jsonString, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * Stop the simulation process
     * @return Response of completion of process
     * @throws InterruptedException Interrupted Exception
     *
     * http://127.0.0.1:9090/EventSimulation/feedSimulation/stop
     */
    @POST
    @Path("/feedSimulation/stop")
    public Response stop() throws  InterruptedException {

        //stop feed simulation
        this.eventSimulatorServiceExecutor.stop();
        String jsonString = new Gson().toJson("success");
        return javax.ws.rs.core.Response.ok(jsonString, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * pause the simulation process
     * @return Response of completion of process
     * @throws InterruptedException Interrupted Exception
     *
     * http://127.0.0.1:9090/EventSimulation/feedSimulation/pause
     */
    @POST
    @Path("/feedSimulation/pause")
    public Response pause() throws  InterruptedException {

        //pause feed simulation
        this.eventSimulatorServiceExecutor.pause();
        String jsonString = new Gson().toJson("success");
        return javax.ws.rs.core.Response.ok(jsonString, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * resume the simulation
     * @return Response of completion of process
     * @throws InterruptedException Interrupted Exception
     *
     * http://127.0.0.1:9090/EventSimulation/feedSimulation/resume
     */
    @POST
    @Path("/feedSimulation/resume")
    public Response resume() throws  InterruptedException {
        //pause feed simulation
        this.eventSimulatorServiceExecutor.resume();
        String jsonString = new Gson().toJson("success");
        return javax.ws.rs.core.Response.ok(jsonString, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*").build();
    }


}
