/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {
		// You fill this in, along with any helper methods //
		database = new NameSurferDataBase(NAMES_DATA_FILE);
		initInteractors();
		add(graph);
	}

	/**
	 * Initialises the interactors (Buttons,Textboxes,Labels) on the screen.
	 */
	private void initInteractors() {
		add(new JLabel("Name"), Program.SOUTH);
		name = new JTextField(25);
		name.setActionCommand("Graph");
		add(name, Program.SOUTH);
		add(new JButton("Graph"), Program.SOUTH);
		add(new JButton("Clear"), Program.SOUTH);
		name.addActionListener(this);
		addActionListeners();
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so
	 * you will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		// You fill this in //
		if (e.getActionCommand().equals("Graph")) {
			// println("Graph : " + database.findEntry(name.getText()));
			NameSurferEntry entry = database.findEntry(name.getText());
			if(entry != null) {
				graph.addEntry(entry);
				graph.update();
			}
		} else if (e.getActionCommand().equals("Clear")) {
			// println("Clear");
			graph.clear();
		}
	}

	private NameSurferDataBase database;
	private NameSurferGraph graph = new NameSurferGraph();
	private JTextField name = null;
}