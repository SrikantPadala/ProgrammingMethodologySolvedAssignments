import acm.graphics.*;
import java.awt.*;
/** Defines a graphical object combining a rectangle and a label */
public class GLabeledRect extends GCompound implements FacePamphletConstants {

	/** Creates a new GLabeledRect object */
	public GLabeledRect(double width, double height, String text) {
		frame = new GRect(width, height);
		add(frame);
		label = new GLabel(text);
		label.setFont(PROFILE_IMAGE_FONT);
		add(label);
		recenterLabel();
	}

	/** Creates a new GLabeledRect object at a given point */
	public GLabeledRect(double x, double y, double width, double height,
			String text) {
		this(width, height, text);
		setLocation(x, y);
	}

	/** Sets the label font */
	public void setFont(String font) {
		label.setFont(font);
		while (label.getWidth() > frame.getWidth()) {
			int indexHyphen = font.lastIndexOf("-");
			int fontSize = Integer.getInteger(font.substring(indexHyphen + 1));
			label.setFont(font.substring(0, indexHyphen) + "-"
					+ Integer.toString(fontSize));
		}
	}

	/** Sets the text of the label */
	public void setLabel(String text) {
		label.setLabel(text);
		recenterLabel();
	}

	/** Gets the text of the label */
	public String getLabel() {
		return label.getLabel();
	}

	/* Recenters the label in the window */
	private void recenterLabel() {
		double x = (frame.getWidth() - label.getWidth()) / 2;
		double y = (frame.getHeight() + label.getAscent()) / 2;
		label.setFont(PROFILE_IMAGE_FONT);
		label.setLocation(x, y);
	}

	public void scale(double sx, double sy) {
		frame.scale(sx, sy);
		recenterLabel();
	}

	/* Private instance variables */
	private GRect frame;
	private GLabel label;

}
