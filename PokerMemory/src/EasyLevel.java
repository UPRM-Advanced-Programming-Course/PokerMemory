import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class EasyLevel extends GameLevel {

	protected EasyLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, 2, mainFrame);
		this.turnsTakenCounter.setDifficultyModeLabel("Easy Level");
		this.cardsPerRow = 4;
		this.rowsPerGrid = 4;
		this.cardsToTurnUp = 2;
		this.totalUniqueCards = rowsPerGrid * cardsPerRow;
	}

	@Override
	protected void makeDeck() {
		// Creates a deck to fill the grid.  Each card appears twice in random places.
		ImageIcon cardIcon[] = this.loadCardIcons();
		ImageIcon backIcon = cardIcon[TotalCardsPerDeck];

		// make an array of card numbers: 0, 0, 1, 1, 2, 2, ..., 7, 7
		// duplicate the image in as many cards as the input imageClones
		int totalCardsInGrid = getRowsPerGrid() * getCardsPerRow();
		int totalUniqueCards = totalCardsInGrid/2;

		// Generate one distinct random card number for each unique card	
		int cardsToAdd[] = new int[totalCardsInGrid];
		boolean cardChosen[] = new boolean[TotalCardsPerDeck];

		int chosenCount = 0;
		Random rand = new Random();
		while (chosenCount < totalUniqueCards)
		{
			int nextCardNo = rand.nextInt(TotalCardsPerDeck);
			if (!cardChosen[nextCardNo]) {
				cardChosen[nextCardNo] = true;
				cardsToAdd[2*chosenCount] = nextCardNo;
				cardsToAdd[2*chosenCount + 1] = nextCardNo;
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
			this.grid.add( new Card(this, cardIcon[num], backIcon, num, rank, suit));
		}
	}

	@Override
	protected boolean addToTurnedCardsBuffer(Card card) {
		// add the card to the list
		this.turnedCardsBuffer.add(card);
		// there are two cards
		if(this.turnedCardsBuffer.size() == getCardsToTurnUp()-1) 
		{
			// Were are turning up the last card
			// record the player's turn
			this.turnsTakenCounter.increment();
			this.turnedCardsBuffer.clear();
			// In easy mode nothing to be done here
		}
		return true;
	}

	@Override
	protected boolean turnUp(Card card) {
		// the card may be turned
		if(this.turnedCardsBuffer.size() < 1) 
		{
			return this.addToTurnedCardsBuffer(card);
		}
		// there are already the number of EasyMode (two face up cards) in the turnedCardsBuffer
		return false;
	}

	@Override
	protected String getMode() {
		// TODO Auto-generated method stub
		return "EasyMode";
	}
}








