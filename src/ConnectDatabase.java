import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import org.apache.commons.cli.*;
import org.postgresql.ds.*;

/*
 * @author mhaden
 * @date 20.02.2016
 * @version 0.3
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

	public static void main(String[] args) {
		/*
		 * CLI
		 */
		
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

			if (ip_adress == null) {
				ip_adress = "192.168.110.135";
			}

			// Port
			port_number = cmd.getOptionValue("port");

			if (port_number == null) {
				port_number = "5432";
			}

			// Database Name
			database = cmd.getOptionValue("d");

			if (database == null) {
				database = "schokofabrik";
			}

			// Username
			user = cmd.getOptionValue("u");

			if (user == null) {
				user = "schokouser";
			}

			// Password
			password = cmd.getOptionValue("p");

			if (password == null) {
				password = "schokouser";
			}

		} catch (ParseException exp) {
			System.err.println("Parsing failed.  Reason: " + exp.getMessage());

			// automatically generate the help statement
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("ConnectDatabase", options, true);
		}
		
		/*
		 * Properties File
		 */
		if (args.length == 0) {
			Properties prop = new Properties();
			InputStream input = null;

			try {
				input = new FileInputStream("settings.properties");

				// load properties file
				prop.load(input);
				
				// IP
				ip_adress = prop.getProperty("ip_adress");

				if (ip_adress == null) {
					ip_adress = "192.168.110.135";
				}

				// Port
				port_number = prop.getProperty("port_number");

				if (port_number == null) {
					port_number = "5432";
				}

				// Database Name
				database = prop.getProperty("database");

				if (database == null) {
					database = "schokofabrik";
				}

				// Username
				user = prop.getProperty("user");

				if (user == null) {
					user = "schokouser";
				}

				// Password
				password = prop.getProperty("password");

				if (password == null) {
					password = "schokouser";
				}
				
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

		// create datasource und konfigurate
		PGSimpleDataSource ds = new PGSimpleDataSource();
		// server IP adress
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
}