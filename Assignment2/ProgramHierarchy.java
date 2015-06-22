/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 * This program draws the Program hierarchy of part of the ACM library
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {	
	private static int CENTRE_X,CENTRE_Y;
	private static final int BOX_WIDTH = 150,BOX_HEIGHT = 50,
	vDistOfBoxFromCenter = 50,hDistOfBoxFromCenter = 200; 
	public void run() {
		/* You fill this in. */
		CENTRE_X = getWidth()/2;
		CENTRE_Y = getHeight()/2;
		drawBoxes();
		drawLines();
		drawLabels();
		
	}
	/*
	 * Method : drawBoxes
	 * ------------------
	 * This methods draws the boxes on the screen.
	 * All boxes are measured from the centre of the screen
	 */
	private void drawBoxes() { 
		GRect program = new GRect(CENTRE_X - BOX_WIDTH/2,
								  CENTRE_Y - vDistOfBoxFromCenter - BOX_HEIGHT/2
								  ,BOX_WIDTH, BOX_HEIGHT);
		
		GRect graphicsProgram = new GRect(CENTRE_X - hDistOfBoxFromCenter - BOX_WIDTH/2,
				  					CENTRE_Y + vDistOfBoxFromCenter - BOX_HEIGHT/2
				  					,BOX_WIDTH, BOX_HEIGHT);
		
		GRect consoleProgram = new GRect(CENTRE_X - BOX_WIDTH/2,
								   CENTRE_Y + vDistOfBoxFromCenter - BOX_HEIGHT/2
								   ,BOX_WIDTH, BOX_HEIGHT);
		
		GRect dialogProgram = new GRect(CENTRE_X + hDistOfBoxFromCenter - BOX_WIDTH/2,
					CENTRE_Y + vDistOfBoxFromCenter - BOX_HEIGHT/2
					,BOX_WIDTH, BOX_HEIGHT);
		
		add(program);
		add(graphicsProgram);
		add(consoleProgram);
		add(dialogProgram);
	}
	/*
	 * Method : drawLines
	 * ------------------
	 * This methods draws the lines on the screen showing the hierarchy.
	 * All lines are measured from the centre of the screen
	 */
	private void drawLines() {
		 add(new GLine(CENTRE_X,CENTRE_Y - vDistOfBoxFromCenter + BOX_HEIGHT/2,
						CENTRE_X - hDistOfBoxFromCenter,
						CENTRE_Y + vDistOfBoxFromCenter - BOX_HEIGHT/2));
		 
		 add(new GLine(CENTRE_X,CENTRE_Y - vDistOfBoxFromCenter + BOX_HEIGHT/2,
						CENTRE_X,CENTRE_Y + vDistOfBoxFromCenter - BOX_HEIGHT/2));
		 
		 add(new GLine(CENTRE_X,CENTRE_Y - vDistOfBoxFromCenter + BOX_HEIGHT/2,
					CENTRE_X + hDistOfBoxFromCenter,
					CENTRE_Y + vDistOfBoxFromCenter - BOX_HEIGHT/2));
	}
	/*
	 * Method : drawLabels
	 * ------------------
	 * This methods draws the strings within the boxes on the screen
	 * All boxes are measured from the centre of the screen
	 */
	private void drawLabels() {
		GLabel program = new GLabel("Program");
		program.setLocation(CENTRE_X - BOX_WIDTH/2 + (BOX_WIDTH - program.getWidth())/2 ,
				CENTRE_Y - vDistOfBoxFromCenter + program.getAscent()/2);
		
		GLabel graphicsProgram = new GLabel("GraphicsProgram");
		graphicsProgram.setLocation(CENTRE_X - hDistOfBoxFromCenter - BOX_WIDTH/2 + 
				(BOX_WIDTH - graphicsProgram.getWidth())/2 ,
				CENTRE_Y + vDistOfBoxFromCenter + graphicsProgram.getAscent()/2);
		
		GLabel consoleProgram = new GLabel("ConsoleProgram");
		consoleProgram.setLocation(CENTRE_X - BOX_WIDTH/2 + 
				(BOX_WIDTH - consoleProgram.getWidth())/2 ,
				CENTRE_Y + vDistOfBoxFromCenter + consoleProgram.getAscent()/2);
		
		GLabel dialogProgram = new GLabel("DialogProgram");
		dialogProgram.setLocation(CENTRE_X + hDistOfBoxFromCenter - BOX_WIDTH/2 + 
				(BOX_WIDTH - dialogProgram.getWidth())/2 ,
				CENTRE_Y + vDistOfBoxFromCenter + dialogProgram.getAscent()/2);
		
		add(program);
		add(graphicsProgram);
		add(consoleProgram);
		add(dialogProgram);
	}
}

