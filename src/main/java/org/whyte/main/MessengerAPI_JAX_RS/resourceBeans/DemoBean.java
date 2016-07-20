package org.whyte.main.MessengerAPI_JAX_RS.resourceBeans;

import javax.ws.rs.CookieParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;

public class DemoBean 
{
	private @MatrixParam("param") String matrixParam;
	private @HeaderParam("customHeaderValue") String headerValue;
	private @CookieParam("cookieName") String cookie;
	
	public String getMatrixParam() 
	{
		return matrixParam;
	}
	
	public void setMatrixParam(String matrixParam) 
	{
		this.matrixParam = matrixParam;
	}
	
	public String getHeaderValue() 
	{
		return headerValue;
	}
	
	public void setHeaderValue(String headerValue) 
	{
		this.headerValue = headerValue;
	}
	
	public String getCookie() 
	{
		return cookie;
	}
	
	public void setCookie(String cookie) 
	{
		this.cookie = cookie;
	}
}

