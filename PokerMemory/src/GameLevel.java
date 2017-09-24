/**
 * Stores currently turned cards
 * also handles turning cards back down after a delay.
 *
 * @author Michael Leonhard (Original Author)
 * @author Modified by Bienvenido Vélez (UPRM)
 * @version Sept 2017
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
	private Vector<Card> turnedCardsBuffer;					// List of cards turned up in current turn
	private int TotalCardsPerDeck = 52;
	private TurnsTakenCounterLabel turnsTakenCounter;	    // Turn counter is incremented at every card turned up
	private Timer turnDownTimer;   							// Timer is used to make a delay
	private int turnDownDelay = 2000;						// Milliseconds to leave cards up before turning them back down
	private ArrayList<Card> grid;							// The list of cards in the deck in row major order
	private MemoryFrame mainFrame;								// The main frame holding the cards
	private int cardsPerRow = 4;								// Number of cards per row in grid
	private int rowsPerGrid = 16;							// Number of card rows in Grid
	private int cardsToTurnUp = 2;							// Number of cards to turn up on each turn
	private int totalUniqueCards = rowsPerGrid * cardsPerRow;	// Total number of cards in the grid

	protected String cardNames[] = 
		{   "2c", "2d", "2h", "2s", "3c", "3d", "3h", "3s", "4c", "4d", "4h", "4s",
				"5c", "5d", "5h", "5s", "6c", "6d", "6h", "6s", "7c", "7d", "7h", "7s",
				"8c", "8d", "8h", "8s", "9c", "9d", "9h", "9s", "tc", "td", "th", "ts",
				"jc", "jd", "jh", "js", "qc", "qd", "qh", "qs", "kc", "kd", "kh", "ks",
				"ac", "ad", "ah", "as", "back"
		};

	protected String suits[] = { "c", "d", "h", "s" };
	protected String ranks[] = { "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k", "a" };

	private ImageIcon cardIcons[];
	
	/**
	 * Constructor
	 *
	 * @param validTurnTime reference to turn counter label in main program window
	 */
	protected GameLevel(TurnsTakenCounterLabel counterLabel, int cardsToGuess, JFrame mainFrame)
	{
		this.turnsTakenCounter = counterLabel; counterLabel.reset();
		this.turnedCardsBuffer= new Vector<Card>(cardsToGuess);
		this.mainFrame = (MemoryFrame) mainFrame;
		this.turnDownTimer = new Timer(turnDownDelay, this);
		this.turnDownTimer.setRepeats(false);
		this.grid= new ArrayList<Card>();
		this.loadCardIcons();
	}

	// Getters
	public int getCardsToTurnUp()              { return cardsToTurnUp;     }
	public int getCardsPerRow()                { return cardsPerRow;       }
	public int getRowsPerGrid()                { return rowsPerGrid;       }
	public ArrayList<Card> getGrid()           { return this.grid;         }
	public Vector<Card> getTurnedCardsBuffer() { return turnedCardsBuffer; }
	public int getTotalCardsPerDeck()          { return TotalCardsPerDeck; }
	public int getTotalUniqueCards()           { return totalUniqueCards;  }
	public Timer getTurnDownTimer()            { return turnDownTimer;     }
	public ImageIcon[] getCardIcons()          { return cardIcons;         }
	public MemoryFrame getMainFrame()          { return mainFrame;         }
	public TurnsTakenCounterLabel getTurnsTakenCounter() { return turnsTakenCounter; }
	public abstract String getLevel() ;

	// Setters
	public void setTurnedCardsBuffer(Vector<Card> turnedCardsBuffer) {
		this.turnedCardsBuffer = turnedCardsBuffer;
	}

	public void setCardsPerRow(int cardsPerRow) {
		this.cardsPerRow = cardsPerRow;
	}

	public void setRowsPerGrid(int rowsPerGrid) {
		this.rowsPerGrid = rowsPerGrid;
	}

	public void setCardsToTurnUp(int cardsToTurnUp) {
		this.cardsToTurnUp = cardsToTurnUp;
	}

	public void setTotalUniqueCards(int totalUniqueCards) {
		this.totalUniqueCards = totalUniqueCards;
	}

	public void setTotalCardsPerDeck(int totalCardsPerDeck) {
		TotalCardsPerDeck = totalCardsPerDeck;
	}

	public void setGrid(ArrayList<Card> grid) { 
		this.grid = grid;
	}

	public void setTurnsTakenCounter(TurnsTakenCounterLabel turnsTakenCounter) {
		this.turnsTakenCounter = turnsTakenCounter;
	}
	
	public void setTurnDownTimer(Timer turnDownTimer) {
		this.turnDownTimer = turnDownTimer;
	}
	
	public void setCardIcons(ImageIcon[] cardIcons) {
		this.cardIcons = cardIcons;
	}
	
	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = (MemoryFrame) mainFrame;
	}

	/**
	 * Selects and adds the cards that will fill the grid according to the requirements of each level
	 *
	 */
	protected abstract void makeDeck();

	/**
	 * The specified card wants to turn, add if currently less than 2 cards
	 *
	 * @param card the Card object that wants to turn
	 * @return true if the card is allowed to turn, otherwise false
	 */
	protected abstract boolean turnUp(Card card);

	/**
	 * Invoked when timer event occurs, turns non-matching cards down
	 *
	 * @param e the timer event information
	 */
	public void actionPerformed(ActionEvent e)
	{
		// turn each card back down after timer runs out
		for(int i = 0; i < this.turnedCardsBuffer.size(); i++ )
		{
			Card card = (Card)this.turnedCardsBuffer.get(i);
			card.turnDown();
		}
		this.turnedCardsBuffer.clear();
	}
	
	/**
	 * Returns true iff game is over. False otherwise.
	 * 
	 */
	public abstract boolean  isGameOver();
	
	// Utility methods potentially useful in subclasses
	
	protected void loadCardIcons() {
		// allocate array to store icons for unique cards, last icon is back icon

		this.cardIcons = new ImageIcon[TotalCardsPerDeck+1];

		for(int i = 0; i < TotalCardsPerDeck+1; i++ )
		{
			// make a new icon from a cardX.gif file
			String fileName = "images/cards/" + cardNames[i] + ".gif";
			this.cardIcons[i] = new ImageIcon(fileName);
			// unable to load icon
			if(this.cardIcons[i].getImageLoadStatus() == MediaTracker.ERRORED)
			{
				// inform the user of the error and then quit
				JOptionPane.showMessageDialog(this.mainFrame
						, "The image " + fileName + " could not be loaded."
						, "Memory Game Error", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}
		//return this.cardIcons;
	}

	protected  void randomizeIntArray(int[] a){
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

}