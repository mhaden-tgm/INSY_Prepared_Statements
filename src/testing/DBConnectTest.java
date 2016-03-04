package testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.DBConnect;

/**
 * @author mhaden
 *
 */
public class DBConnectTest {
	private DBConnect dbc;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dbc = new DBConnect();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link DBConnect#setDefault(java.lang.String[])}.
	 */
	@Test
	public void testSetDefault() {
		String[] act = { null, null, null, null, null };
		String[] exp = { "192.168.110.135", "5432", "schokofabrik", "schokouser", "schokouser" };
		assertArrayEquals(exp, dbc.setDefault(act));
	}

	/**
	 * Test method for {@link DBConnect#connect(java.lang.String[])}.
	 */
	@Test
	public void testConnect() {
		String[] con = { "192.168.110.135", "5432", "schokofabrik", "schokouser", "schokouser" };
		assertTrue(dbc.connect(con));
	}

	/**
	 * Test method for {@link DBConnect#start()}.
	 */
	@Test
	public void testStart() {
		String[] con = { "192.168.110.135", "5432", "schokofabrik", "schokouser", "schokouser" };
		dbc.connect(con);
		assertTrue(dbc.start());
	}

	/**
	 * Test method for
	 * {@link DBConnect#insert(int, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testInsert() {
		String[] con = { "192.168.110.135", "5432", "schokofabrik", "schokouser", "schokouser" };
		dbc.connect(con);
		assertTrue(dbc.insert(100, "Max", "Mustermann"));
		assertFalse(dbc.insert(1, "Max", "Mustermann"));
	}

	/**
	 * Test method for {@link DBConnect#select(int)}.
	 */
	@Test
	public void testSelect() {
		String[] con = { "192.168.110.135", "5432", "schokofabrik", "schokouser", "schokouser" };
		dbc.connect(con);
		assertTrue(dbc.select(1));
	}

	/**
	 * Test method for {@link DBConnect#update(int, java.lang.String)}.
	 */
	@Test
	public void testUpdate() {
		String[] con = { "192.168.110.135", "5432", "schokofabrik", "schokouser", "schokouser" };
		dbc.connect(con);
		assertTrue(dbc.update(100, "Martin"));
	}

	/**
	 * Test method for {@link DBConnect#delete(int)}.
	 */
	@Test
	public void testDelete() {
		String[] con = { "192.168.110.135", "5432", "schokofabrik", "schokouser", "schokouser" };
		dbc.connect(con);
		assertTrue(dbc.delete(100));
		assertFalse(dbc.delete(1));
	}

}
