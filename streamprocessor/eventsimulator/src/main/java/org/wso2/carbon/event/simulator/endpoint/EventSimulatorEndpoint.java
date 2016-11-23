package org.wso2.carbon.event.simulator.endpoint;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.wso2.carbon.event.simulator.querydeployer.core.QueryDeployer;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.config.RandomDataSimulationConfig;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.core.RandomDataEventSimulator;
import org.wso2.carbon.event.simulator.singleventsimulator.SingleEventSimulationConfig;
import org.wso2.carbon.event.simulator.singleventsimulator.SingleEventSimulator;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


/**
 * Created by mathuriga on 12/11/16.
 */

@Path("/RandomEvent")
public class EventSimulatorEndpoint {

    //Random event generator
    @POST
    @Path("/singleEvent")
    public Response singleEventSimulation(String singleEventSimulationConfig) throws IOException {
        //JSON from String to Object
        String message = null;
        ObjectMapper mapper = new ObjectMapper();
        SingleEventSimulationConfig singleEventSimulationConfigInstance = mapper.readValue(singleEventSimulationConfig, SingleEventSimulationConfig.class);
        System.out.println(singleEventSimulationConfigInstance.getStreamName());
        System.out.println(String.valueOf(singleEventSimulationConfigInstance.getAttributeValues().get(0)));
        SingleEventSimulator singleEventSimulator=SingleEventSimulator.getsingleEventSimulator();


        if(singleEventSimulator.convertEvent(QueryDeployer.executionPlanDetails,singleEventSimulationConfigInstance)){
            message="Event is send successfully";
        }
        System.out.println(message);
        String jsonString = new Gson().toJson(message);
        return Response.ok(jsonString, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*").build();
    }

    //Random event generator
    @POST
    @Path("/demo")
    public Response demo(String RandomDataSimulationConfig) throws IOException {
        //JSON from String to Object
        String message = null;
        ObjectMapper mapper = new ObjectMapper();
        RandomDataSimulationConfig randomDatasimulationConfig = mapper.readValue(RandomDataSimulationConfig, RandomDataSimulationConfig.class);
        System.out.println(randomDatasimulationConfig.getOption());
        RandomDataEventSimulator randomDataEventSimulator= RandomDataEventSimulator.getRandomDataEventSimulator();

        if(randomDataEventSimulator.generateRandomEvent(QueryDeployer.executionPlanDetails,randomDatasimulationConfig)){
            message="Event is send successfully";
        }
        System.out.println(message);
        String jsonString = new Gson().toJson(message);
        return Response.ok(jsonString, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*").build();
    }
}
