package org.whyte.main.MessengerAPI_JAX_RS.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
//import org.whyte.main.MessengerAPI_JAX_RS.resourceBeans.DemoBean;  //Needed when using BeanParam annotation

//The other resource classes show examples of query params and path params. There are some others, this 
//resource class shows some examples.

@Path("/demo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class DemoResource 
{
	//1. Not as common but still used, matrix params look like this: url/stuff;param=value
	//They are similar to query params and use the @MatrixParam annotation
	//Secondly we can create custom headers in our request, the values of these custom headers can
	//be retrieved using the @HeaderParam annotation. This can be useful for authentication, sending session
	//id's for example.
	//Lastly the CookieParam annotation can be used to retrieve cookie values
	//(Note: To send a Cookie using postman we add a request header. The key is called "Cookie" and the
	//corresponding value would be something like cookieName=actualValueGoesHere)
	//Annotations are very useful when we know exactly what we are looking for.
	@GET
	@Path("/annotations")
	public String getParamsUsingAnnotations(@MatrixParam("param") String matrixParam,
											@HeaderParam("customHeaderValue") String headerValue,
											@CookieParam("cookieName") String cookie)
	{
		return "Matrix Param is: " +matrixParam+ " Custom Header Value is: " +headerValue+ " Cookie is: " +cookie;
	}
	
	//2. There is another way of doing what is done above. If we dont want to pass many annotations as 
	//arguments into our method we can use something called a Bean Param. To do this we create a class
	//which we have called "DemoBeanParameters" which contains instance variables that correspond to the
	//arguments for the method and getters/setters for the variables. Then we pass in an instance of this
	//class instead of all the arguments. Here is an example that works identically to the method above
	/*@GET
	@Path("/annotations")
	public String getParamsUsingAnnotations(@BeanParam DemoBean beanParam)
	{
		return "Matrix Param is: " +beanParam.getMatrixParam()+ " Custom Header Value is: " +beanParam.getHeaderValue()+ " Cookie is: " +beanParam.getCookie();
	}*/
	
	//3. The @Context annotation can be used with certain classes to get information about the URI, Headers
	//Cookies etc. First we see the UriInfo class which has many useful methods that allow us to work with
	//the request URI. Secondly we see the HttpHeaders class which allows us to get information about the
	//request headers, cookies etc.
	//Context is very useful to get information from the request to view whats there
	@GET
	@Path("/context")
	public String getParamsUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders headers)
	{
		String path = uriInfo.getAbsolutePath().toString();
		String cookies = headers.getCookies().toString();
		return "Path is: " +path+ " Cookies: " +cookies;
	}
}














