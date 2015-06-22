/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {

	// You fill in this part
	public void run() {
		moveToDoor();
		pickNewspaper();
		returnHome();
	}
	/*
	 * Method : moveToDoor
	 * -------------------
	 * Moves Karel to the Door
	 */
	private void moveToDoor() {
		moveToWall();
		turnRight();
		while(leftIsBlocked())
			move();
		turnLeft();
	}
	/*
	 * Method : moveToWall
	 * -------------------
	 * It moves Karel from the current position to the wall to which it is 
	 * facing
	 */
	private void moveToWall() {
		while(frontIsClear())
			move();
	}
	/* 
	 * Method : pickNewspaper
	 * ----------------------
	 * Picks the Newspaper one step ahead of the door and turns around
	 * to reach the initial position
	 */
	private void pickNewspaper() {
		move();
		pickBeeper();
		turnAround();
	}
	/*
	 * Method : returnHome
	 * -------------------
	 * It moves Karel to the initial position
	 * Pre-condition : Karel faces west
	 */
	private void returnHome() {
		moveToWall();
		turnRight();
		moveToWall();
		turnRight();
		
	}
}
