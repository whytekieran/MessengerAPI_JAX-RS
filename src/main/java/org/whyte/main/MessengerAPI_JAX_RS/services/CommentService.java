package org.whyte.main.MessengerAPI_JAX_RS.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.whyte.main.MessengerAPI_JAX_RS.data.DataClass;
import org.whyte.main.MessengerAPI_JAX_RS.models.Comment;
import org.whyte.main.MessengerAPI_JAX_RS.models.ErrorMessage;
import org.whyte.main.MessengerAPI_JAX_RS.models.Message;

//Similar code to the profile and message service classes. Slight differences but still similar.
public class CommentService 
{
	private Map<Integer, Message> messages = DataClass.getMessages();
	
	public List<Comment> getAllComments(int msgID)
	{
		Map<Integer, Comment> comments = messages.get(msgID).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(int msgID, int commentID)
	{
		//create a sample error message
		ErrorMessage errorMessage = new ErrorMessage("Not Found", 404, "http://github.com/whytekieran");
				
		//create the response to send back to the user
		Response response = Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
		
		Message message = messages.get(msgID);
		
		if(message == null)
		{
			//using WebApplicationException which is built into jax-rs hence no exception mapper class
			//is needed
			throw new WebApplicationException(response);
		}
		
		Map<Integer, Comment> comments = message.getComments();
		Comment comment = comments.get(commentID);
		
		if(comment == null)
		{
			throw new WebApplicationException(response);
		}
		
		return comment;
	}
	
	public Comment addComment(int msgID, Comment comment)
	{
		Map<Integer, Comment> comments = messages.get(msgID).getComments();
		comment.setId(comments.size() + 1);
		comment.setCreated(new Date());
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(int msgID, Comment comment)
	{
		Map<Integer, Comment> comments = messages.get(msgID).getComments();
		
		if(comment.getId() <= 0)
		{
			return null;
		}
		else
		{
			comments.put(comment.getId(), comment);
			return comment;
		}
	}
	
	public Comment deleteComment(int msgID, int commentID)
	{
		Map<Integer, Comment> comments = messages.get(msgID).getComments();
		return comments.remove(commentID);
	}
}
