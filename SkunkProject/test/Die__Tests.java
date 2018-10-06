import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.princeton.cs.introcs.StdOut;
/**
 * @author Hughbert Kumwesiga, Derrick Wasswa
 *
 */
public class Die__Tests {
	
	private static int[] die2ArrayValues;
	private static Die die1;  //randomDie
	private static Die die2;  //Predictable Die
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//before the class is tested a instantiate 2 Die	
		die2ArrayValues = new int[] {1,5,3,2,6,5,3,2,3,4,2,1,2,4,3,2,3,4,5,6};
		die1 = new Die(); 					// random Die
		die2 = new Die(die2ArrayValues);	// predictable die
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before //Before each method reset the die //before running a new test case	
	public void setUp() throws Exception {			
		die1 = new Die(); 					// random Die
		die2 = new Die(die2ArrayValues);	// predictable die
	}
	/**
	 * Test method for {@link team__project__1.Die#Die()}.
	 */
	@Test
	public final void testDie() {
		assertTrue((die1 instanceof Die));
		assertTrue((die2 instanceof Die));
	}

	/**
	 * Test method for {@link team__project__1.Die#Die(int[])}.
	 */
	@Test
	public final void testDieIntArray() {
		assertTrue(die2ArrayValues!=null);
		assertArrayEquals(die2ArrayValues, die2ArrayValues);//redundant
	}

	/**
	 * Test method for {@link team__project__1.Die#getRollValue()}.
	 */
	@Test
	public final void testGetRollValue() {
		
		int die1RollValue = 0;
		int die2RollValue = 0;
		boolean range = false;
		
		for (int i = 0; i < 1000; i++) {
			die1.roll();	
			die2.roll();
			die1RollValue = die1.getRollValue();
			die2RollValue = die2.getRollValue();
			range 		  = (die1RollValue > 0 && die1RollValue < 7);	
			
			assertTrue(range);	
			assertEquals(die2ArrayValues[i%die2ArrayValues.length], die2RollValue);	
		}
	}
 
	/**
	 * Test method for {@link team__project__1.Die#roll()}.
	 */
	@Test
	public final void testRoll() {
		testGetRollValue();
	}

	/**
	 * Test method for {@link team__project__1.Die#toString()}.
	 */
	@Test
	public final void testToString() {
		assertTrue(die1.toString() instanceof String);
		assertTrue(die2.toString() instanceof String);

		for (int i = 0; i < 1000; i++) {
			die2.roll();
			int rollValue 		 = die2ArrayValues[i%die2ArrayValues.length];
			assertEquals(die2.toString(), "Die: " + rollValue);
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		StdOut.println("Tested Random Die and Predictable Die Test Cases");
		StdOut.println("Completed Test Suite for Die Class");

	}

	
	
}
