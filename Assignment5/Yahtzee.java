/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	/**
	 * Initializes the players,scores and creates the display.
	 */
	public Yahtzee() {
		super();
		IODialog dialog = getDialog();
		while (true) {
			nPlayers = dialog.readInt("Enter number of players");
			if (nPlayers > 4) dialog.println("Maximum of four players are allowed");
			else if (nPlayers <= 0) dialog.println("Atleast one player required");
			else break;
		}
		playerNames = new String[nPlayers];
		scores = new int[nPlayers][N_CATEGORIES];
		categoryAssigned = new boolean[nPlayers][N_CATEGORIES];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
	}

	public void run() {
		playGame();
	}

	/**
	 * Plays the game of Yahtzee.
	 */
	private void playGame() {
		/* You fill this in */
		for (int k = 0; k < N_SCORING_CATEGORIES; k++) {
			for (int i = 1; i <= nPlayers; ++i) {
				waitForThreeRollsOfPlayer(i);
				// cheatInput();
				waitForSelectingCategoryOfPlayer(i);
			}
		}
		calculateFinaScoresForAllPlayers();
		announceWinner();
	}

	private void calculateFinaScoresForAllPlayers() {
		for (int i = 1; i <= nPlayers; i++) {
			calculateUpperScore(i);
			calculateUpperBonus(i);
			calculateLowerScore(i);
			calculateTotal(i);
		}
	}

	private void cheatInput() {
		IODialog dialog = getDialog();
		for (int i = 1; i <= N_DICE; i++) {
			dice[i - 1] = dialog.readInt("Enter Dice " + i + " value ");
		}
		display.displayDice(dice);
	}

	/**
	 * Waits till a player selects an unassigned category. After category is
	 * selected the scores are updated.
	 * 
	 * @param i
	 *            is the player number.
	 */
	private void waitForSelectingCategoryOfPlayer(int i) {
		display.printMessage("Select a category for this roll.");
		while (true) {
			int category = display.waitForPlayerToSelectCategory();
			if (!categoryAssigned[i - 1][category]) {
				categoryAssigned[i - 1][category] = true;
				scores[i - 1][category] = getScoreForCategory(category);
				display.updateScorecard(category, i, scores[i - 1][category]);
				break;
			} else
				display.printMessage("Category already assigned."
						+ "Select a different one.");
		}
	}

	/**
	 * Announces the winner with the maximum score.
	 */
	private void announceWinner() {
		int player;
		player = findMaxofTotal();
		display.printMessage("Congratulations," + playerNames[player - 1]
				+ ",you're the winner with a total score of"
				+ scores[player - 1][TOTAL] + "!");
	}

	private int findMaxofTotal() {
		int max = scores[0][TOTAL];
		int player = 1;
		for (int i = 1; i <= nPlayers; i++) {
			if (scores[i - 1][TOTAL] > max) {
				max = scores[i - 1][TOTAL];
				player = i;
			}
		}
		return player;
	}

	/**
	 * Calculates Total which is the sum of lower score,upper score and upper
	 * bonus.
	 */
	private void calculateTotal(int i) {
		scores[i - 1][TOTAL - 1] = scores[i - 1][UPPER_SCORE - 1]
				+ scores[i - 1][UPPER_BONUS - 1]
				+ scores[i - 1][LOWER_SCORE - 1];
		display.updateScorecard(TOTAL, i, scores[i - 1][TOTAL - 1]);
	}

	/**
	 * Calculates Lower score which is the sum of scores from THREE_OF_A_KIND
	 * through CHANCE.
	 */
	private void calculateLowerScore(int i) {
		for (int j = THREE_OF_A_KIND; j <= CHANCE; j++) {
			scores[i - 1][LOWER_SCORE - 1] += scores[i - 1][j - 1];
		}
		display.updateScorecard(LOWER_SCORE, i, scores[i - 1][LOWER_SCORE - 1]);
	}

	/**
	 * Calculates Lower score which is the sum of scores from ONES through
	 * SIXES.
	 */
	private void calculateUpperScore(int i) {
		for (int j = ONES; j <= SIXES; j++) {
			scores[i - 1][UPPER_SCORE - 1] += scores[i - 1][j - 1];
		}
		display.updateScorecard(UPPER_SCORE, i, scores[i - 1][UPPER_SCORE - 1]);
	}

	/**
	 * Calculates upper bonus if upper score is greater than some threshold
	 * value.
	 */
	private void calculateUpperBonus(int i) {
		if (scores[i - 1][UPPER_SCORE - 1] >= THRESHOLD_FOR_UPPER_BONUS)
			scores[i - 1][UPPER_BONUS - 1] = SCORE_UPPER_BONUS;
		else
			scores[i - 1][UPPER_BONUS - 1] = 0;
		display.updateScorecard(UPPER_BONUS, i, scores[i - 1][UPPER_BONUS - 1]);
	}

	/**
	 * Waits for player to roll the dice three times. First time all the dice
	 * are rolled. In the next two rolls the player can select some or all the
	 * dice to roll.
	 * 
	 * @param i
	 *            is the player number.
	 */
	private void waitForThreeRollsOfPlayer(int i) {
		display.printMessage(playerNames[i - 1]
				+ "'s turn. Click \"Roll Dice\" button to roll the dice.");
		display.waitForPlayerToClickRoll(i);
		rollAllDice();
		display.displayDice(dice);
		for (int j = 1; j <= 2; ++j) {
			display.printMessage("Select the dice you wish to re-roll "
					+ "and click \"Roll again.\"");
			display.waitForPlayerToSelectDice();
			rollSelectedDice();
			display.displayDice(dice);
		}
	}

	private int getScoreForCategory(int category) {
		if (checkCategory(category)) {
			switch (category) {
			case ONES:
			case TWOS:
			case THREES:
			case FOURS:
			case FIVES:
			case SIXES:
				return getScore(category);
			case THREE_OF_A_KIND:
			case FOUR_OF_A_KIND:
			case CHANCE:
				return getSumOfDice();
			case FULL_HOUSE:
				return SCORE_FULL_HOUSE;
			case SMALL_STRAIGHT:
				return SCORE_SMALL_STRAIGHT;
			case LARGE_STRAIGHT:
				return SCORE_LARGE_STRAIGHT;
			case YAHTZEE:
				return SCORE_YAHTZEE;
			}
		}
		return 0;
	}

	private boolean checkCategory(int category) {
		switch (category) {
		case ONES:
		case TWOS:
		case THREES:
		case FOURS:
		case FIVES:
		case SIXES:
		case CHANCE:
			return true;
		case THREE_OF_A_KIND:
			return isThreeOfAKind();
		case FOUR_OF_A_KIND:
			return isFourOfAKind();
		case FULL_HOUSE:
			return isFullHouse();
		case SMALL_STRAIGHT:
			return isSmallStraight();
		case LARGE_STRAIGHT:
			return isLargeStraight();
		case YAHTZEE:
			return isYahtzee();
		default:
			return false;
		}
	}

	private boolean isYahtzee() {
		if (dice[0] == dice[1] && dice[1] == dice[2] && dice[2] == dice[3]
				&& dice[3] == dice[4])
			return true;
		return false;
	}

	private boolean isLargeStraight() {
		boolean flag[] = new boolean[6];
		for (int i = 1; i <= 6; i++) {
			flag[i - 1] = isNumberPresent(i);
		}
		if (flag[1] && flag[2] && flag[3] && flag[4]) {
			if (flag[0] || flag[5])
				return true;
		}
		return false;
	}

	/**
	 * Checks whether the 'value' is present in any of the dice.
	 * 
	 * @param value
	 *            is the value being checked for.
	 * @return True if the 'value' is present.
	 */
	private boolean isNumberPresent(int value) {
		for (int i = 1; i <= N_DICE; i++) {
			if (dice[i - 1] == value)
				return true;
		}
		return false;
	}

	private boolean isSmallStraight() {
		boolean flag[] = new boolean[6];
		for (int i = 1; i <= 6; i++) {
			flag[i - 1] = isNumberPresent(i);
		}
		if (flag[2] && flag[3]) {
			if ((flag[0] && flag[1]) || (flag[1] && flag[4])
					|| (flag[4] && flag[5]))
				return true;
		}
		return false;
	}

	private boolean isFullHouse() {
		if (dice[0] == dice[1] && dice[1] == dice[2] && dice[3] == dice[4]
				|| dice[0] == dice[1] && dice[1] == dice[3]
				&& dice[2] == dice[4] || dice[0] == dice[1]
				&& dice[1] == dice[4] && dice[2] == dice[3]
				|| dice[0] == dice[2] && dice[2] == dice[3]
				&& dice[1] == dice[4] || dice[0] == dice[2]
				&& dice[2] == dice[4] && dice[1] == dice[3]
				|| dice[0] == dice[3] && dice[3] == dice[4]
				&& dice[1] == dice[2] || dice[1] == dice[2]
				&& dice[2] == dice[3] && dice[0] == dice[4]
				|| dice[1] == dice[2] && dice[2] == dice[4]
				&& dice[0] == dice[3] || dice[1] == dice[3]
				&& dice[3] == dice[4] && dice[0] == dice[2]
				|| dice[2] == dice[3] && dice[3] == dice[4]
				&& dice[0] == dice[1])
			return true;
		return false;
	}

	private boolean isThreeOfAKind() {
		if (dice[0] == dice[1] && dice[1] == dice[2] || dice[0] == dice[1]
				&& dice[1] == dice[3] || dice[0] == dice[1]
				&& dice[1] == dice[4] || dice[0] == dice[2]
				&& dice[2] == dice[3] || dice[0] == dice[2]
				&& dice[2] == dice[4] || dice[0] == dice[3]
				&& dice[3] == dice[4] || dice[1] == dice[2]
				&& dice[2] == dice[3] || dice[1] == dice[2]
				&& dice[2] == dice[4] || dice[1] == dice[3]
				&& dice[3] == dice[4] || dice[2] == dice[3]
				&& dice[3] == dice[4])
			return true;
		return false;
	}

	private boolean isFourOfAKind() {
		if (dice[0] == dice[1] && dice[1] == dice[2] && dice[2] == dice[3]
				|| dice[0] == dice[1] && dice[1] == dice[2]
				&& dice[2] == dice[4] || dice[0] == dice[1]
				&& dice[1] == dice[3] && dice[3] == dice[4]
				|| dice[0] == dice[2] && dice[2] == dice[3]
				&& dice[3] == dice[4] || dice[1] == dice[2]
				&& dice[2] == dice[3] && dice[3] == dice[4])
			return true;
		return false;
	}

	/**
	 * Gets the sum of the points of all dice.
	 * 
	 * @return The score.
	 */
	private int getSumOfDice() {
		int score = 0;
		for (int i = 0; i < N_DICE; ++i) {
			score += dice[i];
		}
		return score;
	}

	/**
	 * Gets the sum of the points on the dice equal to category.
	 * 
	 * @param category
	 *            is ONES,TWOS,THREES,FOURS,FIVES OR SIXES
	 * @return The score.
	 */
	private int getScore(int category) {
		int score = 0;
		for (int i = 0; i < N_DICE; ++i) {
			if (dice[i] == category) {
				score += dice[i];
			}
		}
		return score;
	}

	/**
	 * Rolls the selected dice.
	 */
	private void rollSelectedDice() {
		int low = 1, high = 6;
		for (int i = 0; i < N_DICE; ++i) {
			if (display.isDieSelected(i))
				dice[i] = rgen.nextInt(low, high);
		}
	}

	/**
	 * Rolls all dice.
	 */
	private void rollAllDice() {
		int low = 1, high = 6;
		for (int i = 0; i < N_DICE; ++i) {
			dice[i] = rgen.nextInt(low, high);
		}
	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] dice = new int[N_DICE];
	private int[][] scores;
	private boolean[][] categoryAssigned;
}