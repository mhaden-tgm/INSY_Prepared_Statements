/*
 * @author mhaden
 * @date 20.02.2016
 * @version 0.6
 * 
 * HowTo:
 * 
 * Open:
 * /etc/postgresql/9.3/main/pg_hba.conf
 * 
 * Add:
 * line 93:
 * host	schokofabrik	schokouser	192.168.110.0/24	md5
 * 
 * host	datenbankname	username	ip adresse			md5
 * 
 * Modify:
 * /etc/postgresql/9.3/main/postgresql.conf
 * line 59: 
 * listen_addresses= '*'
 */

public class Main {
	private static String ip_adress;
	private static String port_number;
	private static String database;
	private static String user;
	private static String password;
	private static String property_path;

	/**
	 * set default value if some input is empty
	 */
	public static void default_value() {
		if (ip_adress == null) {
			ip_adress = "192.168.110.135";
		}
		if (port_number == null) {
			port_number = "5432";
		}
		if (database == null) {
			database = "schokofabrik";
		}
		if (user == null) {
			user = "schokouser";
		}
		if (password == null) {
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
		} else {
			cli.parse(args);
			property_path = cli.getArgument("c");
			if (property_path != null) {
				ip_adress = property.read_property("ip_adress", property_path);
				port_number = property.read_property("port_number", property_path);
				database = property.read_property("database", property_path);
				user = property.read_property("user", property_path);
				password = property.read_property("password", property_path);
			} else {
				ip_adress = cli.getArgument("ip");
				port_number = cli.getArgument("port");
				database = cli.getArgument("d");
				user = cli.getArgument("u");
				password = cli.getArgument("p");
			}
		}

		default_value();
		conn.db_connect(ip_adress, port_number, database, user, password);
	}
}