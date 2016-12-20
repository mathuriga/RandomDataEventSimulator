package org.wso2.carbon.event.executionplandelpoyer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mathuriga on 03/12/16.
 */
public class ExecutionPlanDeployer {
    private static final Log log = LogFactory.getLog(ExecutionPlanDeployer.class);
    private static ExecutionPlanDeployer executionPlanDeployer;
    private ExecutionPlanDto executionPlanDto;
    private Map<String, InputHandler> inputHandlerMap = new HashMap<>();
    private static SiddhiManager siddhiManager;
    private ExecutionPlanRuntime executionPlanRuntime;

    private ExecutionPlanDeployer() {

    }

    // TODO: 14/12/16 remove 
    public static void setExecutionPlanDeployer(ExecutionPlanDeployer executionPlanDeployer) {
        ExecutionPlanDeployer.executionPlanDeployer = executionPlanDeployer;
    }

    public ExecutionPlanDto getExecutionPlanDto() {
        return executionPlanDto;
    }

    public void setExecutionPlanDto(ExecutionPlanDto executionPlanDto) {
        this.executionPlanDto = executionPlanDto;
    }

    public Map<String, InputHandler> getInputHandlerMap() {
        return inputHandlerMap;
    }

    public void setInputHandlerMap(Map<String, InputHandler> inputHandlerMap) {
        this.inputHandlerMap = inputHandlerMap;
    }

    public static SiddhiManager getSiddhiManager() {
        return siddhiManager;
    }

    public static void setSiddhiManager(SiddhiManager siddhiManager) {
        ExecutionPlanDeployer.siddhiManager = siddhiManager;
    }

    public ExecutionPlanRuntime getExecutionPlanRuntime() {
        return executionPlanRuntime;
    }

    public void setExecutionPlanRuntime(ExecutionPlanRuntime executionPlanRuntime) {
        this.executionPlanRuntime = executionPlanRuntime;
    }

    public static ExecutionPlanDeployer getInstance() {
        if (executionPlanDeployer == null) {
            synchronized (ExecutionPlanDeployer.class) {
                if (executionPlanDeployer == null) {
                    executionPlanDeployer = new ExecutionPlanDeployer();
                }
            }
        }
        return executionPlanDeployer;
    }

    public static ExecutionPlanDeployer getExecutionPlanDeployerService() {
        return executionPlanDeployer;
    }

    /**
     * Deploy the execution plan
     * @param executionPlanDto Execution Plan Details
     */
    public void deployExecutionPlan(ExecutionPlanDto executionPlanDto) {
        try {
            this.siddhiManager = new SiddhiManager();
            String executionPlan = createExecutionplan(executionPlanDto);
            this.executionPlanDto = executionPlanDto;
            this.executionPlanRuntime = siddhiManager.createExecutionPlanRuntime(executionPlan);
            this.inputHandlerMap = createInputHandlerMap(executionPlanDto, executionPlanRuntime);
            ExecutionPlanDeployer.getInstance().getExecutionPlanRuntime().start();
            addStreamCallback(executionPlanDto);
            System.out.println("Execution Plan is deployed Successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
    }

    /**
     * Create siddhi Execution Plan
     *
     * In this class concatenate all input streams definition and queries as a Execution plan string
     * Siddhi should recognize it without any error
     * @param executionPlanDto
     * @return
     */
    public String createExecutionplan(ExecutionPlanDto executionPlanDto) {
        String streams = "";
        String setOfQuery = "";
        Iterator streamIterator = executionPlanDto.getInputStreamDtoMap().entrySet().iterator();
        while (streamIterator.hasNext()) {
            Map.Entry<String, StreamDefinitionDto> stream = (Map.Entry) streamIterator.next();
            streams += stream.getValue().getStreamDefinition();
            // streamIterator.remove(); // avoids a ConcurrentModificationException
        }

        Iterator queryIterator = executionPlanDto.getQueriesMap().entrySet().iterator();
        while (queryIterator.hasNext()) {
            Map.Entry<String, Queries> query = (Map.Entry) queryIterator.next();
            setOfQuery += query.getValue().getQueryDefinition();
        }

        return streams + setOfQuery;
    }

    /**
     * Create the input handler Map to each input streams
     * @param executionPlanDto Execution plan details
     * @param executionPlanRuntime Execution plan runtime
     * @return
     */
    public Map<String, InputHandler> createInputHandlerMap(ExecutionPlanDto executionPlanDto, ExecutionPlanRuntime executionPlanRuntime) {
        Map<String, InputHandler> inputHandlerMap = new HashMap<>();
        Iterator streamIterator = executionPlanDto.getInputStreamDtoMap().entrySet().iterator();
        while (streamIterator.hasNext()) {
            Map.Entry stream = (Map.Entry) streamIterator.next();
            inputHandlerMap.put((String) stream.getKey(), executionPlanRuntime.getInputHandler((String) stream.getKey()));
            // streamIterator.remove(); // avoids a ConcurrentModificationException
        }
        return inputHandlerMap;
    }

    public void addStreamCallback(ExecutionPlanDto executionPlanDto) {
        this.executionPlanRuntime.addCallback("outputStream", new StreamCallback() {
            @Override
            public void receive(Event[] events) {
                System.out.println("Output Event: " + Arrays.deepToString(events));
            }
        });
    }

}
