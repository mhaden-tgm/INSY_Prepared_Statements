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

	/**
	 * read values from properties file
	 * 
	 * @param propertyname
	 *            variable name in properties file
	 * @param path
	 *            path to properties file
	 * @return value of variable in properties file
	 */
	public String getProperty(String propertyname, String path) {
		prop = new Properties();
		InputStream input = null;
		// properties file path
		property_path = path;

		try {
			input = new FileInputStream(property_path);
			// load properties file
			prop.load(input);

			return prop.getProperty(propertyname);

			// return prop.getProperty(propertyname);
		} catch (IOException ex) {
			System.err.println("Properties file reading failed. Reason: " + ex.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					System.err.println("Properties file reading failed. Reason: " + e.getMessage());
				}
			}
		}

		return null;
	}

	/**
	 * link to read values from properties file setting default path
	 * 
	 * @param propertyname
	 *            key in properties file
	 * @return value of variable in properties file
	 */
	public String getProperty(String propertyname) {
		return getProperty(propertyname, "settings.properties");
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
