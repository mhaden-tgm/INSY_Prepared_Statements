import java.sql.*;
import org.postgresql.ds.*;

/*
 * @author mhaden
 * @date 20.02.2016
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
	private static String ip_adress = "192.168.110.135";
	private static String port = "5432";
	private static String database = "schokofabrik";
	private static String user = "schokouser";
	private static String password = "schokouser";

	public static void main(String[] args) {
		// Datenquelle erzeugen und konfigurieren
		PGSimpleDataSource ds = new PGSimpleDataSource();
		// Server IP - Adresse
		ds.setServerName(ip_adress + ":" + port);
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