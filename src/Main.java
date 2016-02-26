/**
 * @author mhaden
 * @version 0.7
 */

public class Main {
	private static String ip_adress;
	private static String port_number;
	private static String database;
	private static String user;
	private static String password;
	private static String property_path;

	/**
	 * set default values if some input from cli or properties file is empty
	 */
	public static void default_value() {
		if (ip_adress == null) {
			System.out.println("No value for IP adress in properties file. Using default value.");
			ip_adress = "192.168.110.135";
		}
		if (port_number == null) {
			System.out.println("No value for port number in properties file. Using default value.");
			port_number = "5432";
		}
		if (database == null) {
			System.out.println("No value for database in properties file. Using default value.");
			database = "schokofabrik";
		}
		if (user == null) {
			System.out.println("No value for user in properties file. Using default value.");
			user = "schokouser";
		}
		if (password == null) {
			System.out.println("No value for password in properties file. Using default value.");
			password = "schokouser";
		}
	}

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

		// use properties file if no arguments are given
		if (args.length == 0) {
			ip_adress = property.read_property("ip_adress", null);
			port_number = property.read_property("port_number", null);
			database = property.read_property("database", null);
			user = property.read_property("user", null);
			password = property.read_property("password", null);
			// if arguments are given, check for -c argument
		} else {
			cli.parse(args);
			// read -c argument
			property_path = cli.getArgument("c");
			// if -c argument is given, read properties file from path in
			// argument
			if (property_path != null) {
				ip_adress = property.read_property("ip_adress", property_path);
				port_number = property.read_property("port_number", property_path);
				database = property.read_property("database", property_path);
				user = property.read_property("user", property_path);
				password = property.read_property("password", property_path);
				// if no -c argument is given, read cli input
			} else {
				ip_adress = cli.getArgument("ip");
				port_number = cli.getArgument("port");
				database = cli.getArgument("d");
				user = cli.getArgument("u");
				password = cli.getArgument("p");
			}
		}

		// set default values if something is null
		default_value();
		// connect to database
		conn.db_connect(ip_adress, port_number, database, user, password);
	}
}