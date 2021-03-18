package application.servelet;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import application.dao.JdbcUserLevelDao;
import application.entity.UserEntity;
import application.service.ValidationService;
import application.service.ValidationResultSet;




@WebServlet("/UserLevelRegistryServlet")
public class UserLevelRegistryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	/* Constructor. */
	public UserLevelRegistryServlet() {
		super();
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Logging [UserRegistryServlet:doPost]");
		
		/* Init RequestDispatcher before loop or try blocks, init once, use several. */
		RequestDispatcher reqDisp = null;
		/* Make sure all attributes are reset each new instance of Servlet. */
		request.setAttribute("userLevelsList", null);
		// null -> app aways must retrive a free list from UserLevelDao before send to the frontend.
		request.setAttribute("soughtUserLevelEdit", null);
		// null -> app not in edit mode, otherwiase, contents an userLevel (bean) to edit.
		
		/* ### INÍCIO - TÉCNICA DE ASR-URI ### */
		HttpSession loggedSession = request.getSession();
		UserEntity loggedUser = (UserEntity) loggedSession.getAttribute("loggedUser");
		long loggedUsesrIdFromParam =
				(request.getParameter("userId") != null) ? Long.parseLong(request.getParameter("userId")) : 0L;
		
		if ((loggedUser == null) || (loggedUsesrIdFromParam != loggedUser.getId())) {
			request.setAttribute("lastAction", null);
			request.setAttribute("usersList", null);
			reqDisp = request.getRequestDispatcher("resources/error-pages/asr-uri-error.jsp");
			reqDisp.forward(request, response);
		}
		/* ### FIM - TÉCNICA DE ASR-URI ### */
		
		/* Dependencies: Let a temp objects instantiated fot several uses below. */
		JdbcUserLevelDao userLevelDao = new JdbcUserLevelDao();
		String userLevelToSave = new String();
		ValidationResultSet userLevelVRS = new ValidationResultSet();
		
		/* Select and execute the procedures, in fact. */
		switch (request.getParameter("action")) {
			case "insert":
				request.setAttribute("lastAction", "insert");
				System.out.println(
						"Logging [UserLevelRegistryServlet:doPost:Action = " + request.getParameter("action") + "]");
				userLevelToSave = request.getParameter("name");
				
				/* Of course these 2 statements could be cast in 1 statement,
				 * but I need userVRS later. */
				userLevelVRS = ValidationService.validateUserLevelFull(userLevelToSave);
				request.setAttribute("userLevelVRS", userLevelVRS);
				
				if (userLevelVRS.getVerdict()) {
					System.out.println("DEBUG-Logging [UserRegistryServlet:action=insert:if-getVeredict-true.");
					userLevelDao.insert(userLevelToSave);
				}
				else {
					System.out.println("DEBUG-Logging [UserRegistryServlet:action=insert:if-getVeredict-false-else.");
					request.setAttribute("soughtUserLevelEdit", userLevelToSave);
				}
				break;
			
			case "update":
				request.setAttribute("lastAction", "update");
				System.out.println(
						"Logging [UserLevelRegistryServlet:doPost:Action = " + request.getParameter("action") + "]");
				userLevelToSave = request.getParameter("name");
				
				/* Of course these 2 statements could be cast in 1 statement,
				 * but I need userVRS later. */
				userLevelVRS = ValidationService.validateUserLevelFull(userLevelToSave);
				request.setAttribute("userLevelVRS", userLevelVRS);
				
				if (userLevelVRS.getVerdict()) {
					System.out.println("DEBUG-Logging [UserRegistryServlet:action=insert:if-getVeredict-true.");
					userLevelDao.update(userLevelToSave, request.getParameter("oldName"));
				}
				else {
					System.out.println("DEBUG-Logging [UserRegistryServlet:action=insert:if-getVeredict-false-else.");
					request.setAttribute("soughtUserLevelEdit", userLevelToSave);
				}
				break;
			
			case "infodetail":
				request.setAttribute("lastAction", "infodetail");
				System.out.println(
						"Logging [UserLevelRegistryServlet:doPost:Action = " + request.getParameter("action") + "]");
				break;
			
			case "edit":
				request.setAttribute("lastAction", "edit");
				System.out.println(
						"Logging [UserLevelRegistryServlet:doPost:Action = " + request.getParameter("action") + "]");
				request.setAttribute("soughtUserLevelEdit", userLevelDao.seekName(request.getParameter("name")));
				break;
			
			case "exclude":
				request.setAttribute("lastAction", "exclude");
				System.out.println(
						"Logging [UserLevelRegistryServlet:doPost:Action = " + request.getParameter("action") + "]");
				userLevelDao.exclude(request.getParameter("name"));
				break;
			
			default:
				request.setAttribute("lastAction", "default");
				System.out.println(
						"Logging [UserLevelRegistryServlet:doPost:Action = " + request.getParameter("action") + "]");
				break;
		}
		
		// After whatever case from 'request.getParameter("action")' done, we must do to List.
		request.setAttribute("userLevelsList", userLevelDao.list());
		reqDisp = request.getRequestDispatcher("/userLevelRegistry.jsp");
		reqDisp.forward(request, response);
	}
}
