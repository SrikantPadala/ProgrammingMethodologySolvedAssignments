/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import acm.util.RandomGenerator;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants,
		ComponentListener {

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		// You fill in the rest //
		displayList = new ArrayList<NameSurferEntry>();
		width = APPLICATION_WIDTH;
		height = APPLICATION_HEIGHT;
		widthSpacer = width / 11;
		rgen = RandomGenerator.getInstance();
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		// You fill this in //
		displayList.clear();
		update();
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note
	 * that this method does not actually draw the graph, but simply stores the
	 * entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		// You fill this in //
		displayList.add(entry);
		update();
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of
	 * entries. Your application must call update after calling either clear or
	 * addEntry; update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		// You fill this in //
		removeAll();
		drawGraph();
	}

	/** Draws the graphs for the names in the current list. */
	private void drawGraph() {
		drawBackgroundGrid();
		drawDecades();
		plotGraphForEachEntry();
	}

	/** Draws the background grid. */
	private void drawBackgroundGrid() {
		drawVerticalLines();
		drawHorizontalLines();
	}

	/** Draws the vertical lines of the grid. */
	private void drawVerticalLines() {
		int widthSpacer = width / 11;
		int x = widthSpacer;
		for (int i = 0; i < NDECADES - 1; i++) {
			add(new GLine(x, 0, x, height));
			x += widthSpacer;
		}
	}

	/** Draws the horizontal lines of the grid. */
	private void drawHorizontalLines() {
		add(new GLine(0, GRAPH_MARGIN_SIZE, width, GRAPH_MARGIN_SIZE));
		add(new GLine(0, height - GRAPH_MARGIN_SIZE, width, height
				- GRAPH_MARGIN_SIZE));
	}
	
	private void drawDecades() {
		int x = 0;
		for(int i = 0; i < NDECADES; i++) {
			add(new GLabel(" "+Integer.toString(START_DECADE + i * 10),x,height));
			x += widthSpacer;
		}
	}

	private void plotGraphForEachEntry() {
		for (int i = 0; i < displayList.size(); i++) {
			plotGraphForEntry(i, rgen.nextColor());
		}
	}

	/** Plots the graph for entry i with the given color. */
	private void plotGraphForEntry(int i, Color color) {
		// Divide the height into 1000 small units.
		double heightSmallestUnit = (double) (height - 2 * GRAPH_MARGIN_SIZE) / 1000;
		double x = 0,x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		String label = null;
		for (int decade = 0; decade < NDECADES - 1; decade++) {
			x1 = x;
			x2 = x + widthSpacer;
			y1 = rank(i, decade) == 0 ? height - GRAPH_MARGIN_SIZE : rank(i,
					decade)* heightSmallestUnit + GRAPH_MARGIN_SIZE;
			y2 = rank(i, decade + 1) == 0 ? height - GRAPH_MARGIN_SIZE : rank(
					i, decade + 1) * heightSmallestUnit + GRAPH_MARGIN_SIZE;
			drawLine(x1, y1, x2, y2, color);
			if(rank(i,decade) == 0)
				label = " " + displayList.get(i).getName() + " *";
			else
				label = " " + displayList.get(i).getName() + " " + rank(i,decade);
			drawLabel(label, x1, y1, color);
			x += widthSpacer;
		}
		drawLabel(label, x2, y2, color);
	}

	private int rank(int i, int decade) {
		return displayList.get(i).getRank(decade);
	}

	private void drawLabel(String label, double x, double y, Color color) {
		GLabel nameLabel = new GLabel(label, x, y);
		nameLabel.setColor(color);
		add(nameLabel);
	}

	private void drawLine(double x1, double y1, double x2, double y2,
			Color color) {
		GLine line = new GLine(x1, y1, x2, y2);
		line.setColor(color);
		add(line);
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		height = getHeight();
		width = getWidth();
		widthSpacer = width / 11;
		update();
	}

	public void componentShown(ComponentEvent e) {
	}

	/** List to hold the entries in the current display. */
	private ArrayList<NameSurferEntry> displayList;
	private int height, width;
	private double widthSpacer;
	private RandomGenerator rgen;
}