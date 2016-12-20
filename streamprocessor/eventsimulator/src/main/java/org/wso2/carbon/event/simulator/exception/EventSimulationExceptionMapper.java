package org.wso2.carbon.event.simulator.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by mathuriga on 20/12/16.
 */
public class EventSimulationExceptionMapper implements ExceptionMapper<EventSimulationException> {
    @Override
    public Response toResponse(EventSimulationException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                entity(e.getMessage()).
                build();
    }
}
