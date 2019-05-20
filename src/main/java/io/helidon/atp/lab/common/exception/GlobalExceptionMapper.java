package io.helidon.atp.lab.common.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {

		return Response
				.status(Response.Status.BAD_REQUEST).entity("This is an invalid request. The field "
						+ exception.getMessage() + " is not recognized by the system.")
				.type(MediaType.TEXT_PLAIN).build();
	}

}
