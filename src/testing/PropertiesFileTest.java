package testing;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.PropertiesFile;

/**
 * @author mhaden
 *
 */
public class PropertiesFileTest {
	private PropertiesFile prop;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		prop = new PropertiesFile();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link PropertiesFile#init(java.lang.String)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testInitNullException() {
		prop.init(null);
	}

	/**
	 * Test method for {@link PropertiesFile#init()}.
	 */
	@Test
	public void testInit() {
		prop.init();
	}

	/**
	 * Test method for {@link PropertiesFile#getProperty(java.lang.String)}.
	 */
	@Test
	public void testGetPropertyString() {
		prop.init();
		assertEquals("schokouser", prop.getProperty("user"));
	}

	/**
	 * Test method for {@link PropertiesFile#getKeys()}.
	 */
	@Test
	public void testGetKeys() {
		assertTrue(prop.getKeys());
		prop.init();
		assertTrue(prop.getKeys());
	}

}
