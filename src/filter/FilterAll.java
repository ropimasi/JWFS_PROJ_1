package filter;


import java.io.IOException;
import java.sql.Connection;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import application.connection.SingletonConnection;


@WebFilter(urlPatterns = {"/*"})
public class FilterAll implements javax.servlet.Filter {
	
	private static Connection conn;
	
	
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		if (conn == null) { conn = SingletonConnection.getConnection(); }
		
	}
	
	
	
	@Override
	public void doFilter(ServletRequest sReq, ServletResponse sRes, FilterChain fChain)
			throws IOException, ServletException {
		
		try {
			fChain.doFilter(sReq, sRes);
			
			if ((conn == null) || (conn.isClosed())) { conn = SingletonConnection.getConnection(); }
			
			conn.commit();
		}
		catch (Exception e0) {
			e0.printStackTrace();
			
			try {
				conn.rollback();
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
	
}
