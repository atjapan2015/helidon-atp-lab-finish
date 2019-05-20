package io.helidon.atp.lab.common.exception;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ProcessingExceptionMapper implements ExceptionMapper<ProcessingException> {

	@Override
	public Response toResponse(ProcessingException exception) {

		return Response.status(Response.Status.BAD_REQUEST)
				.entity("This is an invalid json. The request can not be parsed. " + exception.getMessage())
				.type(MediaType.APPLICATION_JSON).build();
	}

}
