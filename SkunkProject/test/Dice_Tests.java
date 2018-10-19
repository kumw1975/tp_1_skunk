import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.princeton.cs.introcs.StdOut;

/**
 * @author Hughbert Kumwesiga | Wasswa Derrick 
 * 
 * Skunk Game: A Simulation of the Skunk Board Game.
 * This class Tests the Dice Class Methods and functionality
 * Tests for the Die Object. 
 * The Test tests Dice with both the Random Die and the Predictable Die
 */
public class Dice_Tests{
	
	private static int[] die1ArrayValues;
	private static int[] die2ArrayValues;

	private static Dice dice1;
	private static Dice dice2;                
	private static Dice dice3;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//before the class is tested a instantiate 3 Dice
		die1ArrayValues = new int[] {1,5,3,2,6,5,3,2,3,4,2,1,2,4,3,2,3,4,5,6};
		die2ArrayValues = new int[] {3,2,3,6,2,3,1,5,4,3,4,2,1,5,6,5,2,2,3,4};
		
		dice1 = new Dice(); //Two random Die
		dice2 = new Dice(new Die(die1ArrayValues), new Die(die2ArrayValues));//two prdictable die
		dice3 = new Dice(new Die(die1ArrayValues), new Die(die2ArrayValues));//One random and one Predictable
	}

	@Before //Before each method reset the dice
	public void setUp() throws Exception {
		//before running a new test case	
		die1ArrayValues = new int[] {1,5,3,2,6,5,3,2,3,4,2,1,2,4,3,2,3,4,5,6};
		die2ArrayValues = new int[] {3,2,3,6,2,3,1,5,4,3,4,2,1,5,6,5,2,2,3,4};
		
		dice1 = new Dice(); //Two random Die
		dice2 = new Dice(new Die(die1ArrayValues), new Die(die2ArrayValues));//two predictable die
		dice3 = new Dice(new Die(), new Die(die2ArrayValues));//One random and one Predictable
	}


	@Test //constructor with no parameters
	public final void testDice() {		
		assertTrue((dice1 instanceof Dice));
	}

	@Test //constructor that takes two die as its parameters
	public final void testDiceDieDie() {
		assertTrue((dice2 instanceof Dice));
		assertTrue((dice3 instanceof Dice));
	}

	@Test //die 1 and 2 are Random
	public final void testRandomDiceDieDie() {
	
		int rollValue = 0;
		
		for (int i = 0; i < 1000; i++) {
			dice1.roll();	
			rollValue = dice1.getRollValue();
			assertTrue((rollValue > 0 && rollValue < 13));		
		}
	}
	
	@Test //die 1 and 2 are predictable
	public final void testPredictableDiceDieDie() {
		
		int die1RollValue = 0;
		int die2RollValue = 0;
		
		for (int i = 0; i < 1000; i++) {
			dice2.roll();	
			die1RollValue = dice2.getDie1RollValue();
			die2RollValue = dice2.getDie2RollValue();
			assertEquals(die1ArrayValues[i%die1ArrayValues.length], die1RollValue);		
			assertEquals(die2ArrayValues[i%die2ArrayValues.length], die2RollValue);		
		}
 	}
	
	@Test //Die 1 is random & Die 2 is predictable
	public final void testRandomPredictableDiceDieDie() {
		
		for (int i = 0; i < 1000; i++) {
			dice3.roll();	
			assertTrue((dice3.getDie1RollValue() > 0 && dice3.getDie1RollValue() < 7));				
			assertEquals(die2ArrayValues[i%die2ArrayValues.length], dice3.getDie2RollValue());		
		}		
	}

	@Test // Testing roll values of all dice
	public final void testGetRollValue() {
		
		dice1.roll();  
		dice2.roll();
		dice3.roll(); 

		assertTrue((dice1.getDie1RollValue() > 0 && dice1.getDie1RollValue() < 13));
		assertTrue((dice2.getDie1RollValue() > 0 && dice2.getDie1RollValue() < 13));
		assertTrue((dice3.getDie1RollValue() > 0 && dice3.getDie1RollValue() < 13));	
		
	}

	@Test //using get Roll value test to test ROLL
	public final void testRoll() {
		testGetRollValue();
	}

	@Test
	public final void testToString() {
		
		assertTrue(dice1.toString() instanceof String);
		assertTrue(dice2.toString() instanceof String);
		assertTrue(dice3.toString() instanceof String);

		for (int i = 0; i < 1000; i++) {
			dice2.roll();
			int rollValue 		 = die1ArrayValues[i%die1ArrayValues.length]+die2ArrayValues[i%die2ArrayValues.length];
			String diceRollInfo  = (rollValue < 10 )? " "+rollValue :""+rollValue;		
			diceRollInfo  ="Dice Sum: " + diceRollInfo + " => " + die1ArrayValues[i%die1ArrayValues.length] + " + " + die2ArrayValues[i%die2ArrayValues.length];
			assertEquals(dice2.toString(), diceRollInfo);
		}
	}

	@Test
	public final void testGetDie1RollValue() {
		for (int i = 0; i < 1000; i++) {
			dice2.roll();
			assertEquals(die1ArrayValues[i%die1ArrayValues.length], dice2.getDie1RollValue());			
		}		
	}

	@Test
	public final void testGetDie2RollValue(){	
		for (int i = 0; i < 1000; i++) {
			dice2.roll();
			assertEquals(die2ArrayValues[i%die2ArrayValues.length], dice2.getDie2RollValue());			
		}
	}
	
	@Test
	public final void testShowRollDetails(){
		dice2.roll();
		assertEquals(" 4 => 1 + 3", dice2.showRollDetails());
	}

	@After //After Each method reset the dice <- redundant since we do this in @before
	public void tearDown() throws Exception {
		//before running a new test case		
		dice1 = new Dice(); //Two random Die
		dice2 = new Dice(new Die(die1ArrayValues), new Die(die2ArrayValues));//two prdictable die
		dice3 = new Dice(new Die(die1ArrayValues), new Die(die2ArrayValues));//One random and one Predictable
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		StdOut.println("Completed Test Suite for Dice Class");
	}

}
