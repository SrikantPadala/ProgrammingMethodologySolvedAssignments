/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;
	
	public void run() {
		/* You fill this in. */
		int x;
		int y = getHeight(); //get the y coordinate for each row of bricks
		for( int i = BRICKS_IN_BASE ; i > 0 ; i-- ) {
			//x is the offset from left of the window to 
			//first brick in a particular row
			x = (getWidth() -  i * BRICK_WIDTH) / 2;
			y -= BRICK_HEIGHT;
			for( int j = 0 ; j < i ; j++ ) {
				add(new GRect(x,y,BRICK_WIDTH,BRICK_HEIGHT));
				x += BRICK_WIDTH;
			}
		}
	}
}

