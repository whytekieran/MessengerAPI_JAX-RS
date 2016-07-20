package org.whyte.main.MessengerAPI_JAX_RS.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
//import javax.ws.rs.ext.Provider;

import org.whyte.main.MessengerAPI_JAX_RS.models.ErrorMessage;

//This catches all exceptions that are not already mapped to an exception mapper.
//Usually you want to be more specific with your exceptions. The @Provider annotation has been
//commented out so this exception mapper is currently disabled. This class is an example to show you
//can map one class to all exceptions if you wanted but its not advised.
//@Provider //Class for generic exceptions. All exceptions are throwable.
public class GenericExceptionMapper implements ExceptionMapper<Throwable> 
{
	@Override
	public Response toResponse(Throwable e)
	{
		//create a sample error message
		ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "http://github.com/whytekieran");
		
		//create the response and send it back to the user
		return Response.status(Status.BAD_REQUEST)
				.entity(errorMessage)
				.build();
	}
}
