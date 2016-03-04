package testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.CLI;

/**
 * @author mhaden
 *
 */
public class CLITest {
	private CLI cli;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		cli = new CLI();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link CLI#init(java.lang.String[])}.
	 */
	@Test
	public void testInit() {
		String[] args = { "-u", "schokouser" };
		assertTrue(cli.init(args));
	}

	/**
	 * Test method for {@link CLI#getHelp()}.
	 */
	@Test
	public void testGetHelp() {
		String[] args = { "-u", "schokouser" };
		cli.init(args);
		assertTrue(cli.getHelp());
	}

	/**
	 * Test method for {@link CLI#getArgument(java.lang.String)}.
	 */
	@Test
	public void testGetArgument() {
		String[] args = { "-u", "schokouser" };
		cli.init(args);
		assertEquals("schokouser", cli.getArgument("u"));
	}

}
