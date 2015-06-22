/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {

	public void run() {
		// since there is at least one row it should be filled
		fillRow();
		while(leftIsClear()) { //till there are more rows to fill
		
		/*
		 * To fill the checkerboard in a zigzag fashion 
		 * Karel must know whether he put a beeper in the last avenue 
		 * of the previous street so that he can reposition properly
		 * if Karel has put a beeper previously then It should not put a beeper
		 * in the same avenue of the next street
		 * So he has to move to the next avenue towards west(But a special case
		 * arises when there is only one avenue ...
		 * In this case It has to move to the next street)
		 * Else fill row towards west normally
		 */
			if(beepersPresent()) {
				repositionForRowToWest();
				
				if(frontIsClear()) {
					move();
					fillRow();
				}
			} else {
				repositionForRowToWest();
				fillRow();
			}
			if(rightIsClear()) { //if there are more rows to clean
				repositionForRowToEast();
				fillRow();
			} else { //turn around to make the condition leftIsClear false
					 //and stop Karel
				turnAround();
			}
		}
	}
	
	public void fillRow() {
		putBeeper();
		while(frontIsClear()) {
			move();
			if(frontIsClear()) {
				move();
				putBeeper();
			}
		}
	}
	
	/*
	 * Method : repositionForRowToWest
	 * -------------------------------
	 * This method repositions Karel to move to the next row and face west
	 * For this method to work as intended 
	 * Pre-condition : Karel must be facing east on the last avenue
	 * Post-condition : Karel faces west in the next row
	 */
	public void repositionForRowToWest() {
		turnLeft();
		move();
		turnLeft();
	} 
	
	/*
	 * Method : repositionForRowToEast
	 * -------------------------------
	 * This method repositions Karel to move to the next row and face east
	 * For this method to work as intended 
	 * Pre-condition : Karel must be facing west on the first avenue
	 * Post-condition : Karel faces east in the next row
	 */
	
	public void repositionForRowToEast() {
			turnRight();
			move();
			turnRight();	
	}
}
