package org.whyte.main.MessengerAPI_JAX_RS.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.whyte.main.MessengerAPI_JAX_RS.models.ErrorMessage;

//When exceptions happen jersey (jax-rs) will check for all classes that have the @Provider
//annotation. Then it checks for classes that map to the exception mapper class. In this situation
//if a DataNotFoundException is thrown jersey will check to see if that class is mapped to any
//ExceptionMapper class, it is mapped in this class so this is the toResponse method that is called.
//The toResponse method is part of the exception mapper class and is called in the case of an exception
@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> 
{
	@Override
	public Response toResponse(DataNotFoundException e)
	{
		//create a sample error message
		ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), 404, "http://github.com/whytekieran");
		
		//create the response and send it back to the user
		return Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
	}
}
