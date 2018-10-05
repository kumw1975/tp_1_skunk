/**
 * Dice represents a single pair of rollable Die objects, randomly generating
 * sums of their two values
 * 
 * This is a Javadoc comment: add more to your finished class below
 * 
 * @authors Hughbert Kumwesiga & Wasswa Derrick
 *
 */
 
public class Dice{
	// Instance fields (variables) may be declared anywhere in class body
	// Convention: put at top
	private Die die1;
	private Die die2;
	private int rollValue;

	// Constructors (object initializers) also can be declared anywhere
	// Convention: after instance fields/variables
	// initialize instance variables die1 and die2 by
	// creating a new instance of each Die
	public Dice(){
		this.die1 = new Die();
		this.die2 = new Die();
	}
	
	// overloaded constructor :: use for testable die
	public Dice(Die die1, Die die2){
		this.die1 = die1;
		this.die2 = die2;
	}

	// Instance methods can also be declared anywhere
	// Convention: after constructors
	public int getRollValue(){
		return this.rollValue;
	}

	// Setter method to set the value of the instance variable. 
	private void setRollValue(int rollValue){
		this.rollValue = rollValue;
	}

	// roll each of die1, die2, sum their last rolls,
	// then set Dice.lastRoll to this value	
	public void roll(){
		die1.roll();
		die2.roll();
		int rollValue =  die1.getRollValue() + die2.getRollValue();
		setRollValue(rollValue);	
	}

	// the following method converts the internals of
	// this Dice object, and returns a descriptive String:
	// Roll of 7 => 4 + 3
	public String toString(){		
		int rollValue 			= this.getRollValue();
		String displayLastRoll  = (rollValue < 10 )? " "+rollValue :""+rollValue;		
		return "Dice Sum: " + displayLastRoll + " => " + this.die1.getRollValue() + " + " + this.die2.getRollValue();
	}
	
	public String showRollDetails(){		
		int rollValue 			= this.getRollValue();
		String displayLastRoll  = (rollValue < 10 )? " "+rollValue :""+rollValue;		
		return displayLastRoll + " => " + this.die1.getRollValue() + " + " + this.die2.getRollValue();
	}
	
	public int getDie1RollValue(){		
		return this.die1.getRollValue();
	}
	
	public int getDie2RollValue(){		
		return this.die2.getRollValue();
	}
	
 
}
