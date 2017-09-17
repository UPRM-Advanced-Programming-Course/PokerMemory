/**
 * Stores currently turned cards, allows only two two be turned at the same time,
 * also handles turning cards back down after a delay.
 *
 * Assignment: MP2
 * Class: CS 340, Fall 2005
 * TA: Nitin Jindal
 * System: jEdit, jdk-1.5.0.4, Windows XP
 * @author Michael Leonhard (CS account mleonhar)
 * @version 22 Sep 2005
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.awt.MediaTracker;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public abstract class GameLevel implements ActionListener 
{
	protected Vector<Card> turnedCardsBuffer;					// List of cards turned up in current turn
	protected final int TotalCardsPerDeck = 52;
	protected TurnsTakenCounterLabel turnsTakenCounter;			// Turn counter is incremented at every card turned up
	protected Timer turnDownTimer;   							// Timer is used to make a delay
	protected int turnDownDelay = 2000;							// Milliseconds to leave cards up before turning them back down
	protected ArrayList<Card> grid;								// The list of cards in the deck in row major order
	protected JFrame mainFrame;									// The main frame holding the cards
	protected int cardsPerRow = 4;								// Number of cards per row in grid
	protected int rowsPerGrid = 16;								// Number of card rows in Grid
	protected int cardsToTurnUp = 2;							// Number of cards to turn up on each turn
	protected int totalUniqueCards = rowsPerGrid * cardsPerRow;	// Total number of cards in the grid

	protected String cardNames[] = 
		{   "2c", "2d", "2h", "2s", "3c", "3d", "3h", "3s", "4c", "4d", "4h", "4s",
			"5c", "5d", "5h", "5s", "6c", "6d", "6h", "6s", "7c", "7d", "7h", "7s",
			"8c", "8d", "8h", "8s", "9c", "9d", "9h", "9s", "tc", "td", "th", "ts",
			"jc", "jd", "jh", "js", "qc", "qd", "qh", "qs", "kc", "kd", "kh", "ks",
			"ac", "ad", "ah", "as", "back"
		};

	protected String suits[] = { "c", "d", "h", "s" };
	protected String ranks[] = { "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k", "a" };

	/**
	 * Constructor
	 *
	 * @param validTurnTime reference to turn counter label in main program window
	 */
	protected GameLevel(TurnsTakenCounterLabel counterLabel, int cardsToGuess, JFrame mainFrame)
	{
		this.turnsTakenCounter = counterLabel;
		this.turnedCardsBuffer= new Vector<Card>(cardsToGuess);
		this.mainFrame = mainFrame;
		this.turnDownTimer = new Timer(turnDownDelay, this);
		this.turnDownTimer.setRepeats(false);
		this.grid= new ArrayList<Card>();
	}

	// Getters
	protected int getCardsToTurnUp()    { return cardsToTurnUp; }
	protected int getCardsPerRow()      { return cardsPerRow;   }
	protected int getRowsPerGrid()      { return rowsPerGrid;   }
	protected ArrayList<Card> getGrid() { return this.grid; }
	protected abstract String getMode() ;
	
	/**
	 * Selects and adds the cards that will fill the grid according to the requirements of each level
	 *
	 */
	protected abstract void makeDeck();

	protected ImageIcon[] loadCardIcons() {
		// allocate array to store icons for unique cards, last icon is back icon

		ImageIcon icon[] = new ImageIcon[TotalCardsPerDeck+1];

		for(int i = 0; i < TotalCardsPerDeck+1; i++ )
		{
			// make a new icon from a cardX.gif file
			String fileName = "images/cards/" + cardNames[i] + ".gif";
			icon[i] = new ImageIcon(fileName);
			// unable to load icon
			if(icon[i].getImageLoadStatus() == MediaTracker.ERRORED)
			{
				// inform the user of the error and then quit
				JOptionPane.showMessageDialog(this.mainFrame
						, "The image " + fileName + " could not be loaded."
						, "Memory Game Error", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}
		return icon;
	}

	protected  void randomizeIntArray(int[] a){
		// TODO Auto-generated method stub
		Random randomizer = new Random();
		// iterate over the array
		for(int i = 0; i < a.length; i++ )
		{
			// choose a random int to swap with
			int d = randomizer.nextInt(a.length);
			// swap the entries
			int t = a[d];
			a[d] = a[i];
			a[i] = t;
		}
	}

	/**
	 * Adds the card to the list, decides if cards should be left face up, may accumulate score
	 *
	 * @param card the new card to be added
	 * @return true
	 */
	protected abstract boolean addToTurnedCardsBuffer(Card card);

	/**
	 * The specified card wants to turn, add if currently less than 2 cards
	 *
	 * @param card the Card object that wants to turn
	 * @return true if the card is allowed to turn, otherwise false
	 */
	protected abstract boolean turnUp(Card card);

	/**
	 * Remove the specified card from the buffer.
	 *
	 * @param card the Card object to be removed from the buffer of turned cards
	 */
	public void removeFromBuffer(Card card)
	{
		this.turnedCardsBuffer.remove(card);
	}

	/**
	 * Invoked when timer event occurs, turns non-matching cards down
	 *
	 * @param e the timer event information
	 */
	public void actionPerformed(ActionEvent e)
	{
		// turn each card back down
		for(int i = 0; i < this.turnedCardsBuffer.size(); i++ )
		{
			Card card = (Card)this.turnedCardsBuffer.get(i);
			card.turnDown();
		}
		// flip face down the cards
		this.turnedCardsBuffer.clear();
	}
	
	protected abstract boolean  gameOver();

}