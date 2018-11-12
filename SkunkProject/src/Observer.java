//This class will be a bridge between the business logic and the presentation Logic
public class Observer {

	private Game game;
	
	public Observer(String[] playerNames) {
		this.game= new Game(playerNames);
		game.setActivePlayer();
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public Player getActivePlayer(){
		return this.game.getActivePlayer();
	}

	public String whatNext() {
		return this.game.next();
	}

	public String requestAction(String answer) {
		return game.actAndRespond(answer);
	}

	public String getRoundWinner() {
		return this.game.getRoundWinnerByName();
	}

	public boolean isLastRound() {
		return this.game.isLastRound();
	}

	public void requestFinalSequence() {
		this.game.setLastRoundSequence();
		
	}
}
		