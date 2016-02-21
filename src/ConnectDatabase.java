import java.io.*;
import java.sql.*;
import java.util.Properties;
import org.apache.commons.cli.*;
import org.postgresql.ds.*;

/*
 * @author mhaden
 * @date 20.02.2016
 * @version 0.4
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

public class ConnectDatabase {
	private static String ip_adress;
	private static String port_number;
	private static String database;
	private static String user;
	private static String password;

	/**
	 * handle cli arguments
	 * 
	 * @param args
	 *            program cli arguments
	 */
	public static void cli(String[] args) {
		// create Options object
		Options options = new Options();

		// add options
		options.addOption("ip", true, "IP Adress");
		options.addOption("port", true, "Port Number");
		options.addOption("d", true, "Database Name");
		options.addOption("u", true, "Username");
		options.addOption("p", true, "Password");

		CommandLineParser parser = new DefaultParser();

		try {
			CommandLine cmd = parser.parse(options, args);

			// IP
			ip_adress = cmd.getOptionValue("ip");
			// Port
			port_number = cmd.getOptionValue("port");
			// Database Name
			database = cmd.getOptionValue("d");
			// Username
			user = cmd.getOptionValue("u");
			// Password
			password = cmd.getOptionValue("p");
			// connect to database
			db_connect();

		} catch (ParseException exp) {
			System.err.println("Parsing failed.  Reason: " + exp.getMessage());
			// automatically generate the help statement
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("ConnectDatabase", options, true);
		}
	}

	/**
	 * read values from properties file
	 */
	public static void read_property() {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("settings.properties");
			// load properties file
			prop.load(input);
			// IP
			ip_adress = prop.getProperty("ip_adress");
			// Port
			port_number = prop.getProperty("port_number");
			// Database Name
			database = prop.getProperty("database");
			// Username
			user = prop.getProperty("user");
			// Password
			password = prop.getProperty("password");

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
	}

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
	 * database connection
	 */
	public static void db_connect() {
		default_value();

		// create datasource und konfigurate
		PGSimpleDataSource ds = new PGSimpleDataSource();
		// server IP adress and port
		ds.setServerName(ip_adress + ":" + port_number);
		// databasename
		ds.setDatabaseName(database);
		// username
		ds.setUser(user);
		// password
		ds.setPassword(password);
		try (
				// create connection
				Connection con = ds.getConnection();
				// prepare query and run
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM person");) {
			// handle results
			while (rs.next()) { // move cursor
				String wert = rs.getString(2);
				System.out.println(wert);
			}
		} catch (SQLException sql) {
			sql.printStackTrace(System.err);
		}
	}

	/**
	 * main method
	 * 
	 * @param args
	 *            start arguments
	 */
	public static void main(String[] args) {
		if (args.length == 0) { // use properties file if no arguments are given
			read_property();
			db_connect();
		} else {
			cli(args);
		}
	}
}