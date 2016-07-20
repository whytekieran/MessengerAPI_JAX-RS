package org.whyte.main.MessengerAPI_JAX_RS.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.whyte.main.MessengerAPI_JAX_RS.data.DataClass;
import org.whyte.main.MessengerAPI_JAX_RS.models.Profile;

//Class provides profiles services, contains CRUD methods for profiles
public class ProfileService 
{
	//Using the DataClass.getProfiles() method to return a Map of all
	//Profiles and Profile Names. The DataClass would
	//usually connect to an actual database, possibly using
	//something like JDBC. Here we use the DataClass which represents a
	//class that connects to a database. For sample purposes though it
	//contains in-memory objects to hold data
	private Map<String, Profile> profiles = DataClass.getProfiles();
	
	//Creating a constructor to initialize the hashmap with a profile in it. Can also be left empty
	//and can add profiles using the API
	public ProfileService()
	{
		profiles.put("KieranW", new Profile(1, "KieranW", "Kieran", "Whyte"));
	}
	
	public List<Profile> getAllProfiles()
	{
		//Get all the values from the Map (keys are strings (profile name) and values 
		//messages) then return the as a list of profile objects
		return new ArrayList<Profile>(profiles.values()); 
	}
	
	//Get a particular profile depending on its profile name and return it
	public Profile getProfile(String profileName)
	{
		return profiles.get(profileName);
	}
	
	//Add a new profile into the HashMap. We get the size of the hashmap and add 1
	//to that number to give the new message a unique id, we use profile name as the
	//key value
	public Profile addProfile(Profile profile)
	{
		profile.setId(profiles.size() + 1);
		profile.setCreated(new Date());
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	//Updating a profile, if the profile name is not valid it returns nothing, but 
	//if it is then add the new profile object into the index the old version already 
	//occupies.
	public Profile updateProfile(Profile profile)
	{
		if(profile.getProfileName().isEmpty())
		{
			return null;
		}
		else
		{
			profile.setCreated(new Date());
			profiles.put(profile.getProfileName(), profile);
			return profile;
		}
	}
	
	//Removing a profile from the hashmap of stored profile then returns the profile
	//that was deleted.
	public Profile removeProfile(String profileName)
	{
		return profiles.remove(profileName);
	}
}
