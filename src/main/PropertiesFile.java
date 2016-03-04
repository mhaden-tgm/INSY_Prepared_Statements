package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * handle properties file functionality
 * 
 * @author mhaden
 * @version 0.9
 */

public class PropertiesFile {
	private Properties prop;
	private String property_path;

	public PropertiesFile() {
		prop = new Properties();
	}

	/**
	 * read values from properties file
	 * 
	 * @param propertyname
	 *            variable name in properties file
	 * @param path
	 *            path to properties file
	 * @return value of variable in properties file
	 */
	public String getProperty(String propertyname) {
		return prop.getProperty(propertyname);
	}

	public boolean init(String path) {
		InputStream input = null;
		try {
			// properties file path
			property_path = path;
			input = new FileInputStream(property_path);
			// load properties file
			prop.load(input);
		} catch (IOException ex) {
			System.err.println("Properties file reading failed. Reason: " + ex.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					System.err.println("Properties file closing failed. Reason: " + e.getMessage());
				}
			}
		}
		return true;
	}

	/**
	 * link to init setting default path
	 * 
	 * @param propertyname
	 *            key in properties file
	 * @return execution state
	 */
	public boolean init() {
		return init("settings.properties");
	}

	/**
	 * list the keys of the properties file
	 * 
	 * @return execute successfully state
	 */
	public boolean getKeys() {
		Enumeration<?> names = prop.propertyNames();
		while (names.hasMoreElements()) {
			System.out.println(names.nextElement());
		}

		return true;
	}
}
