import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Hughbert Kumwesiga, Wasswa Derrick, Saad Alshammari 
 */
public class DieTest {
	private static Die die;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		die = new Die();
		assertTrue( die instanceof  Die);
	}

	/**
	 * Test method for {@link Die#Die()}.
	 */
	@Test
	public final void testDie() {
		assertTrue( die instanceof  Die);	
	}

	/**
	 * Test method for {@link Die#getRollValue()}.
	 */
	@Test
	public final void testGetRollValue() {
		die.roll();
		int val = die.getRollValue();
		boolean range = (val > 0 && val < 7);
		assertTrue(range);
	}

	/**
	 * Test method for {@link Die#roll()}.
	 */
	@Test
	public final void testRoll() {	
		int val 		= 0;
		boolean range 	= false;
		
		for (int i = 0; i < 1000; i++) {
			die.roll();
			val   = die.getRollValue();
			range = (val > 0 && val < 7);
			assertTrue(range);			
		}
	}

	/**
	 * Test method for {@link Die#toString()}.
	 */
	@Test
	public final void testToString() {
		die.roll();
		int val   = die.getRollValue();
		String s  = "Die: " + val;
		
		assertEquals(die.toString(), "Die: " + val);
		assertTrue(s instanceof  String);	
	}
}
