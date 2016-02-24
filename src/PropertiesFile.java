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
	Properties prop;

	/**
	 * read values from properties file
	 * 
	 * @param propertyname
	 *            variable name in properties file
	 * @return value of variable in properties file
	 */
	public String read_property(String propertyname) {
		prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("settings.properties");
			// load properties file
			prop.load(input);
			return prop.getProperty(propertyname);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
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
