import java.io.FileNotFoundException;
import java.io.IOException;


public class GameManager {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		// make an instance of the main game class
		MemoryGame instance = new MemoryGame();
		instance.newGame("easy");
		
	}

}
