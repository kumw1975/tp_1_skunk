import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class DiceTest {

	private static Dice dice;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dice = new Dice(new Die(), new Die());
	}

	@Test
	public final void testDice() {
		assertTrue( dice instanceof  Dice);
	}

	@Test
	public final void testDiceDieDie() {
		assertTrue( dice instanceof  Dice);	
	}

	@Test
	public final void testGetRollValue() {
		dice.roll();
		int val = dice.getRollValue();
		boolean range = (val > 0 && val < 13);
		assertTrue(range);
	}

	@Test
	public final void testRoll() {
		dice.roll();
		int val1 = dice.getDie1RollValue();
		int val2 = dice.getDie1RollValue(); 
		
		assertEquals((val1+val2), dice.getRollValue());
		
		boolean range = ((val1 > 0 && val1 < 7) && (val2 > 0 && val2 < 7));
		assertTrue(range);	
	}

	@Test
	public final void testToString() {
		dice.roll();
		int val   = dice.getRollValue();
		String displayLastRoll  = (val < 10 )? " "+val :""+val;	
		assertEquals("Dice Sum: " + displayLastRoll + " => " + dice.getDie1RollValue() + " + " + dice.getDie2RollValue(), dice.toString()); ;
	}

	@Test
	public final void testShowRollDetails() {
		assertTrue( dice.showRollDetails() instanceof  String);			
	}

	@Test
	public final void testGetDie1RollValue() {
		dice.roll();
		int val1 = dice.getDie1RollValue();
	
		boolean range = ((val1 > 0 && val1 < 7));
		assertTrue(range);		
	}

	@Test
	public final void testGetDie2RollValue() {
		dice.roll();
		int val2 = dice.getDie2RollValue();
	
		boolean range = ((val2 > 0 && val2 < 7));
		assertTrue(range);		}

}
