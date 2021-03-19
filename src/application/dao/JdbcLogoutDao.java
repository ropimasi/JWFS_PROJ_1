package application.dao;


import java.sql.Connection;
import application.connection.SingletonConnection;



public class JdbcLogoutDao {
	
	private static Connection conn;
	
	
	
	public JdbcLogoutDao() {
		
		conn = SingletonConnection.getConnection();
		System.out.println();
		
	}
	
	
	
	public void logoff() {
		
		try {
			
			if (conn != null) {
				if (!conn.isClosed()) conn.commit();
				SingletonConnection.disconnect();
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error: Database commit-disconection failed! -=> [" + e + "]");
		}
		
	}
	
}
