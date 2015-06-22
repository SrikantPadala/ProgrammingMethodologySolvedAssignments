/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acmx.export.java.util.ArrayList;

import java.io.*;

public class HangmanLexicon {

	/**
	 * Creates the lexicon array by opening the File.
	 */
	public HangmanLexicon() {
		words = new ArrayList();
		String  value;
		try {
			BufferedReader br = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			try {
				while(true) {
					value = br.readLine();
					if(value == null) break;
					words.add(value);
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
}

/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return words.size();
		//return 10;
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		/*
		switch (index) {
			case 0: return "BUOY";
			case 1: return "COMPUTER";
			case 2: return "CONNOISSEUR";
			case 3: return "DEHYDRATE";
			case 4: return "FUZZY";
			case 5: return "HUBBUB";
			case 6: return "KEYHOLE";
			case 7: return "QUAGMIRE";
			case 8: return "SLITHER";
			case 9: return "ZIRCON";
			default: throw new ErrorException("getWord: Illegal index");
		}
		*/
		return (String)words.get(index);
	}
	/** Array to store the lexicon */ 
	private ArrayList words;
}
