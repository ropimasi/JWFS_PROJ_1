package application.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import application.connection.SingletonConnection;
import application.entity.UserEntity;
import application.service.SymmCrypSamp;



public class JdbcUserDao {
	
	private static Connection conn;
	
	
	
	public JdbcUserDao() {
		
		conn = SingletonConnection.getConnection();
		System.out.println();
		
	}
	
	
	
	public List<UserEntity> list() {
		
		List<UserEntity> returnList = new ArrayList<UserEntity>();
		String sqlList = "SELECT * FROM users ORDER BY id ASC;";
		ResultSet rsList = null;
		
		try {
			PreparedStatement sttmntList = conn.prepareStatement(sqlList);
			rsList = sttmntList.executeQuery();
			
			while (rsList.next()) {
				UserEntity tmpUser = new UserEntity();
				tmpUser.setId(rsList.getString("id"));
				tmpUser.setFullName(rsList.getString("full_name"));
				tmpUser.setLoginName(rsList.getString("login_name"));
				tmpUser.setLoginPassword(rsList.getString("login_password"));
				tmpUser.setLevel(rsList.getString("level"));
				/* Further improvement: Entity needs architecture review for 'addresses'. */
				tmpUser.setAddrPostalCode(rsList.getString("addr_postal_code"));
				tmpUser.setAddrFu(rsList.getString("addr_fu"));
				tmpUser.setAddrCity(rsList.getString("addr_city"));
				tmpUser.setAddrNeighborhood(rsList.getString("addr_neighborhood"));
				tmpUser.setAddrVia(rsList.getString("addr_via"));
				tmpUser.setAddrNumber(rsList.getString("addr_number"));
				tmpUser.setAddrComplement(rsList.getString("addr_complement"));
				tmpUser.setPictureBase64(rsList.getString("picture_base64"));
				tmpUser.setPictureContentType(rsList.getString("picture_content_type"));
				returnList.add(tmpUser);
			}
			
		}
		catch (SQLException sqlExcep) {
			sqlExcep.printStackTrace();
			return null;
		}
		catch (Exception allExcep) {
			allExcep.printStackTrace();
			return null;
		}
		
		return returnList;
		
	}
	
	
	
	public UserEntity seekId(long id) {
		
		String sqlSeekId = "SELECT * FROM users WHERE (id = " + id + ");";
		ResultSet rsSeekId = null;
		
		try {
			PreparedStatement sttmntSeek = conn.prepareStatement(sqlSeekId);
			rsSeekId = sttmntSeek.executeQuery();
			
			if (rsSeekId.next()) {
				UserEntity returnUser = new UserEntity();
				returnUser.setId(rsSeekId.getLong("id"));
				returnUser.setFullName(rsSeekId.getString("full_name"));
				returnUser.setLoginName(rsSeekId.getString("login_name"));
				returnUser.setLoginPassword(rsSeekId.getString("login_password"));
				returnUser.setLevel(rsSeekId.getString("level"));
				/* Further improvement: Entity needs architecture review for 'addresses'. */
				returnUser.setAddrPostalCode(rsSeekId.getString("addr_postal_code"));
				returnUser.setAddrFu(rsSeekId.getString("addr_fu"));
				returnUser.setAddrCity(rsSeekId.getString("addr_city"));
				returnUser.setAddrNeighborhood(rsSeekId.getString("addr_neighborhood"));
				returnUser.setAddrVia(rsSeekId.getString("addr_via"));
				returnUser.setAddrNumber(rsSeekId.getString("addr_number"));
				returnUser.setAddrComplement(rsSeekId.getString("addr_complement"));
				returnUser.setPictureBase64(rsSeekId.getString("picture_base64"));
				returnUser.setPictureContentType(rsSeekId.getString("picture_content_type"));
				return returnUser;
			}
			
		}
		catch (SQLException sqlExcep) {
			sqlExcep.printStackTrace();
			return null;
		}
		catch (Exception allExcep) {
			allExcep.printStackTrace();
			return null;
		}
		
		return null;
		
	}
	
	
	
	public UserEntity seekUserName(String soughtUserName) {
		
		String sqlSeekUserName = "SELECT * FROM users WHERE (user_name = '" + soughtUserName + "');";
		ResultSet rsSeekUserName = null;
		
		try {
			PreparedStatement sttmntList = conn.prepareStatement(sqlSeekUserName);
			rsSeekUserName = sttmntList.executeQuery();
			
			if (rsSeekUserName.next()) {
				UserEntity returnUser = new UserEntity();
				returnUser.setId(rsSeekUserName.getLong("id"));
				returnUser.setFullName(rsSeekUserName.getString("full_name"));
				returnUser.setLoginName(rsSeekUserName.getString("login_name"));
				returnUser.setLoginPassword(rsSeekUserName.getString("login_password"));
				returnUser.setLevel(rsSeekUserName.getString("level"));
				/* Further improvement: Entity needs architecture review for 'addresses'. */
				returnUser.setAddrPostalCode(rsSeekUserName.getString("addr_postal_code"));
				returnUser.setAddrFu(rsSeekUserName.getString("addr_fu"));
				returnUser.setAddrCity(rsSeekUserName.getString("addr_city"));
				returnUser.setAddrNeighborhood(rsSeekUserName.getString("addr_neighborhood"));
				returnUser.setAddrVia(rsSeekUserName.getString("addr_via"));
				returnUser.setAddrNumber(rsSeekUserName.getString("addr_number"));
				returnUser.setAddrComplement(rsSeekUserName.getString("addr_complement"));
				returnUser.setPictureBase64(rsSeekUserName.getString("picture_base64"));
				returnUser.setPictureContentType(rsSeekUserName.getString("picture_content_type"));
				return returnUser;
			}
			else {
				return null;
			}
			
		}
		catch (SQLException sqlExcep) {
			sqlExcep.printStackTrace();
			return null;
		}
		catch (Exception allExcep) {
			allExcep.printStackTrace();
			return null;
		}
		
	}
	
	
	
	public void save(UserEntity user) {
		
		if (user.getId() == 0) {
			insert(user); // insert new user.
		}
		else {
			update(user); // update existing user.
		}
		
	}
	
	
	
	public void insert(UserEntity user) {
		
		String sqlInsert = "INSERT INTO users (full_name, login_name, login_password, level, "
				+ "addr_postal_code, addr_fu, addr_city, addr_neighborhood, addr_via, addr_number, "
				+ "addr_complement, picture_base64, picture_content_type) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		try {
			PreparedStatement sttmntInsert = conn.prepareStatement(sqlInsert);
			sttmntInsert.setString(1, user.getFullName());
			sttmntInsert.setString(2, user.getLoginName());
			sttmntInsert.setString(3, SymmCrypSamp.doIt(user.getLoginPassword()));
			sttmntInsert.setString(4, user.getLevel());
			/* Further improvement: Entity needs architecture review for 'addresses'. */
			sttmntInsert.setString(5, user.getAddrPostalCode());
			sttmntInsert.setString(6, user.getAddrFu());
			sttmntInsert.setString(7, user.getAddrCity());
			sttmntInsert.setString(8, user.getAddrNeighborhood());
			sttmntInsert.setString(9, user.getAddrVia());
			sttmntInsert.setString(10, user.getAddrNumber());
			sttmntInsert.setString(11, user.getAddrComplement());
			
			sttmntInsert.setString(12, user.getPictureBase64String());
			sttmntInsert.setString(13, user.getPictureContentType());
			
			sttmntInsert.execute();
			conn.commit();
		}
		catch (SQLException e) {
			System.out.println("Error in JdbcUserDao.Insert() > [PreparedStatement];");
			e.printStackTrace();
			
			try {
				conn.rollback();
			}
			catch (SQLException e1) {
				System.out.println("Error in JdbcUserDao.Insert() > [PreparedStatement]-catch > conn.rollback;");
				e1.printStackTrace();
			}
			
		}
		
	}
	
	
	
	public void update(UserEntity user) {
		
		String sqlUpdate =
				"UPDATE users SET full_name = ?, login_name = ?, login_password = ?, level = ?, addr_postal_code = ?"
						+ ", addr_fu = ?, addr_city = ?, addr_neighborhood = ?, addr_via = ?, addr_number = ?"
						+ ", addr_complement = ?, picture_base64 = ?, picture_content_type = ?"
						+ "WHERE (id = ?);";
		
		try {
			PreparedStatement sttmntUpdate = conn.prepareStatement(sqlUpdate);
			sttmntUpdate.setString(1, user.getFullName());
			sttmntUpdate.setString(2, user.getLoginName());
			sttmntUpdate.setString(3, SymmCrypSamp.doIt(user.getLoginPassword()));
			sttmntUpdate.setString(4, user.getLevel());
			/* Further improvement: Entity needs architecture review for 'addresses'. */
			sttmntUpdate.setString(5, user.getAddrPostalCode());
			sttmntUpdate.setString(6, user.getAddrFu());
			sttmntUpdate.setString(7, user.getAddrCity());
			sttmntUpdate.setString(8, user.getAddrNeighborhood());
			sttmntUpdate.setString(9, user.getAddrVia());
			sttmntUpdate.setString(10, user.getAddrNumber());
			sttmntUpdate.setString(11, user.getAddrComplement());
			
			sttmntUpdate.setString(12, user.getPictureBase64String());
			sttmntUpdate.setString(13, user.getPictureContentType());
			
			// O último é o '? parameter' da clausula 'where'.
			sttmntUpdate.setLong(14, user.getId());
			sttmntUpdate.executeUpdate();
			conn.commit();
		}
		catch (SQLException e) {
			System.out.println("Error in JdbcUserDao.update() > [PreparedStatement];");
			
			try {
				conn.rollback();
			}
			catch (SQLException e1) {
				System.out.println("Error in JdbcUserDao.update() > [PreparedStatement]-catch > conn.rollback;");
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
	}
	
	
	
	public void exclude(long id) {
		
		String sqlExclude = "DELETE FROM users WHERE (id = ?);";
		
		try {
			PreparedStatement sttmntExclude = conn.prepareStatement(sqlExclude);
			sttmntExclude.setLong(1, id);
			sttmntExclude.execute();
			conn.commit();
		}
		catch (SQLException e) {
			System.out.println("Error in JdbcUserDao.exclude() > [PreparedStatement];");
			
			try {
				conn.rollback();
			}
			catch (SQLException e1) {
				System.out.println("Error in JdbcUserDao.exclude() > [PreparedStatement]-catch > conn.rollback;");
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
	}
	
}
