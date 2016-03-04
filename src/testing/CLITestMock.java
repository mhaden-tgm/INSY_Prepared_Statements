package testing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import main.CLI;

/**
 * @author mhaden
 *
 */
public class CLITestMock {
	private CLI cli;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		String[] args = { "-ip", "192.168.110.135", "-port", "5432", "-h", "schokofabrik", "-u", "schokouser", "-p",
				"schokouser" };
		cli = mock(CLI.class);
		when(cli.init(args)).thenReturn(true);
		when(cli.getArgument("u")).thenReturn("schokouser");
		when(cli.getHelp()).thenReturn(true);
	}

	/**
	 * Test method for {@link main.CLI#init(java.lang.String[])}.
	 */
	@Test
	public void testInit() {
		String[] args = { "-ip", "192.168.110.135", "-port", "5432", "-h", "schokofabrik", "-u", "schokouser", "-p",
				"schokouser" };
		assertTrue(cli.init(args));
		String[] args2 = { "-ip", "192.168.110.100", "-port", "5432", "-h", "schokofabrik", "-u", "schokouser", "-p",
				"schokouser" };
		assertFalse(cli.init(args2));
	}

	/**
	 * Test method for {@link main.CLI#getHelp()}.
	 */
	@Test
	public void testGetHelp() {
		assertTrue(cli.getHelp());
	}

	/**
	 * Test method for {@link main.CLI#getArgument(java.lang.String)}.
	 */
	@Test
	public void testGetArgument() {
		assertEquals("schokouser", cli.getArgument("u"));
	}

}
