
/**
 * @author mhaden
 * @version 0.9
 */

public class Main {
	private static String property_path;

	/**
	 * main method
	 * 
	 * @param args
	 *            start arguments
	 */
	public static void main(String[] args) {
		System.out.println("#########  Prepared Statements  #########");
		PropertiesFile property = new PropertiesFile();
		CLI cli = new CLI();
		DBConnect conn = new DBConnect();
		String[] connectdata = new String[5];

		// use properties file if no arguments are given
		if (args.length == 0) {
			connectdata[0] = property.getProperty("ip_adress");
			connectdata[1] = property.getProperty("port_number");
			connectdata[2] = property.getProperty("database");
			connectdata[3] = property.getProperty("user");
			connectdata[4] = property.getProperty("password");
			// if arguments are given, check for -c argument
		} else {
			cli.init(args);
			// read -c argument
			property_path = cli.getArgument("c");
			// if -c argument is given, read properties file from path in
			// argument
			if (cli.getArgument("c") != null) {
				connectdata[0] = property.getProperty("ip_adress", property_path);
				connectdata[1] = property.getProperty("port_number", property_path);
				connectdata[2] = property.getProperty("database", property_path);
				connectdata[3] = property.getProperty("user", property_path);
				connectdata[4] = property.getProperty("password", property_path);
				// if no -c argument is given, read cli input
			} else {
				connectdata[0] = cli.getArgument("ip");
				connectdata[1] = cli.getArgument("port");
				connectdata[2] = cli.getArgument("d");
				connectdata[3] = cli.getArgument("u");
				connectdata[4] = cli.getArgument("p");
			}
		}

		// set default values if something is null
		// connect to database
		conn.connect(conn.setDefault(connectdata));
		conn.start();
	}
}