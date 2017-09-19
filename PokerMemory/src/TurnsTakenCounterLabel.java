/**
 * Inherits from JLabel and implements the turn counter widget.
 *
 * @author Michael Leonhard (Original Author)
 * @author Modified by Bienvenido Vélez (UPRM)
 * @version Sept 2017
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
	//private  String DESCRIPTION;
	
	public TurnsTakenCounterLabel()
	{
		super();
		reset();
	}
	
	public void setDifficultyModeLabel(String difficultyMode){
		//DESCRIPTION = "Turns Taken: ";
		//setHorizontalTextPosition(JLabel.LEFT);
	}
	
	public int getNumOfTurns(){
		return this.numTurns;
		}
	
	/**
	 * Update the text label with the current counter value
	*/
	private void update()
	{
		this.setText("" + this.getNumOfTurns());
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