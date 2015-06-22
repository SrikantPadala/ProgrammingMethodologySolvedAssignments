/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private static final int Y_OFFSET = 30;
	private static final int REMAINING = SCAFFOLD_HEIGHT / 2 - BODY_LENGTH / 2
			- 2 * HEAD_RADIUS - ROPE_LENGTH;
	private static final int X_REMAINING_WORD = 50;
	private static final int Y_REMAINING_WORD = 415;
	private static final int X_INCORRECT_GUESSES = 50;
	private static final int Y_INCORRECT_GUESSES = 440;

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		/* You fill this in */
		centre = new GPoint(getWidth() / 2, getHeight() / 2);
		drawScaffold();
		incorrect = "";
		incorrectChoices = new GLabel(incorrect);
		add(incorrectChoices, X_INCORRECT_GUESSES, Y_INCORRECT_GUESSES);
		cipheredWord = new GLabel("");
		cipheredWord.setFont("SANS_SERIF-24");
		add(cipheredWord, X_REMAINING_WORD, Y_REMAINING_WORD);
	}

	//All measurements are with respect to the center of the canvas
	/** Draws the scaffold on the canvas */
	private void drawScaffold() {
		add(new GLine(centre.getX() - BEAM_LENGTH, centre.getY() - Y_OFFSET
				- SCAFFOLD_HEIGHT / 2, centre.getX() - BEAM_LENGTH, centre
				.getY()
				- Y_OFFSET + SCAFFOLD_HEIGHT / 2));
		add(new GLine(centre.getX() - BEAM_LENGTH, centre.getY() - Y_OFFSET
				- SCAFFOLD_HEIGHT / 2, centre.getX(), centre.getY() - Y_OFFSET
				- SCAFFOLD_HEIGHT / 2));
		add(new GLine(centre.getX(), centre.getY() - Y_OFFSET - SCAFFOLD_HEIGHT
				/ 2, centre.getX(), centre.getY() - Y_OFFSET - SCAFFOLD_HEIGHT
				/ 2 + ROPE_LENGTH));
	}

	/** Draws the head of Stick-figure */
	private void drawHead() {
		GOval head = new GOval(2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		head.setLocation(centre.getX() - HEAD_RADIUS, centre.getY() - Y_OFFSET
				- REMAINING - BODY_LENGTH / 2 - 2 * HEAD_RADIUS);
		add(head);
	}

	/** Draws the body of Stick-figure */
	private void drawBody() {
		add(new GLine(centre.getX(), centre.getY() - Y_OFFSET - REMAINING
				- BODY_LENGTH / 2, centre.getX(), centre.getY() - Y_OFFSET
				- REMAINING + BODY_LENGTH / 2));
	}

	/** Draws the left hand of Stick-figure */
	private void drawLeftHand() {
		add(new GLine(centre.getX() - UPPER_ARM_LENGTH,
				centre.getY() - Y_OFFSET - REMAINING - BODY_LENGTH / 2
						+ ARM_OFFSET_FROM_HEAD, centre.getX(), centre.getY()
						- Y_OFFSET - REMAINING - BODY_LENGTH / 2
						+ ARM_OFFSET_FROM_HEAD));
		add(new GLine(centre.getX() - UPPER_ARM_LENGTH,
				centre.getY() - Y_OFFSET - REMAINING - BODY_LENGTH / 2
						+ ARM_OFFSET_FROM_HEAD, centre.getX()
						- UPPER_ARM_LENGTH, centre.getY() - Y_OFFSET
						- REMAINING - BODY_LENGTH / 2 + ARM_OFFSET_FROM_HEAD
						+ LOWER_ARM_LENGTH));
	}

	/** Draws the right hand of Stick-figure */
	private void drawRightHand() {
		add(new GLine(centre.getX() + UPPER_ARM_LENGTH,
				centre.getY() - Y_OFFSET - REMAINING - BODY_LENGTH / 2
						+ ARM_OFFSET_FROM_HEAD, centre.getX(), centre.getY()
						- Y_OFFSET - REMAINING - BODY_LENGTH / 2
						+ ARM_OFFSET_FROM_HEAD));
		add(new GLine(centre.getX() + UPPER_ARM_LENGTH,
				centre.getY() - Y_OFFSET - REMAINING - BODY_LENGTH / 2
						+ ARM_OFFSET_FROM_HEAD, centre.getX()
						+ UPPER_ARM_LENGTH, centre.getY() - Y_OFFSET
						- REMAINING - BODY_LENGTH / 2 + ARM_OFFSET_FROM_HEAD
						+ LOWER_ARM_LENGTH));
	}

	/** Draws the left leg of Stick-figure */
	private void drawLeftLeg() {
		add(new GLine(centre.getX() - HIP_WIDTH, centre.getY() - Y_OFFSET
				- REMAINING + BODY_LENGTH / 2, centre.getX(), centre.getY()
				- Y_OFFSET - REMAINING + BODY_LENGTH / 2));
		add(new GLine(centre.getX() - HIP_WIDTH, centre.getY() - Y_OFFSET
				- REMAINING + BODY_LENGTH / 2, centre.getX() - HIP_WIDTH,
				centre.getY() - Y_OFFSET - REMAINING + BODY_LENGTH / 2
						+ LEG_LENGTH));
	}

	/** Draws the right leg of Stick-figure */
	private void drawRightLeg() {
		add(new GLine(centre.getX() + HIP_WIDTH, centre.getY() - Y_OFFSET
				- REMAINING + BODY_LENGTH / 2, centre.getX(), centre.getY()
				- Y_OFFSET - REMAINING + BODY_LENGTH / 2));
		add(new GLine(centre.getX() + HIP_WIDTH, centre.getY() - Y_OFFSET
				- REMAINING + BODY_LENGTH / 2, centre.getX() + HIP_WIDTH,
				centre.getY() - Y_OFFSET - REMAINING + BODY_LENGTH / 2
						+ LEG_LENGTH));
	}

	/** Draws the left foot of Stick-figure */
	private void drawLeftFoot() {
		add(new GLine(centre.getX() - HIP_WIDTH, centre.getY() - Y_OFFSET
				- REMAINING + BODY_LENGTH / 2 + LEG_LENGTH, centre.getX()
				- HIP_WIDTH - FOOT_LENGTH, centre.getY() - Y_OFFSET - REMAINING
				+ BODY_LENGTH / 2 + LEG_LENGTH));
	}

	/** Draws the right foot of Stick-figure */
	private void drawRightFoot() {
		add(new GLine(centre.getX() + HIP_WIDTH, centre.getY() - Y_OFFSET
				- REMAINING + BODY_LENGTH / 2 + LEG_LENGTH, centre.getX()
				+ HIP_WIDTH + FOOT_LENGTH, centre.getY() - Y_OFFSET - REMAINING
				+ BODY_LENGTH / 2 + LEG_LENGTH));
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		/* You fill this in */
		cipheredWord.setLabel(word);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user.
	 * Calling this method causes the next body part to appear on the scaffold
	 * and adds the letter to the list of incorrect guesses that appears at the
	 * bottom of the window.
	 */
	public void noteIncorrectGuess(char letter) {
		/* You fill this in */
		updateIncorrectGuesses(letter);
		drawNextBodyPart();
	}

	/**
	 * Updates the incorrect guesses string shown on the canvas
	 * 
	 * @param letter
	 *            The letter that is to be concatenated
	 */
	private void updateIncorrectGuesses(char letter) {
		if (incorrect.indexOf(letter) == -1) {
			incorrect += letter;
			incorrectChoices.setLabel(incorrect);
		}
	}

	/** Draws the next body part of the stick figure upon incorrect guess */
	private void drawNextBodyPart() {
		switch (++choice) {
		case 1:
			drawHead();
			break;
		case 2:
			drawBody();
			break;
		case 3:
			drawLeftHand();
			break;
		case 4:
			drawRightHand();
			break;
		case 5:
			drawLeftLeg();
			break;
		case 6:
			drawRightLeg();
			break;
		case 7:
			drawLeftFoot();
			break;
		case 8:
			drawRightFoot();
			break;
		}
	}

	private GPoint centre;
	private int choice = 0;
	private GLabel incorrectChoices, cipheredWord;
	private String incorrect;
}
