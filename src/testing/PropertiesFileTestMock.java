package testing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import main.PropertiesFile;

/**
 * @author mhaden
 *
 */
public class PropertiesFileTestMock {
	PropertiesFile prop;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		prop = mock(PropertiesFile.class);
		when(prop.init()).thenReturn(true);
		when(prop.getKeys()).thenReturn(true);
		when(prop.getProperty("user")).thenReturn("schokouser");
	}

	/**
	 * Test method for {@link main.PropertiesFile#getProperty(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetProperty() {
		assertEquals("schokouser", prop.getProperty("user"));
	}

	/**
	 * Test method for {@link main.PropertiesFile#init()}.
	 */
	@Test
	public void testInit() {
		assertTrue(prop.init());
	}

	/**
	 * Test method for {@link main.PropertiesFile#getKeys()}.
	 */
	@Test
	public void testGetKeys() {
		assertTrue(prop.getKeys());
	}

}
