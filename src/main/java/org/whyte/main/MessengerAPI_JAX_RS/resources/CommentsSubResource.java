package org.whyte.main.MessengerAPI_JAX_RS.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.whyte.main.MessengerAPI_JAX_RS.models.Comment;
import org.whyte.main.MessengerAPI_JAX_RS.services.CommentService;

//no starting path needed here as this is a sub resource class and the path comes from the connecting 
//method inside the message resource class
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)//Instead of having produces and consumes for each method for the class
public class CommentsSubResource 
{
	private CommentService commentService = new CommentService();
	
	//Even though we have not declared msgID using the @Path annotation we can still access it because
	//comments are a sub resource. We are directed here from the message resource class and a URL
	//like messages/[msgID]/comments has been passed to here already. In the message resource class the
	//@Path annotation has been already used on the URL we are passing, so we dont need to do it again
	@GET
	//@Produces(MediaType.APPLICATION_XML)//could return xml if we wanted to
	public List<Comment> getAllComments(@PathParam("msgID") int msgID)
	{
		return commentService.getAllComments(msgID);
	}
	
	//Because commentID is an extension to the URL that has been passed here from the message resource
	//class (url already is messages/[msgID]/comments) we must use the @Path annotation to get the value 
	//here.
	@GET
	@Path("/{commentID}")
	public Comment getComment(@PathParam("msgID") int msgID, @PathParam("commentID") int commentID)
	{
		return commentService.getComment(msgID, commentID);
	}
	
	@POST
	public Comment addComment(@PathParam("msgID") int msgID, Comment comment)
	{
		return commentService.addComment(msgID, comment);
	}
	
	@PUT
	@Path("/{commentID}")
	public Comment updateComment(@PathParam("msgID") int msgID, @PathParam("commentID") int commentID, Comment comment)
	{
		comment.setId(commentID);
		return commentService.updateComment(msgID, comment);
	}
	
	@DELETE
	@Path("/{commentID}")
	public Comment deleteComment(@PathParam("msgID") int msgID, @PathParam("commentID") int commentID)
	{
		return commentService.deleteComment(msgID, commentID);
	}
}
