import java.sql.*;
import org.postgresql.ds.PGSimpleDataSource;

/**
 * database connection
 * 
 * @author mhaden
 * @version 0.9
 */

public class DBConnect {
	private PGSimpleDataSource ds;
	private Connection con;
	// minimal value for loop
	private int min = 50;
	// maximal value for loop
	private int max = 10050;
	// value for division to get seconds
	private int sec = 1000000000;
	private PreparedStatement selectPerson, insertPerson, updatePerson, deletePerson = null;

	/**
	 * set default values if some input from cli or properties file is empty
	 * 
	 * @param connectdata
	 *            array of connection values
	 * @return connection values with default values instead of null
	 */
	public String[] setDefault(String[] connectdata) {
		if (connectdata[0] == null) {
			System.out.println("No value for IP adress in properties file. Using default value.");
			connectdata[0] = "192.168.110.135";
		}
		if (connectdata[1] == null) {
			System.out.println("No value for port number in properties file. Using default value.");
			connectdata[1] = "5432";
		}
		if (connectdata[2] == null) {
			System.out.println("No value for database in properties file. Using default value.");
			connectdata[2] = "schokofabrik";
		}
		if (connectdata[3] == null) {
			System.out.println("No value for user in properties file. Using default value.");
			connectdata[3] = "schokouser";
		}
		if (connectdata[4] == null) {
			System.out.println("No value for password in properties file. Using default value.");
			connectdata[4] = "schokouser";
		}

		return connectdata;
	}

	/**
	 * database connection
	 * 
	 * @param connectdata
	 *            connection values array with [ip_adress, port_number,
	 *            database, user, password]
	 * @return execute successfully state
	 */
	public boolean connect(String[] connectdata) {
		// create datasource und configurate
		ds = new PGSimpleDataSource();
		// server IP adress and port
		ds.setServerName(connectdata[0] + ":" + connectdata[1]);
		// databasename
		ds.setDatabaseName(connectdata[2]);
		// username
		ds.setUser(connectdata[3]);
		// password
		ds.setPassword(connectdata[4]);

		try {
			con = ds.getConnection();
			con.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			System.err.println("Connection failed! " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * start the prepared statements execution
	 * 
	 * @return if executed successfully
	 */
	public boolean start() {
		// initialize connection and prepare statement
		long startTime;
		long endTime;
		long duration;
		String mode;

		for (int j = 1; j <= 5; j++) {
			mode = null;
			startTime = System.nanoTime();
			for (int i = min; i <= max; i++) {
				switch (j) {
				case 1:
					insert(i, "Max" + i, "Mustermann" + i);
					mode = "Insert ";
					break;
				case 2:
					select(i);
					mode = "Select ";
					break;
				case 3:
					update(i, "Martin" + i);
					mode = "Update ";
					break;
				case 4:
					select(i);
					mode = "Select ";
					break;
				case 5:
					delete(i);
					mode = "Delete ";
					break;
				default:
					break;
				}
			}
			endTime = System.nanoTime();
			duration = (endTime - startTime) / sec;
			System.out.println(mode + "finished after: " + duration + " sec");
		}
		return true;
	}

	/**
	 * statement for delete command
	 * 
	 * @param number
	 *            person number
	 * @param vname
	 *            firstname
	 * @param nname
	 *            lastname
	 * @return execute successfully state
	 */
	public boolean insert(int number, String vname, String nname) {
		String insertString = "INSERT INTO Person VALUES (?, ?, ?)";
		try {
			insertPerson = con.prepareStatement(insertString);
			insertPerson.setInt(1, number);
			insertPerson.setString(2, vname);
			insertPerson.setString(3, nname);
			insertPerson.executeUpdate();

			return true;
		} catch (SQLException sql) {
			System.err.println("Inserting failed.  Reason: " + sql.getMessage());
			return false;
		}
	}

	/**
	 * statement for delete command
	 * 
	 * @param number
	 *            row number
	 * @return execute successfully state
	 */
	public boolean select(int number) {
		String selectString = "SELECT * from person where nummer=?";

		try {
			selectPerson = con.prepareStatement(selectString);
			selectPerson.setInt(1, number);
			ResultSet rs = selectPerson.executeQuery();

			while (rs.next()) { // move cursor
				String nummer = rs.getString(1);
				String vorname = rs.getString(2);
				String nachname = rs.getString(3);
				System.out.println(nummer + "\t" + vorname + "\t" + nachname);
			}
			return true;

		} catch (SQLException sql) {
			System.err.println("Selecting failed.  Reason: " + sql.getMessage());
			return false;
		}
	}

	/**
	 * statement for delete command
	 * 
	 * @param number
	 *            row number
	 * @param vname
	 *            firstname
	 * @return execute successfully state
	 */
	public boolean update(int number, String vname) {
		String deleteString = "UPDATE Person SET vorname = ? WHERE nummer=?";
		try {
			updatePerson = con.prepareStatement(deleteString);
			updatePerson.setString(1, vname);
			updatePerson.setInt(2, number);
			updatePerson.executeUpdate();
			return true;
		} catch (SQLException sql) {
			System.err.println("Updating failed.  Reason: " + sql.getMessage());
			return false;
		}
	}

	/**
	 * statement for delete command
	 * 
	 * @param number
	 *            row number
	 * @return execute successfully state
	 */
	public boolean delete(int number) {
		String deleteString = "DELETE FROM Person WHERE nummer=?";
		try {
			deletePerson = con.prepareStatement(deleteString);
			deletePerson.setInt(1, number);
			deletePerson.executeUpdate();
			return true;
		} catch (SQLException sql) {
			System.err.println("Deleting failed.  Reason: " + sql.getMessage());
			return false;
		}
	}
}
