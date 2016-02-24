import org.apache.commons.cli.*;

/*
 * handle CLI functionality
 * 
 * @author mhaden
 * @date 20.02.2016
 * @version 0.5
 */

public class CLI {
	private CommandLine cmd;
	
	/**
	 * handle cli arguments
	 * 
	 * @param args
	 *            program cli arguments
	 */
	public void parse(String[] args) {
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
			cmd = parser.parse(options, args);

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
		return cmd.getOptionValue(argumentname);
	}
}
