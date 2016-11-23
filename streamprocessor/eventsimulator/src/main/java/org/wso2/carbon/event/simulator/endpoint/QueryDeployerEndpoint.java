package org.wso2.carbon.event.simulator.endpoint;


import com.google.gson.Gson;
import org.wso2.carbon.event.simulator.querydeployer.bean.ExecutionPlanDetails;
import org.wso2.carbon.event.simulator.querydeployer.core.QueryDeployer;


import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by mathuriga on 12/11/16.
 */

@Path("/Query")
public class QueryDeployerEndpoint {
    QueryDeployer queryDeployer;
    ExecutionPlanDetails executionPlanDetails;

    @POST
    @Path("/Deploy")
    @Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA })
    public Response DeployQuery(@FormParam("inputStreamDefinition") String inputStreamDefinition,
                                @FormParam("query") String query,
                                @FormParam("inputStream") String inputStream,
                                @FormParam("inputStreamAttributeTypeList") String attributeType,
                                @FormParam("outputStream") String outputStream) {

        executionPlanDetails=new ExecutionPlanDetails();
        executionPlanDetails.setInputStreamStreamDefiniton(inputStreamDefinition);
        executionPlanDetails.setQuery(query);
        executionPlanDetails.setInputStream(inputStream);
        executionPlanDetails.setOutputStream(outputStream);
        executionPlanDetails.setAttributeTypeList(attributeType.split(","));


        queryDeployer=new QueryDeployer(executionPlanDetails);
        queryDeployer.deployQuery();
        String jsonString = new Gson().toJson("success");



        return Response.ok(jsonString, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*").build();

    }
}
