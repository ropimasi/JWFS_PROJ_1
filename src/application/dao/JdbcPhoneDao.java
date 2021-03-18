package application.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.connection.SingletonConnection;
import application.entity.PhoneEntity;
import application.entity.PhoneTypeEnum;
import application.entity.UserEntity;




public class JdbcPhoneDao {
	private Connection conn;
	
	
	
	public JdbcPhoneDao() {
		conn = SingletonConnection.getConnection();
	}
	
	
	
	public List<PhoneEntity> list() {
		List<PhoneEntity> returnList = new ArrayList<PhoneEntity>();
		String sqlList = "SELECT * FROM phones ORDER BY id ASC;";
		ResultSet rsList = null;
		
		try {
			PreparedStatement sttmntList = conn.prepareStatement(sqlList);
			rsList = sttmntList.executeQuery();
			
			while (rsList.next()) {
				PhoneEntity tmpPhone = new PhoneEntity();
				tmpPhone.setId(rsList.getLong("id"));
				tmpPhone.setCountryCode(rsList.getString("country_code"));
				tmpPhone.setAreaCode(rsList.getString("area_code"));
				tmpPhone.setNumber(rsList.getString("number"));
				tmpPhone.setType(PhoneTypeEnum.valueOf(rsList.getString("type")));
				tmpPhone.setUserId(rsList.getLong("user_id"));
				returnList.add(tmpPhone);
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
	
	
	
	public List<PhoneEntity> listByUser(UserEntity user) {
		List<PhoneEntity> returnList = new ArrayList<PhoneEntity>();
		String sqlList = "SELECT * FROM phones WHERE (user_id = ?) ORDER BY area_code ASC;";
		ResultSet rsList = null;
		
		try {
			PreparedStatement sttmntList = conn.prepareStatement(sqlList);
			sttmntList.setLong(1, user.getId());
			rsList = sttmntList.executeQuery();
			
			while (rsList.next()) {
				PhoneEntity tmpPhone = new PhoneEntity();
				tmpPhone.setId(rsList.getLong("id"));
				tmpPhone.setCountryCode(rsList.getString("country_code"));
				tmpPhone.setAreaCode(rsList.getString("area_code"));
				tmpPhone.setNumber(rsList.getString("number"));
				tmpPhone.setType(PhoneTypeEnum.valueOf(rsList.getString("type")));
				tmpPhone.setUserId(rsList.getLong("user_id"));
				returnList.add(tmpPhone);
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
	
	
	
	public List<PhoneEntity> listByUserId(long userId) {
		List<PhoneEntity> returnList = new ArrayList<PhoneEntity>();
		String sqlList = "SELECT * FROM phones WHERE (user_id = ?) ORDER BY area_code ASC;";
		ResultSet rsList = null;
		
		try {
			PreparedStatement sttmntList = conn.prepareStatement(sqlList);
			sttmntList.setLong(1, userId);
			rsList = sttmntList.executeQuery();
			
			while (rsList.next()) {
				PhoneEntity tmpPhone = new PhoneEntity();
				tmpPhone.setId(rsList.getLong("id"));
				tmpPhone.setCountryCode(rsList.getString("country_code"));
				tmpPhone.setAreaCode(rsList.getString("area_code"));
				tmpPhone.setNumber(rsList.getString("number"));
				tmpPhone.setType(PhoneTypeEnum.valueOf(rsList.getString("type")));
				tmpPhone.setUserId(rsList.getLong("user_id"));
				returnList.add(tmpPhone);
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
	
	
	
	public PhoneEntity seekId(long id) {
		String sqlSeekId = "SELECT * FROM phones WHERE (id = " + id + ");";
		ResultSet rsSeekId = null;
		
		try {
			PreparedStatement sttmntList = conn.prepareStatement(sqlSeekId);
			rsSeekId = sttmntList.executeQuery();
			
			if (rsSeekId.next()) {
				PhoneEntity returnPhone = new PhoneEntity();
				returnPhone.setId(rsSeekId.getLong("id"));
				returnPhone.setCountryCode(rsSeekId.getString("country_code"));
				returnPhone.setAreaCode(rsSeekId.getString("area_code"));
				returnPhone.setNumber(rsSeekId.getString("number"));
				returnPhone.setType(PhoneTypeEnum.valueOf(rsSeekId.getString("type")));
				returnPhone.setUserId(rsSeekId.getLong("user_id"));
				return returnPhone;
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
	
	
	
	public PhoneEntity seekNumber(String soughtNumber) {
		String sqlSeekNumber = "SELECT * FROM phones WHERE (number = '" + soughtNumber + "');";
		ResultSet rsSeekNumber = null;
		
		try {
			PreparedStatement sttmntList = conn.prepareStatement(sqlSeekNumber);
			rsSeekNumber = sttmntList.executeQuery();
			
			if (rsSeekNumber.next()) {
				PhoneEntity returnPhone = new PhoneEntity();
				returnPhone.setId(rsSeekNumber.getLong("id"));
				returnPhone.setCountryCode(rsSeekNumber.getString("country_code"));
				returnPhone.setAreaCode(rsSeekNumber.getString("area_code"));
				returnPhone.setNumber(rsSeekNumber.getString("number"));
				returnPhone.setType(PhoneTypeEnum.valueOf(rsSeekNumber.getString("type")));
				returnPhone.setUserId(rsSeekNumber.getLong("user_id"));
				return returnPhone;
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
	
	
	
	public void save(PhoneEntity phone) {
		
		/* According 'View-layer' sent inputs, the servlet sends the 'phone' object as
		 * argument, witch will always be '0' in 'id' attribute for 'add new'
		 * registering, and will be some 'id' value else '0' when in 'editing' saving. */
		if (phone.getId() == 0) {
			insert(phone); // insert new user.
		}
		else {
			update(phone); // update existing user.
		}
		
	}
	
	
	
	public void insert(PhoneEntity phone) {
		String sqlInsert =
				"INSERT INTO phones (country_code, area_code, number, type, user_id) VALUES (?, ?, ?, ?, ?);";
		
		try {
			PreparedStatement sttmntInsert = conn.prepareStatement(sqlInsert);
			sttmntInsert.setString(1, phone.getCountryCode());
			sttmntInsert.setString(2, phone.getAreaCode());
			sttmntInsert.setString(3, phone.getNumber());
			sttmntInsert.setString(4, phone.getType().toString());
			sttmntInsert.setLong(5, phone.getUserId());
			sttmntInsert.execute();
			conn.commit();
		}
		catch (SQLException e) {
			System.out.println("Error in JdbcPhoneDao.Insert() > [PreparedStatement];");
			
			try {
				conn.rollback();
			}
			catch (SQLException e1) {
				System.out.println("Error in JdbcPhoneDao.Insert() > [PreparedStatement]-catch > conn.rollback;");
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
	}
	
	
	
	public void update(PhoneEntity phone) {
		String sqlUpdate =
				"UPDATE phones SET country_code = ?, area_code = ?, number = ?, type = ?, user_id = ? WHERE (id = ?);";
		
		try {
			PreparedStatement sttmntUpdate = conn.prepareStatement(sqlUpdate);
			sttmntUpdate.setString(1, phone.getCountryCode());
			sttmntUpdate.setString(2, phone.getAreaCode());
			sttmntUpdate.setString(3, phone.getNumber());
			sttmntUpdate.setString(4, phone.getType().toString());
			sttmntUpdate.setLong(5, phone.getUserId());
			sttmntUpdate.setLong(6, phone.getId());
			sttmntUpdate.executeUpdate();
			conn.commit();
		}
		catch (SQLException e) {
			System.out.println("Error in JdbcPhonesDao.update() > [PreparedStatement];");
			
			try {
				conn.rollback();
			}
			catch (SQLException e1) {
				System.out.println("Error in JdbcPhonesDao.update() > [PreparedStatement]-catch > conn.rollback;");
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
	}
	
	
	
	public void exclude(long id) {
		String sqlExclude = "DELETE FROM phones WHERE (id = ?);";
		
		try {
			PreparedStatement sttmntExclude = conn.prepareStatement(sqlExclude);
			sttmntExclude.setLong(1, id);
			sttmntExclude.execute();
			conn.commit();
		}
		catch (SQLException e) {
			System.out.println("Error in JdbcPhoneDao.exclude() > [PreparedStatement];");
			
			try {
				conn.rollback();
			}
			catch (SQLException e1) {
				System.out.println("Error in JdbcPhoneDao.exclude() > [PreparedStatement]-catch > conn.rollback;");
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
	}
}
