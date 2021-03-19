package application.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import application.connection.SingletonConnection;
import application.entity.ProductEntity;



public class JdbcProductDao {
	
	private static Connection conn;
	
	
	
	public JdbcProductDao() {
		
		conn = SingletonConnection.getConnection();
		System.out.println();
		
	}
	
	
	
	public List<ProductEntity> list() {
		
		List<ProductEntity> returnList = new ArrayList<ProductEntity>();
		String sqlList = "SELECT * FROM products ORDER BY id ASC;";
		ResultSet rsList = null;
		
		try {
			PreparedStatement sttmntList = conn.prepareStatement(sqlList);
			rsList = sttmntList.executeQuery();
			
			while (rsList.next()) {
				ProductEntity tmpProduct = new ProductEntity();
				tmpProduct.setId(rsList.getString("id"));
				tmpProduct.setName(rsList.getString("name"));
				tmpProduct.setPurchasePrice(rsList.getBigDecimal("purchase_price"));
				tmpProduct.setSalePrice(rsList.getBigDecimal("sale_price"));
				tmpProduct.setStockQtty(rsList.getFloat("stock_qtty"));
				returnList.add(tmpProduct);
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
	
	
	
	public ProductEntity seekId(long id) {
		
		String sqlSeekId = "SELECT * FROM products WHERE (id = " + id + ");";
		ResultSet rsSeekId = null;
		
		try {
			PreparedStatement sttmntSeek = conn.prepareStatement(sqlSeekId);
			rsSeekId = sttmntSeek.executeQuery();
			
			if (rsSeekId.next()) {
				ProductEntity returnProduct = new ProductEntity();
				returnProduct.setId(rsSeekId.getLong("id"));
				returnProduct.setName(rsSeekId.getString("name"));
				returnProduct.setPurchasePrice(rsSeekId.getBigDecimal("purchase_price"));
				returnProduct.setSalePrice(rsSeekId.getBigDecimal("sale_price"));
				returnProduct.setStockQtty(rsSeekId.getFloat("stock_qtty"));
				return returnProduct;
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
	
	
	
	public ProductEntity seekProductName(String soughtProductName) {
		
		String sqlSeekProductName = "SELECT * FROM products WHERE (name = '" + soughtProductName + "');";
		ResultSet rsSeekProductName = null;
		
		try {
			PreparedStatement sttmntSeek = conn.prepareStatement(sqlSeekProductName);
			rsSeekProductName = sttmntSeek.executeQuery();
			
			if (rsSeekProductName.next()) {
				ProductEntity returnProduct = new ProductEntity();
				returnProduct.setId(rsSeekProductName.getLong("id"));
				returnProduct.setName(rsSeekProductName.getString("name"));
				returnProduct.setPurchasePrice(rsSeekProductName.getBigDecimal("purchase_price"));
				returnProduct.setSalePrice(rsSeekProductName.getBigDecimal("sale_price"));
				returnProduct.setStockQtty(rsSeekProductName.getFloat("email"));
				return returnProduct;
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
	
	
	
	public void save(ProductEntity product) {
		
		/* According 'View-layer' sent inputs, the servlet sends the 'product' object as argument,
		 * witch will always be '0' in 'id' attribute for 'add new' registering, and will be some 'id'
		 * value else '0' when in 'editing' saving. */
		if (product.getId() == 0) {
			insert(product); // insert new user.
		}
		else {
			update(product); // update existing user.
		}
		
	}
	
	
	
	public void insert(ProductEntity product) {
		
		String sqlInsert = "INSERT INTO products (name, purchase_price, sale_price, stock_qtty) VALUES (?, ?, ?, ?);";
		
		try {
			PreparedStatement sttmntInsert = conn.prepareStatement(sqlInsert);
			sttmntInsert.setString(1, product.getName());
			sttmntInsert.setBigDecimal(2, product.getPurchasePrice());
			sttmntInsert.setBigDecimal(3, product.getSalePrice());
			sttmntInsert.setFloat(4, product.getStockQtty());
			sttmntInsert.execute();
			conn.commit();
		}
		catch (SQLException e) {
			System.out.println("Error in JdbcProductDao.Insert() > [PreparedStatement];");
			
			try {
				conn.rollback();
			}
			catch (SQLException e1) {
				System.out.println("Error in JdbcProductDao.Insert() > [PreparedStatement]-catch > conn.rollback;");
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
	}
	
	
	
	public void update(ProductEntity product) {
		
		String sqlUpdate =
				"UPDATE products SET name = ?, purchase_price = ?, sale_price = ?, stock_qtty = ? WHERE (id = ?);";
		
		try {
			PreparedStatement sttmntUpdate = conn.prepareStatement(sqlUpdate);
			sttmntUpdate.setString(1, product.getName());
			sttmntUpdate.setBigDecimal(2, product.getPurchasePrice());
			sttmntUpdate.setBigDecimal(3, product.getSalePrice());
			sttmntUpdate.setFloat(4, product.getStockQtty());
			sttmntUpdate.setLong(5, product.getId());
			sttmntUpdate.executeUpdate();
			conn.commit();
		}
		catch (SQLException e) {
			System.out.println("Error in JdbcProductDao.update() > [PreparedStatement];");
			
			try {
				conn.rollback();
			}
			catch (SQLException e1) {
				System.out.println("Error in JdbcProductDao.update() > [PreparedStatement]-catch > conn.rollback;");
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
	}
	
	
	
	public void exclude(long id) {
		
		String sqlExclude = "DELETE FROM products WHERE (id = ?);";
		
		try {
			PreparedStatement sttmntExclude = conn.prepareStatement(sqlExclude);
			sttmntExclude.setLong(1, id);
			sttmntExclude.execute();
			conn.commit();
		}
		catch (SQLException e) {
			System.out.println("Error in JdbcProductDao.exclude() > [PreparedStatement];");
			
			try {
				conn.rollback();
			}
			catch (SQLException e1) {
				System.out.println("Error in JdbcProductDao.exclude() > [PreparedStatement]-catch > conn.rollback;");
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
	}
	
}
