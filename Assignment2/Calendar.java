/*
 * Filename : Calendar.java
 * ------------------------
 * 
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Calendar extends GraphicsProgram {
	/* The number of days in the month */
	private static final int DAYS_IN_MONTH = 30;
	
	/* The day of the week on which the month starts */
	/* (Sunday = 0, Monday = 1, Tuesday = 2, and so on) */
	private static final int DAY_MONTH_STARTS = 1;
	
	/* The width in pixels of a day on the calendar */
	private static final int BOX_WIDTH = 40;
	
	/* The height in pixels of a day on the calendar */
	private static final int BOX_HEIGHT = 30;
	
	private static final int DAYS_IN_WEEK = 7;
	
	private static final int CLEARANCE = 4;
	
	public void run() {
		drawLayoutForMonth();
		drawDates();
	}
	private void drawLayoutForMonth() {
		//if month is February and the month starts from Sunday 
		//and it is not a leap year then there
		//will be 4 rows of boxes to accommodate dates
		if(DAYS_IN_MONTH == 28 && DAY_MONTH_STARTS == 0)
			drawRowsOfBoxes(4);
 
	    //if the number of days is 31 and the month starts after Thursday
		//or the number of days is 30 and the month starts after Friday
		else if( (DAYS_IN_MONTH == 31 && DAY_MONTH_STARTS > 4) || 
				 (DAYS_IN_MONTH == 30 && DAY_MONTH_STARTS > 5) )
			drawRowsOfBoxes(6);
		else 
			drawRowsOfBoxes(5);
	}
	/*
	 * Method : drawRowsOfBoxes
	 * ------------------------
	 * Draws n number of rows of 7 boxes each 
	 */
	private void drawRowsOfBoxes(int n) {
		for( int i = 0 ; i < n ; i++ ) {
			for(int j = 0 ; j < DAYS_IN_WEEK ; j++ ) {
				G3DRect box = new G3DRect(j*BOX_WIDTH,i*BOX_HEIGHT,BOX_WIDTH,BOX_HEIGHT);
				box.setFilled(true);
				box.setColor(Color.WHITE);
				box.setRaised(true);
				add( box );
			}
		}
	}
	/*
	 * Method : drawDates
	 * ------------------
	 * Draws the dates within boxes
	 * 
	 */
	private void drawDates() {
		// x and y represents the coordinate of a particular box
		int x = DAY_MONTH_STARTS * BOX_WIDTH, y = 0 ;
		for(int i = 1 ; i <= DAYS_IN_MONTH ; i++ ) {
			GLabel label = new GLabel(Integer.toString(i));
			label.setLocation( x + CLEARANCE , y + label.getAscent() );
			add(label);
			x += BOX_WIDTH ;
			if( ( x * BOX_WIDTH ) % ( DAYS_IN_WEEK * BOX_WIDTH ) == 0 ) {
				y+= BOX_HEIGHT ;
				x %= BOX_WIDTH ;
			}
		}
	}
	public boolean isLeapyear(int year) {
		return ( (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0) ) ;
	}
	
}