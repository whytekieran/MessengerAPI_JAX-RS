package org.whyte.main.MessengerAPI_JAX_RS.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.whyte.main.MessengerAPI_JAX_RS.models.Message;
import org.whyte.main.MessengerAPI_JAX_RS.services.MessageService;

//This class is the API responsible for handling message resources 
@Path("/messages")
//Instead of having produces and consumes for each method for the class we can do it for the entire class
//Can produce or consume more than one thing. Jersey can tell what to send back based on the 
//Accept header in the request. We can also have two methods for example, one produces JSON and one produces
//XML but the data is the same. Depending on what is asked for in the request the method that 
//produces what is requested is called. This means i can control which method is called depending on the
//Accept Header's value. Consumes = Content-Type and Produces = Accept
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})//not every class method consumes JSON or XML but can still be declared for the class, now any method that does consume it will work
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})

public class MessageResource 
{
	//MessageService class handles any message tasks
	MessageService messageService = new MessageService();
	
	/*
	//These two methods are the same as the first two below this commented section
	//except these return XML and the two that are not commented return JSON
	//GET method that returns a list of all message objects as XML
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Message> getMessage()
	{
		return messageService.getAllMessages();
	}
	
	//Gets a message id from the URL and sends back the message that matches it
	@GET
	@Path("/{msgID}")
	@Produces(MediaType.APPLICATION_XML)
	public Message getSingleMessage(@PathParam ("msgID") int msgID) 
	{
	    return messageService.getMessage(msgID);
	}
	*/
	
	//GET method that returns a list of all message objects as JSON. The method handles URL's that
	//do or do not include a query param. For example, the URL /messages does not have a query param
	//hence the value "year" will be set to the default value for an int which is 0. This means we
	//simply return all the messages. However if the URL looks like this /messages?year=2016 then we
	//have passed a year as a query param and it will have a value greater than 0. This means we call
	//the method that searches for message by year.
	//Note: The argument of "year" that is passed into the @QueryParam is saying that if there is a
	//query param called "year" then pass it to the variable year.
	//The use of query param here shows how they can be used to add filtering and that if used correctly
	//this method can now still serve to purposes depending on whats passed
	@GET
	public List<Message> getMessage(@QueryParam("year") int year)
	{
		if(year > 0)
		{
			return messageService.getAllMessageForYear(year);
		}
		else
		{
			return messageService.getAllMessages();
		}
	}
		
	//Gets a message id from the URL and sends back the message that matches it
	@GET
	@Path("/{msgID}")
	public Message getSingleMessage(@PathParam ("msgID") int msgID, @Context UriInfo uriInfo) 
	{
		Message message = messageService.getMessage(msgID);
		String uriMessage = getUriForSelf(uriInfo, message);		//creates the URI link for the message
		String uriProfile = getUriForProfile(uriInfo, message);		//creates URI link for the profile of user that wrote the message
		String uriComments = getUriForComments(uriInfo, message);
		message.addLink(uriMessage, "self");						//adds the message link to message object
		message.addLink(uriProfile, "profile");						//add a link to the profile of the user who wrote the message
		message.addLink(uriComments, "comments");
		
		return message;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) 
	{
		String uri = uriInfo.getBaseUriBuilder()			//gives us the base path http://localhost:8080/3.MessengerAPI_JAX-RS/webapi
				.path(MessageResource.class)				//adds /messages which is what the class path is to the base path 
				//to add the /[msgID]/comments part of the URI we use this. Because comments is a sub
				//resource and the path is mapped to a method here in the message resource class we can
				//pass the class name and the method name that the path maps to.
				.path(MessageResource.class, "getCommentSubResource") //gets /[msgID]/comments
				//because the path that we get from the getCommentSubResource() method is as follows
				//   /[msgID]/comments it contains a variable value that will not be mapped to anything.
				//The resolveTemplate method is a way of specifying the value the variable should have.
				//In this case its the id of the message requested. We need it to send back a link
				//to comments related to the message
				.resolveTemplate("msgID", message.getId())
				.build()									//build the URI
				.toString(); 								//convert it from a URI to a String		
		
		return uri;
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) 
	{
		String uri = uriInfo.getBaseUriBuilder()			//gives us the base path http://localhost:8080/3.MessengerAPI_JAX-RS/webapi
				.path(ProfileResource.class)				//adds /profiles which is what the class path is to the base path 
				.path(message.getAuthor()) 					//add the message author to the path
				.build()									//build the URI
				.toString(); 								//convert it from a URI to a String		
		
		return uri;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) 
	{
		String uri = uriInfo.getBaseUriBuilder()			//gives us the base path http://localhost:8080/3.MessengerAPI_JAX-RS/webapi
				.path(MessageResource.class)				//adds /messages which is what the class path is to the base path 
				.path(Integer.toString(message.getId())) 	//add the message id to the path
				.build()									//build the URI
				.toString(); 								//convert it from a URI to a String		
		
		return uri;
	}
	
	//GET for URL like messages/paginated?start=0&size=2 ....its purpose is to get messages starting at a
	//certain index and then getting a particular amount of messages from that point 
	@GET
	@Path("/paginated")
	public List<Message> getMessagesPaginated(@QueryParam("start") int start, @QueryParam("size") int size) 
	{
		//make sure we have the minimum start index and size needed
		if(start >= 0 && size > 0)
		{
			return messageService.getAllMessagesPaginated(start, size);
		}
		else //if not just send back an empty list
		{
			return new ArrayList<Message>();
		}
	}
	
	//This method will accept JSON in the request body of a POST method that looks like
	//the following: 
	/*{
	"author": "KieranW",
	"message": "Hello Message 1"
	}*/
	//The id and date attributes of the message are added to it inside the addMessage()
	//method which also adds the new message to the other messages.
	//In this method we dont return the new message back to the user. Instead this method 
	//shows an example of how we can add response status codes and response headers using the response
	//object.
	@POST
	public Response addMessage(@Context UriInfo uriInfo, Message msg, @HeaderParam("Accepts") String acceptValue)
	{
		//We could do it like this and return the header in this way using .header() but there is an 
		//easier way.
		/*Message newMessage = messageService.addMessage(msg);
		  
		  return Response.status(Status.CREATED)
				.entity(newMessage)
				.header("Location", "the URI (converted string) of the newly created resource")
				.build();*/
		
		//Here the .created function will automatically send back the 201 created status code
		//useful as sending 201 is very common with POST requests (best practice). 
		//The argument will be the value given to the "Location" response header which indicates 
		//where the newly created resource is located. There are two ways we can do this:
		//1. Pass a string into the URI constructor.
		Message newMessage = messageService.addMessage(msg);
		String uri = uriInfo.getAbsolutePath().toString();
		
		try 
		{
			return Response.created(new URI(uri +"/"+ newMessage.getId()))
					.header("Content-Type", acceptValue)//set return content type to specified format retrieved from Accept request header
					.entity(newMessage)
					.build();
		} 
		catch (URISyntaxException e) 
		{
			//e.printStackTrace();
			return null;
		}
		
		//2. Or we can remove the URI constructor and just pass a URI into .create() we build 
		//the URI ourselves. Eliminates the need for a try/catch
		/*Message newMessage = messageService.addMessage(msg);
		String newID = String.valueOf(newMessage.getId()); //get id as a string
		//Next create the URI .getAbsolutePathBuilder returns the uri info as a URI build. Then
		//.path() adds the additional part to it. Finally .build() creates a URI object
		URI uri = uriInfo.getAbsolutePathBuilder().path(newID).build();
		
		return Response.created(uri)
				.entity(newMessage)
				.build();*/
	}
	
	@PUT
	@Path("/{msgID}")
	public Message updateMessage(Message msg, @PathParam ("msgID") int msgID)
	{
		msg.setId(msgID);
		return messageService.updateMessage(msg);
	}
	
	@DELETE
	@Path("/{msgID}")
	public void deleteMessage(@PathParam ("msgID") int msgID)
	{
		messageService.removeMessage(msgID);
	}
	
	//This method is used to handle sub resources. When a path like messages/[msgid]/comments is accessed
	//we return an instance of the class CommentsSubResource (which is another resource). Then inside
	//the CommentsSubResource class the appropriate method method is choosen depending on what the next 
	//part of the url is.
	@Path("/{msgID}/comments")
	public CommentsSubResource getCommentSubResource()
	{
		return new CommentsSubResource();
	}
}
