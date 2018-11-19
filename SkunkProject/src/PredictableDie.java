public class PredictableDie{
	
	private int 	rollValue;
	private int 	rollPosition;
	private int[] 	rollValues;
   		
	public PredictableDie(int[] rollValues){
		this.rollValues   = rollValues;
		this.rollPosition = 0; 
	}

	// getter or accessor method
	public int getRollValue(){
		return this.rollValue;
	}
	
	// setter method
	private void setRollValue(int rollValue){
		this.rollValue = rollValue;
	}
	
	// note how this changes Die's state, but doesn't return anything
	public void roll(){
		int rollValue = 0;
		rollValue = rollValues[this.rollPosition%this.rollValues.length];
		setRollValue(rollValue);		
		setRollPosition(rollPosition+1);
	}
	
	private void setRollPosition(int i) {
		this.rollPosition = i;		
	}

	//this OVERRIDES the default Object.toString()
	@Override
	public String toString(){
		return "Die: " + this.getRollValue();
	}

}


