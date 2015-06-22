/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {	
	public void run() {
		/* You fill this in. */
		createTarget();
	}
	private void createTarget() {
		final int CENTER_X = getWidth()/2;
		final int CENTER_Y = getHeight()/2;
		
		drawFilledCircle(CENTER_X,CENTER_Y,72,Color.RED);
		drawFilledCircle(CENTER_X,CENTER_Y,(int)(0.65*72),Color.WHITE);
		drawFilledCircle(CENTER_X,CENTER_Y,(int)(0.3*72),Color.RED);
	}
	private void drawFilledCircle(int x,int y,int radius,Color color) {
		GOval circle = new GOval(x-radius,y-radius,2*radius,2*radius);
		circle.setFilled(true);
		circle.setFillColor(color);
		add(circle);
	}
}
