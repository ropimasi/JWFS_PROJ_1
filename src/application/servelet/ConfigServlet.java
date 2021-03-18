package application.servelet;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import application.entity.UserEntity;




@WebServlet("/ConfigServlet")
public class ConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	/* Constructor. */
	public ConfigServlet() {
		super();
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Logging [ConfigServlet:doPost]");
		
		/* Init RequestDispatcher before loop or try blocks, init once, use several. */
		RequestDispatcher reqDisp = null;
		/* Make sure all attributes are reset each new instance of Servlet. */
		request.setAttribute("xxxSession", null);
		// null -> app always must retrieve a free list from UserDao before send to the frontend.
		request.setAttribute("xxxUser", null);
		// null -> app not in edit mode, otherwise, contents an user (entity) to edit.
		
		/* ### INÍCIO - TÉCNICA DE ASR-URI ### */
		HttpSession loggedSession = request.getSession();
		UserEntity loggedUser = (UserEntity) loggedSession.getAttribute("loggedUser");
		long loggedUsesrIdFromParam =
				(request.getParameter("userId") != null) ? Long.parseLong(request.getParameter("userId")) : 0L;
		
		if ((loggedUser == null) || (loggedUsesrIdFromParam != loggedUser.getId())) {
			request.setAttribute("lastAction", null);
			request.setAttribute("usersList", null);
			reqDisp = request.getRequestDispatcher("resources/error-pages/asr-uri-error-page.jsp");
			reqDisp.forward(request, response);
		}
		/* ### FIM - TÉCNICA DE ASR-URI ### */
		
		/* Dependencies: Let a temp objects instantiated fot several uses below. */
		// JdbcxxxDao xxxDAO = new JdbcxxxDao();

		// FURTHER: JfscApplicationConfigDao configDao = new JfscApplicationConfigDao();
		
		/* Select and execute the procedures, in fact. */
		switch (request.getParameter("action")) {
			case "save":
				request.setAttribute("lastAction", "save");
				System.out.println("Logging [ConfigServlet:doPost:Action = " + request.getParameter("action") + "]");
				/* UserEntity userToSave = new UserEntity();
				 * userToSave.setId(request.getParameter("id"));
				 * userToSave.setFullName(request.getParameter("fullName"));
				 * userToSave.setUserName(request.getParameter("userName"));
				 * userToSave.setPassword(request.getParameter("password"));
				 * userToSave.setEmail(request.getParameter("email"));
				 * userToSave.setUserLevel(request.getParameter("userLevel"));
				 * userToSave.setAddrPostalCode(request.getParameter("addrPostalCode"));
				 * userToSave.setAddrFu(request.getParameter("addrFu"));
				 * userToSave.setAddrCity(request.getParameter("addrCity"));
				 * userToSave.setAddrNeighborhood(request.getParameter("addrNeighborhood"));
				 * userToSave.setAddrVia(request.getParameter("addrVia"));
				 * userToSave.setAddrNumber(request.getParameter("addrNumber"));
				 * userToSave.setAddrComplement(request.getParameter("addrComplement"));
				 * 
				 * /* Of course these 2 statements could be cast in 1 statement,
				 * but I need userVRS later. *//* ValidationResultSet userVRS =
												 * ValidationService.validateUserFull(userToSave);
												 * request.setAttribute("userVRS", userVRS);
												 * 
												 * if (userVRS.getVerdict()) {
												 * System.out.
												 * println("DEBUG-Logging [UserRegistryServlet:action=save:if-getVeredict-true."
												 * );
												 * userDAO.save(userToSave);
												 * } else {
												 * System.out.
												 * println("DEBUG-Logging [UserRegistryServlet:action=save:if-getVeredict-false-else."
												 * );
												 * request.setAttribute("soughtUserEdit", userToSave);
												 * } */
				break; // FIM DE SAVE.
				
			case "edit":
				request.setAttribute("lastAction", "edit");
				System.out
						.println("Logging [UserRegistryServlet:doPost:Action=" + request.getParameter("action") + "]");
				/* request.setAttribute("soughtUserEdit",
				 * userDAO.seekId(Long.parseLong(request.getParameter("id")))); */
				break;
			
			default: // By default whatever else value from 'request.getParameter("action")' we must do nothing.
				request.setAttribute("lastAction", "default");
				System.out.println("Logging [ConfigServlet:doPost:Action=" + request.getParameter("action") + "]");
				break;
		}
		
		// After whatever case from 'request.getParameter("action")' done, we must do to List.
		// FURTHER: request.setAttribute(" xxx ", configDao.list());
		reqDisp = request.getRequestDispatcher("/configPage.jsp");
		reqDisp.forward(request, response);
	}
}
