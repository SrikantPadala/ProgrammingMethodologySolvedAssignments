import java.awt.Color;

import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.util.RandomGenerator;

/**
 * 
 */

/**
 * @author Srikant
 * 
 */
public class Ball extends GOval {

	/**
	 * Creates the ball with the specified parameters.
	 * 
	 * @param radius
	 *            : Radius of the ball.
	 * @param color
	 *            : Colour of the ball.
	 */
	Ball(int radius, Color color, double vx, double vy) {
		super(2 * radius, 2 * radius);
		this.setFilled(true);
		this.setFillColor(color);
		this.radius = radius;
		this.vx = rgen.nextDouble(1.0, vx);
		if (rgen.nextBoolean(0.5)) {
			this.vx = -this.vx;
		}
		this.vy = vy;
	}

	/**
	 * @param radius
	 *            the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/** Negates the X velocity so that the ball moves in opposite direction. */
	public void bounceHorizontally() {
		vx = -vx;
	}

	/** Negates the Y velocity so that the ball moves in opposite direction. */
	public void bounceVertically() {
		vy = -vy;
	}

	/**
	 * @return the vx
	 */
	public double getVx() {
		return vx;
	}

	/**
	 * @param vx
	 *            Sets the Velocity of Ball in X-direction.
	 */
	public void setVx(double vx) {
		this.vx = vx;
	}

	/**
	 * @return the vy
	 */
	public double getVy() {
		return vy;
	}

	/**
	 * @param vy
	 *            Sets the Velocity of Ball in Y-direction.
	 */
	public void setVy(double vy) {
		this.vy = vy;
	}

	/** Updates the ball with its velocity. */
	public void update() {
		LastLoc = getLocation();
		move(vx, vy);
	}

	/**
	 * @param ballLastLoc
	 *            the ballLastLoc to set
	 */
	public void setLastLoc(GPoint ballLastLoc) {
		this.LastLoc = ballLastLoc;
	}

	/**
	 * @return the ballLastLoc
	 */
	public GPoint getLastLoc() {
		return LastLoc;
	}

	public void exchangeVelocities(Ball ball) {
		double temp;
		temp = getVx();
		setVx(ball.getVx());
		ball.setVx(temp);
		temp = getVy();
		setVy(ball.getVy());
		ball.setVy(temp);
	}

	private int radius;
	/** vx and vy are velocities in x and y direction of ball */
	private double vx, vy;
	private GPoint LastLoc = new GPoint(Breakout.WIDTH / 2, Breakout.HEIGHT / 2);
	/** Random generator instance */
	private RandomGenerator rgen = RandomGenerator.getInstance();

}
