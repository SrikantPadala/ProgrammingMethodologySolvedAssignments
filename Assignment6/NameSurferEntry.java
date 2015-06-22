/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

	/* Constructor: NameSurferEntry(line) */
	/**
	 * Creates a new NameSurferEntry from a data line as it appears in the data
	 * file. Each line begins with the name, which is followed by integers
	 * giving the rank of that name for each decade.
	 */
	public NameSurferEntry(String line) {
		// You fill this in //
		name = null;
		rank = new int[NDECADES];
		parseLine(line);
	}

	/**
	 * Parses the line and stores the name and ranks.
	 * 
	 * @param line
	 *            The line to be parsed.
	 */
	private void parseLine(String line) {
		int nameStart = 0;
		int nameEnd = line.indexOf(" ");

		parseKey(line, nameStart, nameEnd);
		parseValues(line.substring(nameEnd + 1));
	}

	/** Parses the String line to save the name. */
	private void parseKey(String line, int nameStart, int nameEnd) {
		name = line.substring(nameStart, nameEnd);
	}

	/** Parses the String values to save the ranks of various decades. */
	private void parseValues(String values) {
		int valueStart = 0;
		int valueEnd = values.indexOf(" ");
		int i;
		for (i = 0; i < NDECADES - 1; i++) {
			rank[i] = Integer.parseInt(values.substring(valueStart, valueEnd));
			valueStart = valueEnd + 1;
			valueEnd = values.indexOf(" ", valueStart);
		}
		rank[i] = Integer.parseInt(values.substring(valueStart));
	}

	/* Method: getName() */
	/**
	 * Returns the name associated with this entry.
	 */
	public String getName() {
		// You need to turn this stub into a real implementation //
		return name;
	}

	/* Method: getRank(decade) */
	/**
	 * Returns the rank associated with an entry for a particular decade. The
	 * decade value is an integer indicating how many decades have passed since
	 * the first year in the database, which is given by the constant
	 * START_DECADE. If a name does not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {
		// You need to turn this stub into a real implementation //
		return rank[decade];
	}

	/* Method: toString() */
	/**
	 * Returns a string that makes it easy to see the value of a
	 * NameSurferEntry.
	 */
	public String toString() {
		// You need to turn this stub into a real implementation //
		String entry = name;
		entry += " [ ";
		for(int i = 0 ; i < NDECADES; i++) {
			entry += Integer.toString(rank[i]) + " ";
		}
		entry += "]";
		return entry;
	}

	private String name;
	private int[] rank;
}