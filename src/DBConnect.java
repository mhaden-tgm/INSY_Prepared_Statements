import java.sql.*;
import org.postgresql.ds.PGSimpleDataSource;

/*
 * database connection and statements
 * 
 * @author mhaden
 * @date 20.02.2016
 * @version 0.5
 */

public class DBConnect {
	/**
	 * database connection
	 * 
	 * @param ip_adress server ip adress
	 * @param port_number server port number
	 * @param database database name
	 * @param user username fro login
	 * @param password password for login
	 */
	public void db_connect(String ip_adress, String port_number, String database, String user, String password) {
		// default_value();

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
}
