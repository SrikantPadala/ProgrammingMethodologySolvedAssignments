/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	/**
	 * Constructs the object.
	 */
	public FacePamphlet() {
		name = new JTextField(TEXT_FIELD_SIZE);

		changeStatus = new JTextField(TEXT_FIELD_SIZE);
		changeStatus.setActionCommand("Change Status");

		changePicture = new JTextField(TEXT_FIELD_SIZE);
		changePicture.setActionCommand("Change Picture");

		addFriend = new JTextField(TEXT_FIELD_SIZE);
		addFriend.setActionCommand("Add Friend");

		database = new FacePamphletDatabase();
		currentProfile = null;
		canvas = new FacePamphletCanvas();
		add(canvas);
	}

	/**
	 * This method has the responsibility for initializing the interactors in
	 * the application, and taking care of any other initialization that needs
	 * to be performed.
	 */
	public void init() {
		// You fill this in
		initInteractors();
	}

	private void initInteractors() {
		initInteractorsInNorth();
		initInteractorsInWest();
		addActionListeners();
	}

	/**
	 * Adds the interactors in the NORTH.
	 */
	private void initInteractorsInNorth() {
		add(new JLabel("Name"), NORTH);
		name.addActionListener(this);
		add(name, NORTH);

		add(new JButton("Add"), NORTH);
		add(new JButton("Delete"), NORTH);
		add(new JButton("Lookup"), NORTH);
	}

	/**
	 * Adds the interactors in the WEST.
	 */
	private void initInteractorsInWest() {

		changeStatus.addActionListener(this);
		add(changeStatus, WEST);
		add(new JButton("Change Status"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		changePicture.addActionListener(this);
		add(changePicture, WEST);
		add(new JButton("Change Picture"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		addFriend.addActionListener(this);
		add(addFriend, WEST);
		add(new JButton("Add Friend"), WEST);
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		// You fill this in as well as add any additional methods
		String actionCommand = e.getActionCommand();
		if (actionCommand.equals("Add")) {
			addProfileToDatabase();
		} else if (actionCommand.equals("Delete")) {
			deleteProfileFromDatabase();
		} else if (actionCommand.equals("Lookup")) {
			lookupProfileInDatabase();
		} else if (actionCommand.equals("Change Status")
				|| actionCommand.equals("Change Picture")
				|| actionCommand.equals("Add Friend")) {
			if (currentProfile != null) {
				updateProfile(actionCommand);
			} else {
				println("Select a profile");
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Please select a profile to "+ actionCommand);
			}
		}
	}

	/**
	 * Creates a profile with given name and adds it to the database. Updates
	 * the current profile to this one.
	 */
	private void addProfileToDatabase() {
		if (database.containsProfile(name.getText())) {
			println("Profile already exists : "
					+ database.getProfile(name.getText()).toString());
			canvas.displayProfile(currentProfile);
			canvas.showMessage("Profile with name " + name.getText()
					+ " already exists");
		} else {
			currentProfile = new FacePamphletProfile(name.getText());
			database.addProfile(currentProfile);
			canvas.displayProfile(currentProfile);
			canvas.showMessage("New profile created");
			println("Add :" + currentProfile.toString());
		}
	}

	/**
	 * Deletes a profile in database with the name(if exists). Sets the
	 * currentProfile to null.
	 */
	private void deleteProfileFromDatabase() {
		if (database.containsProfile(name.getText())) {
			println("Delete :" + database.getProfile(name.getText()).toString());

			database.deleteProfile(name.getText());
			currentProfile = null;
			canvas.displayProfile(currentProfile);
			canvas.showMessage("Profile of " + name.getText() + " deleted");
		} else {
			println("Profile does not exist");
			canvas.displayProfile(currentProfile);
			canvas.showMessage("Profile of " + name.getText()
					+ " does not exist");
		}
	}

	/**
	 * Looks up for a profile in database with the name(if exists).
	 */
	private void lookupProfileInDatabase() {
		if (database.containsProfile(name.getText())) {
			currentProfile = database.getProfile(name.getText());
			println("Lookup :" + database.getProfile(name.getText()).toString());
			canvas.displayProfile(currentProfile);
			canvas.showMessage("Displaying " + name.getText());
		} else {
			currentProfile = null;
			println("Profile does not exist");
			canvas.displayProfile(currentProfile);
			canvas.showMessage("Profile of " + name.getText()
					+ " does not exist");
		}
	}

	/**
	 * Updates the current profile(assumes that currentProfile is not null).
	 * 
	 * @param message
	 *            gives the attribute to be updated.
	 */
	private void updateProfile(String message) {
		if (message.equals("Change Status")) {
			updateStatus();
		} else if (message.equals("Change Picture")) {
			updateImage();
		} else if (message.equals("Add Friend")) {
			addFriend();
		}
	}

	/**
	 * Updates the status of the current profile(assumes that currentProfile is
	 * not null).
	 */
	private void updateStatus() {
		currentProfile.setStatus(currentProfile.getName()+ " is "+changeStatus.getText());
		println("Changed Status to : " + currentProfile.getStatus());
		canvas.displayProfile(currentProfile);
		canvas.showMessage("Status updated to " + changeStatus.getText());
	}

	/**
	 * Updates the image of the current profile(assumes that currentProfile is
	 * not null).
	 */
	private void updateImage() {
		GImage image = null;
		try {
			image = new GImage(changePicture.getText());
			currentProfile.setImage(image);
			canvas.displayProfile(currentProfile);
			canvas.showMessage("Picture updated");
			println("Changed Picture");
		} catch (ErrorException e) {
			println("Image not found");
			canvas.displayProfile(currentProfile);
			canvas.showMessage("Image not found");
		}
	}

	/**
	 * Adds as a friend to the current profile whose name is provided in the add
	 * friend text field.
	 */
	private void addFriend() {
		if (!currentProfile.getName().equals(addFriend.getText())
				&& database.containsProfile(addFriend.getText())) {
			if (currentProfile.addFriend(addFriend.getText())) {
				database.getProfile(addFriend.getText()).addFriend(
						currentProfile.getName());
				println(addFriend.getText()
						+ " added successfully to friend list");
				canvas.displayProfile(currentProfile);
				canvas.showMessage(addFriend.getText() + " added as a friend");
			} else {
				println("Friend already exists");
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Friend already exists");
			}
		} else {
			println("Profile with name " + addFriend.getText()
					+ " does not exist.");
			canvas.displayProfile(currentProfile);
			canvas.showMessage("Profile with name " + addFriend.getText()
					+ " does not exist.");
		}
	}

	private JTextField name, changeStatus, changePicture, addFriend;
	private FacePamphletDatabase database;
	private FacePamphletProfile currentProfile;
	private FacePamphletCanvas canvas;

}
