public class Die{
	
	private int rollValue;
    		
	public Die(){}

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
		rollValue = (int) (Math.random() * 6 + 1);
		setRollValue(rollValue);				
 	}
 
	//this OVERRIDES the default Object.toString()
	@Override
	public String toString(){
		return "Die: " + this.getRollValue();
	}

}


