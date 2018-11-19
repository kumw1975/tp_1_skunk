//Business Logic
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
	private String   penaltyDetails;
	private String 	 penalty;
	private String   status;
	private int 	 prevRound;
	private String   roundWinner ="";
	private Player   gameWinner=null;
 
	
	public Game(String[] playerNames){
		
		this.status 				= "";
		this.penaltyDetails			= "";
		this.penalty				= "";		
		this.goal 					= 100; 
		this.roundNumber  			= 1; 
		this.numberOfChipsInKitty 	= 0; 
		this.activePlayerLoc 		= 0;
		this.doubleSkunkCount 		= 0;
		this.isLastRound 			= false;
		this.players 				= new Player[playerNames.length];
		this.numberOfPlayers		= playerNames.length;
		this.prevRound				= 1;

		
		for (int i = 0; i <this.numberOfPlayers; i++) {
			this.players[i] = new Player(playerNames[i].toUpperCase());
			this.players[i].setPlayerNumber(i+1);
		}
		this.activePlayer 			= this.players[activePlayerLoc];		
//		this.activePlayer.setGamePoints(100);
//		this.players[activePlayerLoc+1].setGamePoints(100);
	}
	
	private void takeChipsFromAP(int i) {
		activePlayer.takeNumberOfChips(i);
		addToKitty(i);		
	}

	private void addToKitty(int chipsToAdd) {
		numberOfChipsInKitty = this.numberOfChipsInKitty
		+ chipsToAdd;
	}	
	
 	private void resetAPGP() {
		activePlayer.setGamePoints(0);;		
	}
	
	private void resetAPTP() {
		activePlayer.setTurnPoints(0);		
	}	

	public Player getActivePlayer(){
		return this.activePlayer;
	}

	public void setActivePlayer() {			
		if(isOneSkunk()
		||isDeuceAndSkunk()
		||isDoubleSkunk()){			
			setNextPlayer();		
		}
		else{
			this.activePlayer = this.players[activePlayerLoc];
		}
	}


	private void setStatus(){		
		int pNumber 	= activePlayer.getPlayerNumber();
		String pName    = activePlayer.getName();
		String rollData = activePlayer.showRollDetails();
		status 			= "";
		
		if(isOneSkunk()){
	 		penaltyStatus(pNumber, pName, rollData, 1);	
		}			
		else if(isDeuceAndSkunk()){			
	 		penaltyStatus(pNumber, pName, rollData, 2);	
		}		
		else if(isDoubleSkunk()){			
	 		penaltyStatus(pNumber, pName, rollData, 4);	
		}
		else{
			int prvKitty   = numberOfChipsInKitty;
			int prvDSkunks = doubleSkunkCount;				
			int turns = activePlayer.getRoundTurns();
			status = "ROUND " + roundNumber+ " TURN " +turns;
			status = status   + " FOR "+ pName + " ****** " 
			+ pName +" ROLLED ::"+ rollData;
			status = status +"\n------------------------------------------------------";
			status = status +"\nGAME INFO \t\t\t\t: OLD \t=> NEW" ;
			status = status +"\n------------------------------------------------------";
			status = status + "\n"+activePlayer;
			
			status = status +"\nNUMBER OF CHIPS IN THE GAME'S KITTY \t: " 
			+ prvKitty +" \t=> "+  numberOfChipsInKitty;
			status = status +"\nDOUBLE SKUNKS IN THE GAME  SO FAR  \t: "  
			+ prvDSkunks +" \t=> "+  doubleSkunkCount;			
		}
	}

	private void penaltyStatus(
	int pNumber, String pName, String rollData, int chips) {		
		
		String chipNum = (chips==1)? chips+" chip": chips + " chips";
		int prvKitty   = numberOfChipsInKitty;
		int prvDSkunks = doubleSkunkCount;		
		
		status = status + "\n#####################  PENALTY  ######################";	
		status = status + "\n" + pName + " Rolled One Skunk ::" + rollData;
		status = status + "\n" + pName + " Lost: A turn, all turn points & "+chipNum+" (added to kitty)";
		status = status + "\n######################################################";		
		status = status +"\n------------------------------------------------------";
		status = status +"\nGAME INFO \t\t\t\t: OLD \t=> NEW" ;
		status = status +"\n------------------------------------------------------";
		status = status + "\n"+activePlayer;
		
		status = status +"\nNUMBER OF CHIPS IN THE GAME'S KITTY \t: " 
		+ prvKitty +" \t=> "+  numberOfChipsInKitty;
		status = status +"\nDOUBLE SKUNKS IN THE GAME  SO FAR  \t: "  
		+ prvDSkunks +" \t=> "+  doubleSkunkCount;
		
	}
	
	//roll (1 and 3-6) -> rolled 1 skunk and a 3 or a 4 or a 5 or a 6
	private boolean isOneSkunk(){
		return 
		(  activePlayer.getDie1RollValue() == 1 
		&& activePlayer.getDie2RollValue() >  2) 
		||(activePlayer.getDie1RollValue() >  2 
		&& activePlayer.getDie2RollValue() == 1);
	}
	
	//roll (1 and 2) -> rolled 1 skunk and 1 deuce		
	private boolean isDeuceAndSkunk(){
		return 
		(  activePlayer.getDie1RollValue() == 1 
		&& activePlayer.getDie2RollValue() == 2) 
		||(activePlayer.getDie1RollValue() == 2 
		&& activePlayer.getDie2RollValue() == 1);
	}

	//roll (1 and 1) -> rolled 2 skunks
	private boolean isDoubleSkunk(){
		return 
		(activePlayer.getRollValue()==2);
	}	
	
	private void analyzeDiceValues() {		
		if(isOneSkunk()){	
			this.penalty = "OneSkunk";
		}			
		else if(isDeuceAndSkunk()){			
			this.penalty = "SkunkAndDeuce";
		}		
		else if(isDoubleSkunk()){						
			this.penalty = "TwoSkunks";
		}
		else{
			this.penalty = "";
		}
		
	}		


	private void updateTurns() {
		int turns = activePlayer.getRoundTurns()+1;
		activePlayer.setRoundTurns(turns);		
		activePlayer.incrementTotalTurnsTaken();
	}

	private void enforceNoPenalty() {
		int roundPoints = activePlayer.getRoundPoints();
		int rollValue   = activePlayer.getRollValue();
		int gamePoints  = activePlayer.getGamePoints();
		
		roundPoints 	= roundPoints + rollValue;
		gamePoints 		= gamePoints  + rollValue;
		
		activePlayer.setTurnPoints(rollValue);
		activePlayer.setRoundPoints(roundPoints);	
		activePlayer.setGamePoints(gamePoints);
	}

	/*
		TWO skunks void the ENTIRE accumulated score 
		and draws a penalty of 4 chips placed in the "kitty," 
		and loss of dice. 
		Player must again start to score from scratch.		
	*/	
	private void enforceDoubleSkunk() {
		this.resetAPGP();;			
		this.takeChipsFromAP(4);
		// double skunk counts for this player so far
		int dSkunkCount = activePlayer.getDoubleSkunkCount()+1;
		activePlayer.setDoubleSkunkCount(dSkunkCount);
		// double skunk counts in the game so far
		doubleSkunkCount++;
	}

	/*
		A skunk and a deuce voids the score for that Turn only 
		and draws a penalty of 2 chips placed in the "kitty," 
		and loss of dice.			
	*/		
	private void enforceSkunkDeuce() {
		int gamePoints = activePlayer.getGamePoints();
		int turnPoints = activePlayer.getTurnPoints();
		gamePoints = gamePoints-turnPoints;
		
		activePlayer.setGamePoints(gamePoints);		
		this.resetAPTP();	
		this.takeChipsFromAP(2);
	}

	/*
		A skunk in any Turn voids the score for that Turn only
		and draws a penalty of 1 chip placed in the "kitty," 
		and loss of dice.
	*/	
	private void enforceOneSkunk() {
		int gamePoints = activePlayer.getGamePoints();
		int turnPoints = activePlayer.getTurnPoints();
		gamePoints = gamePoints-turnPoints;
		activePlayer.setGamePoints(gamePoints);		
		this.resetAPTP();	
		this.takeChipsFromAP(1);
	}

	/*
	The winner of each game(round) collects all chips in "kitty" and 
	in addition five chips from each losing player or 10 chips 
	from any player without a score. 
	(so is this a player without a round score or a player without a turn score or a player without a game score?)
	*/	
	private void distribute(int j) {		
		Player winner = getRoundWinner();
		
		winner.setNumberOfChips(
		winner.getNumberOfChips()
		+numberOfChipsInKitty);
		
		if (j==1) {
			takeFiveFromLosers(winner);			
		}		
		else if (j==2) {
			takeTenFromLosers(winner);			
		}		
	}
	
	//or 10 chips from any player without a score.		
	private void takeTenFromLosers(Player winner) {
		// or 10 chips from any player without a score. game score? round score? turn score?
		 for (int i = 0; i < players.length; i++) {
			 if (!(players[i].equals(winner)) && players[i].getGamePoints()==0) {
				players[i].takeNumberOfChips(10);
				winner.setNumberOfChips(winner.getNumberOfChips()+10);
			}						
		 }
	}
	
	//in addition five chips from each losing player		
	private void takeFiveFromLosers(Player winner) {
		//and in addition ﬁve chips from each losing player				 				 
		for (int i = 0; i < players.length; i++) {					
			if (!(players[i].equals(winner))) {
			players[i].takeNumberOfChips(5);
			winner.setNumberOfChips(
			winner.getNumberOfChips()+5);
			}
		}
	}

	//get the winner of this round
	private Player getRoundWinner(){
		Player winner = null;
		for(int i = 0; i < players.length; i++) {
			if (players[i].getName()
			.equals(this.roundWinner)) {
				winner = players[i];
			}
		}
		return winner;
	}


	private void setPassed(){		
		String pName    = activePlayer.getName();
		status 			= "";
//		int prevKitty 	= numberOfChipsInKitty;
//		int prevDSkunks = doubleSkunkCount;
		
		int turns = activePlayer.getRoundTurns()+1;
		activePlayer.setRoundTurns(turns);
		status = "ROUND " + roundNumber+ " TURN " +turns;
		status = status   + " FOR "+ pName + " **** " + pName +" PASSED ::";

		status = status +"\n------------------------------------------------------";
		
//		status = status +"\nGAME INFO \t\t\t\t: OLD \t=> NEW" ;
//		status = status +"\n------------------------------------------------------";
//		status = status +"\n"+activePlayer;
//		
//		status = status +"\nNUMBER OF CHIPS IN THE GAME'S KITTY \t: " 
//		+ prevKitty +" \t=> "+  numberOfChipsInKitty;
//		status = status +"\nDOUBLE SKUNKS IN THE GAME SO FAR  \t: "  
//		+ prevDSkunks +" \t=> "+  doubleSkunkCount;		
	}	
 	
	public String getRoundWinnerByName() {
		return this.roundWinner;			
	}
	
	public void setRoundWinnerByName() {
		int highScore   	=  0;
		int roundWinnerLoc 	= -1;
		String name 		= "";
		
		for (int i = 0; i < players.length; i++) {
			if (players[i].getRoundPoints() > highScore ) {
				highScore   = players[i].getRoundPoints();
				roundWinnerLoc = i;
			}
		}		
		try {
			name = players[roundWinnerLoc].getName();
		} catch (Exception e) {
			name = "NONE";
		}
		System.out.println("ROUND WINNER IS "+ name);
		this.roundWinner = name;			
	}
	
	private void startRound(){ 
		setRoundWinnerByName();
		//initialize Round metrics
 		roundNumber++;
		for (int i = 0; i < players.length; i++) {	
			players[i].setRoundPoints(0);
			players[i].setRoundTurns(0);
		}
	}
	
	//if is skunk deuce, doubleskunk or oneskunk.		
	private void updateTurnMetrics(){
		updateTurns();		
		if		(isOneSkunk())		{enforceOneSkunk();}
		else if	(isDeuceAndSkunk())	{enforceSkunkDeuce();}
		else if	(isDoubleSkunk())	{enforceDoubleSkunk();}		
		else	{enforceNoPenalty();}
		//check for game winner. 
		setGameWinner();		
	}

	
	public void setNextPlayer() {		
		activePlayerLoc++;			
		activePlayerLoc= activePlayerLoc%players.length;
		if(activePlayerLoc == 0){startRound();}
		activePlayer   = this.players[activePlayerLoc];	
		setGameWinner();
		if(gameWinner !=null){
			isLastRound = true;
		}
	}
	
	
	private void setGameWinner() {
		int gp 	 = 0;
		int cap	 = players.length;
		for (int i = 0; i < cap; i++) {
			gp = players[i].getGamePoints(); 
			if (gp >= this.goal) {				
				this.goal = gp;
				this.gameWinner = players[i];
			}
		}
	}

	public boolean isLastRound() {
		return this.isLastRound;
	}
	
 	public String next() {
		String result = "";
		Player player = this.getActivePlayer();
		System.out.println(player);
		int turns = player.getRoundTurns();

		if(player.getRollValue() == 0 
		||!(this.penalty.trim().length() > 0)) {
			result = (turns > 0 )?"ROLLS?":"ROLL?";
		}
		else{
			result = this.penaltyDetails;			
		}		

		if((prevRound != roundNumber) && isLastRound){
			result = "GAME OVER";
			prevRound = roundNumber;
		}
		else if(prevRound != roundNumber){
			result = "DISTRIBUTION";
			prevRound = roundNumber;
		}
		return result;
	} 

	public String actAndRespond(String answer) {
		if(answer.equalsIgnoreCase("Y")){
			handleYes();
		}else 
		if(answer.equalsIgnoreCase("N")){
			handleNo();			
		}else 
		if(answer.equalsIgnoreCase("NONE")){
			roundWinner = "";
			status = "";
		}
		else 
		if(answer.equalsIgnoreCase("1")){
			distribute(1);
			roundWinner = "";
			status = ""; 
		}
		else 
		if(answer.equalsIgnoreCase("2")){
			distribute(2);
			roundWinner = "";
			status = "";
		}
		if (isLastRound && activePlayerLoc == 0 ) {
			if (answer.equalsIgnoreCase("GAME OVER")) {
				setGameWinner(); 

				status += ("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"      );
				status += ("\n\tGAME OVER !!! THANKS FOR PLAYING SKUNK  \t\t    ");
				status += ("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n\n\n");
				status += ("\n\tGame winner is " + this.gameWinner.getName() +" With " 
					            + this.gameWinner.getGamePoints() + " Points \n\n\n" );
				status += ("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n\n"  );				
				status += (this.gameWinner);				
				status += ("\n########################################################\n\n\n");
				 
			}else{
				setGameWinner();			
				//status = "";
			}
		}
		this.penalty = "";
		return status;
	}

	
	//if user wants to roll again
	private void handleYes() {
		activePlayer.roll();
		analyzeDiceValues();
		updateTurnMetrics();
		setStatus();
		setActivePlayer();
	}

	//if user choses NOT to roll again
	private void handleNo() {
		//analyzeDiceValues, updateTurnMetrics, SetStatus, setNextPlayer
		setPassed();
		setNextPlayer();
	}	

	/*	
	The first player to accumulate a total of 100 or more points
	can continue to score as many points over 100 as he believes
	is needed to win. When he decides to stop, his total score is the “goal.” 
	Each succeeding player receives one more chance to better the goal and end the game(round).
	
	*/
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

	private void setLastRound() {	 		
		int cap			 			= players.length;			
		Player[] lastRoundSequence 	= new Player[cap];
		int playerLoc 	 			= getLoc(activePlayer);
		
		//we rearrange the array to put the winner at position 0
		lastRoundSequence[0] 		= gameWinner;
		
		for (int i = 1; i < cap; i++) {
			if(!players[playerLoc%cap].equals(gameWinner)){
				lastRoundSequence[i] 	= players[playerLoc%cap];				
			}
			playerLoc++;
		}
		this.lastRoundSequence 		= lastRoundSequence;
		this.players=lastRoundSequence;
		
		if(this.lastRoundSequence.length >=1){
			//refactor this method
		}
		//Each succeeding player receives one more 
 		//chance to better the goal and end the game(round) hence i=1.
		//so we skip the winning player i=0;

		this.activePlayer 			= players[1]; 
		this.activePlayerLoc 		= 1;
	}

	public void setLastRoundSequence() {
		this.setLastRound();		
	}

}
