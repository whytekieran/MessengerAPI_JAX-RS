package org.whyte.main.MessengerAPI_JAX_RS.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

//Class used to model a message
//Annotation important so this object can be converted to XML when returned
//as a response. Without this annotation the Jersey framework does not know
//how to convert the object to XML
@XmlRootElement
public class Message 
{
	private int id;
	private String message;
	private Date created;
	private String author;
	
	//messages have associated comments so each message instance contains a collection of comments
	private Map<Integer, Comment> comments = new HashMap<Integer, Comment>();
	
	//each message sent back in a response will also send back links related to it. eg, links to itself,
	//related comments, likes etc. This List of Link objects is responsible for that.
	private List<Link> links = new ArrayList<Link>();
	
	//Empty constructor needed if we are returning JSON or XML responses
	//Important for XML or JSON conversion
	//Message will be returned as an XML or JSON response, to do this the
	//Jersey framework needs to be able to create a new instance of my class
	//hence the empty constructor
	public Message()
	{
		
	}
	
	//Constructor
	public Message(int id, String message, String author)
	{
		this.id = id;
		this.message = message;
		this.author = author;
		this.created = new Date();
	}
	
	//Getters and Setters
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	public Date getCreated()
	{
		return created;
	}
	
	public void setCreated(Date created)
	{
		this.created = created;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public void setAuthor(String author)
	{
		this.author = author;
	}
	
	//When we are sending back a list of messages to the client, or a single message we do not want 
	//to send back all the comments for that message as well. By including the @XmlTransiant annotation
	//we are telling jersey to ignore the associated comments when sending back the messages.
	@XmlTransient
	public Map<Integer, Comment> getComments() 
	{
		return comments;
	}

	public void setComments(Map<Integer, Comment> comments) 
	{
		this.comments = comments;
	}

	//getters and setters for the links associated with the message object
	public List<Link> getLinks() 
	{
		return links;
	}

	public void setLinks(List<Link> links) 
	{
		this.links = links;
	}
	
	//adds a new link object to the list of links
	public void addLink(String url, String rel)
	{
		Link link = new Link();
		link.setLink(url);
		link.setRel(rel);
		links.add(link);
	}
}
