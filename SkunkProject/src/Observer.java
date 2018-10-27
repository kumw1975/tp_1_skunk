
public class Observer {
//This class will be a bridge between the business logic and the presentation Logic
	private Game game;
	public Observer() {
		
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public void requestNewGameSetUp(String[] playerNames) {
		this.game = new Game(playerNames);
	}
	
	/*   
	  Request - Observer: Observer->RequestGame
	  Game -response Observer: observer response skunkApp. 
	 
	  Ask how many users are playing :<-user input ::From skunkApp to observer::>From observer to game::>game to observer, then observer sends response to skunkapp
	  Ask for each player's username :<-user input
	  start the game :-
	  tell the user that the game has started:- 
	  
	  Ask if user wants to play again:<-user input
	  Ask how user wants to collect chips:<-user input. 
	  
	 */
	
	
	
//	public static void main(String[] args) {
//		Observer observer = new Observer();
//		observer.getQuestionFromGame();
//		observer.reportRollResultsToGame();
//		observer.requestGameSetupDetails();
//		observer.sendGameSetupDetailsToGame();
//		observer.
//		
//		skunkApp.sendGameSetupDetailsToObserver();
//		skunkApp.requestQuestionFromObserver();
//		
//	}

}
		