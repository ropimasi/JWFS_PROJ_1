package application.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import application.connection.SingletonConnection;
import application.entity.dto.UserCompactDTO;
import application.service.SymmCrypSamp;



public class JdbcLoginDao {
	
	private Connection conn;
	
	
	
	public JdbcLoginDao() {
		
		conn = SingletonConnection.getConnection();
		
	}
	
	
	
	public UserCompactDTO validateLogin(String loginName, String loginPassword) {
		/* Surely, it's a very simple, rudimentary way of implementation of
		 * authentication without cryptography. It's a basic programming propose,
		 * focused on connection. */
		
		UserCompactDTO returnUser = new UserCompactDTO();
		ResultSet rs = null;
		/* This 'ResultSet rs' declared outside and before the 'try-catch' block to be
		 * sure it'll exist in both attempts (both 'try-catch' blocks). */
		
		try {
			
			if ((conn == null) || (conn.isClosed())) {
				conn = SingletonConnection.getConnection();
				System.out.println();
			}
			
			String sqlSeekUser = "SELECT * FROM users WHERE (login_name = '" + loginName + "') AND (login_password = '" + loginPassword + "')";  /* SymmCrypSamp.doIt(userPassword) */
			PreparedStatement sttmnt = conn.prepareStatement(sqlSeekUser);
			rs = sttmnt.executeQuery();
		}
		catch (Exception errSqlSeekUserQuery) {
			errSqlSeekUserQuery.printStackTrace();
			return null;
		}
		
		try {
			
			if (rs.next()) {
				returnUser.setId(rs.getLong("id"));
				returnUser.setFullName(rs.getString("full_name"));
				returnUser.setLoginName(rs.getString("login_name"));
				returnUser.setLevel(rs.getString("level"));
				returnUser.setPictureBase64(rs.getString("picture_base64"));
				returnUser.setPictureBase64(rs.getString("picture_content_type"));
				return returnUser;
			}
			else {
				return null;
			}
			
		}
		catch (Exception errNextSeekUserQuery) {
			errNextSeekUserQuery.printStackTrace();
			return null;
		}
		
	}
	
}
