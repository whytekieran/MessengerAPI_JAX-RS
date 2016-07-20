package org.whyte.main.MessengerAPI_JAX_RS.models;

//Class used to send links to users. For example a message resource might contain links to things
//like comments and likes for that message. We do not need to add the @XmlRootElement annotation
//here to make this class sendable via XML or JSON because its not a root element. Rather this class
//(objects) will be contained inside the message class. Note: @XmlRootElement does not seem to be
//necessary if sending JSON
public class Link 
{
	private String link;
	private String rel;
	
	public String getLink()
	{
		return link;
	}
	
	public void setLink(String link)
	{
		this.link = link;
	}
	
	public String getRel()
	{
		return rel;
	}
	
	public void setRel(String rel)
	{
		this.rel = rel;
	}
}
