/**
 * Stores currently turned cards
 * also handles turning cards back down after a delay.
 *
 * @author Michael Leonhard (Original Author)
 * @author Modified by Bienvenido Vélez (UPRM)
 * @version Sept 2017
 */

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class EasyLevel extends GameLevel {

	protected EasyLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, 2, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Easy Level");
		this.setCardsPerRow(4);
		this.setRowsPerGrid(4);
		this.setCardsToTurnUp(2);
		this.setTotalUniqueCards(this.getRowsPerGrid() * this.getCardsPerRow());
	}

	@Override
	protected void makeDeck() {
		// Creates a deck to fill the 4x4 grid with all cards different from each other
		ImageIcon backIcon = this.getCardIcons()[this.getTotalCardsPerDeck()];

		// make an array of card numbers: 0, 0, 1, 1, 2, 2, ..., 7, 7
		// duplicate the image in as many cards as the input imageClones
		int totalCardsInGrid = getRowsPerGrid() * getCardsPerRow();
		int totalUniqueCards = totalCardsInGrid;

		// Generate one distinct random card number for each unique card	
		int cardsToAdd[] = new int[totalCardsInGrid];
		boolean cardChosen[] = new boolean[getTotalCardsPerDeck()];

		int chosenCount = 0;
		Random rand = new Random();
		while (chosenCount < totalUniqueCards)
		{
			int nextCardNo = rand.nextInt(getTotalCardsPerDeck());
			if (!cardChosen[nextCardNo]) {
				cardChosen[nextCardNo] = true;
				cardsToAdd[chosenCount] = nextCardNo;
				chosenCount++;
			}
		}

		// randomize the order of the cards
		this.randomizeIntArray(cardsToAdd);

		// make each card object and add it to the game grid
		for(int i = 0; i < cardsToAdd.length; i++)
		{
			// number of the card, randomized
			int num = cardsToAdd[i];
			// make the card object and add it to the panel
			String rank = cardNames[num].substring(0, 1);
			String suit = cardNames[num].substring(1, 2);
			this.getGrid().add( new Card(this, this.getCardIcons()[num], backIcon, num, rank, suit));
		}
	}

	@Override
	protected boolean turnUp(Card card) {
		// Turn up any card until all are turned up
		this.getMainFrame().getTurnCounterLabel().setText("5");
		if(this.getTurnedCardsBuffer().size() < this.getTotalUniqueCards()) 
		{
			this.getTurnsTakenCounter().increment();
			this.getTurnedCardsBuffer().add(card);
			return true;
		}
		// All cards are turned up
		return false;
	}

	@Override
	public String getLevel() {
		return "Easy Level";
	}
	
	@Override
	public boolean  isGameOver()
	{
		for (int i =0; i< this.getGrid().size();i++)
			if(!this.getGrid().get(i).isFaceUp()) return false;

		return true;
	}
}








