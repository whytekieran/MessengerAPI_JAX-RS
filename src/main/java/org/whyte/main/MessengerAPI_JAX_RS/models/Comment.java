package org.whyte.main.MessengerAPI_JAX_RS.models;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Comment 
{
	private int id;
	private String comment;
	private Date created;
	private String author;
	
	//empty constructor so jersey can create an instance of this class when needed
	public Comment()
	{
		
	}
	
	public Comment(int id, String comment, String author)
	{
		this.id = id;
		this.comment = comment;
		this.author = author;
		this.created = new Date();
	}
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getComment()
	{
		return comment;
	}
	
	public void setComment(String comment) 
	{
		this.comment = comment;
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
}
