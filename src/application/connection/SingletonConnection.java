package application.connection;


import java.sql.Connection;
import java.sql.DriverManager;




public class SingletonConnection { // PostgreSQL-12 Porta 5432. // PostgreSQL-13 Porta 5433.
	private static String urlDB =
			"jdbc:postgresql://localhost:5432/jwfs_proj_1_db?createDatabaseIfNotExist=true&autoReconnect=true&useTimezone=true&serverTimezone=UTC";
	
	
	// &useSSL=false
	private static String userDB = "postgres";
	private static String passwordDB = "postgres";
	private static Connection conn = null;
	
	// Before Construct Statements.
	static {
		connect();
	}
	
	
	
	// Constructor.
	public SingletonConnection() {
		connect();
	}
	
	
	
	public static Connection getConnection() {
		connect();
		return conn;
	}
	
	
	
	public static void connect() {
		
		try {
			
			if ((conn == null) || (conn.isClosed())) {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection(urlDB, userDB, passwordDB);
				conn.setAutoCommit(false);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Oops, the data base connection failed: [" + e + "]");
		}
		
	}
	
	
	
	public static void disconnect() {
		
		try {
			
			if (conn != null) {
				if (!conn.isClosed()) conn.close();
				conn = null;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("DEBUG: Hum?! Fail on databse disconnection... [" + e + "]");
		}
		
	}
}
