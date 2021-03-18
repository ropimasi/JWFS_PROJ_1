package application.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import application.connection.SingletonConnection;




public class JdbcUserLevelDao {
	private Connection conn;
	
	
	
	public JdbcUserLevelDao() {
		conn = SingletonConnection.getConnection();
	}
	
	
	
	public List<String> list() {
		List<String> returnList = new ArrayList<String>();
		String sqlList = "SELECT * FROM user_levels ORDER BY name ASC;";
		ResultSet rsList = null;
		
		try {
			PreparedStatement sttmntList = conn.prepareStatement(sqlList);
			rsList = sttmntList.executeQuery();
			
			var tmpUserLevel = new String();
			
			while (rsList.next()) {
				tmpUserLevel = rsList.getString("name");
				returnList.add(tmpUserLevel);
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
	
	
	
	public String seekName(String soughtName) {
		String sqlSeekName = "SELECT * FROM user_levels WHERE (name = '" + soughtName + "');";
		ResultSet rsSeekName = null;
		
		try {
			PreparedStatement sttmntList = conn.prepareStatement(sqlSeekName);
			rsSeekName = sttmntList.executeQuery();
			
			if (rsSeekName.next()) {
				String returnUserLevel = new String();
				returnUserLevel = rsSeekName.getString("name");
				return returnUserLevel;
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
	
	
	
	public void insert(String userLevel) {
		/* This algorithm doesn't protect against attempt to insert a duplicated 'UserLevel.name',
		 * although the database project do protect it through PK and UNIQUEs constraints usage.
		 * Therefore the current method causes less appreciated UX I'll let that way as it is, to see
		 * some error possible messages and think about further improvement. */
		String sqlInsert = "INSERT INTO user_levels (name) VALUES (?);";
		
		try {
			PreparedStatement sttmntInsert = conn.prepareStatement(sqlInsert);
			sttmntInsert.setString(1, userLevel);
			sttmntInsert.execute();
			conn.commit();
		}
		catch (SQLException e) {
			System.out.println("Erro em JdbcUserLevelDao.Insert() > [PreparedStatement];");
			
			try {
				conn.rollback();
			}
			catch (SQLException e1) {
				System.out.println("Erro em JdbcUserLevelDao.Insert() > [PreparedStatement]-catch > conn.rollback;");
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
	}
	
	
	
	public void update(String userLevel, String oldName) {
		/* This algorithm doesn't protect against attempt to insert a duplicated 'UserLevel.name',
		 * although the database project do protect it through PK and UNIQUEs constraints usage.
		 * Therefore the current method causes less appreciated UX I'll let that way as it is, to see
		 * some error possible messages and think about further improvement. */
		String sqlUpdate = "UPDATE user_levels SET name = ? WHERE (name = ?);";
		
		try {
			PreparedStatement sttmntUpdate = conn.prepareStatement(sqlUpdate);
			sttmntUpdate.setString(1, userLevel);
			sttmntUpdate.setString(2, oldName);
			sttmntUpdate.executeUpdate();
			conn.commit();
		}
		catch (SQLException e) {
			System.out.println("Error in JdbcUserLevelDao.update() > [PreparedStatement];");
			
			try {
				conn.rollback();
			}
			catch (SQLException e1) {
				System.out.println("Error in JdbcUserLevelDao.update() > [PreparedStatement]-catch > conn.rollback;");
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
	}
	
	
	
	public void exclude(String name) {
		String sqlExclude = "DELETE FROM user_levels WHERE (name = ?);";
		
		try {
			PreparedStatement sttmntExclude = conn.prepareStatement(sqlExclude);
			sttmntExclude.setString(1, name);
			sttmntExclude.execute();
			conn.commit();
		}
		catch (SQLException e) {
			System.out.println("Error in JdbcUserLevelDao.exclude() > [PreparedStatement];");
			
			try {
				conn.rollback();
			}
			catch (SQLException e1) {
				System.out.println("Error in JdbcUserLevelDao.exclude() > [PreparedStatement]-catch > conn.rollback;");
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
	}
}
