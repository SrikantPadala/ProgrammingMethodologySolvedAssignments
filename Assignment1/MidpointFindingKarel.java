/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */
/* 
 * Karel finds the midpoint of a street 
 */
import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

	// You fill in this part
	public void run() {
		createBoundary();
		findMidPoint();
	}
	/*
	 * Method : createBoundary
	 * -----------------------
	 * It makes Karel create a boundary for the street in which Karel is 
	 * present by placing beepers in the first and the last avenue
	 * Note : If the street is only one avenue long then only one beeper is
	 * placed as this avenue is the beginning and the ending of the street
	 * Pre-condition : Karel must be in the first avenue of the street 
	 * 				   facing east
	 * Post-condition : Karel will be in the second last avenue(if there is)
	 * 					facing west 
	 */
	
	private void createBoundary() {
		putBeeper();
		moveToWall();
		//this condition is checked specially for 1x1 world
		if(noBeepersPresent())
			putBeeper();
		moveBack();
	}
	/* Method : findMidPoint
	 * ---------------------
	 * This method finds the midpoint of the street
	 * Pre-condition : The street is bounded by beeper at the beginning and
	 * 				   the ending avenue.
	 * 				   Karel is standing beside the beeper at the last avenue 
	 * 				   and facing away from it.
	 * Post-condition : Karel stands on the midpoint of the street facing east
	 * 					
	 */
	private void findMidPoint() {
		searchForMidPoint();
		//mark the avenue on which it is standing as midpoint
		putBeeper();
		pickAdditionalBeepers();
	}
	/*
	 *  Method : searchForMidPoint
	 *  --------------------------
	 *  Searches for the midpoint by laying beepers at end alternatively
	 *  till the midpoint is found
	 */
	private void searchForMidPoint() {
		while(noBeepersPresent()) {
			putBeeper();
			move();
			moveToBeeper();
			turnAround();
			move();
		}
	}
	/*
	 * Method : moveBack
	 * -----------------
	 * It moves Karel one avenue back if there isn't any wall
	 * Pre-condition : none
	 * Post-condition : Karel moves one avenue back(if there is an avenue back )
	 *  				It will be facing the opposite direction of
	 *  				what it was facing before the method call
	 */
	private void moveBack() {
		turnAround();
		if(frontIsClear())
			move();
	}
	/*
	 * Method : moveToWall
	 * -------------------
	 * It moves Karel from the current position to the wall to which it is 
	 * facing
	 */
	private void moveToWall() {
		while(frontIsClear()) {
			move();
		}
	}
	/*
	 *  Method : pickAdditionalBeepers
	 *  ------------------------------
	 *  Picks up additional beepers which Karel put while searching for midpoint
	 */
	private void pickAdditionalBeepers() {
		cleanARowOfBeepers();
		turnAround();	
		moveToBeeper();
		faceToEast();
	}
	/*
	 * Method : cleanARowOfBeepers
	 * ---------------------------
	 * Pre-condition : Each avenue can have at most one beeper
	 * Post-condition : Karel will be next to  the wall behind it facing  it
	 */
	private void cleanARowOfBeepers(){
		moveToWall();
		turnAround();
		
		while(frontIsClear()) {
			if(beepersPresent())
				pickBeeper();
			if(frontIsClear())
				move();
		}
		pickBeeper();
	}
	/*
	 *  Method : faceToEast
	 *  -------------------
	 *  Makes Karel to face east irrespective of whatever direction it is facing
	 */
	private void faceToEast() {
		while(notFacingEast())
			turnRight();
	}
	/*
	 *  Method : moveToBeeper
	 *  ---------------------
	 *  Moves Karel in the direction it is facing until a beeper is encountered
	 *  For this method to work as intended Karel must not be standing on beeper
	 */
	private void moveToBeeper() {
		while(noBeepersPresent())
			if(frontIsClear())
				move();
	}
}