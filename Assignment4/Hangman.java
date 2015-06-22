/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {
	/** Total number of guesses */
	private static int NUMBER_OF_GUESSES = 8;

	public void init() {
		// Adds the canvas to the right half of the console to display the stick
		// figure
		canvas = new HangmanCanvas();
		add(canvas);
	}

	public void run() {
		canvas.reset();
		createCipheredWord();
		// Main game loop
		while (guessesLeft != 0) {
			showRemainingWord();
			char nextGuess = askGuess();
			updateCipheredWord(nextGuess);
			if (guessed()) {
				println("You guessed the word: " + word);
				println("You won");
				canvas.displayWord(word);
				break;
			}
		}
		if (guessesLeft == 0) {
			println("You're completely hung.");
			println("The word was: " + word);
			println("You lose");
		}
	}

	/**
	 * Initializes the game of Hangman. It gets the word from the lexicon
	 * randomly.
	 */
	public Hangman() {
		println("Welcome to Hangman! ");
		lexicon = new HangmanLexicon();
		word = lexicon.getWord(rgen.nextInt(0, lexicon.getWordCount() - 1));
		wordLength = word.length();
		guessesLeft = NUMBER_OF_GUESSES;
		canvas = new HangmanCanvas();
	}

	/** Creates the ciphered word */
	private void createCipheredWord() {
		cipheredWord = "";
		for (int i = 0; i < wordLength; i++) {
			cipheredWord += "-";
		}
		canvas.displayWord(cipheredWord);
	}

	/**
	 * Asks for a legal guess until the player enters one.
	 * 
	 * @return The guessed character.
	 */
	private char askGuess() {
		String nextGuess = "";
		while (true) {
			nextGuess = readLine("Your Guess: ");
			if (nextGuess.length() == 1
					&& Character.isLetter(nextGuess.charAt(0)))
				return nextGuess.charAt(0);
			else
				println("Illegal guess! Enter only one english alphabet.");
		}
	}

	/**
	 * Updates the ciphered word according to the nextGuess. If nextGuess exists
	 * in the word, it shows all occurrences of it. Else it decrements
	 * guessesLeft and adds the nextGuess to the list of incorrect guesses on
	 * the canvas.
	 * 
	 * @param nextGuess
	 *            is the character guessed by the player.
	 */
	private void updateCipheredWord(char nextGuess) {
		String temp = "";
		boolean found = false;
		canvas.displayWord(cipheredWord);
		for (int i = 0; i < wordLength; i++) {
			if (word.charAt(i) == Character.toUpperCase(nextGuess)) {
				found = true;
				temp += Character.toUpperCase(nextGuess);
			} else
				temp += cipheredWord.charAt(i);
		}
		cipheredWord = temp;
		if (!found) {
			println("There are no " + Character.toUpperCase(nextGuess) + "'s "
					+ "in the word.");
			guessesLeft--;
			canvas.noteIncorrectGuess(Character.toUpperCase(nextGuess));
		} else {
			println("That guess is correct.");
		}
	}

	/**
	 * Shows the remaining word to be guessed in ciphered format. Also shows the
	 * number of guesses left.
	 */
	private void showRemainingWord() {
		println("The word now looks like this: " + cipheredWord);
		canvas.displayWord(cipheredWord);
		println("You have " + guessesLeft + " guesses left.");
	}

	/**
	 * @return True if the whole word is guessed.
	 */
	private boolean guessed() {
		boolean rv = true;
		for (int i = 0; i < wordLength; i++) {
			if (cipheredWord.charAt(i) == '-') {
				rv = false;
				break;
			}
		}
		return rv;
	}

	/**
	 * @return The guesses Left
	 */
	public int getGuessesLeft() {
		return guessesLeft;
	}

	private HangmanCanvas canvas;
	private String word, cipheredWord;
	private int wordLength, guessesLeft;
	private HangmanLexicon lexicon;
	private RandomGenerator rgen = RandomGenerator.getInstance();
}
