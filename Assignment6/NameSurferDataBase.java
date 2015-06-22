/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.util.*;
import java.io.*;

public class NameSurferDataBase implements NameSurferConstants {

	/* Constructor: NameSurferDataBase(filename) */
	/**
	 * Creates a new NameSurferDataBase and initializes it using the data in the
	 * specified file. The constructor throws an error exception if the
	 * requested file does not exist or if an error occurs as the file is being
	 * read.
	 */
	public NameSurferDataBase(String filename) {
		// You fill this in //
		entries = new HashMap<String, NameSurferEntry>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				else {
					NameSurferEntry entry = new NameSurferEntry(line);
					entries.put(entry.getName(), entry);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one exists. If
	 * the name does not appear in the database, this method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		// You need to turn this stub into a real implementation //
		name = toInitCap(name);
		return entries.get(name);
	}

	/** Capitalizes the first letter of name and the rest to small letters.*/
	private String toInitCap(String name) {
		// TODO Auto-generated method stub
		if (name != null) {
			name = Character.toUpperCase(name.charAt(0))
					+ name.substring(1).toLowerCase();
			return name;
		}
		return null;
	}

	private HashMap<String, NameSurferEntry> entries;
}