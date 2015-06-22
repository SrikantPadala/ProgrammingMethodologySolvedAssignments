/*
 * File: StoneMasonKarel.java
 * --------------------------
 * Karel repairs the Quad by repairing each column at a time
 * till all the columns have been repaired. 
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

	// You fill in this part
	public void run() {
		repairQuad();
	}
	/*
	 * Method : repairQuad
	 * -------------------
	 * Repairs the whole quad by repairing each column at a time.
	 */
	private void repairQuad() {
		while(frontIsClear()) {
			repairColumn();
			moveToNextColumn();
		}
		repairColumn();
	}
	/*
	 * Method : repairColumn
	 * ---------------------
	 * Repairs the column where Karel is standing
	 * Pre-condition : Karel is standing at the base of the column facing east
	 * Post-condition : Karel will be at the base of the same column facing east
	 */
	private void repairColumn() {
		turnLeft();
		while(frontIsClear()) {
			if(noBeepersPresent())
				putBeeper();
			move();
		}
		if(noBeepersPresent())
			putBeeper();
		getBackToBottom();
	}
	/*
	 * Method : getBackToBottom
	 * ------------------------
	 * Pre-condition : Karel is at the top of the column facing North
	 * Post-condition : Karel will be at the base of the column facing east
	 */
	
	private void getBackToBottom() {
		turnAround();
		while(frontIsClear())
			move();
		turnLeft();
	}
	/*
	 * Method : moveToNextColumn
	 * -------------------------
	 * Moves Karel to the next column
	 */
	private void moveToNextColumn() {
		for( int i = 0 ; i < 4 ; i++ )
			move();
	}
}
