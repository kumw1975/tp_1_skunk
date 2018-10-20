import edu.princeton.cs.introcs.StdOut;
//Data Layer
public class Player extends Dice {

	//Instance variables & some utility variables for the player class
	private String name;
	private int numberOfChips;
	private int turnPoints;
	private int roundPoints;
	private int gamePoints; 
	private int totalTurnsTaken;
	private int turnsTakenInCurrentRound;	
	private int playerNumber;
	private int doublSkunkCount;
	
	private int previousNumberOfChips;
	private int previousTurnPoints;
	private int previousRoundPoints;
	private int previousGamePoints; 
	private int previousDoublSkunkCount;
 

	//The player class instantiates the player and the dice. 
	public Player(String name) {
		
		super();
		this.name 						= name;
		//decided to use only white chips
		//players to start with 50 chips by placing a par value of "one" on white chips, 
		//(//=> no chip modeling 5 for 1 on red chips and 10 for 1 on the blue chips.)
		this.numberOfChips 				= 50;
		this.turnPoints 				= 0;
		this.roundPoints 				= 0;
		this.gamePoints 				= 0; 
		this.totalTurnsTaken			= 0; 
		this.turnsTakenInCurrentRound 	= 0;	
		this.doublSkunkCount 			= 0;
		
		this.previousNumberOfChips 		= this.numberOfChips;
		this.previousTurnPoints 		= 0;
		this.previousRoundPoints 		= 0;
		this.previousGamePoints 		= 0; 
		this.previousDoublSkunkCount 	= 0;
	} 	
	
	//intantiate a player with 2 testable (Predictable) Die :: we can create variations of 
	//this => one testable and one random but this should suffice. 
	public Player(String name, int[] die1RollValues, int[]die2RollValues) {
		
		super(new Die(die1RollValues), new Die(die2RollValues));
		this.name 						= name;
		this.numberOfChips 				= 50;
		this.turnPoints 				= 0;
		this.roundPoints 				= 0;
		this.gamePoints 				= 0; 
		this.totalTurnsTaken			= 0; 
		this.turnsTakenInCurrentRound 	= 0;	
		this.doublSkunkCount 			= 0;
		
		this.previousNumberOfChips 		= this.numberOfChips;
		this.previousTurnPoints 		= 0;
		this.previousRoundPoints 		= 0;
		this.previousGamePoints 		= 0; 
		this.previousDoublSkunkCount 	= 0;
	} 	
	
	@Override
	public void roll(){	
		//since player rolls the dice ::-> we have created a Dice instance, and can invoke roll() against it, 
		super.roll();
		//since Game class controls the player and implements the rules based on roll values,
		//:: -> we will let the Game Class print out the resulting value & related penalties.
	}

	//one line getters and setters
	//player Metrics will be initialized by the player class but controlled by the game class
	public int getPlayerNumber()					{return this.playerNumber;				}
	public void setPlayerNumber(int playerNumber) 	{this.playerNumber = playerNumber;		}	
	public void setNumberOfChips(int numberOfChips) {this.numberOfChips = numberOfChips;	} 		
	public int getNumberOfChips() 					{return this.numberOfChips;				}	
	public String getName() 						{return this.name;						}			
	public int getTurnPoints() 						{return turnPoints;						}	
	public void setTurnPoints(int turnPoints) 		{this.turnPoints = turnPoints;			}	
	public int getRoundPoints() 					{return this.roundPoints;				}	
	public void setRoundPoints(int roundPoints) 	{this.roundPoints = roundPoints;		}	
	public int getGamePoints() 						{return this.gamePoints;				}	
	public int getDoubleSkunkCount() 				{return this.doublSkunkCount;			}
	public void setDoubleSkunkCount(int i) 			{this.doublSkunkCount = i;				}
	public int getTotalTurnsTaken() 				{return this.totalTurnsTaken;			}	
	public void incrementTotalTurnsTaken() 			{this.totalTurnsTaken++;				}	
	public int getTurnsTakenInCurrentRound() 		{return this.turnsTakenInCurrentRound;	}

	
	public void setGamePoints(int gamePoints) {
		//The assumption here is that a player can never have negative game points
		if(gamePoints < 0 ) {
			this.gamePoints =0;
		}
		else{
			this.gamePoints = gamePoints;	
		}		
	}
	
	public void setTurnsTakenInCurrentRound(int turnsTakenInCurrentRound) {
		//since the player can have multiple turns in one round
		this.turnsTakenInCurrentRound = turnsTakenInCurrentRound;
	}

	public void takeNumberOfChips(int i) {		
		if (i < this.getNumberOfChips()) {
			this.setNumberOfChips(this.getNumberOfChips()-i);	
		}
		else{
			//Case: Player has no chips left but rolls a skunk and gets a penalty. 
			//What do we do here? Do we end the game for this player 
			//or set their chips to zero or just assume that they can have insufficient chips and can be in debt
			StdOut.println(this.getName() + " Doesn't have sufficient chips");
			this.setNumberOfChips(this.getNumberOfChips()-i);	
		}			
	}
	
	//Utility method to show the Metrics from Before the roll & after the roll without having to print twice.
	public void setPreviousMetrics(){
		
		this.previousNumberOfChips 		=this.getNumberOfChips();
		this.previousTurnPoints 		=this.getTurnPoints();
		this.previousRoundPoints 		=this.getRoundPoints();
		this.previousGamePoints 		=this.getGamePoints(); 
		this.previousDoublSkunkCount 	=this.getDoubleSkunkCount();		
	}
	
	@Override
	public String toString() {
	
		//Display relevant player details. 
		String result = "";
		result = result + "Name \t\t\t\t\t: " 							 +this.getName()						+ "\n";
		result = result + "Player Number \t\t\t\t: " 					 +"Player "+this.getPlayerNumber()		+ "\n";
		result = result + "Number of Turns taken in this Round\t: " 	 +this.getTurnsTakenInCurrentRound()	+ "\n";
		result = result + "Total turns taken in all rounds\t\t: " 		 +this.getTotalTurnsTaken()				+ "\n";;
		result = result + "Number of Chips \t\t\t: " 					 +this.previousNumberOfChips			+ "\t=> "+this.getNumberOfChips()	+"\n";
		result = result + "Total Game Points\t\t\t: " 					 +this.previousGamePoints 				+ "\t=> "+this.getGamePoints()		+"\n";
		result = result + this.getName() +"'s Current Round Points\t\t: "+this.previousRoundPoints 				+ "\t=> "+this.getRoundPoints()		+"\n";
		result = result + this.getName() +"'s Double Skunk Count  \t\t: "+this.previousDoublSkunkCount 			+ "\t=> "+this.getDoubleSkunkCount()+"\n";
		result = result + "Current Points accumulated in this Turn\t: "  +this.previousTurnPoints 				+ "\t=> "+this.getTurnPoints()		+"\n";

		setPreviousMetrics();
		return result;
	}

	
	
	
	
}
