package org.whyte.main.MessengerAPI_JAX_RS.models;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Profile 
{
	private int id;
	private String profileName;
	private String firstName;
	private String lastName;
	private Date created;
	
	//Empty constructor so Jersey framework can create instances of this
	//class when doing XML or JSON conversion.
	public Profile()
	{
		
	}
	
	public Profile(int id, String profileName, String firstName, 
			String lastName)
	{
		this.id = id;
		this.profileName = profileName;
		this.firstName = firstName;
		this.lastName = lastName;
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
	
	public String getProfileName()
	{
		return profileName;
	}
	
	public void setProfileName(String profileName)
	{
		this.profileName = profileName;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	
	public Date getCreated()
	{
		return created;
	}
	
	public void setCreated(Date created)
	{
		this.created = created;
	}
}
