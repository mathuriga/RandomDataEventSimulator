package org.wso2.carbon.event.simulator.endpoint;


import com.google.gson.Gson;
import fabricator.Calendar;
import fabricator.Fabricator;
import fabricator.enums.DateFormat;
import org.wso2.carbon.event.simulator.querydeployer.bean.ExecutionPlanDetails;
import org.wso2.carbon.event.simulator.querydeployer.bean.StreamAttributeDto;
import org.wso2.carbon.event.simulator.querydeployer.bean.StreamDefinitionInfoDto;
import org.wso2.carbon.event.simulator.querydeployer.core.QueryDeployer;
import org.wso2.carbon.event.simulator.randomdatafeedsimulation.utils.RandomDataGenerator;


import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathuriga on 12/11/16.
 */

@Path("/Query")
public class QueryDeployerEndpoint {
    private QueryDeployer queryDeployer;
    private ExecutionPlanDetails executionPlanDetails;


    @POST
    @Path("/Deploy")
    @Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA })
    public Response deployQuery(@FormParam("inputStreamDefinition") String inputStreamDefinition,
                                @FormParam("query") String query,
                                @FormParam("inputStream") String inputStream,
                                @FormParam("inputStreamAttributeNameList") String attributeName,
                                @FormParam("inputStreamAttributeTypeList") String attributeType,
                                @FormParam("outputStream") String outputStream) {

        StreamDefinitionInfoDto streamDefinitionInfoDto=new StreamDefinitionInfoDto();
        streamDefinitionInfoDto.setStreamName(inputStream);
        streamDefinitionInfoDto.setStreamDefinition(inputStreamDefinition);
        streamDefinitionInfoDto.setStreamVersion("1.0");
        //streamDefinitionInfoDto.setStreamDefinition("Test Stream");
        String[] attributeNameList=attributeName.split(",");
        String[] attributeTypeList=attributeType.split(",");

        List<StreamAttributeDto> streamAttributeDtos=new ArrayList<>();

        if(attributeNameList.length==attributeTypeList.length)
        {
            for(int i=0;i<attributeNameList.length;i++){
                StreamAttributeDto streamAttributeDto=new StreamAttributeDto();
                streamAttributeDto.setAttributeName(attributeNameList[i]);
                streamAttributeDto.setAttributeType(attributeTypeList[i]);
                streamAttributeDtos.add(streamAttributeDto);
            }
        }

        streamDefinitionInfoDto.setStreamAttributeDtos(streamAttributeDtos);

        executionPlanDetails=new ExecutionPlanDetails();
        executionPlanDetails.setStreamDefinitionInfoDto(streamDefinitionInfoDto);
        executionPlanDetails.setQuery(query);
        executionPlanDetails.setInputStream(inputStream);
        executionPlanDetails.setOutputStream(outputStream);
        queryDeployer=new QueryDeployer(executionPlanDetails);
        queryDeployer.deployQuery();
        String jsonString = new Gson().toJson("success");
        return Response.ok(jsonString, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*").build();

    }
}
