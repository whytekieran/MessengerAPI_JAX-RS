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

import org.whyte.main.MessengerAPI_JAX_RS.models.Profile;
import org.whyte.main.MessengerAPI_JAX_RS.services.ProfileService;

//This class is the API responsible for handling profile resources, very similar to the MessageResource class
@Path("/profiles")
public class ProfileResource 
{
	//ProfileService class handles any profile tasks (holds methods that perform CRUD operations on profiles)
	ProfileService profileService = new ProfileService();
	
	//GET method that returns a list of all profile objects as JSON
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Profile> getProfile()
	{
		return profileService.getAllProfiles();
	}
	
	//Gets a profile name from the URL and sends back the profile that matches it
	@GET
	@Path("/{profileName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Profile getSingleProfile(@PathParam ("profileName") String profileName) 
	{
		return profileService.getProfile(profileName);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Profile addProfile(Profile profile)
	{
		return profileService.addProfile(profile);
	}
	
	@PUT
	@Path("/{profileName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Profile updateProfile(Profile profile, @PathParam ("profileName") String profileName)
	{
		profile.setProfileName(profileName);
		return profileService.updateProfile(profile);
	}
	
	@DELETE
	@Path("/{profileName}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteProfile(@PathParam ("profileName") String profileName)
	{
		profileService.removeProfile(profileName);
	}
}
