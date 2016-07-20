package org.whyte.main.MessengerAPI_JAX_RS.exception;

public class DataNotFoundException extends RuntimeException 
{
	private static final long serialVersionUID = 1L;

	//Basic exception that accepts a string in the constructor which can be output as error message
	public DataNotFoundException(String message)
	{
		super(message);
	}
}
