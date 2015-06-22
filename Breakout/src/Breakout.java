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
 * @author Srikant This class plays the game of Breakout.
 */
public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	public static final int WIDTH = APPLICATION_WIDTH;
	public static final int HEIGHT = APPLICATION_HEIGHT;

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
	private static int PAUSE_TIME = 15;

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
		if (turnsLeft <= 0) {
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
	 * Creates a row of bricks starting from location x , y of given colour
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
		addKeyListeners();
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

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			paddle.move(-2, 0);
			if (paddle.getX() < 0)
				paddle.setLocation(0, HEIGHT - PADDLE_Y_OFFSET);
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			paddle.move(2, 0);
			if (paddle.getX() > WIDTH - PADDLE_WIDTH)
				paddle.setLocation(WIDTH - PADDLE_WIDTH, HEIGHT - PADDLE_Y_OFFSET);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		remove(message);
	}

	/** Plays the game till one chance is over. */
	private void startBreakout() {
		setupBall();
		chanceOver = false;
		displayMessage("CLICK TO PLAY");
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

	/**
	 * Sets the ball in the middle of the board for the chance to begin. Also
	 * gives the starting velocities.
	 */
	private void setupBall() {
		ball1 = new Ball(BALL_RADIUS, Color.RED, 5.0, 5.0);
		add(ball1, 0 + WIDTH / 4, (HEIGHT / 2 - ball1.getRadius()));
		/*
		 * add(ball1, (WIDTH / 2 - ball1.getRadius()), (HEIGHT / 2 - ball1
		 * .getRadius())); ball1.sendToBack();
		 */
		ball2 = new Ball(BALL_RADIUS - 3, Color.BLACK, 3.0, 3.0);
		/*
		 * add(ball2, (WIDTH / 2 - ball2.getRadius()), (HEIGHT / 2 - ball2
		 * .getRadius()));
		 */
		add(ball2, WIDTH - WIDTH / 4 - 2 * (BALL_RADIUS - 3),
				(HEIGHT / 2 - ball2.getRadius()));
		ball2.sendToBack();
	}

	/** This is where the game starts. */
	private void play() {
		updateBall();
		checkForCollision();
		pause(PAUSE_TIME);
	}

	/** Updates the ball with its velocity. */
	private void updateBall() {
		// if the ball is not out of play the ball gets updated.
		if (ball1 != null)
			ball1.update();
		if (ball2 != null)
			ball2.update();
	}

	/** Checks for collision with the Walls and objects (Paddle and bricks). */
	private void checkForCollision() {
		if (ball1 != null) {
			collidedWithWalls(ball1);
			collidedWithObject(ball1);
		}
		if (ball2 != null) {
			collidedWithWalls(ball2);
			collidedWithObject(ball2);
		}
	}

	/**
	 * Checks if the ball has collided with walls. If the ball collides with
	 * wall, it bounces (reflects) of the wall. If it is the lower wall the
	 * chance of the player ends.
	 */
	private void collidedWithWalls(Ball ball) {
		if (crossedLeftWall(ball)) {
			ball.bounceHorizontally();
			ball.setLocation(0, ball.getLastLoc().getY());
			ball.move(ball.getVx(), ball.getVy());
		} else if (crossedRightWall(ball)) {
			ball.bounceHorizontally();
			ball.setLocation(WIDTH - 2 * ball.getRadius(), ball.getLastLoc()
					.getY());
			ball.move(ball.getVx(), ball.getVy());
		} else if (crossedTopWall(ball)) {
			ball.bounceVertically();
			ball.setLocation(ball.getLastLoc().getX(), 0);
			ball.move(ball.getVx(), ball.getVy());
		} else if (crossedBottomWall(ball)) {
			/*
			 * The ball is removed so that the next ball doesn't collide with
			 * this ball after passing the paddle and bounce off.
			 */
			remove(ball);
			if (ball == ball1)
				ball1 = null;
			else if (ball == ball2)
				ball2 = null;
			if (ball1 == null && ball2 == null) {
				chanceOver = true;
				turnsLeft--;
			}
		}
	}

	/** @return True if the ball collides with the left wall */
	private boolean crossedLeftWall(Ball ball) {
		return (ball.getX() < 0);
	}

	/** @return True if the ball collides with the right wall */
	private boolean crossedRightWall(Ball ball) {
		return (ball.getX() > WIDTH - 2 * ball.getRadius());
	}

	/** @return True if the ball collides with the top wall */
	private boolean crossedTopWall(Ball ball) {
		return (ball.getY() < 0);
	}

	/** @return True if the ball collides with the bottom wall */
	private boolean crossedBottomWall(Ball ball) {
		return (ball.getY() > HEIGHT - 2 * ball.getRadius());
	}

	/**
	 * Checks if the ball has collided with any object. If it does, the ball
	 * bounces(reflects) of that object.
	 */
	private void collidedWithObject(Ball ball) {
		GObject collider = getCollidingObject(ball);
		if (collider != null) {
			bounceClip.play();
			if (collider == ball1) {
				ball1.exchangeVelocities(ball2);
			} else if (collider == ball2) {
				ball1.exchangeVelocities(ball2);
			}
			// if it is not paddle it must be brick
			else if (collider != paddle) {
				remove(collider);
				ball.bounceVertically();
				brickCount--;
			} else {
				bounceOfPaddle(ball);
			}
		}
	}

	/**
	 * Gets the object to which the ball collides
	 * 
	 * @return The object to which the ball collides, which are paddle and
	 *         bricks and null if it does not collide.
	 */
	private GObject getCollidingObject(Ball ball) {
		GObject collider;
		/*
		 * Four points are taken on the square encompassing the ball which
		 * happens to be the four vertices of the square. Each vertex is checked
		 * for collision every time the ball moves
		 */
		if (ball != null) {
			if ((collider = getElementAt(ball.getX(), ball.getY())) != null
					|| (collider = getElementAt(ball.getX() + 2
							* ball.getRadius(), ball.getY())) != null
					|| (collider = getElementAt(ball.getX(), ball.getY() + 2
							* ball.getRadius())) != null
					|| (collider = getElementAt(ball.getX() + 2
							* ball.getRadius(), ball.getY() + 2
							* ball.getRadius())) != null) {
				return collider;
			}
		}
		return null;

	}

	/**
	 * Bounces the ball of the paddle. The ball will bounce in both direction if
	 * the ball hits the upper left or right corner of the paddle.
	 */
	private void bounceOfPaddle(Ball ball) {
		if (ball.getY() + ball.getRadius() <= paddle.getY() + PADDLE_HEIGHT / 2) {
			ball.bounceVertically();
			// if ball hits the top left part of paddle
			if (ball.getX() + ball.getRadius() < paddle.getX()) {
				// if the paddle and ball are moving in opposite direction
				// then only the ball will bounce horizontally.
				if (ball.getVx() > 0) {
					ball.bounceHorizontally();
				}
				ball.move(ball.getVx() - 2, ball.getVy() - 2);
			} else if (ball.getX() + ball.getRadius() > paddle.getX()
					+ PADDLE_WIDTH) {
				if (ball.getVx() < 0)
					ball.bounceHorizontally();
				ball.move(ball.getVx() + 2, ball.getVy() - 2);
			} else {
				ball.move(ball.getVx(), ball.getVy() - 2);
			}
		} else {
			ball.bounceHorizontally();
			ball.move(ball.getVx(), ball.getVy());
		}
	}

	/* Instance variables */
	private GRect paddle;
	private GPoint mouseLastLoc = new GPoint(WIDTH / 2, HEIGHT / 2);
	private volatile Ball ball1, ball2;
	private int brickCount = NBRICK_ROWS * NBRICKS_PER_ROW, turnsLeft = NTURNS;
	private boolean chanceOver, gameCompleted = false;
	private GLabel message;
	private AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
	/** Random generator instance */
	private RandomGenerator rgen = RandomGenerator.getInstance();

	public static void main(String[] args) {
		new Breakout().start(args);
	}
}
