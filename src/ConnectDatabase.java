import java.sql.*;

import org.apache.commons.cli.*;
import org.postgresql.ds.*;

/*
 * @author mhaden
 * @date 20.02.2016
 * @version 0.2
 * 
 * HowTo:
 * 
 * 	Anpassen von
 * /etc/postgresql/9.3/main/pg_hba.conf
 * 
 * Hinzufuegen
 * line 93:
 * host	schokofabrik	schokouser	192.168.110.0/24	md5
 * 
 * host	datenbankname	username	ip adresse			md5
 * 
 * Anpassen von
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
			// oops, something went wrong
			System.err.println("Parsing failed.  Reason: " + exp.getMessage());

			// automatically generate the help statement
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("ConnectDatabase", options, true);
		}

		// Datenquelle erzeugen und konfigurieren
		PGSimpleDataSource ds = new PGSimpleDataSource();
		// Server IP - Adresse
		ds.setServerName(ip_adress + ":" + port_number);
		// Datenbank Name
		ds.setDatabaseName(database);
		// Username
		ds.setUser(user);
		// Passwort
		ds.setPassword(password);
		try (
				// Verbindung herstellen
				Connection con = ds.getConnection();
				// Abfrage vorbereiten und ausfuehren
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM person");) {
			// Ergebnisse verarbeiten
			while (rs.next()) { // Cursor bewegen
				String wert = rs.getString(2);
				System.out.println(wert);
			}
		} catch (SQLException sql) {
			sql.printStackTrace(System.err);
		}
	}
}