/**
 * Represents a card, keeps Icons for front and back, handles mouse clicks
 *
 * @author Michael Leonhard (Original Author)
 * @author Modified by Bienvenido Vélez (UPRM)
 * @version Sept 2017
 */

import java.awt.event.*;
import javax.swing.*;

public class Card extends JLabel implements MouseListener
{
	private static final long serialVersionUID = 1L;

	private GameLevel turnedManager;
	private Icon faceIcon;
	private Icon backIcon;
	private boolean faceUp = false;   			// card is initially face down
	private int num;
	private String suit;
	private String rank;
	private int iconWidthHalf, iconHeightHalf; 	// half the dimensions of the back face icon

	/**
	 * Constructor
	 * @param turnedManager object that manages list of currently turned cards
	 * @param face the face of the card, only one other card has this face
	 * @param back the back of the card, same as all other cards
	 * @param num unique number associated with face icon, used to compare cards for equality
	 * @param rank card rank in String form (e.g. "2", "q", "a", etc.)
	 * @param suit card suit in String form could be one of "c" (clubs), "d" (diamonds), "h" (hearts), or "s" (spades) 
	 */
	public Card(GameLevel turnedManager, Icon face, Icon back, int num, String rank, String suit)
	{
		// initially show face down
		super(back);
		// save parameters
		this.turnedManager = turnedManager;
		this.faceIcon = face;
		this.backIcon = back;
		this.num = num;
		this.suit = suit;
		this.rank = rank;
		// catch mouse clicks and events
		this.addMouseListener(this);
		// store icon dimensions for mouse click testing
		this.iconWidthHalf = back.getIconWidth() / 2;
		this.iconHeightHalf = back.getIconHeight() / 2;
	}

	// Getters

	public int getNum() { return num; }
	public String getRank () { return rank; }
	public String getSuit () { return suit; }

	// Instance methods
	
	/**
	 * Try to turn card face up
	 */
	public void turnUp()
	{
		MemoryFrame.dprintln("Card["+num+"].turnUp()");
		// the card is already face up
		if(this.faceUp) return;
		// ask manager to allow turn
		this.faceUp = this.turnedManager.turnUp(this);
		// allowed to turn, so change the picture
		if(this.faceUp) this.setIcon(this.faceIcon);
	}
	/**
	 * This method forces the card to flip face up.
	 * Can be used to guarantee that a selected card flips face up before a modal dialog pops up
	 */
	
	public void faceUp() {
		this.faceUp = true;
		this.setIcon(this.faceIcon);
	}

	/**
	 * Turn card back over, face down. Usually after timer expires
	 *
	 */
	public void turnDown()
	{
		MemoryFrame.dprintln("Card["+num+"].turnDown()");
		if(!this.faceUp) return;
		this.setIcon(this.backIcon);
		this.faceUp = false;
	}

	/**
	 * Check if the coordinates are over the icon
	 *
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return true if coordinates are over icon, otherwise false
	 */
	private boolean overIcon(int x, int y)
	{
		// calculate the distance from the center of the label
		int distX = Math.abs(x - (this.getWidth() / 2));
		int distY = Math.abs(y - (this.getHeight() / 2));
		// outside icon region
		if(distX > this.iconWidthHalf || distY > this.iconHeightHalf )
			return false;
		// inside icon region
		return true;
	}

	public boolean isFaceUp() {
		return faceUp;
	}

	/** Methods to implement MouseListener interface **********************/

	/**
	 * Invoked when the mouse button has been clicked (pressed and released) on a component.
	 *
	 * @param e object holding information about the button click
	 */
	public void mouseClicked(MouseEvent e)
	{
		// over icon, so try to turn up the card
		MemoryFrame.dprintln("Mouse clicked at x = " + e.getX() + " y = " + e.getY());
		if(overIcon(e.getX(), e.getY())) this.turnUp();
	}

	/**
	 * Invoked when a mouse button has been pressed on a component.
	 *
	 * @param e object holding information about the button press
	 */
	public void mousePressed(MouseEvent e) {}

	/**
	 * Invoked when a mouse button has been released on a component.
	 *
	 * @param e object holding information about the button release
	 */
	public void mouseReleased(MouseEvent e) {}

	/**
	 * Invoked when the mouse enters a component.
	 *
	 * @param e object holding information about the mouse pointer
	 */
	public void mouseEntered(MouseEvent e) {}

	/**
	 * Invoked when the mouse exits a component.
	 *
	 * @param e object holding information about the mouse pointer
	 */
	public void mouseExited(MouseEvent e) {}
}