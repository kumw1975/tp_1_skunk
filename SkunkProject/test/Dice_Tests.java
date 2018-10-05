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
		boolean range = false;
		
		for (int i = 0; i < 1000; i++) {
			dice1.roll();	
			rollValue = dice1.getRollValue();
			range = (rollValue > 0 && rollValue < 13);			
			assertTrue(range);		
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

		int die1RollValue 	= 0;
		int die2RollValue 	= 0;		
		boolean range 		= false;
		
		for (int i = 0; i < 1000; i++) {
			dice3.roll();	
			die1RollValue = dice3.getDie1RollValue();
			die2RollValue = dice3.getDie2RollValue();			
			range 		  = (die1RollValue > 0 && die1RollValue < 7);	
			
			assertTrue(range);				
			assertEquals(die2ArrayValues[i%die2ArrayValues.length], die2RollValue);		
		}		
	}

	@Test // Testing roll values of all dice
	public final void testGetRollValue() {
		
		dice1.roll();  
		dice2.roll();
		dice3.roll(); 
		
		int die1RollValue 	= dice1.getDie1RollValue();
		int die2RollValue 	= dice2.getDie1RollValue();
		int die3RollValue 	= dice3.getDie1RollValue();		
		boolean dice1Range  = (die1RollValue > 0 && die1RollValue < 13);	
		boolean dice2Range  = (die2RollValue > 0 && die2RollValue < 13);	
		boolean dice3Range  = (die3RollValue > 0 && die3RollValue < 13);	
		
		assertTrue(dice1Range);
		assertTrue(dice2Range);
		assertTrue(dice3Range);	
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
	public final void testShowRollDetails() {
		testToString();
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

	@After //After Each method reset the dice
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
