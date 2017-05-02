/**
 * This class inherits from JLabel and implements the turn counter widget.
 *
 * Assignment: MP2
 * Class: CS 340, Fall 2005
 * TA: Nitin Jindal
 * System: jEdit, jdk-1.5.0.4, Windows XP
 * @author Michael Leonhard (CS account mleonhar)
 * @version 22 Sep 2005
*/

import javax.swing.JLabel;

public class TurnsTakenCounterLabel extends JLabel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// data fields
	private int numTurns = 0;
	private  String DESCRIPTION;
	
	public TurnsTakenCounterLabel()
	{
		super();
		reset();
	}
	public void setDifficultyModeLabel(String difficultyMode){
		DESCRIPTION = "Turns Taken: ";
		setHorizontalTextPosition(JLabel.LEFT);
	}
	
	public int getNumOfTurns(){
		return this.numTurns;
		}
	
	/**
	 * Update the text label with the current counter value
	*/
	private void update()
	{
		setText(DESCRIPTION + Integer.toString(this.numTurns));
		setHorizontalTextPosition(JLabel.LEFT);
	}
	
	/**
	 * Default constructor, starts counter at 0
	*/

	
	/**
	 * Increments the counter and updates the text label
	*/
	public void increment()
	{
		this.numTurns++;
		update();
	}
	
	/**
	 * Resets the counter to zero and updates the text label
	*/
	public void reset()
	{
		this.numTurns = 0;
		update();
	}
}