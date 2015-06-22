/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.util.*;

public class FacePamphletDatabase implements FacePamphletConstants {

	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the database.
	 */
	public FacePamphletDatabase() {
		// You fill this in
		profiles = new HashMap<String,FacePamphletProfile>();
	}
	
	
	/** 
	 * This method adds the given profile to the database.  If the 
	 * name associated with the profile is the same as an existing 
	 * name in the database, the existing profile is replaced by 
	 * the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {
		// You fill this in
		profiles.put(profile.getName(), profile);
	}

	
	/** 
	 * This method returns the profile associated with the given name 
	 * in the database.  If there is no profile in the database with 
	 * the given name, the method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {
		// You fill this in.  Currently always returns null.
		return profiles.get(name);
	}
	
	
	/** 
	 * This method removes the profile associated with the given name
	 * from the database.  It also updates the list of friends of all
	 * other profiles in the database to make sure that this name is
	 * removed from the list of friends of any other profile.
	 * 
	 * If there is no profile in the database with the given name, then
	 * the database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		// You fill this in
		if(containsProfile(name)) {
			removeFromFriendList(name);
			profiles.remove(name);
		}
	}

	/**
	 * Removes the profile with key 'name' from any profile's friend list.  
	 * @param name is the profile to be removed from the list of friends
	 * of other profiles(if they have 'name' as a friend).
	 */
	private void removeFromFriendList(String name) {
		// get iterator for all the names of profiles in database.
		Iterator<String> it = profiles.keySet().iterator();
		while(it.hasNext()) {
			FacePamphletProfile profile = profiles.get(it.next());
			//get iterator on the friends of 'profile'
			Iterator<String> friends = profile.getFriends();
			while(friends.hasNext()) {
				if(friends.next().equals(name)) {
					profile.removeFriend(name);
					break;
				}
			}
		}
	}
	
	/** 
	 * This method returns true if there is a profile in the database 
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		// You fill this in.  Currently always returns false.
		return profiles.containsKey(name);
	}

	private HashMap<String,FacePamphletProfile> profiles;
}
