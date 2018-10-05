import edu.princeton.cs.introcs.StdOut;

public class SkunkApp {
	public static void main(String[] args) {		
		
		StdOut.println("Welcome to the Skunk Game.");		
		Game game = new Game();		
		//set player 1 with testable Die1 rollValues = {1,1,1,4,5,6,5,4,3,2,1}; 
		//set player 1 with testable Die2 rollValues = {1,2,3,4,5,6,5,4,3,2,1}; 
		//game.getPlayers()[0]=new Player("MO", new int[]{1,1,1,4,5,6,5,4,3,2,1}, new int[]{1,2,3,4,5,6,5,4,3,2,1});
		game.play();
	}
}
