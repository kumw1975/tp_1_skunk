import java.io.*;
import edu.princeton.cs.introcs.StdOut;

public class Game {

	private int 	 goal;
	private int 	 roundNumber;
	private int 	 numberOfChipsInKitty;
	private int 	 activePlayerLoc;
	private int 	 doubleSkunkCount;
	private Player 	 activePlayer;	
	private Player[] players;
	private int 	 numberOfPlayers;
	private boolean  isLastRound;
	private Player[] lastRoundSequence;	
	
	public Game(){
		
		this.goal 					= 100; 
		this.roundNumber  			= 0; 
		this.numberOfChipsInKitty 	= 0; 
		this.activePlayerLoc 		= 0;
		this.doubleSkunkCount 		= 0;
		this.isLastRound 			= false;

		setup(); //uncomment in production
		/*
		//Start Testing Snippet for (Dev) 
		this.players = new Player[]{new Player("MO"), new Player("HO"), new Player("DO")};
		this.numberOfPlayers = players.length;
		
		StdOut.println("------------------------------------------------------");
		StdOut.println("PLAYER INFO\n------------------------------------------------------");
		for (int i = 0; i < players.length; i++) {	
			players[i].setPlayerNumber(i+1);
			String name = (  (i+1) < 10 ) ? " "+(i+1)+" : "+players[i].getName() : (i+1)+" : "+players[i].getName();
			StdOut.println("Player "+ name);
		}
		//Simulate last round
		players[1].setGamePoints(100);
		players[1].setPreviousMetrics();
		//End Testing (Dev) Snippet	
		*/
	}
	
	
/*	private Player[] getPlayers() {
		return players;
	}


	private void setPlayers(Player[] players) {
		this.players = players;
	}
*/

	private void startRound(){
		//initialize Round metrics
 		roundNumber++;
		for (int i = 0; i < players.length; i++) {	
			players[i].setRoundPoints(0);
			players[i].setTurnsTakenInCurrentRound(0);
		}
		activePlayerLoc = 0;
	}

	
	
	
	private void endRound() {
		/*
		The winner of each game(round) collects all chips in "kitty" and 
		in addition five chips from each losing player or 10 chips 
		from any player without a score. 
		(so is this a player without a round score or a player without a turn score or a player without a game score?)
		*/
		
		//get the round winner 
		// is the round winner the one with the overall highest score (cumulative//game points) ?
		// is the round winner the one with highest score only in this round (round points)
		
		int highScore   	= 0;
		int roundWinnerLoc 	= 0;
		
		for (int i = 0; i < players.length; i++) {
			if (players[i].getRoundPoints() > highScore ) {
				highScore   = players[i].getGamePoints();
				roundWinnerLoc = i;
			}
		}
		
		Player winner = players[roundWinnerLoc];
		StdOut.println("The winner was Player "+ winner.getPlayerNumber() +" : "+ winner.getName());
		
		//The winner of each game(round) collects all chips in "kitty"  
		winner.setNumberOfChips(winner.getNumberOfChips() + numberOfChipsInKitty); 
		numberOfChipsInKitty = 0;
		
		BufferedReader reader 	= null;
		String input 			= "";		
	
		StdOut.println(winner.getName()+ ", How Would you like to collect your Chips?");
		StdOut.println( "OPTION 1: Get 5 chips from Each Player");
		StdOut.println( "OPTION 2: Get 10 chips from every player that has a round score of 0");
		StdOut.println( "-> Enter 1 or 2");
		 
		try {					
			 reader 	= new BufferedReader(new InputStreamReader(System.in));
			 input 		= reader.readLine();	
			 
			 if (input.equalsIgnoreCase("1")) {
				
				 //and in addition five chips from each losing player				 				 
				 for (int i = 0; i < players.length; i++) {					
					 if (!(players[i].equals(winner))) {
						players[i].takeNumberOfChips(5);
						winner.setNumberOfChips(winner.getNumberOfChips()+5);
					}
				 }					
			 }
			 else if (input.equalsIgnoreCase("2")) {
				// or 10 chips from any player without a score. game score? round score? turn score?
				 for (int i = 0; i < players.length; i++) {
					 if (!(players[i].equals(winner)) && players[i].getRoundPoints()==0) {
						players[i].takeNumberOfChips(10);
						winner.setNumberOfChips(winner.getNumberOfChips()+10);
					}						
				 }					
			 }
		 
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	
	
	private void startTurn(){
		
		if (!isLastRound) {
			activePlayer = players[activePlayerLoc];
		}
		else{
			activePlayer = players[activePlayerLoc];
			//StdOut.println(activePlayerLoc+ " ACTIVE PLAYER SET TO "+ activePlayer.getName());
		}		
		
		//initialize Turn Metrics
		activePlayer.setTurnPoints(0);
		activePlayer.setTurnsTakenInCurrentRound(0); 
		
		String penalty 			= "";
		BufferedReader reader 	= null;
		String input 			= "Y";		
		String status 			= "";		
		
		while(penalty.trim().length() == 0 && input.trim().equalsIgnoreCase("Y")){
			try {
				activePlayer.roll();

				int previousNumberOfChipsInKitty = numberOfChipsInKitty;
				int previousNumberOfDoubleSkunks = doubleSkunkCount;
				
				status = "ROUND " + roundNumber+ " TURN " +(activePlayer.getTurnsTakenInCurrentRound()+1);
				status = status   + " FOR "+ activePlayer.getName() + " ****** " + activePlayer.getName() +" ROLLED \t::"+ activePlayer.getRollValue();
				status = status   + " => " + activePlayer.getDie1RollValue() +" + "+  activePlayer.getDie2RollValue();

				StdOut.println("******************************************************");
				StdOut.println(status);				
				StdOut.println("******************************************************");

				penalty = analyzeDiceValues();
				updateTurnMetrics(penalty);
				
				StdOut.println("------------------------------------------------------");
				StdOut.println("GAME INFO \t\t\t\t: OLD \t=> NEW" );
				StdOut.println("------------------------------------------------------");
				StdOut.println("NUMBER OF CHIPS IN THE GAME'S KITTY \t: " + previousNumberOfChipsInKitty +" \t=> "+  numberOfChipsInKitty);
				StdOut.println("DOUBLE SKUNKS IN THE GAME  SO FAR  \t: "  + previousNumberOfDoubleSkunks +" \t=> "+  doubleSkunkCount);
				
				/*				
 				Simulate Loading effect
				Thread.sleep(1000);		
				System.out.print("LOADING PLAYER METRICS ");
				Thread.sleep(1000);		
				System.out.print(".");
				Thread.sleep(1000);	
				System.out.print(".");
				Thread.sleep(1000);	
				System.out.print(".");
				Thread.sleep(1000);	
				 */		
				
				StdOut.println(activePlayer);
				
				if(penalty.trim().length() == 0 ) {
					StdOut.println(activePlayer.getName()+ ", Would you like to roll again? -> Enter Y/N");
					reader 	= new BufferedReader(new InputStreamReader(System.in));
					input 	= reader.readLine();					
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}		
		activePlayerLoc++;	
	}
	
	public void play(){		
		while (!isLastRound) {
			startRound();
			for (int i = 0; i < players.length; i++) {
				startTurn();
				if(activePlayer.getGamePoints() >= goal){
					/*	
					The first player to accumulate a total of 100 or more points
					can continue to score as many points over 100 as he believes
					is needed to win. When he decides to stop, his total score is the “goal.” 
					Each succeeding player receives one more chance to better the goal and end the game(round).
					
					*/
					goal =(activePlayer.getGamePoints());
					StdOut.println("******************************************************");
					StdOut.println("******************************************************");
					StdOut.println("******************************************************");
					StdOut.println("******************************************************");
					StdOut.println("******************************************************");
					StdOut.println("New High Score: " + goal +" set by "+ activePlayer.getName());
					StdOut.println("******************************************************");
					StdOut.println("******************************************************");
					StdOut.println("******************************************************");
					StdOut.println("******************************************************");
					StdOut.println("******************************************************");
					StdOut.println("------------------------------------------------------");			
					this.setLastRound();
					i = players.length; 
					StdOut.println("STARTING THE LAST ROUND");
				}				
			}
			if (!isLastRound) {
				endRound();	
			}			
		}
			
 		for (int i = 1; i < players.length; i++) {
			startTurn();			
			//Each succeeding player receives one more chance to better the goal and end the game(round) hence i=1.
			if(activePlayer.getGamePoints() >= goal){
				goal =(activePlayer.getGamePoints());
				StdOut.println("******************************************************");
				StdOut.println("******************************************************");
				StdOut.println("******************************************************");
				StdOut.println("******************************************************");
				StdOut.println("******************************************************");
				StdOut.println("New High Score: " + goal +" set by "+ activePlayer.getName());
				StdOut.println("******************************************************");
				StdOut.println("******************************************************");
				StdOut.println("******************************************************");
				StdOut.println("******************************************************");
				StdOut.println("******************************************************");
				StdOut.println("------------------------------------------------------");			
			}			
		}			

		//get the winner 
		int highScore   	= 0;
		int roundWinnerLoc 	= 0;
		
		for (int i = 0; i < players.length; i++) {
			if (players[i].getGamePoints()> highScore ) {
				highScore   = players[i].getGamePoints();
				roundWinnerLoc = i;
			}
		}
		
		Player winner			= players[roundWinnerLoc];		
		BufferedReader reader 	= null;
		String input 			= "";		
		
		StdOut.println(winner.getName()+ ", How Would you like to collect your Chips?");
		StdOut.println( "OPTION 1: Get 5 chips from Each Player");
		StdOut.println( "OPTION 2: Get 10 chips from every player that has a game score of 0");
		StdOut.println( "-> Enter 1 or 2");
		 
		try {					
			 reader 	= new BufferedReader(new InputStreamReader(System.in));
			 input 		= reader.readLine();	
			 
			 if (input.equalsIgnoreCase("1")) {
				 
				 //and in addition ﬁve chips from each losing player				 				 
				 for (int i = 0; i < players.length; i++) {					
					 if (!(players[i].equals(winner))) {
						players[i].takeNumberOfChips(5);
						winner.setNumberOfChips(winner.getNumberOfChips()+5);
					}
				 }					
			 }			 
			 else if (input.equalsIgnoreCase("2")) {
				// or 10 chips from any player without a score. game score? round score? turn score?
				 for (int i = 0; i < players.length; i++) {
					 if (!(players[i].equals(winner)) && players[i].getGamePoints()==0) {
						players[i].takeNumberOfChips(10);
						winner.setNumberOfChips(winner.getNumberOfChips()+10);
					}						
				 }					
			 }		 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//SuD does final round score update, reporting final round 
		//scores for each player and announcing winner(s) of the round.
		StdOut.println("------------------------------------------------------");	
		StdOut.println("*GAME OVER! GAME OVER! GAME OVER! GAME OVER! GAME OVER*");	
		StdOut.println("######################################################");
		StdOut.println("The winner was Player "+ winner.getPlayerNumber() +" : "+winner.getName());
		StdOut.println("######################################################");
		StdOut.println(winner);	
		StdOut.println("######################################################");

		StdOut.println("------------------------------------------------------");	
		StdOut.println("Metrics from the other players");
		 for (int i = 0; i < players.length; i++) {
			 if (!(players[i].equals(winner))) {
				StdOut.println("------------------------------------------------------");	
				StdOut.println(players[i]);	
				StdOut.println("------------------------------------------------------");				 
			}						
		 }
		 
		StdOut.println("* THANKS FOR PLAYING SKUNK *");			 
		StdOut.println("------------------------------------------------------");	
		System.exit(0);
	}

	
	private String analyzeDiceValues() {

		String result = "";
		Player activePlayer = this.activePlayer;
		
		//roll (1 and 3-6) -> rolled 1 skunk and a 3 or a 4 or a 5 or a 6
		if ((activePlayer.getDie1RollValue()==1 && activePlayer.getDie2RollValue() > 2 ) 
		|| ( activePlayer.getDie1RollValue() >2 && activePlayer.getDie2RollValue()==1)){
	
			result = "OneSkunk";
		}				
		
		//roll (1 and 2) -> rolled 1 skunk and 1 deuce
		if ((activePlayer.getDie1RollValue()==1 && activePlayer.getDie2RollValue()==2 ) 
		|| ( activePlayer.getDie1RollValue()==2 && activePlayer.getDie2RollValue()==1)){
			
			result = "SkunkAndDeuce";
		}
		
		//roll (1 and 1) -> rolled 2 skunks
		if ((activePlayer.getRollValue() == 2)){
						
			result = "TwoSkunks";
		}
		
		return result;
	}	

	private void addChipsToKittyFromActivePlayer(int i) {
		activePlayer.takeNumberOfChips(i);
		addChipsToKitty(i);		
	}

	private void addChipsToKitty(int numberOfChipsToAddToKitty) {
		numberOfChipsInKitty = this.numberOfChipsInKitty+ numberOfChipsToAddToKitty;
	}	
	
//	private void resetActivePlayersRoundPoints() {
//		activePlayer.setRoundPoints(0);		
//	}	
	
	private void resetActivePlayersGamePoints() {
		activePlayer.setGamePoints(0);;		
	}
	
	private void resetActivePlayersTurnPoints() {
		activePlayer.setTurnPoints(0);		
	}
	
	private void updateTurnMetrics(String penalty){
		
		Player activePlayer   = this.activePlayer;
		String penaltyDetails = "#####################  PENALTY  ######################\n";	

		activePlayer.setTurnsTakenInCurrentRound(activePlayer.getTurnsTakenInCurrentRound()+1);		
		activePlayer.incrementTotalTurnsTaken();
		
		if(penalty.equalsIgnoreCase("OneSkunk")){
		/*
			A skunk in any Turn voids the score for that Turn only
			and draws a penalty of 1 chip placed in the "kitty," 
			and loss of dice.
		*/			
			activePlayer.setGamePoints(activePlayer.getGamePoints()-activePlayer.getTurnPoints());		
			this.resetActivePlayersTurnPoints();	
		 	this.addChipsToKittyFromActivePlayer(1);	
			
		 	penaltyDetails = penaltyDetails + (activePlayer.getName()+ " Rolled One Skunk ::" + activePlayer.showRollDetails()+"\n");
		 	penaltyDetails = penaltyDetails + (activePlayer.getName()+ " Lost: A turn, all turn points &  1 chip (added to kitty)");
		 	penaltyDetails = penaltyDetails + ("\n######################################################");		
		 	
		 	penalty = "One Skunk";
		}
		
		else if(penalty.equalsIgnoreCase("TwoSkunks")){
		/*
			TWO skunks void the ENTIRE accumulated score 
			and draws a penalty of 4 chips placed in the "kitty," 
			and loss of dice. 
			Player must again start to score from scratch.		
		 */				
			this.resetActivePlayersGamePoints();;			
		 	this.addChipsToKittyFromActivePlayer(4);
		 	activePlayer.setDoubleSkunkCount(activePlayer.getDoubleSkunkCount()+1);// double skunk counts for this player so far
		 	doubleSkunkCount++; // double skunk counts in the game so far
		 	
		 	penaltyDetails = penaltyDetails + (activePlayer.getName()+ " Rolled Two Skunks ::" + activePlayer.showRollDetails()+"\n");
		 	penaltyDetails = penaltyDetails + (activePlayer.getName()+ " Lost: A turn, all game points &  4 chips (added to kitty)");
		 	penaltyDetails = penaltyDetails + ("\n######################################################");		

		 	penalty = "Two Skunks";

		}
		
		else if(penalty.equalsIgnoreCase("SkunkAndDeuce")){
		/*
			A skunk and a deuce voids the score for that Turn only 
			and draws a penalty of 2 chips placed in the "kitty," 
			and loss of dice.			
		 */				
			activePlayer.setGamePoints(activePlayer.getGamePoints()-activePlayer.getTurnPoints());		
			this.resetActivePlayersTurnPoints();	
		 	this.addChipsToKittyFromActivePlayer(2);	
 		 	
		 	penaltyDetails = penaltyDetails + (activePlayer.getName()+ " Rolled One Skunk and a Deuce ::" + activePlayer.showRollDetails()+"\n");
		 	penaltyDetails = penaltyDetails + (activePlayer.getName()+ " Lost: A turn, all round points &  2 chips (added to kitty)");
		 	penaltyDetails = penaltyDetails + ("\n######################################################");		

		 	penalty = "One Skunk and a Deuce";
		}		
		else{
			
			activePlayer.setTurnPoints(activePlayer.getRollValue());
			activePlayer.setRoundPoints(activePlayer.getRoundPoints()+activePlayer.getRollValue());	
			activePlayer.setGamePoints(activePlayer.getGamePoints()+activePlayer.getRollValue());
		}
		
		if(penalty.trim().length()>0) {
			StdOut.println(penaltyDetails);	
		}

	}



	private void setLastRound() {		

	/*	
	The first player to accumulate a total of 100 or more points
	can continue to score as many points over 100 as he believes
	is needed to win. When he decides to stop, his total score is the “goal.” 
	Each succeeding player receives one more chance to better the goal and end the game(round).
	
	*/		
		int cap			 			= players.length;			
		Player[] lastRoundSequence 	= new Player[cap];
		int playerLoc 	 			= getLoc(activePlayer);
		
		//we rearrange the array to put the winner at position 0		
		for (int i = 0; i < cap; i++) {
			lastRoundSequence[i] 	= players[playerLoc%cap];
			playerLoc++;
		}
		this.lastRoundSequence 		= lastRoundSequence;
		this.players=lastRoundSequence;
		this.isLastRound 			= true;	
		
 		for (int i = 0; i < players.length; i++) {
 			/*StdOut.println*/String result=("Last Round sequence  "+ i + " "+ this.lastRoundSequence[i].getName());
 			result.length();
 		}
		
		//Each succeeding player receives one more chance to better the goal and end the game(round) hence i=1.
		//so we skip the winning player i=0;
		activePlayerLoc = 1; 
	}

	
	private int getLoc(Player player){	
		
		int cap = players.length;
		int loc = 0;		
		for (int i = 0; i < cap; i++) {
			if (players[i].equals(player)) {
				loc = i;
			}
		}
		return loc;
	}
	
	
 	// gets user input and sets number of players & initializes the player array
	private void setup() {
	/*
	Any number can play. [Assume at least two players!] 
	The suggested number of chips to start is 50. 
	There are sufficient chips in the box to allow 8 //(assume up to any number of players)
	players to start with 50 chips by placing a 
	par value of "one" on white chips, 5 for 1 on red chips 
	and 10 for 1 on the blue chips.		
	*/		
		StdOut.println("Enter the number of players");
		this.numberOfPlayers = getNumberOfPlayers();
		StdOut.println("There are "+ this.numberOfPlayers + " players");
		
		this.players = new Player[this.numberOfPlayers];
		
		for (int i = 0; i < this.players.length; i++) {
			StdOut.println("Enter Player "+ (i+1) +"'s username ");
			String playerName = getPlayerName();
			this.players[i] = new Player(playerName.toUpperCase());
			this.players[i].setPlayerNumber(i+1);
			StdOut.println("Player "+  (i+1)  +"'s username is "+ playerName);
		}
		
		StdOut.println("\n------------------------------------------------------");
		StdOut.println("Player Info\n------------------------------------------------------");
		for (int i = 0; i < players.length; i++) {			
			String name = (  (i+1) < 10 ) ? " "+(i+1)+" : "+players[i].getName() : (i+1)+" : "+players[i].getName();
			StdOut.println("Player "+ name);
		}
	}
	
	
	private static int getNumberOfPlayers() {//from user as userInput	
		
		int result				= 0;		
		String input 			= ""; 
		BufferedReader reader 	= null;		
 		try {			
			reader = new BufferedReader(new InputStreamReader(System.in));
			input  = reader.readLine();
			result = Integer.parseInt(input.trim());
			
		} catch (NumberFormatException d) {			
			System.err.println("WARNING: Wrong Input format!!! Enter NUMBERS ONLY");
			StdOut.println("Enter the number of players greater than 0");
			result = getNumberOfPlayers();
		}
		catch (IOException ioe) {			
			System.err.println("WARNING: Invalid Input !!!");
			StdOut.println("Enter the number of players");
			result = getNumberOfPlayers();
		}	
		
		while (result < 2/* || result > 8*/) {			
			System.err.println("GAME RULE VIOLATION: #Players >=2");//[Assume at least two players!] 
			StdOut.println("Enter the number of players");
			result = getNumberOfPlayers();			
		}		
		return result;
	}	
	
	
	private static String getPlayerName(){
		
		String input 			= ""; 
		BufferedReader reader 	= null;
		
		try {			
			reader = new BufferedReader(new InputStreamReader(System.in));
			input  = reader.readLine();
			
		}catch (Exception d) {}
 		while (input.trim().length() < 1) {			
			System.err.println("GAME RULE VIOLATION: Username can not be empty");
			StdOut.println("Enter player username ");
			input = getPlayerName();			
		}		
		return input;
	}
	
	
	/*

The object of the game(round) is to accumulate a score of 100 points or more. 
A score is made by rolling the dice and combining the points on the two dice. 
For example: A 4 and 5 would be 9 points - 
if the player decides to take another roll of the dice and turns up a 
3 and 5 (8 points), he would then have an accumulated total of 17 
points for the two rolls. The player has the privilege of continuing 
to shake to increase his score or of passing the dice to wait for the 
next Turn, thus preventing the possibility of rolling a Skunk and losing his score.	 

	 
PENALTIES:

A skunk in any Turn voids the score for that Turn only
and draws a penalty of 1 chip placed in the "kitty," 
and loss of dice.

A skunk and a deuce voids the score for that Turn only 
and draws a penalty of 2 chips placed in the "kitty," 
and loss of dice.

TWO skunks void the ENTIRE accumulated score 
and draws a penalty of 4 chips placed in the "kitty," 
and loss of dice. 
Player must again start to score from scratch.


Any number can play. [Assume at least two players!] 
The suggested number of chips to start is 50. 
There are sufficient chips in the box to allow 8 
players to start with 50 chips by placing a 
par value of "one" on white chips, 5 for 1 on red chips 
and 10 for 1 on the blue chips.


The first player to accumulate a total of 100 or more points
can continue to score as many points over 100 as he believes
is needed to win. When he decides to stop, his total score is the “goal.” 
Each succeeding player receives one more chance to better the goal and end the game(round).


The winner of each game(round) collects all chips in "kitty" and 
in addition ﬁve chips from each losing player or 10 chips 
from any player without a score.


	 
 start Game:: USERINPUT ::=> #players, player names
 	initialize Players
 	
 	start Round()
 		initialize Round Metrics
 			Start Turn()
 				initialize Turn Metrics
 				roll()
 				analyze dice values
 				update Turn Metrics
 				askToRollAgain()
 					if y:go to roll() else: go to endTurn()
 			end Turn() :: 
 		Update Round Metrics
 	end Round()
 	
 	get Winner()
 	displayChipDistributionOptions()
 	distributeChips()
	 	 
 */
	
		

}
