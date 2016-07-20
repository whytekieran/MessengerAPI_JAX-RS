package org.whyte.main.MessengerAPI_JAX_RS.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.whyte.main.MessengerAPI_JAX_RS.data.DataClass;
import org.whyte.main.MessengerAPI_JAX_RS.exception.DataNotFoundException;
import org.whyte.main.MessengerAPI_JAX_RS.models.Message;

//Class used to provide messaging services
public class MessageService 
{
	//Using the DataClass.getMessages() method to return a Map of all
	//Messages and ID's for those messages. The DataClass would
	//usually connect to an actual database, possibly using
	//something like JDBC. Here we use the DataClass which represents a
	//class that connects to a database. For sample purposes though it
	//contains in-memory objects to hold data
	private Map<Integer, Message> messages = DataClass.getMessages();
	
	//Creating a constructor to initialize the hashmap with some messages in it for example purposes.
	//Can be left empty and can add messages using the API
	public MessageService()
	{
		//messages.put(1, new Message(1, "Hello Message 1", "Kieran Whyte"));
		//messages.put(2, new Message(2, "Hello Message 2", "Peter Nagy"));
	}
	
	public List<Message> getAllMessages()
	{
		//Get all the values from the Map (keys are integers and values 
		//messages) then return the as a list of message objects
		return new ArrayList<Message>(messages.values()); 
	}
	
	//Get a particular message depending on its id and return it
	public Message getMessage(int id)
	{
		Message message = messages.get(id);
		
		if(message == null)//if we dont get a message corresponding to the id throw this exception
		{
			//custom exception written by myself will send back a custom response
			throw new DataNotFoundException("Message with ID: " +id+ " was not found.");
		}
		else
		{
			return message;
		}
	}
	
	//Example of a method used in "Filtering" for example this method is used with a URL like
	//http://localhost:8080/3.MessengerAPI_JAX-RS/webapi/messages?year=2016
	//to retrieve all messages with a certain year of creation. The method is called by the 
	//message resource API
	public List<Message> getAllMessageForYear(int year)
	{
		List<Message> messagesByYear = new ArrayList<Message>();
		Calendar calender = Calendar.getInstance();
		
		for(Message message : messages.values())//for each message object in the hash map
		{
			calender.setTime(message.getCreated()); //first set calendars time to the date it was created
			
			if(calender.get(Calendar.YEAR) == year)//make sure the years match
			{
				messagesByYear.add(message);//if they match add the message object to the list
			}
		}
		
		return messagesByYear; //return the list
	}
	
	//Example of a message used when performing pagination. For example if we wanted to get messages
	//starting with an id of 10 and get the next 30 messages after that we can use a URL as follows
	//http://localhost:8080/3.MessengerAPI_JAX-RS/webapi/messages?start=10&size=30 to specify thats
	//what we are looking for. This is an example of pagination and this method is called by the message 
	//resource API
	public List<Message> getAllMessagesPaginated(int start, int size)
	{
		List<Message> messageList = new ArrayList<Message>(messages.values()); //create list of messages
		
		//make sure we dont go over the max index start point or end point size
		if(start + size > messageList.size() || start > messageList.size())//if it is
		{
			return new ArrayList<Message>(); //just return an empty list
		}
		
		return messageList.subList(start, start + size); //otherwise, return a sub-list determined by start and size
	}
	
	//Add a new message into the HashMap. We get the size of the hashmap and add 1
	//to that number to give the new message a unique id
	public Message addMessage(Message message)
	{
		message.setId(messages.size() + 1);
		message.setCreated(new Date());
		messages.put(message.getId(), message);
		return message;
	}
	
	//Updating a message, if the id is not valid it returns nothing, but if it is then
	//add the new message object into the index the old version already occupies.
	public Message updateMessage(Message message)
	{
		if(message.getId() <= 0)
		{
			return null;
		}
		else
		{
			message.setCreated(new Date());
			messages.put(message.getId(), message);
			return message;
		}
	}
	
	//Removing a message from the hashmap of stored messages then returns the message
	//that was deleted.
	public Message removeMessage(int id)
	{
		return messages.remove(id);
	}
}
