package application.servelet;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import application.dao.JdbcUserDao;
import application.entity.UserEntity;
import application.entity.dto.UserCompactDTO;
import application.service.ValidationResultSet;
import application.service.ValidationService;



@WebServlet("/UserRegistryServlet")
@MultipartConfig
public class UserRegistryServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	
	
	/* Constructor. */
	public UserRegistryServlet() {
		
		super();
		
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);
		
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/* Init RequestDispatcher before loop or try-catch-blocks, init once, use several. */
		RequestDispatcher reqDisp = null;
		
		/* Make sure all attributes are reset each new instance of servlets. */
		request.setAttribute("usersList", null);
		// null -> app always must retrieve a free list from UserDao before send to the frontend.
		request.setAttribute("savingEditingUser;", null);
		// null -> app not in edit mode, otherwise, contents an user (entity) to edit.
		
		/* ### INÍCIO - TÉCNICA DE ASR-URI ### */
		HttpSession loggedSession = request.getSession();
		
		/* loggedUserCompactDTOFromSessionAttrib */
		UserCompactDTO lucdfsa = (UserCompactDTO) loggedSession.getAttribute("lucdsa");
		
		/* loggedUsesrIdFromParam */
		long luifp = ((request.getParameter("loggedUserIdParam") != null)
				&& (!request.getParameter("loggedUserIdParam").isEmpty()))
						? Long.parseLong(request.getParameter("loggedUserIdParam")) : -1L;
		
		if ((lucdfsa == null) || (luifp != lucdfsa.getId())) {
			request.setAttribute("lastAction", null);
			request.setAttribute("usersList", null);
			reqDisp = request.getRequestDispatcher("resources/error-pages/asr-uri-error.jsp");
			reqDisp.forward(request, response);
		}
		/* ### FIM - TÉCNICA DE ASR-URI ### */
		
		/* Dependencies: Let a temp objects instantiated fot several uses below. */
		JdbcUserDao userDAO = new JdbcUserDao();
		
		/* Select and execute the procedures, in fact. */
		switch (request.getParameter("action")) {
			case "save":
				request.setAttribute("lastAction", "save");
				
				UserEntity userToSave = new UserEntity();
				userToSave.setId(request.getParameter("id"));
				userToSave.setFullName(request.getParameter("fullName"));
				userToSave.setUserName(request.getParameter("userName"));
				userToSave.setPassword(request.getParameter("password"));
				userToSave.setUserLevel(request.getParameter("userLevel"));
				userToSave.setAddrPostalCode(request.getParameter("addrPostalCode"));
				userToSave.setAddrFu(request.getParameter("addrFu"));
				userToSave.setAddrCity(request.getParameter("addrCity"));
				userToSave.setAddrNeighborhood(request.getParameter("addrNeighborhood"));
				userToSave.setAddrVia(request.getParameter("addrVia"));
				userToSave.setAddrNumber(request.getParameter("addrNumber"));
				userToSave.setAddrComplement(request.getParameter("addrComplement"));
				// Se os campos de arquivos para upload estiverem inválidos
				// então não quer mudar nada de foto, deve-se manter a mesma.
				if ((request.getPart("userPictureInput") != null) && ServletFileUpload.isMultipartContent(request)) {
					userToSave.setPictureBase64(request.getPart("userPictureInput"));
				}
				
				/* Of course these 2 statements could be cast in 1 statement,
				 * but I need userVRS later. */
				ValidationResultSet userVRS = ValidationService.validateUserFull(userToSave);
				request.setAttribute("userVRS", userVRS);
				
				if (userVRS.getVerdict()) userDAO.save(userToSave);
				else request.setAttribute("savingEditingUser", userToSave);
				
				break; // FIM DE SAVE.
				
			case "infodetail":
				request.setAttribute("lastAction", "infodetail");
				request.setAttribute("infoDetailUser",
						userDAO.seekId(Long.parseLong(request.getParameter("infoDetailUserId"))));
				break;
			
			case "edit":
				request.setAttribute("lastAction", "edit");
				request.setAttribute("savingEditingUser",
						userDAO.seekId(Long.parseLong(request.getParameter("editUserId"))));
				break;
			
			case "exclude":
				request.setAttribute("lastAction", "exclude");
				userDAO.exclude(Long.parseLong(request.getParameter("excludeUserId")));
				break;
			
			default: // By default whatever else value from 'request.getParameter("action")' we must do nothing.
				request.setAttribute("lastAction", "default");
				break;
		}
		
		// After whatever case from 'request.getParameter("action")' done, we must do to List.
		request.setAttribute("usersList", userDAO.list());
		reqDisp = request.getRequestDispatcher("/userRegistry.jsp?loggedUserIdParam=" + lucdfsa.getId());
		reqDisp.forward(request, response);
		
	}
	
}
