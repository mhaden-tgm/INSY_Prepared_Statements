import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/*
 * handle properties file functionality
 * 
 * @author mhaden
 * @date 20.02.2016
 * @version 0.5
 */

public class PropertiesFile {
	private Properties prop;
	private String property_path;

	/**
	 * read values from properties file
	 * 
	 * @param propertyname
	 *            variable name in properties file
	 * @return value of variable in properties file
	 */
	public String read_property(String propertyname, String path) {
		prop = new Properties();
		InputStream input = null;
		property_path = path;
		if (property_path == null) {
			property_path = "settings.properties";
		}

		try {
			input = new FileInputStream(property_path);
			// load properties file
			prop.load(input);
			return prop.getProperty(propertyname);
		} catch (IOException ex) {
			System.err.println("Properties file reading failed.  Reason: " + ex.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					System.err.println("Properties file reading failed.  Reason: " + e.getMessage());
				}
			}
		}

		return null;
	}

	/**
	 * list the keys of the properties file
	 */
	public void getKeys() {
		Enumeration<?> names = prop.propertyNames();
		while (names.hasMoreElements()) {
			System.out.println(names.nextElement());
		}
	}
}
