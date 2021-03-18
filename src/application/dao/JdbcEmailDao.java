package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.connection.SingletonConnection;
import application.entity.EmailEntity;
import application.entity.UserEntity;

public class JdbcEmailDao {
	private Connection conn;

	public JdbcEmailDao() {
		conn = SingletonConnection.getConnection();
	}

	public List<EmailEntity> list() {
		List<EmailEntity> returnList = new ArrayList<EmailEntity>();
		String sqlList = "SELECT * FROM user_emails ORDER BY e_address ASC;";
		ResultSet rsList = null;

		try {
			PreparedStatement sttmntList = conn.prepareStatement(sqlList);
			rsList = sttmntList.executeQuery();

			while (rsList.next()) {
				var tmpEmail = new EmailEntity();
				tmpEmail.setId(rsList.getLong("id"));
				tmpEmail.setEAddress(rsList.getString("e_address"));
				tmpEmail.setUserId(rsList.getLong("user_id"));
				returnList.add(tmpEmail);
			}

		} catch (SQLException sqlExcep) {
			sqlExcep.printStackTrace();
			return null;
		} catch (Exception allExcep) {
			allExcep.printStackTrace();
			return null;
		}

		return returnList;
	}

	public List<EmailEntity> listByUser(UserEntity user) {
		List<EmailEntity> returnList = new ArrayList<EmailEntity>();
		String sqlList = "SELECT * FROM user_emails WHERE (user_id = ?) ORDER BY e_address ASC;";
		ResultSet rsList = null;

		try {
			PreparedStatement sttmntList = conn.prepareStatement(sqlList);
			sttmntList.setLong(1, user.getId());
			rsList = sttmntList.executeQuery();

			while (rsList.next()) {
				EmailEntity tmpEmail = new EmailEntity();
				tmpEmail.setId(rsList.getLong("id"));
				tmpEmail.setEAddress(rsList.getString("e_address"));
				tmpEmail.setUserId(rsList.getLong("user_id"));
				returnList.add(tmpEmail);
			}

		} catch (SQLException sqlExcep) {
			sqlExcep.printStackTrace();
			return null;
		} catch (Exception allExcep) {
			allExcep.printStackTrace();
			return null;
		}

		return returnList;
	}

	public EmailEntity seekEmail(String soughtEmail) {
		String sqlSeekEmail = "SELECT * FROM user_levels WHERE (e_address = '" + soughtEmail + "');";
		ResultSet rsSeekEmail = null;

		try {
			PreparedStatement sttmntSeek = conn.prepareStatement(sqlSeekEmail);
			rsSeekEmail = sttmntSeek.executeQuery();

			var returnEmail = new EmailEntity();

			if (rsSeekEmail.next()) {
				returnEmail.setId(rsSeekEmail.getLong("id"));
				returnEmail.setEAddress(rsSeekEmail.getString("e_address"));
				returnEmail.setUserId(rsSeekEmail.getString("user_id"));
				return returnEmail;
			} else {
				return null;
			}

		} catch (SQLException sqlExcep) {
			sqlExcep.printStackTrace();
			return null;
		} catch (Exception allExcep) {
			allExcep.printStackTrace();
			return null;
		}

	}

	public EmailEntity seekId(long id) {
		String sqlSeekId = "SELECT * FROM user_emails WHERE (id = " + id + ");";
		ResultSet rsSeekId = null;

		try {
			PreparedStatement sttmntSeek = conn.prepareStatement(sqlSeekId);
			rsSeekId = sttmntSeek.executeQuery();

			var returnEmail = new EmailEntity();

			if (rsSeekId.next()) {
				returnEmail.setId(rsSeekId.getLong("id"));
				returnEmail.setEAddress(rsSeekId.getString("e_address"));
				returnEmail.setUserId(rsSeekId.getLong("user_id"));
				return returnEmail;
			} else {
				return null;
			}

		} catch (SQLException sqlExcep) {
		}

		return null;
	}

	public void save(EmailEntity email) {

		/*
		 * According 'View-layer' sent inputs, the servlet sends the 'email' object as
		 * argument, witch will always be '0' in 'id' attribute for 'add new'
		 * registering, and will be some 'id' value else '0' when in 'editing' saving.
		 */
		if (email.getId() == 0) {
			insert(email); // insert new user.
		} else {
			update(email); // update existing user.
		}

	}

	public void insert(EmailEntity email) {
		String sqlInsert = "INSERT INTO user_emails (e_address, user_id) VALUES (?, ?);";

		try {
			PreparedStatement sttmntInsert = conn.prepareStatement(sqlInsert);
			sttmntInsert.setString(1, email.getEAddress());
			sttmntInsert.setLong(2, email.getUserId());
			sttmntInsert.execute();
			conn.commit();
		} catch (SQLException e) {
			System.out.println("Error in JdbcEmailDao.Insert() > [PreparedStatement];");

			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Error in JdbcEmailDao.Insert() > [PreparedStatement]-catch > conn.rollback;");
				e1.printStackTrace();
			}

			e.printStackTrace();
		}

	}

	public void update(EmailEntity email) {
		String sqlUpdate = "UPDATE user_emails SET e_address = ?, user_id = ? WHERE (id = ?);";

		try {
			PreparedStatement sttmntUpdate = conn.prepareStatement(sqlUpdate);
			sttmntUpdate.setString(1, email.getEAddress());
			sttmntUpdate.setLong(2, email.getUserId());
			sttmntUpdate.setLong(3, email.getId());
			sttmntUpdate.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			System.out.println("Error in JdbcUserEmailsDao.update() > [PreparedStatement];");

			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Error in JdbcUserEmailsDao.update() > [PreparedStatement]-catch > conn.rollback;");
				e1.printStackTrace();
			}

			e.printStackTrace();
		}

	}

	public void excludeByAddress(String e_adress) {
		String sqlExclude = "DELETE FROM user_emails WHERE (e_address = ?);";

		try {
			PreparedStatement sttmntExclude = conn.prepareStatement(sqlExclude);
			sttmntExclude.setString(1, e_adress);
			sttmntExclude.execute();
			conn.commit();
		} catch (SQLException e) {
			System.out.println("Error in JdbcEmailDao.excludeByAddress() > [PreparedStatement];");

			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println(
						"Error in JdbcEmailDao.excludeByAddress() > [PreparedStatement]-catch > conn.rollback;");
				e1.printStackTrace();
			}

			e.printStackTrace();
		}

	}

	public void excludeById(Long id) {
		String sqlExclude = "DELETE FROM user_emails WHERE (id = ?);";

		try {
			PreparedStatement sttmntExclude = conn.prepareStatement(sqlExclude);
			sttmntExclude.setString(1, id.toString());
			sttmntExclude.execute();
			conn.commit();
		} catch (SQLException e) {
			System.out.println("Error in JdbcEmailDao.excludeById() > [PreparedStatement];");

			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Error in JdbcEmailDao.excludeById() > [PreparedStatement]-catch > conn.rollback;");
				e1.printStackTrace();
			}

			e.printStackTrace();
		}

	}
}
