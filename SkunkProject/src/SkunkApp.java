import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import javax.swing.plaf.FontUIResource;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;
//Presentation Logic
public class SkunkApp {
	public static void main(String[] args) {		

		//Does not create objects of other classes except the observer (controller) class
		//sends requests to observer
		//receives responses from observer
		//sends stdOut to the user
		//gets stdIn from the user
		//Doesn't do any business logic i.e no calculations or metric handling. 
		

		
		StdOut.println("**************************");		
		StdOut.println("Welcome to the Skunk Game.".toUpperCase());		
		StdOut.println("**************************");		

		

		int numberOfPlayers = getNumberOfPlayers();
		StdOut.println("There are "+ numberOfPlayers + " players");
		
		String[] playerNames = setPlayerNames(numberOfPlayers);
		StdOut.println("Player Names are :\n");
		showPlayerNames(playerNames);
		StdOut.println("\n");

		//This will be the one and only object that this class will create. 
		Observer observer = new Observer(playerNames);
		
		Player player  	= null;	
		String nextStep = "";
		String answer 	= "";	
		String response = "";		
		
		for (int i = 0; i < 10; i++) {
			
			player = observer.getActivePlayer();	
			StdOut.println("ACTIVE PLAYER IS");			
			StdOut.println(player);	
			
			nextStep = observer.whatNext();
			System.out.println(nextStep);
			
			if(nextStep.equalsIgnoreCase("ROLL?")) {
				
				StdOut.println(player.getName() + ",  would you like to roll?");			
				answer = StdIn.readLine();
				while(!(answer.trim().length()>0)){
					answer = StdIn.readLine();
				}
				answer = answer.trim().toUpperCase();	
				System.out.println("USER ANSWERED "+answer);					
			}
			observer.requestAction(answer);
			System.out.println("SUD RESPONDED WITH \n" + response);			
			answer = "";
		}

		
		System.exit(0);
		
		

		
		
		


	}

	/**
	 * @param playerNames
	 */
	private static void showPlayerNames(String[] playerNames) {
		for (int i = 0; i < playerNames.length; i++) {
			StdOut.println("Player "+ (i+1) + "'s name is "+ playerNames[i]);
		}
	}

	/**
	 * @param numberOfPlayers
	 * @return String array of player Names
	 */
	private static String[] setPlayerNames(int numberOfPlayers) {
		String[] playerNames = new String[numberOfPlayers];
		String name = "";
		for (int i = 0; i < playerNames.length; i++) {
			
			StdOut.println("Enter Player "+ (i+1) + "'s name ");			
			name = StdIn.readLine();
			while(!(name.trim().length()>0)){
				name = StdIn.readLine();
			}
			name = name.trim().toUpperCase();
			playerNames[i] = name;	
		}
		return playerNames;
	}
	
	/**
	 	Throws NoSuchElementException - if standard input is empty
	 	Throws InputMismatchException - if the next token cannot be parsed as an int 
	 */
	private static int getNumberOfPlayers() {
		StdOut.println("Enter the number of players");

		int result				= 0;		
 		try {
			result = StdIn.readInt();
			
		} catch (InputMismatchException IME) {			
			System.err.println("WARNING: Wrong Input format!!! Enter positive NUMBERS ONLY");
			result = getNumberOfPlayers();
		}
		catch (NoSuchElementException NSE) {			
			System.err.println("WARNING: Invalid Input !!!");
			result = getNumberOfPlayers();
		}	
		
		while (result < 2/* || result > 8*/) {			
			System.err.println("GAME RULE VIOLATION: #Players MUST be >=2");//[Assume at least two players!] 
			result = getNumberOfPlayers();			
		}	
		
		return result;		
	}
	
}


/* CRC CARD  
|-------------------------------------------|
|                                           |
|-------------------------------------------|
|                                           |
|                                           |
|                                           |
|                                           |
|                                           |
|                                           |
|-------------------------------------------|
*/
