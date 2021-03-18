package application.servelet;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import application.dao.JdbcEmailDao;
import application.entity.EmailEntity;
import application.entity.UserEntity;
import application.service.ValidationService;
import application.service.ValidationResultSet;




@WebServlet("/EmailRegistryServlet")
public class EmailRegistryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	// Constructor.
	public EmailRegistryServlet() {
		super();
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Logging [EmailRegistryServlet:doPost]");
		
		/* Init RequestDispatcher before loop or try blocks, init once, use several. */
		RequestDispatcher reqDisp = null;
		/* Make sure all attributes are reset each new instance of Servlet. */
		request.setAttribute("emailsList", null);
		// null -> app aways must retrive a fres list from UserDao before send to the frontend.
		request.setAttribute("soughtEmailEdit", null);
		// null -> app not in edit mode, otherwiase, id user to edit.
		
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
		else {
			/* Dependencies: Let a temp objects instantiated fot several uses below. */
			JdbcEmailDao emailDao = new JdbcEmailDao();
			
			/* Select and execute the procedures, in fact. */
			switch (request.getParameter("action")) {
				case "save":
					request.setAttribute("lastAction", "save");
					System.out.println(
							"Logging [EmailRegistryServlet:doPost:Action = " + request.getParameter("action") + "]");
					
					EmailEntity emailToSave = new EmailEntity();
					emailToSave.setId(request.getParameter("id"));
					emailToSave.setEAddress(request.getParameter("eAddress"));
					emailToSave.setUserId(request.getParameter("userId"));
					
					ValidationResultSet emailVRS = new ValidationResultSet();
					emailVRS = ValidationService.validateEmailFull(emailToSave);
					request.setAttribute("emailVRS", emailVRS);
					
					if (emailVRS.getVerdict())
						emailDao.save(emailToSave);
					else
						request.setAttribute("soughtEmailEdit", emailToSave);
					break; // FIM DE SAVE.
					
				case "infodetail":
					request.setAttribute("lastAction", "infodetail");
					System.out.println(
							"Logging [EmailRegistryServlet:doPost:Action = " + request.getParameter("action") + "]");
					break;
				
				case "edit":
					request.setAttribute("lastAction", "edit");
					System.out.println(
							"Debug Logging [EmailRegistryServlet:doPost:Action = " + request.getParameter("action")
									+ "]");
					request.setAttribute("soughtEmailEdit",
							emailDao.seekId(Long.parseLong(request.getParameter("id"))));
					break;
				
				case "exclude":
					request.setAttribute("lastAction", "exclude");
					System.out.println(
							"Logging [EmailRegistryServlet:doPost:Action = " + request.getParameter("action") + "]");
					emailDao.excludeById(Long.parseLong(request.getParameter("id")));
					break;
				
				default:
					request.setAttribute("lastAction", "default");
					System.out.println(
							"Logging [EmailRegistryServlet:doPost:Action = " + request.getParameter("action")
									+ "] // CASE DEFAULT.");
					break;
			}
			
			if ((request.getParameter("userId") != null)
					&& (Long.parseLong(request.getParameter(
							"userId")) == ((Long) ((UserEntity) request.getSession().getAttribute("loggedUser"))
									.getId()))) {
				request.setAttribute("emailsList", emailDao.listByUser(loggedUser));
			}
			else {
				request.setAttribute("emailsList", "");
			}
			
			reqDisp = request.getRequestDispatcher("/emailRegistry.jsp");
			reqDisp.forward(request, response);
		}
	}
}
