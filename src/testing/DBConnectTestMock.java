package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.DBConnect;
import static org.mockito.Mockito.*;

/**
 * @author mhaden
 *
 */
public class DBConnectTestMock {
	private DBConnect dbc;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		String[] exp = { "192.168.110.135", "5432", "schokofabrik", "schokouser", "schokouser" };
		dbc=mock(DBConnect.class);
		when(dbc.connect(exp)).thenReturn(true);
	}

	/**
	 * Test method for {@link main.DBConnect#connect(java.lang.String[])}.
	 */
	@Test
	public void testConnect() {
		String[] exp = { "192.168.110.135", "5432", "schokofabrik", "schokouser", "schokouser" };
		assertEquals(true, dbc.connect(exp));
		String[] exp2 = { "192.168.110.1", "5432", "schokofabrik", "schokouser", "schokouser" };
		assertEquals(false, dbc.connect(exp2));
	}
}
