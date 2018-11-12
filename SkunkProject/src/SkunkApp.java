import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

/*Presentation Logic
Does not create objects of other classes except the observer (controller) class
sends requests to observer
receives responses from observer
sends stdOut to the user
gets stdIn from the user
Doesn't do any business logic i.e no calculations or metric handling. 

This class only creates the observer object. 

*/

public class SkunkApp {
	public static void main(String[] args) {

		Player player  		= null;	
		String roundWinner	= "";	
		Observer observer 	= null;
		String nextStep 	= "";
		String answer 		= "";	
		String response 	= "";	
		String msg 		 	= "";			
	
		StdOut.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");		
		StdOut.println("@\t      Welcome to the Skunk Game ".toUpperCase()+"\t       @");		
		StdOut.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");			

 
	 
		int nPlayers = getNumberOfPlayers();
		StdOut.println("Num Players: "+ nPlayers);
		
		String[] names = setPlayerNames(nPlayers);
		StdOut.println("Player Names are :\n");
		showPlayerNames(names);
	 	
		//String[] names = new String[]{"WWW", "XXX"};
 
		observer = new Observer(names);	
		
		while (!(observer.isLastRound())) {
			
			player = observer.getActivePlayer();
			msg    = "\nACTIVE PLAYER => "+player.getName()+" <::::> CURRENT STATS: \n"+player;			
			//StdOut.println(msg);			
			nextStep = observer.whatNext();
			StdOut.println("NEXT STEP => " +nextStep);	
			
			if(nextStep.equalsIgnoreCase("DISTRIBUTION")) {
				System.out.println("in distribution mode");
				roundWinner = observer.getRoundWinner();

				if(!roundWinner.equalsIgnoreCase("NONE")){
					msg = roundWinner + "******* Won This Round\n";
					msg = msg + (roundWinner + ", How Would you like to collect your Chips?\n");
					msg = msg + ( "OPTION 1: Get 5 chips from Each Player\n");
					msg = msg + ( "OPTION 2: Get 10 chips from every player that has a game score of 0\n");
					msg = msg + ( "-> Enter 1 or 2\n");
					
					msg = roundWinner + msg;				
					StdOut.println(msg);
					
					answer = StdIn.readLine();
					while(!(answer.trim().length()>0)){
						answer = StdIn.readLine();
					}
					
					answer 	= answer.toUpperCase();	
					msg 	= "USER ANSWERED "+answer;	
					StdOut.println(msg);	

				}else{
					StdOut.println("THERE WAS NO WINNER FOR THIS ROUND> ");
					answer = "NONE";					
				}
			}			
			if(nextStep.contains("ROLL")) {
				
				msg = (nextStep.equalsIgnoreCase("ROLL?"))
				?",  would you like to roll?"
				:",  would you like to roll Again?";
				
				msg = player.getName() + msg;				
				StdOut.println(msg);		
				
				answer = StdIn.readLine();
				while(!(answer.trim().length()>0)){
					answer = StdIn.readLine();
				}
				
				answer 	= answer.toUpperCase();	
				msg 	= "USER ANSWERED "+answer;			

				StdOut.println(msg);
				nextStep="";				
			}			
			response = observer.requestAction(answer);
			msg 	 = "SUD RESPONSE\n" + response;		
			
			StdOut.println("--------------------------------------------------------");			
			StdOut.println(msg);		
			StdOut.println("--------------------------------------------------------");		

			answer = "";			
			
		}

		StdOut.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");		
		StdOut.println("@\t     THIS IS THE LAST ROUND".toUpperCase()+"\t\t\t@");		
		StdOut.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");			
		observer.requestFinalSequence();
		
		for (int i = 1; i < 2; i++) {
			player = observer.getActivePlayer();
			
			nextStep = observer.whatNext();
			
			while (nextStep.contains("ROLL")){
				msg = (nextStep.equalsIgnoreCase("ROLL?"))
				?",  would you like to roll?"
				:",  would you like to roll Again?";
				
				msg = player.getName() + msg;				
				StdOut.println(msg);		
				
				answer = StdIn.readLine();
				while(!(answer.trim().length()>0)){
					answer = StdIn.readLine();
				}
				
				answer 	= answer.toUpperCase();	
				msg 	= "USER ANSWERED "+answer;			

				StdOut.println(msg);
				nextStep="";				
				
				nextStep = observer.whatNext();	
				response = observer.requestAction(answer);
				msg 	 = "SUD RESPONSE\n" + response;		
				
				StdOut.println("--------------------------------------------------------");			
				StdOut.println(msg);		
				StdOut.println("--------------------------------------------------------");		

				answer = "";					
				
			}			
 			
 
			System.out.println("ACTIVE PLAYER >>>>> " + observer.getActivePlayer());
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
