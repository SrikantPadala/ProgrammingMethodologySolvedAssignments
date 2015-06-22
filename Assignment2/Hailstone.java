/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	private int number,steps = 0;
	public void run() {
		/* You fill this in */
		number = getPositiveInteger();
		showHailStoneSequence();
		println("The process took " + steps + " to reach 1");
	}
	/*
	 * Method : getPositiveInteger
	 * ---------------------------
	 * This method prompts the user for a positive integer until he enters one.
	 */
	private int getPositiveInteger() {
		int number = -1;
		while( number < 0 ) { 
			number = readInt("Enter a positive integer : ");
		}
		return number;
	}
	/*
	 * Method : isEven
	 * ---------------
	 * Checks if the number provided is even 
	 * and returns true or false accordingly
	 */
	private boolean isEven(int number) {
		return ((number % 2) == 0);
	}
	/*
	 * Method : showHailStoneSequence
	 * ------------------------------
	 * Prints the Hail-stone 
	 * If n is even, divide it by two. 
	 * If n is odd, multiply it by three and add one. 
     * Continue this process until n is equal to one.
	 */
	private void  showHailStoneSequence() {
		while( number != 1 ) {
			if( isEven(number) ) {
				print(number + " is even,so I take half: ");
				number /= 2;
				println(number);
			}
			else {
				print(number + " is odd,so I make 3n+1: ");
				number = 3 * number + 1;
				println(number);
			}
			steps++;
		}
	}
}

