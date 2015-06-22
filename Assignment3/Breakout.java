/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Srikant
 * This class plays the game of Breakout.
 */
public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 60;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1)
			* BRICK_SEP)
			/ NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Offset of the left brick column from the left */
	private static final int BRICK_X_OFFSET = (WIDTH - ((NBRICKS_PER_ROW - 1)
			* BRICK_SEP + NBRICKS_PER_ROW * BRICK_WIDTH)) / 2;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	/** Pause time between each update to make the game playable. */
	private static int PAUSE_TIME = 10;

	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		/* You fill this in, along with any subsidiary methods */
		setupBreakout();
		for (int i = 0; i < NTURNS; i++) {
			if (gameCompleted) {
				displayMessage("YOU WON");
				break;
			}
			startBreakout();
		}
		if (turnsLeft == 0) {
			displayMessage("YOU LOSE");
		}
	}

	private void displayMessage(String msg) {
		message = new GLabel(msg);
		message.setColor(Color.RED);
		message.setFont("DIALOG-40");
		message.setLocation((WIDTH - message.getWidth()) / 2, (HEIGHT - message
				.getAscent()) / 2);
		add(message);
	}

	/** Sets up the Breakout game by arranging the bricks and paddle. */
	private void setupBreakout() {
		setupBricks();
		setupPaddle();
	}

	/**
	 * Sets up the Bricks for the game.
	 */
	private void setupBricks() {
		createNRows();
	}

	/** Creates NBRICK_ROWS rows of bricks */
	private void createNRows() {
		for (int i = 0; i < NBRICK_ROWS; ++i) {
			createRow(BRICK_X_OFFSET, BRICK_Y_OFFSET + i
					* (BRICK_HEIGHT + BRICK_SEP), getColorForRow(i));
		}
	}

	/**
	 * Creates a row of bricks starting from location x , y of given color
	 * 
	 * @param x
	 *            X location from where bricks are to be drawn.
	 * @param y
	 *            Y location from where bricks are to be drawn.
	 * @param color
	 *            Colour of the row of bricks.
	 */
	private void createRow(int x, int y, Color color) {
		for (int i = 0; i < NBRICKS_PER_ROW; ++i) {
			add(filledRect(x + i * (BRICK_WIDTH + BRICK_SEP), y, BRICK_WIDTH,
					BRICK_HEIGHT, color));
		}
	}

	/**
	 * Returns the colour for the row provided.
	 * 
	 * @param rowNumber
	 *            Row number for which the colour is needed.
	 * @return Return the colour for the given row number.
	 */
	private Color getColorForRow(int rowNumber) {
		switch (rowNumber) {
		case 0:
		case 1:
			return Color.RED;
		case 2:
		case 3:
			return Color.ORANGE;
		case 4:
		case 5:
			return Color.yellow;
		case 6:
		case 7:
			return Color.GREEN;
		case 8:
		case 9:
			return Color.CYAN;
		default:
			return rgen.nextColor();
		}
	}

	/**
	 * Draws a filled rectangle on the screen with the specified parameters.
	 * 
	 * @param x
	 *            : X coordinate of the rectangle.
	 * @param y
	 *            : Y coordinate of the rectangle.
	 * @param width
	 *            : Width of the rectangle.
	 * @param height
	 *            : Height of the rectangle.
	 * @param color
	 *            : Color of the rectangle.
	 * @return The Rectangle created.
	 */
	private GRect filledRect(int x, int y, int width, int height, Color color) {
		GRect brick = new GRect(x, y, width, height);
		brick.setFilled(true);
		brick.setFillColor(color);
		return brick;
	}

	/** Sets up the paddle */
	private void setupPaddle() {
		paddle = createPaddle();
		addMouseListeners();
		add(paddle);
	}

	/**
	 * Creates the paddle at the provided position.
	 * 
	 * @return The Paddle object
	 */
	private GRect createPaddle() {
		return filledRect((WIDTH - PADDLE_WIDTH) / 2, HEIGHT - PADDLE_Y_OFFSET,
				PADDLE_WIDTH, PADDLE_HEIGHT, Color.BLACK);
	}

	/**
	 * This method is invoked when mouse is moved The paddle moves in the X
	 * direction tracking the movement of mouse
	 */
	public void mouseMoved(MouseEvent e) {
		paddle.move(e.getX() - mouseLastLoc.getX(), 0);
		if (paddle.getX() < 0)
			paddle.setLocation(0, HEIGHT - PADDLE_Y_OFFSET);
		else if (paddle.getX() > WIDTH - PADDLE_WIDTH)
			paddle.setLocation(WIDTH - PADDLE_WIDTH, HEIGHT - PADDLE_Y_OFFSET);
		mouseLastLoc = new GPoint(e.getPoint());
	}

	public void mouseClicked(MouseEvent e) {
		remove(message);
	}

	/** Plays the game till one chance is over. */
	private void startBreakout() {
		setupBall();
		chanceOver = false;
		displayMessage("CLICK TO PLAY");
		initiate(5.0, 5.0);
		// Main game loop
		waitForClick();
		while (!chanceOver) {
			play();
			if (brickCount == 0) {
				gameCompleted = true;
				break;
			}
		}
	}

	/** Sets the ball in the middle of the board for the chance to begin. */
	private void setupBall() {
		ball = createBall(BALL_RADIUS, Color.BLACK);
		add(ball, (WIDTH / 2 - BALL_RADIUS), (HEIGHT / 2 - BALL_RADIUS));
		ball.sendToBack();
	}

	/**
	 * Creates the ball with the given radius and the colour.
	 * 
	 * @param radius
	 *            : Radius of the ball.
	 * @param color
	 *            : Colour of the ball.
	 * @return The ball object.
	 */
	private GOval createBall(int radius, Color color) {
		GOval ball = new GOval(2 * radius, 2 * radius);
		ball.setFilled(true);
		ball.setFillColor(color);
		return ball;
	}

	/** Initialises the velocities of ball in x and y direction */
	private void initiate(double vx, double vy) {
		this.vx = rgen.nextDouble(1.0, vx);
		if (rgen.nextBoolean(0.5)) {
			this.vx = -this.vx;
		}
		this.vy = vy;
	}

	/** This is where the game starts. */
	private void play() {
		updateBall();
		checkForCollision();
		pause(PAUSE_TIME);
	}

	/** Updates the ball with its velocity. */
	private void updateBall() {
		ballLastLoc = ball.getLocation();
		ball.move(vx, vy);
	}

	/** Checks for collision with the Walls and objects (Paddle and bricks). */
	private void checkForCollision() {
		collidedWithWalls();
		collidedWithObject();
	}

	/**
	 * Checks if the ball has collided with walls. If the ball collides with
	 * wall, it bounces (reflects) of the wall. If it is the lower wall the
	 * chance of the player ends.
	 */
	private void collidedWithWalls() {
		if (crossedLeftWall()) {
			bounceHorizontally();
			ball.setLocation(0, ballLastLoc.getY());
			ball.move(vx, vy);
		} else if (crossedRightWall()) {
			bounceHorizontally();
			ball.setLocation(WIDTH - 2 * BALL_RADIUS, ballLastLoc.getY());
			ball.move(vx, vy);
		} else if (crossedTopWall()) {
			bounceVertically();
			ball.setLocation(ballLastLoc.getX(), 0);
			ball.move(vx, vy);
		} else if (crossedBottomWall()) {
			/*
			 * The ball is removed so that the next ball doesn't collide with
			 * this ball after passing the paddle and bounce off.
			 */
			remove(ball);
			chanceOver = true;
			turnsLeft--;
		}
	}

	/** @return True if the ball collides with the left wall */
	private boolean crossedLeftWall() {
		return (ball.getX() < 0);
	}

	/** @return True if the ball collides with the right wall */
	private boolean crossedRightWall() {
		return (ball.getX() > WIDTH - 2 * BALL_RADIUS);
	}

	/** @return True if the ball collides with the top wall */
	private boolean crossedTopWall() {
		return (ball.getY() < 0);
	}

	/** @return True if the ball collides with the bottom wall */
	private boolean crossedBottomWall() {
		return (ball.getY() > HEIGHT - 2 * BALL_RADIUS);
	}

	/**
	 * Checks if the ball has collided with any object. If it does, the ball
	 * bounces(reflects) of that object.
	 */
	private void collidedWithObject() {
		GObject collider = getCollidingObject();
		if (collider != null) {
			bounceClip.play();
			// if it is not paddle it must be brick
			if (collider != paddle) {
				remove(collider);
				bounceVertically();
				brickCount--;
			} else {
				bounceOfPaddle();
			}
		}
	}

	/**
	 * Bounces the ball of the paddle. The ball will bounce in both direction if
	 * the ball hits the upper left or right corner of the paddle.
	 */
	private void bounceOfPaddle() {
		if (ball.getY() + BALL_RADIUS <= paddle.getY() + PADDLE_HEIGHT / 2) {
			bounceVertically();
			// if ball hits the top left part of paddle
			if (ball.getX() + BALL_RADIUS < paddle.getX()) {
				// if the paddle and ball are moving in opposite direction
				// then only the ball will bounce horizontally.
				if (vx > 0) {
					bounceHorizontally();
				}
				ball.move(vx - 2, vy - 2);
			} else if (ball.getX() + BALL_RADIUS > paddle.getX() + PADDLE_WIDTH) {
				if (vx < 0)
					bounceHorizontally();
				ball.move(vx + 2, vy - 2);
			} else {
				ball.move(vx, vy - 2);
			}
		} else {
			bounceHorizontally();
			ball.move(vx, vy);
		}
	}

	/** Negates the X velocity so that the ball moves in opposite direction. */
	private void bounceHorizontally() {
		vx = -vx;
	}

	/** Negates the Y velocity so that the ball moves in opposite direction. */
	private void bounceVertically() {
		vy = -vy;
	}

	/**
	 * Gets the object to which the ball collides
	 * 
	 * @return The object to which the ball collides, which are paddle and
	 *         bricks and null if it does not collide.
	 */
	private GObject getCollidingObject() {
		GObject collider;
		/*
		 * Four points are taken on the square encompassing the ball which
		 * happens to be the four vertices of the square. Each vertex is checked
		 * for collision every time the ball moves
		 */
		if ((collider = getElementAt(ball.getX(), ball.getY())) != null
				|| (collider = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball
						.getY())) != null
				|| (collider = getElementAt(ball.getX(), ball.getY() + 2
						* BALL_RADIUS)) != null
				|| (collider = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball
						.getY()
						+ 2 * BALL_RADIUS)) != null) {
			return collider;
		} else {
			return null;
		}
	}

	/* Instance variables */
	private GRect paddle;
	private GPoint mouseLastLoc = new GPoint(WIDTH / 2, HEIGHT / 2),
			ballLastLoc = new GPoint(WIDTH / 2, HEIGHT / 2);
	private GOval ball;
	/** vx and vy are velocities in x and y direction of ball */
	private double vx, vy;
	private int brickCount = NBRICK_ROWS * NBRICKS_PER_ROW, turnsLeft = NTURNS;
	private boolean chanceOver, gameCompleted = false;
	private GLabel message;
	private AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");

	/** Random generator instance */
	private RandomGenerator rgen = RandomGenerator.getInstance();
}