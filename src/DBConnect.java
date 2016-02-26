import java.sql.*;
import org.postgresql.ds.PGSimpleDataSource;

/**
 * database connection
 * 
 * @author mhaden
 * @date 20.02.2016
 * @version 0.6
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
	 * database connection
	 * 
	 * @param ip_adress
	 *            server ip adress
	 * @param port_number
	 *            server port number
	 * @param database
	 *            database name
	 * @param user
	 *            username for login
	 * @param password
	 *            password for login
	 */
	public void db_connect(String ip_adress, String port_number, String database, String user, String password) {
		// create datasource und konfigurate
		ds = new PGSimpleDataSource();
		// server IP adress and port
		ds.setServerName(ip_adress + ":" + port_number);
		// databasename
		ds.setDatabaseName(database);
		// username
		ds.setUser(user);
		// password
		ds.setPassword(password);

		// initialize connection and prepare statement
		init_select();
		init_insert();
		init_update();
		init_delete();

		// INSERT
		long startTime = System.nanoTime();
		for (int i = min; i <= max; i++) {
			insert(i);
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / sec;
		System.out.println("Insert finished after: " + duration + " sec");

		// SELECT
		// Measure time for statement
		startTime = System.nanoTime();
		for (int i = min; i <= max; i++) {
			select(i);
		}
		endTime = System.nanoTime();
		duration = (endTime - startTime) / sec;
		System.out.println("Select finished after: " + duration + " sec");

		// UPDATE
		startTime = System.nanoTime();
		for (int i = min; i <= max; i++) {
			update(i);
		}
		endTime = System.nanoTime();
		duration = (endTime - startTime) / sec;
		System.out.println("Update finished after: " + duration + " sec");

		// SELECT
		startTime = System.nanoTime();
		for (int i = min; i <= max; i++) {
			select(i);
		}
		endTime = System.nanoTime();
		duration = (endTime - startTime) / sec;
		System.out.println("Select finished after: " + duration + " sec");

		// DELETE
		startTime = System.nanoTime();
		for (int i = min; i <= max; i++) {
			delete(i);
		}
		endTime = System.nanoTime();
		duration = (endTime - startTime) / sec;
		System.out.println("Delete finished after: " + duration + " sec");

	}

	/**
	 * initialize insert statement
	 */
	public void init_insert() {
		String insertString = "INSERT INTO Person VALUES (?, ?, ?)";

		try {
			con = ds.getConnection();
			con.setAutoCommit(true);
			insertPerson = con.prepareStatement(insertString);
		} catch (SQLException sql) {
			System.err.println("Inserting failed.  Reason: " + sql.getMessage());
		}
	}

	/**
	 * statement for delete command
	 * 
	 * @param number
	 *            row number
	 */
	public void insert(int number) {
		try {
			insertPerson.setInt(1, number);
			insertPerson.setString(2, "Max" + number);
			insertPerson.setString(3, "Mustermann" + number);
			insertPerson.executeUpdate();
		} catch (SQLException sql) {
			System.err.println("Inserting failed.  Reason: " + sql.getMessage());
		}
	}

	/**
	 * initialize select statement
	 */
	public void init_select() {
		String selectString = "SELECT * from person where nummer=?";

		try {
			con = ds.getConnection();
			con.setAutoCommit(true);
			selectPerson = con.prepareStatement(selectString);

		} catch (SQLException sql) {
			System.err.println("Selecting failed.  Reason: " + sql.getMessage());
		}
	}

	/**
	 * statement for delete command
	 * 
	 * @param number
	 *            row number
	 */
	public void select(int number) {
		try {
			selectPerson.setInt(1, number);
			ResultSet rs = selectPerson.executeQuery();

			// System.out.println("nummer\tvorname\tnachname"); // handle
			// results

			while (rs.next()) { // move cursor
				String nummer = rs.getString(1);
				String vorname = rs.getString(2);
				String nachname = rs.getString(3);
				System.out.println(nummer + "\t" + vorname + "\t" + nachname);
			}

		} catch (SQLException sql) {
			System.err.println("Selecting failed.  Reason: " + sql.getMessage());
		}
	}

	/**
	 * initialize update statement
	 */
	public void init_update() {
		String deleteString = "UPDATE Person SET vorname = ? WHERE nummer=?";

		try {
			con = ds.getConnection();
			con.setAutoCommit(true);
			updatePerson = con.prepareStatement(deleteString);
		} catch (SQLException sql) {
			System.err.println("Updating failed.  Reason: " + sql.getMessage());
		}
	}

	/**
	 * statement for delete command
	 * 
	 * @param number
	 *            row number
	 */
	public void update(int number) {
		try {
			updatePerson.setString(1, "Martin" + number);
			updatePerson.setInt(2, number);
			updatePerson.executeUpdate();
		} catch (SQLException sql) {
			System.err.println("Updating failed.  Reason: " + sql.getMessage());
		}
	}

	/**
	 * initialize delete statement
	 */
	public void init_delete() {
		String deleteString = "DELETE FROM Person WHERE nummer=?";

		try {
			con = ds.getConnection();
			con.setAutoCommit(true);
			deletePerson = con.prepareStatement(deleteString);
		} catch (SQLException sql) {
			System.err.println("Deleting failed.  Reason: " + sql.getMessage());
		}
	}

	/**
	 * statement for delete command
	 * 
	 * @param number
	 *            row number
	 */
	public void delete(int number) {
		try {
			deletePerson.setInt(1, number);
			deletePerson.executeUpdate();
		} catch (SQLException sql) {
			System.err.println("Deleting failed.  Reason: " + sql.getMessage());
		}
	}
}
