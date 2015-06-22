/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements
		FacePamphletConstants {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
		// You fill this in
		currentProfileName = null;
		currentProfileImage = null;
		currentProfileStatus = null;
	}

	/**
	 * This method displays a message string near the bottom of the canvas.
	 * Every time this method is called, the previously displayed message (if
	 * any) is replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		// You fill this in
		appMessage = new GLabel(msg);
		appMessage.setFont(PROFILE_FRIEND_FONT);
		add(appMessage, (getWidth() - appMessage.getWidth()) / 2, getHeight()
				- BOTTOM_MESSAGE_MARGIN);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the
	 * bottom of the screen) and then the given profile is displayed. The
	 * profile display includes the name of the user from the profile, the
	 * corresponding image (or an indication that an image does not exist), the
	 * status of the user, and a list of the user's friends in the social
	 * network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		// You fill this in
		removeAll();
		if (profile != null) {
			displayName(profile);
			displayImage(profile);
			displayStatus(profile);
			displayFriends(profile);
		}
	}

	private void displayName(FacePamphletProfile profile) {
		currentProfileName = new GLabel(profile.getName());
		currentProfileName.setFont(PROFILE_NAME_FONT);
		currentProfileName.setColor(Color.BLUE);
		currentProfileName.setLocation(LEFT_MARGIN, TOP_MARGIN
				+ currentProfileName.getHeight());
		add(currentProfileName);
	}

	private void displayImage(FacePamphletProfile profile) {
		if ((currentProfileImage = profile.getImage()) != null) {
			currentProfileImage.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(currentProfileImage, LEFT_MARGIN, currentProfileName.getY()
					+ IMAGE_MARGIN);
		} else {
			showNoImage();
		}

	}

	private void showNoImage() {
		add(new GRect(IMAGE_WIDTH, IMAGE_HEIGHT), LEFT_MARGIN,
				currentProfileName.getY() + IMAGE_MARGIN);
		noImage.setFont(PROFILE_IMAGE_FONT);
		noImage.setLocation(LEFT_MARGIN + (IMAGE_WIDTH - noImage.getWidth())
				/ 2, currentProfileName.getY() + IMAGE_MARGIN + IMAGE_HEIGHT
				/ 2 + noImage.getAscent() / 2);
		add(noImage);
	}

	private void displayStatus(FacePamphletProfile profile) {
		if (profile.getStatus() != null)
			currentProfileStatus = new GLabel(profile.getStatus());
		else
			currentProfileStatus = new GLabel("No current status");
		currentProfileStatus.setFont(PROFILE_FRIEND_LABEL_FONT);
		currentProfileStatus.setLocation(LEFT_MARGIN, currentProfileName.getY()
				+ IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN
				+ currentProfileStatus.getHeight());
		add(currentProfileStatus);

	}

	private void displayFriends(FacePamphletProfile profile) {
		double friendsY = currentProfileName.getY() + TOP_MARGIN;
		friendLabel = new GLabel("Friends:", getWidth() / 2, friendsY);
		friendLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendLabel);
		Iterator<String> friends = profile.getFriends();
		while (friends.hasNext()) {
			GLabel entry = new GLabel(friends.next());
			entry.setFont(PROFILE_FRIEND_FONT);
			friendsY += friendLabel.getHeight();
			entry.setLocation(getWidth() / 2, friendsY);
			add(entry);
		}

	}

	private GLabel currentProfileName, currentProfileStatus, friendLabel;
	private GImage currentProfileImage;
	private GLabel noImage = new GLabel("No Image"), appMessage;
}
