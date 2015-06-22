/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	private static final int SENTINEL = 0;
	private int largest = SENTINEL,smallest = SENTINEL,number;
	public void run() {
		/* You fill this in */
		println("This program finds the largest and the smallest numbers.");
		while(true) {
			number = readInt("? ");
			if(number == SENTINEL ) {
				showResult();
				break;
			}
			
			else {
				//the first number entered becomes smallest as well as largest number
				if( largest == SENTINEL && smallest == SENTINEL ) {
					largest = smallest = number;
				}
			//if more values are entered smallest and largest change accordingly
				else {
					updateSmallestAndLargest();
				}
			}
		}
	}
	/*
	 * Method : showResult
	 * -------------------
	 * If the first number entered is equal to sentinel it shows a special
	 * message that no values are entered else it shows the largest and 
	 * smallest value
	 */
	private void showResult() {
		//Condition for checking if first value being entered is SENTINEL
		//(it can be found if both largest and smallest are equal to SENTINEL)
		if(largest == SENTINEL && smallest == SENTINEL) {
			println("No values Entered");
		}
		else {
			println("The smallest value is " + smallest);
			println("The largest value is " + largest);
		}
	}
	/*
	 * Method : updateSmallestAndLargest
	 * ---------------------------------
	 * Updates the smallest and the largest according to the new value entered
	 */
	private void updateSmallestAndLargest() {
		if(number > largest)
			largest = number;
		else if(number < smallest)
			smallest = number;
	}
}