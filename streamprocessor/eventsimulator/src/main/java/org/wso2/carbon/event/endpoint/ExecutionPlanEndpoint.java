package org.wso2.carbon.event.endpoint;

import com.google.gson.Gson;
import org.wso2.carbon.event.executionplandelpoyer.ExecutionPlanDeployer;
import org.wso2.carbon.event.executionplandelpoyer.ExecutionPlanDto;
import org.wso2.carbon.event.executionplandelpoyer.utils.ExecutionPlanParser;
import org.wso2.msf4j.Response;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by mathuriga on 03/12/16.
 */

// TODO: 14/12/16 check the standard
@Path("/ExecutionPlan")
public class ExecutionPlanEndpoint {

    @POST
    @Path("/deploy")
    public javax.ws.rs.core.Response deployExecutionPlan(String executionPlanConfig){
        String message=null;

        ExecutionPlanDto executionPlanDto= ExecutionPlanParser.executionPlanDtoParser(executionPlanConfig);
        ExecutionPlanDeployer executionPlanDeployer=ExecutionPlanDeployer.getExecutionPlanDeployer();
        executionPlanDeployer.deployExecutionPlan(executionPlanDto);
        // TODO: 14/12/16 check the message
        String jsonString = new Gson().toJson(message);
        return javax.ws.rs.core.Response.ok(jsonString, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*").build();
    }


}
