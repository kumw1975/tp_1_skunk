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

		Player player = null;
		String roundWinner = "";
		Observer observer = null;
		String nextStep = "";
		String answer = "";
		String response = "";
		String msg = "";

		StdOut.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		StdOut.println("\t      Welcome to the Skunk Game ".toUpperCase() + "\t       ");
		StdOut.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

//		 int nPlayers = getNumberOfPlayers();
//		 StdOut.println("Num Players: "+ nPlayers);
//		
//		 String[] names = setPlayerNames(nPlayers);
//		 StdOut.println("Player Names are :\n");
//		 showPlayerNames(names);

		String[] names = new String[] { "WWW", "XXX", "VVV" };

		observer = new Observer(names);

		while (!(observer.isLastRound())) {

			player = observer.getActivePlayer();
			//msg = "\nACTIVE PLAYER => " + player.getName() + " <::::> CURRENT STATS: \n\n" + player;
			// StdOut.println(msg);
			nextStep = observer.whatNext();
			
			if (nextStep.equalsIgnoreCase("DISTRIBUTION")) {
				roundWinner = observer.getRoundWinner();
				if (!roundWinner.equalsIgnoreCase("NONE")) {
					msg = roundWinner + "******* Won This Round\n";
					msg = msg + (roundWinner + ", How Would you like to collect your Chips?\n");
					msg = msg + ("OPTION 1: Get 5 chips from Each Player\n");
					msg = msg + ("OPTION 2: Get 10 chips from every player that has a game score of 0\n");
					msg = msg + ("-> Enter 1 or 2\n");

					msg = roundWinner + msg;
					StdOut.println(msg);

					answer = StdIn.readLine();
					while (!(answer.trim().length() > 0)) {
						answer = StdIn.readLine();
					}
					answer = answer.toUpperCase();
				}
			}

			if (nextStep.contains("ROLL")) {
				msg = (nextStep.equalsIgnoreCase("ROLL?")) ? ",  would you like to roll?"
						: ",  would you like to roll Again?";

				msg = player.getName() + msg;
				StdOut.println(msg);

				answer = StdIn.readLine();
				while (!(answer.trim().length() > 0)) {
					answer = StdIn.readLine();
				}
				nextStep = "";
			}
 
			response = observer.requestAction(answer);
			StdOut.println("--------------------------------------------------------");
			StdOut.println(response);
			StdOut.println("--------------------------------------------------------");
			answer = "";
		}

		StdOut.println("\n\n\n");
		StdOut.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		StdOut.println("\t     THIS IS THE LAST ROUND".toUpperCase() + "\t\t       ");
		StdOut.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

 		
		player = observer.getActivePlayer();
		// msg = "\nACTIVE PLAYER IS => "+player.getName()+" <::::> CURRENT
		// STATS: \n"+player;
		// StdOut.println(msg);

		observer.requestFinalSequence();
		nextStep = observer.whatNext();
 
		while (nextStep.contains("ROLL")) {
			player = observer.getActivePlayer();
			msg = (nextStep.equalsIgnoreCase("ROLL?")) ? ",  would you like to roll?"
					: ",  would you like to roll Again?";
			msg = player.getName() + msg;
			StdOut.println(msg);

			answer = StdIn.readLine();
			while (!(answer.trim().length() > 0)) {
				answer = StdIn.readLine();
			}

			response = observer.requestAction(answer);
			nextStep = observer.whatNext();

			if (nextStep.contains("GAME OVER")/* || answer.equalsIgnoreCase("N") */) {
				response = observer.requestAction("GAME OVER");
				StdOut.println("\n\n" + response);
				System.exit(0);
			} else {
				StdOut.println("--------------------------------------------------------");
				StdOut.println(response);
				StdOut.println("--------------------------------------------------------");

			}
			answer = "";
		}

	}

	/**
	 * @param playerNames
	 */
	private static void showPlayerNames(String[] playerNames) {
		for (int i = 0; i < playerNames.length; i++) {
			StdOut.println("Player " + (i + 1) + "'s name is " + playerNames[i]);
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

			StdOut.println("Enter Player " + (i + 1) + "'s name ");
			name = StdIn.readLine();
			while (!(name.trim().length() > 0)) {
				name = StdIn.readLine();
			}
			name = name.trim().toUpperCase();
			playerNames[i] = name;
		}
		return playerNames;
	}

	/**
	 * Throws NoSuchElementException - if standard input is empty Throws
	 * InputMismatchException - if the next token cannot be parsed as an int
	 */
	private static int getNumberOfPlayers() {
		StdOut.println("Enter the number of players");

		int result = 0;
		try {
			result = StdIn.readInt();

		} catch (InputMismatchException IME) {
			System.err.println("WARNING: Wrong Input format!!! Enter positive NUMBERS ONLY");
			result = getNumberOfPlayers();
		} catch (NoSuchElementException NSE) {
			System.err.println("WARNING: Invalid Input !!!");
			result = getNumberOfPlayers();
		}

		while (result < 2/* || result > 8 */) {
			System.err.println("GAME RULE VIOLATION: #Players MUST be >=2");// [Assume// at// least// two// players!]
			result = getNumberOfPlayers();
		}
		return result;
	}

}
