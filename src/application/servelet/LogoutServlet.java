package application.servelet;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import application.dao.JdbcLogoutDao;



@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	public static JdbcLogoutDao logoutDao = new JdbcLogoutDao();
	
	
	
	// Constructor.
	public LogoutServlet() {
		
		super();
		
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);
		
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/* Pega sessão atual e informações do usuário logado, para validação e segurança do fluxo de acesso. */
		HttpSession loggedSession = request.getSession();
		loggedSession.setAttribute("loggedUser", "");
		loggedSession.removeAttribute("loggedUser");
		// Talvez registrar um log de saída do sistema deste usuário.
		logoutDao.logoff();
		
		// Dispatcher.
		RequestDispatcher reqDisp = request.getRequestDispatcher("loginPage.jsp");
		reqDisp.forward(request, response);
		
	}
	
}
