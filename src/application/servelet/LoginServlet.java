package application.servelet;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import application.dao.JdbcLoginDao;
import application.entity.dto.UserCompactDTO;



@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	public static JdbcLoginDao loginDao = new JdbcLoginDao();
	
	
	
	// Constructor.
	public LoginServlet() {
		
		super();
		
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);
		
	}
	
	/* LEGENDA:
	 * lucdfsa = A var - loggedUserCompactDTOFromSessionAttribute
	 * lucdsa - A session attribute - loggedUserCompactDTOSessionAttribute
	 * luifp = A var - loggedUsesrIdFromParam */
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Didatico: Bloco response.getWriter().append(alguma_coisa); -=> adiciona no final do
		// fluxo de dados da response, ou seja, vai para página html.
		
		HttpSession loggedSession = request.getSession();
		UserCompactDTO acceptedUser =
				loginDao.validateLogin(request.getParameter("usuario"), request.getParameter("senha"));
		
		if (acceptedUser != null) {
			loggedSession.setAttribute("lucdsa", acceptedUser);
			RequestDispatcher reqDisp = request.getRequestDispatcher(
					"menuDashboard.jsp?loggedUserIdParam="
							+ ((UserCompactDTO) loggedSession.getAttribute("lucdsa")).getId());
			reqDisp.forward(request, response);
		}
		else {
			request.getSession().setAttribute("lucdsa", "User logging fails!");
			RequestDispatcher reqDisp = request.getRequestDispatcher("accessDenied.jsp");
			reqDisp.forward(request, response);
		}
		
	}
	
}
