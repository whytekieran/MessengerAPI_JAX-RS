package org.whyte.main.MessengerAPI_JAX_RS.data;

import java.util.HashMap;
import java.util.Map;

import org.whyte.main.MessengerAPI_JAX_RS.models.Message;
import org.whyte.main.MessengerAPI_JAX_RS.models.Profile;

//In a real production Web API this class would most likely connect to
//some sort of a database, possibly using something like SQL and JDBC.
//For the purpose of this sample API we will just use in-memory objects
//(Hashmaps) to simulate a database.
//These HashMaps are not set up for multi-threading or concurrency. This is
//only meant as a sample API, security and concurrency should be considered
//when writing code at a production level
public class DataClass 
{
	private static Map<Integer, Message> messages = new HashMap<Integer, Message>();
	private static Map<String, Profile> profiles = new HashMap<String, Profile>();
	
	public static Map<Integer, Message> getMessages()
	{
		return messages;
	}
	
	public static Map<String, Profile> getProfiles()
	{
		return profiles;
	}
}
