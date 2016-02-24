import org.apache.commons.cli.*;

/*
 * handle CLI functionality
 * 
 * @author mhaden
 * @date 20.02.2016
 * @version 0.5
 */

public class CLI {
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
	public void getcli(String[] args) {
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

		} catch (ParseException exp) {
			System.err.println("Parsing failed.  Reason: " + exp.getMessage());
			// automatically generate the help statement
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("ConnectDatabase", options, true);
		}
	}

	/**
	 * return specific value from cli argument
	 * 
	 * @param argumentname name of the argument
	 * @return value from argument
	 */
	public String getArgument(String argumentname) {
		switch (argumentname) {
		case "ip_adress":
			return ip_adress;

		case "port_number":
			return port_number;

		case "database":
			return database;

		case "user":
			return user;

		case "password":
			return password;
		default:
			break;
		}
		return null;
	}
}
